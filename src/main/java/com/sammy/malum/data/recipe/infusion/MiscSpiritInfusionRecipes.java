package com.sammy.malum.data.recipe.infusion;

import com.sammy.malum.data.recipe.builder.*;
import com.sammy.malum.registry.common.item.*;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.crafting.SizedIngredient;

import static com.sammy.malum.registry.common.SpiritTypeRegistry.*;

public class MiscSpiritInfusionRecipes {

    public static void buildRecipes(RecipeOutput consumer) {
        new SpiritInfusionRecipeBuilder(ItemRegistry.RUNEWOOD_PLANKS.get(), 2, ItemRegistry.RUNEWOOD_OBELISK.get(), 1)
                .addExtraItem(ItemRegistry.HALLOWED_GOLD_INGOT.get(), 2)
                .addExtraItem(ItemRegistry.HEX_ASH.get(), 1)
                .addSpirit(AERIAL_SPIRIT, 16)
                .addSpirit(SACRED_SPIRIT, 8)
                .save(consumer);

        new SpiritInfusionRecipeBuilder(ItemRegistry.RUNEWOOD_PLANKS.get(), 2, ItemRegistry.BRILLIANT_OBELISK.get(), 1)
                .addExtraItem(ItemRegistry.CLUSTER_OF_BRILLIANCE.get(), 2)
                .addExtraItem(ItemRegistry.HEX_ASH.get(), 1)
                .addSpirit(AERIAL_SPIRIT, 16)
                .addSpirit(AQUEOUS_SPIRIT, 8)
                .save(consumer);

        new SpiritInfusionRecipeBuilder(ItemRegistry.RUNEWOOD_ITEM_PEDESTAL.get(), 1, ItemRegistry.RUNIC_WORKBENCH.get(), 1)
                .addExtraItem(ItemRegistry.RUNEWOOD_PLANKS.get(), 4)
                .addExtraItem(ItemRegistry.HALLOWED_GOLD_INGOT.get(), 2)
                .addSpirit(AQUEOUS_SPIRIT, 16)
                .addSpirit(SACRED_SPIRIT, 8)
                .save(consumer);

        new SpiritInfusionRecipeBuilder(Items.HONEY_BOTTLE, 1, ItemRegistry.CONCENTRATED_GLUTTONY.get(), 2)
                .addExtraItem(ItemRegistry.ROTTING_ESSENCE.get(), 1)
                .addSpirit(AQUEOUS_SPIRIT, 2)
                .addSpirit(SACRED_SPIRIT, 2)
                .addSpirit(WICKED_SPIRIT, 2)
                .save(consumer);

        new SpiritInfusionRecipeBuilder(Items.HONEY_BOTTLE, 1, ItemRegistry.SPLASH_OF_GLUTTONY.get(), 2)
                .addExtraItem(ItemRegistry.ROTTING_ESSENCE.get(), 1)
                .addExtraItem(SizedIngredient.of(Tags.Items.GUNPOWDERS, 1))
                .addSpirit(AQUEOUS_SPIRIT, 3)
                .addSpirit(SACRED_SPIRIT, 2)
                .addSpirit(WICKED_SPIRIT, 2)
                .save(consumer);

        new SpiritInfusionRecipeBuilder(ItemRegistry.CONCENTRATED_GLUTTONY.get(), 1, ItemRegistry.SPLASH_OF_GLUTTONY.get(), 1)
                .addExtraItem(SizedIngredient.of(Tags.Items.GUNPOWDERS, 1))
                .addSpirit(AQUEOUS_SPIRIT, 1)
                .save(consumer, "splash_of_gluttony_from_concentrated_gluttony");

        new SpiritInfusionRecipeBuilder(Ingredient.of(Tags.Items.INGOTS_IRON), 2, ItemRegistry.LAMPLIGHTERS_TONGS.get(), 1)
                .addSpirit(ARCANE_SPIRIT, 8)
                .addSpirit(INFERNAL_SPIRIT, 8)
                .addExtraItem(ItemRegistry.RUNEWOOD_PLANKS.get(), 2)
                .addExtraItem(ItemRegistry.REFINED_SOULSTONE.get(), 1)
                .save(consumer);

        new SpiritInfusionRecipeBuilder(ItemRegistry.LAMPLIGHTERS_TONGS.get(), 1, ItemRegistry.CATALYST_LOBBER.get(), 1)
                .addSpirit(AQUEOUS_SPIRIT, 32)
                .addSpirit(INFERNAL_SPIRIT, 32)
                .addSpirit(ELDRITCH_SPIRIT, 8)
                .addExtraItem(SizedIngredient.of(Tags.Items.INGOTS_IRON, 4))
                .addExtraItem(ItemRegistry.SOULWOOD_PLANKS.get(), 2)
                .addExtraItem(ItemRegistry.MALIGNANT_LEAD.get(), 1)
                .save(consumer);

        new SpiritInfusionRecipeBuilder(Ingredient.of(ItemTagRegistry.ARCANE_ELEGY_COMPONENTS), 1, ItemRegistry.ARCANE_ELEGY.get(), 1)
                .addSpirit(AERIAL_SPIRIT, 4)
                .addSpirit(AQUEOUS_SPIRIT, 4)
                .addSpirit(EARTHEN_SPIRIT, 4)
                .addSpirit(INFERNAL_SPIRIT, 4)
                .save(consumer);
    }
}
