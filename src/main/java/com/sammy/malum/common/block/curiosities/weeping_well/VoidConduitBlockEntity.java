package com.sammy.malum.common.block.curiosities.weeping_well;

import com.sammy.malum.common.packets.CodecUtil;
import com.sammy.malum.common.recipe.void_favor.*;
import com.sammy.malum.core.systems.recipe.LodestoneRecipeType;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.block.*;
import com.sammy.malum.registry.common.item.*;
import com.sammy.malum.registry.common.recipe.RecipeTypeRegistry;
import com.sammy.malum.visual_effects.*;
import com.sammy.malum.visual_effects.networked.*;
import com.sammy.malum.visual_effects.networked.data.*;
import net.minecraft.core.*;
import net.minecraft.nbt.*;
import net.minecraft.server.level.*;
import net.minecraft.sounds.*;
import net.minecraft.util.*;
import net.minecraft.world.entity.item.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.phys.*;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.systems.blockentity.*;

import java.util.*;

public class VoidConduitBlockEntity extends LodestoneBlockEntity {

    public final List<ItemStack> eatenItems = new ArrayList<>();
    public int progress;
    public int streak;
    public int lingeringRadiance;

    public VoidConduitBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.VOID_CONDUIT.get(), pos, state);
    }

    @Override
    protected void saveAdditional(CompoundTag compound, HolderLookup.Provider registries) {
        if (!eatenItems.isEmpty()) {
            compound.putInt("itemCount", eatenItems.size());
            for (int i = 0; i < eatenItems.size(); i++) {
                ItemStack stack = eatenItems.get(i);
                CompoundTag itemTag = (CompoundTag)CodecUtil.encodeNBT(ItemStack.CODEC, stack);
                compound.put("item_" + i, itemTag);
            }
        }
        compound.putInt("progress", progress);
        compound.putInt("streak", streak);
        compound.putInt("lingeringRadiance", lingeringRadiance);
        super.saveAdditional(compound, registries);
    }

    @Override
    protected void loadAdditional(CompoundTag compound, HolderLookup.Provider pRegistries) {
        eatenItems.clear();
        for (int i = 0; i < compound.getInt("itemCount"); i++) {
            CompoundTag itemTag = compound.getCompound("item_" + i);
            eatenItems.add(CodecUtil.decodeNBT(ItemStack.CODEC, itemTag));
        }
        progress = compound.getInt("progress");
        streak = compound.getInt("streak");
        lingeringRadiance = compound.getInt("lingeringRadiance");
    }

    @Override
    public void tick() {
        super.tick();
        if (lingeringRadiance > 0) {
            lingeringRadiance--;
        }
        if (level instanceof ServerLevel serverLevel) {
            if (serverLevel.getGameTime() % 100L == 0) {
                level.playSound(null, worldPosition, SoundRegistry.UNCANNY_VALLEY.get(), SoundSource.HOSTILE, 1f, Mth.nextFloat(level.getRandom(), 0.55f, 1.75f));
            }
            if (serverLevel.getGameTime() % 20L == 0) {
                level.playSound(null, worldPosition, SoundRegistry.VOID_HEARTBEAT.get(), SoundSource.HOSTILE, 1.5f, Mth.nextFloat(level.getRandom(), 0.95f, 1.15f));
            }
            if (serverLevel.getGameTime() % 40L == 0) {
                eatItems(serverLevel);
            }
            if (!eatenItems.isEmpty()) {
                progress++;
                if (progress >= 80) {
                    int resultingProgress = 60;
                    ParticleEffectType particleEffectType = ParticleEffectTypeRegistry.WEEPING_WELL_REACTS;
                    ItemStack stack = eatenItems.get(eatenItems.size() - 1);
                    if (stack.getItem().equals(ItemRegistry.BLIGHTED_GUNK.get())) {
                        resultingProgress += streak / 2f;
                        streak++;
                        level.playSound(null, worldPosition, SoundRegistry.VOID_EATS_GUNK.get(), SoundSource.PLAYERS, 0.7f, 0.6f + level.random.nextFloat() * 0.3f + streak * 0.05f);
                        level.playSound(null, worldPosition, SoundEvents.GENERIC_EAT, SoundSource.PLAYERS, 0.7f, 0.6f + level.random.nextFloat() * 0.2f + streak * 0.05f);
                    } else {
                        Item result = spitOutItem(stack);
                        if (result.equals(ItemRegistry.FUSED_CONSCIOUSNESS.get())) {
                            lingeringRadiance = 400;
                            particleEffectType = ParticleEffectTypeRegistry.WEEPING_WELL_EMITS_RADIANCE;
                        }
                    }
                    progress = resultingProgress;
                    eatenItems.remove(eatenItems.size() - 1);
                    particleEffectType.createPositionedEffect((ServerLevel) level, new PositionEffectData(worldPosition.getX() + 0.5f, worldPosition.getY() + 0.6f, worldPosition.getZ() + 0.5f));
                    BlockHelper.updateAndNotifyState(level, worldPosition);
                }
                if (eatenItems.isEmpty()) {
                    progress = 0;
                }
            } else if (streak != 0) {
                streak = 0;
            }
        } else {
            if (lingeringRadiance <= 100) {
                WeepingWellParticleEffects.passiveWeepingWellParticles(this);
            } else {
                RadiantParticleEffects.radiantWeepingWellParticles(this);
            }
        }
    }

    protected static Vec3 blockPosToVec3(BlockPos pos) {
        return new Vec3(pos.getX(), pos.getY(), pos.getZ());
    }

    public void eatItems(ServerLevel serverLevel) {
        List<ItemEntity> items = serverLevel.getEntitiesOfClass(ItemEntity.class, new AABB(blockPosToVec3(worldPosition.offset(1, -3, 1)), blockPosToVec3(worldPosition.offset(-1, -1, -1))).inflate(1))
                .stream().sorted(Comparator.comparingInt(itemEntity -> itemEntity.age)).toList();
        for (ItemEntity entity : items) {
            ItemStack item = entity.getItem();
            if (item.getItem().equals(ItemRegistry.BLIGHTED_GUNK.get())) {
                progress += 20;
            }
            eatenItems.add(item);
            entity.discard();
        }
        BlockHelper.updateAndNotifyState(level, worldPosition);
    }

    public Item spitOutItem(ItemStack stack) {
        FavorOfTheVoidRecipe recipe = LodestoneRecipeType.getRecipe(
                level, RecipeTypeRegistry.VOID_FAVOR.get(),
                new SingleRecipeInput(stack)
        );
        float pitch = Mth.nextFloat(level.getRandom(), 0.85f, 1.35f) + streak * 0.1f;
        if (recipe != null) {
            streak++;
            int amount = recipe.output.getCount() * stack.getCount();
            while (amount > 0) {
                int count = Math.min(64, amount);
                ItemStack outputStack = new ItemStack(recipe.output.getItem(), count);
                outputStack.applyComponents(recipe.output.getComponents());
                ItemEntity entity = new ItemEntity(level, worldPosition.getX() + 0.5f, worldPosition.getY() + 0.5f, worldPosition.getZ() + 0.5f, outputStack);
                entity.setDeltaMovement(0, 0.65f, 0.15f);
                level.addFreshEntity(entity);
                amount -= count;
            }
            level.playSound(null, worldPosition, SoundRegistry.VOID_TRANSMUTATION.get(), SoundSource.HOSTILE, 2f, pitch);
            return recipe.output.getItem();
        } else {
            ItemEntity entity = new ItemEntity(level, worldPosition.getX() + 0.5f, worldPosition.getY() + 0.5f, worldPosition.getZ() + 0.5f, stack);
            entity.setDeltaMovement(0, 0.65f, 0.15f);
            level.addFreshEntity(entity);
            level.playSound(null, worldPosition, SoundRegistry.VOID_REJECTION.get(), SoundSource.HOSTILE, 2f, pitch);
            return stack.getItem();
        }
    }

    /*@Override
    public AABB getRenderBoundingBox() {
        var pos = worldPosition;
        return new AABB(pos.getX() - 3, pos.getY() - 1, pos.getZ() - 3, pos.getX() + 4, pos.getY() + 2, pos.getZ() + 4);
    }*/
}