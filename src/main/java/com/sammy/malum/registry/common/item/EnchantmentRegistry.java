package com.sammy.malum.registry.common.item;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.enchantment.*;
import com.sammy.malum.config.CommonConfig;
import net.minecraft.core.*;
import net.minecraft.core.registries.*;
import net.minecraft.resources.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.*;
import java.util.logging.*;

public class EnchantmentRegistry {

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
