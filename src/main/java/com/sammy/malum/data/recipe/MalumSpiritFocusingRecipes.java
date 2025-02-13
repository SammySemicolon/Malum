package com.sammy.malum.data.recipe;

import com.sammy.malum.*;
import com.sammy.malum.common.item.impetus.*;
import com.sammy.malum.data.recipe.builder.*;
import com.sammy.malum.registry.common.item.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;
import net.neoforged.neoforge.common.conditions.NotCondition;
import net.neoforged.neoforge.common.conditions.TagEmptyCondition;
import net.neoforged.neoforge.common.crafting.ConditionalRecipeOutput;
import net.neoforged.neoforge.registries.DeferredHolder;

import static com.sammy.malum.registry.common.SpiritTypeRegistry.*;
import static team.lodestar.lodestone.registry.common.tag.LodestoneItemTags.*;

public class MalumSpiritFocusingRecipes implements IConditionBuilder {

    protected static void buildRecipes(RecipeOutput recipeOutput) {
        int metalDuration = 900;
        int shortDuration = 300;

        new SpiritFocusingRecipeBuilder(shortDuration, 1, Ingredient.of(ItemRegistry.ALCHEMICAL_IMPETUS.get()), Items.GUNPOWDER, 8)
                .addSpirit(EARTHEN_SPIRIT, 1)
                .save(recipeOutput);

        new SpiritFocusingRecipeBuilder(shortDuration, 1, Ingredient.of(ItemRegistry.ALCHEMICAL_IMPETUS.get()), Items.GLOWSTONE_DUST, 8)
                .addSpirit(INFERNAL_SPIRIT, 1)
                .save(recipeOutput);

        new SpiritFocusingRecipeBuilder(shortDuration, 1, Ingredient.of(ItemRegistry.ALCHEMICAL_IMPETUS.get()), Items.REDSTONE, 8)
                .addSpirit(ARCANE_SPIRIT, 1)
                .save(recipeOutput);

        new SpiritFocusingRecipeBuilder(shortDuration, 1, Ingredient.of(ItemRegistry.ALCHEMICAL_IMPETUS.get()), Items.QUARTZ, 4)
                .addSpirit(EARTHEN_SPIRIT, 2)
                .addSpirit(ARCANE_SPIRIT, 2)
                .save(recipeOutput);

        new SpiritFocusingRecipeBuilder(shortDuration, 1, Ingredient.of(ItemRegistry.ALCHEMICAL_IMPETUS.get()), ItemRegistry.BLAZING_QUARTZ.get(), 4)
                .addSpirit(INFERNAL_SPIRIT, 2)
                .addSpirit(ARCANE_SPIRIT, 2)
                .save(recipeOutput);

        new SpiritFocusingRecipeBuilder(shortDuration, 1, Ingredient.of(ItemRegistry.ALCHEMICAL_IMPETUS.get()), Items.PRISMARINE_SHARD, 8)
                .addSpirit(AQUEOUS_SPIRIT, 2)
                .addSpirit(ARCANE_SPIRIT, 2)
                .save(recipeOutput);

        new SpiritFocusingRecipeBuilder(shortDuration, 1, Ingredient.of(ItemRegistry.ALCHEMICAL_IMPETUS.get()), Items.AMETHYST_SHARD, 8)
                .addSpirit(AERIAL_SPIRIT, 2)
                .addSpirit(ARCANE_SPIRIT, 2)
                .save(recipeOutput);

        addImpetusRecipes(recipeOutput, metalDuration, ItemRegistry.IRON_IMPETUS, ItemRegistry.IRON_NODE);
        addImpetusRecipes(recipeOutput, metalDuration, ItemRegistry.GOLD_IMPETUS, ItemRegistry.GOLD_NODE);
        addImpetusRecipes(recipeOutput, metalDuration, ItemRegistry.COPPER_IMPETUS, ItemRegistry.COPPER_NODE);
        addImpetusRecipes(recipeOutput, metalDuration, ItemRegistry.LEAD_IMPETUS, ItemRegistry.LEAD_NODE, NUGGETS_LEAD);
        addImpetusRecipes(recipeOutput, metalDuration, ItemRegistry.SILVER_IMPETUS, ItemRegistry.SILVER_NODE, NUGGETS_SILVER);
        addImpetusRecipes(recipeOutput, metalDuration, ItemRegistry.ALUMINUM_IMPETUS, ItemRegistry.ALUMINUM_NODE, NUGGETS_ALUMINUM);
        addImpetusRecipes(recipeOutput, metalDuration, ItemRegistry.NICKEL_IMPETUS, ItemRegistry.NICKEL_NODE, NUGGETS_NICKEL);
        addImpetusRecipes(recipeOutput, metalDuration, ItemRegistry.URANIUM_IMPETUS, ItemRegistry.URANIUM_NODE, NUGGETS_URANIUM);
        addImpetusRecipes(recipeOutput, metalDuration, ItemRegistry.OSMIUM_IMPETUS, ItemRegistry.OSMIUM_NODE, NUGGETS_OSMIUM);
        addImpetusRecipes(recipeOutput, metalDuration, ItemRegistry.ZINC_IMPETUS, ItemRegistry.ZINC_NODE, NUGGETS_ZINC);
        addImpetusRecipes(recipeOutput, metalDuration, ItemRegistry.TIN_IMPETUS, ItemRegistry.TIN_NODE, NUGGETS_TIN);
        addImpetusRecipes(recipeOutput, metalDuration, ItemRegistry.COBALT_IMPETUS, ItemRegistry.COBALT_NODE, ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "ingots/cobalt"))); //TODO: Move this tag to lodestone side.
    }

    public static void addImpetusRecipes(RecipeOutput recipeOutput, int duration, DeferredHolder<Item, ImpetusItem> impetus, DeferredHolder<Item, Item> node) {
        new SpiritFocusingRecipeBuilder(duration, 2, Ingredient.of(impetus.get()), node.get(), 3)
                .addSpirit(EARTHEN_SPIRIT, 2)
                .addSpirit(INFERNAL_SPIRIT, 2)
                .save(recipeOutput, MalumMod.malumPath("node_focusing_" + BuiltInRegistries.ITEM.getKey(node.get()).getPath().replace("_node", "")));
    }

    public static void addImpetusRecipes(RecipeOutput recipeOutput, int duration, DeferredHolder<Item, ImpetusItem> impetus, DeferredHolder<Item, Item> node, TagKey<Item> nugget) {
        new SpiritFocusingRecipeBuilder(duration, 2, Ingredient.of(impetus.get()), node.get(), 3)
                .addSpirit(EARTHEN_SPIRIT, 2)
                .addSpirit(INFERNAL_SPIRIT, 2)
                .save(new ConditionalRecipeOutput(recipeOutput, new ICondition[]{
                        new NotCondition(new TagEmptyCondition(nugget.location()))
                }), MalumMod.malumPath("node_focusing_" + nugget.location().getPath().replace("nuggets/", "")));
    }
}
