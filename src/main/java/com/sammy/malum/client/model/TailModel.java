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

public class TailModel extends EntityModel<Player> {

    public static ModelLayerLocation LAYER = new ModelLayerLocation(MalumMod.malumPath("tail"), "main");
    private final ModelPart tail;

    public TailModel(ModelPart root) {
        this.tail = root.getChild("tail");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition tail = partdefinition.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 13).addBox(-2.0F, -3.5F, -3.0F, 4.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(14, 13).addBox(-2.0F, -3.5F, -3.0F, 4.0F, 4.0F, 3.0F, new CubeDeformation(0.4F)), PartPose.offsetAndRotation(0.0F, 12.0F, 2.75F, -0.7854F, 0.0F, 0.0F));

        PartDefinition tail2 = tail.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -7.5F, -2.5F, 6.0F, 8.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(22, 0).addBox(-3.0F, -7.5F, -2.5F, 6.0F, 8.0F, 5.0F, new CubeDeformation(0.4F)), PartPose.offsetAndRotation(0.0F, -4.0F, -1.5F, 0.2182F, 0.0F, 0.0F));

        PartDefinition tailtip = tail2.addOrReplaceChild("tailtip", CubeListBuilder.create().texOffs(0, 20).addBox(-2.5F, -2.0F, -2.0F, 5.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(18, 20).addBox(-2.5F, -2.0F, -2.0F, 5.0F, 3.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.0F, -7.5F, 0.0F, -0.3491F, 0.0F, 0.0F));

        PartDefinition tailtip1 = tailtip.addOrReplaceChild("tailtip1", CubeListBuilder.create().texOffs(0, 28).addBox(-0.3856F, -6.5303F, -2.561F, 0.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, 0.0F, -0.1298F, -0.7769F, 0.1841F));

        PartDefinition tailtip2 = tailtip.addOrReplaceChild("tailtip2", CubeListBuilder.create().texOffs(0, 22).addBox(0.3856F, -6.0303F, -2.561F, 0.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, 0.0F, -0.1298F, 0.7769F, -0.1841F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(Player entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
        tail.render(poseStack, buffer, packedLight, packedOverlay);
    }
}