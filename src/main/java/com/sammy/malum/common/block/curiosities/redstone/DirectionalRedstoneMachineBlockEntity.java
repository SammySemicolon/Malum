package com.sammy.malum.common.block.curiosities.redstone;

import com.sammy.malum.common.block.curiosities.redstone.pulsebank.PulsebankBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import team.lodestar.lodestone.helpers.block.BlockEntityHelper;
import team.lodestar.lodestone.helpers.block.BlockStateHelper;

import static com.sammy.malum.common.block.curiosities.redstone.DirectionalRedstoneMachineBlock.SIGNAL_INPUT;

public abstract class DirectionalRedstoneMachineBlockEntity extends RedstoneMachineBlockEntity {

    public DirectionalRedstoneMachineBlock.SignalInput cachedSignal;

    public DirectionalRedstoneMachineBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public void setSignalCache(DirectionalRedstoneMachineBlock.SignalInput cachedSignal) {
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

    public void setSignal(DirectionalRedstoneMachineBlock.SignalInput signalInput) {
        level.setBlock(getBlockPos(), getBlockState().setValue(SIGNAL_INPUT, signalInput), 3);
    }

    public abstract void receiveSignalFromNeighbor(DirectionalRedstoneMachineBlock.SignalInput signalInput);

    @Override
    public void onNeighborUpdate(BlockState state, BlockPos pos, BlockPos neighbor) {
        if (!state.getValue(SIGNAL_INPUT).equals(PulsebankBlock.SignalInput.NONE)) {
            return;
        }
        BlockPos offset = pos.subtract(neighbor);
        Direction direction = Direction.fromDelta(offset.getX(), offset.getY(), offset.getZ());
        if (direction == null) {
            return;
        }
        int signal = level.getSignal(neighbor, direction.getOpposite());
        if (signal > 0) {
            receiveSignalFromNeighbor(DirectionalRedstoneMachineBlock.SignalInput.DIRECTION_MAP.get(direction));
        }
    }

    @Override
    protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.loadAdditional(pTag, pRegistries);
        if (pTag.contains("cachedSignal")) {
            cachedSignal = DirectionalRedstoneMachineBlock.SignalInput.valueOf(pTag.getString("cachedSignal"));
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