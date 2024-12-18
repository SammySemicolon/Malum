package com.sammy.malum.core.handlers;

import com.sammy.malum.*;
import com.sammy.malum.client.VoidRevelationHandler;
import com.sammy.malum.common.block.curiosities.weeping_well.VoidConduitBlockEntity;
import com.sammy.malum.common.capabilities.*;
import com.sammy.malum.common.packets.VoidRejectionPayload;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.item.ItemRegistry;
import com.sammy.malum.visual_effects.networked.data.PositionEffectData;
import io.github.fabricators_of_create.porting_lib.entity.events.tick.EntityTickEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.*;
import net.minecraft.server.level.*;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import team.lodestar.lodestone.helpers.*;

import java.util.*;

import static com.sammy.malum.client.VoidRevelationHandler.RevelationType.BLACK_CRYSTAL;

public class TouchOfDarknessHandler {

    public static final ResourceLocation GRAVITY_MODIFIER_ID = MalumMod.malumPath("weeping_well_reduced_gravity");

    public static void handlePrimordialSoupContact(LivingEntity livingEntity) {
        var data = livingEntity.getAttachedOrCreate(AttachmentTypeRegistry.VOID_INFLUENCE);
        if (data.isInRejectedState) {
            return;
        }
        livingEntity.setDeltaMovement(livingEntity.getDeltaMovement().scale(0.4f));
        data.setAfflictionLevel(100);
        data.setGoopStatus();
    }

    public static void entityTick(EntityTickEvent event) {
        if (event.getEntity() instanceof LivingEntity livingEntity) {
            var level = livingEntity.level();
            var data = livingEntity.getAttachedOrCreate(AttachmentTypeRegistry.VOID_INFLUENCE);
            data.update(livingEntity);
            var gravity = livingEntity.getAttribute(Attributes.GRAVITY);
            if (gravity != null) {
                if (gravity.getModifier(GRAVITY_MODIFIER_ID) != null) {
                    gravity.removeModifier(GRAVITY_MODIFIER_ID);
                }
                if (data.isInRejectedState) {
                    gravity.addTransientModifier(getEntityGravityAttributeModifier(livingEntity));
                }
            }
            if (data.isInGoop()) {
                handleRejectionState(level, livingEntity);
            }
        }
    }

    public static void handleRejectionState(Level level, LivingEntity living) {
        var data = living.getAttachedOrCreate(AttachmentTypeRegistry.VOID_INFLUENCE);
        if (!level.isClientSide) {
            if (living instanceof Player && level.getGameTime() % 6L == 0) {
                float volume = 0.5f + data.voidRejection * 0.02f;
                float pitch = 0.5f + data.voidRejection * 0.03f;
                SoundHelper.playSound(living, SoundRegistry.SONG_OF_THE_VOID.get(), SoundSource.HOSTILE, volume, pitch);
            }
            if (data.wasJustRejected()) {
                if (!(living instanceof Player player)) {
                    living.remove(Entity.RemovalReason.DISCARDED);
                    return;
                }
                launchPlayer(player);
            }
        }
        if (data.isInRejectedState && data.voidRejection > 0) {
            float intensity = data.voidRejection / VoidInfluenceData.MAX_REJECTION;
            Vec3 movement = living.getDeltaMovement();
            living.setDeltaMovement(movement.x, Math.pow(intensity, 2), movement.z);
        }
    }

    public static void launchPlayer(Player player) {
        var data = player.getAttachedOrCreate(AttachmentTypeRegistry.PROGRESSION_DATA);
        var level = player.level();
        if (level instanceof ServerLevel serverLevel) {
            final Optional<VoidConduitBlockEntity> voidConduitBlockEntity = VoidInfluenceData.checkForWeepingWell(player);
            if (voidConduitBlockEntity.isPresent()) {
                VoidConduitBlockEntity weepingWell = voidConduitBlockEntity.get();
                BlockPos worldPosition = weepingWell.getBlockPos();
                ParticleEffectTypeRegistry.WEEPING_WELL_REACTS.createPositionedEffect(serverLevel, new PositionEffectData(worldPosition.getX() + 0.5f, worldPosition.getY() + 0.6f, worldPosition.getZ() + 0.5f));
            } else {
                ParticleEffectTypeRegistry.WEEPING_WELL_REACTS.createEntityEffect(player);
            }
            if (!player.isCreative()) {
                player.hurt(DamageTypeHelper.create(level, DamageTypeRegistry.VOODOO_PLAYERLESS), 4);
            }
            if (!data.hasBeenRejected) {
                SoulHarvestHandler.spawnSpirits(level, player, player.position(), List.of(ItemRegistry.ENCYCLOPEDIA_ARCANA.get().getDefaultInstance()));
            }
            PacketRegistry.sendToPlayersTrackingEntityAndSelf(player, new VoidRejectionPayload(player.getId()));
            SoundHelper.playSound(player, SoundRegistry.VOID_REJECTION.get(), 2f, Mth.nextFloat(player.getRandom(), 0.5f, 0.8f));
        } else {
            VoidRevelationHandler.seeTheRevelation(BLACK_CRYSTAL);
        }

        data.hasBeenRejected = true;
        player.addEffect(new MobEffectInstance(MobEffectRegistry.REJECTED, 400, 0));
    }

    public static AttributeModifier getEntityGravityAttributeModifier(LivingEntity livingEntity) {
        return new AttributeModifier(GRAVITY_MODIFIER_ID, updateEntityGravity(livingEntity), AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
    }

    public static double updateEntityGravity(LivingEntity living) {
        var data = living.getAttachedOrCreate(AttachmentTypeRegistry.VOID_INFLUENCE);
        if (data.voidRejection > 0) {
            return -Math.min(60, data.voidRejection) / 60f;
        }
        return 0;
    }
}