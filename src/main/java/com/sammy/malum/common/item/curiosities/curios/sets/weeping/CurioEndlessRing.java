package com.sammy.malum.common.item.curiosities.curios.sets.weeping;

import com.google.common.collect.*;
import com.sammy.malum.MalumMod;
import com.sammy.malum.common.item.*;
import com.sammy.malum.common.item.curiosities.curios.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.item.*;
import top.theillusivec4.curios.api.*;

public class CurioEndlessRing extends MalumCurioItem implements IVoidItem {
    public CurioEndlessRing(Properties builder) {
        super(builder, MalumTrinketType.VOID);
    }

    @Override
    public void addAttributeModifiers(Multimap<Holder<Attribute>, AttributeModifier> map, SlotContext slotContext, ItemStack stack) {
        addAttributeModifier(map, AttributeRegistry.RESERVE_STAFF_CHARGES,
                new AttributeModifier(MalumMod.malumPath("curio_reserve_staff_charges"), 3f, AttributeModifier.Operation.ADD_VALUE));
    }
}