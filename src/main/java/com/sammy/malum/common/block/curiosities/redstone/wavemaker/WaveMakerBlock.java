package com.sammy.malum.common.block.curiosities.redstone.wavemaker;

import com.sammy.malum.common.block.curiosities.redstone.RedstoneDiodeBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class WaveMakerBlock extends RedstoneDiodeBlock<WaveMakerBlockEntity> {
    public WaveMakerBlock(Properties properties) {
        super(properties);
    }
}