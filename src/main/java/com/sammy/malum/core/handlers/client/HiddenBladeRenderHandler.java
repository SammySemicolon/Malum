package com.sammy.malum.core.handlers.client;

import com.mojang.blaze3d.systems.*;
import com.sammy.malum.*;
import com.sammy.malum.common.item.curiosities.curios.sets.weeping.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.resources.*;
import net.minecraft.util.*;
import net.neoforged.neoforge.client.event.*;
import org.lwjgl.opengl.*;
import team.lodestar.lodestone.registry.client.*;
import team.lodestar.lodestone.systems.rendering.*;
import team.lodestar.lodestone.systems.rendering.shader.*;

public class HiddenBladeRenderHandler {

    public static int fadeOut = 80;

    public static void tick(ClientTickEvent event) {
        var player = Minecraft.getInstance().player;
        if (player != null) {
            var cooldown = player.getData(AttachmentTypeRegistry.CURIO_DATA).hiddenBladeNecklaceCooldown;
            if (cooldown == 0) {
                if (player.hasEffect(MobEffectRegistry.WICKED_INTENT)) {
                    if (fadeOut > 30) {
                        fadeOut = 30;
                    }
                    fadeOut -= 2;
                    return;
                } else if (fadeOut < 0) {
                    fadeOut = 20;
                }
                if (fadeOut < 80) {
                    fadeOut++;
                }
            } else {
                fadeOut = 0;
            }
        }
    }

    public static void renderHiddenBladeCooldown(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        var minecraft = Minecraft.getInstance();
        var poseStack = guiGraphics.pose();
        if (!minecraft.options.hideGui) {
            var player = minecraft.player;
            if (!player.isCreative() && !player.isSpectator()) {
                var cooldown = player.getData(AttachmentTypeRegistry.CURIO_DATA).hiddenBladeNecklaceCooldown;
                if (cooldown > 0 || fadeOut <= 80) {
                    int left = guiGraphics.guiWidth() / 2 - 8;
                    int top = guiGraphics.guiHeight() - 52;
                    poseStack.pushPose();
                    RenderSystem.setShaderTexture(0, getTexture());
                    RenderSystem.depthMask(true);
                    RenderSystem.enableBlend();
                    RenderSystem.defaultBlendFunc();
                    ExtendedShaderInstance shaderInstance = (ExtendedShaderInstance) LodestoneShaders.SCREEN_DISTORTED_TEXTURE.getInstance().get();
                    shaderInstance.safeGetUniform("YFrequency").set(15f);
                    shaderInstance.safeGetUniform("XFrequency").set(15f);
                    shaderInstance.safeGetUniform("Speed").set(550f);
                    shaderInstance.safeGetUniform("Intensity").set(120f);
                    var builder = VFXBuilders.createScreen().setShader(() -> shaderInstance);

                    float size = 16;
                    double delta = Mth.clamp((CurioHiddenBladeNecklace.COOLDOWN_DURATION - cooldown) / (float) CurioHiddenBladeNecklace.COOLDOWN_DURATION, 0, 1);
                    delta -= 0.125f;
                    final boolean secondRow = delta >= 0.5f;
                    int xOffset = 16 * (Mth.floor(delta * 8)) - (secondRow ? 64 : 0);
                    int yOffset = secondRow ? 16 : 0;
                    if (fadeOut > 20) {
                        final boolean hasEffect = player.hasEffect(MobEffectRegistry.WICKED_INTENT);
                        builder.setAlpha((80 - fadeOut) / (hasEffect ? 10f : 60f));
                    }
                    builder.setPositionWithWidth(left, top, size, size)
                            .setUVWithWidth(xOffset, yOffset, 16, 16, 64)
                            .blit(poseStack);
                    if (fadeOut > 0 && fadeOut < 20) {
                        float glow = (10 - Math.abs(10 - fadeOut)) / 10f;
                        RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
                        builder.setAlpha(glow).blit(poseStack);
                    }

                    shaderInstance.setUniformDefaults();
                    RenderSystem.disableBlend();
                    poseStack.popPose();
                }
            }
        }
    }

    public static ResourceLocation getTexture() {
        return MalumMod.malumPath("textures/gui/hud/hidden_blade.png");
    }
}
