package com.sammy.malum.client.renderer.block.redstone;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.block.curiosities.redstone.wavecharger.WaveChargerBlockEntity;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;


public class WaveChargerRenderer extends SpiritDiodeRenderer<WaveChargerBlockEntity> {

    public WaveChargerRenderer(BlockEntityRendererProvider.Context context) {
        super(context, MalumMod.malumPath("textures/block/spirit_diode/wavecharger_overlay.png"), "malum.waveform_artifice.wavecharger");
    }

    @Override
    public float getGlowDelta(WaveChargerBlockEntity blockEntityIn, float delta) {
        return blockEntityIn.getOutputSignal()/15f;
    }
}
