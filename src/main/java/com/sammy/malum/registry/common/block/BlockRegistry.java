package com.sammy.malum.registry.common.block;

import com.sammy.malum.*;
import com.sammy.malum.common.block.*;
import com.sammy.malum.common.block.blight.*;
import com.sammy.malum.common.block.curiosities.banner.*;
import com.sammy.malum.common.block.curiosities.mana_mote.*;
import com.sammy.malum.common.block.curiosities.obelisk.*;
import com.sammy.malum.common.block.curiosities.obelisk.brilliant.*;
import com.sammy.malum.common.block.curiosities.obelisk.runewood.*;
import com.sammy.malum.common.block.curiosities.redstone.wavemaker.WaveMakerBlock;
import com.sammy.malum.common.block.curiosities.redstone.wavebanker.WaveBankerBlock;
import com.sammy.malum.common.block.curiosities.redstone.wavecharger.WaveChargerBlock;
import com.sammy.malum.common.block.curiosities.redstone.wavebreaker.WaveBreakerBlock;
import com.sammy.malum.common.block.curiosities.repair_pylon.*;
import com.sammy.malum.common.block.curiosities.ritual_plinth.*;
import com.sammy.malum.common.block.curiosities.runic_workbench.*;
import com.sammy.malum.common.block.curiosities.spirit_altar.*;
import com.sammy.malum.common.block.curiosities.spirit_crucible.*;
import com.sammy.malum.common.block.curiosities.spirit_catalyzer.*;
import com.sammy.malum.common.block.curiosities.totem.*;
import com.sammy.malum.common.block.curiosities.void_depot.*;
import com.sammy.malum.common.block.curiosities.weavers_workbench.*;
import com.sammy.malum.common.block.curiosities.weeping_well.*;
import com.sammy.malum.common.block.ether.*;
import com.sammy.malum.common.block.nature.*;
import com.sammy.malum.common.block.nature.soulwood.*;
import com.sammy.malum.common.block.storage.jar.*;
import com.sammy.malum.common.block.storage.pedestal.*;
import com.sammy.malum.common.block.storage.stand.*;
import com.sammy.malum.common.block.the_device.*;
import com.sammy.malum.core.systems.spirit.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.item.*;
import com.sammy.malum.registry.common.worldgen.*;
import net.minecraft.client.color.block.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.*;
import net.minecraft.util.valueproviders.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.properties.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import team.lodestar.lodestone.systems.block.*;
import team.lodestar.lodestone.systems.block.sign.*;
import team.lodestar.lodestone.systems.easing.*;

import java.awt.*;

import static com.sammy.malum.MalumMod.*;
import static com.sammy.malum.registry.common.block.BlockTagRegistry.*;
import static net.minecraft.tags.BlockTags.*;
import static net.neoforged.neoforge.common.Tags.Blocks.FENCE_GATES_WOODEN;
import static net.neoforged.neoforge.common.Tags.Blocks.STORAGE_BLOCKS;


public class BlockRegistry {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(BuiltInRegistries.BLOCK, MALUM);

    //region useful blocks
    public static final DeferredHolder<Block, Block> SPIRIT_ALTAR = BLOCKS.register("spirit_altar", () -> new SpiritAltarBlock<>(MalumBlockProperties.RUNEWOOD().setCutoutRenderType().noOcclusion()).setBlockEntity(BlockEntityRegistry.SPIRIT_ALTAR));
    public static final DeferredHolder<Block, Block> SPIRIT_JAR = BLOCKS.register("spirit_jar", () -> new SpiritJarBlock<>(MalumBlockProperties.SPIRIT_JAR().setCutoutRenderType().noOcclusion()).setBlockEntity(BlockEntityRegistry.SPIRIT_JAR));
    public static final DeferredHolder<Block, Block> RITUAL_PLINTH = BLOCKS.register("ritual_plinth", () -> new RitualPlinthBlock<>(MalumBlockProperties.SOULWOOD().setCutoutRenderType().noOcclusion()).setBlockEntity(BlockEntityRegistry.RITUAL_PLINTH));

    public static final DeferredHolder<Block, Block> WEAVERS_WORKBENCH = BLOCKS.register("weavers_workbench", () -> new WeaversWorkbenchBlock<>(MalumBlockProperties.RUNEWOOD().setCutoutRenderType().noOcclusion()).setBlockEntity(BlockEntityRegistry.WEAVERS_WORKBENCH));
    public static final DeferredHolder<Block, Block> RUNIC_WORKBENCH = BLOCKS.register("runic_workbench", () -> new RunicWorkbenchBlock<>(MalumBlockProperties.RUNEWOOD().setCutoutRenderType().noOcclusion()).setBlockEntity(BlockEntityRegistry.RUNIC_WORKBENCH));

    public static final DeferredHolder<Block, Block> RUNEWOOD_OBELISK = BLOCKS.register("runewood_obelisk", () -> new RunewoodObeliskCoreBlock(MalumBlockProperties.RUNEWOOD().setCutoutRenderType().noOcclusion()));
    public static final DeferredHolder<Block, Block> RUNEWOOD_OBELISK_COMPONENT = BLOCKS.register("runewood_obelisk_component", () -> new ObeliskComponentBlock(MalumBlockProperties.RUNEWOOD().setCutoutRenderType().lootFrom(RUNEWOOD_OBELISK).noOcclusion(), ItemRegistry.RUNEWOOD_OBELISK));

    public static final DeferredHolder<Block, Block> BRILLIANT_OBELISK = BLOCKS.register("brilliant_obelisk", () -> new BrillianceObeliskCoreBlock(MalumBlockProperties.RUNEWOOD().setCutoutRenderType().noOcclusion()));
    public static final DeferredHolder<Block, Block> BRILLIANT_OBELISK_COMPONENT = BLOCKS.register("brilliant_obelisk_component", () -> new ObeliskComponentBlock(MalumBlockProperties.RUNEWOOD().setCutoutRenderType().lootFrom(BRILLIANT_OBELISK).noOcclusion(), ItemRegistry.BRILLIANT_OBELISK));

    public static final DeferredHolder<Block, Block> SPIRIT_CRUCIBLE = BLOCKS.register("spirit_crucible", () -> new SpiritCrucibleCoreBlock<>(MalumBlockProperties.TAINTED_ROCK().setCutoutRenderType().noOcclusion()).setBlockEntity(BlockEntityRegistry.SPIRIT_CRUCIBLE));
    public static final DeferredHolder<Block, Block> SPIRIT_CRUCIBLE_COMPONENT = BLOCKS.register("spirit_crucible_component", () -> new SpiritCrucibleComponentBlock(MalumBlockProperties.TAINTED_ROCK().setCutoutRenderType().lootFrom(SPIRIT_CRUCIBLE).noOcclusion()));

    public static final DeferredHolder<Block, Block> SPIRIT_CATALYZER = BLOCKS.register("spirit_catalyzer", () -> new SpiritCatalyzerCoreBlock<>(MalumBlockProperties.TAINTED_ROCK().setCutoutRenderType().noOcclusion()).setBlockEntity(BlockEntityRegistry.SPIRIT_CATALYZER));
    public static final DeferredHolder<Block, Block> SPIRIT_CATALYZER_COMPONENT = BLOCKS.register("spirit_catalyzer_component", () -> new SpiritCatalyzerComponentBlock(MalumBlockProperties.TAINTED_ROCK().setCutoutRenderType().lootFrom(SPIRIT_CATALYZER).noOcclusion(), ItemRegistry.SPIRIT_CATALYZER));

    public static final DeferredHolder<Block, Block> REPAIR_PYLON = BLOCKS.register("repair_pylon", () -> new RepairPylonCoreBlock<>(MalumBlockProperties.TAINTED_ROCK().setCutoutRenderType().noOcclusion()).setBlockEntity(BlockEntityRegistry.REPAIR_PYLON));
    public static final DeferredHolder<Block, Block> REPAIR_PYLON_COMPONENT = BLOCKS.register("repair_pylon_component", () -> new RepairPylonComponentBlock(MalumBlockProperties.TAINTED_ROCK().setCutoutRenderType().lootFrom(REPAIR_PYLON).noOcclusion(), ItemRegistry.REPAIR_PYLON));

    public static final DeferredHolder<Block, Block> RUNEWOOD_TOTEM_BASE = BLOCKS.register("runewood_totem_base", () -> new TotemBaseBlock<>(MalumBlockProperties.RUNEWOOD().addTag(RITE_IMMUNE).noOcclusion(), false).setBlockEntity(BlockEntityRegistry.TOTEM_BASE));
    public static final DeferredHolder<Block, Block> RUNEWOOD_TOTEM_POLE = BLOCKS.register("runewood_totem_pole", () -> new TotemPoleBlock<>(MalumBlockProperties.RUNEWOOD().addTag(RITE_IMMUNE).noOcclusion(), BlockRegistry.RUNEWOOD_LOG, false).setBlockEntity(BlockEntityRegistry.TOTEM_POLE));

    public static final DeferredHolder<Block, Block> SOULWOOD_TOTEM_BASE = BLOCKS.register("soulwood_totem_base", () -> new TotemBaseBlock<>(MalumBlockProperties.SOULWOOD().addTag(RITE_IMMUNE).noOcclusion(), true).setBlockEntity(BlockEntityRegistry.TOTEM_BASE));
    public static final DeferredHolder<Block, Block> SOULWOOD_TOTEM_POLE = BLOCKS.register("soulwood_totem_pole", () -> new TotemPoleBlock<>(MalumBlockProperties.SOULWOOD().addTag(RITE_IMMUNE).noOcclusion(), BlockRegistry.SOULWOOD_LOG, true).setBlockEntity(BlockEntityRegistry.TOTEM_POLE));

    public static final DeferredHolder<Block, Block> WAVECHARGER = BLOCKS.register("wavecharger", () -> new WaveChargerBlock(MalumBlockProperties.REDSTONE_DIODE()).setBlockEntity(BlockEntityRegistry.WAVECHARGER));
    public static final DeferredHolder<Block, Block> WAVEBANKER = BLOCKS.register("wavebanker", () -> new WaveBankerBlock(MalumBlockProperties.REDSTONE_DIODE()).setBlockEntity(BlockEntityRegistry.WAVEBANKER));
    public static final DeferredHolder<Block, Block> WAVEMAKER = BLOCKS.register("wavemaker", () -> new WaveMakerBlock(MalumBlockProperties.REDSTONE_DIODE()).setBlockEntity(BlockEntityRegistry.WAVEMAKER));
    public static final DeferredHolder<Block, Block> WAVEBREAKER = BLOCKS.register("wavebreaker", () -> new WaveBreakerBlock(MalumBlockProperties.REDSTONE_DIODE()).setBlockEntity(BlockEntityRegistry.WAVEBREAKER));

    public static final DeferredHolder<Block, Block> SPIRIT_MOTE = BLOCKS.register("spirit_mote", () -> new ManaMoteBlock(MalumBlockProperties.MANA_MOTE_BLOCK()).setBlockEntity(BlockEntityRegistry.MANA_MOTE));

    public static final DeferredHolder<Block, Block> VOID_CONDUIT = BLOCKS.register("void_conduit", () -> new VoidConduitBlock<>(MalumBlockProperties.PRIMORDIAL_SOUP()).setBlockEntity(BlockEntityRegistry.VOID_CONDUIT));
    public static final DeferredHolder<Block, Block> PRIMORDIAL_SOUP = BLOCKS.register("primordial_soup", () -> new PrimordialSoupBlock(MalumBlockProperties.PRIMORDIAL_SOUP()));

    public static final DeferredHolder<Block, Block> VOID_DEPOT = BLOCKS.register("void_depot", () -> new VoidDepotBlock<>(MalumBlockProperties.WEEPING_WELL()).setBlockEntity(BlockEntityRegistry.VOID_DEPOT));

    public static final DeferredHolder<Block, Block> WEEPING_WELL_BRICKS = BLOCKS.register("weeping_well_bricks", () -> new Block(MalumBlockProperties.WEEPING_WELL()));

    public static final DeferredHolder<Block, Block> WEEPING_WELL_ENCASEMENT = BLOCKS.register("weeping_well_encasement", () -> new WeepingWellBlock(MalumBlockProperties.WEEPING_WELL()));
    public static final DeferredHolder<Block, Block> WEEPING_WELL_ENCASEMENT_MIRRORED = BLOCKS.register("weeping_well_encasement_mirrored", () -> new WeepingWellBlock(MalumBlockProperties.WEEPING_WELL()));
    public static final DeferredHolder<Block, Block> WEEPING_WELL_ENCASEMENT_CORNER = BLOCKS.register("weeping_well_encasement_corner", () -> new WeepingWellBlock(MalumBlockProperties.WEEPING_WELL()));

    public static final DeferredHolder<Block, Block> WEEPING_WELL_CENTRAL_ENCASEMENT = BLOCKS.register("weeping_well_central_encasement", () -> new WeepingWellBlock(MalumBlockProperties.WEEPING_WELL()));
    public static final DeferredHolder<Block, Block> WEEPING_WELL_CENTRAL_ENCASEMENT_SUPPORT = BLOCKS.register("weeping_well_central_encasement_support", () -> new WeepingWellPillarBlock(MalumBlockProperties.WEEPING_WELL()));

    public static final DeferredHolder<Block, Block> WEEPING_WELL_CENTRAL_PILLAR = BLOCKS.register("weeping_well_central_pillar", () -> new WeepingWellPillarBlock(MalumBlockProperties.WEEPING_WELL()));
    public static final DeferredHolder<Block, Block> WEEPING_WELL_SIDE_PILLAR = BLOCKS.register("weeping_well_side_pillar", () -> new WeepingWellBlock(MalumBlockProperties.WEEPING_WELL()));

    public static final DeferredHolder<Block, Block> WEEPING_WELL_CORNER = BLOCKS.register("weeping_well_corner", () -> new WeepingWellBlock(MalumBlockProperties.WEEPING_WELL()));
    public static final DeferredHolder<Block, Block> WEEPING_WELL_SIDE = BLOCKS.register("weeping_well_side", () -> new WeepingWellBlock(MalumBlockProperties.WEEPING_WELL()));
    public static final DeferredHolder<Block, Block> WEEPING_WELL_CORE = BLOCKS.register("weeping_well_core", () -> new WeepingWellBlock(MalumBlockProperties.WEEPING_WELL()));
    //endregion

    //region spirited glass
    public static final DeferredHolder<Block, Block> SACRED_SPIRITED_GLASS = BLOCKS.register("sacred_spirited_glass", () -> new SpiritedGlassBlock(MalumBlockProperties.SPIRITED_GLASS()));
    public static final DeferredHolder<Block, Block> WICKED_SPIRITED_GLASS = BLOCKS.register("wicked_spirited_glass", () -> new SpiritedGlassBlock(MalumBlockProperties.SPIRITED_GLASS()));
    public static final DeferredHolder<Block, Block> ARCANE_SPIRITED_GLASS = BLOCKS.register("arcane_spirited_glass", () -> new SpiritedGlassBlock(MalumBlockProperties.SPIRITED_GLASS()));
    public static final DeferredHolder<Block, Block> ELDRITCH_SPIRITED_GLASS = BLOCKS.register("eldritch_spirited_glass", () -> new SpiritedGlassBlock(MalumBlockProperties.SPIRITED_GLASS()));
    public static final DeferredHolder<Block, Block> AERIAL_SPIRITED_GLASS = BLOCKS.register("aerial_spirited_glass", () -> new SpiritedGlassBlock(MalumBlockProperties.SPIRITED_GLASS()));
    public static final DeferredHolder<Block, Block> AQUEOUS_SPIRITED_GLASS = BLOCKS.register("aqueous_spirited_glass", () -> new SpiritedGlassBlock(MalumBlockProperties.SPIRITED_GLASS()));
    public static final DeferredHolder<Block, Block> INFERNAL_SPIRITED_GLASS = BLOCKS.register("infernal_spirited_glass", () -> new SpiritedGlassBlock(MalumBlockProperties.SPIRITED_GLASS()));
    public static final DeferredHolder<Block, Block> EARTHEN_SPIRITED_GLASS = BLOCKS.register("earthen_spirited_glass", () -> new SpiritedGlassBlock(MalumBlockProperties.SPIRITED_GLASS()));
    //endregion

    public static final DeferredHolder<Block, Block> SOULWOVEN_BANNER = BLOCKS.register("soulwoven_banner", () -> new SoulwovenBannerBlock(MalumBlockProperties.SOULWOVEN_BANNER()).setBlockEntity(BlockEntityRegistry.SOULWOVEN_BANNER));

    //region tainted rock
    public static final DeferredHolder<Block, Block> TAINTED_ROCK = BLOCKS.register("tainted_rock", () -> new Block(MalumBlockProperties.TAINTED_ROCK().addTag(TAINTED_BLOCKS)));
    public static final DeferredHolder<Block, Block> SMOOTH_TAINTED_ROCK = BLOCKS.register("smooth_tainted_rock", () -> new Block(MalumBlockProperties.TAINTED_ROCK().addTag(TAINTED_BLOCKS)));
    public static final DeferredHolder<Block, Block> POLISHED_TAINTED_ROCK = BLOCKS.register("polished_tainted_rock", () -> new Block(MalumBlockProperties.TAINTED_ROCK().addTag(TAINTED_BLOCKS)));
    public static final DeferredHolder<Block, Block> TAINTED_ROCK_BRICKS = BLOCKS.register("tainted_rock_bricks", () -> new Block(MalumBlockProperties.TAINTED_ROCK_BRICKS().addTag(TAINTED_BLOCKS)));
    public static final DeferredHolder<Block, Block> TAINTED_ROCK_TILES = BLOCKS.register("tainted_rock_tiles", () -> new Block(MalumBlockProperties.TAINTED_ROCK_BRICKS().addTag(TAINTED_BLOCKS)));
    public static final DeferredHolder<Block, Block> SMALL_TAINTED_ROCK_BRICKS = BLOCKS.register("small_tainted_rock_bricks", () -> new Block(MalumBlockProperties.TAINTED_ROCK_BRICKS().addTag(TAINTED_BLOCKS)));
    public static final DeferredHolder<Block, Block> RUNIC_TAINTED_ROCK_BRICKS = BLOCKS.register("runic_tainted_rock_bricks", () -> new Block(MalumBlockProperties.TAINTED_ROCK_BRICKS().addTag(TAINTED_BLOCKS)));
    public static final DeferredHolder<Block, Block> RUNIC_TAINTED_ROCK_TILES = BLOCKS.register("runic_tainted_rock_tiles", () -> new Block(MalumBlockProperties.TAINTED_ROCK_BRICKS().addTag(TAINTED_BLOCKS)));
    public static final DeferredHolder<Block, Block> RUNIC_SMALL_TAINTED_ROCK_BRICKS = BLOCKS.register("runic_small_tainted_rock_bricks", () -> new Block(MalumBlockProperties.TAINTED_ROCK_BRICKS().addTag(TAINTED_BLOCKS)));

    public static final DeferredHolder<Block, Block> TAINTED_ROCK_COLUMN = BLOCKS.register("tainted_rock_column", () -> new RotatedPillarBlock(MalumBlockProperties.TAINTED_ROCK()));
    public static final DeferredHolder<Block, Block> TAINTED_ROCK_COLUMN_CAP = BLOCKS.register("tainted_rock_column_cap", () -> new LodestoneDirectionalBlock(MalumBlockProperties.TAINTED_ROCK()));

    public static final DeferredHolder<Block, Block> CUT_TAINTED_ROCK = BLOCKS.register("cut_tainted_rock", () -> new Block(MalumBlockProperties.TAINTED_ROCK()));
    public static final DeferredHolder<Block, Block> CHECKERED_TAINTED_ROCK = BLOCKS.register("checkered_tainted_rock", () -> new Block(MalumBlockProperties.TAINTED_ROCK()));
    public static final DeferredHolder<Block, Block> CHISELED_TAINTED_ROCK = BLOCKS.register("chiseled_tainted_rock", () -> new Block(MalumBlockProperties.TAINTED_ROCK()));

    public static final DeferredHolder<Block, Block> TAINTED_ROCK_SLAB = BLOCKS.register("tainted_rock_slab", () -> new SlabBlock(MalumBlockProperties.TAINTED_ROCK().addTags(SLABS, TAINTED_SLABS)));
    public static final DeferredHolder<Block, Block> SMOOTH_TAINTED_ROCK_SLAB = BLOCKS.register("smooth_tainted_rock_slab", () -> new SlabBlock(MalumBlockProperties.TAINTED_ROCK().addTags(SLABS, TAINTED_SLABS)));
    public static final DeferredHolder<Block, Block> POLISHED_TAINTED_ROCK_SLAB = BLOCKS.register("polished_tainted_rock_slab", () -> new SlabBlock(MalumBlockProperties.TAINTED_ROCK().addTags(SLABS, TAINTED_SLABS)));
    public static final DeferredHolder<Block, Block> TAINTED_ROCK_BRICKS_SLAB = BLOCKS.register("tainted_rock_bricks_slab", () -> new SlabBlock(MalumBlockProperties.TAINTED_ROCK_BRICKS().addTags(SLABS, TAINTED_SLABS)));
    public static final DeferredHolder<Block, Block> TAINTED_ROCK_TILES_SLAB = BLOCKS.register("tainted_rock_tiles_slab", () -> new SlabBlock(MalumBlockProperties.TAINTED_ROCK_BRICKS().addTags(SLABS, TAINTED_SLABS)));
    public static final DeferredHolder<Block, Block> SMALL_TAINTED_ROCK_BRICKS_SLAB = BLOCKS.register("small_tainted_rock_bricks_slab", () -> new SlabBlock(MalumBlockProperties.TAINTED_ROCK_BRICKS().addTags(SLABS, TAINTED_SLABS)));
    public static final DeferredHolder<Block, Block> RUNIC_TAINTED_ROCK_BRICKS_SLAB = BLOCKS.register("runic_tainted_rock_bricks_slab", () -> new SlabBlock(MalumBlockProperties.TAINTED_ROCK_BRICKS().addTags(SLABS, TAINTED_SLABS)));
    public static final DeferredHolder<Block, Block> RUNIC_TAINTED_ROCK_TILES_SLAB = BLOCKS.register("runic_tainted_rock_tiles_slab", () -> new SlabBlock(MalumBlockProperties.TAINTED_ROCK_BRICKS().addTags(SLABS, TAINTED_SLABS)));
    public static final DeferredHolder<Block, Block> RUNIC_SMALL_TAINTED_ROCK_BRICKS_SLAB = BLOCKS.register("runic_small_tainted_rock_bricks_slab", () -> new SlabBlock(MalumBlockProperties.TAINTED_ROCK_BRICKS().addTags(SLABS, TAINTED_SLABS)));

    public static final DeferredHolder<Block, Block> TAINTED_ROCK_STAIRS = BLOCKS.register("tainted_rock_stairs", () -> new StairBlock(TAINTED_ROCK.get().defaultBlockState(), MalumBlockProperties.TAINTED_ROCK().addTags(STAIRS, TAINTED_STAIRS)));
    public static final DeferredHolder<Block, Block> SMOOTH_TAINTED_ROCK_STAIRS = BLOCKS.register("smooth_tainted_rock_stairs", () -> new StairBlock(TAINTED_ROCK.get().defaultBlockState(), MalumBlockProperties.TAINTED_ROCK().addTags(STAIRS, TAINTED_STAIRS)));
    public static final DeferredHolder<Block, Block> POLISHED_TAINTED_ROCK_STAIRS = BLOCKS.register("polished_tainted_rock_stairs", () -> new StairBlock(TAINTED_ROCK.get().defaultBlockState(), MalumBlockProperties.TAINTED_ROCK().addTags(STAIRS, TAINTED_STAIRS)));
    public static final DeferredHolder<Block, Block> TAINTED_ROCK_BRICKS_STAIRS = BLOCKS.register("tainted_rock_bricks_stairs", () -> new StairBlock(TAINTED_ROCK.get().defaultBlockState(), MalumBlockProperties.TAINTED_ROCK_BRICKS().addTags(STAIRS, TAINTED_STAIRS)));
    public static final DeferredHolder<Block, Block> TAINTED_ROCK_TILES_STAIRS = BLOCKS.register("tainted_rock_tiles_stairs", () -> new StairBlock(TAINTED_ROCK.get().defaultBlockState(), MalumBlockProperties.TAINTED_ROCK_BRICKS().addTags(STAIRS, TAINTED_STAIRS)));
    public static final DeferredHolder<Block, Block> SMALL_TAINTED_ROCK_BRICKS_STAIRS = BLOCKS.register("small_tainted_rock_bricks_stairs", () -> new StairBlock(TAINTED_ROCK.get().defaultBlockState(), MalumBlockProperties.TAINTED_ROCK_BRICKS().addTags(STAIRS, TAINTED_STAIRS)));
    public static final DeferredHolder<Block, Block> RUNIC_TAINTED_ROCK_BRICKS_STAIRS = BLOCKS.register("runic_tainted_rock_bricks_stairs", () -> new StairBlock(TAINTED_ROCK.get().defaultBlockState(), MalumBlockProperties.TAINTED_ROCK_BRICKS().addTags(STAIRS, TAINTED_STAIRS)));
    public static final DeferredHolder<Block, Block> RUNIC_TAINTED_ROCK_TILES_STAIRS = BLOCKS.register("runic_tainted_rock_tiles_stairs", () -> new StairBlock(TAINTED_ROCK.get().defaultBlockState(), MalumBlockProperties.TAINTED_ROCK_BRICKS().addTags(STAIRS, TAINTED_STAIRS)));
    public static final DeferredHolder<Block, Block> RUNIC_SMALL_TAINTED_ROCK_BRICKS_STAIRS = BLOCKS.register("runic_small_tainted_rock_bricks_stairs", () -> new StairBlock(TAINTED_ROCK.get().defaultBlockState(), MalumBlockProperties.TAINTED_ROCK_BRICKS().addTags(STAIRS, TAINTED_STAIRS)));

    public static final DeferredHolder<Block, Block> TAINTED_ROCK_BUTTON = BLOCKS.register("tainted_rock_button", () -> new ButtonBlock(BlockSetType.STONE, 20, MalumBlockProperties.TAINTED_ROCK().addTag(BUTTONS)));
    public static final DeferredHolder<Block, Block> TAINTED_ROCK_PRESSURE_PLATE = BLOCKS.register("tainted_rock_pressure_plate", () -> new PressurePlateBlock(BlockSetType.STONE, MalumBlockProperties.TAINTED_ROCK().addTag(PRESSURE_PLATES)));

    public static final DeferredHolder<Block, Block> TAINTED_ROCK_WALL = BLOCKS.register("tainted_rock_wall", () -> new WallBlock(MalumBlockProperties.TAINTED_ROCK().addTags(WALLS, TAINTED_WALLS)));
    public static final DeferredHolder<Block, Block> SMOOTH_TAINTED_ROCK_WALL = BLOCKS.register("smooth_tainted_rock_wall", () -> new WallBlock(MalumBlockProperties.TAINTED_ROCK().addTags(WALLS, TAINTED_WALLS)));
    public static final DeferredHolder<Block, Block> POLISHED_TAINTED_ROCK_WALL = BLOCKS.register("polished_tainted_rock_wall", () -> new WallBlock(MalumBlockProperties.TAINTED_ROCK().addTags(WALLS, TAINTED_WALLS)));
    public static final DeferredHolder<Block, Block> TAINTED_ROCK_BRICKS_WALL = BLOCKS.register("tainted_rock_bricks_wall", () -> new WallBlock(MalumBlockProperties.TAINTED_ROCK_BRICKS().addTags(WALLS, TAINTED_WALLS)));
    public static final DeferredHolder<Block, Block> TAINTED_ROCK_TILES_WALL = BLOCKS.register("tainted_rock_tiles_wall", () -> new WallBlock(MalumBlockProperties.TAINTED_ROCK_BRICKS().addTags(WALLS, TAINTED_WALLS)));
    public static final DeferredHolder<Block, Block> SMALL_TAINTED_ROCK_BRICKS_WALL = BLOCKS.register("small_tainted_rock_bricks_wall", () -> new WallBlock(MalumBlockProperties.TAINTED_ROCK_BRICKS().addTags(WALLS, TAINTED_WALLS)));
    public static final DeferredHolder<Block, Block> RUNIC_TAINTED_ROCK_BRICKS_WALL = BLOCKS.register("runic_tainted_rock_bricks_wall", () -> new WallBlock(MalumBlockProperties.TAINTED_ROCK_BRICKS().addTags(WALLS, TAINTED_WALLS)));
    public static final DeferredHolder<Block, Block> RUNIC_TAINTED_ROCK_TILES_WALL = BLOCKS.register("runic_tainted_rock_tiles_wall", () -> new WallBlock(MalumBlockProperties.TAINTED_ROCK_BRICKS().addTags(WALLS, TAINTED_WALLS)));
    public static final DeferredHolder<Block, Block> RUNIC_SMALL_TAINTED_ROCK_BRICKS_WALL = BLOCKS.register("runic_small_tainted_rock_bricks_wall", () -> new WallBlock(MalumBlockProperties.TAINTED_ROCK_BRICKS().addTags(WALLS, TAINTED_WALLS)));

    public static final DeferredHolder<Block, Block> TAINTED_ROCK_ITEM_STAND = BLOCKS.register("tainted_rock_item_stand", () -> new ItemStandBlock<>(MalumBlockProperties.TAINTED_ROCK().noOcclusion()).setBlockEntity(BlockEntityRegistry.ITEM_STAND));
    public static final DeferredHolder<Block, Block> TAINTED_ROCK_ITEM_PEDESTAL = BLOCKS.register("tainted_rock_item_pedestal", () -> new ItemPedestalBlock<>(MalumBlockProperties.TAINTED_ROCK().noOcclusion()).setBlockEntity(BlockEntityRegistry.ITEM_PEDESTAL));
    //endregion

    //region twisted rock
    public static final DeferredHolder<Block, Block> TWISTED_ROCK = BLOCKS.register("twisted_rock", () -> new Block(MalumBlockProperties.TWISTED_ROCK().addTag(TWISTED_BLOCKS)));
    public static final DeferredHolder<Block, Block> SMOOTH_TWISTED_ROCK = BLOCKS.register("smooth_twisted_rock", () -> new Block(MalumBlockProperties.TWISTED_ROCK().addTag(TWISTED_BLOCKS)));
    public static final DeferredHolder<Block, Block> POLISHED_TWISTED_ROCK = BLOCKS.register("polished_twisted_rock", () -> new Block(MalumBlockProperties.TWISTED_ROCK().addTag(TWISTED_BLOCKS)));
    public static final DeferredHolder<Block, Block> TWISTED_ROCK_BRICKS = BLOCKS.register("twisted_rock_bricks", () -> new Block(MalumBlockProperties.TWISTED_ROCK_BRICKS().addTag(TWISTED_BLOCKS)));
    public static final DeferredHolder<Block, Block> TWISTED_ROCK_TILES = BLOCKS.register("twisted_rock_tiles", () -> new Block(MalumBlockProperties.TWISTED_ROCK_BRICKS().addTag(TWISTED_BLOCKS)));
    public static final DeferredHolder<Block, Block> SMALL_TWISTED_ROCK_BRICKS = BLOCKS.register("small_twisted_rock_bricks", () -> new Block(MalumBlockProperties.TWISTED_ROCK_BRICKS().addTag(TWISTED_BLOCKS)));
    public static final DeferredHolder<Block, Block> RUNIC_TWISTED_ROCK_BRICKS = BLOCKS.register("runic_twisted_rock_bricks", () -> new Block(MalumBlockProperties.TWISTED_ROCK_BRICKS().addTag(TWISTED_BLOCKS)));
    public static final DeferredHolder<Block, Block> RUNIC_TWISTED_ROCK_TILES = BLOCKS.register("runic_twisted_rock_tiles", () -> new Block(MalumBlockProperties.TWISTED_ROCK_BRICKS().addTag(TWISTED_BLOCKS)));
    public static final DeferredHolder<Block, Block> RUNIC_SMALL_TWISTED_ROCK_BRICKS = BLOCKS.register("runic_small_twisted_rock_bricks", () -> new Block(MalumBlockProperties.TWISTED_ROCK_BRICKS().addTag(TWISTED_BLOCKS)));

    public static final DeferredHolder<Block, Block> TWISTED_ROCK_COLUMN = BLOCKS.register("twisted_rock_column", () -> new RotatedPillarBlock(MalumBlockProperties.TWISTED_ROCK()));
    public static final DeferredHolder<Block, Block> TWISTED_ROCK_COLUMN_CAP = BLOCKS.register("twisted_rock_column_cap", () -> new LodestoneDirectionalBlock(MalumBlockProperties.TWISTED_ROCK()));

    public static final DeferredHolder<Block, Block> CUT_TWISTED_ROCK = BLOCKS.register("cut_twisted_rock", () -> new Block(MalumBlockProperties.TWISTED_ROCK()));
    public static final DeferredHolder<Block, Block> CHECKERED_TWISTED_ROCK = BLOCKS.register("checkered_twisted_rock", () -> new Block(MalumBlockProperties.TWISTED_ROCK()));
    public static final DeferredHolder<Block, Block> CHISELED_TWISTED_ROCK = BLOCKS.register("chiseled_twisted_rock", () -> new Block(MalumBlockProperties.TWISTED_ROCK()));

    public static final DeferredHolder<Block, Block> TWISTED_ROCK_SLAB = BLOCKS.register("twisted_rock_slab", () -> new SlabBlock(MalumBlockProperties.TWISTED_ROCK().addTags(SLABS, TWISTED_SLABS)));
    public static final DeferredHolder<Block, Block> SMOOTH_TWISTED_ROCK_SLAB = BLOCKS.register("smooth_twisted_rock_slab", () -> new SlabBlock(MalumBlockProperties.TWISTED_ROCK().addTags(SLABS, TWISTED_SLABS)));
    public static final DeferredHolder<Block, Block> POLISHED_TWISTED_ROCK_SLAB = BLOCKS.register("polished_twisted_rock_slab", () -> new SlabBlock(MalumBlockProperties.TWISTED_ROCK().addTags(SLABS, TWISTED_SLABS)));
    public static final DeferredHolder<Block, Block> TWISTED_ROCK_BRICKS_SLAB = BLOCKS.register("twisted_rock_bricks_slab", () -> new SlabBlock(MalumBlockProperties.TWISTED_ROCK_BRICKS().addTags(SLABS, TWISTED_SLABS)));
    public static final DeferredHolder<Block, Block> TWISTED_ROCK_TILES_SLAB = BLOCKS.register("twisted_rock_tiles_slab", () -> new SlabBlock(MalumBlockProperties.TWISTED_ROCK_BRICKS().addTags(SLABS, TWISTED_SLABS)));
    public static final DeferredHolder<Block, Block> SMALL_TWISTED_ROCK_BRICKS_SLAB = BLOCKS.register("small_twisted_rock_bricks_slab", () -> new SlabBlock(MalumBlockProperties.TWISTED_ROCK_BRICKS().addTags(SLABS, TWISTED_SLABS)));
    public static final DeferredHolder<Block, Block> RUNIC_TWISTED_ROCK_BRICKS_SLAB = BLOCKS.register("runic_twisted_rock_bricks_slab", () -> new SlabBlock(MalumBlockProperties.TWISTED_ROCK_BRICKS().addTags(SLABS, TWISTED_SLABS)));
    public static final DeferredHolder<Block, Block> RUNIC_TWISTED_ROCK_TILES_SLAB = BLOCKS.register("runic_twisted_rock_tiles_slab", () -> new SlabBlock(MalumBlockProperties.TWISTED_ROCK_BRICKS().addTags(SLABS, TWISTED_SLABS)));
    public static final DeferredHolder<Block, Block> RUNIC_SMALL_TWISTED_ROCK_BRICKS_SLAB = BLOCKS.register("runic_small_twisted_rock_bricks_slab", () -> new SlabBlock(MalumBlockProperties.TWISTED_ROCK_BRICKS().addTags(SLABS, TWISTED_SLABS)));

    public static final DeferredHolder<Block, Block> TWISTED_ROCK_STAIRS = BLOCKS.register("twisted_rock_stairs", () -> new StairBlock(TWISTED_ROCK.get().defaultBlockState(), MalumBlockProperties.TWISTED_ROCK().addTags(STAIRS, TWISTED_STAIRS)));
    public static final DeferredHolder<Block, Block> SMOOTH_TWISTED_ROCK_STAIRS = BLOCKS.register("smooth_twisted_rock_stairs", () -> new StairBlock(TWISTED_ROCK.get().defaultBlockState(), MalumBlockProperties.TWISTED_ROCK().addTags(STAIRS, TWISTED_STAIRS)));
    public static final DeferredHolder<Block, Block> POLISHED_TWISTED_ROCK_STAIRS = BLOCKS.register("polished_twisted_rock_stairs", () -> new StairBlock(TWISTED_ROCK.get().defaultBlockState(), MalumBlockProperties.TWISTED_ROCK().addTags(STAIRS, TWISTED_STAIRS)));
    public static final DeferredHolder<Block, Block> TWISTED_ROCK_BRICKS_STAIRS = BLOCKS.register("twisted_rock_bricks_stairs", () -> new StairBlock(TWISTED_ROCK.get().defaultBlockState(), MalumBlockProperties.TWISTED_ROCK_BRICKS().addTags(STAIRS, TWISTED_STAIRS)));
    public static final DeferredHolder<Block, Block> TWISTED_ROCK_TILES_STAIRS = BLOCKS.register("twisted_rock_tiles_stairs", () -> new StairBlock(TWISTED_ROCK.get().defaultBlockState(), MalumBlockProperties.TWISTED_ROCK_BRICKS().addTags(STAIRS, TWISTED_STAIRS)));
    public static final DeferredHolder<Block, Block> SMALL_TWISTED_ROCK_BRICKS_STAIRS = BLOCKS.register("small_twisted_rock_bricks_stairs", () -> new StairBlock(TWISTED_ROCK.get().defaultBlockState(), MalumBlockProperties.TWISTED_ROCK_BRICKS().addTags(STAIRS, TWISTED_STAIRS)));
    public static final DeferredHolder<Block, Block> RUNIC_TWISTED_ROCK_BRICKS_STAIRS = BLOCKS.register("runic_twisted_rock_bricks_stairs", () -> new StairBlock(TWISTED_ROCK.get().defaultBlockState(), MalumBlockProperties.TWISTED_ROCK_BRICKS().addTags(STAIRS, TWISTED_STAIRS)));
    public static final DeferredHolder<Block, Block> RUNIC_TWISTED_ROCK_TILES_STAIRS = BLOCKS.register("runic_twisted_rock_tiles_stairs", () -> new StairBlock(TWISTED_ROCK.get().defaultBlockState(), MalumBlockProperties.TWISTED_ROCK_BRICKS().addTags(STAIRS, TWISTED_STAIRS)));
    public static final DeferredHolder<Block, Block> RUNIC_SMALL_TWISTED_ROCK_BRICKS_STAIRS = BLOCKS.register("runic_small_twisted_rock_bricks_stairs", () -> new StairBlock(TWISTED_ROCK.get().defaultBlockState(), MalumBlockProperties.TWISTED_ROCK_BRICKS().addTags(STAIRS, TWISTED_STAIRS)));

    public static final DeferredHolder<Block, Block> TWISTED_ROCK_BUTTON = BLOCKS.register("twisted_rock_button", () -> new ButtonBlock(BlockSetType.STONE, 20, MalumBlockProperties.TWISTED_ROCK().addTag(BUTTONS)));
    public static final DeferredHolder<Block, Block> TWISTED_ROCK_PRESSURE_PLATE = BLOCKS.register("twisted_rock_pressure_plate", () -> new PressurePlateBlock(BlockSetType.STONE, MalumBlockProperties.TWISTED_ROCK().addTag(PRESSURE_PLATES)));

    public static final DeferredHolder<Block, Block> TWISTED_ROCK_WALL = BLOCKS.register("twisted_rock_wall", () -> new WallBlock(MalumBlockProperties.TWISTED_ROCK().addTags(WALLS, TWISTED_WALLS)));
    public static final DeferredHolder<Block, Block> SMOOTH_TWISTED_ROCK_WALL = BLOCKS.register("smooth_twisted_rock_wall", () -> new WallBlock(MalumBlockProperties.TWISTED_ROCK().addTags(WALLS, TWISTED_WALLS)));
    public static final DeferredHolder<Block, Block> POLISHED_TWISTED_ROCK_WALL = BLOCKS.register("polished_twisted_rock_wall", () -> new WallBlock(MalumBlockProperties.TWISTED_ROCK().addTags(WALLS, TWISTED_WALLS)));
    public static final DeferredHolder<Block, Block> TWISTED_ROCK_BRICKS_WALL = BLOCKS.register("twisted_rock_bricks_wall", () -> new WallBlock(MalumBlockProperties.TWISTED_ROCK_BRICKS().addTags(WALLS, TWISTED_WALLS)));
    public static final DeferredHolder<Block, Block> TWISTED_ROCK_TILES_WALL = BLOCKS.register("twisted_rock_tiles_wall", () -> new WallBlock(MalumBlockProperties.TWISTED_ROCK_BRICKS().addTags(WALLS, TWISTED_WALLS)));
    public static final DeferredHolder<Block, Block> SMALL_TWISTED_ROCK_BRICKS_WALL = BLOCKS.register("small_twisted_rock_bricks_wall", () -> new WallBlock(MalumBlockProperties.TWISTED_ROCK_BRICKS().addTags(WALLS, TWISTED_WALLS)));
    public static final DeferredHolder<Block, Block> RUNIC_TWISTED_ROCK_BRICKS_WALL = BLOCKS.register("runic_twisted_rock_bricks_wall", () -> new WallBlock(MalumBlockProperties.TWISTED_ROCK_BRICKS().addTags(WALLS, TWISTED_WALLS)));
    public static final DeferredHolder<Block, Block> RUNIC_TWISTED_ROCK_TILES_WALL = BLOCKS.register("runic_twisted_rock_tiles_wall", () -> new WallBlock(MalumBlockProperties.TWISTED_ROCK_BRICKS().addTags(WALLS, TWISTED_WALLS)));
    public static final DeferredHolder<Block, Block> RUNIC_SMALL_TWISTED_ROCK_BRICKS_WALL = BLOCKS.register("runic_small_twisted_rock_bricks_wall", () -> new WallBlock(MalumBlockProperties.TWISTED_ROCK_BRICKS().addTags(WALLS, TWISTED_WALLS)));

    public static final DeferredHolder<Block, Block> TWISTED_ROCK_ITEM_STAND = BLOCKS.register("twisted_rock_item_stand", () -> new ItemStandBlock<>(MalumBlockProperties.TWISTED_ROCK().noOcclusion()).setBlockEntity(BlockEntityRegistry.ITEM_STAND));
    public static final DeferredHolder<Block, Block> TWISTED_ROCK_ITEM_PEDESTAL = BLOCKS.register("twisted_rock_item_pedestal", () -> new ItemPedestalBlock<>(MalumBlockProperties.TWISTED_ROCK().noOcclusion()).setBlockEntity(BlockEntityRegistry.ITEM_PEDESTAL));
    //endregion

    //region runewood
    public static final DeferredHolder<Block, Block> RUNEWOOD_SAPLING = BLOCKS.register("runewood_sapling", () -> new MalumSaplingBlock(TreeGrowerRegistry.RUNEWOOD, MalumBlockProperties.RUNEWOOD_SAPLING()));
    public static final DeferredHolder<Block, Block> RUNEWOOD_LEAVES = BLOCKS.register("runewood_leaves", () -> new MalumLeavesBlock(MalumBlockProperties.RUNEWOOD_LEAVES(), MalumBlockProperties.RUNEWOOD_LEAVES_ORANGE, MalumBlockProperties.RUNEWOOD_LEAVES_YELLOW));
    public static final DeferredHolder<Block, Block> HANGING_RUNEWOOD_LEAVES = BLOCKS.register("hanging_runewood_leaves", () -> new MalumHangingLeavesBlock(MalumBlockProperties.HANGING_RUNEWOOD_LEAVES().setCutoutRenderType().noOcclusion().noCollission(), MalumBlockProperties.RUNEWOOD_LEAVES_ORANGE, MalumBlockProperties.RUNEWOOD_LEAVES_YELLOW));

    public static final DeferredHolder<Block, Block> AZURE_RUNEWOOD_SAPLING = BLOCKS.register("azure_runewood_sapling", () -> new MalumSaplingBlock(TreeGrowerRegistry.AZURE_RUNEWOOD, MalumBlockProperties.RUNEWOOD_SAPLING()));
    public static final DeferredHolder<Block, Block> AZURE_RUNEWOOD_LEAVES = BLOCKS.register("azure_runewood_leaves", () -> new MalumLeavesBlock(MalumBlockProperties.RUNEWOOD_LEAVES(), MalumBlockProperties.AZURE_RUNEWOOD_LEAVES_BLUE, MalumBlockProperties.AZURE_RUNEWOOD_LEAVES_CYAN));
    public static final DeferredHolder<Block, Block> HANGING_AZURE_RUNEWOOD_LEAVES = BLOCKS.register("hanging_azure_runewood_leaves", () -> new MalumHangingLeavesBlock(MalumBlockProperties.HANGING_RUNEWOOD_LEAVES().setCutoutRenderType().noOcclusion().noCollission(), MalumBlockProperties.AZURE_RUNEWOOD_LEAVES_BLUE, MalumBlockProperties.AZURE_RUNEWOOD_LEAVES_CYAN));


    public static final DeferredHolder<Block, Block> STRIPPED_RUNEWOOD_LOG = BLOCKS.register("stripped_runewood_log", () -> new RotatedPillarBlock(MalumBlockProperties.RUNEWOOD().addTags(LOGS, STRIPPED_LOGS, RUNEWOOD_LOGS)));
    public static final DeferredHolder<Block, Block> RUNEWOOD_LOG = BLOCKS.register("runewood_log", () -> new MalumLogBLock(MalumBlockProperties.RUNEWOOD().addTags(LOGS, RUNEWOOD_LOGS), STRIPPED_RUNEWOOD_LOG, false));
    public static final DeferredHolder<Block, Block> STRIPPED_RUNEWOOD = BLOCKS.register("stripped_runewood", () -> new RotatedPillarBlock(MalumBlockProperties.RUNEWOOD().addTags(LOGS, STRIPPED_LOGS, RUNEWOOD_LOGS)));
    public static final DeferredHolder<Block, Block> RUNEWOOD = BLOCKS.register("runewood", () -> new LodestoneLogBlock(MalumBlockProperties.RUNEWOOD().addTags(LOGS, RUNEWOOD_LOGS), STRIPPED_RUNEWOOD));

    public static final DeferredHolder<Block, Block> REVEALED_RUNEWOOD_LOG = BLOCKS.register("revealed_runewood_log", () -> new SapFilledLogBlock(MalumBlockProperties.RUNEWOOD().addTags(LOGS, RUNEWOOD_LOGS), STRIPPED_RUNEWOOD_LOG, ItemRegistry.RUNIC_SAP, SpiritTypeRegistry.INFERNAL_SPIRIT.getPrimaryColor()));
    public static final DeferredHolder<Block, Block> EXPOSED_RUNEWOOD_LOG = BLOCKS.register("exposed_runewood_log", () -> new LodestoneLogBlock(MalumBlockProperties.RUNEWOOD().addTags(LOGS, STRIPPED_LOGS, RUNEWOOD_LOGS), REVEALED_RUNEWOOD_LOG));

    public static final DeferredHolder<Block, Block> RUNEWOOD_BOARDS = BLOCKS.register("runewood_boards", () -> new Block(MalumBlockProperties.RUNEWOOD().addTags(PLANKS)));
    public static final DeferredHolder<Block, Block> RUNEWOOD_BOARDS_SLAB = BLOCKS.register("runewood_boards_slab", () -> new SlabBlock(MalumBlockProperties.RUNEWOOD().addTags(SLABS, WOODEN_SLABS)));
    public static final DeferredHolder<Block, Block> RUNEWOOD_BOARDS_STAIRS = BLOCKS.register("runewood_boards_stairs", () -> new StairBlock(RUNEWOOD_BOARDS.get().defaultBlockState(), MalumBlockProperties.RUNEWOOD().addTags(STAIRS, WOODEN_STAIRS)));

    public static final DeferredHolder<Block, Block> VERTICAL_RUNEWOOD_BOARDS = BLOCKS.register("vertical_runewood_boards", () -> new Block(MalumBlockProperties.RUNEWOOD().addTags(PLANKS)));
    public static final DeferredHolder<Block, Block> VERTICAL_RUNEWOOD_BOARDS_SLAB = BLOCKS.register("vertical_runewood_boards_slab", () -> new SlabBlock(MalumBlockProperties.RUNEWOOD().addTags(SLABS, WOODEN_SLABS)));
    public static final DeferredHolder<Block, Block> VERTICAL_RUNEWOOD_BOARDS_STAIRS = BLOCKS.register("vertical_runewood_boards_stairs", () -> new StairBlock(VERTICAL_RUNEWOOD_BOARDS.get().defaultBlockState(), MalumBlockProperties.RUNEWOOD().addTags(STAIRS, WOODEN_STAIRS)));

    public static final DeferredHolder<Block, Block> RUNEWOOD_PLANKS = BLOCKS.register("runewood_planks", () -> new Block(MalumBlockProperties.RUNEWOOD().addTags(PLANKS)));
    public static final DeferredHolder<Block, Block> RUNEWOOD_PLANKS_SLAB = BLOCKS.register("runewood_planks_slab", () -> new SlabBlock(MalumBlockProperties.RUNEWOOD().addTags(SLABS, WOODEN_SLABS)));
    public static final DeferredHolder<Block, Block> RUNEWOOD_PLANKS_STAIRS = BLOCKS.register("runewood_planks_stairs", () -> new StairBlock(RUNEWOOD_PLANKS.get().defaultBlockState(), MalumBlockProperties.RUNEWOOD().addTags(STAIRS, WOODEN_STAIRS)));

    public static final DeferredHolder<Block, Block> RUSTIC_RUNEWOOD_PLANKS = BLOCKS.register("rustic_runewood_planks", () -> new Block(MalumBlockProperties.RUNEWOOD().addTags(PLANKS)));
    public static final DeferredHolder<Block, Block> RUSTIC_RUNEWOOD_PLANKS_SLAB = BLOCKS.register("rustic_runewood_planks_slab", () -> new SlabBlock(MalumBlockProperties.RUNEWOOD().addTags(SLABS, WOODEN_SLABS)));
    public static final DeferredHolder<Block, Block> RUSTIC_RUNEWOOD_PLANKS_STAIRS = BLOCKS.register("rustic_runewood_planks_stairs", () -> new StairBlock(RUSTIC_RUNEWOOD_PLANKS.get().defaultBlockState(), MalumBlockProperties.RUNEWOOD().addTags(STAIRS, WOODEN_STAIRS)));

    public static final DeferredHolder<Block, Block> VERTICAL_RUNEWOOD_PLANKS = BLOCKS.register("vertical_runewood_planks", () -> new Block(MalumBlockProperties.RUNEWOOD().addTags(PLANKS)));
    public static final DeferredHolder<Block, Block> VERTICAL_RUNEWOOD_PLANKS_SLAB = BLOCKS.register("vertical_runewood_planks_slab", () -> new SlabBlock(MalumBlockProperties.RUNEWOOD().addTags(SLABS, WOODEN_SLABS)));
    public static final DeferredHolder<Block, Block> VERTICAL_RUNEWOOD_PLANKS_STAIRS = BLOCKS.register("vertical_runewood_planks_stairs", () -> new StairBlock(VERTICAL_RUNEWOOD_PLANKS.get().defaultBlockState(), MalumBlockProperties.RUNEWOOD().addTags(STAIRS, WOODEN_STAIRS)));

    public static final DeferredHolder<Block, Block> VERTICAL_RUSTIC_RUNEWOOD_PLANKS = BLOCKS.register("vertical_rustic_runewood_planks", () -> new Block(MalumBlockProperties.RUNEWOOD().addTags(PLANKS)));
    public static final DeferredHolder<Block, Block> VERTICAL_RUSTIC_RUNEWOOD_PLANKS_SLAB = BLOCKS.register("vertical_rustic_runewood_planks_slab", () -> new SlabBlock(MalumBlockProperties.RUNEWOOD().addTags(SLABS, WOODEN_SLABS)));
    public static final DeferredHolder<Block, Block> VERTICAL_RUSTIC_RUNEWOOD_PLANKS_STAIRS = BLOCKS.register("vertical_rustic_runewood_planks_stairs", () -> new StairBlock(VERTICAL_RUSTIC_RUNEWOOD_PLANKS.get().defaultBlockState(), MalumBlockProperties.RUNEWOOD().addTags(STAIRS, WOODEN_STAIRS)));

    public static final DeferredHolder<Block, Block> RUNEWOOD_TILES = BLOCKS.register("runewood_tiles", () -> new Block(MalumBlockProperties.RUNEWOOD().addTags(PLANKS)));
    public static final DeferredHolder<Block, Block> RUNEWOOD_TILES_SLAB = BLOCKS.register("runewood_tiles_slab", () -> new SlabBlock(MalumBlockProperties.RUNEWOOD().addTags(SLABS, WOODEN_SLABS)));
    public static final DeferredHolder<Block, Block> RUNEWOOD_TILES_STAIRS = BLOCKS.register("runewood_tiles_stairs", () -> new StairBlock(RUNEWOOD_TILES.get().defaultBlockState(), MalumBlockProperties.RUNEWOOD().addTags(STAIRS, WOODEN_STAIRS)));

    public static final DeferredHolder<Block, Block> RUSTIC_RUNEWOOD_TILES = BLOCKS.register("rustic_runewood_tiles", () -> new Block(MalumBlockProperties.RUNEWOOD().addTags(PLANKS)));
    public static final DeferredHolder<Block, Block> RUSTIC_RUNEWOOD_TILES_SLAB = BLOCKS.register("rustic_runewood_tiles_slab", () -> new SlabBlock(MalumBlockProperties.RUNEWOOD().addTags(SLABS, WOODEN_SLABS)));
    public static final DeferredHolder<Block, Block> RUSTIC_RUNEWOOD_TILES_STAIRS = BLOCKS.register("rustic_runewood_tiles_stairs", () -> new StairBlock(RUSTIC_RUNEWOOD_TILES.get().defaultBlockState(), MalumBlockProperties.RUNEWOOD().addTags(STAIRS, WOODEN_STAIRS)));

    public static final DeferredHolder<Block, Block> RUNEWOOD_PANEL = BLOCKS.register("runewood_panel", () -> new Block(MalumBlockProperties.RUNEWOOD()));
    public static final DeferredHolder<Block, Block> CUT_RUNEWOOD_PLANKS = BLOCKS.register("cut_runewood_planks", () -> new Block(MalumBlockProperties.RUNEWOOD().addTags(PLANKS)));
    public static final DeferredHolder<Block, Block> RUNEWOOD_BEAM = BLOCKS.register("runewood_beam", () -> new RotatedPillarBlock(MalumBlockProperties.RUNEWOOD()));

    public static final DeferredHolder<Block, Block> RUNEWOOD_DOOR = BLOCKS.register("runewood_door", () -> new DoorBlock(MalumBlockSetTypes.RUNEWOOD, MalumBlockProperties.RUNEWOOD().addTags(DOORS, WOODEN_DOORS).setCutoutRenderType().noOcclusion()));
    public static final DeferredHolder<Block, Block> RUNEWOOD_TRAPDOOR = BLOCKS.register("runewood_trapdoor", () -> new TrapDoorBlock(MalumBlockSetTypes.RUNEWOOD, MalumBlockProperties.RUNEWOOD().addTags(TRAPDOORS, WOODEN_TRAPDOORS).setCutoutRenderType().noOcclusion()));
    public static final DeferredHolder<Block, Block> SOLID_RUNEWOOD_TRAPDOOR = BLOCKS.register("solid_runewood_trapdoor", () -> new TrapDoorBlock(MalumBlockSetTypes.RUNEWOOD, MalumBlockProperties.RUNEWOOD().addTags(TRAPDOORS, WOODEN_TRAPDOORS).noOcclusion()));

    public static final DeferredHolder<Block, Block> RUNEWOOD_BUTTON = BLOCKS.register("runewood_planks_button", () -> new ButtonBlock(MalumBlockSetTypes.RUNEWOOD, 20, MalumBlockProperties.RUNEWOOD().addTags(BUTTONS, WOODEN_BUTTONS).addTags(BUTTONS, WOODEN_BUTTONS)));
    public static final DeferredHolder<Block, Block> RUNEWOOD_PRESSURE_PLATE = BLOCKS.register("runewood_planks_pressure_plate", () -> new PressurePlateBlock(MalumBlockSetTypes.RUNEWOOD, MalumBlockProperties.RUNEWOOD().addTags(PRESSURE_PLATES, WOODEN_PRESSURE_PLATES)));

    public static final DeferredHolder<Block, Block> RUNEWOOD_FENCE = BLOCKS.register("runewood_planks_fence", () -> new FenceBlock(MalumBlockProperties.RUNEWOOD().addTags(FENCES, WOODEN_FENCES)));
    public static final DeferredHolder<Block, Block> RUNEWOOD_FENCE_GATE = BLOCKS.register("runewood_planks_fence_gate", () -> new FenceGateBlock( WoodTypeRegistry.RUNEWOOD, MalumBlockProperties.RUNEWOOD().addTags(FENCE_GATES, FENCE_GATES_WOODEN)));

    public static final DeferredHolder<Block, Block> RUNEWOOD_BOARDS_WALL = BLOCKS.register("runewood_boards_wall", () -> new WallBlock(MalumBlockProperties.RUNEWOOD().addTags(WALLS)));

    public static final DeferredHolder<Block, Block> RUNEWOOD_ITEM_STAND = BLOCKS.register("runewood_item_stand", () -> new ItemStandBlock<>(MalumBlockProperties.RUNEWOOD().noOcclusion()).setBlockEntity(BlockEntityRegistry.ITEM_STAND));
    public static final DeferredHolder<Block, Block> RUNEWOOD_ITEM_PEDESTAL = BLOCKS.register("runewood_item_pedestal", () -> new WoodItemPedestalBlock<>(MalumBlockProperties.RUNEWOOD().noOcclusion()).setBlockEntity(BlockEntityRegistry.ITEM_PEDESTAL));
    public static final DeferredHolder<Block, Block> GILDED_RUNEWOOD_ITEM_STAND = BLOCKS.register("gilded_runewood_item_stand", () -> new ItemStandBlock<>(MalumBlockProperties.RUNEWOOD().noOcclusion()).setBlockEntity(BlockEntityRegistry.ITEM_STAND));
    public static final DeferredHolder<Block, Block> GILDED_RUNEWOOD_ITEM_PEDESTAL = BLOCKS.register("gilded_runewood_item_pedestal", () -> new WoodItemPedestalBlock<>(MalumBlockProperties.RUNEWOOD().setCutoutRenderType().noOcclusion()).setBlockEntity(BlockEntityRegistry.ITEM_PEDESTAL));

    public static final DeferredHolder<Block, Block> RUNEWOOD_SIGN = BLOCKS.register("runewood_sign", () -> new LodestoneStandingSignBlock(WoodTypeRegistry.RUNEWOOD, MalumBlockProperties.RUNEWOOD().addTags(SIGNS, STANDING_SIGNS).noOcclusion().noCollission()));
    public static final DeferredHolder<Block, Block> RUNEWOOD_WALL_SIGN = BLOCKS.register("runewood_wall_sign", () -> new LodestoneWallSignBlock(WoodTypeRegistry.RUNEWOOD, MalumBlockProperties.RUNEWOOD().addTags(SIGNS, WALL_SIGNS).noOcclusion().noCollission()));
    //endregion

    //region soulwood
    public static final DeferredHolder<Block, Block> SOULWOOD_GROWTH = BLOCKS.register("soulwood_growth", () -> new SoulwoodGrowthBlock(TreeGrowerRegistry.SOULWOOD, MalumBlockProperties.BLIGHTED_PLANTS().setCutoutRenderType().randomTicks()));
    public static final DeferredHolder<Block, Block> SOULWOOD_LEAVES = BLOCKS.register("soulwood_leaves", () -> new MalumLeavesBlock(MalumBlockProperties.SOULWOOD_LEAVES().setCutoutRenderType(), new Color(213, 8, 63), new Color(255, 61, 243)));
    public static final DeferredHolder<Block, Block> BUDDING_SOULWOOD_LEAVES = BLOCKS.register("budding_soulwood_leaves", () -> new MalumLeavesBlock(MalumBlockProperties.SOULWOOD_LEAVES().setCutoutRenderType(), new Color(213, 8, 63), new Color(255, 61, 243)));
    public static final DeferredHolder<Block, Block> HANGING_SOULWOOD_LEAVES = BLOCKS.register("hanging_soulwood_leaves", () -> new MalumHangingLeavesBlock(MalumBlockProperties.HANGING_SOULWOOD_LEAVES().setCutoutRenderType().noOcclusion().noCollission(), new Color(213, 8, 63), new Color(255, 61, 243)));

    public static final DeferredHolder<Block, Block> STRIPPED_SOULWOOD_LOG = BLOCKS.register("stripped_soulwood_log", () -> new RotatedPillarBlock(MalumBlockProperties.SOULWOOD().addTags(LOGS, STRIPPED_LOGS, SOULWOOD_LOGS)));
    public static final DeferredHolder<Block, Block> SOULWOOD_LOG = BLOCKS.register("soulwood_log", () -> new SoulwoodLogBlock(MalumBlockProperties.SOULWOOD().addTags(LOGS, SOULWOOD_LOGS), STRIPPED_SOULWOOD_LOG, true));
    public static final DeferredHolder<Block, Block> STRIPPED_SOULWOOD = BLOCKS.register("stripped_soulwood", () -> new RotatedPillarBlock(MalumBlockProperties.SOULWOOD().addTags(LOGS, STRIPPED_LOGS, SOULWOOD_LOGS)));
    public static final DeferredHolder<Block, Block> SOULWOOD = BLOCKS.register("soulwood", () -> new SoulwoodBlock(MalumBlockProperties.SOULWOOD().addTags(LOGS, SOULWOOD_LOGS), STRIPPED_SOULWOOD));

    public static final DeferredHolder<Block, Block> REVEALED_SOULWOOD_LOG = BLOCKS.register("revealed_soulwood_log", () -> new SapFilledSoulwoodLogBlock(MalumBlockProperties.SOULWOOD().addTags(LOGS, STRIPPED_LOGS, SOULWOOD_LOGS), STRIPPED_SOULWOOD_LOG, ItemRegistry.CURSED_SAP, new Color(214, 46, 83)));
    public static final DeferredHolder<Block, Block> EXPOSED_SOULWOOD_LOG = BLOCKS.register("exposed_soulwood_log", () -> new SoulwoodBlock(MalumBlockProperties.SOULWOOD().addTags(LOGS, SOULWOOD_LOGS), REVEALED_SOULWOOD_LOG));

    public static final DeferredHolder<Block, Block> SOULWOOD_BOARDS = BLOCKS.register("soulwood_boards", () -> new Block(MalumBlockProperties.SOULWOOD().addTags(PLANKS)));
    public static final DeferredHolder<Block, Block> SOULWOOD_BOARDS_SLAB = BLOCKS.register("soulwood_boards_slab", () -> new SlabBlock(MalumBlockProperties.SOULWOOD().addTags(SLABS, WOODEN_SLABS)));
    public static final DeferredHolder<Block, Block> SOULWOOD_BOARDS_STAIRS = BLOCKS.register("soulwood_boards_stairs", () -> new StairBlock(SOULWOOD_BOARDS.get().defaultBlockState(), MalumBlockProperties.SOULWOOD().addTags(STAIRS, WOODEN_STAIRS)));

    public static final DeferredHolder<Block, Block> VERTICAL_SOULWOOD_BOARDS = BLOCKS.register("vertical_soulwood_boards", () -> new Block(MalumBlockProperties.SOULWOOD().addTags(PLANKS)));
    public static final DeferredHolder<Block, Block> VERTICAL_SOULWOOD_BOARDS_SLAB = BLOCKS.register("vertical_soulwood_boards_slab", () -> new SlabBlock(MalumBlockProperties.SOULWOOD().addTags(SLABS, WOODEN_SLABS)));
    public static final DeferredHolder<Block, Block> VERTICAL_SOULWOOD_BOARDS_STAIRS = BLOCKS.register("vertical_soulwood_boards_stairs", () -> new StairBlock(VERTICAL_SOULWOOD_BOARDS.get().defaultBlockState(), MalumBlockProperties.SOULWOOD().addTags(STAIRS, WOODEN_STAIRS)));

    public static final DeferredHolder<Block, Block> SOULWOOD_PLANKS = BLOCKS.register("soulwood_planks", () -> new Block(MalumBlockProperties.SOULWOOD().addTags(PLANKS)));
    public static final DeferredHolder<Block, Block> SOULWOOD_PLANKS_SLAB = BLOCKS.register("soulwood_planks_slab", () -> new SlabBlock(MalumBlockProperties.SOULWOOD().addTags(SLABS, WOODEN_SLABS)));
    public static final DeferredHolder<Block, Block> SOULWOOD_PLANKS_STAIRS = BLOCKS.register("soulwood_planks_stairs", () -> new StairBlock(SOULWOOD_PLANKS.get().defaultBlockState(), MalumBlockProperties.SOULWOOD().addTags(STAIRS, WOODEN_STAIRS)));

    public static final DeferredHolder<Block, Block> RUSTIC_SOULWOOD_PLANKS = BLOCKS.register("rustic_soulwood_planks", () -> new Block(MalumBlockProperties.SOULWOOD().addTags(PLANKS)));
    public static final DeferredHolder<Block, Block> RUSTIC_SOULWOOD_PLANKS_SLAB = BLOCKS.register("rustic_soulwood_planks_slab", () -> new SlabBlock(MalumBlockProperties.SOULWOOD().addTags(SLABS, WOODEN_SLABS)));
    public static final DeferredHolder<Block, Block> RUSTIC_SOULWOOD_PLANKS_STAIRS = BLOCKS.register("rustic_soulwood_planks_stairs", () -> new StairBlock(RUSTIC_SOULWOOD_PLANKS.get().defaultBlockState(), MalumBlockProperties.SOULWOOD().addTags(STAIRS, WOODEN_STAIRS)));

    public static final DeferredHolder<Block, Block> VERTICAL_SOULWOOD_PLANKS = BLOCKS.register("vertical_soulwood_planks", () -> new Block(MalumBlockProperties.SOULWOOD().addTags(PLANKS)));
    public static final DeferredHolder<Block, Block> VERTICAL_SOULWOOD_PLANKS_SLAB = BLOCKS.register("vertical_soulwood_planks_slab", () -> new SlabBlock(MalumBlockProperties.SOULWOOD().addTags(SLABS, WOODEN_SLABS)));
    public static final DeferredHolder<Block, Block> VERTICAL_SOULWOOD_PLANKS_STAIRS = BLOCKS.register("vertical_soulwood_planks_stairs", () -> new StairBlock(VERTICAL_SOULWOOD_PLANKS.get().defaultBlockState(), MalumBlockProperties.SOULWOOD().addTags(STAIRS, WOODEN_STAIRS)));

    public static final DeferredHolder<Block, Block> VERTICAL_RUSTIC_SOULWOOD_PLANKS = BLOCKS.register("vertical_rustic_soulwood_planks", () -> new Block(MalumBlockProperties.SOULWOOD().addTags(PLANKS)));
    public static final DeferredHolder<Block, Block> VERTICAL_RUSTIC_SOULWOOD_PLANKS_SLAB = BLOCKS.register("vertical_rustic_soulwood_planks_slab", () -> new SlabBlock(MalumBlockProperties.SOULWOOD().addTags(SLABS, WOODEN_SLABS)));
    public static final DeferredHolder<Block, Block> VERTICAL_RUSTIC_SOULWOOD_PLANKS_STAIRS = BLOCKS.register("vertical_rustic_soulwood_planks_stairs", () -> new StairBlock(VERTICAL_RUSTIC_SOULWOOD_PLANKS.get().defaultBlockState(), MalumBlockProperties.SOULWOOD().addTags(STAIRS, WOODEN_STAIRS)));

    public static final DeferredHolder<Block, Block> SOULWOOD_TILES = BLOCKS.register("soulwood_tiles", () -> new Block(MalumBlockProperties.SOULWOOD().addTags(PLANKS)));
    public static final DeferredHolder<Block, Block> SOULWOOD_TILES_SLAB = BLOCKS.register("soulwood_tiles_slab", () -> new SlabBlock(MalumBlockProperties.SOULWOOD().addTags(SLABS, WOODEN_SLABS)));
    public static final DeferredHolder<Block, Block> SOULWOOD_TILES_STAIRS = BLOCKS.register("soulwood_tiles_stairs", () -> new StairBlock(SOULWOOD_TILES.get().defaultBlockState(), MalumBlockProperties.SOULWOOD().addTags(STAIRS, WOODEN_STAIRS)));

    public static final DeferredHolder<Block, Block> RUSTIC_SOULWOOD_TILES = BLOCKS.register("rustic_soulwood_tiles", () -> new Block(MalumBlockProperties.SOULWOOD().addTags(PLANKS)));
    public static final DeferredHolder<Block, Block> RUSTIC_SOULWOOD_TILES_SLAB = BLOCKS.register("rustic_soulwood_tiles_slab", () -> new SlabBlock(MalumBlockProperties.SOULWOOD().addTags(SLABS, WOODEN_SLABS)));
    public static final DeferredHolder<Block, Block> RUSTIC_SOULWOOD_TILES_STAIRS = BLOCKS.register("rustic_soulwood_tiles_stairs", () -> new StairBlock(RUSTIC_SOULWOOD_TILES.get().defaultBlockState(), MalumBlockProperties.SOULWOOD().addTags(STAIRS, WOODEN_STAIRS)));

    public static final DeferredHolder<Block, Block> SOULWOOD_PANEL = BLOCKS.register("soulwood_panel", () -> new Block(MalumBlockProperties.SOULWOOD()));
    public static final DeferredHolder<Block, Block> CUT_SOULWOOD_PLANKS = BLOCKS.register("cut_soulwood_planks", () -> new Block(MalumBlockProperties.SOULWOOD().addTags(PLANKS)));
    public static final DeferredHolder<Block, Block> SOULWOOD_BEAM = BLOCKS.register("soulwood_beam", () -> new RotatedPillarBlock(MalumBlockProperties.SOULWOOD()));

    public static final DeferredHolder<Block, Block> SOULWOOD_DOOR = BLOCKS.register("soulwood_door", () -> new DoorBlock(MalumBlockSetTypes.SOULWOOD, MalumBlockProperties.SOULWOOD().addTags(DOORS, WOODEN_DOORS).setCutoutRenderType().noOcclusion()));
    public static final DeferredHolder<Block, Block> SOULWOOD_TRAPDOOR = BLOCKS.register("soulwood_trapdoor", () -> new TrapDoorBlock(MalumBlockSetTypes.SOULWOOD, MalumBlockProperties.SOULWOOD().addTags(TRAPDOORS, WOODEN_TRAPDOORS).setCutoutRenderType().noOcclusion()));
    public static final DeferredHolder<Block, Block> SOLID_SOULWOOD_TRAPDOOR = BLOCKS.register("solid_soulwood_trapdoor", () -> new TrapDoorBlock( MalumBlockSetTypes.SOULWOOD, MalumBlockProperties.SOULWOOD().addTags(TRAPDOORS, WOODEN_TRAPDOORS).noOcclusion()));

    public static final DeferredHolder<Block, Block> SOULWOOD_BUTTON = BLOCKS.register("soulwood_planks_button", () -> new ButtonBlock(MalumBlockSetTypes.SOULWOOD, 20, MalumBlockProperties.SOULWOOD().addTags(BUTTONS, WOODEN_BUTTONS)));
    public static final DeferredHolder<Block, Block> SOULWOOD_PRESSURE_PLATE = BLOCKS.register("soulwood_planks_pressure_plate", () -> new PressurePlateBlock(MalumBlockSetTypes.SOULWOOD, MalumBlockProperties.SOULWOOD().addTags(PRESSURE_PLATES, WOODEN_PRESSURE_PLATES)));

    public static final DeferredHolder<Block, Block> SOULWOOD_FENCE = BLOCKS.register("soulwood_planks_fence", () -> new FenceBlock(MalumBlockProperties.SOULWOOD().addTags(FENCES, WOODEN_FENCES)));
    public static final DeferredHolder<Block, Block> SOULWOOD_FENCE_GATE = BLOCKS.register("soulwood_planks_fence_gate", () -> new FenceGateBlock(WoodTypeRegistry.SOULWOOD, MalumBlockProperties.SOULWOOD().addTags(FENCE_GATES, FENCE_GATES_WOODEN)));

    public static final DeferredHolder<Block, Block> SOULWOOD_BOARDS_WALL = BLOCKS.register("soulwood_boards_wall", () -> new WallBlock(MalumBlockProperties.SOULWOOD().addTags(WALLS)));

    public static final DeferredHolder<Block, Block> SOULWOOD_ITEM_STAND = BLOCKS.register("soulwood_item_stand", () -> new ItemStandBlock<>(MalumBlockProperties.SOULWOOD().noOcclusion()).setBlockEntity(BlockEntityRegistry.ITEM_STAND));
    public static final DeferredHolder<Block, Block> SOULWOOD_ITEM_PEDESTAL = BLOCKS.register("soulwood_item_pedestal", () -> new WoodItemPedestalBlock<>(MalumBlockProperties.SOULWOOD().noOcclusion()).setBlockEntity(BlockEntityRegistry.ITEM_PEDESTAL));
    public static final DeferredHolder<Block, Block> ORNATE_SOULWOOD_ITEM_STAND = BLOCKS.register("ornate_soulwood_item_stand", () -> new ItemStandBlock<>(MalumBlockProperties.SOULWOOD().noOcclusion()).setBlockEntity(BlockEntityRegistry.ITEM_STAND));
    public static final DeferredHolder<Block, Block> ORNATE_SOULWOOD_ITEM_PEDESTAL = BLOCKS.register("ornate_soulwood_item_pedestal", () -> new WoodItemPedestalBlock<>(MalumBlockProperties.SOULWOOD().setCutoutRenderType().noOcclusion()).setBlockEntity(BlockEntityRegistry.ITEM_PEDESTAL));

    public static final DeferredHolder<Block, Block> SOULWOOD_SIGN = BLOCKS.register("soulwood_sign", () -> new LodestoneStandingSignBlock(WoodTypeRegistry.SOULWOOD, MalumBlockProperties.SOULWOOD().addTags(SIGNS, STANDING_SIGNS).noOcclusion().noCollission()));
    public static final DeferredHolder<Block, Block> SOULWOOD_WALL_SIGN = BLOCKS.register("soulwood_wall_sign", () -> new LodestoneWallSignBlock(WoodTypeRegistry.SOULWOOD, MalumBlockProperties.SOULWOOD().addTags(SIGNS, WALL_SIGNS).noOcclusion().noCollission()));
    //endregion

    //region blight
    public static final DeferredHolder<Block, Block> BLIGHTED_EARTH = BLOCKS.register("blighted_earth", () -> new BlightedSoilBlock(MalumBlockProperties.BLIGHT()));
    public static final DeferredHolder<Block, Block> BLIGHTED_SOIL = BLOCKS.register("blighted_soil", () -> new BlightedSoilBlock(MalumBlockProperties.BLIGHT()));
    public static final DeferredHolder<Block, Block> BLIGHTED_GROWTH = BLOCKS.register("blighted_growth", () -> new BlightedGrowthBlock(MalumBlockProperties.BLIGHTED_PLANTS().replaceable().setCutoutRenderType()));
    public static final DeferredHolder<Block, Block> CLINGING_BLIGHT = BLOCKS.register("clinging_blight", () -> new ClingingBlightBlock(MalumBlockProperties.BLIGHTED_PLANTS().replaceable().setCutoutRenderType()));
    public static final DeferredHolder<Block, Block> BLIGHTED_SOULWOOD = BLOCKS.register("blighted_soulwood", () -> new BlightedSoulwoodBlock(MalumBlockProperties.SOULWOOD().addTags(LOGS, SOULWOOD_LOGS)));

    public static final DeferredHolder<Block, Block> CALCIFIED_BLIGHT = BLOCKS.register("calcified_blight", () -> new CalcifiedBlightBlock(MalumBlockProperties.CALCIFIED_BLIGHT().setCutoutRenderType()));
    public static final DeferredHolder<Block, Block> TALL_CALCIFIED_BLIGHT = BLOCKS.register("tall_calcified_blight", () -> new TallCalcifiedBlightBlock(MalumBlockProperties.CALCIFIED_BLIGHT().setCutoutRenderType()));
    //endregion

    //region ether
    public static final DeferredHolder<Block, Block> ETHER = BLOCKS.register("ether", () -> new EtherBlock<>(MalumBlockProperties.ETHER()).setBlockEntity(BlockEntityRegistry.ETHER));
    public static final DeferredHolder<Block, Block> ETHER_TORCH = BLOCKS.register("ether_torch", () -> new EtherTorchBlock<>(MalumBlockProperties.ETHER_TORCH().noCollission().instabreak().lightLevel((b) -> 14)).setBlockEntity(BlockEntityRegistry.ETHER));
    public static final DeferredHolder<Block, Block> WALL_ETHER_TORCH = BLOCKS.register("wall_ether_torch", () -> new EtherWallTorchBlock<>(MalumBlockProperties.ETHER_TORCH().noCollission().instabreak().lightLevel((b) -> 14).lootFrom(ETHER_TORCH)).setBlockEntity(BlockEntityRegistry.ETHER));
    public static final DeferredHolder<Block, Block> TAINTED_ETHER_BRAZIER = BLOCKS.register("tainted_ether_brazier", () -> new EtherBrazierBlock<>(MalumBlockProperties.TAINTED_ETHER_BRAZIER().lightLevel((b) -> 14).noOcclusion()).setBlockEntity(BlockEntityRegistry.ETHER));
    public static final DeferredHolder<Block, Block> TWISTED_ETHER_BRAZIER = BLOCKS.register("twisted_ether_brazier", () -> new EtherBrazierBlock<>(MalumBlockProperties.TWISTED_ETHER_BRAZIER().lightLevel((b) -> 14).noOcclusion()).setBlockEntity(BlockEntityRegistry.ETHER));

    public static final DeferredHolder<Block, Block> IRIDESCENT_ETHER = BLOCKS.register("iridescent_ether", () -> new EtherBlock<>(MalumBlockProperties.ETHER()).setBlockEntity(BlockEntityRegistry.ETHER));
    public static final DeferredHolder<Block, Block> IRIDESCENT_ETHER_TORCH = BLOCKS.register("iridescent_ether_torch", () -> new EtherTorchBlock<>(MalumBlockProperties.ETHER_TORCH().noCollission().instabreak().lightLevel((b) -> 14)).setBlockEntity(BlockEntityRegistry.ETHER));
    public static final DeferredHolder<Block, Block> IRIDESCENT_WALL_ETHER_TORCH = BLOCKS.register("iridescent_wall_ether_torch", () -> new EtherWallTorchBlock<>(MalumBlockProperties.ETHER_TORCH().noCollission().instabreak().lightLevel((b) -> 14).lootFrom(IRIDESCENT_ETHER_TORCH)).setBlockEntity(BlockEntityRegistry.ETHER));
    public static final DeferredHolder<Block, Block> TAINTED_IRIDESCENT_ETHER_BRAZIER = BLOCKS.register("tainted_iridescent_ether_brazier", () -> new EtherBrazierBlock<>(MalumBlockProperties.TAINTED_ETHER_BRAZIER().lightLevel((b) -> 14).noOcclusion()).setBlockEntity(BlockEntityRegistry.ETHER));
    public static final DeferredHolder<Block, Block> TWISTED_IRIDESCENT_ETHER_BRAZIER = BLOCKS.register("twisted_iridescent_ether_brazier", () -> new EtherBrazierBlock<>(MalumBlockProperties.TWISTED_ETHER_BRAZIER().lightLevel((b) -> 14).noOcclusion()).setBlockEntity(BlockEntityRegistry.ETHER));
    //endregion

    public static final DeferredHolder<Block, Block> SOULSTONE_ORE = BLOCKS.register("soulstone_ore", () -> new DropExperienceBlock(UniformInt.of(14, 18), MalumBlockProperties.SOULSTONE_ORE(false)));
    public static final DeferredHolder<Block, Block> DEEPSLATE_SOULSTONE_ORE = BLOCKS.register("deepslate_soulstone_ore", () -> new DropExperienceBlock(UniformInt.of(14, 18), MalumBlockProperties.SOULSTONE_ORE(true)));
    public static final DeferredHolder<Block, Block> BLOCK_OF_RAW_SOULSTONE = BLOCKS.register("block_of_raw_soulstone", () -> new Block(MalumBlockProperties.SOULSTONE_BLOCK()));
    public static final DeferredHolder<Block, Block> BLOCK_OF_SOULSTONE = BLOCKS.register("block_of_soulstone", () -> new Block(MalumBlockProperties.SOULSTONE_BLOCK()));

    public static final DeferredHolder<Block, Block> BRILLIANT_STONE = BLOCKS.register("brilliant_stone", () -> new DropExperienceBlock(UniformInt.of(14, 18), MalumBlockProperties.BRILLIANCE_ORE(false).setCutoutRenderType()));
    public static final DeferredHolder<Block, Block> BRILLIANT_DEEPSLATE = BLOCKS.register("brilliant_deepslate", () -> new DropExperienceBlock(UniformInt.of(16, 26), MalumBlockProperties.BRILLIANCE_ORE(true).setCutoutRenderType()));
    public static final DeferredHolder<Block, Block> BLOCK_OF_BRILLIANCE = BLOCKS.register("block_of_brilliance", () -> new Block(MalumBlockProperties.BRILLIANCE_BLOCK()));

    public static final DeferredHolder<Block, Block> BLOCK_OF_ARCANE_CHARCOAL = BLOCKS.register("block_of_arcane_charcoal", () -> new Block(MalumBlockProperties.ARCANE_CHARCOAL_BLOCK()));

    public static final DeferredHolder<Block, Block> BLAZING_QUARTZ_ORE = BLOCKS.register("blazing_quartz_ore", () -> new DropExperienceBlock(UniformInt.of(4, 7), MalumBlockProperties.BLAZING_QUARTZ_ORE().setCutoutRenderType().lightLevel((b) -> 6)));
    public static final DeferredHolder<Block, Block> BLAZING_QUARTZ_CLUSTER = BLOCKS.register("blazing_quartz_cluster", () -> new AmethystClusterBlock(4, 3, MalumBlockProperties.BLAZING_QUARTZ_CLUSTER().setCutoutRenderType().lightLevel((b) -> 14)));
    public static final DeferredHolder<Block, Block> BLOCK_OF_BLAZING_QUARTZ = BLOCKS.register("block_of_blazing_quartz", () -> new Block(MalumBlockProperties.BLAZING_QUARTZ_BLOCK().lightLevel((b) -> 14)));

    public static final DeferredHolder<Block, Block> NATURAL_QUARTZ_ORE = BLOCKS.register("natural_quartz_ore", () -> new DropExperienceBlock(UniformInt.of(1, 4), MalumBlockProperties.NATURAL_QUARTZ_ORE(false).setCutoutRenderType()));
    public static final DeferredHolder<Block, Block> DEEPSLATE_QUARTZ_ORE = BLOCKS.register("deepslate_quartz_ore", () -> new DropExperienceBlock(UniformInt.of(2, 5), MalumBlockProperties.NATURAL_QUARTZ_ORE(true).setCutoutRenderType()));
    public static final DeferredHolder<Block, Block> NATURAL_QUARTZ_CLUSTER = BLOCKS.register("natural_quartz_cluster", () -> new AmethystClusterBlock(6, 3, MalumBlockProperties.NATURAL_QUARTZ_CLUSTER().setCutoutRenderType()));

    public static final DeferredHolder<Block, Block> CTHONIC_GOLD_ORE = BLOCKS.register("cthonic_gold_ore", () -> new DropExperienceBlock(UniformInt.of(10, 100), MalumBlockProperties.CTHONIC_GOLD_ORE()));
    public static final DeferredHolder<Block, Block> CTHONIC_GOLD_CLUSTER = BLOCKS.register("cthonic_gold_cluster", () -> new AmethystClusterBlock(4, 3, MalumBlockProperties.CTHONIC_GOLD_CLUSTER().setCutoutRenderType()));
    public static final DeferredHolder<Block, Block> BLOCK_OF_CTHONIC_GOLD = BLOCKS.register("block_of_cthonic_gold", () -> new Block(MalumBlockProperties.CTHONIC_GOLD_BLOCK()));

    public static final DeferredHolder<Block, Block> BLOCK_OF_ROTTING_ESSENCE = BLOCKS.register("block_of_rotting_essence", () -> new Block(new LodestoneBlockProperties().needsPickaxe().addTags(STORAGE_BLOCKS).strength(1F, 6.0F).sound(SoundType.CORAL_BLOCK)));
    public static final DeferredHolder<Block, Block> BLOCK_OF_GRIM_TALC = BLOCKS.register("block_of_grim_talc", () -> new RotatedPillarBlock(LodestoneBlockProperties.copy(Blocks.BONE_BLOCK).needsPickaxe().addTags(STORAGE_BLOCKS)));
    public static final DeferredHolder<Block, Block> BLOCK_OF_ASTRAL_WEAVE = BLOCKS.register("block_of_astral_weave", () -> new Block(LodestoneBlockProperties.copy(Blocks.LIGHT_BLUE_WOOL).needsPickaxe().needsHoe().addTags(STORAGE_BLOCKS)));
    //    public static final DeferredHolder<Block, Block> BLOCK_OF_WARP_FLUX = BLOCKS.register("block_of_warp_flux", () -> new Block(LodestoneBlockProperties.copy(Blocks.LIGHT_BLUE_WOOL).needsPickaxe().needsHoe().addTags(STORAGE_BLOCKS)));
    public static final DeferredHolder<Block, Block> BLOCK_OF_HEX_ASH = BLOCKS.register("block_of_hex_ash", () -> new Block(LodestoneBlockProperties.copy(Blocks.PURPLE_CONCRETE_POWDER).needsPickaxe().needsHoe().addTags(STORAGE_BLOCKS)));
    public static final DeferredHolder<Block, Block> BLOCK_OF_LIVING_FLESH = BLOCKS.register("block_of_living_flesh", () -> new Block(new LodestoneBlockProperties().needsPickaxe().addTags(STORAGE_BLOCKS).strength(1F, 6.0F).sound(SoundType.CORAL_BLOCK)));
    public static final DeferredHolder<Block, Block> BLOCK_OF_ALCHEMICAL_CALX = BLOCKS.register("block_of_alchemical_calx", () -> new Block(LodestoneBlockProperties.copy(Blocks.CALCITE).needsPickaxe().addTags(STORAGE_BLOCKS)));
    public static final DeferredHolder<Block, Block> MASS_OF_BLIGHTED_GUNK = BLOCKS.register("mass_of_blighted_gunk", () -> new Block(MalumBlockProperties.BLIGHT().needsPickaxe().addTags(STORAGE_BLOCKS)));
    public static final DeferredHolder<Block, Block> BLOCK_OF_NULL_SLATE = BLOCKS.register("block_of_null_slate", () -> new Block(MalumBlockProperties.SOULSTONE_BLOCK()));
    public static final DeferredHolder<Block, Block> BLOCK_OF_VOID_SALTS = BLOCKS.register("block_of_void_salts", () -> new Block(LodestoneBlockProperties.copy(Blocks.BLACK_CONCRETE_POWDER).needsPickaxe().needsShovel().addTags(STORAGE_BLOCKS)));
    public static final DeferredHolder<Block, Block> BLOCK_OF_MNEMONIC_FRAGMENT = BLOCKS.register("block_of_mnemonic_fragment", () -> new Block(MalumBlockProperties.BRILLIANCE_BLOCK()));
    public static final DeferredHolder<Block, Block> BLOCK_OF_AURIC_EMBERS = BLOCKS.register("block_of_auric_embers", () -> new Block(LodestoneBlockProperties.copy(Blocks.GLOWSTONE).needsPickaxe().addTags(STORAGE_BLOCKS)));
    public static final DeferredHolder<Block, Block> BLOCK_OF_MALIGNANT_LEAD = BLOCKS.register("block_of_malignant_lead", () -> new Block(MalumBlockProperties.MALIGNANT_LEAD_BLOCK()));

    public static final DeferredHolder<Block, Block> BLOCK_OF_SOUL_STAINED_STEEL = BLOCKS.register("block_of_soul_stained_steel", () -> new Block(MalumBlockProperties.SOUL_STAINED_STEEL_BLOCK()));
    public static final DeferredHolder<Block, Block> BLOCK_OF_HALLOWED_GOLD = BLOCKS.register("block_of_hallowed_gold", () -> new Block(MalumBlockProperties.HALLOWED_GOLD()));
    public static final DeferredHolder<Block, Block> BLOCK_OF_MALIGNANT_PEWTER = BLOCKS.register("block_of_malignant_pewter", () -> new Block(MalumBlockProperties.MALIGNANT_PEWTER_BLOCK()));

    public static final DeferredHolder<Block, Block> RUNIC_SAP_BLOCK = BLOCKS.register("runic_sap_block", () -> new Block(MalumBlockProperties.RUNIC_SAP()));
    public static final DeferredHolder<Block, Block> CURSED_SAP_BLOCK = BLOCKS.register("cursed_sap_block", () -> new Block(MalumBlockProperties.CURSED_SAP()));

    public static final DeferredHolder<Block, Block> THE_DEVICE = BLOCKS.register("the_device", () -> new TheDevice(MalumBlockProperties.TAINTED_ROCK()));
    public static final DeferredHolder<Block, Block> THE_VESSEL = BLOCKS.register("the_vessel", () -> new TheVessel(MalumBlockProperties.TWISTED_ROCK()));

    @EventBusSubscriber(modid = MalumMod.MALUM, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
    public static class ClientOnly {

        @SubscribeEvent
        public static void setBlockColors(RegisterColorHandlersEvent.Block event) {
            BlockColors blockColors = event.getBlockColors();
            blockColors.register((s, l, p, c) -> {
                BlockEntity blockEntity = l.getBlockEntity(p);
                if (blockEntity instanceof EtherBlockEntity etherBlockEntity) {
                    if (etherBlockEntity.firstColor != null) {
                        return c == 0 ? etherBlockEntity.firstColor.getRGB() : -1;
                    }
                }
                return -1;
            }, ETHER.get(), IRIDESCENT_ETHER.get());

            var colorProperty = MalumLeavesBlock.COLOR;
            blockColors.register((s, l, p, c) -> {
                float colorMax = colorProperty.getPossibleValues().size();
                float color = s.getValue(colorProperty);
                float pct = (colorMax - (color / colorMax));
                float value = Easing.SINE_IN_OUT.ease(pct, 0, 1, 1);
                var leaves = (IGradientedLeavesBlock) s.getBlock();
                int red = (int) Mth.lerp(value, leaves.getMinColor().getRed(), leaves.getMaxColor().getRed());
                int green = (int) Mth.lerp(value, leaves.getMinColor().getGreen(), leaves.getMaxColor().getGreen());
                int blue = (int) Mth.lerp(value, leaves.getMinColor().getBlue(), leaves.getMaxColor().getBlue());
                return red << 16 | green << 8 | blue;
            }, RUNEWOOD_LEAVES.get(), HANGING_RUNEWOOD_LEAVES.get(), AZURE_RUNEWOOD_LEAVES.get(), HANGING_AZURE_RUNEWOOD_LEAVES.get());

            blockColors.register((s, l, p, c) -> {
                float distanceMax = MalumLeavesBlock.DISTANCE.getPossibleValues().size();
                float distance = s.getBlock() instanceof MalumLeavesBlock ? s.getValue(MalumLeavesBlock.DISTANCE) : distanceMax;
                float colorMax = colorProperty.getPossibleValues().size();
                float color = s.getValue(colorProperty);
                float pct = Math.max((distanceMax - distance) / distanceMax, color / colorMax);
                float value = Easing.SINE_IN_OUT.ease(pct, 0, 1, 1);
                var leaves = (IGradientedLeavesBlock) s.getBlock();
                int red = (int) Mth.lerp(value, leaves.getMinColor().getRed(), leaves.getMaxColor().getRed());
                int green = (int) Mth.lerp(value, leaves.getMinColor().getGreen(), leaves.getMaxColor().getGreen());
                int blue = (int) Mth.lerp(value, leaves.getMinColor().getBlue(), leaves.getMaxColor().getBlue());
                return red << 16 | green << 8 | blue;
            }, SOULWOOD_LEAVES.get(), BUDDING_SOULWOOD_LEAVES.get(), HANGING_SOULWOOD_LEAVES.get());

            blockColors.register((s, l, p, c) -> {
                var spiritType = MalumSpiritType.getSpiritType(s.getValue(ManaMoteBlock.SPIRIT_TYPE));
                var color  = spiritType.getPrimaryColor();
                int red = color.getRed();
                int green = color.getGreen();
                int blue = color.getBlue();
                return red << 16 | green << 8 | blue;
            }, SPIRIT_MOTE.get());
        }
    }
}