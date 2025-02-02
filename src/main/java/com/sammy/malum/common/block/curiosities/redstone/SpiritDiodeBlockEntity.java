package com.sammy.malum.common.block.curiosities.redstone;

import com.sammy.malum.common.packets.spirit_diode.SpiritDiodeToggleOpenPayload;
import com.sammy.malum.common.packets.spirit_diode.SpiritDiodeUpdatePayload;
import com.sammy.malum.registry.common.ParticleEffectTypeRegistry;
import com.sammy.malum.registry.common.SoundRegistry;
import com.sammy.malum.registry.common.item.ItemTagRegistry;
import com.sammy.malum.visual_effects.networked.data.ColorEffectData;
import com.sammy.malum.visual_effects.networked.data.PositionEffectData;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.network.PacketDistributor;
import team.lodestar.lodestone.helpers.RandomHelper;
import team.lodestar.lodestone.systems.blockentity.LodestoneBlockEntity;

import java.awt.*;

public class SpiritDiodeBlockEntity extends LodestoneBlockEntity {

    protected static final Color REDSTONE_COLOR = new Color(170, 15, 1);
    public int delay;
    public int outputSignal;

    public long toggleTime;

    public int inputSignal;
    public long visualStartTime;
    public int visualTransitionTime;
    public int visualTransitionStart;
    public int visualTransitionEnd;

    public SpiritDiodeBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        if (state.getBlock() instanceof SpiritDiodeBlock<?> diodeBlock) {
            delay = diodeBlock.getDefaultFrequency(pos, state);
        }
    }

    public int getOutputSignal() {
        return Mth.clamp(outputSignal, 0, 15);
    }

    public void updateVisuals(int outputSignal, int inputSignal, boolean isPowering) {
        this.outputSignal = outputSignal;
        this.inputSignal = inputSignal;
        this.visualStartTime = getLevel().getGameTime();
        this.visualTransitionTime = 2 * delay;
        this.visualTransitionStart = isPowering ? 0 : 1;
        this.visualTransitionEnd = 1 - visualTransitionStart;
    }

    public void updateAnimation(ServerLevel serverLevel, BlockPos pos, int inputSignal) {
        int outputSignal = getOutputSignal();
        PacketDistributor.sendToPlayersTrackingChunk(serverLevel,
                new ChunkPos(pos), new SpiritDiodeUpdatePayload(pos, outputSignal, inputSignal, outputSignal == 0));
    }
    public void updateToggle(ServerLevel serverLevel, BlockPos pos, boolean isOpen) {
        PacketDistributor.sendToPlayersTrackingChunk(serverLevel,
                new ChunkPos(pos), new SpiritDiodeToggleOpenPayload(pos, isOpen));
    }

    @Override
    public ItemInteractionResult onUseWithItem(Player pPlayer, ItemStack pStack, InteractionHand pHand) {
        if (pStack.is(ItemTagRegistry.IS_REDSTONE_TOOL)) {
            if (pPlayer.isCrouching()) {
                if (pPlayer.getCooldowns().isOnCooldown(pStack.getItem())) {
                    return ItemInteractionResult.FAIL;
                }
                if (level instanceof ServerLevel serverLevel) {
                    var isOpen = getBlockState().getValue(SpiritDiodeBlock.OPEN);
                    level.setBlock(getBlockPos(), getBlockState().setValue(SpiritDiodeBlock.OPEN, !isOpen), 3);
                    level.playSound(null, getBlockPos(), isOpen ? SoundRegistry.SPIRIT_DIODE_CLOSE.get() : SoundRegistry.SPIRIT_DIODE_OPEN.get(), SoundSource.BLOCKS, 0.8f, RandomHelper.randomBetween(level.getRandom(), 0.9f, 1.1f));
                    pPlayer.getCooldowns().addCooldown(pStack.getItem(), 20);
                    var particleEffect = isOpen ? ParticleEffectTypeRegistry.SPIRIT_DIODE_CLOSE : ParticleEffectTypeRegistry.SPIRIT_DIODE_OPEN;
                    particleEffect.createPositionedEffect(serverLevel,
                            new PositionEffectData(worldPosition.getCenter().add(0, isOpen ? 0 : 0.5f, 0)),
                            new ColorEffectData(REDSTONE_COLOR));
                    updateToggle(serverLevel, getBlockPos(), isOpen);
                }
                return ItemInteractionResult.SUCCESS;
            }
            if (getBlockState().getValue(SpiritDiodeBlock.OPEN)) {
                if (getBlockState().getBlock() instanceof SpiritDiodeBlock<?> diodeBlock) {
                    delay = diodeBlock.getFrequencyPresets()[2];
                }
            }
        }
        return super.onUseWithItem(pPlayer, pStack, pHand);
    }

    @Override
    protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.loadAdditional(pTag, pRegistries);
        delay = pTag.getInt("delay");
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("delay", delay);
    }
}