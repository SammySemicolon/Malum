package com.sammy.malum.common.block.blight;

import com.mojang.serialization.*;
import net.minecraft.core.*;
import net.minecraft.server.level.*;
import net.minecraft.util.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.phys.shapes.*;
import net.neoforged.neoforge.common.*;

import static com.sammy.malum.registry.common.block.BlockTagRegistry.*;

public class BlightedGrowthBlock extends BushBlock implements BonemealableBlock, IShearable {

    public static final MapCodec<BlightedGrowthBlock> CODEC = simpleCodec(BlightedGrowthBlock::new);
    protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 8.0D, 14.0D);
    public BlightedGrowthBlock(Properties p_57318_) {
        super(p_57318_);
    }

    @Override
    protected MapCodec<? extends BushBlock> codec() {
        return CODEC;
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    protected boolean mayPlaceOn(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        if (pState.is(BLIGHTED_BLOCKS)) {
            return true;
        }
        return super.mayPlaceOn(pState, pLevel, pPos);
    }


    @Override
    public boolean isValidBonemealTarget(LevelReader levelReader, BlockPos blockPos, BlockState blockState) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(Level pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        final BlockPos below = pPos.below();
        final BlockState state = pLevel.getBlockState(below);
        final Block block = state.getBlock();
        return block instanceof BlightedSoilBlock blightedSoilBlock && blightedSoilBlock.isBonemealSuccess(pLevel, pRandom, below, state);
    }

    @Override
    public void performBonemeal(ServerLevel pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        final BlockPos below = pPos.below();
        final BlockState state = pLevel.getBlockState(below);
        if (state.getBlock() instanceof BlightedSoilBlock blightedSoilBlock) {
            blightedSoilBlock.performBonemeal(pLevel, pRandom, below, state);
        }
    }
}
