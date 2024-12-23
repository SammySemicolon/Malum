package com.sammy.malum.common.block.curiosities.redstone;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import team.lodestar.lodestone.systems.block.LodestoneEntityBlock;

public class RedstoneMachineBlock<T extends RedstoneMachineBlockEntity> extends LodestoneEntityBlock<T> {

    public RedstoneMachineBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected boolean isSignalSource(BlockState state) {
        return true;
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        level.setBlock(pos, defaultBlockState(), 3);
    }
}