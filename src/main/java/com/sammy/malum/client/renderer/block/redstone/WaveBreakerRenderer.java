package com.sammy.malum.client.renderer.block.redstone;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.block.curiosities.redstone.wavebreaker.WaveBreakerBlockEntity;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;


public class WaveBreakerRenderer extends RedstoneDiodeRenderer<WaveBreakerBlockEntity> {

    public WaveBreakerRenderer(BlockEntityRendererProvider.Context context) {
        super(context, MalumMod.malumPath("textures/block/redstone/wavebreaker_overlay.png"));
    }

    @Override
    public float getGlowDelta(WaveBreakerBlockEntity blockEntityIn) {
        // todo SpiritDiodeBlockEntity.timer no longer exists; replaced with SpiritDiodeBlockEntity.signal as temporary fix
        int timer = blockEntityIn.signal;
        int frequency = blockEntityIn.delay;
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
