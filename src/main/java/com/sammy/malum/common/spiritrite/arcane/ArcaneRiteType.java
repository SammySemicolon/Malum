package com.sammy.malum.common.spiritrite.arcane;

import com.sammy.malum.common.block.blight.*;
import com.sammy.malum.common.block.curiosities.totem.*;
import com.sammy.malum.common.block.storage.*;
import com.sammy.malum.common.packets.particle.rite.BlightTransformItemParticlePacket;
import com.sammy.malum.common.packets.particle.rite.generic.BlockSparkleParticlePacket;
import com.sammy.malum.common.recipe.spirit.transmutation.*;
import com.sammy.malum.common.spiritrite.*;
import com.sammy.malum.common.worldevent.*;
import net.minecraft.core.*;
import net.minecraft.server.level.*;
import net.minecraft.world.entity.item.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.phys.*;
import net.neoforged.neoforge.network.PacketDistributor;
import team.lodestar.lodestone.handlers.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.systems.blockentity.*;

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
                WorldEventHandler.addWorldEvent(totemBase.getLevel(), new TotemCreatedBlightEvent().setPosition(totemBase.getBlockPos()).setBlightData(2, 4, 4));
            }
        };
    }

    @Override
    public TotemicRiteEffect getCorruptedEffect() {
        return new TotemicRiteEffect(TotemicRiteEffect.MalumRiteEffectCategory.RADIAL_BLOCK_EFFECT) {

            @SuppressWarnings("ConstantConditions")
            @Override
            public void doRiteEffect(TotemBaseBlockEntity totemBase, ServerLevel level) {
                BlockPos pos = totemBase.getBlockPos();
                List<BlockPos> nearbyBlocks = getNearbyBlocks(totemBase, BlightedSoilBlock.class).toList();
                for (BlockPos p : nearbyBlocks) {
                    BlockPos posToTransmute = p.above();
                    BlockState stateToTransmute = level.getBlockState(posToTransmute);
                    if (level.getBlockEntity(posToTransmute) instanceof IMalumSpecialItemAccessPoint iMalumSpecialItemAccessPoint) {
                        LodestoneBlockEntityInventory inventoryForAltar = iMalumSpecialItemAccessPoint.getSuppliedInventory();
                        ItemStack stack = inventoryForAltar.getStackInSlot(0);
                        var recipe = SpiritTransmutationRecipe.getRecipe(level, stack);
                        if (recipe != null && !inventoryForAltar.extractItem(0, 1, true).isEmpty()) {
                            Vec3 itemPos = iMalumSpecialItemAccessPoint.getItemPos();
                            level.addFreshEntity(new ItemEntity(level, itemPos.x, itemPos.y, itemPos.z, recipe.output.copy()));
                            PacketDistributor.sendToPlayersTrackingChunk(level, new ChunkPos(p), new BlightTransformItemParticlePacket(List.of(ARCANE_SPIRIT.getIdentifier()), itemPos));
                            inventoryForAltar.extractItem(0, 1, false);
                            BlockStateHelper.updateAndNotifyState(level, p);
                        }
                    }
                    ItemStack stack = stateToTransmute.getBlock().asItem().getDefaultInstance();
                    var recipe = SpiritTransmutationRecipe.getRecipe(level, stack);
                    if (recipe != null) {
                        ItemStack output = recipe.output.copy();
                        if (output.getItem() instanceof BlockItem blockItem) {
                            Block block = blockItem.getBlock();
                            BlockEntity entity = level.getBlockEntity(posToTransmute);
                            BlockState newState = BlockHelper.setBlockStateWithExistingProperties(level, posToTransmute, block.defaultBlockState(), 3);
                            level.levelEvent(2001, posToTransmute, Block.getId(newState));
                            PacketDistributor.sendToPlayersTrackingChunk(level, new ChunkPos(posToTransmute), new BlockSparkleParticlePacket(ARCANE_SPIRIT.getPrimaryColor(), posToTransmute, true));
                            if (block instanceof EntityBlock entityBlock) {
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
