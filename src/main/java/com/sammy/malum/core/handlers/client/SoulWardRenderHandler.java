package com.sammy.malum.core.handlers.client;

import com.mojang.blaze3d.systems.*;
import com.sammy.malum.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.player.*;
import net.minecraft.resources.*;
import net.minecraft.util.*;
import net.minecraft.world.entity.ai.attributes.*;
import org.joml.*;
import org.lwjgl.opengl.*;
import team.lodestar.lodestone.registry.client.*;
import team.lodestar.lodestone.systems.rendering.*;
import team.lodestar.lodestone.systems.rendering.shader.*;

import java.lang.Math;

public class SoulWardRenderHandler {
    public static int fadeOut;

    public static void tick(Minecraft minecraft) {
        final LocalPlayer player = Minecraft.getInstance().player;
        if (player != null) {
            var data = player.getAttachedOrCreate(AttachmentTypeRegistry.SOUL_WARD);
            if (data.getSoulWard() >= player.getAttributeValue(AttributeRegistry.SOUL_WARD_CAPACITY)) {
                if (fadeOut < 80) {
                    fadeOut++;
                }
            } else {
                fadeOut = Mth.clamp(fadeOut - 2, 0, 30);
            }
        }
    }

    public static void renderSoulWard(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        var minecraft = Minecraft.getInstance();
        var poseStack = guiGraphics.pose();
        if (!minecraft.options.hideGui) {
            var player = minecraft.player;
            if (!player.isCreative() && !player.isSpectator()) {
                var data = player.getAttachedOrCreate(AttachmentTypeRegistry.SOUL_WARD);
                double soulWard = data.getSoulWard();
                if (soulWard > 0) {
                    float absorb = Mth.ceil(player.getAbsorptionAmount());
                    float maxHealth = (float) player.getAttribute(Attributes.MAX_HEALTH).getValue();
                    float armor = (float) player.getAttribute(Attributes.ARMOR).getValue();

                    int left = guiGraphics.guiWidth() / 2 - 91;
                    int top = guiGraphics.guiHeight() - 66;

                    if (armor == 0) {
                        top += 10;
                    }
                    int healthRows = Mth.ceil((maxHealth + absorb) / 2.0F / 10.0F);
                    int rowHeight = Math.max(10 - (healthRows - 2), 3);

                    poseStack.pushPose();
                    RenderSystem.setShaderTexture(0, getSoulWardTexture());
                    RenderSystem.depthMask(true);
                    RenderSystem.enableBlend();
                    RenderSystem.defaultBlendFunc();
                    ExtendedShaderInstance shaderInstance = (ExtendedShaderInstance) LodestoneShaders.DISTORTED_TEXTURE.getInstance().get();
                    shaderInstance.safeGetUniform("YFrequency").set(15f);
                    shaderInstance.safeGetUniform("XFrequency").set(15f);
                    shaderInstance.safeGetUniform("Speed").set(550f);
                    shaderInstance.safeGetUniform("Intensity").set(120f);
                    var builder = VFXBuilders.createScreen()
                            .setPosTexColorDefaultFormat()
                            .setShader(() -> shaderInstance);
                    if (fadeOut > 20) {
                        final boolean isDamaged = soulWard < player.getAttributeValue(AttributeRegistry.SOUL_WARD_CAPACITY);
                        builder.setAlpha((80 - fadeOut) / (isDamaged ? 10f : 60f));
                    }

                    int size = 13;
                    boolean forceDisplay = soulWard <= 1;
                    double soulWardAmount = forceDisplay ? 1 : Math.ceil(Math.floor(soulWard) / 3f);
                    for (int i = 0; i < soulWardAmount; i++) {
                        int row = (int) (i / 10f);
                        int x = left + i % 10 * 8;
                        int y = top - row * 4 + rowHeight * 2 - 15;
                        int progress = Math.min(3, (int) soulWard - i * 3);
                        int xTextureOffset = forceDisplay ? 31 : 1 + (3 - progress) * 15;

                        shaderInstance.safeGetUniform("UVCoordinates").set(new Vector4f(xTextureOffset / 45f, (xTextureOffset + size) / 45f, 0, 15 / 45f));
                        shaderInstance.safeGetUniform("TimeOffset").set(i * 150f);

                        builder.setPositionWithWidth(x - 2, y - 2, size, size)
                                .setUVWithWidth(xTextureOffset, 0, size, size, 45)
                                .draw(poseStack);
                        if (fadeOut > 0 && fadeOut < 20) {
                            float glow = (10 - Math.abs(10 - fadeOut)) / 10f;
                            RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
                            builder.setAlpha(glow).draw(poseStack).setAlpha(1);
                            RenderSystem.defaultBlendFunc();
                        }
                    }
                    shaderInstance.setUniformDefaults();
                    RenderSystem.disableBlend();
                    poseStack.popPose();
                }
            }
        }
    }

    public static ResourceLocation getSoulWardTexture() {
        return MalumMod.malumPath("textures/gui/hud/soul_ward.png");
    }
}
