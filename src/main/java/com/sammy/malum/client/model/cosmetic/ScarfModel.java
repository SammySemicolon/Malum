package com.sammy.malum.client.model.cosmetic;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.sammy.malum.MalumMod;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.player.Player;

public class ScarfModel extends EntityModel<Player> {

    public static ModelLayerLocation LAYER = new ModelLayerLocation(MalumMod.malumPath("scarf"), "main");
    public final ModelPart headScarf;
    public final ModelPart torsoScarf;

    public ScarfModel(ModelPart root) {
        this.headScarf = root.getChild("head_scarf");
        this.torsoScarf = root.getChild("torso_scarf");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = HumanoidModel.createMesh(new CubeDeformation(0), 0);
        PartDefinition partdefinition = mesh.getRoot();

        PartDefinition headScarf = partdefinition.addOrReplaceChild("head_scarf", CubeListBuilder.create().texOffs(0, 14).addBox(-4.5F, -2.25F, -4.5F, 9.0F, 3.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition torsoScarf = partdefinition.addOrReplaceChild("torso_scarf", CubeListBuilder.create().texOffs(0, 0).addBox(-5.5F, -1.0F, -2.5F, 11.0F, 9.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(mesh, 64, 32);
    }

    public void copyFromDefault(HumanoidModel model) {
        torsoScarf.copyFrom(model.body);
        headScarf.copyFrom(model.head);
    }

    @Override
    public void setupAnim(Player pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {

    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
        headScarf.render(poseStack, buffer, packedLight, packedOverlay);
        torsoScarf.render(poseStack, buffer, packedLight, packedOverlay);
    }
}