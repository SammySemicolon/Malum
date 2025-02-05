package com.sammy.malum.common.spiritrite.arcane;

import com.sammy.malum.common.block.curiosities.totem.*;
import com.sammy.malum.common.worldevent.*;
import com.sammy.malum.core.systems.rite.*;
import com.sammy.malum.registry.common.block.*;
import com.sammy.malum.registry.common.recipe.*;
import net.minecraft.core.*;
import net.minecraft.server.level.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.*;
import team.lodestar.lodestone.handlers.*;
import team.lodestar.lodestone.helpers.block.*;
import team.lodestar.lodestone.systems.recipe.*;

import java.util.*;

import static com.sammy.malum.registry.common.SpiritTypeRegistry.*;

public class ArcaneRiteType extends TotemicRiteType {
    public ArcaneRiteType() {
        super("arcane_rite", ARCANE_SPIRIT, ARCANE_SPIRIT, ARCANE_SPIRIT, ARCANE_SPIRIT, ARCANE_SPIRIT);
    }

    @Override
    public TotemicRiteEffect getNaturalRiteEffect() {
        return new TotemicRiteEffect(TotemicRiteEffect.MalumRiteEffectCategory.ONE_TIME_EFFECT) {

            @SuppressWarnings("ConstantConditions")
            @Override
            public void doRiteEffect(TotemBaseBlockEntity totemBase, ServerLevel level) {
                WorldEventHandler.addWorldEvent(totemBase.getLevel(),
                        new UnchainedTotemConversionWorldEvent()
                                .setPosition(totemBase.getBlockPos())
                                .setData(List.of(1, 3, 5, 6, 7, 8), 4, 0));
            }
        };
    }

    @Override
    public TotemicRiteEffect getCorruptedEffect() {
        return new TotemicRiteEffect(TotemicRiteEffect.MalumRiteEffectCategory.RADIAL_BLOCK_EFFECT) {

            @Override
            public boolean canAffectBlock(TotemBaseBlockEntity totemBase, BlockState state, BlockPos pos) {
                return state.is(BlockTagRegistry.UNCHAINED_RITE_CATALYST) && super.canAffectBlock(totemBase, state, pos);
            }

            @SuppressWarnings("ConstantConditions")
            @Override
            public void doRiteEffect(TotemBaseBlockEntity totemBase, ServerLevel level) {
                var pos = totemBase.getBlockPos();
                var nearbyBlocks = getNearbyBlocks(totemBase, Block.class).toList();
                for (BlockPos p : nearbyBlocks) {
                    var targetPos = p.above();
                    var targetState = level.getBlockState(targetPos);
                    var targetAsItem = targetState.getBlock().asItem().getDefaultInstance();
                    var recipe = LodestoneRecipeType.getRecipe(level, RecipeTypeRegistry.SPIRIT_TRANSMUTATION.get(), new SingleRecipeInput(targetAsItem));
                    if (recipe != null) {
                        if (recipe.output.getItem() instanceof BlockItem blockItem) {
                            Block resultBlock = blockItem.getBlock();
                            BlockState newState = BlockStateHelper.setBlockStateWithExistingProperties(level, targetPos, resultBlock.defaultBlockState(), 3);
                            level.levelEvent(2001, targetPos, Block.getId(newState));
                            if (resultBlock instanceof EntityBlock entityBlock) {
                                BlockEntity entity = level.getBlockEntity(targetPos);
                                if (entity != null) {
                                    BlockEntity newEntity = entityBlock.newBlockEntity(pos, newState);
                                    if (newEntity != null) {
                                        if (newEntity.getClass().equals(entity.getClass())) {
                                            level.setBlockEntity(entity);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        };
    }
}
