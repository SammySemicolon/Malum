package com.sammy.malum.mixin;

import com.sammy.malum.MalumMod;
import net.minecraft.core.HolderOwner;
import net.minecraft.core.HolderSet;
import net.minecraft.tags.TagKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HolderSet.Named.class)
public class HolderSetNamedMixin {

    @Inject(method = "<init>", at = @At("TAIL"))
    private void innfsdjh(HolderOwner owner, TagKey key, CallbackInfo ci) {
        MalumMod.innfsdjh(owner, key);
    }
}
