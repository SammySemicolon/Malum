package com.sammy.malum.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.sammy.malum.core.handlers.hiding.flags.FeatureFlagCacher;
import com.sammy.malum.registry.client.HiddenTagRegistry;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlagRegistry;
import net.minecraft.world.flag.FeatureFlagSet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ClientPacketListener.class)
public class ClientPacketListenerMixin implements FeatureFlagCacher {

	@Unique
	private Iterable<ResourceLocation> malum$previousSet;

	@Override
	public Iterable<ResourceLocation> malum$cachedFeatureFlags() {
		return malum$previousSet;
	}

//	@WrapOperation(method = "handleEnabledFeatures", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/flag/FeatureFlagRegistry;fromNames(Ljava/lang/Iterable;)Lnet/minecraft/world/flag/FeatureFlagSet;"))
//	public FeatureFlagSet captureFeatureFlags(FeatureFlagRegistry instance, Iterable<ResourceLocation> names, Operation<FeatureFlagSet> original) {
//		malum$previousSet = names;
//		FeatureFlagSet set = original.call(instance, names);
//		HiddenTagRegistry.attachFeatureFlags(set);
//		return set;
//	}

}
