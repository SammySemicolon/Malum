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
        renderEntityTrail(poseStack, builder, trailPointBuilder, entity, t-> primaryColor, t-> secondaryColor, scaleScalar, alphaScalar, partialTicks);
    }

    public static void renderEntityTrail(PoseStack poseStack, VFXBuilders.WorldVFXBuilder builder, TrailPointBuilder trailPointBuilder, Entity entity, Function<Float, Color> primaryColor, Function<Float, Color> secondaryColor, float scaleScalar, float alphaScalar, float partialTicks) {
        List<TrailPoint> spinningTrailPoints = trailPointBuilder.getTrailPoints();
        poseStack.pushPose();
        float trailOffsetX = (float) Mth.lerp(partialTicks, entity.xOld, entity.getX());
        float trailOffsetY = (float) Mth.lerp(partialTicks, entity.yOld, entity.getY());
        float trailOffsetZ = (float) Mth.lerp(partialTicks, entity.zOld, entity.getZ());
        if (spinningTrailPoints.size() >= 2) {
            poseStack.translate(-trailOffsetX, -trailOffsetY, -trailOffsetZ);
            for (int i = 0; i < 2; i++) {
                float size = (0.2f + i * 0.2f) * scaleScalar;
                float alpha = (0.7f - i * 0.35f) * alphaScalar;
                builder.setAlpha(alpha)
                        .renderTrail(poseStack, spinningTrailPoints, f -> size, f -> builder.setAlpha(alpha * f).setColor(ColorHelper.colorLerp(Easing.SINE_IN, f * 2f, secondaryColor.apply(f), primaryColor.apply(f))))
                        .renderTrail(poseStack, spinningTrailPoints, f -> 1.5f * size, f -> builder.setAlpha(alpha * f / 2f).setColor(ColorHelper.colorLerp(Easing.SINE_IN, f * 1.5f, secondaryColor.apply(f), primaryColor.apply(f))))
                        .renderTrail(poseStack, spinningTrailPoints, f -> size * 2.5f, f -> builder.setAlpha(alpha * f / 4f).setColor(ColorHelper.colorLerp(Easing.SINE_IN, f * 1.5f, secondaryColor.apply(f), primaryColor.apply(f))));
            }
            poseStack.translate(trailOffsetX, trailOffsetY, trailOffsetZ);
        }
        poseStack.popPose();
    }

    public static void drawCube(PoseStack poseStack, VFXBuilders.WorldVFXBuilder builder, float scale, CubeVertexData cubeVertexData) {
        drawCube(poseStack, builder, builder, scale, cubeVertexData);
    }
    public static void drawCubeSides(PoseStack poseStack, VFXBuilders.WorldVFXBuilder builder, float scale, CubeVertexData cubeVertexData) {
        drawCube(poseStack, null, builder, scale, cubeVertexData);
    }
    public static void drawCube(PoseStack poseStack, @Nullable VFXBuilders.WorldVFXBuilder builder, VFXBuilders.WorldVFXBuilder sideBuilder, float scale, CubeVertexData cubeVertexData) {
        Vector3f[] bottomVertices = cubeVertexData.bottomVertices;
        Vector3f[] topVertices = cubeVertexData.topVertices;
        Collection<Vector3f[]> offsetMap = cubeVertexData.offsetMap;
        poseStack.pushPose();
        poseStack.translate(0.5f, 0.5f, 0.5f);
        poseStack.scale(scale, scale, scale);
        poseStack.translate(-0.5f, -0.5f, -0.5f);
        for (Vector3f[] offsets : offsetMap) {
            drawSide(poseStack, sideBuilder, offsets);
        }
        if (builder != null) {
            drawSide(poseStack, builder, new Vector3f[]{bottomVertices[3], bottomVertices[2], bottomVertices[1], bottomVertices[0]});
            drawSide(poseStack, builder, topVertices);
        }
        poseStack.popPose();
    }

    public static void drawSide(PoseStack poseStack, VFXBuilders.WorldVFXBuilder builder, Vector3f[] offsets) {
        builder.renderQuad(poseStack, offsets, 1f);
    }

    public static CubeVertexData makeCubePositions(float scale) {
        return makeCubePositions(scale, scale);
    }
    public static CubeVertexData makeCubePositions(float xScale, float yScale) {
        float half = 0.5f;
        float xOffset = xScale / 2f;
        float yOffset = yScale / 2f;
        float xStart = -xOffset + half;
        float xEnd = xOffset + half;
        float yStart = -yOffset + half;
        float yEnd = yOffset + half;
        Vector3f[] bottomVertices = new Vector3f[]{new Vector3f(xStart, yStart, xStart), new Vector3f(xStart, yStart, xEnd), new Vector3f(xEnd, yStart, xEnd), new Vector3f(xEnd, yStart, xStart)};
        Vector3f[] topVertices = new Vector3f[]{new Vector3f(xStart, yEnd, xStart), new Vector3f(xStart, yEnd, xEnd), new Vector3f(xEnd, yEnd, xEnd), new Vector3f(xEnd, yEnd, xStart)};
        Collection<Vector3f[]> offsetMap = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            offsetMap.add(new Vector3f[]{bottomVertices[i], bottomVertices[(i + 1) % 4], topVertices[(i + 1) % 4], topVertices[(i) % 4]});
        }
        return new CubeVertexData(bottomVertices, topVertices, offsetMap);
    }

    public record CubeVertexData(Vector3f[] bottomVertices, Vector3f[] topVertices, Collection<Vector3f[]> offsetMap) {

        public CubeVertexData applyWobble(float sineOffset, float strength) {
            return applyWobble(sineOffset, sineOffset, strength);
        }

        public CubeVertexData applyWobble(float bottomSineOffset, float topSineOffset, float strength) {
            applyWobble(bottomVertices, bottomSineOffset, strength);
            applyWobble(topVertices, topSineOffset, strength);
            return applyWobble(bottomVertices, bottomSineOffset, strength)
                    .applyWobble(topVertices, topSineOffset, strength);
        }

        public CubeVertexData applyWobble(Vector3f[] offsets, float sineOffset, float strength) {
            float offset = sineOffset;
            for (Vector3f vector3f : offsets) {
                double time = ((Minecraft.getInstance().level.getGameTime() / 40.0F) % Math.PI * 2);
                float sine = Mth.sin((float) (time + (offset * Math.PI * 2))) * strength;
                vector3f.add(sine, -sine, sine);
                offset += 0.25f;
            }
            return this;
        }

        public CubeVertexData stretch(float scale) {
            return stretch(scale, scale);
        }

        public CubeVertexData stretch(float width, float height) {
            return stretch(width, height, width);
        }

        public CubeVertexData stretch(float x, float y, float z) {
            for (int i = 0; i < bottomVertices.length; i++) {
                bottomVertices[i].mul(x, y, z);
                topVertices[i].mul(x, y, z);
            }
            return this;
        }

        public CubeVertexData constrict(float scale) {
            return constrict(scale, scale);
        }

        public CubeVertexData constrict(float width, float height) {
            return constrict(width, height, width);
        }

        public CubeVertexData constrict(float x, float y, float z) {
            for (int i = 0; i < bottomVertices.length; i++) {
                bottomVertices[i].div(x, y, z);
                topVertices[i].div(x, y, z);
            }
            return this;
        }

        public CubeVertexData offset(float x, float y, float z) {
            for (int i = 0; i < bottomVertices.length; i++) {
                bottomVertices[i].add(x, y, z);
                topVertices[i].add(x, y, z);
            }
            return this;
        }
    }
}
