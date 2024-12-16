package com.sammy.malum.registry.common.item;

import com.sammy.malum.MalumMod;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.EnumMap;
import java.util.List;
import java.util.function.Supplier;

public class ArmorTiers {

    public static final Holder<ArmorMaterial> SOUL_HUNTER = register("spirit_hunter", Util.make(new EnumMap<>(ArmorItem.Type.class), defense -> {
        defense.put(ArmorItem.Type.BOOTS, 1);
        defense.put(ArmorItem.Type.LEGGINGS, 3);
        defense.put(ArmorItem.Type.CHESTPLATE, 4);
        defense.put(ArmorItem.Type.HELMET, 2);
        defense.put(ArmorItem.Type.BODY, 2);
    }), 16, SoundEvents.ARMOR_EQUIP_LEATHER, 0.0F, 0.0F, () -> Ingredient.of(ItemRegistry.SOULWOVEN_SILK.get()));

    public static final Holder<ArmorMaterial> SOUL_STAINED_STEEL = register("soul_stained_steel", Util.make(new EnumMap<>(ArmorItem.Type.class), defense -> {
        defense.put(ArmorItem.Type.BOOTS, 2);
        defense.put(ArmorItem.Type.LEGGINGS, 6);
        defense.put(ArmorItem.Type.CHESTPLATE, 7);
        defense.put(ArmorItem.Type.HELMET, 3);
        defense.put(ArmorItem.Type.BODY, 4);
    }), 24, SoundEvents.ARMOR_EQUIP_IRON, 2.0F, 0.0F, () -> Ingredient.of(ItemRegistry.SOUL_STAINED_STEEL_PLATING.get()));

    public static final Holder<ArmorMaterial> MALIGNANT_ALLOY = register("malignant_alloy", Util.make(new EnumMap<>(ArmorItem.Type.class), defense -> {
        defense.put(ArmorItem.Type.BOOTS, 3);
        defense.put(ArmorItem.Type.LEGGINGS, 6);
        defense.put(ArmorItem.Type.CHESTPLATE, 8);
        defense.put(ArmorItem.Type.HELMET, 3);
        defense.put(ArmorItem.Type.BODY, 4);
    }), 32, SoundEvents.ARMOR_EQUIP_NETHERITE, 2.0F, 0.1f, () -> Ingredient.of(ItemRegistry.MALIGNANT_PEWTER_PLATING.get()));

    private static Holder<ArmorMaterial> register(
            String name, EnumMap<ArmorItem.Type, Integer> defense, int enchantmentValue,
            Holder<SoundEvent> equipSound, float toughness, float knockbackResistance, Supplier<Ingredient> repairIngredient
    ) {
        List<ArmorMaterial.Layer> list = List.of(new ArmorMaterial.Layer(ResourceLocation.withDefaultNamespace(name)));
        EnumMap<ArmorItem.Type, Integer> enummap = new EnumMap<>(ArmorItem.Type.class);

        for (ArmorItem.Type armoritem$type : ArmorItem.Type.values()) {
            enummap.put(armoritem$type, defense.get(armoritem$type));
        }

        return Registry.registerForHolder(
                BuiltInRegistries.ARMOR_MATERIAL,
                MalumMod.malumPath(name),
                new ArmorMaterial(enummap, enchantmentValue, equipSound, repairIngredient, list, toughness, knockbackResistance)
        );
    }
}