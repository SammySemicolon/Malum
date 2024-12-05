package com.sammy.malum.mixin;

import com.sammy.malum.MalumMod;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.MappedRegistry;
import net.minecraft.tags.TagKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Map;

@Mixin(MappedRegistry.class)
public class MappedRegistryMixin {

    @Inject(method = "bindTags", at = @At("HEAD"))
    private void gsdg(Map<TagKey<?>, List<Holder<?>>> tagMap, CallbackInfo ci) {

    }
    @Inject(method = "createTag", at = @At("HEAD"))
    private void gsdgdd(TagKey<?> key, CallbackInfoReturnable<HolderSet.Named<?>> cir) {
        MalumMod.innfddddsdjh(key);
    }
}
