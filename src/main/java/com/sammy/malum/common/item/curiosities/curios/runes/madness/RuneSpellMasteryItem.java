package com.sammy.malum.common.item.curiosities.curios.runes.madness;

import com.google.common.collect.*;
import com.sammy.malum.common.item.curiosities.curios.runes.*;
import com.sammy.malum.compability.irons_spellbooks.IronsSpellsCompat;
import com.sammy.malum.registry.common.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.item.*;
import team.lodestar.lodestone.registry.common.*;
import top.theillusivec4.curios.api.*;

public class RuneSpellMasteryItem extends AbstractRuneCurioItem {

    public RuneSpellMasteryItem(Properties builder) {
        super(builder, SpiritTypeRegistry.ARCANE_SPIRIT);
    }

    @Override
    public void addAttributeModifiers(Multimap<Attribute, AttributeModifier> map, SlotContext slotContext, ItemStack stack) {
        addAttributeModifier(map, LodestoneAttributeRegistry.MAGIC_PROFICIENCY.get(), uuid -> new AttributeModifier(uuid,
                "Curio Magic Proficiency", 0.1, AttributeModifier.Operation.ADDITION));
        addAttributeModifier(map, AttributeRegistry.RESERVE_STAFF_CHARGES.get(), uuid -> new AttributeModifier(uuid,
                "Curio Reserve Staff Charges", 2f, AttributeModifier.Operation.ADDITION));
        IronsSpellsCompat.addSpellPowerToCurio(this, map, 0.1f);
    }
}