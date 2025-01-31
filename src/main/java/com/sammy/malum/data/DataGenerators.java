package com.sammy.malum.data;

import com.sammy.malum.MalumMod;
import com.sammy.malum.data.block.*;
import com.sammy.malum.data.item.MalumEnchantments;
import com.sammy.malum.data.item.MalumItemModels;
import com.sammy.malum.data.item.MalumItemTags;
import com.sammy.malum.data.lang.*;
import com.sammy.malum.data.recipe.*;
import net.minecraft.core.*;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.*;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.*;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = MalumMod.MALUM, bus = EventBusSubscriber.Bus.MOD)
public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> provider = event.getLookupProvider();
        ExistingFileHelper helper = event.getExistingFileHelper();

        final boolean includeClient = event.includeClient();
        final boolean includeServer = event.includeServer();

        var registryDataProvider = new RegistryDataGenerator(output, provider);
        generator.addProvider(includeServer, registryDataProvider);
        var registryProvider = registryDataProvider.getRegistryProvider();

        var itemModelsProvider = new MalumItemModels(output, helper);
        var blockTagsProvider = new MalumBlockTags(output, provider, helper);

        generator.addProvider(includeClient, new MalumBlockStates(output, helper, itemModelsProvider));
        generator.addProvider(includeClient, itemModelsProvider);

        generator.addProvider(includeServer, blockTagsProvider);
        generator.addProvider(includeServer, new MalumBlockLootTables(output, provider));
        generator.addProvider(includeServer, new MalumItemTags(output, provider, blockTagsProvider.contentsGetter(), helper));

        generator.addProvider(includeServer, new MalumRecipes(output, provider));
        generator.addProvider(includeServer, new MalumEnchantments(output, provider));

        generator.addProvider(includeServer, new MalumBiomeTags(output, registryProvider, helper));
        generator.addProvider(includeServer, new MalumDamageTypeTags(output, registryProvider, helper));
        generator.addProvider(includeServer, new MalumEnchantmentTags(output, registryProvider, helper));

        generator.addProvider(includeClient, new MalumLang(output));

        generator.addProvider(includeServer, new MalumCuriosThings(output, helper, provider));

        generator.addProvider(event.includeDev(), new MalumSoundDatagen(output, helper));

    }
}
