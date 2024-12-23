package com.sammy.malum.common.block.curiosities.redstone.pulselag;

import com.sammy.malum.common.block.curiosities.redstone.DirectionalRedstoneMachineBlock;
import com.sammy.malum.common.block.curiosities.redstone.DirectionalRedstoneMachineBlockEntity;
import com.sammy.malum.common.block.curiosities.redstone.pulsebank.PulsebankBlockEntity;
import com.sammy.malum.registry.common.block.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import team.lodestar.lodestone.helpers.block.BlockStateHelper;

public class PulselagBlockEntity extends DirectionalRedstoneMachineBlockEntity {

    public PulselagBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.PULSELAG.get(), pos, state);
    }
    @Override
    public void tick() {
        super.tick();
        if (timer > 0) {
            timer--;
            if (timer == 10) {
                if (level instanceof ServerLevel) {
                    setSignalFromCache();
                    scheduleSignalReset(10);
                } else {
                    spawnRedstoneParticles();
                }
            }
        }
    }
    @Override
    public void receiveSignalFromNeighbor(DirectionalRedstoneMachineBlock.SignalInput signalInput) {
        if (timer <= 10) {
            setSignal(DirectionalRedstoneMachineBlock.SignalInput.NONE);
            setSignalCache(signalInput);
            timer = frequency+10;
        }
    }
}