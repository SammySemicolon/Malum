package com.sammy.malum.core.handlers;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.sammy.malum.MalumMod;
import com.sammy.malum.common.capability.MalumPlayerDataCapability;
import com.sammy.malum.config.CommonConfig;
import com.sammy.malum.common.item.IMalumEventResponderItem;
import com.sammy.malum.mixin.AccessorEvent;
import com.sammy.malum.registry.common.AttributeRegistry;
import com.sammy.malum.registry.common.SoundRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.*;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import org.joml.Vector4f;
import team.lodestar.lodestone.helpers.ItemHelper;
import team.lodestar.lodestone.registry.client.*;
import team.lodestar.lodestone.registry.common.tag.*;
import team.lodestar.lodestone.systems.rendering.VFXBuilders;
import team.lodestar.lodestone.systems.rendering.shader.ExtendedShaderInstance;


public class SoulWardHandler {
    public float soulWard;
    public float soulWardProgress;

    public static Codec<SoulWardHandler> CODEC = RecordCodecBuilder.create(obj -> obj.group(
         Codec.FLOAT.fieldOf("soulWard").forGetter(sw -> sw.soulWard),
            Codec.FLOAT.fieldOf("soulWardProgress").forGetter(sw -> sw.soulWardProgress)
    ).apply(obj, SoulWardHandler::new));

    public SoulWardHandler() {}

    public SoulWardHandler(float soulWard, float soulWardProgress) {
        this.soulWard = soulWard;
        this.soulWardProgress = soulWardProgress;
    }

    public static void recoverSoulWard(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (!player.level().isClientSide) {
            SoulWardHandler soulWardHandler = MalumPlayerDataCapability.getCapability(player).soulWardHandler;
            AttributeInstance soulWardCap = player.getAttribute(AttributeRegistry.SOUL_WARD_CAP);
            if (soulWardCap != null) {
                if (soulWardHandler.soulWard < soulWardCap.getValue() && soulWardHandler.soulWardProgress <= 0) {
                    soulWardHandler.soulWard++;
                    if (!player.isCreative()) {
                        SoundEvent sound = soulWardHandler.soulWard >= soulWardCap.getValue() ? SoundRegistry.SOUL_WARD_CHARGE.get() : SoundRegistry.SOUL_WARD_GROW.get();
                        float pitch = 1f + (soulWardHandler.soulWard / (float) soulWardCap.getValue()) * 0.5f + (Mth.ceil(soulWardHandler.soulWard) % 3) * 0.25f;
                        player.level().playSound(null, player.blockPosition(), sound, SoundSource.PLAYERS, 0.25f, pitch);
                    }
                    soulWardHandler.soulWardProgress = getSoulWardCooldown(player);
                    MalumPlayerDataCapability.syncTrackingAndSelf(player);
                } else {
                    soulWardHandler.soulWardProgress--;
                }
                if (soulWardHandler.soulWard > soulWardCap.getValue()) {
                    soulWardHandler.soulWard = (float) soulWardCap.getValue();
                    MalumPlayerDataCapability.syncTrackingAndSelf(player);
                }
            }
        }
    }

    public static void shieldPlayer(LivingDamageEvent.Post event) {
        AccessorEvent.PostDamage evnt = (AccessorEvent.PostDamage) event;
        if (evnt.malum$isCancelled() || event.getOriginalDamage() <= 0) {
            return;
        }
        if (event.getEntity() instanceof Player player) {
            if (!player.level().isClientSide) {
                SoulWardHandler soulWardHandler = MalumPlayerDataCapability.getCapability(player).soulWardHandler;
                soulWardHandler.soulWardProgress = getSoulWardCooldown(0) + getSoulWardCooldown(player);
                if (soulWardHandler.soulWard > 0) {
                    DamageSource source = event.getSource();

                    float amount = event.getOriginalDamage();
                    float multiplier = source.is(LodestoneDamageTypeTags.IS_MAGIC) ? CommonConfig.SOUL_WARD_MAGIC.getConfigValue().floatValue() : CommonConfig.SOUL_WARD_PHYSICAL.getConfigValue().floatValue();

                    for (ItemStack s : ItemHelper.getEventResponders(player)) {
                        if (s.getItem() instanceof IMalumEventResponderItem eventItem) {
                            multiplier = eventItem.overrideSoulwardDamageAbsorbPercentage(event, player, s, multiplier);
                            break;
                        }
                    }
                    float result = amount * multiplier;
                    float absorbed = amount - result;
                    double strength = 1+player.getAttributeValue(AttributeRegistry.SOUL_WARD_STRENGTH);
                    float soulwardLost = (float) (soulWardHandler.soulWard - (absorbed / strength));
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
                    SoundEvent sound = soulWardHandler.soulWard == 0 ? SoundRegistry.SOUL_WARD_DEPLETE.get() : SoundRegistry.SOUL_WARD_HIT.get();
                    player.level().playSound(null, player.blockPosition(), sound, player.getSoundSource(), 1, Mth.nextFloat(player.getRandom(), 1f, 1.5f));
                    evnt.malum$setNewDamage(result);

                    MalumPlayerDataCapability.syncTrackingAndSelf(player);
                }
            }
        }
    }

    public static int getSoulWardCooldown(Player player) {
        return getSoulWardCooldown(player.getAttributeValue(AttributeRegistry.SOUL_WARD_RECOVERY_RATE));
    }

    public static int getSoulWardCooldown(double recoverySpeed) {
        int baseValue = CommonConfig.SOUL_WARD_RATE.getConfigValue();
        if (recoverySpeed == 0) {
            return baseValue;
        }
        float n = 0.6f;
        double exponent = 1 + (Math.pow(recoverySpeed * 0.25f + 1, 1 - n) - 1) / (1 - n);
        return (int) (baseValue * (1 / exponent));
    }

    public static class ClientOnly {
        public static void renderSoulWard(ForgeGui gui, GuiGraphics guiGraphics, int width, int height) {
            Minecraft minecraft = Minecraft.getInstance();
            PoseStack poseStack = guiGraphics.pose();
            if (!minecraft.options.hideGui && gui.shouldDrawSurvivalElements()) {
                gui.setupOverlayRenderState(true, false);
                LocalPlayer player = minecraft.player;
                if (!player.isCreative() && !player.isSpectator()) {
                    SoulWardHandler soulWardHandler = MalumPlayerDataCapability.getCapability(player).soulWardHandler;
                    final float soulWard = soulWardHandler.soulWard;
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
                        RenderSystem.setShaderTexture(0, getSoulWardTexture());
                        RenderSystem.depthMask(true);
                        RenderSystem.enableBlend();
                        RenderSystem.defaultBlendFunc();
                        ExtendedShaderInstance shaderInstance = (ExtendedShaderInstance) LodestoneShaderRegistry.DISTORTED_TEXTURE.getInstance().get();
                        shaderInstance.safeGetUniform("YFrequency").set(15f);
                        shaderInstance.safeGetUniform("XFrequency").set(15f);
                        shaderInstance.safeGetUniform("Speed").set(550f);
                        shaderInstance.safeGetUniform("Intensity").set(120f);
                        VFXBuilders.ScreenVFXBuilder builder = VFXBuilders.createScreen()
                                .setPosTexColorDefaultFormat()
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