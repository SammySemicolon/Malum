package com.sammy.malum.client.renderer.block.artifice;

import com.mojang.blaze3d.vertex.*;
import com.mojang.math.*;
import com.sammy.malum.client.*;
import com.sammy.malum.client.renderer.entity.*;
import com.sammy.malum.common.block.curiosities.spirit_crucible.artifice.IArtificeAcceptor;
import com.sammy.malum.common.block.curiosities.spirit_crucible.catalyzer.*;
import com.sammy.malum.core.systems.spirit.*;
import com.sammy.malum.registry.client.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.blockentity.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import team.lodestar.lodestone.registry.client.*;
import team.lodestar.lodestone.systems.easing.*;

import java.util.*;

import static net.minecraft.client.renderer.texture.OverlayTexture.*;

public class SpiritCatalyzerRenderer implements BlockEntityRenderer<SpiritCatalyzerCoreBlockEntity>, ArtificeAcceptorRenderer.ArtificeModifierSourceRenderer<SpiritCatalyzerCoreBlockEntity> {

    public SpiritCatalyzerRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(SpiritCatalyzerCoreBlockEntity blockEntityIn, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        Level level = Minecraft.getInstance().level;
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        ItemStack stack = blockEntityIn.inventory.getStackInSlot(0);
        if (!stack.isEmpty()) {
            poseStack.pushPose();
            Vec3 offset = SpiritCatalyzerCoreBlockEntity.CATALYZER_ITEM_OFFSET;
            poseStack.translate(offset.x, offset.y, offset.z);
            poseStack.mulPose(Axis.YP.rotationDegrees(((level.getGameTime() % 360) + partialTicks) * 3));
            poseStack.scale(0.45f, 0.45f, 0.45f);
            itemRenderer.renderStatic(stack, ItemDisplayContext.FIXED, combinedLightIn, NO_OVERLAY, poseStack, bufferIn, level, 0);
            poseStack.popPose();
        }
        stack = blockEntityIn.augmentInventory.getStackInSlot(0);
        if (!stack.isEmpty()) {
            poseStack.pushPose();
            Vec3 offset = SpiritCatalyzerCoreBlockEntity.CATALYZER_AUGMENT_OFFSET;
            poseStack.translate(offset.x, offset.y, offset.z);
            poseStack.mulPose(Axis.YP.rotationDegrees(((-level.getGameTime() % 360) - partialTicks) * 3));
            poseStack.scale(0.45f, 0.45f, 0.45f);
            itemRenderer.renderStatic(stack, ItemDisplayContext.FIXED, combinedLightIn, NO_OVERLAY, poseStack, bufferIn, level, 0);
            poseStack.popPose();
        }
    }

    @Override
    public void render(SpiritCatalyzerCoreBlockEntity catalyzer, IArtificeAcceptor target, SpiritInfluenceRendererData spiritInfluence, float partialTicks, PoseStack poseStack) {
        poseStack.pushPose();
        var pos = catalyzer.getBlockPos();
        var offset = SpiritCatalyzerCoreBlockEntity.CATALYZER_ITEM_OFFSET;
        for (MalumSpiritType spirit : spiritInfluence.keySet()) {
            float delta = spiritInfluence.getDelta(spirit);
            if (delta > 0) {
                poseStack.translate(-pos.getX(), -pos.getY(), -pos.getZ());
                renderBeam(catalyzer, target, poseStack, spirit, delta);
                poseStack.translate(pos.getX() + offset.x, pos.getY() + offset.y, pos.getZ() + offset.z);
                FloatingItemEntityRenderer.renderSpiritGlimmer(poseStack, spirit, delta, partialTicks);
                poseStack.translate(-offset.x, -offset.y, -offset.z);
            }
        }
        poseStack.popPose();
    }

    private static void renderBeam(SpiritCatalyzerCoreBlockEntity catalyzer, IArtificeAcceptor target, PoseStack poseStack, MalumSpiritType spiritType, float delta) {
        var catalyzerPos = catalyzer.getBlockPos();
        var startPos = SpiritCatalyzerCoreBlockEntity.CATALYZER_ITEM_OFFSET.add(catalyzerPos.getX(), catalyzerPos.getY(), catalyzerPos.getZ());
        var targetPos = target.getVisualAccelerationPoint();
        var difference = targetPos.subtract(startPos);
        float distance = 0.35f + Easing.SINE_OUT.ease(delta, 0, 0.35f, 1);
        var midPoint = startPos.add(difference.scale(distance));
        var renderType = LodestoneRenderTypes.ADDITIVE_TEXTURE.applyAndCache(MalumRenderTypeTokens.CONCENTRATED_TRAIL);
        SpiritBasedWorldVFXBuilder.create(spiritType)
                .setColor(spiritType.getPrimaryColor())
                .setRenderType(renderType)
                .setAlpha(delta)
                .renderBeam(poseStack.last().pose(), startPos, midPoint, 0.4f, b -> b.setColor(spiritType.getSecondaryColor()).setAlpha(0f));
    }
}