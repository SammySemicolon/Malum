package com.sammy.malum.common.block.nature;

import com.sammy.malum.registry.common.item.*;
import net.minecraft.core.*;
import net.minecraft.sounds.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.phys.*;
import net.minecraft.world.phys.shapes.*;

import java.awt.*;

import static com.sammy.malum.MalumMod.RANDOM;

public class MalumHangingLeavesBlock extends Block implements SimpleWaterloggedBlock, iGradientedLeavesBlock {

    protected static final VoxelShape SHAPE = Block.box(3.0D, 3.0D, 3.0D, 13.0D, 16.0D, 13.0D);

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final IntegerProperty COLOR = MalumLeavesBlock.COLOR;

    public final Color maxColor;
    public final Color minColor;

    public MalumHangingLeavesBlock(Properties properties, Color maxColor, Color minColor) {
        super(properties);
        this.maxColor = maxColor;
        this.minColor = minColor;
        registerDefaultState(defaultBlockState().setValue(COLOR, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(COLOR, WATERLOGGED);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return super.getStateForPlacement(context).setValue(COLOR, 0);
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        if (pFacing.equals(Direction.UP)) {
            if (pFacingState.hasProperty(COLOR)) {
                return super.updateShape(pState.setValue(COLOR, pFacingState.getValue(COLOR)), pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
            }
        }
        return !pState.canSurvive(pLevel, pCurrentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        if (stack.getItem().equals(ItemRegistry.INFERNAL_SPIRIT.get())) {
            level.setBlockAndUpdate(pos, state.setValue(COLOR, (state.getValue(COLOR) + 1) % 5));
            player.swing(handIn);
            player.playSound(SoundEvents.BLAZE_SHOOT, 1F, 1.5f + RANDOM.nextFloat() * 0.5f);
            return ItemInteractionResult.SUCCESS;
        }
        return super.useItemOn(stack, state, level, pos, player, handIn, hit);
    }

    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        var block = pLevel.getBlockState(pPos.above()).getBlock();
        return block instanceof LeavesBlock;
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public Color getMaxColor() {
        return maxColor;
    }

    @Override
    public Color getMinColor() {
        return minColor;
    }
}