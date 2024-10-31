package com.sammy.malum.common.effect;

import com.sammy.malum.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.ai.attributes.*;
import team.lodestar.lodestone.helpers.*;

public class SacrificialEmpowermentEffect extends MobEffect {
    public SacrificialEmpowermentEffect() {
        super(MobEffectCategory.BENEFICIAL, ColorHelper.getColor(SpiritTypeRegistry.WICKED_SPIRIT.getPrimaryColor()));
        addAttributeModifier(AttributeRegistry.SCYTHE_PROFICIENCY, MalumMod.malumPath("sacrificial_empowerment"), 0.15f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
    }
}