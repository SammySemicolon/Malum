package com.sammy.malum.common.entity.nitrate;

import com.sammy.malum.registry.client.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.entity.*;
import com.sammy.malum.visual_effects.*;
import com.sammy.malum.visual_effects.networked.*;
import com.sammy.malum.visual_effects.networked.data.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import net.neoforged.api.distmarker.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.systems.easing.*;
import team.lodestar.lodestone.systems.particle.*;
import team.lodestar.lodestone.systems.particle.builder.*;
import team.lodestar.lodestone.systems.particle.data.*;
import team.lodestar.lodestone.systems.particle.data.color.*;
import team.lodestar.lodestone.systems.particle.data.spin.*;
import team.lodestar.lodestone.systems.particle.render_types.*;
import team.lodestar.lodestone.systems.particle.world.*;

import java.awt.*;
import java.util.List;
import java.util.function.*;

public class EthericNitrateEntity extends AbstractNitrateEntity {

    public static final Color AURIC_YELLOW = new Color(239, 215, 75);
    public static final Color AURIC_PURPLE = new Color(236, 54, 163);
    public static final Color AURIC_RED = new Color(170, 31, 73);
    public static final ColorParticleData AURIC_COLOR_DATA = ColorParticleData.create(AURIC_YELLOW, AURIC_PURPLE).setEasing(Easing.SINE_IN_OUT).setCoefficient(0.9f).build();

    public EthericNitrateEntity(Level level) {
        super(EntityRegistry.ETHERIC_NITRATE.get(), level);
    }

    public EthericNitrateEntity(LivingEntity owner) {
        super(EntityRegistry.ETHERIC_NITRATE.get(), owner, owner.level());
    }

    @Override
    public float getExplosionRadius() {
        return 2.25f;
    }

    @Override
    public int getMaxPierce() {
        return 3;
    }

    @Override
    public ParticleEffectType getImpactParticleEffect() {
        return ParticleEffectTypeRegistry.ETHERIC_NITRATE_IMPACT;
    }

    @Override
    public ColorEffectData getImpactParticleEffectColor() {
        return new ColorEffectData(AURIC_COLOR_DATA);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void spawnParticles() {
        float scalar = age > MAX_AGE - 10 ? 1f - (age - MAX_AGE + 10) / 10f : 1f;
        if (age < 5) {
            scalar = age / 5f;
        }
        Vec3 norm = getDeltaMovement().normalize().scale(0.05f);
        var lightSpecs = SpiritLightSpecs.spiritLightSpecs(level(), position(), AURIC_COLOR_DATA);
        lightSpecs.getBuilder().multiplyLifetime(1.5f).setMotion(norm);
        lightSpecs.getBloomBuilder().multiplyLifetime(1.5f).setMotion(norm);
        lightSpecs.spawnParticles();
        Color startingSmokeColor = age < 3 ? AbstractNitrateEntity.SECOND_SMOKE_COLOR : AURIC_YELLOW;
        for (int i = 0; i < 3; i++) {
            int lifetime = (int) (RandomHelper.randomBetween(random, 60, 80) * (1 - i / 3f));
            final SpinParticleData spinData = SpinParticleData.createRandomDirection(random, 0, RandomHelper.randomBetween(random, 0f, 0.4f), 0).randomSpinOffset(random).build();
            final Consumer<LodestoneWorldParticle> behavior = p -> p.setParticleSpeed(p.getParticleSpeed().scale(0.98f));
            WorldParticleBuilder.create(ParticleRegistry.STRANGE_SMOKE)
                    .setTransparencyData(GenericParticleData.create(0.7f * scalar, 0.9f * scalar, 0f).setEasing(Easing.SINE_IN_OUT, Easing.SINE_IN).build())
                    .setSpinData(spinData)
                    .setScaleData(GenericParticleData.create(0.2f * scalar, 0.4f * scalar, 0.6f * scalar).setEasing(Easing.QUINTIC_OUT, Easing.SINE_IN).build())
                    .setColorData(ColorParticleData.create(startingSmokeColor, AbstractNitrateEntity.SECOND_SMOKE_COLOR).setEasing(Easing.QUINTIC_OUT).build())
                    .setLifetime(Math.min(6 + age * 3, lifetime))
                    .setLifeDelay(1)
                    .enableNoClip()
                    .enableForcedSpawn()
                    .setSpritePicker(SimpleParticleOptions.ParticleSpritePicker.WITH_AGE)
                    .addTickActor(behavior)
                    .setRenderType(LodestoneWorldParticleRenderType.LUMITRANSPARENT)
                    .spawn(level(), position().x, position().y, position().z);
        }
    }
}