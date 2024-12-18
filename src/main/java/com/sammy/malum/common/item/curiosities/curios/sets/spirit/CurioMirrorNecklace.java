package com.sammy.malum.common.item.curiosities.curios.sets.spirit;

import com.google.common.collect.Multimap;
import com.sammy.malum.MalumMod;
import com.sammy.malum.common.item.IMalumEventResponderItem;
import com.sammy.malum.common.item.curiosities.curios.MalumCurioItem;
import com.sammy.malum.registry.common.AttributeRegistry;
import dev.emi.trinkets.api.SlotReference;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;

public class CurioMirrorNecklace extends MalumCurioItem implements IMalumEventResponderItem {
    public CurioMirrorNecklace(Properties builder) {
        super(builder, MalumTrinketType.GILDED);
    }

    @Override
    public void addAttributeModifiers(Multimap<Holder<Attribute>, AttributeModifier> map, SlotReference slot, ItemStack stack, LivingEntity living) {
        addAttributeModifier(map, AttributeRegistry.ARCANE_RESONANCE,
                new AttributeModifier(MalumMod.malumPath("curio_arcane_resonance"), 1f, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
    }
}