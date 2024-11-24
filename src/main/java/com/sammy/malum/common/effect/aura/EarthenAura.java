package com.sammy.malum.common.effect.aura;

import com.sammy.malum.*;
import com.sammy.malum.registry.common.SpiritTypeRegistry;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import team.lodestar.lodestone.helpers.ColorHelper;

public class EarthenAura extends MobEffect {
    public EarthenAura() {
        super(MobEffectCategory.BENEFICIAL, ColorHelper.getColor(SpiritTypeRegistry.EARTHEN_SPIRIT.getPrimaryColor()));
        var id = MalumMod.malumPath("earthen_aura");
        addAttributeModifier(Attributes.ARMOR, id, 2f, AttributeModifier.Operation.ADD_VALUE);
        addAttributeModifier(Attributes.ARMOR_TOUGHNESS, id, 1f, AttributeModifier.Operation.ADD_VALUE);
    }
}