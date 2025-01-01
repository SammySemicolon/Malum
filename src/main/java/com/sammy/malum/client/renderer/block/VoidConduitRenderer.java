package com.sammy.malum.client.renderer.block;

import com.mojang.blaze3d.vertex.*;
import com.mojang.math.*;
import com.sammy.malum.common.block.curiosities.weeping_well.*;
import com.sammy.malum.registry.client.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.blockentity.*;
import org.joml.*;
import team.lodestar.lodestone.handlers.*;
import team.lodestar.lodestone.registry.client.*;
import team.lodestar.lodestone.systems.rendering.*;


public class VoidConduitRenderer implements BlockEntityRenderer<VoidConduitBlockEntity> {

    public VoidConduitRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(VoidConduitBlockEntity blockEntityIn, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        renderQuad(blockEntityIn, poseStack, partialTicks);
    }

    public void renderQuad(VoidConduitBlockEntity voidConduit, PoseStack poseStack, float partialTicks) {
        if (voidConduit.lingeringRadiance == 0) {
            float height = 0.375f;
            float width = 1.5f;


            Vector3f[] positions = new Vector3f[]{new Vector3f(-width, height, width), new Vector3f(width, height, width), new Vector3f(width, height, -width), new Vector3f(-width, height, -width)};
            VFXBuilders.WorldVFXBuilder builder = VFXBuilders.createWorld();
            poseStack.pushPose();
            poseStack.translate(0.5f, 0.01f, 0.5f);

            builder.replaceBufferSource(RenderHandler.LATE_DELAYED_RENDER.getTarget()).setRenderType(LodestoneRenderTypes.TRANSPARENT_TEXTURE.applyAndCache(MalumRenderTypeTokens.VOID_VIGNETTE)).renderQuad(poseStack, positions, 1f);
            final long gameTime = voidConduit.getLevel().getGameTime();
            float uOffset = ((gameTime + partialTicks) % 4000) / 2000f;
            float vOffset = ((gameTime + 500f + partialTicks) % 8000) / 8000f;
            float alpha = 0.05f;

            final LodestoneRenderType renderType = LodestoneRenderTypes.ADDITIVE_DISTORTED_TEXTURE.applyAndCache(MalumRenderTypeTokens.VOID_NOISE);
            builder.replaceBufferSource(RenderHandler.DELAYED_RENDER.getTarget());
            for (int i = 0; i < 3; i++) {
                builder.setAlpha(alpha);
                float speed = 1000f + 250f * i;
                builder.setColor(SpiritTypeRegistry.WICKED_SPIRIT.getPrimaryColor()).setRenderType(LodestoneRenderTypes.applyUniformChanges(LodestoneRenderTypes.copyAndStore(i, renderType), s -> {
                    s.safeGetUniform("Speed").set(speed);
                    s.safeGetUniform("Width").set(48f);
                    s.safeGetUniform("Height").set(48f);
                    s.safeGetUniform("UVEncasement").set(new Vector4f(-10, 20, -10, 20));
                }));
                builder.setUV(-uOffset, vOffset, 1 - uOffset, 1 + vOffset).renderQuad(poseStack, positions, 1f);
                builder.setUV(uOffset, -vOffset, 1 + uOffset, 1 - vOffset).renderQuad(poseStack, positions, 1f);
                alpha -= 0.0125f;
                uOffset = -uOffset - 0.2f;
                vOffset = -vOffset + 0.4f;
                poseStack.translate(0, 0.05f, 0);
                poseStack.mulPose(Axis.YP.rotationDegrees(90));
                if (i == 0) {
                    builder.setColor(SpiritTypeRegistry.ELDRITCH_SPIRIT.getPrimaryColor());
                }
            }
            poseStack.popPose();
        }
    }
}