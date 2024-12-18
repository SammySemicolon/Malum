package com.sammy.malum.data.recipe;

import com.sammy.malum.common.item.impetus.*;
import com.sammy.malum.data.recipe.builder.*;
import com.sammy.malum.registry.common.item.*;
import io.github.fabricators_of_create.porting_lib.tags.Tags;
import io.github.fabricators_of_create.porting_lib.util.DeferredHolder;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;

import java.util.*;

import static com.sammy.malum.registry.common.SpiritTypeRegistry.*;

public class MalumSpiritRepairRecipes   {

    protected static void buildRecipes(RecipeOutput recipeOutput) {
        var has = MalumRecipes.has(ItemRegistry.SPIRIT_CRUCIBLE.get());
        new SpiritRepairRecipeBuilder("wooden_.+", 0.5f, Ingredient.of(ItemTags.PLANKS), 4)
                .addSpirit(SACRED_SPIRIT, 4)
                .addSpirit(EARTHEN_SPIRIT, 4)
                .addItem(Items.BOW)
                .addItem(Items.CROSSBOW)
                .unlockedBy("has_crucible", has)
                .save(recipeOutput, "wooden");

        new SpiritRepairRecipeBuilder("flint_.+", 0.5f, Ingredient.of(Items.FLINT), 2)
                .addSpirit(EARTHEN_SPIRIT, 4)
                .unlockedBy("has_crucible", has)
                .save(recipeOutput, "flint");

        new SpiritRepairRecipeBuilder("stone_.+", 0.5f, Ingredient.of(ItemTags.STONE_TOOL_MATERIALS), 2)
                .addSpirit(EARTHEN_SPIRIT, 4)
                .unlockedBy("has_crucible", has)
                .save(recipeOutput, "stone");

        new SpiritRepairRecipeBuilder("copper_.+", 0.5f, Ingredient.of(Tags.Items.INGOTS_COPPER), 2)
                .addSpirit(EARTHEN_SPIRIT, 6)
                .unlockedBy("has_crucible", has)
                .save(recipeOutput, "copper");

        new SpiritRepairRecipeBuilder("iron_.+", 0.5f, Ingredient.of(Tags.Items.INGOTS_IRON), 2)
                .addItem(ItemRegistry.CRUDE_SCYTHE.get())
                .addSpirit(EARTHEN_SPIRIT, 8)
                .unlockedBy("has_crucible", has)
                .save(recipeOutput, "iron");

        new SpiritRepairRecipeBuilder("golden_.+", 0.5f, Ingredient.of(Tags.Items.INGOTS_GOLD), 2)
                .addSpirit(ARCANE_SPIRIT, 8)
                .unlockedBy("has_crucible", has)
                .save(recipeOutput, "gold");

        new SpiritRepairRecipeBuilder("diamond_.+", 0.5f, Ingredient.of(Tags.Items.GEMS_DIAMOND), 2)
                .addSpirit(ARCANE_SPIRIT, 16)
                .addSpirit(EARTHEN_SPIRIT, 16)
                .unlockedBy("has_crucible", has)
                .save(recipeOutput, "diamond");

        new SpiritRepairRecipeBuilder("netherite_.+", 0.5f, Ingredient.of(Tags.Items.INGOTS_NETHERITE), 1)
                .addSpirit(INFERNAL_SPIRIT, 16)
                .addSpirit(ARCANE_SPIRIT, 16)
                .addSpirit(EARTHEN_SPIRIT, 16)
                .unlockedBy("has_crucible", has)
                .save(recipeOutput, "netherite");

        new SpiritRepairRecipeBuilder(1.0f, Ingredient.of(Items.HEART_OF_THE_SEA), 1)
                .addItem(Items.TRIDENT)
                .addSpirit(AQUEOUS_SPIRIT, 16)
                .addSpirit(ARCANE_SPIRIT, 16)
                .unlockedBy("has_crucible", has)
                .save(recipeOutput, "trident");

        new SpiritRepairRecipeBuilder(1.0f, Ingredient.of(Items.BREEZE_ROD), 1)
                .addItem(Items.MACE)
                .addSpirit(AERIAL_SPIRIT, 16)
                .addSpirit(ARCANE_SPIRIT, 16)
                .unlockedBy("has_crucible", has)
                .save(recipeOutput, "mace");

        new SpiritRepairRecipeBuilder(0.75f, Ingredient.of(Items.OBSIDIAN), 2)
                .addItem(ItemRegistry.TYRVING.get())
                .addSpirit(WICKED_SPIRIT, 16)
                .addSpirit(ARCANE_SPIRIT, 8)
                .addSpirit(EARTHEN_SPIRIT, 8)
                .addSpirit(ELDRITCH_SPIRIT, 2)
                .unlockedBy("has_crucible", has)
                .save(recipeOutput, "tyrving");

        new SpiritRepairRecipeBuilder(0.75f, Ingredient.of(ItemRegistry.SOUL_STAINED_STEEL_INGOT.get()), 2)
                .addItem(ItemRegistry.SOUL_STAINED_STEEL_SCYTHE.get())
                .addItem(ItemRegistry.SOUL_STAINED_STEEL_HELMET.get())
                .addItem(ItemRegistry.SOUL_STAINED_STEEL_CHESTPLATE.get())
                .addItem(ItemRegistry.SOUL_STAINED_STEEL_LEGGINGS.get())
                .addItem(ItemRegistry.SOUL_STAINED_STEEL_BOOTS.get())
                .addSpirit(WICKED_SPIRIT, 8)
                .addSpirit(ARCANE_SPIRIT, 8)
                .addSpirit(EARTHEN_SPIRIT, 4)
                .unlockedBy("has_crucible", has)
                .save(recipeOutput, "special_soul_stained_steel");

        new SpiritRepairRecipeBuilder(0.75f, Ingredient.of(ItemRegistry.SOUL_STAINED_STEEL_INGOT.get()), 2)
                .addItem(ItemRegistry.SOUL_STAINED_STEEL_SWORD.get())
                .addItem(ItemRegistry.SOUL_STAINED_STEEL_AXE.get())
                .addItem(ItemRegistry.SOUL_STAINED_STEEL_PICKAXE.get())
                .addItem(ItemRegistry.SOUL_STAINED_STEEL_SHOVEL.get())
                .addItem(ItemRegistry.SOUL_STAINED_STEEL_HOE.get())
                .addItem(ItemRegistry.SOUL_STAINED_STEEL_KNIFE.get())
                .addSpirit(WICKED_SPIRIT, 8)
                .addSpirit(ARCANE_SPIRIT, 8)
                .addSpirit(EARTHEN_SPIRIT, 4)
                .unlockedBy("has_crucible", has)
                .save(recipeOutput, "soul_stained_steel");

        new SpiritRepairRecipeBuilder(0.75f, Ingredient.of(ItemRegistry.SOULWOVEN_SILK.get()), 2)
                .addItem(ItemRegistry.SOUL_HUNTER_CLOAK.get())
                .addItem(ItemRegistry.SOUL_HUNTER_ROBE.get())
                .addItem(ItemRegistry.SOUL_HUNTER_LEGGINGS.get())
                .addItem(ItemRegistry.SOUL_HUNTER_BOOTS.get())
                .addSpirit(WICKED_SPIRIT, 8)
                .addSpirit(ARCANE_SPIRIT, 8)
                .addSpirit(AERIAL_SPIRIT, 4)
                .unlockedBy("has_crucible", has)
                .save(recipeOutput, "soul_hunter_armor");

        new SpiritRepairRecipeBuilder(1f, Ingredient.of(ItemRegistry.ALCHEMICAL_CALX.get()), 2)
                .addItem(ItemRegistry.CRACKED_ALCHEMICAL_IMPETUS.get())
                .addSpirit(ARCANE_SPIRIT, 4)
                .addSpirit(EARTHEN_SPIRIT, 4)
                .overrideOutput(ItemRegistry.ALCHEMICAL_IMPETUS.get())
                .unlockedBy("has_crucible", has)
                .save(recipeOutput, "alchemical_impetus_restoration");

        List<DeferredHolder<Item, ? extends Item>> metalImpetus = ItemRegistry.ITEMS.getEntries().stream().filter(i -> i.get() instanceof ImpetusItem).sorted((d1, d2) -> d1.getId().compareNamespaced(d2.getId())).toList();
        List<DeferredHolder<Item, ? extends Item>> crackedMetalImpetus = ItemRegistry.ITEMS.getEntries().stream().filter(i -> i.get() instanceof CrackedImpetusItem).sorted((d1, d2) -> d1.getId().compareNamespaced(d2.getId())).toList();
        for (int i = 0; i < metalImpetus.size(); i++) {
            var impetus = metalImpetus.get(i);
            var crackedImpetus = crackedMetalImpetus.get(i);
            if (impetus.get().equals(ItemRegistry.ALCHEMICAL_IMPETUS.get())) {
                continue;
            }
            new SpiritRepairRecipeBuilder(1f, Ingredient.of(ItemRegistry.CTHONIC_GOLD_FRAGMENT.get()), 2)
                    .addItem(crackedImpetus.get())
                    .overrideOutput(impetus.get())
                    .addSpirit(INFERNAL_SPIRIT, 8)
                    .addSpirit(EARTHEN_SPIRIT, 8)
                    .unlockedBy("has_crucible", has)
                    .save(recipeOutput, impetus.getId().getPath().split("_")[0] + "_impetus_restoration");
        }
    }
}
