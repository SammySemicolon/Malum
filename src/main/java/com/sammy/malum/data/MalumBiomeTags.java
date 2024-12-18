package com.sammy.malum.data;

import com.sammy.malum.*;
import com.sammy.malum.registry.common.worldgen.*;
import io.github.fabricators_of_create.porting_lib.data.ExistingFileHelper;
import io.github.fabricators_of_create.porting_lib.tags.Tags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.*;
import net.minecraft.data.tags.*;
import net.minecraft.tags.*;
import net.minecraft.world.level.biome.*;
import org.jetbrains.annotations.*;

import java.util.concurrent.*;

public class MalumBiomeTags extends FabricTagProvider<Biome> {

    public MalumBiomeTags(FabricDataOutput pOutput, CompletableFuture<HolderLookup.Provider> pProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, Registries.BIOME, pProvider);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        //super.addTags(pProvider);
        getOrCreateTagBuilder(BiomeTagRegistry.HAS_SOULSTONE).addOptionalTag(BiomeTags.IS_OVERWORLD);
        getOrCreateTagBuilder(BiomeTagRegistry.HAS_BRILLIANT).addOptionalTag(BiomeTags.IS_OVERWORLD);
        getOrCreateTagBuilder(BiomeTagRegistry.HAS_BLAZING_QUARTZ).addOptionalTag(BiomeTags.IS_NETHER);
        getOrCreateTagBuilder(BiomeTagRegistry.HAS_QUARTZ).addOptionalTag(BiomeTags.IS_OVERWORLD);
        getOrCreateTagBuilder(BiomeTagRegistry.HAS_CTHONIC).addOptionalTag(BiomeTags.IS_OVERWORLD);

        getOrCreateTagBuilder(BiomeTagRegistry.HAS_RUNEWOOD).addOptionalTag(Tags.Biomes.IS_PLAINS).addOptionalTag(Tags.Biomes.IS_MOUNTAIN).addOptionalTag(BiomeTags.IS_HILL);
        getOrCreateTagBuilder(BiomeTagRegistry.HAS_RARE_RUNEWOOD).addOptionalTag(BiomeTags.IS_FOREST);

        getOrCreateTagBuilder(BiomeTagRegistry.HAS_AZURE_RUNEWOOD).add(Biomes.SNOWY_PLAINS).add(Biomes.SNOWY_TAIGA).add(Biomes.FROZEN_RIVER).add(Biomes.SNOWY_BEACH);
        getOrCreateTagBuilder(BiomeTagRegistry.HAS_RARE_AZURE_RUNEWOOD).add(Biomes.FROZEN_PEAKS).add(Biomes.JAGGED_PEAKS).add(Biomes.SNOWY_SLOPES).add(Biomes.GROVE);
    }
}
