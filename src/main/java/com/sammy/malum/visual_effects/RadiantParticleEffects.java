package com.sammy.malum.visual_effects;

import com.sammy.malum.common.block.curiosities.weeping_well.*;
import com.sammy.malum.visual_effects.networked.data.*;
import net.minecraft.core.*;
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
import team.lodestar.lodestone.systems.particle.render_types.*;
import team.lodestar.lodestone.systems.particle.world.*;

import java.awt.*;
import java.util.List;
import java.util.function.*;

public class RadiantParticleEffects {
    public static final java.util.List<Color> RADIANT_COLORS = List.of(
            new Color(179, 35, 218),
            new Color(224, 210, 68),
            new Color(42, 146, 218));

    public static void spitOutWeepingWellRadiance(Level level, PositionEffectData positionEffectData) {
        double posX = positionEffectData.posX;
        double posY = positionEffectData.posY;
        double posZ = positionEffectData.posZ;
        Vec3 pos = new Vec3(posX, posY, posZ);
        RandomSource rand = level.random;
        Consumer<LodestoneWorldParticle> spawnBehavior = p -> p.tick(2);
        for (int i = 0; i < 64; i++) {
            Color color = RADIANT_COLORS.get((i % 12) / 4);
            final ColorParticleData colorData = ColorParticleData.create(color.brighter(), color).setCoefficient(0.5f).build();
            float xVelocity = RandomHelper.randomBetween(rand, 0f, 0.15f) * (rand.nextBoolean() ? 1 : -1);
            float yVelocity = RandomHelper.randomBetween(rand, 0.5f, 1f);
            float zVelocity = RandomHelper.randomBetween(rand, 0f, 0.15f) * (rand.nextBoolean() ? 1 : -1);
            float gravityStrength = RandomHelper.randomBetween(rand, 0.75f, 1f);
            if (rand.nextFloat() < 0.85f) {
                var sparkParticles = WeepingWellParticleEffects.weepingWellSparks(level, pos, colorData, LodestoneWorldParticleRenderType.ADDITIVE);
                sparkParticles.getBuilder()
                        .addSpawnActor(spawnBehavior)
                        .disableNoClip()
                        .setGravityStrength(gravityStrength / 2f)
                        .setMotion(xVelocity, yVelocity, zVelocity)
                        .modifyData(AbstractParticleBuilder::getTransparencyData, d -> d.multiplyValue(2f))
                        .modifyData(AbstractParticleBuilder::getScaleData, d -> d.multiplyValue(1.5f));
                sparkParticles.getBloomBuilder()
                        .addSpawnActor(spawnBehavior)
                        .disableNoClip()
                        .setGravityStrength(gravityStrength / 2f)
                        .setMotion(xVelocity, yVelocity, zVelocity)
                        .modifyData(AbstractParticleBuilder::getTransparencyData, d -> d.multiplyValue(1.25f));
                sparkParticles.spawnParticles();
            }
            if (rand.nextFloat() < 0.85f) {
                xVelocity *= 1.25f;
                yVelocity *= 0.75f;
                zVelocity *= 1.25f;
                var lightSpecs = WeepingWellParticleEffects.weepingWellSpecs(level, pos, colorData, LodestoneWorldParticleRenderType.ADDITIVE);
                lightSpecs.getBuilder()
                        .addSpawnActor(spawnBehavior)
                        .disableNoClip()
                        .setGravityStrength(gravityStrength)
                        .setMotion(xVelocity, yVelocity, zVelocity)
                        .modifyData(AbstractParticleBuilder::getScaleData, d -> d.multiplyValue(1.5f));
                lightSpecs.getBloomBuilder()
                        .addSpawnActor(spawnBehavior)
                        .disableNoClip()
                        .setGravityStrength(gravityStrength)
                        .setMotion(xVelocity, yVelocity, zVelocity)
                        .modifyData(AbstractParticleBuilder::getTransparencyData, d -> d.multiplyValue(1.25f));
                lightSpecs.spawnParticles();
            }
        }
        int spinOffset = rand.nextInt(360);
        for (int i = 0; i < 3; i++) {
            int lifeDelay = 4 * i;
            Color color = RADIANT_COLORS.get((int) ((level.getGameTime() % 3 + i) % 3));
            final ColorParticleData colorData = ColorParticleData.create(color.brighter(), color).setCoefficient(0.5f).build();
            int spinDirection = (rand.nextBoolean() ? 1 : -1);
            WorldParticleBuilder.create(LodestoneParticleTypes.SPARKLE_PARTICLE)
                    .setTransparencyData(GenericParticleData.create(0.1f, 0.4f, 0).setEasing(Easing.QUAD_IN, Easing.CIRC_IN).build())
                    .setSpinData(SpinParticleData.create((0.05f + rand.nextFloat() * 0.025f) * spinDirection).setSpinOffset(spinOffset).build())
                    .setScaleData(GenericParticleData.create(3f, 5f, 0.6f).setEasing(Easing.QUARTIC_OUT, Easing.SINE_IN).build())
                    .setColorData(colorData)
                    .setLifeDelay(lifeDelay)
                    .setLifetime(25 + lifeDelay)
                    .setRandomOffset(0.6f)
                    .enableNoClip()
                    .setRandomMotion(0.02f, 0.02f)
                    .setDiscardFunction(SimpleParticleOptions.ParticleDiscardFunctionType.ENDING_CURVE_INVISIBLE)
                    .repeat(level, posX, posY + 0.25f, posZ, 5);

        }
        for (int i = 0; i < 27; i++) {
            int finalI = 4 + i;
            Color color = RADIANT_COLORS.get(((int) (level.getGameTime() - 27 + i) % 27) / 9);
            final ColorParticleData colorData = ColorParticleData.create(color.brighter(), color).setCoefficient(0.5f).build();
            var squares = WeepingWellParticleEffects.weepingWellSquare(level, pos, colorData);
            squares.getBuilder().addSpawnActor(p -> p.tick(finalI));
            squares.spawnParticles();
        }
    }

    public static void radiantWeepingWellParticles(VoidConduitBlockEntity voidConduit) {
        Level level = voidConduit.getLevel();
        RandomSource rand = level.random;
        Color color = RADIANT_COLORS.get(((int) level.getGameTime() % 27) / 9);
        final BlockPos blockPos = voidConduit.getBlockPos();
        final ColorParticleData colorData = ColorParticleData.create(color.brighter(), color).setCoefficient(0.5f).build();
        if (level.getGameTime() % 3L == 0) {
            WeepingWellParticleEffects.weepingWellSquare(level, new Vec3(blockPos.getX() + 0.5f, blockPos.getY() + 0.75f, blockPos.getZ() + 0.5f), colorData).spawnParticles();
        }

        final float acceleration = RandomHelper.randomBetween(rand, 0.002f, 0.02f);
        final long gameTime = level.getGameTime();
        final Consumer<LodestoneWorldParticle> behavior = p -> {
            if (level.getGameTime() < gameTime + 5) {
                p.setParticleSpeed(p.getParticleSpeed().add(0, acceleration, 0));
            }
        };
        if (level.getGameTime() % 2L == 0) {
            int rotation = (int) ((level.getGameTime() / 2f) % 16);
            Vec3 offsetPosition = VecHelper.rotatingRadialOffset(new Vec3(blockPos.getX() + 0.5f, blockPos.getY() + 0.75f, blockPos.getZ() + 0.5f), 1.1f, rotation, 16, voidConduit.getLevel().getGameTime(), 640);
            var lightSpecs = WeepingWellParticleEffects.weepingWellSpecs(level, offsetPosition, colorData, LodestoneWorldParticleRenderType.ADDITIVE);
            lightSpecs.getBuilder().addTickActor(behavior);
            lightSpecs.getBloomBuilder().addTickActor(behavior);
            lightSpecs.spawnParticles();
        }
    }
}