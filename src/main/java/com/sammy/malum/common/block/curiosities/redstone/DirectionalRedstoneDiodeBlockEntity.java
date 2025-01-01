package com.sammy.malum.common.block.curiosities.redstone;

import com.sammy.malum.common.block.curiosities.redstone.pulsebank.PulsebankBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import team.lodestar.lodestone.helpers.block.BlockStateHelper;

import static com.sammy.malum.common.block.curiosities.redstone.DirectionalRedstoneDiodeBlock.SIGNAL_INPUT;

public abstract class DirectionalRedstoneDiodeBlockEntity extends RedstoneDiodeBlockEntity {

    public DirectionalRedstoneDiodeBlock.SignalInput cachedSignal;

    public DirectionalRedstoneDiodeBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public void setSignalCache(DirectionalRedstoneDiodeBlock.SignalInput cachedSignal) {
        this.cachedSignal = cachedSignal;
        BlockStateHelper.updateAndNotifyState(level, getBlockPos());
    }

    public void setSignalFromCache() {
        if (cachedSignal != null) {
            setSignal(cachedSignal);
            cachedSignal = null;
            BlockStateHelper.updateState(level, getBlockPos());
        }
    }

    public void setSignal(DirectionalRedstoneDiodeBlock.SignalInput signalInput) {
        level.setBlock(getBlockPos(), getBlockState().setValue(SIGNAL_INPUT, signalInput), 3);
    }

    public abstract void receiveSignalFromNeighbor(DirectionalRedstoneDiodeBlock.SignalInput signalInput, int signalStrength);

    @Override
    public void onNeighborUpdate(BlockState state, BlockPos pos, BlockPos neighbor) {
        if (!state.getValue(SIGNAL_INPUT).equals(PulsebankBlock.SignalInput.NONE)) {
            return;
        }
        BlockState neighborState = level.getBlockState(neighbor);
        if (neighborState.getBlock() instanceof RedstoneDiodeBlock<?>) {
            return;
        }
        BlockPos offset = pos.subtract(neighbor);
        Direction direction = Direction.fromDelta(offset.getX(), offset.getY(), offset.getZ());
        if (direction == null) {
            return;
        }
        int signal = level.getSignal(neighbor, direction.getOpposite());
        if (signal > 0) {
            receiveSignalFromNeighbor(DirectionalRedstoneDiodeBlock.SignalInput.DIRECTION_MAP.get(direction), signal);
        }
    }

    @Override
    protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.loadAdditional(pTag, pRegistries);
        if (pTag.contains("cachedSignal")) {
            cachedSignal = DirectionalRedstoneDiodeBlock.SignalInput.valueOf(pTag.getString("cachedSignal"));
        }
        else {
            cachedSignal = null;
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        if (cachedSignal != null) {
            tag.putString("cachedSignal", cachedSignal.toString());
        }
    }
}