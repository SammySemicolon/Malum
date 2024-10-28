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

public class CurioRunicBrooch extends MalumCurioItem {

    public static final ResourceLocation RUNIC_BROOCH_RING = MalumMod.malumPath("runic_brooch_ring");
    public static final ResourceLocation RUNIC_BROOCH_RUNE = MalumMod.malumPath("runic_brooch_rune");

    public CurioRunicBrooch(Properties builder) {
        super(builder, MalumTrinketType.GILDED);
    }

    @Override
    public void addAttributeModifiers(Multimap<Holder<Attribute>, AttributeModifier> map, SlotContext slotContext, ItemStack stack) {
        CuriosApi.addSlotModifier(map, "rune", RUNIC_BROOCH_RUNE, 2, AttributeModifier.Operation.ADD_VALUE);
        CuriosApi.addSlotModifier(map, "ring", RUNIC_BROOCH_RING, -1, AttributeModifier.Operation.ADD_VALUE);
    }
}