package com.sammy.malum.core.handlers;

import com.mojang.blaze3d.systems.RenderSystem;
import com.sammy.malum.MalumMod;
import com.sammy.malum.common.capability.MalumPlayerDataCapability;
import com.sammy.malum.config.CommonConfig;
import com.sammy.malum.common.item.IMalumEventResponderItem;
import com.sammy.malum.registry.common.AttributeRegistry;
import com.sammy.malum.registry.common.SoundRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.event.tick.*;
import org.joml.Vector4f;
import team.lodestar.lodestone.helpers.ItemHelper;
import team.lodestar.lodestone.registry.client.*;
import team.lodestar.lodestone.registry.common.tag.*;
import team.lodestar.lodestone.systems.rendering.VFXBuilders;
import team.lodestar.lodestone.systems.rendering.shader.ExtendedShaderInstance;


public class SoulWardHandler {
    public double soulWard;
    public double soulWardProgress;

    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putDouble("soulWard", soulWard);
        tag.putDouble("soulWardProgress", soulWardProgress);
        return tag;
    }

    public void deserializeNBT(CompoundTag tag) {
        soulWard = tag.getDouble("soulWard");
        soulWardProgress = tag.getDouble("soulWardProgress");
    }

    public static void recoverSoulWard(PlayerTickEvent event) {
        var player = event.getEntity();
        if (!player.level().isClientSide) {
            var handler = MalumPlayerDataCapability.getCapability(player).soulWardHandler;
            var capacity = player.getAttribute(AttributeRegistry.SOUL_WARD_CAP);
            if (capacity != null) {
                if (handler.soulWard < capacity.getValue()) {
                    if (handler.soulWardProgress <= 0) {
                        handler.recoverSoulWard(player);
                    } else {
                        handler.soulWardProgress--;
                    }
                }
                if (handler.soulWard > capacity.getValue()) {
                    handler.soulWard = (float) capacity.getValue();
                    MalumPlayerDataCapability.syncTrackingAndSelf(player);
                }
            }
        }
    }

    public static void shieldPlayer(LivingDamageEvent.Post event) {
        if (event.getEntity().level().isClientSide()) {
            return;
        }
        if (event.isCanceled() || event.getAmount() <= 0) {
            return;
        }
        if (event.getEntity() instanceof Player player) {
            SoulWardHandler soulWardHandler = MalumPlayerDataCapability.getCapability(player).soulWardHandler;
            soulWardHandler.soulWardProgress = getSoulWardCooldown(1) + getSoulWardCooldown(player);
            if (soulWardHandler.soulWard > 0) {
                DamageSource source = event.getSource();

                float amount = event.getAmount();
                float multiplier = source.is(LodestoneDamageTypeTags.IS_MAGIC) ? CommonConfig.SOUL_WARD_MAGIC.getConfigValue().floatValue() : CommonConfig.SOUL_WARD_PHYSICAL.getConfigValue().floatValue();

                for (ItemStack s : ItemHelper.getEventResponders(player)) {
                    if (s.getItem() instanceof IMalumEventResponderItem eventItem) {
                        multiplier = eventItem.adjustSoulWardDamageAbsorption(event, player, s, multiplier);
                        break;
                    }
                }
                float result = amount * multiplier;
                float absorbed = amount - result;
                double strength = player.getAttributeValue(AttributeRegistry.SOUL_WARD_INTEGRITY.get());
                double soulwardLost =  soulWardHandler.soulWard - (absorbed / strength);
                if (strength != 0) {
                    soulWardHandler.soulWard = Math.max(0, soulwardLost);
                } else {
                    soulwardLost = soulWardHandler.soulWard;
                    soulWardHandler.soulWard = 0;
                }
                for (ItemStack s : ItemHelper.getEventResponders(player)) {
                    if (s.getItem() instanceof IMalumEventResponderItem eventItem) {
                        eventItem.onSoulwardAbsorbDamage(event, player, s, soulwardLost, absorbed);
                    }
                }
                var sound = soulWardHandler.soulWard == 0 ? SoundRegistry.SOUL_WARD_DEPLETE.get() : SoundRegistry.SOUL_WARD_HIT.get();
                player.playSound(sound, 1, Mth.nextFloat(player.getRandom(), 1f, 1.5f));
                event.setAmount(result);
                MalumPlayerDataCapability.syncTrackingAndSelf(player);
            }
        }
    }

    public void recoverSoulWard(Player player) {
        soulWard++;
        if (!player.isCreative()) {
            var capacity = player.getAttribute(AttributeRegistry.SOUL_WARD_CAP.get());
            if (capacity != null) {
                var sound = soulWard >= capacity.getValue() ? SoundRegistry.SOUL_WARD_CHARGE.get() : SoundRegistry.SOUL_WARD_GROW.get();
                double pitchOffset = (soulWard / capacity.getValue()) * 0.5f + (Mth.ceil(soulWard) % 3) * 0.25f;
                player.playSound(sound, 0.25f, (float) (1f + pitchOffset));
            }
        }
        soulWardProgress += getSoulWardCooldown(player);
        MalumPlayerDataCapability.syncTrackingAndSelf(player);
    }

    public static float getSoulWardCooldown(Player player) {
        return getSoulWardCooldown(player.getAttributeValue(AttributeRegistry.SOUL_WARD_RECOVERY_RATE.get()));
    }

    public static float getSoulWardCooldown(double recoverySpeed) {
        return Mth.floor(CommonConfig.SOUL_WARD_RATE.getConfigValue() / recoverySpeed);
    }

    public static class ClientOnly {
        public static void renderSoulWard(ForgeGui gui, GuiGraphics guiGraphics, int width, int height) {
            var minecraft = Minecraft.getInstance();
            var poseStack = guiGraphics.pose();
            if (!minecraft.options.hideGui && gui.shouldDrawSurvivalElements()) {
                var player = minecraft.player;
                if (!player.isCreative() && !player.isSpectator()) {
                    var handler = MalumPlayerDataCapability.getCapability(player).soulWardHandler;
                    double soulWard = handler.soulWard;
                    if (soulWard > 0) {
                        float absorb = Mth.ceil(player.getAbsorptionAmount());
                        float maxHealth = (float) player.getAttribute(Attributes.MAX_HEALTH).getValue();
                        float armor = (float) player.getAttribute(Attributes.ARMOR).getValue();

                        int left = width / 2 - 91;
                        int top = height - gui.leftHeight;

                        if (armor == 0) {
                            top += 4;
                        }
                        int healthRows = Mth.ceil((maxHealth + absorb) / 2.0F / 10.0F);
                        int rowHeight = Math.max(10 - (healthRows - 2), 3);

                        poseStack.pushPose();
                        gui.setupOverlayRenderState(true, false);
                        RenderSystem.setShaderTexture(0, getSoulWardTexture());
                        RenderSystem.depthMask(true);
                        RenderSystem.enableBlend();
                        RenderSystem.defaultBlendFunc();
                        ExtendedShaderInstance shaderInstance = (ExtendedShaderInstance) LodestoneShaderRegistry.DISTORTED_TEXTURE.getInstance().get();
                        shaderInstance.safeGetUniform("YFrequency").set(15f);
                        shaderInstance.safeGetUniform("XFrequency").set(15f);
                        shaderInstance.safeGetUniform("Speed").set(550f);
                        shaderInstance.safeGetUniform("Intensity").set(120f);
                        var builder = VFXBuilders.createScreen()
                                .setPosColorTexDefaultFormat()
                                .setShader(() -> shaderInstance);

                        int size = 13;
                        boolean forceDisplay = soulWard <= 1;
                        double soulWardAmount = forceDisplay ? 1 : Math.ceil(Math.floor(soulWard) / 3f);
                        for (int i = 0; i < soulWardAmount; i++) {
                            int row = (int) (i / 10f);
                            int x = left + i % 10 * 8;
                            int y = top - row * 4 + rowHeight * 2 - 15;
                            int progress = Math.min(3, (int) soulWard - i * 3);
                            int xTextureOffset = forceDisplay ? 31 : 1 + (3 - progress) * 15;

                            shaderInstance.safeGetUniform("UVCoordinates").set(new Vector4f(xTextureOffset / 45f, (xTextureOffset + size) / 45f, 0, 15 / 45f));
                            shaderInstance.safeGetUniform("TimeOffset").set(i * 150f);

                            builder.setPositionWithWidth(x - 2, y - 2, size, size)
                                    .setUVWithWidth(xTextureOffset, 0, size, size, 45)
                                    .draw(poseStack);
                        }
                        shaderInstance.setUniformDefaults();
                        RenderSystem.depthMask(true);
                        RenderSystem.disableBlend();
                        poseStack.popPose();
                    }
                }
            }
        }

        public static ResourceLocation getSoulWardTexture() {
            return MalumMod.malumPath("textures/gui/soul_ward/default.png");
        }
    }
}