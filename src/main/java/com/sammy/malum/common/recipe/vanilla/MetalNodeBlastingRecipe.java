package com.sammy.malum.common.recipe.vanilla;

import com.sammy.malum.registry.common.recipe.RecipeSerializerRegistry;
import net.minecraft.core.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;

public class MetalNodeBlastingRecipe extends BlastingRecipe implements INodeCookingRecipe {

    public static final String NAME = "node_blasting";

    private final Ingredient rawOutput;
    private final int outputCount;
    private ItemStack outputCache;

    public MetalNodeBlastingRecipe(String group, Ingredient ingredient, Ingredient output, int outputCount, float experience, int cookingTime) {
        super(group, CookingBookCategory.MISC, ingredient, ItemStack.EMPTY, experience, cookingTime);
        this.rawOutput = output;
        this.outputCount = outputCount;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeSerializerRegistry.METAL_NODE_BLASTING_SERIALIZER.get();
    }

    @Override
    public Ingredient getInput() {
        return ingredient;
    }

    @Override
    public int getOutputCount() {
        return outputCount;
    }

    @Override
    public Ingredient getRawOutput() {
        return rawOutput;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries) {
        return getOutput();
    }

    @Override
    public ItemStack getOutput() {
        if (outputCache == null) {
            outputCache = NodeCookingSerializer.getStackFromIngredient(rawOutput).copyWithCount(outputCount);
        }
        return outputCache;
    }
}