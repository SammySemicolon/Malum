package com.sammy.malum.visual_effects;

import com.sammy.malum.common.block.curiosities.void_depot.*;
import com.sammy.malum.common.block.curiosities.weeping_well.*;
import com.sammy.malum.registry.client.*;
import com.sammy.malum.visual_effects.networked.data.*;
import net.minecraft.core.*;
import net.minecraft.util.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.phys.*;
import net.minecraft.world.phys.shapes.*;
import team.lodestar.lodestone.handlers.*;
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
import team.lodestar.lodestone.systems.particle.world.behaviors.components.*;
import team.lodestar.lodestone.systems.particle.world.options.*;

import java.awt.*;
import java.util.function.*;

import static com.sammy.malum.visual_effects.SpiritLightSpecs.*;

public class WeepingWellParticleEffects {

    private static final VoxelShape WELL_SHAPE = Block.box(-16.0D, 4f, -16.0D, 32.0D, 5f, 32.0D);
    private static final VoxelShape DEPOT_SHAPE = Block.box(3f, 14f, 3f, 13f, 15f, 13f);
    private static final GenericParticleData SMOKE_TRANSPARENCY = GenericParticleData.create(0.5f, 1f, 0.2f).setEasing(Easing.SINE_IN, Easing.SINE_OUT).build();

    public static Color getWeepingWellSmokeColor(RandomSource rand) {
        float colorMultiplier = RandomHelper.randomBetween(rand, 0.6f, 1.2f);
        return new Color((int) (12 * colorMultiplier), (int) (3 * colorMultiplier), (int) (12 * colorMultiplier));
    }

    public static void spitOutItemParticles(Level level, PositionEffectData positionEffectData) {
        double posX = positionEffectData.posX;
        double posY = positionEffectData.posY;
        double posZ = positionEffectData.posZ;
        Vec3 pos = new Vec3(posX, posY, posZ);
        RandomSource rand = level.random;
        Color color = getWeepingWellSmokeColor(rand);
        ColorParticleData colorData = ColorParticleData.create(color, color.darker()).setCoefficient(0.5f).build();
        Consumer<LodestoneWorldParticle> spawnBehavior = p -> p.tick(2);
        for (int i = 0; i < 64; i++) {
            float xVelocity = RandomHelper.randomBetween(rand, 0f, 0.15f) * (rand.nextBoolean() ? 1 : -1);
            float yVelocity = RandomHelper.randomBetween(rand, 0.5f, 1f);
            float zVelocity = RandomHelper.randomBetween(rand, 0f, 0.15f) * (rand.nextBoolean() ? 1 : -1);
            float gravityStrength = RandomHelper.randomBetween(rand, 0.75f, 1f);
            if (rand.nextFloat() < 0.85f) {
                var sparkParticles = weepingWellSparks(level, pos, colorData, LodestoneWorldParticleRenderType.LUMITRANSPARENT);
                sparkParticles.getBuilder()
                        .addSpawnActor(spawnBehavior)
                        .disableNoClip()
                        .setGravityStrength(gravityStrength / 2f)
                        .setMotion(xVelocity, yVelocity, zVelocity)
                        .setRenderTarget(RenderHandler.LATE_DELAYED_RENDER)
                        .modifyData(AbstractParticleBuilder::getTransparencyData, d -> d.multiplyValue(2f))
                        .modifyData(AbstractParticleBuilder::getScaleData, d -> d.multiplyValue(1.5f));
                sparkParticles.getBloomBuilder()
                        .addSpawnActor(spawnBehavior)
                        .disableNoClip()
                        .setGravityStrength(gravityStrength / 2f)
                        .setMotion(xVelocity, yVelocity, zVelocity)
                        .setRenderTarget(RenderHandler.LATE_DELAYED_RENDER)
                        .modifyData(AbstractParticleBuilder::getTransparencyData, d -> d.multiplyValue(1.25f));
                sparkParticles.spawnParticles();
            }
            if (rand.nextFloat() < 0.85f) {
                xVelocity *= 1.25f;
                yVelocity *= 0.75f;
                zVelocity *= 1.25f;
                var lightSpecs = weepingWellSpecs(level, pos, colorData, LodestoneWorldParticleRenderType.LUMITRANSPARENT);
                lightSpecs.getBuilder()
                        .addSpawnActor(spawnBehavior)
                        .disableNoClip()
                        .setGravityStrength(gravityStrength)
                        .setMotion(xVelocity, yVelocity, zVelocity)
                        .setRenderTarget(RenderHandler.LATE_DELAYED_RENDER)
                        .modifyData(AbstractParticleBuilder::getScaleData, d -> d.multiplyValue(2.5f));
                lightSpecs.getBloomBuilder()
                        .addSpawnActor(spawnBehavior)
                        .disableNoClip()
                        .setGravityStrength(gravityStrength)
                        .setMotion(xVelocity, yVelocity, zVelocity)
                        .setRenderTarget(RenderHandler.LATE_DELAYED_RENDER)
                        .modifyData(AbstractParticleBuilder::getTransparencyData, d -> d.multiplyValue(1.25f));
                lightSpecs.spawnParticles();
            }
        }
        int spinOffset = rand.nextInt(360);
        for (int i = 0; i < 4; i++) {
            int spinDirection = (rand.nextBoolean() ? 1 : -1);
            float scaleMultiplier = (float) (1 + Math.pow(rand.nextFloat(), 2));
            WorldParticleBuilder.create(LodestoneParticleTypes.SPARKLE_PARTICLE)
                    .setTransparencyData(GenericParticleData.create(0.7f, 0.5f, 0).setEasing(Easing.SINE_IN, Easing.CIRC_IN).build())
                    .setSpinData(SpinParticleData.create((0.125f + rand.nextFloat() * 0.075f) * spinDirection).setSpinOffset(spinOffset).build())
                    .setScaleData(GenericParticleData.create(2.4f * scaleMultiplier, 0.8f, 0).setEasing(Easing.QUAD_IN, Easing.SINE_IN).build())
                    .setColorData(colorData)
                    .setLifetime(25)
                    .setRandomOffset(0.6f)
                    .enableNoClip()
                    .setRandomMotion(0.02f, 0.02f)
                    .setRenderTarget(RenderHandler.LATE_DELAYED_RENDER)
                    .setDiscardFunction(SimpleParticleOptions.ParticleDiscardFunctionType.ENDING_CURVE_INVISIBLE)
                    .setRenderType(LodestoneWorldParticleRenderType.LUMITRANSPARENT)
                    .repeat(level, posX, posY + 0.25f, posZ, 5);
        }
        for (int i = 0; i < 8; i++) {
            int finalI = 4 + i / 2;
            var squares = weepingWellSquare(level, pos, colorData);
            squares.getBuilder().multiplyLifetime(0.5f).addSpawnActor(p -> p.tick(finalI));
            squares.spawnParticles();
        }
    }

    public static void passiveWeepingWellParticles(VoidConduitBlockEntity voidConduit) {
        Level level = voidConduit.getLevel();
        if (level.getGameTime() % 6L == 0) {
            final BlockPos blockPos = voidConduit.getBlockPos();
            var rand = level.random;
            int lifetime = RandomHelper.randomBetween(rand, 80, 120);
            float yMotion = 0.004f;
            Color color = getWeepingWellSmokeColor(rand);
            ColorParticleData colorData = ColorParticleData.create(color, color.darker()).setCoefficient(0.5f).build();
            WorldParticleBuilder.create(LodestoneParticleTypes.WISP_PARTICLE, LodestoneBehaviorComponent.DIRECTIONAL)
                    .setTransparencyData(GenericParticleData.create(0.6f, 0.4f, 0f).setEasing(Easing.SINE_IN, Easing.SINE_OUT).build())
                    .setSpinData(SpinParticleData.createRandomDirection(rand, 0.02f, 0.04f, 0).setEasing(Easing.SINE_IN, Easing.SINE_OUT).build())
                    .setScaleData(GenericParticleData.create(0f, 0.6f, 0.3f).setEasing(Easing.SINE_IN, Easing.SINE_OUT).build())
                    .setColorData(colorData)
                    .setLifetime(lifetime)
                    .addMotion(0, yMotion, 0)
                    .enableNoClip()
                    .setDiscardFunction(SimpleParticleOptions.ParticleDiscardFunctionType.ENDING_CURVE_INVISIBLE)
                    .setRenderType(LodestoneWorldParticleRenderType.LUMITRANSPARENT)
                    .setRenderTarget(RenderHandler.LATE_DELAYED_RENDER)
                    .surroundVoxelShape(level, blockPos, WELL_SHAPE, 12);
            if (rand.nextFloat() < 0.75f) {
                int rotation = rand.nextInt(16);
                Vec3 offsetPosition = VecHelper.rotatingRadialOffset(new Vec3(blockPos.getX() + 0.5f, blockPos.getY() + 0.75f, blockPos.getZ() + 0.5f), 1.1f, rotation, 16, level.getGameTime(), 640);
                final float acceleration = RandomHelper.randomBetween(rand, 0.002f, 0.02f);
                final long gameTime = level.getGameTime();
                final Consumer<LodestoneWorldParticle> behavior = p -> {
                    if (level.getGameTime() < gameTime + 10) {
                        p.setParticleSpeed(p.getParticleSpeed().add(0, acceleration, 0));
                    }
                };
                for (int i = 0; i < 2; i++) {
                    var lightSpecs = weepingWellSpecs(level, offsetPosition);
                    lightSpecs.getBuilder().addTickActor(behavior);
                    lightSpecs.getBuilder().setRenderTarget(RenderHandler.LATE_DELAYED_RENDER);
                    lightSpecs.getBloomBuilder().addTickActor(behavior);
                    lightSpecs.getBloomBuilder().setRenderTarget(RenderHandler.LATE_DELAYED_RENDER);
                    lightSpecs.spawnParticles();
                }
            }
        }
    }

    public static void passiveVoidDepotParticles(VoidDepotBlockEntity voidDepot) {
        Level level = voidDepot.getLevel();
        if (level.getGameTime() % 60L == 0) {
            final BlockPos blockPos = voidDepot.getBlockPos();
            var rand = level.random;
            int lifetime = RandomHelper.randomBetween(rand, 80, 120);
            float yMotion = 0.0005f;
            Color color = getWeepingWellSmokeColor(rand);
            ColorParticleData colorData = ColorParticleData.create(color, color.darker()).setCoefficient(0.5f).build();
            WorldParticleBuilder.create(LodestoneParticleTypes.WISP_PARTICLE, new DirectionalBehaviorComponent(new Vec3(0, 1, 0)))
                    .setTransparencyData(GenericParticleData.create(0.8f, 0.6f, 0f).setEasing(Easing.SINE_IN, Easing.SINE_OUT).build())
                    .setSpinData(SpinParticleData.createRandomDirection(rand, 0.02f, 0.04f, 0).setEasing(Easing.SINE_IN, Easing.SINE_OUT).build())
                    .setScaleData(GenericParticleData.create(0f, 0.2f, 0.05f).setEasing(Easing.SINE_IN, Easing.SINE_OUT).build())
                    .setColorData(colorData)
                    .setLifetime(lifetime)
                    .addMotion(0, yMotion, 0)
                    .enableNoClip()
                    .setDiscardFunction(SimpleParticleOptions.ParticleDiscardFunctionType.ENDING_CURVE_INVISIBLE)
                    .setRenderType(LodestoneWorldParticleRenderType.LUMITRANSPARENT)
                    .setRenderTarget(RenderHandler.LATE_DELAYED_RENDER)
                    .surroundVoxelShape(level, blockPos, DEPOT_SHAPE, 4);
            if (rand.nextFloat() < 0.2f) {
                int rotation = rand.nextInt(16);
                Vec3 offsetPosition = VecHelper.rotatingRadialOffset(new Vec3(blockPos.getX() + 0.5f, blockPos.getY() + 0.75f, blockPos.getZ() + 0.5f), 0.5f, rotation, 16, level.getGameTime(), 640);
                final float acceleration = RandomHelper.randomBetween(rand, 0.002f, 0.02f);
                final long gameTime = level.getGameTime();
                final Consumer<LodestoneWorldParticle> behavior = p -> {
                    if (level.getGameTime() < gameTime + 4) {
                        p.setParticleSpeed(p.getParticleSpeed().add(0, acceleration, 0));
                    }
                };
                for (int i = 0; i < 2; i++) {
                    var lightSpecs = weepingWellSpecs(level, offsetPosition);
                    lightSpecs.getBuilder().addTickActor(behavior);
                    lightSpecs.getBuilder().setRenderTarget(RenderHandler.LATE_DELAYED_RENDER);
                    lightSpecs.getBloomBuilder().addTickActor(behavior);
                    lightSpecs.getBloomBuilder().setRenderTarget(RenderHandler.LATE_DELAYED_RENDER);
                    lightSpecs.spawnParticles();
                }
            }
        }
    }

    public static ParticleEffectSpawner weepingWellSparks(Level level, Vec3 pos) {
        RandomSource rand = level.random;
        Color color = getWeepingWellSmokeColor(rand);
        ColorParticleData colorData = ColorParticleData.create(color, color.darker()).setCoefficient(0.5f).build();
        return weepingWellSparks(level, pos, colorData, LodestoneWorldParticleRenderType.LUMITRANSPARENT);
    }

    public static ParticleEffectSpawner weepingWellSparks(Level level, Vec3 pos, ColorParticleData colorData, LodestoneWorldParticleRenderType renderType) {
        RandomSource rand = level.random;
        var lightSpecs = SparkParticleEffects.spiritMotionSparks(level, pos, colorData);
        lightSpecs.getBuilder().act(b -> b
                .setRenderType(renderType)
                .multiplyLifetime(6f)
                .modifyOptionalData(b.getBehaviorData(SparkBehaviorComponent.class, SparkBehaviorComponent::getLengthData), d -> d.multiplyValue(RandomHelper.randomBetween(rand, 1.75f, 2.5f)))
                .modifyData(b::getTransparencyData, d -> d.multiplyValue(RandomHelper.randomBetween(rand, 0.75f, 1f)))
                .modifyData(b::getScaleData, d -> d.multiplyValue(RandomHelper.randomBetween(rand, 1.5f, 3.5f))));
        lightSpecs.getBloomBuilder().act(b -> b
                .setRenderType(renderType)
                .multiplyLifetime(6f)
                .setTransparencyData(GenericParticleData.create(0f, 0.75f, 0.25f).build())
                .setDiscardFunction(SimpleParticleOptions.ParticleDiscardFunctionType.ENDING_CURVE_INVISIBLE)
                .modifyData(b::getScaleData, d -> d.multiplyValue(RandomHelper.randomBetween(rand, 1f, 1.25f))));
        return lightSpecs;
    }

    public static ParticleEffectSpawner weepingWellSpecs(Level level, Vec3 pos) {
        var rand = level.random;
        Color color = getWeepingWellSmokeColor(rand);
        ColorParticleData colorData = ColorParticleData.create(color, color.darker()).setCoefficient(0.5f).build();
        return weepingWellSpecs(level, pos, colorData, LodestoneWorldParticleRenderType.LUMITRANSPARENT);
    }

    public static ParticleEffectSpawner weepingWellSpecs(Level level, Vec3 pos, ColorParticleData colorData, LodestoneWorldParticleRenderType renderType) {
        var rand = level.random;
        var lightSpecs = spiritLightSpecs(level, pos, colorData, new WorldParticleOptions(ParticleRegistry.LIGHT_SPEC_SMALL.get()));
        lightSpecs.getBuilder().act(b -> b
                .setRenderType(renderType)
                .multiplyLifetime(6f)
                .modifyData(b::getTransparencyData, d -> d.multiplyValue(RandomHelper.randomBetween(rand, 0.75f, 1f)))
                .modifyData(b::getScaleData, d -> d.multiplyValue(RandomHelper.randomBetween(rand, 1.5f, 3.5f))));
        lightSpecs.getBloomBuilder().act(b -> b
                .setRenderType(renderType)
                .multiplyLifetime(6f)
                .setTransparencyData(GenericParticleData.create(0f, 0.75f, 0.25f).build())
                .setDiscardFunction(SimpleParticleOptions.ParticleDiscardFunctionType.ENDING_CURVE_INVISIBLE)
                .modifyData(b::getScaleData, d -> d.multiplyValue(RandomHelper.randomBetween(rand, 1f, 1.25f))));
        return lightSpecs;
    }

    public static ParticleEffectSpawner weepingWellSquare(Level level, Vec3 pos, ColorParticleData colorData) {
        RandomSource rand = level.random;
        final GenericParticleData scaleData = GenericParticleData.create(0.1f, RandomHelper.randomBetween(rand, 1.7f, 1.8f), 0.5f).setEasing(Easing.SINE_OUT, Easing.SINE_IN).setCoefficient(RandomHelper.randomBetween(rand, 1f, 1.25f)).build();
        final Consumer<LodestoneWorldParticle> behavior = p -> p.setParticleSpeed(p.getParticleSpeed().scale(0.95f));
        float yMotion = RandomHelper.randomBetween(rand, 0.04f, 0.06f);
        Vec3 motion = new Vec3(0f, yMotion, 0f);
        var squares = WorldParticleBuilder.create(ParticleRegistry.SQUARE.get(), LodestoneBehaviorComponent.DIRECTIONAL)
                .setTransparencyData(GenericParticleData.create(0.9f, 0.05f, 0f).setEasing(Easing.CUBIC_OUT, Easing.EXPO_IN).build())
                .setScaleData(scaleData)
                .setColorData(colorData)
                .setLifetime(100)
                .setMotion(motion)
                .enableNoClip()
                .setRenderTarget(RenderHandler.LATE_DELAYED_RENDER)
                .setSpritePicker(SimpleParticleOptions.ParticleSpritePicker.RANDOM_SPRITE)
                .addTickActor(behavior);
        Consumer<WorldParticleBuilder> squareSpawner = b -> b
                .spawn(level, pos.x, pos.y, pos.z)
                .setTransparencyData(GenericParticleData.create(0.1f, 0.6f, 0f).setEasing(Easing.CUBIC_OUT, Easing.EXPO_OUT).build())
                .setRenderType(LodestoneWorldParticleRenderType.LUMITRANSPARENT)
                .spawn(level, pos.x, pos.y, pos.z);

        return new ParticleEffectSpawner(squares, squareSpawner);
    }
}