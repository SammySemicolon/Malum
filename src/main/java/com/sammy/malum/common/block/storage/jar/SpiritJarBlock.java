package com.sammy.malum.common.block.storage.jar;

import com.sammy.malum.registry.common.SpiritTypeRegistry;
import io.github.fabricators_of_create.porting_lib.transfer.item.ItemHandlerHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import team.lodestar.lodestone.helpers.block.*;
import team.lodestar.lodestone.systems.block.WaterLoggedEntityBlock;

public class SpiritJarBlock<T extends SpiritJarBlockEntity> extends WaterLoggedEntityBlock<T> {
    public static final VoxelShape SHAPE = makeShape();

    public SpiritJarBlock(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        return SHAPE;
    }

    @Override
    public void attack(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer) {
        handleAttack(pLevel, pPos, pPlayer);
    }

    public boolean handleAttack(Level pLevel, BlockPos pPos, Player pPlayer) {
        BlockEntity be = pLevel.getBlockEntity(pPos);
        if (be instanceof SpiritJarBlockEntity jar) {
            var jarHandler = jar.inventory;
            ItemStack item = jarHandler.get().extractItem(0, pPlayer.isShiftKeyDown() ? 64 : 1, false);
            if (!item.isEmpty()) {
                ItemHandlerHelper.giveItemToPlayer(pPlayer, item, pPlayer.getInventory().selected);
                if (!pLevel.isClientSide) {
                    BlockStateHelper.updateAndNotifyState(pLevel, pPos);
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState pState) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState pState, Level pLevel, BlockPos pPos) {
        BlockEntity be = pLevel.getBlockEntity(pPos);
        if (be instanceof SpiritJarBlockEntity jar) {
            if (jar.type == null)
                return 0;
            return Math.min(SpiritTypeRegistry.getIndexForSpiritType(jar.type) + 1, 15);
        }
        return 0;
    }

    public static VoxelShape makeShape() {
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.15625, 0.03125, 0.15625, 0.84375, 0.84375, 0.84375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.21875, 0.90625, 0.21875, 0.78125, 1.03125, 0.78125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.28125, 0.84375, 0.28125, 0.71875, 0.90625, 0.71875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.34375, -0.03125, 0.34375, 0.65625, 0.09375, 0.65625), BooleanOp.OR);
        return shape;
    }
}