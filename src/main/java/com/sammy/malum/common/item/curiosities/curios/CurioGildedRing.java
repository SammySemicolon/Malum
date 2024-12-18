package com.sammy.malum.common.item.curiosities.curios;

import com.google.common.collect.Multimap;
import com.sammy.malum.MalumMod;
import dev.emi.trinkets.api.SlotReference;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;

public class CurioGildedRing extends MalumCurioItem {
    public CurioGildedRing(Properties builder) {
        super(builder, MalumTrinketType.GILDED);
    }

    @Override
    public void addAttributeModifiers(Multimap<Holder<Attribute>, AttributeModifier> map, SlotReference slot, ItemStack stack, LivingEntity living) {
        addAttributeModifier(map, Attributes.ARMOR, new AttributeModifier(MalumMod.malumPath("curio_armor_ring"), 1f, AttributeModifier.Operation.ADD_VALUE));
    }
}