package com.sammy.malum.client.renderer.curio;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.sammy.malum.MalumMod;
import com.sammy.malum.client.model.TopHatModel;
import com.sammy.malum.registry.client.ModelRegistry;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.client.TrinketRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class TopHatCurioRenderer implements TrinketRenderer {

    public static TopHatModel TOP_HAT;
    private static final ResourceLocation HAT = MalumMod.malumPath("textures/cosmetic/tophat.png");

    @Override
    public void render(ItemStack itemStack, SlotReference slotReference, EntityModel<? extends LivingEntity> entityModel, PoseStack poseStack, MultiBufferSource multiBufferSource, int light, LivingEntity livingEntity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        renderTopHat(livingEntity, entityModel, RenderType.entityTranslucent(HAT), poseStack, multiBufferSource, light, headYaw, headPitch);
    }

    public static void renderTopHat(LivingEntity livingEntity, EntityModel<? extends LivingEntity> entityModel, RenderType renderType, PoseStack poseStack, MultiBufferSource renderTypeBuffer, int light, float headYaw, float headPitch) {

        if (TOP_HAT == null) {
            TOP_HAT = new TopHatModel(Minecraft.getInstance().getEntityModels().bakeLayer(TopHatModel.LAYER));
        }
        VertexConsumer vertexconsumer = renderTypeBuffer.getBuffer(renderType);
        if (livingEntity instanceof AbstractClientPlayer player) {
            TrinketRenderer.translateToFace(poseStack, (PlayerModel<AbstractClientPlayer>) entityModel, player, headYaw, headPitch);
            poseStack.translate(0, 0.2, 0.3f);
        }
        TOP_HAT.renderToBuffer(poseStack, vertexconsumer, light, OverlayTexture.NO_OVERLAY, 1);
    }
}