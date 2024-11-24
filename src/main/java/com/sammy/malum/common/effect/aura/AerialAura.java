package com.sammy.malum.common.effect.aura;

import com.sammy.malum.*;
import com.sammy.malum.registry.common.SpiritTypeRegistry;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import team.lodestar.lodestone.helpers.ColorHelper;

public class AerialAura extends MobEffect {
    public AerialAura() {
        super(MobEffectCategory.BENEFICIAL, ColorHelper.getColor(SpiritTypeRegistry.AERIAL_SPIRIT.getPrimaryColor()));
        addAttributeModifier(Attributes.MOVEMENT_SPEED, MalumMod.malumPath("aerial_aura"), 0.2f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
    }
}