package com.sammy.malum.common.block.curiosities.weavers_workbench;

import com.sammy.malum.common.container.WeaversWorkbenchContainer;
import com.sammy.malum.common.packets.particle.rite.BlightTransformItemParticlePacket;
import com.sammy.malum.registry.common.SoundRegistry;
import com.sammy.malum.registry.common.block.BlockEntityRegistry;
import com.sammy.malum.registry.common.item.DataComponentRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.capabilities.IBlockCapabilityProvider;
import net.neoforged.neoforge.common.CommonHooks;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import team.lodestar.lodestone.helpers.BlockHelper;
import team.lodestar.lodestone.systems.blockentity.LodestoneBlockEntity;

import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

import static com.sammy.malum.common.item.cosmetic.skins.ArmorSkin.MALUM_SKIN_TAG;
import static com.sammy.malum.common.item.cosmetic.skins.ArmorSkin.getApplicableItemSkinTag;
import static com.sammy.malum.registry.common.PacketRegistry.MALUM_CHANNEL;
import static com.sammy.malum.registry.common.SpiritTypeRegistry.ARCANE_SPIRIT;

public class WeaversWorkbenchBlockEntity extends LodestoneBlockEntity implements IBlockCapabilityProvider<IItemHandler, Direction> {

    public final WeaversWorkbenchItemHandler itemHandler = new WeaversWorkbenchItemHandler(2, 1, this);

    public WeaversWorkbenchBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.WEAVERS_WORKBENCH.get(), pos, state);
    }

    @Override
    public ItemInteractionResult onUse(Player player, InteractionHand pHand) {
        if (player instanceof ServerPlayer serverPlayer) {
            MenuProvider container = new SimpleMenuProvider((w, p, pl) -> new WeaversWorkbenchContainer(w, p, this), WeaversWorkbenchContainer.component);
            serverPlayer.openMenu(container, buf -> buf.writeBlockPos(this.getBlockPos()));
        }
        return ItemInteractionResult.SUCCESS;
    }

    @Override
    public @Nullable IItemHandler getCapability(Level level, BlockPos blockPos, BlockState blockState, @Nullable BlockEntity blockEntity, Direction direction) {
        return itemHandler;
    }

    public void onCraft() {
        if (!level.isClientSide) {
            Vec3 itemPos = getItemPos();
            PacketDistributor.sendToPlayersTrackingChunk((ServerLevel) level, new ChunkPos(getBlockPos()), new BlightTransformItemParticlePacket(List.of(ARCANE_SPIRIT.getIdentifier()), itemPos));
            level.playSound(null, getBlockPos(), SoundRegistry.ALTERATION_PLINTH_ALTERS.get(), SoundSource.BLOCKS, 1, 0.9f + level.random.nextFloat() * 0.25f);
        }
        itemHandler.getStackInSlot(0).shrink(1);
        itemHandler.getStackInSlot(1).shrink(1);
    }

    public ItemStack tryCraft() {
        itemHandler.isCrafting = true;
        ItemStack output = getOutput();
        itemHandler.setStackInSlot(2, output);
        itemHandler.isCrafting = false;
        return output;
    }

    public ItemStack getOutput() {
        ItemStack target = itemHandler.getStackInSlot(0);
        ItemStack weave = itemHandler.getStackInSlot(1);
        if (!target.isEmpty() && weave.isEmpty()) {
            if (target.has(DataComponentRegistry.ITEM_SKIN)) {
                ItemStack result = target.copy();
                target.remove(DataComponentRegistry.ITEM_SKIN);
                return result;
            }
        }
        if (!target.isEmpty() && !weave.isEmpty()) {
            ItemStack result = target.copy();
            String skinTag = getApplicableItemSkinTag(target, weave);
            if (skinTag != null) {
                if (target.get(DataComponentRegistry.ITEM_SKIN).equals(skinTag)) {
                    return ItemStack.EMPTY;
                }
                result.set(DataComponentRegistry.ITEM_SKIN, skinTag);
                return result;
            }
        }
        return ItemStack.EMPTY;
    }

    public Vec3 getItemPos() {
        return BlockHelper.fromBlockPos(getBlockPos()).add(itemOffset());
    }

    public Vec3 itemOffset() {
        return new Vec3(0.5f, 1.35f, 0.5f);
    }
}