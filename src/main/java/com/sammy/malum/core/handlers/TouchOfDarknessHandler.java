package com.sammy.malum.core.handlers;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
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
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.network.PacketDistributor;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.rendering.VFXBuilders;
import team.lodestar.lodestone.systems.rendering.shader.ExtendedShaderInstance;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

import static com.sammy.malum.client.VoidRevelationHandler.RevelationType.BLACK_CRYSTAL;

public class TouchOfDarknessHandler {

    public static final UUID GRAVITY_MODIFIER_UUID = UUID.fromString("d0aea6b5-f6c5-479d-b70c-455e46a62184");
    public static final float MAX_AFFLICTION = 100f;

    public boolean isNearWeepingWell;
    public int weepingWellInfluence;

    public int expectedAffliction;
    public int afflictionDuration;
    public float currentAffliction;

    public int progressToRejection;
    public int rejection;

    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putBoolean("isNearWeepingWell", isNearWeepingWell);
        tag.putInt("weepingWellInfluence", weepingWellInfluence);

        tag.putInt("expectedAffliction", expectedAffliction);
        tag.putInt("afflictionDuration", afflictionDuration);
        tag.putFloat("currentAffliction", currentAffliction);

        tag.putInt("progressToRejection", progressToRejection);
        tag.putInt("rejection", rejection);
        return tag;
    }

    public void deserializeNBT(CompoundTag tag) {
        isNearWeepingWell = tag.getBoolean("isNearWeepingWell");
        weepingWellInfluence = tag.getInt("weepingWellInfluence");

        expectedAffliction = tag.getInt("expectedAffliction");
        afflictionDuration = tag.getInt("afflictionDuration");
        currentAffliction = tag.getFloat("currentAffliction");

        progressToRejection = tag.getInt("progressToRejection");
        rejection = tag.getInt("rejection");
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

    public static void entityTick(LivingEvent.LivingTickEvent event) {
        LivingEntity livingEntity = event.getEntity();
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

        AttributeInstance gravity = livingEntity.getAttribute(ForgeMod.ENTITY_GRAVITY.get());
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
                        SoundHelper.playSound(livingEntity, SoundRegistry.SONG_OF_THE_VOID.get(), SoundSource.HOSTILE, 0.5f + handler.progressToRejection * 0.02f, 0.5f + handler.progressToRejection * 0.03f);
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
                livingEntity.hurt(DamageTypeHelper.create(level, DamageTypeRegistry.VOODOO_PLAYERLESS), 4);
            }
            if (!playerDataCapability.hasBeenRejected) {
                SpiritHarvestHandler.spawnItemAsSpirit(ItemRegistry.UMBRAL_SPIRIT.get().getDefaultInstance(), player, player);
            }
            level.playSound(null, livingEntity.blockPosition(), SoundRegistry.VOID_REJECTION.get(), SoundSource.HOSTILE, 2f, Mth.nextFloat(livingEntity.getRandom(), 0.5f, 0.8f));
        } else {
            VoidRevelationHandler.seeTheRevelation(BLACK_CRYSTAL);
        }

        playerDataCapability.hasBeenRejected = true;
        livingEntity.addEffect(new MobEffectInstance(MobEffectRegistry.REJECTED.get(), 400, 0));
    }

    public static AttributeModifier getEntityGravityAttributeModifier(LivingEntity livingEntity) {
        return new AttributeModifier(GRAVITY_MODIFIER_UUID, "Weeping Well Gravity Modifier", 0, AttributeModifier.Operation.MULTIPLY_TOTAL) {
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
        private static final Tesselator INSTANCE = new Tesselator();

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
                    .setPosColorTexDefaultFormat()
                    .setPositionWithWidth(0, 0, screenWidth, screenHeight)
                    .overrideBufferBuilder(INSTANCE.getBuilder())
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
