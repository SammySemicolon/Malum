package com.sammy.malum.data.worldgen;

import com.sammy.malum.MalumMod;
import com.sammy.malum.registry.common.worldgen.BiomeTagRegistry;
import com.sammy.malum.registry.common.worldgen.PlacedFeatureRegistry;
import io.github.fabricators_of_create.porting_lib.tags.Tags;
import net.fabricmc.fabric.api.biome.v1.BiomeModification;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;

@SuppressWarnings("unchecked")
public class BiomeModifications {

    public static void bootstrap(BootstrapContext<Biome> biomeBootstrapContext) {
        init();
    }

    public static void init() {
        BiomeModification modifications = net.fabricmc.fabric.api.biome.v1.BiomeModifications.create(MalumMod.malumPath("worldgen"));
        modifications.add(ModificationPhase.ADDITIONS, BiomeSelectors.tag(BiomeTagRegistry.HAS_SOULSTONE), (biomeSelectionContext, biomeModificationContext) -> {
            biomeModificationContext.getGenerationSettings().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, PlacedFeatureRegistry.ORE_SOULSTONE);
        });

        modifications.add(ModificationPhase.ADDITIONS, BiomeSelectors.tag(BiomeTagRegistry.HAS_BRILLIANT), (biomeSelectionContext, biomeModificationContext) -> {
            biomeModificationContext.getGenerationSettings().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, PlacedFeatureRegistry.ORE_BRILLIANT);
        });

        modifications.add(ModificationPhase.ADDITIONS, BiomeSelectors.tag(BiomeTagRegistry.HAS_QUARTZ), (biomeSelectionContext, biomeModificationContext) -> {
            biomeModificationContext.getGenerationSettings().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, PlacedFeatureRegistry.ORE_NATURAL_QUARTZ);
        });

        modifications.add(ModificationPhase.ADDITIONS, BiomeSelectors.tag(BiomeTagRegistry.HAS_CTHONIC), (biomeSelectionContext, biomeModificationContext) -> {
            biomeModificationContext.getGenerationSettings().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, PlacedFeatureRegistry.ORE_CTHONIC_GOLD);
        });

        modifications.add(ModificationPhase.ADDITIONS, BiomeSelectors.tag(BiomeTagRegistry.HAS_BLAZING_QUARTZ), (biomeSelectionContext, biomeModificationContext) -> {
            biomeModificationContext.getGenerationSettings().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, PlacedFeatureRegistry.ORE_BLAZING_QUARTZ);
        });

        modifications.add(ModificationPhase.ADDITIONS, BiomeSelectors.tag(BiomeTagRegistry.HAS_RUNEWOOD), (biomeSelectionContext, biomeModificationContext) -> {
            biomeModificationContext.getGenerationSettings().addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, PlacedFeatureRegistry.RUNEWOOD_TREE);
        });

        modifications.add(ModificationPhase.ADDITIONS, BiomeSelectors.tag(BiomeTagRegistry.HAS_RARE_RUNEWOOD), (biomeSelectionContext, biomeModificationContext) -> {
            biomeModificationContext.getGenerationSettings().addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, PlacedFeatureRegistry.RARE_RUNEWOOD_TREE);
        });

        modifications.add(ModificationPhase.ADDITIONS, BiomeSelectors.tag(BiomeTagRegistry.HAS_AZURE_RUNEWOOD), (biomeSelectionContext, biomeModificationContext) -> {
            biomeModificationContext.getGenerationSettings().addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, PlacedFeatureRegistry.AZURE_RUNEWOOD_TREE);
        });

        modifications.add(ModificationPhase.ADDITIONS, BiomeSelectors.tag(BiomeTagRegistry.HAS_RARE_AZURE_RUNEWOOD), (biomeSelectionContext, biomeModificationContext) -> {
            biomeModificationContext.getGenerationSettings().addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, PlacedFeatureRegistry.RARE_AZURE_RUNEWOOD_TREE);
        });

        modifications.add(ModificationPhase.ADDITIONS, BiomeSelectors.tag(BiomeTagRegistry.HAS_QUARTZ), (biomeSelectionContext, biomeModificationContext) -> {
            biomeModificationContext.getGenerationSettings().addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, PlacedFeatureRegistry.QUARTZ_GEODE_FEATURE);
        });

        modifications.add(ModificationPhase.ADDITIONS, BiomeSelectors.tag(BiomeTagRegistry.HAS_QUARTZ), (biomeSelectionContext, biomeModificationContext) -> {
            biomeModificationContext.getGenerationSettings().addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, PlacedFeatureRegistry.DEEPSLATE_QUARTZ_GEODE_FEATURE);
        });


        //Remove
        modifications.add(ModificationPhase.REMOVALS, BiomeSelectors.tag(Tags.Biomes.IS_COLD_OVERWORLD), ctx -> {
            ctx.getGenerationSettings().removeFeature(PlacedFeatureRegistry.RUNEWOOD_TREE);
        });

        modifications.add(ModificationPhase.REMOVALS, BiomeSelectors.tag(Tags.Biomes.IS_COLD_OVERWORLD),ctx -> {
            ctx.getGenerationSettings().removeFeature(PlacedFeatureRegistry.RARE_RUNEWOOD_TREE);
        });
    }
}
