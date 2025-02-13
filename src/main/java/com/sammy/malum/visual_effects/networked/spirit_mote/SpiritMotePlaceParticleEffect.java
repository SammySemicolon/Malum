package com.sammy.malum.visual_effects.networked.spirit_mote;

import com.sammy.malum.visual_effects.networked.*;
import com.sammy.malum.visual_effects.networked.data.ColorEffectData;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;

import net.neoforged.api.distmarker.*;
import team.lodestar.lodestone.helpers.ColorHelper;
import team.lodestar.lodestone.registry.common.particle.*;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.particle.SimpleParticleOptions;
import team.lodestar.lodestone.systems.particle.builder.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.color.ColorParticleData;
import team.lodestar.lodestone.systems.particle.data.spin.SpinParticleData;

import java.awt.*;
import java.util.function.Supplier;

public class SpiritMotePlaceParticleEffect extends ParticleEffectType {

    public SpiritMotePlaceParticleEffect(String id) {
        super(id);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public Supplier<ParticleEffectActor> get() {
        return () -> (level, random, positionData, colorData, nbtData) -> {
            BlockPos pos = positionData.getAsBlockPos();
            for (int i = 0; i < 6; i++) {
                int spinDirection = (random.nextBoolean() ? 1 : -1);
                int spinOffset = random.nextInt(360);
                int lifetime = (int) (20 * Mth.nextFloat(random, 0.9f, 1.8f));
                WorldParticleBuilder.create(LodestoneParticleTypes.TWINKLE_PARTICLE)
                        .setTransparencyData(GenericParticleData.create(0.4f, 0.8f, 0).build())
                        .setSpinData(SpinParticleData.create(0.7f * spinDirection, 0).setSpinOffset(spinOffset).setSpinOffset(1.25f).setEasing(Easing.CUBIC_IN).build())
                        .setScaleData(GenericParticleData.create(0.075f, 0.15f, 0).setCoefficient(0.8f).setEasing(Easing.QUINTIC_OUT, Easing.EXPO_IN_OUT).build())
                        .setColorData(colorData.getSpirit().createColorData().setCoefficient(Mth.nextFloat(random, 0.6f, 1.1f)).build())
                        .setLifetime(lifetime)
                        .setRandomOffset(0.6f)
                        .enableNoClip()
                        .setGravityStrength(1.1f)
                        .addMotion(0, 0.25f + random.nextFloat() * 0.1f, 0)
                        .disableNoClip()
                        .setRandomMotion(0.1f, 0.12f)
                        .setDiscardFunction(SimpleParticleOptions.ParticleDiscardFunctionType.ENDING_CURVE_INVISIBLE)
                        .surroundBlock(level, pos);
            }
            for (int i = 0; i < 8; i++) {
                int spinDirection = (random.nextBoolean() ? 1 : -1);
                int spinOffset = random.nextInt(360);
                int lifetime = (int) (30 * Mth.nextFloat(random, 0.9f, 1.8f));
                WorldParticleBuilder.create(LodestoneParticleTypes.SMOKE_PARTICLE)
                        .setTransparencyData(GenericParticleData.create(0.12f, 0.06f, 0).setEasing(Easing.SINE_IN, Easing.SINE_IN).build())
                        .setSpinData(SpinParticleData.create((0.125f + random.nextFloat() * 0.075f) * spinDirection).setSpinOffset(spinOffset).build())
                        .setScaleData(GenericParticleData.create(0.85f, 0.5f, 0).setEasing(Easing.EXPO_OUT, Easing.SINE_IN).setCoefficient(0.8f).build())
                        .setColorData(colorData.getSpirit().createColorData().setCoefficient(0.6f).build())
                        .setLifetime(lifetime)
                        .setRandomOffset(0.4f)
                        .enableNoClip()
                        .setRandomMotion(0.01f, 0.01f)
                        .setDiscardFunction(SimpleParticleOptions.ParticleDiscardFunctionType.ENDING_CURVE_INVISIBLE)
                        .repeatSurroundBlock(level, pos, 2);
            }
        };
    }
}