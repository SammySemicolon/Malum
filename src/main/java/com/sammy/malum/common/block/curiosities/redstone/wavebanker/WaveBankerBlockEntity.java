package com.sammy.malum.common.block.curiosities.redstone.wavebanker;

import com.sammy.malum.common.block.curiosities.redstone.DirectionalRedstoneDiodeBlockEntity;
import com.sammy.malum.common.block.curiosities.redstone.DirectionalRedstoneDiodeBlock;
import com.sammy.malum.registry.common.SoundRegistry;
import com.sammy.malum.registry.common.block.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.block.state.BlockState;

public class WaveBankerBlockEntity extends DirectionalRedstoneDiodeBlockEntity {

    public WaveBankerBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.WAVEBANKER.get(), pos, state);
    }

    @Override
    public void tick() {
        super.tick();
        if (timer > 0) {
            timer--;
            if (timer == 0) {
                if (level instanceof ServerLevel) {
                    setPowered(false);
                } else {
                    spawnRedstoneParticles();
                }
            }
        }
    }

    @Override
    public void receiveSignalFromNeighbor(int signalStrength) {
        level.playSound(null, getBlockPos(), SoundRegistry.WAVEBANKER_STORE.get(), SoundSource.BLOCKS, 0.3f, 1.2f);
        setPowered(true);
        timer = frequency;
    }
}