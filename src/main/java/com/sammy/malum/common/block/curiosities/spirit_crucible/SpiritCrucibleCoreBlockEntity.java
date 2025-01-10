package com.sammy.malum.common.block.curiosities.spirit_crucible;

import com.sammy.malum.common.block.*;
import com.sammy.malum.common.item.augment.MendingDiffuserItem;
import com.sammy.malum.common.item.augment.ShieldingApparatusItem;
import com.sammy.malum.common.item.augment.WarpingEngineItem;
import com.sammy.malum.core.systems.artifice.*;
import com.sammy.malum.common.block.storage.*;
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
import org.jetbrains.annotations.NotNull;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.helpers.block.*;
import team.lodestar.lodestone.systems.blockentity.*;
import team.lodestar.lodestone.systems.multiblock.*;
import team.lodestar.lodestone.systems.recipe.*;

import javax.annotation.Nullable;
import java.util.function.*;

public class SpiritCrucibleCoreBlockEntity extends MultiBlockCoreEntity implements IArtificeAcceptor, IMalumSpecialItemAccessPoint, IBlockCapabilityProvider<IItemHandler, Direction> {

    public static final Vec3 CRUCIBLE_ITEM_OFFSET = new Vec3(0.5f, 1.6f, 0.5f);
    public static final Vec3 CRUCIBLE_CORE_AUGMENT_OFFSET = new Vec3(0.5f, 3f, 0.5f);
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
        inventory = MalumBlockEntityInventory.singleNotSpirit(this).onContentsChanged(this::updateRecipe);
        spiritInventory = MalumSpiritBlockEntityInventory.spiritStacks(this, 4).onContentsChanged(this::updateRecipe);
        augmentInventory = AugmentBlockEntityInventory.augmentInventory(this, 4).onContentsChanged(() -> recalibrateAccelerators(level, pos));
        coreAugmentInventory = AugmentBlockEntityInventory.coreAugmentInventory(this, 1).onContentsChanged(() -> recalibrateAccelerators(level, pos));
    }

    public SpiritCrucibleCoreBlockEntity(BlockPos pos, BlockState state) {
        this(BlockEntityRegistry.SPIRIT_CRUCIBLE.get(), STRUCTURE.get(), pos, state);
    }

    @Override
    protected void saveAdditional(CompoundTag compound, HolderLookup.Provider pRegistries) {
        super.saveAdditional(compound, pRegistries);
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
        if (!(level instanceof ServerLevel serverLevel)) {
            return ItemInteractionResult.CONSUME;
        }
        if (pStack.is(ItemTagRegistry.IS_ARTIFICE_TOOL)) {
            attributes.applyTuningForkBuff(serverLevel, worldPosition);
            return ItemInteractionResult.SUCCESS;
        }
        return super.onUseWithItem(pPlayer, pStack, pHand);
    }

    @Override
    public ItemInteractionResult onUse(Player pPlayer, InteractionHand pHand) {
        if (!(level instanceof ServerLevel serverLevel)) {
            return ItemInteractionResult.CONSUME;
        }
        var heldStack = pPlayer.getItemInHand(pHand);
        boolean isAugment = heldStack.has(DataComponentRegistry.ARTIFICE_AUGMENT);
        if (!isAugment || heldStack.isEmpty()) {
            var spiritResult = spiritInventory.interact(serverLevel, pPlayer, pHand);
            if (!spiritResult.isEmpty()) {
                return ItemInteractionResult.SUCCESS;
            }
            var impetusResult = inventory.interact(serverLevel, pPlayer, pHand);
            if (!impetusResult.isEmpty()) {
                return ItemInteractionResult.SUCCESS;
            }
        }
        if (isAugment || heldStack.isEmpty()) {
            if (heldStack.isEmpty() || !heldStack.get(DataComponentRegistry.ARTIFICE_AUGMENT).isCoreAugment()) {
                var augment = augmentInventory.interact(serverLevel, pPlayer, pHand);
                if (!augment.isEmpty()) {
                    return ItemInteractionResult.SUCCESS;
                }
            }
            var coreAugment = coreAugmentInventory.interact(serverLevel, pPlayer, pHand);
            if (!coreAugment.isEmpty()) {
                return ItemInteractionResult.SUCCESS;
            }
        }
        return ItemInteractionResult.FAIL;
    }

    @Override
    public void onBreak(@Nullable Player player) {
        inventory.dumpItems(level, worldPosition);
        spiritInventory.dumpItems(level, worldPosition);
        augmentInventory.dumpItems(level, worldPosition);
        coreAugmentInventory.dumpItems(level, worldPosition);
        invalidateModifiers(level);
        super.onBreak(player);
    }

    @Override
    public void update(@NotNull Level level) {
        if (level.isClientSide) {
            if (recipe == null) {
                CrucibleSoundInstance.playSound(this);
            }
            updateRecipe();
        }
    }

    @Override
    public void tick() {
        spiritAmount = Math.max(1, Mth.lerp(0.1f, spiritAmount, spiritInventory.getFilledSlotCount()));
        float speed = attributes.focusingSpeed.getValue(attributes);
        if (level instanceof ServerLevel serverLevel) {
            if (queuedCracks > 0) {
                crackTimer++;
                if (crackTimer % 5 == 0) {
                    if (crackTimer >= 15) {
                        crackTimer = 0;
                    }
                    float pitch = RandomHelper.randomBetween(level.getRandom(), 0.9f, 1.1f) * (0.95f + (crackTimer - 8) * 0.015f);
                    level.playSound(null, worldPosition, SoundRegistry.IMPETUS_CRACK.get(), SoundSource.BLOCKS, 0.7f, pitch);
                    queuedCracks--;
                    if (queuedCracks == 0) {
                        crackTimer = 0;
                    }
                }
            }
            if (recipe != null) {
                attributes.getInfluenceData(level).ifPresent(d -> {
                    for (ArtificeModifierSourceInstance modifier : d.modifiers()) {
                        modifier.tickFocusing(attributes);
                        if (!modifier.canModifyFocusing()) {
                            recalibrateAccelerators(level, worldPosition);
                        }
                    }
                });
                if (progress == 0) {
                    recalibrateAccelerators(level, worldPosition);
                }
                progress += speed;
                if (progress >= recipe.time) {
                    craft(serverLevel);
                }
            } else {
                if (progress != 0) {
                    progress = 0;
                    invalidateModifiers(level);
                }
            }
        } else {
            spiritSpin += 1 + speed * 0.1f;
            SpiritCrucibleParticleEffects.passiveCrucibleParticles(this);
        }
    }

    public void craft(ServerLevel level) {
        var impetus = inventory.getStackInSlot(0);
        var outputStack = recipe.output.copy();
        var itemPos = getItemPos();
        var random = level.random;
        float speed = attributes.focusingSpeed.getValue(attributes);
        float instability = attributes.instability.getValue(attributes);
        float fortuneChance = attributes.fortuneChance.getValue(attributes);
        int durabilityCost = 0;
        if (!ShieldingApparatusItem.shieldImpetus(level, worldPosition, attributes)) {
            if (recipe.durabilityCost != 0 && impetus.isDamageableItem()) {
                durabilityCost = recipe.durabilityCost;
                if (instability > 0 && random.nextFloat() < instability) {
                    durabilityCost *= 2;
                    if (instability > 1) {
                        durabilityCost = Math.round(durabilityCost * (instability));
                    }
                }
                queuedCracks += durabilityCost;
            }
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
        progress = WarpingEngineItem.skipForward(level, worldPosition, attributes) ? recipe.time - 10 * speed : 0;
        ParticleEffectTypeRegistry.SPIRIT_CRUCIBLE_CRAFTS.createPositionedEffect(level, new PositionEffectData(worldPosition), ColorEffectData.fromRecipe(recipe.spirits));
        level.playSound(null, worldPosition, SoundRegistry.CRUCIBLE_CRAFT.get(), SoundSource.BLOCKS, 1, 0.75f + random.nextFloat() * 0.5f);
        level.addFreshEntity(new ItemEntity(level, itemPos.x, itemPos.y, itemPos.z, outputStack));
        while (fortuneChance > 0) {
            if (fortuneChance >= 1 || random.nextFloat() < fortuneChance) {
                level.addFreshEntity(new ItemEntity(level, itemPos.x, itemPos.y, itemPos.z, outputStack.copy()));
            }
            fortuneChance -= 1;
        }
        if (durabilityCost > 0) {
            impetus.hurtAndBreak(durabilityCost, level, null, brokenStack -> {
                if (brokenStack instanceof ImpetusItem impetusItem) {
                    inventory.setStackInSlot(0, impetusItem.getCrackedVariant().getDefaultInstance());
                }
            });
            MendingDiffuserItem.repairImpetus(level, attributes, impetus);
        }
        updateRecipe();
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
        int spiritCount = spiritInventory.getFilledSlotCount();
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
    public void applyAugments(Consumer<ItemStack> augmentConsumer) {
        augmentInventory.getNonEmptyStacks().forEach(augmentConsumer);
        coreAugmentInventory.getNonEmptyStacks().forEach(augmentConsumer);
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

    public void updateRecipe() {
        recipe = LodestoneRecipeType.getRecipe(level, RecipeTypeRegistry.SPIRIT_FOCUSING.get(), new SpiritBasedRecipeInput(inventory.getStackInSlot(0), spiritInventory.nonEmptyItemStacks));
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