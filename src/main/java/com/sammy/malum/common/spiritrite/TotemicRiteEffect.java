package com.sammy.malum.common.spiritrite;

import com.sammy.malum.common.block.curiosities.totem.*;
import com.sammy.malum.registry.common.block.*;
import net.minecraft.core.*;
import net.minecraft.server.level.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.phys.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.helpers.block.*;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

@SuppressWarnings("ConstantConditions")
public abstract class TotemicRiteEffect {

    public enum MalumRiteEffectCategory {
        AURA(40, 8),
        LIVING_ENTITY_EFFECT(40, 4),
        DIRECTIONAL_BLOCK_EFFECT(160, 2, 1),
        RADIAL_BLOCK_EFFECT(80, 5),
        ONE_TIME_EFFECT(0, 0);

        private final int tickRate;
        private final int effectWidth;
        private final int effectHeight;

        MalumRiteEffectCategory(int tickRate, int range) {
            this(tickRate, range, range);
        }
        MalumRiteEffectCategory(int tickRate, int effectWidth, int effectHeight) {
            this.tickRate = tickRate;
            this.effectWidth = effectWidth;
            this.effectHeight = effectHeight;
        }

        public String getTranslationKey() {
            return "malum.gui.rite.category." + name().toLowerCase(Locale.ROOT);
        }
    }

    public final MalumRiteEffectCategory category;

    protected TotemicRiteEffect(MalumRiteEffectCategory category) {
        this.category = category;
    }

    public final void doRiteEffect(TotemBaseBlockEntity totemBase) {
        if (totemBase.getLevel() instanceof ServerLevel serverLevel) {
            doRiteEffect(totemBase, serverLevel);
        }
    }

    protected abstract void doRiteEffect(TotemBaseBlockEntity totemBase, ServerLevel level);

    public BlockPos getRiteEffectCenter(TotemBaseBlockEntity totemBase) {
        if (category.equals(MalumRiteEffectCategory.DIRECTIONAL_BLOCK_EFFECT)) {
            return totemBase.getBlockPos().below().relative(totemBase.getDirection(), 3);
        }
        return totemBase.getBlockPos();
    }

    public String getRiteCoverageDescriptor() {
        int coverage = getRiteEffectHorizontalRadius();
        if (coverage > 1) {
            coverage = coverage * 2 + 1;
        }
        int yCoverage = getRiteEffectVerticalRadius();
        if (yCoverage > 1) {
            yCoverage = yCoverage * 2 + 1;
        }
        return coverage + "x" + yCoverage + "x" + coverage;
    }

    public int getRiteEffectHorizontalRadius() {
        return category.effectWidth;
    }

    public int getRiteEffectVerticalRadius() {
        return category.effectHeight;
    }

    public int getRiteEffectTickRate() {
        return category.tickRate;
    }

    public <T extends Entity> Stream<T> getNearbyEntities(TotemBaseBlockEntity totemBase, Class<T> clazz) {
        return getNearbyEntities(totemBase, clazz, e -> true);
    }

    public <T extends Entity> Stream<T> getNearbyEntities(TotemBaseBlockEntity totemBase, Class<T> clazz, Predicate<T> predicate) {
        final int horizontal = getRiteEffectHorizontalRadius();
        final int vertical = getRiteEffectVerticalRadius();
        final AABB area = new AABB(getRiteEffectCenter(totemBase)).inflate(horizontal, vertical, horizontal);
        return totemBase.getLevel().getEntitiesOfClass(clazz, area).stream().filter(predicate);
    }

    public Stream<BlockPos> getNearbyBlocks(TotemBaseBlockEntity totemBase, Class<?> clazz) {
        final int horizontal = getRiteEffectHorizontalRadius();
        final int vertical = getRiteEffectVerticalRadius();
        return BlockPosHelper.getBlocksStream(
                getRiteEffectCenter(totemBase),
                horizontal, vertical, horizontal,
                p -> canAffectBlock(totemBase, clazz, p));
    }

    public Stream<BlockPos> getBlocksAhead(TotemBaseBlockEntity totemBase) {
        return BlockPosHelper.getPlaneOfBlocksStream(
                getRiteEffectCenter(totemBase),
                getRiteEffectHorizontalRadius(),
                p -> canAffectBlock(totemBase, p));
    }

    public final boolean canAffectBlock(TotemBaseBlockEntity totemBase, Class<?> clazz, BlockPos pos) {
        BlockState state = totemBase.getLevel().getBlockState(pos);
        return clazz.isInstance(state.getBlock()) && canAffectBlock(totemBase, state, pos);
    }

    public final boolean canAffectBlock(TotemBaseBlockEntity totemBase, BlockPos pos) {
        BlockState state = totemBase.getLevel().getBlockState(pos);
        return canAffectBlock(totemBase, state, pos);
    }

    public boolean canAffectBlock(TotemBaseBlockEntity totemBase, BlockState state, BlockPos pos) {
        return !state.is(BlockTagRegistry.RITE_IMMUNE);
    }
}