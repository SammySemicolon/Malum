package com.sammy.malum.client.renderer.curio;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.sammy.malum.MalumMod;
import com.sammy.malum.common.item.cosmetic.curios.CurioTokenOfGratitude;
import com.sammy.malum.registry.client.ModelRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
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
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

import java.util.UUID;

public class TokenOfGratitudeRenderer implements ICurioRenderer {

    private static final ResourceLocation SAMMY = MalumMod.malumPath("textures/cosmetic/sammy_tail.png");
    private static final ResourceLocation OWL_PERSON_EYES = MalumMod.malumPath("textures/cosmetic/owl_person_eyes.png");
    private static final ResourceLocation OWL_PERSON_ELYTRA = MalumMod.malumPath("textures/cosmetic/owl_person_elytra.png");
    private static final ResourceLocation SNAKE_FELLA_SCARF = MalumMod.malumPath("textures/cosmetic/snake_scarf.png");
    private static final ResourceLocation BOBBU_SCARF = MalumMod.malumPath("textures/cosmetic/bobbu_scarf.png");
    private static final ResourceLocation DELLY_NECKLACE = MalumMod.malumPath("textures/cosmetic/delly_necklace.png");
    private static final ResourceLocation LOFI = MalumMod.malumPath("textures/cosmetic/lofi_tail.png");

    private static final ResourceLocation TRANS_SCARF = MalumMod.malumPath("textures/cosmetic/trans_scarf.png");

    @Override
    public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack stack, SlotContext slotContext, PoseStack poseStack, RenderLayerParent<T, M> renderLayerParent, MultiBufferSource renderTypeBuffer, int light, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (slotContext.entity() instanceof AbstractClientPlayer playerEntity) {
            if (playerEntity.getUUID().equals(CurioTokenOfGratitude.SAMMY)) {
                renderTail(stack, SAMMY, poseStack, playerEntity, renderTypeBuffer, light, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch);
            }
            if (playerEntity.getUUID().equals(CurioTokenOfGratitude.LOFI) || playerEntity.getUUID().equals(CurioTokenOfGratitude.CREECHURE)) {
                renderTail(stack, LOFI, poseStack, playerEntity, renderTypeBuffer, light, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch);
            }
            if (playerEntity.getUUID().equals(CurioTokenOfGratitude.OWL_PERSON)) {
                renderGlowingEyes(playerEntity, LodestoneRenderTypes.TRANSPARENT_TEXTURE.applyAndCache(RenderTypeToken.createCachedToken(OWL_PERSON_EYES)), poseStack, renderTypeBuffer, RenderHelper.FULL_BRIGHT);
            }
            if (playerEntity.getUUID().equals(CurioTokenOfGratitude.SNAKE_SCARF_FELLA)) {
                renderScarf(playerEntity, SNAKE_FELLA_SCARF, poseStack, renderTypeBuffer, light);
            }
            if (playerEntity.getUUID().equals(CurioTokenOfGratitude.BOBBU)) {
                renderScarf(playerEntity, BOBBU_SCARF, poseStack, renderTypeBuffer, light);
            }
            if (playerEntity.getUUID().equals(CurioTokenOfGratitude.DELLY)) {
                renderScarf(playerEntity, DELLY_NECKLACE, poseStack, renderTypeBuffer, light);
            }
            if (CurioTokenOfGratitude.TRANS_SCARFS.contains(playerEntity.getUUID())) {
                renderScarf(playerEntity, TRANS_SCARF, poseStack, renderTypeBuffer, light);
            }
        }
    }

    public static ResourceLocation getElytraTexture(UUID uuid, ResourceLocation original) {
        if (uuid.equals(CurioTokenOfGratitude.OWL_PERSON)) {
            return OWL_PERSON_ELYTRA;
        }
        return original;
    }

    public static void renderGlowingEyes(AbstractClientPlayer playerEntity, RenderType renderType, PoseStack poseStack, MultiBufferSource renderTypeBuffer, int light) {
        VertexConsumer vertexconsumer = renderTypeBuffer.getBuffer(renderType);
        ICurioRenderer.followHeadRotations(playerEntity, ModelRegistry.HEAD_OVERLAY_MODEL.overlay);
        ModelRegistry.HEAD_OVERLAY_MODEL.renderToBuffer(poseStack, vertexconsumer, light, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
    }


    public static void renderTail(ItemStack stack, ResourceLocation texture, PoseStack poseStack, AbstractClientPlayer playerEntity, MultiBufferSource renderTypeBuffer, int light, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
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
        VertexConsumer vertexconsumer = ItemRenderer.getArmorFoilBuffer(renderTypeBuffer, RenderType.armorCutoutNoCull(texture), false, stack.hasFoil());
        ModelRegistry.TAIL_MODEL.setupAnim(playerEntity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        ModelRegistry.TAIL_MODEL.prepareMobModel(playerEntity, limbSwing, limbSwingAmount, partialTicks);
        ICurioRenderer.translateIfSneaking(poseStack, playerEntity);
        ICurioRenderer.rotateIfSneaking(poseStack, playerEntity);
        ModelRegistry.TAIL_MODEL.renderToBuffer(poseStack, vertexconsumer, light, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
        poseStack.popPose();
    }

    public static void renderScarf(AbstractClientPlayer playerEntity, ResourceLocation texture, PoseStack poseStack, MultiBufferSource renderTypeBuffer, int light) {
        VertexConsumer vertexconsumer = renderTypeBuffer.getBuffer(RenderType.armorCutoutNoCull(texture));
        float pticks = Minecraft.getInstance().getFrameTime();
        float f = Mth.rotLerp(pticks, playerEntity.yBodyRotO, playerEntity.yBodyRot);
        float f1 = Mth.rotLerp(pticks, playerEntity.yHeadRotO, playerEntity.yHeadRot);
        float netHeadYaw = f1 - f;
        float netHeadPitch = Mth.lerp(pticks, playerEntity.xRotO, playerEntity.getXRot());
        EntityRenderer<? super AbstractClientPlayer> render = Minecraft.getInstance().getEntityRenderDispatcher().getRenderer(playerEntity);
        if (render instanceof LivingEntityRenderer livingEntityRenderer) {
            EntityModel<AbstractClientPlayer> model = livingEntityRenderer.getModel();
            if (model instanceof HumanoidModel humanoidModel) {
                ModelRegistry.SCARF.copyFromDefault(humanoidModel);
            }
        }
        ModelRegistry.SCARF.setupAnim(playerEntity, playerEntity.walkAnimation.position(), playerEntity.walkAnimation.speed(), playerEntity.tickCount + pticks, netHeadYaw, netHeadPitch);
        ModelRegistry.SCARF.renderToBuffer(poseStack, vertexconsumer, light, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
    }
}