package com.sammy.malum.common.block.nature.soulwood;

import com.sammy.malum.common.block.nature.SapFilledLogBlock;
import com.sammy.malum.registry.common.SoundRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import java.awt.*;
import java.util.function.Supplier;

public class SapFilledSoulwoodLogBlock extends SapFilledLogBlock {
    public SapFilledSoulwoodLogBlock(Properties properties, Supplier<Block> drained, Supplier<Item> sap, Color... sapColor) {
        super(properties, drained, sap, sapColor);
    }

    @Override
    public void collectSap(Level level, BlockPos pos, Player player) {
        level.playSound(null, pos, SoundRegistry.MAJOR_BLIGHT_MOTIF.get(), SoundSource.BLOCKS, 1, 1);
    }
}
