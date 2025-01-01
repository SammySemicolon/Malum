
package com.sammy.malum.common.block.curiosities.redstone.pulselag;

import com.sammy.malum.common.block.curiosities.redstone.DirectionalRedstoneDiodeBlock;
import com.sammy.malum.common.block.curiosities.redstone.DirectionalRedstoneDiodeBlockEntity;
import com.sammy.malum.registry.common.SoundRegistry;
import com.sammy.malum.registry.common.block.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.block.state.BlockState;

public class PulselagBlockEntity extends DirectionalRedstoneDiodeBlockEntity {

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
                    level.playSound(null, getBlockPos(), SoundRegistry.PULSELAG_RELEASE.get(), SoundSource.BLOCKS, 0.3f, 1.5f);
                    setSignalFromCache();
                    scheduleSignalReset(10);
                } else {
                    spawnRedstoneParticles();
                }
            }
        }
    }
    @Override
    public void receiveSignalFromNeighbor(DirectionalRedstoneDiodeBlock.SignalInput signalInput, int signalStrength) {
        if (timer <= 10) {
            level.playSound(null, getBlockPos(), SoundRegistry.PULSELAG_STORE.get(), SoundSource.BLOCKS, 0.3f, 0.9f);
            setSignal(DirectionalRedstoneDiodeBlock.SignalInput.NONE);
            setSignalCache(signalInput);
            timer = frequency+10;
        }
    }
}