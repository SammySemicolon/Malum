package com.sammy.malum.mixin.client;

import com.sammy.malum.common.item.curiosities.*;
import com.sammy.malum.common.item.curiosities.weapons.scythe.*;
import net.minecraft.client.renderer.*;
import net.minecraft.world.item.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(ItemInHandRenderer.class)
public class ItemInHandRendererMixin {

    @ModifyVariable(method = "renderArmWithItem", at = @At(value = "HEAD"), index = 6, argsOnly = true)
    protected ItemStack malum$renderArmWithItem(ItemStack stack) {
        if (stack.getItem() instanceof TemporarilyDisabledItem) {
            return ItemStack.EMPTY;
        }
        return stack;
    }
}
