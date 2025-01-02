package com.sammy.malum.client.renderer.block.redstone;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.block.curiosities.redstone.wavecharger.WaveChargerBlockEntity;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;


public class WaveChargerRenderer extends RedstoneDiodeRenderer<WaveChargerBlockEntity> {

    public WaveChargerRenderer(BlockEntityRendererProvider.Context context) {
        super(context, MalumMod.malumPath("textures/block/redstone/wavecharger_overlay.png"));
    }

    @Override
    public float getGlowDelta(WaveChargerBlockEntity blockEntityIn) {
        // todo WaveChargerBlockEntity.desiredSignal and SpiritDiodeBlockEntity.timer no longer exists; replaced with SpiritDiodeBlockEntity.signal as temporary fix
        int desiredSignal = blockEntityIn.signal;
        int currentSignal = blockEntityIn.signal;
        float partial = (float) blockEntityIn.signal / Math.max(Math.round(blockEntityIn.delay / 15f), 1);
        if (currentSignal > desiredSignal) {
            return (currentSignal - partial) / 15f;
        } else {
            return (currentSignal + partial) / 15f;
        }
    }
}
