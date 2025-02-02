package com.sammy.malum.data.block;

import com.sammy.malum.*;
import com.sammy.malum.common.block.curiosities.banner.*;
import com.sammy.malum.data.item.*;
import net.minecraft.data.*;
import net.minecraft.resources.*;
import net.minecraft.world.level.block.*;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.systems.datagen.*;
import team.lodestar.lodestone.systems.datagen.providers.*;
import team.lodestar.lodestone.systems.datagen.statesmith.*;

import javax.annotation.*;
import java.util.*;
import java.util.function.*;

import static com.sammy.malum.MalumMod.*;
import static com.sammy.malum.registry.common.block.BlockRegistry.*;

public class MalumBlockStates extends LodestoneBlockStateProvider {

    public MalumBlockStates(PackOutput output, ExistingFileHelper exFileHelper, LodestoneItemModelProvider itemModelProvider) {
        super(output, MALUM, exFileHelper, itemModelProvider);
    }

    @Nonnull
    @Override
    public String getName() {
        return "Malum BlockStates";
    }

    @Override
    protected void registerStatesAndModels() {
        Set<Supplier<? extends Block>> blocks = new HashSet<>(BLOCKS.getEntries());

        AbstractBlockStateSmith.StateSmithData data = new AbstractBlockStateSmith.StateSmithData(this, blocks::remove);

        setTexturePath("banners/");
        MalumBlockStateSmithTypes.SOULWOVEN_BANNER.act(data, DataHelper.takeAll(blocks, b -> b.get() instanceof SoulwovenBannerBlock));
        setTexturePath("spirited_glass/");
        BlockStateSmithTypes.FULL_BLOCK.act(data,
                SACRED_SPIRITED_GLASS, WICKED_SPIRITED_GLASS, ARCANE_SPIRITED_GLASS, ELDRITCH_SPIRITED_GLASS,
                AERIAL_SPIRITED_GLASS, AQUEOUS_SPIRITED_GLASS, INFERNAL_SPIRITED_GLASS, EARTHEN_SPIRITED_GLASS);

        setTexturePath("arcane_rock/tainted/");
        BlockStateSmithTypes.FULL_BLOCK.act(data,
                TAINTED_ROCK, POLISHED_TAINTED_ROCK, SMOOTH_TAINTED_ROCK,
                TAINTED_ROCK_BRICKS, TAINTED_ROCK_TILES, SMALL_TAINTED_ROCK_BRICKS,
                RUNIC_TAINTED_ROCK_BRICKS, RUNIC_TAINTED_ROCK_TILES, RUNIC_SMALL_TAINTED_ROCK_BRICKS,
                CHISELED_TAINTED_ROCK);

        BlockStateSmithTypes.SLAB_BLOCK.act(data, TAINTED_ROCK_SLAB, POLISHED_TAINTED_ROCK_SLAB, SMOOTH_TAINTED_ROCK_SLAB,
                TAINTED_ROCK_BRICKS_SLAB, TAINTED_ROCK_TILES_SLAB, SMALL_TAINTED_ROCK_BRICKS_SLAB,
                RUNIC_TAINTED_ROCK_BRICKS_SLAB, RUNIC_TAINTED_ROCK_TILES_SLAB, RUNIC_SMALL_TAINTED_ROCK_BRICKS_SLAB);

        BlockStateSmithTypes.STAIRS_BLOCK.act(data, TAINTED_ROCK_STAIRS, POLISHED_TAINTED_ROCK_STAIRS, SMOOTH_TAINTED_ROCK_STAIRS,
                TAINTED_ROCK_BRICKS_STAIRS, TAINTED_ROCK_TILES_STAIRS, SMALL_TAINTED_ROCK_BRICKS_STAIRS,
                RUNIC_TAINTED_ROCK_BRICKS_STAIRS, RUNIC_TAINTED_ROCK_TILES_STAIRS, RUNIC_SMALL_TAINTED_ROCK_BRICKS_STAIRS);

        BlockStateSmithTypes.WALL_BLOCK.act(data,
                TAINTED_ROCK_WALL, SMOOTH_TAINTED_ROCK_WALL, POLISHED_TAINTED_ROCK_WALL,
                TAINTED_ROCK_BRICKS_WALL, TAINTED_ROCK_TILES_WALL, SMALL_TAINTED_ROCK_BRICKS_WALL,
                RUNIC_TAINTED_ROCK_BRICKS_WALL, RUNIC_TAINTED_ROCK_TILES_WALL, RUNIC_SMALL_TAINTED_ROCK_BRICKS_WALL);

        BlockStateSmithTypes.CUSTOM_MODEL.act(data, this::simpleBlock, this::cutRockBlockModel, CUT_TAINTED_ROCK, CHECKERED_TAINTED_ROCK);
        BlockStateSmithTypes.CUSTOM_MODEL.act(data, this::directionalBlock, this::columnCapModel, TAINTED_ROCK_COLUMN_CAP);
        BlockStateSmithTypes.CUSTOM_MODEL.act(data, this::simpleBlock, this::rockItemPedestalModel, TAINTED_ROCK_ITEM_PEDESTAL);
        BlockStateSmithTypes.CUSTOM_MODEL.act(data, this::directionalBlock, this::itemStandModel, TAINTED_ROCK_ITEM_STAND);

        BlockStateSmithTypes.LOG_BLOCK.act(data, TAINTED_ROCK_COLUMN);
        BlockStateSmithTypes.BUTTON_BLOCK.act(data, TAINTED_ROCK_BUTTON);
        BlockStateSmithTypes.PRESSURE_PLATE_BLOCK.act(data, TAINTED_ROCK_PRESSURE_PLATE);

        itemModelProvider.setTexturePath("ether/");
        MalumBlockStateSmithTypes.BRAZIER_BLOCK.act(data, TAINTED_ETHER_BRAZIER);
        MalumBlockStateSmithTypes.IRIDESCENT_BRAZIER_BLOCK.act(data, TAINTED_IRIDESCENT_ETHER_BRAZIER);
        itemModelProvider.setTexturePath("");

        setTexturePath("arcane_rock/twisted/");
        BlockStateSmithTypes.FULL_BLOCK.act(data,
                TWISTED_ROCK, POLISHED_TWISTED_ROCK, SMOOTH_TWISTED_ROCK,
                TWISTED_ROCK_BRICKS, TWISTED_ROCK_TILES, SMALL_TWISTED_ROCK_BRICKS,
                RUNIC_TWISTED_ROCK_BRICKS, RUNIC_TWISTED_ROCK_TILES, RUNIC_SMALL_TWISTED_ROCK_BRICKS,
                CHISELED_TWISTED_ROCK);

        BlockStateSmithTypes.SLAB_BLOCK.act(data, TWISTED_ROCK_SLAB, POLISHED_TWISTED_ROCK_SLAB, SMOOTH_TWISTED_ROCK_SLAB,
                TWISTED_ROCK_BRICKS_SLAB, TWISTED_ROCK_TILES_SLAB, SMALL_TWISTED_ROCK_BRICKS_SLAB,
                RUNIC_TWISTED_ROCK_BRICKS_SLAB, RUNIC_TWISTED_ROCK_TILES_SLAB, RUNIC_SMALL_TWISTED_ROCK_BRICKS_SLAB);

        BlockStateSmithTypes.STAIRS_BLOCK.act(data, TWISTED_ROCK_STAIRS, POLISHED_TWISTED_ROCK_STAIRS, SMOOTH_TWISTED_ROCK_STAIRS,
                TWISTED_ROCK_BRICKS_STAIRS, TWISTED_ROCK_TILES_STAIRS, SMALL_TWISTED_ROCK_BRICKS_STAIRS,
                RUNIC_TWISTED_ROCK_BRICKS_STAIRS, RUNIC_TWISTED_ROCK_TILES_STAIRS, RUNIC_SMALL_TWISTED_ROCK_BRICKS_STAIRS);

        BlockStateSmithTypes.WALL_BLOCK.act(data,
                TWISTED_ROCK_WALL, SMOOTH_TWISTED_ROCK_WALL, POLISHED_TWISTED_ROCK_WALL,
                TWISTED_ROCK_BRICKS_WALL, TWISTED_ROCK_TILES_WALL, SMALL_TWISTED_ROCK_BRICKS_WALL,
                RUNIC_TWISTED_ROCK_BRICKS_WALL, RUNIC_TWISTED_ROCK_TILES_WALL, RUNIC_SMALL_TWISTED_ROCK_BRICKS_WALL);

        BlockStateSmithTypes.CUSTOM_MODEL.act(data, this::simpleBlock, this::cutRockBlockModel, CUT_TWISTED_ROCK, CHECKERED_TWISTED_ROCK);
        BlockStateSmithTypes.CUSTOM_MODEL.act(data, this::directionalBlock, this::columnCapModel, TWISTED_ROCK_COLUMN_CAP);
        BlockStateSmithTypes.CUSTOM_MODEL.act(data, this::simpleBlock, this::rockItemPedestalModel, TWISTED_ROCK_ITEM_PEDESTAL);
        BlockStateSmithTypes.CUSTOM_MODEL.act(data, this::directionalBlock, this::itemStandModel, TWISTED_ROCK_ITEM_STAND);

        BlockStateSmithTypes.LOG_BLOCK.act(data, TWISTED_ROCK_COLUMN);
        BlockStateSmithTypes.BUTTON_BLOCK.act(data, TWISTED_ROCK_BUTTON);
        BlockStateSmithTypes.PRESSURE_PLATE_BLOCK.act(data, TWISTED_ROCK_PRESSURE_PLATE);

        itemModelProvider.setTexturePath("ether/");
        MalumBlockStateSmithTypes.BRAZIER_BLOCK.act(data, TWISTED_ETHER_BRAZIER);
        MalumBlockStateSmithTypes.IRIDESCENT_BRAZIER_BLOCK.act(data, TWISTED_IRIDESCENT_ETHER_BRAZIER);
        itemModelProvider.setTexturePath("");

        setTexturePath("runewood/");
        BlockStateSmithTypes.FULL_BLOCK.act(data,
                RUNEWOOD_BOARDS, VERTICAL_RUNEWOOD_BOARDS,
                RUNEWOOD_PLANKS, VERTICAL_RUNEWOOD_PLANKS, RUNEWOOD_TILES,
                RUSTIC_RUNEWOOD_PLANKS, VERTICAL_RUSTIC_RUNEWOOD_PLANKS, RUSTIC_RUNEWOOD_TILES,
                RUNEWOOD_PANEL);
        BlockStateSmithTypes.SLAB_BLOCK.act(data,
                RUNEWOOD_BOARDS_SLAB, VERTICAL_RUNEWOOD_BOARDS_SLAB,
                RUNEWOOD_PLANKS_SLAB, VERTICAL_RUNEWOOD_PLANKS_SLAB, RUNEWOOD_TILES_SLAB,
                RUSTIC_RUNEWOOD_PLANKS_SLAB, VERTICAL_RUSTIC_RUNEWOOD_PLANKS_SLAB, RUSTIC_RUNEWOOD_TILES_SLAB);
        BlockStateSmithTypes.STAIRS_BLOCK.act(data,
                RUNEWOOD_BOARDS_STAIRS, VERTICAL_RUNEWOOD_BOARDS_STAIRS,
                RUNEWOOD_PLANKS_STAIRS, VERTICAL_RUNEWOOD_PLANKS_STAIRS, RUNEWOOD_TILES_STAIRS,
                RUSTIC_RUNEWOOD_PLANKS_STAIRS, VERTICAL_RUSTIC_RUNEWOOD_PLANKS_STAIRS, RUSTIC_RUNEWOOD_TILES_STAIRS);

        BlockStateSmithTypes.LOG_BLOCK.act(data, RUNEWOOD_BEAM, RUNEWOOD_LOG, STRIPPED_RUNEWOOD_LOG, EXPOSED_RUNEWOOD_LOG, REVEALED_RUNEWOOD_LOG);
        BlockStateSmithTypes.WOOD_BLOCK.act(data, RUNEWOOD, STRIPPED_RUNEWOOD);
        BlockStateSmithTypes.LEAVES_BLOCK.act(data, RUNEWOOD_LEAVES, AZURE_RUNEWOOD_LEAVES);
        MalumBlockStateSmithTypes.HANGING_LEAVES.act(data, HANGING_RUNEWOOD_LEAVES, HANGING_AZURE_RUNEWOOD_LEAVES);

        BlockStateSmithTypes.CROSS_MODEL_BLOCK.act(data, RUNEWOOD_SAPLING, AZURE_RUNEWOOD_SAPLING);
        BlockStateSmithTypes.BUTTON_BLOCK.act(data, RUNEWOOD_BUTTON);
        BlockStateSmithTypes.PRESSURE_PLATE_BLOCK.act(data, RUNEWOOD_PRESSURE_PLATE);
        BlockStateSmithTypes.DOOR_BLOCK.act(data, RUNEWOOD_DOOR);
        BlockStateSmithTypes.TRAPDOOR_BLOCK.act(data, RUNEWOOD_TRAPDOOR, SOLID_RUNEWOOD_TRAPDOOR);
        BlockStateSmithTypes.WOODEN_SIGN_BLOCK.act(data, RUNEWOOD_SIGN, RUNEWOOD_WALL_SIGN);
        BlockStateSmithTypes.FENCE_BLOCK.act(data, RUNEWOOD_FENCE);
        BlockStateSmithTypes.FENCE_GATE_BLOCK.act(data, RUNEWOOD_FENCE_GATE);
        BlockStateSmithTypes.WALL_BLOCK.act(data, RUNEWOOD_BOARDS_WALL);

        MalumBlockStateSmithTypes.TOTEM_POLE.act(data, RUNEWOOD_TOTEM_POLE);
        BlockStateSmithTypes.CUSTOM_MODEL.act(data, ItemModelSmithTypes.BLOCK_MODEL_ITEM, this::simpleBlock, this::totemBaseModel, RUNEWOOD_TOTEM_BASE);
        BlockStateSmithTypes.CUSTOM_MODEL.act(data, this::simpleBlock, this::cutWoodBlockModel, CUT_RUNEWOOD_PLANKS);
        BlockStateSmithTypes.CUSTOM_MODEL.act(data, this::simpleBlock, this::woodenItemPedestalModel, RUNEWOOD_ITEM_PEDESTAL);
        BlockStateSmithTypes.CUSTOM_MODEL.act(data, this::simpleBlock, this::decoratedItemPedestalModel, GILDED_RUNEWOOD_ITEM_PEDESTAL);
        BlockStateSmithTypes.CUSTOM_MODEL.act(data, this::directionalBlock, this::itemStandModel, RUNEWOOD_ITEM_STAND);
        BlockStateSmithTypes.CUSTOM_MODEL.act(data, this::directionalBlock, this::decoratedItemStandModel, GILDED_RUNEWOOD_ITEM_STAND);

        setTexturePath("soulwood/");
        BlockStateSmithTypes.FULL_BLOCK.act(data,
                SOULWOOD_BOARDS, VERTICAL_SOULWOOD_BOARDS,
                SOULWOOD_PLANKS, VERTICAL_SOULWOOD_PLANKS, SOULWOOD_TILES,
                RUSTIC_SOULWOOD_PLANKS, VERTICAL_RUSTIC_SOULWOOD_PLANKS, RUSTIC_SOULWOOD_TILES,
                SOULWOOD_PANEL);
        BlockStateSmithTypes.SLAB_BLOCK.act(data,
                SOULWOOD_BOARDS_SLAB, VERTICAL_SOULWOOD_BOARDS_SLAB,
                SOULWOOD_PLANKS_SLAB, VERTICAL_SOULWOOD_PLANKS_SLAB, SOULWOOD_TILES_SLAB,
                RUSTIC_SOULWOOD_PLANKS_SLAB, VERTICAL_RUSTIC_SOULWOOD_PLANKS_SLAB, RUSTIC_SOULWOOD_TILES_SLAB);
        BlockStateSmithTypes.STAIRS_BLOCK.act(data,
                SOULWOOD_BOARDS_STAIRS, VERTICAL_SOULWOOD_BOARDS_STAIRS,
                SOULWOOD_PLANKS_STAIRS, VERTICAL_SOULWOOD_PLANKS_STAIRS, SOULWOOD_TILES_STAIRS,
                RUSTIC_SOULWOOD_PLANKS_STAIRS, VERTICAL_RUSTIC_SOULWOOD_PLANKS_STAIRS, RUSTIC_SOULWOOD_TILES_STAIRS);

        BlockStateSmithTypes.LOG_BLOCK.act(data, SOULWOOD_BEAM, SOULWOOD_LOG, STRIPPED_SOULWOOD_LOG, EXPOSED_SOULWOOD_LOG, REVEALED_SOULWOOD_LOG);
        BlockStateSmithTypes.WOOD_BLOCK.act(data, SOULWOOD, STRIPPED_SOULWOOD);
        BlockStateSmithTypes.LEAVES_BLOCK.act(data, SOULWOOD_LEAVES);
        BlockStateSmithTypes.CUSTOM_MODEL.act(data, ItemModelSmithTypes.BLOCK_TEXTURE_ITEM, this::simpleBlock, this::hangingLeavesModel, HANGING_SOULWOOD_LEAVES);

        BlockStateSmithTypes.CROSS_MODEL_BLOCK.act(data, SOULWOOD_GROWTH);
        BlockStateSmithTypes.BUTTON_BLOCK.act(data, SOULWOOD_BUTTON);
        BlockStateSmithTypes.PRESSURE_PLATE_BLOCK.act(data, SOULWOOD_PRESSURE_PLATE);
        BlockStateSmithTypes.DOOR_BLOCK.act(data, SOULWOOD_DOOR);
        BlockStateSmithTypes.TRAPDOOR_BLOCK.act(data, SOULWOOD_TRAPDOOR, SOLID_SOULWOOD_TRAPDOOR);
        BlockStateSmithTypes.WOODEN_SIGN_BLOCK.act(data, SOULWOOD_SIGN, SOULWOOD_WALL_SIGN);
        BlockStateSmithTypes.FENCE_BLOCK.act(data, SOULWOOD_FENCE);
        BlockStateSmithTypes.FENCE_GATE_BLOCK.act(data, SOULWOOD_FENCE_GATE);
        BlockStateSmithTypes.WALL_BLOCK.act(data, SOULWOOD_BOARDS_WALL);

        MalumBlockStateSmithTypes.TOTEM_POLE.act(data, SOULWOOD_TOTEM_POLE);
        BlockStateSmithTypes.CUSTOM_MODEL.act(data, ItemModelSmithTypes.BLOCK_MODEL_ITEM, this::simpleBlock, this::totemBaseModel, SOULWOOD_TOTEM_BASE);
        BlockStateSmithTypes.CUSTOM_MODEL.act(data, this::simpleBlock, this::cutWoodBlockModel, CUT_SOULWOOD_PLANKS);
        BlockStateSmithTypes.CUSTOM_MODEL.act(data, this::simpleBlock, this::woodenItemPedestalModel, SOULWOOD_ITEM_PEDESTAL);
        BlockStateSmithTypes.CUSTOM_MODEL.act(data, this::simpleBlock, this::decoratedItemPedestalModel, ORNATE_SOULWOOD_ITEM_PEDESTAL);
        BlockStateSmithTypes.CUSTOM_MODEL.act(data, this::directionalBlock, this::itemStandModel, SOULWOOD_ITEM_STAND);
        BlockStateSmithTypes.CUSTOM_MODEL.act(data, this::directionalBlock, this::decoratedItemStandModel, ORNATE_SOULWOOD_ITEM_STAND);

        setTexturePath("ores/");
        BlockStateSmithTypes.FULL_BLOCK.act(data, CTHONIC_GOLD_ORE, NATURAL_QUARTZ_ORE, DEEPSLATE_QUARTZ_ORE, SOULSTONE_ORE, DEEPSLATE_SOULSTONE_ORE);
        BlockStateSmithTypes.CUSTOM_MODEL.act(data, this::simpleBlock, this::layeredBlockModel, BLAZING_QUARTZ_ORE, BRILLIANT_STONE, BRILLIANT_DEEPSLATE);
        BlockStateSmithTypes.CUSTOM_MODEL.act(data, ItemModelSmithTypes.GENERATED_ITEM, this::directionalBlock, fromFunction(models()::cross), NATURAL_QUARTZ_CLUSTER, CTHONIC_GOLD_CLUSTER, BLAZING_QUARTZ_CLUSTER);

        setTexturePath("storage_blocks/");
        BlockStateSmithTypes.FULL_BLOCK.act(data,
                BLOCK_OF_RAW_SOULSTONE, BLOCK_OF_SOULSTONE, BLOCK_OF_CTHONIC_GOLD, BLOCK_OF_BRILLIANCE,
                BLOCK_OF_ROTTING_ESSENCE, BLOCK_OF_ASTRAL_WEAVE, BLOCK_OF_HEX_ASH, BLOCK_OF_ALCHEMICAL_CALX,
                MASS_OF_BLIGHTED_GUNK, BLOCK_OF_SOUL_STAINED_STEEL, BLOCK_OF_HALLOWED_GOLD, BLOCK_OF_MALIGNANT_PEWTER,
                BLOCK_OF_NULL_SLATE, BLOCK_OF_VOID_SALTS, BLOCK_OF_MNEMONIC_FRAGMENT, BLOCK_OF_MALIGNANT_LEAD,
                RUNIC_SAP_BLOCK, CURSED_SAP_BLOCK, BLOCK_OF_BLAZING_QUARTZ, BLOCK_OF_ARCANE_CHARCOAL,
                BLOCK_OF_AURIC_EMBERS, BLOCK_OF_LIVING_FLESH);
        BlockStateSmithTypes.LOG_BLOCK.act(data, BLOCK_OF_GRIM_TALC);

        setTexturePath("blight/");
        MalumBlockStateSmithTypes.BLIGHTED_BLOCK.act(data, BLIGHTED_SOIL);
        MalumBlockStateSmithTypes.BLIGHTED_GROWTH.act(data, BLIGHTED_GROWTH);
        MalumBlockStateSmithTypes.CLINGING_BLIGHT.act(data, CLINGING_BLIGHT);
        BlockStateSmithTypes.CUSTOM_MODEL.act(data, ItemModelSmithTypes.BLOCK_MODEL_ITEM, this::simpleBlock, this::blightedEarthModel, BLIGHTED_EARTH);
        BlockStateSmithTypes.CUSTOM_MODEL.act(data, ItemModelSmithTypes.BLOCK_MODEL_ITEM, this::simpleBlock, this::blightedSoulwoodModel, BLIGHTED_SOULWOOD);

        setTexturePath("blight/calcified/");
        MalumBlockStateSmithTypes.CALCIFIED_BLIGHT.act(data, CALCIFIED_BLIGHT);
        MalumBlockStateSmithTypes.TALL_CALCIFIED_BLIGHT.act(data, TALL_CALCIFIED_BLIGHT);

        setTexturePath("spirit_diode/");
        MalumBlockStateSmithTypes.SPIRIT_DIODE.act(data, WAVECHARGER, WAVEBANKER, WAVEMAKER, WAVEBREAKER);

        setTexturePath("");
        itemModelProvider.setTexturePath("ether/");
        BlockStateSmithTypes.CUSTOM_MODEL.act(data, ItemModelSmithTypes.GENERATED_ITEM, this::simpleBlock, this::etherModel, ETHER);
        BlockStateSmithTypes.CUSTOM_MODEL.act(data, MalumItemModelSmithTypes.GENERATED_OVERLAY_ITEM, this::simpleBlock, this::etherTorchModel, ETHER_TORCH);
        BlockStateSmithTypes.CUSTOM_MODEL.act(data, ItemModelSmithTypes.NO_DATAGEN, (b, m) -> horizontalBlock(b, m, 90), this::wallEtherTorchModel, WALL_ETHER_TORCH);
        BlockStateSmithTypes.CUSTOM_MODEL.act(data, MalumItemModelSmithTypes.GENERATED_OVERLAY_ITEM, this::simpleBlock, this::etherModel, IRIDESCENT_ETHER);
        BlockStateSmithTypes.CUSTOM_MODEL.act(data, MalumItemModelSmithTypes.IRIDESCENT_ETHER_TORCH_ITEM, this::simpleBlock, this::etherTorchModel, IRIDESCENT_ETHER_TORCH);
        BlockStateSmithTypes.CUSTOM_MODEL.act(data, ItemModelSmithTypes.NO_DATAGEN, (b, m) -> horizontalBlock(b, m, 90), this::wallEtherTorchModel, IRIDESCENT_WALL_ETHER_TORCH);
        itemModelProvider.setTexturePath("");


        BlockStateSmithTypes.CUSTOM_MODEL.act(data, ItemModelSmithTypes.BLOCK_MODEL_ITEM, this::simpleBlock, this::predefinedModel,
                SPIRIT_ALTAR, SPIRIT_JAR, RITUAL_PLINTH);

        BlockStateSmithTypes.CUSTOM_MODEL.act(data, ItemModelSmithTypes.BLOCK_MODEL_ITEM, this::horizontalBlock, this::predefinedModel,
                WEAVERS_WORKBENCH, RUNIC_WORKBENCH);

        BlockStateSmithTypes.CUSTOM_MODEL.act(data, ItemModelSmithTypes.NO_DATAGEN, this::simpleBlock, this::predefinedModel,
                RUNEWOOD_OBELISK, RUNEWOOD_OBELISK_COMPONENT, BRILLIANT_OBELISK, BRILLIANT_OBELISK_COMPONENT, SPIRIT_CRUCIBLE, SPIRIT_CRUCIBLE_COMPONENT, REPAIR_PYLON);

        MalumBlockStateSmithTypes.REPAIR_PYLON_COMPONENT.act(data, REPAIR_PYLON_COMPONENT);

        BlockStateSmithTypes.CUSTOM_MODEL.act(data, ItemModelSmithTypes.NO_DATAGEN, this::horizontalBlock, this::predefinedModel,
                SPIRIT_CATALYZER, SPIRIT_CATALYZER_COMPONENT);

        BlockStateSmithTypes.CUSTOM_MODEL.act(data, ItemModelSmithTypes.BLOCK_MODEL_ITEM, this::simpleBlock, this::predefinedModel,
                VOID_CONDUIT, VOID_DEPOT, WEEPING_WELL_BRICKS);
        BlockStateSmithTypes.CUSTOM_MODEL.act(data, ItemModelSmithTypes.BLOCK_MODEL_ITEM, this::horizontalBlock, this::predefinedModel,
                WEEPING_WELL_ENCASEMENT, WEEPING_WELL_ENCASEMENT_MIRRORED, WEEPING_WELL_ENCASEMENT_CORNER, WEEPING_WELL_CENTRAL_ENCASEMENT, WEEPING_WELL_SIDE_PILLAR);
        BlockStateSmithTypes.CUSTOM_MODEL.act(data, ItemModelSmithTypes.BLOCK_MODEL_ITEM, this::directionalBlock, this::predefinedModel,
                WEEPING_WELL_CENTRAL_PILLAR, WEEPING_WELL_CENTRAL_ENCASEMENT_SUPPORT);
        MalumBlockStateSmithTypes.PRIMORDIAL_SOUP.act(data, PRIMORDIAL_SOUP);

        BlockStateSmithTypes.FULL_BLOCK.act(data, THE_DEVICE, THE_VESSEL);

        BlockStateSmithTypes.CUSTOM_MODEL.act(data, ItemModelSmithTypes.NO_DATAGEN, this::simpleBlock, this::cubeModelAirTexture, SPIRIT_MOTE);
    }

    public ModelFile cubeModelAirTexture(Block block) {
        String name = getBlockName(block);
        return models().cubeAll(name, MalumMod.malumPath("block/air")).texture("particle", getBlockTexture(name));
    }

    public ModelFile columnCapModel(Block block) {
        String name = getBlockName(block);
        ResourceLocation top = getBlockTexture(name + "_top");
        ResourceLocation bottom = getBlockTexture(name.replace("_cap", "") + "_top");
        ResourceLocation side = getBlockTexture(name);
        return models().cubeBottomTop(name, side, bottom, top);
    }

    public ModelFile directionalRedstoneMachineBlock(Block block) {
        String name = getBlockName(block);
        ResourceLocation top = getBlockTexture("runewood_frame_top");
        ResourceLocation bottom = getBlockTexture("runewood_frame_bottom");
        ResourceLocation locked = getBlockTexture("runewood_frame_locked");
        ResourceLocation input = getBlockTexture("runewood_frame_input");
        ResourceLocation output = getBlockTexture(name + "_output");
        return models().cube(name, bottom, top, output, input, locked, locked).texture("particle", locked);
    }

    public ModelFile cutRockBlockModel(Block block) {
        String name = getBlockName(block);
        int index = name.indexOf("_");
        String substring = name.substring(index + 1);
        ResourceLocation top = getBlockTexture("polished_" + substring);
        ResourceLocation bottom = getBlockTexture("smooth_" + substring);
        ResourceLocation side = getBlockTexture(name);
        return models().cubeBottomTop(name, side, bottom, top);
    }

    public ModelFile cutWoodBlockModel(Block block) {
        String name = getBlockName(block);
        ResourceLocation top = getBlockTexture(name.replace("cut_", ""));
        ResourceLocation side = getBlockTexture(name);
        return models().cubeBottomTop(name, side, top, top);
    }

    public ModelFile rockItemPedestalModel(Block block) {
        return itemPedestalModel(block, "template_rock_item_pedestal");
    }

    public ModelFile woodenItemPedestalModel(Block block) {
        return itemPedestalModel(block, "template_item_pedestal_wooden");
    }

    public ModelFile decoratedItemPedestalModel(Block block) {
        return itemPedestalModel(block, "template_item_pedestal_wooden_decorated", s -> s.substring(s.indexOf("_")+1) + "_" + s.split("_")[0]);
    }

    public ModelFile itemPedestalModel(Block block, String template) {
        return itemPedestalModel(block, template, s -> s);
    }

    public ModelFile itemPedestalModel(Block block, String template, Function<String, String> pathFunction) {
        String name = getBlockName(block);
        ResourceLocation parent = malumPath("block/templates/" + template);
        ResourceLocation pedestal = getBlockTexture(pathFunction.apply(name));
        return models().withExistingParent(name, parent).texture("pedestal", pedestal);
    }

    public ModelFile itemStandModel(Block block) {
        return itemStandModel(block, "template_item_stand", s -> s);
    }
    public ModelFile decoratedItemStandModel(Block block) {
        return itemStandModel(block, "template_item_stand_decorated", s -> s.substring(s.indexOf("_")+1) + "_" + s.split("_")[0]);
    }
    public ModelFile itemStandModel(Block block, String template, Function<String, String> pathFunction) {
        String name = getBlockName(block);
        ResourceLocation parent = malumPath("block/templates/" + template);
        ResourceLocation stand = getBlockTexture(pathFunction.apply(name));
        return models().withExistingParent(name, parent).texture("stand", stand);
    }

    public ModelFile layeredBlockModel(Block block) {
        String name = getBlockName(block);
        ResourceLocation parent = malumPath("block/templates/template_glowing_block");
        ResourceLocation texture = getBlockTexture(name);
        ResourceLocation glowingTexture = getBlockTexture(name + "_glow");
        return models().withExistingParent(name, parent).texture("all", texture).texture("glow", glowingTexture).texture("particle", texture);
    }

    public ModelFile etherModel(Block block) {
        String name = getBlockName(block);
        return models().withExistingParent(name, ResourceLocation.withDefaultNamespace("block/air")).texture("particle", itemModelProvider.getItemTexture("ether"));
    }

    public ModelFile etherTorchModel(Block block) {
        return models().getExistingFile(malumPath("block/ether_torch"));
    }

    public ModelFile wallEtherTorchModel(Block block) {
        return models().getExistingFile(malumPath("block/ether_torch_wall"));
    }

    public ModelFile totemBaseModel(Block block) {
        String name = getBlockName(block);
        String woodName = name.substring(0, 8);
        ResourceLocation side = getBlockTexture(woodName + "_log");
        ResourceLocation top = getBlockTexture(woodName + "_log_top");
        ResourceLocation planks = getBlockTexture(woodName + "_planks");
        ResourceLocation panel = getBlockTexture(woodName + "_panel");
        return models().withExistingParent(name, malumPath("block/templates/template_totem_base")).texture("side", side).texture("top", top).texture("planks", planks).texture("panel", panel);
    }

    public ModelFile hangingLeavesModel(Block block) {
        String name = getBlockName(block);
        ResourceLocation texture = getBlockTexture(name);
        return models().withExistingParent(name, malumPath("block/templates/template_hanging_leaves")).texture("hanging_leaves", texture).texture("particle", texture);
    }

    public ModelFile blightedSoulwoodModel(Block block) {
        String name = getBlockName(block);
        ResourceLocation side = getBlockTexture(name);
        ResourceLocation bottom = getBlockTexture("blighted_soil_0");
        ResourceLocation top = getStaticBlockTexture("soulwood/soulwood_log_top");
        return models().cubeBottomTop(name, side, bottom, top);
    }

    public ModelFile blightedEarthModel(Block block) {
        String name = getBlockName(block);
        ResourceLocation side = getBlockTexture(name);
        ResourceLocation bottom = ResourceLocation.withDefaultNamespace("block/dirt");
        ResourceLocation top = getBlockTexture("blighted_soil_0");
        return models().cubeBottomTop(name, side, bottom, top);
    }
}
