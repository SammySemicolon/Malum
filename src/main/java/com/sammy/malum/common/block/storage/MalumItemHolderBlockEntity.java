package com.sammy.malum.common.block.storage;

import com.sammy.malum.common.item.spirit.SpiritShardItem;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import com.sammy.malum.visual_effects.SpiritLightSpecs;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import team.lodestar.lodestone.helpers.BlockHelper;
import team.lodestar.lodestone.systems.blockentity.ItemHolderBlockEntity;
import team.lodestar.lodestone.systems.blockentity.LodestoneBlockEntityInventory;

public abstract class MalumItemHolderBlockEntity extends ItemHolderBlockEntity implements IMalumSpecialItemAccessPoint {

    public MalumItemHolderBlockEntity(BlockEntityType<? extends MalumItemHolderBlockEntity> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        inventory = new LodestoneBlockEntityInventory(1, 64) {
            @Override
            public void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                BlockHelper.updateAndNotifyState(level, worldPosition);
            }
        };
    }

    @Override
    public LodestoneBlockEntityInventory getSuppliedInventory() {
        return inventory;
    }

    @Override
    public Vec3 getItemPos(float partialTicks) {
        final BlockPos blockPos = getBlockPos();
        final Vec3 offset = getItemOffset(partialTicks);
        return new Vec3(blockPos.getX() + offset.x, blockPos.getY() + offset.y, blockPos.getZ() + offset.z);
    }

    @Override
    public BlockPos getAccessPointBlockPos() {
        return worldPosition;
    }

    @Override
    public void tick() {
        if (level.isClientSide) {
            if (inventory.getStackInSlot(0).getItem() instanceof SpiritShardItem item) {
                MalumSpiritType type = item.type;
                SpiritLightSpecs.rotatingLightSpecs(level, getItemPos(), type, 0.4f, 2);
            }
        }
    }
}