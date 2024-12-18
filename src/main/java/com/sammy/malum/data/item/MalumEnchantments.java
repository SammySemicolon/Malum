package com.sammy.malum.data.item;

import com.mojang.serialization.Codec;
import com.sammy.malum.MalumMod;
import com.sammy.malum.registry.common.item.ItemTagRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricCodecDataProvider;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;

import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class MalumEnchantments extends FabricCodecDataProvider<Enchantment> {

    static final Enchantment.Cost LEGACY_LOWEST = Enchantment.dynamicCost(1, 10);
    static final Enchantment.Cost LEGACY_HIGHEST = Enchantment.dynamicCost(6, 10);

    public MalumEnchantments(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, Registries.ENCHANTMENT, Enchantment.DIRECT_CODEC);
    }

    @Override
    protected void configure(BiConsumer<ResourceLocation, Enchantment> biConsumer, HolderLookup.Provider provider) {
        HolderLookup.RegistryLookup<Item> itemGetter = provider.lookup(Registries.ITEM).get();

        biConsumer.accept(MalumMod.malumPath("rebound"), new Enchantment.Builder(Enchantment.definition(
                itemGetter.getOrThrow(ItemTagRegistry.REBOUND_ENCHANTABLE),
                itemGetter.getOrThrow(ItemTagRegistry.REBOUND_ENCHANTABLE),
                Weights.UNCOMMON, 3, LEGACY_LOWEST, LEGACY_HIGHEST,
                1,
                EquipmentSlotGroup.HAND
        )).build(MalumMod.malumPath("rebound")));

        biConsumer.accept(MalumMod.malumPath("haunted"), new Enchantment.Builder(Enchantment.definition(
                itemGetter.getOrThrow(ItemTagRegistry.ASCENSION_ENCHANTABLE),
                itemGetter.getOrThrow(ItemTagRegistry.ASCENSION_ENCHANTABLE),
                Weights.UNCOMMON, 2, LEGACY_LOWEST, LEGACY_HIGHEST,
                1,
                EquipmentSlotGroup.HAND
        )).build(MalumMod.malumPath("haunted")));

        biConsumer.accept(MalumMod.malumPath("replenishing"), new Enchantment.Builder(Enchantment.definition(
                itemGetter.getOrThrow(ItemTagRegistry.STAVES),
                itemGetter.getOrThrow(ItemTagRegistry.STAVES),
                Weights.COMMON, 2, LEGACY_LOWEST, LEGACY_HIGHEST,
                1,
                EquipmentSlotGroup.HAND
        )).build(MalumMod.malumPath("replenishing")));

        biConsumer.accept(MalumMod.malumPath("spirit_plunder"), new Enchantment.Builder(Enchantment.definition(
                itemGetter.getOrThrow(ItemTagRegistry.SOUL_SHATTER_CAPABLE_WEAPONS),
                itemGetter.getOrThrow(ItemTagRegistry.SOUL_SHATTER_CAPABLE_WEAPONS),
                Weights.COMMON, 2, LEGACY_LOWEST, LEGACY_HIGHEST,
                1,
                EquipmentSlotGroup.HAND
        )).build(MalumMod.malumPath("spirit_plunder")));
    }

    interface Weights {
        int COMMON = 10;
        int UNCOMMON = 5;
        int RARE = 2;
        int VERY_RARE = 1;
    }


    @Override
    public String getName() {
        return "Malum Enchantments";
    }
}
