package com.sammy.malum.common.block.curiosities.spirit_crucible;

import com.sammy.malum.common.block.*;
import com.sammy.malum.common.block.storage.*;
import com.sammy.malum.common.item.augment.*;
import com.sammy.malum.common.item.augment.core.*;
import com.sammy.malum.common.item.impetus.*;
import com.sammy.malum.common.item.spirit.*;
import com.sammy.malum.common.recipe.spirit.focusing.*;
import com.sammy.malum.core.systems.recipe.*;
import com.sammy.malum.core.systems.spirit.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.block.*;
import com.sammy.malum.registry.common.item.*;
import com.sammy.malum.registry.common.recipe.*;
import com.sammy.malum.visual_effects.*;
import com.sammy.malum.visual_effects.networked.data.*;
import net.minecraft.core.*;
import net.minecraft.nbt.*;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.phys.*;
import net.neoforged.neoforge.capabilities.IBlockCapabilityProvider;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.wrapper.CombinedInvWrapper;
import org.jetbrains.annotations.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.systems.blockentity.*;
import team.lodestar.lodestone.systems.multiblock.*;

import javax.annotation.Nullable;
import javax.annotation.*;
import java.util.*;
import java.util.function.*;

public class SpiritCrucibleCoreBlockEntity extends MultiBlockCoreEntity implements ICatalyzerAccelerationTarget, IMalumSpecialItemAccessPoint, IBlockCapabilityProvider<IItemHandler, Direction> {

    private static final Vec3 CRUCIBLE_ITEM_OFFSET = new Vec3(0.5f, 1.6f, 0.5f);
    public static final Supplier<MultiBlockStructure> STRUCTURE = () -> (MultiBlockStructure.of(new MultiBlockStructure.StructurePiece(0, 1, 0, BlockRegistry.SPIRIT_CRUCIBLE_COMPONENT.get().defaultBlockState())));

    public LodestoneBlockEntityInventory inventory;
    public LodestoneBlockEntityInventory spiritInventory;
    public LodestoneBlockEntityInventory augmentInventory;
    public LodestoneBlockEntityInventory coreAugmentInventory;
    public SpiritFocusingRecipe recipe;

    public float spiritAmount;
    public float spiritSpin;

    public float progress;

    public int queuedCracks;
    public int crackTimer;

    public CrucibleAccelerationData acceleratorData = CrucibleAccelerationData.DEFAULT;
    public CrucibleTuning.CrucibleAttributeType tuningType = CrucibleTuning.CrucibleAttributeType.NONE;

    private final Supplier<IItemHandler> combinedInventory = () -> new CombinedInvWrapper(inventory, spiritInventory);

    public SpiritCrucibleCoreBlockEntity(BlockEntityType<? extends SpiritCrucibleCoreBlockEntity> type, MultiBlockStructure structure, BlockPos pos, BlockState state) {
        super(type, structure, pos, state);
        inventory = new MalumBlockEntityInventory(1, 1, t -> !(t.getItem() instanceof SpiritShardItem)) {
            @Override
            public void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                needsSync = true;
                BlockHelper.updateAndNotifyState(level, worldPosition);
            }
        };
        spiritInventory = new MalumBlockEntityInventory(4, 64) {
            @Override
            public void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                needsSync = true;
                spiritAmount = Math.max(1, Mth.lerp(0.15f, spiritAmount, nonEmptyItemAmount + 1));
                BlockHelper.updateAndNotifyState(level, worldPosition);
            }

            @Override
            public boolean isItemValid(int slot, @NotNull ItemStack stack) {
                if (!(stack.getItem() instanceof SpiritShardItem spiritItem))
                    return false;

                for (int i = 0; i < getSlots(); i++) {
                    if (i != slot) {
                        ItemStack stackInSlot = getStackInSlot(i);
                        if (!stackInSlot.isEmpty() && stackInSlot.getItem() == spiritItem)
                            return false;
                    }
                }
                return true;
            }
        };
        augmentInventory = new AugmentBlockEntityInventory(4, 1) {
            @Override
            public void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                needsSync = true;
                BlockHelper.updateAndNotifyState(level, worldPosition);
            }
        };
        coreAugmentInventory = new AugmentBlockEntityInventory(1, 1, t -> t.getItem() instanceof AbstractCoreAugmentItem) {
            @Override
            public void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                needsSync = true;
                BlockHelper.updateAndNotifyState(level, worldPosition);
            }
        };
    }

    public SpiritCrucibleCoreBlockEntity(BlockPos pos, BlockState state) {
        this(BlockEntityRegistry.SPIRIT_CRUCIBLE.get(), STRUCTURE.get(), pos, state);
    }

    @Override
    protected void saveAdditional(CompoundTag compound, HolderLookup.Provider pRegistries) {
        if (spiritAmount != 0) {
            compound.putFloat("spiritAmount", spiritAmount);
        }
        if (progress != 0) {
            compound.putFloat("progress", progress);
        }
        if (queuedCracks != 0) {
            compound.putInt("queuedCracks", queuedCracks);
        }

        compound.putInt("tuningType", tuningType.ordinal());
        acceleratorData.save(compound);
        inventory.save(pRegistries, compound);
        spiritInventory.save(pRegistries, compound, "spiritInventory");
        augmentInventory.save(pRegistries, compound, "augmentInventory");
        coreAugmentInventory.save(pRegistries, compound, "coreAugmentInventory");
    }

    @Override
    public void loadAdditional(CompoundTag compound, HolderLookup.Provider pRegistries) {
        spiritAmount = compound.getFloat("spiritAmount");
        progress = compound.getFloat("progress");
        queuedCracks = compound.getInt("queuedCracks");

        tuningType = CrucibleTuning.CrucibleAttributeType.values()[Mth.clamp(compound.getInt("tuningType"), 0, CrucibleTuning.CrucibleAttributeType.values().length-1)];
        acceleratorData = CrucibleAccelerationData.load(level, this, compound);

        inventory.load(pRegistries, compound);
        spiritInventory.load(pRegistries, compound, "spiritInventory");
        augmentInventory.load(pRegistries, compound, "augmentInventory");
        coreAugmentInventory.load(pRegistries, compound, "coreAugmentInventory");

        super.loadAdditional(compound, pRegistries);
    }

    @Override
    public ItemInteractionResult onUseWithItem(Player pPlayer, ItemStack pStack, InteractionHand pHand) {
        if (level.isClientSide) {
            return ItemInteractionResult.CONSUME;
        }
        if (pHand.equals(InteractionHand.MAIN_HAND)) {
            var item = pStack.getItem();
            if (item.equals(ItemRegistry.TUNING_FORK.get())) {
                tuningType = tuningType.next(tuningType, this);
                recalibrateAccelerators(level, worldPosition);
                level.playSound(null, worldPosition, SoundRegistry.TUNING_FORK_TINKERS.get(), SoundSource.BLOCKS, 1.25f+level.random.nextFloat()*0.5f, 0.75f+level.random.nextFloat()*0.5f);
                BlockHelper.updateAndNotifyState(level, worldPosition);
                return ItemInteractionResult.SUCCESS;
            }
        }
        return super.onUseWithItem(pPlayer, pStack, pHand);
    }

    @Override
    public ItemInteractionResult onUse(Player pPlayer, InteractionHand pHand) {
        var heldStack = pPlayer.getItemInHand(pHand);
        var item = heldStack.getItem();
        final boolean augmentOnly = item instanceof AbstractAugmentItem;
        final boolean isEmpty = heldStack.isEmpty();
        if (augmentOnly || (isEmpty && inventory.isEmpty() && spiritInventory.isEmpty())) {
            final boolean isCoreAugment = item instanceof AbstractCoreAugmentItem;
            if ((augmentOnly && !isCoreAugment) || isEmpty) {
                var stack = augmentInventory.interact(level, pPlayer, pHand);
                if (!stack.isEmpty()) {
                    recalibrateAccelerators(level, worldPosition);
                    return ItemInteractionResult.SUCCESS;
                }
            }
            if ((augmentOnly && isCoreAugment) || isEmpty) {
                var stack = coreAugmentInventory.interact(level, pPlayer, pHand);
                if (!stack.isEmpty()) {
                    recalibrateAccelerators(level, worldPosition);
                    return ItemInteractionResult.SUCCESS;
                }
            }
        }
        recalibrateAccelerators(level, worldPosition);
        if (!augmentOnly) {
            var spiritStack = spiritInventory.interact(level, pPlayer, pHand);
            if (!spiritStack.isEmpty()) {
                return ItemInteractionResult.SUCCESS;
            }
            if (!(item instanceof SpiritShardItem)) {
                ItemStack stack = inventory.interact(level, pPlayer, pHand);
                if (!stack.isEmpty()) {
                    return ItemInteractionResult.SUCCESS;
                }
            }
        }
        if (isEmpty) {
            return ItemInteractionResult.SUCCESS;
        } else {
            return ItemInteractionResult.FAIL;
        }
    }

    @Override
    public void onBreak(@Nullable Player player) {
        inventory.dumpItems(level, worldPosition);
        spiritInventory.dumpItems(level, worldPosition);
        augmentInventory.dumpItems(level, worldPosition);
        super.onBreak(player);
    }

    @Override
    public void init() {
        if (level.isClientSide && recipe == null) {
            CrucibleSoundInstance.playSound(this);
        }
        recipe = LodestoneRecipeType.getRecipe(level, RecipeTypeRegistry.SPIRIT_FOCUSING.get(), new SpiritBasedRecipeInput(inventory.getStackInSlot(0), spiritInventory.nonEmptyItemStacks));
    }

    @Override
    public void tick() {
        super.tick();
        spiritAmount = Math.max(1, Mth.lerp(0.1f, spiritAmount, spiritInventory.nonEmptyItemAmount));
        if (queuedCracks > 0) {
            crackTimer++;
            if (crackTimer % 5 == 0) {
                if (crackTimer >= 15) {
                    crackTimer = 0;
                }
                float pitch = 0.95f + (crackTimer - 8) * 0.015f + level.random.nextFloat() * 0.05f;
                level.playSound(null, worldPosition, SoundRegistry.IMPETUS_CRACK.get(), SoundSource.BLOCKS, 0.7f, pitch);
                queuedCracks--;
                if (queuedCracks == 0) {
                    crackTimer = 0;
                }
            }
        }
        float speed = acceleratorData.focusingSpeed.getValue(acceleratorData);
        if (!level.isClientSide) {
            if (recipe != null) {
                boolean needsRecalibration = !acceleratorData.accelerators.stream().allMatch(ICrucibleAccelerator::canContinueAccelerating);
                if (needsRecalibration) {
                    recalibrateAccelerators(level, worldPosition);
                    BlockHelper.updateAndNotifyState(level, worldPosition);
                }
                progress += speed;
                if (progress >= recipe.time) {
                    craft();
                }
            } else {
                progress = 0;
            }
        }
        else {
            spiritSpin += 1 + speed * 0.1f;
            SpiritCrucibleParticleEffects.passiveCrucibleParticles(this);
        }
    }

    public void craft() {
        ItemStack stack = inventory.getStackInSlot(0);
        ItemStack outputStack = recipe.output.copy();
        Vec3 itemPos = getItemPos();
        final RandomSource random = level.random;
        float processingSpeed = acceleratorData.focusingSpeed.getValue(acceleratorData);
        float damageChance = acceleratorData.damageChance.getValue(acceleratorData);
        float bonusYieldChance = acceleratorData.bonusYieldChance.getValue(acceleratorData);
        float instantCompletionChance = acceleratorData.chainFocusingChance.getValue(acceleratorData);
        float completeDamageNegationChance = acceleratorData.damageAbsorptionChance.getValue(acceleratorData);
        float restorationChance = acceleratorData.restorationChance.getValue(acceleratorData);
        if (random.nextFloat() < restorationChance) {
            stack.setDamageValue(Math.max(stack.getDamageValue()-recipe.durabilityCost*4, 0));
        }
        if (completeDamageNegationChance == 0 || random.nextFloat() < completeDamageNegationChance) {
            if (recipe.durabilityCost != 0 && stack.isDamageableItem()) {
                int durabilityCost = recipe.durabilityCost;
                if (damageChance > 0 && random.nextFloat() < damageChance) {
                    durabilityCost *= 2;
                }

                queuedCracks += durabilityCost;
                stack.hurtAndBreak(durabilityCost, (ServerLevel) level, null, brokenStack -> {
                    if (brokenStack instanceof ImpetusItem impetusItem) {
                        inventory.setStackInSlot(0, impetusItem.getCrackedVariant().getDefaultInstance());
                    }
                });
            }
        }
        else {
            level.playSound(null, worldPosition, SoundRegistry.SHIELDING_APPARATUS_SHIELDS.get(), SoundSource.BLOCKS, 0.5f, 0.25f + random.nextFloat() * 0.25f);
        }
        for (SpiritIngredient spirit : recipe.spirits) {
            for (int i = 0; i < spiritInventory.slotCount; i++) {
                ItemStack spiritStack = spiritInventory.getStackInSlot(i);
                if (spirit.test(spiritStack)) {
                    spiritStack.shrink(spirit.getCount());
                    break;
                }
            }
        }

        spiritInventory.updateData();
        final boolean instantCompletion = instantCompletionChance > 0 && random.nextFloat() < instantCompletionChance;
        progress = instantCompletion ? recipe.time - 10 * processingSpeed : 0;
        if (instantCompletion) {
            level.playSound(null, worldPosition, SoundRegistry.WARPING_ENGINE_REVERBERATES.get(), SoundSource.BLOCKS, 1.5f, 1f + random.nextFloat() * 0.25f);
        }
        acceleratorData.globalAttributeModifier = instantCompletion ? acceleratorData.globalAttributeModifier + 0.2f : 0;
        ParticleEffectTypeRegistry.SPIRIT_CRUCIBLE_CRAFTS.createPositionedEffect((ServerLevel) level, new PositionEffectData(worldPosition), ColorEffectData.fromRecipe(recipe.spirits));
        level.playSound(null, worldPosition, SoundRegistry.CRUCIBLE_CRAFT.get(), SoundSource.BLOCKS, 1, 0.75f + random.nextFloat() * 0.5f);
        level.addFreshEntity(new ItemEntity(level, itemPos.x, itemPos.y, itemPos.z, outputStack));
        while (bonusYieldChance > 0) {
            if (bonusYieldChance >= 1 || random.nextFloat() < bonusYieldChance) {
                level.addFreshEntity(new ItemEntity(level, itemPos.x, itemPos.y, itemPos.z, outputStack.copy()));
            }
            bonusYieldChance-=1;
        }
        recipe = LodestoneRecipeType.getRecipe(level, RecipeTypeRegistry.SPIRIT_FOCUSING.get(), new SpiritBasedRecipeInput(inventory.getStackInSlot(0), spiritInventory.nonEmptyItemStacks));
        BlockHelper.updateAndNotifyState(level, worldPosition);
    }

    @Override
    public Vec3 getVisualAccelerationPoint() {
        return getItemPos();
    }

    @Override
    public List<ItemStack> getExtraAugments() {
        return augmentInventory.nonEmptyItemStacks;
    }

    @Override
    public ItemStack getCoreAugment() {
        return coreAugmentInventory.getStackInSlot(0);
    }

    @Override
    public boolean isValidAccelerationTarget() {
        return !isRemoved() && recipe != null;
    }

    @Override
    public CrucibleAccelerationData getAccelerationData() {
        return acceleratorData;
    }

    @Override
    public void setAccelerationData(CrucibleAccelerationData data) {
        this.acceleratorData = data;
    }

    @Override
    public CrucibleTuning.CrucibleAttributeType getTuningType() {
        return tuningType;
    }

    @Override
    public MalumSpiritType getActiveSpiritType() {
        int spiritCount = spiritInventory.nonEmptyItemAmount;
        Item currentItem = spiritInventory.getStackInSlot(0).getItem();
        if (spiritCount > 1) {
            float duration = 60f * spiritCount;
            float gameTime = (getLevel().getGameTime() % duration) / 60f;
            currentItem = spiritInventory.getStackInSlot(Mth.floor(gameTime)).getItem();
        }
        if (!(currentItem instanceof SpiritShardItem spiritItem)) {
            return null;
        }
        return spiritItem.type;
    }

    @Override
    public Vec3 getItemPos(float partialTicks) {
        final BlockPos blockPos = getBlockPos();
        final Vec3 offset = getCentralItemOffset();
        return new Vec3(blockPos.getX()+offset.x, blockPos.getY()+offset.y, blockPos.getZ()+offset.z);
    }

    @Override
    public BlockPos getAccessPointBlockPos() {
        return getBlockPos();
    }

    @Override
    public LodestoneBlockEntityInventory getSuppliedInventory() {
        return inventory;
    }

    public Vec3 getCentralItemOffset() {
        return CRUCIBLE_ITEM_OFFSET;
    }

    public Vec3 getSpiritItemOffset(int slot, float partialTicks) {
        float distance = 0.75f + (float) Math.sin(((spiritSpin + partialTicks) % 6.28f) / 20f) * 0.025f;
        float height = 1.8f;
        return DataHelper.rotatingRadialOffset(new Vec3(0.5f, height, 0.5f), distance, slot, spiritAmount, (long) (spiritSpin + partialTicks), 360);
    }

    public Vec3 getAugmentItemOffset(int slot, float partialTicks) {
        float distance = 0.6f + (float) Math.sin(((spiritSpin + partialTicks) % 6.28f) / 20f) * 0.025f;
        float height = 1.6f;
        return DataHelper.rotatingRadialOffset(new Vec3(0.5f, height, 0.5f), distance, slot, augmentInventory.slotCount, (long) (spiritSpin + partialTicks), 240);
    }

    @Override
    public @Nullable IItemHandler getCapability(Level level, BlockPos blockPos, BlockState blockState, @Nullable BlockEntity blockEntity, Direction direction) {
        return combinedInventory.get();
    }
}
