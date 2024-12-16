package com.sammy.malum.common.item.curiosities.curios.runes.madness;

import com.google.common.collect.*;
import com.sammy.malum.MalumMod;
import com.sammy.malum.common.item.curiosities.curios.runes.*;
import com.sammy.malum.compability.irons_spellbooks.IronsSpellsCompat;
import com.sammy.malum.registry.common.*;
import net.minecraft.core.Holder;
import net.minecraft.resources.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.item.*;
import team.lodestar.lodestone.registry.common.*;
import top.theillusivec4.curios.api.*;

public class RuneSpellMasteryItem extends AbstractRuneCurioItem {

    public RuneSpellMasteryItem(Properties builder) {
        super(builder, SpiritTypeRegistry.ARCANE_SPIRIT);
    }

    @Override
    public void addAttributeModifiers(Multimap<Holder<Attribute>, AttributeModifier> map, SlotContext slotContext, ItemStack stack) {
        var id = MalumMod.malumPath("spell_mastery_rune");
        addAttributeModifier(map, LodestoneAttributes.MAGIC_PROFICIENCY,
                new AttributeModifier(id, 0.15, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
        addAttributeModifier(map, AttributeRegistry.RESERVE_STAFF_CHARGES,
                new AttributeModifier(id, 2f, AttributeModifier.Operation.ADD_VALUE));

        IronsSpellsCompat.addSpellPowerToCurio(this, map, id,0.1f);
    }
}