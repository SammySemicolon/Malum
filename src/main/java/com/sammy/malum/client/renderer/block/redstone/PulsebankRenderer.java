package com.sammy.malum.client.renderer.block.redstone;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.block.curiosities.redstone.pulsebank.PulsebankBlockEntity;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import team.lodestar.lodestone.systems.easing.Easing;


public class PulsebankRenderer extends RedstoneDiodeRenderer<PulsebankBlockEntity> {

    public PulsebankRenderer(BlockEntityRendererProvider.Context context) {
        super(context, MalumMod.malumPath("textures/block/redstone/pulsebank_overlay.png"));
    }

    @Override
    public float getGlowDelta(PulsebankBlockEntity blockEntityIn) {
        return Easing.QUARTIC_OUT.ease((float) blockEntityIn.timer / blockEntityIn.frequency, 0, 1);
    }
}