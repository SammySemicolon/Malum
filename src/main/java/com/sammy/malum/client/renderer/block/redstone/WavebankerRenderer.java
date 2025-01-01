package com.sammy.malum.client.renderer.block.redstone;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.block.curiosities.redstone.wavebanker.WaveBankerBlockEntity;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import team.lodestar.lodestone.systems.easing.Easing;


public class WavebankerRenderer extends RedstoneDiodeRenderer<WaveBankerBlockEntity> {

    public WavebankerRenderer(BlockEntityRendererProvider.Context context) {
        super(context, MalumMod.malumPath("textures/block/redstone/wavebanker_overlay.png"));
    }

    @Override
    public float getGlowDelta(WaveBankerBlockEntity blockEntityIn) {
        return Easing.QUARTIC_OUT.ease((float) blockEntityIn.timer / blockEntityIn.frequency, 0, 1);
    }
}