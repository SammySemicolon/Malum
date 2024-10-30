package com.sammy.malum.common.block.curiosities.repair_pylon;

import net.minecraft.core.*;
import net.minecraft.world.item.context.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.phys.shapes.*;
import net.neoforged.neoforge.items.*;
import team.lodestar.lodestone.systems.block.*;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.*;

public class RepairPylonCoreBlock<T extends RepairPylonCoreBlockEntity> extends WaterLoggedEntityBlock<T> {

    public static final VoxelShape SHAPE = makeShape();

    public RepairPylonCoreBlock(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState pState) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState pState, Level pLevel, BlockPos pPos) {
        if (pLevel.getBlockEntity(pPos) instanceof RepairPylonCoreBlockEntity pylon) {
            return ItemHandlerHelper.calcRedstoneFromInventory(pylon.inventory);
        }
        return 0;
    }

    public static VoxelShape makeShape(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.625, 0.625, 0.625, 1, 1, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.625, 0, 0.625, 1.125, 0.625, 1.125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.625, 0, -0.125, 1.125, 0.625, 0.375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(-0.125, 0, 0.625, 0.375, 0.625, 1.125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(-0.125, 0, -0.125, 0.375, 0.625, 0.375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0.625, 0.625, 0.375, 1, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0.625, 0, 0.375, 1, 0.375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.0625, 0.5, 0.0625, 0.9375, 1, 0.9375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0, 0, 1, 0.5, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0, 0, 1, 0, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.625, 0.625, 0, 1, 1, 0.375), BooleanOp.OR);
        return shape;
    }
}
