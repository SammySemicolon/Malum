package com.sammy.malum.visual_effects;

import com.sammy.malum.common.block.curiosities.spirit_crucible.*;
import com.sammy.malum.common.item.augment.core.CoreAugmentItem;
import com.sammy.malum.core.systems.artifice.ArtificeInfluenceData;
import com.sammy.malum.core.systems.artifice.ArtificeModifierSourceInstance;
import com.sammy.malum.core.systems.artifice.IArtificeAcceptor;
import com.sammy.malum.common.block.curiosities.spirit_catalyzer.*;
import com.sammy.malum.common.item.augment.*;
import com.sammy.malum.common.item.spirit.*;
import com.sammy.malum.core.systems.spirit.*;
import com.sammy.malum.registry.client.*;
import com.sammy.malum.visual_effects.networked.data.*;
import net.minecraft.core.*;
import net.minecraft.util.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.systems.easing.*;
import team.lodestar.lodestone.systems.particle.*;
import team.lodestar.lodestone.systems.particle.builder.*;
import team.lodestar.lodestone.systems.particle.data.*;
import team.lodestar.lodestone.systems.particle.data.spin.*;
import team.lodestar.lodestone.systems.particle.world.*;
import team.lodestar.lodestone.systems.particle.world.behaviors.components.*;
import team.lodestar.lodestone.systems.particle.world.options.*;

import java.util.Optional;
import java.util.function.*;

import static com.sammy.malum.visual_effects.SpiritLightSpecs.*;

public class SpiritCrucibleParticleEffects {

    public static void passiveCrucibleParticles(SpiritCrucibleCoreBlockEntity crucible) {
        var activeSpiritType = crucible.getActiveSpiritType();
        var level = crucible.getLevel();
        var random = level.random;
        if (level.getGameTime() % 16L == 0) {
            var item = crucible.coreAugmentInventory.getStackInSlot(0);
            if (item.getItem() instanceof CoreAugmentItem augmentItem) {
                var blockPos = crucible.getBlockPos();
                for (var augmentSpiritType : augmentItem.spiritTypes) {
                    Vec3 offset = SpiritCrucibleCoreBlockEntity.CRUCIBLE_CORE_AUGMENT_OFFSET.add(
                            Mth.nextFloat(random, -0.1f, 0.1f),
                            Mth.nextFloat(random, -0.1f, 0.1f),
                            Mth.nextFloat(random, -0.1f, 0.1f));
                    var particlePosition = new Vec3(blockPos.getX() + offset.x, blockPos.getY() + offset.y, blockPos.getZ() + offset.z);
                    var lightSpecs = SpiritLightSpecs.spiritLightSpecs(level, particlePosition, augmentSpiritType);
                    lightSpecs.getBuilder().multiplyLifetime(2.5f).modifyData(AbstractParticleBuilder::getScaleData, d -> d.multiplyValue(1.3f));
                    lightSpecs.getBloomBuilder().multiplyLifetime(1.5f);
                    lightSpecs.spawnParticles();
                }
            }
        }

        if (activeSpiritType == null) {
            return;
        }
        var itemPos = crucible.getItemPos();
        var spiritInventory = crucible.spiritInventory;
        var augmentInventory = crucible.augmentInventory;
        var recipe = crucible.recipe;
        if (recipe != null) {
            Optional<ArtificeInfluenceData> influenceData = crucible.attributes.getInfluenceData(level);
            influenceData.ifPresent(d -> {
                for (ArtificeModifierSourceInstance modifier : d.modifiers()) {
                    modifier.addParticles(crucible, activeSpiritType);
                }
            });
        }
        if (recipe != null) {
            var lightSpecs = spiritLightSpecs(level, itemPos, activeSpiritType, new WorldParticleOptions(ParticleRegistry.STAR.get()));
            lightSpecs.getBuilder()
                    .setSpinData(SpinParticleData.create(0).setSpinOffset((level.getGameTime() * 0.05f) % 6.28f).build())
                    .modifyData(AbstractParticleBuilder::getScaleData, d -> d.multiplyValue(2f))
                    .modifyData(AbstractParticleBuilder::getTransparencyData, d -> d.multiplyValue(0.25f));
            lightSpecs.getBloomBuilder()
                    .modifyData(AbstractParticleBuilder::getScaleData, d -> d.multiplyValue(2f))
                    .modifyData(AbstractParticleBuilder::getTransparencyData, d -> d.multiplyValue(0.5f));
            lightSpecs.spawnParticles();
        }

        int spiritsRendered = 0;
        for (int i = 0; i < spiritInventory.slotCount; i++) {
            var item = spiritInventory.getStackInSlot(i);
            if (item.getItem() instanceof SpiritShardItem spiritSplinterItem) {
                var offset = crucible.getSpiritItemOffset(spiritsRendered++, 0);
                var spiritType = spiritSplinterItem.type;
                var blockPos = crucible.getBlockPos();
                var spiritPosition = new Vec3(blockPos.getX() + offset.x, blockPos.getY() + offset.y, blockPos.getZ() + offset.z);
                if (recipe != null) {
                    Vec3 velocity = itemPos.subtract(spiritPosition).normalize().scale(RandomHelper.randomBetween(random, 0.03f, 0.06f));
                    if (random.nextFloat() < 0.85f) {
                        var sparkParticles = SparkParticleEffects.spiritMotionSparks(level, spiritPosition, spiritType);
                        sparkParticles.getBuilder().setMotion(velocity).modifyData(AbstractParticleBuilder::getScaleData, d -> d.multiplyValue(1.2f));
                        sparkParticles.getBloomBuilder().setMotion(velocity);
                        sparkParticles.spawnParticles();
                    }
                    if (random.nextFloat() < 0.85f) {
                        var lightSpecs = SpiritLightSpecs.spiritLightSpecs(level, spiritPosition, spiritType);
                        lightSpecs.getBuilder().multiplyLifetime(0.8f).setMotion(velocity.scale(1.5f)).modifyData(AbstractParticleBuilder::getScaleData, d -> d.multiplyValue(1.6f));
                        lightSpecs.getBloomBuilder().setMotion(velocity);
                        lightSpecs.spawnParticles();
                    }
                }
            }
        }
        if (level.getGameTime() % 4L == 0) {
            int augmentsRendered = 0;
            for (int i = 0; i < augmentInventory.slotCount; i++) {
                var item = augmentInventory.getStackInSlot(i);
                if (item.getItem() instanceof AugmentItem augmentItem) {
                    var offset = crucible.getAugmentItemOffset(augmentsRendered++, 0);
                    var blockPos = crucible.getBlockPos();
                    var particlePosition = new Vec3(blockPos.getX() + offset.x, blockPos.getY() + offset.y, blockPos.getZ() + offset.z);
                    for (var spiritType : augmentItem.spiritTypes) {
                        if (recipe != null) {
                            Vec3 velocity = itemPos.subtract(particlePosition).normalize().scale(RandomHelper.randomBetween(random, 0.01f, 0.02f));
                            if (random.nextFloat() < 0.15f) {
                                var sparkParticles = SparkParticleEffects.spiritMotionSparks(level, particlePosition, spiritType);
                                sparkParticles.getBuilder().multiplyLifetime(2.5f).setMotion(velocity).modifyData(AbstractParticleBuilder::getScaleData, d -> d.multiplyValue(1.2f));
                                sparkParticles.getBloomBuilder().multiplyLifetime(1.5f).setMotion(velocity);
                                sparkParticles.spawnParticles();
                            }
                            if (random.nextFloat() < 0.15f) {
                                var lightSpecs = SpiritLightSpecs.spiritLightSpecs(level, particlePosition, spiritType);
                                lightSpecs.getBuilder().multiplyLifetime(2.5f).setMotion(velocity.scale(1.5f)).modifyData(AbstractParticleBuilder::getScaleData, d -> d.multiplyValue(1.6f));
                                lightSpecs.getBloomBuilder().multiplyLifetime(1.5f).setMotion(velocity);
                                lightSpecs.spawnParticles();
                            }
                        }
                        var lightSpecs = SpiritLightSpecs.spiritLightSpecs(level, particlePosition, spiritType);
                        lightSpecs.getBuilder().multiplyLifetime(2.5f).modifyData(AbstractParticleBuilder::getScaleData, d -> d.multiplyValue(1.3f));
                        lightSpecs.getBloomBuilder().multiplyLifetime(1.5f);
                        lightSpecs.spawnParticles();
                    }
                }
            }
        }
    }
    public static void craftItemParticles(SpiritCrucibleCoreBlockEntity crucible, ColorEffectData colorData) {
        MalumSpiritType activeSpiritType = crucible.getActiveSpiritType();
        if (activeSpiritType == null) {
            return;
        }
        Level level = crucible.getLevel();
        var random = level.random;
        var cruciblePos = crucible.getBlockPos();
        Vec3 crucibleItemPos = SpiritCrucibleCoreBlockEntity.CRUCIBLE_ITEM_OFFSET.add(cruciblePos.getX(), cruciblePos.getY(), cruciblePos.getZ());

        for (int i = 0; i < 2; i++) {
            SpiritLightSpecs.coolLookingShinyThing(level, crucibleItemPos, activeSpiritType);
        }
        for (int i = 0; i < 24; i++) {
            int lifeDelay = i / 8;
            MalumSpiritType cyclingSpiritType = colorData.getSpirit();
            float xVelocity = RandomHelper.randomBetween(random, Easing.CUBIC_OUT, -0.075f, 0.075f);
            float yVelocity = RandomHelper.randomBetween(random, 0.2f, 0.5f);
            float zVelocity = RandomHelper.randomBetween(random, Easing.CUBIC_OUT, -0.075f, 0.075f);
            float gravityStrength = RandomHelper.randomBetween(random, 0.75f, 1f);
            if (random.nextFloat() < 0.85f) {
                var sparkParticles = SparkParticleEffects.spiritMotionSparks(level, crucibleItemPos, cyclingSpiritType);
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
                var lightSpecs = SpiritLightSpecs.spiritLightSpecs(level, crucibleItemPos, cyclingSpiritType);
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

        for (int i = 0; i < 8; i++) {
            MalumSpiritType cyclingSpiritType = colorData.getSpirit();
            float xVelocity = RandomHelper.randomBetween(random, Easing.CUBIC_OUT, -0.025f, 0.025f);
            float yVelocity = RandomHelper.randomBetween(random, 0.015f, 0.035f);
            float zVelocity = RandomHelper.randomBetween(random, Easing.CUBIC_OUT, -0.025f, 0.025f);
            if (random.nextFloat() < 0.85f) {
                var lightSpecs = SpiritLightSpecs.spiritLightSpecs(level, crucibleItemPos.subtract(0, 0.5f, 0), cyclingSpiritType, new WorldParticleOptions(ParticleRegistry.STRANGE_SMOKE.get()));
                lightSpecs.getBuilder()
                        .disableNoClip()
                        .setLifeDelay(i)
                        .multiplyLifetime(5)
                        .setMotion(xVelocity, yVelocity, zVelocity)
                        .setSpritePicker(SimpleParticleOptions.ParticleSpritePicker.WITH_AGE)
                        .modifyColorData(c -> c.multiplyCoefficient(0.5f))
                        .modifyData(AbstractParticleBuilder::getScaleData, d -> d.multiplyValue(1.5f))
                        .modifyData(AbstractParticleBuilder::getTransparencyData, d -> d.multiplyValue(0.1f));
                lightSpecs.getBloomBuilder()
                        .disableNoClip()
                        .setLifeDelay(i)
                        .multiplyLifetime(5)
                        .setMotion(xVelocity, yVelocity, zVelocity)
                        .setSpritePicker(SimpleParticleOptions.ParticleSpritePicker.WITH_AGE)
                        .modifyColorData(c -> c.multiplyCoefficient(0.5f))
                        .modifyData(AbstractParticleBuilder::getScaleData, d -> d.multiplyValue(2.5f))
                        .modifyData(AbstractParticleBuilder::getTransparencyData, d -> d.multiplyValue(0.25f));
                lightSpecs.spawnParticles();
            }
        }
    }

    public static void activeSpiritCatalyzerParticles(SpiritCatalyzerCoreBlockEntity catalyzer, IArtificeAcceptor target, MalumSpiritType spiritType) {
        Level level = catalyzer.getLevel();
        BlockPos catalyzerPos = catalyzer.getBlockPos();
        Vec3 startPos = SpiritCatalyzerCoreBlockEntity.CATALYZER_ITEM_OFFSET.add(catalyzerPos.getX(), catalyzerPos.getY(), catalyzerPos.getZ());
        RandomSource random = level.random;
        Vec3 targetPos = target.getVisualAccelerationPoint();
        if (level.getGameTime() % 2L == 0) {
            Vec3 velocity = targetPos.subtract(startPos).normalize().scale(RandomHelper.randomBetween(random, 0.06f, 0.12f));
            Vec3 sparkPos = startPos.add(0.05f - random.nextFloat() * 0.1f, 0.05f - random.nextFloat() * 0.1f, 0.05f - random.nextFloat() * 0.1f);
            var sparkParticles = SparkParticleEffects.spiritMotionSparks(level, sparkPos, spiritType);
            sparkParticles.getBuilder().setMotion(velocity)
                    .modifyData(AbstractParticleBuilder::getScaleData, d -> d.multiplyValue(1.5f))
                    .modifyOptionalData(b -> b.getBehaviorData(SparkBehaviorComponent.class, SparkBehaviorComponent::getLengthData), d -> d.multiplyValue(2f).multiplyCoefficient(0.75f))
                    .modifyColorData(c -> c.multiplyCoefficient(0.8f));
            sparkParticles.getBloomBuilder().setMotion(velocity);
            sparkParticles.spawnParticlesRaw();
        }
        if (level.getGameTime() % 10L == 0) {
            Vec3 velocity = targetPos.subtract(startPos).normalize().scale(0.02f * targetPos.distanceTo(startPos));
            final Consumer<LodestoneWorldParticle> behavior = p -> p.setParticleSpeed(p.getParticleSpeed().scale(0.98f));
            final SpinParticleData spinData = SpinParticleData.createRandomDirection(random, RandomHelper.randomBetween(random, 0.1f, 0.2f)).randomSpinOffset(random).build();
            WorldParticleBuilder.create(ParticleRegistry.HEXAGON.get(), new DirectionalBehaviorComponent(velocity.normalize()))
                    .setTransparencyData(GenericParticleData.create(0.6f, 0.4f, 0f).setEasing(Easing.SINE_IN_OUT, Easing.SINE_IN).build())
                    .setSpinData(spinData)
                    .setScaleData(GenericParticleData.create(0.15f, 0).setEasing(Easing.SINE_IN_OUT).build())
                    .setColorData(spiritType.createColorData().build())
                    .setLifetime(60)
                    .setMotion(velocity)
                    .enableNoClip()
                    .setSpritePicker(SimpleParticleOptions.ParticleSpritePicker.RANDOM_SPRITE)
                    .addTickActor(behavior)
                    .spawn(level, startPos.x, startPos.y, startPos.z);
        }

        if (level.getGameTime() % 4L == 0) {
            ItemStack item = catalyzer.augmentInventory.getStackInSlot(0);
            if (item.getItem() instanceof AugmentItem augmentItem) {
                Vec3 offset = SpiritCatalyzerCoreBlockEntity.CATALYZER_AUGMENT_OFFSET;
                BlockPos blockPos = catalyzer.getBlockPos();
                Vec3 particlePosition = new Vec3(blockPos.getX() + offset.x, blockPos.getY() + offset.y, blockPos.getZ() + offset.z);
                for (var augmentSpiritType : augmentItem.spiritTypes) {
                    Vec3 velocity = targetPos.subtract(particlePosition).normalize().scale(RandomHelper.randomBetween(random, 0.03f, 0.06f));
                    if (random.nextFloat() < 0.15f) {
                        var sparkParticles = SparkParticleEffects.spiritMotionSparks(level, particlePosition, augmentSpiritType);
                        sparkParticles.getBuilder().multiplyLifetime(2.5f).setMotion(velocity).modifyData(AbstractParticleBuilder::getScaleData, d -> d.multiplyValue(1.2f));
                        sparkParticles.getBloomBuilder().multiplyLifetime(1.5f).setMotion(velocity);
                        sparkParticles.spawnParticles();
                    }
                    if (random.nextFloat() < 0.15f) {
                        var lightSpecs = SpiritLightSpecs.spiritLightSpecs(level, particlePosition, augmentSpiritType);
                        lightSpecs.getBuilder().multiplyLifetime(2.5f).setMotion(velocity.scale(1.5f)).modifyData(AbstractParticleBuilder::getScaleData, d -> d.multiplyValue(1.6f));
                        lightSpecs.getBloomBuilder().multiplyLifetime(1.5f).setMotion(velocity);
                        lightSpecs.spawnParticles();
                    }
                }
            }
        }
    }


    public static void passiveSpiritCatalyzerParticles(SpiritCatalyzerCoreBlockEntity catalyzer) {
        Level level = catalyzer.getLevel();
        RandomSource random = level.random;
        if (level.getGameTime() % 16L == 0) {
            ItemStack item = catalyzer.augmentInventory.getStackInSlot(0);
            if (item.getItem() instanceof AugmentItem augmentItem) {
                BlockPos blockPos = catalyzer.getBlockPos();
                for (var augmentSpiritType : augmentItem.spiritTypes ) {
                    Vec3 offset = SpiritCatalyzerCoreBlockEntity.CATALYZER_AUGMENT_OFFSET.add(
                            Mth.nextFloat(random, -0.1f, 0.1f),
                            Mth.nextFloat(random, -0.1f, 0.1f),
                            Mth.nextFloat(random, -0.1f, 0.1f));
                    Vec3 particlePosition = new Vec3(blockPos.getX() + offset.x, blockPos.getY() + offset.y, blockPos.getZ() + offset.z);
                    var lightSpecs = SpiritLightSpecs.spiritLightSpecs(level, particlePosition, augmentSpiritType);
                    lightSpecs.getBuilder().multiplyLifetime(2.5f).modifyData(AbstractParticleBuilder::getScaleData, d -> d.multiplyValue(1.3f));
                    lightSpecs.getBloomBuilder().multiplyLifetime(1.5f);
                    lightSpecs.spawnParticles();
                }
            }
        }
    }
}
