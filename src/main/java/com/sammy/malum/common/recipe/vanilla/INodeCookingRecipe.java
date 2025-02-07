package com.sammy.malum.common.recipe.vanilla;

import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;

public interface INodeCookingRecipe {

    ItemStack getOutput();

    int getOutputCount();

    Ingredient getRawOutput();

    Ingredient getInput();
}
