package com.sammy.malum.common.entity.bolt;

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
import team.lodestar.lodestone.handlers.*;
import team.lodestar.lodestone.systems.easing.*;
import team.lodestar.lodestone.systems.particle.*;
import team.lodestar.lodestone.systems.particle.builder.*;
import team.lodestar.lodestone.systems.particle.data.*;
import team.lodestar.lodestone.systems.particle.render_types.*;
import team.lodestar.lodestone.systems.particle.world.*;
import team.lodestar.lodestone.systems.particle.world.behaviors.components.*;

import java.util.function.*;

public class AuricFlameBoltEntity extends AbstractBoltProjectileEntity {

    public AuricFlameBoltEntity(Level level) {
        super(EntityRegistry.AURIC_FLAME_BOLT.get(), level);
        noPhysics = false;
    }

    public AuricFlameBoltEntity(Level level, double pX, double pY, double pZ) {
        this(level);
        setPos(pX, pY, pZ);
        noPhysics = false;
    }

    @Override
    protected void onHitBlock(BlockHitResult pResult) {
        final AABB boundingBox = getBoundingBox();
        setBoundingBox(boundingBox.deflate(0.5f));
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
        super.playSound(pSound, pVolume, pPitch);
        super.playSound(SoundRegistry.AURIC_FLAME_MOTIF.get(), pVolume+0.1f, pPitch+0.6f);
    }

    @Override
    public int getMaxAge() {
        return 80;
    }

    @Override
    public ParticleEffectType getImpactParticleEffect() {
        return ParticleEffectTypeRegistry.AURIC_BOLT_IMPACT;
    }

    @Override
    protected Item getDefaultItem() {
        return ItemRegistry.STAFF_OF_THE_AURIC_FLAME.get();
    }

    @Override
    public void spawnParticles() {
        Level level = level();
        Vec3 position = position();
        float scalar = getVisualEffectScalar();
        Vec3 norm = getDeltaMovement().normalize().scale(0.05f);
        var lightSpecs = SpiritLightSpecs.spiritLightSpecs(level, position, AuricFlameStaffItem.AURIC_COLOR_DATA);
        lightSpecs.getBuilder()
                .setRenderTarget(RenderHandler.LATE_DELAYED_RENDER)
                .multiplyLifetime(1.25f)
                .setMotion(norm);
        lightSpecs.getBloomBuilder()
                .setRenderTarget(RenderHandler.LATE_DELAYED_RENDER)
                .multiplyLifetime(1.25f)
                .setMotion(norm);
        lightSpecs.spawnParticles();
        final Consumer<LodestoneWorldParticle> behavior = p -> p.setParticleSpeed(p.getParticleSpeed().scale(0.98f));
        final float min = Math.min(1f, 2 * scalar);
        WorldParticleBuilder.create(ParticleRegistry.BOLT, new SparkBehaviorComponent(GenericParticleData.create(2f * min, 0.2f * min).setEasing(Easing.CUBIC_IN).build()))
                .setRenderTarget(RenderHandler.LATE_DELAYED_RENDER)
                .setTransparencyData(GenericParticleData.create(0.5f * scalar, 0.2f * scalar, 0f).setEasing(Easing.SINE_IN_OUT, Easing.SINE_IN).build())
                .setScaleData(GenericParticleData.create(0.6f * scalar, 0.1f * min).setEasing(Easing.QUAD_OUT).build())
                .setColorData(AuricFlameStaffItem.AURIC_COLOR_DATA)
                .setLifetime(Math.min(6 + age * 3, 15))
                .setMotion(norm)
                .enableNoClip()
                .enableForcedSpawn()
                .setSpritePicker(SimpleParticleOptions.ParticleSpritePicker.FIRST_INDEX)
                .addTickActor(behavior)
                .spawn(level, position.x, position.y, position.z)
                .setRenderType(LodestoneWorldParticleRenderType.LUMITRANSPARENT)
                .spawn(level, position.x, position.y, position.z);
    }
}