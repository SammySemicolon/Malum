package com.sammy.malum.client.renderer.block.redstone;

import com.mojang.blaze3d.vertex.PoseStack;
import com.sammy.malum.MalumMod;
import com.sammy.malum.common.block.curiosities.redstone.SpiritDiodeBlock;
import com.sammy.malum.common.block.curiosities.redstone.SpiritDiodeBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.state.BlockState;
import team.lodestar.lodestone.registry.client.LodestoneRenderTypes;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.rendering.VFXBuilders;
import team.lodestar.lodestone.systems.rendering.cube.CubeVertexData;
import team.lodestar.lodestone.systems.rendering.rendeertype.RenderTypeToken;

import java.awt.*;


public abstract class RedstoneDiodeRenderer<T extends SpiritDiodeBlockEntity> implements BlockEntityRenderer<T> {

    protected static final RenderTypeToken LOCKED = RenderTypeToken.createToken(MalumMod.malumPath("textures/block/redstone/runewood_frame_locked_overlay.png"));
    protected static final RenderTypeToken INPUT = RenderTypeToken.createToken(MalumMod.malumPath("textures/block/redstone/runewood_frame_input_overlay.png"));
    protected final RenderTypeToken output;
    protected static final Color COLOR = new Color(170, 15, 1);

    public RedstoneDiodeRenderer(BlockEntityRendererProvider.Context context, ResourceLocation tokenTexture) {
        this.output = RenderTypeToken.createToken(tokenTexture);
    }

    public abstract float getGlowDelta(T blockEntityIn);
    @Override
    public void render(T blockEntityIn, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        float pct = Mth.clamp(getGlowDelta(blockEntityIn), 0, 1);
        if (pct > 0) {
            float alpha = Easing.CUBIC_OUT.clamped(pct, 0, 1) * 0.8f;
            float glowAlpha = Easing.SINE_OUT.clamped(pct, 0, 1) * 0.4f;
            var cubeVertexData = CubeVertexData.makeCubePositions(1.01f);
            var builder = VFXBuilders.createWorld()
                    .setColor(COLOR);
            poseStack.pushPose();
            poseStack.translate(0.5f,0.5f,0.5f);
            BlockState state = blockEntityIn.getBlockState();
            for (int i = 0; i < 4; i++) {
                var direction = Direction.from2DDataValue(i);
                var token = getTokenForSide(state, direction);
                var transparent = LodestoneRenderTypes.TRANSPARENT_TEXTURE.applyAndCache(token);
                var additive = LodestoneRenderTypes.ADDITIVE_TEXTURE.applyAndCache(token);
                builder.setAlpha(alpha).setRenderType(transparent).drawCubeSide(poseStack, cubeVertexData, direction);
                builder.setAlpha(glowAlpha).setRenderType(additive).drawCubeSide(poseStack, cubeVertexData, direction);
            }
            poseStack.popPose();
        }
    }

    public RenderTypeToken getTokenForSide(BlockState state, Direction direction) {
        Direction facing = state.getValue(SpiritDiodeBlock.FACING);
        if (direction.equals(facing)) {
            return INPUT;
        }
        if (direction.equals(facing.getOpposite())) {
            return output;
        }
        return LOCKED;
    }
}
