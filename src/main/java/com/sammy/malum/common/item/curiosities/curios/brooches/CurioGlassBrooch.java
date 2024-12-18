package com.sammy.malum.common.item.curiosities.curios.brooches;

import com.google.common.collect.*;
import com.sammy.malum.MalumMod;
import com.sammy.malum.common.item.curiosities.curios.*;
import dev.emi.trinkets.api.SlotAttributes;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.item.*;

public class CurioGlassBrooch extends MalumCurioItem {

    public CurioGlassBrooch(Properties builder) {
        super(builder, MalumTrinketType.METALLIC);
    }

    @Override
    public void addAttributeModifier(Multimap<Holder<Attribute>, AttributeModifier> map, Holder<Attribute> attribute, AttributeModifier modifier) {
        var id = MalumMod.malumPath("glass_brooch");
        SlotAttributes.addSlotModifier(map, "legs/rune", id, 2, AttributeModifier.Operation.ADD_VALUE);
        addAttributeModifier(map, Attributes.MAX_HEALTH,
                new AttributeModifier(id, -0.2f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
    }
}