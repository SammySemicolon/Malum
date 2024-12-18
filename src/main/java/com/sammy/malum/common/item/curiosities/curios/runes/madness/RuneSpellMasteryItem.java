package com.sammy.malum.common.item.curiosities.curios.runes.madness;

import com.google.common.collect.*;
import com.sammy.malum.MalumMod;
import com.sammy.malum.common.item.curiosities.curios.runes.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.core.Holder;
import net.minecraft.resources.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.item.*;
import team.lodestar.lodestone.registry.common.*;

public class RuneSpellMasteryItem extends AbstractRuneCurioItem {

    public RuneSpellMasteryItem(Properties builder) {
        super(builder, SpiritTypeRegistry.ARCANE_SPIRIT);
    }

    @Override
    public void addAttributeModifier(Multimap<Holder<Attribute>, AttributeModifier> map, Holder<Attribute> attribute, AttributeModifier modifier) {
        addAttributeModifier(map, LodestoneAttributes.MAGIC_PROFICIENCY,
                new AttributeModifier(MalumMod.malumPath("curio_magic_proficiency"), 0.15, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
        addAttributeModifier(map, AttributeRegistry.RESERVE_STAFF_CHARGES,
                new AttributeModifier(MalumMod.malumPath("curio_reserve_staff_charges"), 2f, AttributeModifier.Operation.ADD_VALUE));

    }
}