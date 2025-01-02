package com.sammy.malum.common.block.curiosities.redstone.wavecharger;

import com.sammy.malum.common.block.curiosities.redstone.SpiritDiodeBlockEntity;
import com.sammy.malum.registry.common.block.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class WaveChargerBlockEntity extends SpiritDiodeBlockEntity {

    public WaveChargerBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.WAVECHARGER.get(), pos, state);
    }
}
