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
        int desiredSignal = blockEntityIn.desiredSignal;
        int currentSignal = blockEntityIn.currentSignal;
        float partial = (float) blockEntityIn.timer / Math.max(Math.round(blockEntityIn.frequency / 15f), 1);
        if (currentSignal > desiredSignal) {
            return (currentSignal - partial) / 15f;
        } else {
            return (currentSignal + partial) / 15f;
        }
    }
}