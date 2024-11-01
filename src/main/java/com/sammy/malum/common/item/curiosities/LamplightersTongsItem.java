package com.sammy.malum.common.item.curiosities;

import com.sammy.malum.common.item.spirit.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.visual_effects.networked.data.*;
import net.minecraft.server.level.*;
import net.minecraft.sounds.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.phys.shapes.*;

public class LamplightersTongsItem extends Item {
    public LamplightersTongsItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        var context = new BlockPlaceContext(pContext);
        var player = context.getPlayer();
        if (player == null) {
            return super.useOn(context);
        }
        var level = player.level();
        var hand = context.getHand();
        var spiritStack = player.getItemInHand(hand.equals(InteractionHand.MAIN_HAND) ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND);
        if (!(spiritStack.getItem() instanceof SpiritShardItem spiritShard)) {
            return super.useOn(context);
        }
        var spiritType = spiritShard.type;
        var spiritMote = getPlacementState(context, spiritType.getSpiritMoteBlockState());
        if (spiritMote == null) {
            return super.useOn(context);
        }
        var pPos = context.getClickedPos();
        SoundType soundtype = spiritMote.getSoundType(level, pPos, player);
        level.setBlock(pPos, spiritMote, 3);
        level.levelEvent(2001, pPos, Block.getId(spiritMote));
        level.playSound(player, pPos, SoundRegistry.SPIRIT_MOTE_CREATED.get(), SoundSource.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, Mth.nextFloat(level.random, 1.1f, 1.4f));
        if (!player.getAbilities().instabuild) {
            spiritStack.shrink(1);
        }
        if (level instanceof ServerLevel serverLevel) {
            ParticleEffectTypeRegistry.SPIRIT_MOTE_SPARKLES.createPositionedEffect(serverLevel, new PositionEffectData(pPos), new ColorEffectData(spiritType));
        }
        return InteractionResult.SUCCESS;
    }

    protected BlockState getPlacementState(BlockPlaceContext pContext, BlockState state) {
        return state != null && this.canPlace(pContext, state) ? state : null;
    }

    protected boolean canPlace(BlockPlaceContext pContext, BlockState pState) {
        Player player = pContext.getPlayer();
        CollisionContext collisioncontext = player == null ? CollisionContext.empty() : CollisionContext.of(player);
        return pContext.getLevel().isUnobstructed(pState, pContext.getClickedPos(), collisioncontext);
    }
}
