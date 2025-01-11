package com.sammy.malum.data.recipe;

import com.sammy.malum.*;
import com.sammy.malum.common.item.impetus.*;
import com.sammy.malum.data.recipe.builder.vanilla.*;
import com.sammy.malum.registry.common.item.*;
import net.minecraft.advancements.critereon.*;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraftforge.common.*;
import net.minecraftforge.common.crafting.*;
import net.minecraftforge.common.crafting.conditions.*;
import net.minecraftforge.registries.*;
import team.lodestar.lodestone.data.builder.*;
import team.lodestar.lodestone.systems.recipe.*;

import java.util.function.*;

import static com.sammy.malum.MalumMod.*;
import static com.sammy.malum.data.recipe.builder.vanilla.MetalNodeCookingRecipeBuilder.*;
import static com.sammy.malum.data.recipe.builder.vanilla.StackedMalumCookingRecipeBuilder.*;
import static net.minecraft.data.recipes.ShapedRecipeBuilder.*;
import static net.minecraft.data.recipes.ShapelessRecipeBuilder.*;
import static net.minecraft.data.recipes.SimpleCookingRecipeBuilder.*;
import static team.lodestar.lodestone.registry.common.tag.LodestoneItemTags.*;

public class MalumVanillaRecipes implements IConditionBuilder {

    protected static void buildRecipes(Consumer<FinishedRecipe> consumer) {
        //KEY ITEMS
        shapeless(RecipeCategory.MISC, ItemRegistry.ENCYCLOPEDIA_ARCANA.get()).requires(Items.BOOK).requires(ItemRegistry.PROCESSED_SOULSTONE.get()).unlockedBy("has_soulstone", has(ItemRegistry.PROCESSED_SOULSTONE.get())).save(consumer);
        shaped(RecipeCategory.MISC, ItemRegistry.CRUDE_SCYTHE.get()).define('#', Tags.Items.RODS_WOODEN).define('Y', ItemRegistry.PROCESSED_SOULSTONE.get()).define('X', Tags.Items.INGOTS_IRON).pattern("XXY").pattern(" #X").pattern("#  ").unlockedBy("has_soulstone", has(ItemRegistry.RAW_SOULSTONE.get())).save(consumer);
        shaped(RecipeCategory.MISC, ItemRegistry.SPIRIT_ALTAR.get()).define('Z', Tags.Items.INGOTS_GOLD).define('Y', ItemRegistry.PROCESSED_SOULSTONE.get()).define('X', ItemRegistry.RUNEWOOD_PLANKS.get()).pattern(" Y ").pattern("ZXZ").pattern("XXX").unlockedBy("has_soulstone", has(ItemRegistry.RAW_SOULSTONE.get())).save(consumer);
        shaped(RecipeCategory.MISC, ItemRegistry.SPIRIT_JAR.get()).define('Z', ItemRegistry.HALLOWED_GOLD_INGOT.get()).define('Y', Tags.Items.GLASS_PANES).pattern("YZY").pattern("Y Y").pattern("YYY").unlockedBy("has_hallowed_gold", has(ItemRegistry.HALLOWED_GOLD_INGOT.get())).save(consumer);
        shaped(RecipeCategory.MISC, ItemRegistry.SPIRIT_POUCH.get()).define('X', Tags.Items.STRING).define('Y', ItemRegistry.SPIRIT_FABRIC.get()).define('Z', ItemTags.SOUL_FIRE_BASE_BLOCKS).pattern(" X ").pattern("YZY").pattern(" Y ").unlockedBy("has_spirit_fabric", has(ItemRegistry.SPIRIT_FABRIC.get())).save(consumer);
        shaped(RecipeCategory.MISC, ItemRegistry.WEAVERS_WORKBENCH.get()).define('Z', ItemRegistry.HALLOWED_GOLD_INGOT.get()).define('Y', ItemRegistry.HEX_ASH.get()).define('X', ItemRegistry.RUNEWOOD_PLANKS.get()).pattern("XYX").pattern("XZX").unlockedBy("has_hex_ash", has(ItemRegistry.HEX_ASH.get())).save(consumer);
        shaped(RecipeCategory.MISC, ItemRegistry.TOTEMIC_STAFF.get()).define('X', Tags.Items.RODS_WOODEN).define('Y', ItemTagRegistry.RUNEWOOD_PLANKS).pattern("  Y").pattern(" X ").pattern("X  ").unlockedBy("has_runewood", has(ItemRegistry.RUNEWOOD_PLANKS.get())).save(consumer);

        //CRAFTING COMPONENTS
        shaped(RecipeCategory.MISC, ItemRegistry.SPECTRAL_LENS.get()).define('X', ItemRegistry.HEX_ASH.get()).define('Y', Tags.Items.GLASS_PANES).pattern(" X ").pattern("XYX").pattern(" X ").unlockedBy("has_hex_ash", has(ItemRegistry.HEX_ASH.get())).save(consumer);
        shaped(RecipeCategory.MISC, ItemRegistry.SPECTRAL_OPTIC.get(), 2).define('#', ItemRegistry.HALLOWED_GOLD_INGOT.get()).define('X', ItemRegistry.RUNEWOOD_PLANKS.get()).define('Y', ItemRegistry.SPECTRAL_LENS.get()).pattern(" X ").pattern("#Y#").pattern(" X ").unlockedBy("has_hallowed_gold", has(ItemRegistry.HALLOWED_GOLD_INGOT.get())).save(consumer);

        //SPIRIT METALS
        shaped(RecipeCategory.MISC, ItemRegistry.BLOCK_OF_SOUL_STAINED_STEEL.get()).define('#', ItemRegistry.SOUL_STAINED_STEEL_INGOT.get()).pattern("###").pattern("###").pattern("###").unlockedBy("has_soul_stained_steel", has(ItemRegistry.SOUL_STAINED_STEEL_INGOT.get())).save(consumer);
        shaped(RecipeCategory.MISC, ItemRegistry.SOUL_STAINED_STEEL_INGOT.get()).define('#', ItemRegistry.SOUL_STAINED_STEEL_NUGGET.get()).pattern("###").pattern("###").pattern("###").unlockedBy("has_soul_stained_steel", has(ItemRegistry.SOUL_STAINED_STEEL_INGOT.get())).save(consumer, malumPath("soul_stained_steel_from_nuggets"));
        shapeless(RecipeCategory.MISC, ItemRegistry.SOUL_STAINED_STEEL_NUGGET.get(), 9).requires(ItemRegistry.SOUL_STAINED_STEEL_INGOT.get()).unlockedBy("has_soul_stained_steel", has(ItemRegistry.SOUL_STAINED_STEEL_INGOT.get())).save(consumer);
        shapeless(RecipeCategory.MISC, ItemRegistry.SOUL_STAINED_STEEL_INGOT.get(), 9).requires(ItemRegistry.BLOCK_OF_SOUL_STAINED_STEEL.get()).unlockedBy("has_soul_stained_steel", has(ItemRegistry.SOUL_STAINED_STEEL_INGOT.get())).save(consumer, malumPath("soul_stained_steel_from_block"));
        shaped(RecipeCategory.MISC, ItemRegistry.SOUL_STAINED_STEEL_PLATING.get(), 2).define('X', ItemRegistry.SOUL_STAINED_STEEL_INGOT.get()).define('Y', ItemRegistry.SOUL_STAINED_STEEL_NUGGET.get()).pattern(" Y ").pattern("YXY").pattern(" Y ").unlockedBy("has_soul_stained_steel", has(ItemRegistry.SOUL_STAINED_STEEL_INGOT.get())).save(consumer);

        shaped(RecipeCategory.MISC, ItemRegistry.BLOCK_OF_HALLOWED_GOLD.get()).define('#', ItemRegistry.HALLOWED_GOLD_INGOT.get()).pattern("###").pattern("###").pattern("###").unlockedBy("has_hallowed_gold", has(ItemRegistry.HALLOWED_GOLD_INGOT.get())).save(consumer);
        shaped(RecipeCategory.MISC, ItemRegistry.HALLOWED_GOLD_INGOT.get()).define('#', ItemRegistry.HALLOWED_GOLD_NUGGET.get()).pattern("###").pattern("###").pattern("###").unlockedBy("has_hallowed_gold", has(ItemRegistry.HALLOWED_GOLD_INGOT.get())).save(consumer, malumPath("hallowed_gold_from_nuggets"));
        shapeless(RecipeCategory.MISC, ItemRegistry.HALLOWED_GOLD_NUGGET.get(), 9).requires(ItemRegistry.HALLOWED_GOLD_INGOT.get()).unlockedBy("has_hallowed_gold", has(ItemRegistry.HALLOWED_GOLD_INGOT.get())).save(consumer);
        shapeless(RecipeCategory.MISC, ItemRegistry.HALLOWED_GOLD_INGOT.get(), 9).requires(ItemRegistry.BLOCK_OF_HALLOWED_GOLD.get()).unlockedBy("has_hallowed_gold", has(ItemRegistry.HALLOWED_GOLD_INGOT.get())).save(consumer, malumPath("hallowed_gold_from_block"));


        shaped(RecipeCategory.MISC, ItemRegistry.BLOCK_OF_MALIGNANT_PEWTER.get()).define('#', ItemRegistry.MALIGNANT_PEWTER_INGOT.get()).pattern("###").pattern("###").pattern("###").unlockedBy("has_malignant_alloy", has(ItemRegistry.MALIGNANT_PEWTER_INGOT.get())).save(consumer);
        shaped(RecipeCategory.MISC, ItemRegistry.MALIGNANT_PEWTER_INGOT.get()).define('#', ItemRegistry.MALIGNANT_PEWTER_NUGGET.get()).pattern("###").pattern("###").pattern("###").unlockedBy("has_malignant_alloy", has(ItemRegistry.MALIGNANT_PEWTER_INGOT.get())).save(consumer, malumPath("malignant_alloy_from_nuggets"));
        shapeless(RecipeCategory.MISC, ItemRegistry.MALIGNANT_PEWTER_NUGGET.get(), 9).requires(ItemRegistry.MALIGNANT_PEWTER_INGOT.get()).unlockedBy("has_malignant_alloy", has(ItemRegistry.MALIGNANT_PEWTER_INGOT.get())).save(consumer);
        shapeless(RecipeCategory.MISC, ItemRegistry.MALIGNANT_PEWTER_INGOT.get(), 9).requires(ItemRegistry.BLOCK_OF_MALIGNANT_PEWTER.get()).unlockedBy("has_malignant_alloy", has(ItemRegistry.MALIGNANT_PEWTER_INGOT.get())).save(consumer, malumPath("malignant_alloy_from_block"));
        shaped(RecipeCategory.MISC, ItemRegistry.MALIGNANT_PEWTER_PLATING.get(), 2).define('X', ItemRegistry.MALIGNANT_PEWTER_INGOT.get()).define('Y', ItemRegistry.MALIGNANT_PEWTER_NUGGET.get()).pattern(" Y ").pattern("YXY").pattern(" Y ").unlockedBy("has_malignant_alloy", has(ItemRegistry.MALIGNANT_PEWTER_INGOT.get())).save(consumer);


        //NODES
        smeltingWithCount(Ingredient.of(ItemRegistry.IRON_NODE.get()), RecipeCategory.MISC, Items.IRON_NUGGET, 6, 0.25f, 200).unlockedBy("has_impetus", has(ItemRegistry.IRON_IMPETUS.get())).save(consumer, malumPath("iron_from_node_smelting"));
        blastingWithCount(Ingredient.of(ItemRegistry.IRON_NODE.get()), RecipeCategory.MISC, Items.IRON_NUGGET, 6, 0.25f, 100).unlockedBy("has_impetus", has(ItemRegistry.IRON_IMPETUS.get())).save(consumer, malumPath("iron_from_node_blasting"));

        smeltingWithCount(Ingredient.of(ItemRegistry.GOLD_NODE.get()), RecipeCategory.MISC, Items.GOLD_NUGGET, 6, 0.25f, 200).unlockedBy("has_impetus", has(ItemRegistry.GOLD_IMPETUS.get())).save(consumer, malumPath("gold_from_node_smelting"));
        blastingWithCount(Ingredient.of(ItemRegistry.GOLD_NODE.get()), RecipeCategory.MISC, Items.GOLD_NUGGET, 6, 0.25f, 100).unlockedBy("has_impetus", has(ItemRegistry.GOLD_IMPETUS.get())).save(consumer, malumPath("gold_from_node_blasting"));

        smeltingWithCount(Ingredient.of(ItemRegistry.COPPER_NODE.get()), RecipeCategory.MISC, ItemRegistry.COPPER_NUGGET.get(), 6, 0.25f, 200).unlockedBy("has_impetus", has(ItemRegistry.COPPER_IMPETUS.get())).save(consumer, malumPath("copper_from_node_smelting"));
        blastingWithCount(Ingredient.of(ItemRegistry.COPPER_NODE.get()), RecipeCategory.MISC, ItemRegistry.COPPER_NUGGET.get(), 6, 0.25f, 100).unlockedBy("has_impetus", has(ItemRegistry.COPPER_IMPETUS.get())).save(consumer, malumPath("copper_from_node_blasting"));

        nodeSmelting(consumer, ItemRegistry.LEAD_IMPETUS, ItemRegistry.LEAD_NODE, NUGGETS_LEAD);
        nodeSmelting(consumer, ItemRegistry.SILVER_IMPETUS, ItemRegistry.SILVER_NODE, NUGGETS_SILVER);
        nodeSmelting(consumer, ItemRegistry.ALUMINUM_IMPETUS, ItemRegistry.ALUMINUM_NODE, NUGGETS_ALUMINUM);
        nodeSmelting(consumer, ItemRegistry.NICKEL_IMPETUS, ItemRegistry.NICKEL_NODE, NUGGETS_NICKEL);
        nodeSmelting(consumer, ItemRegistry.URANIUM_IMPETUS, ItemRegistry.URANIUM_NODE, NUGGETS_URANIUM);
        nodeSmelting(consumer, ItemRegistry.OSMIUM_IMPETUS, ItemRegistry.OSMIUM_NODE, NUGGETS_OSMIUM);
        nodeSmelting(consumer, ItemRegistry.ZINC_IMPETUS, ItemRegistry.ZINC_NODE, NUGGETS_ZINC);
        nodeSmelting(consumer, ItemRegistry.TIN_IMPETUS, ItemRegistry.TIN_NODE, NUGGETS_TIN);
        //TODO: Move this V to Lodestone's side. (LodestoneItemTags.class)
        nodeSmelting(consumer, ItemRegistry.COBALT_IMPETUS, ItemRegistry.COBALT_NODE, ItemTags.create(new ResourceLocation("forge", "nuggets/cobalt")));
        //TOOLS
        shaped(RecipeCategory.MISC, ItemRegistry.SOUL_STAINED_STEEL_HOE.get()).define('#', Tags.Items.RODS_WOODEN).define('X', ItemRegistry.SOUL_STAINED_STEEL_INGOT.get()).pattern("XX").pattern(" #").pattern(" #").unlockedBy("has_soul_stained_steel", has(ItemRegistry.SOUL_STAINED_STEEL_INGOT.get())).save(consumer);
        shaped(RecipeCategory.MISC, ItemRegistry.SOUL_STAINED_STEEL_PICKAXE.get()).define('#', Tags.Items.RODS_WOODEN).define('X', ItemRegistry.SOUL_STAINED_STEEL_INGOT.get()).pattern("XXX").pattern(" # ").pattern(" # ").unlockedBy("has_soul_stained_steel", has(ItemRegistry.SOUL_STAINED_STEEL_INGOT.get())).save(consumer);
        shaped(RecipeCategory.MISC, ItemRegistry.SOUL_STAINED_STEEL_AXE.get()).define('#', Tags.Items.RODS_WOODEN).define('X', ItemRegistry.SOUL_STAINED_STEEL_INGOT.get()).pattern("XX ").pattern("X# ").pattern(" # ").unlockedBy("has_soul_stained_steel", has(ItemRegistry.SOUL_STAINED_STEEL_INGOT.get())).save(consumer);
        shaped(RecipeCategory.MISC, ItemRegistry.SOUL_STAINED_STEEL_SHOVEL.get()).define('#', Tags.Items.RODS_WOODEN).define('X', ItemRegistry.SOUL_STAINED_STEEL_INGOT.get()).pattern("X").pattern("#").pattern("#").unlockedBy("has_soul_stained_steel", has(ItemRegistry.SOUL_STAINED_STEEL_INGOT.get())).save(consumer);
        shaped(RecipeCategory.MISC, ItemRegistry.SOUL_STAINED_STEEL_SWORD.get()).define('#', Tags.Items.RODS_WOODEN).define('X', ItemRegistry.SOUL_STAINED_STEEL_INGOT.get()).pattern("X").pattern("X").pattern("#").unlockedBy("has_soul_stained_steel", has(ItemRegistry.SOUL_STAINED_STEEL_INGOT.get())).save(consumer);

        //TRINKETS
        shaped(RecipeCategory.MISC, ItemRegistry.GILDED_BELT.get()).define('#', ItemRegistry.HALLOWED_GOLD_INGOT.get()).define('X', Tags.Items.LEATHER).define('Y', ItemRegistry.PROCESSED_SOULSTONE.get()).pattern("XXX").pattern("#Y#").pattern(" # ").unlockedBy("has_hallowed_gold", has(ItemRegistry.HALLOWED_GOLD_INGOT.get())).save(consumer);
        shaped(RecipeCategory.MISC, ItemRegistry.GILDED_RING.get()).define('#', ItemRegistry.HALLOWED_GOLD_INGOT.get()).define('X', Tags.Items.LEATHER).pattern("#X ").pattern("X X").pattern(" X ").unlockedBy("has_hallowed_gold", has(ItemRegistry.HALLOWED_GOLD_INGOT.get())).save(consumer);
        shaped(RecipeCategory.MISC, ItemRegistry.ORNATE_NECKLACE.get()).define('#', ItemRegistry.SOUL_STAINED_STEEL_INGOT.get()).define('X', Tags.Items.STRING).pattern(" X ").pattern("X X").pattern(" # ").unlockedBy("has_soul_stained_steel", has(ItemRegistry.SOUL_STAINED_STEEL_INGOT.get())).save(consumer);
        shaped(RecipeCategory.MISC, ItemRegistry.ORNATE_RING.get()).define('#', ItemRegistry.SOUL_STAINED_STEEL_INGOT.get()).define('X', Tags.Items.LEATHER).pattern("#X ").pattern("X X").pattern(" X ").unlockedBy("has_soul_stained_steel", has(ItemRegistry.SOUL_STAINED_STEEL_INGOT.get())).save(consumer);

        shaped(RecipeCategory.MISC, ItemRegistry.RUNIC_BROOCH.get()).define('X', ItemRegistry.HALLOWED_GOLD_INGOT.get()).define('Y', ItemRegistry.BLOCK_OF_HALLOWED_GOLD.get()).define('Z', Tags.Items.LEATHER).pattern(" Z ").pattern("ZXZ").pattern(" Y ").unlockedBy("has_hallowed_gold", has(ItemRegistry.HALLOWED_GOLD_INGOT.get())).save(consumer);
        shaped(RecipeCategory.MISC, ItemRegistry.ELABORATE_BROOCH.get()).define('X', ItemRegistry.SOUL_STAINED_STEEL_INGOT.get()).define('Y', ItemRegistry.BLOCK_OF_SOUL_STAINED_STEEL.get()).define('Z', Tags.Items.LEATHER).pattern(" Z ").pattern("ZXZ").pattern(" Y ").unlockedBy("has_soul_stained_steel", has(ItemRegistry.SOUL_STAINED_STEEL_INGOT.get())).save(consumer);

        //FRAGMENTS
        shapeless(RecipeCategory.MISC, ItemRegistry.COAL_FRAGMENT.get(), 8).requires(Items.COAL).unlockedBy("has_coal", has(Items.COAL)).save(consumer, malumPath("coal_fragment"));
        shapeless(RecipeCategory.MISC, Items.COAL, 1).requires(ItemRegistry.COAL_FRAGMENT.get(), 8).unlockedBy("has_coal", has(Items.COAL)).save(consumer, malumPath("coal_from_fragment"));
        shapeless(RecipeCategory.MISC, ItemRegistry.CHARCOAL_FRAGMENT.get(), 8).requires(Items.CHARCOAL).unlockedBy("has_charcoal", has(Items.CHARCOAL)).save(consumer, malumPath("charcoal_fragment"));
        shapeless(RecipeCategory.MISC, Items.CHARCOAL, 1).requires(ItemRegistry.CHARCOAL_FRAGMENT.get(), 8).unlockedBy("has_charcoal", has(Items.CHARCOAL)).save(consumer, malumPath("charcoal_from_fragment"));
        shapeless(RecipeCategory.MISC, ItemRegistry.BLAZING_QUARTZ_FRAGMENT.get(), 8).requires(ItemRegistry.BLAZING_QUARTZ.get()).unlockedBy("has_blazing_quartz", has(ItemRegistry.BLAZING_QUARTZ.get())).save(consumer, malumPath("blazing_quartz_fragment"));
        shapeless(RecipeCategory.MISC, ItemRegistry.BLAZING_QUARTZ.get(), 1).requires(ItemRegistry.BLAZING_QUARTZ_FRAGMENT.get(), 8).unlockedBy("has_blazing_quartz", has(ItemRegistry.BLAZING_QUARTZ.get())).save(consumer, malumPath("blazing_quartz_from_fragment"));
        shapeless(RecipeCategory.MISC, ItemRegistry.ARCANE_CHARCOAL_FRAGMENT.get(), 8).requires(ItemRegistry.ARCANE_CHARCOAL.get()).unlockedBy("has_arcane_charcoal", has(ItemRegistry.ARCANE_CHARCOAL.get())).save(consumer, malumPath("arcane_charcoal_fragment"));
        shapeless(RecipeCategory.MISC, ItemRegistry.ARCANE_CHARCOAL.get(), 1).requires(ItemRegistry.ARCANE_CHARCOAL_FRAGMENT.get(), 8).unlockedBy("has_arcane_charcoal", has(ItemRegistry.ARCANE_CHARCOAL.get())).save(consumer, malumPath("arcane_charcoal_from_fragment"));

        //COPPER
        shaped(RecipeCategory.MISC, Items.COPPER_INGOT).define('#', NUGGETS_COPPER).pattern("###").pattern("###").pattern("###").unlockedBy("has_copper", has(INGOTS_COPPER)).save(consumer, malumPath("copper_ingot_from_nugget"));
        shapeless(RecipeCategory.MISC, ItemRegistry.COPPER_NUGGET.get(), 9).requires(INGOTS_COPPER).unlockedBy("has_copper", has(INGOTS_COPPER)).save(consumer, malumPath("copper_nugget_from_ingot"));

        //ORE SMELTING
        smelting(Ingredient.of(ItemRegistry.BLAZING_QUARTZ_ORE.get()), RecipeCategory.MISC, ItemRegistry.BLAZING_QUARTZ.get(), 0.25f, 200).unlockedBy("has_blazing_quartz", has(ItemRegistry.BLAZING_QUARTZ.get())).save(consumer, malumPath("blazing_quartz_from_smelting"));
        blasting(Ingredient.of(ItemRegistry.BLAZING_QUARTZ_ORE.get()), RecipeCategory.MISC, ItemRegistry.BLAZING_QUARTZ.get(), 0.25f, 100).unlockedBy("has_blazing_quartz", has(ItemRegistry.BLAZING_QUARTZ.get())).save(consumer, malumPath("blazing_quartz_from_blasting"));
        smelting(Ingredient.of(ItemRegistry.NATURAL_QUARTZ_ORE.get()), RecipeCategory.MISC, ItemRegistry.NATURAL_QUARTZ.get(), 0.25f, 200).unlockedBy("has_natural_quartz", has(ItemRegistry.NATURAL_QUARTZ.get())).save(consumer, malumPath("natural_quartz_from_smelting"));
        blasting(Ingredient.of(ItemRegistry.NATURAL_QUARTZ_ORE.get()), RecipeCategory.MISC, ItemRegistry.NATURAL_QUARTZ.get(), 0.25f, 100).unlockedBy("has_natural_quartz", has(ItemRegistry.NATURAL_QUARTZ.get())).save(consumer, malumPath("natural_quartz_from_blasting"));
        smelting(Ingredient.of(ItemRegistry.DEEPSLATE_QUARTZ_ORE.get()), RecipeCategory.MISC, ItemRegistry.NATURAL_QUARTZ.get(), 0.25f, 200).unlockedBy("has_natural_quartz", has(ItemRegistry.NATURAL_QUARTZ.get())).save(consumer, malumPath("natural_quartz_from_deepslate_smelting"));
        blasting(Ingredient.of(ItemRegistry.DEEPSLATE_QUARTZ_ORE.get()), RecipeCategory.MISC, ItemRegistry.NATURAL_QUARTZ.get(), 0.25f, 100).unlockedBy("has_natural_quartz", has(ItemRegistry.NATURAL_QUARTZ.get())).save(consumer, malumPath("natural_quartz_from_deepslate_blasting"));
        smeltingWithCount(Ingredient.of(ItemRegistry.BRILLIANT_STONE.get()), RecipeCategory.MISC, ItemRegistry.CHUNK_OF_BRILLIANCE.get(), 2, 1, 200).unlockedBy("has_brilliance", has(ItemRegistry.CLUSTER_OF_BRILLIANCE.get())).save(consumer, malumPath("brilliance_from_smelting"));
        blastingWithCount(Ingredient.of(ItemRegistry.BRILLIANT_STONE.get()), RecipeCategory.MISC, ItemRegistry.CHUNK_OF_BRILLIANCE.get(), 2, 1, 100).unlockedBy("has_brilliance", has(ItemRegistry.CLUSTER_OF_BRILLIANCE.get())).save(consumer, malumPath("brilliance_from_blasting"));
        smeltingWithCount(Ingredient.of(ItemRegistry.BRILLIANT_DEEPSLATE.get()), RecipeCategory.MISC, ItemRegistry.CHUNK_OF_BRILLIANCE.get(), 2, 1, 200).unlockedBy("has_brilliance", has(ItemRegistry.CLUSTER_OF_BRILLIANCE.get())).save(consumer, malumPath("brilliance_from_deepslate_smelting"));
        blastingWithCount(Ingredient.of(ItemRegistry.BRILLIANT_DEEPSLATE.get()), RecipeCategory.MISC, ItemRegistry.CHUNK_OF_BRILLIANCE.get(), 2, 1, 100).unlockedBy("has_brilliance", has(ItemRegistry.CLUSTER_OF_BRILLIANCE.get())).save(consumer, malumPath("brilliance_from_deepslate_blasting"));
        smeltingWithCount(Ingredient.of(ItemRegistry.SOULSTONE_ORE.get()), RecipeCategory.MISC, ItemRegistry.PROCESSED_SOULSTONE.get(), 2, 0.25f, 200).unlockedBy("has_soulstone", has(ItemRegistry.RAW_SOULSTONE.get())).save(consumer, malumPath("soulstone_from_smelting"));
        blastingWithCount(Ingredient.of(ItemRegistry.SOULSTONE_ORE.get()), RecipeCategory.MISC, ItemRegistry.PROCESSED_SOULSTONE.get(), 2, 0.25f, 100).unlockedBy("has_soulstone", has(ItemRegistry.RAW_SOULSTONE.get())).save(consumer, malumPath("soulstone_from_blasting"));
        smeltingWithCount(Ingredient.of(ItemRegistry.DEEPSLATE_SOULSTONE_ORE.get()), RecipeCategory.MISC, ItemRegistry.PROCESSED_SOULSTONE.get(), 2, 0.25f, 200).unlockedBy("has_soulstone", has(ItemRegistry.RAW_SOULSTONE.get())).save(consumer, malumPath("soulstone_from_deepslate_smelting"));
        blastingWithCount(Ingredient.of(ItemRegistry.DEEPSLATE_SOULSTONE_ORE.get()), RecipeCategory.MISC, ItemRegistry.PROCESSED_SOULSTONE.get(), 2, 0.25f, 100).unlockedBy("has_soulstone", has(ItemRegistry.RAW_SOULSTONE.get())).save(consumer, malumPath("soulstone_from_deepslate_blasting"));

        smeltingWithCount(Ingredient.of(ItemRegistry.CLUSTER_OF_BRILLIANCE.get()), RecipeCategory.MISC, ItemRegistry.CHUNK_OF_BRILLIANCE.get(), 2, 1, 200).unlockedBy("has_brilliance", has(ItemRegistry.CLUSTER_OF_BRILLIANCE.get())).save(consumer, malumPath("brilliance_from_raw_smelting"));
        blastingWithCount(Ingredient.of(ItemRegistry.CLUSTER_OF_BRILLIANCE.get()), RecipeCategory.MISC, ItemRegistry.CHUNK_OF_BRILLIANCE.get(), 2, 1, 100).unlockedBy("has_brilliance", has(ItemRegistry.CLUSTER_OF_BRILLIANCE.get())).save(consumer, malumPath("brilliance_from_raw_blasting"));
        smeltingWithCount(Ingredient.of(ItemRegistry.CRUSHED_BRILLIANCE.get()), RecipeCategory.MISC, ItemRegistry.CHUNK_OF_BRILLIANCE.get(), 2, 1, 200).unlockedBy("has_brilliance", has(ItemRegistry.CLUSTER_OF_BRILLIANCE.get())).save(consumer, malumPath("brilliance_from_crushed_smelting"));
        blastingWithCount(Ingredient.of(ItemRegistry.CRUSHED_BRILLIANCE.get()), RecipeCategory.MISC, ItemRegistry.CHUNK_OF_BRILLIANCE.get(), 2, 1, 100).unlockedBy("has_brilliance", has(ItemRegistry.CLUSTER_OF_BRILLIANCE.get())).save(consumer, malumPath("brilliance_from_crushed_blasting"));
        smeltingWithCount(Ingredient.of(ItemRegistry.RAW_SOULSTONE.get()), RecipeCategory.MISC, ItemRegistry.PROCESSED_SOULSTONE.get(), 2, 0.25f, 200).unlockedBy("has_soulstone", has(ItemRegistry.RAW_SOULSTONE.get())).save(consumer, malumPath("soulstone_from_raw_smelting"));
        blastingWithCount(Ingredient.of(ItemRegistry.RAW_SOULSTONE.get()), RecipeCategory.MISC, ItemRegistry.PROCESSED_SOULSTONE.get(), 2, 0.25f, 100).unlockedBy("has_soulstone", has(ItemRegistry.RAW_SOULSTONE.get())).save(consumer, malumPath("soulstone_from_raw_blasting"));
        smeltingWithCount(Ingredient.of(ItemRegistry.CRUSHED_SOULSTONE.get()), RecipeCategory.MISC, ItemRegistry.PROCESSED_SOULSTONE.get(), 2, 0.25f, 200).unlockedBy("has_soulstone", has(ItemRegistry.RAW_SOULSTONE.get())).save(consumer, malumPath("soulstone_from_crushed_smelting"));
        blastingWithCount(Ingredient.of(ItemRegistry.CRUSHED_SOULSTONE.get()), RecipeCategory.MISC, ItemRegistry.PROCESSED_SOULSTONE.get(), 2, 0.25f, 100).unlockedBy("has_soulstone", has(ItemRegistry.RAW_SOULSTONE.get())).save(consumer, malumPath("soulstone_from_crushed_blasting"));

        //RAW ORE BLOCKS
        shaped(RecipeCategory.MISC, ItemRegistry.BLOCK_OF_RAW_SOULSTONE.get()).define('#', ItemRegistry.RAW_SOULSTONE.get()).pattern("###").pattern("###").pattern("###").unlockedBy("has_soulstone", has(ItemRegistry.RAW_SOULSTONE.get())).save(consumer, malumPath("raw_soulstone_block"));
        shapeless(RecipeCategory.MISC, ItemRegistry.RAW_SOULSTONE.get(), 9).requires(ItemRegistry.BLOCK_OF_RAW_SOULSTONE.get()).unlockedBy("has_soulstone", has(ItemRegistry.RAW_SOULSTONE.get())).save(consumer, malumPath("raw_soulstone_from_block"));

        //ORE BLOCKS
        shaped(RecipeCategory.MISC, ItemRegistry.BLOCK_OF_BLAZING_QUARTZ.get()).define('#', ItemRegistry.BLAZING_QUARTZ.get()).pattern("###").pattern("###").pattern("###").unlockedBy("has_blazing_quartz", has(ItemRegistry.BLAZING_QUARTZ.get())).save(consumer, malumPath("block_of_blazing_quartz"));
        shapeless(RecipeCategory.MISC, ItemRegistry.BLAZING_QUARTZ.get(), 9).requires(ItemRegistry.BLOCK_OF_BLAZING_QUARTZ.get()).unlockedBy("has_blazing_quartz", has(ItemRegistry.BLAZING_QUARTZ.get())).save(consumer, malumPath("blazing_quartz_from_block"));
        shaped(RecipeCategory.MISC, ItemRegistry.BLOCK_OF_ARCANE_CHARCOAL.get()).define('#', ItemRegistry.ARCANE_CHARCOAL.get()).pattern("###").pattern("###").pattern("###").unlockedBy("has_arcane_charcoal", has(ItemRegistry.ARCANE_CHARCOAL.get())).save(consumer, malumPath("block_of_arcane_charcoal"));
        shapeless(RecipeCategory.MISC, ItemRegistry.ARCANE_CHARCOAL.get(), 9).requires(ItemRegistry.BLOCK_OF_ARCANE_CHARCOAL.get()).unlockedBy("has_arcane_charcoal", has(ItemRegistry.ARCANE_CHARCOAL.get())).save(consumer, malumPath("arcane_charcoal_from_block"));
        shaped(RecipeCategory.MISC, ItemRegistry.BLOCK_OF_BRILLIANCE.get()).define('#', ItemRegistry.CLUSTER_OF_BRILLIANCE.get()).pattern("###").pattern("###").pattern("###").unlockedBy("has_brilliance", has(ItemRegistry.CLUSTER_OF_BRILLIANCE.get())).save(consumer, malumPath("block_of_brilliance"));
        shapeless(RecipeCategory.MISC, ItemRegistry.CLUSTER_OF_BRILLIANCE.get(), 9).requires(ItemRegistry.BLOCK_OF_BRILLIANCE.get()).unlockedBy("has_brilliance", has(ItemRegistry.CLUSTER_OF_BRILLIANCE.get())).save(consumer, malumPath("brilliance_from_block"));
        shaped(RecipeCategory.MISC, ItemRegistry.BLOCK_OF_SOULSTONE.get()).define('#', ItemRegistry.PROCESSED_SOULSTONE.get()).pattern("###").pattern("###").pattern("###").unlockedBy("has_soulstone", has(ItemRegistry.RAW_SOULSTONE.get())).save(consumer, malumPath("block_of_soulstone"));
        shapeless(RecipeCategory.MISC, ItemRegistry.PROCESSED_SOULSTONE.get(), 9).requires(ItemRegistry.BLOCK_OF_SOULSTONE.get()).unlockedBy("has_soulstone", has(ItemRegistry.RAW_SOULSTONE.get())).save(consumer, malumPath("soulstone_from_block"));

        shaped(RecipeCategory.MISC, ItemRegistry.BLOCK_OF_CTHONIC_GOLD.get()).define('#', ItemRegistry.CTHONIC_GOLD.get()).pattern("###").pattern("###").pattern("###").unlockedBy("has_cthonic_gold", has(ItemRegistry.CTHONIC_GOLD.get())).save(consumer, malumPath("block_of_cthonic_gold"));
        shapeless(RecipeCategory.MISC, ItemRegistry.CTHONIC_GOLD.get(), 9).requires(ItemRegistry.BLOCK_OF_CTHONIC_GOLD.get()).unlockedBy("has_cthonic_gold", has(ItemRegistry.CTHONIC_GOLD.get())).save(consumer, malumPath("cthonic_gold_from_block"));

        shapeless(RecipeCategory.MISC, ItemRegistry.CTHONIC_GOLD.get()).requires(ItemRegistry.CTHONIC_GOLD_FRAGMENT.get(), 8).unlockedBy("has_cthonic_gold", has(ItemRegistry.CTHONIC_GOLD.get())).save(consumer, malumPath("cthonic_gold_from_fragment"));
        shapeless(RecipeCategory.MISC, ItemRegistry.CTHONIC_GOLD_FRAGMENT.get(), 8).requires(ItemRegistry.CTHONIC_GOLD.get()).unlockedBy("has_cthonic_gold", has(ItemRegistry.CTHONIC_GOLD.get())).save(consumer, malumPath("cthonic_gold_fragment"));

        //COMPACT BLOCKS
        shaped(RecipeCategory.MISC, ItemRegistry.BLOCK_OF_ROTTING_ESSENCE.get()).define('#', ItemRegistry.ROTTING_ESSENCE.get()).pattern("###").pattern("###").pattern("###").unlockedBy("has_rotting_essence", has(ItemRegistry.ROTTING_ESSENCE.get())).save(consumer, malumPath("block_of_rotting_essence"));
        shapeless(RecipeCategory.MISC, ItemRegistry.ROTTING_ESSENCE.get(), 9).requires(ItemRegistry.BLOCK_OF_ROTTING_ESSENCE.get()).unlockedBy("has_rotting_essence", has(ItemRegistry.ROTTING_ESSENCE.get())).save(consumer, malumPath("rotting_essence_from_block"));
        shaped(RecipeCategory.MISC, ItemRegistry.BLOCK_OF_GRIM_TALC.get()).define('#', ItemRegistry.GRIM_TALC.get()).pattern("###").pattern("###").pattern("###").unlockedBy("has_grim_talc", has(ItemRegistry.GRIM_TALC.get())).save(consumer, malumPath("block_of_grim_talc"));
        shapeless(RecipeCategory.MISC, ItemRegistry.GRIM_TALC.get(), 9).requires(ItemRegistry.BLOCK_OF_GRIM_TALC.get()).unlockedBy("has_grim_talc", has(ItemRegistry.GRIM_TALC.get())).save(consumer, malumPath("grim_talc_from_block"));
        shaped(RecipeCategory.MISC, ItemRegistry.BLOCK_OF_ALCHEMICAL_CALX.get()).define('#', ItemRegistry.ALCHEMICAL_CALX.get()).pattern("###").pattern("###").pattern("###").unlockedBy("has_alchemical_calx", has(ItemRegistry.ALCHEMICAL_CALX.get())).save(consumer, malumPath("block_of_alchemical_calx"));
        shapeless(RecipeCategory.MISC, ItemRegistry.ALCHEMICAL_CALX.get(), 9).requires(ItemRegistry.BLOCK_OF_ALCHEMICAL_CALX.get()).unlockedBy("has_alchemical_calx", has(ItemRegistry.ALCHEMICAL_CALX.get())).save(consumer, malumPath("alchemical_calx_from_block"));
        shaped(RecipeCategory.MISC, ItemRegistry.BLOCK_OF_ASTRAL_WEAVE.get()).define('#', ItemRegistry.ASTRAL_WEAVE.get()).pattern("###").pattern("###").pattern("###").unlockedBy("has_astral_weave", has(ItemRegistry.ASTRAL_WEAVE.get())).save(consumer, malumPath("block_of_astral_weave"));
        shapeless(RecipeCategory.MISC, ItemRegistry.ASTRAL_WEAVE.get(), 9).requires(ItemRegistry.BLOCK_OF_ASTRAL_WEAVE.get()).unlockedBy("has_astral_weave", has(ItemRegistry.ASTRAL_WEAVE.get())).save(consumer, malumPath("astral_weave_from_block"));
        shaped(RecipeCategory.MISC, ItemRegistry.BLOCK_OF_HEX_ASH.get()).define('#', ItemRegistry.HEX_ASH.get()).pattern("###").pattern("###").pattern("###").unlockedBy("has_hex_ash", has(ItemRegistry.HEX_ASH.get())).save(consumer, malumPath("block_of_hex_ash"));
        shapeless(RecipeCategory.MISC, ItemRegistry.HEX_ASH.get(), 9).requires(ItemRegistry.BLOCK_OF_HEX_ASH.get()).unlockedBy("has_hex_ash", has(ItemRegistry.HEX_ASH.get())).save(consumer, malumPath("hex_ash_from_block"));
        shaped(RecipeCategory.MISC, ItemRegistry.MASS_OF_BLIGHTED_GUNK.get()).define('#', ItemRegistry.BLIGHTED_GUNK.get()).pattern("###").pattern("###").pattern("###").unlockedBy("has_blighted_gunk", has(ItemRegistry.BLIGHTED_GUNK.get())).save(consumer);
        shapeless(RecipeCategory.MISC, ItemRegistry.BLIGHTED_GUNK.get(), 9).requires(ItemRegistry.MASS_OF_BLIGHTED_GUNK.get()).unlockedBy("has_blighted_gunk", has(ItemRegistry.BLIGHTED_GUNK.get())).save(consumer, malumPath("blighted_gunk_from_mass"));
        shaped(RecipeCategory.MISC, ItemRegistry.BLOCK_OF_NULL_SLATE.get()).define('#', ItemRegistry.NULL_SLATE.get()).pattern("###").pattern("###").pattern("###").unlockedBy("has_null_slate", has(ItemRegistry.NULL_SLATE.get())).save(consumer);
        shapeless(RecipeCategory.MISC, ItemRegistry.NULL_SLATE.get(), 9).requires(ItemRegistry.BLOCK_OF_NULL_SLATE.get()).unlockedBy("has_null_slate", has(ItemRegistry.NULL_SLATE.get())).save(consumer, malumPath("null_slate_from_block"));
        shaped(RecipeCategory.MISC, ItemRegistry.BLOCK_OF_VOID_SALTS.get()).define('#', ItemRegistry.VOID_SALTS.get()).pattern("###").pattern("###").pattern("###").unlockedBy("has_void_salts", has(ItemRegistry.VOID_SALTS.get())).save(consumer);
        shapeless(RecipeCategory.MISC, ItemRegistry.VOID_SALTS.get(), 9).requires(ItemRegistry.BLOCK_OF_VOID_SALTS.get()).unlockedBy("has_void_salts", has(ItemRegistry.VOID_SALTS.get())).save(consumer, malumPath("void_salts_from_block"));
        shaped(RecipeCategory.MISC, ItemRegistry.BLOCK_OF_MNEMONIC_FRAGMENT.get()).define('#', ItemRegistry.MNEMONIC_FRAGMENT.get()).pattern("###").pattern("###").pattern("###").unlockedBy("has_mnemonic_fragment", has(ItemRegistry.MNEMONIC_FRAGMENT.get())).save(consumer);
        shapeless(RecipeCategory.MISC, ItemRegistry.MNEMONIC_FRAGMENT.get(), 9).requires(ItemRegistry.BLOCK_OF_MNEMONIC_FRAGMENT.get()).unlockedBy("has_mnemonic_fragment", has(ItemRegistry.MNEMONIC_FRAGMENT.get())).save(consumer, malumPath("mnemonic_fragment_from_block"));
        shaped(RecipeCategory.MISC, ItemRegistry.BLOCK_OF_MALIGNANT_LEAD.get()).define('#', ItemRegistry.MALIGNANT_LEAD.get()).pattern("###").pattern("###").pattern("###").unlockedBy("has_malignant_lead", has(ItemRegistry.MALIGNANT_LEAD.get())).save(consumer);
        shapeless(RecipeCategory.MISC, ItemRegistry.MALIGNANT_LEAD.get(), 9).requires(ItemRegistry.BLOCK_OF_MALIGNANT_LEAD.get()).unlockedBy("has_malignant_lead", has(ItemRegistry.MALIGNANT_LEAD.get())).save(consumer, malumPath("malignant_lead_from_block"));
        shaped(RecipeCategory.MISC, ItemRegistry.BLOCK_OF_AURIC_EMBERS.get()).define('#', ItemRegistry.AURIC_EMBERS.get()).pattern("###").pattern("###").pattern("###").unlockedBy("has_auric_embers", has(ItemRegistry.AURIC_EMBERS.get())).save(consumer);
        shapeless(RecipeCategory.MISC, ItemRegistry.AURIC_EMBERS.get(), 9).requires(ItemRegistry.BLOCK_OF_AURIC_EMBERS.get()).unlockedBy("has_auric_embers", has(ItemRegistry.AURIC_EMBERS.get())).save(consumer, malumPath("auric_embers_from_block"));

        //MISC
        shaped(RecipeCategory.MISC, Items.NETHERRACK, 2).define('Z', ItemRegistry.BLAZING_QUARTZ.get()).define('Y', Tags.Items.COBBLESTONE).pattern("ZY").pattern("YZ").unlockedBy("has_blazing_quartz", has(ItemRegistry.BLAZING_QUARTZ.get())).save(consumer, malumPath("netherrack_from_blazing_quartz"));
        shapeless(RecipeCategory.MISC, Items.EXPERIENCE_BOTTLE).requires(ItemRegistry.CHUNK_OF_BRILLIANCE.get()).requires(Items.GLASS_BOTTLE).unlockedBy("has_brilliance", has(ItemRegistry.CHUNK_OF_BRILLIANCE.get())).save(consumer, malumPath("experience_bottle_from_brilliance"));

        shapeless(RecipeCategory.MISC, Items.BONE_MEAL, 6).requires(ItemRegistry.GRIM_TALC.get()).unlockedBy("has_grim_talc", has(ItemRegistry.GRIM_TALC.get())).save(consumer, malumPath("bonemeal_from_grim_talc"));
        shaped(RecipeCategory.MISC, Items.SKELETON_SKULL).define('#', ItemRegistry.GRIM_TALC.get()).define('&', Tags.Items.BONES).pattern("&&&").pattern("&#&").pattern("&&&").unlockedBy("has_grim_talc", has(ItemRegistry.GRIM_TALC.get())).save(consumer, malumPath("skeleton_skull_from_grim_talc"));
        shaped(RecipeCategory.MISC, Items.ZOMBIE_HEAD).define('#', ItemRegistry.GRIM_TALC.get()).define('&', Items.ROTTEN_FLESH).pattern("&&&").pattern("&#&").pattern("&&&").unlockedBy("has_grim_talc", has(ItemRegistry.GRIM_TALC.get())).save(consumer, malumPath("zombie_head_from_grim_talc"));

        shaped(RecipeCategory.MISC, Items.TORCH, 6).define('#', ItemRegistry.BLAZING_QUARTZ.get()).define('&', Items.STICK).pattern("#").pattern("&").unlockedBy("has_blazing_quartz", has(ItemRegistry.BLAZING_QUARTZ.get())).save(consumer, malumPath("torch_from_blazing_quartz"));

        //SAP & ARCANE CHARCOAL
        smelting(Ingredient.of(ItemTagRegistry.RUNEWOOD_LOGS), RecipeCategory.MISC, ItemRegistry.ARCANE_CHARCOAL.get(), 0.25f, 200).unlockedBy("has_runewood_planks", has(ItemTagRegistry.RUNEWOOD_LOGS)).save(consumer, malumPath("arcane_charcoal_from_runewood"));
        shapeless(RecipeCategory.MISC, ItemRegistry.RUNIC_SAPBALL.get()).requires(ItemRegistry.RUNIC_SAP.get(), 2).unlockedBy("has_runic_sap", has(ItemRegistry.RUNIC_SAP.get())).save(consumer);
        shapeless(RecipeCategory.MISC, ItemRegistry.RUNIC_SAP_BLOCK.get(), 8).requires(ItemRegistry.RUNIC_SAP.get(), 4).unlockedBy("has_runic_sap", has(ItemRegistry.RUNIC_SAP.get())).save(consumer);

        smelting(Ingredient.of(ItemTagRegistry.SOULWOOD_LOGS), RecipeCategory.MISC, ItemRegistry.ARCANE_CHARCOAL.get(), 0.25f, 200).unlockedBy("has_soulwood_planks", has(ItemTagRegistry.SOULWOOD_LOGS)).save(consumer, malumPath("arcane_charcoal_from_soulwood"));
        shapeless(RecipeCategory.MISC, ItemRegistry.CURSED_SAPBALL.get()).requires(ItemRegistry.CURSED_SAP.get(), 2).unlockedBy("has_cursed_sap", has(ItemRegistry.CURSED_SAP.get())).save(consumer);
        shapeless(RecipeCategory.MISC, ItemRegistry.CURSED_SAP_BLOCK.get(), 8).requires(ItemRegistry.CURSED_SAP.get(), 4).unlockedBy("has_cursed_sap", has(ItemRegistry.CURSED_SAP.get())).save(consumer);

        //ETHER
        etherTorch(consumer, ItemRegistry.ETHER_TORCH.get(), ItemRegistry.ETHER.get());
        etherBrazier(consumer, ItemRegistry.TAINTED_ETHER_BRAZIER.get(), ItemRegistry.TAINTED_ROCK.get(), ItemRegistry.ETHER.get());
        etherBrazier(consumer, ItemRegistry.TWISTED_ETHER_BRAZIER.get(), ItemRegistry.TWISTED_ROCK.get(), ItemRegistry.ETHER.get());
        etherTorch(consumer, ItemRegistry.IRIDESCENT_ETHER_TORCH.get(), ItemRegistry.IRIDESCENT_ETHER.get());
        etherBrazier(consumer, ItemRegistry.TAINTED_IRIDESCENT_ETHER_BRAZIER.get(), ItemRegistry.TAINTED_ROCK.get(), ItemRegistry.IRIDESCENT_ETHER.get());
        etherBrazier(consumer, ItemRegistry.TWISTED_IRIDESCENT_ETHER_BRAZIER.get(), ItemRegistry.TWISTED_ROCK.get(), ItemRegistry.IRIDESCENT_ETHER.get());

        //THE DEVICE
        TheDeviceRecipeBuilder.shaped(ItemRegistry.THE_DEVICE.get()).define('X', ItemRegistry.TWISTED_ROCK.get()).define('Y', ItemRegistry.TAINTED_ROCK.get()).pattern("XYX").pattern("YXY").pattern("XYX").unlockedBy("has_bedrock", has(Items.BEDROCK)).save(consumer);

        weaveRecipe(consumer, ItemRegistry.BLIGHTED_GUNK.get(), ItemRegistry.ANCIENT_WEAVE);
        weaveRecipe(consumer, Items.IRON_INGOT, ItemRegistry.CORNERED_WEAVE);
        weaveRecipe(consumer, Items.COPPER_INGOT, ItemRegistry.DREADED_WEAVE);
        weaveRecipe(consumer, Items.LAPIS_LAZULI, ItemRegistry.MECHANICAL_WEAVE_V1);
        weaveRecipe(consumer, Items.REDSTONE, ItemRegistry.MECHANICAL_WEAVE_V2);

        weaveRecipe(consumer, Items.BREAD, ItemRegistry.ACE_PRIDEWEAVE);
        weaveRecipe(consumer, Items.BOOK, ItemRegistry.AGENDER_PRIDEWEAVE);
        weaveRecipe(consumer, Items.ARROW, ItemRegistry.ARO_PRIDEWEAVE);
        weaveRecipe(consumer, Items.WHEAT_SEEDS, ItemRegistry.AROACE_PRIDEWEAVE);
        weaveRecipe(consumer, Items.WHEAT, ItemRegistry.BI_PRIDEWEAVE);
        weaveRecipe(consumer, Items.RAW_IRON, ItemRegistry.DEMIBOY_PRIDEWEAVE);
        weaveRecipe(consumer, Items.RAW_COPPER, ItemRegistry.DEMIGIRL_PRIDEWEAVE);
        weaveRecipe(consumer, Items.MOSS_BLOCK, ItemRegistry.ENBY_PRIDEWEAVE);
        weaveRecipe(consumer, Items.MELON_SLICE, ItemRegistry.GAY_PRIDEWEAVE);
        weaveRecipe(consumer, Items.WATER_BUCKET, ItemRegistry.GENDERFLUID_PRIDEWEAVE);
        weaveRecipe(consumer, Items.GLASS_BOTTLE, ItemRegistry.GENDERQUEER_PRIDEWEAVE);
        weaveRecipe(consumer, Items.AZALEA, ItemRegistry.INTERSEX_PRIDEWEAVE);
        weaveRecipe(consumer, Items.HONEYCOMB, ItemRegistry.LESBIAN_PRIDEWEAVE);
        weaveRecipe(consumer, Items.CARROT, ItemRegistry.PAN_PRIDEWEAVE);
        weaveRecipe(consumer, Items.REPEATER, ItemRegistry.PLURAL_PRIDEWEAVE);
        weaveRecipe(consumer, Items.COMPARATOR, ItemRegistry.POLY_PRIDEWEAVE);
        weaveRecipe(consumer, Items.STONE_BRICK_WALL, ItemRegistry.PRIDE_PRIDEWEAVE);
        weaveRecipe(consumer, Items.EGG, ItemRegistry.TRANS_PRIDEWEAVE);
    }

    private static void weaveRecipe(Consumer<FinishedRecipe> consumer, Item sideItem, Supplier<? extends Item> output) {
        shapeless(RecipeCategory.MISC, output.get()).requires(ItemRegistry.ESOTERIC_SPOOL.get()).requires(sideItem).unlockedBy("has_spool", has(ItemRegistry.ESOTERIC_SPOOL.get())).save(consumer);
    }

    private static void nodeSmelting(Consumer<FinishedRecipe> recipeConsumer, RegistryObject<ImpetusItem> impetus, RegistryObject<Item> node, TagKey<Item> tag) {
        String name = ForgeRegistries.ITEMS.getKey(node.get()).getPath().replaceFirst("_node", "");

        ConditionalRecipe.builder().addCondition(new NotCondition(new TagEmptyCondition(tag.location().toString()))).addRecipe(
                        smeltingWithTag(new IngredientWithCount(Ingredient.of(tag), 6), Ingredient.of(node.get()), 0.25f, 200)
                                ::build)
                .generateAdvancement()
                .build(recipeConsumer, MalumMod.malumPath(name + "_from_node_smelting"));

        ConditionalRecipe.builder().addCondition(new NotCondition(new TagEmptyCondition(tag.location().toString()))).addRecipe(
                        blastingWithTag(new IngredientWithCount(Ingredient.of(tag), 6), Ingredient.of(node.get()), 0.25f, 100)
                                ::build)
                .generateAdvancement()
                .build(recipeConsumer, MalumMod.malumPath(name + "_from_node_blasting"));
    }

    private static void etherBrazier(Consumer<FinishedRecipe> recipeConsumer, ItemLike output, ItemLike rock, ItemLike ether) {
        NBTCarryRecipeBuilder.shapedRecipe(output, 2, Ingredient.of(ether)).key('#', rock).key('S', Ingredient.of(Tags.Items.RODS_WOODEN)).key('X', ether).patternLine("#X#").patternLine("S#S").addCriterion("has_ether", has(ItemRegistry.ETHER.get())).build(recipeConsumer, MalumMod.malumPath(ForgeRegistries.ITEMS.getKey(output.asItem()).getPath()));
    }

    private static void etherTorch(Consumer<FinishedRecipe> recipeConsumer, ItemLike output, ItemLike ether) {
        NBTCarryRecipeBuilder.shapedRecipe(output, 4, Ingredient.of(ether)).key('#', Ingredient.of(Tags.Items.RODS_WOODEN)).key('X', ether).patternLine("X").patternLine("#").addCriterion("has_ether", has(ItemRegistry.ETHER.get())).build(recipeConsumer, MalumMod.malumPath(ForgeRegistries.ITEMS.getKey(output.asItem()).getPath() + "_alternative"));
    }

    private static void shapelessPlanks(Consumer<FinishedRecipe> recipeConsumer, ItemLike planks, TagKey<Item> input) {
        shapeless(RecipeCategory.MISC, planks, 4).requires(input).group("planks").unlockedBy("has_logs", has(input)).save(recipeConsumer);
    }

    private static void shapelessWood(Consumer<FinishedRecipe> recipeConsumer, ItemLike stripped, ItemLike input) {
        shaped(RecipeCategory.MISC, stripped, 3).define('#', input).pattern("##").pattern("##").group("bark").unlockedBy("has_log", has(input)).save(recipeConsumer);
    }

    private static void shapelessButton(Consumer<FinishedRecipe> recipeConsumer, ItemLike button, ItemLike input) {
        shapeless(RecipeCategory.MISC, button).requires(input).unlockedBy("has_input", has(input)).save(recipeConsumer);
    }

    private static void shapedDoor(Consumer<FinishedRecipe> recipeConsumer, ItemLike door, ItemLike input) {
        shaped(RecipeCategory.MISC, door, 3).define('#', input).pattern("##").pattern("##").pattern("##").unlockedBy("has_input", has(input)).save(recipeConsumer);
    }

    private static void shapedFence(Consumer<FinishedRecipe> recipeConsumer, ItemLike fence, ItemLike input) {
        shaped(RecipeCategory.MISC, fence, 3).define('#', Tags.Items.RODS_WOODEN).define('W', input).pattern("W#W").pattern("W#W").unlockedBy("has_input", has(input)).save(recipeConsumer);
    }

    private static void shapedFenceGate(Consumer<FinishedRecipe> recipeConsumer, ItemLike fenceGate, ItemLike input) {
        shaped(RecipeCategory.MISC, fenceGate).define('#', Tags.Items.RODS_WOODEN).define('W', input).pattern("#W#").pattern("#W#").unlockedBy("has_input", has(input)).save(recipeConsumer);
    }

    private static void shapedPressurePlate(Consumer<FinishedRecipe> recipeConsumer, ItemLike pressurePlate, ItemLike input) {
        shaped(RecipeCategory.MISC, pressurePlate).define('#', input).pattern("##").unlockedBy("has_input", has(input)).save(recipeConsumer);
    }

    private static void shapedSlab(Consumer<FinishedRecipe> recipeConsumer, ItemLike slab, ItemLike input) {
        shaped(RecipeCategory.MISC, slab, 6).define('#', input).pattern("###").unlockedBy("has_input", has(input)).save(recipeConsumer);
    }

    private static void shapedStairs(Consumer<FinishedRecipe> recipeConsumer, ItemLike stairs, ItemLike input) {
        shaped(RecipeCategory.MISC, stairs, 4).define('#', input).pattern("#  ").pattern("## ").pattern("###").unlockedBy("has_input", has(input)).save(recipeConsumer);
    }

    private static void shapelessSolidTrapdoor(Consumer<FinishedRecipe> recipeConsumer, ItemLike solid, ItemLike normal) {
        shapeless(RecipeCategory.MISC, solid).requires(normal).unlockedBy("has_input", has(normal)).save(recipeConsumer);
    }

    private static void shapelessSolidTrapdoor(Consumer<FinishedRecipe> recipeConsumer, ItemLike solid, ItemLike normal, String path) {
        shapeless(RecipeCategory.MISC, solid).requires(normal).unlockedBy("has_input", has(normal)).save(recipeConsumer, malumPath(path));
    }

    private static void shapedTrapdoor(Consumer<FinishedRecipe> recipeConsumer, ItemLike trapdoor, ItemLike input) {
        shaped(RecipeCategory.MISC, trapdoor, 2).define('#', input).pattern("###").pattern("###").unlockedBy("has_input", has(input)).save(recipeConsumer);
    }

    private static void shapedSign(Consumer<FinishedRecipe> recipeConsumer, ItemLike sign, ItemLike input) {
        String s = ForgeRegistries.ITEMS.getKey(input.asItem()).getPath();
        shaped(RecipeCategory.MISC, sign, 3).group("sign").define('#', input).define('X', Tags.Items.RODS_WOODEN).pattern("###").pattern("###").pattern(" X ").unlockedBy("has_" + s, has(input)).save(recipeConsumer);
    }


    protected static EnterBlockTrigger.TriggerInstance insideOf(Block pBlock) {
        return new EnterBlockTrigger.TriggerInstance(ContextAwarePredicate.ANY, pBlock, StatePropertiesPredicate.ANY);
    }

    public static InventoryChangeTrigger.TriggerInstance has(MinMaxBounds.Ints pCount, ItemLike pItem) {
        return inventoryTrigger(ItemPredicate.Builder.item().of(pItem).withCount(pCount).build());
    }

    public static InventoryChangeTrigger.TriggerInstance has(ItemLike pItemLike) {
        return inventoryTrigger(ItemPredicate.Builder.item().of(pItemLike).build());
    }

    public static InventoryChangeTrigger.TriggerInstance has(TagKey<Item> pTag) {
        return inventoryTrigger(ItemPredicate.Builder.item().of(pTag).build());
    }

    public static InventoryChangeTrigger.TriggerInstance inventoryTrigger(ItemPredicate... pPredicates) {
        return new InventoryChangeTrigger.TriggerInstance(ContextAwarePredicate.ANY, MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, pPredicates);
    }
}
