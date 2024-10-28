package com.sammy.malum.common.block.nature.soulwood;

import com.sammy.malum.common.block.nature.MalumLogBLock;
import com.sammy.malum.common.item.spirit.SpiritShardItem;
import com.sammy.malum.registry.common.SoundRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class SoulwoodLogBlock extends MalumLogBLock {
    public SoulwoodLogBlock(Properties properties, Supplier<Block> stripped, boolean isCorrupt) {
        super(properties, stripped, isCorrupt);
    }

    @Nullable
    @Override
    public BlockState getToolModifiedState(BlockState state, UseOnContext context, ItemAbility itemAbility, boolean simulate) {
        if (itemAbility.equals(ItemAbilities.AXE_STRIP)) {
            if (!simulate) {
                context.getLevel().playSound(null, context.getClickedPos(), SoundRegistry.MAJOR_BLIGHT_MOTIF.get(), SoundSource.BLOCKS, 1, 1);
            }
            return stripped.get().defaultBlockState().setValue(AXIS, state.getValue(AXIS));
        }
        return null;
    }

    @Override
    public boolean createTotemPole(Level level, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit, ItemStack stack, SpiritShardItem spirit) {
        boolean success = super.createTotemPole(level, pos, player, handIn, hit, stack, spirit);
        if (success) {
            level.playSound(null, pos, SoundRegistry.MAJOR_BLIGHT_MOTIF.get(), SoundSource.BLOCKS, 1, 1);
        }
        return success;
    }
}