package com.sammy.malum.common.block.curiosities.banner;

import com.sammy.malum.common.block.curiosities.totem.*;
import com.sammy.malum.common.item.spirit.*;
import com.sammy.malum.core.systems.spirit.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.block.*;
import com.sammy.malum.visual_effects.networked.data.*;
import net.minecraft.core.*;
import net.minecraft.nbt.*;
import net.minecraft.server.level.*;
import net.minecraft.sounds.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.neoforged.neoforge.common.*;
import team.lodestar.lodestone.helpers.block.*;
import team.lodestar.lodestone.systems.blockentity.*;

import javax.annotation.*;

public class SoulwovenBannerBlockEntity extends LodestoneBlockEntity {

    public MalumSpiritType spirit;
    public boolean intense;

    public SoulwovenBannerBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.SOULWOVEN_BANNER.get(), pos, state);
    }

    @Override
    public ItemInteractionResult onUseWithItem(Player pPlayer, ItemStack pStack, InteractionHand pHand) {
        if (level instanceof ServerLevel serverLevel) {
            if (pStack.getItem() instanceof SpiritShardItem shardItem) {
                var spirit = shardItem.type;
                if ((spirit.equals(this.spirit) && intense) || spirit.equals(SpiritTypeRegistry.UMBRAL_SPIRIT)) {
                    return super.onUseWithItem(pPlayer, pStack, pHand);
                }
                if (!pPlayer.isCreative()) {
                    pStack.shrink(1);
                }
                setSpirit(serverLevel, spirit);
                pPlayer.swing(pHand, true);
            }
            if (pStack.canPerformAction(ItemAbilities.SHEARS_DISARM)) {
                if (spirit != null) {
                    setSpirit(serverLevel, null);
                    pPlayer.swing(pHand, true);
                }
            }
        }
        return super.onUseWithItem(pPlayer, pStack, pHand);
    }

    public void setSpirit(ServerLevel serverLevel, @Nullable MalumSpiritType spirit) {
        level.playSound(null, worldPosition, SoundRegistry.TOTEM_ENGRAVE.get(), SoundSource.BLOCKS, 1, Mth.nextFloat(level.random, 0.9f, 1.1f));
        level.playSound(null, worldPosition, SoundEvents.SHEEP_SHEAR, SoundSource.BLOCKS, 1, Mth.nextFloat(level.random, 0.9f, 1.1f));
        if (spirit != null) {
            ParticleEffectTypeRegistry.TOTEM_POLE_ACTIVATED.createPositionedEffect(serverLevel, new PositionEffectData(worldPosition), new ColorEffectData(spirit));
        }
        intense = this.spirit == spirit;
        this.spirit = spirit;
        BlockStateHelper.updateState(level, worldPosition);
    }

    @Override
    protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.loadAdditional(pTag, pRegistries);
        if (pTag.contains("spirit")) {
            spirit = MalumSpiritType.getSpiritType(pTag.getString("spirit"));
        } else {
            spirit = null;
        }
        intense = pTag.getBoolean("intense");
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        if (spirit != null) {
            tag.putString("spirit", spirit.getIdentifier());
        }
        tag.putBoolean("intense", intense);
    }
}
