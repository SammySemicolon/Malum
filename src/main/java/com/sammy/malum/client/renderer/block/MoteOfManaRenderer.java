package com.sammy.malum.client.renderer.block;

import com.mojang.blaze3d.vertex.*;
import com.sammy.malum.*;
import com.sammy.malum.client.*;
import com.sammy.malum.common.block.curiosities.mana_mote.*;
import com.sammy.malum.core.systems.spirit.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.blockentity.*;
import team.lodestar.lodestone.registry.client.*;
import team.lodestar.lodestone.systems.rendering.cube.CubeVertexData;
import team.lodestar.lodestone.systems.rendering.rendeertype.*;

import static com.sammy.malum.client.RenderUtils.*;
import static com.sammy.malum.registry.client.MalumRenderTypeTokens.MOTE_OF_MANA;


public class MoteOfManaRenderer implements BlockEntityRenderer<ManaMoteBlockEntity> {

    public MoteOfManaRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(ManaMoteBlockEntity blockEntityIn, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        MalumSpiritType spiritType = MalumSpiritType.getSpiritType(blockEntityIn.getBlockState().getValue(ManaMoteBlock.SPIRIT_TYPE));

        var builder = SpiritBasedWorldVFXBuilder.create(spiritType)
                .setRenderType(LodestoneRenderTypes.ADDITIVE_TEXTURE.applyAndCache(MOTE_OF_MANA));

        CubeVertexData cubeVertexData = CubeVertexData.makeCubePositions(1f).applyWobble(0, 0.5f, 0.015f);
        CubeVertexData inverse = CubeVertexData.makeCubePositions(-1f).applyWobble(0, 0.5f, 0.015f);

        poseStack.pushPose();
        poseStack.translate(0.5f, 0.5f, 0.5f);
        builder.setRenderType(LodestoneRenderTypes.ADDITIVE_TEXTURE.applyAndCache(MOTE_OF_MANA))
                .setColor(spiritType.getPrimaryColor(), 0.9f)
                .drawCube(poseStack, cubeVertexData);
        builder.setAlpha(0.5f).setUV(0.0625f, 0.0625f, 1.0625f, 1.0625f)
                .drawCube(poseStack, cubeVertexData.scale(1.08f));
        builder.setRenderType(LodestoneRenderTypes.TRANSPARENT_TEXTURE.applyAndCache(MOTE_OF_MANA))
                .setColor(spiritType.getSecondaryColor(), 0.4f)
                .setUV(-0.0625f, -0.0625f, 0.9375f, 0.9375f)
                .drawCube(poseStack, inverse.scale(0.82f));

        poseStack.popPose();
    }
}