package com.sammy.malum.common.spiritrite.eldritch;

import com.sammy.malum.common.block.curiosities.totem.*;
import com.sammy.malum.core.systems.rite.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.visual_effects.networked.data.*;
import net.minecraft.server.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;

import static com.sammy.malum.registry.common.SpiritTypeRegistry.*;

public class EldritchEarthenRiteType extends TotemicRiteType {
    public EldritchEarthenRiteType() {
        super("greater_earthen_rite", ELDRITCH_SPIRIT, ARCANE_SPIRIT, EARTHEN_SPIRIT, EARTHEN_SPIRIT);
    }

    @Override
    public TotemicRiteEffect getNaturalRiteEffect() {
        return new TotemicRiteEffect(TotemicRiteEffect.MalumRiteEffectCategory.DIRECTIONAL_BLOCK_EFFECT) {
            @Override
            public void doRiteEffect(TotemBaseBlockEntity totemBase, ServerLevel level) {
                getBlocksAhead(totemBase).forEach(p -> {
                    BlockState state = level.getBlockState(p);
                    boolean canBreak = !state.isAir() && state.getDestroySpeed(level, p) != -1;
                    if (canBreak) {
                        level.destroyBlock(p, true);
                        ParticleEffectTypeRegistry.BLOCK_RITE_EFFECT.createPositionedEffect(level, new PositionEffectData(p), new ColorEffectData(EARTHEN_SPIRIT));
                    }
                });
            }
        };
    }

    @Override
    public TotemicRiteEffect getCorruptedEffect() {
        return new TotemicRiteEffect(TotemicRiteEffect.MalumRiteEffectCategory.DIRECTIONAL_BLOCK_EFFECT) {
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
                        ParticleEffectTypeRegistry.BLOCK_RITE_EFFECT.createPositionedEffect(level, new PositionEffectData(p), new ColorEffectData(EARTHEN_SPIRIT));
                    }
                });
            }
        };
    }
}