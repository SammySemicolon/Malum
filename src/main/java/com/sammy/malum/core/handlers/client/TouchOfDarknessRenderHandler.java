package com.sammy.malum.core.handlers.client;

import com.mojang.blaze3d.systems.*;
import com.mojang.blaze3d.vertex.*;
import com.sammy.malum.common.capabilities.*;
import com.sammy.malum.registry.client.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.util.*;
import net.minecraft.world.entity.player.*;
import team.lodestar.lodestone.systems.easing.*;
import team.lodestar.lodestone.systems.rendering.*;
import team.lodestar.lodestone.systems.rendering.shader.*;

import java.util.function.*;

public class TouchOfDarknessRenderHandler {

    public static void renderDarknessVignette(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        Minecraft minecraft = Minecraft.getInstance();
        PoseStack poseStack = guiGraphics.pose();
        Player player = minecraft.player;
        var data = player.getData(AttachmentTypeRegistry.TOUCH_OF_DARKNESS);
        if (data.touchOfDarkness == 0f) {
            return;
        }
        int screenWidth = minecraft.getWindow().getGuiScaledWidth();
        int screenHeight = minecraft.getWindow().getGuiScaledHeight();

        float effectStrength = Easing.SINE_IN_OUT.ease(data.touchOfDarkness / TouchOfDarknessData.MAX_TOUCH_OF_DARKNESS, 0, 1, 1);
        float alpha = Math.min(1, effectStrength * 5);
        float zoom = 0.5f + Math.min(0.35f, effectStrength);
        float intensity = 1f + (effectStrength > 0.5f ? (effectStrength - 0.5f) * 2.5f : 0);

        ExtendedShaderInstance shaderInstance = (ExtendedShaderInstance) ShaderRegistry.TOUCH_OF_DARKNESS.getInstance().get();
        shaderInstance.safeGetUniform("Speed").set(1000f);
        Consumer<Float> setZoom = f -> shaderInstance.safeGetUniform("Zoom").set(f);
        Consumer<Float> setIntensity = f -> shaderInstance.safeGetUniform("Intensity").set(f);
        VFXBuilders.ScreenVFXBuilder builder = VFXBuilders.createScreen()
                .setPositionWithWidth(screenWidth*-0.2f, screenHeight*-0.2f, screenWidth*1.4f, screenHeight*1.4f)
                .setAlpha(alpha)
                .setShader(shaderInstance);

        poseStack.pushPose();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        for (int i = 0; i < 4; i++) {
            poseStack.pushPose();
            if (i != 2) {
                float angle = ((player.level().getGameTime() + deltaTracker.getGameTimeDeltaTicks()) / 80 + i * 2.09f) * 6.28f;
                float xOffset = Mth.sin(angle) * 20;
                float yOffset = Mth.cos(angle) * 20;
                poseStack.translate(xOffset, yOffset, 0);
            }
            builder.setColor(i==0?55:0, i==1?55:0, i==2?55:0);

            setZoom.accept(zoom);
            setIntensity.accept(intensity);
            builder.setAlpha(alpha).blit(poseStack);

            setZoom.accept(zoom * 1.25f + 0.15f);
            setIntensity.accept(intensity * 0.8f + 0.5f);
            builder.setAlpha(0.5f * alpha).blit(poseStack);
            poseStack.popPose();
        }

        RenderSystem.disableBlend();
        poseStack.popPose();

        shaderInstance.setUniformDefaults();
    }
}
