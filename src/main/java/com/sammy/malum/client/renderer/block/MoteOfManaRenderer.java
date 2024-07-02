package com.sammy.malum.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.sammy.malum.MalumMod;
import com.sammy.malum.client.RenderUtils;
import com.sammy.malum.client.SpiritBasedWorldVFXBuilder;
import com.sammy.malum.common.block.curiosities.mana_mote.MoteOfManaBlockEntity;
import com.sammy.malum.common.block.curiosities.mana_mote.SpiritMoteBlock;
import com.sammy.malum.core.handlers.SpiritHarvestHandler;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import team.lodestar.lodestone.registry.client.LodestoneRenderTypeRegistry;
import team.lodestar.lodestone.systems.rendering.rendeertype.RenderTypeToken;

import static com.sammy.malum.client.RenderUtils.drawCube;


public class MoteOfManaRenderer implements BlockEntityRenderer<MoteOfManaBlockEntity> {

    public static final RenderTypeToken MOTE_OF_MANA = RenderTypeToken.createToken(MalumMod.malumPath("textures/block/spirit_mote.png"));

    public MoteOfManaRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(MoteOfManaBlockEntity blockEntityIn, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        poseStack.pushPose();
        MalumSpiritType spiritType = SpiritHarvestHandler.getSpiritType(blockEntityIn.getBlockState().getValue(SpiritMoteBlock.SPIRIT_TYPE));

        var builder = SpiritBasedWorldVFXBuilder.create(spiritType)
                .setRenderType(LodestoneRenderTypeRegistry.ADDITIVE_TEXTURE.applyAndCache(MOTE_OF_MANA));

        RenderUtils.CubeVertexData cubeVertexData = RenderUtils.makeCubePositions(1f).applyWobble(0, 0.5f, 0.015f);
        RenderUtils.CubeVertexData inverse = RenderUtils.makeCubePositions(-1f).applyWobble(0, 0.5f, 0.015f);

        drawCube(poseStack, builder.setColor(spiritType.getPrimaryColor(), 0.9f), 1f, cubeVertexData);
        drawCube(poseStack, builder.setColor(spiritType.getPrimaryColor(), 0.5f).setUV(0.0625f, 0.0625f, 1.0625f, 1.0625f), 1.08f, cubeVertexData);

        builder.setRenderType(LodestoneRenderTypeRegistry.TRANSPARENT_TEXTURE.applyAndCache(MOTE_OF_MANA));

        drawCube(poseStack, builder.setColor(spiritType.getSecondaryColor(), 0.4f).setUV(-0.0625f, -0.0625f, 0.9375f, 0.9375f), 0.82f, inverse);
        poseStack.popPose();
    }
}