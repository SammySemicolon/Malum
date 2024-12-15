package com.sammy.malum.client.renderer.block;

import com.mojang.blaze3d.vertex.*;
import com.mojang.math.*;
import com.sammy.malum.*;
import com.sammy.malum.client.*;
import com.sammy.malum.common.block.curiosities.banner.*;
import com.sammy.malum.common.block.curiosities.mana_mote.*;
import com.sammy.malum.core.systems.spirit.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.blockentity.*;
import net.minecraft.core.*;
import net.minecraft.util.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import org.joml.*;
import team.lodestar.lodestone.handlers.*;
import team.lodestar.lodestone.registry.client.*;
import team.lodestar.lodestone.systems.rendering.*;
import team.lodestar.lodestone.systems.rendering.rendeertype.*;

import java.lang.Math;

import static com.sammy.malum.client.RenderUtils.*;


public class SoulwovenBannerRenderer implements BlockEntityRenderer<SoulwovenBannerBlockEntity> {

    public SoulwovenBannerRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(SoulwovenBannerBlockEntity blockEntityIn, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        var blockState = blockEntityIn.getBlockState();
        if (!(blockState.getBlock() instanceof SoulwovenBannerBlock block)) {
            return;
        }
        var token = RenderTypeToken.createCachedToken(block.texture);
        var banner = LodestoneRenderTypes.TRANSPARENT_TEXTURE.applyAndCache(token);
        var builder = VFXBuilders.createWorld().setRenderType(banner).setLight(combinedLightIn);
        var pos = blockEntityIn.getBlockPos();
        var spirit = blockEntityIn.spirit;
        var type = blockState.getValue(SoulwovenBannerBlock.BANNER_TYPE);
        var direction = type.direction.getAxis().isVertical() ? type.equals(SoulwovenBannerBlock.BannerType.HANGING_Z) ? Direction.NORTH : Direction.WEST : type.direction;
        float sway = ((float) Math.floorMod((pos.getX() * 7L + pos.getY() * 9L + pos.getZ() * 13L) + blockEntityIn.getLevel().getGameTime(), 100L) + partialTicks) / 100.0F;
        float swayRotation = (0.01F * Mth.cos((float) (Math.PI * 2) * sway)) * (float) Math.PI;
        if (spirit == SpiritTypeRegistry.AERIAL_SPIRIT) {
            swayRotation*=2f;
        }

        poseStack.pushPose();
        poseStack.translate(0.5f, 0.5f, 0.5f);
        poseStack.mulPose(Axis.YN.rotationDegrees(direction.toYRot()));
        poseStack.translate(-0.5f, -0.5f, -0.5f);
        if (type.direction.getAxis().isHorizontal()) {
            poseStack.translate(0, -0.25f, 0.0625f);
            swayRotation -= 0.0125f;
        } else {
            poseStack.translate(0, 0, 0.5f);
            swayRotation = swayRotation - 0.0157f;
        }
        poseStack.translate(0, 1f, 0);
        poseStack.mulPose(Axis.XP.rotation(swayRotation));
        float xStart = 0;
        float xEnd = 1;
        float yStart = -2;
        float yEnd = 0;
        var vertices = new Vector3f[]{new Vector3f(xEnd, yStart, 0), new Vector3f(xStart, yStart, 0), new Vector3f(xStart, yEnd, 0), new Vector3f(xEnd, yEnd, 0)};
        var reversedVertices = new Vector3f[]{vertices[1], vertices[0], vertices[3], vertices[2]};

        builder.renderQuad(poseStack, vertices, 1f);
        builder.renderQuad(poseStack, reversedVertices, 1f);
        if (spirit != null) {
            var spiritGlow = blockEntityIn.intense ?
                    LodestoneRenderTypes.ADDITIVE_TEXTURE.applyAndCache(token) :
                    LodestoneRenderTypes.ADDITIVE_TEXTURE.applyAndCache(token, ShaderUniformHandler.LUMITRANSPARENT);
            var spiritBuilder = VFXBuilders.createWorld().setRenderType(spiritGlow).setColor(spirit.getPrimaryColor());
            for (int i = 0; i < 4; i++) {
                int offset = 3 - i * 2;
                poseStack.pushPose();
                poseStack.translate(0, 0, 0.001f * offset);
                spiritBuilder.setAlpha(0.8f * Mth.abs(offset));
                spiritBuilder.renderQuad(poseStack, vertices, 1f);
                spiritBuilder.renderQuad(poseStack, reversedVertices, 1f);
                poseStack.popPose();
            }
        }
        poseStack.popPose();
    }
}