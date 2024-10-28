package com.sammy.malum.common.item.curiosities.curios.sets.misc;

import com.google.common.collect.Multimap;
import com.sammy.malum.MalumMod;
import com.sammy.malum.common.item.curiosities.curios.MalumCurioItem;
import com.sammy.malum.registry.common.AttributeRegistry;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;

import java.util.function.Consumer;

public class CurioNarrowNecklace extends MalumCurioItem {
    public CurioNarrowNecklace(Properties builder) {
        super(builder, MalumTrinketType.METALLIC);
    }

    @Override
    public void addExtraTooltipLines(Consumer<Component> consumer) {
        consumer.accept(negativeEffect("no_sweep"));
    }

    @Override
    public void addAttributeModifiers(Multimap<Holder<Attribute>, AttributeModifier> map, SlotContext slotContext, ItemStack stack) {
        addAttributeModifier(map, AttributeRegistry.SCYTHE_PROFICIENCY,
                new AttributeModifier(MalumMod.malumPath("curio_scythe_proficiency"), 4f, AttributeModifier.Operation.ADD_VALUE));
    }
}
