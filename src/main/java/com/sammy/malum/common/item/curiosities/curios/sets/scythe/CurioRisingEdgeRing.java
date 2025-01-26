package com.sammy.malum.common.item.curiosities.curios.sets.scythe;

import com.google.common.collect.*;
import com.sammy.malum.*;
import com.sammy.malum.common.item.curiosities.curios.*;
import com.sammy.malum.core.helpers.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.core.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.item.*;
import top.theillusivec4.curios.api.*;

import java.util.function.*;

public class CurioRisingEdgeRing extends MalumCurioItem {
    public CurioRisingEdgeRing(Properties builder) {
        super(builder, MalumTrinketType.METALLIC);
    }

    @Override
    public void addExtraTooltipLines(Consumer<Component> consumer) {
        consumer.accept(ComponentHelper.positiveCurioEffect("ascension_launch"));
        consumer.accept(ComponentHelper.negativeCurioEffect("longer_ascension_cooldown"));
    }

    @Override
    public void addAttributeModifiers(Multimap<Holder<Attribute>, AttributeModifier> map, SlotContext slotContext, ItemStack stack) {
        addAttributeModifier(map, AttributeRegistry.SCYTHE_PROFICIENCY,
                new AttributeModifier(MalumMod.malumPath("rising_edge_ring"), 0.25f, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
    }
}
