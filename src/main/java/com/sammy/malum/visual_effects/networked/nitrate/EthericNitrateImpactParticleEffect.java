package com.sammy.malum.visual_effects.networked.nitrate;

import com.sammy.malum.registry.client.*;
import com.sammy.malum.visual_effects.*;
import com.sammy.malum.visual_effects.networked.*;
import net.minecraft.util.*;
import net.minecraft.world.phys.*;
import net.neoforged.api.distmarker.*;
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

import java.awt.*;
import java.util.function.*;

import static com.sammy.malum.visual_effects.SpiritLightSpecs.spiritLightSpecs;
import static net.minecraft.util.Mth.*;

public class EthericNitrateImpactParticleEffect extends ParticleEffectType {

    public EthericNitrateImpactParticleEffect(String id) {
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
            final Color primaryColor = colorData.getDefaultColorRecord().primaryColor();
            final Color secondaryColor = colorData.getDefaultColorRecord().secondaryColor();
            ColorParticleData colorParticleData = ColorParticleData.create(primaryColor, secondaryColor).setEasing(Easing.SINE_IN_OUT).setCoefficient(0.9f).build();
            for (int i = 0; i < 16; i++) {
                float lifetimeMultiplier = RandomHelper.randomBetween(random, 1f, 1.5f);
                float gravityStrength = RandomHelper.randomBetween(random, 0.03f, 0.06f);
                double horizontalAngle = random.nextDouble() * Math.PI * 2;
                double x = (Math.cos(horizontalAngle));
                double y = Mth.nextFloat(random, -1, 1);
                double z = (Math.sin(horizontalAngle));
                Vec3 direction = new Vec3(x, y, z);
                Vec3 motion = direction.scale(RandomHelper.randomBetween(random, 2f, 3f));
                Vec3 spawnPosition = pos.add(direction.scale(0.25f));
                final Consumer<LodestoneWorldParticle> sparkBehavior = p -> {
                    Vec3 velocity = p.getParticleSpeed().scale(0.75f);
                    if (velocity.equals(Vec3.ZERO)) {
                        velocity = p.getParticleSpeed();
                    }
                    p.setParticleSpeed(velocity.x, (velocity.y - gravityStrength) * 0.98f, velocity.z);
                    if (p.getAge() < p.getLifetime() * 0.7f) {
                        if (level.getGameTime() % 2 == 0) {
                            var lightSpecs = spiritLightSpecs(level, p.getParticlePosition(), colorParticleData);
                            lightSpecs.getBuilder()
                                    .multiplyLifetime(lifetimeMultiplier / 2f)
                                    .enableForcedSpawn();
                            lightSpecs.getBloomBuilder()
                                    .multiplyLifetime(lifetimeMultiplier / 4f);
                            lightSpecs.spawnParticles();
                        }
                    }
                };
                float scalar = RandomHelper.randomBetween(random, 0.8f, 1.1f);
                var lengthData = GenericParticleData.create(3f * scalar, 0.75f * scalar, 0f).setEasing(Easing.QUARTIC_OUT, Easing.SINE_IN_OUT).build();
                var sparks = SparkParticleEffects.spiritMotionSparks(level, spawnPosition, colorParticleData).act(b -> b.getParticleOptions().setBehavior(new SparkBehaviorComponent(lengthData)));
                sparks.getBuilder()
                        .multiplyLifetime(lifetimeMultiplier)
                        .enableForcedSpawn()
                        .addTickActor(sparkBehavior)
                        .setMotion(motion)
                        .setScaleData(GenericParticleData.create(0.4f * scalar, 0.2f * scalar, 0f).setEasing(Easing.SINE_IN, Easing.QUAD_IN).build());
                sparks.getBloomBuilder()
                        .multiplyLifetime(lifetimeMultiplier)
                        .addTickActor(sparkBehavior)
                        .modifyData(AbstractParticleBuilder::getScaleData, d -> d.multiplyValue(2f))
                        .setMotion(motion);
                sparks.spawnParticles();
            }
            final SpinParticleData spinData = SpinParticleData.createRandomDirection(random, nextFloat(random, 0.05f, 0.1f)).randomSpinOffset(random).build();
            float scaleMultiplier = (float) (1 + Math.pow(random.nextFloat(), 2) * 0.5f);
            WorldParticleBuilder.create(ParticleRegistry.GIANT_GLOWING_STAR.get())
                    .setTransparencyData(GenericParticleData.create(0.9f, 0.07f, 0).setEasing(Easing.SINE_IN, Easing.CIRC_IN).build())
                    .setLifetime(15)
                    .setSpinData(spinData)
                    .setScaleData(GenericParticleData.create(5f * scaleMultiplier, 0.5f, 0).setEasing(Easing.EXPO_OUT, Easing.SINE_IN).build())
                    .setColorData(colorParticleData)
                    .setRandomOffset(0.6f)
                    .enableNoClip()
                    .setRandomMotion(0.02f, 0.02f)
                    .setDiscardFunction(SimpleParticleOptions.ParticleDiscardFunctionType.ENDING_CURVE_INVISIBLE)
                    .repeat(level, posX, posY, posZ, 3);
        };
    }
}