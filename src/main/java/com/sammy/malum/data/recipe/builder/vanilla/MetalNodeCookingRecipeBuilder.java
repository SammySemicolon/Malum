package com.sammy.malum.data.recipe.builder.vanilla;

import com.sammy.malum.common.recipe.vanilla.*;
import net.minecraft.advancements.*;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.*;
import team.lodestar.lodestone.recipe.builder.*;

import javax.annotation.*;
import java.util.*;

public class MetalNodeCookingRecipeBuilder<T extends AbstractCookingRecipe> implements LodestoneRecipeBuilder<T> {

    public final RecipeCategory category;
    public final CookingBookCategory bookCategory;
    public final Ingredient ingredient;
    public final Ingredient result;
    public final int outputCount;
    public final float experience;
    public final int cookingTime;
    public Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
    @Nullable
    public String group;
    public final NodeCookingSerializer.Factory<T> factory;

    protected MetalNodeCookingRecipeBuilder(RecipeCategory category, CookingBookCategory bookCategory, Ingredient result, Ingredient ingredient, int outputCount, float experience, int cookingTime, NodeCookingSerializer.Factory<T> factory) {
        this.category = category;
        this.bookCategory = bookCategory;
        this.result = result;
        this.ingredient = ingredient;
        this.outputCount = outputCount;
        this.experience = experience;
        this.cookingTime = cookingTime;
        this.factory = factory;
    }

    public static MetalNodeCookingRecipeBuilder<MetalNodeBlastingRecipe> blasting(Ingredient ingredient, RecipeCategory category, Ingredient result, int outputCount, float experience, int cookingTime) {
        return new MetalNodeCookingRecipeBuilder<>(
                category, CookingBookCategory.MISC, result, ingredient, outputCount, experience, cookingTime, MetalNodeBlastingRecipe::new
        );
    }

    public static MetalNodeCookingRecipeBuilder<MetalNodeSmeltingRecipe> smelting(Ingredient ingredient, RecipeCategory category, Ingredient result, int outputCount, float experience, int cookingTime) {
        return new MetalNodeCookingRecipeBuilder<>(
                category, CookingBookCategory.MISC, result, ingredient, outputCount, experience, cookingTime, MetalNodeSmeltingRecipe::new
        );
    }

    public MetalNodeCookingRecipeBuilder<T> unlockedBy(String name, Criterion<?> criterion) {
        this.criteria.put(name, criterion);
        return this;
    }

    public MetalNodeCookingRecipeBuilder<T> group(@Nullable String groupName) {
        this.group = groupName;
        return this;
    }

    @Override
    public void save(RecipeOutput recipeOutput, ResourceLocation resourceLocation) {
        defaultSaveFunc(recipeOutput, resourceLocation);
    }

    @Override
    public T build(ResourceLocation id) {
        return factory.create(group == null ? "" : group, ingredient, result, outputCount, experience, cookingTime);
    }
}