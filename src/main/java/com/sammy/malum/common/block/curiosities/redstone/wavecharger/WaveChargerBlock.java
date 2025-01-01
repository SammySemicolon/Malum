package com.sammy.malum.common.block.curiosities.redstone.wavecharger;

import com.sammy.malum.common.block.curiosities.redstone.DirectionalRedstoneDiodeBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;

public class WaveChargerBlock extends DirectionalRedstoneDiodeBlock<WaveChargerBlockEntity> {

    public WaveChargerBlock(Properties properties) {
        super(properties);
    }


    @Override
    public boolean getWeakChanges(BlockState state, LevelReader level, BlockPos pos) {
        return true;
    }

    @Override
    protected int getSignal(BlockState blockState, BlockGetter blockAccess, BlockPos pos, Direction side) {
        if (!(blockAccess.getBlockEntity(pos) instanceof WaveChargerBlockEntity wavecharger)) {
            return 0;
        }
        int signal = super.getSignal(blockState, blockAccess, pos, side);
        if (signal != 0) {
            return wavecharger.currentSignal;
        }
        return 0;
    }
}