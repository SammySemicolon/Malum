package com.sammy.malum.registry.common;

import com.sammy.malum.*;
import com.sammy.malum.common.geas.*;
import com.sammy.malum.core.systems.geas.*;
import net.minecraft.core.*;
import net.minecraft.resources.*;
import net.neoforged.neoforge.registries.*;

public class MalumGeasEffectTypeRegistry {

    public static ResourceKey<Registry<GeasEffectType>> GEAS_TYPES_KEY = ResourceKey.createRegistryKey(MalumMod.malumPath("etching_types"));
    public static final DeferredRegister<GeasEffectType> GEAS_TYPES = DeferredRegister.create(GEAS_TYPES_KEY, MalumMod.MALUM);
    public static final Registry<GeasEffectType> GEAS_TYPES_REGISTRY = GEAS_TYPES.makeRegistry(builder -> builder.sync(true));

    public static final DeferredHolder<GeasEffectType, GeasEffectType> BLESSED_MOON = GEAS_TYPES.register("blessed_moon", () -> new GeasEffectType(BlessedMoonGeas::new));
    public static final DeferredHolder<GeasEffectType, GeasEffectType> RADIANT_DAWN = GEAS_TYPES.register("radiant_dawn", () -> new GeasEffectType(RadiantDawnGeas::new));
    public static final DeferredHolder<GeasEffectType, GeasEffectType> SOULDRINKERS_ECSTASY = GEAS_TYPES.register("souldrinkers_ecstasy", () -> new GeasEffectType(SouldrinkerGeas::new));

    public static final DeferredHolder<GeasEffectType, GeasEffectType> MANAWEAVERS_INTEGRITY = GEAS_TYPES.register("manaweavers_integrity", () -> new GeasEffectType(ManaweaverIntegrityGeas::new));
    public static final DeferredHolder<GeasEffectType, GeasEffectType> MANAWEAVERS_OBSESSION = GEAS_TYPES.register("manaweavers_obsession", () -> new GeasEffectType(ManaweaverObsessionGeas::new));
    public static final DeferredHolder<GeasEffectType, GeasEffectType> RUNIC_INFUSION = GEAS_TYPES.register("runic_infusion", () -> new GeasEffectType(RunicInfusionGeas::new));

    public static final DeferredHolder<GeasEffectType, GeasEffectType> OVERKEEN_EYE = GEAS_TYPES.register("overkeen_eye", () -> new GeasEffectType(OverkeenEyeGeas::new));
    public static final DeferredHolder<GeasEffectType, GeasEffectType> OVEREAGER_FIST = GEAS_TYPES.register("overeager_fist", () -> new GeasEffectType(OvereagerFist::new));


    public static final DeferredHolder<GeasEffectType, GeasEffectType> SOULWASHING = GEAS_TYPES.register("soulwashing", () -> new GeasEffectType(SoulwashingGeas::new));
    public static final DeferredHolder<GeasEffectType, GeasEffectType> LIONS_HEART = GEAS_TYPES.register("lions_heart", () -> new GeasEffectType(LionsHeartGeas::new));

}
