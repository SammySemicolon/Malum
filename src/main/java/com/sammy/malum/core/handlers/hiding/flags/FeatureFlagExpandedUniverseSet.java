package com.sammy.malum.core.handlers.hiding.flags;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlagSet;
import org.jetbrains.annotations.Nullable;


public interface FeatureFlagExpandedUniverseSet {
	@Nullable
	FeatureFlagSet malum$getAttachedFeatureSet(ResourceLocation location);

	@Nullable
	UncappedFeatureFlagSet malum$getAttachedUncappedFeatureSet(ResourceLocation location);

	void malum$attachFeatureSet(FeatureFlagSet set);

	void malum$attachFeatureSet(UncappedFeatureFlagSet set);

	FeatureFlagSet malum$copyWithoutExpansion();
}
