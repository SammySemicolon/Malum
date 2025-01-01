package com.sammy.malum.client.renderer.block.redstone;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.block.curiosities.redstone.wavemaker.WaveMakerBlockEntity;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.rendering.rendeertype.RenderTypeToken;


public class WaveMakerRenderer extends RedstoneDiodeRenderer<WaveMakerBlockEntity> {

    public WaveMakerRenderer(BlockEntityRendererProvider.Context context) {
        super(context, MalumMod.malumPath("textures/block/redstone/wavemaker_overlay.png"));
    }

    @Override
    public float getGlowDelta(WaveMakerBlockEntity blockEntityIn) {
        return Easing.EXPO_IN.ease((float) blockEntityIn.timer / blockEntityIn.frequency, 0, 1);
    }

    @Override
    public RenderTypeToken getTokenForSide(BlockState state, Direction direction) {
        return output;
    }
}
