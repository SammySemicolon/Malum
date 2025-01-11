package com.sammy.malum.data.recipe.infusion;

import com.sammy.malum.*;
import com.sammy.malum.common.item.impetus.*;
import com.sammy.malum.data.recipe.builder.*;
import com.sammy.malum.registry.common.item.*;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;
import net.minecraftforge.common.*;
import net.minecraftforge.common.crafting.*;
import net.minecraftforge.common.crafting.conditions.*;
import net.minecraftforge.registries.*;

import java.util.function.*;

import static com.sammy.malum.registry.common.SpiritTypeRegistry.*;
import static team.lodestar.lodestone.registry.common.tag.LodestoneItemTags.*;

public class ArtificeSpiritInfusionRecipes {

    public static void buildRecipes(Consumer<FinishedRecipe> consumer) {
        new SpiritInfusionRecipeBuilder(Items.FURNACE, 1, ItemRegistry.SPIRIT_CRUCIBLE.get(), 1)
                .addSpirit(INFERNAL_SPIRIT, 12)
                .addSpirit(ARCANE_SPIRIT, 4)
                .addExtraItem(ItemRegistry.HEX_ASH.get(), 2)
                .addExtraItem(ItemRegistry.TAINTED_ROCK.get(), 8)
                .addExtraItem(ItemRegistry.TWISTED_ROCK.get(), 8)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(ItemRegistry.ALCHEMICAL_CALX.get(), 8, ItemRegistry.ALCHEMICAL_IMPETUS.get(), 1)
                .addSpirit(ARCANE_SPIRIT, 4)
                .addSpirit(EARTHEN_SPIRIT, 4)
                .addExtraItem(ItemRegistry.PROCESSED_SOULSTONE.get(), 3)
                .addExtraItem(ItemRegistry.HEX_ASH.get(), 1)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(Ingredient.of(Tags.Items.INGOTS_IRON), 2, ItemRegistry.TUNING_FORK.get(), 1)
                .addSpirit(ARCANE_SPIRIT, 8)
                .addSpirit(AQUEOUS_SPIRIT, 8)
                .addExtraItem(ItemRegistry.RUNEWOOD_PLANKS.get(), 2)
                .addExtraItem(ItemRegistry.PROCESSED_SOULSTONE.get(), 1)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(ItemRegistry.ALCHEMICAL_CALX.get(), 4, ItemRegistry.MENDING_DIFFUSER.get(), 1)
                .addSpirit(SACRED_SPIRIT, 8)
                .addSpirit(ARCANE_SPIRIT, 4)
                .addExtraItem(ItemRegistry.LIVING_FLESH.get(), 2)
                .addExtraItem(ItemRegistry.PROCESSED_SOULSTONE.get(), 2)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(ItemRegistry.ALCHEMICAL_CALX.get(), 4, ItemRegistry.IMPURITY_STABILIZER.get(), 1)
                .addSpirit(WICKED_SPIRIT, 8)
                .addSpirit(ARCANE_SPIRIT, 4)
                .addExtraItem(ItemRegistry.HEX_ASH.get(), 2)
                .addExtraItem(ItemRegistry.PROCESSED_SOULSTONE.get(), 2)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(ItemRegistry.ALCHEMICAL_CALX.get(), 4, ItemRegistry.SHIELDING_APPARATUS.get(), 1)
                .addSpirit(ARCANE_SPIRIT, 8)
                .addExtraItem(ItemRegistry.SOUL_STAINED_STEEL_PLATING.get(), 2)
                .addExtraItem(ItemRegistry.PROCESSED_SOULSTONE.get(), 2)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(ItemRegistry.ALCHEMICAL_CALX.get(), 4, ItemRegistry.WARPING_ENGINE.get(), 1)
                .addSpirit(ELDRITCH_SPIRIT, 2)
                .addSpirit(ARCANE_SPIRIT, 4)
                .addExtraItem(ItemRegistry.WARP_FLUX.get(), 2)
                .addExtraItem(ItemRegistry.PROCESSED_SOULSTONE.get(), 2)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(ItemRegistry.ALCHEMICAL_CALX.get(), 4, ItemRegistry.ACCELERATING_INLAY.get(), 1)
                .addSpirit(AERIAL_SPIRIT, 8)
                .addSpirit(ARCANE_SPIRIT, 4)
                .addExtraItem(ItemRegistry.ASTRAL_WEAVE.get(), 2)
                .addExtraItem(ItemRegistry.PROCESSED_SOULSTONE.get(), 2)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(ItemRegistry.ALCHEMICAL_CALX.get(), 4, ItemRegistry.PRISMATIC_FOCUS_LENS.get(), 1)
                .addSpirit(AQUEOUS_SPIRIT, 8)
                .addSpirit(ARCANE_SPIRIT, 4)
                .addExtraItem(Ingredient.of(Tags.Items.GEMS_PRISMARINE), 2)
                .addExtraItem(ItemRegistry.PROCESSED_SOULSTONE.get(), 2)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(ItemRegistry.ALCHEMICAL_CALX.get(), 4, ItemRegistry.BLAZING_DIODE.get(), 1)
                .addSpirit(INFERNAL_SPIRIT, 8)
                .addSpirit(ARCANE_SPIRIT, 4)
                .addExtraItem(ItemRegistry.BLAZING_QUARTZ.get(), 2)
                .addExtraItem(ItemRegistry.PROCESSED_SOULSTONE.get(), 2)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(ItemRegistry.ALCHEMICAL_CALX.get(), 4, ItemRegistry.INTRICATE_ASSEMBLY.get(), 1)
                .addSpirit(EARTHEN_SPIRIT, 8)
                .addSpirit(ARCANE_SPIRIT, 4)
                .addExtraItem(Ingredient.of(Tags.Items.GEMS_EMERALD), 2)
                .addExtraItem(ItemRegistry.PROCESSED_SOULSTONE.get(), 2)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(ItemRegistry.TWISTED_ROCK_ITEM_PEDESTAL.get(), 1, ItemRegistry.SPIRIT_CATALYZER.get(), 1)
                .addSpirit(INFERNAL_SPIRIT, 8)
                .addSpirit(AERIAL_SPIRIT, 8)
                .addExtraItem(ItemRegistry.TAINTED_ROCK.get(), 4)
                .addExtraItem(ItemRegistry.ETHER.get(), 1)
                .addExtraItem(ItemRegistry.TWISTED_ROCK.get(), 4)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(ItemRegistry.TAINTED_ROCK_ITEM_PEDESTAL.get(), 1, ItemRegistry.REPAIR_PYLON.get(), 1)
                .addSpirit(SACRED_SPIRIT, 16)
                .addSpirit(AERIAL_SPIRIT, 16)
                .addSpirit(AQUEOUS_SPIRIT, 16)
                .addSpirit(INFERNAL_SPIRIT, 16)
                .addExtraItem(ItemRegistry.TAINTED_ROCK.get(), 8)
                .addExtraItem(ItemRegistry.TWISTED_ROCK.get(), 8)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(ItemRegistry.ALCHEMICAL_CALX.get(), 4, ItemRegistry.STELLAR_MECHANISM.get(), 1)
                .addExtraItem(ItemRegistry.FUSED_CONSCIOUSNESS.get(), 1)
                .addExtraItem(ItemRegistry.NULL_SLATE.get(), 2)
                .addSpirit(AERIAL_SPIRIT, 16)
                .addSpirit(AQUEOUS_SPIRIT, 16)
                .addSpirit(EARTHEN_SPIRIT, 16)
                .addSpirit(INFERNAL_SPIRIT, 16)
                .addSpirit(ELDRITCH_SPIRIT, 4)
                .build(consumer);

        metalImpetusRecipe(consumer, ItemRegistry.IRON_IMPETUS, Items.IRON_INGOT);
        metalImpetusRecipe(consumer, ItemRegistry.COPPER_IMPETUS, Items.COPPER_INGOT);
        metalImpetusRecipe(consumer, ItemRegistry.GOLD_IMPETUS, Items.GOLD_INGOT);
        metalImpetusRecipe(consumer, ItemRegistry.LEAD_IMPETUS, INGOTS_LEAD);
        metalImpetusRecipe(consumer, ItemRegistry.SILVER_IMPETUS, INGOTS_SILVER);
        metalImpetusRecipe(consumer, ItemRegistry.ALUMINUM_IMPETUS, INGOTS_ALUMINUM);
        metalImpetusRecipe(consumer, ItemRegistry.NICKEL_IMPETUS, INGOTS_NICKEL);
        metalImpetusRecipe(consumer, ItemRegistry.URANIUM_IMPETUS, INGOTS_URANIUM);
        metalImpetusRecipe(consumer, ItemRegistry.OSMIUM_IMPETUS, INGOTS_OSMIUM);
        metalImpetusRecipe(consumer, ItemRegistry.ZINC_IMPETUS, INGOTS_ZINC);
        metalImpetusRecipe(consumer, ItemRegistry.TIN_IMPETUS, INGOTS_TIN);
        //TODO: move tag to lodestone side.
        metalImpetusRecipe(consumer, ItemRegistry.COBALT_IMPETUS, ItemTags.create(new ResourceLocation("forge", "ingots/cobalt")));
    }

    public static void metalImpetusRecipe(Consumer<FinishedRecipe> consumer, RegistryObject<ImpetusItem> output, TagKey<Item> ingot) {
        ConditionalRecipe.builder().addCondition(new NotCondition(new TagEmptyCondition(ingot.location().toString()))).addRecipe(
                        new SpiritInfusionRecipeBuilder(ItemRegistry.ALCHEMICAL_IMPETUS.get(), 1, output.get(), 1)
                                .addSpirit(EARTHEN_SPIRIT, 8)
                                .addSpirit(INFERNAL_SPIRIT, 8)
                                .addExtraItem(Ingredient.of(Tags.Items.GUNPOWDER), 4)
                                .addExtraItem(Ingredient.of(ItemRegistry.CTHONIC_GOLD.get()), 1)
                                .addExtraItem(Ingredient.of(ingot), 6)
                                ::build)
                .generateAdvancement()
                .build(consumer, MalumMod.malumPath("impetus_creation_" + ingot.location().getPath().replace("ingots/", "")));
    }

    public static void metalImpetusRecipe(Consumer<FinishedRecipe> consumer, RegistryObject<ImpetusItem> output, Item ingot) {
        new SpiritInfusionRecipeBuilder(ItemRegistry.ALCHEMICAL_IMPETUS.get(), 1, output.get(), 1)
                .addSpirit(EARTHEN_SPIRIT, 8)
                .addSpirit(INFERNAL_SPIRIT, 8)
                .addExtraItem(Ingredient.of(Tags.Items.GUNPOWDER), 4)
                .addExtraItem(Ingredient.of(ItemRegistry.CTHONIC_GOLD.get()), 1)
                .addExtraItem(Ingredient.of(ingot), 6)
                .build(consumer);
    }
}
