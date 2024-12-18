package com.sammy.malum.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.sammy.malum.common.item.curiosities.curios.sets.rotten.CurioVoraciousRing;
import com.sammy.malum.common.item.curiosities.curios.sets.weeping.CurioGruesomeConcentrationRing;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.sammy.malum.registry.common.AttributeRegistry.*;
import static com.sammy.malum.registry.common.AttributeRegistry.MALIGNANT_CONVERSION;
import static team.lodestar.lodestone.registry.common.LodestoneAttributes.*;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @Inject(method = "eat", at = @At("HEAD"))
    private void malum$eat(Level pLevel, ItemStack pFood, CallbackInfoReturnable<ItemStack> cir) {
        LivingEntity livingEntity = LivingEntity.class.cast(this);
        if (pFood.has(DataComponents.FOOD)) {
            CurioVoraciousRing.onEat(pLevel, livingEntity, pFood);
            CurioGruesomeConcentrationRing.onEat(pLevel, livingEntity, pFood);
        }
    }

    @ModifyReturnValue(method = "createLivingAttributes", at = @At("RETURN"))
    private static AttributeSupplier.Builder lodestone$CreateLivingAttributes(AttributeSupplier.Builder original) {
        return original
                .add(MAGIC_RESISTANCE)
                .add(MAGIC_PROFICIENCY)
                .add(MAGIC_DAMAGE)
                .add(SCYTHE_PROFICIENCY)
                .add(SPIRIT_SPOILS)
                .add(ARCANE_RESONANCE)
                .add(SOUL_WARD_INTEGRITY)
                .add(SOUL_WARD_RECOVERY_RATE)
                .add(SOUL_WARD_CAPACITY)
                .add(RESERVE_STAFF_CHARGES)
                .add(MALIGNANT_CONVERSION);
    }
}
