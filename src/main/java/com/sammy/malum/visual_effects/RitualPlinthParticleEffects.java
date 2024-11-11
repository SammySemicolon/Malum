package com.sammy.malum.visual_effects;

import com.sammy.malum.common.block.curiosities.ritual_plinth.*;
import com.sammy.malum.core.systems.spirit.*;
import com.sammy.malum.registry.client.*;
import com.sammy.malum.visual_effects.networked.data.*;
import net.minecraft.core.*;
import net.minecraft.util.*;
import net.minecraft.world.item.*;
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
import team.lodestar.lodestone.systems.particle.world.behaviors.components.*;
import team.lodestar.lodestone.systems.particle.world.options.*;

import java.awt.*;
import java.util.function.*;

import static com.sammy.malum.visual_effects.SpiritLightSpecs.*;
import static net.minecraft.util.Mth.*;

public class RitualPlinthParticleEffects {

    public static void failRitualParticles(RitualPlinthBlockEntity plinth, ColorEffectData colorData) {
        MalumSpiritType spiritType = colorData.getSpiritType();
        Level level = plinth.getLevel();
        var random = level.random;
        Vec3 ritualIconPos = plinth.getRitualIconPos();

        for (int i = 0; i < 6; i++) {
            float scale = 2f + i * (i < 2 ? 6f : 1f) * Math.min(plinth.activeDuration, 30f) / 30f;
            var lightSpecs = spiritLightSpecs(level, ritualIconPos, spiritType, new WorldParticleOptions(i < 2 ? ParticleRegistry.STAR : LodestoneParticleTypes.TWINKLE_PARTICLE));
            lightSpecs.getBuilder()
                    .setSpinData(SpinParticleData.create(0.4f, 0).setSpinOffset((level.getGameTime() * 0.05f) % 6.28f).setEasing(Easing.CUBIC_IN).build())
                    .setScaleData(GenericParticleData.create(RandomHelper.randomBetween(random, 0.2f, 0.3f) * scale, 0.1f * scale, 0).setEasing(Easing.QUAD_IN, Easing.SINE_IN).build())
                    .modifyData(AbstractParticleBuilder::getTransparencyData, d -> d.multiplyValue(0.75f));
            lightSpecs.getBloomBuilder()
                    .modifyData(AbstractParticleBuilder::getScaleData, d -> d.multiplyValue(scale))
                    .modifyData(AbstractParticleBuilder::getTransparencyData, d -> d.multiplyValue(0.75f));
            lightSpecs.spawnParticles();
        }
        int spinOffset = random.nextInt(360);
        for (int i = 0; i < 4; i++) {
            int spinDirection = (random.nextBoolean() ? 1 : -1);
            float scaleMultiplier = (float) (1+Math.pow(random.nextFloat(), 2));
            WorldParticleBuilder.create(LodestoneParticleTypes.SPARKLE_PARTICLE)
                    .setTransparencyData(GenericParticleData.create(0.4f, 0.5f, 0).setEasing(Easing.SINE_IN, Easing.CIRC_IN).build())
                    .setSpinData(SpinParticleData.create((0.125f + random.nextFloat() * 0.075f) * spinDirection).setSpinOffset(spinOffset).build())
                    .setScaleData(GenericParticleData.create(1.2f*scaleMultiplier, 0.4f, 0).setEasing(Easing.QUAD_IN, Easing.SINE_IN).build())
                    .setColorData(spiritType.createColorData().build())
                    .setLifetime(40)
                    .setRandomOffset(0.6f)
                    .enableNoClip()
                    .setDiscardFunction(SimpleParticleOptions.ParticleDiscardFunctionType.ENDING_CURVE_INVISIBLE)
                    .setRenderType(LodestoneWorldParticleRenderType.LUMITRANSPARENT)
                    .repeat(level, ritualIconPos.x, ritualIconPos.y, ritualIconPos.z, 3);
        }
        for (int i = 0; i < 64; i++) {
            float lifetimeMultiplier = RandomHelper.randomBetween(random, 2f, 2.5f);
            float gravityStrength = RandomHelper.randomBetween(random, 0.01f, 0.05f);
            double horizontalAngle = random.nextDouble() * Math.PI * 2;
            double x = (Math.cos(horizontalAngle));
            double y = Mth.nextFloat(random, -1, 1.5f);
            double z = (Math.sin(horizontalAngle));
            Vec3 direction = new Vec3(x, y, z);
            Vec3 motion = direction.scale(RandomHelper.randomBetween(random, 0.25f, 0.5f));
            Vec3 spawnPosition = ritualIconPos.add(direction.scale(0.25f));
            final Consumer<LodestoneWorldParticle> slowDown = p -> {
                Vec3 velocity = p.getParticleSpeed().scale(0.95f);
                if (velocity.equals(Vec3.ZERO)) {
                    velocity = p.getParticleSpeed();
                }
                p.setParticleSpeed(velocity.x, (velocity.y-gravityStrength)*0.98f, velocity.z);
            };
            boolean star = random.nextFloat() < 0.2f;
            if (random.nextFloat() < 0.8f) {
                final WorldParticleOptions options = new WorldParticleOptions((star ? ParticleRegistry.STAR : ParticleRegistry.LIGHT_SPEC_SMALL));
                var lightSpecs = spiritLightSpecs(level, spawnPosition, spiritType, options);
                lightSpecs.getBuilder()
                        .multiplyLifetime(lifetimeMultiplier)
                        .enableForcedSpawn()
                        .addTickActor(slowDown)
                        .modifyData(AbstractParticleBuilder::getScaleData, d -> d.multiplyValue(1.2f))
                        .setMotion(motion);
                lightSpecs.getBloomBuilder()
                        .multiplyLifetime(lifetimeMultiplier)
                        .addTickActor(slowDown)
                        .modifyData(AbstractParticleBuilder::getScaleData, d -> d.multiplyValue(star ? 2.4f : 1.2f))
                        .setMotion(motion);
                lightSpecs.spawnParticles();
            }
            if (!star && random.nextFloat() < 0.8f) {
                float scalar = RandomHelper.randomBetween(random, 0.8f, 1.1f);
                var lengthData = GenericParticleData.create(2f * scalar, 0.5f * scalar, 0f).setEasing(Easing.QUARTIC_OUT, Easing.SINE_IN_OUT).build();
                var sparks = SparkParticleEffects.spiritMotionSparks(level, spawnPosition, spiritType).act(b -> b.getParticleOptions().setBehavior(new SparkBehaviorComponent(lengthData)));
                sparks.getBuilder()
                        .multiplyLifetime(lifetimeMultiplier)
                        .enableForcedSpawn()
                        .addTickActor(slowDown)
                        .setMotion(motion)
                        .setScaleData(GenericParticleData.create(0.4f*scalar, 0.2f*scalar, 0f).setEasing(Easing.SINE_IN, Easing.QUAD_IN).build());
                sparks.getBloomBuilder()
                        .multiplyLifetime(lifetimeMultiplier)
                        .addTickActor(slowDown)
                        .modifyData(AbstractParticleBuilder::getScaleData, d -> d.multiplyValue(2f))
                        .setMotion(motion);
                sparks.spawnParticles();
            }
        }
    }

    public static void incrementRitualTierParticles(RitualPlinthBlockEntity plinth, ColorEffectData colorData) {
        MalumSpiritType spiritType = colorData.getSpiritType();
        Level level = plinth.getLevel();
        var random = level.random;
        Vec3 ritualIconPos = plinth.getRitualIconPos();

        for (int i = 0; i < 6; i++) {
            float scale = 2f + i * (i < 2 ? 6f : 1.25f);
            final WorldParticleOptions options = new WorldParticleOptions(i < 2 ? ParticleRegistry.STAR : ParticleRegistry.LIGHT_SPEC_SMALL);
            var lightSpecs = spiritLightSpecs(level, ritualIconPos, spiritType, options);
            lightSpecs.getBuilder()
                    .setSpinData(SpinParticleData.create(0.4f, 0).setSpinOffset((level.getGameTime() * 0.05f) % 6.28f).setEasing(Easing.CUBIC_IN).build())
                    .setScaleData(GenericParticleData.create(RandomHelper.randomBetween(random, 0.2f, 0.3f), 0.1f, 0).setEasing(Easing.QUAD_IN, Easing.SINE_IN).build())
                    .modifyData(AbstractParticleBuilder::getScaleData, d -> d.multiplyValue(scale))
                    .modifyData(AbstractParticleBuilder::getTransparencyData, d -> d.multiplyValue(0.75f));
            lightSpecs.getBloomBuilder()
                    .modifyData(AbstractParticleBuilder::getScaleData, d -> d.multiplyValue(scale))
                    .modifyData(AbstractParticleBuilder::getTransparencyData, d -> d.multiplyValue(0.75f));
            lightSpecs.spawnParticles();
        }
        int spinOffset = random.nextInt(360);
        for (int i = 0; i < 4; i++) {
            int spinDirection = (random.nextBoolean() ? 1 : -1);
            float scaleMultiplier = (float) (1+Math.pow(random.nextFloat(), 2));
            WorldParticleBuilder.create(LodestoneParticleTypes.SPARKLE_PARTICLE)
                    .setTransparencyData(GenericParticleData.create(0.4f, 0.5f, 0).setEasing(Easing.SINE_IN, Easing.CIRC_IN).build())
                    .setSpinData(SpinParticleData.create((0.125f + random.nextFloat() * 0.075f) * spinDirection).setSpinOffset(spinOffset).build())
                    .setScaleData(GenericParticleData.create(1.2f*scaleMultiplier, 0.4f, 0).setEasing(Easing.QUAD_IN, Easing.SINE_IN).build())
                    .setColorData(spiritType.createColorData().build())
                    .setLifetime(40)
                    .setRandomOffset(0.6f)
                    .enableNoClip()
                    .setDiscardFunction(SimpleParticleOptions.ParticleDiscardFunctionType.ENDING_CURVE_INVISIBLE)
                    .setRenderType(LodestoneWorldParticleRenderType.LUMITRANSPARENT)
                    .repeat(level, ritualIconPos.x, ritualIconPos.y, ritualIconPos.z, 3);
        }
        for (int i = 0; i < 48; i++) {
            float angle = i / 32f * (float) Math.PI * 2f;
            float speed = RandomHelper.randomBetween(random, 0.05f, 0.15f);
            float distance = RandomHelper.randomBetween(random, 1.5f, 2.5f);
            double x = Math.sin(angle);
            double y = random.nextFloat() * (random.nextBoolean() ? 1 : -1);
            double z = Math.cos(angle);
            Vec3 offset = new Vec3(x, y, z).normalize();
            Vec3 particlePosition  = ritualIconPos.add(offset.scale(distance));
            Vec3 particleMotion = offset.scale(-speed);
            float gravityStrength = 0f;
            final GenericParticleData transparencyData = GenericParticleData.create(0.1f, 0.9f, 0f).setEasing(Easing.SINE_IN_OUT, Easing.SINE_IN).build();
            if (random.nextFloat() < 0.85f) {
                var sparkParticles = SparkParticleEffects.spiritMotionSparks(level, particlePosition, spiritType);
                sparkParticles.getBuilder()
                        .setTransparencyData(transparencyData)
                        .disableNoClip()
                        .multiplyLifetime(2)
                        .setGravityStrength(gravityStrength)
                        .setMotion(particleMotion)
                        .modifyData(AbstractParticleBuilder::getScaleData, d -> d.multiplyValue(2f));
                sparkParticles.getBloomBuilder()
                        .setTransparencyData(transparencyData)
                        .disableNoClip()
                        .multiplyLifetime(2)
                        .setGravityStrength(gravityStrength)
                        .setMotion(particleMotion)
                        .modifyData(AbstractParticleBuilder::getTransparencyData, d -> d.multiplyValue(1.25f));
                sparkParticles.spawnParticles();
            }
            if (random.nextFloat() < 0.85f) {
                var lightSpecs = SpiritLightSpecs.spiritLightSpecs(level, particlePosition, spiritType);
                lightSpecs.getBuilder()
                        .disableNoClip()
                        .multiplyLifetime(4)
                        .setGravityStrength(gravityStrength)
                        .setMotion(particleMotion)
                        .modifyData(AbstractParticleBuilder::getScaleData, d -> d.multiplyValue(2.5f));
                lightSpecs.getBloomBuilder()
                        .disableNoClip()
                        .multiplyLifetime(4)
                        .setGravityStrength(gravityStrength)
                        .setMotion(particleMotion)
                        .modifyData(AbstractParticleBuilder::getTransparencyData, d -> d.multiplyValue(1.25f));
                lightSpecs.spawnParticles();
            }
        }
    }

    public static void beginChargingParticles(RitualPlinthBlockEntity plinth, ColorEffectData colorData) {
        MalumSpiritType spiritType = colorData.getSpiritType();
        Level level = plinth.getLevel();
        long gameTime = level.getGameTime();
        var random = level.random;
        Vec3 plinthItemPos = plinth.getItemPos();
        Vec3 ritualIconPos = plinth.getRitualIconPos().add(0f, 0.3125f, 0f);

        for (int i = 0; i < 4; i++) {
            SpiritLightSpecs.coolLookingShinyThing(level, plinthItemPos, spiritType);
            float scale = 1f + i * (i < 2 ? 3f : 0.75f);
            var lightSpecs = spiritLightSpecs(level, plinthItemPos, spiritType, new WorldParticleOptions(i < 2 ? ParticleRegistry.STAR : LodestoneParticleTypes.TWINKLE_PARTICLE));
            lightSpecs.getBuilder()
                    .setSpinData(SpinParticleData.create(0.2f, 0).setSpinOffset((level.getGameTime() * 0.05f) % 6.28f).setEasing(Easing.CUBIC_IN).build())
                    .modifyData(AbstractParticleBuilder::getScaleData, d -> d.multiplyValue(scale))
                    .modifyData(AbstractParticleBuilder::getTransparencyData, d -> d.multiplyValue(0.5f))
                    .multiplyLifetime(1.5f);
            lightSpecs.getBloomBuilder()
                    .modifyData(AbstractParticleBuilder::getScaleData, d -> d.multiplyValue(scale))
                    .modifyData(AbstractParticleBuilder::getTransparencyData, d -> d.multiplyValue(0.75f))
                    .multiplyLifetime(1.5f);
            lightSpecs.spawnParticles();
        }
        for (int i = 0; i < 6; i++) {
            float scale = 2f + i * (i < 2 ? 4f : 1f);
            Vec3 motion = new Vec3(0, 0.15f, 0);
            var lightSpecs = spiritLightSpecs(level, ritualIconPos, spiritType, new WorldParticleOptions(i < 2 ? ParticleRegistry.STAR : LodestoneParticleTypes.TWINKLE_PARTICLE));
            lightSpecs.getBuilder()
                    .setSpinData(SpinParticleData.create(0.2f, 0).setSpinOffset((level.getGameTime() * 0.05f) % 6.28f).setEasing(Easing.CUBIC_IN).build())
                    .modifyData(AbstractParticleBuilder::getScaleData, d -> d.multiplyValue(scale))
                    .modifyData(AbstractParticleBuilder::getTransparencyData, d -> d.multiplyValue(0.5f))
                    .multiplyLifetime(1.5f)
                    .setMotion(motion);
            lightSpecs.getBloomBuilder()
                    .modifyData(AbstractParticleBuilder::getScaleData, d -> d.multiplyValue(scale))
                    .modifyData(AbstractParticleBuilder::getTransparencyData, d -> d.multiplyValue(0.75f))
                    .multiplyLifetime(1.5f)
                    .setMotion(motion);
            lightSpecs.spawnParticles();
        }
        for (int i = 0; i < 128; i++) {
            int finalI = i;
            Vec3 particlePosition = VecHelper.rotatingRadialOffset(plinthItemPos, 0.75f, i, 64, gameTime, 160);
            Consumer<WorldParticleBuilder> behavior = b -> b.addTickActor(p -> {
                if (level.getGameTime() < gameTime + (finalI + 2)/2f) {
                    p.setParticleSpeed(0, 0.03f*(finalI/128f), 0);
                }
            });
            Consumer<WorldParticleBuilder> behaviorSpark = b -> b.addTickActor(p -> {
                if (level.getGameTime() < gameTime + (finalI + 2)/2f) {
                    p.setParticleSpeed(0, 0.03f*(finalI/128f), 0);
                }
                p.setParticleSpeed(p.getParticleSpeed().scale(0.95f));
            });

            WorldParticleBuilder.create(ParticleRegistry.SHARP_SPARK.get(), new DirectionalBehaviorComponent(plinthItemPos.subtract(particlePosition).normalize()))
                    .setTransparencyData(GenericParticleData.create(0f, 0.9f, 0f).setEasing(Easing.SINE_IN_OUT, Easing.SINE_IN).build())
                    .setSpinData(SpinParticleData.createRandomDirection(random, nextFloat(random, 0.025f, 0.05f)).randomSpinOffset(random).build())
                    .setScaleData(GenericParticleData.create(0.3f, 0).setEasing(Easing.SINE_IN_OUT).build())
                    .setColorData(spiritType.createColorData().build().multiplyCoefficient(1.5f))
                    .setLifetime(160+finalI*8)
                    .setMotion(new Vec3(0, 0.005f, 0))
                    .enableNoClip()
                    .setDiscardFunction(SimpleParticleOptions.ParticleDiscardFunctionType.ENDING_CURVE_INVISIBLE)
                    .act(behaviorSpark)
                    .spawn(level, particlePosition.x, particlePosition.y, particlePosition.z);
            var lightSpecs = spiritLightSpecs(level, particlePosition, spiritType);
            lightSpecs.getBuilder().act(b -> b
                    .act(behavior)
                    .modifyColorData(d -> d.multiplyCoefficient(0.35f))
                    .modifyData(b::getScaleData, d -> d.multiplyValue(2f).multiplyCoefficient(0.9f))
                    .modifyData(b::getTransparencyData, d -> d.multiplyCoefficient(0.9f))
                    .multiplyLifetime(5f)
                    .setLifetime(b.getParticleOptions().lifetimeSupplier.get() + finalI*4));
            lightSpecs.getBloomBuilder().act(b -> b
                    .act(behavior)
                    .modifyColorData(d -> d.multiplyCoefficient(0.35f))
                    .modifyData(b::getScaleData, d -> d.multiplyValue(1.6f).multiplyCoefficient(0.9f))
                    .modifyData(b::getTransparencyData, d -> d.multiplyCoefficient(0.9f))
                    .setLifetime(b.getParticleOptions().lifetimeSupplier.get() + finalI*4));
            lightSpecs.spawnParticles();
        }
        for (int i = 0; i < 48; i++) {
            int lifeDelay = i / 8;
            float xVelocity = RandomHelper.randomBetween(random, Easing.CUBIC_OUT, -0.075f, 0.075f);
            float yVelocity = RandomHelper.randomBetween(random, 0.1f, 0.2f);
            float zVelocity = RandomHelper.randomBetween(random, Easing.CUBIC_OUT, -0.075f, 0.075f);
            float gravityStrength = 0f;
            if (random.nextFloat() < 0.85f) {
                var sparkParticles = SparkParticleEffects.spiritMotionSparks(level, ritualIconPos, spiritType);
                sparkParticles.getBuilder()
                        .disableNoClip()
                        .setLifeDelay(lifeDelay)
                        .multiplyLifetime(2)
                        .setGravityStrength(gravityStrength)
                        .setMotion(xVelocity, yVelocity, zVelocity)
                        .modifyData(AbstractParticleBuilder::getScaleData, d -> d.multiplyValue(2f));
                sparkParticles.getBloomBuilder()
                        .disableNoClip()
                        .setLifeDelay(lifeDelay)
                        .multiplyLifetime(2)
                        .setGravityStrength(gravityStrength)
                        .setMotion(xVelocity, yVelocity, zVelocity)
                        .modifyData(AbstractParticleBuilder::getTransparencyData, d -> d.multiplyValue(1.25f));
                sparkParticles.spawnParticles();
            }
            if (random.nextFloat() < 0.85f) {
                xVelocity *= 1.25f;
                yVelocity *= 0.75f;
                zVelocity *= 1.25f;
                var lightSpecs = SpiritLightSpecs.spiritLightSpecs(level, ritualIconPos, spiritType);
                lightSpecs.getBuilder()
                        .disableNoClip()
                        .setLifeDelay(lifeDelay)
                        .multiplyLifetime(4)
                        .setGravityStrength(gravityStrength)
                        .setMotion(xVelocity, yVelocity, zVelocity)
                        .modifyData(AbstractParticleBuilder::getScaleData, d -> d.multiplyValue(2.5f));
                lightSpecs.getBloomBuilder()
                        .disableNoClip()
                        .setLifeDelay(lifeDelay)
                        .multiplyLifetime(4)
                        .setGravityStrength(gravityStrength)
                        .setMotion(xVelocity, yVelocity, zVelocity)
                        .modifyData(AbstractParticleBuilder::getTransparencyData, d -> d.multiplyValue(1.25f));
                lightSpecs.spawnParticles();
            }
        }
    }

    public static void eatItemParticles(RitualPlinthBlockEntity plinth, Vec3 targetPos, ColorEffectData colorData, ItemStack stack) {
        MalumSpiritType spiritType = colorData.getSpiritType();
        Level level = plinth.getLevel();
        long gameTime = level.getGameTime();
        var random = level.random;
        Vec3 plinthItemPos = plinth.getItemPos();
        for (int i = 0; i < 2; i++) {
            SpiritLightSpecs.coolLookingShinyThing(level, targetPos, spiritType);
            SpiritLightSpecs.coolLookingShinyThing(level, plinthItemPos, spiritType);
        }
        for (int i = 0; i < 16; i++) {
            Vec3 velocity = plinthItemPos.subtract(targetPos).normalize().scale(0.025f);
            int finalI = i;
            Vec3 offsetPosition = VecHelper.rotatingRadialOffset(targetPos, 0.5f, i, 16, gameTime, 160);
            final Consumer<LodestoneWorldParticle> behavior = p -> {
                if (level.getGameTime() > gameTime + finalI * 2 && level.getGameTime() < gameTime + (finalI + 4) * 2) {
                    p.setParticleSpeed(p.getParticleSpeed().add(velocity));
                }
            };
            var lightSpecs = spiritLightSpecs(level, offsetPosition, spiritType);
            lightSpecs.getBuilder().act(b -> b
                    .addTickActor(behavior)
                    .multiplyLifetime(2.5f)
                    .modifyData(b::getScaleData, d -> d.multiplyValue(RandomHelper.randomBetween(random, 1f, 2f))));
            lightSpecs.getBloomBuilder().act(b -> b
                    .addTickActor(behavior)
                    .multiplyLifetime(2f)
                    .modifyData(b::getScaleData, d -> d.multiplyValue(RandomHelper.randomBetween(random, 0.6f, 1.5f))));
            lightSpecs.spawnParticles();

            var crumbles = ItemCrumbleParticleEffects.spawnItemCrumbs(level, targetPos, stack);
            crumbles.getBuilder().setLifeDelay(i).addTickActor(behavior);
            crumbles.spawnParticles();
            crumbles.getBuilder().setRandomOffset(0.2f).getScaleData();
            crumbles.spawnParticles();
        }
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 4; j++) {
                final Vec3 particlePosition = VecHelper.rotatingRadialOffset(targetPos, 0.5f, j, 4, level.getGameTime()+i*4, 160);
                WorldParticleBuilder.create(ParticleRegistry.SHARP_SPARK, new DirectionalBehaviorComponent(targetPos.subtract(particlePosition).normalize()))
                        .setTransparencyData(GenericParticleData.create(0f, 0.6f, 0f).setEasing(Easing.SINE_IN_OUT, Easing.SINE_IN).build())
                        .setSpinData(SpinParticleData.createRandomDirection(random, nextFloat(random, 0.05f, 0.1f)).randomSpinOffset(random).build())
                        .setScaleData(GenericParticleData.create(0.125f, 0.075f, 0).setEasing(Easing.SINE_IN_OUT, Easing.SINE_IN).build())
                        .setColorData(spiritType.createColorData().build().multiplyCoefficient(1.5f))
                        .setLifetime(60)
                        .setLifeDelay(i * 2)
                        .setMotion(new Vec3(0, 0.005f, 0))
                        .enableNoClip()
                        .setDiscardFunction(SimpleParticleOptions.ParticleDiscardFunctionType.ENDING_CURVE_INVISIBLE)
                        .spawn(level, particlePosition.x, particlePosition.y - 0.125f, particlePosition.z);
            }
        }
    }

    public static void eatSpiritParticles(RitualPlinthBlockEntity plinth, Vec3 targetPos, ColorEffectData colorData, ItemStack stack) {
        MalumSpiritType spiritType = colorData.getSpiritType();
        Level level = plinth.getLevel();
        long gameTime = level.getGameTime();
        var random = level.random;
        Vec3 plinthItemPos = plinth.getItemPos();
        for (int i = 0; i < 2; i++) {
            SpiritLightSpecs.coolLookingShinyThing(level, targetPos, spiritType);
            SpiritLightSpecs.coolLookingShinyThing(level, plinthItemPos, spiritType);
        }
    }

    public static void holdingPrimeItemPlinthParticles(RitualPlinthBlockEntity plinth) {
        Level level = plinth.getLevel();
        MalumSpiritType spiritType = plinth.ritualType != null ? plinth.ritualType.spirit : plinth.ritualRecipe.ritualType.spirit;
        RandomSource random = level.random;
        Vec3 itemPos = plinth.getItemPos();
        SpiritLightSpecs.rotatingLightSpecs(level, itemPos, spiritType, 0.5f, 3, b -> b.multiplyLifetime(1.2f).modifyData(b::getScaleData, d -> d.multiplyValue(1.2f)));
        if (level.getGameTime() % 3L == 0) {
            for (int i = 0; i < 8; i++) {
                final Vec3 particlePosition = VecHelper.rotatingRadialOffset(itemPos, 0.5f, i, 8, level.getGameTime(), 320);
                WorldParticleBuilder.create(ParticleRegistry.SHARP_SPARK, new DirectionalBehaviorComponent(itemPos.subtract(particlePosition).normalize()))
                        .setTransparencyData(GenericParticleData.create(0f, 0.4f, 0f).setEasing(Easing.SINE_IN_OUT, Easing.SINE_IN).build())
                        .setSpinData(SpinParticleData.createRandomDirection(random, nextFloat(random, 0.05f, 0.1f)).randomSpinOffset(random).build())
                        .setScaleData(GenericParticleData.create(0.125f, 0.075f, 0).setEasing(Easing.SINE_IN_OUT, Easing.SINE_IN).build())
                        .setColorData(spiritType.createColorData().build().multiplyCoefficient(1.5f))
                        .setLifetime(40)
                        .setMotion(new Vec3(0, 0.005f, 0))
                        .enableNoClip()
                        .setDiscardFunction(SimpleParticleOptions.ParticleDiscardFunctionType.ENDING_CURVE_INVISIBLE)
                        .spawn(level, particlePosition.x, particlePosition.y-0.125f, particlePosition.z);
            }
        }
    }

    public static void riteActivePlinthParticles(RitualPlinthBlockEntity plinth) {
        Level level = plinth.getLevel();
        MalumSpiritType spiritType = plinth.type.spirit;
        RandomSource random = level.random;

        Vec3 ritualIconPos = plinth.getRitualIconPos();
        final long gameTime = level.getGameTime();
        if (gameTime % 24L == 0) {
            float scale = 0.8f + 0.08f * (plinth.ritualTier != null ? plinth.ritualTier.potency : 0);
            WorldParticleBuilder.create(random.nextBoolean() ? LodestoneParticleTypes.TWINKLE_PARTICLE : LodestoneParticleTypes.STAR_PARTICLE)
                    .setTransparencyData(GenericParticleData.create(0f, 0.6f, 0).setEasing(Easing.SINE_IN, Easing.CIRC_IN).build())
                    .setSpinData(SpinParticleData.createRandomDirection(random, RandomHelper.randomBetween(random, 0.025f, 0.05f)).randomSpinOffset(random).build())
                    .setScaleData(GenericParticleData.create(0.4f, scale, 0).setEasing(Easing.SINE_IN_OUT, Easing.SINE_IN).build())
                    .setColorData(random.nextBoolean() ? spiritType.createColorData().build().multiplyCoefficient(0.5f) : spiritType.createColorData().build())
                    .setLifetime(120)
                    .setRandomOffset(0.1f)
                    .enableNoClip()
                    .setDiscardFunction(SimpleParticleOptions.ParticleDiscardFunctionType.ENDING_CURVE_INVISIBLE)
                    .setRenderType(LodestoneWorldParticleRenderType.LUMITRANSPARENT)
                    .repeat(level, ritualIconPos.x, ritualIconPos.y, ritualIconPos.z, 3);
        }

        for (Direction direction : Direction.values()) {
            if (direction.getAxis().equals(Direction.Axis.Y)) {
                continue;
            }
            Vec3 particlePosition = plinth.getParticlePositionPosition(direction);
            Vec3 particleVelocity = new Vec3(0.03f * direction.getStepX(), 0, 0.03f * direction.getStepZ());
            if (gameTime % 6L == 0) {
                Vec3 randomizedVelocity = particleVelocity.scale(RandomHelper.randomBetween(random, 0.8f, 1.2f));
                var lightSpecs = spiritLightSpecs(level, particlePosition, spiritType);
                lightSpecs.getBuilder().multiplyLifetime(1.75f).setMotion(randomizedVelocity);
                lightSpecs.getBloomBuilder().multiplyLifetime(1.5f).setMotion(randomizedVelocity);
                lightSpecs.spawnParticles();
            }
            if (gameTime % 6L == 0) {
                Vec3 randomizedVelocity = particleVelocity.scale(RandomHelper.randomBetween(random, 0.8f, 1.2f));
                Vec3 sparkPos = particlePosition.add(0.05f - random.nextFloat() * 0.1f, 0.05f - random.nextFloat() * 0.1f, 0.05f - random.nextFloat() * 0.1f);
                var sparkParticles = SparkParticleEffects.spiritMotionSparks(level, sparkPos, spiritType);
                sparkParticles.getBuilder().setMotion(randomizedVelocity)
                        .modifyData(AbstractParticleBuilder::getScaleData, d -> d.multiplyValue(1.25f))
                        .modifyOptionalData(b -> b.getBehaviorData(SparkBehaviorComponent.class, SparkBehaviorComponent::getLengthData), d -> d.multiplyValue(1.75f).multiplyCoefficient(0.75f))
                        .modifyColorData(c -> c.multiplyCoefficient(0.5f));
                sparkParticles.getBloomBuilder().setMotion(randomizedVelocity);
                sparkParticles.spawnParticlesRaw();
            }
            if (gameTime % 12L == 0) {
                WorldParticleBuilder.create(gameTime % 24L == 0 ? ParticleRegistry.CIRCLE : ParticleRegistry.SQUARE, new DirectionalBehaviorComponent(particleVelocity.normalize()))
                        .setTransparencyData(GenericParticleData.create(0.8f, 1f, 0f).setEasing(Easing.SINE_IN_OUT, Easing.SINE_IN).build())
                        .setSpinData(SpinParticleData.createRandomDirection(random, nextFloat(random, 0.05f, 0.1f)).randomSpinOffset(random).build())
                        .setScaleData(GenericParticleData.create(0f, 0.2f).setEasing(Easing.SINE_IN).build())
                        .setColorData(spiritType.createColorData().setCoefficient(0.75f).build())
                        .setLifetime(80)
                        .enableNoClip()
                        .setDiscardFunction(SimpleParticleOptions.ParticleDiscardFunctionType.ENDING_CURVE_INVISIBLE)
                        .spawn(level, particlePosition.x, particlePosition.y, particlePosition.z);
            }
            Vec3 normalizedParticleVelocity = particleVelocity.normalize();
            float yRot = ((float) (Mth.atan2(normalizedParticleVelocity.x, normalizedParticleVelocity.z) * (double) (180F / (float) Math.PI)));
            float yaw = (float) Math.toRadians(yRot);
            Vec3 left = new Vec3(-Math.cos(yaw), 0, Math.sin(yaw));
            Vec3 up = left.cross(normalizedParticleVelocity);
            final Consumer<LodestoneWorldParticle> behavior = p -> p.setParticleSpeed(p.getParticleSpeed().scale(0.98f));
            float angle = ((gameTime % 40) / 40f) * (float) Math.PI * 2f;
            Vec3 randomizedVelocity = particleVelocity.scale(RandomHelper.randomBetween(random, 0.8f, 1.2f));
            Vec3 particleDirection = particleVelocity
                    .add(left.scale(Math.sin(angle)))
                    .add(up.scale(Math.cos(angle)))
                    .normalize();
            particlePosition = particlePosition.add(particleDirection.scale(0.075f));
            WorldParticleBuilder.create(ParticleRegistry.SHARP_SPARK, new DirectionalBehaviorComponent(particleDirection))
                    .setTransparencyData(GenericParticleData.create(0.6f, 0.4f, 0f).setEasing(Easing.SINE_IN_OUT, Easing.SINE_IN).build())
                    .setSpinData(SpinParticleData.createRandomDirection(random, nextFloat(random, 0.05f, 0.1f)).randomSpinOffset(random).build())
                    .setScaleData(GenericParticleData.create(0.125f, 0.075f, 0).setEasing(Easing.SINE_IN_OUT, Easing.SINE_IN).build())
                    .setColorData(spiritType.createColorData().build().multiplyCoefficient(1.5f))
                    .setLifetime(30)
                    .setMotion(randomizedVelocity)
                    .enableNoClip()
                    .addTickActor(behavior)
                    .spawn(level, particlePosition.x, particlePosition.y, particlePosition.z);
        }
        if (gameTime % 16L == 0) {
            Vec3 particlePosition = plinth.getBlockPos().getCenter();
            for (int i = 0; i < 2; i++) {
                final GenericParticleData transparencyData = GenericParticleData.create(0f, 0.5f, 0f).setEasing(Easing.SINE_IN_OUT, Easing.SINE_IN).build();
                final GenericParticleData scaleData = GenericParticleData.create(0.35f).setEasing(Easing.SINE_IN).build();
                final SpinParticleData spinData = SpinParticleData.create(0.0157f).setSpinOffset((i == 1 ? 0 : 0.785f) + 2.512f * (gameTime % 800) / 160f).build();
                final Color color = i == 0 ? spiritType.getPrimaryColor() : spiritType.getSecondaryColor();
                final ColorParticleData colorData = ColorParticleData.create(color, color).build();
                WorldParticleBuilder.create(ParticleRegistry.RITUAL_CIRCLE, new DirectionalBehaviorComponent(new Vec3(0, 1, 0)))
                        .setTransparencyData(transparencyData)
                        .setSpinData(spinData)
                        .setScaleData(scaleData)
                        .setColorData(colorData)
                        .setLifetime(40)
                        .enableNoClip()
                        .setDiscardFunction(SimpleParticleOptions.ParticleDiscardFunctionType.ENDING_CURVE_INVISIBLE)
                        .spawn(level, particlePosition.x, particlePosition.y + 0.6875f, particlePosition.z);
                WorldParticleBuilder.create(ParticleRegistry.RITUAL_CIRCLE_WISP, new DirectionalBehaviorComponent(new Vec3(0, 1, 0)))
                        .setTransparencyData(transparencyData.multiplyValue(0.25f))
                        .setSpinData(spinData)
                        .setScaleData(scaleData.multiplyValue(1.25f))
                        .setColorData(colorData)
                        .setLifetime(40)
                        .enableNoClip()
                        .setDiscardFunction(SimpleParticleOptions.ParticleDiscardFunctionType.ENDING_CURVE_INVISIBLE)
                        .spawn(level, particlePosition.x, particlePosition.y + 0.6875f, particlePosition.z);
            }
        }
    }
}
