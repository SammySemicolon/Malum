package com.sammy.malum.client.renderer.block.artifice;

import com.mojang.blaze3d.vertex.*;
import com.mojang.math.*;
import com.sammy.malum.client.renderer.entity.*;
import com.sammy.malum.common.block.curiosities.spirit_catalyzer.SpiritCatalyzerCoreBlockEntity;
import com.sammy.malum.common.block.curiosities.spirit_crucible.*;
import com.sammy.malum.core.systems.artifice.ArtificeAttributeType;
import com.sammy.malum.common.item.augment.*;
import com.sammy.malum.common.item.spirit.*;
import net.minecraft.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.blockentity.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.network.chat.*;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import org.joml.*;
import team.lodestar.lodestone.handlers.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.registry.client.*;
import team.lodestar.lodestone.systems.blockentity.*;
import team.lodestar.lodestone.systems.easing.*;
import team.lodestar.lodestone.systems.rendering.*;

import java.lang.Math;

import static net.minecraft.client.renderer.texture.OverlayTexture.*;


public class SpiritCrucibleRenderer extends ArtificeAcceptorRenderer<SpiritCrucibleCoreBlockEntity> {

    private static final MultiBufferSource TEXT = new LodestoneBufferWrapper(LodestoneRenderTypes.ADDITIVE_TEXT, RenderHandler.DELAYED_RENDER.getTarget());

    public SpiritCrucibleRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(SpiritCrucibleCoreBlockEntity blockEntityIn, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        renderModifiers(blockEntityIn, partialTicks, poseStack, bufferIn, combinedLightIn, combinedOverlayIn);

        Level level = Minecraft.getInstance().level;
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        LodestoneBlockEntityInventory inventory = blockEntityIn.spiritInventory;
        int spiritsRendered = 0;
        if (!inventory.isEmpty()) {
            for (int i = 0; i < inventory.slotCount; i++) {
                ItemStack item = inventory.getStackInSlot(i);
                if (item.getItem() instanceof SpiritShardItem shardItem) {
                    poseStack.pushPose();
                    Vector3f offset = blockEntityIn.getSpiritItemOffset(spiritsRendered++, partialTicks).toVector3f();
                    poseStack.translate(offset.x(), offset.y(), offset.z());
                    FloatingItemEntityRenderer.renderSpiritGlimmer(poseStack, shardItem.type, partialTicks);
                    poseStack.mulPose(Axis.YP.rotationDegrees(((level.getGameTime() % 360) + partialTicks) * 3));
                    poseStack.scale(0.5f, 0.5f, 0.5f);
                    itemRenderer.renderStatic(item, ItemDisplayContext.FIXED, combinedLightIn, NO_OVERLAY, poseStack, bufferIn, level, 0);
                    poseStack.popPose();
                }
            }
        }
        ItemStack stack = blockEntityIn.inventory.getStackInSlot(0);
        if (!stack.isEmpty()) {
            poseStack.pushPose();
            Vec3 offset = SpiritCrucibleCoreBlockEntity.CRUCIBLE_ITEM_OFFSET;
            poseStack.translate(offset.x, offset.y, offset.z);
            poseStack.mulPose(Axis.YP.rotationDegrees(((level.getGameTime() % 360) + partialTicks) * 3));
            poseStack.scale(0.45f, 0.45f, 0.45f);
            itemRenderer.renderStatic(stack, ItemDisplayContext.FIXED, combinedLightIn, NO_OVERLAY, poseStack, bufferIn, level, 0);
            poseStack.popPose();
        }
        stack = blockEntityIn.coreAugmentInventory.getStackInSlot(0);
        if (!stack.isEmpty()) {
            poseStack.pushPose();
            Vec3 offset = SpiritCrucibleCoreBlockEntity.CRUCIBLE_CORE_AUGMENT_OFFSET;
            poseStack.translate(offset.x, offset.y, offset.z);
            poseStack.mulPose(Axis.YP.rotationDegrees(((-level.getGameTime() % 360) - partialTicks) * 3));
            poseStack.scale(0.45f, 0.45f, 0.45f);
            itemRenderer.renderStatic(stack, ItemDisplayContext.FIXED, combinedLightIn, NO_OVERLAY, poseStack, bufferIn, level, 0);
            poseStack.popPose();
        }

        final LodestoneBlockEntityInventory augmentInventory = blockEntityIn.augmentInventory;

        int augmentsRendered = 0;
        if (!augmentInventory.isEmpty()) {
            float total = augmentInventory.slotCount;
            float time = 240;
            for (int i = 0; i < total; i++) {
                ItemStack item = augmentInventory.getStackInSlot(i);
                if (item.getItem() instanceof AugmentItem) {
                    double angle = augmentsRendered / total * (Math.PI * 2);
                    angle -= (((long) (blockEntityIn.spiritSpin + partialTicks) % time) / time) * (Math.PI * 2);
                    poseStack.pushPose();
                    Vector3f offset = blockEntityIn.getAugmentItemOffset(augmentsRendered++, partialTicks).toVector3f();
                    poseStack.translate(offset.x(), offset.y(), offset.z());
                    poseStack.mulPose(Axis.YP.rotation((float) angle - (i % 2 == 0 ? 4.71f : 1.57f)));
                    poseStack.scale(0.5f, 0.5f, 0.5f);
                    itemRenderer.renderStatic(item, ItemDisplayContext.FIXED, combinedLightIn, NO_OVERLAY, poseStack, bufferIn, level, 0);
                    poseStack.popPose();
                }
            }
        }
        if (FORK_TRACKER.isVisible()) {
            var accelerationData = blockEntityIn.getAttributes();
            var attributes = accelerationData.getExistingAttributesForTuning();
            var font = Minecraft.getInstance().font;
            float scalar = Easing.SINE_IN_OUT.ease(FORK_TRACKER.getDelta(partialTicks), 0, 1);
            float scale = 0.016F - (1 - scalar) * 0.004f;
            poseStack.pushPose();
            poseStack.translate(0.5f, 2f, 0.55f);
            poseStack.mulPose(Minecraft.getInstance().getEntityRenderDispatcher().cameraOrientation());
            for (int i = 0; i < attributes.size(); i++) {
                var attributeType = attributes.get(i);
                var dataPrint = attributeType.getDataPrint(accelerationData);
                boolean important = attributeType.equals(accelerationData.tunedAttribute);
                MutableComponent dataText = Component.literal(" <" + dataPrint + ">");
                MutableComponent text = Component.translatable(attributeType.getLangKey());
                MutableComponent outlineText = Component.translatable(attributeType.getLangKey());
                if (important) {
                    dataText.withStyle(ChatFormatting.BOLD);
                    text = Component.literal("[").append(text).append(Component.literal("]")).withStyle(ChatFormatting.BOLD);
                    outlineText = Component.literal("[").append(outlineText).append(Component.literal("]")).withStyle(ChatFormatting.BOLD);
                }

                text.append(dataText).withStyle(ChatFormatting.AQUA);
                outlineText.append(dataText).withStyle(ChatFormatting.LIGHT_PURPLE);

                float f = (-font.width(text) / 2f);
                float xPos = 0 + f;
                poseStack.pushPose();
                poseStack.translate(0, i * 0.15f, 0);
                poseStack.scale(scale, -scale, -scale);
                Matrix4f pose = poseStack.last().pose();
                float alpha = 0.38f * scalar;
                int color = ColorHelper.getColor(1, 1, 1, alpha);
                if (alpha > 0.02) { //For whatever reason Font#adjustColor gets considers alpha lesser than 3 to be "unset" and so it sets it to 255...
                    renderText(text, xPos, 0, color, pose);
                }
                alpha = 0.18f * scalar;
                if (alpha > 0.02) {
                    color = ColorHelper.getColor(1, 1, 1, alpha);
                    renderText(text, xPos - 0.5f, 0, color, pose);
                    renderText(text, xPos - 0.5f, 0, color, pose);
                    renderText(text, xPos, 0.5f, color, pose);
                    renderText(text, xPos, -0.5f, color, pose);
                }
                alpha = 0.12f * scalar;
                if (alpha > 0.02) {
                    color = ColorHelper.getColor(1, 1, 1, alpha);
                    renderText(text, xPos - 1, 0, color, pose);
                    renderText(outlineText, xPos + 1, 0, color, pose);
                    renderText(outlineText, xPos, 1, color, pose);
                    renderText(text, xPos, -1, color, pose);
                    renderText(outlineText, xPos - 0.5f, -0.5f, color, pose);
                    renderText(text, xPos - 0.5f, 0.5f, color, pose);
                    renderText(outlineText, xPos + 0.5f, 0.5f, color, pose);
                    renderText(text, xPos + 0.5f, -0.5f, color, pose);
                }
                poseStack.popPose();
            }
            poseStack.popPose();
        }
    }

    public static void renderText(MutableComponent component, float xPos, float yPos, int color, Matrix4f pose) {
        var font = Minecraft.getInstance().font;
        font.drawInBatch(component, xPos, yPos, color, false, pose, TEXT, Font.DisplayMode.NORMAL, 0, LightTexture.FULL_BRIGHT);
    }

    @Override
    public AABB getRenderBoundingBox(SpiritCrucibleCoreBlockEntity blockEntity) {
        var pos = blockEntity.getBlockPos();
        return new AABB(pos.getX() - 1, pos.getY(), pos.getZ() - 1, pos.getX() + 1, pos.getY() + 4, pos.getZ() + 1);
    }
}