package com.sammy.malum.core.systems.recipe;

import com.sammy.malum.forge_stuff.SizedIngredient;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;

import java.util.*;

public class SpiritBasedRecipeInput implements RecipeInput {

    public final List<ItemStack> items;
    public final List<ItemStack> spirits;

    public SpiritBasedRecipeInput(ItemStack stack, ItemStack spirit) {
        this(stack, List.of(spirit));
    }

    public SpiritBasedRecipeInput(ItemStack stack, List<ItemStack> spirits) {
        this(List.of(stack), spirits);
    }

    public SpiritBasedRecipeInput(List<ItemStack> items, List<ItemStack> spirits) {
        this.items = items;
        this.spirits = spirits;
    }

    @Override
    public ItemStack getItem(int index) {
        return index < items.size() ? items.get(index) : spirits.get(index - items.size());
    }

    @Override
    public int size() {
        return items.size() + spirits.size();
    }

    public boolean test(SizedIngredient mainIngredient, List<SizedIngredient> extraIngredients, List<SpiritIngredient> spirits) {
        final List<SizedIngredient> ingredients = new ArrayList<>(List.of(mainIngredient));
        ingredients.addAll(extraIngredients);
        return test(ingredients, spirits);
    }

    public boolean test(SizedIngredient ingredient, SpiritIngredient spirit) {
        return test(ingredient, List.of(spirit));
    }

    public boolean test(SizedIngredient ingredient, List<SpiritIngredient> spirits) {
        return test(List.of(ingredient), spirits);
    }

    public boolean test(List<SizedIngredient> ingredients, List<SpiritIngredient> spirits) {
        return testItems(ingredients) && testSpirits(spirits);
    }

    public boolean testItems(List<SizedIngredient> ingredients) {
        if (ingredients.isEmpty()) {
            return true;
        }
        if (items.size() != ingredients.size()) {
            return false;
        }

        for (int i = 0; i < items.size(); i++) {
            SizedIngredient ingredient = ingredients.get(i);
            ItemStack stack = items.get(i);
            if (!ingredient.test(stack)) {
                return false;
            }
        }
        return true;
    }

    public boolean testSpirits(List<SpiritIngredient> recipeSpirits) {
        if (recipeSpirits.isEmpty()) {
            return true;
        }
        if (this.spirits.size() != recipeSpirits.size()) {
            return false;
        }
        List<ItemStack> sortedStacks = sortSpirits(recipeSpirits);
        if (sortedStacks.size() < this.spirits.size()) {
            return false;
        }
        for (int i = 0; i < this.spirits.size(); i++) {
            SpiritIngredient ingredient = recipeSpirits.get(i);
            ItemStack stack = sortedStacks.get(i);
            if (!ingredient.test(stack)) {
                return false;
            }
        }
        return true;
    }

    public List<ItemStack> sortSpirits(List<SpiritIngredient> recipeSpirits) {
        List<ItemStack> sortedStacks = new ArrayList<>();
        for (SpiritIngredient ingredient : recipeSpirits) {
            for (ItemStack stack : spirits) {
                if (ingredient.test(stack)) {
                    sortedStacks.add(stack);
                    break;
                }
            }
        }
        return sortedStacks;
    }
}