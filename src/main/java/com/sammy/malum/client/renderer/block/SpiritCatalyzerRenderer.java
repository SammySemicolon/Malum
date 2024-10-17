package com.sammy.malum.client.renderer.block;

import com.mojang.blaze3d.vertex.*;
import com.mojang.math.*;
import com.sammy.malum.client.*;
import com.sammy.malum.client.renderer.entity.*;
import com.sammy.malum.common.block.curiosities.spirit_crucible.catalyzer.*;
import com.sammy.malum.core.systems.spirit.*;
import com.sammy.malum.registry.client.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.blockentity.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.core.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import team.lodestar.lodestone.registry.client.*;
import team.lodestar.lodestone.systems.easing.*;
import team.lodestar.lodestone.systems.rendering.rendeertype.*;

import java.util.*;

import static net.minecraft.client.renderer.texture.OverlayTexture.*;


public class SpiritCatalyzerRenderer implements BlockEntityRenderer<SpiritCatalyzerCoreBlockEntity> {


    public SpiritCatalyzerRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(SpiritCatalyzerCoreBlockEntity blockEntityIn, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        Level level = Minecraft.getInstance().level;
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        ItemStack stack = blockEntityIn.inventory.getStackInSlot(0);
        if (!stack.isEmpty()) {
            poseStack.pushPose();
            Vec3 offset = blockEntityIn.getItemOffset();
            poseStack.translate(offset.x, offset.y, offset.z);
            poseStack.mulPose(Axis.YP.rotationDegrees(((level.getGameTime() % 360) + partialTicks) * 3));
            poseStack.scale(0.45f, 0.45f, 0.45f);
            itemRenderer.renderStatic(stack, ItemDisplayContext.FIXED, combinedLightIn, NO_OVERLAY, poseStack, bufferIn, level, 0);
            poseStack.popPose();
        }
        stack = blockEntityIn.augmentInventory.getStackInSlot(0);
        if (!stack.isEmpty()) {
            poseStack.pushPose();
            Vec3 offset = blockEntityIn.getAugmentOffset();
            poseStack.translate(offset.x, offset.y, offset.z);
            poseStack.mulPose(Axis.YP.rotationDegrees(((-level.getGameTime() % 360) - partialTicks) * 3));
            poseStack.scale(0.45f, 0.45f, 0.45f);
            itemRenderer.renderStatic(stack, ItemDisplayContext.FIXED, combinedLightIn, NO_OVERLAY, poseStack, bufferIn, level, 0);
            poseStack.popPose();
        }
        if (blockEntityIn.getTarget() != null && blockEntityIn.intensity != null) {
            poseStack.pushPose();
            final BlockPos blockPos = blockEntityIn.getBlockPos();
            Vec3 offset = blockEntityIn.getItemOffset();
            for (Map.Entry<MalumSpiritType, Integer> entry : blockEntityIn.intensity.entrySet()) {
                if (entry.getValue() > 0) {
                    final MalumSpiritType spirit = entry.getKey();
                    poseStack.translate(-blockPos.getX(), -blockPos.getY(), -blockPos.getZ());
                    renderBeam(blockEntityIn, poseStack, spirit, entry.getValue());
                    poseStack.translate(blockPos.getX()+offset.x, blockPos.getY()+offset.y, blockPos.getZ()+offset.z);
                    FloatingItemEntityRenderer.renderSpiritGlimmer(poseStack, spirit, entry.getValue() / 60f, partialTicks);
                    poseStack.translate(-offset.x, -offset.y, -offset.z);
                }
            }
            poseStack.popPose();
        }
    }

    public void renderBeam(SpiritCatalyzerCoreBlockEntity catalyzer, PoseStack poseStack, MalumSpiritType spiritType, int intensity) {
        var catalyzerPos = catalyzer.getBlockPos();
        var startPos = catalyzer.getItemOffset().add(catalyzerPos.getX(), catalyzerPos.getY(), catalyzerPos.getZ());
        var targetPos = catalyzer.getTarget().getVisualAccelerationPoint();
        var difference = targetPos.subtract(startPos);
        float distance = 0.35f + Easing.SINE_OUT.ease(intensity / 60f, 0, 0.35f, 1);
        float alpha = intensity / 60f;
        var midPoint = startPos.add(difference.scale(distance));
        var renderType = LodestoneRenderTypes.ADDITIVE_TEXTURE.applyAndCache(MalumRenderTypeTokens.CONCENTRATED_TRAIL);
        SpiritBasedWorldVFXBuilder.create(spiritType)
                .setColor(spiritType.getPrimaryColor())
                .setRenderType(renderType)
                .setAlpha(alpha)
                .renderBeam(poseStack.last().pose(), startPos, midPoint, 0.4f, b -> b.setColor(spiritType.getSecondaryColor()).setAlpha(0f));
    }
}