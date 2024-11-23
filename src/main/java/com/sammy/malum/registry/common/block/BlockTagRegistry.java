package com.sammy.malum.registry.common.block;

import com.sammy.malum.*;
import net.minecraft.resources.*;
import net.minecraft.tags.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.*;
import net.minecraftforge.registries.*;

public class BlockTagRegistry {

    public static final TagKey<Block> BLIGHTED_BLOCKS = malumTag("blighted_blocks");
    public static final TagKey<Block> BLIGHTED_PLANTS = malumTag("blighted_plants");

    public static final TagKey<Block> RUNEWOOD_LOGS = malumTag("runewood_logs");
    public static final TagKey<Block> RUNEWOOD_PLANKS = malumTag("runewood_planks");
    public static final TagKey<Block> RUNEWOOD_STAIRS = malumTag("runewood_stairs");
    public static final TagKey<Block> RUNEWOOD_SLABS = malumTag("runewood_slabs");

    public static final TagKey<Block> SOULWOOD_LOGS = malumTag("soulwood_logs");
    public static final TagKey<Block> SOULWOOD_PLANKS = malumTag("soulwood_planks");
    public static final TagKey<Block> SOULWOOD_STAIRS = malumTag("soulwood_stairs");
    public static final TagKey<Block> SOULWOOD_SLABS = malumTag("soulwood_slabs");



    public static final TagKey<Block> TAINTED_ROCK = malumTag("tainted_rock");
    public static final TagKey<Block> TAINTED_BLOCKS = malumTag("tainted_rock_blocks");
    public static final TagKey<Block> TAINTED_SLABS = malumTag("tainted_rock_slabs");
    public static final TagKey<Block> TAINTED_STAIRS = malumTag("tainted_rock_stairs");
    public static final TagKey<Block> TAINTED_WALLS = malumTag("tainted_rock_walls");

    public static final TagKey<Block> TWISTED_ROCK = malumTag("twisted_rock");
    public static final TagKey<Block> TWISTED_BLOCKS = malumTag("twisted_rock_blocks");
    public static final TagKey<Block> TWISTED_SLABS = malumTag("twisted_rock_slabs");
    public static final TagKey<Block> TWISTED_STAIRS = malumTag("twisted_rock_stairs");
    public static final TagKey<Block> TWISTED_WALLS = malumTag("twisted_rock_walls");

    public static final TagKey<Block> RITE_IMMUNE = malumTag("rite_immune");
    public static final TagKey<Block> UNCHAINED_RITE_CATALYST = malumTag("unchained_rite_catalyst");

    public static final TagKey<Block> ENDLESS_FLAME = malumTag("endless_flame");

    public static final TagKey<Block> GREATER_AERIAL_WHITELIST = malumTag("greater_aerial_whitelist");

    public static final TagKey<Block> TRAY_HEAT_SOURCES = modTag("farmersdelight:tray_heat_sources");
    public static final TagKey<Block> HEAT_SOURCES = modTag("farmersdelight:heat_sources");

    public static final TagKey<Block> STRIPPED_LOGS = forgeTag("stripped_logs");

    private static TagKey<Block> modTag(String path) {
        return TagKey.create(ForgeRegistries.BLOCKS.getRegistryKey(), new ResourceLocation(path));
    }

    private static TagKey<Block> malumTag(String path) {
        return TagKey.create(ForgeRegistries.BLOCKS.getRegistryKey(), MalumMod.malumPath(path));
    }

    private static TagKey<Block> forgeTag(String name) {
        return BlockTags.create(new ResourceLocation("forge", name));
    }
}
