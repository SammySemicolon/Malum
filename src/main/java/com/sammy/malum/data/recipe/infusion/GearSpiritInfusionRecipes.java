package com.sammy.malum.data.recipe.infusion;

import com.sammy.malum.data.recipe.builder.*;
import com.sammy.malum.registry.common.item.*;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.crafting.SizedIngredient;

import static com.sammy.malum.registry.common.SpiritTypeRegistry.*;

public class GearSpiritInfusionRecipes {

    public static void buildRecipes(RecipeOutput recipeOutput) {
        new SpiritInfusionRecipeBuilder(ItemRegistry.SOUL_STAINED_STEEL_SWORD.get(), 1, ItemRegistry.TYRVING.get(), 1)
                .addSpirit(WICKED_SPIRIT, 32)
                .addSpirit(ARCANE_SPIRIT, 16)
                .addSpirit(ELDRITCH_SPIRIT, 8)
                .addExtraItem(ItemRegistry.TWISTED_ROCK.get(), 16)
                .addExtraItem(ItemRegistry.SOULWOOD_PLANKS.get(), 8)
                .addExtraItem(SizedIngredient.of(Tags.Items.OBSIDIANS, 4))
                .addExtraItem(ItemRegistry.HEX_ASH.get(), 4)
                .save(recipeOutput);

        new SpiritInfusionRecipeBuilder(ItemRegistry.SOUL_STAINED_STEEL_AXE.get(), 1, ItemRegistry.WEIGHT_OF_WORLDS.get(), 1)
                .addExtraItem(ItemRegistry.MALIGNANT_PEWTER_INGOT.get(), 2)
                .addSpirit(EARTHEN_SPIRIT, 32)
                .addSpirit(WICKED_SPIRIT, 32)
                .addSpirit(ELDRITCH_SPIRIT, 16)
                .save(recipeOutput);

        new SpiritInfusionRecipeBuilder(ItemRegistry.SOUL_STAINED_STEEL_SCYTHE.get(), 1, ItemRegistry.EDGE_OF_DELIVERANCE.get(), 1)
                .addExtraItem(ItemRegistry.MALIGNANT_PEWTER_INGOT.get(), 2)
                .addSpirit(EARTHEN_SPIRIT, 32)
                .addSpirit(WICKED_SPIRIT, 32)
                .addSpirit(ELDRITCH_SPIRIT, 16)
                .save(recipeOutput);

        new SpiritInfusionRecipeBuilder(ItemRegistry.TOTEMIC_STAFF.get(), 1, ItemRegistry.MNEMONIC_HEX_STAFF.get(), 1)
                .addExtraItem(ItemRegistry.MNEMONIC_FRAGMENT.get(), 8)
                .addExtraItem(ItemRegistry.SOUL_STAINED_STEEL_INGOT.get(), 4)
                .addExtraItem(ItemRegistry.SOULWOOD_PLANKS.get(), 2)
                .addSpirit(WICKED_SPIRIT, 32)
                .addSpirit(AERIAL_SPIRIT, 16)
                .addSpirit(AQUEOUS_SPIRIT, 16)
                .addSpirit(ELDRITCH_SPIRIT, 8)
                .save(recipeOutput);

        new SpiritInfusionRecipeBuilder(ItemRegistry.MNEMONIC_HEX_STAFF.get(), 1, ItemRegistry.EROSION_SCEPTER.get(), 1)
                .addExtraItem(ItemRegistry.MALIGNANT_PEWTER_INGOT.get(), 2)
                .addExtraItem(ItemRegistry.VOID_SALTS.get(), 8)
                .addSpirit(AQUEOUS_SPIRIT, 32)
                .addSpirit(WICKED_SPIRIT, 32)
                .addSpirit(ELDRITCH_SPIRIT, 16)
                .save(recipeOutput);

        new SpiritInfusionRecipeBuilder(ItemRegistry.MNEMONIC_HEX_STAFF.get(), 1, ItemRegistry.STAFF_OF_THE_AURIC_FLAME.get(), 1)
                .addExtraItem(ItemRegistry.FUSED_CONSCIOUSNESS.get(), 1)
                .addExtraItem(ItemRegistry.AURIC_EMBERS.get(), 8)
                .addExtraItem(ItemRegistry.VOID_SALTS.get(), 8)
                .addSpirit(WICKED_SPIRIT, 32)
                .addSpirit(ARCANE_SPIRIT, 16)
                .addSpirit(INFERNAL_SPIRIT, 16)
                .addSpirit(ELDRITCH_SPIRIT, 16)
                .save(recipeOutput);

        new SpiritInfusionRecipeBuilder(ItemRegistry.CRUDE_SCYTHE.get(), 1, ItemRegistry.SOUL_STAINED_STEEL_SCYTHE.get(), 1)
                .addExtraItem(ItemRegistry.SOUL_STAINED_STEEL_INGOT.get(), 4)
                .addExtraItem(ItemRegistry.HEX_ASH.get(), 2)
                .addExtraItem(ItemRegistry.REFINED_SOULSTONE.get(), 4)
                .addSpirit(EARTHEN_SPIRIT, 16)
                .addSpirit(WICKED_SPIRIT, 8)
                .addSpirit(ARCANE_SPIRIT, 8)
                .save(recipeOutput);

        new SpiritInfusionRecipeBuilder(Items.IRON_HELMET, 1, ItemRegistry.SOUL_STAINED_STEEL_HELMET.get(), 1)
                .addExtraItem(ItemRegistry.SOUL_STAINED_STEEL_PLATING.get(), 6)
                .addExtraItem(ItemRegistry.REFINED_SOULSTONE.get(), 4)
                .addExtraItem(ItemRegistry.TWISTED_ROCK.get(), 8)
                .addSpirit(EARTHEN_SPIRIT, 16)
                .addSpirit(WICKED_SPIRIT, 8)
                .addSpirit(ARCANE_SPIRIT, 8)
                .save(recipeOutput);

        new SpiritInfusionRecipeBuilder(Items.IRON_CHESTPLATE, 1, ItemRegistry.SOUL_STAINED_STEEL_CHESTPLATE.get(), 1)
                .addExtraItem(ItemRegistry.SOUL_STAINED_STEEL_PLATING.get(), 6)
                .addExtraItem(ItemRegistry.REFINED_SOULSTONE.get(), 4)
                .addExtraItem(ItemRegistry.TWISTED_ROCK.get(), 8)
                .addSpirit(EARTHEN_SPIRIT, 16)
                .addSpirit(WICKED_SPIRIT, 8)
                .addSpirit(ARCANE_SPIRIT, 8)
                .save(recipeOutput);

        new SpiritInfusionRecipeBuilder(Items.IRON_LEGGINGS, 1, ItemRegistry.SOUL_STAINED_STEEL_LEGGINGS.get(), 1)
                .addExtraItem(ItemRegistry.SOUL_STAINED_STEEL_PLATING.get(), 6)
                .addExtraItem(ItemRegistry.REFINED_SOULSTONE.get(), 4)
                .addExtraItem(ItemRegistry.TWISTED_ROCK.get(), 8)
                .addSpirit(EARTHEN_SPIRIT, 16)
                .addSpirit(WICKED_SPIRIT, 8)
                .addSpirit(ARCANE_SPIRIT, 8)
                .save(recipeOutput);

        new SpiritInfusionRecipeBuilder(Items.IRON_BOOTS, 1, ItemRegistry.SOUL_STAINED_STEEL_BOOTS.get(), 1)
                .addExtraItem(ItemRegistry.SOUL_STAINED_STEEL_PLATING.get(), 6)
                .addExtraItem(ItemRegistry.REFINED_SOULSTONE.get(), 4)
                .addExtraItem(ItemRegistry.TWISTED_ROCK.get(), 8)
                .addSpirit(EARTHEN_SPIRIT, 16)
                .addSpirit(WICKED_SPIRIT, 8)
                .addSpirit(ARCANE_SPIRIT, 8)
                .save(recipeOutput);

        new SpiritInfusionRecipeBuilder(Items.LEATHER_HELMET, 1, ItemRegistry.SOUL_HUNTER_CLOAK.get(), 1)
                .addExtraItem(ItemRegistry.SOULWOVEN_SILK.get(), 4)
                .addExtraItem(ItemRegistry.REFINED_SOULSTONE.get(), 4)
                .addExtraItem(SizedIngredient.of(Tags.Items.LEATHERS, 2))
                .addSpirit(AERIAL_SPIRIT, 8)
                .addSpirit(EARTHEN_SPIRIT, 8)
                .save(recipeOutput);

        new SpiritInfusionRecipeBuilder(Items.LEATHER_CHESTPLATE, 1, ItemRegistry.SOUL_HUNTER_ROBE.get(), 1)
                .addExtraItem(ItemRegistry.SOULWOVEN_SILK.get(), 4)
                .addExtraItem(ItemRegistry.REFINED_SOULSTONE.get(), 4)
                .addExtraItem(SizedIngredient.of(Tags.Items.LEATHERS, 2))
                .addSpirit(AERIAL_SPIRIT, 8)
                .addSpirit(EARTHEN_SPIRIT, 8)
                .save(recipeOutput);

        new SpiritInfusionRecipeBuilder(Items.LEATHER_LEGGINGS, 1, ItemRegistry.SOUL_HUNTER_LEGGINGS.get(), 1)
                .addExtraItem(ItemRegistry.SOULWOVEN_SILK.get(), 4)
                .addExtraItem(ItemRegistry.REFINED_SOULSTONE.get(), 4)
                .addExtraItem(SizedIngredient.of(Tags.Items.LEATHERS, 2))
                .addSpirit(AERIAL_SPIRIT, 8)
                .addSpirit(EARTHEN_SPIRIT, 8)
                .save(recipeOutput);

        new SpiritInfusionRecipeBuilder(Items.LEATHER_BOOTS, 1, ItemRegistry.SOUL_HUNTER_BOOTS.get(), 1)
                .addExtraItem(ItemRegistry.SOULWOVEN_SILK.get(), 4)
                .addExtraItem(ItemRegistry.REFINED_SOULSTONE.get(), 4)
                .addExtraItem(SizedIngredient.of(Tags.Items.LEATHERS, 2))
                .addSpirit(AERIAL_SPIRIT, 8)
                .addSpirit(EARTHEN_SPIRIT, 8)
                .save(recipeOutput);

        new SpiritInfusionRecipeBuilder(ItemRegistry.SOUL_STAINED_STEEL_HELMET.get(), 1, ItemRegistry.MALIGNANT_STRONGHOLD_HELMET.get(), 1)
                .addExtraItem(ItemRegistry.MALIGNANT_PEWTER_PLATING.get(), 3)
                .addSpirit(EARTHEN_SPIRIT, 32)
                .addSpirit(WICKED_SPIRIT, 32)
                .addSpirit(ELDRITCH_SPIRIT, 16)
                .save(recipeOutput);

        new SpiritInfusionRecipeBuilder(ItemRegistry.SOUL_STAINED_STEEL_CHESTPLATE.get(), 1, ItemRegistry.MALIGNANT_STRONGHOLD_CHESTPLATE.get(), 1)
                .addExtraItem(ItemRegistry.MALIGNANT_PEWTER_PLATING.get(), 3)
                .addSpirit(EARTHEN_SPIRIT, 32)
                .addSpirit(WICKED_SPIRIT, 32)
                .addSpirit(ELDRITCH_SPIRIT, 16)
                .save(recipeOutput);

        new SpiritInfusionRecipeBuilder(ItemRegistry.SOUL_STAINED_STEEL_LEGGINGS.get(), 1, ItemRegistry.MALIGNANT_STRONGHOLD_LEGGINGS.get(), 1)
                .addExtraItem(ItemRegistry.MALIGNANT_PEWTER_PLATING.get(), 3)
                .addSpirit(EARTHEN_SPIRIT, 32)
                .addSpirit(WICKED_SPIRIT, 32)
                .addSpirit(ELDRITCH_SPIRIT, 16)
                .save(recipeOutput);

        new SpiritInfusionRecipeBuilder(ItemRegistry.SOUL_STAINED_STEEL_BOOTS.get(), 1, ItemRegistry.MALIGNANT_STRONGHOLD_BOOTS.get(), 1)
                .addExtraItem(ItemRegistry.MALIGNANT_PEWTER_PLATING.get(), 3)
                .addSpirit(EARTHEN_SPIRIT, 32)
                .addSpirit(WICKED_SPIRIT, 32)
                .addSpirit(ELDRITCH_SPIRIT, 16)
                .save(recipeOutput);
    }
}
