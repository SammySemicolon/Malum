package com.sammy.malum.common.item.curiosities.curios.brooches;

import com.google.common.collect.*;
import com.sammy.malum.MalumMod;
import com.sammy.malum.common.item.curiosities.curios.*;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.item.*;
import top.theillusivec4.curios.api.*;

public class CurioGlassBrooch extends MalumCurioItem {

    public CurioGlassBrooch(Properties builder) {
        super(builder, MalumTrinketType.METALLIC);
    }

    @Override
    public void addAttributeModifiers(Multimap<Holder<Attribute>, AttributeModifier> map, SlotContext slotContext, ItemStack stack) {
        var id = MalumMod.malumPath("glass_brooch");
        CuriosApi.addSlotModifier(map, "rune", id, 2, AttributeModifier.Operation.ADD_VALUE);
        addAttributeModifier(map, Attributes.MAX_HEALTH,
                new AttributeModifier(id, -0.2f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
    }
}