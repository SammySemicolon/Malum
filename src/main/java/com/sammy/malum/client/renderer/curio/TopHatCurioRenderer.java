package com.sammy.malum.client.renderer.curio;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.sammy.malum.MalumMod;
import com.sammy.malum.registry.client.ModelRegistry;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

public class TopHatCurioRenderer implements ICurioRenderer {

    private static final ResourceLocation HAT = MalumMod.malumPath("textures/cosmetic/tophat.png");

    @Override
    public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack stack, SlotContext slotContext, PoseStack poseStack, RenderLayerParent<T, M> renderLayerParent, MultiBufferSource renderTypeBuffer, int light, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        final LivingEntity livingEntity = slotContext.entity();
        renderTopHat(livingEntity, RenderType.entityTranslucent(HAT), poseStack, renderTypeBuffer, light);
    }

    public static void renderTopHat(LivingEntity livingEntity, RenderType renderType, PoseStack poseStack, MultiBufferSource renderTypeBuffer, int light) {
        VertexConsumer vertexconsumer = renderTypeBuffer.getBuffer(renderType);
        ICurioRenderer.followHeadRotations(livingEntity, ModelRegistry.TOP_HAT.topHat);
        ModelRegistry.TOP_HAT.renderToBuffer(poseStack, vertexconsumer, light, OverlayTexture.NO_OVERLAY, 16777215);
    }
}