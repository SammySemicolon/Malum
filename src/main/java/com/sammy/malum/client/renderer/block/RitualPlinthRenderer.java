package com.sammy.malum.client.renderer.block;

import com.mojang.blaze3d.vertex.*;
import com.mojang.math.*;
import com.sammy.malum.common.block.curiosities.ritual_plinth.*;
import com.sammy.malum.common.entity.nitrate.*;
import com.sammy.malum.core.systems.ritual.*;
import com.sammy.malum.core.systems.spirit.*;
import com.sammy.malum.registry.client.MalumRenderTypeTokens;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.blockentity.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import team.lodestar.lodestone.handlers.*;
import team.lodestar.lodestone.registry.client.*;
import team.lodestar.lodestone.systems.rendering.*;
import team.lodestar.lodestone.systems.rendering.rendeertype.*;

import static net.minecraft.client.renderer.texture.OverlayTexture.*;

public class RitualPlinthRenderer implements BlockEntityRenderer<RitualPlinthBlockEntity> {

    public RitualPlinthRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public AABB getRenderBoundingBox(RitualPlinthBlockEntity blockEntityIn) {
        var pos = blockEntityIn.getBlockPos();
        return new AABB(pos.getX() - 2, pos.getY()-1, pos.getZ() - 2, pos.getX() + 3, pos.getY() + 6, pos.getZ() + 3);
    }
    
    @Override
    public void render(RitualPlinthBlockEntity blockEntityIn, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        Level level = Minecraft.getInstance().level;
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        ItemStack stack = blockEntityIn.inventory.getStackInSlot(0);
        if (!stack.isEmpty()) {
            poseStack.pushPose();
            Vec3 offset = blockEntityIn.getCentralItemOffset();
            poseStack.translate(offset.x, offset.y, offset.z);
            poseStack.mulPose(Axis.YP.rotationDegrees(((level.getGameTime() % 360) + partialTicks) * 3));
            poseStack.scale(0.45f, 0.45f, 0.45f);
            itemRenderer.renderStatic(stack, ItemDisplayContext.FIXED, combinedLightIn, NO_OVERLAY, poseStack, bufferIn, level, 0);
            poseStack.popPose();
        }
        final MalumRitualType ritualType = blockEntityIn.ritualType;
        final MalumRitualTier ritualTier = blockEntityIn.ritualTier;
        if (blockEntityIn.activeDuration > 0 && ritualType != null) {
            final boolean hasDecor = ritualTier != null && !MalumRitualTier.FADED.equals(ritualTier);
            RenderType silhouette = (LodestoneRenderTypes.TRANSPARENT_TEXTURE.applyAndCache(MalumRenderTypeTokens.RITUAL_SILHOUETTE));
            RenderType icon = (LodestoneRenderTypes.ADDITIVE_TEXTURE.applyAndCache(ritualTier == null ? MalumRenderTypeTokens.INCOMPLETE_RITUAL : RenderTypeToken.createCachedToken(ritualType.getIcon())));
            RenderType decorGlow = hasDecor ? LodestoneRenderTypes.ADDITIVE_TEXTURE.applyAndCache(RenderTypeToken.createCachedToken(ritualTier.getDecorTexture())) : null;
            RenderType decorSilhouette = hasDecor ? LodestoneRenderTypes.TRANSPARENT_TEXTURE.applyAndCache(RenderTypeToken.createCachedToken(ritualTier.getDecorTexture())) : null;
            MalumSpiritType spirit = ritualType.spirit;
            Vec3 offset = blockEntityIn.getRitualIconOffset(partialTicks);
            final float scalar = Math.min(blockEntityIn.activeDuration, 15) / 15f;
            float alpha = 0.9f * scalar;
            float scale = 0.25f * (1 + scalar);
            var worldVFXBuilder = VFXBuilders.createWorld()
                    .replaceBufferSource(RenderHandler.LATE_DELAYED_RENDER)
                    .setColor(spirit.getPrimaryColor())
                    .setAlpha(alpha);
            var backgroundBuilder = VFXBuilders.createWorld()
                    .setColor(EthericNitrateEntity.SECOND_SMOKE_COLOR)
                    .setAlpha(0.4f * scalar);

            poseStack.pushPose();
            poseStack.translate(offset.x, offset.y, offset.z);
            poseStack.mulPose(Minecraft.getInstance().getEntityRenderDispatcher().cameraOrientation());
            worldVFXBuilder.setRenderType(icon).renderQuad(poseStack, scale);
            if (hasDecor) {
                worldVFXBuilder.setRenderType(decorGlow).renderQuad(poseStack, scale*2.5f);
            }
            float gameTime = level.getGameTime()+partialTicks;
            int time = 160;
            float distance = 0.01f;
            for (int i = 0; i < 8; i++) {
                boolean odd = i % 2 == 0;
                double angle = i / 8f * (Math.PI * 2);
                angle += ((gameTime % time) / time) * (Math.PI * 2);
                double xOffset = (distance * Math.cos(angle));
                double zOffset = (distance * Math.sin(angle));
                poseStack.translate(xOffset, zOffset/2f, zOffset);
                worldVFXBuilder.setRenderType(icon).setAlpha(alpha*(i/8f)).renderQuad(poseStack, scale);
                backgroundBuilder.setRenderType(silhouette).renderQuad(poseStack, scale*(odd ? 0.8f : 1.2f));
                if (hasDecor) {
                    worldVFXBuilder.setRenderType(decorGlow).renderQuad(poseStack, scale*2.5f);
                    backgroundBuilder.setRenderType(decorSilhouette).renderQuad(poseStack, scale * (odd ? 2.1f : 2.7f));
                }
                poseStack.translate(-xOffset, -zOffset/2f, -zOffset);
                if (i == 4) {
                    worldVFXBuilder.setColor(spirit.getSecondaryColor());
                    alpha *= 0.5f;
                }
                distance+=0.0125f;
            }
            poseStack.popPose();
        }
    }
}
