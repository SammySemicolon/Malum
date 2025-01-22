package com.sammy.malum.visual_effects.networked.geas;

import com.sammy.malum.registry.client.*;
import com.sammy.malum.visual_effects.*;
import com.sammy.malum.visual_effects.networked.*;
import net.minecraft.client.*;
import net.minecraft.util.*;
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
import team.lodestar.lodestone.systems.particle.world.behaviors.components.*;

import java.awt.*;
import java.util.function.*;

import static com.sammy.malum.visual_effects.SpiritLightSpecs.spiritLightSpecs;
import static net.minecraft.util.Mth.*;

public class SoulwashingGeasImpactParticleEffect extends ParticleEffectType {

    public SoulwashingGeasImpactParticleEffect(String id) {
        super(id);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public Supplier<ParticleEffectActor> get() {
        return () -> (level, random, positionData, colorData, nbtData) -> {
            double posX = positionData.posX;
            double posY = positionData.posY;
            double posZ = positionData.posZ;
            Vec3 pos = new Vec3(posX, posY, posZ);
            Vec3 directionToPlayer = Minecraft.getInstance().player.getEyePosition().subtract(pos).normalize();
            Vec3 behindPos = pos.subtract(directionToPlayer.scale(2f));
            Vec3 inFrontPos = pos.add(directionToPlayer.scale(0.75f));
            final Color primaryColor = colorData.getDefaultColorRecord().primaryColor();
            final Color secondaryColor = colorData.getDefaultColorRecord().secondaryColor();
            for (int i = 0; i < 3; i++) {
                ColorParticleData colorParticleData = ColorParticleData.create(primaryColor, secondaryColor).setEasing(Easing.SINE_IN_OUT).setCoefficient(0.9f).build();
                final SpinParticleData spinData = SpinParticleData.createRandomDirection(random, nextFloat(random, 0.15f, 0.3f)).randomSpinOffset(random).build();
                float scaleMultiplier = RandomHelper.randomBetween(random, 0.5f, 1f);
                WorldParticleBuilder.create(ParticleRegistry.SHINE.get())
                        .setScaleData(GenericParticleData.create(0.8f * scaleMultiplier, 0.25f, 0).setEasing(Easing.EXPO_OUT, Easing.SINE_IN).build())
                        .setTransparencyData(GenericParticleData.create(0.6f, 0.07f, 0).setEasing(Easing.SINE_IN, Easing.CIRC_IN).build())
                        .setDiscardFunction(SimpleParticleOptions.ParticleDiscardFunctionType.ENDING_CURVE_INVISIBLE)
                        .setSpritePicker(SimpleParticleOptions.ParticleSpritePicker.WITH_AGE)
                        .setLifetime(RandomHelper.randomBetween(random, 5, 15))
                        .setRandomMotion(0.02f, 0.02f)
                        .setColorData(colorParticleData)
                        .setSpinData(spinData)
                        .setRandomOffset(1f)
                        .enableNoClip()
                        .repeat(level, inFrontPos.x, inFrontPos.y, inFrontPos.z, 2);

                WorldParticleBuilder.create(ParticleRegistry.GIANT_GLOWING_STAR.get())
                        .setScaleData(GenericParticleData.create(5f * scaleMultiplier, 0.25f, 0).setEasing(Easing.SINE_IN, Easing.SINE_IN).build())
                        .setTransparencyData(GenericParticleData.create(0.4f, 0.07f, 0).setEasing(Easing.SINE_IN, Easing.CIRC_IN).build())
                        .setColorData(ColorParticleData.create(secondaryColor, ColorHelper.darker(primaryColor, 6)).build())
                        .setDiscardFunction(SimpleParticleOptions.ParticleDiscardFunctionType.ENDING_CURVE_INVISIBLE)
                        .setLifetime(RandomHelper.randomBetween(random, 10, 20))
                        .setRenderType(LodestoneWorldParticleRenderType.LUMITRANSPARENT)
                        .setRandomMotion(0.02f, 0.02f)
                        .setSpinData(spinData)
                        .setRandomOffset(1f)
                        .enableNoClip()
                        .repeat(level, behindPos.x, behindPos.y, behindPos.z, 1);
            }
        };
    }
}