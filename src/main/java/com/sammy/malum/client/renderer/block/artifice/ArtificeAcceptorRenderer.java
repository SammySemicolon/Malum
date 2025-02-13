package com.sammy.malum.client.renderer.block.artifice;

import com.mojang.blaze3d.vertex.PoseStack;
import com.sammy.malum.core.systems.artifice.ArtificeModifierSourceInstance;
import com.sammy.malum.core.systems.artifice.IArtificeModifierSource;
import com.sammy.malum.core.systems.artifice.IArtificeAcceptor;
import com.sammy.malum.core.systems.item.HeldItemTracker;
import com.sammy.malum.registry.client.MalumRenderTypeTokens;
import com.sammy.malum.registry.common.item.ItemTagRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.entity.BlockEntity;
import team.lodestar.lodestone.helpers.ColorHelper;
import team.lodestar.lodestone.registry.client.LodestoneRenderTypes;
import team.lodestar.lodestone.systems.blockentity.LodestoneBlockEntity;
import team.lodestar.lodestone.systems.rendering.VFXBuilders;
import team.lodestar.lodestone.systems.rendering.rendeertype.ShaderUniformHandler;

import java.awt.*;
import java.util.WeakHashMap;


public abstract class ArtificeAcceptorRenderer<T extends LodestoneBlockEntity> implements BlockEntityRenderer<T> {

    public static final WeakHashMap<IArtificeAcceptor, Color> DEBUG_COLORS = new WeakHashMap<>();

    public static final HeldItemTracker FORK_TRACKER = new HeldItemTracker(p -> p.is(ItemTagRegistry.IS_ARTIFICE_TOOL));

    @SuppressWarnings({"rawtypes", "unchecked", "DataFlowIssue"})
    public static void renderModifiers(IArtificeAcceptor target, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        var minecraft = Minecraft.getInstance();
        var level = minecraft.level;
        var targetBlockEntity = (BlockEntity) target;
        var pos = targetBlockEntity.getBlockPos();
        poseStack.pushPose();
        poseStack.translate(-pos.getX(), -pos.getY(), -pos.getZ());
        target.getAttributes().getInfluenceData(level).ifPresent(p -> {
            for (ArtificeModifierSourceInstance modifier : p.modifiers()) {
                var modifierPosition = modifier.sourcePosition;
                var modifierBlockEntity = level.getBlockEntity(modifierPosition);
                if (modifierBlockEntity == null) {
                    continue;
                }
                var modifierBlockPos = modifierBlockEntity.getBlockPos();
                if (modifierBlockEntity instanceof IArtificeModifierSource influencer) {
                    BlockEntityRenderer<BlockEntity> renderer = minecraft.getBlockEntityRenderDispatcher().getRenderer(modifierBlockEntity);
                    if (renderer instanceof ArtificeModifierSourceRenderer modifierRenderer) {
                        poseStack.pushPose();
                        poseStack.translate(modifierBlockPos.getX(), modifierBlockPos.getY(), modifierBlockPos.getZ());
                        modifierRenderer.render(influencer, target, SpiritInfluenceRendererData.getSpiritInfluenceData(target), partialTicks, poseStack);
                        renderDebugGizmo(poseStack, target);
                        poseStack.popPose();
                    }
                }
            }
        });
        poseStack.translate(pos.getX(), pos.getY(), pos.getZ());
        renderDebugGizmo(poseStack, target);
        poseStack.popPose();
    }

    public static void renderDebugGizmo(PoseStack stack, IArtificeAcceptor target) {
        if (true) {//TODO: this should only render if holding the tuning fork and f3 and whatnot
            return;
        }
        Minecraft minecraft = Minecraft.getInstance();
        Color color = DEBUG_COLORS.computeIfAbsent(target, a -> ColorHelper.getColor(Mth.abs(a.hashCode())));
        var debugGizmo = LodestoneRenderTypes.TEXTURE.applyWithModifierAndCache(MalumRenderTypeTokens.DEBUG_GIZMO, ShaderUniformHandler.LUMITRANSPARENT, b -> b.setWriteMaskState(RenderStateShard.COLOR_WRITE).setDepthTestState(RenderStateShard.NO_DEPTH_TEST));
        stack.pushPose();
        stack.translate(0.5f, 4f, 0.5f);
        stack.mulPose(minecraft.getEntityRenderDispatcher().cameraOrientation());
        VFXBuilders.createWorld()
                .setRenderType(debugGizmo)
                .setColor(color)
                .renderQuad(stack, 0.4f);
        stack.popPose();
    }

    public interface ArtificeModifierSourceRenderer<T extends IArtificeModifierSource> {
        void render(T modifier, IArtificeAcceptor target, SpiritInfluenceRendererData spiritInfluence, float partialTicks, PoseStack poseStack);
    }
}