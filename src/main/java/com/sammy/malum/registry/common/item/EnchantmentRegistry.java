package com.sammy.malum.registry.common.item;

import com.sammy.malum.MalumMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.neoforge.registries.DeferredRegister;

public class EnchantmentRegistry {

    public static final EnchantmentCategory SOUL_HUNTER_WEAPON = EnchantmentCategory.create(MalumMod.MALUM + ":soul_hunter_only", i -> i.getDefaultInstance().is(ItemTagRegistry.SOUL_HUNTER_WEAPON));
    public static final EnchantmentCategory SCYTHE = EnchantmentCategory.create(MalumMod.MALUM + ":scythe_only", i -> i.getDefaultInstance().is(ItemTagRegistry.SCYTHE));
    public static final EnchantmentCategory REBOUND_SCYTHE = EnchantmentCategory.create(MalumMod.MALUM + ":rebound_only", i -> i.getDefaultInstance().is(ItemTagRegistry.SCYTHE) || (CommonConfig.ULTIMATE_REBOUND.getConfigValue() && i instanceof TieredItem));
    public static final EnchantmentCategory STAFF = EnchantmentCategory.create(MalumMod.MALUM + ":staff_only", i -> i.getDefaultInstance().is(ItemTagRegistry.STAFF));
    public static final EnchantmentCategory SCYTHE_OR_STAFF = EnchantmentCategory.create(MalumMod.MALUM + ":scythe_or_staff", i -> i.getDefaultInstance().is(ItemTagRegistry.SCYTHE) || i.getDefaultInstance().is(ItemTagRegistry.STAFF));

    public static final ResourceKey<Enchantment> REBOUND = keyOf("rebound");
    public static final ResourceKey<Enchantment> HAUNTED = keyOf("haunted");
    public static final ResourceKey<Enchantment> SPIRIT_PLUNDER = keyOf("spirit_plunder");
    public static final ResourceKey<Enchantment> REPLENISHING = keyOf("replenishing");

    static ResourceKey<Enchantment> keyOf(String id) {
        return ResourceKey.create(Registries.ENCHANTMENT, MalumMod.malumPath(id));
    }
}
