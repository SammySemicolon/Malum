package com.sammy.malum.data;

import com.sammy.malum.common.data.ImpetusRepairData;
import com.sammy.malum.registry.common.DataMapRegistry;
import com.sammy.malum.registry.common.item.ItemRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DataMapProvider;

import java.util.concurrent.CompletableFuture;

public class MalumDataMaps extends DataMapProvider {

    protected MalumDataMaps(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    protected void gather() {
        builder(DataMapRegistry.DAMAGED_IMPETUS_VARIANT)
                .add(ItemRegistry.ALCHEMICAL_IMPETUS, new ImpetusRepairData(ItemRegistry.CRACKED_ALCHEMICAL_IMPETUS), false)
                .add(ItemRegistry.IRON_IMPETUS, new ImpetusRepairData(ItemRegistry.CRACKED_IRON_IMPETUS), false)
                .add(ItemRegistry.COPPER_IMPETUS, new ImpetusRepairData(ItemRegistry.CRACKED_COPPER_IMPETUS), false)
                .add(ItemRegistry.GOLD_IMPETUS, new ImpetusRepairData(ItemRegistry.CRACKED_GOLD_IMPETUS), false)
                .add(ItemRegistry.LEAD_IMPETUS, new ImpetusRepairData(ItemRegistry.CRACKED_LEAD_IMPETUS), false)
                .add(ItemRegistry.SILVER_IMPETUS, new ImpetusRepairData(ItemRegistry.CRACKED_SILVER_IMPETUS), false)
                .add(ItemRegistry.ALUMINUM_IMPETUS, new ImpetusRepairData(ItemRegistry.CRACKED_ALUMINUM_IMPETUS), false)
                .add(ItemRegistry.NICKEL_IMPETUS, new ImpetusRepairData(ItemRegistry.CRACKED_NICKEL_IMPETUS), false)
                .add(ItemRegistry.URANIUM_IMPETUS, new ImpetusRepairData(ItemRegistry.CRACKED_URANIUM_IMPETUS), false)
                .add(ItemRegistry.OSMIUM_IMPETUS, new ImpetusRepairData(ItemRegistry.CRACKED_OSMIUM_IMPETUS), false)
                .add(ItemRegistry.ZINC_IMPETUS, new ImpetusRepairData(ItemRegistry.CRACKED_ZINC_IMPETUS), false)
                .add(ItemRegistry.TIN_IMPETUS, new ImpetusRepairData(ItemRegistry.CRACKED_TIN_IMPETUS), false);
    }
}
