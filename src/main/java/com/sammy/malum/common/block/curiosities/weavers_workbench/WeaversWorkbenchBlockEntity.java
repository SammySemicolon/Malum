package com.sammy.malum.common.block.curiosities.weavers_workbench;

import com.sammy.malum.common.container.WeaversWorkbenchContainer;
import com.sammy.malum.common.packets.particle.rite.BlightTransformItemParticlePacket;
import com.sammy.malum.registry.common.PacketRegistry;
import com.sammy.malum.registry.common.SoundRegistry;
import com.sammy.malum.registry.common.block.BlockEntityRegistry;
import com.sammy.malum.registry.common.item.DataComponentRegistry;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import team.lodestar.lodestone.helpers.block.*;
import team.lodestar.lodestone.systems.blockentity.LodestoneBlockEntity;

import java.util.List;

import static com.sammy.malum.common.item.cosmetic.skins.ArmorSkin.getApplicableItemSkinTag;
import static com.sammy.malum.registry.common.SpiritTypeRegistry.ARCANE_SPIRIT;

public class WeaversWorkbenchBlockEntity extends LodestoneBlockEntity {

    public final WeaversWorkbenchItemHandler itemHandler = new WeaversWorkbenchItemHandler(2, 1, this);

    public WeaversWorkbenchBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.WEAVERS_WORKBENCH.get(), pos, state);
    }

    @Override
    public ItemInteractionResult onUse(Player player, InteractionHand hand) {
        if (player instanceof ServerPlayer serverPlayer) {
            var be = this;
            serverPlayer.openMenu(new ExtendedScreenHandlerFactory<FriendlyByteBuf>() {
                @Override
                public FriendlyByteBuf getScreenOpeningData(ServerPlayer serverPlayer) {
                    var buf = new FriendlyByteBuf(Unpooled.buffer());
                    buf.writeBlockPos(getBlockPos());
                    return buf;
                }

                @Override
                public Component getDisplayName() {
                    return WeaversWorkbenchContainer.component;
                }

                @Nullable
                @Override
                public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
                    return new WeaversWorkbenchContainer(i, inventory, ContainerLevelAccess.create(level, getBlockPos()));
                }
            });
        }
        return ItemInteractionResult.SUCCESS;
    }

    public void onCraft() {
        if (!level.isClientSide) {
            Vec3 itemPos = getItemPos();
            PacketRegistry.sendToPlayersTrackingChunk((ServerLevel) level, new ChunkPos(getBlockPos()), new BlightTransformItemParticlePacket(List.of(ARCANE_SPIRIT.getIdentifier()), itemPos));
            level.playSound(null, getBlockPos(), SoundRegistry.WEAVERS_WORKBENCH_CRAFT.get(), SoundSource.BLOCKS, 1, 0.9f + level.random.nextFloat() * 0.25f);
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
            if (target.has(DataComponentRegistry.ITEM_SKIN.get())) {
                ItemStack result = target.copy();
                target.remove(DataComponentRegistry.ITEM_SKIN.get());
                return result;
            }
        }
        if (!target.isEmpty() && !weave.isEmpty()) {
            ItemStack result = target.copy();
            String skinTag = getApplicableItemSkinTag(target, weave);
            if (skinTag != null) {
                if (skinTag.equals(target.get(DataComponentRegistry.ITEM_SKIN.get()))) {
                    return ItemStack.EMPTY;
                }
                result.set(DataComponentRegistry.ITEM_SKIN.get(), skinTag);
                return result;
            }
        }
        return ItemStack.EMPTY;
    }

    public Vec3 getItemPos() {
        return BlockPosHelper.fromBlockPos(getBlockPos()).add(itemOffset());
    }

    public Vec3 itemOffset() {
        return new Vec3(0.5f, 1.35f, 0.5f);
    }
}