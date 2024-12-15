package com.sammy.malum.visual_effects;

import com.sammy.malum.common.item.*;
import com.sammy.malum.core.systems.spirit.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.client.*;
import net.minecraft.client.player.*;
import net.minecraft.util.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.registry.common.particle.*;
import team.lodestar.lodestone.systems.easing.*;
import team.lodestar.lodestone.systems.particle.*;
import team.lodestar.lodestone.systems.particle.builder.*;
import team.lodestar.lodestone.systems.particle.data.*;
import team.lodestar.lodestone.systems.particle.data.color.*;
import team.lodestar.lodestone.systems.particle.data.spin.*;
import team.lodestar.lodestone.systems.particle.render_types.*;
import team.lodestar.lodestone.systems.particle.screen.*;

import java.awt.*;

import static net.minecraft.util.Mth.*;

public class ScreenParticleEffects {

    public static void spawnRuneParticles(ScreenParticleHolder target, MalumSpiritType spiritType) {
        var rand = Minecraft.getInstance().level.getRandom();
        ScreenParticleBuilder.create(LodestoneScreenParticleTypes.SPARKLE, target)
                .setTransparencyData(GenericParticleData.create(0.03f, 0f).setEasing(Easing.SINE_IN_OUT).build())
                .setScaleData(GenericParticleData.create(0.5f + rand.nextFloat() * 0.1f, 0).setEasing(Easing.SINE_IN_OUT, Easing.BOUNCE_IN_OUT).build())
                .setColorData(spiritType.createColorData().build())
                .setLifetime(RandomHelper.randomBetween(rand, 20, 30))
                .setRandomOffset(0.05f)
                .setRandomMotion(0.05f, 0.05f)
                .spawnOnStack(2, -1);

        ScreenParticleBuilder.create(LodestoneScreenParticleTypes.WISP, target)
                .setTransparencyData(GenericParticleData.create(0.015f, 0f).setEasing(Easing.SINE_IN_OUT).build())
                .setSpinData(SpinParticleData.create(nextFloat(rand, 0.2f, 0.4f)).setEasing(Easing.EXPO_OUT).build())
                .setScaleData(GenericParticleData.create(0.3f + rand.nextFloat() * 0.3f, 0).setEasing(Easing.EXPO_OUT).build())
                .setColorData(spiritType.createColorData().build())
                .setLifetime(RandomHelper.randomBetween(rand, 20, 30))
                .setRandomOffset(0.1f)
                .setRandomMotion(0.4f, 0.4f)
                .spawnOnStack(2, 0)
                .setLifetime(RandomHelper.randomBetween(rand, 8, 12))
                .setSpinData(SpinParticleData.create(nextFloat(rand, 0.05f, 0.1f)).build())
                .setScaleData(GenericParticleData.create(0.6f + rand.nextFloat() * 0.3f, 0f).build())
                .setRandomMotion(0.01f, 0.01f)
                .spawnOnStack(2, -1);
    }


    public static void spawnSpiritShardScreenParticles(ScreenParticleHolder target, MalumSpiritType spiritType) {
        var rand = Minecraft.getInstance().level.getRandom();
        var color = spiritType.getPrimaryColor();
        var endColor = spiritType.getSecondaryColor();
        ScreenParticleBuilder.create(LodestoneScreenParticleTypes.SPARKLE, target)
                .setTransparencyData(GenericParticleData.create(0.04f, 0f).setEasing(Easing.SINE_IN_OUT).build())
                .setScaleData(GenericParticleData.create(0.8f + rand.nextFloat() * 0.1f, 0).setEasing(Easing.SINE_IN_OUT, Easing.BOUNCE_IN_OUT).build())
                .setColorData(ColorParticleData.create(color, endColor).setCoefficient(2f).build())
                .setLifetime(10 + rand.nextInt(10))
                .setRandomOffset(0.05f)
                .setRandomMotion(0.05f, 0.05f)
                .spawnOnStack(0, 0);

        ScreenParticleBuilder.create(LodestoneScreenParticleTypes.WISP, target)
                .setTransparencyData(GenericParticleData.create(0.03f, 0f).setEasing(Easing.SINE_IN_OUT).build())
                .setSpinData(SpinParticleData.create(nextFloat(rand, 0.2f, 0.4f)).setEasing(Easing.EXPO_OUT).build())
                .setScaleData(GenericParticleData.create(0.6f + rand.nextFloat() * 0.4f, 0).setEasing(Easing.EXPO_OUT).build())
                .setColorData(ColorParticleData.create(color, endColor).setCoefficient(1.25f).build())
                .setLifetime(20 + rand.nextInt(8))
                .setRandomOffset(0.1f)
                .setRandomMotion(0.4f, 0.4f)
                .spawnOnStack(0, 0);
    }

    public static void spawnVoidItemScreenParticles(ScreenParticleHolder target, Level level, float intensity, float partialTick) {
        float colorMultiplier = Mth.nextFloat(level.random, 0.4f, 1.2f);
        float timeMultiplier = Mth.nextFloat(level.random, 0.9f, 1.4f);
        Color color = new Color((int) (31 * colorMultiplier), (int) (19 * colorMultiplier), (int) (31 * colorMultiplier));
        Color endColor = new Color((int) (111 * colorMultiplier), (int) (31 * colorMultiplier), (int) (121 * colorMultiplier));
        float gameTime = level.getGameTime() + partialTick;
        var rand = Minecraft.getInstance().level.getRandom();
        SpinParticleData spinParticleData = SpinParticleData.createRandomDirection(rand, 0, level.random.nextBoolean() ? 1 : -2).setSpinOffset(0.025f * gameTime % 6.28f).setEasing(Easing.EXPO_IN_OUT).build();
        ScreenParticleBuilder.create(LodestoneScreenParticleTypes.STAR, target)
                .setScaleData(GenericParticleData.create(1.2f * intensity + rand.nextFloat() * 0.1f * intensity, 0).setEasing(Easing.SINE_IN_OUT, Easing.BOUNCE_IN_OUT).build())
                .setTransparencyData(GenericParticleData.create(0.1f, 0.6f, 0f).setEasing(Easing.SINE_IN_OUT).build())
                .setColorData(ColorParticleData.create(color, endColor).setCoefficient(2f).build())
                .setSpinData(spinParticleData)
                .setLifetime((int) ((10 + rand.nextInt(10)) * timeMultiplier))
                .setRandomOffset(0.05f)
                .setRandomMotion(0.05f, 0.05f)
                .setRenderType(LodestoneScreenParticleRenderType.LUMITRANSPARENT)
                .spawnOnStack(0, 0);

        ScreenParticleBuilder.create(LodestoneScreenParticleTypes.WISP, target)
                .setScaleData(GenericParticleData.create(0.8f * intensity + rand.nextFloat() * 0.6f * intensity, 0).setEasing(Easing.EXPO_OUT).build())
                .setTransparencyData(GenericParticleData.create(0.1f, 0.2f, 0f).setEasing(Easing.SINE_IN_OUT).build())
                .setColorData(ColorParticleData.create(color, endColor.darker()).setCoefficient(1.25f).build())
                .setSpinData(spinParticleData)
                .setLifetime(20 + rand.nextInt(8))
                .setRandomOffset(0.1f)
                .setRandomMotion(0.4f, 0.4f)
                .setRenderType(LodestoneScreenParticleRenderType.LUMITRANSPARENT)
                .spawnOnStack(0, 0)
                .setLifetime((int) ((10 + rand.nextInt(2)) * timeMultiplier))
                .setSpinData(SpinParticleData.create(nextFloat(rand, 0.05f, 0.1f)).build())
                .setScaleData(GenericParticleData.create(0.8f + rand.nextFloat() * 0.4f, 0f).build())
                .setRandomMotion(0.01f, 0.01f)
                .spawnOnStack(0, 0);
    }

    public static void spawnEncyclopediaEsotericaScreenParticles(ScreenParticleHolder target, Level level, float partialTick) {
        var rand = level.getRandom();
        float distance = 7.5f;
        for (int i = 0; i < 2; i++) {
            float time = (((level.getGameTime() + partialTick) * 0.05f + i * (6.28f / 2)) % 6.28f);
            float scalar = 0.6f;
            if (time > 1.57f && time < 4.71f) {
                scalar *= Easing.QUAD_IN.ease(Math.abs(3.14f - time) / 1.57f, 0, 1, 1);
            }
            double xOffset = Math.sin(time) * distance;
            double yOffset = Math.cos(time) * distance * 0.5f;
            float colorMultiplier = Mth.nextFloat(level.random, 0.7f, 1f);
            Color color = new Color((int) (255 * colorMultiplier), (int) (51 * colorMultiplier), (int) (195 * colorMultiplier));
            Color endColor = new Color((int) (56 * colorMultiplier), (int) (32 * colorMultiplier), (int) (77 * colorMultiplier));
            float gameTime = level.getGameTime() + partialTick;
            SpinParticleData spinParticleData = SpinParticleData.createRandomDirection(rand, level.random.nextBoolean() ? 1 : -2).setSpinOffset(0.025f * gameTime % 6.28f).build();
            ScreenParticleBuilder.create(LodestoneScreenParticleTypes.WISP, target)
                    .setScaleData(GenericParticleData.create(0, 0.1f*scalar, 0).setEasing(Easing.SINE_IN_OUT).setEasing(Easing.EXPO_OUT).build())
                    .setTransparencyData(GenericParticleData.create(0f, 0.25f, 0f).setEasing(Easing.SINE_IN_OUT).build())
                    .setColorData(ColorParticleData.create(color, endColor.darker()).setCoefficient(1.25f).build())
                    .setSpinData(spinParticleData)
                    .setLifetime(60)
                    .setDiscardFunction(SimpleParticleOptions.ParticleDiscardFunctionType.ENDING_CURVE_INVISIBLE)
                    .spawnOnStack(xOffset, yOffset)
                    .setRenderType(LodestoneScreenParticleRenderType.LUMITRANSPARENT)
                    .setScaleData(GenericParticleData.create(0.25f * scalar, RandomHelper.randomBetween(rand, 0.3f, 0.4f) * scalar, 0).setEasing(Easing.SINE_IN_OUT).setEasing(Easing.EXPO_OUT).build())
                    .repeatOnStack(xOffset, yOffset, 2);
        }
    }

    public static class VoidTransmutableParticleEffect implements IVoidItem {

        public static VoidTransmutableParticleEffect INSTANCE = new VoidTransmutableParticleEffect();
        private boolean isNearWell;
        private boolean isItemReal;

        @Override
        public float getVoidParticleIntensity() {
            return 1.2f;
        }

        @Override
        public void spawnEarlyParticles(ScreenParticleHolder target, Level level, float partialTick, ItemStack stack, float x, float y) {
            final LocalPlayer player = Minecraft.getInstance().player;
            isNearWell = player.getData(AttachmentTypeRegistry.VOID_INFLUENCE).isNearWeepingWell;
            if (!isNearWell) {
                return;
            }
            if (level.getGameTime() % 20L == 0) {
                isItemReal = player.getInventory().contains(stack);
            }
            if (isItemReal) {
                IVoidItem.super.spawnEarlyParticles(target, level, partialTick, stack, x, y);
            }
        }

        @Override
        public void spawnLateParticles(ScreenParticleHolder target, Level level, float partialTick, ItemStack stack, float x, float y) {
            if (!isNearWell || !isItemReal) {
                return;
            }
            var rand = level.getRandom();
            float distance = 7.5f;
            for (int i = 0; i < 2; i++) {
                float time = (((i == 1 ? 3.14f : 0) + ((level.getGameTime() + partialTick) * 0.1f)) % 6.28f);
                float scalar = 0.6f;
                double xOffset = Math.sin(time) * distance;
                double yOffset = Math.cos(time) * distance;
                float colorMultiplier = Mth.nextFloat(level.random, 0.7f, 1f);
                Color color = new Color((int) (255 * colorMultiplier), (int) (51 * colorMultiplier), (int) (195 * colorMultiplier));
                Color endColor = new Color((int) (56 * colorMultiplier), (int) (32 * colorMultiplier), (int) (77 * colorMultiplier));
                float gameTime = level.getGameTime() + partialTick;
                SpinParticleData spinParticleData = SpinParticleData.createRandomDirection(rand, level.random.nextBoolean() ? 1 : -2).setSpinOffset(0.025f * gameTime % 6.28f).build();
                ScreenParticleBuilder.create(LodestoneScreenParticleTypes.WISP, target)
                        .setScaleData(GenericParticleData.create(0.1f*scalar, RandomHelper.randomBetween(rand, 0.2f, 0.3f)*scalar, 0).setEasing(Easing.SINE_IN_OUT).setEasing(Easing.EXPO_OUT).build())
                        .setTransparencyData(GenericParticleData.create(0f, 0.25f, 0f).setEasing(Easing.SINE_IN_OUT).build())
                        .setColorData(ColorParticleData.create(color, endColor.darker()).setCoefficient(1.25f).build())
                        .setSpinData(spinParticleData)
                        .setLifetime(40)
                        .setDiscardFunction(SimpleParticleOptions.ParticleDiscardFunctionType.ENDING_CURVE_INVISIBLE)
                        .spawnOnStack(xOffset, yOffset)
                        .setRenderType(LodestoneScreenParticleRenderType.LUMITRANSPARENT)
                        .setScaleData(GenericParticleData.create(0.25f*scalar, RandomHelper.randomBetween(rand, 0.3f, 0.4f)*scalar, 0).setEasing(Easing.SINE_IN_OUT).setEasing(Easing.EXPO_OUT).build())
                        .repeatOnStack(xOffset, yOffset, 2);
            }
        }
    }
}
