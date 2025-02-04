package com.sammy.malum.data.recipe.crafting;

import com.sammy.malum.data.item.*;
import com.sammy.malum.registry.common.item.*;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.*;
import net.minecraft.tags.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.*;

import static com.sammy.malum.data.recipe.MalumVanillaRecipes.*;
import static net.minecraft.data.recipes.RecipeBuilder.*;
import static net.minecraft.data.recipes.ShapedRecipeBuilder.*;
import static net.minecraft.data.recipes.ShapelessRecipeBuilder.*;
import static net.minecraft.data.recipes.SimpleCookingRecipeBuilder.*;
import static net.minecraft.data.recipes.SingleItemRecipeBuilder.*;

public class MalumRockSetDatagen {

    private static final MalumDatagenRockSet TAINTED_ROCK = new MalumDatagenRockSet(
            "tainted_rock",
            ItemRegistry.TAINTED_ROCK.get(), ItemRegistry.SMOOTH_TAINTED_ROCK.get(), ItemRegistry.POLISHED_TAINTED_ROCK.get(),
            ItemRegistry.TAINTED_ROCK_STAIRS.get(), ItemRegistry.SMOOTH_TAINTED_ROCK_STAIRS.get(), ItemRegistry.POLISHED_TAINTED_ROCK_STAIRS.get(),
            ItemRegistry.TAINTED_ROCK_SLAB.get(), ItemRegistry.SMOOTH_TAINTED_ROCK_SLAB.get(), ItemRegistry.POLISHED_TAINTED_ROCK_SLAB.get(),

            ItemRegistry.TAINTED_ROCK_BRICKS.get(), ItemRegistry.TAINTED_ROCK_TILES.get(), ItemRegistry.SMALL_TAINTED_ROCK_BRICKS.get(),
            ItemRegistry.TAINTED_ROCK_BRICKS_STAIRS.get(), ItemRegistry.TAINTED_ROCK_TILES_STAIRS.get(), ItemRegistry.SMALL_TAINTED_ROCK_BRICKS_STAIRS.get(),
            ItemRegistry.TAINTED_ROCK_BRICKS_SLAB.get(), ItemRegistry.TAINTED_ROCK_TILES_SLAB.get(), ItemRegistry.SMALL_TAINTED_ROCK_BRICKS_SLAB.get(),

            ItemRegistry.RUNIC_TAINTED_ROCK_BRICKS.get(), ItemRegistry.RUNIC_TAINTED_ROCK_TILES.get(), ItemRegistry.RUNIC_SMALL_TAINTED_ROCK_BRICKS.get(),
            ItemRegistry.RUNIC_TAINTED_ROCK_BRICKS_STAIRS.get(), ItemRegistry.RUNIC_TAINTED_ROCK_TILES_STAIRS.get(), ItemRegistry.RUNIC_SMALL_TAINTED_ROCK_BRICKS_STAIRS.get(),
            ItemRegistry.RUNIC_TAINTED_ROCK_BRICKS_SLAB.get(), ItemRegistry.RUNIC_TAINTED_ROCK_TILES_SLAB.get(), ItemRegistry.RUNIC_SMALL_TAINTED_ROCK_BRICKS_SLAB.get(),

            ItemRegistry.TAINTED_ROCK_WALL.get(), ItemRegistry.SMOOTH_TAINTED_ROCK_WALL.get(), ItemRegistry.POLISHED_TAINTED_ROCK_WALL.get(),
            ItemRegistry.TAINTED_ROCK_BRICKS_WALL.get(), ItemRegistry.TAINTED_ROCK_TILES_WALL.get(), ItemRegistry.SMALL_TAINTED_ROCK_BRICKS_WALL.get(),
            ItemRegistry.RUNIC_TAINTED_ROCK_BRICKS_WALL.get(), ItemRegistry.RUNIC_TAINTED_ROCK_TILES_WALL.get(), ItemRegistry.RUNIC_SMALL_TAINTED_ROCK_BRICKS_WALL.get(),

            ItemRegistry.TAINTED_ROCK_COLUMN.get(), ItemRegistry.TAINTED_ROCK_COLUMN_CAP.get(),

            ItemRegistry.CUT_TAINTED_ROCK.get(), ItemRegistry.CHECKERED_TAINTED_ROCK.get(),

            ItemRegistry.CHISELED_TAINTED_ROCK.get(),

            ItemRegistry.TAINTED_ROCK_PRESSURE_PLATE.get(), ItemRegistry.TAINTED_ROCK_BUTTON.get(),

            ItemRegistry.TAINTED_ROCK_ITEM_STAND.get(), ItemRegistry.TAINTED_ROCK_ITEM_PEDESTAL.get(),

            ItemTagRegistry.TAINTED_ROCK, ItemTagRegistry.TAINTED_BLOCKS, ItemTagRegistry.TAINTED_STAIRS, ItemTagRegistry.TAINTED_SLABS, ItemTagRegistry.TAINTED_WALLS
    );

    private static final MalumDatagenRockSet TWISTED_ROCK = new MalumDatagenRockSet(
            "twisted_rock",
            ItemRegistry.TWISTED_ROCK.get(), ItemRegistry.SMOOTH_TWISTED_ROCK.get(), ItemRegistry.POLISHED_TWISTED_ROCK.get(),
            ItemRegistry.TWISTED_ROCK_STAIRS.get(), ItemRegistry.SMOOTH_TWISTED_ROCK_STAIRS.get(), ItemRegistry.POLISHED_TWISTED_ROCK_STAIRS.get(),
            ItemRegistry.TWISTED_ROCK_SLAB.get(), ItemRegistry.SMOOTH_TWISTED_ROCK_SLAB.get(), ItemRegistry.POLISHED_TWISTED_ROCK_SLAB.get(),

            ItemRegistry.TWISTED_ROCK_BRICKS.get(), ItemRegistry.TWISTED_ROCK_TILES.get(), ItemRegistry.SMALL_TWISTED_ROCK_BRICKS.get(),
            ItemRegistry.TWISTED_ROCK_BRICKS_STAIRS.get(), ItemRegistry.TWISTED_ROCK_TILES_STAIRS.get(), ItemRegistry.SMALL_TWISTED_ROCK_BRICKS_STAIRS.get(),
            ItemRegistry.TWISTED_ROCK_BRICKS_SLAB.get(), ItemRegistry.TWISTED_ROCK_TILES_SLAB.get(), ItemRegistry.SMALL_TWISTED_ROCK_BRICKS_SLAB.get(),

            ItemRegistry.RUNIC_TWISTED_ROCK_BRICKS.get(), ItemRegistry.RUNIC_TWISTED_ROCK_TILES.get(), ItemRegistry.RUNIC_SMALL_TWISTED_ROCK_BRICKS.get(),
            ItemRegistry.RUNIC_TWISTED_ROCK_BRICKS_STAIRS.get(), ItemRegistry.RUNIC_TWISTED_ROCK_TILES_STAIRS.get(), ItemRegistry.RUNIC_SMALL_TWISTED_ROCK_BRICKS_STAIRS.get(),
            ItemRegistry.RUNIC_TWISTED_ROCK_BRICKS_SLAB.get(), ItemRegistry.RUNIC_TWISTED_ROCK_TILES_SLAB.get(), ItemRegistry.RUNIC_SMALL_TWISTED_ROCK_BRICKS_SLAB.get(),

            ItemRegistry.TWISTED_ROCK_WALL.get(), ItemRegistry.SMOOTH_TWISTED_ROCK_WALL.get(), ItemRegistry.POLISHED_TWISTED_ROCK_WALL.get(),
            ItemRegistry.TWISTED_ROCK_BRICKS_WALL.get(), ItemRegistry.TWISTED_ROCK_TILES_WALL.get(), ItemRegistry.SMALL_TWISTED_ROCK_BRICKS_WALL.get(),
            ItemRegistry.RUNIC_TWISTED_ROCK_BRICKS_WALL.get(), ItemRegistry.RUNIC_TWISTED_ROCK_TILES_WALL.get(), ItemRegistry.RUNIC_SMALL_TWISTED_ROCK_BRICKS_WALL.get(),

            ItemRegistry.TWISTED_ROCK_COLUMN.get(), ItemRegistry.TWISTED_ROCK_COLUMN_CAP.get(),

            ItemRegistry.CUT_TWISTED_ROCK.get(), ItemRegistry.CHECKERED_TWISTED_ROCK.get(),

            ItemRegistry.CHISELED_TWISTED_ROCK.get(),

            ItemRegistry.TWISTED_ROCK_PRESSURE_PLATE.get(), ItemRegistry.TWISTED_ROCK_BUTTON.get(),

            ItemRegistry.TWISTED_ROCK_ITEM_STAND.get(), ItemRegistry.TWISTED_ROCK_ITEM_PEDESTAL.get(),

            ItemTagRegistry.TWISTED_ROCK, ItemTagRegistry.TWISTED_BLOCKS, ItemTagRegistry.TWISTED_STAIRS, ItemTagRegistry.TWISTED_SLABS, ItemTagRegistry.TWISTED_WALLS
    );

    private static MalumRockSetDatagen.MalumDatagenRockSet cachedRockSet;

    public static void addTags(MalumItemTags provider) {
        addTags(provider, TAINTED_ROCK);
        addTags(provider, TWISTED_ROCK);
    }

    public static void buildRecipes(RecipeOutput consumer) {
        buildRecipes(consumer, TAINTED_ROCK);
        buildRecipes(consumer, TWISTED_ROCK);
    }

    protected static void addTags(MalumItemTags provider, MalumRockSetDatagen.MalumDatagenRockSet rockSet) {
        provider.safeCopy(rockSet.setEncompassingTag);
        provider.safeCopy(rockSet.blockTag);
        provider.safeCopy(rockSet.stairTag);
        provider.safeCopy(rockSet.slabTag);
        provider.safeCopy(rockSet.wallTag);
    }

    protected static void buildRecipes(RecipeOutput recipeOutput, MalumRockSetDatagen.MalumDatagenRockSet rockSet) {
        var condition = has(rockSet.rock);
        cachedRockSet = rockSet;
        shapedSlab(recipeOutput, rockSet.rockSlab, rockSet.rock);
        shapedSlab(recipeOutput, rockSet.smoothRockSlab, rockSet.smoothRock);
        shapedSlab(recipeOutput, rockSet.polishedRockSlab, rockSet.polishedRock);
        shapedStairs(recipeOutput, rockSet.rockStairs, rockSet.rock);
        shapedStairs(recipeOutput, rockSet.smoothRockStairs, rockSet.smoothRock);
        shapedStairs(recipeOutput, rockSet.polishedRockStairs, rockSet.polishedRock);
        shapedWall(recipeOutput, rockSet.rockWall, rockSet.rock);
        shapedWall(recipeOutput, rockSet.smoothRockWall, rockSet.smoothRock);
        shapedWall(recipeOutput, rockSet.polishedRockWall, rockSet.polishedRock);

        shapedSlab(recipeOutput, rockSet.bricksSlab, rockSet.bricks);
        shapedSlab(recipeOutput, rockSet.tilesSlab, rockSet.tiles);
        shapedSlab(recipeOutput, rockSet.smallBricksSlab, rockSet.smallBricks);
        shapedStairs(recipeOutput, rockSet.bricksStairs, rockSet.bricks);
        shapedStairs(recipeOutput, rockSet.tilesStairs, rockSet.tiles);
        shapedStairs(recipeOutput, rockSet.smallBricksStairs, rockSet.smallBricks);
        shapedWall(recipeOutput, rockSet.bricksWall, rockSet.bricks);
        shapedWall(recipeOutput, rockSet.tilesWall, rockSet.tiles);
        shapedWall(recipeOutput, rockSet.smallBricksWall, rockSet.smallBricks);

        shapedSlab(recipeOutput, rockSet.runicBricksSlab, rockSet.runicBricks);
        shapedSlab(recipeOutput, rockSet.runicTilesSlab, rockSet.runicTiles);
        shapedSlab(recipeOutput, rockSet.runicSmallBricksSlab, rockSet.runicSmallBricks);
        shapedStairs(recipeOutput, rockSet.runicBricksStairs, rockSet.runicBricks);
        shapedStairs(recipeOutput, rockSet.runicTilesStairs, rockSet.runicTiles);
        shapedStairs(recipeOutput, rockSet.runicSmallBricksStairs, rockSet.runicSmallBricks);
        shapedWall(recipeOutput, rockSet.runicBricksWall, rockSet.bricks);
        shapedWall(recipeOutput, rockSet.runicTilesWall, rockSet.tiles);
        shapedWall(recipeOutput, rockSet.runicSmallBricksWall, rockSet.smallBricks);

        rockExchange(recipeOutput, rockSet.bricks, rockSet.rock);

        rockExchange(recipeOutput, rockSet.tiles, rockSet.bricks);
        rockExchange(recipeOutput, rockSet.smallBricks, rockSet.tiles);
        rockExchange(recipeOutput, rockSet.bricks, rockSet.smallBricks);

        runicExchange(recipeOutput, rockSet.runicBricks, rockSet.bricks);
        runicExchange(recipeOutput, rockSet.runicTiles, rockSet.tiles);
        runicExchange(recipeOutput, rockSet.runicSmallBricks, rockSet.smallBricks);

        rockExchange(recipeOutput, rockSet.runicTiles, rockSet.runicBricks);
        rockExchange(recipeOutput, rockSet.runicSmallBricks, rockSet.runicTiles);
        rockExchange(recipeOutput, rockSet.runicBricks, rockSet.runicSmallBricks);

        shapelessButton(recipeOutput, rockSet.button, rockSet.rock);
        shapedPressurePlate(recipeOutput, rockSet.pressurePlate, rockSet.rock);

        runicExchange(recipeOutput, rockSet.smoothRock, rockSet.rock);
        rockExchange(recipeOutput, rockSet.polishedRock, rockSet.smoothRock);


        shaped(RecipeCategory.MISC, rockSet.chiseledRock, 1)
                .define('#', rockSet.polishedRockSlab)
                .pattern("#")
                .pattern("#")
                .unlockedBy("has_input", condition)
                .save(recipeOutput);
        stoneCutting(recipeOutput, rockSet.rock, rockSet.chiseledRock);

        shaped(RecipeCategory.MISC, rockSet.cutRock, 2)
                .define('X', rockSet.polishedRock)
                .define('Y', rockSet.rock)
                .pattern("X")
                .pattern("Y")
                .unlockedBy("has_input", condition)
                .save(recipeOutput);
        stoneCutting(recipeOutput, rockSet.rock, rockSet.cutRock);
        stoneCutting(recipeOutput, rockSet.polishedRock, rockSet.cutRock);

        runicExchange(recipeOutput, rockSet.checkeredRock, rockSet.cutRock);

        shaped(RecipeCategory.MISC, rockSet.column, 3)
                .define('#', rockSet.rock)
                .pattern("#")
                .pattern("#")
                .pattern("#")
                .unlockedBy("has_input", condition)
                .save(recipeOutput);
        stoneCutting(recipeOutput, rockSet.rock, rockSet.column);

        shaped(RecipeCategory.MISC, rockSet.columnCap, 2)
                .define('X', rockSet.polishedRock)
                .define('Y', rockSet.column)
                .pattern("X")
                .pattern("Y")
                .unlockedBy("has_input", condition)
                .save(recipeOutput);
        stoneCutting(recipeOutput, rockSet.rock, rockSet.columnCap);
        stoneCutting(recipeOutput, rockSet.polishedRock, rockSet.columnCap);

        shaped(RecipeCategory.MISC, rockSet.itemStand, 2)
                .define('X', rockSet.rock)
                .define('Y', rockSet.rockSlab)
                .pattern("YYY")
                .pattern("XXX")
                .unlockedBy("has_input", condition)
                .save(recipeOutput);
        stoneCutting(recipeOutput, rockSet.rock, rockSet.itemStand);

        shaped(RecipeCategory.MISC, rockSet.itemPedestal)
                .define('X', rockSet.rock)
                .define('Y', rockSet.rockSlab)
                .pattern("YYY")
                .pattern(" X ")
                .pattern("YYY")
                .unlockedBy("has_input", condition)
                .save(recipeOutput);
        stoneCutting(recipeOutput, rockSet.rock, rockSet.itemPedestal);
    }


    private static void rockExchange(RecipeOutput recipeOutput, ItemLike output, ItemLike input) {
        final ResourceLocation recipeID = getDefaultRecipeId(output).withSuffix("_from_" + getDefaultRecipeId(input).getPath());
        shaped(RecipeCategory.MISC, output, 4)
                .define('#', input)
                .pattern("##")
                .pattern("##")
                .unlockedBy("has_input", has(input))
                .save(recipeOutput, recipeID);
        stoneCutting(recipeOutput, input, output);
    }

    private static void runicExchange(RecipeOutput recipeOutput, ItemLike output, ItemLike input) {
        smelting(Ingredient.of(input), RecipeCategory.MISC, output, 0.1f, 200)
                .unlockedBy("has_input", has(input)).save(recipeOutput);
        stoneCutting(recipeOutput, input, output);
    }

    private static void shapelessButton(RecipeOutput recipeOutput, ItemLike button, Item input) {
        shapeless(RecipeCategory.MISC, button)
                .requires(input)
                .unlockedBy("has_input", has(input))
                .save(recipeOutput);
        stoneCutting(recipeOutput, input, button);
    }

    private static void shapedPressurePlate(RecipeOutput recipeOutput, ItemLike pressurePlate, Item input) {
        shaped(RecipeCategory.MISC, pressurePlate)
                .define('#', input)
                .pattern("##")
                .unlockedBy("has_input", has(input))
                .save(recipeOutput);
        stoneCutting(recipeOutput, input, pressurePlate);
    }

    private static void shapedSlab(RecipeOutput recipeOutput, ItemLike slab, Item input) {
        shaped(RecipeCategory.MISC, slab, 6)
                .define('#', input)
                .pattern("###")
                .unlockedBy("has_input", has(input))
                .save(recipeOutput);
        stoneCutting(recipeOutput, input, slab, 2);
        if (!input.equals(cachedRockSet.rock)) {
            stoneCutting(recipeOutput, cachedRockSet.rock, slab, 2);
        }
    }

    private static void shapedStairs(RecipeOutput recipeOutput, ItemLike stairs, Item input) {
        shaped(RecipeCategory.MISC, stairs, 4)
                .define('#', input)
                .pattern("#  ")
                .pattern("## ")
                .pattern("###")
                .unlockedBy("has_input", has(input))
                .save(recipeOutput);
        stoneCutting(recipeOutput, input, stairs);
        if (!input.equals(cachedRockSet.rock)) {
            stoneCutting(recipeOutput, cachedRockSet.rock, stairs);
        }
    }

    private static void shapedWall(RecipeOutput recipeOutput, ItemLike wall, Item input) {
        shaped(RecipeCategory.MISC, wall, 6)
                .define('#', input)
                .pattern("###")
                .pattern("###")
                .unlockedBy("has_input", has(input))
                .save(recipeOutput);
        stoneCutting(recipeOutput, input, wall, 1);
        if (!input.equals(cachedRockSet.rock)) {
            stoneCutting(recipeOutput, cachedRockSet.rock, wall, 1);
        }
    }

    private static void stoneCutting(RecipeOutput recipeOutput, ItemLike input, ItemLike output) {
        stoneCutting(recipeOutput, input, output, 1);
    }
    private static void stoneCutting(RecipeOutput recipeOutput, ItemLike input, ItemLike output, int outputCount) {
        final ResourceLocation recipeID = getDefaultRecipeId(output).withSuffix("_stonecutting_from_" + getDefaultRecipeId(input).getPath());
        stonecutting(Ingredient.of(input), RecipeCategory.MISC, output, outputCount).unlockedBy("has_input", has(input)).save(recipeOutput, recipeID);
    }

    public record MalumDatagenRockSet(
            String prefix,

            Item rock, Item smoothRock, Item polishedRock,
            Item rockStairs, Item smoothRockStairs, Item polishedRockStairs,
            Item rockSlab, Item smoothRockSlab, Item polishedRockSlab,

            Item bricks, Item tiles, Item smallBricks,
            Item bricksStairs, Item tilesStairs, Item smallBricksStairs,
            Item bricksSlab, Item tilesSlab, Item smallBricksSlab,

            Item runicBricks, Item runicTiles, Item runicSmallBricks,
            Item runicBricksStairs, Item runicTilesStairs, Item runicSmallBricksStairs,
            Item runicBricksSlab, Item runicTilesSlab, Item runicSmallBricksSlab,

            Item rockWall, Item smoothRockWall, Item polishedRockWall,
            Item bricksWall, Item tilesWall, Item smallBricksWall,
            Item runicBricksWall, Item runicTilesWall, Item runicSmallBricksWall,

            Item column, Item columnCap,

            Item cutRock, Item checkeredRock,

            Item chiseledRock,

            Item pressurePlate, Item button,

            Item itemStand, Item itemPedestal,

            TagKey<Item> setEncompassingTag, TagKey<Item> blockTag, TagKey<Item> stairTag, TagKey<Item> slabTag, TagKey<Item> wallTag
            ) { }
}
