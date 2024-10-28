package com.sammy.malum.common.item.curiosities.curios.runes.miracle;

import com.google.common.collect.*;
import com.sammy.malum.MalumMod;
import com.sammy.malum.common.item.curiosities.curios.runes.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.item.*;
import team.lodestar.lodestone.registry.common.*;
import top.theillusivec4.curios.api.*;

public class RuneCullingItem extends AbstractRuneCurioItem {

    public RuneCullingItem(Properties builder) {
        super(builder, SpiritTypeRegistry.WICKED_SPIRIT);
    }

    @Override
    public void addAttributeModifiers(Multimap<Holder<Attribute>, AttributeModifier> map, SlotContext slotContext, ItemStack stack) {
        addAttributeModifier(map, LodestoneAttributes.MAGIC_PROFICIENCY,
                new AttributeModifier(MalumMod.malumPath("curio_magic_proficiency"), 4f, AttributeModifier.Operation.ADD_VALUE));
    }
}