package com.sammy.malum.common.item.curiosities.armor;

import com.sammy.malum.*;
import com.sammy.malum.registry.common.AttributeRegistry;
import net.minecraft.resources.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.component.*;

import java.util.*;

import static com.sammy.malum.registry.common.item.ArmorTiers.SOUL_STAINED_STEEL;

public class SoulStainedSteelArmorItem extends MalumArmorItem {
    public SoulStainedSteelArmorItem(ArmorItem.Type slot, Properties builder) {
        super(SOUL_STAINED_STEEL, slot, builder);
    }

    @Override
    public List<ItemAttributeModifiers.Entry> createExtraAttributes() {
        var group = EquipmentSlotGroup.bySlot(getEquipmentSlot());
        var resourcelocation = MalumMod.malumPath("armor." + type.getName());
        return List.of(
                new ItemAttributeModifiers.Entry(
                        AttributeRegistry.SOUL_WARD_CAPACITY,
                        new AttributeModifier(resourcelocation, 3f, AttributeModifier.Operation.ADD_VALUE),
                        group),

                new ItemAttributeModifiers.Entry(
                        AttributeRegistry.SOUL_WARD_RECOVERY_RATE,
                        new AttributeModifier(resourcelocation, 0.15f, AttributeModifier.Operation.ADD_MULTIPLIED_BASE),
                        group)
        );
    }
    @Override
    public ResourceLocation getArmorTexture() {
        return MalumMod.malumPath("textures/armor/soul_stained_steel_reforged");
    }
}