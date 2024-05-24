package com.sammy.malum.data;

import com.sammy.malum.MalumMod;
import com.sammy.malum.data.block.MalumBlockLootTables;
import com.sammy.malum.data.block.MalumBlockStates;
import com.sammy.malum.data.block.MalumBlockTags;
import com.sammy.malum.data.item.MalumItemModels;
import com.sammy.malum.data.item.MalumItemTags;
import com.sammy.malum.data.recipe.*;
import com.sammy.malum.data.worldgen.*;
import io.github.fabricators_of_create.porting_lib.data.ExistingFileHelper;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class DataGenerators implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        var pack = generator.createPack();
        ExistingFileHelper helper = ExistingFileHelper.withResourcesFromArg();

        var items = pack.addProvider(((output, registriesFuture) -> new MalumItemModels(output, helper)));
        var blocks = pack.addProvider(((output, registriesFuture) -> new MalumBlockTags(output, registriesFuture)));

        pack.addProvider((output, registriesFuture) -> new MalumBlockStates(output, helper, items));
        pack.addProvider((output, registriesFuture) -> new MalumBlockLootTables(output));
        pack.addProvider((output, registriesFuture) -> new MalumItemTags(output, registriesFuture, blocks.contentsGetter()));
        pack.addProvider((output, registriesFuture) -> new MalumRecipes(output));
        pack.addProvider((output, registriesFuture) -> new MalumBiomeTags(output, registriesFuture, helper));
        pack.addProvider((output, registriesFuture) -> new MalumDamageTypeTags(output, registriesFuture, helper));
        pack.addProvider((output, registriesFuture) -> new WorldgenRegistryDataGenerator(output, registriesFuture));
        pack.addProvider((output, registriesFuture) -> new MalumLang(output));
        //TODO ? pack.addProvider((output, registriesFuture) -> new MalumCuriosThings(output, helper, registriesFuture));

    }
}
