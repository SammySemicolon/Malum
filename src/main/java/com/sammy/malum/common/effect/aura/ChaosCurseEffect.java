package com.sammy.malum.common.effect.aura;

import com.sammy.malum.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.ai.attributes.*;
import team.lodestar.lodestone.helpers.*;

public class ChaosCurseEffect extends MobEffect {
    public ChaosCurseEffect() {
        super(MobEffectCategory.HARMFUL, ColorHelper.getColor(SpiritTypeRegistry.INFERNAL_SPIRIT.getPrimaryColor()));
        var id = MalumMod.malumPath("chaos_curse");
        addAttributeModifier(AttributeRegistry.MALIGNANT_CONVERSION, id, 0.02f, AttributeModifier.Operation.ADD_VALUE);
    }


}