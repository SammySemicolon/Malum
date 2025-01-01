package com.sammy.malum.common.block.curiosities.redstone;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import static com.sammy.malum.common.block.curiosities.redstone.DirectionalRedstoneDiodeBlock.FACING;
import static com.sammy.malum.common.block.curiosities.redstone.RedstoneDiodeBlock.POWERED;

public abstract class DirectionalRedstoneDiodeBlockEntity extends RedstoneDiodeBlockEntity {

    public DirectionalRedstoneDiodeBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public void setPowered(boolean powered) {
        level.setBlock(getBlockPos(), getBlockState().setValue(POWERED, powered), 3);
    }

    public abstract void receiveSignalFromNeighbor(int signalStrength);

    @Override
    public void onNeighborUpdate(BlockState state, BlockPos pos, BlockPos neighbor) {
        if (state.getValue(POWERED)) {
            return;
        }
        BlockState neighborState = level.getBlockState(neighbor);
        if (neighborState.getBlock() instanceof RedstoneDiodeBlock<?>) {
            return;
        }
        Direction direction = state.getValue(FACING);
        if (!pos.relative(direction).equals(neighbor)) {
            return;
        }
        int signal = level.getSignal(neighbor, direction);
        if (signal > 0) {
            receiveSignalFromNeighbor(signal);
        }
    }
}