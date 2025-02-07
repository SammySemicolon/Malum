package com.sammy.malum.common.block.curiosities.redstone;

import com.sammy.malum.common.packets.spirit_diode.SpiritDiodeToggleOpenPayload;
import com.sammy.malum.common.packets.spirit_diode.SpiritDiodeUpdatePayload;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.EventHooks;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;
import team.lodestar.lodestone.systems.block.LodestoneEntityBlock;

import java.util.EnumSet;

public abstract class SpiritDiodeBlock<T extends SpiritDiodeBlockEntity> extends LodestoneEntityBlock<T> {

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    public static final BooleanProperty OPEN = BlockStateProperties.OPEN;

    public SpiritDiodeBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(POWERED, false).setValue(OPEN, false).setValue(FACING, Direction.NORTH));
    }

    /**
     * Measured in redstone ticks (i.e. 2 game ticks)
     */
    public abstract int getDefaultFrequency(BlockPos pos, BlockState state);

    /**
     * Measured in redstone ticks (i.e. 2 game ticks)
     */
    public abstract int[] getFrequencyPresets();

    /**
     * Should update blockstates.
     * Return true if further updates are required.
     */
    public abstract boolean processUpdate(Level level, BlockPos pos, BlockState state, T diode, int signal);

    /**
     * Should not update blockstates or Block Entity data.
     * Return true if the blockstate or signal output needs to change.
     */
    public abstract boolean shouldUpdateWhenNeighborChanged(Level level, BlockPos pos, BlockState state, T diode, int newInput);


    public int redstoneTicksUntilUpdate(Level level, BlockPos pos, BlockState state, T diode, int newInput) {
        return Math.max(diode.delay, 1);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        if (!level.getBlockTicks().willTickThisTick(pos, this)) {
            BlockEntity blockentity = level.getBlockEntity(pos);
            if (blockentity instanceof SpiritDiodeBlockEntity spiritDiode) {
                Direction direction = state.getValue(FACING);
                int signal = level.getSignal(pos.relative(direction), direction);
                if (shouldUpdateWhenNeighborChanged(level, pos, state, (T) spiritDiode, signal)) {
                    final int delay = 2 * redstoneTicksUntilUpdate(level, pos, state, (T) spiritDiode, signal);
                    level.scheduleTick(pos, this, delay);
                    if (level instanceof ServerLevel serverLevel) {
                        spiritDiode.updateAnimation(serverLevel, pos, signal);
                    }
                }
            }
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        BlockEntity blockentity = level.getBlockEntity(pos);
        if (blockentity instanceof SpiritDiodeBlockEntity spiritDiode) {
            Direction direction = state.getValue(FACING);
            int signal = level.getSignal(pos.relative(direction), direction);
            if (processUpdate(level, pos, state, (T) spiritDiode, signal)) {
                final int delay = 2 * redstoneTicksUntilUpdate(level, pos, state, (T) spiritDiode, signal);
                level.scheduleTick(pos, this, delay);
                spiritDiode.updateAnimation(level, pos, signal);
            }
        }
    }

    public void updateState(Level level, BlockPos pos, BlockState state, T diode) {
        boolean shouldBePowered = diode.getOutputSignal() != 0;
        boolean isPowered = state.getValue(POWERED);
        if (shouldBePowered != isPowered)
            level.setBlock(pos, state.setValue(SpiritDiodeBlock.POWERED, shouldBePowered), 2);

        notifyNeighborsInFront(level, pos, state);
    }

    public void notifyNeighborsInFront(Level level, BlockPos pos, BlockState state) {
        Direction direction = state.getValue(FACING);
        BlockPos blockpos = pos.relative(direction.getOpposite());
        if (!EventHooks.onNeighborNotify(level, pos, level.getBlockState(pos), EnumSet.of(direction.getOpposite()), false).isCanceled()) {
            level.neighborChanged(blockpos, this, pos);
            level.updateNeighborsAtExceptFromFacing(blockpos, this, direction);
        }
    }


    public void emitRedstoneParticles(Level level, BlockPos pos) {
        Vec3 center = pos.getCenter();
        float offset = 0.625f;
        level.addParticle(DustParticleOptions.REDSTONE, center.x + offset, center.y, center.z, 0, 0, 0);
        level.addParticle(DustParticleOptions.REDSTONE, center.x - offset, center.y, center.z, 0, 0, 0);
        level.addParticle(DustParticleOptions.REDSTONE, center.x, center.y, center.z + offset, 0, 0, 0);
        level.addParticle(DustParticleOptions.REDSTONE, center.x, center.y, center.z - offset, 0, 0, 0);
    }

    @Override
    protected boolean isSignalSource(BlockState state) {
        return true;
    }

    @Override
    public boolean canConnectRedstone(BlockState state, BlockGetter level, BlockPos pos, @Nullable Direction direction) {
        Direction facing = state.getValue(FACING);
        return facing == direction || facing.getOpposite() == direction;
    }

    @Override
    protected int getSignal(BlockState state, BlockGetter level, BlockPos pos, Direction side) {
		if (state.getValue(POWERED)) {
			if (state.getValue(FACING) == side) {
				BlockEntity blockentity = level.getBlockEntity(pos);
				if (blockentity instanceof SpiritDiodeBlockEntity spiritDiode) {
					return spiritDiode.getOutputSignal();
				}
			}
		}
        return 0;
	}

    @Override
    public boolean getWeakChanges(BlockState state, LevelReader level, BlockPos pos) {
        return true;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        Direction direction = pContext.getHorizontalDirection();
        if (pContext.getPlayer() == null || !pContext.getPlayer().isCrouching()) {
            direction = direction.getOpposite();
        }
        return this.defaultBlockState().setValue(FACING, direction);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(POWERED, OPEN, FACING);
    }

    @Override
    protected BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Override
    protected BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }
}
