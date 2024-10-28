package com.sammy.malum.common.block.curiosities.spirit_crucible.catalyzer;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.*;
import org.jetbrains.annotations.*;
import team.lodestar.lodestone.systems.multiblock.MultiBlockComponentEntity;
import team.lodestar.lodestone.systems.multiblock.MultiblockComponentBlock;

import java.util.function.Supplier;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_FACING;

public class SpiritCatalyzerComponentBlock extends MultiblockComponentBlock {
    public static final VoxelShape NORTH_SOUTH_SHAPE = makeNorthSouthShape();
    public static final VoxelShape WEST_EAST_SHAPE = makeWestEastShape();

    private final Supplier<Item> cloneStack;

    public SpiritCatalyzerComponentBlock(Properties properties, Supplier<Item> cloneStack) {
        super(properties);
        this.cloneStack = cloneStack;
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
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HORIZONTAL_FACING);
    }

    @Override
    public @NotNull ItemStack getCloneItemStack(BlockState state, HitResult target, LevelReader level, BlockPos pos, Player player) {
        return cloneStack.get().getDefaultInstance();
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState pState) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState pState, Level pLevel, BlockPos pPos) {
        if (pLevel.getBlockEntity(pPos) instanceof MultiBlockComponentEntity component) {
            IItemHandler inv = Capabilities.ItemHandler.BLOCK.getCapability(pLevel, pPos, pState, component, null);
            if (inv != null) return ItemHandlerHelper.calcRedstoneFromInventory(inv);
        }
        return 0;
    }

    public static VoxelShape makeNorthSouthShape() {
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.0625, 0, 0.3125, 0.25, 0.3125, 0.6875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.25, 0, 0.1875, 0.75, 0.4375, 0.3125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.25, 0, 0.6875, 0.75, 0.4375, 0.8125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.75, 0, 0.3125, 0.9375, 0.3125, 0.6875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(-0.0625, 0.3125, 0.1875, 0.25, 0.625, 0.8125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.75, 0.3125, 0.1875, 1.0625, 0.625, 0.8125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.125, 0.4375, 0.0625, 0.875, 0.6875, 0.9375), BooleanOp.OR);

        return shape;
    }

    public static VoxelShape makeWestEastShape() {
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.3125, 0, 0.75, 0.6875, 0.3125, 0.9375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.1875, 0, 0.25, 0.3125, 0.4375, 0.75), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.6875, 0, 0.25, 0.8125, 0.4375, 0.75), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.3125, 0, 0.0625, 0.6875, 0.3125, 0.25), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.1875, 0.3125, 0.75, 0.8125, 0.625, 1.0625), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.1875, 0.3125, -0.0625, 0.8125, 0.625, 0.25), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.0625, 0.4375, 0.125, 0.9375, 0.6875, 0.875), BooleanOp.OR);

        return shape;
    }
}
