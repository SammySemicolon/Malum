package com.sammy.malum.common.block.curiosities.totem;

import com.sammy.malum.core.systems.spirit.*;
import com.sammy.malum.registry.common.SpiritTypeRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;
import team.lodestar.lodestone.systems.block.LodestoneEntityBlock;

import java.util.function.Supplier;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_FACING;

public class TotemPoleBlock<T extends TotemPoleBlockEntity> extends LodestoneEntityBlock<T> {

    public static final SpiritTypeProperty SPIRIT_TYPE = SpiritTypeRegistry.SPIRIT_TYPE_PROPERTY;

    public final Supplier<? extends Block> logBlock;
    public final boolean isSoulwood;

    public TotemPoleBlock(Properties properties, Supplier<? extends Block> logBlock, boolean isSoulwood) {
        super(properties.lootFrom(logBlock));
        this.logBlock = logBlock;
        this.isSoulwood = isSoulwood;
        this.registerDefaultState(this.stateDefinition.any().setValue(HORIZONTAL_FACING, Direction.NORTH).setValue(SPIRIT_TYPE, "sacred"));
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState pState) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState pState, Level pLevel, BlockPos pPos) {
        BlockEntity be = pLevel.getBlockEntity(pPos);
        if (be instanceof TotemPoleBlockEntity pole) {
            return Math.min(SpiritTypeRegistry.getIndexForSpiritType(pole.spirit) + 1, 15);
        }
        return 0;
    }


    @Override
    public @NotNull ItemStack getCloneItemStack(BlockState state, HitResult target, LevelReader world, BlockPos pos, Player player) {
        return logBlock.get().getCloneItemStack(world, pos, state);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(HORIZONTAL_FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HORIZONTAL_FACING, SPIRIT_TYPE);
    }
}