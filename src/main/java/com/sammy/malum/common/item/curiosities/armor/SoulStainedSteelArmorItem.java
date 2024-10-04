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
        return List.of(
                new ItemAttributeModifiers.Entry(
                        AttributeRegistry.SOUL_WARD_CAP,
                        new AttributeModifier(MalumMod.malumPath("soul_ward_cap"), 3f, AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.ARMOR),

                new ItemAttributeModifiers.Entry(
                        AttributeRegistry.SOUL_WARD_RECOVERY_RATE,
                        new AttributeModifier(MalumMod.malumPath("soul_ward_recovery"), 0.15f, AttributeModifier.Operation.ADD_MULTIPLIED_BASE),
                        EquipmentSlotGroup.ARMOR)
        );
    }
    @Override
    public ResourceLocation getArmorTexture() {
        return MalumMod.malumPath("textures/armor/soul_stained_steel_reforged");
    }
}