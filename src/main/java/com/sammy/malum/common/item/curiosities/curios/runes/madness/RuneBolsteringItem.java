package com.sammy.malum.common.item.curiosities.curios.runes.madness;

import com.google.common.collect.*;
import com.sammy.malum.MalumMod;
import com.sammy.malum.common.item.curiosities.curios.runes.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.item.*;
import top.theillusivec4.curios.api.*;

public class RuneBolsteringItem extends AbstractRuneCurioItem {

    public RuneBolsteringItem(Properties builder) {
        super(builder, SpiritTypeRegistry.SACRED_SPIRIT);
    }

    @Override
    public void addAttributeModifiers(Multimap<Holder<Attribute>, AttributeModifier> map, SlotContext slotContext, ItemStack stack) {
        addAttributeModifier(map, Attributes.MAX_HEALTH,
                new AttributeModifier(MalumMod.malumPath("curio_max_health"), 0.2f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
    }
}