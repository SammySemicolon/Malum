package com.sammy.malum.common.item.spirit;

import com.sammy.malum.common.data_components.*;
import com.sammy.malum.core.systems.ritual.*;
import com.sammy.malum.core.systems.spirit.*;
import com.sammy.malum.registry.common.item.*;
import com.sammy.malum.visual_effects.*;
import net.minecraft.client.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import team.lodestar.lodestone.handlers.screenparticle.ParticleEmitterHandler.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.registry.common.particle.*;
import team.lodestar.lodestone.systems.easing.*;
import team.lodestar.lodestone.systems.particle.*;
import team.lodestar.lodestone.systems.particle.builder.*;
import team.lodestar.lodestone.systems.particle.data.*;
import team.lodestar.lodestone.systems.particle.data.spin.*;
import team.lodestar.lodestone.systems.particle.screen.*;

import java.util.*;

public class RitualShardItem extends Item implements ItemParticleSupplier {

    public RitualShardItem(Properties properties) {
        super(properties);
    }

    public static int getStateDisplay(ItemStack stack) {
        var data = stack.get(DataComponentRegistry.RITUAL_DATA);
        if (data == null) {
            return -1;
        }
        return data.ritualTier().potency;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        var data = stack.get(DataComponentRegistry.RITUAL_DATA);
        if (data != null) {
            tooltipComponents.addAll(data.ritualType().makeRitualShardDescriptor(data.ritualTier()));
        }
    }

    @Override
    public void spawnLateParticles(ScreenParticleHolder target, Level level, float partialTick, ItemStack stack, float x, float y) {
        var data = stack.get(DataComponentRegistry.RITUAL_DATA);
        if (data != null) {
            var ritualType = data.ritualType();
            var ritualTier = data.ritualTier();
            var spiritType = ritualType.spirit;
            ScreenParticleEffects.spawnSpiritShardScreenParticles(target, spiritType);
            if (ritualTier.isGreaterThan(MalumRitualTier.DIM)) {
                float distance = 2f + ritualTier.potency;
                var rand = level.getRandom();
                for (int i = 0; i < 2; i++) {
                    float time = (((i == 1 ? 3.14f : 0) + ((level.getGameTime() + partialTick) * 0.05f)) % 6.28f);
                    float scalar = 0.4f + 0.15f * ritualTier.potency;
                    if (time > 1.57f && time < 4.71f) {
                        scalar *= Easing.QUAD_IN.ease(Math.abs(3.14f - time) / 1.57f, 0, 1, 1);
                    }
                    double xOffset = Math.sin(time) * distance;
                    double yOffset = Math.cos(time) * distance * 0.5f;
                    ScreenParticleBuilder.create(LodestoneScreenParticleTypes.WISP, target)
                            .setTransparencyData(GenericParticleData.create(0.3f, 0.5f, 0f).setEasing(Easing.SINE_IN_OUT).build())
                            .setSpinData(SpinParticleData.create(RandomHelper.randomBetween(rand, 0.2f, 0.4f)).setEasing(Easing.EXPO_OUT).build())
                            .setScaleData(GenericParticleData.create(RandomHelper.randomBetween(rand, 0.1f, 0.2f)*scalar, 0).setEasing(Easing.EXPO_OUT).build())
                            .setColorData(spiritType.createColorData().build())
                            .setLifetime(RandomHelper.randomBetween(rand, 60, 80))
                            .setRandomOffset(0.1f)
                            .setDiscardFunction(SimpleParticleOptions.ParticleDiscardFunctionType.ENDING_CURVE_INVISIBLE)
                            .spawnOnStack(xOffset, yOffset);
                    if (!ritualTier.isGreaterThan(MalumRitualTier.BRIGHT)) {
                        break;
                    }
                }
            }
        }
    }
}
