package com.sammy.malum.data.recipe;

import com.sammy.malum.*;
import com.sammy.malum.common.item.impetus.*;
import com.sammy.malum.data.recipe.builder.*;
import com.sammy.malum.registry.common.item.*;
import net.minecraft.core.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.*;
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
        int complexDuration = 2700;
        int metalDuration = 900;
        int shortDuration = 300;

        new SpiritFocusingRecipeBuilder(shortDuration, 1, ItemRegistry.ALCHEMICAL_IMPETUS, Items.GUNPOWDER, 8)
                .addSpirit(EARTHEN_SPIRIT, 1)
                .save(recipeOutput);

        new SpiritFocusingRecipeBuilder(shortDuration, 1, ItemRegistry.ALCHEMICAL_IMPETUS, Items.GLOWSTONE_DUST, 8)
                .addSpirit(INFERNAL_SPIRIT, 1)
                .save(recipeOutput);

        new SpiritFocusingRecipeBuilder(shortDuration, 1, ItemRegistry.ALCHEMICAL_IMPETUS, Items.REDSTONE, 8)
                .addSpirit(ARCANE_SPIRIT, 1)
                .save(recipeOutput);

        new SpiritFocusingRecipeBuilder(shortDuration, 1, ItemRegistry.ALCHEMICAL_IMPETUS, Items.QUARTZ, 4)
                .addSpirit(EARTHEN_SPIRIT, 2)
                .addSpirit(ARCANE_SPIRIT, 2)
                .save(recipeOutput);

        new SpiritFocusingRecipeBuilder(shortDuration, 1, ItemRegistry.ALCHEMICAL_IMPETUS, ItemRegistry.BLAZING_QUARTZ.get(), 4)
                .addSpirit(INFERNAL_SPIRIT, 2)
                .addSpirit(ARCANE_SPIRIT, 2)
                .save(recipeOutput);

        new SpiritFocusingRecipeBuilder(shortDuration, 1, ItemRegistry.ALCHEMICAL_IMPETUS, Items.PRISMARINE_SHARD, 4)
                .addSpirit(AQUEOUS_SPIRIT, 2)
                .addSpirit(ARCANE_SPIRIT, 2)
                .save(recipeOutput);

        new SpiritFocusingRecipeBuilder(shortDuration, 1, ItemRegistry.ALCHEMICAL_IMPETUS, Items.AMETHYST_SHARD, 4)
                .addSpirit(AERIAL_SPIRIT, 2)
                .addSpirit(ARCANE_SPIRIT, 2)
                .save(recipeOutput);

        new SpiritFocusingRecipeBuilder(complexDuration, 1, ItemRegistry.ZEPHYR_IMPETUS, Items.WIND_CHARGE, 4)
                .addSpirit(AERIAL_SPIRIT, 2)
                .addSpirit(ARCANE_SPIRIT, 2)
                .addSpirit(SACRED_SPIRIT, 2)
                .save(recipeOutput);

        new SpiritFocusingRecipeBuilder(complexDuration, 1, ItemRegistry.ZEPHYR_IMPETUS, ItemRegistry.WIND_NUCLEUS.get(), 4)
                .addSpirit(AERIAL_SPIRIT, 2)
                .addSpirit(ARCANE_SPIRIT, 2)
                .addSpirit(WICKED_SPIRIT, 2)
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
    }

    public static void addImpetusRecipes(RecipeOutput recipeOutput, int duration, Holder<Item> impetus, Holder<Item> node) {
        var recipeName = MalumMod.malumPath("node_focusing_" + BuiltInRegistries.ITEM.getKey(node.value()).getPath().replace("_node", ""));
        new SpiritFocusingRecipeBuilder(duration, 2, Ingredient.of(impetus.value()), node.value(), 3)
                .addSpirit(EARTHEN_SPIRIT, 2)
                .addSpirit(INFERNAL_SPIRIT, 2)
                .save(recipeOutput, recipeName);
    }

    public static void addImpetusRecipes(RecipeOutput recipeOutput, int duration, Holder<Item> impetus, Holder<Item> node, TagKey<Item> nugget) {
        var recipeName = MalumMod.malumPath("node_focusing_" + nugget.location().getPath().replace("nuggets/", ""));
        new SpiritFocusingRecipeBuilder(duration, 2, Ingredient.of(impetus.value()), node.value(), 3)
                .addSpirit(EARTHEN_SPIRIT, 2)
                .addSpirit(INFERNAL_SPIRIT, 2)
                .save(recipeOutput.withConditions(
                        new NotCondition(
                                new TagEmptyCondition(nugget.location())
                        )), recipeName);
    }
}