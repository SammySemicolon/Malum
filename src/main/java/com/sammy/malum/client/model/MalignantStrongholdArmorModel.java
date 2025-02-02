package com.sammy.malum.client.model;
// Made with Blockbench 3.9.2
// Exported for Minecraft version 1.15 - 1.16 with MCP mappings
// Paste this class into your mod and generate all required imports


import com.google.common.collect.*;
import com.mojang.blaze3d.vertex.*;
import com.sammy.malum.*;
import com.sammy.malum.common.item.curiosities.curios.runes.*;
import com.sammy.malum.core.systems.spirit.*;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.*;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.*;
import net.minecraft.world.entity.*;
import team.lodestar.lodestone.systems.easing.*;
import team.lodestar.lodestone.systems.model.*;
import team.lodestar.lodestone.systems.rendering.rendeertype.*;

import java.awt.*;
import java.util.List;
import java.util.*;

public class MalignantStrongholdArmorModel extends LodestoneArmorModel {
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(MalumMod.malumPath("malignant_lead_armor"), "main");

    public MalignantStrongholdArmorModel(ModelPart root) {
        super(root);
    }

    @Override
    public void renderToBuffer(PoseStack matrixStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int p_350361_) {
        super.renderToBuffer(matrixStack, vertexConsumer, packedLight, packedOverlay, p_350361_);
    }

    public static LayerDefinition createBodyLayer() {
        return createArmorModel((mesh, root, body, leggings, right_legging, left_legging, right_foot, left_foot, right_arm, left_arm, head) -> {
            PartDefinition helmet = head.addOrReplaceChild("helmet", CubeListBuilder.create().texOffs(24, 25).addBox(-4.0F, -8.0F, -4.5F, 3.0F, 2.0F, 9.0F, new CubeDeformation(0.0F))
                    .texOffs(0, 32).addBox(-4.5F, -6.0F, -1.0F, 9.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
                    .texOffs(24, 25).mirror().addBox(1.0F, -8.0F, -4.5F, 3.0F, 2.0F, 9.0F, new CubeDeformation(0.0F)).mirror(false)
                    .texOffs(0, 0).addBox(-1.5F, -9.0F, -5.0F, 3.0F, 5.0F, 11.0F, new CubeDeformation(0.0F))
                    .texOffs(0, 0).addBox(-1.5F, -9.0F, -5.0F, 3.0F, 5.0F, 11.0F, new CubeDeformation(0.0F))
                    .texOffs(0, 16).addBox(-5.5F, -11.0F, -3.0F, 2.0F, 6.0F, 10.0F, new CubeDeformation(0.0F))
                    .texOffs(0, 16).mirror().addBox(3.5F, -11.0F, -3.0F, 2.0F, 6.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false)
                    .texOffs(14, 16).mirror().addBox(3.0F, -4.0F, -4.5F, 2.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                    .texOffs(14, 16).addBox(-5.0F, -4.0F, -4.5F, 2.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
                    .texOffs(17, 2).addBox(-3.0F, -4.0F, -5.0F, 6.0F, 5.0F, 4.0F, new CubeDeformation(0.0F))
                    .texOffs(21, 14).addBox(-5.0F, -6.0F, -4.5F, 4.0F, 2.0F, 9.0F, new CubeDeformation(0.0F))
                    .texOffs(21, 14).mirror().addBox(1.0F, -6.0F, -4.5F, 4.0F, 2.0F, 9.0F, new CubeDeformation(0.0F)).mirror(false)
                    .texOffs(86, 25).addBox(-4.0F, -8.0F, -4.5F, 3.0F, 2.0F, 9.0F, new CubeDeformation(0.25F))
                    .texOffs(86, 25).mirror().addBox(1.0F, -8.0F, -4.5F, 3.0F, 2.0F, 9.0F, new CubeDeformation(0.25F)).mirror(false)
                    .texOffs(62, 0).addBox(-1.5F, -9.0F, -5.0F, 3.0F, 5.0F, 11.0F, new CubeDeformation(0.25F))
                    .texOffs(62, 32).addBox(-4.5F, -6.0F, -1.0F, 9.0F, 6.0F, 6.0F, new CubeDeformation(0.25F))
                    .texOffs(83, 14).addBox(-5.0F, -6.0F, -4.5F, 4.0F, 2.0F, 9.0F, new CubeDeformation(0.3F))
                    .texOffs(76, 16).mirror().addBox(3.0F, -4.0F, -4.5F, 2.0F, 3.0F, 4.0F, new CubeDeformation(0.25F)).mirror(false)
                    .texOffs(79, 2).addBox(-3.0F, -4.0F, -5.0F, 6.0F, 5.0F, 4.0F, new CubeDeformation(0.25F))
                    .texOffs(76, 16).addBox(-5.0F, -4.0F, -4.5F, 2.0F, 3.0F, 4.0F, new CubeDeformation(0.25F))
                    .texOffs(83, 14).mirror().addBox(1.0F, -6.0F, -4.5F, 4.0F, 2.0F, 9.0F, new CubeDeformation(0.3F)).mirror(false)
                    .texOffs(62, 16).mirror().addBox(3.5F, -11.0F, -3.0F, 2.0F, 6.0F, 10.0F, new CubeDeformation(0.25F)).mirror(false)
                    .texOffs(62, 16).addBox(-5.5F, -11.0F, -3.0F, 2.0F, 6.0F, 10.0F, new CubeDeformation(0.25F))
                    .texOffs(62, 0).addBox(-1.5F, -9.0F, -5.0F, 3.0F, 5.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

            PartDefinition torso = body.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(0, 57).addBox(-5.0F, 2.0F, -3.0F, 10.0F, 5.0F, 6.0F, new CubeDeformation(0.025F))
                    .texOffs(0, 68).addBox(-4.5F, 6.5F, -2.5F, 9.0F, 4.0F, 5.0F, new CubeDeformation(0.0F))
                    .texOffs(62, 57).addBox(-5.0F, 2.0F, -3.0F, 10.0F, 5.0F, 6.0F, new CubeDeformation(0.275F))
                    .texOffs(62, 68).addBox(-4.5F, 6.5F, -2.5F, 9.0F, 4.0F, 5.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, 0.0F, 0.0F));

            PartDefinition cuirass = torso.addOrReplaceChild("cuirass", CubeListBuilder.create().texOffs(0, 44).addBox(-6.0F, -1.0F, -5.0F, 12.0F, 4.0F, 9.0F, new CubeDeformation(0.0F))
                    .texOffs(62, 44).addBox(-6.0F, -1.0F, -5.0F, 12.0F, 4.0F, 9.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.0F, 0.05F, 0.075F, 0.2618F, 0.0F, 0.0F));

            PartDefinition scarf = torso.addOrReplaceChild("scarf", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

            PartDefinition lower_scarf = scarf.addOrReplaceChild("lower_scarf", CubeListBuilder.create().texOffs(0, 121).addBox(-5.5F, 2.05F, 4.075F, 11.0F, 5.0F, 0.0F, new CubeDeformation(0.0F))
                    .texOffs(62, 121).addBox(-5.5F, 2.05F, 4.075F, 11.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0873F, 0.0F, 0.0F));

            PartDefinition upper_scarf = scarf.addOrReplaceChild("upper_scarf", CubeListBuilder.create().texOffs(40, 110).addBox(-3.5F, -1.0F, 4.0F, 7.0F, 6.0F, 0.0F, new CubeDeformation(0.0F))
                    .texOffs(102, 110).addBox(-3.5F, -1.0F, 4.0F, 7.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.05F, 0.325F, 0.2618F, 0.0F, 0.0F));

            PartDefinition uppermost_scarf = upper_scarf.addOrReplaceChild("uppermost_scarf", CubeListBuilder.create().texOffs(34, 104).addBox(-4.5F, -1.0F, 4.0F, 9.0F, 6.0F, 0.0F, new CubeDeformation(0.0F))
                    .texOffs(96, 104).addBox(-4.5F, -1.0F, 4.0F, 9.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.2618F, 0.0F, 0.0F));

            PartDefinition left_shoulder = left_arm.addOrReplaceChild("left_shoulder", CubeListBuilder.create().texOffs(33, 57).addBox(3.0F, -4.5F, -3.0F, 2.0F, 5.0F, 6.0F, new CubeDeformation(0.01F))
                    .texOffs(22, 118).addBox(2.0F, 2.0F, -3.0F, 3.0F, 4.0F, 6.0F, new CubeDeformation(0.01F))
                    .texOffs(0, 77).addBox(0.0F, -5.5F, -3.0F, 3.0F, 6.0F, 6.0F, new CubeDeformation(0.01F))
                    .texOffs(95, 57).addBox(3.0F, -4.5F, -3.0F, 2.0F, 5.0F, 6.0F, new CubeDeformation(0.26F))
                    .texOffs(84, 118).addBox(2.0F, 2.0F, -3.0F, 3.0F, 4.0F, 6.0F, new CubeDeformation(0.01F))
                    .texOffs(62, 77).addBox(0.0F, -5.5F, -3.0F, 3.0F, 6.0F, 6.0F, new CubeDeformation(0.26F)), PartPose.offset(0.0F, 0.0F, 0.0F));

            PartDefinition left_shoulder_pad = left_shoulder.addOrReplaceChild("left_shoulder_pad", CubeListBuilder.create().texOffs(20, 69).addBox(1.8918F, -1.4881F, -4.0F, 6.0F, 3.0F, 8.0F, new CubeDeformation(0.01F))
                    .texOffs(82, 69).addBox(1.8918F, -1.4881F, -4.0F, 6.0F, 3.0F, 8.0F, new CubeDeformation(0.26F)), PartPose.offsetAndRotation(-2.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.2182F));

            PartDefinition right_shoulder = right_arm.addOrReplaceChild("right_shoulder", CubeListBuilder.create().texOffs(33, 57).mirror().addBox(-5.0F, -4.5F, -3.0F, 2.0F, 5.0F, 6.0F, new CubeDeformation(0.01F)).mirror(false)
                    .texOffs(0, 77).mirror().addBox(-3.0F, -5.5F, -3.0F, 3.0F, 6.0F, 6.0F, new CubeDeformation(0.01F)).mirror(false)
                    .texOffs(22, 118).mirror().addBox(-5.0F, 2.0F, -3.0F, 3.0F, 4.0F, 6.0F, new CubeDeformation(0.01F)).mirror(false)
                    .texOffs(95, 57).mirror().addBox(-5.0F, -4.5F, -3.0F, 2.0F, 5.0F, 6.0F, new CubeDeformation(0.26F)).mirror(false)
                    .texOffs(62, 77).mirror().addBox(-3.0F, -5.5F, -3.0F, 3.0F, 6.0F, 6.0F, new CubeDeformation(0.26F)).mirror(false)
                    .texOffs(84, 118).mirror().addBox(-5.0F, 2.0F, -3.0F, 3.0F, 4.0F, 6.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

            PartDefinition right_shoulder_pad = right_shoulder.addOrReplaceChild("right_shoulder_pad", CubeListBuilder.create().texOffs(20, 69).mirror().addBox(-7.6753F, -1.4644F, -4.0F, 6.0F, 3.0F, 8.0F, new CubeDeformation(0.01F)).mirror(false)
                    .texOffs(82, 69).mirror().addBox(-7.6753F, -1.4644F, -4.0F, 6.0F, 3.0F, 8.0F, new CubeDeformation(0.26F)).mirror(false), PartPose.offsetAndRotation(2.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.2182F));

            PartDefinition left_leg = left_legging.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 89).mirror().addBox(-2.4F, -0.5F, -2.5F, 5.0F, 9.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false)
                    .texOffs(62, 89).mirror().addBox(-2.4F, -0.5F, -2.5F, 5.0F, 9.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

            PartDefinition left_thigh_guard_right = left_leg.addOrReplaceChild("left_thigh_guard_right", CubeListBuilder.create().texOffs(18, 80).mirror().addBox(1.3934F, -2.3778F, -3.0F, 3.0F, 6.0F, 6.0F, new CubeDeformation(0.05F)).mirror(false)
                    .texOffs(80, 80).mirror().addBox(1.3934F, -2.3778F, -3.0F, 3.0F, 6.0F, 6.0F, new CubeDeformation(0.3F)).mirror(false), PartPose.offsetAndRotation(0.1F, 2.0F, 0.0F, 0.0F, 0.0F, -1.1345F));

            PartDefinition left_thigh_guard_bottom = left_leg.addOrReplaceChild("left_thigh_guard_bottom", CubeListBuilder.create().texOffs(18, 80).mirror().addBox(-0.2266F, -2.1443F, -3.0F, 3.0F, 6.0F, 6.0F, new CubeDeformation(0.02F)).mirror(false)
                    .texOffs(80, 80).mirror().addBox(-0.2266F, -2.1443F, -3.0F, 3.0F, 6.0F, 6.0F, new CubeDeformation(0.27F)).mirror(false), PartPose.offsetAndRotation(1.1F, 4.0F, 0.0F, 0.0F, 0.0F, -1.1345F));

            PartDefinition left_leg_cloth_l = left_leg.addOrReplaceChild("left_leg_cloth_l", CubeListBuilder.create().texOffs(22, 104).mirror().addBox(-0.5F, -2.0F, -3.0F, 3.0F, 8.0F, 6.0F, new CubeDeformation(0.13F)).mirror(false)
                    .texOffs(84, 104).mirror().addBox(-0.5F, -2.0F, -3.0F, 3.0F, 8.0F, 6.0F, new CubeDeformation(0.38F)).mirror(false), PartPose.offsetAndRotation(-0.9F, 1.0F, 0.0F, 0.0F, 0.0F, -0.3054F));

            PartDefinition right_leg = right_legging.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 89).addBox(-2.6F, -0.5F, -2.5F, 5.0F, 9.0F, 5.0F, new CubeDeformation(0.0F))
                    .texOffs(62, 89).addBox(-2.6F, -0.5F, -2.5F, 5.0F, 9.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

            PartDefinition right_thigh_guard_top = right_leg.addOrReplaceChild("right_thigh_guard_top", CubeListBuilder.create().texOffs(18, 80).addBox(-4.3934F, -2.3778F, -3.0F, 3.0F, 6.0F, 6.0F, new CubeDeformation(0.05F))
                    .texOffs(80, 80).addBox(-4.3934F, -2.3778F, -3.0F, 3.0F, 6.0F, 6.0F, new CubeDeformation(0.3F)), PartPose.offsetAndRotation(-0.1F, 2.0F, 0.0F, 0.0F, 0.0F, 1.1345F));

            PartDefinition right_thigh_guard_bottom = right_leg.addOrReplaceChild("right_thigh_guard_bottom", CubeListBuilder.create().texOffs(18, 80).addBox(-2.7734F, -2.1443F, -3.0F, 3.0F, 6.0F, 6.0F, new CubeDeformation(0.02F))
                    .texOffs(80, 80).addBox(-2.7734F, -2.1443F, -3.0F, 3.0F, 6.0F, 6.0F, new CubeDeformation(0.27F)), PartPose.offsetAndRotation(-1.1F, 4.0F, 0.0F, 0.0F, 0.0F, 1.1345F));

            PartDefinition right_leg_cloth_r = right_leg.addOrReplaceChild("right_leg_cloth_r", CubeListBuilder.create().texOffs(22, 104).addBox(-2.5F, -2.0F, -3.0F, 3.0F, 8.0F, 6.0F, new CubeDeformation(0.13F))
                    .texOffs(84, 104).addBox(-2.5F, -2.0F, -3.0F, 3.0F, 8.0F, 6.0F, new CubeDeformation(0.38F)), PartPose.offsetAndRotation(0.9F, 1.0F, 0.0F, 0.0F, 0.0F, 0.3054F));

            PartDefinition codpiece = leggings.addOrReplaceChild("codpiece", CubeListBuilder.create().texOffs(36, 84).addBox(-4.5F, -14.5F, -3.0F, 9.0F, 2.0F, 6.0F, new CubeDeformation(0.06F))
                    .texOffs(98, 84).addBox(-4.5F, -14.5F, -3.0F, 9.0F, 2.0F, 6.0F, new CubeDeformation(0.31F)), PartPose.offset(0.0F, 24.0F, 0.0F));

            PartDefinition codpiece_cloth = codpiece.addOrReplaceChild("codpiece_cloth", CubeListBuilder.create().texOffs(0, 104).addBox(-2.5F, -17.5F, -3.0F, 5.0F, 11.0F, 6.0F, new CubeDeformation(0.15F))
                    .texOffs(62, 104).addBox(-2.5F, -17.5F, -3.0F, 5.0F, 11.0F, 6.0F, new CubeDeformation(0.4F)), PartPose.offset(0.0F, 0.0F, 0.0F));

            PartDefinition left_boot = left_foot.addOrReplaceChild("left_boot", CubeListBuilder.create().texOffs(20, 92).addBox(-2.9F, 8.0F, -3.0F, 6.0F, 5.0F, 6.0F, new CubeDeformation(0.01F))
                    .texOffs(82, 92).addBox(-2.9F, 8.0F, -3.0F, 6.0F, 5.0F, 6.0F, new CubeDeformation(0.26F)), PartPose.offset(0.0F, 0.0F, 0.0F));

            PartDefinition right_boot = right_foot.addOrReplaceChild("right_boot", CubeListBuilder.create().texOffs(20, 92).mirror().addBox(-3.1F, 8.0F, -3.0F, 6.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false)
                    .texOffs(82, 92).mirror().addBox(-3.1F, 8.0F, -3.0F, 6.0F, 5.0F, 6.0F, new CubeDeformation(0.25F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));
            return LayerDefinition.create(mesh, 128, 128);
        });
    }
}