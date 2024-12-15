package com.sammy.malum.data.recipe.infusion;

import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import com.sammy.malum.data.recipe.builder.*;
import com.sammy.malum.registry.common.item.*;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.crafting.SizedIngredient;

import static com.sammy.malum.registry.common.SpiritTypeRegistry.*;

public class MaterialSpiritInfusionRecipes {

    public static void buildRecipes(RecipeOutput recipeOutput) {
        spiritedGlassRecipe(recipeOutput, SACRED_SPIRIT, ItemRegistry.SACRED_SPIRITED_GLASS.get());
        spiritedGlassRecipe(recipeOutput, WICKED_SPIRIT, ItemRegistry.WICKED_SPIRITED_GLASS.get());
        spiritedGlassRecipe(recipeOutput, ARCANE_SPIRIT, ItemRegistry.ARCANE_SPIRITED_GLASS.get());
        spiritedGlassRecipe(recipeOutput, ELDRITCH_SPIRIT, ItemRegistry.ELDRITCH_SPIRITED_GLASS.get());
        spiritedGlassRecipe(recipeOutput, AERIAL_SPIRIT, ItemRegistry.AERIAL_SPIRITED_GLASS.get());
        spiritedGlassRecipe(recipeOutput, AQUEOUS_SPIRIT, ItemRegistry.AQUEOUS_SPIRITED_GLASS.get());
        spiritedGlassRecipe(recipeOutput, EARTHEN_SPIRIT, ItemRegistry.EARTHEN_SPIRITED_GLASS.get());
        spiritedGlassRecipe(recipeOutput, INFERNAL_SPIRIT, ItemRegistry.INFERNAL_SPIRITED_GLASS.get());

        new SpiritInfusionRecipeBuilder(Items.GUNPOWDER, 1, ItemRegistry.HEX_ASH.get(), 1)
                .addSpirit(ARCANE_SPIRIT, 1)
                .save(recipeOutput);

        new SpiritInfusionRecipeBuilder(Items.ROTTEN_FLESH, 4, ItemRegistry.LIVING_FLESH.get(), 2)
                .addSpirit(SACRED_SPIRIT, 2)
                .addSpirit(WICKED_SPIRIT, 2)
                .save(recipeOutput);

        new SpiritInfusionRecipeBuilder(Items.CLAY_BALL, 4, ItemRegistry.ALCHEMICAL_CALX.get(), 4)
                .addSpirit(ARCANE_SPIRIT, 2)
                .addSpirit(EARTHEN_SPIRIT, 2)
                .addSpirit(AQUEOUS_SPIRIT, 2)
                .save(recipeOutput);

        new SpiritInfusionRecipeBuilder(Ingredient.of(ItemTags.COALS), 4, ItemRegistry.ARCANE_CHARCOAL.get(), 4)
                .addSpirit(ARCANE_SPIRIT, 1)
                .addSpirit(INFERNAL_SPIRIT, 2)
                .save(recipeOutput);

        new SpiritInfusionRecipeBuilder(Ingredient.of(ItemTags.STONE_TOOL_MATERIALS), 16, ItemRegistry.TAINTED_ROCK.get(), 16)
                .addSpirit(SACRED_SPIRIT, 1)
                .addSpirit(ARCANE_SPIRIT, 1)
                .save(recipeOutput);

        new SpiritInfusionRecipeBuilder(Ingredient.of(ItemTags.STONE_TOOL_MATERIALS), 16, ItemRegistry.TWISTED_ROCK.get(), 16)
                .addSpirit(WICKED_SPIRIT, 1)
                .addSpirit(ARCANE_SPIRIT, 1)
                .save(recipeOutput);

        new SpiritInfusionRecipeBuilder(Items.GLOWSTONE_DUST, 4, ItemRegistry.ETHER.get(), 2)
                .addSpirit(INFERNAL_SPIRIT, 2)
                .addSpirit(ARCANE_SPIRIT, 1)
                .addExtraItem(ItemRegistry.BLAZING_QUARTZ.get(), 1)
                .save(recipeOutput);

        new SpiritInfusionRecipeBuilder(ItemRegistry.ETHER.get(), 1, ItemRegistry.IRIDESCENT_ETHER.get(), 1)
                .addSpirit(AQUEOUS_SPIRIT, 2)
                .addExtraItem(Items.PRISMARINE_CRYSTALS, 1)
                .addExtraItem(ItemRegistry.ARCANE_CHARCOAL.get(), 1)
                .save(recipeOutput);

        new SpiritInfusionRecipeBuilder(Items.GOLD_INGOT, 1, ItemRegistry.HALLOWED_GOLD_INGOT.get(), 1)
                .addExtraItem(SizedIngredient.of(Tags.Items.GEMS_QUARTZ, 4))
                .addSpirit(SACRED_SPIRIT, 2)
                .addSpirit(ARCANE_SPIRIT, 1)
                .save(recipeOutput);

        new SpiritInfusionRecipeBuilder(Items.IRON_INGOT, 1, ItemRegistry.SOUL_STAINED_STEEL_INGOT.get(), 1)
                .addExtraItem(ItemRegistry.REFINED_SOULSTONE.get(), 4)
                .addSpirit(WICKED_SPIRIT, 3)
                .addSpirit(EARTHEN_SPIRIT, 1)
                .addSpirit(ARCANE_SPIRIT, 1)
                .save(recipeOutput);

        new SpiritInfusionRecipeBuilder(Ingredient.of(ItemTags.WOOL), 2, ItemRegistry.SOULWOVEN_SILK.get(), 4)
                .addExtraItem(SizedIngredient.of(Tags.Items.STRINGS, 2))
                .addExtraItem(ItemRegistry.HEX_ASH.get(), 1)
                .addSpirit(WICKED_SPIRIT, 2)
                .addSpirit(EARTHEN_SPIRIT, 1)
                .addSpirit(AERIAL_SPIRIT, 1)
                .save(recipeOutput);

        new SpiritInfusionRecipeBuilder(Items.HAY_BLOCK, 1, ItemRegistry.POPPET.get(), 2)
                .addSpirit(WICKED_SPIRIT, 4)
                .addSpirit(EARTHEN_SPIRIT, 4)
                .addExtraItem(Items.WHEAT, 2)
                .addExtraItem(ItemRegistry.HEX_ASH.get(), 1)
                .save(recipeOutput);

        new SpiritInfusionRecipeBuilder(Ingredient.of(Tags.Items.INGOTS_IRON), 4, ItemRegistry.ESOTERIC_SPOOL.get(), 4)
                .addSpirit(ARCANE_SPIRIT, 4)
                .addExtraItem(ItemRegistry.HEX_ASH.get(), 2)
                .save(recipeOutput);

        new SpiritInfusionRecipeBuilder(ItemRegistry.ANOMALOUS_DESIGN.get(), 1, ItemRegistry.COMPLETE_DESIGN.get(), 1)
                .addSpirit(SACRED_SPIRIT, 4)
                .addSpirit(WICKED_SPIRIT, 4)
                .addSpirit(ARCANE_SPIRIT, 4)
                .addSpirit(ELDRITCH_SPIRIT, 4)
                .addSpirit(AERIAL_SPIRIT, 4)
                .addSpirit(AQUEOUS_SPIRIT, 4)
                .addSpirit(EARTHEN_SPIRIT, 4)
                .addSpirit(INFERNAL_SPIRIT, 4)
                .save(recipeOutput);

        new SpiritInfusionRecipeBuilder(Ingredient.of(Tags.Items.INGOTS_IRON), 4, ItemRegistry.MALIGNANT_PEWTER_INGOT.get(), 1)
                .addExtraItem(ItemRegistry.MALIGNANT_LEAD.get(), 1)
                .addExtraItem(ItemRegistry.NULL_SLATE.get(), 8)
                .addExtraItem(Items.NETHERITE_SCRAP, 3)
                .addSpirit(EARTHEN_SPIRIT, 16)
                .addSpirit(ELDRITCH_SPIRIT, 4)
                .save(recipeOutput);
    }

    public static void spiritedGlassRecipe(RecipeOutput recipeOutput, MalumSpiritType spirit, Item glass) {
        new SpiritInfusionRecipeBuilder(Ingredient.of(Tags.Items.GLASS_BLOCKS), 16, glass, 16)
                .addSpirit(spirit, 2)
                .addExtraItem(Items.IRON_INGOT, 1)
                .save(recipeOutput);
    }
}
