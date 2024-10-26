package com.sammy.malum.mixin.client;

import com.mojang.blaze3d.vertex.*;
import com.sammy.malum.common.item.curiosities.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.layers.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

@Mixin(ItemInHandLayer.class)
public class ItemInHandLayerMixin {

    @Inject(method = "renderArmWithItem", at = @At("HEAD"), cancellable = true)
    protected void malum$renderArmWithItem(LivingEntity pLivingEntity, ItemStack pItemStack, ItemDisplayContext pDisplayContext, HumanoidArm pArm, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, CallbackInfo ci) {
        if (pItemStack.getItem() instanceof TemporarilyDisabledItem) {
            ci.cancel();
        }
    }
}
