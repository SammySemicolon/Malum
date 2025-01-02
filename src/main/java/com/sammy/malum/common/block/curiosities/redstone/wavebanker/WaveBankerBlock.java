package com.sammy.malum.common.block.curiosities.redstone.wavebanker;

import com.sammy.malum.common.block.curiosities.redstone.SpiritDiodeBlock;
import com.sammy.malum.registry.common.SoundRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class WaveBankerBlock extends SpiritDiodeBlock<WaveBankerBlockEntity> {
    public WaveBankerBlock(Properties properties) {
        super(properties);
    }

    @Override
    public int getDefaultFrequency(BlockPos pos, BlockState state) {
        return 20;
    }

    @Override
    public boolean processUpdate(Level level, BlockPos pos, BlockState state, WaveBankerBlockEntity diode, int signal) {
        int previousSignal = diode.signal;
        diode.signal = signal;

        if (previousSignal > diode.signal) {
            level.playSound(null, pos, SoundRegistry.WAVEBANKER_STORE.get(), SoundSource.BLOCKS, 0.3f, 1.2f);
            emitRedstoneParticles(level, pos);
        }
        updateState(level, pos, state, diode);

        return false;
    }

    @Override
    public boolean shouldUpdateWhenNeighborChanged(Level level, BlockPos pos, BlockState state, WaveBankerBlockEntity diode, int newInput) {
        return newInput != diode.signal;
    }

    @Override
    public int redstoneTicksUntilUpdate(Level level, BlockPos pos, BlockState state, WaveBankerBlockEntity diode, int newInput) {
        if (newInput < diode.signal)
            return super.redstoneTicksUntilUpdate(level, pos, state, diode, newInput);
        else
            return 2; // One redstone tick
    }
}
