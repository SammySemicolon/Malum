package com.sammy.malum.common.item.curiosities.curios.runes.miracle;

import com.google.common.collect.*;
import com.sammy.malum.MalumMod;
import com.sammy.malum.common.item.curiosities.curios.runes.*;
import com.sammy.malum.registry.common.*;
import dev.emi.trinkets.api.SlotReference;
import net.minecraft.core.Holder;
import net.minecraft.resources.*;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.item.*;

public class RuneReinforcementItem extends AbstractRuneCurioItem {

    public RuneReinforcementItem(Properties builder) {
        super(builder, SpiritTypeRegistry.ARCANE_SPIRIT);
    }

    @Override
    public void addAttributeModifiers(Multimap<Holder<Attribute>, AttributeModifier> map, SlotReference slot, ItemStack stack, LivingEntity living) {
        var id = MalumMod.malumPath("reinforcement_rune");
        addAttributeModifier(map, AttributeRegistry.SOUL_WARD_CAPACITY,
                new AttributeModifier(id, 6f, AttributeModifier.Operation.ADD_VALUE));
        addAttributeModifier(map, AttributeRegistry.SOUL_WARD_INTEGRITY,
                new AttributeModifier(id, 0.25, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
    }
}