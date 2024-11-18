package com.sammy.malum.registry.common.item;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.enchantment.*;
import com.sammy.malum.common.enchantment.scythe.*;
import com.sammy.malum.common.enchantment.staff.*;
import com.sammy.malum.compability.irons_spellbooks.IronsSpellsCompat;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.*;

public class EnchantmentRegistry {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, MalumMod.MALUM);

    public static final EnchantmentCategory SCYTHE_ONLY = create("scythe_only", i -> i.getDefaultInstance().is(ItemTagRegistry.SCYTHE));
    public static final EnchantmentCategory STAFF_ONLY = create("staff_only", i -> i.getDefaultInstance().is(ItemTagRegistry.STAFF) || IronsSpellsCompat.isStaff(i.getDefaultInstance()));

    public static final EnchantmentCategory SOUL_SHATTER_CAPABLE_WEAPON = create("soul_hunter_weapon", i -> i.getDefaultInstance().is(ItemTagRegistry.SOUL_HUNTER_WEAPON));
    public static final EnchantmentCategory MAGIC_CAPABLE_WEAPON = create("magic_capable_weapon", i -> i.getDefaultInstance().is(ItemTagRegistry.MAGIC_CAPABLE_WEAPON));

    //Scythe
    public static final RegistryObject<Enchantment> ANIMATED = ENCHANTMENTS.register("animated", AnimatedEnchantment::new);
    public static final RegistryObject<Enchantment> REBOUND = ENCHANTMENTS.register("rebound", ReboundEnchantment::new);
    public static final RegistryObject<Enchantment> ASCENSION = ENCHANTMENTS.register("ascension", AscensionEnchantment::new);

    //Staff
    public static final RegistryObject<Enchantment> REPLENISHING = ENCHANTMENTS.register("replenishing", ReplenishingEnchantment::new);

    //Common
    public static final RegistryObject<Enchantment> HAUNTED = ENCHANTMENTS.register("haunted", HauntedEnchantment::new);
    public static final RegistryObject<Enchantment> SPIRIT_PLUNDER = ENCHANTMENTS.register("spirit_plunder", SpiritPlunderEnchantment::new);

    public static EnchantmentCategory create(String name, Predicate<Item> predicate) {
        return EnchantmentCategory.create(MalumMod.MALUM + ":" + name, predicate);
    }
}
