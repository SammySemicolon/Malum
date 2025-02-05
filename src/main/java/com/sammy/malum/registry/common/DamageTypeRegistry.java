package com.sammy.malum.registry.common;

import com.sammy.malum.MalumMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;

public class DamageTypeRegistry {

    public static final ResourceKey<DamageType> VOODOO = ResourceKey.create(Registries.DAMAGE_TYPE, MalumMod.malumPath("voodoo"));
    public static final ResourceKey<DamageType> VOODOO_PLAYERLESS = ResourceKey.create(Registries.DAMAGE_TYPE, MalumMod.malumPath("voodoo_playerless"));

    public static final ResourceKey<DamageType> NITRATE = ResourceKey.create(Registries.DAMAGE_TYPE, MalumMod.malumPath("nitrate"));
    public static final ResourceKey<DamageType> NITRATE_PLAYERLESS = ResourceKey.create(Registries.DAMAGE_TYPE, MalumMod.malumPath("nitrate_playerless"));

    public static final ResourceKey<DamageType> VOID = ResourceKey.create(Registries.DAMAGE_TYPE, MalumMod.malumPath("void"));

    public static final ResourceKey<DamageType> SCYTHE_MELEE = ResourceKey.create(Registries.DAMAGE_TYPE, MalumMod.malumPath("scythe_melee"));
    public static final ResourceKey<DamageType> SCYTHE_SWEEP = ResourceKey.create(Registries.DAMAGE_TYPE, MalumMod.malumPath("scythe_sweep"));
    public static final ResourceKey<DamageType> SCYTHE_REBOUND = ResourceKey.create(Registries.DAMAGE_TYPE, MalumMod.malumPath("scythe_rebound"));
    public static final ResourceKey<DamageType> SCYTHE_ASCENSION = ResourceKey.create(Registries.DAMAGE_TYPE, MalumMod.malumPath("scythe_ascension"));
    public static final ResourceKey<DamageType> SCYTHE_COMBO = ResourceKey.create(Registries.DAMAGE_TYPE, MalumMod.malumPath("scythe_combo"));
    public static final ResourceKey<DamageType> SCYTHE_MAELSTROM = ResourceKey.create(Registries.DAMAGE_TYPE, MalumMod.malumPath("scythe_maelstrom"));

    public static final ResourceKey<DamageType> HIDDEN_BLADE_MAGIC_COUNTER = ResourceKey.create(Registries.DAMAGE_TYPE, MalumMod.malumPath("hidden_blade_magic_counter"));
    public static final ResourceKey<DamageType> HIDDEN_BLADE_PHYSICAL_COUNTER = ResourceKey.create(Registries.DAMAGE_TYPE, MalumMod.malumPath("hidden_blade_physical_counter"));

    public static final ResourceKey<DamageType> TYRVING = ResourceKey.create(Registries.DAMAGE_TYPE, MalumMod.malumPath("tyrving"));

    public static final ResourceKey<DamageType> SOULWASHING_RETALIATION = ResourceKey.create(Registries.DAMAGE_TYPE, MalumMod.malumPath("soulwashing_retaliation"));
    public static final ResourceKey<DamageType> SOULWASHING_PROPAGATION = ResourceKey.create(Registries.DAMAGE_TYPE, MalumMod.malumPath("soulwashing_propagation"));


}