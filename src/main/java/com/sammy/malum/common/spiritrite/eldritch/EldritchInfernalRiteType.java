package com.sammy.malum.common.spiritrite.eldritch;

import com.sammy.malum.common.block.curiosities.totem.*;
import com.sammy.malum.core.systems.rite.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.visual_effects.networked.data.*;
import net.minecraft.server.level.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.*;

import static com.sammy.malum.registry.common.SpiritTypeRegistry.*;

public class EldritchInfernalRiteType extends TotemicRiteType {
    public EldritchInfernalRiteType() {
        super("greater_infernal_rite", ELDRITCH_SPIRIT, ARCANE_SPIRIT, INFERNAL_SPIRIT, INFERNAL_SPIRIT);
    }

    @Override
    public TotemicRiteEffect getNaturalRiteEffect() {
        return new TotemicRiteEffect(TotemicRiteEffect.MalumRiteEffectCategory.DIRECTIONAL_BLOCK_EFFECT) {
            @Override
            public void doRiteEffect(TotemBaseBlockEntity totemBase, ServerLevel level) {
                getBlocksAhead(totemBase).forEach(p -> {
                    var state = level.getBlockState(p);
                    var recipeOptional = level.getRecipeManager().getRecipeFor(RecipeType.SMELTING, new SingleRecipeInput(new ItemStack(state.getBlock().asItem(), 1)), level);
                    if (recipeOptional.isPresent()) {
                        var recipe = recipeOptional.get().value();
                        var output = recipe.getResultItem(level.registryAccess());
                        if (output.getItem() instanceof BlockItem blockItem) {
                            var block = blockItem.getBlock();
                            var newState = block.defaultBlockState();
                            level.setBlockAndUpdate(p, newState);
                            level.levelEvent(2001, p, Block.getId(newState));
                            ParticleEffectTypeRegistry.BLOCK_RITE_EFFECT.createPositionedEffect(level, new PositionEffectData(p), new ColorEffectData(INFERNAL_SPIRIT));
                        }
                    }
                });
            }
        };
    }

    @Override
    public TotemicRiteEffect getCorruptedEffect() {
        return new TotemicRiteEffect(TotemicRiteEffect.MalumRiteEffectCategory.RADIAL_BLOCK_EFFECT) {
            @Override
            public void doRiteEffect(TotemBaseBlockEntity totemBase, ServerLevel level) {
                getNearbyBlocks(totemBase, AbstractFurnaceBlock.class).forEach(p -> {
                    if (level.getBlockEntity(p) instanceof AbstractFurnaceBlockEntity furnace) {
                        ParticleEffectTypeRegistry.BLOCK_RITE_EFFECT.createPositionedEffect(level, new PositionEffectData(furnace.getBlockPos()), new ColorEffectData(INFERNAL_SPIRIT));
                        furnace.cookingProgress = Math.min(furnace.cookingProgress + 20, furnace.cookingTotalTime - 1);
                    }
                });
            }
        };
    }
}