package com.sammy.malum.visual_effects;

import com.sammy.malum.common.block.curiosities.repair_pylon.*;
import com.sammy.malum.common.block.storage.*;
import com.sammy.malum.common.item.spirit.*;
import com.sammy.malum.common.recipe.*;
import com.sammy.malum.core.systems.spirit.*;
import com.sammy.malum.visual_effects.networked.data.*;
import net.minecraft.core.*;
import net.minecraft.util.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.systems.blockentity.*;
import team.lodestar.lodestone.systems.easing.*;
import team.lodestar.lodestone.systems.particle.builder.*;

import java.util.function.*;

import static com.sammy.malum.visual_effects.SpiritLightSpecs.*;

public class RepairPylonParticleEffects {

    public static MalumSpiritType getCentralSpiritType(RepairPylonCoreBlockEntity pylon) {
        final LodestoneBlockEntityInventory spiritInventory = pylon.spiritInventory;
        int spiritCount = spiritInventory.nonEmptyItemAmount;
        Item currentItem = spiritInventory.getStackInSlot(0).getItem();
        if (spiritCount > 1) {
            float duration = 60f * spiritCount;
            float gameTime = (pylon.getLevel().getGameTime() % duration) / 60f;
            currentItem = spiritInventory.getStackInSlot(Mth.floor(gameTime)).getItem();
        }
        if (!(currentItem instanceof SpiritShardItem spiritItem)) {
            return null;
        }
        return spiritItem.type;
    }

    public static void passiveRepairPylonParticles(RepairPylonCoreBlockEntity pylon) {
        MalumSpiritType activeSpiritType = getCentralSpiritType(pylon);
        if (activeSpiritType == null) {
            return;
        }
        Level level = pylon.getLevel();
        var random = level.random;
        Vec3 itemPos = pylon.getItemPos();
        LodestoneBlockEntityInventory spiritInventory = pylon.spiritInventory;
        SpiritRepairRecipe recipe = pylon.recipe;
        final RepairPylonCoreBlockEntity.RepairPylonState state = pylon.state;
        if (recipe != null && !state.equals(RepairPylonCoreBlockEntity.RepairPylonState.COOLDOWN)) {
            SpiritLightSpecs.rotatingLightSpecs(level, itemPos, activeSpiritType, 0.5f, 3, b -> b.multiplyLifetime(1.2f).modifyData(b::getScaleData, d -> d.multiplyValue(1.2f)));
        }

        int spiritsRendered = 0;
        for (int i = 0; i < spiritInventory.slotCount; i++) {
            ItemStack item = spiritInventory.getStackInSlot(i);
            if (item.getItem() instanceof SpiritShardItem spiritSplinterItem) {
                Vec3 offset = pylon.getSpiritItemOffset(spiritsRendered++, 0);
                activeSpiritType = spiritSplinterItem.type;
                BlockPos blockPos = pylon.getBlockPos();
                Vec3 spiritPosition = new Vec3(blockPos.getX() + offset.x, blockPos.getY() + offset.y, blockPos.getZ() + offset.z);
                spiritLightSpecs(level, spiritPosition, activeSpiritType).spawnParticles();
                if (recipe != null && state.equals(RepairPylonCoreBlockEntity.RepairPylonState.CHARGING)) {
                    Vec3 velocity = itemPos.subtract(spiritPosition).normalize().scale(RandomHelper.randomBetween(random, 0.03f, 0.06f));
                    if (random.nextFloat() < 0.85f) {
                        var sparkParticles = SparkParticleEffects.spiritMotionSparks(level, spiritPosition, activeSpiritType);
                        sparkParticles.getBuilder().setMotion(velocity).modifyData(AbstractParticleBuilder::getScaleData, d -> d.multiplyValue(1.2f));
                        sparkParticles.getBloomBuilder().setMotion(velocity);
                        sparkParticles.spawnParticles();
                    }
                    if (random.nextFloat() < 0.85f) {
                        var lightSpecs = SpiritLightSpecs.spiritLightSpecs(level, spiritPosition, activeSpiritType);
                        lightSpecs.getBuilder().multiplyLifetime(0.8f).setMotion(velocity.scale(1.5f)).modifyData(AbstractParticleBuilder::getScaleData, d -> d.multiplyValue(1.6f));
                        lightSpecs.getBloomBuilder().setMotion(velocity);
                        lightSpecs.spawnParticles();
                    }
                }
            }
        }
    }

    public static void prepareRepairParticles(RepairPylonCoreBlockEntity pylon, IMalumSpecialItemAccessPoint holder, ColorEffectData colorData) {
        MalumSpiritType activeSpiritType = getCentralSpiritType(pylon);
        if (activeSpiritType == null) {
            return;
        }
        Level level = pylon.getLevel();
        var random = level.random;
        long gameTime = level.getGameTime();
        Vec3 pylonItemPos = pylon.getItemPos();
        Vec3 holderItemPos = holder.getItemPos();

        for (int i = 0; i < 2; i++) {
            SpiritLightSpecs.coolLookingShinyThing(level, pylonItemPos, activeSpiritType);
        }
        for (int i = 0; i < 4; i++) {
            MalumSpiritType cyclingSpiritType = colorData.getCyclingColorRecord().spiritType();
            for (int j = 0; j < 60; j++) {
                float distance = 0.8f * (1 - j / 90f);
                long time = gameTime+j*4;
                Vec3 offsetPosition = DataHelper.rotatingRadialOffset(holderItemPos, distance, i, 4, time, 160);
                if (random.nextFloat() < 0.85f) {
                    var sparkParticles = SparkParticleEffects.spiritMotionSparks(level, offsetPosition, cyclingSpiritType);
                    sparkParticles.getBuilder()
                            .disableNoClip()
                            .setLifeDelay(j)
                            .multiplyLifetime(0.75f)
                            .modifyData(AbstractParticleBuilder::getScaleData, d -> d.multiplyValue(1f));
                    sparkParticles.getBloomBuilder()
                            .disableNoClip()
                            .setLifeDelay(j)
                            .multiplyLifetime(0.75f)
                            .modifyData(AbstractParticleBuilder::getTransparencyData, d -> d.multiplyValue(1.25f));
                    sparkParticles.spawnParticles();
                }
                if (random.nextFloat() < 0.85f) {
                    var lightSpecs = SpiritLightSpecs.spiritLightSpecs(level, offsetPosition, cyclingSpiritType);
                    lightSpecs.getBuilder()
                            .disableNoClip()
                            .setLifeDelay(j)
                            .modifyData(AbstractParticleBuilder::getScaleData, d -> d.multiplyValue(1.5f));
                    lightSpecs.getBloomBuilder()
                            .disableNoClip()
                            .setLifeDelay(j)
                            .modifyData(AbstractParticleBuilder::getTransparencyData, d -> d.multiplyValue(1.25f));
                    lightSpecs.spawnParticles();
                }
            }
            for (int j = 0; j < 32; j++) {
                float distance = 0.8f * (j / 32f);
                long time = gameTime+j*3;
                int lifeDelay = 32 - j;
                Vec3 offsetPosition = DataHelper.rotatingRadialOffset(pylonItemPos, distance, i, 4, time, 160);
                if (random.nextFloat() < 0.85f) {
                    var sparkParticles = SparkParticleEffects.spiritMotionSparks(level, offsetPosition, cyclingSpiritType);
                    sparkParticles.getBuilder()
                            .disableNoClip()
                            .setLifeDelay(lifeDelay)
                            .multiplyLifetime(0.75f)
                            .modifyData(AbstractParticleBuilder::getScaleData, d -> d.multiplyValue(1f));
                    sparkParticles.getBloomBuilder()
                            .disableNoClip()
                            .setLifeDelay(lifeDelay)
                            .multiplyLifetime(0.75f)
                            .modifyData(AbstractParticleBuilder::getTransparencyData, d -> d.multiplyValue(1.25f));
                    sparkParticles.spawnParticles();
                }
                if (random.nextFloat() < 0.85f) {
                    var lightSpecs = SpiritLightSpecs.spiritLightSpecs(level, offsetPosition, cyclingSpiritType);
                    lightSpecs.getBuilder()
                            .disableNoClip()
                            .setLifeDelay(lifeDelay)
                            .modifyData(AbstractParticleBuilder::getScaleData, d -> d.multiplyValue(1.5f));
                    lightSpecs.getBloomBuilder()
                            .disableNoClip()
                            .setLifeDelay(lifeDelay)
                            .modifyData(AbstractParticleBuilder::getTransparencyData, d -> d.multiplyValue(1.25f));
                    lightSpecs.spawnParticles();
                }
            }
        }
    }

    public static void repairItemParticles(RepairPylonCoreBlockEntity pylon, IMalumSpecialItemAccessPoint holder, ColorEffectData colorData) {
        MalumSpiritType activeSpiritType = getCentralSpiritType(pylon);
        if (activeSpiritType == null) {
            return;
        }
        var level = pylon.getLevel();
//        repairItemParticles(level, activeSpiritType, pylon.getItemPos(), colorData);
        repairItemParticles(level, activeSpiritType, holder.getItemPos(), colorData);
    }
    public static void repairItemParticles(Level level, MalumSpiritType activeSpiritType, Vec3 itemPos, ColorEffectData colorData) {
        long gameTime = level.getGameTime();
        var random = level.random;
        for (int i = 0; i < 2; i++) {
            SpiritLightSpecs.coolLookingShinyThing(level, itemPos, activeSpiritType);
        }
        for (int i = 0; i < 24; i++) {
            int lifeDelay = i / 8;
            MalumSpiritType cyclingSpiritType = colorData.getCyclingColorRecord().spiritType();
            float xVelocity = RandomHelper.randomBetween(random, Easing.CUBIC_OUT, -0.075f, 0.075f);
            float yVelocity = RandomHelper.randomBetween(random, 0.2f, 0.5f);
            float zVelocity = RandomHelper.randomBetween(random, Easing.CUBIC_OUT, -0.075f, 0.075f);
            float gravityStrength = RandomHelper.randomBetween(random, 0.75f, 1f);
            if (random.nextFloat() < 0.85f) {
                var sparkParticles = SparkParticleEffects.spiritMotionSparks(level, itemPos, cyclingSpiritType);
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
                var lightSpecs = SpiritLightSpecs.spiritLightSpecs(level, itemPos, cyclingSpiritType);
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
            int finalI = i;
            Vec3 offsetPosition = DataHelper.rotatingRadialOffset(itemPos, 0.6f, i, 8, gameTime, 160);
            Consumer<WorldParticleBuilder> behavior = b -> b.addTickActor(p -> {
                if (level.getGameTime() > gameTime + finalI * 4 && level.getGameTime() < gameTime + (finalI + 4) * 4) {
                    p.setParticleSpeed(p.getParticleSpeed().add(0, 0.015f, 0));
                }
            });

            var lightSpecs = spiritLightSpecs(level, offsetPosition, activeSpiritType);
            lightSpecs.getBuilder().act(b -> b
                    .act(behavior)
                    .modifyColorData(d -> d.multiplyCoefficient(0.35f))
                    .modifyData(b::getScaleData, d -> d.multiplyValue(2f).multiplyCoefficient(0.9f))
                    .modifyData(b::getTransparencyData, d -> d.multiplyCoefficient(0.9f))
                    .multiplyLifetime(1.5f)
                    .setLifetime(b.getParticleOptions().lifetimeSupplier.get() + finalI * 2));
            lightSpecs.getBloomBuilder().act(b -> b
                    .act(behavior)
                    .modifyColorData(d -> d.multiplyCoefficient(0.35f))
                    .modifyData(b::getScaleData, d -> d.multiplyValue(1.6f).multiplyCoefficient(0.9f))
                    .modifyData(b::getTransparencyData, d -> d.multiplyCoefficient(0.9f))
                    .setLifetime((int) (b.getParticleOptions().lifetimeSupplier.get() + finalI * 2.5f)));
            lightSpecs.spawnParticles();
        }
    }
}
