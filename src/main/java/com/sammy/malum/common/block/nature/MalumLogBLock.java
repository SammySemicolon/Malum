package com.sammy.malum.common.block.nature;

import com.sammy.malum.common.block.curiosities.totem.TotemPoleBlockEntity;
import com.sammy.malum.common.item.spirit.SpiritShardItem;
import com.sammy.malum.registry.common.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import team.lodestar.lodestone.systems.block.LodestoneLogBlock;

import java.util.function.Supplier;

public class MalumLogBLock extends LodestoneLogBlock {
    private final boolean isCorrupt;

    public MalumLogBLock(Properties properties, Supplier<Block> stripped, boolean isCorrupt) {
        super(properties, stripped);
        this.isCorrupt = isCorrupt;
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        if (stack.getItem() instanceof SpiritShardItem item) {
            if (hit.getDirection().equals(Direction.UP) || hit.getDirection().equals(Direction.DOWN)) {
                return ItemInteractionResult.FAIL;
            }
            if (level.isClientSide) {
                return ItemInteractionResult.SUCCESS;
            }
            boolean success = createTotemPole(level, pos, player, handIn, hit, stack, item);
            if (success) {
                return ItemInteractionResult.SUCCESS;
            }
        }
        return super.useItemOn(stack, state, level, pos, player, handIn, hit);
    }

    public boolean createTotemPole(Level level, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit, ItemStack stack, SpiritShardItem spirit) {
        if (spirit.type.equals(SpiritTypeRegistry.UMBRAL_SPIRIT)) {
            return false;
        }
        level.setBlockAndUpdate(pos, spirit.type.getTotemPoleBlockState(isCorrupt, hit));
        if (level.getBlockEntity(pos) instanceof TotemPoleBlockEntity blockEntity) {
            blockEntity.setSpirit(spirit.type);
        }
        if (!player.isCreative()) {
            stack.shrink(1);
        }
        level.levelEvent(2001, pos, Block.getId(level.getBlockState(pos)));
        player.swing(handIn, true);
        return true;
    }
}