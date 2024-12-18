package com.sammy.malum.registry.common.item;

import com.sammy.malum.MalumMod;
import net.minecraft.core.*;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.*;
import net.minecraft.resources.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;

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

    //TODO: move this to lodestone
    public static int getEnchantmentLevel(Level level, ResourceKey<Enchantment> key, ItemStack stack) {
        HolderGetter<Enchantment> enchantmentLookup = level.registryAccess().asGetterLookup().lookupOrThrow(Registries.ENCHANTMENT);
        var opt = enchantmentLookup.get(key);
        return opt.map(enchantmentReference -> stack.get(DataComponents.ENCHANTMENTS).getLevel(enchantmentReference)).orElse(0);
    }

}
