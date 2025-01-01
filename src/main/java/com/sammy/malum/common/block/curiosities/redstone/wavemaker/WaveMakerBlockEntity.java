package com.sammy.malum.common.block.curiosities.redstone.wavemaker;

import com.sammy.malum.common.block.curiosities.redstone.RedstoneDiodeBlockEntity;
import com.sammy.malum.registry.common.SoundRegistry;
import com.sammy.malum.registry.common.block.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.block.state.BlockState;

public class WaveMakerBlockEntity extends RedstoneDiodeBlockEntity {

    public WaveMakerBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.WAVEMAKER.get(), pos, state);
    }

    @Override
    public void tick() {
        super.tick();
        if (timer == 0) {
            timer = frequency;
            if (level instanceof ServerLevel) {
                level.setBlock(getBlockPos(), getBlockState().setValue(WaveMakerBlock.POWERED, true), 3);
                level.playSound(null, getBlockPos(), SoundRegistry.WAVEMAKER_PULSE.get(), SoundSource.BLOCKS, 0.3f, 1.8f);
                scheduleSignalReset(10);
            } else {
                spawnRedstoneParticles();
            }
        } else {
            timer--;
        }
    }
}