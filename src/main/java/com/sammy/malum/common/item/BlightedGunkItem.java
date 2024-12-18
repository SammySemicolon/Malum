package com.sammy.malum.common.item;

import com.sammy.malum.registry.common.block.*;
import net.minecraft.core.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.phys.shapes.*;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class BlightedGunkItem extends ItemNameBlockItem {

    public final Block wallPlacement;

    public BlightedGunkItem(Properties pProperties) {
        this(BlockRegistry.BLIGHTED_GROWTH.get(), BlockRegistry.CLINGING_BLIGHT.get(), pProperties);
    }
    public BlightedGunkItem(Block defaultPlacement, Block wallPlacement, Properties pProperties) {
        super(defaultPlacement, pProperties);
        this.wallPlacement = wallPlacement;
    }

    @Nullable
    @Override
    protected BlockState getPlacementState(BlockPlaceContext pContext) {
        final Player player = pContext.getPlayer();
        BlockState wallPlacementState = wallPlacement.getStateForPlacement(pContext);
        BlockState defaultState = getBlock().getStateForPlacement(pContext);
        BlockState placed = wallPlacementState;
        if ((player != null && player.isShiftKeyDown()) || placed == null) {
            placed = defaultState;
        }
        LevelReader levelreader = pContext.getLevel();
        BlockPos blockpos = pContext.getClickedPos();
        return placed != null && canPlace(pContext, placed) && levelreader.isUnobstructed(placed, blockpos, CollisionContext.empty()) ? placed : null;
    }

    @Override
    public void registerBlocks(Map<Block, Item> pBlockToItemMap, Item pItem) {
        super.registerBlocks(pBlockToItemMap, pItem);
        pBlockToItemMap.put(this.wallPlacement, pItem);
    }
}
