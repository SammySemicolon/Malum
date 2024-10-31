package com.sammy.malum.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.sammy.malum.MalumMod;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.player.Player;

public class TopHatModel extends EntityModel<Player> {

    public static ModelLayerLocation LAYER = new ModelLayerLocation(MalumMod.malumPath("top_hat"), "main");
    public final ModelPart topHat;

    public TopHatModel(ModelPart root) {
        this.topHat = root.getChild("topHat");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition topHat = partdefinition.addOrReplaceChild("topHat", CubeListBuilder.create().texOffs(36, 0).addBox(-6.5F, -9.0F, -6.5F, 13.0F, 2.0F, 13.0F, new CubeDeformation(0.0F))
                .texOffs(36, 15).addBox(-6.5F, -9.0F, -6.5F, 13.0F, 2.0F, 13.0F, new CubeDeformation(0.35F))
                .texOffs(0, 0).addBox(-4.5F, -20.0F, -4.5F, 9.0F, 11.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(0, 20).addBox(-4.5F, -20.0F, -4.5F, 9.0F, 11.0F, 9.0F, new CubeDeformation(0.35F)), PartPose.offset(0.0F, 1.0F, 0.0F));
        return LayerDefinition.create(meshdefinition, 96, 64);
    }

    @Override
    public void setupAnim(Player entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
        topHat.render(poseStack, buffer, packedLight, packedOverlay, color);
    }
}