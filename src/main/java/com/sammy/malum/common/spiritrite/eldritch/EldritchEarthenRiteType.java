package com.sammy.malum.common.spiritrite.eldritch;

import com.sammy.malum.common.block.curiosities.totem.*;
import com.sammy.malum.common.packets.particle.rite.generic.BlockSparkleParticlePacket;
import com.sammy.malum.common.spiritrite.*;
import net.minecraft.server.level.*;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.neoforged.neoforge.network.PacketDistributor;

import static com.sammy.malum.registry.common.SpiritTypeRegistry.*;

public class EldritchEarthenRiteType extends TotemicRiteType {
    public EldritchEarthenRiteType() {
        super("greater_earthen_rite", ELDRITCH_SPIRIT, ARCANE_SPIRIT, EARTHEN_SPIRIT, EARTHEN_SPIRIT);
    }

    @Override
    public TotemicRiteEffect getNaturalRiteEffect() {
        return new BlockAffectingRiteEffect() {
            @Override
            public void doRiteEffect(TotemBaseBlockEntity totemBase, ServerLevel level) {
                getBlocksAhead(totemBase).forEach(p -> {
                    BlockState state = level.getBlockState(p);
                    boolean canBreak = !state.isAir() && state.getDestroySpeed(level, p) != -1;
                    if (canBreak) {
                        level.destroyBlock(p, true);
                        PacketDistributor.sendToPlayersTrackingChunk(level, new ChunkPos(p), new BlockSparkleParticlePacket(EARTHEN_SPIRIT.getPrimaryColor(), p));
                    }
                });
            }
        };
    }

    @Override
    public TotemicRiteEffect getCorruptedEffect() {
        return new BlockAffectingRiteEffect() {
            @SuppressWarnings("ConstantConditions")
            @Override
            public void doRiteEffect(TotemBaseBlockEntity totemBase, ServerLevel level) {
                getBlocksAhead(totemBase).forEach(p -> {
                    BlockState state = level.getBlockState(p);
                    boolean canPlace = state.isAir() || state.canBeReplaced();
                    if (canPlace) {
                        BlockState cobblestone = Blocks.COBBLESTONE.defaultBlockState();
                        level.setBlockAndUpdate(p, cobblestone);
                        level.levelEvent(2001, p, Block.getId(cobblestone));
                        PacketDistributor.sendToPlayersTrackingChunk(level, new ChunkPos(p), new BlockSparkleParticlePacket(EARTHEN_SPIRIT.getPrimaryColor(), p));
                    }
                });
            }
        };
    }
}