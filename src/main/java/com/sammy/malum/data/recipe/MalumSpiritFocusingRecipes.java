package com.sammy.malum.data.recipe;

import com.sammy.malum.*;
import com.sammy.malum.common.item.impetus.*;
import com.sammy.malum.data.recipe.builder.*;
import com.sammy.malum.registry.common.item.*;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;
import net.minecraftforge.common.crafting.*;
import net.minecraftforge.common.crafting.conditions.*;
import net.minecraftforge.registries.*;

import java.util.function.*;

import static com.sammy.malum.registry.common.SpiritTypeRegistry.*;
import static team.lodestar.lodestone.registry.common.tag.LodestoneItemTags.*;

public class MalumSpiritFocusingRecipes implements IConditionBuilder {

    protected static void buildRecipes(Consumer<FinishedRecipe> consumer) {
        int metalDuration = 900;
        int shortDuration = 300;

        new SpiritFocusingRecipeBuilder(shortDuration, 1, Ingredient.of(ItemRegistry.ALCHEMICAL_IMPETUS.get()), Items.GUNPOWDER, 8)
                .addSpirit(EARTHEN_SPIRIT, 1)
                .build(consumer);

        new SpiritFocusingRecipeBuilder(shortDuration, 1, Ingredient.of(ItemRegistry.ALCHEMICAL_IMPETUS.get()), Items.GLOWSTONE_DUST, 8)
                .addSpirit(INFERNAL_SPIRIT, 1)
                .build(consumer);

        new SpiritFocusingRecipeBuilder(shortDuration, 1, Ingredient.of(ItemRegistry.ALCHEMICAL_IMPETUS.get()), Items.REDSTONE, 8)
                .addSpirit(ARCANE_SPIRIT, 1)
                .build(consumer);

        new SpiritFocusingRecipeBuilder(shortDuration, 1, Ingredient.of(ItemRegistry.ALCHEMICAL_IMPETUS.get()), Items.QUARTZ, 4)
                .addSpirit(EARTHEN_SPIRIT, 2)
                .addSpirit(ARCANE_SPIRIT, 2)
                .build(consumer);

        new SpiritFocusingRecipeBuilder(shortDuration, 1, Ingredient.of(ItemRegistry.ALCHEMICAL_IMPETUS.get()), ItemRegistry.BLAZING_QUARTZ.get(), 4)
                .addSpirit(INFERNAL_SPIRIT, 2)
                .addSpirit(ARCANE_SPIRIT, 2)
                .build(consumer);

        new SpiritFocusingRecipeBuilder(shortDuration, 1, Ingredient.of(ItemRegistry.ALCHEMICAL_IMPETUS.get()), Items.PRISMARINE_SHARD, 8)
                .addSpirit(AQUEOUS_SPIRIT, 2)
                .addSpirit(ARCANE_SPIRIT, 2)
                .build(consumer);

        new SpiritFocusingRecipeBuilder(shortDuration, 1, Ingredient.of(ItemRegistry.ALCHEMICAL_IMPETUS.get()), Items.AMETHYST_SHARD, 8)
                .addSpirit(AERIAL_SPIRIT, 2)
                .addSpirit(ARCANE_SPIRIT, 2)
                .build(consumer);

        addImpetusRecipes(consumer, metalDuration, ItemRegistry.IRON_IMPETUS, ItemRegistry.IRON_NODE);
        addImpetusRecipes(consumer, metalDuration, ItemRegistry.GOLD_IMPETUS, ItemRegistry.GOLD_NODE);
        addImpetusRecipes(consumer, metalDuration, ItemRegistry.COPPER_IMPETUS, ItemRegistry.COPPER_NODE);
        addImpetusRecipes(consumer, metalDuration, ItemRegistry.LEAD_IMPETUS, ItemRegistry.LEAD_NODE, NUGGETS_LEAD);
        addImpetusRecipes(consumer, metalDuration, ItemRegistry.SILVER_IMPETUS, ItemRegistry.SILVER_NODE, NUGGETS_SILVER);
        addImpetusRecipes(consumer, metalDuration, ItemRegistry.ALUMINUM_IMPETUS, ItemRegistry.ALUMINUM_NODE, NUGGETS_ALUMINUM);
        addImpetusRecipes(consumer, metalDuration, ItemRegistry.NICKEL_IMPETUS, ItemRegistry.NICKEL_NODE, NUGGETS_NICKEL);
        addImpetusRecipes(consumer, metalDuration, ItemRegistry.URANIUM_IMPETUS, ItemRegistry.URANIUM_NODE, NUGGETS_URANIUM);
        addImpetusRecipes(consumer, metalDuration, ItemRegistry.OSMIUM_IMPETUS, ItemRegistry.OSMIUM_NODE, NUGGETS_OSMIUM);
        addImpetusRecipes(consumer, metalDuration, ItemRegistry.ZINC_IMPETUS, ItemRegistry.ZINC_NODE, NUGGETS_ZINC);
        addImpetusRecipes(consumer, metalDuration, ItemRegistry.TIN_IMPETUS, ItemRegistry.TIN_NODE, NUGGETS_TIN);
        //TODO: Move this tag V to Lodestone's side. (LodestoneItemTags.class)
        addImpetusRecipes(consumer, metalDuration, ItemRegistry.COBALT_IMPETUS, ItemRegistry.COBALT_NODE, ItemTags.create(new ResourceLocation("forge", "nuggets/cobalt")));
    }

    public static void addImpetusRecipes(Consumer<FinishedRecipe> consumer, int duration, RegistryObject<ImpetusItem> impetus, RegistryObject<Item> node) {
        new SpiritFocusingRecipeBuilder(duration, 2, Ingredient.of(impetus.get()), node.get(), 3)
                .addSpirit(EARTHEN_SPIRIT, 2)
                .addSpirit(INFERNAL_SPIRIT, 2)
                .build(consumer, MalumMod.malumPath("node_focusing_" + ForgeRegistries.ITEMS.getKey(node.get()).getPath().replace("_node", "")));
    }

    public static void addImpetusRecipes(Consumer<FinishedRecipe> consumer, int duration, RegistryObject<ImpetusItem> impetus, RegistryObject<Item> node, TagKey<Item> nugget) {
        ConditionalRecipe.builder().addCondition(new NotCondition(new TagEmptyCondition(nugget.location()))).addRecipe(
                        new SpiritFocusingRecipeBuilder(duration, 2, Ingredient.of(impetus.get()), node.get(), 3)
                                .addSpirit(EARTHEN_SPIRIT, 2)
                                .addSpirit(INFERNAL_SPIRIT, 2)
                                ::build
                )
                .generateAdvancement()
                .build(consumer, MalumMod.malumPath("node_focusing_" + nugget.location().getPath().replace("nuggets/", "")));
    }
}
