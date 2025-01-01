package com.sammy.malum.common.block.curiosities.redstone.pulsecharger;

import com.sammy.malum.common.block.curiosities.redstone.DirectionalRedstoneDiodeBlock;
import com.sammy.malum.common.block.curiosities.redstone.DirectionalRedstoneDiodeBlockEntity;
import com.sammy.malum.registry.common.SoundRegistry;
import com.sammy.malum.registry.common.block.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.block.state.BlockState;
import team.lodestar.lodestone.helpers.block.BlockStateHelper;

public class PulsechargerBlockEntity extends DirectionalRedstoneDiodeBlockEntity {

    public int desiredSignal;
    public int currentSignal;
    public boolean locked;
    public PulsechargerBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.PULSECHARGER.get(), pos, state);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("desiredSignal", desiredSignal);
        tag.putInt("currentSignal", currentSignal);
        tag.putBoolean("locked", locked);
    }

    @Override
    protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.loadAdditional(pTag, pRegistries);
        desiredSignal = pTag.getInt("desiredSignal");
        currentSignal = pTag.getInt("currentSignal");
        locked = pTag.getBoolean("locked");
    }

    @Override
    public void tick() {
        super.tick();
        if (desiredSignal != currentSignal) {
            if (timer >= Math.max(Math.round(frequency / 15f), 2)) {
                timer = 0;
                if (currentSignal > desiredSignal) {
                    currentSignal--;
                }
                else {
                    currentSignal++;
                }
                level.updateNeighborsAt(getBlockPos(), getBlockState().getBlock());
                if (currentSignal == desiredSignal) {
                    desiredSignal = 0;
                    if (level instanceof ServerLevel) {
                        float pitch = 1.5f;
                        if (currentSignal == 0) {
                            setSignal(DirectionalRedstoneDiodeBlock.SignalInput.NONE);
                            locked = false;
                            pitch = 0.9f;
                        }
                        level.playSound(null, getBlockPos(), SoundRegistry.PULSECHARGER_CHARGE.get(), SoundSource.BLOCKS, 0.3f, pitch);

                        BlockStateHelper.updateAndNotifyState(level, getBlockPos());
                    }
                    else {
                        spawnRedstoneParticles();
                    }
                }
            } else {
                timer++;
            }
        }
    }

    @Override
    public void receiveSignalFromNeighbor(DirectionalRedstoneDiodeBlock.SignalInput signalInput, int signalStrength) {
        if (locked) {
            return;
        }
        locked = true;
        desiredSignal = signalStrength;
        setSignal(signalInput);
    }
}