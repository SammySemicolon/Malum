package com.sammy.malum.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.sammy.malum.*;
import com.sammy.malum.core.handlers.hiding.flags.FeatureFlagExpandedUniverseSet;
import com.sammy.malum.core.handlers.hiding.flags.UncappedFeatureFlagSet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlagUniverse;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Mixin(FeatureFlagSet.class)
public class FeatureFlagSetMixin implements FeatureFlagExpandedUniverseSet {

	@Shadow @Final @Nullable private FeatureFlagUniverse universe;
	@Shadow @Final private long mask;

	@Unique
	private Map<ResourceLocation, FeatureFlagSet> malum$featureFlags;
	@Unique
	private Map<ResourceLocation, UncappedFeatureFlagSet> malum$uncappedFeatureFlags;

	@Nullable
	@Override
	public FeatureFlagSet malum$getAttachedFeatureSet(ResourceLocation location) {
		if (malum$featureFlags == null)
			return null;
		return malum$featureFlags.get(location);
	}

	@Nullable
	@Override
	public UncappedFeatureFlagSet malum$getAttachedUncappedFeatureSet(ResourceLocation location) {
		if (malum$uncappedFeatureFlags == null)
			return null;
		return malum$uncappedFeatureFlags.get(location);
	}

	@Override
	public void malum$attachFeatureSet(FeatureFlagSet set) {
//		FeatureFlagUniverse universe = ((AccessorFeatureFlagSet) (Object) set).malum$getUniverse();
//
//		if (universe != null) {
//			if (malum$featureFlags == null)
//				malum$featureFlags = new HashMap<>();
//			malum$featureFlags.put(MalumMod.malumPath(universe.toString()), set);
//		}
	}

	@Override
	public void malum$attachFeatureSet(UncappedFeatureFlagSet set) {
		FeatureFlagUniverse universe = set.getUniverse();

		if (universe != null) {
			if (malum$uncappedFeatureFlags == null)
				malum$uncappedFeatureFlags = new HashMap<>();
			malum$uncappedFeatureFlags.put(MalumMod.malumPath(universe.toString()), set);
		}
	}

	@Override
	public FeatureFlagSet malum$copyWithoutExpansion() {
		return AccessorFeatureFlagSet.malum$createNewSet(universe, mask);
	}

	@ModifyReturnValue(method = "equals", at = @At("RETURN"))
	private boolean equals(boolean value, Object pOther) {
		if (value) {
			// We can safely assume the other object is a FeatureFlagSet - and by extension has been mixed in
			FeatureFlagSetMixin other = (FeatureFlagSetMixin) pOther;
			boolean regularBothEmpty = (malum$featureFlags == null || malum$featureFlags.isEmpty()) &&
				(other.malum$featureFlags == null || other.malum$featureFlags.isEmpty());
			boolean uncappedBothEmpty = (malum$uncappedFeatureFlags == null || malum$uncappedFeatureFlags.isEmpty()) &&
				(other.malum$uncappedFeatureFlags == null || other.malum$uncappedFeatureFlags.isEmpty());

			return (regularBothEmpty || Objects.equals(malum$featureFlags, other.malum$featureFlags)) &&
				(uncappedBothEmpty || Objects.equals(malum$uncappedFeatureFlags, other.malum$uncappedFeatureFlags));
		}

		return false;
	}

	@ModifyReturnValue(method = "hashCode", at = @At("RETURN"))
	private int modifyHashCode(int hashCode) {
		if (malum$uncappedFeatureFlags == null && malum$featureFlags == null)
			return hashCode;
		return Objects.hash(hashCode, malum$featureFlags, malum$uncappedFeatureFlags);
	}
}
