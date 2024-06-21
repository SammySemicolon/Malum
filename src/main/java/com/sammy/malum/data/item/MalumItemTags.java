package com.sammy.malum.data.item;

import com.sammy.malum.common.item.curiosities.trinkets.MalumTinketsItem;
import com.sammy.malum.common.item.impetus.NodeItem;
import com.sammy.malum.registry.common.block.BlockTagRegistry;
import com.sammy.malum.registry.common.item.ItemRegistry;
import com.sammy.malum.registry.common.item.ItemTagRegistry;
import io.github.fabricators_of_create.porting_lib.tags.Tags;
import io.github.fabricators_of_create.porting_lib.util.RegistryObject;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

import static com.sammy.malum.registry.common.item.ItemRegistry.*;
import static com.sammy.malum.registry.common.item.ItemTagRegistry.*;
import static team.lodestar.lodestone.registry.common.tag.LodestoneItemTags.NUGGETS_COPPER;

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

        getOrCreateTagBuilder(Tags.Items.GEMS).add(PROCESSED_SOULSTONE.get(), BLAZING_QUARTZ.get());
        getOrCreateTagBuilder(ItemTags.LOGS).addTag(RUNEWOOD_LOGS).addTag(SOULWOOD_LOGS);
        getOrCreateTagBuilder(ItemTags.LOGS_THAT_BURN).addTag(RUNEWOOD_LOGS).addTag(SOULWOOD_LOGS);
        getOrCreateTagBuilder(Tags.Items.SLIMEBALLS).add(RUNIC_SAPBALL.get(), CURSED_SAPBALL.get());
        getOrCreateTagBuilder(Tags.Items.GEMS_QUARTZ).add(NATURAL_QUARTZ.get());
        getOrCreateTagBuilder(Tags.Items.ORES_QUARTZ).add(NATURAL_QUARTZ_ORE.get(), DEEPSLATE_QUARTZ_ORE.get());
        getOrCreateTagBuilder(ItemTags.MUSIC_DISCS).add(AESTHETICA.get());
        tag(Tags.Items.GEMS).add(PROCESSED_SOULSTONE.get(), BLAZING_QUARTZ.get());
        tag(ItemTags.LOGS).addTag(ItemTagRegistry.RUNEWOOD_LOGS).addTag(ItemTagRegistry.SOULWOOD_LOGS);
        tag(ItemTags.LOGS_THAT_BURN).addTag(ItemTagRegistry.RUNEWOOD_LOGS).addTag(ItemTagRegistry.SOULWOOD_LOGS);
        tag(Tags.Items.SLIMEBALLS).add(RUNIC_SAPBALL.get(), CURSED_SAPBALL.get());
        tag(Tags.Items.GEMS_QUARTZ).add(NATURAL_QUARTZ.get());
        tag(Tags.Items.ORES_QUARTZ).add(NATURAL_QUARTZ_ORE.get(), DEEPSLATE_QUARTZ_ORE.get());
        tag(ItemTags.MUSIC_DISCS).add(AESTHETICA.get());

        getOrCreateTagBuilder(ItemTagRegistry.SAPBALLS).add(RUNIC_SAPBALL.get(), CURSED_SAPBALL.get());
        getOrCreateTagBuilder(GROSS_FOODS).add(Items.ROTTEN_FLESH, ROTTING_ESSENCE.get());
        tag(ItemTagRegistry.SAPBALLS).add(RUNIC_SAPBALL.get(), CURSED_SAPBALL.get());
        tag(ItemTagRegistry.GROSS_FOODS).add(Items.ROTTEN_FLESH, ROTTING_ESSENCE.get());

        ITEMS.getEntries().stream().filter(i -> i.get() instanceof NodeItem).map(RegistryObject::get).forEach(i -> {
            getOrCreateTagBuilder(METAL_NODES).add(i);
            tag(ItemTagRegistry.METAL_NODES).add(i);
        });
        getOrCreateTagBuilder(PROSPECTORS_TREASURE).addTags(Tags.Items.ORES, Tags.Items.STORAGE_BLOCKS, Tags.Items.INGOTS, Tags.Items.NUGGETS, Tags.Items.GEMS, Tags.Items.RAW_MATERIALS, ItemTags.COALS, METAL_NODES);
        getOrCreateTagBuilder(PROSPECTORS_TREASURE).addOptional(new ResourceLocation("tetra", "geode"));
        tag(ItemTagRegistry.PROSPECTORS_TREASURE)
                .addTags(Tags.Items.ORES, Tags.Items.STORAGE_BLOCKS, Tags.Items.INGOTS, Tags.Items.NUGGETS, Tags.Items.GEMS, Tags.Items.RAW_MATERIALS, ItemTags.COALS, ItemTagRegistry.METAL_NODES)
                .addOptional(new ResourceLocation("tetra", "geode"));

        getOrCreateTagBuilder(RUNEWOOD_LOGS).add(RUNEWOOD_LOG.get(), STRIPPED_RUNEWOOD_LOG.get(), RUNEWOOD.get(), STRIPPED_RUNEWOOD.get(), EXPOSED_RUNEWOOD_LOG.get(), REVEALED_RUNEWOOD_LOG.get());
        getOrCreateTagBuilder(RUNEWOOD_BOARD_INGREDIENT).add(RUNEWOOD_LOG.get(), RUNEWOOD.get());
        getOrCreateTagBuilder(ItemTagRegistry.RUNEWOOD_PLANKS).add(
        tag(ItemTagRegistry.RUNEWOOD_LOGS).add(RUNEWOOD_LOG.get(), STRIPPED_RUNEWOOD_LOG.get(), RUNEWOOD.get(), STRIPPED_RUNEWOOD.get(), EXPOSED_RUNEWOOD_LOG.get(), REVEALED_RUNEWOOD_LOG.get());
        tag(ItemTagRegistry.RUNEWOOD_BOARD_INGREDIENT).add(RUNEWOOD_LOG.get(), RUNEWOOD.get());
        tag(ItemTagRegistry.RUNEWOOD_PLANKS).add(
                RUNEWOOD_BOARDS.get(), VERTICAL_RUNEWOOD_BOARDS.get(),
                ItemRegistry.RUNEWOOD_PLANKS.get(), RUSTIC_RUNEWOOD_PLANKS.get(), VERTICAL_RUNEWOOD_PLANKS.get(),
                VERTICAL_RUSTIC_RUNEWOOD_PLANKS.get(), RUNEWOOD_TILES.get(), RUSTIC_RUNEWOOD_TILES.get());
        getOrCreateTagBuilder(RUNEWOOD_SLABS).add(
                RUNEWOOD_PLANKS.get(), RUSTIC_RUNEWOOD_PLANKS.get(), VERTICAL_RUNEWOOD_PLANKS.get(),
                VERTICAL_RUSTIC_RUNEWOOD_PLANKS.get(), RUNEWOOD_TILES.get(), RUSTIC_RUNEWOOD_TILES.get()
        );
        tag(ItemTagRegistry.RUNEWOOD_SLABS).add(
                RUNEWOOD_BOARDS_SLAB.get(), VERTICAL_RUNEWOOD_BOARDS_SLAB.get(),
                RUNEWOOD_PLANKS_SLAB.get(), RUSTIC_RUNEWOOD_PLANKS_SLAB.get(), VERTICAL_RUNEWOOD_PLANKS_SLAB.get(),
                VERTICAL_RUSTIC_RUNEWOOD_PLANKS_SLAB.get(), RUNEWOOD_TILES_SLAB.get(), RUSTIC_RUNEWOOD_TILES_SLAB.get());
        getOrCreateTagBuilder(RUNEWOOD_STAIRS).add(
                VERTICAL_RUSTIC_RUNEWOOD_PLANKS_SLAB.get(), RUNEWOOD_TILES_SLAB.get(), RUSTIC_RUNEWOOD_TILES_SLAB.get()
        );
        tag(ItemTagRegistry.RUNEWOOD_STAIRS).add(
                RUNEWOOD_BOARDS_STAIRS.get(), VERTICAL_RUNEWOOD_BOARDS_STAIRS.get(),
                RUNEWOOD_PLANKS_STAIRS.get(), RUSTIC_RUNEWOOD_PLANKS_STAIRS.get(), VERTICAL_RUNEWOOD_PLANKS_STAIRS.get(),
                VERTICAL_RUSTIC_RUNEWOOD_PLANKS_STAIRS.get(), RUNEWOOD_TILES_STAIRS.get(), RUSTIC_RUNEWOOD_TILES_STAIRS.get());


        getOrCreateTagBuilder(SOULWOOD_LOGS).add(SOULWOOD_LOG.get(), STRIPPED_SOULWOOD_LOG.get(), SOULWOOD.get(), STRIPPED_SOULWOOD.get(), EXPOSED_SOULWOOD_LOG.get(), REVEALED_SOULWOOD_LOG.get(), BLIGHTED_SOULWOOD.get());
        getOrCreateTagBuilder(SOULWOOD_BOARD_INGREDIENT).add(SOULWOOD_LOG.get(), SOULWOOD.get());
        getOrCreateTagBuilder(ItemTagRegistry.SOULWOOD_PLANKS).add(
        tag(ItemTagRegistry.SOULWOOD_LOGS).add(SOULWOOD_LOG.get(), STRIPPED_SOULWOOD_LOG.get(), SOULWOOD.get(), STRIPPED_SOULWOOD.get(), EXPOSED_SOULWOOD_LOG.get(), REVEALED_SOULWOOD_LOG.get(), BLIGHTED_SOULWOOD.get());
        tag(ItemTagRegistry.SOULWOOD_BOARD_INGREDIENT).add(SOULWOOD_LOG.get(), SOULWOOD.get());
        tag(ItemTagRegistry.SOULWOOD_PLANKS).add(
                SOULWOOD_BOARDS.get(), VERTICAL_SOULWOOD_BOARDS.get(),
                ItemRegistry.SOULWOOD_PLANKS.get(), RUSTIC_SOULWOOD_PLANKS.get(), VERTICAL_SOULWOOD_PLANKS.get(),
                VERTICAL_RUSTIC_SOULWOOD_PLANKS.get(), SOULWOOD_TILES.get(), RUSTIC_SOULWOOD_TILES.get());
        getOrCreateTagBuilder(SOULWOOD_SLABS).add(
                SOULWOOD_PLANKS.get(), RUSTIC_SOULWOOD_PLANKS.get(), VERTICAL_SOULWOOD_PLANKS.get(),
                VERTICAL_RUSTIC_SOULWOOD_PLANKS.get(), SOULWOOD_TILES.get(), RUSTIC_SOULWOOD_TILES.get()
        );
        tag(ItemTagRegistry.SOULWOOD_SLABS).add(
                SOULWOOD_BOARDS_SLAB.get(), VERTICAL_SOULWOOD_BOARDS_SLAB.get(),
                SOULWOOD_PLANKS_SLAB.get(), RUSTIC_SOULWOOD_PLANKS_SLAB.get(), VERTICAL_SOULWOOD_PLANKS_SLAB.get(),
                VERTICAL_RUSTIC_SOULWOOD_PLANKS_SLAB.get(), SOULWOOD_TILES_SLAB.get(), RUSTIC_SOULWOOD_TILES_SLAB.get());
        getOrCreateTagBuilder(SOULWOOD_STAIRS).add(
                VERTICAL_RUSTIC_SOULWOOD_PLANKS_SLAB.get(), SOULWOOD_TILES_SLAB.get(), RUSTIC_SOULWOOD_TILES_SLAB.get()
        );
        tag(ItemTagRegistry.SOULWOOD_STAIRS).add(
                SOULWOOD_BOARDS_STAIRS.get(), VERTICAL_SOULWOOD_BOARDS_STAIRS.get(),
                SOULWOOD_PLANKS_STAIRS.get(), RUSTIC_SOULWOOD_PLANKS_STAIRS.get(), VERTICAL_SOULWOOD_PLANKS_STAIRS.get(),
                VERTICAL_RUSTIC_SOULWOOD_PLANKS_STAIRS.get(), SOULWOOD_TILES_STAIRS.get(), RUSTIC_SOULWOOD_TILES_STAIRS.get()
        );

        safeCopy(BlockTagRegistry.TAINTED_ROCK, ItemTagRegistry.TAINTED_ROCK);
        safeCopy(BlockTagRegistry.TAINTED_BLOCKS, ItemTagRegistry.TAINTED_BLOCKS);
        safeCopy(BlockTagRegistry.TAINTED_SLABS, ItemTagRegistry.TAINTED_SLABS);
        safeCopy(BlockTagRegistry.TAINTED_STAIRS, ItemTagRegistry.TAINTED_STAIRS);
        safeCopy(BlockTagRegistry.TAINTED_WALLS, ItemTagRegistry.TAINTED_WALLS);
        safeCopy(BlockTagRegistry.TAINTED_BLOCKS, ItemTagRegistry.TAINTED_BLOCKS);

        getOrCreateTagBuilder(SCYTHE).add(CRUDE_SCYTHE.get(), SOUL_STAINED_STEEL_SCYTHE.get(), CREATIVE_SCYTHE.get());
        getOrCreateTagBuilder(STAFF).add(MNEMONIC_HEX_STAFF.get(), STAFF_OF_THE_AURIC_FLAME.get(), EROSION_SCEPTER.get());
        safeCopy(BlockTagRegistry.TWISTED_ROCK, ItemTagRegistry.TWISTED_ROCK);
        safeCopy(BlockTagRegistry.TWISTED_BLOCKS, ItemTagRegistry.TWISTED_BLOCKS);
        safeCopy(BlockTagRegistry.TWISTED_SLABS, ItemTagRegistry.TWISTED_SLABS);
        safeCopy(BlockTagRegistry.TWISTED_STAIRS, ItemTagRegistry.TWISTED_STAIRS);
        safeCopy(BlockTagRegistry.TWISTED_WALLS, ItemTagRegistry.TWISTED_WALLS);
        safeCopy(BlockTagRegistry.TWISTED_BLOCKS, ItemTagRegistry.TWISTED_BLOCKS);

        getOrCreateTagBuilder(SOUL_HUNTER_WEAPON).add(MNEMONIC_HEX_STAFF.get(), STAFF_OF_THE_AURIC_FLAME.get(), EROSION_SCEPTER.get());
        getOrCreateTagBuilder(SOUL_HUNTER_WEAPON).add(TYRVING.get(), CRUDE_SCYTHE.get(), SOUL_STAINED_STEEL_SCYTHE.get(), CREATIVE_SCYTHE.get(), WEIGHT_OF_WORLDS.get());
        getOrCreateTagBuilder(SOUL_HUNTER_WEAPON).add(SOUL_STAINED_STEEL_AXE.get(), SOUL_STAINED_STEEL_PICKAXE.get(), SOUL_STAINED_STEEL_SHOVEL.get(), SOUL_STAINED_STEEL_SWORD.get(), SOUL_STAINED_STEEL_HOE.get(), SOUL_STAINED_STEEL_KNIFE.get());
        tag(ItemTagRegistry.SCYTHE).add(CRUDE_SCYTHE.get(), SOUL_STAINED_STEEL_SCYTHE.get(), CREATIVE_SCYTHE.get());
        tag(ItemTagRegistry.STAFF).add(MNEMONIC_HEX_STAFF.get(), STAFF_OF_THE_AURIC_FLAME.get(), EROSION_SCEPTER.get());

        tag(ItemTagRegistry.SOUL_HUNTER_WEAPON)
                //staves
                .add(MNEMONIC_HEX_STAFF.get(), STAFF_OF_THE_AURIC_FLAME.get(), EROSION_SCEPTER.get())
                //unique weapons
                .add(TYRVING.get(), CRUDE_SCYTHE.get(), SOUL_STAINED_STEEL_SCYTHE.get(), CREATIVE_SCYTHE.get(), WEIGHT_OF_WORLDS.get())
                //soul stained steel gear
                .add(SOUL_STAINED_STEEL_AXE.get(), SOUL_STAINED_STEEL_PICKAXE.get(), SOUL_STAINED_STEEL_SHOVEL.get(), SOUL_STAINED_STEEL_SWORD.get(), SOUL_STAINED_STEEL_HOE.get(), SOUL_STAINED_STEEL_KNIFE.get());

        getOrCreateTagBuilder(Tags.Items.NUGGETS).add(COPPER_NUGGET.get(), HALLOWED_GOLD_NUGGET.get(), SOUL_STAINED_STEEL_NUGGET.get());
        getOrCreateTagBuilder(Tags.Items.GEMS).add(NATURAL_QUARTZ.get(), BLAZING_QUARTZ.get(), CLUSTER_OF_BRILLIANCE.get());
        getOrCreateTagBuilder(Tags.Items.INGOTS).add(SOUL_STAINED_STEEL_INGOT.get(), HALLOWED_GOLD_INGOT.get());

        getOrCreateTagBuilder(KNIVES).add(SOUL_STAINED_STEEL_KNIFE.get());
        getOrCreateTagBuilder(KNIVES_FD).add(SOUL_STAINED_STEEL_KNIFE.get());
        tag(ItemTagRegistry.KNIVES).add(SOUL_STAINED_STEEL_KNIFE.get());
        tag(ItemTagRegistry.KNIVES_FD).add(SOUL_STAINED_STEEL_KNIFE.get());

        getOrCreateTagBuilder(NUGGETS_COPPER).add(COPPER_NUGGET.get());

        getOrCreateTagBuilder(HIDDEN_ALWAYS).add(THE_DEVICE.get(), THE_VESSEL.get());
        tag(ItemTagRegistry.HIDDEN_ALWAYS).add(THE_DEVICE.get(), THE_VESSEL.get());

        getOrCreateTagBuilder(HIDDEN_UNTIL_VOID)
                .addTag(HIDDEN_UNTIL_BLACK_CRYSTAL)
        tag(ItemTagRegistry.HIDDEN_UNTIL_VOID)
                .addTag(ItemTagRegistry.HIDDEN_UNTIL_BLACK_CRYSTAL)
                // The Well
                .add(PRIMORDIAL_SOUP.get())
                // Encyclopedia
                .add(ENCYCLOPEDIA_ESOTERICA.get())
                // Materials
                .add(BLOCK_OF_NULL_SLATE.get(), NULL_SLATE.get(),
                        BLOCK_OF_VOID_SALTS.get(), VOID_SALTS.get(),
                        BLOCK_OF_MNEMONIC_FRAGMENT.get(), MNEMONIC_FRAGMENT.get(),
                        BLOCK_OF_AURIC_EMBERS.get(), AURIC_EMBERS.get(),
                        BLOCK_OF_MALIGNANT_LEAD.get(), MALIGNANT_LEAD.get());

        getOrCreateTagBuilder(HIDDEN_UNTIL_BLACK_CRYSTAL)
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
                        WEIGHT_OF_WORLDS.get(), EROSION_SCEPTER.get(),
                        MNEMONIC_HEX_STAFF.get(), STAFF_OF_THE_AURIC_FLAME.get())
                // Runes
                .add(VOID_TABLET.get(),
                        RUNE_OF_BOLSTERING.get(), RUNE_OF_SACRIFICIAL_EMPOWERMENT.get(),
                        RUNE_OF_SPELL_MASTERY.get(), RUNE_OF_THE_HERETIC.get(),
                        RUNE_OF_UNNATURAL_STAMINA.get(), RUNE_OF_TWINNED_DURATION.get(),
                        RUNE_OF_TOUGHNESS.get(), RUNE_OF_IGNEOUS_SOLACE.get())
                // Trinkets
                .add(RING_OF_THE_ENDLESS_WELL.get(), RING_OF_GROWING_FLESH.get(),
                        RING_OF_GRUESOME_CONCENTRATION.get(), NECKLACE_OF_THE_HIDDEN_BLADE.get(),
                        NECKLACE_OF_THE_WATCHER.get(), BELT_OF_THE_LIMITLESS.get())
                // Augments
                .add(STELLAR_MECHANISM.get());

        for (RegistryObject<Item> i : ITEMS.getEntries()) {
            if (i.get() instanceof MalumTinketsItem) {
                final Item item = i.get();
                final ResourceLocation id = i.getId();
                if (id.getPath().contains("_ring") || id.getPath().contains("ring_")) {
                    getOrCreateTagBuilder(RING).add(item);
                    continue;
                }
                if (id.getPath().contains("_necklace") || id.getPath().contains("necklace_")) {
                    getOrCreateTagBuilder(NECKLACE).add(item);
                    continue;
                }
                if (id.getPath().contains("_belt") || id.getPath().contains("belt_")) {
                    getOrCreateTagBuilder(BELT).add(item);
                    continue;
                }
                if (id.getPath().contains("_rune") || id.getPath().contains("rune_")) {
                    getOrCreateTagBuilder(RUNE).add(item);
                    continue;
                }
                if (id.getPath().contains("_brooch") || id.getPath().contains("brooch_")) {
                    getOrCreateTagBuilder(BROOCH).add(item);
                }
            }
        }
        getOrCreateTagBuilder(CHARM).add(TOPHAT.get(), TOKEN_OF_GRATITUDE.get());
        tag(ItemTagRegistry.CHARM).add(TOPHAT.get(), TOKEN_OF_GRATITUDE.get());
    }

    public void safeCopy(TagKey<Block> blockTag, TagKey<Item> itemTag) {
        safeCopy(BlockRegistry.BLOCKS, blockTag, itemTag);
    }

    public void safeCopy(DeferredRegister<Block> blocks, TagKey<Block> blockTag, TagKey<Item> itemTag) {
        for (RegistryObject<Block> object : blocks.getEntries()) {
            final Block block = object.get();
            if (block.properties instanceof LodestoneBlockProperties lodestoneBlockProperties) {
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