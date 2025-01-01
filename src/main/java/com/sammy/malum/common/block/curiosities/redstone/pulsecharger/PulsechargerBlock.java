package com.sammy.malum.common.block.curiosities.redstone.pulsecharger;

import com.sammy.malum.common.block.curiosities.redstone.DirectionalRedstoneDiodeBlock;
import com.sammy.malum.common.block.curiosities.redstone.RedstoneDiodeBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class PulsechargerBlock extends DirectionalRedstoneDiodeBlock<PulsechargerBlockEntity> {

    public PulsechargerBlock(Properties properties) {
        super(properties);
    }


    @Override
    public boolean getWeakChanges(BlockState state, LevelReader level, BlockPos pos) {
        return true;
    }

    @Override
    protected int getSignal(BlockState blockState, BlockGetter blockAccess, BlockPos pos, Direction side) {
        if (!(blockAccess.getBlockEntity(pos) instanceof PulsechargerBlockEntity pulsecharger)) {
            return 0;
        }
        int signal = super.getSignal(blockState, blockAccess, pos, side);
        if (signal != 0) {
            return pulsecharger.currentSignal;
        }
        return 0;
    }
}