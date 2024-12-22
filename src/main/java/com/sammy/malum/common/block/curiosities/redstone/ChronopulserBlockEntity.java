package com.sammy.malum.common.block.curiosities.redstone;

import com.sammy.malum.common.data_components.SoulwovenBannerPatternData;
import com.sammy.malum.common.item.spirit.SpiritShardItem;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import com.sammy.malum.registry.common.ParticleEffectTypeRegistry;
import com.sammy.malum.registry.common.SoundRegistry;
import com.sammy.malum.registry.common.SpiritTypeRegistry;
import com.sammy.malum.registry.common.block.BlockEntityRegistry;
import com.sammy.malum.registry.common.item.DataComponentRegistry;
import com.sammy.malum.visual_effects.networked.data.ColorEffectData;
import com.sammy.malum.visual_effects.networked.data.PositionEffectData;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.ItemAbilities;
import team.lodestar.lodestone.helpers.block.BlockStateHelper;
import team.lodestar.lodestone.systems.blockentity.LodestoneBlockEntity;

import javax.annotation.Nullable;

public class ChronopulserBlockEntity extends LodestoneBlockEntity {

    public int timer;
    public int frequency = 80;
    public ChronopulserBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.CHRONOPULSER.get(), pos, state);
    }

    @Override
    public void tick() {
        super.tick();
        timer++;
        if (timer >= frequency) {

            timer = 0;
            if (level instanceof ServerLevel) {
                level.setBlock(getBlockPos(), getBlockState().setValue(ChronopulserBlock.LIT, true), 3);
                level.scheduleTick(worldPosition, getBlockState().getBlock(), 10);
                BlockStateHelper.updateAndNotifyState(level, worldPosition);
            } else {
                Vec3 center = getBlockPos().getCenter();
                float offset = 0.625f;
                level.addParticle(DustParticleOptions.REDSTONE, center.x+ offset, center.y, center.z, 0, 0, 0);
                level.addParticle(DustParticleOptions.REDSTONE, center.x-offset, center.y, center.z, 0, 0, 0);
                level.addParticle(DustParticleOptions.REDSTONE, center.x, center.y, center.z+offset, 0, 0, 0);
                level.addParticle(DustParticleOptions.REDSTONE, center.x, center.y, center.z-offset, 0, 0, 0);
            }
        }
    }

    @Override
    protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.loadAdditional(pTag, pRegistries);
        timer = pTag.getInt("timer");
        frequency = pTag.getInt("frequency");
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("timer", timer);
        tag.putInt("frequency", frequency);
    }
}