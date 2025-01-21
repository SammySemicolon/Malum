package com.sammy.malum.registry.common;

import com.sammy.malum.*;
import com.sammy.malum.common.etching.*;
import com.sammy.malum.core.systems.etching.*;
import net.minecraft.core.*;
import net.minecraft.resources.*;
import net.neoforged.neoforge.registries.*;
import team.lodestar.lodestone.*;
import team.lodestar.lodestone.systems.worldevent.*;

public class MalumEtchingEffectTypeRegistry {

    public static ResourceKey<Registry<EtchingEffectType>> ETCHING_TYPES_KEY = ResourceKey.createRegistryKey(MalumMod.malumPath("etching_types"));
    public static final DeferredRegister<EtchingEffectType> ETCHING_TYPES = DeferredRegister.create(ETCHING_TYPES_KEY, MalumMod.MALUM);
    public static final Registry<EtchingEffectType> ETCHING_TYPE_REGISTRY = ETCHING_TYPES.makeRegistry(builder -> builder.sync(true));

    public static final DeferredHolder<EtchingEffectType, EtchingEffectType> BLESSED_MOON = ETCHING_TYPES.register("blessed_moon", () -> new EtchingEffectType(BlessedMoonEtching::new));
    public static final DeferredHolder<EtchingEffectType, EtchingEffectType> RADIANT_DAWN = ETCHING_TYPES.register("radiant_dawn", () -> new EtchingEffectType(RadiantDawnEtching::new));
    public static final DeferredHolder<EtchingEffectType, EtchingEffectType> SOULDRINKER = ETCHING_TYPES.register("souldrinker", () -> new EtchingEffectType(SouldrinkerEtching::new));

    public static final DeferredHolder<EtchingEffectType, EtchingEffectType> MANAWEAVERS_INTEGRITY = ETCHING_TYPES.register("manaweavers_integrity", () -> new EtchingEffectType(ManaweaverIntegrityEtching::new));
    public static final DeferredHolder<EtchingEffectType, EtchingEffectType> MANAWEAVERS_OBSESSION = ETCHING_TYPES.register("manaweavers_obsession", () -> new EtchingEffectType(ManaweaverObsessionEtching::new));
    public static final DeferredHolder<EtchingEffectType, EtchingEffectType> RUNIC_INFUSION = ETCHING_TYPES.register("runic_infusion", () -> new EtchingEffectType(RunicInfusionEtching::new));
}
