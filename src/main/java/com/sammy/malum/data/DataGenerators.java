package com.sammy.malum.data;

import com.sammy.malum.MalumMod;
import com.sammy.malum.data.block.*;
import com.sammy.malum.data.item.MalumEnchantments;
import com.sammy.malum.data.item.MalumItemModels;
import com.sammy.malum.data.item.MalumItemTags;
import com.sammy.malum.data.recipe.*;
import com.sammy.malum.data.worldgen.BiomeModifications;
import com.sammy.malum.data.worldgen.ConfiguredFeatures;
import com.sammy.malum.data.worldgen.PlacedFeatures;
import com.sammy.malum.data.worldgen.WorldGenProvider;
import com.sammy.malum.registry.common.DamageTypeRegistry;
import io.github.fabricators_of_create.porting_lib.data.ExistingFileHelper;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;

import java.util.concurrent.CompletableFuture;



public class DataGenerators implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        var pack = generator.createPack();
        ExistingFileHelper helper = ExistingFileHelper.withResourcesFromArg();

        var items = pack.addProvider(((output, registriesFuture) -> new MalumItemModels(output, helper)));
        var blocks = pack.addProvider(((output, registriesFuture) -> new MalumBlockTags(output, registriesFuture)));
        pack.addProvider((output, registriesFuture) -> new MalumBlockStates(output, helper, items));

        pack.addProvider((output, registriesFuture) -> new MalumBlockLootTables.BlocksLoot(output, registriesFuture));
        pack.addProvider((output, registriesFuture) -> new MalumItemTags(output, registriesFuture, blocks));

        pack.addProvider((output, registriesFuture) -> new MalumRecipes(output, registriesFuture));
        pack.addProvider((output, registriesFuture) -> new MalumEnchantments(output, registriesFuture));

        pack.addProvider((output, registriesFuture) -> new MalumBiomeTags(output, registriesFuture, helper));
        pack.addProvider((output, registriesFuture) -> new MalumDamageTypeTags(output, registriesFuture, helper));

        pack.addProvider((output, registriesFuture) -> new MalumLang(output));

        pack.addProvider((fabricDataOutput, completableFuture) -> new MalumSoundDatagen(fabricDataOutput, helper));

        pack.addProvider((fabricDataOutput, completableFuture) -> new WorldGenProvider(fabricDataOutput, completableFuture));
    }

    @Override
    public void buildRegistry(RegistrySetBuilder registryBuilder) {
        registryBuilder.add(Registries.CONFIGURED_FEATURE, ConfiguredFeatures::bootstrap);
        registryBuilder.add(Registries.PLACED_FEATURE, PlacedFeatures::bootstrap);
        registryBuilder.add(Registries.BIOME, BiomeModifications::bootstrap);
        registryBuilder.add(Registries.DAMAGE_TYPE, DamageTypeRegistry::bootstrap);

    }
}
