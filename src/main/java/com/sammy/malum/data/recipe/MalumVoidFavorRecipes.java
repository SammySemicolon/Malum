package com.sammy.malum.data.recipe;

import com.sammy.malum.*;
import com.sammy.malum.data.recipe.builder.*;
import com.sammy.malum.registry.common.item.*;
import io.github.fabricators_of_create.porting_lib.tags.Tags;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;


public class MalumVoidFavorRecipes {

    protected static void buildRecipes(RecipeOutput recipeOutput) {
        new VoidFavorRecipeBuilder(Ingredient.of(ItemRegistry.ENCYCLOPEDIA_ARCANA.get()), ItemRegistry.ENCYCLOPEDIA_ESOTERICA.get(), 1)
                .save(recipeOutput);

        new VoidFavorRecipeBuilder(Ingredient.of(Tags.Items.RAW_MATERIALS), ItemRegistry.RAW_SOULSTONE.get(), 1)
                .save(recipeOutput);

        new VoidFavorRecipeBuilder(Ingredient.of(Tags.Items.STORAGE_BLOCKS_IRON), ItemRegistry.ANOMALOUS_DESIGN.get(), 1)
                .save(recipeOutput);
        new VoidFavorRecipeBuilder(Ingredient.of(ItemRegistry.COMPLETE_DESIGN.get()), ItemRegistry.FUSED_CONSCIOUSNESS.get(), 1)
                .save(recipeOutput);

        new VoidFavorRecipeBuilder(ItemRegistry.REFINED_SOULSTONE.get(), ItemRegistry.NULL_SLATE.get(), 1)
                .save(recipeOutput);

        new VoidFavorRecipeBuilder(ItemRegistry.HEX_ASH.get(), ItemRegistry.VOID_SALTS.get(), 1)
                .save(recipeOutput);

        new VoidFavorRecipeBuilder(ItemRegistry.REFINED_BRILLIANCE.get(), ItemRegistry.MNEMONIC_FRAGMENT.get(), 1)
                .save(recipeOutput);
        new VoidFavorRecipeBuilder(ItemRegistry.RAW_BRILLIANCE.get(), ItemRegistry.MNEMONIC_FRAGMENT.get(), 2)
                .save(recipeOutput, MalumMod.malumPath("mnemonic_fragment_from_cluster"));

        new VoidFavorRecipeBuilder(Items.BLAZE_POWDER, ItemRegistry.AURIC_EMBERS.get(), 1)
                .save(recipeOutput);

        new VoidFavorRecipeBuilder(ItemRegistry.CTHONIC_GOLD.get(), ItemRegistry.MALIGNANT_LEAD.get(), 1)
                .save(recipeOutput);

        new VoidFavorRecipeBuilder(Ingredient.of(ItemRegistry.THE_DEVICE.get()), ItemRegistry.THE_VESSEL.get(), 1)
                .save(recipeOutput);

        new VoidFavorRecipeBuilder(Items.BLACK_WOOL, ItemRegistry.TOPHAT.get(), 1)
                .save(recipeOutput);

        new VoidFavorRecipeBuilder(ItemRegistry.ARCANE_ELEGY.get(), ItemRegistry.AESTHETICA.get(), 1)
                .save(recipeOutput);
    }
}
