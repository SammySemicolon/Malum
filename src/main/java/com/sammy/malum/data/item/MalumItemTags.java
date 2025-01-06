package com.sammy.malum.data.item;

import com.sammy.malum.*;
import com.sammy.malum.common.item.*;
import com.sammy.malum.common.item.augment.*;
import com.sammy.malum.common.item.curiosities.curios.*;
import com.sammy.malum.common.item.impetus.*;
import com.sammy.malum.data.recipe.crafting.*;
import com.sammy.malum.registry.common.block.*;
import com.sammy.malum.registry.common.item.*;
import net.minecraft.core.*;
import net.minecraft.core.registries.*;
import net.minecraft.data.*;
import net.minecraft.data.tags.*;
import net.minecraft.resources.*;
import net.minecraft.tags.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.*;
import net.neoforged.neoforge.common.*;
import net.neoforged.neoforge.common.data.*;
import net.neoforged.neoforge.registries.*;
import org.jetbrains.annotations.*;
import team.lodestar.lodestone.systems.block.*;
import team.lodestar.lodestone.systems.datagen.*;

import java.util.concurrent.*;

import static com.sammy.malum.registry.common.item.ItemRegistry.*;
import static team.lodestar.lodestone.registry.common.tag.LodestoneItemTags.*;

@SuppressWarnings("unchecked")
public class MalumItemTags extends ItemTagsProvider {

    public MalumItemTags(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider, CompletableFuture<TagLookup<Block>> pBlockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, pLookupProvider, pBlockTags, MalumMod.MALUM, existingFileHelper);
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

        tag(Tags.Items.GEMS).add(REFINED_SOULSTONE.get(), BLAZING_QUARTZ.get());
        tag(ItemTags.LOGS).addTag(ItemTagRegistry.RUNEWOOD_LOGS).addTag(ItemTagRegistry.SOULWOOD_LOGS);
        tag(ItemTags.LOGS_THAT_BURN).addTag(ItemTagRegistry.RUNEWOOD_LOGS).addTag(ItemTagRegistry.SOULWOOD_LOGS);
        tag(Tags.Items.SLIMEBALLS).add(RUNIC_SAPBALL.get(), CURSED_SAPBALL.get());
        tag(Tags.Items.GEMS_QUARTZ).add(NATURAL_QUARTZ.get());
        tag(Tags.Items.ORES_QUARTZ).add(NATURAL_QUARTZ_ORE.get(), DEEPSLATE_QUARTZ_ORE.get());

        tag(ItemTagRegistry.MAGIC_CAPABLE_WEAPONS).add(
                CRUDE_SCYTHE.get(), SOUL_STAINED_STEEL_SCYTHE.get(), CREATIVE_SCYTHE.get(),
                MNEMONIC_HEX_STAFF.get(), STAFF_OF_THE_AURIC_FLAME.get(), EROSION_SCEPTER.get());

        tag(ItemTagRegistry.SCYTHES).add(CRUDE_SCYTHE.get(), SOUL_STAINED_STEEL_SCYTHE.get(), EDGE_OF_DELIVERANCE.get(), CREATIVE_SCYTHE.get());
        tag(ItemTagRegistry.STAVES).add(MNEMONIC_HEX_STAFF.get(), STAFF_OF_THE_AURIC_FLAME.get(), EROSION_SCEPTER.get());

        tag(ItemTagRegistry.SOUL_SHATTER_CAPABLE_WEAPONS)
                .addTags(ItemTagRegistry.SCYTHES, ItemTagRegistry.STAVES)
                .add(TYRVING.get(), WEIGHT_OF_WORLDS.get())
                .add(SOUL_STAINED_STEEL_AXE.get(), SOUL_STAINED_STEEL_PICKAXE.get(), SOUL_STAINED_STEEL_SHOVEL.get(), SOUL_STAINED_STEEL_SWORD.get(), SOUL_STAINED_STEEL_HOE.get(), SOUL_STAINED_STEEL_KNIFE.get());

        tag(ItemTagRegistry.SOUL_SHATTER_CAPABLE_WEAPONS)
                .addOptional(ResourceLocation.parse("irons_spellbooks:graybeard_staff"))
                .addOptional(ResourceLocation.parse("irons_spellbooks:artificer_cane"))
                .addOptional(ResourceLocation.parse("irons_spellbooks:lightning_rod"))
                .addOptional(ResourceLocation.parse("irons_spellbooks:ice_staff"))
                .addOptional(ResourceLocation.parse("irons_spellbooks:blood_staff"))
                .addOptional(ResourceLocation.parse("irons_spellbooks:magehunter"))
                .addOptional(ResourceLocation.parse("irons_spellbooks:keeper_flamberge"))
                .addOptional(ResourceLocation.parse("irons_spellbooks:spellbreaker"))
                .addOptional(ResourceLocation.parse("irons_spellbooks:amethyst_rapier"));

        tag(ItemTagRegistry.ANIMATED_ENCHANTABLE).addTag(ItemTagRegistry.SCYTHES);
        tag(ItemTagRegistry.REBOUND_ENCHANTABLE).addTag(ItemTagRegistry.SCYTHES);
        tag(ItemTagRegistry.ASCENSION_ENCHANTABLE).addTag(ItemTagRegistry.SCYTHES);
        tag(ItemTagRegistry.REPLENISHING_ENCHANTABLE).addTag(ItemTagRegistry.STAVES);
        tag(ItemTagRegistry.HAUNTED_ENCHANTABLE).addTag(ItemTagRegistry.MAGIC_CAPABLE_WEAPONS);
        tag(ItemTagRegistry.SPIRIT_SPOILS_ENCHANTABLE).addTag(ItemTagRegistry.SOUL_SHATTER_CAPABLE_WEAPONS);

        tag(ItemTagRegistry.ASPECTED_SPIRITS).add(
                SACRED_SPIRIT.get(), WICKED_SPIRIT.get(), ARCANE_SPIRIT.get(), ELDRITCH_SPIRIT.get(),
                AERIAL_SPIRIT.get(), AQUEOUS_SPIRIT.get(), EARTHEN_SPIRIT.get(), INFERNAL_SPIRIT.get());
        tag(ItemTagRegistry.SPIRITS).addTag(ItemTagRegistry.ASPECTED_SPIRITS).add(UMBRAL_SPIRIT.get());
        tag(ItemTagRegistry.MOB_DROPS).add(
                ROTTING_ESSENCE.get(), GRIM_TALC.get(), ASTRAL_WEAVE.get(), WARP_FLUX.get(),
                Items.ROTTEN_FLESH, Items.SPIDER_EYE, Items.BONE, Items.GUNPOWDER, Items.STRING, Items.SLIME_BALL,
                Items.MAGMA_CREAM, Items.BLAZE_ROD, Items.BREEZE_ROD,
                Items.LEATHER, Items.RABBIT_HIDE, Items.FEATHER, Items.INK_SAC);
        tag(ItemTagRegistry.MATERIALS).add(
                ROTTING_ESSENCE.get(), GRIM_TALC.get(), ASTRAL_WEAVE.get(), WARP_FLUX.get(),
                HEX_ASH.get(),LIVING_FLESH.get(), ALCHEMICAL_CALX.get(), BLIGHTED_GUNK.get(),
                SOULWOVEN_SILK.get(), ETHER.get(), IRIDESCENT_ETHER.get(),
                SOUL_STAINED_STEEL_INGOT.get(), SOUL_STAINED_STEEL_NUGGET.get(), SOUL_STAINED_STEEL_PLATING.get(),
                HALLOWED_GOLD_INGOT.get(), HALLOWED_GOLD_NUGGET.get(),
                MALIGNANT_PEWTER_INGOT.get(), MALIGNANT_PEWTER_NUGGET.get(), MALIGNANT_PEWTER_PLATING.get(),
                NULL_SLATE.get(), VOID_SALTS.get(), MNEMONIC_FRAGMENT.get(), AURIC_EMBERS.get(), MALIGNANT_LEAD.get(),
                ANOMALOUS_DESIGN.get(), COMPLETE_DESIGN.get(), FUSED_CONSCIOUSNESS.get());
        tag(ItemTagRegistry.MINERALS).add(
                RAW_SOULSTONE.get(), CRUSHED_SOULSTONE.get(), REFINED_SOULSTONE.get(),
                RAW_BRILLIANCE.get(), CRUSHED_BRILLIANCE.get(), REFINED_BRILLIANCE.get(),
                BLAZING_QUARTZ.get(), ARCANE_CHARCOAL.get(),
                NATURAL_QUARTZ.get(), CTHONIC_GOLD.get(), CTHONIC_GOLD_FRAGMENT.get());

        tag(ItemTagRegistry.AUGMENTS).addAll(items.stream().filter(i -> i.get() instanceof AugmentItem).map(DeferredHolder::getKey).toList());
        tag(ItemTagRegistry.METAL_NODES).addAll(items.stream().filter(i -> i.get() instanceof NodeItem).map(DeferredHolder::getKey).toList());
        tag(ItemTagRegistry.SOULWOVEN_BANNERS).addAll(items.stream().filter(i -> i.get() instanceof SoulwovenBannerBlockItem).map(DeferredHolder::getKey).toList());

        tag(ItemTagRegistry.IS_TOTEMIC_TOOL).add(TOTEMIC_STAFF.get());
        tag(ItemTagRegistry.IS_REDSTONE_TOOL).add(ARTIFICERS_CLAW.get()).addOptional(ResourceLocation.parse("create:wrench"));
        tag(ItemTagRegistry.IS_ARTIFICE_TOOL).add(TUNING_FORK.get()).addOptional(ResourceLocation.parse("create:wrench"));

        tag(ItemTagRegistry.GROSS_FOODS).add(Items.ROTTEN_FLESH, ROTTING_ESSENCE.get(), CONCENTRATED_GLUTTONY.get());

        tag(ItemTagRegistry.PROSPECTORS_TREASURE)
                .addTags(Tags.Items.ORES, Tags.Items.STORAGE_BLOCKS, Tags.Items.INGOTS, Tags.Items.NUGGETS, Tags.Items.GEMS, Tags.Items.RAW_MATERIALS, ItemTags.COALS, ItemTagRegistry.METAL_NODES)
                .addOptional(ResourceLocation.parse("tetra:geode"));

        tag(ItemTagRegistry.SOULHUNTERS_TREASURE)
                .addTags(ItemTagRegistry.SOUL_SHATTER_CAPABLE_WEAPONS, ItemTagRegistry.SPIRITS, ItemTagRegistry.MOB_DROPS, ItemTagRegistry.MATERIALS, ItemTagRegistry.MINERALS)
                .addTags(ItemTagRegistry.AUGMENTS, ItemTagRegistry.METAL_NODES, ItemTagRegistry.SOULWOVEN_BANNERS)
                .addTags(ItemTagRegistry.RING, ItemTagRegistry.NECKLACE, ItemTagRegistry.BELT, ItemTagRegistry.BROOCH, ItemTagRegistry.RUNE)
                .add(TUNING_FORK.get(), LAMPLIGHTERS_TONGS.get(), CATALYST_LOBBER.get())
                .add(ENCYCLOPEDIA_ARCANA.get(), ENCYCLOPEDIA_ESOTERICA.get());
        tag(ItemTagRegistry.SOULWOVEN_POUCH_AUTOCOLLECT)
                .addTags(ItemTagRegistry.SPIRITS, ItemTagRegistry.MOB_DROPS, ItemTagRegistry.MATERIALS, ItemTagRegistry.MINERALS);

        tag(Tags.Items.NUGGETS).add(COPPER_NUGGET.get(), HALLOWED_GOLD_NUGGET.get(), SOUL_STAINED_STEEL_NUGGET.get());
        tag(Tags.Items.GEMS).add(NATURAL_QUARTZ.get(), BLAZING_QUARTZ.get(), RAW_BRILLIANCE.get());
        tag(Tags.Items.INGOTS).add(SOUL_STAINED_STEEL_INGOT.get(), HALLOWED_GOLD_INGOT.get());

        tag(ItemTagRegistry.KNIVES).add(SOUL_STAINED_STEEL_KNIFE.get());
        tag(ItemTagRegistry.KNIVES_FD).add(SOUL_STAINED_STEEL_KNIFE.get());

        tag(NUGGETS_COPPER).add(COPPER_NUGGET.get());

        tag(ItemTagRegistry.HIDDEN_ALWAYS).add(THE_DEVICE.get(), THE_VESSEL.get());

        tag(ItemTagRegistry.HIDDEN_UNTIL_VOID)
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

        tag(ItemTagRegistry.HIDDEN_UNTIL_BLACK_CRYSTAL)
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
                    tag(ItemTagRegistry.RING).add(item);
                    continue;
                }
                if (id.getPath().contains("_necklace") || id.getPath().contains("necklace_")) {
                    tag(ItemTagRegistry.NECKLACE).add(item);
                    continue;
                }
                if (id.getPath().contains("_belt") || id.getPath().contains("belt_")) {
                    tag(ItemTagRegistry.BELT).add(item);
                    continue;
                }
                if (id.getPath().contains("_rune") || id.getPath().contains("rune_")) {
                    tag(ItemTagRegistry.RUNE).add(item);
                    continue;
                }
                if (id.getPath().contains("_brooch") || id.getPath().contains("brooch_")) {
                    tag(ItemTagRegistry.BROOCH).add(item);
                }
            }
        }
        tag(ItemTagRegistry.CHARM).add(TOPHAT.get(), TOKEN_OF_GRATITUDE.get());
    }

    @Override
    public IntrinsicTagAppender<Item> tag(TagKey<Item> pTag) {
        return super.tag(pTag);
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
                        tag(itemTag).add(item);
                    }
                }
            }
        }
    }
}