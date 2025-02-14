package com.sammy.malum.registry.common.item;

import com.sammy.malum.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.*;
import net.minecraft.tags.*;
import net.minecraft.world.item.*;

public class ItemTagRegistry {

    public static final TagKey<Item> SOUL_SHATTER_CAPABLE_WEAPONS = malumTag("soul_shatter_capable_weapons");
    public static final TagKey<Item> MAGIC_CAPABLE_WEAPONS = malumTag("magic_capable_weapon");
    public static final TagKey<Item> SCYTHES = malumTag("scythe");
    public static final TagKey<Item> STAVES = malumTag("staff");
    public static final TagKey<Item> KNIVES_FD = modTag("farmersdelight:tools/knives");
    public static final TagKey<Item> KNIVES = commonTag("tools/knives");

    public static final TagKey<Item> ANIMATED_ENCHANTABLE = malumTag("enchantable/animated");
    public static final TagKey<Item> REBOUND_ENCHANTABLE = malumTag("enchantable/rebound");
    public static final TagKey<Item> ASCENSION_ENCHANTABLE = malumTag("enchantable/ascension");
    public static final TagKey<Item> REPLENISHING_ENCHANTABLE = malumTag("enchantable/replenishing");
    public static final TagKey<Item> CAPACITOR_ENCHANTABLE = malumTag("enchantable/capacitor");
    public static final TagKey<Item> HAUNTED_ENCHANTABLE = malumTag("enchantable/haunted");
    public static final TagKey<Item> SPIRIT_SPOILS_ENCHANTABLE = malumTag("enchantable/spirit_spoils");

    public static final TagKey<Item> SPIRITS = malumTag("spirits");
    public static final TagKey<Item> ASPECTED_SPIRITS = malumTag("aspected_spirits");
    public static final TagKey<Item> MOB_DROPS = malumTag("mob_drops");
    public static final TagKey<Item> MATERIALS = malumTag("materials");
    public static final TagKey<Item> MINERALS = malumTag("minerals");
    public static final TagKey<Item> AUGMENTS = malumTag("augments");
    public static final TagKey<Item> IMPETUS = malumTag("impetus");
    public static final TagKey<Item> FRACTURED_IMPETUS = malumTag("fractured_impetus");
    public static final TagKey<Item> METAL_IMPETUS = malumTag("metal_impetus");
    public static final TagKey<Item> FRACTURED_METAL_IMPETUS = malumTag("fractured_metal_impetus");
    public static final TagKey<Item> METAL_NODES = malumTag("metal_nodes");
    public static final TagKey<Item> SOULWOVEN_BANNERS = malumTag("soulwoven_banners");

    public static final TagKey<Item> IS_TOTEMIC_TOOL = malumTag("totemic_tool");
    public static final TagKey<Item> IS_REDSTONE_TOOL = malumTag("redstone_tool");
    public static final TagKey<Item> IS_ARTIFICE_TOOL = malumTag("artifice_tool");

    public static final TagKey<Item> SAPBALLS = malumTag("sapballs");
    public static final TagKey<Item> GROSS_FOODS = malumTag("gross_foods");

    public static final TagKey<Item> PROSPECTORS_TREASURE = malumTag("prospectors_treasure");

    public static final TagKey<Item> SOULHUNTERS_TREASURE = malumTag("soulhunters_treasure");
    public static final TagKey<Item> SOULWOVEN_POUCH_AUTOCOLLECT = malumTag("soulwoven_pouch_autocollect");

    public static final TagKey<Item> HIDDEN_ALWAYS = malumTag("hidden_items/always");
    public static final TagKey<Item> HIDDEN_UNTIL_VOID = malumTag("hidden_items/void");
    public static final TagKey<Item> HIDDEN_UNTIL_BLACK_CRYSTAL = malumTag("hidden_items/black_crystal");

    public static final TagKey<Item> ARCANE_ELEGY_COMPONENTS = malumTag("arcane_elegy_component");

    public static final TagKey<Item> BROOCH = modTag("curios:brooch");
    public static final TagKey<Item> BELT = modTag("curios:belt");
    public static final TagKey<Item> CHARM = modTag("curios:charm");
    public static final TagKey<Item> NECKLACE = modTag("curios:necklace");
    public static final TagKey<Item> RING = modTag("curios:ring");
    public static final TagKey<Item> RUNE = modTag("curios:rune");

    public static final TagKey<Item> RUNEWOOD_BOARD_INGREDIENT = malumTag("runewood_board_ingredient");
    public static final TagKey<Item> RUNEWOOD_LOGS = malumTag("runewood_logs");
    public static final TagKey<Item> RUNEWOOD_PLANKS = malumTag("runewood_planks");
    public static final TagKey<Item> RUNEWOOD_BOARDS = malumTag("runewood_boards");
    public static final TagKey<Item> RUNEWOOD_SLABS = malumTag("runewood_slabs");
    public static final TagKey<Item> RUNEWOOD_STAIRS = malumTag("runewood_stairs");

    public static final TagKey<Item> SOULWOOD_BOARD_INGREDIENT = malumTag("soulwood_board_ingredient");
    public static final TagKey<Item> SOULWOOD_LOGS = malumTag("soulwood_logs");
    public static final TagKey<Item> SOULWOOD_PLANKS = malumTag("soulwood_planks");
    public static final TagKey<Item> SOULWOOD_BOARDS = malumTag("soulwood_boards");
    public static final TagKey<Item> SOULWOOD_SLABS = malumTag("soulwood_slabs");
    public static final TagKey<Item> SOULWOOD_STAIRS = malumTag("soulwood_stairs");

    public static final TagKey<Item> TAINTED_ROCK = malumTag("tainted_rock");
    public static final TagKey<Item> TAINTED_BLOCKS = malumTag("tainted_rock_blocks");
    public static final TagKey<Item> TAINTED_SLABS = malumTag("tainted_rock_slabs");
    public static final TagKey<Item> TAINTED_STAIRS = malumTag("tainted_rock_stairs");
    public static final TagKey<Item> TAINTED_WALLS = malumTag("tainted_rock_walls");

    public static final TagKey<Item> TWISTED_ROCK = malumTag("twisted_rock");
    public static final TagKey<Item> TWISTED_BLOCKS = malumTag("twisted_rock_blocks");
    public static final TagKey<Item> TWISTED_SLABS = malumTag("twisted_rock_slabs");
    public static final TagKey<Item> TWISTED_STAIRS = malumTag("twisted_rock_stairs");
    public static final TagKey<Item> TWISTED_WALLS = malumTag("twisted_rock_walls");

    public static final TagKey<Item> STRIPPED_LOGS = commonTag("stripped_logs");
    public static final TagKey<Item> STRIPPED_WOODS = commonTag("stripped_woods");


    private static TagKey<Item> modTag(String path) {
        return TagKey.create(Registries.ITEM, ResourceLocation.parse(path));
    }

    private static TagKey<Item> malumTag(String path) {
        return TagKey.create(Registries.ITEM, MalumMod.malumPath(path));
    }

    private static TagKey<Item> commonTag(String name) {
        return ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", name));
    }
}
