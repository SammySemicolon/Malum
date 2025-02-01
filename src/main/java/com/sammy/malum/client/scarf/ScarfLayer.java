package com.sammy.malum.client.scarf;

import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.entity.layers.*;
import net.minecraft.world.entity.*;

import java.awt.*;

public class ScarfLayer<T extends LivingEntity, M extends EntityModel<T>> extends
        RenderLayer<T, M> {

    private final RenderLayerParent<T, M> renderLayerParent;

    public ScarfLayer(RenderLayerParent<T, M> renderer) {
        super(renderer);
        this.renderLayerParent = renderer;
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, T livingEntity, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {

    }
}