package com.sammy.malum.registry.common;

import com.sammy.malum.MalumMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageScaling;
import net.minecraft.world.damagesource.DamageType;

public class DamageTypeRegistry {

    public static final String VOODOO_IDENTIFIER = "voodoo";
    public static final String SCYTHE_SWEEP_IDENTIFIER = "scythe_sweep";

    public static final ResourceKey<DamageType> VOODOO_PLAYERLESS = ResourceKey.create(Registries.DAMAGE_TYPE, MalumMod.malumPath("voodoo_playerless"));
    public static final ResourceKey<DamageType> VOODOO = ResourceKey.create(Registries.DAMAGE_TYPE, MalumMod.malumPath("voodoo"));
    public static final ResourceKey<DamageType> VOID = ResourceKey.create(Registries.DAMAGE_TYPE, MalumMod.malumPath("void"));

    public static final ResourceKey<DamageType> SCYTHE_MELEE = ResourceKey.create(Registries.DAMAGE_TYPE, MalumMod.malumPath("scythe_melee"));

    public static final ResourceKey<DamageType> SCYTHE_SWEEP = ResourceKey.create(Registries.DAMAGE_TYPE, MalumMod.malumPath("scythe_sweep"));

    public static final ResourceKey<DamageType> HIDDEN_BLADE_COUNTER = ResourceKey.create(Registries.DAMAGE_TYPE, MalumMod.malumPath("hidden_blade_counter"));

    public static void bootstrap(BootstrapContext<DamageType> context) {
        context.register(VOODOO, new DamageType(VOODOO_IDENTIFIER, DamageScaling.WHEN_CAUSED_BY_LIVING_NON_PLAYER, 0));
        context.register(SCYTHE_SWEEP, new DamageType(SCYTHE_SWEEP_IDENTIFIER, DamageScaling.WHEN_CAUSED_BY_LIVING_NON_PLAYER, 0));
    }
}