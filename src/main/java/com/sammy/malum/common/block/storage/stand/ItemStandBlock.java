package com.sammy.malum.common.block.storage.stand;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import team.lodestar.lodestone.systems.block.WaterLoggedEntityBlock;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.FACING;

public class ItemStandBlock<T extends ItemStandBlockEntity> extends WaterLoggedEntityBlock<T> {

    public static final VoxelShape UP = Block.box(4, 0, 4, 12, 2, 12);
    public static final VoxelShape DOWN = Block.box(4, 14, 4, 12, 16, 12);
    public static final VoxelShape SOUTH = Block.box(4, 4, 0, 12, 12, 2);
    public static final VoxelShape NORTH = Block.box(4, 4, 14, 12, 12, 16);
    public static final VoxelShape WEST = Block.box(14, 4, 4, 16, 12, 12);
    public static final VoxelShape EAST = Block.box(0, 4, 4, 2, 12, 12);

    public ItemStandBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState pState) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState pState, Level pLevel, BlockPos pPos) {
        BlockEntity be = pLevel.getBlockEntity(pPos);
        if (be instanceof ItemStandBlockEntity stand) {
            return ItemHandlerHelper.calcRedstoneFromInventory(stand.getCapability(pLevel, pPos, pState, stand, null));
        }
        return 0;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        switch (state.getValue(FACING)) {
            case UP -> {
                return UP;
            }
            case DOWN -> {
                return DOWN;
            }
            case SOUTH -> {
                return SOUTH;
            }
            case NORTH -> {
                return NORTH;
            }
            case WEST -> {
                return WEST;
            }
            case EAST -> {
                return EAST;
            }
        }
        return super.getShape(state, level, pos, context);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
        super.createBlockStateDefinition(builder);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext p_152803_) {
        return super.getStateForPlacement(p_152803_).setValue(FACING, p_152803_.getClickedFace());
    }
}
