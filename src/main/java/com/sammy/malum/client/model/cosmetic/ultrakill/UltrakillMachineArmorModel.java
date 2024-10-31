package com.sammy.malum.client.model.cosmetic.ultrakill;
// Made with Blockbench 3.9.2
// Exported for Minecraft version 1.15 - 1.16 with MCP mappings
// Paste this class into your mod and generate all required imports


import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.sammy.malum.MalumMod;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.EquipmentSlot;
import team.lodestar.lodestone.helpers.RenderHelper;
import team.lodestar.lodestone.systems.model.LodestoneArmorModel;

public class UltrakillMachineArmorModel extends LodestoneArmorModel {
    public static ModelLayerLocation LAYER = new ModelLayerLocation(MalumMod.malumPath("ultrakill_machine"), "main");

    public ModelPart wings;
    public ModelPart wingsFullBright;
    public ModelPart helmetFullBright;

    public UltrakillMachineArmorModel(ModelPart root) {
        super(root);
        this.wings = root.getChild("wings");
        this.wingsFullBright = root.getChild("wings_full_bright");
        this.helmetFullBright = root.getChild("helmet_full_bright");
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
        super.renderToBuffer(poseStack, buffer, packedLight, packedOverlay, color);
        if (!this.young) {
            if (slot == EquipmentSlot.CHEST) {
                wingsFullBright.render(poseStack, buffer, RenderHelper.FULL_BRIGHT, packedOverlay, color);
            }
            if (slot == EquipmentSlot.HEAD) {
                helmetFullBright.render(poseStack, buffer, RenderHelper.FULL_BRIGHT, packedOverlay, color);
            }
        }
    }

    @Override
    protected Iterable<ModelPart> bodyParts() {
        if (this.slot == EquipmentSlot.CHEST) {
            return ImmutableList.of(this.body, this.leftArm, this.rightArm, this.wings);
        } else if (this.slot == EquipmentSlot.LEGS) {
            return ImmutableList.of(this.leftLegging, this.rightLegging, this.leggings);
        } else {
            return this.slot == EquipmentSlot.FEET ? ImmutableList.of(this.leftFoot, this.rightFoot) : ImmutableList.of();
        }
    }

    @Override
    public void copyFromDefault(HumanoidModel model) {
        super.copyFromDefault(model);
        wings.copyFrom(model.body);
        wingsFullBright.copyFrom(model.body);
        helmetFullBright.copyFrom(model.head);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = HumanoidModel.createMesh(new CubeDeformation(0), 0);
        PartDefinition root = createHumanoidAlias(mesh);
        PartDefinition wings = root.addOrReplaceChild("wings", new CubeListBuilder(), PartPose.ZERO);
        PartDefinition wingsFullBright = root.addOrReplaceChild("wings_full_bright", new CubeListBuilder(), PartPose.ZERO);
        PartDefinition helmetFullBright = root.addOrReplaceChild("helmet_full_bright", new CubeListBuilder(), PartPose.ZERO);

        PartDefinition body = root.getChild("body");
        PartDefinition torso = body.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(24, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.95F))
                .texOffs(0, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.75F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition right_legging = root.getChild("right_legging");
        PartDefinition right_leg = right_legging.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 48).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.45F)).mirror(false), PartPose.offset(0, 0, 0));

        PartDefinition right_foot = root.getChild("right_foot");
        PartDefinition right_boot = right_foot.addOrReplaceChild("right_boot", CubeListBuilder.create().texOffs(32, 44).addBox(-3.0F, 7.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.25F))
                .texOffs(32, 32).addBox(-3.0F, 7.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition right_arm = root.getChild("right_arm");
        PartDefinition right_shoulder = right_arm.addOrReplaceChild("right_shoulder", CubeListBuilder.create().texOffs(0, 32).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.5F))
                .texOffs(16, 32).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.7F)), PartPose.offset(0, 0, 0.0F));

        PartDefinition left_legging = root.getChild("left_legging");
        PartDefinition left_leg = left_legging.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 48).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.45F)), PartPose.offset(0, 0, 0.0F));

        PartDefinition left_foot = root.getChild("left_foot");
        PartDefinition left_boot = left_foot.addOrReplaceChild("left_boot", CubeListBuilder.create().texOffs(32, 44).mirror().addBox(-3.0F, 7.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.26F)).mirror(false)
                .texOffs(32, 32).mirror().addBox(-3.0F, 7.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offset(0, 0.0F, 0.0F));

        PartDefinition left_arm = root.getChild("left_arm");
        PartDefinition left_shoulder = left_arm.addOrReplaceChild("left_shoulder", CubeListBuilder.create().texOffs(0, 32).mirror().addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.5F)).mirror(false)
                .texOffs(16, 32).mirror().addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.7F)).mirror(false), PartPose.offset(0, 0, 0.0F));

        PartDefinition head = root.getChild("head");
        PartDefinition helmet = head.addOrReplaceChild("helmet", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.5F))
                .texOffs(32, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.95F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition helmet_full_bright = helmetFullBright.addOrReplaceChild("helmet_full_bright", CubeListBuilder.create().texOffs(10, 10).addBox(-2.0F, -6.0F, -4.25F, 4.0F, 4.0F, 0.0F, new CubeDeformation(0.251F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition l1 = wings.addOrReplaceChild("l1", CubeListBuilder.create().texOffs(16, 48).addBox(3.0F, -1.0F, -1.0F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(24, 48).mirror().addBox(3.0F, -1.0F, -1.0F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.25F)).mirror(false)
                .texOffs(16, 51).addBox(6.0F, -4.0F, -1.0F, 3.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(24, 51).addBox(6.0F, -4.25F, -1.0F, 3.0F, 5.0F, 1.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.0F, 5F, 5, 0.0F, -0.1745F, -0.6981F));

        PartDefinition l2 = wings.addOrReplaceChild("l2", CubeListBuilder.create().texOffs(16, 48).addBox(3.0F, -1.0F, -1.0F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(24, 48).mirror().addBox(3.0F, -1.0F, -1.0F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.25F)).mirror(false)
                .texOffs(16, 51).addBox(6.0F, -4.0F, -1.0F, 3.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(24, 51).addBox(6.0F, -4.25F, -1.0F, 3.0F, 5.0F, 1.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.0F, 5F, 5, 0.0F, -0.1309F, 0.0F));

        PartDefinition l3 = wings.addOrReplaceChild("l3", CubeListBuilder.create().texOffs(16, 48).addBox(3.0F, -1.0F, -1.0F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(24, 48).mirror().addBox(3.0F, -1.0F, -1.0F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.25F)).mirror(false)
                .texOffs(16, 51).addBox(6.0F, -4.0F, -1.0F, 3.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(24, 51).addBox(6.0F, -4.25F, -1.0F, 3.0F, 5.0F, 1.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.0F, 5F, 5, 0.0F, -0.0873F, 0.6981F));

        PartDefinition r1 = wings.addOrReplaceChild("r1", CubeListBuilder.create().texOffs(16, 48).mirror().addBox(-6.0F, -1.0F, -1.0F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(24, 48).addBox(-6.0F, -1.0F, -1.0F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.25F))
                .texOffs(16, 51).mirror().addBox(-9.0F, -4.0F, -1.0F, 3.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(24, 51).mirror().addBox(-9.0F, -4.25F, -1.0F, 3.0F, 5.0F, 1.0F, new CubeDeformation(0.25F)).mirror(false), PartPose.offsetAndRotation(0.0F, 5F, 5, 0.0F, 0.1745F, 0.6981F));

        PartDefinition r2 = wings.addOrReplaceChild("r2", CubeListBuilder.create().texOffs(16, 48).mirror().addBox(-6.0F, -1.0F, -1.0F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(24, 48).addBox(-6.0F, -1.0F, -1.0F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.25F))
                .texOffs(16, 51).mirror().addBox(-9.0F, -4.0F, -1.0F, 3.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(24, 51).mirror().addBox(-9.0F, -4.25F, -1.0F, 3.0F, 5.0F, 1.0F, new CubeDeformation(0.25F)).mirror(false), PartPose.offsetAndRotation(0.0F, 5F, 5, 0.0F, 0.1309F, 0.0F));

        PartDefinition r3 = wings.addOrReplaceChild("r3", CubeListBuilder.create().texOffs(16, 48).mirror().addBox(-6.0F, -1.0F, -1.0F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(24, 48).addBox(-6.0F, -1.0F, -1.0F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.25F))
                .texOffs(16, 51).mirror().addBox(-9.0F, -4.0F, -1.0F, 3.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(24, 51).mirror().addBox(-9.0F, -4.25F, -1.0F, 3.0F, 5.0F, 1.0F, new CubeDeformation(0.25F)).mirror(false), PartPose.offsetAndRotation(0.0F, 5F, 5, 0.0F, 0.0873F, -0.6981F));

        PartDefinition l4 = wingsFullBright.addOrReplaceChild("l4", CubeListBuilder.create().texOffs(16, 57).mirror().addBox(6.0F, -3.5F, -0.5F, 12.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 5, 5, 0.0F, -0.1745F, -0.6981F));

        PartDefinition l5 = wingsFullBright.addOrReplaceChild("l5", CubeListBuilder.create().texOffs(16, 57).mirror().addBox(7.0F, -3.5F, -0.5F, 12.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 5, 5, 0.0F, -0.1309F, 0.0F));

        PartDefinition l6 = wingsFullBright.addOrReplaceChild("l6", CubeListBuilder.create().texOffs(16, 57).mirror().addBox(6.0F, -3.5F, -0.5F, 12.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 5, 5, 0.0F, -0.0873F, 0.6981F));

        PartDefinition r4 = wingsFullBright.addOrReplaceChild("r4", CubeListBuilder.create().texOffs(16, 57).addBox(-19.0F, -3.5F, -0.5F, 12.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 5, 5, 0.0F, 0.1745F, 0.6981F));

        PartDefinition r5 = wingsFullBright.addOrReplaceChild("r5", CubeListBuilder.create().texOffs(16, 57).addBox(-19.0F, -3.5F, -0.5F, 12.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 5, 5, 0.0F, 0.1309F, 0.0F));

        PartDefinition r6 = wingsFullBright.addOrReplaceChild("r6", CubeListBuilder.create().texOffs(16, 57).addBox(-19.0F, -3.5F, -0.5F, 12.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 5, 5, 0.0F, 0.0873F, -0.6981F));

        return LayerDefinition.create(mesh, 64, 64);
    }
}