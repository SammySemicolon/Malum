package com.sammy.malum.common.block.nature.soulwood;

import com.sammy.malum.common.block.nature.*;
import com.sammy.malum.common.item.spirit.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.core.*;
import net.minecraft.server.level.*;
import net.minecraft.sounds.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.grower.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.phys.*;

import static com.sammy.malum.registry.common.block.BlockTagRegistry.*;

public class SoulwoodGrowthBlock extends MalumSaplingBlock {
    public SoulwoodGrowthBlock(TreeGrower treeGrower, Properties properties) {
        super(treeGrower, properties);
    }

    @Override
    protected boolean mayPlaceOn(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        if (pState.is(BLIGHTED_BLOCKS)) {
            return true;
        }
        return super.mayPlaceOn(pState, pLevel, pPos);
    }

    @Override
    public void performBonemeal(ServerLevel pLevel, RandomSource pRand, BlockPos pPos, BlockState pState) {
        super.performBonemeal(pLevel, pRand, pPos, pState);
        pLevel.levelEvent(1505, pPos, 0);
        pLevel.playSound(null, pPos, SoundRegistry.MINOR_BLIGHT_MOTIF.get(), SoundSource.BLOCKS, 1, 0.9f + pLevel.random.nextFloat() * 0.25f);
        pLevel.playSound(null, pPos, SoundEvents.BONE_MEAL_USE, SoundSource.BLOCKS, 1, 0.9f + pLevel.random.nextFloat() * 0.25f);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack itemInHand, BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand hand, BlockHitResult hitResult) {
        if (itemInHand.getItem() instanceof SpiritShardItem) {
            if (pLevel instanceof ServerLevel serverLevel) {
                performBonemeal(serverLevel, pLevel.random, pPos, pState);
            }
            if (!pPlayer.isCreative()) {
                itemInHand.shrink(1);
            }
            return ItemInteractionResult.SUCCESS;
        }
        return super.useItemOn(itemInHand, pState, pLevel, pPos, pPlayer, hand, hitResult);
    }
}