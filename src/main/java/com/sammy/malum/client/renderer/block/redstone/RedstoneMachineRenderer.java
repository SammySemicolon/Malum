package com.sammy.malum.client.renderer.block.redstone;

import com.mojang.blaze3d.vertex.PoseStack;
import com.sammy.malum.client.RenderUtils;
import com.sammy.malum.common.block.curiosities.redstone.RedstoneMachineBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import team.lodestar.lodestone.registry.client.LodestoneRenderTypes;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.rendering.VFXBuilders;
import team.lodestar.lodestone.systems.rendering.rendeertype.RenderTypeToken;

import java.awt.*;

import static com.sammy.malum.client.RenderUtils.drawCube;
import static com.sammy.malum.client.RenderUtils.drawCubeSides;


public abstract class RedstoneMachineRenderer<T extends RedstoneMachineBlockEntity> implements BlockEntityRenderer<T> {

    private final RenderTypeToken token;
    private static final Color COLOR = new Color(170, 15, 1);

    public RedstoneMachineRenderer(BlockEntityRendererProvider.Context context, ResourceLocation tokenTexture) {
        this.token = RenderTypeToken.createToken(tokenTexture);
    }

    public abstract float getGlowDelta(T blockEntityIn);
    @Override
    public void render(T blockEntityIn, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        float pct = Mth.clamp(getGlowDelta(blockEntityIn), 0, 1);
        if (pct > 0) {
            float alpha = Easing.CUBIC_OUT.clamped(pct, 0, 1) * 0.8f;
            float glowAlpha = Easing.SINE_OUT.clamped(pct, 0, 1) * 0.4f;
            var cubeVertexData = RenderUtils.makeCubePositions(1f);
            var builder = VFXBuilders.createWorld()
                    .setColor(COLOR);
            poseStack.pushPose();
            drawCubeSides(poseStack, builder.setAlpha(alpha).setRenderType(LodestoneRenderTypes.TRANSPARENT_TEXTURE.applyAndCache(token)), 1.00125f, cubeVertexData);
            drawCubeSides(poseStack, builder.setAlpha(glowAlpha).setRenderType(LodestoneRenderTypes.ADDITIVE_TEXTURE.applyAndCache(token)), 1.0025f, cubeVertexData);
            poseStack.popPose();
        }
    }
}
