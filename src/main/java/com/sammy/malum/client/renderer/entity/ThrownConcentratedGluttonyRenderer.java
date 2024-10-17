package com.sammy.malum.client.renderer.entity;

import com.mojang.blaze3d.vertex.*;
import com.mojang.math.*;
import com.sammy.malum.client.*;
import com.sammy.malum.common.entity.thrown.*;
import com.sammy.malum.registry.client.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.core.*;
import net.minecraft.resources.*;
import net.minecraft.world.item.*;
import team.lodestar.lodestone.handlers.*;
import team.lodestar.lodestone.registry.client.*;
import team.lodestar.lodestone.systems.rendering.*;
import team.lodestar.lodestone.systems.rendering.rendeertype.*;
import team.lodestar.lodestone.systems.rendering.trail.*;

import java.awt.*;

import static com.sammy.malum.MalumMod.*;

public class ThrownConcentratedGluttonyRenderer extends EntityRenderer<ThrownConcentratedGluttony> {

   private static final RenderType TRANSPARENT_TRAIL_TYPE = LodestoneRenderTypeRegistry.TRANSPARENT_TWO_SIDED_TEXTURE_TRIANGLE.apply(RenderTypeToken.createCachedToken(malumPath("textures/vfx/concentrated_trail.png")), ShaderUniformHandler.LUMITRANSPARENT);

   private static final Color GLUTTONY_GREEN = new Color(47, 81, 28);
   private static final Color GLUTTONY_DARK = new Color(31, 35, 30);
   private static final Color GLUTTONY_SHADE = new Color(14, 14, 16);

   private final ItemRenderer itemRenderer;
   private final float scale;
   private final boolean fullBright;

   public ThrownConcentratedGluttonyRenderer(EntityRendererProvider.Context pContext, float pScale, boolean pFullBright) {
      super(pContext);
      this.itemRenderer = pContext.getItemRenderer();
      this.scale = pScale;
      this.fullBright = pFullBright;
   }

   public ThrownConcentratedGluttonyRenderer(EntityRendererProvider.Context pContext) {
      this(pContext, 1.0F, false);
   }

   @Override
   protected int getBlockLightLevel(ThrownConcentratedGluttony entity, BlockPos pPos) {
      return this.fullBright ? 15 : super.getBlockLightLevel(entity, pPos);
   }

   @Override
   public void render(ThrownConcentratedGluttony entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int packedLightIn) {
      if (entity.tickCount >= 2 || !(this.entityRenderDispatcher.camera.getEntity().distanceToSqr(entity) < 12.25D)) {
         if (!entity.fadingAway) {
            poseStack.pushPose();
            poseStack.scale(this.scale, this.scale, this.scale);
            poseStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
            poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
            this.itemRenderer.renderStatic(entity.getItem(), ItemDisplayContext.GROUND, packedLightIn, OverlayTexture.NO_OVERLAY, poseStack, RenderHandler.DELAYED_RENDER.getTarget(), entity.level(), entity.getId());
            poseStack.popPose();
         }
         float scale = entity.getVisualEffectScalar();
         var additive = LodestoneRenderTypeRegistry.ADDITIVE_TWO_SIDED_TEXTURE_TRIANGLE.applyAndCache(MalumRenderTypeTokens.CONCENTRATED_TRAIL);
         var transparent = LodestoneRenderTypeRegistry.TRANSPARENT_TWO_SIDED_TEXTURE_TRIANGLE.applyAndCache(MalumRenderTypeTokens.CONCENTRATED_TRAIL, ShaderUniformHandler.LUMITRANSPARENT);
         var builder = VFXBuilders.createWorld().setRenderType(additive);
         for (TrailPointBuilder trail : entity.trails) {
            RenderUtils.renderEntityTrail(poseStack, builder, trail, entity, GLUTTONY_GREEN, GLUTTONY_DARK, scale * 0.5f, scale * 0.5f, partialTicks);
         }
         builder.setRenderType(TRANSPARENT_TRAIL_TYPE);
         for (TrailPointBuilder trail : entity.trails) {
            RenderUtils.renderEntityTrail(poseStack, builder, trail, entity, GLUTTONY_GREEN, GLUTTONY_SHADE, scale * 0.75f, scale * 2f, partialTicks);
            RenderUtils.renderEntityTrail(poseStack, builder, trail, entity, GLUTTONY_DARK, GLUTTONY_SHADE, scale, scale * 3f, partialTicks);
         }
         super.render(entity, entityYaw, partialTicks, poseStack, bufferIn, packedLightIn);
      }
   }

   @Override
   public ResourceLocation getTextureLocation(ThrownConcentratedGluttony entity) {
      return TextureAtlas.LOCATION_BLOCKS;
   }
}