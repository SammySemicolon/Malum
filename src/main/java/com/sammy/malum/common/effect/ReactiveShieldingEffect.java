package com.sammy.malum.common.effect;

import com.sammy.malum.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.ai.attributes.*;
import team.lodestar.lodestone.helpers.*;

public class ReactiveShieldingEffect extends MobEffect {
    public ReactiveShieldingEffect() {
        super(MobEffectCategory.BENEFICIAL, ColorHelper.getColor(SpiritTypeRegistry.EARTHEN_SPIRIT.getPrimaryColor()));
        addAttributeModifier(Attributes.ARMOR, MalumMod.malumPath("reactive_shielding"), 0.1f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        addAttributeModifier(Attributes.ARMOR_TOUGHNESS, MalumMod.malumPath("reactive_shielding"), 0.1f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);

    }
}