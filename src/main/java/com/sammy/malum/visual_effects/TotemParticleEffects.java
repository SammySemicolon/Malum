package com.sammy.malum.visual_effects;

import com.sammy.malum.common.block.curiosities.totem.*;
import com.sammy.malum.core.systems.spirit.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.systems.particle.builder.*;
import team.lodestar.lodestone.systems.particle.data.*;

import static com.sammy.malum.visual_effects.SpiritLightSpecs.*;

public class TotemParticleEffects {

    public static void activeTotemPoleParticles(TotemPoleBlockEntity totemPoleBlockEntity) {
        MalumSpiritType spiritType = totemPoleBlockEntity.spirit;
        Level level = totemPoleBlockEntity.getLevel();
        long gameTime = level.getGameTime();
        var random = level.random;
        if (gameTime % 12L == 0) {
            int offset = (totemPoleBlockEntity.getBlockPos().getY() - totemPoleBlockEntity.totemBaseYLevel) * 40;
            gameTime += offset;
            final float time = 480;
            for (int i = 0; i < 2; i++) {
                float velocity = RandomHelper.randomBetween(random, 0.005f, 0.015f);
                Vec3 offsetPosition = VecHelper.rotatingRadialOffset(totemPoleBlockEntity.getBlockPos().getCenter(), 0.9f, i, 2, gameTime, time);
                offsetPosition = offsetPosition.add(0, (Math.cos(((gameTime + i * 240) % time) / time) * 0.25f) - 0.25f, 0);
                var lightSpecs = spiritLightSpecs(level, offsetPosition, spiritType);
                lightSpecs.getBuilder()
                        .multiplyLifetime(4.5f)
                        .setMotion(0, velocity, 0)
                        .setTransparencyData(GenericParticleData.create(0.2f, 0.8f, 0f).build())
                        .modifyData(AbstractParticleBuilder::getScaleData, d -> d.multiplyValue(RandomHelper.randomBetween(random, 1f, 2f)));
                lightSpecs.getBloomBuilder()
                        .multiplyLifetime(3f)
                        .setMotion(0, velocity, 0)
                        .setTransparencyData(GenericParticleData.create(0.05f, 0.35f, 0f).build())
                        .modifyData(AbstractParticleBuilder::getScaleData, d -> d.multiplyValue(RandomHelper.randomBetween(random, 0.5f, 1f)));
                lightSpecs.spawnParticles();
            }
        }
    }

    public static void activateTotemPoleParticles(Level level, MalumSpiritType spiritType, Vec3 position) {
        long gameTime = level.getGameTime();
        var random = level.random;
        final float time = 16;
        for (int i = 0; i < 16; i++) {
            float velocity = RandomHelper.randomBetween(random, 0.005f, 0.015f);
            Vec3 offsetPosition = VecHelper.rotatingRadialOffset(position, 0.85f, i, 16, gameTime, time);
            offsetPosition = offsetPosition.add(0, (Math.cos(((gameTime + i * 240) % time) / time) * 0.25f) - 0.25f, 0);
            var lightSpecs = spiritLightSpecs(level, offsetPosition, spiritType);
            lightSpecs.getBuilder()
                    .multiplyLifetime(2.5f)
                    .setMotion(0, velocity, 0)
                    .setLifeDelay(i)
                    .setTransparencyData(GenericParticleData.create(0.2f, 0.8f, 0f).build())
                    .modifyData(AbstractParticleBuilder::getScaleData, d -> d.multiplyValue(RandomHelper.randomBetween(random, 1f, 2f)));
            lightSpecs.getBloomBuilder()
                    .multiplyLifetime(1.5f)
                    .setMotion(0, velocity, 0)
                    .setLifeDelay(i)
                    .setTransparencyData(GenericParticleData.create(0.05f, 0.35f, 0f).build())
                    .modifyData(AbstractParticleBuilder::getScaleData, d -> d.multiplyValue(RandomHelper.randomBetween(random, 0.5f, 1f)));
            lightSpecs.spawnParticles();
        }
    }

    public static void triggerEntityEffect(Level level, MalumSpiritType spiritType, Vec3 position) {
        long gameTime = level.getGameTime();
        var random = level.random;
        final float time = 16;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 8; j++) {
                float velocity = RandomHelper.randomBetween(random, 0.02f, 0.03f);
                var offsetPosition = VecHelper.rotatingRadialOffset(position, 0.7f, j, 8, gameTime, time);
                offsetPosition = offsetPosition.add(0, (Math.cos(((gameTime + j * 480) % time) / time) * 0.25f) - 0.25f, 0);
                var motion = offsetPosition.subtract(position).normalize().scale(velocity);
                var lightSpecs = spiritLightSpecs(level, offsetPosition, spiritType);
                lightSpecs.getBuilder()
                        .multiplyLifetime(2.5f)
                        .setMotion(motion)
                        .setLifeDelay(i*6)
                        .setTransparencyData(GenericParticleData.create(0.2f, 0.8f, 0f).build())
                        .modifyData(AbstractParticleBuilder::getScaleData, d -> d.multiplyValue(RandomHelper.randomBetween(random, 1f, 2f)));
                lightSpecs.getBloomBuilder()
                        .multiplyLifetime(1.5f)
                        .setMotion(motion)
                        .setLifeDelay(i)
                        .setTransparencyData(GenericParticleData.create(0.05f, 0.35f, 0f).build())
                        .modifyData(AbstractParticleBuilder::getScaleData, d -> d.multiplyValue(RandomHelper.randomBetween(random, 0.5f, 1f)));
                lightSpecs.spawnParticles();
            }
        }
    }
}