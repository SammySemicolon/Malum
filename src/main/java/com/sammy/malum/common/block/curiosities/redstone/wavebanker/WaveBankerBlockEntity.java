package com.sammy.malum.common.block.curiosities.redstone.wavebanker;

import com.sammy.malum.common.block.curiosities.redstone.SpiritDiodeBlockEntity;
import com.sammy.malum.registry.common.block.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class WaveBankerBlockEntity extends SpiritDiodeBlockEntity {

    public WaveBankerBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.WAVEBANKER.get(), pos, state);
    }
}
