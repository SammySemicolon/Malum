package com.sammy.malum.common.block.curiosities.runic_workbench;

import com.sammy.malum.common.block.storage.*;
import com.sammy.malum.common.item.spirit.*;
import com.sammy.malum.common.packets.particle.rite.BlightTransformItemParticlePacket;
import com.sammy.malum.common.recipe.*;
import com.sammy.malum.core.systems.recipe.LodestoneRecipeType;
import com.sammy.malum.core.systems.recipe.SpiritBasedRecipeInput;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.block.*;
import com.sammy.malum.registry.common.recipe.RecipeTypeRegistry;
import net.minecraft.core.*;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.item.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.phys.*;
import net.neoforged.neoforge.network.PacketDistributor;
import team.lodestar.lodestone.helpers.*;

import java.util.*;

import static com.sammy.malum.registry.common.PacketRegistry.*;

public class RunicWorkbenchBlockEntity extends MalumItemHolderBlockEntity {

    public static final Vec3 RUNIC_WORKBENCH_ITEM_OFFSET = new Vec3(0.5f, 1.25f, 0.5f);

    public RunicWorkbenchBlockEntity(BlockEntityType<? extends RunicWorkbenchBlockEntity> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public RunicWorkbenchBlockEntity(BlockPos pos, BlockState state) {
        this(BlockEntityRegistry.RUNIC_WORKBENCH.get(), pos, state);
    }

    @Override
    public Vec3 getItemOffset(float partialTicks) {
        if (inventory.getStackInSlot(0).getItem() instanceof SpiritShardItem) {
            float gameTime = level.getGameTime() + partialTicks;
            return RUNIC_WORKBENCH_ITEM_OFFSET.add(0, (float) Math.sin((gameTime % 360) / 20f) * 0.05f, 0);
        }
        return RUNIC_WORKBENCH_ITEM_OFFSET;
    }

    @Override
    public ItemInteractionResult onUseWithItem(Player player, ItemStack secondaryInput, InteractionHand hand) {
        final ItemStack primaryInput = inventory.getStackInSlot(0);
        if (!primaryInput.isEmpty()) {
            RunicWorkbenchRecipe recipe = LodestoneRecipeType.getRecipe(player.level(), RecipeTypeRegistry.RUNEWORKING.get(), new SpiritBasedRecipeInput(primaryInput, secondaryInput));
            if (recipe != null) {
                Vec3 itemPos = getItemPos();
                if (!level.isClientSide) {
                    primaryInput.shrink(recipe.primaryInput.count());
                    secondaryInput.shrink(recipe.secondaryInput.getCount());
                    level.addFreshEntity(new ItemEntity(level, itemPos.x, itemPos.y, itemPos.z, recipe.output.copy()));
                    level.playSound(null, worldPosition, SoundRegistry.ALTERATION_PLINTH_ALTERS.get(), SoundSource.BLOCKS, 1, 0.9f + level.random.nextFloat() * 0.25f);
                    if (secondaryInput.getItem() instanceof SpiritShardItem spiritShardItem) {
                        PacketDistributor.sendToPlayersTrackingChunk((ServerLevel) level, new ChunkPos(getBlockPos()), new BlightTransformItemParticlePacket(List.of(spiritShardItem.type.getIdentifier()), itemPos));
                    }
                    BlockHelper.updateAndNotifyState(level, worldPosition);
                }
                return ItemInteractionResult.SUCCESS;
            }
        }


        return super.onUse(player, hand);
    }
}