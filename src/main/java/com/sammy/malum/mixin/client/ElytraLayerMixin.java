package com.sammy.malum.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.sammy.malum.core.ElytraLayerExtensions;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.sammy.malum.client.renderer.curio.TokenOfGratitudeRenderer.getElytraTexture;

@Environment(EnvType.CLIENT)
@Mixin(ElytraLayer.class)
public abstract class ElytraLayerMixin<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> implements ElytraLayerExtensions<T> {
    public ElytraLayerMixin(RenderLayerParent<T, M> renderer) {
        super(renderer);
    }

    @WrapOperation(method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/LivingEntity;FFFFFF)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z")
    )
    private boolean malum$mod(ItemStack instance, Item item, Operation<Boolean> original, @Local(argsOnly = true) T livingEntity) {
        if (shouldRender(instance, livingEntity)) {
            return original.call(instance, item);
        }
        return false;
    }

    @WrapOperation(method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/LivingEntity;FFFFFF)V",
            at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/entity/layers/ElytraLayer;WINGS_LOCATION:Lnet/minecraft/resources/ResourceLocation;")
    )
    private ResourceLocation malum$changeTexture(Operation<ResourceLocation> original, @Local(argsOnly = true) T livingEntity, @Local ItemStack itemStack) {
        var tex = getElytraTexture(itemStack, livingEntity);
        if (tex != null) {
            return tex;
        }
        return original.call();
    }
}
