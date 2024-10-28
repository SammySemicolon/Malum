package com.sammy.malum.common.block.curiosities.spirit_crucible.catalyzer;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import team.lodestar.lodestone.systems.block.WaterLoggedEntityBlock;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_FACING;

public class SpiritCatalyzerCoreBlock<T extends SpiritCatalyzerCoreBlockEntity> extends WaterLoggedEntityBlock<T> {
    public static final VoxelShape NORTH_SOUTH_SHAPE = makeNorthSouthShape();
    public static final VoxelShape WEST_EAST_SHAPE = makeWestEastShape();

    public SpiritCatalyzerCoreBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(HORIZONTAL_FACING, Direction.NORTH));
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        switch (pState.getValue(HORIZONTAL_FACING)) {
            case NORTH, SOUTH -> {
                return NORTH_SOUTH_SHAPE;
            }
            case EAST, WEST -> {
                return WEST_EAST_SHAPE;
            }
        }
        return super.getShape(pState, pLevel, pPos, pContext);
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState pState) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState pState, Level pLevel, BlockPos pPos) {
        if (pLevel.getBlockEntity(pPos) instanceof SpiritCatalyzerCoreBlockEntity catalyzer) {
            IItemHandler inv = Capabilities.ItemHandler.BLOCK.getCapability(pLevel, pPos, pState, catalyzer, null);
            if (inv != null) return ItemHandlerHelper.calcRedstoneFromInventory(inv);
        }
        return 0;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return super.getStateForPlacement(context).setValue(HORIZONTAL_FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HORIZONTAL_FACING);
        super.createBlockStateDefinition(builder);
    }

    public static VoxelShape makeNorthSouthShape() {
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.25, 0.3125, 0.1875, 0.75, 1, 0.8125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0, 0.625, 0.375, 0.3125, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.375, 0, 0.0625, 0.625, 0.1875, 0.1875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.0625, 0.3125, 0.3125, 0.25, 1, 0.6875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.75, 0.3125, 0.3125, 0.9375, 1, 0.6875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.0625, 0, 0.375, 0.375, 0.3125, 0.625), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.625, 0, 0.375, 0.9375, 0.3125, 0.625), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.375, 0, 0.8125, 0.625, 0.1875, 0.9375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.375, 0, 0.1875, 0.625, 0.3125, 0.8125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.625, 0, 0.625, 1, 0.3125, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0, 0, 0.375, 0.3125, 0.375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.625, 0, 0, 1, 0.3125, 0.375), BooleanOp.OR);

        return shape;
    }

    public static VoxelShape makeWestEastShape() {
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.1875, 0.3125, 0.25, 0.8125, 1, 0.75), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.625, 0, 0.625, 1, 0.3125, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.0625, 0, 0.375, 0.1875, 0.1875, 0.625), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.3125, 0.3125, 0.75, 0.6875, 1, 0.9375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.3125, 0.3125, 0.0625, 0.6875, 1, 0.25), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.375, 0, 0.625, 0.625, 0.3125, 0.9375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.375, 0, 0.0625, 0.625, 0.3125, 0.375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.8125, 0, 0.375, 0.9375, 0.1875, 0.625), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.1875, 0, 0.375, 0.8125, 0.3125, 0.625), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.625, 0, 0, 1, 0.3125, 0.375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0, 0.625, 0.375, 0.3125, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0, 0, 0.375, 0.3125, 0.375), BooleanOp.OR);

        return shape;
    }
}
