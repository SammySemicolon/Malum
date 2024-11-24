package com.sammy.malum.data.item;

import com.sammy.malum.MalumMod;
import com.sammy.malum.registry.common.item.ItemTagRegistry;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class MalumEnchantments extends DatapackBuiltinEntriesProvider {

    static final Enchantment.Cost LEGACY_LOWEST = Enchantment.dynamicCost(1, 10);
    static final Enchantment.Cost LEGACY_HIGHEST = Enchantment.dynamicCost(6, 10);

    interface Weights {
        int COMMON = 10;
        int UNCOMMON = 5;
        int RARE = 2;
        int VERY_RARE = 1;
    }

    private static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.ENCHANTMENT, MalumEnchantments::bootstrap);

    public MalumEnchantments(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(MalumMod.MALUM));
    }

    public static void bootstrap(BootstrapContext<Enchantment> context) {
        HolderGetter<Item> itemGetter = context.lookup(Registries.ITEM);

        context.register(rKey("rebound"), new Enchantment.Builder(Enchantment.definition(
                itemGetter.getOrThrow(ItemTagRegistry.REBOUND_ENCHANTABLE),
                itemGetter.getOrThrow(ItemTagRegistry.REBOUND_ENCHANTABLE),
                Weights.UNCOMMON, 3, LEGACY_LOWEST, LEGACY_HIGHEST,
                1,
                EquipmentSlotGroup.HAND
        )).build(MalumMod.malumPath("rebound")));

        context.register(rKey("haunted"), new Enchantment.Builder(Enchantment.definition(
                itemGetter.getOrThrow(ItemTagRegistry.ASCENSION_ENCHANTABLE),
                itemGetter.getOrThrow(ItemTagRegistry.ASCENSION_ENCHANTABLE),
                Weights.UNCOMMON, 2, LEGACY_LOWEST, LEGACY_HIGHEST,
                1,
                EquipmentSlotGroup.HAND
        )).build(MalumMod.malumPath("haunted")));

        context.register(rKey("replenishing"), new Enchantment.Builder(Enchantment.definition(
                itemGetter.getOrThrow(ItemTagRegistry.STAFF),
                itemGetter.getOrThrow(ItemTagRegistry.STAFF),
                Weights.COMMON, 2, LEGACY_LOWEST, LEGACY_HIGHEST,
                1,
                EquipmentSlotGroup.HAND
        )).build(MalumMod.malumPath("replenishing")));

        context.register(rKey("spirit_plunder"), new Enchantment.Builder(Enchantment.definition(
                itemGetter.getOrThrow(ItemTagRegistry.SOUL_HUNTER_WEAPON),
                itemGetter.getOrThrow(ItemTagRegistry.SOUL_HUNTER_WEAPON),
                Weights.COMMON, 2, LEGACY_LOWEST, LEGACY_HIGHEST,
                1,
                EquipmentSlotGroup.HAND
        )).build(MalumMod.malumPath("spirit_plunder")));
    }

    static ResourceKey<Enchantment> rKey(String id) {
        return ResourceKey.create(Registries.ENCHANTMENT, MalumMod.malumPath(id));
    }

    @Override
    public String getName() {
        return "Malum Enchantments";
    }
}
