package com.sammy.malum.client.renderer.entity.bolt;

import com.mojang.blaze3d.vertex.*;
import com.sammy.malum.client.*;
import com.sammy.malum.common.entity.bolt.*;
import com.sammy.malum.registry.client.MalumRenderTypeTokens;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.resources.*;
import net.minecraft.util.*;
import team.lodestar.lodestone.helpers.ColorHelper;
import team.lodestar.lodestone.registry.client.*;
import team.lodestar.lodestone.systems.rendering.VFXBuilders;
import team.lodestar.lodestone.systems.rendering.rendeertype.*;

import java.awt.*;

import static com.sammy.malum.MalumMod.*;

public abstract class AbstractBoltEntityRenderer<T extends AbstractBoltProjectileEntity> extends EntityRenderer<T> {
    public final Color primaryColor;
    public final Color secondaryColor;

    public AbstractBoltEntityRenderer(EntityRendererProvider.Context context, Color primaryColor, Color secondaryColor) {
        super(context);
        this.primaryColor = primaryColor;
        this.secondaryColor = secondaryColor;
        this.shadowRadius = 0;
        this.shadowStrength = 0;
    }

    public RenderType getTrailRenderType(boolean isTransparent) {
        return isTransparent ?
                LodestoneRenderTypes.TRANSPARENT_TWO_SIDED_TEXTURE_TRIANGLE.apply(MalumRenderTypeTokens.CONCENTRATED_TRAIL, ShaderUniformHandler.LUMITRANSPARENT) :
                LodestoneRenderTypes.ADDITIVE_TWO_SIDED_TEXTURE_TRIANGLE.apply(MalumRenderTypeTokens.CONCENTRATED_TRAIL);
    }

    public Color getPrimaryColor(int trailIndex) {
        if (trailIndex < 2) {
            return ColorHelper.darker(primaryColor, 3);
        }
        return primaryColor;
    }
    public Color getSecondaryColor(int trailIndex) {
        if (trailIndex < 2) {
            return ColorHelper.darker(secondaryColor, 3);
        }
        return secondaryColor;
    }

    public float getAlphaMultiplier() {
        return 1f;
    }

    public float getScaleMultiplier() {
        return 1.4f;
    }

    @Override
    public void render(T entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int packedLightIn) {
        if (entity.spawnDelay > 0) {
            return;
        }
        float delta = entity.getVisualEffectScalar();
        float scale = delta * getScaleMultiplier();
        float alpha = Mth.clamp(delta * getAlphaMultiplier(), 0, 1);
        VFXBuilders.WorldVFXBuilder builder = VFXBuilders.createWorld().setRenderType(getTrailRenderType(true));
        RenderUtils.renderEntityTrail(poseStack, builder, entity.trailPointBuilder, entity, getPrimaryColor(0), getSecondaryColor(0), scale * 2f, alpha, partialTicks);
        RenderUtils.renderEntityTrail(poseStack, builder, entity.spinningTrailPointBuilder, entity, getPrimaryColor(1), getSecondaryColor(1), scale * 2f, alpha, partialTicks);
        builder.setRenderType(getTrailRenderType(false));
        RenderUtils.renderEntityTrail(poseStack, builder, entity.trailPointBuilder, entity, getPrimaryColor(2), getSecondaryColor(2), scale, alpha, partialTicks);
        RenderUtils.renderEntityTrail(poseStack, builder, entity.spinningTrailPointBuilder, entity, getPrimaryColor(3), getSecondaryColor(3), scale, alpha, partialTicks);
        super.render(entity, entityYaw, partialTicks, poseStack, bufferIn, packedLightIn);
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}