package com.sammy.malum.common.item.curiosities.armor;

import com.sammy.malum.MalumMod;
import com.sammy.malum.compability.irons_spellbooks.IronsSpellsCompat;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import team.lodestar.lodestone.registry.common.LodestoneAttributes;

import java.util.List;

import static com.sammy.malum.registry.common.item.ArmorTiers.SOUL_HUNTER;

public class SoulHunterArmorItem extends MalumArmorItem {
    public SoulHunterArmorItem(ArmorItem.Type slot, Properties builder) {
        super(SOUL_HUNTER, slot, builder);
    }

    @Override
    public List<ItemAttributeModifiers.Entry> createExtraAttributes() {
        var group = EquipmentSlotGroup.bySlot(getEquipmentSlot());
        var resourcelocation = MalumMod.malumPath("soul_hunter_armor." + type.getName());
        ItemAttributeModifiers.Builder attributes = ItemAttributeModifiers.builder();
        attributes.add(LodestoneAttributes.MAGIC_PROFICIENCY,
                new AttributeModifier(resourcelocation, 0.15f, AttributeModifier.Operation.ADD_MULTIPLIED_BASE),
                group);
        IronsSpellsCompat.addSoulHunterSpellPower(attributes, group);
        return attributes.build().modifiers();
    }

    @Override
    public ResourceLocation getArmorTexture() {
        return MalumMod.malumPath("textures/armor/spirit_hunter_reforged.png");
    }
}
