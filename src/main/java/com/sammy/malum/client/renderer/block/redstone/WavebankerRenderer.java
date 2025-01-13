package com.sammy.malum.client.renderer.block.redstone;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.block.curiosities.redstone.wavebanker.WaveBankerBlockEntity;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import team.lodestar.lodestone.systems.easing.Easing;


public class WavebankerRenderer extends SpiritDiodeRenderer<WaveBankerBlockEntity> {

    public WavebankerRenderer(BlockEntityRendererProvider.Context context) {
        super(context, MalumMod.malumPath("textures/block/spirit_diode/wavebanker_overlay.png"), "malum.waveform_artifice.wavebanker");
    }

    @Override
    public float getGlowDelta(WaveBankerBlockEntity blockEntityIn, float delta) {
        return Easing.QUARTIC_OUT.ease(delta, 0, 1);
    }
}
