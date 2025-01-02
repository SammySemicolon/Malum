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
        // todo SpiritDiodeBlockEntity.timer no longer exists; replaced with SpiritDiodeBlockEntity.signal as temporary fix
        return Easing.QUARTIC_OUT.ease((float) blockEntityIn.signal / blockEntityIn.delay, 0, 1);
    }
}
