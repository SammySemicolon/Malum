package com.sammy.malum.common.entity.scythe;

import com.sammy.malum.common.enchantment.scythe.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.entity.*;
import net.minecraft.nbt.*;
import net.minecraft.network.syncher.*;
import net.minecraft.server.level.*;
import net.minecraft.util.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.entity.projectile.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import team.lodestar.lodestone.helpers.*;

public class ScytheBoomerangEntity extends AbstractScytheProjectileEntity {

    private static final EntityDataAccessor<Boolean> DATA_ENHANCED = SynchedEntityData.defineId(ScytheBoomerangEntity.class, EntityDataSerializers.BOOLEAN);

    public ScytheBoomerangEntity(Level level) {
        super(EntityRegistry.SCYTHE_BOOMERANG.get(), level);
    }

    public ScytheBoomerangEntity(Level level, double pX, double pY, double pZ) {
        super(EntityRegistry.SCYTHE_BOOMERANG.get(), pX, pY, pZ, level);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(DATA_ENHANCED, false);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("isEnhanced", isEnhanced());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        setEnhanced(compound.getBoolean("isEnhanced"));
    }

    public void setEnhanced(boolean enhanced) {
        getEntityData().set(DATA_ENHANCED, enhanced);
    }

    public boolean isEnhanced() {
        return getEntityData().get(DATA_ENHANCED);
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        returnTimer = 0;
        if (getOwner() instanceof LivingEntity scytheOwner) {
            flyBack(scytheOwner);
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if (isEnhanced()) {
            returnTimer = 0;
            if (getOwner() instanceof LivingEntity scytheOwner) {
                flyBack(scytheOwner);
            }
        }
    }

    @Override
    public void tick() {
        super.tick();
        var scythe = getItem();
        var level = level();
        if (!level.isClientSide()) {
            var owner = getOwner();
            if (owner == null || !owner.isAlive() || !owner.level().equals(level()) || distanceTo(owner) > 1000f) {
                setDeltaMovement(Vec3.ZERO);
                return;
            }
            if (owner instanceof LivingEntity scytheOwner) {
                if (age % 3 == 0) {
                    float pitch = (float) (0.8f + Math.sin(level.getGameTime() * 0.5f) * 0.2f);
                    float volumeScalar = Mth.clamp(age / 12f, 0, 1f);
                    if (isInWater()) {
                        volumeScalar *= 0.2f;
                        pitch *= 0.5f;
                    }
                    SoundHelper.playSound(this, SoundRegistry.SCYTHE_SPINS.get(), 0.6f * volumeScalar, pitch);
                    SoundHelper.playSound(this, SoundRegistry.SCYTHE_SWEEP.get(), 0.4f * volumeScalar, pitch);
                }
                if (returnTimer <= 0) {
                    var ownerPos = scytheOwner.position().add(0, scytheOwner.getBbHeight() * 0.6f, 0);
                    float velocityLimit = 2f;
                    if (isEnhanced()) {
                        double radians = Math.toRadians(90 - scytheOwner.yHeadRot);
                        ownerPos = scytheOwner.position().add(0.75f * Math.sin(radians), scytheOwner.getBbHeight() * 0.5f, 0.75f * Math.cos(radians));
                        velocityLimit = 4f;
                        if (returnTimer == 0) {
                            flyBack(scytheOwner);
                        }
                    }
                    var motion = getDeltaMovement();
                    double velocity = Mth.clamp(motion.length() * 3, 0.5f, velocityLimit);
                    var returnMotion = ownerPos.subtract(position()).normalize().scale(velocity);
                    float distance = distanceTo(scytheOwner);

                    if (isAlive() && distance < 3f) {
                        if (scytheOwner instanceof ServerPlayer player) {
                            ReboundEnchantment.pickupScythe(this, scythe, player);
                            SoundHelper.playSound(this, SoundRegistry.SCYTHE_CATCH.get(), 1.5f, RandomHelper.randomBetween(level().getRandom(), 0.75f, 1.25f));
                            remove(RemovalReason.DISCARDED);
                        }
                    }
                    float delta = 0.1f;
                    double x = Mth.lerp(delta, motion.x, returnMotion.x);
                    double y = Mth.lerp(delta, motion.y, returnMotion.y);
                    double z = Mth.lerp(delta, motion.z, returnMotion.z);
                    setDeltaMovement(new Vec3(x, y, z));
                }
                returnTimer--;
            }
            updateRotation();
        }
    }

    @Override
    public void addTrailPoints() {
        if (isEnhanced()) {
            Vec3 direction = getDeltaMovement().normalize();
            float yRot = ((float) (Mth.atan2(direction.x, direction.z) * (double) (180F / (float) Math.PI)));
            float yaw = (float) Math.toRadians(yRot);
            Vec3 left = new Vec3(-Math.cos(yaw), 0, Math.sin(yaw));
            Vec3 up = left.cross(direction);

            for (int i = 0; i < 2; i++) {
                float progress = (i + 1) * 0.5f;
                Vec3 position = getPosition(progress);
                float scalar = (age + progress) / 2f;
                for (int j = 0; j < 2; j++) {
                    var trail = j == 0 ? theFormer : theLatter;
                    final float angle = spinOffset + 3.14f * j + scalar;
                    Vec3 offset = direction.scale(Math.sin(angle))
                            .add(up.scale(Math.cos(angle)))
                            .normalize().scale(1.2f);
                    trail.addTrailPoint(position.add(offset));
                }
            }
            return;
        }
        super.addTrailPoints();
    }

    public void flyBack(Entity scytheOwner) {
        var ownerPos = scytheOwner.position().add(0, scytheOwner.getBbHeight()*0.5f, 0);
        var returnMotion = ownerPos.subtract(position()).normalize().scale(0.75f);
        setDeltaMovement(returnMotion);
    }
}