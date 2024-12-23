package com.sammy.malum.client.renderer.block.redstone;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.block.curiosities.redstone.DirectionalRedstoneMachineBlock;
import com.sammy.malum.common.block.curiosities.redstone.pulsebank.PulsebankBlockEntity;
import com.sammy.malum.common.block.curiosities.redstone.pulselag.PulselagBlockEntity;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import team.lodestar.lodestone.systems.rendering.rendeertype.RenderTypeToken;


public class PulselagRenderer extends RedstoneMachineRenderer<PulselagBlockEntity> {

    private static final RenderTypeToken TOKEN = RenderTypeToken.createToken(MalumMod.malumPath("textures/block/redstone/pulselag_overlay.png"));

    public PulselagRenderer(BlockEntityRendererProvider.Context context) {
        super(context, TOKEN);
    }

    @Override
    public float getGlowDelta(PulselagBlockEntity blockEntityIn) {
        int timer = blockEntityIn.timer;
        int frequency = blockEntityIn.frequency;
        if (timer > frequency - 20) {
            return Math.max((float) (timer-frequency+20) / 80f, 0.1f);
        }
        else if (timer > 10) {
            return 0.1f;
        }
        if (timer > 0) {
            return (float) timer / 10f;
        }
        return 0;
    }
}