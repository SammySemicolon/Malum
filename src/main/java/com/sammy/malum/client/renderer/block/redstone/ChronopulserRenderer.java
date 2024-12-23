package com.sammy.malum.client.renderer.block.redstone;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.block.curiosities.redstone.chronopulser.ChronopulserBlockEntity;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.rendering.rendeertype.RenderTypeToken;

import static com.sammy.malum.client.RenderUtils.drawCube;


public class ChronopulserRenderer extends RedstoneMachineRenderer<ChronopulserBlockEntity> {

    private static final RenderTypeToken TOKEN = RenderTypeToken.createToken(MalumMod.malumPath("textures/block/redstone/chronopulser_overlay.png"));

    public ChronopulserRenderer(BlockEntityRendererProvider.Context context) {
        super(context, TOKEN);
    }

    @Override
    public float getGlowDelta(ChronopulserBlockEntity blockEntityIn) {
        return Easing.EXPO_IN.ease((float) blockEntityIn.timer / blockEntityIn.frequency, 0, 1);
    }
}
