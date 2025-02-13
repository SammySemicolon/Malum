package com.sammy.malum.common.entity.scythe;

import com.sammy.malum.client.*;
import com.sammy.malum.common.item.*;
import com.sammy.malum.common.item.curiosities.curios.sets.scythe.*;
import com.sammy.malum.core.handlers.enchantment.*;
import com.sammy.malum.registry.client.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.entity.*;
import com.sammy.malum.visual_effects.*;
import net.minecraft.nbt.*;
import net.minecraft.network.syncher.*;
import net.minecraft.server.level.*;
import net.minecraft.util.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.systems.particle.data.*;
import team.lodestar.lodestone.systems.particle.data.spin.*;
import team.lodestar.lodestone.systems.rendering.trail.*;

public class ScytheBoomerangEntity extends AbstractScytheProjectileEntity {

    private static final EntityDataAccessor<Boolean> DATA_NARROW = SynchedEntityData.defineId(ScytheBoomerangEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_MAELSTROM = SynchedEntityData.defineId(ScytheBoomerangEntity.class, EntityDataSerializers.BOOLEAN);

    public final TrailPointBuilder theFormer = TrailPointBuilder.create(8);
    public final TrailPointBuilder theLatter = TrailPointBuilder.create(8);

    public ScytheBoomerangEntity(Level level) {
        super(EntityRegistry.SCYTHE_BOOMERANG.get(), level);
    }

    public ScytheBoomerangEntity(Level level, double pX, double pY, double pZ) {
        super(EntityRegistry.SCYTHE_BOOMERANG.get(), pX, pY, pZ, level);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_NARROW, false);
        builder.define(DATA_MAELSTROM, false);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("isNarrow", isNarrow());
        compound.putBoolean("isMaelstrom", isMaelstrom());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        setNarrow(compound.getBoolean("isNarrow"));
        setMaelstrom(compound.getBoolean("isMaelstrom"));
    }

    public void setNarrow(boolean setNarrow) {
        getEntityData().set(DATA_NARROW, setNarrow);
    }

    public boolean isNarrow() {
        return getEntityData().get(DATA_NARROW);
    }

    public void setMaelstrom(boolean maelstrom) {
        getEntityData().set(DATA_MAELSTROM, maelstrom);
    }

    public boolean isMaelstrom() {
        return getEntityData().get(DATA_MAELSTROM);
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
        if (isNarrow()) {
            if (returnTimer > 0) {
                final Entity target = result.getEntity();
                LocalizedMaelstromEntity maelstrom = new LocalizedMaelstromEntity(level(), target.getX(), target.getY() + target.getBbHeight()*0.75f, target.getZ());
                maelstrom.setData(getOwner(), damage, magicDamage, 0, 40);
                maelstrom.setItem(getItem());
                level().addFreshEntity(maelstrom);
                returnTimer = 0;
            }
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
        if (level instanceof ServerLevel serverLevel) {
            var owner = getOwner();
            if (owner == null || !owner.isAlive() || !owner.level().equals(level()) || distanceTo(owner) > 1000f) {
                setDeltaMovement(Vec3.ZERO);
                return;
            }
            if (owner instanceof LivingEntity scytheOwner) {
                if (isMaelstrom() && !isNarrow()) {
                    CurioHowlingMaelstromRing.handleMaelstrom(serverLevel, scytheOwner, this);
                }
                playSound();
                returnTimer--;
                if (returnTimer <= 0) {
                    var ownerPos = scytheOwner.position().add(0, scytheOwner.getBbHeight() * 0.6f, 0);
                    float velocityLimit = 2f;
                    if (isNarrow()) {
                        double radians = Math.toRadians(90 - scytheOwner.yHeadRot);
                        ownerPos = scytheOwner.position().add(0.75f * Math.sin(radians), scytheOwner.getBbHeight() * 0.5f, 0.75f * Math.cos(radians));
                        velocityLimit = 4f;
                        if (returnTimer == 0) {
                            flyBack(scytheOwner);
                        }
                    }
                    if (isMaelstrom()) {
                        velocityLimit -= 1.25f;
                    }
                    var motion = getDeltaMovement();
                    double velocity = Mth.clamp(motion.length() * 3, 0.5f, velocityLimit);
                    var returnMotion = ownerPos.subtract(position()).normalize().scale(velocity);
                    setDeltaMovement(motion.lerp(returnMotion, 0.1f));

                    if (isAlive() && distanceTo(scytheOwner) < 3f) {
                        if (scytheOwner instanceof ServerPlayer player) {
                            ReboundHandler.pickupScythe(this, scythe, player);
                        }
                        SoundHelper.playSound(scytheOwner, SoundRegistry.SCYTHE_CATCH.get(), 0.5f, RandomHelper.randomBetween(level().getRandom(), 0.75f, 1.25f));
                        remove(RemovalReason.DISCARDED);
                    }
                }
            }
        } else {
            if (isMaelstrom() && !isNarrow()) {
                WeaponParticleEffects.spawnMaelstromParticles(this);
            }
            addTrailPoints();
            theFormer.tickTrailPoints();
            theLatter.tickTrailPoints();
            updateRotation();
        }
    }

    public void playSound() {
        if (age % 3 == 0) {
            float pitch = (float) (0.8f + Math.sin(level().getGameTime() * 0.5f) * 0.2f);
            float volumeScalar = Mth.clamp(age / 12f, 0, 1f);
            if (isInWater()) {
                volumeScalar *= 0.2f;
                pitch *= 0.5f;
            }
            SoundHelper.playSound(this, SoundRegistry.SCYTHE_SPINS.get(), 0.6f * volumeScalar, pitch);
            SoundHelper.playSound(this, SoundRegistry.SCYTHE_SWEEP.get(), 0.4f * volumeScalar, pitch);
        }
    }

    public void addTrailPoints() {
        if (isNarrow()) {
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
        for (int i = 0; i < 2; i++) {
            float progress = (i + 1) * 0.5f;
            Vec3 position = getPosition(progress);
            float scalar = (age + progress) / 2f;
            for (int j = 0; j < 2; j++) {
                var trail = j == 0 ? theFormer : theLatter;
                double xOffset = Math.cos(spinOffset + 3.14f * j + scalar) * 1.2f;
                double zOffset = Math.sin(spinOffset + 3.14f * j + scalar) * 1.2f;
                trail.addTrailPoint(position.add(xOffset, 0, zOffset));
            }
        }
    }

    public void flyBack(Entity scytheOwner) {
        var ownerPos = scytheOwner.position().add(0, scytheOwner.getBbHeight()*0.5f, 0);
        var returnMotion = ownerPos.subtract(position()).normalize().scale(0.75f);
        setDeltaMovement(returnMotion);
    }
}