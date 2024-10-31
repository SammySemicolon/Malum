package com.sammy.malum.common.entity;

import net.minecraft.core.*;
import net.minecraft.nbt.*;
import net.minecraft.network.protocol.*;
import net.minecraft.network.protocol.game.*;
import net.minecraft.server.level.*;
import net.minecraft.util.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.entity.projectile.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.phys.*;
import net.neoforged.neoforge.event.*;
import team.lodestar.lodestone.systems.easing.*;
import team.lodestar.lodestone.systems.rendering.trail.*;

import java.util.*;

public abstract class FloatingEntity extends Entity {

    public final TrailPointBuilder trail = TrailPointBuilder.create(10);

    public int age;
    public int maxAge;
    public float windUp;
    public float hoverOffset;

    public UUID ownerUUID;
    public LivingEntity owner;

    public FloatingEntity(EntityType<? extends FloatingEntity> type, Level level) {
        super(type, level);
        noPhysics = false;
        this.hoverOffset = (float) (Math.random() * Math.PI * 2.0D);
    }


    public void spawnParticles(double x, double y, double z) {

    }

    public Vec3 getDestination() {
        if (owner != null) {
            return owner.position().add(0, owner.getBbHeight() / 3, 0);
        }
        return null;
    }

    public abstract void collect();

    public abstract float getMotionCoefficient();

    public float getFriction() {
        return 0.95f;
    }
    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        compound.putInt("age", age);
        compound.putInt("maxAge", maxAge);
        compound.putFloat("windUp", windUp);
        if (ownerUUID != null) {
            compound.putUUID("ownerUUID", ownerUUID);
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        age = compound.getInt("age");
        maxAge = compound.getInt("maxAge");
        windUp = compound.getFloat("windUp");
        if (compound.contains("ownerUUID")) {
            setOwner(compound.getUUID("ownerUUID"));
        }
    }

    @Override
    public void tick() {
        super.tick();
        baseTick();
        hoverOffset = getHoverStart(0);
        age++;
        if (age > maxAge) {
            discard();
        }
        float friction = getFriction();
        setDeltaMovement(getDeltaMovement().multiply(friction, friction, friction));

        if (isAlive()) {
            if (owner == null || !owner.isAlive()) {
                if (level().getGameTime() % 40L == 0) {
                    Player playerEntity = level().getNearestPlayer(this, 50);
                    if (playerEntity != null) {
                        setOwner(playerEntity.getUUID());
                    }
                }
            }

            final Vec3 destination = getDestination();
            if (destination != null) {
                if (windUp < 1) {
                    windUp += 0.02f;
                }
                float velocity = Mth.clamp(windUp-0.25f, 0, 0.75f) * 5f;
                Vec3 desiredMotion = destination.subtract(position()).normalize().multiply(velocity, velocity, velocity);
                float easing = getMotionCoefficient();
                float xMotion = (float) Mth.lerp(easing, getDeltaMovement().x, desiredMotion.x);
                float yMotion = (float) Mth.lerp(easing, getDeltaMovement().y, desiredMotion.y);
                float zMotion = (float) Mth.lerp(easing, getDeltaMovement().z, desiredMotion.z);
                Vec3 resultingMotion = new Vec3(xMotion, yMotion, zMotion);
                setDeltaMovement(resultingMotion);

                float distance = (float) distanceToSqr(destination);
                if (distance < 0.4f) {
                    collect();
                    remove(RemovalReason.DISCARDED);
                    return;
                }
            }
        }

        this.checkInsideBlocks();
        Vec3 vec3 = this.getDeltaMovement();
        double d0 = this.getX() + vec3.x;
        double d1 = this.getY() + vec3.y;
        double d2 = this.getZ() + vec3.z;
        this.updateRotation();
        this.applyGravity();
        this.setPos(d0, d1, d2);

        if (level().isClientSide) {
            spawnParticles(xOld, yOld + getYOffset(0), zOld);
            for (int i = 0; i < 2; i++) {
                float progress = (i+1) * 0.5f;
                Vec3 position = getPosition(progress).add(0, getYOffset(progress), 0);
                trail.addTrailPoint(position);
            }
            trail.tickTrailPoints();
        }

    }

    @Override
    public void lerpMotion(double pX, double pY, double pZ) {
        this.setDeltaMovement(pX, pY, pZ);
        this.setOldPosAndRot();
    }

    protected void updateRotation() {
        Vec3 vec3 = this.getDeltaMovement();
        double d0 = vec3.horizontalDistance();
        this.setXRot(lerpRotation(this.xRotO, (float)(Mth.atan2(vec3.y, d0) * 180.0 / 3.1415927410125732)));
        this.setYRot(lerpRotation(this.yRotO, (float)(Mth.atan2(vec3.x, vec3.z) * 180.0 / 3.1415927410125732)));
    }

    protected static float lerpRotation(float p_37274_, float p_37275_) {
        while (p_37275_ - p_37274_ < -180.0F) {
            p_37274_ -= 360.0F;
        }

        while (p_37275_ - p_37274_ >= 180.0F) {
            p_37274_ += 360.0F;
        }

        return Mth.lerp(0.2F, p_37274_, p_37275_);
    }

    public float getYOffset(float partialTicks) {
        return Mth.sin(((float) age + partialTicks) / 10.0F + getHoverStart(partialTicks)) * 0.1F + 0.35F;
    }

    public float getRotation(float partialTicks) {
        return ((float) age + partialTicks) / 20.0F + getHoverStart(partialTicks) / 2f;
    }

    public float getHoverStart(float partialTicks) {
        return hoverOffset + (1 - Easing.SINE_OUT.ease(Math.min(1, (age + partialTicks) / 60f), 0, 1, 1)) * 0.35f;
    }

    public void setOwner(UUID ownerUUID) {
        this.ownerUUID = ownerUUID;
        if (level() instanceof ServerLevel serverLevel) {
            owner = (LivingEntity) serverLevel.getEntity(ownerUUID);
        }
    }
    @Override
    public boolean isNoGravity() {
        return true;
    }

    @Override
    public boolean fireImmune() {
        return true;
    }
}
