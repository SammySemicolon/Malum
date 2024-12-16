package com.sammy.malum.common.item.curiosities.curios.runes.miracle;

import com.google.common.collect.*;
import com.sammy.malum.MalumMod;
import com.sammy.malum.common.item.curiosities.curios.runes.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.core.Holder;
import net.minecraft.resources.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.item.*;
import top.theillusivec4.curios.api.*;

public class RuneReinforcementItem extends AbstractRuneCurioItem {

    public RuneReinforcementItem(Properties builder) {
        super(builder, SpiritTypeRegistry.ARCANE_SPIRIT);
    }

    @Override
    public void addAttributeModifiers(Multimap<Holder<Attribute>, AttributeModifier> map, SlotContext slotContext, ItemStack stack) {
        var id = MalumMod.malumPath("reinforcement_rune");
        addAttributeModifier(map, AttributeRegistry.SOUL_WARD_CAPACITY,
                new AttributeModifier(id, 6f, AttributeModifier.Operation.ADD_VALUE));
        addAttributeModifier(map, AttributeRegistry.SOUL_WARD_INTEGRITY,
                new AttributeModifier(id, 0.25, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
    }
}