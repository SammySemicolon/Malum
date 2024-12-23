package com.sammy.malum.client.renderer.block.redstone;

import com.mojang.blaze3d.vertex.PoseStack;
import com.sammy.malum.MalumMod;
import com.sammy.malum.client.RenderUtils;
import com.sammy.malum.common.block.curiosities.redstone.pulsebank.PulsebankBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.util.Mth;
import team.lodestar.lodestone.registry.client.LodestoneRenderTypes;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.rendering.VFXBuilders;
import team.lodestar.lodestone.systems.rendering.rendeertype.RenderTypeToken;

import java.awt.*;

import static com.sammy.malum.client.RenderUtils.drawCubeSides;


public class PulsebankRenderer extends RedstoneMachineRenderer<PulsebankBlockEntity> {

    private static final RenderTypeToken TOKEN = RenderTypeToken.createToken(MalumMod.malumPath("textures/block/redstone/pulsebank_overlay.png"));

    public PulsebankRenderer(BlockEntityRendererProvider.Context context) {
        super(context, TOKEN);
    }

    @Override
    public float getGlowDelta(PulsebankBlockEntity blockEntityIn) {
        return Easing.QUARTIC_OUT.ease((float) blockEntityIn.timer / blockEntityIn.frequency, 0, 1);
    }
}