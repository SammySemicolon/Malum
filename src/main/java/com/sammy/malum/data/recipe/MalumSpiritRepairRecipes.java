package com.sammy.malum.data.recipe;

import com.sammy.malum.data.recipe.builder.*;
import com.sammy.malum.registry.common.item.*;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;

import static com.sammy.malum.registry.common.SpiritTypeRegistry.*;

public class MalumSpiritRepairRecipes implements IConditionBuilder {

    protected static void buildRecipes(RecipeOutput recipeOutput) {

        new SpiritRepairRecipeBuilder("wooden_.+", 0.5f, Ingredient.of(ItemTags.PLANKS), 4)
                .addSpirit(EARTHEN_SPIRIT, 2)
                .save(recipeOutput, "wooden");

        new SpiritRepairRecipeBuilder("flint_.+", 0.5f, Ingredient.of(Items.FLINT), 2)
                .addSpirit(EARTHEN_SPIRIT, 4)
                .save(recipeOutput, "flint");

        new SpiritRepairRecipeBuilder("stone_.+", 0.5f, Ingredient.of(ItemTags.STONE_TOOL_MATERIALS), 2)
                .addSpirit(EARTHEN_SPIRIT, 4)
                .save(recipeOutput, "stone");

        new SpiritRepairRecipeBuilder("copper_.+", 0.5f, Ingredient.of(Tags.Items.INGOTS_COPPER), 2)
                .addSpirit(EARTHEN_SPIRIT, 6)
                .save(recipeOutput, "copper");

        new SpiritRepairRecipeBuilder("iron_.+", 0.5f, Ingredient.of(Tags.Items.INGOTS_IRON), 2)
                .addItem(ItemRegistry.CRUDE_SCYTHE.get())
                .addSpirit(EARTHEN_SPIRIT, 8)
                .save(recipeOutput, "iron");

        new SpiritRepairRecipeBuilder("golden_.+", 0.5f, Ingredient.of(Tags.Items.INGOTS_GOLD), 2)
                .addSpirit(ARCANE_SPIRIT, 8)
                .save(recipeOutput, "gold");

        new SpiritRepairRecipeBuilder("diamond_.+", 0.5f, Ingredient.of(Tags.Items.GEMS_DIAMOND), 2)
                .addSpirit(ARCANE_SPIRIT, 16)
                .addSpirit(EARTHEN_SPIRIT, 8)
                .save(recipeOutput, "diamond");

        new SpiritRepairRecipeBuilder("netherite_.+", 0.5f, Ingredient.of(Tags.Items.INGOTS_NETHERITE), 1)
                .addSpirit(INFERNAL_SPIRIT, 16)
                .addSpirit(ARCANE_SPIRIT, 8)
                .addSpirit(EARTHEN_SPIRIT, 8)
                .addSpirit(ELDRITCH_SPIRIT, 1)
                .save(recipeOutput, "netherite");

        new SpiritRepairRecipeBuilder(1.0f, Ingredient.of(Items.HEART_OF_THE_SEA), 1)
                .addItem(Items.TRIDENT)
                .addSpirit(AQUEOUS_SPIRIT, 16)
                .addSpirit(ARCANE_SPIRIT, 16)
                .save(recipeOutput, "trident");

        new SpiritRepairRecipeBuilder(0.75f, Ingredient.of(ItemRegistry.TWISTED_ROCK.get()), 8)
                .addItem(ItemRegistry.TYRVING.get())
                .addSpirit(WICKED_SPIRIT, 16)
                .addSpirit(ARCANE_SPIRIT, 8)
                .addSpirit(EARTHEN_SPIRIT, 8)
                .addSpirit(ELDRITCH_SPIRIT, 2)
                .save(recipeOutput, "tyrving");

        new SpiritRepairRecipeBuilder(0.75f, Ingredient.of(ItemRegistry.SOUL_STAINED_STEEL_INGOT.get()), 4)
                .addItem(ItemRegistry.SOUL_STAINED_STEEL_SCYTHE.get())
                .addItem(ItemRegistry.SOUL_STAINED_STEEL_HELMET.get())
                .addItem(ItemRegistry.SOUL_STAINED_STEEL_CHESTPLATE.get())
                .addItem(ItemRegistry.SOUL_STAINED_STEEL_LEGGINGS.get())
                .addItem(ItemRegistry.SOUL_STAINED_STEEL_BOOTS.get())
                .addSpirit(ARCANE_SPIRIT, 16)
                .addSpirit(EARTHEN_SPIRIT, 8)
                .addSpirit(WICKED_SPIRIT, 4)
                .save(recipeOutput, "special_soul_stained_steel");

        new SpiritRepairRecipeBuilder(0.75f, Ingredient.of(ItemRegistry.SOUL_STAINED_STEEL_INGOT.get()), 2)
                .addItem(ItemRegistry.SOUL_STAINED_STEEL_SWORD.get())
                .addItem(ItemRegistry.SOUL_STAINED_STEEL_AXE.get())
                .addItem(ItemRegistry.SOUL_STAINED_STEEL_PICKAXE.get())
                .addItem(ItemRegistry.SOUL_STAINED_STEEL_SHOVEL.get())
                .addItem(ItemRegistry.SOUL_STAINED_STEEL_HOE.get())
                .addItem(ItemRegistry.SOUL_STAINED_STEEL_KNIFE.get())
                .addSpirit(ARCANE_SPIRIT, 8)
                .addSpirit(EARTHEN_SPIRIT, 4)
                .addSpirit(WICKED_SPIRIT, 2)
                .save(recipeOutput, "soul_stained_steel");

        new SpiritRepairRecipeBuilder(0.75f, Ingredient.of(ItemRegistry.SPIRIT_FABRIC.get()), 4)
                .addItem(ItemRegistry.SOUL_HUNTER_CLOAK.get())
                .addItem(ItemRegistry.SOUL_HUNTER_ROBE.get())
                .addItem(ItemRegistry.SOUL_HUNTER_LEGGINGS.get())
                .addItem(ItemRegistry.SOUL_HUNTER_BOOTS.get())
                .addSpirit(ARCANE_SPIRIT, 16)
                .addSpirit(AERIAL_SPIRIT, 8)
                .addSpirit(WICKED_SPIRIT, 4)
                .save(recipeOutput, "soul_hunter_armor");

        new SpiritRepairRecipeBuilder("none", 1f, Ingredient.of(ItemRegistry.ALCHEMICAL_CALX.get()), 2)
                .addItem(ItemRegistry.ALCHEMICAL_IMPETUS.get().getCrackedVariant())
                .addSpirit(EARTHEN_SPIRIT, 4)
                .addSpirit(ARCANE_SPIRIT, 4)
                .save(recipeOutput, "alchemical_impetus_restoration");

        new SpiritRepairRecipeBuilder("none", 1f, Ingredient.of(ItemRegistry.CTHONIC_GOLD_FRAGMENT.get()), 2)
                .addItem(ItemRegistry.CRACKED_IRON_IMPETUS.get())
                .addItem(ItemRegistry.CRACKED_GOLD_IMPETUS.get())
                .addItem(ItemRegistry.CRACKED_COPPER_IMPETUS.get())
                .addItem(ItemRegistry.CRACKED_LEAD_IMPETUS.get())
                .addItem(ItemRegistry.CRACKED_SILVER_IMPETUS.get())
                .addItem(ItemRegistry.CRACKED_ALUMINUM_IMPETUS.get())
                .addItem(ItemRegistry.CRACKED_NICKEL_IMPETUS.get())
                .addItem(ItemRegistry.CRACKED_URANIUM_IMPETUS.get())
                .addItem(ItemRegistry.CRACKED_OSMIUM_IMPETUS.get())
                .addItem(ItemRegistry.CRACKED_ZINC_IMPETUS.get())
                .addItem(ItemRegistry.CRACKED_TIN_IMPETUS.get())
                .addSpirit(INFERNAL_SPIRIT, 16)
                .addSpirit(EARTHEN_SPIRIT, 16)
                .save(recipeOutput, "metal_impetus_restoration");
    }
}
