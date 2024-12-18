package com.sammy.malum.registry.common.worldgen;

import com.sammy.malum.common.worldgen.ore.*;
import com.sammy.malum.common.worldgen.tree.*;
import io.github.fabricators_of_create.porting_lib.util.DeferredHolder;
import io.github.fabricators_of_create.porting_lib.util.DeferredRegister;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.*;

import static com.sammy.malum.MalumMod.*;

public class FeatureRegistry {

    public static final DeferredRegister<Feature<?>> FEATURE_TYPES = DeferredRegister.create(BuiltInRegistries.FEATURE, MALUM);

    public static final DeferredHolder<Feature<?>, RunewoodTreeFeature> RUNEWOOD_TREE = FEATURE_TYPES.register("runewood_tree", RunewoodTreeFeature::new);
    public static final DeferredHolder<Feature<?>, SoulwoodTreeFeature> SOULWOOD_TREE = FEATURE_TYPES.register("soulwood_tree", SoulwoodTreeFeature::new);
    public static final DeferredHolder<Feature<?>, LayeredOreFeature> CTHONIC_GOLD_ORE = FEATURE_TYPES.register("cthonic_gold_ore", CthonicGoldOreFeature::new);
}