package com.sammy.malum.common.block.curiosities.redstone;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import team.lodestar.lodestone.systems.blockentity.LodestoneBlockEntity;

public class SpiritDiodeBlockEntity extends LodestoneBlockEntity {

    public int delay;
    public int signal;

    public SpiritDiodeBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        if (state.getBlock() instanceof SpiritDiodeBlock<?> diodeBlock) {
            delay = diodeBlock.getDefaultFrequency(pos, state);
        }
    }

    public int getOutputSignal() {
        return Mth.clamp(signal, 0, 15);
    }

    @Override
    protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.loadAdditional(pTag, pRegistries);
        delay = pTag.getInt("delay");
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("delay", delay);
    }
}
