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
        return blockEntityIn.signal/15f;
    }
}
