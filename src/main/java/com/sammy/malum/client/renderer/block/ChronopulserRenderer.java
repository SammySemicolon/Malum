package com.sammy.malum.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.sammy.malum.MalumMod;
import com.sammy.malum.client.RenderUtils;
import com.sammy.malum.client.SpiritBasedWorldVFXBuilder;
import com.sammy.malum.common.block.curiosities.redstone.ChronopulserBlockEntity;
import com.sammy.malum.common.block.curiosities.totem.TotemPoleBlockEntity;
import com.sammy.malum.common.item.ether.EtherItem;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.joml.Vector3f;
import team.lodestar.lodestone.helpers.ColorHelper;
import team.lodestar.lodestone.helpers.EasingHelper;
import team.lodestar.lodestone.registry.client.LodestoneRenderTypes;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.rendering.VFXBuilders;
import team.lodestar.lodestone.systems.rendering.rendeertype.RenderTypeToken;
import team.lodestar.lodestone.systems.rendering.rendeertype.ShaderUniformHandler;

import java.awt.*;

import static com.sammy.malum.client.RenderUtils.drawCube;
import static com.sammy.malum.client.RenderUtils.drawCubeSides;


public class ChronopulserRenderer implements BlockEntityRenderer<ChronopulserBlockEntity> {

    private static final RenderTypeToken TOKEN = RenderTypeToken.createToken(MalumMod.malumPath("textures/block/redstone/chronopulser_overlay.png"));
    private static final Color COLOR = new Color(170, 15, 1);

    public ChronopulserRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(ChronopulserBlockEntity blockEntityIn, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        float pct = Mth.clamp(1 - (blockEntityIn.timer / 15f), 0, 1);
        if (pct > 0) {
            float alpha = Easing.CUBIC_OUT.clamped(pct, 0, 1) * 0.8f;
            float glowAlpha = Easing.SINE_OUT.clamped(pct, 0, 1) * 0.4f;
            var cubeVertexData = RenderUtils.makeCubePositions(1f);
            var builder = VFXBuilders.createWorld()
                    .setColor(COLOR);
            poseStack.pushPose();
            drawCubeSides(poseStack, builder.setAlpha(alpha).setRenderType(LodestoneRenderTypes.TRANSPARENT_TEXTURE.applyAndCache(TOKEN)), 1.00125f, cubeVertexData);
            drawCubeSides(poseStack, builder.setAlpha(glowAlpha).setRenderType(LodestoneRenderTypes.ADDITIVE_TEXTURE.applyAndCache(TOKEN)), 1.0025f, cubeVertexData);
            poseStack.popPose();
        }
    }
}
