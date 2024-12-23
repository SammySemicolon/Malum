package com.sammy.malum.common.block.curiosities.redstone;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;

import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public class DirectionalRedstoneMachineBlock<T extends RedstoneMachineBlockEntity> extends RedstoneMachineBlock<T> {

    public static final EnumProperty<SignalInput> SIGNAL_INPUT = EnumProperty.create("signal_input", SignalInput.class);

    public DirectionalRedstoneMachineBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(SIGNAL_INPUT, SignalInput.NONE));
    }

    @Override
    protected int getSignal(BlockState blockState, BlockGetter blockAccess, BlockPos pos, Direction side) {
        SignalInput input = blockState.getValue(SIGNAL_INPUT);
        if (input.equals(SignalInput.NONE)) {
            return 0;
        }
        Direction direction = input.direction;
        return !side.equals(direction) ? getActiveSignalStrength(blockState, blockAccess, pos) : 0;
    }

    protected int getActiveSignalStrength(BlockState blockState, BlockGetter blockAccess, BlockPos pos) {
        return 15;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(SIGNAL_INPUT);
    }

    public enum SignalInput implements StringRepresentable {
        NONE(null),
        DOWN(Direction.DOWN),
        UP(Direction.UP),
        NORTH(Direction.NORTH),
        SOUTH(Direction.SOUTH),
        WEST(Direction.WEST),
        EAST(Direction.EAST);

        private static final SignalInput[] VALUES = SignalInput.values();
        public static final Map<Direction, SignalInput> DIRECTION_MAP = Arrays.stream(VALUES).filter(b -> b.direction != null && b.direction.getAxis().isHorizontal()).collect(Collectors.toMap(b -> b.direction, b -> b));
        public final Direction direction;
        final String name = name().toLowerCase(Locale.ROOT);

        SignalInput(Direction direction) {
            this.direction = direction;
        }

        @Override
        public String getSerializedName() {
            return name;
        }
    }
}