package com.sammy.malum.visual_effects;

import com.sammy.malum.client.*;
import com.sammy.malum.core.systems.spirit.*;
import com.sammy.malum.registry.client.*;
import net.minecraft.util.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.registry.common.particle.*;
import team.lodestar.lodestone.systems.easing.*;
import team.lodestar.lodestone.systems.particle.*;
import team.lodestar.lodestone.systems.particle.builder.*;
import team.lodestar.lodestone.systems.particle.data.*;
import team.lodestar.lodestone.systems.particle.data.color.*;
import team.lodestar.lodestone.systems.particle.data.spin.*;
import team.lodestar.lodestone.systems.particle.world.*;
import team.lodestar.lodestone.systems.particle.world.behaviors.components.*;
import team.lodestar.lodestone.systems.particle.world.options.*;

import java.util.function.*;

import static net.minecraft.util.Mth.*;

public class SparkParticleEffects {

    public static ParticleEffectSpawner spiritMotionSparks(Level level, Vec3 pos, MalumSpiritType spiritType) {
        return spiritMotionSparks(level, pos, spiritType, new WorldParticleOptions(ParticleRegistry.SPARK));
    }

    public static ParticleEffectSpawner spiritMotionSparks(Level level, Vec3 pos, ColorParticleData colorData) {
        return spiritMotionSparks(level, pos, colorData, new WorldParticleOptions(ParticleRegistry.SPARK));
    }

    public static ParticleEffectSpawner spiritMotionSparks(Level level, Vec3 pos, MalumSpiritType spiritType, WorldParticleOptions options) {
        return spiritMotionSparks(level, pos, options, o -> SpiritBasedParticleBuilder.createSpirit(o).setSpirit(spiritType));
    }

    public static ParticleEffectSpawner spiritMotionSparks(Level level, Vec3 pos, ColorParticleData colorData, WorldParticleOptions options) {
        return spiritMotionSparks(level, pos, options, o -> WorldParticleBuilder.create(o).setColorData(colorData));
    }

    public static ParticleEffectSpawner spiritMotionSparks(Level level, Vec3 pos, WorldParticleOptions options, Function<WorldParticleOptions, WorldParticleBuilder> builderSupplier) {
        var lengthData = GenericParticleData.create(0.1f, 0.2f, 0f).setEasing(Easing.SINE_IN, Easing.SINE_IN_OUT).build();
        var builder = builderSupplier.apply(options.setBehaviorIfDefault(new SparkBehaviorComponent(lengthData)));
        var bloomBuilder = builderSupplier.apply(new WorldParticleOptions(LodestoneParticleTypes.WISP_PARTICLE));
        return spiritMotionSparks(level, pos, builder, bloomBuilder);
    }

    public static ParticleEffectSpawner spiritMotionSparks(Level level, Vec3 pos, WorldParticleBuilder builder, WorldParticleBuilder bloomBuilder) {
        RandomSource rand = level.getRandom();
        final SpinParticleData spinData = SpinParticleData.createRandomDirection(rand, nextFloat(rand, 0.05f, 0.1f)).randomSpinOffset(rand).build();
        final Consumer<LodestoneWorldParticle> slowDown = p -> p.setParticleSpeed(p.getParticleSpeed().scale(0.95f));
        int lifetime = RandomHelper.randomBetween(rand, 10, 20);
        final WorldParticleBuilder sparkParticleBuilder = builder
                .setScaleData(GenericParticleData.create(0.1f, RandomHelper.randomBetween(rand, 0.2f, 0.3f), 0).build())
                .setSpritePicker(SimpleParticleOptions.ParticleSpritePicker.WITH_AGE)
                .setTransparencyData(GenericParticleData.create(0.8f, 0f).build())
                .addTickActor(slowDown)
                .setLifetime(lifetime)
                .enableNoClip();
        final WorldParticleBuilder bloomParticleBuilder = SpiritLightSpecs.spiritBloom(level, bloomBuilder, lifetime).modifyData(AbstractParticleBuilder::getScaleData, d -> d.multiplyValue(0.5f)).setSpinData(spinData).addTickActor(slowDown);
        return new ParticleEffectSpawner(level, pos, sparkParticleBuilder, bloomParticleBuilder);
    }
}