package com.sammy.malum.common.item.curiosities.curios.sets.spirit;

import com.google.common.collect.Multimap;
import com.sammy.malum.MalumMod;
import com.sammy.malum.common.item.curiosities.curios.MalumCurioItem;
import com.sammy.malum.registry.common.AttributeRegistry;
import dev.emi.trinkets.api.SlotReference;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;

public class CurioArcaneSpoilRing extends MalumCurioItem {

    public CurioArcaneSpoilRing(Properties builder) {
        super(builder, MalumTrinketType.ORNATE);
    }

    @Override
    public void addAttributeModifiers(Multimap<Holder<Attribute>, AttributeModifier> map, SlotReference slot, ItemStack stack, LivingEntity living) {
        addAttributeModifier(map, AttributeRegistry.SPIRIT_SPOILS,
                new AttributeModifier(MalumMod.malumPath("curio_spirit_spoils"), 1f, AttributeModifier.Operation.ADD_VALUE));
    }
}