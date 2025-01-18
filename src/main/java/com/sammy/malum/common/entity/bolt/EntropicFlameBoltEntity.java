package com.sammy.malum.common.entity.bolt;

import com.sammy.malum.common.entity.nitrate.EthericNitrateEntity;
import com.sammy.malum.common.item.curiosities.weapons.staff.*;
import com.sammy.malum.registry.client.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.entity.*;
import com.sammy.malum.registry.common.item.*;
import com.sammy.malum.visual_effects.*;
import com.sammy.malum.visual_effects.networked.*;
import net.minecraft.sounds.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.projectile.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import net.neoforged.api.distmarker.*;
import team.lodestar.lodestone.handlers.*;
import team.lodestar.lodestone.systems.easing.*;
import team.lodestar.lodestone.systems.particle.*;
import team.lodestar.lodestone.systems.particle.builder.*;
import team.lodestar.lodestone.systems.particle.data.*;
import team.lodestar.lodestone.systems.particle.data.color.ColorParticleData;
import team.lodestar.lodestone.systems.particle.render_types.*;
import team.lodestar.lodestone.systems.particle.world.*;
import team.lodestar.lodestone.systems.particle.world.behaviors.components.*;
import team.lodestar.lodestone.systems.rendering.trail.TrailPointBuilder;

import java.util.function.*;

public class EntropicFlameBoltEntity extends AbstractBoltProjectileEntity {

    public TrailPointBuilder secondarySpinningTrailPointBuilder;

    public EntropicFlameBoltEntity(Level level) {
        super(EntityRegistry.ENTROPIC_FLAME_BOLT.get(), level);
        noPhysics = false;
        trailPointBuilder = TrailPointBuilder.create(32);
        secondarySpinningTrailPointBuilder = TrailPointBuilder.create(24);
        spinningTrailPointBuilder = TrailPointBuilder.create(24);
    }

    public EntropicFlameBoltEntity(Level level, double pX, double pY, double pZ) {
        this(level);
        setPos(pX, pY, pZ);
        noPhysics = false;
    }

    @Override
    public float getOrbitingTrailDistance() {
        return 0.2f + random.nextFloat() * 0.4f;
    }

    @Override
    protected void onHitBlock(BlockHitResult pResult) {
        final AABB boundingBox = getBoundingBox();
        setBoundingBox(boundingBox.deflate(0.25f));
        HitResult hitresult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);
        if (hitresult instanceof BlockHitResult blockHitResult) {
            super.onHitBlock(blockHitResult);
        }
        setBoundingBox(boundingBox);
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        if (fadingAway || spawnDelay > 0) {
            return;
        }
        if (result.getEntity() instanceof LivingEntity livingentity) {
            livingentity.igniteForSeconds(4);
        }
        super.onHitEntity(result);
    }

    @Override
    public void tick() {
        super.tick();
        if (level().isClientSide) {
            if (spawnDelay > 0) {
                return;
            }
            float offsetScale = fadingAway ? 0f : getOrbitingTrailDistance();
            for (int i = 0; i < 2; i++) {
                float progress = (i + 1) * 0.5f;
                Vec3 position = getPosition(progress);
                float scalar = (age + progress) / 2f;
                double xOffset = Math.cos(spinOffset + 3.14f + scalar) * offsetScale;
                double zOffset = Math.sin(spinOffset + 3.14f + scalar) * offsetScale;
                secondarySpinningTrailPointBuilder.addTrailPoint(position.add(xOffset, 0, zOffset));
            }
            secondarySpinningTrailPointBuilder.tickTrailPoints();
        }
    }

    //    @Override
//    public void tick() {
//        Vec3 motion = getDeltaMovement();
//        super.tick();
//        Entity owner = getOwner();
//        if (spawnDelay > 0 || owner == null || fadingAway) {
//            return;
//        }
//        List<LivingEntity> entities = level().getEntitiesOfClass(LivingEntity.class, getBoundingBox().inflate(25), target -> target != owner && target.isAlive() && !target.isAlliedTo(owner));
//        if (!entities.isEmpty()) {
//            LivingEntity nearest = entities.stream().min(Comparator.comparingDouble((e) -> e.distanceToSqr(this))).get();
//            Vec3 nearestPosition = nearest.position().add(0, nearest.getBbHeight() / 2, 0);
//            Vec3 diff = nearestPosition.subtract(position());
//            double speed = motion.length();
//            Vec3 nextPosition = position().add(getDeltaMovement());
//            if (nearest.distanceToSqr(nextPosition) > nearest.distanceToSqr(position())) {
//                return;
//            }
//            Vec3 newMotion = diff.normalize().scale(speed);
//            final double dot = motion.normalize().dot(diff.normalize());
//            if (dot < 0.8f) {
//                return;
//            }
//            if (newMotion.length() == 0) {
//                newMotion = newMotion.add(0.01, 0, 0);
//            }
//            float angleScalar = (float) ((dot - 0.8f) * 5f);
//            float factor = 0.15f * angleScalar;
//            final double x = Mth.lerp(factor, motion.x, newMotion.x);
//            final double y = Mth.lerp(factor, motion.y, newMotion.y);
//            final double z = Mth.lerp(factor, motion.z, newMotion.z);
//            setDeltaMovement(new Vec3(x, y, z));
//        }
//    }

    @Override
    public void playSound(SoundEvent pSound, float pVolume, float pPitch) {
        super.playSound(pSound, pVolume/2f, pPitch+0.4f);
        super.playSound(SoundRegistry.WORLDSOUL_MOTIF_LIGHT_IMPACT.get(), pVolume - 0.2f, pPitch + 0.3f);
    }

    @Override
    public int getMaxAge() {
        return 60;
    }

    @Override
    public ParticleEffectType getImpactParticleEffect() {
        return ParticleEffectTypeRegistry.ENTROPIC_BOLT_IMPACT;
    }

    @Override
    protected Item getDefaultItem() {
        return ItemRegistry.UNWINDING_CHAOS.get();
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void spawnParticles() {
        Level level = level();
        Vec3 position = position();
        float scalar = getVisualEffectScalar();
        Vec3 norm = getDeltaMovement().normalize().scale(0.05f);
        var lightSpecs = SpiritLightSpecs.spiritLightSpecs(level, position, UnwindingChaosStaffItem.AURIC_COLOR_DATA);
        lightSpecs.getBuilder()
                .modifyData(AbstractParticleBuilder::getScaleData, d -> d.multiplyValue(3 * scalar))
                .setRenderTarget(RenderHandler.LATE_DELAYED_RENDER)
                .multiplyLifetime(2.5f)
                .setRenderType(LodestoneWorldParticleRenderType.LUMITRANSPARENT)
                .enableForcedSpawn()
                .setMotion(norm);
        lightSpecs.getBloomBuilder()
                .setRenderTarget(RenderHandler.LATE_DELAYED_RENDER)
                .multiplyLifetime(1.5f)
                .setRenderType(LodestoneWorldParticleRenderType.LUMITRANSPARENT)
                .setMotion(norm);
        lightSpecs.spawnParticles();

        final Consumer<LodestoneWorldParticle> behavior = p -> p.setParticleSpeed(p.getParticleSpeed().scale(0.98f));
        final float min = Math.min(1f, 2 * scalar);
        WorldParticleBuilder.create(ParticleRegistry.GIANT_ARROW, new SparkBehaviorComponent(GenericParticleData.create(1.8f * scalar, 2.4f * scalar, 0.1f * scalar).setEasing(Easing.CUBIC_IN).build()))
                .setTransparencyData(GenericParticleData.create(0.5f * min, 0f).setEasing(Easing.SINE_IN_OUT, Easing.SINE_IN).build())
                .setScaleData(GenericParticleData.create(1.2f * scalar, 0.1f * scalar).setEasing(Easing.SINE_IN_OUT).build())
                .setSpritePicker(SimpleParticleOptions.ParticleSpritePicker.WITH_AGE)
                .setRenderTarget(RenderHandler.LATE_DELAYED_RENDER)
                .setColorData(UnwindingChaosStaffItem.AURIC_COLOR_DATA)
                .setLifetime(Math.min(2 + age, 16))
                .addTickActor(behavior)
                .enableForcedSpawn()
                .setMotion(norm)
                .enableNoClip()
                .spawn(level, position.x, position.y, position.z);
        WorldParticleBuilder.create(ParticleRegistry.GIANT_ARROW, new SparkBehaviorComponent(GenericParticleData.create(2f * scalar, 2.8f * scalar, 0.3f * scalar).setEasing(Easing.CUBIC_IN).build()))
                .setTransparencyData(GenericParticleData.create(0.6f * min, 0.3f * min, 0f).setEasing(Easing.SINE_IN_OUT, Easing.SINE_IN).build())
                .setColorData(ColorParticleData.create(EthericNitrateEntity.AURIC_RED, EthericNitrateEntity.AURIC_RED).setCoefficient(3f).build())
                .setScaleData(GenericParticleData.create(1.5f * scalar, 0.3f * scalar).setEasing(Easing.SINE_IN_OUT).build())
                .setSpritePicker(SimpleParticleOptions.ParticleSpritePicker.WITH_AGE)
                .setRenderType(LodestoneWorldParticleRenderType.LUMITRANSPARENT)
                .setLifetime(Math.min(3 + age, 24))
                .addTickActor(behavior)
                .enableForcedSpawn()
                .setMotion(norm.scale(0.5f))
                .enableNoClip()
                .spawn(level, position.x, position.y, position.z);
    }
}