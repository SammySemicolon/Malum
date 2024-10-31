package com.sammy.malum.client.model;
// Made with Blockbench 3.9.2
// Exported for Minecraft version 1.15 - 1.16 with MCP mappings
// Paste this class into your mod and generate all required imports


import com.google.common.collect.*;
import com.mojang.blaze3d.vertex.*;
import com.sammy.malum.*;
import com.sammy.malum.common.item.curiosities.curios.runes.*;
import com.sammy.malum.core.systems.spirit.*;
import com.sammy.malum.registry.client.*;
import net.minecraft.client.*;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.*;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.phys.*;
import team.lodestar.lodestone.handlers.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.registry.client.*;
import team.lodestar.lodestone.systems.easing.*;
import team.lodestar.lodestone.systems.model.*;
import team.lodestar.lodestone.systems.rendering.rendeertype.*;

import java.awt.*;
import java.util.List;
import java.util.*;
import java.util.stream.*;

public class MalignantStrongholdArmorModel extends LodestoneArmorModel {
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(MalumMod.malumPath("malignant_lead_armor"), "main");
    public static final RenderTypeToken GLOW_TEXTURE = RenderTypeToken.createToken(MalumMod.malumPath("textures/armor/malignant_stronghold_glow.png"));
    private final ModelPart right_arm_glow;
    private final ModelPart left_arm_glow;
    private final ModelPart right_boot_glow;
    private final ModelPart left_boot_glow;
    private final ModelPart helmet_glow;
    private final ModelPart torso_glow;

    private final List<MalumSpiritType> activeGlows = new ArrayList<>();

    public MalignantStrongholdArmorModel(ModelPart root) {
        super(root);
        this.right_arm_glow = root.getChild("right_arm_glow");
        this.left_arm_glow = root.getChild("left_arm_glow");
        this.right_boot_glow = root.getChild("right_boot_glow");
        this.left_boot_glow = root.getChild("left_boot_glow");
        this.helmet_glow = root.getChild("helmet_glow");
        this.torso_glow = root.getChild("torso_glow");
    }

    protected List<ModelPart> getGlowingParts() {
        if (this.slot == EquipmentSlot.CHEST) {
            return ImmutableList.of(torso_glow, left_arm_glow, right_arm_glow);
        } else if (this.slot == EquipmentSlot.HEAD) {
            return ImmutableList.of(helmet_glow);
        } else {
            return this.slot == EquipmentSlot.FEET ? ImmutableList.of(left_boot_glow, right_boot_glow) : ImmutableList.of();
        }
    }

    public void updateGlow(List<AbstractRuneCurioItem> runes) {
        activeGlows.clear();
        activeGlows.addAll(runes.stream().map(r -> r.spiritType).toList());
    }

    public static Color colorLerp(Easing easing, float pct, float[] hsv1, float[] hsv2) {
        pct = Mth.clamp(pct, 0, 1);
        float ease = easing.ease(pct, 0, 1, 1);
        float h = Mth.rotLerp(ease, 360f * hsv1[0], 360f * hsv2[0]) / 360f;
        float s = Mth.lerp(ease, hsv1[1], hsv2[1]);
        float v = Mth.lerp(ease, hsv1[2], hsv2[2]);
        int packed = Color.HSBtoRGB(h, s, v);
        float r = FastColor.ARGB32.red(packed) / 255.0f;
        float g = FastColor.ARGB32.green(packed) / 255.0f;
        float b = FastColor.ARGB32.blue(packed) / 255.0f;
        return new Color(r, g, b);
    }

    public static Color multicolorLerp(Easing easing, float pct, List<Color> colors) {
        pct = Mth.clamp(pct, 0, 1);
        int colorCount = colors.size() - 1;
        float lerp = easing.ease(pct, 0, 1, 1);
        float colorIndex = colorCount * lerp;
        int index = (int) Mth.clamp(colorIndex, 0, colorCount);
        Color color = colors.get(index);
        Color nextColor = index == colorCount ? color : colors.get(index + 1);
        float[] hsv1 = new float[3];
        float[] hsv2 = new float[3];
        Color.RGBtoHSB(Math.min(255, color.getRed()), Math.min(255, color.getGreen()), Math.min(255, color.getBlue()), hsv1);
        Color.RGBtoHSB(Math.min(255, nextColor.getRed()), Math.min(255, nextColor.getGreen()), Math.min(255, nextColor.getBlue()), hsv2);
        return colorLerp(easing, colorIndex - (int) (colorIndex), hsv1, hsv2);
    }


    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
        super.renderToBuffer(poseStack, buffer, packedLight, packedOverlay, color);
//        if (!this.young && !activeGlows.isEmpty()) {
//            final List<ModelPart> glowingParts = getGlowingParts();
//            if (glowingParts.isEmpty()) {
//                return;
//            }
//            poseStack.pushPose();
//
//            float gameTime = Minecraft.getInstance().level.getGameTime() + Minecraft.getInstance().getPartialTick();
//            Color primaryColor = activeGlows.get(0).getPrimaryColor();
//            Color secondaryColor = activeGlows.get(0).getSecondaryColor();
//            float colorCoefficient = activeGlows.get(0).getColorCoefficient();
//            if (activeGlows.size() > 1) {
//                activeGlows.add(activeGlows.get(0));
//                final float loopTime = 100 * activeGlows.size();
//                final float pct = (gameTime % loopTime) / loopTime;
//                final float relative = pct * activeGlows.size();
//                primaryColor = multicolorLerp(Easing.LINEAR, pct, activeGlows.stream().map(MalumSpiritType::getPrimaryColor).collect(Collectors.toList()));
//                secondaryColor = multicolorLerp(Easing.LINEAR, pct, activeGlows.stream().map(MalumSpiritType::getSecondaryColor).collect(Collectors.toList()));
//                colorCoefficient = activeGlows.size() == 1 ?
//                        activeGlows.get(0).getColorCoefficient() :
//                        Mth.lerp(relative % activeGlows.size(),
//                                activeGlows.get((int)relative).getColorCoefficient(),
//                                activeGlows.get(Math.min((int)relative+1, activeGlows.size()-1)).getColorCoefficient());
//            }
//
//            final VertexConsumer transparent = RenderHandler.DELAYED_RENDER.getTarget().getBuffer(LodestoneRenderTypes.TRANSPARENT_TEXTURE.applyAndCache(GLOW_TEXTURE));
//            final VertexConsumer additive = RenderHandler.DELAYED_RENDER.getTarget().getBuffer(RenderTypeRegistry.MALIGNANT_GLOW.applyAndCache(GLOW_TEXTURE));
//            float distance = 0.06f;
//            float alpha = 0.25f;
//            int time = 320;
//            for (ModelPart glowingPart : glowingParts) {
//                glowingPart.render(pPoseStack, transparent, pPackedLight, pPackedOverlay, primaryColor.getRed()/255f, primaryColor.getGreen()/255f, primaryColor.getBlue()/255f, 0.6f);
//            }
//            for (int i = 0; i < 8; i++) {
//                double angle = i / 4f * (Math.PI * 2);
//                angle += ((gameTime % time) / time) * (Math.PI * 2);
//                for (ModelPart glowingPart : glowingParts) {
//                    final double sin = Math.sin(angle);
//                    Color color = ColorHelper.colorLerp(Easing.SINE_IN, Math.min(1, i/2f/colorCoefficient), secondaryColor, primaryColor);
//                    float yaw = glowingPart.yRot;
//                    float pitch = -glowingPart.xRot;
//                    if (glowingPart.equals(helmet_glow)) {
//                        yaw += 1.57f;
//                    }
//                    Vec3 forward = new Vec3(-Math.cos(yaw), 0, Math.sin(yaw));
//                    if (!glowingPart.equals(helmet_glow)) {
//                        Vec3 direction = new Vec3(Math.cos(yaw) * Math.cos(pitch), Math.sin(yaw) * Math.cos(pitch), Math.sin(pitch));
//                        forward = forward.cross(direction).add(0, 1, 0);
//                    }
//                    Vec3 offset = forward.xRot(pitch).normalize().scale(distance * sin);
//                    pPoseStack.translate(offset.x, offset.y, offset.z);
//                    glowingPart.render(pPoseStack, additive, RenderHelper.FULL_BRIGHT, pPackedOverlay, color.getRed()/255f, color.getGreen()/255f, color.getBlue()/255f, alpha);
//                    pPoseStack.translate(-offset.x, -offset.y, -offset.z);
//                }
//                if (i == 3) {
//                    distance *= 2.5f;
//                    alpha *= 0.35f;
//                }
//            }
//            pPoseStack.popPose();
//        }
    }


    @Override
    public void copyFromDefault(HumanoidModel model) {
        super.copyFromDefault(model);
        left_arm_glow.copyFrom(model.leftArm);
        right_arm_glow.copyFrom(model.rightArm);
        left_boot_glow.copyFrom(model.leftLeg);
        right_boot_glow.copyFrom(model.rightLeg);
        helmet_glow.copyFrom(model.head);
        torso_glow.copyFrom(model.body);
    }


    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = HumanoidModel.createMesh(new CubeDeformation(0), 0);
        PartDefinition root = createHumanoidAlias(mesh);
        PartDefinition right_arm_glow = root.addOrReplaceChild("right_arm_glow", new CubeListBuilder(), PartPose.ZERO);
        PartDefinition left_arm_glow = root.addOrReplaceChild("left_arm_glow", new CubeListBuilder(), PartPose.ZERO);
        PartDefinition right_foot_glow = root.addOrReplaceChild("right_boot_glow", new CubeListBuilder(), PartPose.ZERO);
        PartDefinition left_foot_glow = root.addOrReplaceChild("left_boot_glow", new CubeListBuilder(), PartPose.ZERO);
        PartDefinition helmet_glow = root.addOrReplaceChild("helmet_glow", new CubeListBuilder(), PartPose.ZERO);
        PartDefinition torso_glow = root.addOrReplaceChild("torso_glow", new CubeListBuilder(), PartPose.ZERO);

        PartDefinition body = root.getChild("body");
        PartDefinition leggings = root.getChild("leggings");
        PartDefinition right_legging = root.getChild("right_legging");
        PartDefinition left_legging = root.getChild("left_legging");
        PartDefinition right_foot = root.getChild("right_foot");
        PartDefinition left_foot = root.getChild("left_foot");
        PartDefinition right_arm = root.getChild("right_arm");
        PartDefinition left_arm = root.getChild("left_arm");
        PartDefinition head = root.getChild("head");

        PartDefinition front_glow = torso_glow.addOrReplaceChild("front_glow", CubeListBuilder.create().texOffs(105, 4).addBox(-1.5F, 1.5F, -5.9F, 3.0F, 3.0F, 2.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.2618F, 0.0F, 0.0F));

        PartDefinition back_glow = torso_glow.addOrReplaceChild("back_glow", CubeListBuilder.create().texOffs(95, 4).addBox(-1.5F, 3.5F, 2.0F, 3.0F, 3.0F, 2.0F, new CubeDeformation(-0.25F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition helmet_glow_left = helmet_glow.addOrReplaceChild("helmet_glow_left", CubeListBuilder.create().texOffs(106, 0).mirror().addBox(4.6846F, -10.0F, -1.2529F, 2.0F, 5.0F, 9.0F, new CubeDeformation(-0.5F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0873F, 0.0F));

        PartDefinition helmet_glow_right = helmet_glow.addOrReplaceChild("helmet_glow_right", CubeListBuilder.create().texOffs(106, 0).mirror().addBox(-6.6846F, -10.0F, -1.2529F, 2.0F, 5.0F, 9.0F, new CubeDeformation(-0.5F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.0873F, 0.0F));

        PartDefinition helmet = head.addOrReplaceChild("helmet", CubeListBuilder.create().texOffs(0, 34).addBox(-4.5F, -8.0F, -5.0F, 3.0F, 3.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(6, 40).addBox(-5.5F, -8.0F, -5.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(26, 34).addBox(-4.5F, -8.0F, -5.0F, 3.0F, 3.0F, 10.0F, new CubeDeformation(0.25F))
                .texOffs(0, 47).addBox(-4.5F, -5.0F, 1.0F, 9.0F, 5.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(26, 47).addBox(-4.5F, -5.0F, 1.0F, 9.0F, 5.0F, 4.0F, new CubeDeformation(0.25F))
                .texOffs(0, 34).mirror().addBox(1.5F, -8.0F, -5.0F, 3.0F, 3.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(6, 40).mirror().addBox(4.5F, -8.0F, -5.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(26, 34).mirror().addBox(1.5F, -8.0F, -5.0F, 3.0F, 3.0F, 10.0F, new CubeDeformation(0.25F)).mirror(false)
                .texOffs(0, 0).addBox(-1.5F, -10.0F, -6.0F, 3.0F, 6.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(0, 3).addBox(-2.5F, -13.0F, -6.0F, 5.0F, 8.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(30, 0).addBox(-1.5F, -10.0F, -6.0F, 3.0F, 6.0F, 12.0F, new CubeDeformation(0.25F))
                .texOffs(0, 18).addBox(-5.5F, -11.0F, -3.0F, 2.0F, 7.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(0, 18).mirror().addBox(3.5F, -11.0F, -3.0F, 2.0F, 7.0F, 9.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(22, 18).addBox(-5.5F, -11.0F, -3.0F, 2.0F, 7.0F, 9.0F, new CubeDeformation(0.25F))
                .texOffs(22, 18).mirror().addBox(3.5F, -11.0F, -3.0F, 2.0F, 7.0F, 9.0F, new CubeDeformation(0.25F)).mirror(false)
                .texOffs(18, 3).addBox(-5.0F, -5.0F, -5.0F, 2.0F, 5.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(18, 3).mirror().addBox(3.0F, -5.0F, -5.0F, 2.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(30, 3).mirror().addBox(3.0F, -5.0F, -5.0F, 2.0F, 5.0F, 4.0F, new CubeDeformation(0.25F)).mirror(false)
                .texOffs(30, 3).addBox(-5.0F, -5.0F, -5.0F, 2.0F, 5.0F, 4.0F, new CubeDeformation(0.25F))
                .texOffs(30, 4).addBox(-3.0F, -2.0F, -5.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(30, 4).mirror().addBox(2.0F, -2.0F, -5.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition torso = body.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(0, 69).addBox(-5.0F, 2.0F, -3.0F, 10.0F, 5.0F, 6.0F, new CubeDeformation(0.025F))
                .texOffs(0, 80).addBox(-4.5F, 6.5F, -2.5F, 9.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0, 0.0F));

        PartDefinition thing = torso.addOrReplaceChild("thing", CubeListBuilder.create().texOffs(0, 56).addBox(-6.0F, -1.0F, -5.0F, 12.0F, 4.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(42, 56).addBox(-6.0F, -1.0F, -5.0F, 12.0F, 4.0F, 9.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.0F, 0.05F, 0.075F, 0.2618F, 0.0F, 0.0F));

        PartDefinition left_shoulder = left_arm.addOrReplaceChild("left_shoulder", CubeListBuilder.create().texOffs(0, 89).addBox(0.0F, -4.5F, -3.0F, 5.0F, 5.0F, 6.0F, new CubeDeformation(0.01F))
                .texOffs(22, 89).addBox(0.0F, -4.5F, -3.0F, 5.0F, 5.0F, 6.0F, new CubeDeformation(0.26F))
                .texOffs(44, 89).addBox(0.0F, -5.5F, -3.0F, 3.0F, 1.0F, 6.0F, new CubeDeformation(0.01F))
                .texOffs(62, 89).addBox(0.0F, -5.5F, -3.0F, 3.0F, 1.0F, 6.0F, new CubeDeformation(0.27F))
                .texOffs(0, 111).addBox(-1.5F, 5.0F, -3.0F, 5.0F, 6.0F, 6.0F, new CubeDeformation(0.01F))
                .texOffs(22, 111).addBox(-1.5F, 5.0F, -3.0F, 5.0F, 6.0F, 6.0F, new CubeDeformation(0.26F)), PartPose.offset(0, 0, 0.0F));

        PartDefinition left_shoulder_pad = left_shoulder.addOrReplaceChild("left_shoulder_pad", CubeListBuilder.create().texOffs(0, 100).addBox(1.8918F, -1.4881F, -4.0F, 6.0F, 3.0F, 8.0F, new CubeDeformation(0.01F))
                .texOffs(28, 100).addBox(1.8918F, -1.4881F, -4.0F, 6.0F, 3.0F, 8.0F, new CubeDeformation(0.26F)), PartPose.offsetAndRotation(-2.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.2182F));

        PartDefinition right_shoulder = right_arm.addOrReplaceChild("right_shoulder", CubeListBuilder.create().texOffs(0, 89).mirror().addBox(-5.0F, -4.5F, -3.0F, 5.0F, 5.0F, 6.0F, new CubeDeformation(0.01F)).mirror(false)
                .texOffs(44, 89).mirror().addBox(-3.0F, -5.5F, -3.0F, 3.0F, 1.0F, 6.0F, new CubeDeformation(0.01F)).mirror(false)
                .texOffs(62, 89).mirror().addBox(-3.0F, -5.5F, -3.0F, 3.0F, 1.0F, 6.0F, new CubeDeformation(0.27F)).mirror(false)
                .texOffs(22, 89).mirror().addBox(-5.0F, -4.5F, -3.0F, 5.0F, 5.0F, 6.0F, new CubeDeformation(0.26F)).mirror(false)
                .texOffs(0, 111).mirror().addBox(-3.5F, 5.0F, -3.0F, 5.0F, 6.0F, 6.0F, new CubeDeformation(0.01F)).mirror(false)
                .texOffs(22, 111).mirror().addBox(-3.5F, 5.0F, -3.0F, 5.0F, 6.0F, 6.0F, new CubeDeformation(0.26F)).mirror(false), PartPose.offset(0, -0, 0.0F));

        PartDefinition right_shoulder_pad = right_shoulder.addOrReplaceChild("right_shoulder_pad", CubeListBuilder.create().texOffs(0, 100).mirror().addBox(-7.6753F, -1.4644F, -4.0F, 6.0F, 3.0F, 8.0F, new CubeDeformation(0.01F)).mirror(false)
                .texOffs(28, 100).mirror().addBox(-7.6753F, -1.4644F, -4.0F, 6.0F, 3.0F, 8.0F, new CubeDeformation(0.26F)).mirror(false), PartPose.offsetAndRotation(2.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.2182F));

        PartDefinition left_shoulder_glow = left_arm_glow.addOrReplaceChild("left_shoulder_glow", CubeListBuilder.create(), PartPose.offset(0, 0, 0.0F));

        PartDefinition left_shoulder_upper_glow = left_shoulder_glow.addOrReplaceChild("left_shoulder_upper_glow", CubeListBuilder.create().texOffs(116, 22).addBox(6.0F, -4.75F, -1.5F, 3.0F, 8.0F, 3.0F, new CubeDeformation(-0.24F))
                .texOffs(89, 5).addBox(4.0F, -2.25F, -3.75F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.01F))
                .texOffs(89, 5).addBox(4.0F, -2.25F, 2.75F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(-2.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.1309F));

        PartDefinition left_shoulder_lower_glow = left_shoulder_glow.addOrReplaceChild("left_shoulder_lower_glow", CubeListBuilder.create().texOffs(116, 14).addBox(3.5F, 4.0F, -1.5F, 3.0F, 5.0F, 3.0F, new CubeDeformation(-0.23F)), PartPose.offset(0, 0.0F, 0.0F));

        PartDefinition right_shoulder_glow = right_arm_glow.addOrReplaceChild("right_shoulder_glow", CubeListBuilder.create(), PartPose.offset(0, 0, 0.0F));

        PartDefinition right_shoulder_upper_glow = right_shoulder_glow.addOrReplaceChild("right_shoulder_upper_glow", CubeListBuilder.create().texOffs(116, 22).mirror().addBox(-9.0F, -5.75F, -1.5F, 3.0F, 8.0F, 3.0F, new CubeDeformation(-0.24F)).mirror(false)
                .texOffs(89, 5).mirror().addBox(-6.0F, -3.25F, -3.75F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.01F)).mirror(false)
                .texOffs(89, 5).mirror().addBox(-6.0F, -3.25F, 2.75F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offsetAndRotation(2.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.1309F));

        PartDefinition right_shoulder_lower_glow = right_shoulder_glow.addOrReplaceChild("right_shoulder_lower_glow", CubeListBuilder.create().texOffs(116, 14).mirror().addBox(-6.5F, 4.0F, -1.5F, 3.0F, 5.0F, 3.0F, new CubeDeformation(-0.23F)).mirror(false), PartPose.offset(0, 0.0F, 0.0F));

        PartDefinition left_leg = left_legging.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(84, 116).mirror().addBox(-2.4F, -0.5F, -2.5F, 5.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0, 0, 0.0F));

        PartDefinition left_thigh_guard1 = left_leg.addOrReplaceChild("left_thigh_guard1", CubeListBuilder.create().texOffs(48, 117).mirror().addBox(1.3934F, -1.1278F, -3.0F, 3.0F, 5.0F, 6.0F, new CubeDeformation(0.03F)).mirror(false)
                .texOffs(48, 106).mirror().addBox(1.3934F, -1.1278F, -3.0F, 3.0F, 5.0F, 6.0F, new CubeDeformation(0.28F)).mirror(false), PartPose.offsetAndRotation(0.1F, 2.0F, 0.0F, 0.0F, 0.0F, -1.1345F));

        PartDefinition left_thigh_guard2 = left_leg.addOrReplaceChild("left_thigh_guard2", CubeListBuilder.create().texOffs(66, 115).mirror().addBox(-0.2266F, -2.8943F, -3.0F, 3.0F, 7.0F, 6.0F, new CubeDeformation(0.02F)).mirror(false)
                .texOffs(66, 102).mirror().addBox(-0.2266F, -2.8943F, -3.0F, 3.0F, 7.0F, 6.0F, new CubeDeformation(0.27F)).mirror(false), PartPose.offsetAndRotation(1.1F, 4.0F, 0.0F, 0.0F, 0.0F, -1.1345F));

        PartDefinition right_leg = right_legging.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(84, 116).addBox(-2.6F, -0.5F, -2.5F, 5.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0, 0, 0.0F));

        PartDefinition right_thigh_guard1 = right_leg.addOrReplaceChild("right_thigh_guard1", CubeListBuilder.create().texOffs(48, 117).addBox(-4.3934F, -1.1278F, -3.0F, 3.0F, 5.0F, 6.0F, new CubeDeformation(0.03F))
                .texOffs(48, 106).addBox(-4.3934F, -1.1278F, -3.0F, 3.0F, 5.0F, 6.0F, new CubeDeformation(0.28F)), PartPose.offsetAndRotation(-0.1F, 2.0F, 0.0F, 0.0F, 0.0F, 1.1345F));

        PartDefinition right_thigh_guard2 = right_leg.addOrReplaceChild("right_thigh_guard2", CubeListBuilder.create().texOffs(66, 115).addBox(-2.7734F, -2.8943F, -3.0F, 3.0F, 7.0F, 6.0F, new CubeDeformation(0.02F))
                .texOffs(66, 102).addBox(-2.7734F, -2.8943F, -3.0F, 3.0F, 7.0F, 6.0F, new CubeDeformation(0.27F)), PartPose.offsetAndRotation(-1.1F, 4.0F, 0.0F, 0.0F, 0.0F, 1.1345F));

        PartDefinition codpiece = leggings.addOrReplaceChild("codpiece", CubeListBuilder.create().texOffs(32, 72).addBox(-4.0F, -14.5F, -3.0F, 8.0F, 2.0F, 6.0F, new CubeDeformation(0.04F))
                .texOffs(60, 72).addBox(-4.0F, -14.5F, -3.0F, 8.0F, 2.0F, 6.0F, new CubeDeformation(0.29F))
                .texOffs(28, 80).addBox(-2.0F, -12.5F, -3.0F, 4.0F, 3.0F, 6.0F, new CubeDeformation(0.04F))
                .texOffs(48, 80).addBox(-2.0F, -12.5F, -3.0F, 4.0F, 3.0F, 6.0F, new CubeDeformation(0.29F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition left_boot = left_foot.addOrReplaceChild("left_boot", CubeListBuilder.create().texOffs(104, 115).addBox(-2.9F, 6.0F, -3.0F, 6.0F, 7.0F, 6.0F, new CubeDeformation(0.01F))
                .texOffs(104, 102).addBox(-2.9F, 6.0F, -3.0F, 6.0F, 7.0F, 6.0F, new CubeDeformation(0.26F)), PartPose.offset(0, 0, 0.0F));

        PartDefinition right_boot = right_foot.addOrReplaceChild("right_boot", CubeListBuilder.create().texOffs(104, 115).mirror().addBox(-3.1F, 6.0F, -3.0F, 6.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(104, 102).mirror().addBox(-3.1F, 6.0F, -3.0F, 6.0F, 7.0F, 6.0F, new CubeDeformation(0.25F)).mirror(false), PartPose.offset(0, 0, 0.0F));

        PartDefinition left_boot_glow = left_foot_glow.addOrReplaceChild("left_boot_glow", CubeListBuilder.create().texOffs(106, 14).addBox(2.6F, 6.5F, -1.5F, 2.0F, 4.0F, 3.0F, new CubeDeformation(-0.24F)), PartPose.offset(0, 0, 0.0F));

        PartDefinition right_boot_glow = right_foot_glow.addOrReplaceChild("right_boot_glow", CubeListBuilder.create().texOffs(106, 14).mirror().addBox(-4.6F, 6.5F, -1.5F, 2.0F, 4.0F, 3.0F, new CubeDeformation(-0.25F)).mirror(false), PartPose.offset(0, 0, 0.0F));
        return LayerDefinition.create(mesh, 128, 128);
    }
}