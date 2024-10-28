package com.sammy.malum.common.block.nature;

import com.sammy.malum.registry.common.block.*;
import net.minecraft.core.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.grower.*;
import net.minecraft.world.level.block.state.*;

public class MalumSaplingBlock extends SaplingBlock {

    public MalumSaplingBlock(TreeGrower treeGrower, Properties properties) {
        super(treeGrower, properties);
    }

    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        if (pLevel.getBlockState(pPos.below()).is(BlockTagRegistry.BLIGHTED_BLOCKS)) {
            return true;
        }
        return super.canSurvive(pState, pLevel, pPos);
    }
}