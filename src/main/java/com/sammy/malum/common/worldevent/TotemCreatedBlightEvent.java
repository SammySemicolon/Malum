package com.sammy.malum.common.worldevent;

import com.sammy.malum.common.block.curiosities.totem.TotemBaseBlockEntity;
import com.sammy.malum.common.block.curiosities.totem.TotemPoleBlockEntity;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import com.sammy.malum.registry.common.*;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import team.lodestar.lodestone.helpers.block.*;

import static com.sammy.malum.registry.common.block.BlockRegistry.SOULWOOD_TOTEM_BASE;
import static com.sammy.malum.registry.common.block.BlockRegistry.SOULWOOD_TOTEM_POLE;

public class TotemCreatedBlightEvent extends ActiveBlightEvent {
    public int totemTakeoverTimer;

    public TotemCreatedBlightEvent() {
        super(WorldEventTypes.TOTEM_CREATED_BLIGHT.get());
    }

    @Override
    public void tick(Level level) {
        if (totemTakeoverTimer == 0) {
            BlockState state = BlockStateHelper.setBlockStateWithExistingProperties(level, sourcePos, SOULWOOD_TOTEM_BASE.get().defaultBlockState(), 3);
            level.setBlockEntity(new TotemBaseBlockEntity(sourcePos, state));
            level.levelEvent(null, 2001, sourcePos, Block.getId(state));
            level.playSound(null, sourcePos, SoundRegistry.MINOR_BLIGHT_MOTIF.get(), SoundSource.BLOCKS, 1f, 1.8f);
        }
        totemTakeoverTimer++;
        if (totemTakeoverTimer % rate == 0) {
            int offset = totemTakeoverTimer / 4;
            BlockPos totemPos = sourcePos.above(offset);
            if (level.getBlockEntity(totemPos) instanceof TotemPoleBlockEntity totemPoleTile) {
                MalumSpiritType type = totemPoleTile.spirit;
                BlockState state = BlockStateHelper.setBlockStateWithExistingProperties(level, totemPos, SOULWOOD_TOTEM_POLE.get().defaultBlockState(), 3);
                TotemPoleBlockEntity newTotemPole = new TotemPoleBlockEntity(totemPos, state);
                newTotemPole.setLevel(level);
                newTotemPole.setSpirit(type);
                level.setBlockEntity(newTotemPole);
                level.setBlockAndUpdate(totemPos, state);
                level.levelEvent(null, 2001, totemPos, Block.getId(state));
                level.playSound(null, sourcePos, SoundRegistry.MINOR_BLIGHT_MOTIF.get(), SoundSource.BLOCKS, 1f, 1.8f);
            }
        }
        super.tick(level);
    }

    @Override
    public void end(Level level) {
        if (totemTakeoverTimer >= 24) {
            super.end(level);
        }
    }
}