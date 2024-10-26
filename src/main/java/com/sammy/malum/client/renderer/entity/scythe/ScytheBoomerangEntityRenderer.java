package com.sammy.malum.client.renderer.entity.scythe;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.sammy.malum.client.*;
import com.sammy.malum.common.entity.scythe.ScytheBoomerangEntity;
import com.sammy.malum.common.item.*;
import com.sammy.malum.common.item.curiosities.weapons.scythe.MalumScytheItem;
import com.sammy.malum.registry.client.*;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.phys.*;
import team.lodestar.lodestone.registry.client.*;
import team.lodestar.lodestone.systems.rendering.*;
import team.lodestar.lodestone.systems.rendering.rendeertype.*;

import java.awt.*;

public class ScytheBoomerangEntityRenderer extends EntityRenderer<ScytheBoomerangEntity> {

    public final ItemRenderer itemRenderer;

    public ScytheBoomerangEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public void render(ScytheBoomerangEntity entityIn, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int packedLightIn) {
        poseStack.pushPose();
        var itemstack = entityIn.getItem();
        var model = this.itemRenderer.getModel(itemstack, entityIn.level(), null, 1);
        if (entityIn.isEnhanced()) {
            Vec3 direction = entityIn.getDeltaMovement().normalize();
            float yRot = ((float) (Mth.atan2(direction.x, direction.z)));
            poseStack.mulPose(Axis.YP.rotation(yRot+1.57f));
        }
        else {
            poseStack.mulPose(Axis.XP.rotationDegrees(90f));
        }
        poseStack.mulPose(Axis.ZP.rotation((entityIn.age + partialTicks) * 0.9f));
        poseStack.scale(2f, 2, 1.5f);
        itemRenderer.render(itemstack, itemstack.getItem() instanceof MalumScytheItem ? ItemDisplayContext.NONE : ItemDisplayContext.FIXED, false, poseStack, bufferIn, packedLightIn, OverlayTexture.NO_OVERLAY, model);
        poseStack.popPose();

        var spirit = entityIn.getItem().getItem() instanceof ISpiritAffiliatedItem affiliatedItem ? affiliatedItem.getDefiningSpiritType() : null;
        boolean isMagical = spirit != null;
        var renderType = isMagical ?
                LodestoneRenderTypeRegistry.ADDITIVE_TWO_SIDED_TEXTURE_TRIANGLE.applyAndCache(MalumRenderTypeTokens.CONCENTRATED_TRAIL) :
                LodestoneRenderTypeRegistry.TRANSPARENT_TWO_SIDED_TEXTURE_TRIANGLE.applyAndCache(MalumRenderTypeTokens.CONCENTRATED_TRAIL, ShaderUniformHandler.LUMITRANSPARENT);
        var primaryColor = isMagical ? spirit.getPrimaryColor() : new Color(0.9f, 0.9f, 0.9f);
        var secondaryColor = isMagical ? spirit.getSecondaryColor() : new Color(0.5f, 0.5f, 0.5f);
        var builder = VFXBuilders.createWorld().setRenderType(renderType);
        final float scalar = Math.min(entityIn.age / 20f, 1f);
        RenderUtils.renderEntityTrail(poseStack, builder, entityIn.theFormer, entityIn, primaryColor, secondaryColor, scalar, partialTicks);
        RenderUtils.renderEntityTrail(poseStack, builder, entityIn.theLatter, entityIn, primaryColor, secondaryColor, scalar, partialTicks);

        super.render(entityIn, entityYaw, partialTicks, poseStack, bufferIn, packedLightIn);
    }

    @Override
    public ResourceLocation getTextureLocation(ScytheBoomerangEntity entity) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}