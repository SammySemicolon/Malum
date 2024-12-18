package com.sammy.malum.visual_effects.networked.generic;


import com.sammy.malum.visual_effects.networked.ParticleEffectType;
import com.sammy.malum.visual_effects.networked.data.ColorEffectData;
import net.minecraft.util.Mth;


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

public class RisingSparklesParticleEffect extends ParticleEffectType {

    //Generic sparkle particles all shooting up from a singular point

    public RisingSparklesParticleEffect(String id) {
        super(id);
    }

    
    @Override
    public Supplier<ParticleEffectActor> get() {
        return () -> (level, random, positionData, colorData, nbtData) -> {
            ColorEffectData.ColorRecord colorRecord = colorData.getDefaultColorRecord();
            Color primaryColor = colorData.getPrimaryColor(colorRecord);
            Color secondaryColor = colorData.getSecondaryColor(colorRecord);
            double posX = positionData.posX;
            double posY = positionData.posY;
            double posZ = positionData.posZ;

            for (int i = 0; i < 6; i++) {
                int spinDirection = (random.nextBoolean() ? 1 : -1);
                int spinOffset = random.nextInt(360);
                int lifetime = (int) (20 * Mth.nextFloat(random, 0.9f, 1.8f));
                WorldParticleBuilder.create(LodestoneParticleTypes.TWINKLE_PARTICLE)
                        .setTransparencyData(GenericParticleData.create(0.4f, 0.8f, 0).build())
                        .setSpinData(SpinParticleData.create(0.7f * spinDirection, 0).setSpinOffset(spinOffset).setSpinOffset(1.25f).setEasing(Easing.CUBIC_IN).build())
                        .setScaleData(GenericParticleData.create(0.075f, 0.15f, 0).setCoefficient(0.8f).setEasing(Easing.QUINTIC_OUT, Easing.EXPO_IN_OUT).build())
                        .setColorData(ColorParticleData.create(ColorHelper.brighter(primaryColor, 2), secondaryColor).setCoefficient(Mth.nextFloat(random, 0.6f, 1.1f)).build())
                        .setLifetime(lifetime)
                        .setRandomOffset(0.2f)
                        .enableNoClip()
                        .setGravityStrength(1.1f)
                        .addMotion(0, 0.25f + random.nextFloat() * 0.1f, 0)
                        .disableNoClip()
                        .setRandomMotion(0.1f, 0.12f)
                        .setDiscardFunction(SimpleParticleOptions.ParticleDiscardFunctionType.ENDING_CURVE_INVISIBLE)
                        .repeat(level, posX, posY, posZ, 2);
            }
        };
    }
}