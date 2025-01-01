package com.sammy.malum.client.renderer.block.redstone;

import com.mojang.blaze3d.vertex.PoseStack;
import com.sammy.malum.MalumMod;
import com.sammy.malum.client.RenderUtils;
import com.sammy.malum.common.block.curiosities.redstone.pulsebank.PulsebankBlockEntity;
import com.sammy.malum.common.block.curiosities.redstone.pulsecharger.PulsechargerBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.util.Mth;
import team.lodestar.lodestone.registry.client.LodestoneRenderTypes;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.rendering.VFXBuilders;

import static com.sammy.malum.client.RenderUtils.drawCubeSides;


public class PulseChargerRenderer extends RedstoneDiodeRenderer<PulsechargerBlockEntity> {

    public PulseChargerRenderer(BlockEntityRendererProvider.Context context) {
        super(context, MalumMod.malumPath("textures/block/redstone/pulsecharger_overlay.png"));
    }

    @Override
    public float getGlowDelta(PulsechargerBlockEntity blockEntityIn) {
        int desiredSignal = blockEntityIn.desiredSignal;
        int currentSignal = blockEntityIn.currentSignal;
        float partial = (float) blockEntityIn.timer / Math.max(Math.round(blockEntityIn.frequency / 15f), 2);
        if (currentSignal > desiredSignal) {
            return (currentSignal - partial) / 15f;
        } else {
            return (currentSignal + partial) / 15f;
        }
    }
    @Override
    public void render(PulsechargerBlockEntity blockEntityIn, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        float pct = Mth.clamp(getGlowDelta(blockEntityIn), 0, 1);
        var builder = VFXBuilders.createWorld()
                .setColor(COLOR);
        var cubeVertexData = RenderUtils.makeCubePositions(1f);
        if (blockEntityIn.desiredSignal != 0 || blockEntityIn.currentSignal != 0) {
            if (blockEntityIn.desiredSignal == 0) {
                builder.setAlpha(Easing.CUBIC_OUT.clamped(pct, 0, 1) * 0.8f);
            }
            else {
                builder.setAlpha(Easing.CUBIC_OUT.clamped(pct*4f, 0, 1) * 0.8f);
            }
            drawCubeSides(poseStack, builder.setRenderType(LodestoneRenderTypes.TRANSPARENT_TEXTURE.applyAndCache(token)), 1.00125f, cubeVertexData);
        }
        if (pct > 0) {
            float height = pct * 8;
            float uvStart = 0.25f;
            float uvEnd = uvStart + height * 0.0625f;
            float glowAlpha = Easing.SINE_OUT.clamped(pct, 0, 1) * 0.4f;
            cubeVertexData = RenderUtils.makeCubePositions(0, 1, uvStart, uvEnd);
            builder.setUV(0, uvEnd, 1, uvStart);
            poseStack.pushPose();
            drawCubeSides(poseStack, builder.setAlpha(glowAlpha).setRenderType(LodestoneRenderTypes.ADDITIVE_TEXTURE.applyAndCache(token)), 1.0025f, cubeVertexData);
            poseStack.popPose();
        }
    }
}