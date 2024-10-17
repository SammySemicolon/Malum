package com.sammy.malum.client.renderer.entity.nitrate;

import com.mojang.blaze3d.vertex.*;
import com.sammy.malum.client.*;
import com.sammy.malum.common.entity.nitrate.*;
import com.sammy.malum.registry.client.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.resources.*;
import team.lodestar.lodestone.registry.client.*;
import team.lodestar.lodestone.systems.rendering.*;

import java.awt.*;
import java.util.function.*;

import static com.sammy.malum.client.renderer.entity.FloatingItemEntityRenderer.*;

public class AbstractNitrateEntityRenderer<T extends AbstractNitrateEntity> extends EntityRenderer<T> {
    public final Function<Float, Color> primaryColor;
    public final Function<Float, Color> secondaryColor;

    public AbstractNitrateEntityRenderer(EntityRendererProvider.Context context, Function<Float, Color> primaryColor, Function<Float, Color> secondaryColor) {
        super(context);
        this.primaryColor = primaryColor;
        this.secondaryColor = secondaryColor;
        this.shadowRadius = 0;
        this.shadowStrength = 0;
    }

    public AbstractNitrateEntityRenderer(EntityRendererProvider.Context context, Color primaryColor, Color secondaryColor) {
        this(context, f -> primaryColor, f -> secondaryColor);
    }


    @Override
    public void render(T entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int packedLightIn) {
        float effectScalar = entity.getVisualEffectScalar();
        var renderType = LodestoneRenderTypeRegistry.ADDITIVE_TEXTURE_TRIANGLE.applyAndCache(MalumRenderTypeTokens.CONCENTRATED_TRAIL);
        var builder = VFXBuilders.createWorld().setRenderType(renderType);
        RenderUtils.renderEntityTrail(poseStack, builder, entity.trailPointBuilder, entity, primaryColor, secondaryColor, effectScalar, partialTicks);
        RenderUtils.renderEntityTrail(poseStack, builder, entity.spinningTrailPointBuilder, entity, primaryColor, secondaryColor, effectScalar, partialTicks);
        if (entity.age > 1 && !entity.fadingAway) {
            poseStack.pushPose();
            float glimmerScale = 3f * Math.min(1f, (entity.age - 2) / 5f);
            poseStack.scale(glimmerScale, glimmerScale, glimmerScale);
            renderSpiritGlimmer(poseStack, primaryColor.apply(0f), secondaryColor.apply(0.125f), partialTicks);
            poseStack.popPose();
        }
        super.render(entity, entityYaw, partialTicks, poseStack, bufferIn, packedLightIn);
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}
