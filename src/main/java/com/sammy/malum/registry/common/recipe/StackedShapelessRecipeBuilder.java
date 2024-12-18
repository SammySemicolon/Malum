package com.sammy.malum.registry.common.recipe;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.NonNullList;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class StackedShapelessRecipeBuilder implements RecipeBuilder {
    public final RecipeCategory category;
    private final ItemStack result;
    public final NonNullList<Ingredient> ingredients = NonNullList.create();
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap();
    @Nullable
    public String group;

    public StackedShapelessRecipeBuilder(RecipeCategory category, ItemStack result) {
        this.category = category;
        this.result = result;
    }

    public static StackedShapelessRecipeBuilder shapeless(RecipeCategory category, ItemStack result) {
        return new StackedShapelessRecipeBuilder(category, result);
    }

    public static StackedShapelessRecipeBuilder shapeless(RecipeCategory category, ItemStack result, int count) {
        return new StackedShapelessRecipeBuilder(category, result);
    }

    public StackedShapelessRecipeBuilder requires(TagKey<Item> tag) {
        return this.requires(Ingredient.of(tag));
    }

    public StackedShapelessRecipeBuilder requires(ItemLike item) {
        return this.requires((ItemLike)item, 1);
    }

    public StackedShapelessRecipeBuilder requires(ItemLike item, int quantity) {
        for(int i = 0; i < quantity; ++i) {
            this.requires(Ingredient.of(new ItemLike[]{item}));
        }

        return this;
    }

    public StackedShapelessRecipeBuilder requires(Ingredient ingredient) {
        return this.requires((Ingredient)ingredient, 1);
    }

    public StackedShapelessRecipeBuilder requires(Ingredient ingredient, int quantity) {
        for(int i = 0; i < quantity; ++i) {
            this.ingredients.add(ingredient);
        }

        return this;
    }

    public StackedShapelessRecipeBuilder unlockedBy(String name, Criterion<?> criterion) {
        this.criteria.put(name, criterion);
        return this;
    }

    public StackedShapelessRecipeBuilder group(@Nullable String groupName) {
        this.group = groupName;
        return this;
    }

    public Item getResult() {
        return this.result.getItem();
    }

    public void save(RecipeOutput recipeOutput, ResourceLocation id) {
        this.ensureValid(id);
        Advancement.Builder builder = recipeOutput.advancement().addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id)).rewards(AdvancementRewards.Builder.recipe(id)).requirements(AdvancementRequirements.Strategy.OR);
        Objects.requireNonNull(builder);
        this.criteria.forEach(builder::addCriterion);
        ShapelessRecipe shapelessRecipe = new ShapelessRecipe((String)Objects.requireNonNullElse(this.group, ""), RecipeBuilder.determineBookCategory(this.category), this.result, this.ingredients);
        recipeOutput.accept(id, shapelessRecipe, builder.build(id.withPrefix("recipes/" + this.category.getFolderName() + "/")));
    }

    public final void ensureValid(ResourceLocation id) {
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + String.valueOf(id));
        }
    }
}
