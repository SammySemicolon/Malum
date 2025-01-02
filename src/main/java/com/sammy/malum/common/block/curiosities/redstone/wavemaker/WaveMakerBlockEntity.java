package com.sammy.malum.common.block.curiosities.redstone.wavemaker;

import com.sammy.malum.common.block.curiosities.redstone.SpiritDiodeBlockEntity;
import com.sammy.malum.registry.common.block.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;

public class WaveMakerBlockEntity extends SpiritDiodeBlockEntity {

    public boolean inverted;

    public WaveMakerBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.WAVEMAKER.get(), pos, state);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putBoolean("inverted", inverted);
    }

    @Override
    protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.loadAdditional(pTag, pRegistries);
        inverted = pTag.getBoolean("inverted");
    }

    @Override
    public int getOutputSignal() {
        int rawSignal = super.getOutputSignal();
        return inverted ? 15 - rawSignal : rawSignal;
    }
}
