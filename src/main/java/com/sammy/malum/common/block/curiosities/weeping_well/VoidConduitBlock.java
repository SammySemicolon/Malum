package com.sammy.malum.common.block.curiosities.weeping_well;

import com.sammy.malum.core.handlers.*;
import net.minecraft.core.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.material.*;
import net.minecraft.world.phys.shapes.*;
import team.lodestar.lodestone.systems.block.*;

import static com.sammy.malum.common.block.curiosities.weeping_well.PrimordialSoupBlock.*;

public class VoidConduitBlock<T extends VoidConduitBlockEntity> extends LodestoneEntityBlock<T> {
    public VoidConduitBlock(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return TOP_SHAPE;
    }

    @Override
    public VoxelShape getOcclusionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return Shapes.empty();
    }

    @Override
    public VoxelShape getCollisionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        if (pContext instanceof EntityCollisionContext entityCollisionContext) {
            Entity entity = entityCollisionContext.getEntity();
            if (entity != null) {
                return Shapes.empty();
            }
        }
        return getShape(pState, pLevel, pPos, pContext);
    }

    @Override
    public boolean skipRendering(BlockState pState, BlockState pAdjacentBlockState, Direction pDirection) {
        return pAdjacentBlockState.getBlock() instanceof PrimordialSoupBlock || super.skipRendering(pState, pAdjacentBlockState, pDirection);
    }

    @Override
    public void entityInside(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity) {
        if (pEntity instanceof LivingEntity livingEntity) {
            WeepingWellRejectionHandler.handlePrimordialSoupContact(livingEntity);
        }
    }

    @Override
    public boolean canBeReplaced(BlockState pState, Fluid pFluid) {
        return false;
    }
}