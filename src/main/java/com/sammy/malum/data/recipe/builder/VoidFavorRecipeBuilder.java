package com.sammy.malum.data.recipe.builder;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.recipe.void_favor.FavorOfTheVoidRecipe;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import team.lodestar.lodestone.recipe.builder.AutonamedRecipeBuilder;

public class VoidFavorRecipeBuilder implements AutonamedRecipeBuilder<FavorOfTheVoidRecipe> {
    private final Ingredient input;

    private final ItemStack output;

    public VoidFavorRecipeBuilder(Ingredient input, ItemStack output) {
        this.input = input;
        this.output = output;
    }

    public VoidFavorRecipeBuilder(Ingredient input, ItemLike output, int outputCount) {
        this(input, new ItemStack(output, outputCount));
    }

    public VoidFavorRecipeBuilder(ItemLike input, ItemStack output) {
        this(Ingredient.of(input), output);
    }

    public VoidFavorRecipeBuilder(ItemLike input, ItemLike output, int outputCount) {
        this(input, new ItemStack(output, outputCount));
    }

    public VoidFavorRecipeBuilder(ItemLike input, ItemLike output) {
        this(input, new ItemStack(output));
    }

    @Override
    public Item getResult() {
        return output.getItem();
    }

    @Override
    public FavorOfTheVoidRecipe build(ResourceLocation resourceLocation) {
        return new FavorOfTheVoidRecipe(input, output);
    }

    @Override
    public void save(RecipeOutput recipeOutput, String recipeName) {
        saveRecipe(recipeOutput, MalumMod.malumPath("void_favor/" + recipeName));
    }

    @Override
    public void saveRecipe(RecipeOutput recipeOutput, ResourceLocation resourceLocation) {
        defaultSaveFunc(recipeOutput, resourceLocation);
    }

    @Override
    public String getRecipeSubfolder() {
        return "void_favor";
    }
}
