package com.sammy.malum.data.recipe;

import com.mojang.datafixers.util.*;
import com.sammy.malum.data.recipe.builder.*;
import net.minecraft.data.recipes.*;
import net.minecraft.world.level.block.*;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.*;

import static com.sammy.malum.registry.common.block.BlockRegistry.*;

public class MalumSpiritTransmutationRecipes {

    private static final List<Pair<DeferredHolder<Block, Block>, DeferredHolder<Block, Block>>> SOULWOOD_TRANSMUTATIONS = List.of(
            new Pair<>(RUNEWOOD_TOTEM_BASE, SOULWOOD_TOTEM_BASE),
            new Pair<>(RUNEWOOD_SAPLING, SOULWOOD_GROWTH),
            new Pair<>(RUNEWOOD_LEAVES, SOULWOOD_LEAVES),
            new Pair<>(STRIPPED_RUNEWOOD_LOG, STRIPPED_SOULWOOD_LOG),
            new Pair<>(RUNEWOOD_LOG, SOULWOOD_LOG),
            new Pair<>(STRIPPED_RUNEWOOD, STRIPPED_SOULWOOD),
            new Pair<>(RUNEWOOD, SOULWOOD),
            new Pair<>(REVEALED_RUNEWOOD_LOG, REVEALED_SOULWOOD_LOG),
            new Pair<>(EXPOSED_RUNEWOOD_LOG, EXPOSED_SOULWOOD_LOG),
            new Pair<>(RUNEWOOD_BOARDS, SOULWOOD_BOARDS),
            new Pair<>(RUNEWOOD_BOARDS_SLAB, SOULWOOD_BOARDS_SLAB),
            new Pair<>(RUNEWOOD_BOARDS_STAIRS, SOULWOOD_BOARDS_STAIRS),
            new Pair<>(VERTICAL_RUNEWOOD_BOARDS, VERTICAL_SOULWOOD_BOARDS),
            new Pair<>(VERTICAL_RUNEWOOD_BOARDS_SLAB, VERTICAL_SOULWOOD_BOARDS_SLAB),
            new Pair<>(VERTICAL_RUNEWOOD_BOARDS_STAIRS, VERTICAL_SOULWOOD_BOARDS_STAIRS),
            new Pair<>(RUNEWOOD_PLANKS, SOULWOOD_PLANKS),
            new Pair<>(RUNEWOOD_PLANKS_SLAB, SOULWOOD_PLANKS_SLAB),
            new Pair<>(RUNEWOOD_PLANKS_STAIRS, SOULWOOD_PLANKS_STAIRS),
            new Pair<>(RUSTIC_RUNEWOOD_PLANKS, RUSTIC_SOULWOOD_PLANKS),
            new Pair<>(RUSTIC_RUNEWOOD_PLANKS_SLAB, RUSTIC_SOULWOOD_PLANKS_SLAB),
            new Pair<>(RUSTIC_RUNEWOOD_PLANKS_STAIRS, RUSTIC_SOULWOOD_PLANKS_STAIRS),
            new Pair<>(VERTICAL_RUNEWOOD_PLANKS, VERTICAL_SOULWOOD_PLANKS),
            new Pair<>(VERTICAL_RUNEWOOD_PLANKS_SLAB, VERTICAL_SOULWOOD_PLANKS_SLAB),
            new Pair<>(VERTICAL_RUNEWOOD_PLANKS_STAIRS, VERTICAL_SOULWOOD_PLANKS_STAIRS),
            new Pair<>(VERTICAL_RUSTIC_RUNEWOOD_PLANKS, VERTICAL_RUSTIC_SOULWOOD_PLANKS),
            new Pair<>(VERTICAL_RUSTIC_RUNEWOOD_PLANKS_SLAB, VERTICAL_RUSTIC_SOULWOOD_PLANKS_SLAB),
            new Pair<>(VERTICAL_RUSTIC_RUNEWOOD_PLANKS_STAIRS, VERTICAL_RUSTIC_SOULWOOD_PLANKS_STAIRS),
            new Pair<>(RUNEWOOD_TILES, SOULWOOD_TILES),
            new Pair<>(RUNEWOOD_TILES_SLAB, SOULWOOD_TILES_SLAB),
            new Pair<>(RUNEWOOD_TILES_STAIRS, SOULWOOD_TILES_STAIRS),
            new Pair<>(RUNEWOOD_PANEL, SOULWOOD_PANEL),
            new Pair<>(CUT_RUNEWOOD_PLANKS, CUT_SOULWOOD_PLANKS),
            new Pair<>(RUNEWOOD_BEAM, SOULWOOD_BEAM),
            new Pair<>(RUNEWOOD_DOOR, SOULWOOD_DOOR),
            new Pair<>(SOULWOOD_DOOR, BOLTED_SOULWOOD_DOOR),
            new Pair<>(RUNEWOOD_TRAPDOOR, SOULWOOD_TRAPDOOR),
            new Pair<>(BOLTED_RUNEWOOD_TRAPDOOR, BOLTED_SOULWOOD_TRAPDOOR),
            new Pair<>(RUNEWOOD_BUTTON, SOULWOOD_BUTTON),
            new Pair<>(RUNEWOOD_PRESSURE_PLATE, SOULWOOD_PRESSURE_PLATE),
            new Pair<>(RUNEWOOD_FENCE, SOULWOOD_FENCE),
            new Pair<>(RUNEWOOD_FENCE_GATE, SOULWOOD_FENCE_GATE),
            new Pair<>(RUNEWOOD_BOARDS_WALL, SOULWOOD_BOARDS_WALL),
            new Pair<>(RUNEWOOD_ITEM_STAND, SOULWOOD_ITEM_STAND),
            new Pair<>(RUNEWOOD_ITEM_PEDESTAL, SOULWOOD_ITEM_PEDESTAL),
            new Pair<>(RUNEWOOD_SIGN, SOULWOOD_SIGN) // Wall sign already handled by this. Is it??? Wire? Huh ? How
    );

    protected static void buildRecipes(RecipeOutput recipeOutput) {
        for (var transmutation : SOULWOOD_TRANSMUTATIONS) {
            new SpiritTransmutationRecipeBuilder(transmutation.getFirst().get(), transmutation.getSecond().get())
                    .group("soulwood")
                    .save(recipeOutput, "soulwood/" + transmutation.getSecond().getId().getPath().replace("soulwood_", "").replace("_soulwood", ""));
        }

        new SpiritTransmutationRecipeBuilder(Blocks.STONE, Blocks.COBBLESTONE)
                .save(recipeOutput, "stone_to_cobblestone");

        new SpiritTransmutationRecipeBuilder(Blocks.COBBLESTONE, Blocks.GRAVEL)
                .save(recipeOutput, "cobblestone_to_gravel");

        new SpiritTransmutationRecipeBuilder(Blocks.GRAVEL, Blocks.SAND)
                .save(recipeOutput, "gravel_to_sand");

        new SpiritTransmutationRecipeBuilder(Blocks.SAND, BLIGHTED_SOIL.get())
                .save(recipeOutput, "sand_to_blighted_soil");

        new SpiritTransmutationRecipeBuilder(Blocks.ANDESITE, Blocks.TUFF)
                .save(recipeOutput, "andesite_to_tuff");

        new SpiritTransmutationRecipeBuilder(Blocks.TUFF, Blocks.STONE)
                .save(recipeOutput, "tuff_block_to_stone");

        new SpiritTransmutationRecipeBuilder(Blocks.GRANITE, Blocks.DRIPSTONE_BLOCK)
                .save(recipeOutput, "granite_to_dripstone_block");

        new SpiritTransmutationRecipeBuilder(Blocks.DRIPSTONE_BLOCK, Blocks.STONE)
                .save(recipeOutput, "dripstone_block_to_stone");

        new SpiritTransmutationRecipeBuilder(Blocks.DIORITE, Blocks.CALCITE)
                .save(recipeOutput, "diorite_to_calcite");

        new SpiritTransmutationRecipeBuilder(Blocks.CALCITE, Blocks.STONE)
                .save(recipeOutput, "calcite_to_stone");

        new SpiritTransmutationRecipeBuilder(Blocks.DEEPSLATE, Blocks.COBBLED_DEEPSLATE)
                .save(recipeOutput, "deepslate_to_cobbled_deepslate");

        new SpiritTransmutationRecipeBuilder(Blocks.COBBLED_DEEPSLATE, Blocks.BASALT)
                .save(recipeOutput, "cobbled_deepslate_to_basalt");

        new SpiritTransmutationRecipeBuilder(Blocks.BASALT, Blocks.NETHERRACK)
                .save(recipeOutput, "basalt_to_netherrack");

        new SpiritTransmutationRecipeBuilder(Blocks.NETHERRACK, Blocks.SOUL_SAND)
                .save(recipeOutput, "netherrack_to_soul_sand");

        new SpiritTransmutationRecipeBuilder(Blocks.SOUL_SAND, Blocks.RED_SAND)
                .save(recipeOutput, "soul_sand_to_red_sand");

        new SpiritTransmutationRecipeBuilder(Blocks.RED_SAND, BLIGHTED_SOIL.get())
                .save(recipeOutput, "red_sand_to_blighted_soil");

        new SpiritTransmutationRecipeBuilder(Blocks.SMOOTH_BASALT, Blocks.CLAY)
                .save(recipeOutput, "smooth_basalt_to_clay");

        new SpiritTransmutationRecipeBuilder(Blocks.CLAY, Blocks.PRISMARINE)
                .save(recipeOutput, "clay_to_prismarine");

        new SpiritTransmutationRecipeBuilder(Blocks.PRISMARINE, Blocks.SEA_LANTERN)
                .save(recipeOutput, "prismarine_to_sea_lantern");

        new SpiritTransmutationRecipeBuilder(Blocks.SEA_LANTERN, Blocks.SNOW_BLOCK)
                .save(recipeOutput, "sea_lantern_to_snow_block");

        new SpiritTransmutationRecipeBuilder(Blocks.SNOW_BLOCK, Blocks.ICE)
                .save(recipeOutput, "snow_block_to_ice");

        new SpiritTransmutationRecipeBuilder(Blocks.ICE, BLIGHTED_SOIL.get())
                .save(recipeOutput, "ice_to_blighted_soil");
    }
}
