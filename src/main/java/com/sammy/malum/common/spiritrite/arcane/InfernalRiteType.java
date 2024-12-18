package com.sammy.malum.common.spiritrite.arcane;

import com.sammy.malum.common.block.curiosities.totem.*;
import com.sammy.malum.common.packets.particle.rite.InfernalExtinguishRiteEffectPacket;
import com.sammy.malum.common.packets.particle.rite.generic.BlockSparkleParticlePacket;
import com.sammy.malum.common.spiritrite.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.block.*;
import net.minecraft.server.level.*;
import net.minecraft.sounds.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;

import java.util.function.*;

import static com.sammy.malum.registry.common.SpiritTypeRegistry.*;

public class InfernalRiteType extends TotemicRiteType {
    public InfernalRiteType() {
        super("infernal_rite", ARCANE_SPIRIT, INFERNAL_SPIRIT, INFERNAL_SPIRIT);
    }

    @Override
    public TotemicRiteEffect getNaturalRiteEffect() {
        return new PotionRiteEffect(LivingEntity.class, MobEffectRegistry.MINERS_RAGE);
    }

    @Override
    public TotemicRiteEffect getCorruptedEffect() {
        return new PotionRiteEffect(LivingEntity.class, MobEffectRegistry.IFRITS_EMBRACE) {

            @SuppressWarnings("ConstantConditions")
            @Override
            public void doRiteEffect(TotemBaseBlockEntity totemBase, ServerLevel level) {
                super.doRiteEffect(totemBase, level);
                getNearbyBlocks(totemBase, BaseFireBlock.class).forEach(p -> {
                    BlockState state = totemBase.getLevel().getBlockState(p);
                    if (!state.is(BlockTagRegistry.ENDLESS_FLAME)) {
                        level.playSound(null, p, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 1, 2.6F + (level.random.nextFloat() - level.random.nextFloat()) * 0.8F);

                        PacketRegistry.sendToPlayersTrackingChunk(level, new ChunkPos(p), new InfernalExtinguishRiteEffectPacket(INFERNAL_SPIRIT.getPrimaryColor(), p));
                        PacketRegistry.sendToPlayersTrackingChunk(level, new ChunkPos(p), new BlockSparkleParticlePacket(ARCANE_SPIRIT.getPrimaryColor(), p, false));
                        totemBase.getLevel().removeBlock(p, false);
                    }
                });
            }

            @Override
            public Predicate<LivingEntity> getEntityPredicate() {
                return super.getEntityPredicate().and(Entity::isOnFire);
            }
        };
    }
}