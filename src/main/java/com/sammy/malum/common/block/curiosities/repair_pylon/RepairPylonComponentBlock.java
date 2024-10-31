package com.sammy.malum.common.block.curiosities.repair_pylon;

import net.minecraft.core.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.phys.*;
import net.minecraft.world.phys.shapes.*;
import net.neoforged.neoforge.items.*;
import org.jetbrains.annotations.*;
import team.lodestar.lodestone.systems.multiblock.*;

import java.util.function.*;

public class RepairPylonComponentBlock extends MultiblockComponentBlock {

    public static final VoxelShape TOP_SHAPE = makeTopShape();
    public static final VoxelShape MIDDLE_SHAPE = makeMiddleShape();

    public static final BooleanProperty TOP = BooleanProperty.create("top");

    private final Supplier<Item> cloneStack;

    public RepairPylonComponentBlock(Properties properties, Supplier<Item> cloneStack) {
        super(properties);
        this.cloneStack = cloneStack;
        this.registerDefaultState(this.stateDefinition.any().setValue(TOP, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(TOP);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return pState.getValue(TOP) ? TOP_SHAPE : MIDDLE_SHAPE;
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
//        if (pLevel.getBlockEntity(pPos) instanceof MultiBlockComponentEntity component) {
//            return ItemHandlerHelper.calcRedstoneFromInventory(component.getCapability(pLevel, pPos, pState, stand, Direction.UP));
//        }
        return 0;
    }

    public static VoxelShape makeTopShape(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(-0.0625, -0.125, -0.0625, 0.3125, 0.4375, 0.3125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(-0.0625, -0.125, 0.6875, 0.3125, 0.4375, 1.0625), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.6875, -0.125, 0.6875, 1.0625, 0.4375, 1.0625), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.6875, -0.125, -0.0625, 1.0625, 0.4375, 0.3125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.0625, 0, 0.0625, 0.9375, 0.3125, 0.9375), BooleanOp.OR);

        return shape;
    }
    
    public static VoxelShape makeMiddleShape() {
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0, 0, 0, 0.375, 0.25, 0.375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.625, 0, 0, 1, 0.25, 0.375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.625, 0, 0.625, 1, 0.25, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0, 0.625, 0.375, 0.25, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.5625, 0.25, 0.3125, 0.9375, 1, 0.6875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.0625, 0.25, 0.3125, 0.4375, 1, 0.6875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.3125, 0.25, 0.5625, 0.6875, 1, 0.9375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.3125, 0.25, 0.0625, 0.6875, 1, 0.4375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.0625, 0, 0.0625, 0.9375, 0.25, 0.9375), BooleanOp.OR);

        return shape;
    }
}
