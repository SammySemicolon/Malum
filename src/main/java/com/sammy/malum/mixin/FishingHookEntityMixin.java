package com.sammy.malum.mixin;

import com.sammy.malum.registry.common.MobEffectRegistry;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FishingHook.class)
public class FishingHookEntityMixin {

    @Mutable
    @Shadow
    @Final
    private int luck;
    @Mutable
    @Shadow
    @Final
    private int lureSpeed;
    @Unique
    private Player malum$player;

    @ModifyVariable(method = "<init>(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/level/Level;II)V", at = @At("RETURN"), index = 1, argsOnly = true)
    private Player malumFishingStatChangesPlayerGrabberMixin(Player player) {
        return this.malum$player = player;
    }

    @Inject(method = "<init>(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/level/Level;II)V", at = @At("RETURN"))
    private void malumModifyFishingLuckStatsMixin(Player p_37106_, Level p_37107_, int p_37108_, int p_37109_, CallbackInfo ci) {
        if (malum$player.hasEffect(MobEffectRegistry.ANGLERS_LURE)) {
            float bonus = (malum$player.getEffect(MobEffectRegistry.ANGLERS_LURE).getAmplifier() / 2f);
            luck += bonus * 2f;
            lureSpeed += bonus + 0.5f;
        }
    }
}