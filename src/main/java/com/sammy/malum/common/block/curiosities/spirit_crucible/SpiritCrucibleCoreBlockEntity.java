package com.sammy.malum.common.block.curiosities.spirit_crucible;

import com.sammy.malum.common.block.*;
import com.sammy.malum.common.block.curiosities.spirit_crucible.artifice.*;
import com.sammy.malum.common.block.storage.*;
import com.sammy.malum.common.item.augment.*;
import com.sammy.malum.common.item.augment.core.*;
import com.sammy.malum.common.item.impetus.*;
import com.sammy.malum.common.item.spirit.*;
import com.sammy.malum.common.packets.CodecUtil;
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
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.helpers.block.*;
import team.lodestar.lodestone.systems.blockentity.*;
import team.lodestar.lodestone.systems.multiblock.*;
import team.lodestar.lodestone.systems.recipe.*;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.*;

public class SpiritCrucibleCoreBlockEntity extends MultiBlockCoreEntity implements IArtificeAcceptor, IMalumSpecialItemAccessPoint, IBlockCapabilityProvider<IItemHandler, Direction> {

    public static final Vec3 CRUCIBLE_ITEM_OFFSET = new Vec3(0.5f, 1.6f, 0.5f);
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

    public ArtificeAttributeData attributes = new ArtificeAttributeData();
    private final Supplier<IItemHandler> combinedInventory = () -> new CombinedInvWrapper(inventory, spiritInventory);

    public SpiritCrucibleCoreBlockEntity(BlockEntityType<? extends SpiritCrucibleCoreBlockEntity> type, MultiBlockStructure structure, BlockPos pos, BlockState state) {
        super(type, structure, pos, state);
        inventory = MalumBlockEntityInventory.singleNotSpirit(this);
        spiritInventory = MalumSpiritBlockEntityInventory.spiritStacks(this, 4);
        augmentInventory = AugmentBlockEntityInventory.augmentInventory(this, 4);
        coreAugmentInventory = AugmentBlockEntityInventory.coreAugmentInventory(this, 1);
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

        compound.put("attributes", CodecUtil.encodeNBT(ArtificeAttributeData.CODEC, attributes));
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

        attributes = CodecUtil.decodeNBT(ArtificeAttributeData.CODEC, compound.getCompound("attributes"));
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
                attributes.selectNextAttributeForTuning();
                level.playSound(null, worldPosition, SoundRegistry.TUNING_FORK_TINKER.get(), SoundSource.BLOCKS, 1.25f + level.random.nextFloat() * 0.5f, 0.75f + level.random.nextFloat() * 0.5f);
                BlockStateHelper.updateAndNotifyState(level, worldPosition);
                return ItemInteractionResult.SUCCESS;
            }
        }
        return super.onUseWithItem(pPlayer, pStack, pHand);
    }

    @Override
    public ItemInteractionResult onUse(Player pPlayer, InteractionHand pHand) {
        var heldStack = pPlayer.getItemInHand(pHand);
        var item = heldStack.getItem();
        boolean augmentOnly = item instanceof AugmentItem;
        boolean isEmpty = heldStack.isEmpty();
        if (augmentOnly || (isEmpty && inventory.isEmpty() && spiritInventory.isEmpty())) {
            final boolean isCoreAugment = item instanceof CoreAugmentItem;
            if ((augmentOnly && !isCoreAugment) || isEmpty) {
                var stack = augmentInventory.interact(level, pPlayer, pHand);
                if (!stack.isEmpty()) {
                    return ItemInteractionResult.SUCCESS;
                }
            }
            if ((augmentOnly && isCoreAugment) || isEmpty) {
                var stack = coreAugmentInventory.interact(level, pPlayer, pHand);
                if (!stack.isEmpty()) {
                    return ItemInteractionResult.SUCCESS;
                }
            }
        }
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
    public void loadLevel() {
        if (level.isClientSide && recipe == null) {
            CrucibleSoundInstance.playSound(this);
        }
        recipe = LodestoneRecipeType.getRecipe(level, RecipeTypeRegistry.SPIRIT_FOCUSING.get(), new SpiritBasedRecipeInput(inventory.getStackInSlot(0), spiritInventory.nonEmptyItemStacks));
        if (!level.isClientSide) {
            recalibrateAccelerators(level, worldPosition);
        }
    }

    @Override
    public void tick() {
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
        float speed = attributes.focusingSpeed.getValue(attributes);
        if (level instanceof ServerLevel serverLevel) {
            if (recipe != null) {
                attributes.getInfluenceData(level).ifPresent(d -> {
                    for (ArtificeModifierSource modifier : d.modifiers()) {
                        modifier.tickFocusing(attributes);
                        if (!modifier.canModifyFocusing()) {
                            recalibrateAccelerators(level, worldPosition);
                        }
                    }
                });
                progress += speed;
                if (progress >= recipe.time) {
                    craft(serverLevel);
                }
            } else {
                progress = 0;
            }
        } else {
            spiritSpin += 1 + speed * 0.1f;
            SpiritCrucibleParticleEffects.passiveCrucibleParticles(this);
        }
    }

    public void craft(ServerLevel serverLevel) {
        var stack = inventory.getStackInSlot(0);
        var outputStack = recipe.output.copy();
        var itemPos = getItemPos();
        var random = serverLevel.random;
        float processingSpeed = attributes.focusingSpeed.getValue(attributes);
        float damageChance = attributes.instability.getValue(attributes);
        float bonusYieldChance = attributes.fortuneChance.getValue(attributes);
        float instantCompletionChance = attributes.chainFocusingChance.getValue(attributes);
        float completeDamageNegationChance = attributes.damageAbsorptionChance.getValue(attributes);
        float restorationChance = attributes.restorationChance.getValue(attributes);
        if (random.nextFloat() < restorationChance) {
            stack.setDamageValue(Math.max(stack.getDamageValue() - recipe.durabilityCost * 4, 0));
        }
        if (completeDamageNegationChance == 0 || random.nextFloat() < completeDamageNegationChance) {
            if (recipe.durabilityCost != 0 && stack.isDamageableItem()) {
                int durabilityCost = recipe.durabilityCost;
                if (damageChance > 0 && random.nextFloat() < damageChance) {
                    durabilityCost *= 2;
                }

                queuedCracks += durabilityCost;
                stack.hurtAndBreak(durabilityCost, serverLevel, null, brokenStack -> {
                    if (brokenStack instanceof ImpetusItem impetusItem) {
                        inventory.setStackInSlot(0, impetusItem.getCrackedVariant().getDefaultInstance());
                    }
                });
            }
        } else {
            serverLevel.playSound(null, worldPosition, SoundRegistry.SHIELDING_APPARATUS_SHIELDS.get(), SoundSource.BLOCKS, 0.5f, 0.25f + random.nextFloat() * 0.25f);
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
        if (instantCompletion) {
            level.playSound(null, worldPosition, SoundRegistry.WARPING_ENGINE_REVERBERATES.get(), SoundSource.BLOCKS, 1.5f, 1f + random.nextFloat() * 0.25f);
        }
        progress = instantCompletion ? recipe.time - 10 * processingSpeed : 0;
        attributes.chainProcessingBonus = instantCompletion ? attributes.chainProcessingBonus + 0.2f : 0;
        level.playSound(null, worldPosition, SoundRegistry.CRUCIBLE_CRAFT.get(), SoundSource.BLOCKS, 1, 0.75f + random.nextFloat() * 0.5f);
        level.addFreshEntity(new ItemEntity(level, itemPos.x, itemPos.y, itemPos.z, outputStack));
        while (bonusYieldChance > 0) {
            if (bonusYieldChance >= 1 || random.nextFloat() < bonusYieldChance) {
                level.addFreshEntity(new ItemEntity(level, itemPos.x, itemPos.y, itemPos.z, outputStack.copy()));
            }
            bonusYieldChance -= 1;
        }
        ParticleEffectTypeRegistry.SPIRIT_CRUCIBLE_CRAFTS.createPositionedEffect(serverLevel, new PositionEffectData(worldPosition), ColorEffectData.fromRecipe(recipe.spirits));
        recipe = LodestoneRecipeType.getRecipe(level, RecipeTypeRegistry.SPIRIT_FOCUSING.get(), new SpiritBasedRecipeInput(inventory.getStackInSlot(0), spiritInventory.nonEmptyItemStacks));
        BlockStateHelper.updateAndNotifyState(level, worldPosition);
    }

    @Override
    public Vec3 getVisualAccelerationPoint() {
        return getItemPos();
    }


    @Override
    public ArtificeAttributeData getAttributes() {
        return attributes;
    }

    @Override
    public void setAttributes(ArtificeAttributeData attributes) {
        this.attributes = attributes;
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
        final Vec3 offset = CRUCIBLE_ITEM_OFFSET;
        return new Vec3(blockPos.getX() + offset.x, blockPos.getY() + offset.y, blockPos.getZ() + offset.z);
    }

    @Override
    public BlockPos getAccessPointBlockPos() {
        return getBlockPos();
    }

    @Override
    public LodestoneBlockEntityInventory getSuppliedInventory() {
        return inventory;
    }

    public Vec3 getSpiritItemOffset(int slot, float partialTicks) {
        float distance = 0.75f + (float) Math.sin(((spiritSpin + partialTicks) % 6.28f) / 20f) * 0.025f;
        float height = 1.8f;
        return VecHelper.rotatingRadialOffset(new Vec3(0.5f, height, 0.5f), distance, slot, spiritAmount, spiritSpin + partialTicks, 360);
    }

    public Vec3 getAugmentItemOffset(int slot, float partialTicks) {
        float distance = 0.6f + (float) Math.sin(((spiritSpin + partialTicks) % 6.28f) / 20f) * 0.025f;
        float height = 1.6f;
        return VecHelper.rotatingRadialOffset(new Vec3(0.5f, height, 0.5f), distance, slot, augmentInventory.slotCount, spiritSpin + partialTicks, 240);
    }

    @Override
    public @Nullable IItemHandler getCapability(Level level, BlockPos blockPos, BlockState blockState, @Nullable BlockEntity blockEntity, Direction direction) {
        return combinedInventory.get();
    }
}