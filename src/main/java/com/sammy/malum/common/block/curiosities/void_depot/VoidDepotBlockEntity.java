package com.sammy.malum.common.block.curiosities.void_depot;

import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.block.*;
import com.sammy.malum.registry.common.item.*;
import com.sammy.malum.visual_effects.*;
import com.sammy.malum.visual_effects.networked.data.*;
import net.minecraft.core.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.*;
import net.minecraft.resources.*;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.state.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.systems.blockentity.*;

import java.util.*;
import java.util.function.*;

public class VoidDepotBlockEntity extends LodestoneBlockEntity {

    public final List<VoidDepotGoal> goals = new ArrayList<>();
    public final List<String> textToDisplay = new ArrayList<>();
    public boolean repeatable = false;
    public boolean oncePerPlayer = true;
    public List<UUID> playersWhoCompleted = new ArrayList<>();

    public int nearTimer;
    public float textVisibility = 0;

    public VoidDepotBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.VOID_DEPOT.get(), pos, state);
    }

    @Override
    protected void saveAdditional(CompoundTag compound, HolderLookup.Provider registries) {
        super.saveAdditional(compound, registries);

        CompoundTag textTag = new CompoundTag();
        if (!textToDisplay.isEmpty()) {
            for (int i = 0; i < textToDisplay.size(); i++) {
                String text = textToDisplay.get(i);
                textTag.putString("line_"+i, text);
            }
        }
        compound.put("textDisplay", textTag);

        CompoundTag goalsTag = new CompoundTag();
        if (!goals.isEmpty()) {
            for (int i = 0, goalsSize = goals.size(); i < goalsSize; i++) {
                VoidDepotGoal goal = goals.get(i);
                CompoundTag goalTag = goal.serialize();
                goalTag.putString("goalName", goal.index);
                goalTag.putString("type", goal.type.name);
                goalTag.putBoolean("completed", goal.completed);
                goalsTag.put("goal_" + i, goalTag);
            }
            goalsTag.putBoolean("repeatable", repeatable);
            goalsTag.putBoolean("oncePerPlayer", oncePerPlayer);

            if (!playersWhoCompleted.isEmpty()) {
                CompoundTag playerList = new CompoundTag();
                for (int i = 0; i < playersWhoCompleted.size(); i++) {
                    playerList.putUUID("player_" + i, playersWhoCompleted.get(i));
                }
                goalsTag.put("playersWhoCompleted", playerList);
            }
            compound.put("goals", goalsTag);
        }
    }

    @Override
    protected void loadAdditional(CompoundTag compound, HolderLookup.Provider pRegistries) {
        super.loadAdditional(compound, pRegistries);

        if (compound.contains("textDisplay")) {
            textToDisplay.clear();
            CompoundTag textTag = compound.getCompound("textDisplay");
            int lineCount = textTag.size();
            for (int i = 0; i < lineCount; i++) {
                if (!textTag.contains("line_" + i)) {
                    continue;
                }
                textToDisplay.add(textTag.getString("line_" + i));
            }
        }
        if (compound.contains("goals")) {
            goals.clear();
            CompoundTag goalsTag = compound.getCompound("goals");
            int goalCount = goalsTag.size();
            for (int i = 0; i < goalCount; i++) {
                if (!goalsTag.contains("goal_" + i)) {
                    continue;
                }
                CompoundTag goalTag = goalsTag.getCompound("goal_" + i);
                final VoidDepotGoal.VoidDepotGoalType type = CODEC.byName(goalTag.getString("type"));
                if (type == null) {
                    continue;
                }
                final VoidDepotGoal goal = type.deserializer.apply(goalTag);
                goal.setCompleted(goalTag.getBoolean("completed"));
                goals.add(goal);
            }

            playersWhoCompleted.clear();
            CompoundTag playerList = goalsTag.getCompound("playersWhoCompleted");
            if (!playerList.isEmpty()) {
                int playerCount = playerList.size();
                for (int i = 0; i < playerCount; i++) {
                    if (!playerList.contains("player_" + i)) {
                        continue;
                    }
                    playersWhoCompleted.add(playerList.getUUID("player_" + i));
                }
            }
            repeatable = goalsTag.getBoolean("repeatable");
            oncePerPlayer = goalsTag.getBoolean("oncePerPlayer");
        }
    }

    @Override
    public ItemInteractionResult onUseWithItem(Player player, ItemStack stack, InteractionHand hand) {
        if (!goals.isEmpty()) {
            if (player.getItemInHand(hand).getItem().equals(ItemRegistry.CREATIVE_SCYTHE.get())) {
                List<VoidDepotGoal> newGoals = new ArrayList<>();
                for (VoidDepotGoal goal : goals) {
                    if (goal instanceof ItemGoal itemGoal) {
                        final int amount = Math.max(itemGoal.amount / 2, 1);
                        newGoals.add(new ItemGoal(itemGoal.index, itemGoal.item, amount, Math.min(itemGoal.deliveredAmount, amount)));
                    }
                    if (goal instanceof ExperienceGoal experienceGoal) {
                        final int amount = Math.max(experienceGoal.amount / 2, 1);
                        newGoals.add(new ExperienceGoal(experienceGoal.index, amount, Math.min(experienceGoal.deliveredAmount, amount)));
                    }
                }
                this.goals.clear();
                this.goals.addAll(newGoals);
                BlockHelper.updateState(level, getBlockPos());
                return ItemInteractionResult.SUCCESS;
            }
            if (player.getItemInHand(hand).getItem().equals(ItemRegistry.VOID_CONDUIT.get())) {
                List<VoidDepotGoal> newGoals = new ArrayList<>();
                for (VoidDepotGoal goal : goals) {
                    if (goal instanceof ItemGoal itemGoal) {
                        newGoals.add(new ItemGoal(itemGoal.index, itemGoal.item, itemGoal.amount*2, itemGoal.deliveredAmount));
                    }
                    if (goal instanceof ExperienceGoal experienceGoal) {
                        newGoals.add(new ExperienceGoal(experienceGoal.index, experienceGoal.amount*2, experienceGoal.deliveredAmount));
                    }
                }
                this.goals.clear();
                this.goals.addAll(newGoals);
                BlockHelper.updateState(level, getBlockPos());
                return ItemInteractionResult.SUCCESS;
            }
            if (oncePerPlayer && playersWhoCompleted.contains(player.getUUID())) {
                return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
            }
            boolean isSuccessful = false;
            for (VoidDepotGoal goal : goals) {
                if (goal.isCompleted()) {
                    continue;
                }
                if (goal instanceof ItemGoal itemGoal) {
                    if (stack.getItem().equals(itemGoal.item)) {
                        int givenQuantity = Math.min(stack.getCount(), itemGoal.amount - itemGoal.deliveredAmount);
                        if (givenQuantity > 0) {
                            if (!level.isClientSide) {
                                itemGoal.deliveredAmount += givenQuantity;
                                stack.shrink(givenQuantity);
                                if (itemGoal.deliveredAmount >= itemGoal.amount) {
                                    itemGoal.setCompleted(true);
                                }
                            }
                            isSuccessful = true;
                        }
                    }
                }

                if (goal instanceof ExperienceGoal experienceGoal) {
                    if (hand.equals(InteractionHand.MAIN_HAND) && player.getItemInHand(hand).isEmpty()) {
                        int givenQuantity = Math.min(player.totalExperience, experienceGoal.amount - experienceGoal.deliveredAmount);
                        if (givenQuantity > 0) {
                            if (!level.isClientSide) {
                                experienceGoal.deliveredAmount += givenQuantity;
                                player.giveExperiencePoints(-givenQuantity);
                                if (experienceGoal.deliveredAmount >= experienceGoal.amount) {
                                    experienceGoal.setCompleted(true);
                                }
                            }
                            isSuccessful = true;
                        }
                    }
                }
            }
            if (isSuccessful) {
                boolean isCompleted = goals.stream().allMatch(VoidDepotGoal::isCompleted);
                if (isCompleted) {
                    level.scheduleTick(getBlockPos(), getBlockState().getBlock(), 40);
                    if (oncePerPlayer) {
                        playersWhoCompleted.add(player.getUUID());
                    }
                }

                final RandomSource random = level.random;
                level.playSound(null, worldPosition, SoundRegistry.VOID_EATS_GUNK.get(), SoundSource.PLAYERS, 0.7f, 0.6f + random.nextFloat() * 0.3f);
                level.playSound(null, worldPosition, SoundEvents.GENERIC_EAT, SoundSource.PLAYERS, 0.7f, 0.6f + random.nextFloat() * 0.2f);
                level.playSound(null, worldPosition, SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.PLAYERS, 0.1F, (random.nextFloat() - random.nextFloat()) * 0.35F + 0.9F);
                BlockHelper.updateState(level, getBlockPos());
                return ItemInteractionResult.SUCCESS;
            }
        }
        return super.onUseWithItem(player, stack, hand);
    }

    @Override
    public void tick() {
        super.tick();
        if (level.isClientSide) {
            WeepingWellParticleEffects.passiveVoidDepotParticles(this);
            if (level.getGameTime() % 5f == 0) {
                final BlockPos blockPos = getBlockPos();
                final Player nearestPlayer = level.getNearestPlayer(blockPos.getX(), blockPos.getY(), blockPos.getZ(), 6f, false);
                if (nearestPlayer != null) {
                    nearTimer = 10;
                }
            }
            nearTimer--;
            if (nearTimer > 0 && textVisibility < 40) {
                textVisibility++;
            }
            else if (textVisibility > 0){
                textVisibility--;
            }
        }
    }

    public void onCompletion() {
        float pitch = Mth.nextFloat(level.getRandom(), 1.5f, 1.75f);
        ParticleEffectTypeRegistry.WEEPING_WELL_REACTS.createPositionedEffect((ServerLevel) level, new PositionEffectData(worldPosition.getX()+0.5f, worldPosition.getY()+0.9f, worldPosition.getZ()+0.5f));
        level.playSound(null, worldPosition, SoundRegistry.FLESH_RING_ABSORBS.get(), SoundSource.HOSTILE, 0.7f, pitch);
        level.playSound(null, worldPosition, SoundRegistry.VOID_TRANSMUTATION.get(), SoundSource.HOSTILE, 2f, pitch);
        if (repeatable) {
            for (VoidDepotGoal goal : goals) {
                goal.reset();
            }
        }
        BlockHelper.updateState(level, getBlockPos());
    }

    public static final StringRepresentable.EnumCodec<VoidDepotGoal.VoidDepotGoalType> CODEC = StringRepresentable.fromEnum(VoidDepotGoal.VoidDepotGoalType::values);
    public static abstract class VoidDepotGoal {

        public enum VoidDepotGoalType implements StringRepresentable {
            ITEM("item", ItemGoal::deserialize),
            EXPERIENCE("experience", ExperienceGoal::deserialize);
            public final String name;
            public final Function<CompoundTag, VoidDepotGoal> deserializer;

            VoidDepotGoalType(String name, Function<CompoundTag, VoidDepotGoal> deserializer) {
                this.name = name;
                this.deserializer = deserializer;
            }

            @Override
            public String getSerializedName() {
                return name;
            }
        }

        public final VoidDepotGoalType type;
        public final String index;
        public final int amount;

        public int deliveredAmount;
        public boolean completed;

        public VoidDepotGoal(VoidDepotGoalType type, String index, int amount, int deliveredAmount) {
            this.type = type;
            this.index = index;
            this.amount = amount;
            this.deliveredAmount = deliveredAmount;
        }

        public void setCompleted(boolean completed) {
            this.completed = completed;
        }

        public boolean isCompleted() {
            return completed;
        }

        public void reset() {
            deliveredAmount = 0;
            completed = false;
        }

        public CompoundTag serialize() {
            CompoundTag compoundTag = new CompoundTag();
            compoundTag.putInt("amount", amount);
            compoundTag.putInt("deliveredAmount", deliveredAmount);
            return compoundTag;
        }
    }
    public static class ExperienceGoal extends VoidDepotGoal {

        public ExperienceGoal(String index, int amount, int deliveredAmount) {
            super(VoidDepotGoalType.EXPERIENCE, index, amount, deliveredAmount);
        }

        public static ExperienceGoal deserialize(CompoundTag compoundTag) {
            return new ExperienceGoal(
                    compoundTag.getString("goalName"),
                    compoundTag.getInt("amount"),
                    compoundTag.getInt("deliveredAmount"));
        }
    }
    public static class ItemGoal extends VoidDepotGoal {
        public final Item item;

        public ItemGoal(String index, Item item, int amount, int deliveredAmount) {
            super(VoidDepotGoalType.ITEM, index, amount, deliveredAmount);
            this.item = item;
        }

        @Override
        public CompoundTag serialize() {
            CompoundTag compoundTag = new CompoundTag();
            compoundTag.putString("itemType", BuiltInRegistries.ITEM.getKey(item).toString());
            compoundTag.putInt("amount", amount);
            compoundTag.putInt("deliveredAmount", deliveredAmount);
            return compoundTag;
        }

        public static ItemGoal deserialize(CompoundTag compoundTag) {
            return new ItemGoal(
                    compoundTag.getString("goalName"),
                    BuiltInRegistries.ITEM.get(ResourceLocation.parse(compoundTag.getString("itemType"))),
                    compoundTag.getInt("amount"),
                    compoundTag.getInt("deliveredAmount"));
        }
    }
}