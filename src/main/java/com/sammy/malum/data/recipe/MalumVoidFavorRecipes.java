package com.sammy.malum.data.recipe;

import com.sammy.malum.data.recipe.builder.VoidFavorRecipeBuilder;
import com.sammy.malum.registry.common.item.ItemRegistry;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.Tags;

import java.util.function.Consumer;

public class MalumVoidFavorRecipes extends RecipeProvider {
    public MalumVoidFavorRecipes(PackOutput generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        new VoidFavorRecipeBuilder(Ingredient.of(Tags.Items.RAW_MATERIALS), ItemRegistry.RAW_SOULSTONE.get(), 1)
                .build(consumer);

        new VoidFavorRecipeBuilder(Ingredient.of(Tags.Items.STORAGE_BLOCKS_IRON), ItemRegistry.ANOMALOUS_SNARE.get(), 1)
                .build(consumer);

        new VoidFavorRecipeBuilder(Ingredient.of(ItemRegistry.PROCESSED_SOULSTONE.get()), ItemRegistry.NULL_SLATE.get(), 1)
                .build(consumer);
        new VoidFavorRecipeBuilder(Ingredient.of(ItemRegistry.HEX_ASH.get()), ItemRegistry.VOID_SALTS.get(), 1)
                .build(consumer);
        new VoidFavorRecipeBuilder(Ingredient.of(Tags.Items.ENDER_PEARLS), ItemRegistry.STRANGE_NUCLEUS.get(), 1)
                .build(consumer);
        new VoidFavorRecipeBuilder(Ingredient.of(Items.GHAST_TEAR), ItemRegistry.CRYSTALLIZED_NIHILITY.get(), 1)
                .build(consumer);

        new VoidFavorRecipeBuilder(Ingredient.of(ItemRegistry.THE_DEVICE.get()), ItemRegistry.THE_VESSEL.get(), 1)
                .build(consumer);
    }
}
