package com.sammy.malum.common.block.curiosities.redstone.pulselag;

import com.sammy.malum.common.block.curiosities.redstone.DirectionalRedstoneMachineBlock;
import com.sammy.malum.common.block.curiosities.redstone.chronopulser.ChronopulserBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;

public class PulselagBlock extends DirectionalRedstoneMachineBlock<PulselagBlockEntity> {
    public PulselagBlock(Properties properties) {
        super(properties);
    }
}