package com.sammy.malum.mixin;

import com.sammy.malum.common.item.curiosities.curios.sets.rotten.CurioVoraciousRing;
import com.sammy.malum.common.item.curiosities.curios.sets.weeping.CurioGruesomeConcentrationRing;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @Inject(method = "eat", at = @At("HEAD"))
    private void malum$eat(Level pLevel, ItemStack pFood, CallbackInfoReturnable<ItemStack> cir) {
        if (pFood.isEdible()) {
            LivingEntity livingEntity = (LivingEntity) ((Object)(this));
            CurioVoraciousRing.onEat(pLevel, livingEntity, pFood);
            CurioGruesomeConcentrationRing.onEat(pLevel, livingEntity, pFood);
        }
    }
}
