package com.sammy.malum.common.item.curiosities.curios.runes.miracle;

import com.google.common.collect.*;
import com.sammy.malum.common.item.curiosities.curios.runes.*;
import com.sammy.malum.compability.irons_spellbooks.IronsSpellsCompat;
import com.sammy.malum.registry.common.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.item.*;
import team.lodestar.lodestone.registry.common.*;
import top.theillusivec4.curios.api.*;

public class RuneCullingItem extends AbstractRuneCurioItem {

    public RuneCullingItem(Properties builder) {
        super(builder, SpiritTypeRegistry.WICKED_SPIRIT);
    }

    @Override
    public void addAttributeModifiers(Multimap<Attribute, AttributeModifier> map, SlotContext slotContext, ItemStack stack) {
        addAttributeModifier(map, LodestoneAttributeRegistry.MAGIC_PROFICIENCY.get(), uuid -> new AttributeModifier(uuid,
                "Curio Magic Proficiency", 0.2, AttributeModifier.Operation.MULTIPLY_BASE));
        IronsSpellsCompat.addSpellPowerToCurio(this, map, 0.2f);
    }
}