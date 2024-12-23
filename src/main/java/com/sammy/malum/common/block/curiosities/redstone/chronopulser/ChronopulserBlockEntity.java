package com.sammy.malum.common.block.curiosities.redstone.chronopulser;

import com.sammy.malum.common.block.curiosities.redstone.RedstoneMachineBlockEntity;
import com.sammy.malum.registry.common.block.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;

public class ChronopulserBlockEntity extends RedstoneMachineBlockEntity {

    public ChronopulserBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.CHRONOPULSER.get(), pos, state);
    }

    @Override
    public void tick() {
        super.tick();
        if (timer == 0) {
            timer = frequency;
            if (level instanceof ServerLevel) {
                level.setBlock(getBlockPos(), getBlockState().setValue(ChronopulserBlock.LIT, true), 3);
                scheduleSignalReset(10);
            } else {
                spawnRedstoneParticles();
            }
        } else {
            timer--;
        }
    }
}