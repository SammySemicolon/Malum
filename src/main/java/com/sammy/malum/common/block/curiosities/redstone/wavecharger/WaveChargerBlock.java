package com.sammy.malum.common.block.curiosities.redstone.wavecharger;

import com.sammy.malum.common.block.curiosities.redstone.SpiritDiodeBlock;
import com.sammy.malum.registry.common.SoundRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class WaveChargerBlock extends SpiritDiodeBlock<WaveChargerBlockEntity> {

    public WaveChargerBlock(Properties properties) {
        super(properties);
    }

    @Override
    public int getDefaultFrequency(BlockPos pos, BlockState state) {
        return 3;
    }

    @Override
    public int[] getFrequencyPresets() {
        return new int[] {3, 6, 12, 24, 48, 96, 192, 384, 768};
    }

    @Override
    public boolean processUpdate(Level level, BlockPos pos, BlockState state, WaveChargerBlockEntity diode, int signal) {
        int startingSignal = diode.signal;
        if (startingSignal > signal) {
            diode.signal--;
        } else if (startingSignal < signal) {
            diode.signal++;
        } else {
            return false;
        }

        updateState(level, pos, state, diode);

        if (diode.signal == signal) {
            level.playSound(null, pos, SoundRegistry.WAVECHARGER_CHARGE.get(), SoundSource.BLOCKS, 0.3f, signal == 0 ? 0.9f : 1.5f);
            emitRedstoneParticles(level, pos);
            return false;
        }

        return true;
    }

    @Override
    public boolean shouldUpdateWhenNeighborChanged(Level level, BlockPos pos, BlockState state, WaveChargerBlockEntity diode, int newInput) {
        return newInput != diode.signal;
    }
}
