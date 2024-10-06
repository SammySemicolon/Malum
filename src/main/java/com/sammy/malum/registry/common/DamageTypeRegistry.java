package com.sammy.malum.registry.common;

import com.sammy.malum.MalumMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class DamageTypeRegistry {

    public static final String VOODOO_IDENTIFIER = "voodoo";
    public static final String SCYTHE_SWEEP_IDENTIFIER = "scythe_sweep";

    public static final ResourceKey<DamageType> VOODOO = ResourceKey.create(Registries.DAMAGE_TYPE, MalumMod.malumPath("voodoo"));

    public static final ResourceKey<DamageType> SCYTHE_MELEE = ResourceKey.create(Registries.DAMAGE_TYPE, MalumMod.malumPath("scythe_melee"));
    public static final ResourceKey<DamageType> SCYTHE_SWEEP = ResourceKey.create(Registries.DAMAGE_TYPE, MalumMod.malumPath("scythe_sweep"));
    public static final ResourceKey<DamageType> HIDDEN_BLADE_COUNTER = ResourceKey.create(Registries.DAMAGE_TYPE, MalumMod.malumPath("hidden_blade_counter"));
}