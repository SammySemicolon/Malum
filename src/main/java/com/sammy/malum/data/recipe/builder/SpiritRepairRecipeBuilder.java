package com.sammy.malum.data.recipe.builder;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.recipe.SpiritRepairRecipe;
import com.sammy.malum.core.systems.recipe.SpiritIngredient;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.crafting.SizedIngredient;
import team.lodestar.lodestone.recipe.builder.LodestoneRecipeBuilder;

import java.util.ArrayList;
import java.util.List;

public class SpiritRepairRecipeBuilder implements LodestoneRecipeBuilder<SpiritRepairRecipe> {

    public final String itemIdRegex;
    public final String modIdRegex;
    public final float durabilityPercentage;
    public final List<Item> inputs = new ArrayList<>();
    public final SizedIngredient repairMaterial;
    public final List<SpiritIngredient> spirits = new ArrayList<>();

    public SpiritRepairRecipeBuilder(String itemIdRegex, String modIdRegex, float durabilityPercentage, SizedIngredient repairMaterial) {
        this.itemIdRegex = itemIdRegex;
        this.modIdRegex = modIdRegex;
        this.durabilityPercentage = durabilityPercentage;
        this.repairMaterial = repairMaterial;
    }

    public SpiritRepairRecipeBuilder(String itemIdRegex, float durabilityPercentage, Ingredient repairMaterial, int repairMaterialCount) {
        this(itemIdRegex, "", durabilityPercentage, new SizedIngredient(repairMaterial, repairMaterialCount));
    }

    public SpiritRepairRecipeBuilder(float durabilityPercentage, Ingredient repairMaterial, int repairMaterialCount) {
        this("", "", durabilityPercentage, new SizedIngredient(repairMaterial, repairMaterialCount));
    }

    public SpiritRepairRecipeBuilder addItem(Item item) {
        inputs.add(item);
        return this;
    }

    public SpiritRepairRecipeBuilder addSpirit(MalumSpiritType type, int count) {
        spirits.add(new SpiritIngredient(type, count));
        return this;
    }

    @Override
    public SpiritRepairRecipe build(ResourceLocation resourceLocation) {
        List<ResourceLocation> inputIds = new ArrayList<>();
        for (Item input : inputs) {
            inputIds.add(BuiltInRegistries.ITEM.getKey(input));
        }
        return new SpiritRepairRecipe(this.durabilityPercentage, this.itemIdRegex, this.modIdRegex, inputIds, this.repairMaterial, this.spirits);
    }

    public void save(RecipeOutput recipeOutput) {
        save(recipeOutput, BuiltInRegistries.ITEM.getKey(inputs.get(0)).getPath());
    }

    public void save(RecipeOutput recipeOutput, String recipeName) {
        save(recipeOutput, MalumMod.malumPath("spirit_crucible/repair/" + recipeName));
    }

    @Override
    public void save(RecipeOutput recipeOutput, ResourceLocation resourceLocation) {
        defaultSaveFunc(recipeOutput, resourceLocation);
    }

    @Override
    public String getRecipeSubfolder() {
        return "spirit_repair";
    }
}
