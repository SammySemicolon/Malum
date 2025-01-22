package com.sammy.malum.client.renderer.entity;

import com.mojang.blaze3d.vertex.*;
import com.sammy.malum.client.*;
import com.sammy.malum.common.entity.activator.*;
import com.sammy.malum.registry.client.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.resources.*;
import team.lodestar.lodestone.registry.client.*;

import static com.sammy.malum.registry.common.SpiritTypeRegistry.*;

public class SpiritCollectionActivatorEntityRenderer extends EntityRenderer<SpiritCollectionActivatorEntity> {
    public final ItemRenderer itemRenderer;

    public SpiritCollectionActivatorEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.itemRenderer = context.getItemRenderer();
        this.shadowRadius = 0;
        this.shadowStrength = 0;
    }


    @Override
    public void render(SpiritCollectionActivatorEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int packedLightIn) {
        var spiritType = UMBRAL_SPIRIT;
        var secondarySpiritType = ELDRITCH_SPIRIT;
        var additive = LodestoneRenderTypes.ADDITIVE_TWO_SIDED_TEXTURE_TRIANGLE.apply(MalumRenderTypeTokens.CONCENTRATED_TRAIL);
        var transparent = LodestoneRenderTypes.TRANSPARENT_TWO_SIDED_TEXTURE_TRIANGLE.apply(MalumRenderTypeTokens.CONCENTRATED_TRAIL);
        var trailBuilder = SpiritBasedWorldVFXBuilder.create(spiritType).setRenderType(additive);
        float yOffset = entity.getYOffset(partialTicks);

        for (int i = 0; i < 2; i++) {

            poseStack.pushPose();
            poseStack.translate(0.0D, yOffset, 0.0D);
            FloatingItemEntityRenderer.renderSpiritGlimmer(poseStack, secondarySpiritType, 1f, 4f, partialTicks);
            FloatingItemEntityRenderer.renderSpiritGlimmer(poseStack, spiritType, 2f, 0.5f, partialTicks);
            poseStack.popPose();

            RenderUtils.renderEntityTrail(poseStack, trailBuilder, entity.trail, entity, spiritType.getPrimaryColor(), spiritType.getSecondaryColor(), 0.7f, 1f, partialTicks);
            RenderUtils.renderEntityTrail(poseStack, trailBuilder, entity.secondaryTrailPointBuilder, entity, spiritType.getPrimaryColor(), spiritType.getSecondaryColor(), 0.9f, 1f, partialTicks);
            RenderUtils.renderEntityTrail(poseStack, trailBuilder, entity.trinaryTrailPointBuilder, entity, spiritType.getPrimaryColor(), spiritType.getSecondaryColor(), 0.9f, 1f, partialTicks);
            trailBuilder.setRenderType(transparent);
            RenderUtils.renderEntityTrail(poseStack, trailBuilder, entity.trail, entity, spiritType.getPrimaryColor(), spiritType.getSecondaryColor(), 0.7f, 1.25f, partialTicks);
            RenderUtils.renderEntityTrail(poseStack, trailBuilder, entity.secondaryTrailPointBuilder, entity, spiritType.getPrimaryColor(), spiritType.getSecondaryColor(), 0.4f, 1.25f, partialTicks);
            RenderUtils.renderEntityTrail(poseStack, trailBuilder, entity.trinaryTrailPointBuilder, entity, spiritType.getPrimaryColor(), spiritType.getSecondaryColor(), 0.4f, 1.25f, partialTicks);
        }
        super.render(entity, entityYaw, partialTicks, poseStack, bufferIn, packedLightIn);
    }

    @Override
    public ResourceLocation getTextureLocation(SpiritCollectionActivatorEntity entity) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}