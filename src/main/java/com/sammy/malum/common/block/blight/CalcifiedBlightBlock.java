package com.sammy.malum.common.block.blight;

import com.mojang.serialization.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.block.*;
import com.sammy.malum.registry.common.item.*;
import io.github.fabricators_of_create.porting_lib.extensions.extensions.IShearable;
import net.minecraft.core.*;
import net.minecraft.sounds.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.phys.*;
import net.minecraft.world.phys.shapes.*;

import java.util.*;
import java.util.stream.*;

import static com.sammy.malum.registry.common.block.BlockTagRegistry.*;

public class CalcifiedBlightBlock extends BushBlock implements IShearable {

    protected static final List<VoxelShape> SHAPES = IntStream.range(0, 4).boxed().map(i -> Block.box(2.0D, 0.0D, 2.0D, 14.0D, 6 + i * 2, 14.0D)).collect(Collectors.toList());
    public static final IntegerProperty STAGE = IntegerProperty.create("stage", 0, 3);

    public CalcifiedBlightBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BushBlock> codec() {
        return null;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(STAGE);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (stack.getItem().equals(ItemRegistry.CALCIFIED_BLIGHT.get())) {
            final int stage = state.getValue(STAGE);
            if (stage < 3) {
                level.setBlock(pos, state.setValue(STAGE, stage + 1), 3);
            }
            else {
                TallCalcifiedBlightBlock.placeAt(level, BlockRegistry.TALL_CALCIFIED_BLIGHT.get().defaultBlockState(), pos, 3);
            }
            if (!player.getAbilities().instabuild) {
                stack.shrink(1);
            }
            SoundType soundtype = state.getSoundType();
            level.playSound(null, pos, SoundRegistry.CALCIFIED_BLIGHT_PLACE.get(), SoundSource.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * (1 + stage * 0.1f));
            return ItemInteractionResult.SUCCESS;
        }
        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPES.get(pState.getValue(STAGE));
    }

    @Override
    public boolean mayPlaceOn(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        if (pState.is(BLIGHTED_BLOCKS)) {
            return true;
        }
        return super.mayPlaceOn(pState, pLevel, pPos);
    }
}