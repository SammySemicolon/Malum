package com.sammy.malum.common.block.curiosities.banner;

import com.mojang.datafixers.util.*;
import com.sammy.malum.*;
import com.sammy.malum.common.block.blight.*;
import com.sammy.malum.common.block.curiosities.mana_mote.*;
import com.sammy.malum.core.systems.spirit.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.core.*;
import net.minecraft.resources.*;
import net.minecraft.util.*;
import net.minecraft.world.item.context.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.phys.shapes.*;
import net.neoforged.neoforge.client.extensions.common.*;
import org.jetbrains.annotations.*;
import team.lodestar.lodestone.systems.block.*;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_FACING;

public class SoulwovenBannerBlock extends LodestoneEntityBlock<SoulwovenBannerBlockEntity> {
    public static final EnumProperty<BannerType> BANNER_TYPE = EnumProperty.create("banner_type", BannerType.class);
    public final ResourceLocation texture;

    public SoulwovenBannerBlock(String id, Properties properties) {
        this(MalumMod.malumPath("textures/block/soulwoven_banner_" + id + ".png"), properties);
    }

    public SoulwovenBannerBlock(ResourceLocation texture, Properties properties) {
        super(properties);
        this.texture = texture;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BANNER_TYPE);
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