package com.sammy.malum.data;

import com.sammy.malum.*;
import com.sammy.malum.data.lang.*;
import com.sammy.malum.data.worldgen.*;
import net.minecraft.core.*;
import net.minecraft.core.registries.*;
import net.minecraft.data.*;
import net.minecraft.data.registries.*;
import net.neoforged.neoforge.common.data.*;
import net.neoforged.neoforge.registries.*;

import java.util.*;
import java.util.concurrent.*;

public class RegistryDataGenerator extends DatapackBuiltinEntriesProvider {

    private static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.ENCHANTMENT, MalumEnchantmentDatagen::bootstrap)
            .add(Registries.DAMAGE_TYPE, MalumDamageTypeDatagen::bootstrap)
            .add(Registries.CONFIGURED_FEATURE, ConfiguredFeatures::bootstrap)
            .add(Registries.PLACED_FEATURE, PlacedFeatures::bootstrap)
            .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, BiomeModifications::bootstrap);

    public RegistryDataGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, RegistryPatchGenerator.createLookup(registries, BUILDER), Set.of("minecraft", MalumMod.MALUM));
    }
}
