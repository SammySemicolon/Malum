package com.sammy.malum.client.renderer.curio;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.sammy.malum.MalumMod;
import com.sammy.malum.client.model.HeadOverlayModel;
import com.sammy.malum.client.model.TailModel;
import com.sammy.malum.client.model.cosmetic.ScarfModel;
import com.sammy.malum.common.item.cosmetic.curios.CurioTokenOfGratitude;
import com.sammy.malum.registry.client.ModelRegistry;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.client.TrinketRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import team.lodestar.lodestone.helpers.RenderHelper;
import team.lodestar.lodestone.registry.client.*;
import team.lodestar.lodestone.systems.rendering.rendeertype.*;

import java.util.UUID;
public class TokenOfGratitudeRenderer implements TrinketRenderer {

    private static final ResourceLocation SAMMY = MalumMod.malumPath("textures/cosmetic/sammy_tail.png");
    private static final ResourceLocation OWL_PERSON_EYES = MalumMod.malumPath("textures/cosmetic/owl_person_eyes.png");
    private static final ResourceLocation OWL_PERSON_ELYTRA = MalumMod.malumPath("textures/cosmetic/owl_person_elytra.png");
    private static final ResourceLocation SNAKE_FELLA_SCARF = MalumMod.malumPath("textures/cosmetic/snake_scarf.png");
    private static final ResourceLocation BOBBU_SCARF = MalumMod.malumPath("textures/cosmetic/bobbu_scarf.png");
    private static final ResourceLocation DELLY_NECKLACE = MalumMod.malumPath("textures/cosmetic/delly_necklace.png");
    private static final ResourceLocation LOFI = MalumMod.malumPath("textures/cosmetic/lofi_tail.png");

    private static final ResourceLocation TRANS_SCARF = MalumMod.malumPath("textures/cosmetic/trans_scarf.png");

    public static TailModel TAIL_MODEL;
    public static ScarfModel SCARF;
    public static HeadOverlayModel HEAD_OVERLAY_MODEL;

    @Override
    public void render(ItemStack itemStack, SlotReference slotReference, EntityModel<? extends LivingEntity> entityModel, PoseStack poseStack, MultiBufferSource multiBufferSource, int light, LivingEntity livingEntity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        if (livingEntity instanceof AbstractClientPlayer playerEntity) {
            if (TAIL_MODEL == null) {
                TAIL_MODEL = new TailModel(Minecraft.getInstance().getEntityModels().bakeLayer(TailModel.LAYER));
            }
            if (SCARF == null) {
                SCARF = new ScarfModel(Minecraft.getInstance().getEntityModels().bakeLayer(ScarfModel.LAYER));
            }

            if (playerEntity.getUUID().equals(CurioTokenOfGratitude.SAMMY) && entityModel instanceof PlayerModel<? extends LivingEntity> playerModel) {
                renderTail(playerModel, itemStack, SAMMY, poseStack, playerEntity, multiBufferSource, light, limbAngle, limbDistance, tickDelta, animationProgress, headYaw, headPitch);
            }
            if ((playerEntity.getUUID().equals(CurioTokenOfGratitude.LOFI) || playerEntity.getUUID().equals(CurioTokenOfGratitude.CREECHURE)) && entityModel instanceof PlayerModel<? extends LivingEntity> playerModel) {
                renderTail(playerModel, itemStack, LOFI, poseStack, playerEntity, multiBufferSource, light, limbAngle, limbDistance, tickDelta, animationProgress, headYaw, headPitch);
            }
            if (playerEntity.getUUID().equals(CurioTokenOfGratitude.OWL_PERSON) && entityModel instanceof PlayerModel<? extends LivingEntity> playerModel) {
                renderGlowingEyes(playerEntity, playerModel, LodestoneRenderTypes.TRANSPARENT_TEXTURE.applyAndCache(RenderTypeToken.createCachedToken(OWL_PERSON_EYES)), poseStack, multiBufferSource, RenderHelper.FULL_BRIGHT, headYaw, headPitch);
            }
            if (playerEntity.getUUID().equals(CurioTokenOfGratitude.SNAKE_SCARF_FELLA)) {
                renderScarf(playerEntity, SNAKE_FELLA_SCARF, poseStack, multiBufferSource, light);
            }
            if (playerEntity.getUUID().equals(CurioTokenOfGratitude.BOBBU)) {
                renderScarf(playerEntity, BOBBU_SCARF, poseStack, multiBufferSource, light);
            }
            if (playerEntity.getUUID().equals(CurioTokenOfGratitude.DELLY)) {
                renderScarf(playerEntity, DELLY_NECKLACE, poseStack, multiBufferSource, light);
            }
            if (CurioTokenOfGratitude.TRANS_SCARFS.contains(playerEntity.getUUID())) {
                renderScarf(playerEntity, TRANS_SCARF, poseStack, multiBufferSource, light);
            }
        }
    }


    public static ResourceLocation getElytraTexture(UUID uuid, ResourceLocation original) {
        if (uuid.equals(CurioTokenOfGratitude.OWL_PERSON)) {
            return OWL_PERSON_ELYTRA;
        }
        return original;
    }

    public static void renderGlowingEyes(AbstractClientPlayer playerEntity, PlayerModel entityModel, RenderType renderType, PoseStack poseStack, MultiBufferSource renderTypeBuffer, int light, float headYaw, float headPitch) {
        VertexConsumer vertexconsumer = renderTypeBuffer.getBuffer(renderType);

        TrinketRenderer.translateToFace(poseStack, (PlayerModel<AbstractClientPlayer>) entityModel, playerEntity, headYaw, headPitch);
        poseStack.translate(0, 0.2, 0.3f);
        if (HEAD_OVERLAY_MODEL == null) {
            HEAD_OVERLAY_MODEL = new HeadOverlayModel(Minecraft.getInstance().getEntityModels().bakeLayer(HeadOverlayModel.LAYER));
        }

        HEAD_OVERLAY_MODEL.renderToBuffer(poseStack, vertexconsumer, light, OverlayTexture.NO_OVERLAY, 1);
    }


    public static void renderTail(PlayerModel playerModel, ItemStack stack, ResourceLocation texture, PoseStack poseStack, AbstractClientPlayer playerEntity, MultiBufferSource renderTypeBuffer, int light, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        poseStack.pushPose();
        Vec3 movement = new Vec3(playerEntity.getDeltaMovement().x, 0, playerEntity.getDeltaMovement().z);
        double wagSpeed = playerEntity.getDeltaMovement().length();
        if (wagSpeed != 0) {
            double factor = 55;
            Vec3 yawLook = Vec3.directionFromRotation(0.0f, playerEntity.getYRot());
            Vec3 look = new Vec3(yawLook.x, 0.0, yawLook.z);
            Vec3 desiredDirection = look.yRot((float) Math.toRadians(90)).normalize();
            Vec3 sidewaysVelocity = desiredDirection.scale(movement.dot(desiredDirection));
            double speedAndDirection = (sidewaysVelocity.length() * -Math.signum(desiredDirection.dot(sidewaysVelocity))) / wagSpeed;
            poseStack.mulPose(Axis.YP.rotationDegrees((float) (speedAndDirection * factor)));
        }
        float ambientFactor = playerEntity.isShiftKeyDown() ? 2 : 6;
        double ambientXRotation = Math.sin(playerEntity.level().getGameTime() / 18f) * ambientFactor;
        poseStack.mulPose(Axis.XP.rotationDegrees((float) ambientXRotation));
        double ambientYRotation = Math.cos(playerEntity.level().getGameTime() / 24f) * -ambientFactor;
        poseStack.mulPose(Axis.YP.rotationDegrees((float) ambientYRotation));
        VertexConsumer vertexconsumer = ItemRenderer.getArmorFoilBuffer(renderTypeBuffer, RenderType.armorCutoutNoCull(texture), stack.hasFoil());
        TAIL_MODEL.setupAnim(playerEntity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        TAIL_MODEL.prepareMobModel(playerEntity, limbSwing, limbSwingAmount, partialTicks);
        TrinketRenderer.followBodyRotations(playerEntity, playerModel);
        TAIL_MODEL.renderToBuffer(poseStack, vertexconsumer, light, OverlayTexture.NO_OVERLAY, 1);
        poseStack.popPose();
    }

    public static void renderScarf(AbstractClientPlayer playerEntity, ResourceLocation texture, PoseStack poseStack, MultiBufferSource renderTypeBuffer, int light) {
        VertexConsumer vertexconsumer = renderTypeBuffer.getBuffer(RenderType.armorCutoutNoCull(texture));
        float pticks = Minecraft.getInstance().getTimer().getGameTimeDeltaTicks();
        float f = Mth.rotLerp(pticks, playerEntity.yBodyRotO, playerEntity.yBodyRot);
        float f1 = Mth.rotLerp(pticks, playerEntity.yHeadRotO, playerEntity.yHeadRot);
        float netHeadYaw = f1 - f;
        float netHeadPitch = Mth.lerp(pticks, playerEntity.xRotO, playerEntity.getXRot());
        EntityRenderer<? super AbstractClientPlayer> render = Minecraft.getInstance().getEntityRenderDispatcher().getRenderer(playerEntity);
        if (render instanceof LivingEntityRenderer livingEntityRenderer) {
            EntityModel<AbstractClientPlayer> model = livingEntityRenderer.getModel();
            if (model instanceof HumanoidModel humanoidModel) {
                SCARF.copyFromDefault(humanoidModel);
            }
        }
        SCARF.setupAnim(playerEntity, playerEntity.walkAnimation.position(), playerEntity.walkAnimation.speed(), playerEntity.tickCount + pticks, netHeadYaw, netHeadPitch);
        SCARF.renderToBuffer(poseStack, vertexconsumer, light, OverlayTexture.NO_OVERLAY, 1);
    }

}