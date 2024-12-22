package com.sammy.malum.common.block.curiosities.redstone;

import com.sammy.malum.registry.common.block.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import team.lodestar.lodestone.helpers.block.BlockStateHelper;
import team.lodestar.lodestone.systems.blockentity.LodestoneBlockEntity;

import static com.sammy.malum.common.block.curiosities.redstone.PulsebankBlock.SIGNAL_INPUT;

public class PulsebankBlockEntity extends LodestoneBlockEntity {

    public int timer;
    public PulsebankBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.PULSEBANK.get(), pos, state);
    }

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
            timer = 40;
            level.setBlock(pos, state.setValue(SIGNAL_INPUT, PulsebankBlock.SignalInput.DIRECTION_MAP.get(direction)), 3);
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (timer > 0) {
            timer--;
            if (timer == 0) {
                level.setBlock(getBlockPos(), getBlockState().setValue(PulsebankBlock.SIGNAL_INPUT, PulsebankBlock.SignalInput.NONE), 3);
            }
        }
    }

    @Override
    protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.loadAdditional(pTag, pRegistries);
        timer = pTag.getInt("timer");
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("timer", timer);
    }
}