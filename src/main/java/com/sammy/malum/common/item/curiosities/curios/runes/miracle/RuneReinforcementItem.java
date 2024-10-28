package com.sammy.malum.common.item.curiosities.curios.runes.miracle;

import com.google.common.collect.*;
import com.sammy.malum.MalumMod;
import com.sammy.malum.common.item.curiosities.curios.runes.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.item.*;
import top.theillusivec4.curios.api.*;

public class RuneReinforcementItem extends AbstractRuneCurioItem {

    public RuneReinforcementItem(Properties builder) {
        super(builder, SpiritTypeRegistry.ARCANE_SPIRIT);
    }

    @Override
    public void addAttributeModifiers(Multimap<Holder<Attribute>, AttributeModifier> map, SlotContext slotContext, ItemStack stack) {
        addAttributeModifier(map, AttributeRegistry.SOUL_WARD_CAP,
                new AttributeModifier(MalumMod.malumPath("curio_soul_ward_capacity"), 6f, AttributeModifier.Operation.ADD_VALUE));
        addAttributeModifier(map, AttributeRegistry.SOUL_WARD_STRENGTH,
                new AttributeModifier(MalumMod.malumPath("curio_soul_ward_strength"), 1f, AttributeModifier.Operation.ADD_VALUE));
    }
}