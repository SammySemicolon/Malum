package com.sammy.malum.client;

import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.*;
import net.minecraft.util.*;
import net.minecraft.world.entity.*;
import org.joml.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.systems.easing.*;
import team.lodestar.lodestone.systems.rendering.*;
import team.lodestar.lodestone.systems.rendering.trail.*;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.List;
import java.util.*;
import java.util.function.*;
import java.lang.Math;

public class RenderUtils {


    public static void renderEntityTrail(PoseStack poseStack, VFXBuilders.WorldVFXBuilder builder, TrailPointBuilder trailPointBuilder, Entity entity, Color primaryColor, Color secondaryColor, float effectScalar, float partialTicks) {
        renderEntityTrail(poseStack, builder, trailPointBuilder, entity, t -> primaryColor, t -> secondaryColor, effectScalar, effectScalar, partialTicks);
    }

    public static void renderEntityTrail(PoseStack poseStack, VFXBuilders.WorldVFXBuilder builder, TrailPointBuilder trailPointBuilder, Entity entity, Function<Float, Color> primaryColor, Function<Float, Color> secondaryColor, float effectScalar, float partialTicks) {
        renderEntityTrail(poseStack, builder, trailPointBuilder, entity, primaryColor, secondaryColor, effectScalar, effectScalar, partialTicks);
    }

    public static void renderEntityTrail(PoseStack poseStack, VFXBuilders.WorldVFXBuilder builder, TrailPointBuilder trailPointBuilder, Entity entity, Color primaryColor, Color secondaryColor, float scaleScalar, float alphaScalar, float partialTicks) {
        renderEntityTrail(poseStack, builder, trailPointBuilder, entity, t -> primaryColor, t -> secondaryColor, scaleScalar, alphaScalar, partialTicks);
    }

    public static void renderEntityTrail(PoseStack poseStack, VFXBuilders.WorldVFXBuilder builder, TrailPointBuilder trailPointBuilder, Entity entity, Function<Float, Color> primaryColor, Function<Float, Color> secondaryColor, float scaleScalar, float alphaScalar, float partialTicks) {
        poseStack.pushPose();
        float trailOffsetX = (float) Mth.lerp(partialTicks, entity.xOld, entity.getX());
        float trailOffsetY = (float) Mth.lerp(partialTicks, entity.yOld, entity.getY());
        float trailOffsetZ = (float) Mth.lerp(partialTicks, entity.zOld, entity.getZ());
        poseStack.translate(-trailOffsetX, -trailOffsetY, -trailOffsetZ);
        float size = 0.5f * scaleScalar;
        float alpha = 0.9f * alphaScalar;
        builder.setAlpha(alpha)
                .renderTrail(poseStack, trailPointBuilder, f -> size, f -> builder.setAlpha(alpha * f).setColor(ColorHelper.colorLerp(Easing.SINE_IN, f * 2f, secondaryColor.apply(f), primaryColor.apply(f))));
        poseStack.translate(trailOffsetX, trailOffsetY, trailOffsetZ);
        poseStack.popPose();
    }
}
