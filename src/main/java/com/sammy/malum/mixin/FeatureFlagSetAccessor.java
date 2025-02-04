package com.sammy.malum.mixin;

import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlagUniverse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(FeatureFlagSet.class)
public interface FeatureFlagSetAccessor {

//	@Accessor("universe")
//	FeatureFlagUniverse malum$getUniverse();

	@Invoker("<init>")
	static FeatureFlagSet malum$createNewSet(FeatureFlagUniverse universe, long mask) {
		throw new IllegalStateException("Mixin did not work");
	}
}
