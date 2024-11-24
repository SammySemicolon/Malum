package com.sammy.malum.common.effect.aura;

import com.sammy.malum.*;
import com.sammy.malum.registry.common.SpiritTypeRegistry;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import team.lodestar.lodestone.helpers.ColorHelper;

public class CorruptedEarthenAura extends MobEffect {
    public CorruptedEarthenAura() {
        super(MobEffectCategory.BENEFICIAL, ColorHelper.getColor(SpiritTypeRegistry.EARTHEN_SPIRIT.getPrimaryColor()));
        addAttributeModifier(Attributes.ATTACK_DAMAGE, MalumMod.malumPath("corrupted_earthen_aura"), 2, AttributeModifier.Operation.ADD_VALUE);
    }
}