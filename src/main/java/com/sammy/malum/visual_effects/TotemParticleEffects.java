package com.sammy.malum.visual_effects;

import com.sammy.malum.common.block.curiosities.totem.*;
import com.sammy.malum.core.systems.spirit.*;
import net.minecraft.core.*;
import net.minecraft.util.*;
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
        for (int i = 0; i < 8; i++) {
            var offsetPosition = VecHelper.rotatingRadialOffset(position, 0.7f, i, 8, gameTime, time);
            offsetPosition = offsetPosition.add(0, (Math.cos(((gameTime + i * 480) % time) / time) * 0.25f) - 0.25f, 0);
            for (int j = 0; j < 3; j++) {
                var lightSpecs = spiritLightSpecs(level, offsetPosition, spiritType);
                float velocity = RandomHelper.randomBetween(random, 0.02f, 0.03f);
                var motion = offsetPosition.subtract(position).normalize().scale(velocity);
                lightSpecs.getBuilder()
                        .multiplyLifetime(2.5f)
                        .setMotion(motion)
                        .setLifeDelay(j * 6)
                        .setTransparencyData(GenericParticleData.create(0.2f, 0.8f, 0f).build())
                        .modifyData(AbstractParticleBuilder::getScaleData, d -> d.multiplyValue(RandomHelper.randomBetween(random, 1f, 2f)));
                lightSpecs.getBloomBuilder()
                        .multiplyLifetime(1.5f)
                        .setMotion(motion)
                        .setLifeDelay(j * 6)
                        .setTransparencyData(GenericParticleData.create(0.05f, 0.35f, 0f).build())
                        .modifyData(AbstractParticleBuilder::getScaleData, d -> d.multiplyValue(RandomHelper.randomBetween(random, 0.5f, 1f)));
                lightSpecs.spawnParticles();
            }
        }
    }
    public static void triggerBlockFallEffect(Level level, MalumSpiritType spiritType, BlockPos position) {
        var random = level.random;
        for (int i = 0; i < 4; i++) {
            int xOffset = Mth.clamp(i%3, 0, 1);
            int zOffset = Mth.clamp((i-1)%4, 0, 1);
            for (int j = 0; j < 2; j++) {
                Vec3 offsetPosition = new Vec3(position.getX()+xOffset, position.getY()+j, position.getZ()+zOffset);
                float motion = RandomHelper.randomBetween(random, 0.05f, 0.06f);
                Vec3 velocity = position.getCenter().subtract(offsetPosition).add(0, -2, 0).normalize().scale(motion);
                var lightSpecs = spiritLightSpecs(level, offsetPosition, spiritType);
                lightSpecs.getBuilder()
                        .multiplyLifetime(2.5f)
                        .setMotion(velocity)
                        .setTransparencyData(GenericParticleData.create(0.2f, 0.8f, 0f).build())
                        .modifyData(AbstractParticleBuilder::getScaleData, d -> d.multiplyValue(RandomHelper.randomBetween(random, 1f, 2f)));
                lightSpecs.getBloomBuilder()
                        .setTransparencyData(GenericParticleData.create(0.05f, 0.35f, 0f).build())
                        .modifyData(AbstractParticleBuilder::getScaleData, d -> d.multiplyValue(RandomHelper.randomBetween(random, 0.5f, 1f)));
                lightSpecs.spawnParticles();
            }
        }
    }

    public static void triggerBlockEffect(Level level, MalumSpiritType spiritType, BlockPos position) {
        var random = level.random;
        for (int i = 0; i < 4; i++) {
            int xOffset = Mth.clamp(i%3, 0, 1);
            int zOffset = Mth.clamp((i-1)%4, 0, 1);
            float xMotion = (i%2) * (i > 1 ? 0.06f : -0.06f);
            float zMotion = ((i + 1) % 2) * (i > 1 ? -0.06f : 0.06f);
            for (int j = 0; j < 2; j++) {
                Vec3 offsetPosition = new Vec3(position.getX()+xOffset, position.getY()+j, position.getZ()+zOffset);
                var lightSpecs = spiritLightSpecs(level, offsetPosition, spiritType);
                lightSpecs.getBuilder()
                        .multiplyLifetime(2.5f)
                        .setMotion(xMotion, 0, zMotion)
                        .setTransparencyData(GenericParticleData.create(0.2f, 0.8f, 0f).build())
                        .modifyData(AbstractParticleBuilder::getScaleData, d -> d.multiplyValue(RandomHelper.randomBetween(random, 1f, 2f)));
                lightSpecs.getBloomBuilder()
                        .multiplyLifetime(1.5f)
                        .setMotion(xMotion, 0, zMotion)
                        .setTransparencyData(GenericParticleData.create(0.05f, 0.35f, 0f).build())
                        .modifyData(AbstractParticleBuilder::getScaleData, d -> d.multiplyValue(RandomHelper.randomBetween(random, 0.5f, 1f)));
                lightSpecs.spawnParticles();
            }
        }
    }
}