package com.sammy.malum.common.block.curiosities.redstone;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import team.lodestar.lodestone.helpers.block.BlockStateHelper;
import team.lodestar.lodestone.systems.blockentity.LodestoneBlockEntity;

public abstract class RedstoneDiodeBlockEntity extends LodestoneBlockEntity {

    public int timer;
    public int frequency = 80;

    public RedstoneDiodeBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public void scheduleSignalReset(int delay) {
        level.scheduleTick(worldPosition, getBlockState().getBlock(), delay);
    }

    public void spawnRedstoneParticles() {
        Vec3 center = getBlockPos().getCenter();
        float offset = 0.625f;
        level.addParticle(DustParticleOptions.REDSTONE, center.x + offset, center.y, center.z, 0, 0, 0);
        level.addParticle(DustParticleOptions.REDSTONE, center.x - offset, center.y, center.z, 0, 0, 0);
        level.addParticle(DustParticleOptions.REDSTONE, center.x, center.y, center.z + offset, 0, 0, 0);
        level.addParticle(DustParticleOptions.REDSTONE, center.x, center.y, center.z - offset, 0, 0, 0);
    }

    @Override
    protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.loadAdditional(pTag, pRegistries);
        timer = pTag.getInt("timer");
        frequency = pTag.getInt("frequency");
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("timer", timer);
        tag.putInt("frequency", frequency);
    }
}