package com.sammy.malum.common.item.curiosities.curios.runes.madness;

import com.google.common.collect.*;
import com.sammy.malum.MalumMod;
import com.sammy.malum.common.item.curiosities.curios.runes.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.item.*;

public class RuneToughnessItem extends AbstractRuneCurioItem {

    public RuneToughnessItem(Properties builder) {
        super(builder, SpiritTypeRegistry.EARTHEN_SPIRIT);
    }

    @Override
    public void addAttributeModifier(Multimap<Holder<Attribute>, AttributeModifier> map, Holder<Attribute> attribute, AttributeModifier modifier) {
        addAttributeModifier(map, Attributes.ARMOR_TOUGHNESS,
                new AttributeModifier(MalumMod.malumPath("curio_armor_toughness"), 0.2f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
    }
}