package com.sammy.malum.mixin;

import com.sammy.malum.common.geas.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

@Mixin(MobEffectInstance.class)
public class MobEffectInstanceMixin {

    @Unique
    private LivingEntity malum$entity;

    @Inject(method = "tick", at = @At("HEAD"))
    private void malum$cacheEntity(LivingEntity entity, Runnable onExpirationRunnable, CallbackInfoReturnable<Boolean> cir) {
        malum$entity = entity;
    }

    @Inject(method = "tickDownDuration", at = @At("HEAD"), cancellable = true)
    private void malum$tickDownDuration(CallbackInfoReturnable<Integer> cir) {
        if (malum$entity != null) {
            final MobEffectInstance instance = (MobEffectInstance) (Object) this;
            if (LionsHeartGeas.pausePotionEffects(malum$entity, instance)) {
                cir.setReturnValue((instance).getDuration());
            }
        }
    }
}
