package com.sammy.malum.common.block.curiosities.ritual_plinth;

import com.sammy.malum.common.block.curiosities.spirit_altar.*;
import net.minecraft.core.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.phys.shapes.*;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import team.lodestar.lodestone.systems.block.*;

public class RitualPlinthBlock<T extends RitualPlinthBlockEntity> extends WaterLoggedEntityBlock<T> {
//    public static final VoxelShape SHAPE = makeShape();
//    public static final VoxelShape RENDER_SHAPE = makeRenderShape();

    public RitualPlinthBlock(Properties properties) {
        super(properties);
    }

//    @Override
//    public VoxelShape getInteractionShape(BlockState p_60547_, BlockGetter p_60548_, BlockPos p_60549_) {
//        return SHAPE;
//    }
//
//    @Override
//    public VoxelShape getCollisionShape(BlockState p_60572_, BlockGetter p_60573_, BlockPos p_60574_, CollisionContext p_60575_) {
//        return SHAPE;
//    }
//
//    @Override
//    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
//        return RENDER_SHAPE;
//    }

    @Override
    public int getAnalogOutputSignal(BlockState pState, Level pLevel, BlockPos pPos) {
        BlockEntity be = pLevel.getBlockEntity(pPos);
        if (be instanceof RitualPlinthBlockEntity altar) {
            return ItemHandlerHelper.calcRedstoneFromInventory(altar.getCapability(pLevel, pPos, pState, altar, null));
        }
        return 0;
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState pState) {
        return true;
    }

//    public static VoxelShape makeShape() {
//        VoxelShape shape = Shapes.empty();
//        shape = Shapes.join(shape, Shapes.box(0.0625, 0, 0.0625, 0.9375, 0.25, 0.9375), BooleanOp.OR);
//        shape = Shapes.join(shape, Shapes.box(0.1875, 0.25, 0.1875, 0.8125, 0.625, 0.8125), BooleanOp.OR);
//        shape = Shapes.join(shape, Shapes.box(0, 0.625, 0, 1, 1, 1), BooleanOp.OR);
//        shape = Shapes.join(shape, Shapes.box(0, 1, 0, 0.1875, 1.0625, 0.1875), BooleanOp.OR);
//        shape = Shapes.join(shape, Shapes.box(0, 1, 0.8125, 0.1875, 1.0625, 1), BooleanOp.OR);
//        shape = Shapes.join(shape, Shapes.box(0.8125, 1, 0, 1, 1.0625, 0.1875), BooleanOp.OR);
//        shape = Shapes.join(shape, Shapes.box(0.8125, 1, 0.8125, 1, 1.0625, 1), BooleanOp.OR);
//        shape = Shapes.join(shape, Shapes.box(0.8125, 0, 0.3125, 1, 0.375, 0.6875), BooleanOp.OR);
//        shape = Shapes.join(shape, Shapes.box(0.3125, 0, 0, 0.6875, 0.375, 0.1875), BooleanOp.OR);
//        shape = Shapes.join(shape, Shapes.box(0.3125, 0, 0.8125, 0.6875, 0.375, 1), BooleanOp.OR);
//        shape = Shapes.join(shape, Shapes.box(0, 0, 0.3125, 0.1875, 0.375, 0.6875), BooleanOp.OR);
//
//        return shape;
//    }
//
//    public static VoxelShape makeRenderShape() {
//        VoxelShape shape = Shapes.empty();
//        shape = Shapes.join(shape, Shapes.box(0.0625, 0, 0.0625, 0.9375, 0.25, 0.9375), BooleanOp.OR);
//        shape = Shapes.join(shape, Shapes.box(0.1875, 0.25, 0.1875, 0.8125, 0.625, 0.8125), BooleanOp.OR);
//        shape = Shapes.join(shape, Shapes.box(0, 0.625, 0, 1, 1, 1), BooleanOp.OR);
//        shape = Shapes.join(shape, Shapes.box(-0.125, 0.5625, -0.125, 0.1875, 1.0625, 0.1875), BooleanOp.OR);
//        shape = Shapes.join(shape, Shapes.box(-0.125, 0.5625, 0.8125, 0.1875, 1.0625, 1.125), BooleanOp.OR);
//        shape = Shapes.join(shape, Shapes.box(0.8125, 0.5625, -0.125, 1.125, 1.0625, 0.1875), BooleanOp.OR);
//        shape = Shapes.join(shape, Shapes.box(0.8125, 0.5625, 0.8125, 1.125, 1.0625, 1.125), BooleanOp.OR);
//        shape = Shapes.join(shape, Shapes.box(0.8125, 0, 0.3125, 1, 0.375, 0.6875), BooleanOp.OR);
//        shape = Shapes.join(shape, Shapes.box(0.3125, 0, 0, 0.6875, 0.375, 0.1875), BooleanOp.OR);
//        shape = Shapes.join(shape, Shapes.box(0.3125, 0, 0.8125, 0.6875, 0.375, 1), BooleanOp.OR);
//        shape = Shapes.join(shape, Shapes.box(0, 0, 0.3125, 0.1875, 0.375, 0.6875), BooleanOp.OR);
//
//        return shape;
//    }
}
