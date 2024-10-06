package com.sammy.malum.common.effect;

import com.sammy.malum.registry.common.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.ai.attributes.*;
import team.lodestar.lodestone.helpers.*;

public class SacrificialEmpowermentEffect extends MobEffect {
    public SacrificialEmpowermentEffect() {
        super(MobEffectCategory.BENEFICIAL, ColorHelper.getColor(SpiritTypeRegistry.WICKED_SPIRIT.getPrimaryColor()));
        addAttributeModifier(AttributeRegistry.SCYTHE_PROFICIENCY.get(), "5149e7f4-8f69-404e-9011-27396058db80", 0.15f, AttributeModifier.Operation.MULTIPLY_BASE);

    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return false;
    }
}