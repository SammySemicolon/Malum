package com.sammy.malum.client.renderer.block;

import com.mojang.blaze3d.vertex.*;
import com.mojang.math.*;
import com.sammy.malum.client.*;
import com.sammy.malum.common.block.curiosities.totem.*;
import com.sammy.malum.core.systems.spirit.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.blockentity.*;
import net.minecraft.core.*;
import net.minecraft.util.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.state.properties.*;
import org.joml.*;
import team.lodestar.lodestone.registry.client.*;
import team.lodestar.lodestone.systems.easing.*;
import team.lodestar.lodestone.systems.rendering.rendeertype.*;

import java.awt.*;
import java.lang.Math;


public class TotemPoleRenderer implements BlockEntityRenderer<TotemPoleBlockEntity> {

    public TotemPoleRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(TotemPoleBlockEntity blockEntityIn, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        Direction direction = blockEntityIn.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING);
        MalumSpiritType spiritType = blockEntityIn.spirit;
        if (spiritType == null) {
            return;
        }

        Level level = Minecraft.getInstance().level;
        RenderType renderType = LodestoneRenderTypes.ADDITIVE_TEXTURE.apply(RenderTypeToken.createToken(spiritType.getTotemGlowTexture()));

        poseStack.pushPose();
        poseStack.translate(0.5f, 0.5f, 0.5f);
        poseStack.mulPose(Axis.YN.rotationDegrees(direction.toYRot()));
        poseStack.translate(-0.5f, -0.5f, -0.5f);

        float pct = blockEntityIn.chargeProgress / 20f;
        Color color = spiritType.getPrimaryColor();
        float alphaBonus = pct * 0.5f;
        float alpha = pct * 0.2f + alphaBonus;
        float ease = Easing.SINE_OUT.ease(pct, 0, 1, 1);
        float distance = 0.2f - ease * 0.2f;
        float wobbleStrength = 0.1f - ease * 0.075f;

        Vector3f[] positions = new Vector3f[]{new Vector3f(-0.025f, -0.025f, 1.01f), new Vector3f(1.025f, -0.025f, 1.01f), new Vector3f(1.025f, 1.025f, 1.01f), new Vector3f(-0.025f, 1.025f, 1.01f)};

        float gameTime = level.getGameTime() + partialTicks;
        int time = 160;
        for (int i = 0; i < 4; i++) {
            applyWobble(positions, wobbleStrength);
            double translation = 0;
            if (distance > 0) {
                boolean odd = i % 2 == 0;
                double angle = i / 4f * (Math.PI * 2);
                angle += ((gameTime % time) / time) * (Math.PI * 2);
                double offset = (distance * Math.cos(angle));
                translation = odd ? -offset : offset;
            }
            poseStack.translate(translation, 0, 0);
            SpiritBasedWorldVFXBuilder.create(spiritType)
                    .setColor(color, alpha)
                    .setRenderType(renderType)
                    .renderQuad(poseStack, positions, 1f);
            poseStack.translate(-translation, 0, 0);
            alpha *= 1 - alphaBonus;
        }
        poseStack.popPose();
    }

    public static void applyWobble(Vector3f[] offsets, float strength) {
        float offset = 0;
        for (Vector3f vector3f : offsets) {
            double time = ((Minecraft.getInstance().level.getGameTime() / 40.0F) % Math.PI * 2);
            float sine = Mth.sin((float) (time + (offset * Math.PI * 2))) * strength;
            vector3f.add(sine, -sine, 0);
            offset += 0.25f;
        }
    }
}
