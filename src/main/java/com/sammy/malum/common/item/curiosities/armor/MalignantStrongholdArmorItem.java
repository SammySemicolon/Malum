package com.sammy.malum.common.item.curiosities.armor;

import com.sammy.malum.MalumMod;
import com.sammy.malum.registry.common.AttributeRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.component.ItemAttributeModifiers;

import java.util.List;

import static com.sammy.malum.registry.common.item.ArmorTiers.MALIGNANT_ALLOY;

public class MalignantStrongholdArmorItem extends MalumArmorItem {

    public MalignantStrongholdArmorItem(Type slot, Properties builder) {
        super(MALIGNANT_ALLOY, slot, builder);
    }

    @Override
    public List<ItemAttributeModifiers.Entry> createExtraAttributes() {
        var group = EquipmentSlotGroup.bySlot(getEquipmentSlot());
        var resourcelocation = MalumMod.malumPath("malignant_stronghold_armor." + type.getName());
        ItemAttributeModifiers.Builder attributes = ItemAttributeModifiers.builder();
        attributes.add(AttributeRegistry.MALIGNANT_CONVERSION,
                new AttributeModifier(resourcelocation, 0.25f, AttributeModifier.Operation.ADD_VALUE),
                group);
        return attributes.build().modifiers();
    }

    @Override
    public ResourceLocation getArmorTexture() {
        return MalumMod.malumPath("textures/armor/malignant_stronghold.png");
    }
}
