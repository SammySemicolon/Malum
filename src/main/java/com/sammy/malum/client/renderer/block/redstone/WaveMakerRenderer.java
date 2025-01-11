package com.sammy.malum.client.renderer.block.redstone;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.block.curiosities.redstone.wavemaker.WaveMakerBlockEntity;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import team.lodestar.lodestone.systems.easing.Easing;


public class WaveMakerRenderer extends SpiritDiodeRenderer<WaveMakerBlockEntity> {

    public WaveMakerRenderer(BlockEntityRendererProvider.Context context) {
        super(context, MalumMod.malumPath("textures/block/redstone/wavemaker_overlay.png"));
    }

    @Override
    public float getGlowDelta(WaveMakerBlockEntity blockEntityIn, float delta) {
        return Easing.EXPO_IN.ease(delta, 0, 1);
    }
}
