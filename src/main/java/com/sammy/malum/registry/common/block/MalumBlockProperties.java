package com.sammy.malum.registry.common.block;

import com.sammy.malum.registry.common.*;
import net.minecraft.client.renderer.*;
import net.minecraft.tags.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.material.*;
import net.neoforged.neoforge.common.Tags;
import team.lodestar.lodestone.systems.block.*;

import java.awt.*;

import static net.minecraft.tags.BlockTags.*;
import static net.minecraft.world.level.block.Blocks.COPPER_BLOCK;

public class MalumBlockProperties {

    public static LodestoneBlockProperties SPIRITED_GLASS() {
        return new LodestoneBlockProperties()
                .setRenderType(() -> RenderType::translucent)
                .instrument(NoteBlockInstrument.HAT)
                .sound(SoundType.GLASS)
                .strength(0.3F)
                .needsPickaxe()
                .noOcclusion();
    }

    public static LodestoneBlockProperties TAINTED_ROCK() {
        return new LodestoneBlockProperties()
                .addTag(BlockTagRegistry.TAINTED_ROCK)
                .strength(1.25F, 9.0F)
                .sound(SoundRegistry.TAINTED_ROCK)
                .mapColor(MapColor.COLOR_GRAY)
                .requiresCorrectToolForDrops()
                .needsPickaxe();
    }

    public static LodestoneBlockProperties TAINTED_ROCK_BRICKS() {
        return TAINTED_ROCK().sound(SoundRegistry.TAINTED_ROCK_BRICKS);
    }

    public static LodestoneBlockProperties TWISTED_ROCK() {
        return new LodestoneBlockProperties()
                .addTag(BlockTagRegistry.TWISTED_ROCK)
                .strength(1.25F, 9.0F)
                .sound(SoundRegistry.TWISTED_ROCK)
                .mapColor(MapColor.COLOR_BLACK)
                .requiresCorrectToolForDrops()
                .needsPickaxe();
    }

    public static LodestoneBlockProperties TWISTED_ROCK_BRICKS() {
        return TWISTED_ROCK().sound(SoundRegistry.TWISTED_ROCK_BRICKS);
    }

    public static final Color RUNEWOOD_LEAVES_YELLOW = new Color(251, 193, 76);
    public static final Color RUNEWOOD_LEAVES_ORANGE = new Color(217, 110, 23);
    public static final Color AZURE_RUNEWOOD_LEAVES_CYAN = new Color(176, 234, 255);
    public static final Color AZURE_RUNEWOOD_LEAVES_BLUE = new Color(64, 95, 157);

    public static LodestoneBlockProperties RUNEWOOD() {
        return new LodestoneBlockProperties()
                .strength(1.75F, 4.0F)
                .instrument(NoteBlockInstrument.BASS)
                .mapColor(MapColor.TERRACOTTA_BROWN)
                .sound(SoundRegistry.RUNEWOOD)
                .needsAxe();
    }

    public static LodestoneBlockProperties RUNEWOOD_PLANKS() {
        return RUNEWOOD().addTag(PLANKS);
    }

    public static LodestoneBlockProperties RUNEWOOD_SLABS() {
        return RUNEWOOD().addTags(SLABS, WOODEN_SLABS);
    }

    public static LodestoneBlockProperties RUNEWOOD_STAIRS() {
        return RUNEWOOD().addTags(STAIRS, WOODEN_STAIRS);
    }

    public static LodestoneBlockProperties RUNEWOOD_SAPLING() {
        return new LodestoneBlockProperties()
                .addTag(BlockTags.SAPLINGS)

                .mapColor(MapColor.TERRACOTTA_ORANGE)
                .sound(SoundType.GRASS)
                .setCutoutRenderType()
                .noCollission()
                .noOcclusion()
                .randomTicks()
                .instabreak();
    }

    public static LodestoneBlockProperties RUNEWOOD_LEAVES() {
        return new LodestoneBlockProperties()
                .mapColor(MapColor.TERRACOTTA_YELLOW)
                .addTag(BlockTags.LEAVES)
                .strength(0.2F)
                .randomTicks()
                .noOcclusion()
                .isValidSpawn(Blocks::ocelotOrParrot)
                .isSuffocating((a,b,c) -> false)
                .isViewBlocking((a,b,c) -> false)
                .setCutoutRenderType()
                .sound(SoundRegistry.RUNEWOOD_LEAVES)
                .needsHoe();
    }

    public static LodestoneBlockProperties HANGING_RUNEWOOD_LEAVES() {
        return new LodestoneBlockProperties()
                .mapColor(MapColor.TERRACOTTA_YELLOW)
                .strength(0.05F)
                .randomTicks()
                .noOcclusion()
                .isValidSpawn(Blocks::ocelotOrParrot)
                .isSuffocating((a,b,c) -> false)
                .isViewBlocking((a,b,c) -> false)
                .setCutoutRenderType()
                .sound(SoundRegistry.RUNEWOOD_LEAVES)
                .needsHoe();
    }

    public static LodestoneBlockProperties RUNIC_SAP() {
        return new LodestoneBlockProperties()
                .mapColor(MapColor.TERRACOTTA_YELLOW)
                .addTag(Tags.Blocks.STORAGE_BLOCKS)
                .friction(0.8F)
                .sound(SoundType.SLIME_BLOCK)
                .noOcclusion();
    }

    public static LodestoneBlockProperties SOULWOVEN_BANNER() {
        return RUNEWOOD()
                .noOcclusion()
                .noCollission()
                .setCutoutRenderType();
    }

    public static LodestoneBlockProperties SOULWOOD() {
        return new LodestoneBlockProperties()
                .mapColor(MapColor.TERRACOTTA_PURPLE)
                .sound(SoundRegistry.SOULWOOD)
                .strength(1.75F, 4.0F)
                .instrument(NoteBlockInstrument.BASS)
                .needsAxe();
    }

    public static LodestoneBlockProperties SOULWOOD_LEAVES() {
        return new LodestoneBlockProperties()
                .mapColor(MapColor.TERRACOTTA_RED)
                .addTag(BlockTags.LEAVES)
                .needsHoe()
                .strength(0.2F)
                .randomTicks()
                .noOcclusion()
                .isValidSpawn(Blocks::ocelotOrParrot)
                .isSuffocating((a,b,c) -> false)
                .isViewBlocking((a,b,c) -> false)
                .sound(SoundRegistry.SOULWOOD_LEAVES);
    }

    public static LodestoneBlockProperties HANGING_SOULWOOD_LEAVES() {
        return new LodestoneBlockProperties()
                .mapColor(MapColor.TERRACOTTA_RED)
                .needsHoe()
                .strength(0.05F)
                .randomTicks()
                .noOcclusion()
                .isValidSpawn(Blocks::ocelotOrParrot)
                .isSuffocating((a,b,c) -> false)
                .isViewBlocking((a,b,c) -> false)
                .sound(SoundRegistry.SOULWOOD_LEAVES);
    }

    public static LodestoneBlockProperties CURSED_SAP() {
        return new LodestoneBlockProperties()
                .mapColor(MapColor.TERRACOTTA_RED)
                .addTag(Tags.Blocks.STORAGE_BLOCKS)
                .friction(0.8F)
                .sound(SoundRegistry.CURSED_SAP)
                .noOcclusion();
    }

    public static LodestoneBlockProperties BLIGHT() {
        return new LodestoneBlockProperties()
                .mapColor(MapColor.COLOR_BLACK)
                .addTag(BlockTagRegistry.BLIGHTED_BLOCKS)
                .needsShovel()
                .needsHoe()
                .sound(SoundRegistry.BLIGHTED_EARTH)
                .strength(0.7f);
    }

    public static LodestoneBlockProperties BLIGHTED_PLANTS() {
        return new LodestoneBlockProperties()
                .mapColor(MapColor.TERRACOTTA_BLACK)
                .addTag(BlockTagRegistry.BLIGHTED_PLANTS)
                .noCollission()
                .noOcclusion()
                .sound(SoundRegistry.BLIGHTED_FOLIAGE)
                .instabreak();
    }


    public static LodestoneBlockProperties CALCIFIED_BLIGHT() {
        return new LodestoneBlockProperties()
                .mapColor(MapColor.TERRACOTTA_WHITE)
                .addTag(BlockTagRegistry.BLIGHTED_PLANTS)
                .noCollission()
                .noOcclusion()
                .sound(SoundRegistry.CALCIFIED_BLIGHT)
                .instabreak();
    }

    public static LodestoneBlockProperties ORE_PROPERTIES(boolean isDeepslate) {
        return new LodestoneBlockProperties()
                .addTag(isDeepslate ? Tags.Blocks.ORES_IN_GROUND_DEEPSLATE : Tags.Blocks.ORES_IN_GROUND_STONE)
                .addTag(Tags.Blocks.ORES)
                .needsPickaxe()
                .requiresCorrectToolForDrops();
    }
    public static LodestoneBlockProperties BRILLIANCE_ORE(boolean isDeepslate) {
        return ORE_PROPERTIES(isDeepslate)
                .mapColor(MapColor.COLOR_GREEN)
                .addTag(Tags.Blocks.ORE_RATES_SINGULAR)
                .strength(isDeepslate ? 5f : 3f, 3f)
                .sound(isDeepslate ? SoundType.DEEPSLATE : SoundType.STONE);
    }

    public static LodestoneBlockProperties NATURAL_QUARTZ_ORE(boolean isDeepslate) {
        return ORE_PROPERTIES(isDeepslate)
                .mapColor(MapColor.TERRACOTTA_WHITE)
                .addTag(Tags.Blocks.ORE_RATES_SINGULAR)
                .strength(isDeepslate ? 6f : 4f, 3f)
                .sound(isDeepslate ? SoundRegistry.DEEPSLATE_QUARTZ : SoundRegistry.NATURAL_QUARTZ);
    }

    public static LodestoneBlockProperties SOULSTONE_ORE(boolean isDeepslate) {
        return ORE_PROPERTIES(isDeepslate)
                .mapColor(MapColor.TERRACOTTA_PURPLE)
                .addTag(Tags.Blocks.ORE_RATES_SINGULAR)
                .strength(isDeepslate ? 7.0f : 5.0F, 3.0F)
                .sound(isDeepslate ? SoundRegistry.DEEPSLATE_SOULSTONE : SoundRegistry.SOULSTONE);
    }

    public static LodestoneBlockProperties BLAZING_QUARTZ_ORE() {
        return new LodestoneBlockProperties()
                .mapColor(MapColor.TERRACOTTA_ORANGE)
                .addTag(Tags.Blocks.ORE_RATES_SINGULAR)
                .addTag(Tags.Blocks.ORES)
                .addTag(Tags.Blocks.ORES_IN_GROUND_NETHERRACK)
                .needsPickaxe()
                .requiresCorrectToolForDrops()
                .strength(3.0F, 3.0F)
                .sound(SoundRegistry.BLAZING_QUARTZ_ORE);
    }

    public static LodestoneBlockProperties BLAZING_QUARTZ_CLUSTER() {
        return new LodestoneBlockProperties()
                .mapColor(MapColor.COLOR_YELLOW)
                .addTag(Tags.Blocks.CLUSTERS)
                .needsPickaxe()
                .requiresCorrectToolForDrops()
                .strength(1.5F)
                .sound(SoundRegistry.BLAZING_QUARTZ_CLUSTER);
    }

    public static LodestoneBlockProperties CTHONIC_GOLD_ORE() {
        return new LodestoneBlockProperties()
                .mapColor(MapColor.COLOR_YELLOW)
                .addTag(Tags.Blocks.ORE_RATES_DENSE)
                .addTag(Tags.Blocks.ORES)
                .needsPickaxe()
                .requiresCorrectToolForDrops()
                .strength(10f, 9999f)
                .sound(SoundRegistry.CTHONIC_GOLD);
    }

    public static LodestoneBlockProperties CTHONIC_GOLD_CLUSTER() {
        return new LodestoneBlockProperties()
                .addTag(Tags.Blocks.CLUSTERS)
                .mapColor(MapColor.COLOR_YELLOW)
                .needsPickaxe()
                .requiresCorrectToolForDrops()
                .strength(4f, 9999f)
                .sound(SoundRegistry.CTHONIC_GOLD);
    }

    public static LodestoneBlockProperties CTHONIC_GOLD_BLOCK() {
        return new LodestoneBlockProperties()
                .mapColor(MapColor.COLOR_YELLOW)
                .addTag(Tags.Blocks.STORAGE_BLOCKS)
                .addTag(BlockTags.BEACON_BASE_BLOCKS)
                .needsPickaxe()
                .requiresCorrectToolForDrops()
                .strength(10f, 9999f)
                .sound(SoundRegistry.CTHONIC_GOLD);
    }

    public static LodestoneBlockProperties NATURAL_QUARTZ_CLUSTER() {
        return new LodestoneBlockProperties()
                .addTag(Tags.Blocks.CLUSTERS)
                .mapColor(MapColor.TERRACOTTA_WHITE)
                .needsPickaxe()
                .requiresCorrectToolForDrops()
                .strength(1.5F)
                .sound(SoundRegistry.QUARTZ_CLUSTER);
    }

    public static LodestoneBlockProperties ETHER() {
        return new LodestoneBlockProperties()
                .mapColor(MapColor.COLOR_YELLOW)
                .addTag(BlockTagRegistry.TRAY_HEAT_SOURCES)
                .sound(SoundRegistry.ETHER)
                .noCollission()
                .instabreak()
                .setCutoutRenderType()
                .lightLevel((b) -> 14);
    }
    public static LodestoneBlockProperties ETHER_TORCH() {
        return RUNEWOOD()
                .addTag(BlockTags.WALL_POST_OVERRIDE)
                .mapColor(MapColor.COLOR_YELLOW)
                .addTag(BlockTagRegistry.TRAY_HEAT_SOURCES)
                .noCollission()
                .instabreak()
                .setCutoutRenderType()
                .lightLevel((b) -> 14);
    }
    public static LodestoneBlockProperties TAINTED_ETHER_BRAZIER() {
        return TAINTED_ROCK()
                .addTag(BlockTags.WALL_POST_OVERRIDE)
                .mapColor(MapColor.COLOR_YELLOW)
                .addTag(BlockTagRegistry.TRAY_HEAT_SOURCES)
                .setCutoutRenderType()
                .lightLevel((b) -> 14);
    }
    public static LodestoneBlockProperties TWISTED_ETHER_BRAZIER() {
        return TWISTED_ROCK()
                .addTag(BlockTags.WALL_POST_OVERRIDE)
                .mapColor(MapColor.COLOR_YELLOW)
                .addTag(BlockTagRegistry.TRAY_HEAT_SOURCES)
                .setCutoutRenderType()
                .lightLevel((b) -> 14);
    }

    public static LodestoneBlockProperties REDSTONE_DIODE() {
        return new LodestoneBlockProperties()
                .mapColor(COPPER_BLOCK.defaultMapColor())
                .strength(3.0F, 6.0F)
                .sound(SoundRegistry.SPIRIT_DIODE)
                .requiresCorrectToolForDrops()
                .isRedstoneConductor(Blocks::never)
                .needsPickaxe()
                .needsAxe();
    }

    public static LodestoneBlockProperties MANA_MOTE_BLOCK() {
        return new LodestoneBlockProperties()
                .mapColor(MapColor.COLOR_LIGHT_BLUE)
                .setRenderType(() -> RenderType::cutout)
                .noOcclusion()
                .needsPickaxe()
                .requiresCorrectToolForDrops()
                .strength(8.0F, 8.0f)
                .lightLevel((b) -> 8)
                .sound(SoundRegistry.BLAZING_QUARTZ_BLOCK);
    }

    public static LodestoneBlockProperties SOULSTONE_BLOCK() {
        return new LodestoneBlockProperties()
                .mapColor(MapColor.TERRACOTTA_PURPLE)
                .addTag(Tags.Blocks.STORAGE_BLOCKS)
                .addTag(BlockTags.BEACON_BASE_BLOCKS)
                .needsPickaxe()
                .requiresCorrectToolForDrops()
                .strength(5.0F, 3.0F)
                .sound(SoundRegistry.SOULSTONE);
    }

    public static LodestoneBlockProperties BLAZING_QUARTZ_BLOCK() {
        return new LodestoneBlockProperties()
                .mapColor(MapColor.COLOR_ORANGE)
                .addTag(Tags.Blocks.STORAGE_BLOCKS)
                .addTag(BlockTags.BEACON_BASE_BLOCKS)
                .addTags(BlockTagRegistry.HEAT_SOURCES)
                .needsPickaxe()
                .requiresCorrectToolForDrops()
                .strength(5.0F, 6.0F)
                .sound(SoundRegistry.BLAZING_QUARTZ_BLOCK);
    }

    public static LodestoneBlockProperties BRILLIANCE_BLOCK() {
        return new LodestoneBlockProperties()
                .mapColor(MapColor.COLOR_GREEN)
                .addTag(Tags.Blocks.STORAGE_BLOCKS)
                .addTag(BlockTags.BEACON_BASE_BLOCKS)
                .needsPickaxe()
                .requiresCorrectToolForDrops()
                .strength(5.0F, 3.0F)
                .sound(SoundRegistry.BRILLIANCE_BLOCK);
    }

    public static LodestoneBlockProperties ARCANE_CHARCOAL_BLOCK() {
        return new LodestoneBlockProperties()
                .mapColor(MapColor.COLOR_GRAY)
                .addTag(Tags.Blocks.STORAGE_BLOCKS)
                .needsPickaxe()
                .requiresCorrectToolForDrops()
                .strength(5.0F, 6.0F)
                .sound(SoundRegistry.ARCANE_CHARCOAL_BLOCK);
    }

    public static LodestoneBlockProperties SOUL_STAINED_STEEL_BLOCK() {
        return new LodestoneBlockProperties()
                .mapColor(MapColor.COLOR_PURPLE)
                .addTag(Tags.Blocks.STORAGE_BLOCKS)
                .addTag(BlockTags.BEACON_BASE_BLOCKS)
                .requiresCorrectToolForDrops()
                .needsPickaxe()
                .sound(SoundRegistry.SOUL_STAINED_STEEL)
                .strength(5f, 64.0f);
    }

    public static LodestoneBlockProperties HALLOWED_GOLD() {
        return new LodestoneBlockProperties()
                .mapColor(MapColor.GOLD)
                .addTag(Tags.Blocks.STORAGE_BLOCKS)
                .addTag(BlockTags.BEACON_BASE_BLOCKS)
                .requiresCorrectToolForDrops()
                .needsPickaxe()
                .sound(SoundRegistry.HALLOWED_GOLD)
                .noOcclusion()
                .strength(2F, 16.0F);
    }

    public static LodestoneBlockProperties MALIGNANT_LEAD_BLOCK() {
        return new LodestoneBlockProperties()
                .mapColor(MapColor.COLOR_LIGHT_BLUE)
                .addTag(Tags.Blocks.STORAGE_BLOCKS)
                .addTag(BlockTags.BEACON_BASE_BLOCKS)
                .requiresCorrectToolForDrops()
                .needsPickaxe()
                .sound(SoundRegistry.MALIGNANT_LEAD)
                .strength(10f, 9999f);
    }

    public static LodestoneBlockProperties MALIGNANT_PEWTER_BLOCK() {
        return new LodestoneBlockProperties()
                .mapColor(MapColor.COLOR_LIGHT_BLUE)
                .addTag(Tags.Blocks.STORAGE_BLOCKS)
                .addTag(BlockTags.BEACON_BASE_BLOCKS)
                .requiresCorrectToolForDrops()
                .needsPickaxe()
                .sound(SoundRegistry.MALIGNANT_PEWTER)
                .strength(10f, 9999f);
    }

    public static LodestoneBlockProperties SPIRIT_JAR() {
        return new LodestoneBlockProperties()
                .mapColor(MapColor.GOLD)
                .strength(0.5f, 64f)
                .sound(SoundRegistry.HALLOWED_GOLD)
                .noOcclusion();
    }

    public static LodestoneBlockProperties WEEPING_WELL() {
        return new LodestoneBlockProperties()
                .mapColor(MapColor.COLOR_GRAY)
                .needsPickaxe()
                .sound(SoundRegistry.WEEPING_WELL_BRICKS)
                .requiresCorrectToolForDrops()
                .isRedstoneConductor((a,b,c) -> false)
                .strength(-1.0F, 3600000.0F);
    }

    public static LodestoneBlockProperties PRIMORDIAL_SOUP() {
        return new LodestoneBlockProperties()
                .mapColor(MapColor.TERRACOTTA_BLACK)
                .pushReaction(PushReaction.BLOCK)
                .setCutoutRenderType()
                .sound(SoundRegistry.BLIGHTED_EARTH)
                .strength(-1.0F, 3600000.0F);
    }
}
