package com.sammy.malum.common.worldevent;

import com.sammy.malum.common.block.blight.*;
import com.sammy.malum.common.block.curiosities.totem.*;
import com.sammy.malum.core.systems.spirit.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.block.*;
import net.minecraft.core.*;
import net.minecraft.server.level.*;
import net.minecraft.sounds.*;
import net.minecraft.util.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.block.state.properties.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.helpers.block.*;

import static com.sammy.malum.registry.common.block.BlockRegistry.*;

public class SuspiciousDeviceTriggerWorldEvent extends ActiveBlightWorldEvent {
    public int transformedTotemParts;

    public SuspiciousDeviceTriggerWorldEvent() {
        super(WorldEventTypeRegistry.UNCHAINED_TOTEM_CONVERSION.get());
    }

    @Override
    public void createBlight(ServerLevel level, int intensity) {
        super.createBlight(level, intensity);
        if (transformedTotemParts == 0) {
            BlockState state = BlockStateHelper.setBlockStateWithExistingProperties(level, position, SOULWOOD_TOTEM_BASE.get().defaultBlockState(), 3);
            placeBlock(position, state);
            transformedTotemParts++;
            return;
        }
        int offset = transformedTotemParts;
        BlockPos totemPos = position.above(offset);
        if (level.getBlockEntity(totemPos) instanceof TotemPoleBlockEntity totemPoleTile) {
            placeTotemPole(totemPos, totemPoleTile.spirit);
        }
        transformedTotemParts++;
    }

    public void placeTotemPole(BlockPos pos, MalumSpiritType spiritType) {
        BlockState totemPoleState = BlockStateHelper.setBlockStateWithExistingProperties(level, pos, SOULWOOD_TOTEM_POLE.get().defaultBlockState(), 3);
        TotemPoleBlockEntity totemPole = new TotemPoleBlockEntity(pos, totemPoleState);
        totemPole.setLevel(level);
        totemPole.setSpirit(spiritType);
        level.setBlockEntity(totemPole);
        placeBlock(pos, totemPoleState);
        maybePlaceBlightedGunk(pos, totemPoleState);
    }

    public void placeBlock(BlockPos pos, BlockState state) {
        level.setBlockAndUpdate(pos, state);
        level.levelEvent(null, 2001, pos, Block.getId(state));
        level.playSound(null, pos, SoundRegistry.MINOR_BLIGHT_MOTIF.get(), SoundSource.BLOCKS, 1f, RandomHelper.randomBetween(level.getRandom(), 1.6f, 2f));
    }
    public void maybePlaceBlightedGunk(BlockPos pos, BlockState totemPoleState) {
        final RandomSource random = level.getRandom();
        if (random.nextFloat() < 0.4f) {
            var direction = Direction.from2DDataValue(random.nextInt(4));
            if (direction.equals(totemPoleState.getValue(BlockStateProperties.HORIZONTAL_FACING))) {
                return;
            }
            var state = BlockRegistry.CLINGING_BLIGHT.get().defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, direction.getOpposite()).setValue(ClingingBlightBlock.BLIGHT_TYPE, ClingingBlightBlock.BlightType.SOULWOOD_SPIKE);
            placeBlock(pos.relative(direction), state);
        }
    }
}