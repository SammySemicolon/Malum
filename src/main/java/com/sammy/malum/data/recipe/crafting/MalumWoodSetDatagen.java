package com.sammy.malum.data.recipe.crafting;

import com.sammy.malum.data.item.*;
import com.sammy.malum.registry.common.item.*;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.*;
import net.minecraft.tags.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.neoforged.neoforge.common.*;
import net.neoforged.neoforge.common.conditions.*;

import java.util.function.*;

import static com.sammy.malum.MalumMod.*;
import static com.sammy.malum.data.recipe.MalumVanillaRecipes.*;
import static net.minecraft.data.recipes.RecipeBuilder.*;
import static net.minecraft.data.recipes.ShapedRecipeBuilder.*;
import static net.minecraft.data.recipes.ShapelessRecipeBuilder.*;

public class MalumWoodSetDatagen implements IConditionBuilder {

    private static final MalumDatagenWoodSet RUNEWOOD = new MalumDatagenWoodSet(
            "runewood",
            ItemRegistry.RUNEWOOD_LOG.get(), ItemRegistry.RUNEWOOD.get(),
            ItemRegistry.STRIPPED_RUNEWOOD_LOG.get(), ItemRegistry.STRIPPED_RUNEWOOD.get(),
            ItemRegistry.REVEALED_RUNEWOOD_LOG.get(), ItemRegistry.EXPOSED_RUNEWOOD_LOG.get(),
            ItemRegistry.RUNEWOOD_BOARDS.get(), ItemRegistry.VERTICAL_RUNEWOOD_BOARDS.get(),
            ItemRegistry.RUNEWOOD_BOARDS_SLAB.get(), ItemRegistry.VERTICAL_RUNEWOOD_BOARDS_SLAB.get(),
            ItemRegistry.RUNEWOOD_BOARDS_STAIRS.get(), ItemRegistry.VERTICAL_RUNEWOOD_BOARDS_STAIRS.get(),
            ItemRegistry.RUNEWOOD_PLANKS.get(), ItemRegistry.VERTICAL_RUNEWOOD_PLANKS.get(), ItemRegistry.RUNEWOOD_TILES.get(),
            ItemRegistry.RUSTIC_RUNEWOOD_PLANKS.get(), ItemRegistry.VERTICAL_RUSTIC_RUNEWOOD_PLANKS.get(), ItemRegistry.RUSTIC_RUNEWOOD_TILES.get(),
            ItemRegistry.RUNEWOOD_PLANKS_SLAB.get(), ItemRegistry.VERTICAL_RUNEWOOD_PLANKS_SLAB.get(), ItemRegistry.RUNEWOOD_TILES_SLAB.get(),
            ItemRegistry.RUSTIC_RUNEWOOD_PLANKS_SLAB.get(), ItemRegistry.VERTICAL_RUSTIC_RUNEWOOD_PLANKS_SLAB.get(), ItemRegistry.RUSTIC_RUNEWOOD_TILES_SLAB.get(),
            ItemRegistry.RUNEWOOD_PLANKS_STAIRS.get(), ItemRegistry.VERTICAL_RUNEWOOD_PLANKS_STAIRS.get(), ItemRegistry.RUNEWOOD_TILES_STAIRS.get(),
            ItemRegistry.RUSTIC_RUNEWOOD_PLANKS_STAIRS.get(), ItemRegistry.VERTICAL_RUSTIC_RUNEWOOD_PLANKS_STAIRS.get(), ItemRegistry.RUSTIC_RUNEWOOD_TILES_STAIRS.get(),
            ItemRegistry.RUNEWOOD_PANEL.get(), ItemRegistry.CUT_RUNEWOOD_PLANKS.get(), ItemRegistry.RUNEWOOD_BEAM.get(),
            ItemRegistry.RUNEWOOD_BUTTON.get(), ItemRegistry.RUNEWOOD_PRESSURE_PLATE.get(),
            ItemRegistry.SOLID_RUNEWOOD_TRAPDOOR.get(), ItemRegistry.RUNEWOOD_TRAPDOOR.get(),
            ItemRegistry.RUNEWOOD_FENCE.get(), ItemRegistry.RUNEWOOD_FENCE_GATE.get(),
            ItemRegistry.RUNEWOOD_BOARDS_WALL.get(),
            ItemRegistry.RUNEWOOD_DOOR.get(),
            ItemRegistry.RUNEWOOD_SIGN.get(), ItemRegistry.RUNEWOOD_SIGN.get(),
            ItemRegistry.RUNEWOOD_ITEM_STAND.get(), ItemRegistry.RUNEWOOD_ITEM_PEDESTAL.get(),
            ItemTagRegistry.RUNEWOOD_LOGS, ItemTagRegistry.RUNEWOOD_BOARD_INGREDIENT, ItemTagRegistry.RUNEWOOD_PLANKS, ItemTagRegistry.RUNEWOOD_STAIRS, ItemTagRegistry.RUNEWOOD_SLABS,
            ItemRegistry.RUNEWOOD_BOAT.get()
    );

    private static final MalumDatagenWoodSet SOULWOOD = new MalumDatagenWoodSet(
            "soulwood",
            ItemRegistry.SOULWOOD_LOG.get(), ItemRegistry.SOULWOOD.get(),
            ItemRegistry.STRIPPED_SOULWOOD_LOG.get(), ItemRegistry.STRIPPED_SOULWOOD.get(),
            ItemRegistry.REVEALED_SOULWOOD_LOG.get(), ItemRegistry.EXPOSED_SOULWOOD_LOG.get(),
            ItemRegistry.SOULWOOD_BOARDS.get(), ItemRegistry.VERTICAL_SOULWOOD_BOARDS.get(),
            ItemRegistry.SOULWOOD_BOARDS_SLAB.get(), ItemRegistry.VERTICAL_SOULWOOD_BOARDS_SLAB.get(),
            ItemRegistry.SOULWOOD_BOARDS_STAIRS.get(), ItemRegistry.VERTICAL_SOULWOOD_BOARDS_STAIRS.get(),
            ItemRegistry.SOULWOOD_PLANKS.get(), ItemRegistry.VERTICAL_SOULWOOD_PLANKS.get(), ItemRegistry.SOULWOOD_TILES.get(),
            ItemRegistry.RUSTIC_SOULWOOD_PLANKS.get(), ItemRegistry.VERTICAL_RUSTIC_SOULWOOD_PLANKS.get(), ItemRegistry.RUSTIC_SOULWOOD_TILES.get(),
            ItemRegistry.SOULWOOD_PLANKS_SLAB.get(), ItemRegistry.VERTICAL_SOULWOOD_PLANKS_SLAB.get(), ItemRegistry.SOULWOOD_TILES_SLAB.get(),
            ItemRegistry.RUSTIC_SOULWOOD_PLANKS_SLAB.get(), ItemRegistry.VERTICAL_RUSTIC_SOULWOOD_PLANKS_SLAB.get(), ItemRegistry.RUSTIC_SOULWOOD_TILES_SLAB.get(),
            ItemRegistry.SOULWOOD_PLANKS_STAIRS.get(), ItemRegistry.VERTICAL_SOULWOOD_PLANKS_STAIRS.get(), ItemRegistry.SOULWOOD_TILES_STAIRS.get(),
            ItemRegistry.RUSTIC_SOULWOOD_PLANKS_STAIRS.get(), ItemRegistry.VERTICAL_RUSTIC_SOULWOOD_PLANKS_STAIRS.get(), ItemRegistry.RUSTIC_SOULWOOD_TILES_STAIRS.get(),
            ItemRegistry.SOULWOOD_PANEL.get(), ItemRegistry.CUT_SOULWOOD_PLANKS.get(), ItemRegistry.SOULWOOD_BEAM.get(),
            ItemRegistry.SOULWOOD_BUTTON.get(), ItemRegistry.SOULWOOD_PRESSURE_PLATE.get(),
            ItemRegistry.SOLID_SOULWOOD_TRAPDOOR.get(), ItemRegistry.SOULWOOD_TRAPDOOR.get(),
            ItemRegistry.SOULWOOD_FENCE.get(), ItemRegistry.SOULWOOD_FENCE_GATE.get(),
            ItemRegistry.SOULWOOD_BOARDS_WALL.get(),
            ItemRegistry.SOULWOOD_DOOR.get(),
            ItemRegistry.SOULWOOD_SIGN.get(), ItemRegistry.SOULWOOD_SIGN.get(),
            ItemRegistry.SOULWOOD_ITEM_STAND.get(), ItemRegistry.SOULWOOD_ITEM_PEDESTAL.get(),
            ItemTagRegistry.SOULWOOD_LOGS, ItemTagRegistry.SOULWOOD_BOARD_INGREDIENT, ItemTagRegistry.SOULWOOD_PLANKS, ItemTagRegistry.SOULWOOD_STAIRS, ItemTagRegistry.SOULWOOD_SLABS,
            ItemRegistry.SOULWOOD_BOAT.get()
    );

    public static void addTags(MalumItemTags provider) {
        addTags(provider, RUNEWOOD);
        addTags(provider, SOULWOOD);
    }

    public static void buildRecipes(RecipeOutput recipeOutput) {
        buildRecipes(recipeOutput, RUNEWOOD);
        buildRecipes(recipeOutput, SOULWOOD);
    }

    protected static void addTags(MalumItemTags provider, MalumDatagenWoodSet woodSet) {
        provider.tag(woodSet.logTag).add(
                woodSet.log, woodSet.strippedLog, woodSet.wood, woodSet.strippedWood, woodSet.sapFilledLog, woodSet.strippedSapFilledLog);
        provider.tag(woodSet.boardIngredientTag).add(woodSet.log, woodSet.wood);
        provider.tag(woodSet.planksTag).add(
                woodSet.boards, woodSet.verticalBoards,
                woodSet.planks, woodSet.verticalPlanks,
                woodSet.rusticPlanks, woodSet.verticalRusticPlanks,
                woodSet.tiles, woodSet.rusticTiles
        );
        provider.tag(woodSet.stairsTag).add(
                woodSet.boardsStairs, woodSet.verticalBoardsStairs,
                woodSet.planksStairs, woodSet.verticalPlanksStairs,
                woodSet.rusticPlanksStairs, woodSet.verticalRusticPlanksStairs,
                woodSet.tilesStairs, woodSet.rusticTilesStairs
        );
        provider.tag(woodSet.slabTag).add(
                woodSet.boardsSlab, woodSet.verticalBoardsSlab,
                woodSet.planksSlab, woodSet.verticalPlanksSlab,
                woodSet.rusticPlanksSlab, woodSet.verticalRusticPlanksSlab,
                woodSet.tilesSlab, woodSet.rusticTilesSlab
        );
    }

    protected static void buildRecipes(RecipeOutput recipeOutput, MalumDatagenWoodSet woodSet) {
        shapelessPlanks(recipeOutput, woodSet.planks, woodSet.logTag);

        rusticExchange(recipeOutput, woodSet.rusticPlanks, woodSet.planks);
        rusticExchange(recipeOutput, woodSet.verticalRusticPlanks, woodSet.verticalPlanks);
        rusticExchange(recipeOutput, woodSet.rusticTiles, woodSet.tiles);

        shapedBoards(recipeOutput, woodSet.boards, woodSet.boardIngredientTag);

        shapedSlab(recipeOutput, woodSet.boardsSlab, woodSet.boards);
        shapedStairs(recipeOutput, woodSet.boardsStairs, woodSet.boards);
        shapedSlab(recipeOutput, woodSet.verticalBoardsSlab, woodSet.verticalBoards);
        shapedStairs(recipeOutput, woodSet.verticalBoardsStairs, woodSet.verticalBoards);

        planksExchange(recipeOutput, woodSet.boards, woodSet.verticalBoards);
        planksExchange(recipeOutput, woodSet.verticalBoards, woodSet.boards);

        shapedSlab(recipeOutput, woodSet.planksSlab, woodSet.planks);
        shapedStairs(recipeOutput, woodSet.planksStairs, woodSet.planks);
        shapedSlab(recipeOutput, woodSet.verticalPlanksSlab, woodSet.verticalPlanks);
        shapedStairs(recipeOutput, woodSet.verticalPlanksStairs, woodSet.verticalPlanks);
        shapedSlab(recipeOutput, woodSet.tilesSlab, woodSet.tiles);
        shapedStairs(recipeOutput, woodSet.tilesStairs, woodSet.tiles);

        shapedSlab(recipeOutput, woodSet.rusticPlanksSlab, woodSet.rusticPlanks);
        shapedStairs(recipeOutput, woodSet.rusticPlanksStairs, woodSet.rusticPlanks);
        shapedSlab(recipeOutput, woodSet.verticalRusticPlanksSlab, woodSet.verticalRusticPlanks);
        shapedStairs(recipeOutput, woodSet.verticalRusticPlanksStairs, woodSet.verticalRusticPlanks);
        shapedSlab(recipeOutput, woodSet.rusticTilesSlab, woodSet.rusticTiles);
        shapedStairs(recipeOutput, woodSet.rusticTilesStairs, woodSet.rusticTiles);

        shapelessWood(recipeOutput, woodSet.wood, woodSet.log);
        shapelessWood(recipeOutput, woodSet.strippedWood, woodSet.strippedLog);
        shapelessButton(recipeOutput, woodSet.button, woodSet.planksTag);
        shapedDoor(recipeOutput, woodSet.door, woodSet.planksTag);
        shapedFence(recipeOutput, woodSet.fence, woodSet.planksTag);
        shapedFenceGate(recipeOutput, woodSet.fenceGate, woodSet.planksTag);
        shapedPressurePlate(recipeOutput, woodSet.pressurePlate, woodSet.planksTag);

        shapedTrapdoor(recipeOutput, woodSet.solidTrapdoor, woodSet.planksTag);

        shapedSign(recipeOutput, woodSet.sign, woodSet.planksTag);

        trapdoorExchange(recipeOutput, woodSet.solidTrapdoor, woodSet.openTrapdoor, woodSet.prefix + "_open_trapdoor_exchange");
        trapdoorExchange(recipeOutput, woodSet.openTrapdoor, woodSet.solidTrapdoor, woodSet.prefix + "_solid_trapdoor_exchange");

        planksExchange(recipeOutput, woodSet.planks, woodSet.verticalPlanks);
        planksExchange(recipeOutput, woodSet.verticalPlanks, woodSet.tiles);
        planksExchange(recipeOutput, woodSet.tiles, woodSet.planks);

        planksExchange(recipeOutput, woodSet.rusticPlanks, woodSet.verticalRusticPlanks);
        planksExchange(recipeOutput, woodSet.verticalRusticPlanks, woodSet.rusticTiles);
        planksExchange(recipeOutput, woodSet.rusticTiles, woodSet.rusticPlanks);

        shapedBoat(recipeOutput, woodSet.boat, woodSet.planksTag);

        shapedPanel(recipeOutput, woodSet.panel, woodSet.planksTag);

        var condition = has(woodSet.planksTag);
        shaped(RecipeCategory.MISC, woodSet.boardWall, 6)
                .define('#', woodSet.boards)
                .pattern("###")
                .pattern("###")
                .unlockedBy("has_input", condition)
                .save(recipeOutput);

        shaped(RecipeCategory.MISC, woodSet.cutPlanks, 2)
                .define('X', woodSet.panel)
                .define('Y', woodSet.planksTag)
                .pattern("X").pattern("Y")
                .unlockedBy("has_input", condition)
                .save(recipeOutput);
        shaped(RecipeCategory.MISC, woodSet.beam, 3)
                .define('#', woodSet.planksTag)
                .pattern("#")
                .pattern("#")
                .pattern("#")
                .unlockedBy("has_input", condition)
                .save(recipeOutput);

        shaped(RecipeCategory.MISC, woodSet.itemStand, 2)
                .define('X', woodSet.planksTag)
                .define('Y', woodSet.slabTag)
                .pattern("YYY")
                .pattern("XXX")
                .unlockedBy("has_input", condition)
                .save(recipeOutput);
        shaped(RecipeCategory.MISC, woodSet.itemPedestal)
                .define('X', woodSet.planksTag)
                .define('Y', woodSet.slabTag)
                .pattern("YYY")
                .pattern(" X ")
                .pattern("YYY")
                .unlockedBy("has_input", condition)
                .save(recipeOutput);

    }

    private static void trapdoorExchange(RecipeOutput recipeOutput, ItemLike input, ItemLike output, String path) {
        shapeless(RecipeCategory.MISC, output)
                .requires(input)
                .unlockedBy("has_input", has(input))
                .save(recipeOutput, malumPath(path));
    }

    private static void planksExchange(RecipeOutput recipeOutput, ItemLike input, ItemLike planks) {
        final ResourceLocation recipeID = getDefaultRecipeId(planks).withSuffix("_from_" + getDefaultRecipeId(input).getPath());
        shaped(RecipeCategory.MISC, planks, 4)
                .define('#', input)
                .pattern(" # ")
                .pattern("# #")
                .pattern(" # ")
                .unlockedBy("has_input", has(input))
                .save(recipeOutput, recipeID);
    }

    private static void shapelessPlanks(RecipeOutput recipeOutput, ItemLike planks, TagKey<Item> input) {
        shapeless(RecipeCategory.MISC, planks, 4)
                .requires(input)
                .group("planks")
                .unlockedBy("has_logs", has(input))
                .save(recipeOutput);
    }

    private static void shapedBoards(RecipeOutput recipeOutput, ItemLike output, TagKey<Item> input) {
        shaped(RecipeCategory.MISC, output, 20)
                .define('#', input)
                .pattern(" # ")
                .pattern("###")
                .pattern(" # ")
                .unlockedBy("has_input", has(input)).save(recipeOutput);
    }

    private static void shapedPanel(RecipeOutput recipeOutput, ItemLike output, TagKey<Item> input) {
        shaped(RecipeCategory.MISC, output, 9)
                .define('#', input)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .unlockedBy("has_input", has(input)).save(recipeOutput);
    }

    private static void rusticExchange(RecipeOutput recipeOutput, ItemLike output, ItemLike input) {
        final ResourceLocation recipeID = getDefaultRecipeId(output).withSuffix("_from_" + getDefaultRecipeId(input).getPath());
        shaped(RecipeCategory.MISC, output, 5)
                .define('#', input)
                .pattern(" # ")
                .pattern("###")
                .pattern(" # ")
                .unlockedBy("has_input", has(input)).save(recipeOutput, recipeID);
    }

    private static void shapelessWood(RecipeOutput recipeOutput, ItemLike stripped, ItemLike input) {
        shaped(RecipeCategory.MISC, stripped, 3)
                .define('#', input)
                .pattern("##")
                .pattern("##")
                .group("bark")
                .unlockedBy("has_log", has(input))
                .save(recipeOutput);
    }

    private static void shapelessButton(RecipeOutput recipeOutput, ItemLike button, TagKey<Item> input) {
        shapeless(RecipeCategory.MISC, button)
                .requires(input)
                .unlockedBy("has_input", has(input))
                .save(recipeOutput);
    }

    private static void shapedDoor(RecipeOutput recipeOutput, ItemLike door, TagKey<Item> input) {
        shaped(RecipeCategory.MISC, door, 3)
                .define('#', input)
                .pattern("##")
                .pattern("##")
                .pattern("##")
                .unlockedBy("has_input", has(input))
                .save(recipeOutput);
    }

    private static void shapedFence(RecipeOutput recipeOutput, ItemLike fence, TagKey<Item> input) {
        shaped(RecipeCategory.MISC, fence, 3)
                .define('#', Tags.Items.RODS_WOODEN)
                .define('W', input)
                .pattern("W#W")
                .pattern("W#W")
                .unlockedBy("has_input", has(input))
                .save(recipeOutput);
    }

    private static void shapedFenceGate(RecipeOutput recipeOutput, ItemLike fenceGate, TagKey<Item> input) {
        shaped(RecipeCategory.MISC, fenceGate)
                .define('#', Tags.Items.RODS_WOODEN)
                .define('W', input)
                .pattern("#W#")
                .pattern("#W#")
                .unlockedBy("has_input", has(input))
                .save(recipeOutput);
    }

    private static void shapedPressurePlate(RecipeOutput recipeOutput, ItemLike pressurePlate, TagKey<Item> input) {
        shaped(RecipeCategory.MISC, pressurePlate)
                .define('#', input)
                .pattern("##")
                .unlockedBy("has_input", has(input))
                .save(recipeOutput);
    }

    private static void shapedSlab(RecipeOutput recipeOutput, ItemLike slab, ItemLike input) {
        shaped(RecipeCategory.MISC, slab, 6)
                .define('#', input)
                .pattern("###")
                .unlockedBy("has_input", has(input))
                .save(recipeOutput);
    }

    private static void shapedStairs(RecipeOutput recipeOutput, ItemLike stairs, ItemLike input) {
        shaped(RecipeCategory.MISC, stairs, 4)
                .define('#', input)
                .pattern("#  ")
                .pattern("## ")
                .pattern("###")
                .unlockedBy("has_input", has(input))
                .save(recipeOutput);
    }

    private static void shapedTrapdoor(RecipeOutput recipeOutput, ItemLike trapdoor, TagKey<Item> input) {
        shaped(RecipeCategory.MISC, trapdoor, 2)
                .define('#', input)
                .pattern("###")
                .pattern("###")
                .unlockedBy("has_input", has(input))
                .save(recipeOutput);
    }

    private static void shapedSign(RecipeOutput recipeOutput, ItemLike sign, TagKey<Item> input) {
        shaped(RecipeCategory.MISC, sign, 3)
                .group("sign")
                .define('#', input)
                .define('X', Tags.Items.RODS_WOODEN)
                .pattern("###")
                .pattern("###")
                .pattern(" X ")
                .unlockedBy("has_input", has(input))
                .save(recipeOutput);
    }

    private static void shapedBoat(RecipeOutput recipeOutput, ItemLike boat, TagKey<Item> input) {
        shaped(RecipeCategory.MISC, boat)
                .define('#', input)
                .pattern("# #")
                .pattern("###")
                .unlockedBy("has_input", has(input)).save(recipeOutput);
    }

    public record MalumDatagenWoodSet(
            String prefix,

            Item log, Item wood,
            Item strippedLog, Item strippedWood,

            Item sapFilledLog, Item strippedSapFilledLog,

            Item boards, Item verticalBoards,
            Item boardsSlab, Item verticalBoardsSlab,
            Item boardsStairs, Item verticalBoardsStairs,

            Item planks, Item verticalPlanks, Item tiles,
            Item rusticPlanks, Item verticalRusticPlanks, Item rusticTiles,
            Item planksSlab, Item verticalPlanksSlab, Item tilesSlab,
            Item rusticPlanksSlab, Item verticalRusticPlanksSlab, Item rusticTilesSlab,
            Item planksStairs, Item verticalPlanksStairs, Item tilesStairs,
            Item rusticPlanksStairs, Item verticalRusticPlanksStairs, Item rusticTilesStairs,

            Item panel, Item cutPlanks, Item beam,

            Item button, Item pressurePlate,

            Item solidTrapdoor, Item openTrapdoor,

            Item fence, Item fenceGate,

            Item boardWall,

            Item door,

            Item sign, Item hangingSign,

            Item itemStand, Item itemPedestal,

            TagKey<Item> logTag, TagKey<Item> boardIngredientTag, TagKey<Item> planksTag, TagKey<Item> stairsTag, TagKey<Item> slabTag,

            Item boat
    ) { }
}