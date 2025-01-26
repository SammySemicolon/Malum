package com.sammy.malum.common.block.curiosities.banner;

import com.sammy.malum.registry.common.item.DataComponentRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import team.lodestar.lodestone.systems.block.LodestoneEntityBlock;

import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public class SoulwovenBannerBlock extends LodestoneEntityBlock<SoulwovenBannerBlockEntity> {
    public static final EnumProperty<BannerType> BANNER_TYPE = EnumProperty.create("banner_type", BannerType.class);
    private static final VoxelShape HANGING_SHAPE_X = Block.box(6.0, 9.0, 0.0, 10.0, 16.0, 16.0);
    private static final VoxelShape HANGING_SHAPE_Z = Block.box(0.0, 9.0, 6.0, 16.0, 16.0, 10.0);

    private static final VoxelShape MOUNTED_NORTH_SHAPE = Block.box(0.0, 6.0, 14.0, 16.0, 16.0, 16.0);
    private static final VoxelShape MOUNTED_SOUTH_SHAPE = Block.box(0.0, 6.0, 0.0, 16.0, 16.0, 2.0);
    private static final VoxelShape MOUNTED_WEST_SHAPE = Block.box(14.0, 6.0, 0.0, 16.0, 16.0, 16.0);
    private static final VoxelShape MOUNTED_EAST_SHAPE = Block.box(0.0, 6.0, 0.0, 2.0, 16.0, 16.0);

    public SoulwovenBannerBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BANNER_TYPE);
    }

    @Override
    public @NotNull ItemStack getCloneItemStack(BlockState state, HitResult target, LevelReader level, BlockPos pos, Player player) {
        final ItemStack stack = super.getCloneItemStack(state, target, level, pos, player);
        if (level.getBlockEntity(pos) instanceof SoulwovenBannerBlockEntity banner) {
            stack.set(DataComponentRegistry.SOULWOVEN_BANNER_PATTERN, banner.patternData);
        }
        return stack;
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        return !pState.canSurvive(pLevel, pCurrentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        final BannerType value = state.getValue(BANNER_TYPE);
        Direction direction = value.direction.getOpposite();
        return Block.canSupportCenter(level, pos.relative(direction), direction.getOpposite());
    }

    @Override
    public VoxelShape getBlockSupportShape(BlockState pState, BlockGetter pReader, BlockPos pPos) {
        return Shapes.empty();
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        switch (state.getValue(BANNER_TYPE)) {
            case HANGING_X -> {
                return HANGING_SHAPE_X;
            }
            case HANGING_Z -> {
                return HANGING_SHAPE_Z;
            }
            case MOUNTED_NORTH -> {
                return MOUNTED_NORTH_SHAPE;
            }
            case MOUNTED_SOUTH -> {
                return MOUNTED_SOUTH_SHAPE;
            }
            case MOUNTED_WEST -> {
                return MOUNTED_WEST_SHAPE;
            }
            case MOUNTED_EAST -> {
                return MOUNTED_EAST_SHAPE;
            }
            default -> {
                return super.getShape(state, level, pos, context);
            }
        }
    }


    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        final Direction clickedFace = context.getClickedFace();
        if (clickedFace.equals(Direction.DOWN)) {
            return defaultBlockState().setValue(BANNER_TYPE, context.getHorizontalDirection().getAxis().equals(Direction.Axis.X) ? BannerType.HANGING_X : BannerType.HANGING_Z);
        }
        if (clickedFace.getAxis().isHorizontal()) {
            return defaultBlockState().setValue(BANNER_TYPE, BannerType.DIRECTION_MAP.get(clickedFace));
        }
        return null;
    }

    public enum BannerType implements StringRepresentable {
        HANGING_X(Direction.DOWN),
        HANGING_Z(Direction.DOWN),
        MOUNTED_NORTH(Direction.NORTH),
        MOUNTED_SOUTH(Direction.SOUTH),
        MOUNTED_WEST(Direction.WEST),
        MOUNTED_EAST(Direction.EAST);

        private static final BannerType[] VALUES = BannerType.values();
        private static final Map<Direction, BannerType> DIRECTION_MAP = Arrays.stream(VALUES).filter(b -> b.direction.getAxis().isHorizontal()).collect(Collectors.toMap(b -> b.direction, b -> b));
        public final Direction direction;
        final String name = name().toLowerCase(Locale.ROOT);

        BannerType(Direction direction) {
            this.direction = direction;
        }

        @Override
        public String getSerializedName() {
            return name;
        }
    }
}
