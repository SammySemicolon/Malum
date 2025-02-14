package com.sammy.malum.data;

import com.sammy.malum.common.data.ImpetusData;
import com.sammy.malum.registry.common.DataMapRegistry;
import com.sammy.malum.registry.common.item.ItemRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.datamaps.builtin.*;

import java.util.concurrent.CompletableFuture;

public class MalumDataMaps extends DataMapProvider {

    protected MalumDataMaps(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    protected void gather() {
        builder(DataMapRegistry.FRACTURED_IMPETUS_VARIANT)
                .add(ItemRegistry.ALCHEMICAL_IMPETUS, new ImpetusData(ItemRegistry.FRACTURED_ALCHEMICAL_IMPETUS), false)
                .add(ItemRegistry.IRON_IMPETUS, new ImpetusData(ItemRegistry.FRACTURED_IRON_IMPETUS), false)
                .add(ItemRegistry.COPPER_IMPETUS, new ImpetusData(ItemRegistry.FRACTURED_COPPER_IMPETUS), false)
                .add(ItemRegistry.GOLD_IMPETUS, new ImpetusData(ItemRegistry.FRACTURED_GOLD_IMPETUS), false)
                .add(ItemRegistry.LEAD_IMPETUS, new ImpetusData(ItemRegistry.FRACTURED_LEAD_IMPETUS), false)
                .add(ItemRegistry.SILVER_IMPETUS, new ImpetusData(ItemRegistry.FRACTURED_SILVER_IMPETUS), false)
                .add(ItemRegistry.ALUMINUM_IMPETUS, new ImpetusData(ItemRegistry.FRACTURED_ALUMINUM_IMPETUS), false)
                .add(ItemRegistry.NICKEL_IMPETUS, new ImpetusData(ItemRegistry.FRACTURED_NICKEL_IMPETUS), false)
                .add(ItemRegistry.URANIUM_IMPETUS, new ImpetusData(ItemRegistry.FRACTURED_URANIUM_IMPETUS), false)
                .add(ItemRegistry.OSMIUM_IMPETUS, new ImpetusData(ItemRegistry.FRACTURED_OSMIUM_IMPETUS), false)
                .add(ItemRegistry.ZINC_IMPETUS, new ImpetusData(ItemRegistry.FRACTURED_ZINC_IMPETUS), false)
                .add(ItemRegistry.TIN_IMPETUS, new ImpetusData(ItemRegistry.FRACTURED_TIN_IMPETUS), false);

        builder(NeoForgeDataMaps.COMPOSTABLES)
                .add(ItemRegistry.RUNEWOOD_SAPLING, new Compostable(0.3f), false)
                .add(ItemRegistry.RUNEWOOD_LEAVES, new Compostable(0.3f), false)
                .add(ItemRegistry.HANGING_RUNEWOOD_LEAVES, new Compostable(0.2f), false)
                .add(ItemRegistry.AZURE_RUNEWOOD_SAPLING, new Compostable(0.3f), false)
                .add(ItemRegistry.AZURE_RUNEWOOD_LEAVES, new Compostable(0.3f), false)
                .add(ItemRegistry.HANGING_AZURE_RUNEWOOD_LEAVES, new Compostable(0.2f), false)
                .add(ItemRegistry.SOULWOOD_GROWTH, new Compostable(0.3f), false)
                .add(ItemRegistry.SOULWOOD_LEAVES, new Compostable(0.3f), false)
                .add(ItemRegistry.HANGING_SOULWOOD_LEAVES, new Compostable(0.2f), false)
                .add(ItemRegistry.BLIGHTED_GUNK, new Compostable(0.1f), false);
    }
}
