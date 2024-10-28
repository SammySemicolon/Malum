package com.sammy.malum.common.block.nature;

import com.sammy.malum.registry.common.*;
import com.sammy.malum.visual_effects.networked.*;
import com.sammy.malum.visual_effects.networked.data.*;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import team.lodestar.lodestone.helpers.BlockHelper;
import team.lodestar.lodestone.registry.common.particle.*;

import java.awt.*;
import java.util.function.Supplier;

public class SapFilledLogBlock extends RotatedPillarBlock {
    public final Supplier<Block> drained;
    public final Supplier<Item> sap;
    public final Color sapColor;

    public SapFilledLogBlock(Properties properties, Supplier<Block> drained, Supplier<Item> sap, Color sapColor) {
        super(properties);
        this.drained = drained;
        this.sap = sap;
        this.sapColor = sapColor;
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack itemstack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        if (itemstack.getItem() == Items.GLASS_BOTTLE) {
            if (!level.isClientSide) {
                itemstack.shrink(1);
                level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.BOTTLE_FILL, SoundSource.NEUTRAL, 1.0F, 1.0F);
                ItemHandlerHelper.giveItemToPlayer(player, new ItemStack(sap.get()));
                ParticleEffectTypeRegistry.SAP_COLLECTED.createPositionedEffect((ServerLevel) level, new PositionEffectData(pos), new ColorEffectData(sapColor), SapCollectionParticleEffect.createData(hit.getDirection()));
                if (level.random.nextBoolean()) {
                    BlockHelper.setBlockStateWithExistingProperties(level, pos, drained.get().defaultBlockState(), 3);
                }
                collectSap(level, pos, player);
            }
            return ItemInteractionResult.SUCCESS;
        }
        return super.useItemOn(itemstack, state, level, pos, player, handIn, hit);
    }

    public void collectSap(Level level, BlockPos pos, Player player) {
    }
}