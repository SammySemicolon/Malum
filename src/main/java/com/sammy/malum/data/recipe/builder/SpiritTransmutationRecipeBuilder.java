package com.sammy.malum.data.recipe.builder;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.recipe.spirit.transmutation.*;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.registries.DeferredHolder;
import team.lodestar.lodestone.recipe.builder.AutonamedRecipeBuilder;

import javax.annotation.Nullable;

public class SpiritTransmutationRecipeBuilder implements AutonamedRecipeBuilder<SpiritTransmutationRecipe> {
    private final Ingredient ingredient;
    private final ItemStack output;

    @Nullable
    private String group = "";

    public SpiritTransmutationRecipeBuilder(Ingredient input, ItemStack output) {
        ingredient = input;
        this.output = output;
    }

    public SpiritTransmutationRecipeBuilder(DeferredHolder<Item, ? extends ItemLike> input, DeferredHolder<Item, ? extends ItemLike> output) {
        this(input.get(), output.get());
    }

    public SpiritTransmutationRecipeBuilder(Ingredient input, Item output) {
        this(input, new ItemStack(output));
    }

    public SpiritTransmutationRecipeBuilder(ItemLike input, ItemStack output) {
        this(Ingredient.of(input), output);
    }

    public SpiritTransmutationRecipeBuilder(ItemStack input, ItemStack output) {
        this(Ingredient.of(input), output);
    }

    public SpiritTransmutationRecipeBuilder(ItemLike input, ItemLike output) {
        this(Ingredient.of(input), new ItemStack(output));
    }

    public SpiritTransmutationRecipeBuilder(ItemStack input, ItemLike output) {
        this(Ingredient.of(input), new ItemStack(output));
    }

    public SpiritTransmutationRecipeBuilder group(@Nullable String group) {
        this.group = group;
        return this;
    }

    @Override
    public Item getResult() {
        return output.getItem();
    }

    @Override
    public SpiritTransmutationRecipe build(ResourceLocation resourceLocation) {
        return new SpiritTransmutationRecipe(ingredient, output, group);
    }

    @Override
    public void save(RecipeOutput recipeOutput, String recipeName) {
        save(recipeOutput, MalumMod.malumPath("spirit_transmutation/" + recipeName));
    }

    @Override
    public void save(RecipeOutput recipeOutput, ResourceLocation resourceLocation) {
        defaultSaveFunc(recipeOutput, resourceLocation);
    }

    @Override
    public String getRecipeSubfolder() {
        return "spirit_transmutation";
    }
}
