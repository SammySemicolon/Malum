package com.sammy.malum.common.item.curiosities.curios.brooches;

import com.google.common.collect.*;
import com.sammy.malum.MalumMod;
import com.sammy.malum.common.item.curiosities.curios.*;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.item.*;
import top.theillusivec4.curios.api.*;

import java.util.*;

public class CurioElaborateBrooch extends MalumCurioItem {

    public static final ResourceLocation ELABORATE_BROOCH_NECKLACE = MalumMod.malumPath("elaborate_brooch_necklace");
    public static final ResourceLocation ELABORATE_BROOCH_BELT = MalumMod.malumPath("elaborate_brooch_belt");

    public CurioElaborateBrooch(Properties builder) {
        super(builder, MalumTrinketType.ORNATE);
    }

    @Override
    public void addAttributeModifiers(Multimap<Holder<Attribute>, AttributeModifier> map, SlotContext slotContext, ItemStack stack) {
        CuriosApi.addSlotModifier(map, "belt", ELABORATE_BROOCH_BELT, 1, AttributeModifier.Operation.ADD_VALUE);
        CuriosApi.addSlotModifier(map, "necklace", ELABORATE_BROOCH_NECKLACE, -1, AttributeModifier.Operation.ADD_VALUE);
    }
}
