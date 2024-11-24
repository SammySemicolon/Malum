package com.sammy.malum.client.model;
// Made with Blockbench 3.9.2
// Exported for Minecraft version 1.15 - 1.16 with MCP mappings
// Paste this class into your mod and generate all required imports


import com.google.common.collect.ImmutableList;
import com.sammy.malum.MalumMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import team.lodestar.lodestone.systems.model.LodestoneArmorModel;

public class SoulHunterArmorModel extends LodestoneArmorModel {
    public static ModelLayerLocation LAYER = new ModelLayerLocation(MalumMod.malumPath("soul_hunter_armor"), "main");

    public ModelPart cape;
    public ModelPart lowered_hood;

    public SoulHunterArmorModel(ModelPart root) {
        super(root);
        this.cape = root.getChild("cape");
        this.lowered_hood = root.getChild("lowered_hood");
    }

    @Override
    protected Iterable<ModelPart> bodyParts() {
        if (this.slot == EquipmentSlot.CHEST) {
            return ImmutableList.of(this.body, this.leftArm, this.rightArm, this.cape, this.lowered_hood);
        } else if (this.slot == EquipmentSlot.LEGS) {
            return ImmutableList.of(this.leftLegging, this.rightLegging, this.leggings);
        } else {
            return this.slot == EquipmentSlot.FEET ? ImmutableList.of(this.leftFoot, this.rightFoot) : ImmutableList.of();
        }
    }

    @Override
    public void copyFromDefault(HumanoidModel model) {
        super.copyFromDefault(model);
        cape.copyFrom(model.body);
        lowered_hood.copyFrom(model.body);
    }

    @Override
    public void setupAnim(LivingEntity pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        float pPartialTicks = Minecraft.getInstance().timer.getGameTimeDeltaTicks();
        lowered_hood.visible = pEntity.getItemBySlot(EquipmentSlot.HEAD).isEmpty();
        if (pEntity instanceof AbstractClientPlayer clientPlayer) {
            double d0 = Mth.lerp(pPartialTicks, clientPlayer.xCloakO, clientPlayer.xCloak) - Mth.lerp(pPartialTicks, pEntity.xo, pEntity.getX());
            double d1 = Mth.lerp(pPartialTicks, clientPlayer.yCloakO, clientPlayer.yCloak) - Mth.lerp(pPartialTicks, pEntity.yo, pEntity.getY());
            double d2 = Mth.lerp(pPartialTicks, clientPlayer.zCloakO, clientPlayer.zCloak) - Mth.lerp(pPartialTicks, pEntity.zo, pEntity.getZ());
            float f = pEntity.yBodyRotO + (pEntity.yBodyRot - pEntity.yBodyRotO);
            double d3 = Mth.sin(f * ((float) Math.PI / 180F));
            double d4 = (-Mth.cos(f * ((float) Math.PI / 180F)));
            float f1 = (float) d1 * 10.0F;
            f1 = Mth.clamp(f1, -6.0F, 16.0F);
            float f2 = (float) (d0 * d3 + d2 * d4) * 65.0F;
            f2 = Mth.clamp(f2, 0.0F, 75.0F);
            float f3 = (float) (d0 * d4 - d2 * d3) * 100.0F;
            f3 = Mth.clamp(f3, -20.0F, 20.0F);
            if (f2 < 0.0F) {
                f2 = 0.0F;
            }
            float f4 = Mth.lerp(pPartialTicks, clientPlayer.oBob, clientPlayer.bob);
            f1 += Mth.sin(Mth.lerp(pPartialTicks, pEntity.walkDistO, pEntity.walkDist) * 6.0F) * 32.0F * f4;
            if (pEntity.isCrouching()) {
                f1 += 25.0F;
            }
            float x = (float) Math.toRadians(6.0F + f2 / 2.0F + f1);
            float y = (float) Math.toRadians(f3 / 2.0F);
            float z = (float) Math.toRadians(f3 / 2.0F);
            cape.xRot = x;
            cape.yRot = y;
            cape.zRot = z;
            lowered_hood.xRot = x / 3f;
            lowered_hood.yRot = y / 3f;
            lowered_hood.zRot = z / 3f;
        } else {
            cape.xRot = 0;
            cape.yRot = 0;
            cape.zRot = 0;
            lowered_hood.xRot = 0;
            lowered_hood.yRot = 0;
            lowered_hood.zRot = 0;
        }
        super.setupAnim(pEntity, pLimbSwing, pLimbSwingAmount, pAgeInTicks, pNetHeadYaw, pHeadPitch);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = HumanoidModel.createMesh(new CubeDeformation(0), 0);
        PartDefinition root = createHumanoidAlias(mesh);
        PartDefinition cape = root.addOrReplaceChild("cape", new CubeListBuilder(), PartPose.ZERO);
        PartDefinition lowered_hood = root.addOrReplaceChild("lowered_hood", new CubeListBuilder(), PartPose.ZERO);

        PartDefinition body = root.getChild("body");
        PartDefinition torso = body.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(0, 18).addBox(-4.5F, -0.5F, -2.5F, 9.0F, 10.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(28, 18).addBox(-4.5F, -0.5F, -2.5F, 9.0F, 10.0F, 5.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition left_stripe = torso.addOrReplaceChild("left_stripe", CubeListBuilder.create().texOffs(0, 33).addBox(-1.9074F, 0.0F, -0.6014F, 3.0F, 10.0F, 1.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(-2.5F, -0.5F, -2.5F, -0.0892F, 0.3487F, -0.0061F));
        PartDefinition right_stripe = torso.addOrReplaceChild("right_stripe", CubeListBuilder.create().texOffs(0, 33).mirror().addBox(-1.0926F, 0.0F, -0.6014F, 3.0F, 10.0F, 1.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offsetAndRotation(2.5F, -0.5F, -2.5F, -0.0892F, -0.3487F, 0.0061F));

        PartDefinition leggings = root.getChild("leggings");
        PartDefinition codpiece = leggings.addOrReplaceChild("codpiece", CubeListBuilder.create().texOffs(0, 84).addBox(-5.0F, -14.5F, -3.5F, 10.0F, 3.0F, 7.0F, new CubeDeformation(0.01F))
                .texOffs(34, 84).addBox(-5.0F, -14.5F, -3.5F, 10.0F, 3.0F, 7.0F, new CubeDeformation(0.26F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition right_legging = root.getChild("right_legging");
        PartDefinition right_leg = right_legging.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(39, 103).addBox(-2.5F, -0.5F, -2.5F, 5.0F, 8.0F, 5.0F, new CubeDeformation(0.01F)), PartPose.offset(0, 0, 0.0F));
        PartDefinition right_leg_robe = right_legging.addOrReplaceChild("right_leg_robe", CubeListBuilder.create().texOffs(0, 118).addBox(-1.0F, 3.0F, -5.0F, 1.0F, 3.0F, 7.0F, new CubeDeformation(0.25F))
                .texOffs(0, 108).addBox(-1.0F, 3.0F, -5.0F, 1.0F, 3.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(22, 94).addBox(0.0F, -1.0F, -5.0F, 4.0F, 7.0F, 7.0F, new CubeDeformation(0.25F))
                .texOffs(0, 94).addBox(0.0F, -1.0F, -5.0F, 4.0F, 7.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, -0.5F, 1.5F, 0.0F, 0.0F, 0.1745F));

        PartDefinition right_foot = root.getChild("right_foot");
        PartDefinition right_boot = right_foot.addOrReplaceChild("right_boot", CubeListBuilder.create().texOffs(16, 116).addBox(-3.0F, 7.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(40, 116).addBox(-3.0F, 7.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition right_arm = root.getChild("right_arm");
        PartDefinition right_shoulder = right_arm.addOrReplaceChild("right_shoulder", CubeListBuilder.create().texOffs(0, 44).mirror().addBox(-4.5F, 5.5F, -2.5F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.01F)).mirror(false)
                .texOffs(20, 44).mirror().addBox(-4.5F, 5.5F, -2.5F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.26F)).mirror(false)
                .texOffs(40, 46).mirror().addBox(-5.5F, 4.5F, -1.5F, 2.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(50, 46).mirror().addBox(-5.5F, 4.5F, -1.5F, 2.0F, 5.0F, 3.0F, new CubeDeformation(0.25F)).mirror(false)
                .texOffs(8, 33).mirror().addBox(-4.5F, -2.5F, -2.5F, 5.0F, 6.0F, 5.0F, new CubeDeformation(0.01F)).mirror(false)
                .texOffs(28, 33).mirror().addBox(-4.5F, -2.5F, -2.5F, 5.0F, 6.0F, 5.0F, new CubeDeformation(0.26F)).mirror(false), PartPose.offset(1, 0, 0.0F));

        PartDefinition left_legging = root.getChild("left_legging");
        PartDefinition left_leg = left_legging.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(39, 103).mirror().addBox(-2.5F, -0.5F, -2.5F, 5.0F, 8.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0, 0, 0.0F));
        PartDefinition left_leg_robe = left_legging.addOrReplaceChild("left_leg_robe", CubeListBuilder.create().texOffs(0, 118).mirror().addBox(0.0F, 3.0F, -5.0F, 1.0F, 3.0F, 7.0F, new CubeDeformation(0.25F)).mirror(false)
                .texOffs(0, 108).mirror().addBox(0.0F, 3.0F, -5.0F, 1.0F, 3.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(22, 94).mirror().addBox(-4.0F, -1.0F, -5.0F, 4.0F, 7.0F, 7.0F, new CubeDeformation(0.25F)).mirror(false)
                .texOffs(0, 94).mirror().addBox(-4.0F, -1.0F, -5.0F, 4.0F, 7.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(3.0F, -0.5F, 1.5F, 0.0F, 0.0F, -0.1745F));

        PartDefinition left_foot = root.getChild("left_foot");
        PartDefinition left_boot = left_foot.addOrReplaceChild("left_boot", CubeListBuilder.create().texOffs(16, 116).mirror().addBox(-3.0F, 7.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.01F)).mirror(false)
                .texOffs(40, 116).mirror().addBox(-3.0F, 7.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.25F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition left_arm = root.getChild("left_arm");
        PartDefinition left_shoulder = left_arm.addOrReplaceChild("left_shoulder", CubeListBuilder.create().texOffs(8, 33).addBox(-0.5F, -2.5F, -2.5F, 5.0F, 6.0F, 5.0F, new CubeDeformation(0.01F))
                .texOffs(28, 33).addBox(-0.5F, -2.5F, -2.5F, 5.0F, 6.0F, 5.0F, new CubeDeformation(0.26F))
                .texOffs(0, 44).addBox(-0.5F, 5.5F, -2.5F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.01F))
                .texOffs(20, 44).addBox(-0.5F, 5.5F, -2.5F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.26F))
                .texOffs(40, 46).addBox(3.5F, 4.5F, -1.5F, 2.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(50, 46).addBox(3.5F, 4.5F, -1.5F, 2.0F, 5.0F, 3.0F, new CubeDeformation(0.25F)), PartPose.offset(-1, 0, 0.0F));

        PartDefinition head = root.getChild("head");
        PartDefinition helmet = head.addOrReplaceChild("helmet", CubeListBuilder.create().texOffs(0, 0).addBox(-4.5F, -8.5F, -4.5F, 9.0F, 9.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(36, 0).addBox(-4.5F, -8.5F, -4.5F, 9.0F, 9.0F, 9.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition left_hood_bit = head.addOrReplaceChild("left_hood_bit", CubeListBuilder.create().texOffs(53, 59).mirror().addBox(-2.0F, -5.0F, -4.0F, 2.0F, 5.0F, 7.0F, new CubeDeformation(0.25F)).mirror(false)
                .texOffs(53, 47).mirror().addBox(-2.0F, -5.0F, -4.0F, 2.0F, 5.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-4.5F, 0.5F, 0.5F, 0.0F, 0.0F, 0.3927F));
        PartDefinition back_hood_bit = head.addOrReplaceChild("back_hood_bit", CubeListBuilder.create().texOffs(48, 33).addBox(-3.5F, 0.0F, 0.0F, 7.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -8.5F, 4.5F, -0.7854F, 0.0F, 0.0F));
        PartDefinition right_hood_bit = head.addOrReplaceChild("right_hood_bit", CubeListBuilder.create().texOffs(53, 59).addBox(0.0F, -5.0F, -4.0F, 2.0F, 5.0F, 7.0F, new CubeDeformation(0.25F))
                .texOffs(53, 47).addBox(0.0F, -5.0F, -4.0F, 2.0F, 5.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.5F, 0.5F, 0.5F, 0.0F, 0.0F, -0.3927F));

        PartDefinition cape_top = cape.addOrReplaceChild("cape_top", CubeListBuilder.create().texOffs(0, 54).addBox(-6.5213F, -0.1479F, -1.0812F, 11.0F, 8.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(24, 54).addBox(-6.5213F, -0.1479F, -1.0812F, 11.0F, 8.0F, 1.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(1.0F, -0.5F, 3.5F, 0.0839F, -0.02F, 0.0126F));

        PartDefinition cape_middle = cape_top.addOrReplaceChild("cape_middle", CubeListBuilder.create().texOffs(24, 62).addBox(-6.5213F, -0.2878F, -1.0526F, 11.0F, 7.0F, 1.0F, new CubeDeformation(0.25F))
                .texOffs(0, 62).addBox(-6.5213F, -0.2878F, -1.0526F, 11.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 8.0F, 0.0F, 0.1309F, 0.0F, 0.0F));

        PartDefinition cape_bottom = cape_middle.addOrReplaceChild("cape_bottom", CubeListBuilder.create().texOffs(24, 77).addBox(-6.5213F, -0.9301F, 2.1414F, 3.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 77).addBox(-3.5213F, -0.9301F, 2.1414F, 5.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(24, 77).mirror().addBox(1.4787F, -0.9301F, 2.1414F, 3.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(32, 77).mirror().addBox(1.4787F, -0.9301F, 2.1414F, 3.0F, 4.0F, 1.0F, new CubeDeformation(0.25F)).mirror(false)
                .texOffs(12, 77).addBox(-3.5213F, -0.9301F, 2.1414F, 5.0F, 6.0F, 1.0F, new CubeDeformation(0.25F))
                .texOffs(32, 77).addBox(-6.5213F, -0.9301F, 2.1414F, 3.0F, 4.0F, 1.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.0F, 8.0F, -3.0F, 0.1745F, 0.0F, 0.0F));

        PartDefinition hood = lowered_hood.addOrReplaceChild("hood", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition overhang = hood.addOrReplaceChild("overhang", CubeListBuilder.create().texOffs(26, 70).addBox(-4.98F, -0.5028F, -2.1358F, 8.0F, 2.0F, 5.0F, new CubeDeformation(0.25F))
                .texOffs(0, 70).addBox(-4.98F, -0.5028F, -2.1358F, 8.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -0.5F, 3.5F, -0.6109F, 0.0F, 0.0F));

        return LayerDefinition.create(mesh, 128, 128);
    }
}