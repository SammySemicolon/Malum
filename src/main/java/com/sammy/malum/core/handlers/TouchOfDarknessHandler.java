package com.sammy.malum.core.handlers;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.sammy.malum.*;
import com.sammy.malum.client.VoidRevelationHandler;
import com.sammy.malum.common.block.curiosities.weeping_well.PrimordialSoupBlock;
import com.sammy.malum.common.block.curiosities.weeping_well.VoidConduitBlock;
import com.sammy.malum.common.block.curiosities.weeping_well.VoidConduitBlockEntity;
import com.sammy.malum.common.capability.MalumLivingEntityDataCapability;
import com.sammy.malum.common.capability.MalumPlayerDataCapability;
import com.sammy.malum.common.packets.VoidRejectionPacket;
import com.sammy.malum.registry.client.ShaderRegistry;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.item.ItemRegistry;
import com.sammy.malum.visual_effects.networked.data.PositionEffectData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.*;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.*;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import team.lodestar.lodestone.helpers.BlockHelper;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.rendering.VFXBuilders;
import team.lodestar.lodestone.systems.rendering.shader.ExtendedShaderInstance;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

import static com.sammy.malum.client.VoidRevelationHandler.RevelationType.BLACK_CRYSTAL;

public class TouchOfDarknessHandler {

    public static final ResourceLocation GRAVITY_MODIFIER_UUID = MalumMod.malumPath("weeping_well_reduced_gravity");
    public static final float MAX_AFFLICTION = 100f;

    public boolean isNearWeepingWell;
    public int weepingWellInfluence;

    public int expectedAffliction;
    public int afflictionDuration;
    public float currentAffliction;

    public int progressToRejection;
    public int rejection;

    public static final Codec<TouchOfDarknessHandler> CODEC = RecordCodecBuilder.create(obj -> obj.group(
                Codec.BOOL.fieldOf("isNearWeepingWell").forGetter(h -> h.isNearWeepingWell),
                Codec.INT.fieldOf("weepingWellInfluence").forGetter(h -> h.weepingWellInfluence),
                Codec.INT.fieldOf("expectedAffliction").forGetter(h -> h.expectedAffliction),
                Codec.INT.fieldOf("afflictionDuration").forGetter(h -> h.afflictionDuration),
                Codec.FLOAT.fieldOf("currentAffliction").forGetter(h -> h.currentAffliction),
                Codec.INT.fieldOf("progressToRejection").forGetter(h -> h.progressToRejection),
                Codec.INT.fieldOf("rejection").forGetter(h -> h.rejection)
        ).apply(obj, TouchOfDarknessHandler::new));

    public TouchOfDarknessHandler() {}

    public TouchOfDarknessHandler(boolean isNearWeepingWell, int weepingWellInfluence, int expectedAffliction, int afflictionDuration, float currentAffliction, int progressToRejection, int rejection) {
        this.isNearWeepingWell = isNearWeepingWell;
        this.weepingWellInfluence = weepingWellInfluence;
        this.expectedAffliction = expectedAffliction;
        this.afflictionDuration = afflictionDuration;
        this.currentAffliction = currentAffliction;
        this.progressToRejection = progressToRejection;
        this.rejection = rejection;
    }

    public static void handlePrimordialSoupContact(LivingEntity livingEntity) {
        TouchOfDarknessHandler touchOfDarknessHandler = MalumLivingEntityDataCapability.getCapability(livingEntity).touchOfDarknessHandler;
        if (touchOfDarknessHandler.rejection > 0) {
            return;
        }
        livingEntity.setDeltaMovement(livingEntity.getDeltaMovement().scale(0.4f));
        touchOfDarknessHandler.afflict(100);
    }

    public static Optional<VoidConduitBlockEntity> checkForWeepingWell(LivingEntity livingEntity) {
        return BlockHelper.getBlockEntitiesStream(VoidConduitBlockEntity.class, livingEntity.level(), livingEntity.blockPosition(), 8).findFirst();
    }

    public static void entityTick(EntityTickEvent event) {
        if (event.getEntity() instanceof LivingEntity livingEntity) {
            Level level = livingEntity.level();
            TouchOfDarknessHandler handler = MalumLivingEntityDataCapability.getCapability(livingEntity).touchOfDarknessHandler;

            if (livingEntity instanceof Player player) {
                if (level.getGameTime() % 20L == 0) {
                    final Optional<VoidConduitBlockEntity> voidConduitBlockEntity = checkForWeepingWell(player);
                    handler.isNearWeepingWell = voidConduitBlockEntity.isPresent();
                }
                if (handler.isNearWeepingWell) {
                    handler.weepingWellInfluence++;
                }
            }
            Block block = level.getBlockState(livingEntity.blockPosition()).getBlock();
            boolean isInTheGoop = block instanceof PrimordialSoupBlock || block instanceof VoidConduitBlock;
            if (!isInTheGoop) {
                block = level.getBlockState(livingEntity.blockPosition().above()).getBlock();
                isInTheGoop = block instanceof PrimordialSoupBlock || block instanceof VoidConduitBlock;
            }
            //VALUE UPDATES
            if (handler.afflictionDuration > 0) {
                handler.afflictionDuration--;
                if (handler.afflictionDuration == 0) {
                    handler.expectedAffliction = 0;
                }
            }
            if (handler.currentAffliction < handler.expectedAffliction) {
                handler.currentAffliction = Math.min(MAX_AFFLICTION, handler.currentAffliction + 1.5f);
            }
            if (handler.currentAffliction > handler.expectedAffliction) {
                handler.currentAffliction = Math.max(handler.currentAffliction - (handler.expectedAffliction == 0 ? 1.5f : 0.75f), handler.expectedAffliction);
            }
            //GRAVITY

            AttributeInstance gravity = livingEntity.getAttribute(Attributes.GRAVITY);
            if (gravity != null) {
                boolean hasModifier = gravity.getModifier(GRAVITY_MODIFIER_UUID) != null;
                if (handler.progressToRejection > 0) {
                    if (!hasModifier) {
                        gravity.addTransientModifier(getEntityGravityAttributeModifier(livingEntity));
                    }
                    gravity.setDirty();
                } else if (hasModifier) {
                    gravity.removeModifier(GRAVITY_MODIFIER_UUID);
                }
            }
            //REJECTION
            if (isInTheGoop) {
                if (!(livingEntity instanceof Player player) || (!player.isSpectator())) {
                    handler.progressToRejection++;
                    if (!level.isClientSide) {
                        if (livingEntity instanceof Player && level.getGameTime() % 6L == 0) {
                            level.playSound(null, livingEntity.blockPosition(), SoundRegistry.SONG_OF_THE_VOID, SoundSource.HOSTILE, 0.5f + handler.progressToRejection * 0.02f, 0.5f + handler.progressToRejection * 0.03f);
                        }
                        if (handler.rejection == 0 && handler.progressToRejection > 60) {
                            handler.reject(livingEntity);
                        }
                    }
                }
            } else {
                handler.progressToRejection = 0;
            }

            //MOTION
            if (handler.rejection > 0) {
                handler.rejection--;
                float intensity = handler.rejection / 40f;
                Vec3 movement = livingEntity.getDeltaMovement();
                livingEntity.setDeltaMovement(movement.x, Math.pow(intensity, 2), movement.z);
            }
        }
    }

    public void afflict(int expectedAffliction) {
        if (this.expectedAffliction > expectedAffliction) {
            return;
        }
        this.expectedAffliction = expectedAffliction;
        this.afflictionDuration = 60;
    }

    public void reject(LivingEntity livingEntity) {
        if (!(livingEntity instanceof Player player)) {
            livingEntity.remove(Entity.RemovalReason.DISCARDED);
            return;
        }

        var playerDataCapability = MalumPlayerDataCapability.getCapability(player);

        final Level level = livingEntity.level();
        progressToRejection = 0;
        rejection = 40;
        if (!level.isClientSide) {
            PacketRegistry.MALUM_CHANNEL.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> livingEntity), new VoidRejectionPacket(livingEntity.getId()));
            final Optional<VoidConduitBlockEntity> voidConduitBlockEntity = checkForWeepingWell(livingEntity);
            if (voidConduitBlockEntity.isPresent()) {
                VoidConduitBlockEntity weepingWell = voidConduitBlockEntity.get();
                BlockPos worldPosition = weepingWell.getBlockPos();
                ParticleEffectTypeRegistry.WEEPING_WELL_REACTS.createPositionedEffect(level, new PositionEffectData(worldPosition.getX()+0.5f, worldPosition.getY()+0.6f, worldPosition.getZ()+0.5f));
            }
            else {
                ParticleEffectTypeRegistry.WEEPING_WELL_REACTS.createEntityEffect(livingEntity);
            }
            if (!player.isCreative()) {
                livingEntity.hurt(DamageTypeHelper.create(level, DamageTypeRegistry.VOODOO), 4);
            }
            if (!playerDataCapability.hasBeenRejected) {
                SpiritHarvestHandler.spawnItemAsSpirit(ItemRegistry.UMBRAL_SPIRIT.get().getDefaultInstance(), player, player);
            }
            SoundHelper.playSound(livingEntity, SoundRegistry.VOID_REJECTION, 2f, Mth.nextFloat(livingEntity.getRandom(), 0.5f, 0.8f));
        } else {
            VoidRevelationHandler.seeTheRevelation(BLACK_CRYSTAL);
        }

        playerDataCapability.hasBeenRejected = true;
        livingEntity.addEffect(new MobEffectInstance(MobEffectRegistry.REJECTED, 400, 0));
    }

    public static AttributeModifier getEntityGravityAttributeModifier(LivingEntity livingEntity) {
        return new AttributeModifier(GRAVITY_MODIFIER_UUID, "Weeping Well Gravity Modifier", 0, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL) {
            @Override
            public double getAmount() {
                return updateEntityGravity(livingEntity);
            }
        };
    }

    public static double updateEntityGravity(LivingEntity livingEntity) {
        TouchOfDarknessHandler handler = MalumLivingEntityDataCapability.getCapability(livingEntity).touchOfDarknessHandler;
        if (handler.progressToRejection > 0) {
            return -Math.min(60, handler.progressToRejection) / 60f;
        }
        return 0;
    }

    public static class ClientOnly {

        public static void renderDarknessVignette(GuiGraphics guiGraphics) {
            Minecraft minecraft = Minecraft.getInstance();
            PoseStack poseStack = guiGraphics.pose();
            Player player = minecraft.player;
            TouchOfDarknessHandler darknessHandler = MalumLivingEntityDataCapability.getCapability(player).touchOfDarknessHandler;
            if (darknessHandler.currentAffliction == 0f) {
                return;
            }
            int screenWidth = minecraft.getWindow().getGuiScaledWidth();
            int screenHeight = minecraft.getWindow().getGuiScaledHeight();

            float effectStrength = Easing.SINE_IN_OUT.ease(darknessHandler.currentAffliction / MAX_AFFLICTION, 0, 1, 1);
            float alpha = Math.min(1, effectStrength * 5);
            float zoom = 0.5f + Math.min(0.35f, effectStrength);
            float intensity = 1f + (effectStrength > 0.5f ? (effectStrength - 0.5f) * 2.5f : 0);

            ExtendedShaderInstance shaderInstance = (ExtendedShaderInstance) ShaderRegistry.TOUCH_OF_DARKNESS.getInstance().get();
            shaderInstance.safeGetUniform("Speed").set(1000f);
            Consumer<Float> setZoom = f -> shaderInstance.safeGetUniform("Zoom").set(f);
            Consumer<Float> setIntensity = f -> shaderInstance.safeGetUniform("Intensity").set(f);
            VFXBuilders.ScreenVFXBuilder builder = VFXBuilders.createScreen()
                    .setPosColorTexLightmapDefaultFormat()
                    .setPositionWithWidth(0, 0, screenWidth, screenHeight)
                    .setColor(0, 0, 0)
                    .setAlpha(alpha)
                    .setShader(ShaderRegistry.TOUCH_OF_DARKNESS.getInstance());

            poseStack.pushPose();
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();

            setZoom.accept(zoom);
            setIntensity.accept(intensity);
            builder.draw(poseStack);

            setZoom.accept(zoom * 1.25f + 0.15f);
            setIntensity.accept(intensity * 0.8f + 0.5f);
            builder.setAlpha(0.5f * alpha).draw(poseStack);

            RenderSystem.disableBlend();
            poseStack.popPose();

            shaderInstance.setUniformDefaults();
        }
    }
}
