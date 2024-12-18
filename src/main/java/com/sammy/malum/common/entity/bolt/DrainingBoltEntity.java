package com.sammy.malum.common.entity.bolt;

import com.sammy.malum.common.item.curiosities.weapons.staff.*;
import com.sammy.malum.registry.client.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.entity.*;
import com.sammy.malum.registry.common.item.*;
import com.sammy.malum.visual_effects.*;
import com.sammy.malum.visual_effects.networked.*;
import net.minecraft.sounds.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;

import team.lodestar.lodestone.handlers.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.systems.easing.*;
import team.lodestar.lodestone.systems.particle.builder.*;
import team.lodestar.lodestone.systems.particle.data.*;
import team.lodestar.lodestone.systems.particle.data.spin.*;
import team.lodestar.lodestone.systems.particle.render_types.*;
import team.lodestar.lodestone.systems.particle.world.*;
import team.lodestar.lodestone.systems.particle.world.behaviors.components.*;

import java.util.function.*;

public class DrainingBoltEntity extends AbstractBoltProjectileEntity {

    public DrainingBoltEntity(Level level) {
        super(EntityRegistry.DRAINING_BOLT.get(), level);
        noPhysics = false;
    }

    public DrainingBoltEntity(Level level, double pX, double pY, double pZ) {
        this(level);
        setPos(pX, pY, pZ);
        noPhysics = false;
    }

    @Override
    public void onDealDamage(LivingEntity target) {
        var effect = target.getEffect(MobEffectRegistry.SILENCED);
        if (effect == null) {
            target.addEffect(new MobEffectInstance(MobEffectRegistry.SILENCED, 300, 0, true, true, true));
        } else {
            EntityHelper.amplifyEffect(effect, target, 1, 9);
            EntityHelper.extendEffect(effect, target, 30, 600);
        }
    }

    @Override
    public void playSound(SoundEvent pSound, float pVolume, float pPitch) {
        super.playSound(pSound, pVolume, pPitch-0.2f);
        super.playSound(SoundRegistry.DRAINING_MOTIF.get(), pVolume, pPitch-0.1f);
    }

    @Override
    public int getMaxAge() {
        return 30;
    }

    @Override
    public float getOrbitingTrailDistance() {
        return 0.5f;
    }

    @Override
    public ParticleEffectType getImpactParticleEffect() {
        return ParticleEffectTypeRegistry.DRAINING_BOLT_IMPACT;
    }

    @Override
    protected Item getDefaultItem() {
        return ItemRegistry.EROSION_SCEPTER.get();
    }

    
    @Override
    public void spawnParticles() {
        Level level = level();
        Vec3 position = position();
        float scalar = getVisualEffectScalar();
        Vec3 norm = getDeltaMovement().normalize().scale(0.05f);
        var lightSpecs = SpiritLightSpecs.spiritLightSpecs(level, position, ErosionScepterItem.MALIGNANT_COLOR_DATA);
        lightSpecs.getBuilder()
                .setRenderTarget(RenderHandler.LATE_DELAYED_RENDER)
                .setRenderType(LodestoneWorldParticleRenderType.LUMITRANSPARENT)
                .multiplyLifetime(1.5f)
                .setMotion(norm);
        lightSpecs.getBloomBuilder()
                .setRenderTarget(RenderHandler.LATE_DELAYED_RENDER)
                .setRenderType(LodestoneWorldParticleRenderType.LUMITRANSPARENT)
                .multiplyLifetime(1.5f)
                .setMotion(norm);
        lightSpecs.spawnParticles();
        final SpinParticleData spinData = SpinParticleData.createRandomDirection(random, RandomHelper.randomBetween(random, 0.25f, 0.5f)).randomSpinOffset(random).build();
        final Consumer<LodestoneWorldParticle> behavior = p -> p.setParticleSpeed(p.getParticleSpeed().scale(0.95f));
        WorldParticleBuilder.create(ParticleRegistry.SAW, new DirectionalBehaviorComponent(getDeltaMovement().normalize()))
                .setRenderTarget(RenderHandler.LATE_DELAYED_RENDER)
                .setTransparencyData(GenericParticleData.create(0.4f * scalar, 0.2f * scalar, 0f).setEasing(Easing.SINE_IN_OUT, Easing.SINE_IN).build())
                .setSpinData(spinData)
                .setScaleData(GenericParticleData.create(0.3f * scalar, 0).setEasing(Easing.SINE_IN_OUT).build())
                .setColorData(ErosionScepterItem.MALIGNANT_COLOR_DATA)
                .setLifetime(Math.min(6 + age * 3, 24))
                .enableNoClip()
                .enableForcedSpawn()
                .addTickActor(behavior)
                .spawn(level, position.x, position.y, position.z)
                .setRenderType(LodestoneWorldParticleRenderType.LUMITRANSPARENT)
                .setTransparencyData(GenericParticleData.create(0.9f * scalar, 0.4f * scalar, 0f).setEasing(Easing.SINE_IN_OUT, Easing.SINE_IN).build())
                .spawn(level, position.x, position.y, position.z);
    }
}