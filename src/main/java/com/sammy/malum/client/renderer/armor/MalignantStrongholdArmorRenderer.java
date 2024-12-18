package com.sammy.malum.client.renderer.armor;

import com.mojang.blaze3d.vertex.PoseStack;
import com.sammy.malum.MalumMod;
import com.sammy.malum.client.cosmetic.ArmorSkinRenderingData;
import com.sammy.malum.client.model.MalignantStrongholdArmorModel;
import com.sammy.malum.common.item.cosmetic.skins.ArmorSkin;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import team.lodestar.lodestone.systems.model.LodestoneArmorModel;

public class MalignantStrongholdArmorRenderer implements ArmorRenderer {
    LodestoneArmorModel armorModel;
    private ResourceLocation texture;

    public MalignantStrongholdArmorRenderer() {
    }

    @Override
    public void render(PoseStack matrices, MultiBufferSource vertexConsumers, ItemStack stack, LivingEntity entity, EquipmentSlot slot, int light, HumanoidModel<LivingEntity> contextModel) {
        if (armorModel == null) {
            armorModel = new MalignantStrongholdArmorModel(Minecraft.getInstance().getEntityModels().bakeLayer(MalignantStrongholdArmorModel.LAYER));
        }
        float pticks = Minecraft.getInstance().getTimer().getGameTimeDeltaTicks();
        float f = Mth.rotLerp(pticks, entity.yBodyRotO, entity.yBodyRot);
        float f1 = Mth.rotLerp(pticks, entity.yHeadRotO, entity.yHeadRot);
        float netHeadYaw = f1 - f;
        float netHeadPitch = Mth.lerp(pticks, entity.xRotO, entity.getXRot());
        ArmorSkin skin = ArmorSkin.getAppliedItemSkin(stack);

        if (skin != null) {
            armorModel = ArmorSkinRenderingData.RENDERING_DATA.apply(skin).getModel(entity);
            texture = ArmorSkinRenderingData.RENDERING_DATA.apply(skin).getTexture(entity);
        }

        if (texture == null) {
            texture = MalumMod.malumPath("textures/armor/malignant_stronghold.png");
        }

        armorModel.slot = slot;
        armorModel.copyFromDefault(contextModel);
        armorModel.setupAnim(entity, entity.walkAnimation.position(), entity.walkAnimation.speed(), entity.tickCount + pticks, netHeadYaw, netHeadPitch);
        contextModel.copyPropertiesTo(armorModel);
        armorModel.setAllVisible(false);
        armorModel.head.visible = slot == EquipmentSlot.HEAD;
        armorModel.body.visible = slot == EquipmentSlot.CHEST;
        armorModel.leftArm.visible = slot == EquipmentSlot.CHEST;
        armorModel.rightArm.visible = slot == EquipmentSlot.CHEST;
        armorModel.leftLeg.visible = slot == EquipmentSlot.LEGS || slot == EquipmentSlot.FEET;
        armorModel.rightLeg.visible = slot == EquipmentSlot.LEGS || slot == EquipmentSlot.FEET;
        armorModel.leftFoot.visible = slot == EquipmentSlot.FEET;
        armorModel.rightFoot.visible = slot == EquipmentSlot.FEET;
        armorModel.leggings.visible = slot == EquipmentSlot.LEGS;
        armorModel.leftLegging.visible = slot == EquipmentSlot.LEGS;
        armorModel.rightLegging.visible = slot == EquipmentSlot.LEGS;
        ArmorRenderer.renderPart(matrices, vertexConsumers, light, stack, armorModel, texture);
    }
}
