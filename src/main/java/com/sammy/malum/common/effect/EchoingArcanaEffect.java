package com.sammy.malum.common.effect;

import com.sammy.malum.registry.common.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import team.lodestar.lodestone.helpers.*;

import java.awt.*;

public class EchoingArcanaEffect extends MobEffect {
    public EchoingArcanaEffect() {
        super(MobEffectCategory.BENEFICIAL, ColorHelper.getColor(new Color(255, 79, 234)));
        addAttributeModifier(AttributeRegistry.ARCANE_RESONANCE.get(), "04448cbf-ee2c-4f36-b71f-e641a312834a", 0.05f, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    @Override
    public void applyEffectTick(LivingEntity entityLivingBaseIn, int amplifier) {
    }
}