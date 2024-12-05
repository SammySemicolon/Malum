package com.sammy.malum.mixin;

import com.sammy.malum.MalumMod;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TagKey.class)
public class TagKeyMixin {
    @Inject(method = "create", at = @At("RETURN"))
    private static void aaa(ResourceKey<? extends Registry<?>> registry, ResourceLocation location, CallbackInfoReturnable<TagKey<?>> cir) {
        TagKey<?> returnValue = cir.getReturnValue();
        MalumMod.aahhhh(registry, location, returnValue);
    }

}
