package com.sammy.malum.client.renderer.block.redstone;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.block.curiosities.redstone.chronopulser.ChronopulserBlockEntity;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import team.lodestar.lodestone.systems.easing.Easing;

import static com.sammy.malum.client.RenderUtils.drawCube;


public class ChronopulserRenderer extends RedstoneDiodeRenderer<ChronopulserBlockEntity> {

    public ChronopulserRenderer(BlockEntityRendererProvider.Context context) {
        super(context, MalumMod.malumPath("textures/block/redstone/chronopulser_overlay.png"));
    }

    @Override
    public float getGlowDelta(ChronopulserBlockEntity blockEntityIn) {
        return Easing.EXPO_IN.ease((float) blockEntityIn.timer / blockEntityIn.frequency, 0, 1);
    }
}
