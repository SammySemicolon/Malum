package com.sammy.malum.data.recipe;

import com.sammy.malum.data.recipe.crafting.*;
import com.sammy.malum.data.recipe.infusion.*;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.advancements.*;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.*;
import net.minecraft.data.recipes.*;
import net.minecraft.data.recipes.packs.*;
import net.minecraft.tags.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;

import java.util.*;
import java.util.concurrent.CompletableFuture;

public class MalumRecipes extends FabricRecipeProvider {

    MalumVanillaRecipeReplacements vanillaRecipeReplacements;

    public PackOutput pOutput;

    public MalumRecipes(FabricDataOutput pOutput, CompletableFuture<HolderLookup.Provider> registries) {
        super(pOutput, registries);
        this.pOutput = pOutput;
        this.vanillaRecipeReplacements = new MalumVanillaRecipeReplacements(pOutput, registries);
    }

    @Override
    public void buildRecipes(RecipeOutput recipeOutput) {
        vanillaRecipeReplacements.buildRecipes(recipeOutput);
        MalumVanillaRecipes.buildRecipes(recipeOutput);
        MalumWoodSetDatagen.buildRecipes(recipeOutput);
        MalumRockSetDatagen.buildRecipes(recipeOutput);

        ArtificeSpiritInfusionRecipes.buildRecipes(recipeOutput);
        CurioSpiritInfusionRecipes.buildRecipes(recipeOutput);
        GearSpiritInfusionRecipes.buildRecipes(recipeOutput);
        MaterialSpiritInfusionRecipes.buildRecipes(recipeOutput);
        TotemicSpiritInfusionRecipes.buildRecipes(recipeOutput);
        MiscSpiritInfusionRecipes.buildRecipes(recipeOutput);

        MalumRuneworkingRecipes.buildRecipes(recipeOutput);
        MalumSpiritFocusingRecipes.buildRecipes(recipeOutput);
        MalumSpiritRepairRecipes.buildRecipes(recipeOutput);
        MalumSpiritTransmutationRecipes.buildRecipes(recipeOutput);
        MalumVoidFavorRecipes.buildRecipes(recipeOutput);
    }

    public static Criterion<EnterBlockTrigger.TriggerInstance> insideOf(Block block) {
        return CriteriaTriggers.ENTER_BLOCK
                .createCriterion(new EnterBlockTrigger.TriggerInstance(Optional.empty(), Optional.of(block.builtInRegistryHolder()), Optional.empty()));
    }

    public static Criterion<InventoryChangeTrigger.TriggerInstance> has(MinMaxBounds.Ints count, ItemLike item) {
        return inventoryTrigger(ItemPredicate.Builder.item().of(item).withCount(count));
    }

    public static Criterion<InventoryChangeTrigger.TriggerInstance> has(ItemLike itemLike) {
        return inventoryTrigger(ItemPredicate.Builder.item().of(itemLike));
    }

    public static Criterion<InventoryChangeTrigger.TriggerInstance> has(TagKey<Item> tag) {
        return inventoryTrigger(ItemPredicate.Builder.item().of(tag));
    }

    public static Criterion<InventoryChangeTrigger.TriggerInstance> inventoryTrigger(ItemPredicate.Builder... items) {
        return inventoryTrigger(Arrays.stream(items).map(ItemPredicate.Builder::build).toArray(ItemPredicate[]::new));
    }

    public static Criterion<InventoryChangeTrigger.TriggerInstance> inventoryTrigger(ItemPredicate... predicates) {
        return CriteriaTriggers.INVENTORY_CHANGED
                .createCriterion(new InventoryChangeTrigger.TriggerInstance(Optional.empty(), InventoryChangeTrigger.TriggerInstance.Slots.ANY, List.of(predicates)));
    }
}
