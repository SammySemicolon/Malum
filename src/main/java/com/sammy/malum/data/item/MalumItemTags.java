package com.sammy.malum.data.item;

import com.sammy.malum.common.item.*;
import com.sammy.malum.common.item.augment.*;
import com.sammy.malum.common.item.curiosities.curios.*;
import com.sammy.malum.common.item.impetus.*;
import com.sammy.malum.data.recipe.crafting.*;
import com.sammy.malum.registry.common.block.*;
import com.sammy.malum.registry.common.item.*;
import io.github.fabricators_of_create.porting_lib.tags.Tags;
import io.github.fabricators_of_create.porting_lib.util.DeferredHolder;
import io.github.fabricators_of_create.porting_lib.util.DeferredRegister;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.*;
import net.minecraft.core.registries.*;
import net.minecraft.resources.*;
import net.minecraft.tags.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.*;
import org.jetbrains.annotations.Nullable;
import team.lodestar.lodestone.systems.block.*;
import team.lodestar.lodestone.systems.datagen.*;

import java.util.concurrent.*;

import static com.sammy.malum.registry.common.item.ItemRegistry.*;
import static team.lodestar.lodestone.registry.common.tag.LodestoneItemTags.*;

@SuppressWarnings("unchecked")
public class MalumItemTags extends FabricTagProvider.ItemTagProvider {

    public MalumItemTags(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture, @Nullable FabricTagProvider.BlockTagProvider blockTagProvider) {
        super(output, completableFuture, blockTagProvider);
    }

    @Override
    public String getName() {
        return "Malum Item Tags";
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        var items = ITEMS.getEntries();
        MalumWoodSetDatagen.addTags(this);
        MalumRockSetDatagen.addTags(this);

        copy(BlockTags.PLANKS, ItemTags.PLANKS);
        copy(BlockTags.WOODEN_BUTTONS, ItemTags.WOODEN_BUTTONS);
        copy(BlockTags.BUTTONS, ItemTags.BUTTONS);
        copy(BlockTags.WOODEN_DOORS, ItemTags.WOODEN_DOORS);
        copy(BlockTags.WOODEN_STAIRS, ItemTags.WOODEN_STAIRS);
        copy(BlockTags.WOODEN_SLABS, ItemTags.WOODEN_SLABS);
        copy(BlockTags.WOODEN_FENCES, ItemTags.WOODEN_FENCES);
        copy(BlockTags.WOODEN_PRESSURE_PLATES, ItemTags.WOODEN_PRESSURE_PLATES);
        copy(BlockTags.DOORS, ItemTags.DOORS);
        copy(BlockTags.SAPLINGS, ItemTags.SAPLINGS);
        copy(BlockTagRegistry.STRIPPED_LOGS, ItemTagRegistry.STRIPPED_LOGS);
        copy(BlockTags.SLABS, ItemTags.SLABS);
        copy(BlockTags.WALLS, ItemTags.WALLS);
        copy(BlockTags.STAIRS, ItemTags.STAIRS);
        copy(BlockTags.LEAVES, ItemTags.LEAVES);
        copy(BlockTags.WOODEN_TRAPDOORS, ItemTags.WOODEN_TRAPDOORS);
        copy(BlockTags.TRAPDOORS, ItemTags.TRAPDOORS);
        copy(BlockTags.FENCES, ItemTags.FENCES);
        copy(Tags.Blocks.ORES, Tags.Items.ORES);
        copy(Tags.Blocks.STORAGE_BLOCKS, Tags.Items.STORAGE_BLOCKS);

        getOrCreateTagBuilder(Tags.Items.GEMS).add(REFINED_SOULSTONE.get(), BLAZING_QUARTZ.get());
        getOrCreateTagBuilder(ItemTags.LOGS).addOptionalTag(ItemTagRegistry.RUNEWOOD_LOGS).addOptionalTag(ItemTagRegistry.SOULWOOD_LOGS);
        getOrCreateTagBuilder(ItemTags.LOGS_THAT_BURN).addOptionalTag(ItemTagRegistry.RUNEWOOD_LOGS).addOptionalTag(ItemTagRegistry.SOULWOOD_LOGS);
        getOrCreateTagBuilder(Tags.Items.SLIMEBALLS).add(RUNIC_SAPBALL.get(), CURSED_SAPBALL.get());
        getOrCreateTagBuilder(Tags.Items.GEMS_QUARTZ).add(NATURAL_QUARTZ.get());
        getOrCreateTagBuilder(Tags.Items.ORES_QUARTZ).add(NATURAL_QUARTZ_ORE.get(), DEEPSLATE_QUARTZ_ORE.get());

        getOrCreateTagBuilder(ItemTagRegistry.MAGIC_CAPABLE_WEAPONS).add(
                CRUDE_SCYTHE.get(), SOUL_STAINED_STEEL_SCYTHE.get(), CREATIVE_SCYTHE.get(),
                MNEMONIC_HEX_STAFF.get(), STAFF_OF_THE_AURIC_FLAME.get(), EROSION_SCEPTER.get());

        getOrCreateTagBuilder(ItemTagRegistry.SCYTHES).add(CRUDE_SCYTHE.get(), SOUL_STAINED_STEEL_SCYTHE.get(), EDGE_OF_DELIVERANCE.get(), CREATIVE_SCYTHE.get());
        getOrCreateTagBuilder(ItemTagRegistry.STAVES).add(MNEMONIC_HEX_STAFF.get(), STAFF_OF_THE_AURIC_FLAME.get(), EROSION_SCEPTER.get());

        getOrCreateTagBuilder(ItemTagRegistry.SOUL_SHATTER_CAPABLE_WEAPONS)
                .addTags(ItemTagRegistry.SCYTHES, ItemTagRegistry.STAVES);
        getOrCreateTagBuilder(ItemTagRegistry.SOUL_SHATTER_CAPABLE_WEAPONS)
                .add(TYRVING.get(), WEIGHT_OF_WORLDS.get())
                .add(SOUL_STAINED_STEEL_AXE.get(), SOUL_STAINED_STEEL_PICKAXE.get(), SOUL_STAINED_STEEL_SHOVEL.get(), SOUL_STAINED_STEEL_SWORD.get(), SOUL_STAINED_STEEL_HOE.get(), SOUL_STAINED_STEEL_KNIFE.get());

        getOrCreateTagBuilder(ItemTagRegistry.SOUL_SHATTER_CAPABLE_WEAPONS)
                .addOptional(ResourceLocation.parse("irons_spellbooks:graybeard_staff"))
                .addOptional(ResourceLocation.parse("irons_spellbooks:artificer_cane"))
                .addOptional(ResourceLocation.parse("irons_spellbooks:lightning_rod"))
                .addOptional(ResourceLocation.parse("irons_spellbooks:ice_staff"))
                .addOptional(ResourceLocation.parse("irons_spellbooks:blood_staff"))
                .addOptional(ResourceLocation.parse("irons_spellbooks:magehunter"))
                .addOptional(ResourceLocation.parse("irons_spellbooks:keeper_flamberge"))
                .addOptional(ResourceLocation.parse("irons_spellbooks:spellbreaker"))
                .addOptional(ResourceLocation.parse("irons_spellbooks:amethyst_rapier"));

        getOrCreateTagBuilder(ItemTagRegistry.ANIMATED_ENCHANTABLE).addTag(ItemTagRegistry.SCYTHES);
        getOrCreateTagBuilder(ItemTagRegistry.REBOUND_ENCHANTABLE).addTag(ItemTagRegistry.SCYTHES);
        getOrCreateTagBuilder(ItemTagRegistry.ASCENSION_ENCHANTABLE).addTag(ItemTagRegistry.SCYTHES);
        getOrCreateTagBuilder(ItemTagRegistry.REPLENISHING_ENCHANTABLE).addTag(ItemTagRegistry.STAVES);
        getOrCreateTagBuilder(ItemTagRegistry.HAUNTED_ENCHANTABLE).addTag(ItemTagRegistry.MAGIC_CAPABLE_WEAPONS);
        getOrCreateTagBuilder(ItemTagRegistry.SPIRIT_SPOILS_ENCHANTABLE).addTag(ItemTagRegistry.SOUL_SHATTER_CAPABLE_WEAPONS);

        getOrCreateTagBuilder(ItemTagRegistry.ASPECTED_SPIRITS).add(
                SACRED_SPIRIT.get(), WICKED_SPIRIT.get(), ARCANE_SPIRIT.get(), ELDRITCH_SPIRIT.get(),
                AERIAL_SPIRIT.get(), AQUEOUS_SPIRIT.get(), EARTHEN_SPIRIT.get(), INFERNAL_SPIRIT.get());
        getOrCreateTagBuilder(ItemTagRegistry.SPIRITS).addTag(ItemTagRegistry.ASPECTED_SPIRITS).add(UMBRAL_SPIRIT.get());
        getOrCreateTagBuilder(ItemTagRegistry.MOB_DROPS).add(
                ROTTING_ESSENCE.get(), GRIM_TALC.get(), ASTRAL_WEAVE.get(), WARP_FLUX.get(),
                Items.ROTTEN_FLESH, Items.SPIDER_EYE, Items.BONE, Items.GUNPOWDER, Items.STRING, Items.SLIME_BALL,
                Items.MAGMA_CREAM, Items.BLAZE_ROD, Items.BREEZE_ROD,
                Items.LEATHER, Items.RABBIT_HIDE, Items.FEATHER, Items.INK_SAC);
        getOrCreateTagBuilder(ItemTagRegistry.MATERIALS).add(
                ROTTING_ESSENCE.get(), GRIM_TALC.get(), ASTRAL_WEAVE.get(), WARP_FLUX.get(),
                HEX_ASH.get(),LIVING_FLESH.get(), ALCHEMICAL_CALX.get(), BLIGHTED_GUNK.get(),
                SOULWOVEN_SILK.get(), ETHER.get(), IRIDESCENT_ETHER.get(),
                SOUL_STAINED_STEEL_INGOT.get(), SOUL_STAINED_STEEL_NUGGET.get(), SOUL_STAINED_STEEL_PLATING.get(),
                HALLOWED_GOLD_INGOT.get(), HALLOWED_GOLD_NUGGET.get(),
                MALIGNANT_PEWTER_INGOT.get(), MALIGNANT_PEWTER_NUGGET.get(), MALIGNANT_PEWTER_PLATING.get(),
                NULL_SLATE.get(), VOID_SALTS.get(), MNEMONIC_FRAGMENT.get(), AURIC_EMBERS.get(), MALIGNANT_LEAD.get(),
                ANOMALOUS_DESIGN.get(), COMPLETE_DESIGN.get(), FUSED_CONSCIOUSNESS.get());
        getOrCreateTagBuilder(ItemTagRegistry.MINERALS).add(
                RAW_SOULSTONE.get(), CRUSHED_SOULSTONE.get(), REFINED_SOULSTONE.get(),
                RAW_BRILLIANCE.get(), CRUSHED_BRILLIANCE.get(), REFINED_BRILLIANCE.get(),
                BLAZING_QUARTZ.get(), ARCANE_CHARCOAL.get(),
                NATURAL_QUARTZ.get(), CTHONIC_GOLD.get(), CTHONIC_GOLD_FRAGMENT.get());

        getOrCreateTagBuilder(ItemTagRegistry.AUGMENTS).addAll(items.stream().filter(i -> i.get() instanceof AbstractAugmentItem).map(DeferredHolder::getKey).toList());
        getOrCreateTagBuilder(ItemTagRegistry.METAL_NODES).addAll(items.stream().filter(i -> i.get() instanceof NodeItem).map(DeferredHolder::getKey).toList());
        getOrCreateTagBuilder(ItemTagRegistry.SOULWOVEN_BANNERS).addAll(items.stream().filter(i -> i.get() instanceof SoulwovenBannerBlockItem).map(DeferredHolder::getKey).toList());

        getOrCreateTagBuilder(ItemTagRegistry.SAPBALLS).add(RUNIC_SAPBALL.get(), CURSED_SAPBALL.get());
        getOrCreateTagBuilder(ItemTagRegistry.GROSS_FOODS).add(Items.ROTTEN_FLESH, ROTTING_ESSENCE.get(), CONCENTRATED_GLUTTONY.get());

        getOrCreateTagBuilder(ItemTagRegistry.PROSPECTORS_TREASURE)
                .addTags(Tags.Items.ORES, Tags.Items.STORAGE_BLOCKS, Tags.Items.INGOTS, Tags.Items.NUGGETS, Tags.Items.GEMS, Tags.Items.RAW_MATERIALS, ItemTags.COALS, ItemTagRegistry.METAL_NODES)
                .addOptional(ResourceLocation.parse("tetra:geode"));

        getOrCreateTagBuilder(ItemTagRegistry.SOULHUNTERS_TREASURE)
                .addTags(ItemTagRegistry.SOUL_SHATTER_CAPABLE_WEAPONS, ItemTagRegistry.SPIRITS, ItemTagRegistry.MOB_DROPS, ItemTagRegistry.MATERIALS, ItemTagRegistry.MINERALS)
                .addTags(ItemTagRegistry.AUGMENTS, ItemTagRegistry.METAL_NODES, ItemTagRegistry.SOULWOVEN_BANNERS)
                .addTags(ItemTagRegistry.RING, ItemTagRegistry.NECKLACE, ItemTagRegistry.BELT, ItemTagRegistry.BROOCH, ItemTagRegistry.RUNE);
        getOrCreateTagBuilder(ItemTagRegistry.SOULHUNTERS_TREASURE)
                .add(TUNING_FORK.get(), LAMPLIGHTERS_TONGS.get(), CATALYST_LOBBER.get())
                .add(ENCYCLOPEDIA_ARCANA.get(), ENCYCLOPEDIA_ESOTERICA.get());
        getOrCreateTagBuilder(ItemTagRegistry.SOULWOVEN_POUCH_AUTOCOLLECT)
                .addTags(ItemTagRegistry.SPIRITS, ItemTagRegistry.MOB_DROPS, ItemTagRegistry.MATERIALS, ItemTagRegistry.MINERALS);

        getOrCreateTagBuilder(Tags.Items.NUGGETS).add(COPPER_NUGGET.get(), HALLOWED_GOLD_NUGGET.get(), SOUL_STAINED_STEEL_NUGGET.get());
        getOrCreateTagBuilder(Tags.Items.GEMS).add(NATURAL_QUARTZ.get(), BLAZING_QUARTZ.get(), RAW_BRILLIANCE.get());
        getOrCreateTagBuilder(Tags.Items.INGOTS).add(SOUL_STAINED_STEEL_INGOT.get(), HALLOWED_GOLD_INGOT.get());

        getOrCreateTagBuilder(ItemTagRegistry.KNIVES).add(SOUL_STAINED_STEEL_KNIFE.get());
        getOrCreateTagBuilder(ItemTagRegistry.KNIVES_FD).add(SOUL_STAINED_STEEL_KNIFE.get());

        getOrCreateTagBuilder(NUGGETS_COPPER).add(COPPER_NUGGET.get());

        getOrCreateTagBuilder(ItemTagRegistry.HIDDEN_ALWAYS).add(THE_DEVICE.get(), THE_VESSEL.get());

        getOrCreateTagBuilder(ItemTagRegistry.HIDDEN_UNTIL_VOID)
                .addTag(ItemTagRegistry.HIDDEN_UNTIL_BLACK_CRYSTAL)
                // The Well
                .add(PRIMORDIAL_SOUP.get())
                // Encyclopedia
                .add(ENCYCLOPEDIA_ESOTERICA.get())
                //Equipment
                .add(CATALYST_LOBBER.get())
                // Materials
                .add(BLOCK_OF_NULL_SLATE.get(), NULL_SLATE.get(),
                        BLOCK_OF_VOID_SALTS.get(), VOID_SALTS.get(),
                        BLOCK_OF_MNEMONIC_FRAGMENT.get(), MNEMONIC_FRAGMENT.get(),
                        BLOCK_OF_AURIC_EMBERS.get(), AURIC_EMBERS.get(),
                        BLOCK_OF_MALIGNANT_LEAD.get(), MALIGNANT_LEAD.get());

        getOrCreateTagBuilder(ItemTagRegistry.HIDDEN_UNTIL_BLACK_CRYSTAL)
                // Umbral Spirit
                .add(UMBRAL_SPIRIT.get())
                // Anomalous Design
                .add(ANOMALOUS_DESIGN.get(), COMPLETE_DESIGN.get(), FUSED_CONSCIOUSNESS.get())
                // Malignant Pewter
                .add(MALIGNANT_PEWTER_INGOT.get(), MALIGNANT_PEWTER_PLATING.get(),
                        MALIGNANT_PEWTER_NUGGET.get(), BLOCK_OF_MALIGNANT_PEWTER.get())
                // Equipment
                .add(MALIGNANT_STRONGHOLD_HELMET.get(), MALIGNANT_STRONGHOLD_CHESTPLATE.get(),
                        MALIGNANT_STRONGHOLD_LEGGINGS.get(), MALIGNANT_STRONGHOLD_BOOTS.get(),
                        WEIGHT_OF_WORLDS.get(), EDGE_OF_DELIVERANCE.get(),
                        EROSION_SCEPTER.get(),
                        MNEMONIC_HEX_STAFF.get(), STAFF_OF_THE_AURIC_FLAME.get())
                // Runes
                .add(RUNE_OF_BOLSTERING.get(), RUNE_OF_SACRIFICIAL_EMPOWERMENT.get(),
                        RUNE_OF_SPELL_MASTERY.get(), RUNE_OF_THE_HERETIC.get(),
                        RUNE_OF_UNNATURAL_STAMINA.get(), RUNE_OF_TWINNED_DURATION.get(),
                        RUNE_OF_TOUGHNESS.get(), RUNE_OF_IGNEOUS_SOLACE.get())
                // Trinkets
                .add(RING_OF_THE_ENDLESS_WELL.get(), RING_OF_GROWING_FLESH.get(), RING_OF_ECHOING_ARCANA.get(),
                        RING_OF_GRUESOME_CONCENTRATION.get(), NECKLACE_OF_THE_HIDDEN_BLADE.get(),
                        NECKLACE_OF_THE_WATCHER.get(), BELT_OF_THE_LIMITLESS.get())
                // Augments
                .add(STELLAR_MECHANISM.get())
                // Aesthetica
                .add(AESTHETICA.get());

        for (DeferredHolder<Item, ? extends Item> i : items) {
            if (i.get() instanceof MalumCurioItem) {
                final Item item = i.get();
                final ResourceLocation id = i.getId();
                if (id.getPath().contains("_ring") || id.getPath().contains("ring_")) {
                    getOrCreateTagBuilder(ItemTagRegistry.RING).add(item);
                    continue;
                }
                if (id.getPath().contains("_necklace") || id.getPath().contains("necklace_")) {
                    getOrCreateTagBuilder(ItemTagRegistry.NECKLACE).add(item);
                    continue;
                }
                if (id.getPath().contains("_belt") || id.getPath().contains("belt_")) {
                    getOrCreateTagBuilder(ItemTagRegistry.BELT).add(item);
                    continue;
                }
                if (id.getPath().contains("_rune") || id.getPath().contains("rune_")) {
                    getOrCreateTagBuilder(ItemTagRegistry.RUNE).add(item);
                    continue;
                }
                if (id.getPath().contains("_brooch") || id.getPath().contains("brooch_")) {
                    getOrCreateTagBuilder(ItemTagRegistry.BROOCH).add(item);
                }
            }
        }
        getOrCreateTagBuilder(ItemTagRegistry.CHARM).add(TOPHAT.get(), TOKEN_OF_GRATITUDE.get());
    }


    public void safeCopy(TagKey<Item> itemTag) {
        safeCopy(BlockRegistry.BLOCKS, TagKey.create(BuiltInRegistries.BLOCK.key(), itemTag.location()), itemTag);
    }

    public void safeCopy(TagKey<Block> blockTag, TagKey<Item> itemTag) {
        safeCopy(BlockRegistry.BLOCKS, blockTag, itemTag);
    }

    public void safeCopy(DeferredRegister<Block> blocks, TagKey<Block> blockTag, TagKey<Item> itemTag) {
        for (DeferredHolder<Block, ? extends Block> object : blocks.getEntries()) {
            final Block block = object.get();
            if (block.properties() instanceof LodestoneBlockProperties lodestoneBlockProperties) {
                final LodestoneDatagenBlockData datagenData = lodestoneBlockProperties.getDatagenData();
                if (datagenData.getTags().contains(blockTag)) {
                    final Item item = block.asItem();
                    if (!item.equals(Items.AIR)) {
                        getOrCreateTagBuilder(itemTag).add(item);
                    }
                }
            }
        }
    }
}