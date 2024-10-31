package com.sammy.malum.client.renderer.block;

import com.mojang.blaze3d.vertex.*;
import com.mojang.math.*;
import com.sammy.malum.*;
import com.sammy.malum.common.block.curiosities.void_depot.*;
import com.sammy.malum.registry.client.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.blockentity.*;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.*;
import net.minecraft.util.*;
import org.joml.*;
import team.lodestar.lodestone.handlers.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.registry.client.*;
import team.lodestar.lodestone.systems.easing.*;
import team.lodestar.lodestone.systems.rendering.*;
import team.lodestar.lodestone.systems.rendering.rendeertype.*;

import java.awt.*;
import java.util.List;
import java.util.*;
import java.util.stream.*;


public class VoidDepotRenderer implements BlockEntityRenderer<VoidDepotBlockEntity> {

    private static final RenderTypeToken VIGNETTE = RenderTypeToken.createToken(MalumMod.malumPath("textures/block/weeping_well/primordial_soup_vignette.png"));
    private static final RenderTypeToken NOISE_TEXTURE = RenderTypeToken.createToken(MalumMod.malumPath("textures/vfx/void_noise.png"));

    private static final MultiBufferSource ADDITIVE = new LodestoneBufferWrapper(LodestoneRenderTypes.ADDITIVE_TEXT, RenderHandler.LATE_DELAYED_RENDER.getTarget());
    private static final MultiBufferSource TRANSPARENT = new LodestoneBufferWrapper(LodestoneRenderTypes.TRANSPARENT_TEXT, RenderHandler.DELAYED_RENDER.getTarget());

    public VoidDepotRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(VoidDepotBlockEntity blockEntityIn, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        renderQuad(blockEntityIn, poseStack, partialTicks);
    }

    public void renderQuad(VoidDepotBlockEntity voidDepot, PoseStack poseStack, float partialTicks) {
        float height = 0.9375f;
        float width = 0.3125f;

        Vector3f[] positions = new Vector3f[]{new Vector3f(-width, height, width), new Vector3f(width, height, width), new Vector3f(width, height, -width), new Vector3f(-width, height, -width)};
        VFXBuilders.WorldVFXBuilder builder = VFXBuilders.createWorld();
        poseStack.pushPose();
        poseStack.translate(0.5f, 0.01f, 0.5f);

        builder.replaceBufferSource(RenderHandler.LATE_DELAYED_RENDER.getTarget()).setRenderType(LodestoneRenderTypes.TRANSPARENT_TEXTURE.applyAndCache(VIGNETTE)).renderQuad(poseStack, positions, 1f);
        final long gameTime = voidDepot.getLevel().getGameTime();
        float uOffset = ((gameTime + partialTicks) % 4000) / 2000f;
        float vOffset = ((gameTime + 500f + partialTicks) % 8000) / 8000f;
        float alpha = 0.05f;

        final LodestoneRenderType renderType = RenderTypeRegistry.ADDITIVE_DISTORTED_TEXTURE.applyAndCache(NOISE_TEXTURE);
        builder.replaceBufferSource(RenderHandler.DELAYED_RENDER.getTarget());
        for (int i = 0; i < 2; i++) {
            builder.setAlpha(alpha);
            float speed = 1000f + 250f * i;
            builder.setColor(SpiritTypeRegistry.WICKED_SPIRIT.getPrimaryColor()).setRenderType(LodestoneRenderTypes.applyUniformChanges(LodestoneRenderTypes.copyAndStore(i, renderType), s -> {
                s.safeGetUniform("Speed").set(speed);
                s.safeGetUniform("Width").set(16f);
                s.safeGetUniform("Height").set(16f);
                s.safeGetUniform("UVEncasement").set(new Vector4f(-10, 20, -10, 20));
            }));
            builder.setUV(-uOffset, vOffset, 1 - uOffset, 1 + vOffset).renderQuad(poseStack, positions, 1f);
            builder.setUV(uOffset, -vOffset, 1 + uOffset, 1 - vOffset).renderQuad(poseStack, positions, 1f);
            alpha -= 0.0125f;
            uOffset = -uOffset - 0.2f;
            vOffset = -vOffset + 0.4f;
            poseStack.translate(0, 0.05f, 0);
            poseStack.mulPose(Axis.YP.rotationDegrees(90));
            if (i == 0) {
                builder.setColor(SpiritTypeRegistry.ELDRITCH_SPIRIT.getPrimaryColor());
            }
        }
        poseStack.popPose();


        if (voidDepot.textVisibility > 12) {
            final Font font = Minecraft.getInstance().font;
            float timer = Mth.clamp((voidDepot.textVisibility + (voidDepot.nearTimer > 0 ? 1 : -1) * partialTicks), 0, 40);
            float scalar = Easing.SINE_IN_OUT.ease(timer/40f, 0, 1, 1);
            float scale = 0.016F - (1-scalar)*0.004f;
            final Font.DisplayMode display = Font.DisplayMode.NORMAL;

            List<VoidDepotBlockEntity.VoidDepotGoal> goals = voidDepot.goals;
            List<MutableComponent> components = new ArrayList<>();
            if (!voidDepot.goals.isEmpty()) {
                components = goals.stream().map(g -> Component.literal(g.index + ": <" + g.deliveredAmount + "/" + g.amount + ">")).collect(Collectors.toCollection(ArrayList::new));
            }
            components.addAll(voidDepot.textToDisplay.stream().map(Component::literal).toList());

            poseStack.pushPose();
            poseStack.translate(0.5f, 2f, 0.5f);
            poseStack.mulPose(Minecraft.getInstance().getEntityRenderDispatcher().cameraOrientation());
            poseStack.mulPose(Axis.YP.rotationDegrees(180f));
            for (int i = 0; i < components.size(); i++) {
                for (int j = 0; j < 2; j++) {
                    final MutableComponent text = components.get(i).copy();
                    final boolean isAdditive = j == 0;
                    MultiBufferSource bufferToUse = isAdditive ? ADDITIVE : TRANSPARENT;
                    MutableComponent outlineText = text.copy();

                    text.withStyle(isAdditive ? style -> style.withColor(TextColor.fromRgb(SpiritTypeRegistry.WICKED_SPIRIT.getPrimaryColor().getRGB())) : style -> style.withColor(TextColor.fromRgb(new Color(50, 0, 50).getRGB())));
                    outlineText.withStyle(isAdditive ? ChatFormatting.RED : ChatFormatting.BLACK);

                    poseStack.pushPose();
                    Matrix4f pose = poseStack.last().pose();
                    poseStack.translate(0, i * 0.15f, 0);
                    if (isAdditive) {
                        poseStack.translate(0f, 0, 0.05f);
                    }
                    poseStack.scale(scale, -scale, -scale);

                    float offset = isAdditive ? 0.4f : 0.8f;
                    float f = (-font.width(text) / 2f);
                    float xPos = 0 + f;
                    int color = ColorHelper.getColor(1, 1, 1, (isAdditive ? 0.3f : 0.9f)*scalar);
                    font.drawInBatch(text, xPos, 0, color, false, pose, bufferToUse, display, 0, LightTexture.FULL_BRIGHT);

                    color = ColorHelper.getColor(1, 1, 1, (isAdditive ? 0.15f : 0.7f)*scalar);
                    font.drawInBatch(text, xPos - offset, 0, color, false, pose, bufferToUse, display, 0, LightTexture.FULL_BRIGHT);
                    font.drawInBatch(text, xPos - offset, 0, color, false, pose, bufferToUse, display, 0, LightTexture.FULL_BRIGHT);
                    font.drawInBatch(text, xPos, offset, color, false, pose, bufferToUse, display, 0, LightTexture.FULL_BRIGHT);
                    font.drawInBatch(text, xPos, -offset, color, false, pose, bufferToUse, display, 0, LightTexture.FULL_BRIGHT);

                    color = ColorHelper.getColor(1, 1, 1, (isAdditive ? 0.1f : 0.5f)*scalar);
                    font.drawInBatch(text, xPos - 2*offset, 0, color, false, pose, bufferToUse, display, 0, LightTexture.FULL_BRIGHT);
                    font.drawInBatch(outlineText, xPos + 2*offset, 0, color, false, pose, bufferToUse, display, 0, LightTexture.FULL_BRIGHT);
                    font.drawInBatch(outlineText, xPos, 2*offset, color, false, pose, bufferToUse, display, 0, LightTexture.FULL_BRIGHT);
                    font.drawInBatch(text, xPos, -2*offset, color, false, pose, bufferToUse, display, 0, LightTexture.FULL_BRIGHT);

                    font.drawInBatch(outlineText, xPos - offset, -offset, color, false, pose, bufferToUse, display, 0, LightTexture.FULL_BRIGHT);
                    font.drawInBatch(text, xPos - offset, offset, color, false, pose, bufferToUse, display, 0, LightTexture.FULL_BRIGHT);
                    font.drawInBatch(outlineText, xPos + offset, offset, color, false, pose, bufferToUse, display, 0, LightTexture.FULL_BRIGHT);
                    font.drawInBatch(text, xPos + offset, -offset, color, false, pose, bufferToUse, display, 0, LightTexture.FULL_BRIGHT);
                    poseStack.popPose();
                }
            }
            poseStack.popPose();
        }
    }
}