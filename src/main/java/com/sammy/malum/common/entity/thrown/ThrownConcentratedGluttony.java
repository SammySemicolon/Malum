package com.sammy.malum.common.entity.thrown;

import com.sammy.malum.common.item.food.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.entity.*;
import com.sammy.malum.registry.common.item.*;
import com.sammy.malum.visual_effects.networked.data.*;
import net.minecraft.nbt.*;
import net.minecraft.network.syncher.*;
import net.minecraft.server.level.*;
import net.minecraft.sounds.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.projectile.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import team.lodestar.lodestone.systems.rendering.trail.*;

import javax.annotation.*;
import java.util.*;

public class ThrownConcentratedGluttony extends ThrowableItemProjectile {

    protected static final EntityDataAccessor<Boolean> DATA_FADING_AWAY = SynchedEntityData.defineId(ThrownConcentratedGluttony.class, EntityDataSerializers.BOOLEAN);

    public final List<TrailPointBuilder> trails = new ArrayList<>(List.of(TrailPointBuilder.create(4), TrailPointBuilder.create(8), TrailPointBuilder.create(12)));
    public float spinOffset = (float) (random.nextFloat() * Math.PI * 2);

    public int age;

    public int fadingTimer;

    public ThrownConcentratedGluttony(Level pLevel) {
        super(EntityRegistry.THROWN_GLUTTONY.get(), pLevel);
    }

    public ThrownConcentratedGluttony(Level pLevel, LivingEntity pShooter) {
        super(EntityRegistry.THROWN_GLUTTONY.get(), pShooter, pLevel);
    }

    public ThrownConcentratedGluttony(Level pLevel, double pX, double pY, double pZ) {
        super(EntityRegistry.THROWN_GLUTTONY.get(), pX, pY, pZ, pLevel);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(DATA_FADING_AWAY, false);
        super.defineSynchedData(builder);
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> pKey) {
        if (DATA_FADING_AWAY.equals(pKey)) {
            if (isFadingAway()) {
                setDeltaMovement(getDeltaMovement().scale(0.02f));
            }
        }
        super.onSyncedDataUpdated(pKey);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        if (age != 0) {
            compound.putInt("age", age);
        }
        if (entityData.get(DATA_FADING_AWAY)) {
            compound.putBoolean("fadingAway", true);
            compound.putInt("fadingTimer", fadingTimer);
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        age = compound.getInt("age");
        getEntityData().set(DATA_FADING_AWAY, compound.getBoolean("fadingAway"));
    }

    @Override
    protected Item getDefaultItem() {
        return ItemRegistry.SPLASH_OF_GLUTTONY.get();
    }

    @Override
    public void tick() {
        super.tick();
        boolean fadingAway = isFadingAway();
        if (level().isClientSide) {
            for (int i = 0; i < 3; i++) {
                var trailPointBuilder = trails.get(i);
                float offsetScale = i*0.1f;
                if (fadingAway) {
                    offsetScale *= 1+fadingTimer/4f;
                }
                for (int j = 0; j < 2; j++) {
                    float progress = (j + 1) * 0.5f;
                    Vec3 position = getPosition(progress);
                    float scalar = (i*2.35f + age + progress) / 2f;
                    double xOffset = Math.cos(spinOffset + scalar) * offsetScale;
                    double zOffset = Math.sin(spinOffset + scalar) * offsetScale;
                    trailPointBuilder.addTrailPoint(position.add(xOffset, 0, zOffset));
                }
                trailPointBuilder.tickTrailPoints();
            }
        }

        age++;
        if (fadingAway) {
            fadingTimer++;
            if (fadingTimer >= 40) {
                discard();
            }
        }
    }

    @Override
    protected void onHit(HitResult pResult) {
        if (!(level() instanceof ServerLevel level)) {
            return;
        }
        if (isFadingAway()) {
            return;
        }
        var impactedEntity = pResult instanceof EntityHitResult entityHitResult ? entityHitResult.getEntity() : null;
        getEntityData().set(DATA_FADING_AWAY, true);
        setDeltaMovement(getDeltaMovement().scale(0.05f));
        level.levelEvent(2002, blockPosition(), MobEffectRegistry.GLUTTONY.get().getColor());
        level.playSound(null, blockPosition(), SoundRegistry.CONCENTRATED_GLUTTONY_DRINK.get(), SoundSource.PLAYERS, 0.5f, 1.25f + level.random.nextFloat() * 0.25f);
        ParticleEffectTypeRegistry.THROWN_GLUTTONY_IMPACT.createPositionedEffect(level, new PositionEffectData(position()));
        applyGluttony(level, impactedEntity);
        super.onHit(pResult);
    }

    private void applyGluttony(ServerLevel level, @Nullable Entity impactedEntity) {
        var targets = level.getEntitiesOfClass(LivingEntity.class, getBoundingBox().inflate(3f, 2f, 3f));
        if (!targets.isEmpty()) {
            Entity owner = getEffectSource();
            MobEffectInstance gluttony = ConcentratedGluttonyItem.createGluttonyEffect(owner);
            for (LivingEntity target : targets) {
                if (target.isAffectedByPotions()) {
                    double distance = this.distanceToSqr(target);
                    if (distance < 6.0D) {
                        float durationScalar = target.equals(impactedEntity) ? 1f : (float) (1f - Math.sqrt(distance) / 4f);
                        int newDuration = gluttony.mapDuration((d) -> (int) (durationScalar * (double) d + 0.5D));
                        if (newDuration > 20) {
                            MobEffectInstance appliedGluttony = ConcentratedGluttonyItem.createGluttonyEffect(owner, durationScalar);
                            target.addEffect(appliedGluttony, owner);
                            ConcentratedGluttonyItem.createGluttonyVFX(target, 0.25f);
                        }
                    }
                }
            }
        }
    }

    public boolean isFadingAway() {
        return entityData.get(DATA_FADING_AWAY);
    }

    public float getVisualEffectScalar() {
        float effectScalar = 1;
        if (age < 5) {
            effectScalar = age / 5f;
        }
        else if (isFadingAway()) {
            effectScalar = effectScalar * (40 - fadingTimer) / 40f;
        }
        return effectScalar;
    }
}
