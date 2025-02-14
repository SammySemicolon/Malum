package com.sammy.malum.data.recipe.builder;

import com.google.common.collect.Lists;
import com.sammy.malum.MalumMod;
import com.sammy.malum.common.recipe.spirit.focusing.SpiritFocusingRecipe;
import com.sammy.malum.core.systems.recipe.SpiritIngredient;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import net.minecraft.core.*;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import team.lodestar.lodestone.recipe.builder.AutonamedRecipeBuilder;

import java.util.List;

public class SpiritFocusingRecipeBuilder implements AutonamedRecipeBuilder<SpiritFocusingRecipe> {
    private final int time;
    private final int durabilityCost;
    private final Ingredient input;
    private final ItemStack output;
    private final List<SpiritIngredient> spirits = Lists.newArrayList();

    public SpiritFocusingRecipeBuilder(int time, int durabilityCost, Ingredient input, ItemStack output) {
        this.time = time;
        this.durabilityCost = durabilityCost;
        this.input = input;
        this.output = output;
    }

    public SpiritFocusingRecipeBuilder(int time, int durabilityCost, Ingredient input, ItemLike output, int outputCount) {
        this(time, durabilityCost, input, new ItemStack(output, outputCount));
    }

    public SpiritFocusingRecipeBuilder(int time, int durabilityCost, Holder<Item> input, ItemLike output, int outputCount) {
        this(time, durabilityCost, Ingredient.of(input.value()), new ItemStack(output, outputCount));
    }

    public SpiritFocusingRecipeBuilder addSpirit(MalumSpiritType type, int count) {
        spirits.add(new SpiritIngredient(type, count));
        return this;
    }

    @Override
    public Item getResult() {
        return this.output.getItem();
    }

    @Override
    public SpiritFocusingRecipe build(ResourceLocation resourceLocation) {
        return new SpiritFocusingRecipe(time, durabilityCost, input, output, spirits);
    }

    @Override
    public void save(RecipeOutput recipeOutput, ResourceLocation id) {
        defaultSaveFunc(recipeOutput, MalumMod.malumPath(id.getPath()));
    }

    @Override
    public String getRecipeSubfolder() {
        return "spirit_crucible";
    }
}
