package com.sammy.malum.registry.common.item;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.enchantment.*;
import com.sammy.malum.config.CommonConfig;
import net.minecraft.core.registries.*;
import net.minecraft.resources.*;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.*;

public class EnchantmentRegistry {

//    public static final EnchantmentCategory SCYTHE_ONLY = create("scythe_only", i -> i.getDefaultInstance().is(ItemTagRegistry.SCYTHE));
//    public static final EnchantmentCategory STAFF_ONLY = create("staff_only", i -> i.getDefaultInstance().is(ItemTagRegistry.STAFF));
//
//    public static final EnchantmentCategory SOUL_SHATTER_CAPABLE_WEAPON = create("soul_hunter_weapon", i -> i.getDefaultInstance().is(ItemTagRegistry.SOUL_HUNTER_WEAPON));
//    public static final EnchantmentCategory MAGIC_CAPABLE_WEAPON = create("magic_capable_weapon", i -> i.getDefaultInstance().is(ItemTagRegistry.MAGIC_CAPABLE_WEAPON));

    public static final ResourceKey<Enchantment> ANIMATED = keyOf("animated");
    public static final ResourceKey<Enchantment> REBOUND = keyOf("rebound");
    public static final ResourceKey<Enchantment> ASCENSION = keyOf("ascension");
    public static final ResourceKey<Enchantment> REPLENISHING = keyOf("replenishing");
    public static final ResourceKey<Enchantment> HAUNTED = keyOf("haunted");
    public static final ResourceKey<Enchantment> SPIRIT_PLUNDER = keyOf("spirit_plunder");

    static ResourceKey<Enchantment> keyOf(String id) {
        return ResourceKey.create(Registries.ENCHANTMENT, MalumMod.malumPath(id));
    }
}
