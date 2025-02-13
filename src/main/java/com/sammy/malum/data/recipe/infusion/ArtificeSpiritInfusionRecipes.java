package com.sammy.malum.data.recipe.infusion;

import com.sammy.malum.*;
import com.sammy.malum.common.item.impetus.*;
import com.sammy.malum.data.recipe.builder.*;
import com.sammy.malum.registry.common.item.*;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.crafting.SizedIngredient;
import net.neoforged.neoforge.registries.DeferredHolder;

import static com.sammy.malum.registry.common.SpiritTypeRegistry.*;
import static team.lodestar.lodestone.registry.common.tag.LodestoneItemTags.*;

public class ArtificeSpiritInfusionRecipes {

    public static void buildRecipes(RecipeOutput recipeOutput) {
        new SpiritInfusionRecipeBuilder(Items.COPPER_BLOCK, 1, ItemRegistry.WAVECHARGER.get(), 2)
                .addSpirit(AERIAL_SPIRIT, 4)
                .addSpirit(ARCANE_SPIRIT, 2)
                .addExtraItem(Items.REDSTONE, 4)
                .addExtraItem(ItemRegistry.ETHER.get(), 1)
                .addExtraItem(ItemTagRegistry.RUNEWOOD_PLANKS, 2)
                .save(recipeOutput);
        new SpiritInfusionRecipeBuilder(Items.COPPER_BLOCK, 1, ItemRegistry.WAVEBANKER.get(), 2)
                .addSpirit(AQUEOUS_SPIRIT, 4)
                .addSpirit(ARCANE_SPIRIT, 2)
                .addExtraItem(Items.REDSTONE, 4)
                .addExtraItem(ItemRegistry.ETHER.get(), 1)
                .addExtraItem(ItemTagRegistry.RUNEWOOD_PLANKS, 2)
                .save(recipeOutput);
        new SpiritInfusionRecipeBuilder(Items.COPPER_BLOCK, 1, ItemRegistry.WAVEMAKER.get(), 2)
                .addSpirit(EARTHEN_SPIRIT, 4)
                .addSpirit(ARCANE_SPIRIT, 2)
                .addExtraItem(Items.REDSTONE, 4)
                .addExtraItem(ItemRegistry.ETHER.get(), 1)
                .addExtraItem(ItemTagRegistry.RUNEWOOD_PLANKS, 2)
                .save(recipeOutput);
        new SpiritInfusionRecipeBuilder(Items.COPPER_BLOCK, 1, ItemRegistry.WAVEBREAKER.get(), 2)
                .addSpirit(INFERNAL_SPIRIT, 4)
                .addSpirit(ARCANE_SPIRIT, 2)
                .addExtraItem(Items.REDSTONE, 4)
                .addExtraItem(ItemRegistry.ETHER.get(), 1)
                .addExtraItem(ItemTagRegistry.RUNEWOOD_PLANKS, 2)
                .save(recipeOutput);

        new SpiritInfusionRecipeBuilder(Ingredient.of(Tags.Items.INGOTS_COPPER), 2, ItemRegistry.ARTIFICERS_CLAW.get(), 1)
                .addSpirit(EARTHEN_SPIRIT, 8)
                .addSpirit(AQUEOUS_SPIRIT, 8)
                .addExtraItem(ItemRegistry.RUNEWOOD_PLANKS.get(), 2)
                .addExtraItem(Items.REDSTONE_BLOCK, 1)
                .save(recipeOutput);


        new SpiritInfusionRecipeBuilder(Items.FURNACE, 1, ItemRegistry.SPIRIT_CRUCIBLE.get(), 1)
                .addSpirit(INFERNAL_SPIRIT, 8)
                .addSpirit(AQUEOUS_SPIRIT, 8)
                .addExtraItem(ItemRegistry.HEX_ASH.get(), 2)
                .addExtraItem(ItemRegistry.TAINTED_ROCK.get(), 2)
                .addExtraItem(ItemRegistry.TWISTED_ROCK.get(), 2)
                .save(recipeOutput);

        new SpiritInfusionRecipeBuilder(ItemRegistry.ALCHEMICAL_CALX.get(), 4, ItemRegistry.ALCHEMICAL_IMPETUS.get(), 1)
                .addSpirit(ARCANE_SPIRIT, 4)
                .addSpirit(EARTHEN_SPIRIT, 4)
                .addExtraItem(ItemRegistry.REFINED_SOULSTONE.get(), 4)
                .addExtraItem(ItemRegistry.HEX_ASH.get(), 2)
                .save(recipeOutput);

        new SpiritInfusionRecipeBuilder(Ingredient.of(Tags.Items.INGOTS_IRON), 2, ItemRegistry.TUNING_FORK.get(), 1)
                .addSpirit(ARCANE_SPIRIT, 8)
                .addSpirit(AQUEOUS_SPIRIT, 8)
                .addExtraItem(ItemRegistry.RUNEWOOD_PLANKS.get(), 2)
                .addExtraItem(ItemRegistry.REFINED_SOULSTONE.get(), 2)
                .save(recipeOutput);

        new SpiritInfusionRecipeBuilder(ItemRegistry.ALCHEMICAL_CALX.get(), 6, ItemRegistry.SYMPATHY_DRIVE.get(), 1)
                .addSpirit(SACRED_SPIRIT, 16)
                .addSpirit(WICKED_SPIRIT, 16)
                .addExtraItem(ItemRegistry.LIVING_FLESH.get(), 8)
                .addExtraItem(ItemRegistry.REFINED_SOULSTONE.get(), 8)
                .save(recipeOutput);

        new SpiritInfusionRecipeBuilder(ItemRegistry.ALCHEMICAL_CALX.get(), 6, ItemRegistry.SUSPICIOUS_DEVICE.get(), 1)
                .addSpirit(ARCANE_SPIRIT, 16)
                .addSpirit(ELDRITCH_SPIRIT, 16)
                .addExtraItem(ItemRegistry.WARP_FLUX.get(), 4)
                .save(recipeOutput);

        new SpiritInfusionRecipeBuilder(ItemRegistry.ALCHEMICAL_CALX.get(), 6, ItemRegistry.CAUSTIC_CATALYST.get(), 1)
                .addSpirit(AQUEOUS_SPIRIT, 16)
                .addSpirit(INFERNAL_SPIRIT, 16)
                .addExtraItem(ItemRegistry.BLAZING_QUARTZ.get(), 8)
                .addExtraItem(Items.PRISMARINE_CRYSTALS, 8)
                .save(recipeOutput);

        new SpiritInfusionRecipeBuilder(ItemRegistry.ALCHEMICAL_CALX.get(), 6, ItemRegistry.RESONANCE_TUNER.get(), 1)
                .addSpirit(EARTHEN_SPIRIT, 16)
                .addSpirit(AERIAL_SPIRIT, 16)
                .addExtraItem(ItemRegistry.ASTRAL_WEAVE.get(), 8)
                .addExtraItem(ItemRegistry.REFINED_BRILLIANCE.get(), 8)
                .save(recipeOutput);

        new SpiritInfusionRecipeBuilder(ItemRegistry.ALCHEMICAL_CALX.get(), 4, ItemRegistry.MENDING_DIFFUSER.get(), 1)
                .addSpirit(SACRED_SPIRIT, 8)
                .addSpirit(ARCANE_SPIRIT, 4)
                .addExtraItem(ItemRegistry.LIVING_FLESH.get(), 2)
                .addExtraItem(ItemRegistry.REFINED_SOULSTONE.get(), 2)
                .save(recipeOutput);

        new SpiritInfusionRecipeBuilder(ItemRegistry.ALCHEMICAL_CALX.get(), 4, ItemRegistry.IMPURITY_STABILIZER.get(), 1)
                .addSpirit(WICKED_SPIRIT, 8)
                .addSpirit(ARCANE_SPIRIT, 4)
                .addExtraItem(ItemRegistry.HEX_ASH.get(), 2)
                .addExtraItem(ItemRegistry.REFINED_SOULSTONE.get(), 2)
                .save(recipeOutput);

        new SpiritInfusionRecipeBuilder(ItemRegistry.ALCHEMICAL_CALX.get(), 4, ItemRegistry.SHIELDING_APPARATUS.get(), 1)
                .addSpirit(ARCANE_SPIRIT, 8)
                .addExtraItem(ItemRegistry.SOUL_STAINED_STEEL_PLATING.get(), 2)
                .addExtraItem(ItemRegistry.REFINED_SOULSTONE.get(), 2)
                .save(recipeOutput);

        new SpiritInfusionRecipeBuilder(ItemRegistry.ALCHEMICAL_CALX.get(), 4, ItemRegistry.WARPING_ENGINE.get(), 1)
                .addSpirit(ELDRITCH_SPIRIT, 2)
                .addSpirit(ARCANE_SPIRIT, 4)
                .addExtraItem(ItemRegistry.WARP_FLUX.get(), 2)
                .addExtraItem(ItemRegistry.REFINED_SOULSTONE.get(), 2)
                .save(recipeOutput);

        new SpiritInfusionRecipeBuilder(ItemRegistry.ALCHEMICAL_CALX.get(), 4, ItemRegistry.ACCELERATING_INLAY.get(), 1)
                .addSpirit(AERIAL_SPIRIT, 8)
                .addSpirit(ARCANE_SPIRIT, 4)
                .addExtraItem(ItemRegistry.ASTRAL_WEAVE.get(), 2)
                .addExtraItem(ItemRegistry.REFINED_SOULSTONE.get(), 2)
                .save(recipeOutput);

        new SpiritInfusionRecipeBuilder(ItemRegistry.ALCHEMICAL_CALX.get(), 4, ItemRegistry.PRISMATIC_FOCUS_LENS.get(), 1)
                .addSpirit(AQUEOUS_SPIRIT, 8)
                .addSpirit(ARCANE_SPIRIT, 4)
                .addExtraItem(SizedIngredient.of(Tags.Items.GEMS_PRISMARINE, 2))
                .addExtraItem(ItemRegistry.REFINED_SOULSTONE.get(), 2)
                .save(recipeOutput);

        new SpiritInfusionRecipeBuilder(ItemRegistry.ALCHEMICAL_CALX.get(), 4, ItemRegistry.BLAZING_DIODE.get(), 1)
                .addSpirit(INFERNAL_SPIRIT, 8)
                .addSpirit(ARCANE_SPIRIT, 4)
                .addExtraItem(ItemRegistry.BLAZING_QUARTZ.get(), 2)
                .addExtraItem(ItemRegistry.REFINED_SOULSTONE.get(), 2)
                .save(recipeOutput);

        new SpiritInfusionRecipeBuilder(ItemRegistry.ALCHEMICAL_CALX.get(), 4, ItemRegistry.INTRICATE_ASSEMBLY.get(), 1)
                .addSpirit(EARTHEN_SPIRIT, 8)
                .addSpirit(ARCANE_SPIRIT, 4)
                .addExtraItem(SizedIngredient.of(Tags.Items.GEMS_EMERALD, 2))
                .addExtraItem(ItemRegistry.REFINED_SOULSTONE.get(), 2)
                .save(recipeOutput);

        new SpiritInfusionRecipeBuilder(ItemRegistry.TWISTED_ROCK_ITEM_PEDESTAL.get(), 1, ItemRegistry.SPIRIT_CATALYZER.get(), 1)
                .addSpirit(INFERNAL_SPIRIT, 8)
                .addSpirit(AERIAL_SPIRIT, 8)
                .addExtraItem(ItemRegistry.TAINTED_ROCK.get(), 4)
                .addExtraItem(ItemRegistry.ETHER.get(), 1)
                .addExtraItem(ItemRegistry.TWISTED_ROCK.get(), 4)
                .save(recipeOutput);

        new SpiritInfusionRecipeBuilder(ItemRegistry.TAINTED_ROCK_ITEM_PEDESTAL.get(), 1, ItemRegistry.REPAIR_PYLON.get(), 1)
                .addSpirit(SACRED_SPIRIT, 16)
                .addSpirit(AERIAL_SPIRIT, 16)
                .addSpirit(AQUEOUS_SPIRIT, 16)
                .addSpirit(INFERNAL_SPIRIT, 16)
                .addExtraItem(ItemRegistry.TAINTED_ROCK.get(), 8)
                .addExtraItem(ItemRegistry.TWISTED_ROCK.get(), 8)
                .save(recipeOutput);

        new SpiritInfusionRecipeBuilder(ItemRegistry.ALCHEMICAL_CALX.get(), 4, ItemRegistry.STELLAR_MECHANISM.get(), 1)
                .addExtraItem(ItemRegistry.FUSED_CONSCIOUSNESS.get(), 1)
                .addExtraItem(ItemRegistry.NULL_SLATE.get(), 2)
                .addSpirit(AERIAL_SPIRIT, 16)
                .addSpirit(AQUEOUS_SPIRIT, 16)
                .addSpirit(EARTHEN_SPIRIT, 16)
                .addSpirit(INFERNAL_SPIRIT, 16)
                .addSpirit(ELDRITCH_SPIRIT, 4)
                .save(recipeOutput);

        metalImpetusRecipe(recipeOutput, ItemRegistry.IRON_IMPETUS, Items.IRON_INGOT);
        metalImpetusRecipe(recipeOutput, ItemRegistry.COPPER_IMPETUS, Items.COPPER_INGOT);
        metalImpetusRecipe(recipeOutput, ItemRegistry.GOLD_IMPETUS, Items.GOLD_INGOT);
        metalImpetusRecipe(recipeOutput, ItemRegistry.LEAD_IMPETUS, INGOTS_LEAD);
        metalImpetusRecipe(recipeOutput, ItemRegistry.SILVER_IMPETUS, INGOTS_SILVER);
        metalImpetusRecipe(recipeOutput, ItemRegistry.ALUMINUM_IMPETUS, INGOTS_ALUMINUM);
        metalImpetusRecipe(recipeOutput, ItemRegistry.NICKEL_IMPETUS, INGOTS_NICKEL);
        metalImpetusRecipe(recipeOutput, ItemRegistry.URANIUM_IMPETUS, INGOTS_URANIUM);
        metalImpetusRecipe(recipeOutput, ItemRegistry.OSMIUM_IMPETUS, INGOTS_OSMIUM);
        metalImpetusRecipe(recipeOutput, ItemRegistry.ZINC_IMPETUS, INGOTS_ZINC);
        metalImpetusRecipe(recipeOutput, ItemRegistry.TIN_IMPETUS, INGOTS_TIN);
    }

    public static void metalImpetusRecipe(RecipeOutput recipeOutput, DeferredHolder<Item, ? extends ImpetusItem> output, TagKey<Item> ingot) {
                new SpiritInfusionRecipeBuilder(ItemRegistry.ALCHEMICAL_IMPETUS.get(), output.get(), 1)
                        .addSpirit(EARTHEN_SPIRIT, 8)
                        .addSpirit(INFERNAL_SPIRIT, 8)
                        .addExtraItem(SizedIngredient.of(Tags.Items.GUNPOWDERS, 4))
                        .addExtraItem(SizedIngredient.of(ItemRegistry.CTHONIC_GOLD.get(), 1))
                        .addExtraItem(SizedIngredient.of(ingot, 6))
                .save(recipeOutput, MalumMod.malumPath("impetus_creation_" + ingot.location().getPath().replace("ingots/", "")));

//new NotCondition(new TagEmptyCondition(ingot.location().toString()))
    }

    public static void metalImpetusRecipe(RecipeOutput recipeOutput, DeferredHolder<Item, ? extends ImpetusItem> output, Item ingot) {
        new SpiritInfusionRecipeBuilder(ItemRegistry.ALCHEMICAL_IMPETUS.get(), output.get(), 1)
                .addSpirit(EARTHEN_SPIRIT, 8)
                .addSpirit(INFERNAL_SPIRIT, 8)
                .addExtraItem(SizedIngredient.of(Tags.Items.GUNPOWDERS, 4))
                .addExtraItem(SizedIngredient.of(ItemRegistry.CTHONIC_GOLD.get(), 1))
                .addExtraItem(SizedIngredient.of(ingot, 6))
                .save(recipeOutput);
    }
}
