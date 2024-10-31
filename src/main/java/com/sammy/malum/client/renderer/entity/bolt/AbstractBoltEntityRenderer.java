package com.sammy.malum.client.renderer.entity.bolt;

import com.mojang.blaze3d.vertex.*;
import com.sammy.malum.client.*;
import com.sammy.malum.common.entity.bolt.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.resources.*;
import net.minecraft.util.*;
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

    private static final RenderType TRAIL_TYPE = LodestoneRenderTypes.ADDITIVE_TEXTURE_TRIANGLE.apply(RenderTypeToken.createCachedToken(malumPath("textures/vfx/concentrated_trail.png")));

    public RenderType getTrailRenderType() {
        return TRAIL_TYPE;
    }

    public float getAlphaMultiplier() {
        return 1f;
    }

    @Override
    public void render(T entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int packedLightIn) {
        if (entity.spawnDelay > 0) {
            return;
        }
        float effectScalar = entity.getVisualEffectScalar();
        final float alphaScalar = Mth.clamp(effectScalar * getAlphaMultiplier(), 0, 1);
        VFXBuilders.WorldVFXBuilder builder = VFXBuilders.createWorld().setRenderType(getTrailRenderType());
        RenderUtils.renderEntityTrail(poseStack, builder, entity.trailPointBuilder, entity, primaryColor, secondaryColor, effectScalar, alphaScalar, partialTicks);
        RenderUtils.renderEntityTrail(poseStack, builder, entity.spinningTrailPointBuilder, entity, primaryColor, secondaryColor, effectScalar, alphaScalar, partialTicks);
        super.render(entity, entityYaw, partialTicks, poseStack, bufferIn, packedLightIn);
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}
