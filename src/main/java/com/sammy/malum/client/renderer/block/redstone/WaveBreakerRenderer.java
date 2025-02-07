package com.sammy.malum.client.renderer.block.redstone;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.block.curiosities.redstone.wavebreaker.WaveBreakerBlockEntity;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.util.Mth;


public class WaveBreakerRenderer extends SpiritDiodeRenderer<WaveBreakerBlockEntity> {

    public WaveBreakerRenderer(BlockEntityRendererProvider.Context context) {
        super(context, MalumMod.malumPath("textures/block/spirit_diode/wavebreaker_overlay.png"), "malum.waveform_artifice.wavebreaker");
    }

    @Override
    public float getGlowDelta(WaveBreakerBlockEntity blockEntityIn, float delta) {
        if (blockEntityIn.visualTransitionEnd == 0) {
            if (delta > 0.1f) {
                return 1;
            }
            return delta*10f;
        }
        if (delta > 0.9f) {
            return (delta-0.9f)*10;
        }
        return 0;
    }
}
