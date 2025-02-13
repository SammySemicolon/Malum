package com.sammy.malum.client.renderer.block;

import com.mojang.blaze3d.vertex.*;
import com.sammy.malum.client.*;
import com.sammy.malum.common.block.curiosities.totem.*;
import com.sammy.malum.core.systems.item.HeldItemTracker;
import com.sammy.malum.core.systems.rite.*;
import com.sammy.malum.core.systems.spirit.*;
import com.sammy.malum.registry.client.*;
import com.sammy.malum.registry.common.item.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.blockentity.*;
import net.minecraft.core.*;
import net.minecraft.util.*;
import team.lodestar.lodestone.registry.client.*;
import team.lodestar.lodestone.systems.easing.*;
import team.lodestar.lodestone.systems.rendering.*;
import team.lodestar.lodestone.systems.rendering.cube.CubeVertexData;


public class TotemBaseRenderer implements BlockEntityRenderer<TotemBaseBlockEntity> {

    public static final HeldItemTracker STAFF_TRACKER = new HeldItemTracker(p -> p.is(ItemTagRegistry.IS_TOTEMIC_TOOL));

    public TotemBaseRenderer(BlockEntityRendererProvider.Context context) {
    }


    @Override
    public void render(TotemBaseBlockEntity blockEntityIn, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        if (STAFF_TRACKER.isVisible()) {
            if (blockEntityIn.cachedRadiusRite == null) {
                return;
            }
            float totemTimer = Mth.clamp((blockEntityIn.radiusVisibility + (blockEntityIn.isActiveOrAssembling() ? 1 : -1) * partialTicks), 0, 40);
            float scalar = Easing.SINE_IN_OUT.ease(STAFF_TRACKER.getDelta(partialTicks), 0, 1) * Easing.SINE_IN_OUT.ease(totemTimer / 40f, 0, 1, 1);
            MalumSpiritType spiritType = blockEntityIn.cachedRadiusRite.getIdentifyingSpirit();
            TotemicRiteEffect riteEffect = blockEntityIn.cachedRadiusRite.getRiteEffect(blockEntityIn.isSoulwood);
            BlockPos riteEffectCenter = riteEffect.getRiteEffectCenter(blockEntityIn);
            BlockPos offset = riteEffectCenter.subtract(blockEntityIn.getBlockPos());
            int width = riteEffect.getRiteEffectHorizontalRadius();
            if (width > 1) {
                width = width * 2 + 1;
            }
            int height = riteEffect.getRiteEffectVerticalRadius();
            if (height > 1) {
                height = height * 2 + 1;
            }
            float shaderWidth = width * 32;
            float shaderHeight = height * 32;
            float distortion = 6f + height / 2f;
            float sideDistortion = 6f + width / 2f;
            final LodestoneRenderType renderType = LodestoneRenderTypes.ADDITIVE_DISTORTED_TEXTURE.applyWithModifierAndCache(MalumRenderTypeTokens.AREA_COVERAGE, b -> b.setCullState(LodestoneRenderTypes.NO_CULL));
            float index = shaderWidth + distortion;
            float sideIndex = shaderWidth * shaderHeight + sideDistortion;

            var builder = SpiritBasedWorldVFXBuilder.create(spiritType)
                    .setRenderType(LodestoneRenderTypes.applyUniformChanges(LodestoneRenderTypes.copyAndStore(index, renderType), s -> {
                        s.safeGetUniform("Speed").set(1500f);
                        s.safeGetUniform("Distortion").set(distortion);
                        s.safeGetUniform("Width").set(shaderWidth);
                        s.safeGetUniform("Height").set(shaderWidth);
                    }))
                    .setColor(spiritType.getPrimaryColor(), 0.7f * scalar);
            var sideBuilder = SpiritBasedWorldVFXBuilder.create(spiritType)
                    .setRenderType(LodestoneRenderTypes.applyUniformChanges(LodestoneRenderTypes.copyAndStore(sideIndex, renderType), s -> {
                        s.safeGetUniform("Speed").set(1500f);
                        s.safeGetUniform("Distortion").set(sideDistortion);
                        s.safeGetUniform("Width").set(shaderWidth);
                        s.safeGetUniform("Height").set(shaderHeight);
                    }))
                    .setColor(spiritType.getPrimaryColor(), 0.7f * scalar);

            poseStack.pushPose();
            poseStack.translate(offset.getX() + 0.5f, offset.getY() + 0.5f, offset.getZ() + 0.5f);

            CubeVertexData cubeVertexData = CubeVertexData.makeCubePositions(width, height)
                    .applyWobble(0, 0.5f, 0.01f)
                    .scale(1.05f);
            CubeVertexData inverse = CubeVertexData.makeCubePositions(-width, -height)
                    .applyWobble(0, 0.5f, 0.01f)
                    .scale(1.05f);

            builder.drawCubeSides(poseStack, cubeVertexData, Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST);
            sideBuilder.drawCubeSides(poseStack, cubeVertexData, Direction.UP, Direction.DOWN);

            builder.setUV(0, 1, 1, 0).setColor(spiritType.getSecondaryColor(), 0.6f * scalar);
            sideBuilder.setUV(0, 1, 1, 0).setColor(spiritType.getSecondaryColor(), 0.6f * scalar);

            builder.drawCubeSides(poseStack, inverse, Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST);
            sideBuilder.drawCubeSides(poseStack, inverse, Direction.UP, Direction.DOWN);
            poseStack.popPose();
        }
    }
}