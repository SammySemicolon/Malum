package com.sammy.malum.registry.common.recipe;


import com.google.gson.JsonObject;
import net.minecraft.advancements.*;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

public class StackedCookingRecipeBuilder implements RecipeBuilder {
    private final RecipeCategory category;
    private final CookingBookCategory bookCategory;
    private final ItemStack result;
    private final Ingredient ingredient;
    private final float experience;
    private final int cookingTime;
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap();
    @Nullable
    private String group;
    private final AbstractCookingRecipe.Factory<?> factory;

    private StackedCookingRecipeBuilder(
            RecipeCategory category,
            CookingBookCategory bookCategory,
            ItemStack result,
            Ingredient ingredient,
            float experience,
            int cookingTime,
            AbstractCookingRecipe.Factory<?> factory
    ) {
        this.category = category;
        this.bookCategory = bookCategory;
        this.result = result;
        this.ingredient = ingredient;
        this.experience = experience;
        this.cookingTime = cookingTime;
        this.factory = factory;
    }

    public static <T extends AbstractCookingRecipe> StackedCookingRecipeBuilder generic(
            Ingredient ingredient,
            RecipeCategory category,
            ItemStack result,
            float experience,
            int cookingTime,
            RecipeSerializer<T> cookingSerializer,
            AbstractCookingRecipe.Factory<T> factory
    ) {
        return new StackedCookingRecipeBuilder(category, determineRecipeCategory(cookingSerializer, result), result, ingredient, experience, cookingTime, factory);
    }

    public static StackedCookingRecipeBuilder campfireCooking(Ingredient ingredient, RecipeCategory category, ItemStack result, float experience, int cookingTime) {
        return new StackedCookingRecipeBuilder(category, CookingBookCategory.FOOD, result, ingredient, experience, cookingTime, CampfireCookingRecipe::new);
    }

    public static StackedCookingRecipeBuilder blasting(Ingredient ingredient, RecipeCategory category, ItemStack result, float experience, int cookingTime) {
        return new StackedCookingRecipeBuilder(category, determineBlastingRecipeCategory(result), result, ingredient, experience, cookingTime, BlastingRecipe::new);
    }

    public static StackedCookingRecipeBuilder smelting(Ingredient ingredient, RecipeCategory category, ItemStack result, float experience, int cookingTime) {
        return new StackedCookingRecipeBuilder(category, determineSmeltingRecipeCategory(result), result, ingredient, experience, cookingTime, SmeltingRecipe::new);
    }

    public static StackedCookingRecipeBuilder smoking(Ingredient ingredient, RecipeCategory category, ItemStack result, float experience, int cookingTime) {
        return new StackedCookingRecipeBuilder(category, CookingBookCategory.FOOD, result, ingredient, experience, cookingTime, SmokingRecipe::new);
    }

    public StackedCookingRecipeBuilder unlockedBy(String name, Criterion<?> criterion) {
        this.criteria.put(name, criterion);
        return this;
    }

    public StackedCookingRecipeBuilder group(@Nullable String groupName) {
        this.group = groupName;
        return this;
    }

    @Override
    public Item getResult() {
        return this.result.getItem();
    }

    @Override
    public void save(RecipeOutput recipeOutput, ResourceLocation id) {
        this.ensureValid(id);
        Advancement.Builder builder = recipeOutput.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
                .rewards(AdvancementRewards.Builder.recipe(id))
                .requirements(AdvancementRequirements.Strategy.OR);
        this.criteria.forEach(builder::addCriterion);
        AbstractCookingRecipe abstractCookingRecipe = this.factory
                .create(
                        (String) Objects.requireNonNullElse(this.group, ""), this.bookCategory, this.ingredient, this.result, this.experience, this.cookingTime
                );
        recipeOutput.accept(id, abstractCookingRecipe, builder.build(id.withPrefix("recipes/" + this.category.getFolderName() + "/")));
    }

    private static CookingBookCategory determineSmeltingRecipeCategory(ItemStack result) {
        if (result.getComponents().has(DataComponents.FOOD)) {
            return CookingBookCategory.FOOD;
        } else {
            return result.getItem() instanceof BlockItem ? CookingBookCategory.BLOCKS : CookingBookCategory.MISC;
        }
    }

    private static CookingBookCategory determineBlastingRecipeCategory(ItemStack result) {
        return result.getItem() instanceof BlockItem ? CookingBookCategory.BLOCKS : CookingBookCategory.MISC;
    }

    private static CookingBookCategory determineRecipeCategory(RecipeSerializer<? extends AbstractCookingRecipe> serializer, ItemStack result) {
        if (serializer == RecipeSerializer.SMELTING_RECIPE) {
            return determineSmeltingRecipeCategory(result);
        } else if (serializer == RecipeSerializer.BLASTING_RECIPE) {
            return determineBlastingRecipeCategory(result);
        } else if (serializer != RecipeSerializer.SMOKING_RECIPE && serializer != RecipeSerializer.CAMPFIRE_COOKING_RECIPE) {
            throw new IllegalStateException("Unknown cooking recipe type");
        } else {
            return CookingBookCategory.FOOD;
        }
    }

    /**
     * Makes sure that this obtainable.
     */
    private void ensureValid(ResourceLocation id) {
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + id);
        }
    }
}
