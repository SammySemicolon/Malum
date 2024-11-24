package com.sammy.malum.registry.common.worldgen;

import net.minecraft.world.level.block.grower.*;

import java.util.*;

public class TreeGrowerRegistry {

    public static final TreeGrower RUNEWOOD = new TreeGrower("malum:runewood", Optional.empty(), Optional.of(ConfiguredFeatureRegistry.CONFIGURED_RUNEWOOD_TREE), Optional.empty());
    public static final TreeGrower AZURE_RUNEWOOD = new TreeGrower("malum:azure_runewood", Optional.empty(), Optional.of(ConfiguredFeatureRegistry.CONFIGURED_AZURE_RUNEWOOD_TREE), Optional.empty());
    public static final TreeGrower SOULWOOD = new TreeGrower("malum:soulwood", Optional.empty(), Optional.of(ConfiguredFeatureRegistry.CONFIGURED_SOULWOOD_TREE), Optional.empty());

}
