package com.sammy.malum.common.block.curiosities.redstone.pulsebank;

import com.sammy.malum.common.block.curiosities.redstone.DirectionalRedstoneMachineBlockEntity;
import com.sammy.malum.common.block.curiosities.redstone.DirectionalRedstoneMachineBlock;
import com.sammy.malum.registry.common.SoundRegistry;
import com.sammy.malum.registry.common.block.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class PulsebankBlockEntity extends DirectionalRedstoneMachineBlockEntity {

    public PulsebankBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.PULSEBANK.get(), pos, state);
    }

    @Override
    public void tick() {
        super.tick();
        if (timer > 0) {
            timer--;
            if (timer == 0) {
                if (level instanceof ServerLevel) {
                    setSignal(DirectionalRedstoneMachineBlock.SignalInput.NONE);
                } else {
                    spawnRedstoneParticles();
                }
            }
        }
    }

    @Override
    public void receiveSignalFromNeighbor(DirectionalRedstoneMachineBlock.SignalInput signalInput) {
        level.playSound(null, getBlockPos(), SoundRegistry.PULSEBANK_STORE.get(), SoundSource.BLOCKS, 0.3f, 1.2f);
        setSignal(signalInput);
        timer = frequency;
    }
}