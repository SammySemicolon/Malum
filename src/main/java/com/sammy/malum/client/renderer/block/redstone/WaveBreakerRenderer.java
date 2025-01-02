package com.sammy.malum.client.renderer.block.redstone;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.block.curiosities.redstone.wavebreaker.WaveBreakerBlockEntity;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.util.Mth;


public class WaveBreakerRenderer extends SpiritDiodeRenderer<WaveBreakerBlockEntity> {

    public WaveBreakerRenderer(BlockEntityRendererProvider.Context context) {
        super(context, MalumMod.malumPath("textures/block/redstone/wavebreaker_overlay.png"));
    }

    @Override
    public float getGlowDelta(WaveBreakerBlockEntity blockEntityIn, float delta) {
        if (delta > 0.6f) {
            return (delta-0.6f)*5;
        }
        return 0;
    }
}
