package com.sammy.malum.registry.common.item;

import com.sammy.malum.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.*;
import net.minecraft.tags.*;
import net.minecraft.world.item.*;

public class ItemTagRegistry {
    public static final TagKey<Item> SOUL_HUNTER_WEAPON = malumTag("soul_hunter_weapon");
    public static final TagKey<Item> MAGIC_CAPABLE_WEAPON = malumTag("magic_capable_weapon");
    public static final TagKey<Item> SCYTHE = malumTag("scythe");
    public static final TagKey<Item> STAFF = malumTag("staff");

    public static final TagKey<Item> ANIMATED_ENCHANTABLE = malumTag("enchantable/animated");
    public static final TagKey<Item> REBOUND_ENCHANTABLE = malumTag("enchantable/rebound");
    public static final TagKey<Item> ASCENSION_ENCHANTABLE = malumTag("enchantable/ascension");
    public static final TagKey<Item> REPLENISHING_ENCHANTABLE = malumTag("enchantable/replenishing");
    public static final TagKey<Item> HAUNTED_ENCHANTABLE = malumTag("enchantable/haunted");
    public static final TagKey<Item> SPIRIT_SPOILS_ENCHANTABLE = malumTag("enchantable/spirit_spoils");

    public static final TagKey<Item> SPIRITS = malumTag("spirit");
    public static final TagKey<Item> ASPECTED_SPIRITS = malumTag("aspected_spirits");

    public static final TagKey<Item> RUNEWOOD_BOARD_INGREDIENT = malumTag("runewood_board_ingredient");
    public static final TagKey<Item> RUNEWOOD_LOGS = malumTag("runewood_logs");
    public static final TagKey<Item> RUNEWOOD_PLANKS = malumTag("runewood_planks");
    public static final TagKey<Item> RUNEWOOD_SLABS = malumTag("runewood_slabs");
    public static final TagKey<Item> RUNEWOOD_STAIRS = malumTag("runewood_stairs");

    public static final TagKey<Item> SOULWOOD_BOARD_INGREDIENT = malumTag("soulwood_board_ingredient");
    public static final TagKey<Item> SOULWOOD_LOGS = malumTag("soulwood_logs");
    public static final TagKey<Item> SOULWOOD_PLANKS = malumTag("soulwood_planks");
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

    public static final TagKey<Item> SAPBALLS = malumTag("sapballs");
    public static final TagKey<Item> GROSS_FOODS = malumTag("gross_foods");
    public static final TagKey<Item> PROSPECTORS_TREASURE = malumTag("prospectors_treasure");
    public static final TagKey<Item> METAL_NODES = malumTag("metal_nodes");

    public static final TagKey<Item> HIDDEN_ALWAYS = malumTag("hidden_items/always");
    public static final TagKey<Item> HIDDEN_UNTIL_VOID = malumTag("hidden_items/void");
    public static final TagKey<Item> HIDDEN_UNTIL_BLACK_CRYSTAL = malumTag("hidden_items/black_crystal");

    public static final TagKey<Item> KNIVES_FD = modTag("farmersdelight:tools/knives");
    public static final TagKey<Item> KNIVES = forgeTag("tools/knives");

    public static final TagKey<Item> STRIPPED_LOGS = forgeTag("stripped_logs");

    public static final TagKey<Item> ARCANE_ELEGY_COMPONENTS = malumTag("arcane_elegy_component");

    public static final TagKey<Item> BROOCH = modTag("curios:brooch");
    public static final TagKey<Item> BELT = modTag("curios:belt");
    public static final TagKey<Item> CHARM = modTag("curios:charm");
    public static final TagKey<Item> NECKLACE = modTag("curios:necklace");
    public static final TagKey<Item> RING = modTag("curios:ring");
    public static final TagKey<Item> RUNE = modTag("curios:rune");


    private static TagKey<Item> modTag(String path) {
        return TagKey.create(Registries.ITEM, ResourceLocation.tryParse(path));
    }

    private static TagKey<Item> malumTag(String path) {
        return TagKey.create(Registries.ITEM, MalumMod.malumPath(path));
    }

    private static TagKey<Item> forgeTag(String name) {
        return ItemTags.create(ResourceLocation.fromNamespaceAndPath("forge", name));
    }
}
