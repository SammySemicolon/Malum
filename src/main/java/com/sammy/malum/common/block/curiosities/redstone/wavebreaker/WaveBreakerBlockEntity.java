
package com.sammy.malum.common.block.curiosities.redstone.wavebreaker;

import com.sammy.malum.common.block.curiosities.redstone.DirectionalRedstoneDiodeBlock;
import com.sammy.malum.common.block.curiosities.redstone.DirectionalRedstoneDiodeBlockEntity;
import com.sammy.malum.registry.common.SoundRegistry;
import com.sammy.malum.registry.common.block.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.block.state.BlockState;

public class WaveBreakerBlockEntity extends DirectionalRedstoneDiodeBlockEntity {

    public WaveBreakerBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.WAVEBREAKER.get(), pos, state);
    }
    @Override
    public void tick() {
        super.tick();
        if (timer > 0) {
            timer--;
            if (timer == 10) {
                if (level instanceof ServerLevel) {
                    level.playSound(null, getBlockPos(), SoundRegistry.PULSELAG_RELEASE.get(), SoundSource.BLOCKS, 0.3f, 1.5f);
                    setPowered(true);
                    scheduleSignalReset(10);
                } else {
                    spawnRedstoneParticles();
                }
            }
        }
    }
    @Override
    public void receiveSignalFromNeighbor(int signalStrength) {
        if (timer <= 10) {
            level.playSound(null, getBlockPos(), SoundRegistry.PULSELAG_STORE.get(), SoundSource.BLOCKS, 0.3f, 0.9f);
            setPowered(false);
            timer = frequency+10;
        }
    }
}