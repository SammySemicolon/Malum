package com.sammy.malum.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.sammy.malum.MalumMod;
import com.sammy.malum.client.RenderUtils;
import com.sammy.malum.common.block.curiosities.redstone.ChronopulserBlockEntity;
import com.sammy.malum.common.block.curiosities.redstone.PulsebankBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.util.Mth;
import team.lodestar.lodestone.registry.client.LodestoneRenderTypes;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.rendering.VFXBuilders;
import team.lodestar.lodestone.systems.rendering.rendeertype.RenderTypeToken;

import java.awt.*;

import static com.sammy.malum.client.RenderUtils.drawCubeSides;


public class PulsebankRenderer implements BlockEntityRenderer<PulsebankBlockEntity> {

    private static final RenderTypeToken TOKEN = RenderTypeToken.createToken(MalumMod.malumPath("textures/block/redstone/pulsebank_overlay.png"));
    private static final Color COLOR = new Color(170, 15, 1);

    public PulsebankRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(PulsebankBlockEntity blockEntityIn, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        float pct = Mth.clamp(blockEntityIn.timer / 40f, 0, 1);
        if (pct > 0) {
            float alpha = Easing.CUBIC_OUT.clamped(pct, 0, 1) * 0.8f;
            float glowAlpha = Easing.SINE_OUT.clamped(pct, 0, 1) * 0.4f;
            var cubeVertexData = RenderUtils.makeCubePositions(1f);
            var builder = VFXBuilders.createWorld()
                    .setColor(COLOR);
            poseStack.pushPose();
            drawCubeSides(poseStack, builder.setAlpha(alpha).setRenderType(LodestoneRenderTypes.TRANSPARENT_TEXTURE.applyAndCache(TOKEN)), 1.00125f, cubeVertexData);
            drawCubeSides(poseStack, builder.setAlpha(glowAlpha).setRenderType(LodestoneRenderTypes.ADDITIVE_TEXTURE.applyAndCache(TOKEN)), 1.0025f, cubeVertexData);
            poseStack.popPose();
        }
    }
}
