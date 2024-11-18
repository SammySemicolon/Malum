package com.sammy.malum.common.item.curiosities.curios.sets.weeping;

import com.mojang.blaze3d.systems.*;
import com.sammy.malum.*;
import com.sammy.malum.common.capability.*;
import com.sammy.malum.common.entity.hidden_blade.*;
import com.sammy.malum.common.item.*;
import com.sammy.malum.common.item.curiosities.curios.MalumCurioItem;
import com.sammy.malum.core.handlers.*;
import com.sammy.malum.core.helpers.*;
import com.sammy.malum.core.systems.spirit.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.item.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.player.*;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.*;
import net.minecraft.server.level.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.*;
import net.minecraftforge.client.gui.overlay.*;
import net.minecraftforge.event.*;
import net.minecraftforge.event.entity.living.*;
import org.lwjgl.opengl.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.registry.client.*;
import team.lodestar.lodestone.registry.common.*;
import team.lodestar.lodestone.registry.common.tag.LodestoneDamageTypeTags;
import team.lodestar.lodestone.systems.rendering.*;
import team.lodestar.lodestone.systems.rendering.shader.*;

import java.util.function.Consumer;

public class CurioHiddenBladeNecklace extends MalumCurioItem implements IMalumEventResponderItem, IVoidItem {

    public static final int COOLDOWN_DURATION = 200;

    public CurioHiddenBladeNecklace(Properties builder) {
        super(builder, MalumTrinketType.VOID);
    }

    @Override
    public void addExtraTooltipLines(Consumer<Component> consumer) {
        consumer.accept(positiveEffect("scythe_counterattack"));
        consumer.accept(positiveEffect("enhanced_maneuvers"));
        consumer.accept(negativeEffect("pacifist_recharge"));
        consumer.accept(negativeEffect("no_sweep"));
    }

    @Override
    public void takeDamageEvent(LivingHurtEvent event, LivingEntity attacker, LivingEntity attacked, ItemStack stack) {
        if (attacked.level().isClientSide()) {
            return;
        }
        MalumLivingEntityDataCapability.getCapabilityOptional(attacked).ifPresent(c -> {
            if (c.hiddenBladeCounterCooldown == 0) {
                float damage = event.getAmount();
                int amplifier = Math.min(1 + Mth.floor(damage / 6), 10);
                MobEffect effect = MobEffectRegistry.WICKED_INTENT.get();
                attacked.addEffect(new MobEffectInstance(effect, 100, amplifier));
                SoundHelper.playSound(attacked, SoundRegistry.HIDDEN_BLADE_PRIMED.get(), 1f, RandomHelper.randomBetween(attacked.level().getRandom(), 1.4f, 1.6f));
            }
        });
    }

    @Override
    public void hurtEvent(LivingHurtEvent event, LivingEntity attacker, LivingEntity target, ItemStack stack) {
        var source = event.getSource();
        var level = attacker.level();
        if (level.isClientSide()) {
            return;
        }
        if (!source.is(DamageTypeTagRegistry.IS_SCYTHE)) {
            return;
        }
        if (CurioHelper.hasCurioEquipped(attacker, ItemRegistry.NECKLACE_OF_THE_HIDDEN_BLADE.get())) {
            MalumLivingEntityDataCapability.getCapabilityOptional(attacker).ifPresent(c -> {
                var random = level.getRandom();
                if (c.hiddenBladeCounterCooldown != 0) {
                    if (c.hiddenBladeCounterCooldown <= COOLDOWN_DURATION) {
                        SoundHelper.playSound(attacker, SoundRegistry.HIDDEN_BLADE_DISRUPTED.get(), 1f, RandomHelper.randomBetween(random, 0.7f, 0.8f));
                    }
                    c.hiddenBladeCounterCooldown = (int) (COOLDOWN_DURATION * 1.5);
                    MalumLivingEntityDataCapability.syncSelf((ServerPlayer) attacker);
                    return;
                }
                var effect = attacker.getEffect(MobEffectRegistry.WICKED_INTENT.get());
                if (effect == null) {
                    return;
                }
                var scytheWeapon = SoulDataHandler.getScytheWeapon(source, attacker);
                final boolean isRanged = source.getDirectEntity() != null;
                var damageDealer = isRanged ? source.getDirectEntity() : attacker;
                var direction = isRanged ? damageDealer.getDeltaMovement().normalize() : attacker.getLookAngle();
                var damageCenter = damageDealer.position().add(direction);
                var attributes = attacker.getAttributes();
                float multiplier = (float) Mth.clamp(attributes.getValue(Attributes.ATTACK_SPEED), 0, 2) * ((effect.amplifier*2) + 1);
                int duration = 25;
                float physicalDamage = (float) (attributes.getValue(Attributes.ATTACK_DAMAGE) / duration) * multiplier;
                float magicDamage = (float) (attributes.getValue(LodestoneAttributeRegistry.MAGIC_DAMAGE.get()) / duration) * multiplier;


                var entity = new HiddenBladeDelayedImpactEntity(level, damageCenter.x, damageCenter.y - 3f + attacker.getBbHeight() / 2f, damageCenter.z);
                entity.setData(attacker, physicalDamage, magicDamage, duration);
                entity.setItem(scytheWeapon);
                level.addFreshEntity(entity);
                c.hiddenBladeCounterCooldown = 200;
                if (!effect.isInfiniteDuration()) {
                    attacker.removeEffect(effect.getEffect());
                }
                for (int i = 0; i < 3; i++) {
                    SoundHelper.playSound(attacker, SoundRegistry.HIDDEN_BLADE_UNLEASHED.get(), 3f, RandomHelper.randomBetween(random, 0.75f, 1.25f));
                }
                var particle = ParticleHelper.createSlashingEffect(ParticleEffectTypeRegistry.HIDDEN_BLADE_COUNTER_FLURRY);
                if (scytheWeapon.getItem() instanceof ISpiritAffiliatedItem spiritAffiliatedItem) {
                    particle.setSpiritType(spiritAffiliatedItem);
                }
                particle.setRandomSlashAngle(random)
                        .mirrorRandomly(random)
                        .spawnForwardSlashingParticle(damageDealer, direction);
                MalumLivingEntityDataCapability.syncSelf((ServerPlayer) attacker);
                event.setCanceled(true);
            });
        }
    }
    public static void entityTick(LivingEvent.LivingTickEvent event) {
        var entity = event.getEntity();
        var level = entity.level();
        MalumLivingEntityDataCapability.getCapabilityOptional(entity).ifPresent(c -> {
            if (c.hiddenBladeCounterCooldown > 0) {
                c.hiddenBladeCounterCooldown--;
                if (!level.isClientSide()) {
                    if (c.hiddenBladeCounterCooldown == 0) {
                        SoundHelper.playSound(entity, SoundRegistry.HIDDEN_BLADE_CHARGED.get(), 1f, RandomHelper.randomBetween(level.getRandom(), 1.0f, 1.2f));
                    }
                }
            }
        });
    }

    public static class ClientOnly {

        public static int fadeOut;

        public static void tick(TickEvent.ClientTickEvent event) {
            final LocalPlayer player = Minecraft.getInstance().player;
            if (player != null) {
                var cooldown = MalumLivingEntityDataCapability.getCapability(player).hiddenBladeCounterCooldown;
                if (cooldown == 0) {
                    if (player.hasEffect(MobEffectRegistry.WICKED_INTENT.get())) {
                        if (fadeOut > 30) {
                            fadeOut = 30;
                        }
                        fadeOut -= 2;
                        return;
                    }
                    else if (fadeOut < 0) {
                        fadeOut = 20;
                    }
                    if (fadeOut < 80) {
                        fadeOut++;
                    }
                } else {
                    fadeOut = 0;
                }
            }
        }

        public static void renderHiddenBladeCooldown(ForgeGui gui, GuiGraphics guiGraphics, int width, int height) {
            var minecraft = Minecraft.getInstance();
            var poseStack = guiGraphics.pose();
            if (!minecraft.options.hideGui && gui.shouldDrawSurvivalElements()) {
                var player = minecraft.player;
                if (!player.isCreative() && !player.isSpectator()) {
                    var cooldown = MalumLivingEntityDataCapability.getCapability(player).hiddenBladeCounterCooldown;
                    if (cooldown > 0 || fadeOut <= 80) {
                        int left = width / 2 - 8;
                        int top = height - 52;
                        poseStack.pushPose();
                        gui.setupOverlayRenderState(true, false);
                        RenderSystem.setShaderTexture(0, getTexture());
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

                        float size = 16;
                        double delta = Mth.clamp((COOLDOWN_DURATION-cooldown) / (float)COOLDOWN_DURATION, 0, 1);
                        delta -= 0.125f;
                        final boolean secondRow = delta >= 0.5f;
                        int xOffset = 16*(Mth.floor(delta*8)) - (secondRow ? 64 : 0);
                        int yOffset = secondRow ? 16 : 0;

                        if (fadeOut > 20) {
                            final boolean hasEffect = player.hasEffect(MobEffectRegistry.WICKED_INTENT.get());
                            builder.setAlpha((80 - fadeOut) / (hasEffect ? 10f : 60f));
                        }
                        builder.setPosColorTexDefaultFormat()
                                .setPositionWithWidth(left, top, size, size)
                                .setUVWithWidth(xOffset, yOffset, 16, 16, 64)
                                .draw(poseStack);
                        if (fadeOut > 0 && fadeOut < 20) {
                            float glow = (10 - Math.abs(10 - fadeOut)) / 10f;
                            RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);

                            builder.setAlpha(glow).draw(poseStack);
                        }

                        shaderInstance.setUniformDefaults();
                        RenderSystem.disableBlend();
                        poseStack.popPose();
                    }
                }
            }
        }

        public static ResourceLocation getTexture() {
            return MalumMod.malumPath("textures/gui/hud/hidden_blade.png");
        }
    }
}
