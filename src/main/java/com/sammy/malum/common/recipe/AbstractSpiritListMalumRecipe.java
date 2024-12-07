package com.sammy.malum.common.recipe;

import com.sammy.malum.core.systems.recipe.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;
import team.lodestar.lodestone.systems.recipe.*;

import java.util.*;

public abstract class AbstractSpiritListMalumRecipe extends LodestoneInWorldRecipe<SingleRecipeInput> {

    public final List<SpiritIngredient> spirits;

    protected AbstractSpiritListMalumRecipe(RecipeSerializer<?> recipeSerializer, RecipeType<?> recipeType, List<SpiritIngredient> spirits) {
        super(recipeSerializer, recipeType);
        this.spirits = spirits;
    }

    public List<ItemStack> getSortedSpirits(List<ItemStack> stacks) {
        List<ItemStack> sortedStacks = new ArrayList<>();
        for (SpiritIngredient item : spirits) {
            for (ItemStack stack : stacks) {
                if (item.test(stack)) {
                    sortedStacks.add(stack);
                    break;
                }
            }
        }
        return sortedStacks;
    }

    public boolean doSpiritsMatch(List<ItemStack> spirits) {
        if (this.spirits.isEmpty()) {
            return true;
        }
        if (this.spirits.size() != spirits.size()) {
            return false;
        }
        List<ItemStack> sortedStacks = getSortedSpirits(spirits);
        if (sortedStacks.size() < this.spirits.size()) {
            return false;
        }
        for (int i = 0; i < this.spirits.size(); i++) {
            SpiritIngredient item = this.spirits.get(i);
            ItemStack stack = sortedStacks.get(i);
            if (!item.test(stack)) {
                return false;
            }
        }
        return true;
    }
}
