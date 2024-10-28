package com.sammy.malum.common.block.nature.soulwood;

import com.sammy.malum.registry.common.SoundRegistry;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;
import org.jetbrains.annotations.Nullable;
import team.lodestar.lodestone.systems.block.LodestoneLogBlock;

import java.util.function.Supplier;

public class SoulwoodBlock extends LodestoneLogBlock {
    public SoulwoodBlock(Properties properties, Supplier<Block> stripped) {
        super(properties, stripped);
    }

    @Nullable
    @Override
    public BlockState getToolModifiedState(BlockState state, UseOnContext context, ItemAbility itemAbility, boolean simulate) {
        if (itemAbility.equals(ItemAbilities.AXE_STRIP)) {
            if (!simulate) {
                context.getLevel().playSound(null, context.getClickedPos(), SoundRegistry.MAJOR_BLIGHT_MOTIF.get(), SoundSource.BLOCKS, 1, 1);
            }
            return stripped.get().defaultBlockState();
        }
        return null;
    }
}
