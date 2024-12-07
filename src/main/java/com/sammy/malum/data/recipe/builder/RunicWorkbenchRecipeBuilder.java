package com.sammy.malum.data.recipe.builder;

import com.sammy.malum.*;
import com.sammy.malum.common.recipe.RunicWorkbenchRecipe;
import com.sammy.malum.core.systems.recipe.SpiritIngredient;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import net.minecraft.advancements.Criterion;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.neoforged.neoforge.common.crafting.SizedIngredient;
import team.lodestar.lodestone.recipe.builder.AutonamedRecipeBuilder;

import java.util.LinkedHashMap;
import java.util.Map;

public class RunicWorkbenchRecipeBuilder implements AutonamedRecipeBuilder<RunicWorkbenchRecipe> {
    private ItemStack primaryInput;
    private SpiritIngredient secondaryInput;
    private final ItemStack output;

    public RunicWorkbenchRecipeBuilder(ItemStack output) {
        this.output = output;
    }

    public RunicWorkbenchRecipeBuilder(ItemLike output, int outputCount) {
        this.output = new ItemStack(output.asItem(), outputCount);
    }

    public RunicWorkbenchRecipeBuilder setPrimaryInput(ItemStack primaryInput) {
        this.primaryInput = primaryInput;
        return this;
    }

    public RunicWorkbenchRecipeBuilder setPrimaryInput(ItemLike primaryInput, int primaryInputCount) {
        return setPrimaryInput(new ItemStack(primaryInput, primaryInputCount));
    }

    public RunicWorkbenchRecipeBuilder setSecondaryInput(MalumSpiritType type, int amount) {
        this.secondaryInput = new SpiritIngredient(type, amount);
        return this;
    }

    @Override
    public Item getResult() {
        return output.getItem();
    }

    @Override
    public RunicWorkbenchRecipe build(ResourceLocation resourceLocation) {
        return new RunicWorkbenchRecipe(
                SizedIngredient.of(primaryInput.getItem(), primaryInput.getCount()),
                secondaryInput, output);
    }

    @Override
    public void save(RecipeOutput recipeOutput, ResourceLocation id) {
        defaultSaveFunc(recipeOutput, MalumMod.malumPath(id.getPath()));
    }

    @Override
    public String getRecipeSubfolder() {
        return "runeworking";
    }
}
