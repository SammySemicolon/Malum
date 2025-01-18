package com.sammy.malum.common.entity.bolt;

import com.sammy.malum.registry.common.*;
import com.sammy.malum.visual_effects.networked.*;
import com.sammy.malum.visual_effects.networked.data.*;
import com.sammy.malum.visual_effects.networked.staff.*;
import net.minecraft.nbt.*;
import net.minecraft.network.syncher.*;
import net.minecraft.server.level.*;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.projectile.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import net.neoforged.api.distmarker.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.systems.rendering.trail.*;

import java.util.Comparator;
import java.util.List;

public abstract class AbstractBoltProjectileEntity extends ThrowableItemProjectile {

    protected static final EntityDataAccessor<Boolean> DATA_FADING_AWAY = SynchedEntityData.defineId(AbstractBoltProjectileEntity.class, EntityDataSerializers.BOOLEAN);
    protected static final EntityDataAccessor<Integer> DATA_SPAWN_DELAY = SynchedEntityData.defineId(AbstractBoltProjectileEntity.class, EntityDataSerializers.INT);

    public TrailPointBuilder trailPointBuilder = TrailPointBuilder.create(8);
    public TrailPointBuilder spinningTrailPointBuilder = TrailPointBuilder.create(16);
    public float spinOffset = (float) (random.nextFloat() * Math.PI * 2);
    protected float magicDamage;
    public boolean isHoming;
    public int age;
    public int spawnDelay;

    public boolean fadingAway;
    public int fadingTimer;

    public AbstractBoltProjectileEntity(EntityType<? extends AbstractBoltProjectileEntity> pEntityType, Level level) {
        super(pEntityType, level);
        noPhysics = false;
    }

    public void setData(Entity owner, float magicDamage, int spawnDelay) {
        setOwner(owner);
        this.magicDamage = magicDamage;
        getEntityData().set(DATA_SPAWN_DELAY, spawnDelay);
        if (!level().isClientSide) {
            if (spawnDelay == 0) {
                playSound(SoundRegistry.STAFF_FIRES.get(), 0.5f, Mth.nextFloat(random, 0.9F, 1.5F));
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    public abstract void spawnParticles();

    public abstract int getMaxAge();

    public abstract ParticleEffectType getImpactParticleEffect();

    public float getOrbitingTrailDistance() {
        return 0.3f;
    }

    public void onDealDamage(LivingEntity target) {

    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(DATA_FADING_AWAY, false);
        builder.define(DATA_SPAWN_DELAY, 0);
        super.defineSynchedData(builder);
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> pKey) {
        if (DATA_FADING_AWAY.equals(pKey)) {
            fadingAway = entityData.get(DATA_FADING_AWAY);
            if (fadingAway) {
                age = getMaxAge() - 10;
                setDeltaMovement(getDeltaMovement().scale(0.02f));
            }
        }
        if (DATA_SPAWN_DELAY.equals(pKey)) {
            spawnDelay = entityData.get(DATA_SPAWN_DELAY);
        }
        super.onSyncedDataUpdated(pKey);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        if (magicDamage != 0) {
            compound.putFloat("magicDamage", magicDamage);
        }
        if (isHoming) {
            compound.putBoolean("isHoming", true);
        }
        if (age != 0) {
            compound.putInt("age", age);
        }
        if (spawnDelay != 0) {
            compound.putInt("spawnDelay", spawnDelay);
        }
        if (fadingAway) {
            compound.putBoolean("fadingAway", true);
            compound.putInt("fadingTimer", fadingTimer);
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        magicDamage = compound.getFloat("magicDamage");
        isHoming = compound.getBoolean("isHoming");
        age = compound.getInt("age");
        getEntityData().set(DATA_SPAWN_DELAY, compound.getInt("spawnDelay"));
        getEntityData().set(DATA_FADING_AWAY, compound.getBoolean("fadingAway"));
    }

    @Override
    protected void onHitBlock(BlockHitResult pResult) {
        if (fadingAway || spawnDelay > 0) {
            return;
        }
        if (level() instanceof ServerLevel serverLevel) {
            getImpactParticleEffect().createPositionedEffect(serverLevel, new PositionEffectData(position().add(getDeltaMovement().scale(0.25f))), new ColorEffectData(SpiritTypeRegistry.WICKED_SPIRIT), HexBoltImpactParticleEffect.createData(getDeltaMovement().reverse().normalize()));
            playSound(SoundRegistry.STAFF_STRIKES.get(), 0.5f, Mth.nextFloat(random, 0.9F, 1.5F));
            getEntityData().set(DATA_FADING_AWAY, true);
            Vec3 direction = pResult.getLocation().subtract(position());
            Vec3 offset = direction.normalize().scale(0.5f);
            setPosRaw(getX() - offset.x, getY() - offset.y, getZ() - offset.z);
        }
        super.onHitBlock(pResult);
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        if (level() instanceof ServerLevel serverLevel) {
            if (fadingAway || spawnDelay > 0) {
                return;
            }
            if (getOwner() instanceof LivingEntity staffOwner) {
                Entity target = result.getEntity();
                target.invulnerableTime = 0;
                DamageSource source = DamageTypeHelper.create(level(), DamageTypeRegistry.VOODOO, this, staffOwner);
                boolean success = target.hurt(source, magicDamage);
                if (success && target instanceof LivingEntity livingentity) {
                    onDealDamage(livingentity);
                    getImpactParticleEffect().createPositionedEffect(serverLevel, new PositionEffectData(position().add(getDeltaMovement().scale(0.5f))), new ColorEffectData(SpiritTypeRegistry.WICKED_SPIRIT), HexBoltImpactParticleEffect.createData(getDeltaMovement().reverse().normalize()));
                    playSound(SoundRegistry.STAFF_STRIKES.get(), 0.75f, Mth.nextFloat(random, 1f, 1.4f));
                    setDeltaMovement(getDeltaMovement().scale(0.05f));
                    getEntityData().set(DATA_FADING_AWAY, true);
                }
            }
        }
        super.onHitEntity(result);
    }

    @Override
    public void tick() {
        if (spawnDelay > 0) {
            spawnDelay--;
            if (spawnDelay == 0 && !level().isClientSide) {
                spawnDelay = -1;
                playSound(SoundRegistry.STAFF_FIRES.get(), 1f, Mth.nextFloat(random, 0.9F, 1.5F));
            }
            return;
        }
        super.tick();
        age++;
        if (fadingAway) {
            fadingTimer++;
        }
        else {
            var motion = getDeltaMovement();
            float scalar = 0.96f;
            setDeltaMovement(motion.x * scalar, (motion.y-0.02f)* scalar, motion.z * scalar);
        }
        if (isHoming) {
            homeIn();
        }
        if (level().isClientSide) {
            float offsetScale = fadingAway ? 0f : getOrbitingTrailDistance();
            for (int i = 0; i < 2; i++) {
                float progress = (i + 1) * 0.5f;
                Vec3 position = getPosition(progress);
                float scalar = (age + progress) / 2f;
                double xOffset = Math.cos(spinOffset + scalar) * offsetScale;
                double zOffset = Math.sin(spinOffset + scalar) * offsetScale;
                trailPointBuilder.addTrailPoint(position);
                spinningTrailPointBuilder.addTrailPoint(position.add(xOffset, 0, zOffset));
            }
            trailPointBuilder.tickTrailPoints();
            spinningTrailPointBuilder.tickTrailPoints();
            if (!fadingAway) {
                spawnParticles();
            }
        }
        else if (age >= getMaxAge()) {
            if (fadingAway) {
                discard();
            }
            else {
                getEntityData().set(DATA_FADING_AWAY, true);
            }
        }
    }

    public void homeIn() {
        Vec3 motion = getDeltaMovement();
        Entity owner = getOwner();
        if (spawnDelay > 0 || owner == null || fadingAway) {
            return;
        }
        List<LivingEntity> entities = level().getEntitiesOfClass(LivingEntity.class, getBoundingBox().inflate(25), target -> target != owner && target.isAlive() && !target.isAlliedTo(owner));
        if (!entities.isEmpty()) {
            LivingEntity nearest = entities.stream().min(Comparator.comparingDouble((e) -> e.distanceToSqr(this))).get();
            Vec3 nearestPosition = nearest.position().add(0, nearest.getBbHeight() / 2, 0);
            Vec3 diff = nearestPosition.subtract(position());
            double speed = motion.length();
            Vec3 nextPosition = position().add(getDeltaMovement());
            if (nearest.distanceToSqr(nextPosition) > nearest.distanceToSqr(position())) {
                return;
            }
            Vec3 newMotion = diff.normalize().scale(speed);
            final double dot = motion.normalize().dot(diff.normalize());
            if (dot < 0.8f) {
                return;
            }
            if (newMotion.length() == 0) {
                newMotion = newMotion.add(0.01, 0, 0);
            }
            float angleScalar = (float) ((dot - 0.6f) * 5f);
            float factor = 0.15f * angleScalar;
            final double x = Mth.lerp(factor, motion.x, newMotion.x);
            final double y = Mth.lerp(factor, motion.y, newMotion.y);
            final double z = Mth.lerp(factor, motion.z, newMotion.z);
            setDeltaMovement(new Vec3(x, y, z));
        }
    }

    @Override
    protected boolean canHitEntity(Entity pTarget) {
        if (pTarget.equals(getOwner())) {
            return false;
        }
        if (pTarget instanceof AbstractBoltProjectileEntity) {
            return false;
        }
        return super.canHitEntity(pTarget);
    }

    @Override
    public SoundSource getSoundSource() {
        return getOwner() != null ? getOwner().getSoundSource() : SoundSource.PLAYERS;
    }

    @Override
    public void lerpMotion(double pX, double pY, double pZ) {
        this.setDeltaMovement(pX, pY, pZ);
        if (this.xRotO == 0.0F && this.yRotO == 0.0F) {
            double d0 = Math.sqrt(pX * pX + pZ * pZ);
            this.setXRot((float)(Mth.atan2(pY, d0) * (double)(180F / (float)Math.PI)));
            this.setYRot((float)(Mth.atan2(pX, pZ) * (double)(180F / (float)Math.PI)));
            this.xRotO = this.getXRot();
            this.yRotO = this.getYRot();
            this.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), this.getXRot());
        }

    }

    public void shootFromRotation(Entity shooter, float rotationPitch, float rotationYaw, float pitchOffset, float velocity, float innacuracy) {
        float f = -Mth.sin(rotationYaw * ((float) Math.PI / 180F)) * Mth.cos(rotationPitch * ((float) Math.PI / 180F));
        float f1 = -Mth.sin((rotationPitch + pitchOffset) * ((float) Math.PI / 180F));
        float f2 = Mth.cos(rotationYaw * ((float) Math.PI / 180F)) * Mth.cos(rotationPitch * ((float) Math.PI / 180F));
        this.shoot(f, f1, f2, velocity, innacuracy);
    }

    public float getVisualEffectScalar() {
        float effectScalar = 1;
        if (age < 8) {
            effectScalar = age / 8f;
        }
        else if (fadingAway) {
            effectScalar = effectScalar / ((fadingTimer+2) / 2f);
        }
        return effectScalar;
    }

    @Override
    public boolean isInWater() {
        return false;
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    @Override
    public float getPickRadius() {
        return 4f;
    }

    @Override
    public boolean fireImmune() {
        return true;
    }

    @Override
    public boolean ignoreExplosion(Explosion explosion) {
        return true;
    }
}