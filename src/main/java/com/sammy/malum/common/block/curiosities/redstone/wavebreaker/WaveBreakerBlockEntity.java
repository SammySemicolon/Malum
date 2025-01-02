
package com.sammy.malum.common.block.curiosities.redstone.wavebreaker;

import com.sammy.malum.common.block.curiosities.redstone.SpiritDiodeBlockEntity;
import com.sammy.malum.registry.common.block.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;

public class WaveBreakerBlockEntity extends SpiritDiodeBlockEntity {

    public int pendingSignal;

    public WaveBreakerBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.WAVEBREAKER.get(), pos, state);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("pending", pendingSignal);
    }

    @Override
    protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.loadAdditional(pTag, pRegistries);
        pendingSignal = pTag.getInt("pending");
    }
}
