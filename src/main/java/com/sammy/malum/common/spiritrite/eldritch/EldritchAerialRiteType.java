package com.sammy.malum.common.spiritrite.eldritch;

import com.sammy.malum.common.block.curiosities.totem.*;
import com.sammy.malum.common.spiritrite.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.block.*;
import com.sammy.malum.visual_effects.networked.data.*;
import net.minecraft.core.*;
import net.minecraft.resources.*;
import net.minecraft.server.level.*;
import net.minecraft.sounds.*;
import net.minecraft.stats.*;
import net.minecraft.tags.*;
import net.minecraft.world.entity.item.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;

import java.util.*;

import static com.sammy.malum.registry.common.SpiritTypeRegistry.*;

public class EldritchAerialRiteType extends TotemicRiteType {
    public EldritchAerialRiteType() {
        super("greater_aerial_rite", ELDRITCH_SPIRIT, ARCANE_SPIRIT, AERIAL_SPIRIT, AERIAL_SPIRIT);
    }

    @Override
    public TotemicRiteEffect getNaturalRiteEffect() {
        return new BlockAffectingRiteEffect() {
            @Override
            public void doRiteEffect(TotemBaseBlockEntity totemBase, ServerLevel level) {
                BlockPos pos = totemBase.getBlockPos();
                getBlocksAhead(totemBase).forEach(p -> {
                    BlockState stateBelow = level.getBlockState(p.below());
                    if (FallingBlock.isFree(stateBelow) || !stateBelow.canOcclude() || stateBelow.is(BlockTags.SLABS)) {
                        BlockState state = level.getBlockState(p);
                        if (!state.isAir() && level.getBlockEntity(p) == null && canSilkTouch(level, pos, state)) {
                            FallingBlockEntity.fall(level, p, state);
                            level.playSound(null, p, SoundRegistry.TOTEM_AERIAL_MAGIC.get(), SoundSource.BLOCKS, 0.5f, 2.6F + (level.random.nextFloat() - level.random.nextFloat()) * 0.8F);
                        }
                    }
                });
            }
        };
    }

    @Override
    public TotemicRiteEffect getCorruptedEffect() {
        return new TotemicRiteEffect(TotemicRiteEffect.MalumRiteEffectCategory.LIVING_ENTITY_EFFECT) {
            @Override
            public void doRiteEffect(TotemBaseBlockEntity totemBase, ServerLevel level) {
                getNearbyEntities(totemBase, ServerPlayer.class).forEach(p -> {
                    ServerStatsCounter stats = p.getStats();
                    Stat<ResourceLocation> sleepStat = Stats.CUSTOM.get(Stats.TIME_SINCE_REST);
                    int value = stats.getValue(sleepStat);
                    stats.setValue(p, sleepStat, Math.max(0, value - 1000));
                    ParticleEffectTypeRegistry.RITE_EFFECT_TRIGGERED.createEntityEffect(p, new ColorEffectData(AERIAL_SPIRIT));
                });
            }
        };
    }

    private static final List<Item> TOOLS = List.of(Items.NETHERITE_PICKAXE, Items.NETHERITE_AXE, Items.NETHERITE_SHOVEL, Items.NETHERITE_HOE);
    // From Botania, modified slightly

    private static ItemStack getToolForState(BlockState state) {
        if (!state.requiresCorrectToolForDrops()) {
            return new ItemStack(Items.NETHERITE_PICKAXE);
        } else {
            for (Item item : TOOLS) {
                ItemStack stack = new ItemStack(item);
                if (stack.isCorrectToolForDrops(state)) {
                    return stack;
                }
            }

            return ItemStack.EMPTY;
        }
    }

    private static boolean canSilkTouch(ServerLevel level, BlockPos pos, BlockState state) {
        if (state.is(BlockTagRegistry.GREATER_AERIAL_WHITELIST)) {
            return true;
        }

        ItemStack harvestToolStack = getToolForState(state);
        if (harvestToolStack.isEmpty()) {
            return false;
        }
        harvestToolStack.enchant(level.registryAccess().holderOrThrow(Enchantments.SILK_TOUCH), 1);
        List<ItemStack> drops = Block.getDrops(state, level, pos, null, null, harvestToolStack);
        Item blockItem = state.getBlock().asItem();
        return drops.stream().anyMatch(s -> s.getItem() == blockItem);
    }
}
