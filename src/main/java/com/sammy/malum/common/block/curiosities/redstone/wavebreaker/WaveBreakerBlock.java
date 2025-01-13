package com.sammy.malum.common.block.curiosities.redstone.wavebreaker;

import com.sammy.malum.common.block.curiosities.redstone.SpiritDiodeBlock;
import com.sammy.malum.registry.common.SoundRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class WaveBreakerBlock extends SpiritDiodeBlock<WaveBreakerBlockEntity> {
    public WaveBreakerBlock(Properties properties) {
        super(properties);
    }

    @Override
    public int getDefaultFrequency(BlockPos pos, BlockState state) {
        return 10;
    }

    @Override
    public int[] getFrequencyPresets() {
        return new int[] {10, 20, 40, 80, 160, 320, 640, 1280, 2560};
    }

    @Override
    public boolean processUpdate(Level level, BlockPos pos, BlockState state, WaveBreakerBlockEntity diode, int signal) {
        if (diode.signal == diode.pendingSignal) {
            if (signal != diode.pendingSignal) {
                diode.pendingSignal = signal;
                level.playSound(null, pos, SoundRegistry.WAVEBREAKER_STORE.get(), SoundSource.BLOCKS, 0.3f, 1.5f);
                emitRedstoneParticles(level, pos);
                return true;
            }

            return false;
        } else {
            diode.signal = diode.pendingSignal;

            level.playSound(null, pos, SoundRegistry.WAVEBREAKER_RELEASE.get(), SoundSource.BLOCKS, 0.3f, 1.5f);
            updateState(level, pos, state, diode);
            emitRedstoneParticles(level, pos);

            return signal != diode.signal;
        }
    }



    @Override
    public int redstoneTicksUntilUpdate(Level level, BlockPos pos, BlockState state, WaveBreakerBlockEntity diode, int newInput) {
        if (diode.pendingSignal != diode.signal)
            return super.redstoneTicksUntilUpdate(level, pos, state, diode, newInput);
        else
            return 2; // One redstone tick
    }

    @Override
    public boolean shouldUpdateWhenNeighborChanged(Level level, BlockPos pos, BlockState state, WaveBreakerBlockEntity diode, int newInput) {
		return newInput != diode.pendingSignal;
	}
}
