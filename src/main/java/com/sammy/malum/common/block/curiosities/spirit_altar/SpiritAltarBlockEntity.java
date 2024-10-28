package com.sammy.malum.common.block.curiosities.spirit_altar;

import com.sammy.malum.common.block.MalumBlockEntityInventory;
import com.sammy.malum.common.block.storage.IMalumSpecialItemAccessPoint;
import com.sammy.malum.common.item.spirit.SpiritShardItem;
import com.sammy.malum.common.recipe.spirit.infusion.SpiritInfusionRecipe;
import com.sammy.malum.core.systems.recipe.LodestoneRecipeType;
import com.sammy.malum.core.systems.recipe.SpiritBasedRecipeInput;
import com.sammy.malum.core.systems.recipe.SpiritIngredient;
import com.sammy.malum.registry.common.ParticleEffectTypeRegistry;
import com.sammy.malum.registry.common.SoundRegistry;
import com.sammy.malum.registry.common.SpiritTypeRegistry;
import com.sammy.malum.registry.common.block.BlockEntityRegistry;
import com.sammy.malum.registry.common.recipe.RecipeTypeRegistry;
import com.sammy.malum.visual_effects.SpiritAltarParticleEffects;
import com.sammy.malum.visual_effects.networked.altar.SpiritAltarEatItemParticleEffect;
import com.sammy.malum.visual_effects.networked.data.ColorEffectData;
import com.sammy.malum.visual_effects.networked.data.PositionEffectData;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.*;
import net.neoforged.neoforge.capabilities.IBlockCapabilityProvider;
import net.neoforged.neoforge.common.crafting.SizedIngredient;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.IItemHandlerModifiable;
import net.neoforged.neoforge.items.wrapper.CombinedInvWrapper;
import org.jetbrains.annotations.NotNull;
import team.lodestar.lodestone.helpers.BlockHelper;
import team.lodestar.lodestone.helpers.DataHelper;
import team.lodestar.lodestone.systems.blockentity.LodestoneBlockEntity;
import team.lodestar.lodestone.systems.blockentity.LodestoneBlockEntityInventory;
import team.lodestar.lodestone.systems.easing.Easing;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Supplier;

public class SpiritAltarBlockEntity extends LodestoneBlockEntity implements IBlockCapabilityProvider<IItemHandler, Direction> {

    private static final Vec3 ALTAR_ITEM_OFFSET = new Vec3(0.5f, 1.25f, 0.5f);
    public static final int HORIZONTAL_RANGE = 4;
    public static final int VERTICAL_RANGE = 3;

    public float speed = 1f;
    public int progress;
    public int idleProgress;
    public float spiritYLevel;

    public List<BlockPos> acceleratorPositions = new ArrayList<>();
    public List<IAltarAccelerator> accelerators = new ArrayList<>();
    public float spiritAmount;
    public float spiritSpin;
    public boolean isCrafting;

    public LodestoneBlockEntityInventory inventory;
    public LodestoneBlockEntityInventory extrasInventory;
    public LodestoneBlockEntityInventory spiritInventory;
    public Map<SpiritInfusionRecipe, AltarCraftingHelper.Ranking> possibleRecipes = new HashMap<>();
    public SpiritInfusionRecipe recipe;

    public Supplier<IItemHandler> internalInventory = () -> new CombinedInvWrapper(inventory, extrasInventory, spiritInventory);
    public Supplier<IItemHandler> exposedInventory = () -> new CombinedInvWrapper(inventory, spiritInventory);

    public SpiritAltarBlockEntity(BlockEntityType<? extends SpiritAltarBlockEntity> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public SpiritAltarBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.SPIRIT_ALTAR.get(), pos, state);

        inventory = new MalumBlockEntityInventory(1, 64, t -> !(t.getItem() instanceof SpiritShardItem)) {
            @Override
            public void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                needsSync = true;
                BlockHelper.updateAndNotifyState(level, worldPosition);
            }
        };
        extrasInventory = new MalumBlockEntityInventory(8, 64) {
            @Override
            public void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                BlockHelper.updateAndNotifyState(level, worldPosition);
            }
        };
        spiritInventory = new MalumBlockEntityInventory(SpiritTypeRegistry.SPIRITS.size(), 64) {
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
                        if (!stackInSlot.isEmpty() && stackInSlot.getItem() == spiritItem) {
                            return false;
                        }
                    }
                }
                return true;
            }
        };
    }

    @Override
    protected void saveAdditional(CompoundTag compound, HolderLookup.Provider pRegistries) {
        compound.putInt("progress", progress);
        compound.putInt("idleProgress", idleProgress);
        compound.putFloat("spiritYLevel", spiritYLevel);
        compound.putFloat("speed", speed);
        compound.putFloat("spiritAmount", spiritAmount);
        compound.putInt("acceleratorAmount", acceleratorPositions.size());
        for (int i = 0; i < acceleratorPositions.size(); i++) {
            BlockHelper.saveBlockPos(compound, acceleratorPositions.get(i), "" + i);
        }

        inventory.save(pRegistries, compound);
        spiritInventory.save(pRegistries, compound, "spiritInventory");
        extrasInventory.save(pRegistries, compound, "extrasInventory");
    }

    @Override
    public void loadAdditional(CompoundTag compound, HolderLookup.Provider pRegistries) {
        progress = compound.getInt("progress");
        idleProgress = compound.getInt("idleProgress");
        spiritYLevel = compound.getFloat("spiritYLevel");
        speed = compound.getFloat("speed");
        spiritAmount = compound.getFloat("spiritAmount");

        acceleratorPositions.clear();
        accelerators.clear();
        int amount = compound.getInt("acceleratorAmount");
        for (int i = 0; i < amount; i++) {
            BlockPos pos = BlockHelper.loadBlockPos(compound, "" + i);
            if (level != null && level.getBlockEntity(pos) instanceof IAltarAccelerator accelerator) {
                acceleratorPositions.add(pos);
                accelerators.add(accelerator);
            }
        }
        inventory.load(pRegistries, compound);
        spiritInventory.load(pRegistries, compound, "spiritInventory");
        extrasInventory.load(pRegistries, compound, "extrasInventory");
        super.loadAdditional(compound, pRegistries);
    }

    @Override
    public void onBreak(@Nullable Player player) {
        inventory.dumpItems(level, worldPosition);
        spiritInventory.dumpItems(level, worldPosition);
        extrasInventory.dumpItems(level, worldPosition);
    }

    @Override
    public ItemInteractionResult onUseWithItem(Player player, ItemStack heldStack, InteractionHand hand) {
        if (level.isClientSide) {
            return ItemInteractionResult.CONSUME;
        }
        if (hand.equals(InteractionHand.MAIN_HAND)) {;
            recalibrateAccelerators();
            if (!(heldStack.getItem() instanceof SpiritShardItem)) {
                ItemStack stack = inventory.interact(level, player, hand);
                if (!stack.isEmpty()) {
                    return ItemInteractionResult.SUCCESS;
                }
            }
            spiritInventory.interact(level, player, hand);
            if (heldStack.isEmpty()) {
                return ItemInteractionResult.SUCCESS;
            } else {
                return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
            }
        }
        return super.onUse(player, hand);
    }

    @Override
    public void init() {
        recalculateRecipes();
        if (level.isClientSide && isCrafting) {
            AltarSoundInstance.playSound(this);
        }
    }

    @Override
    public void tick() {
        super.tick();
        spiritAmount = Math.max(1, Mth.lerp(0.1f, spiritAmount, spiritInventory.nonEmptyItemAmount));

        if (!inventory.getStackInSlot(0).isEmpty()) {
            idleProgress++;
            int progressCap = (int) (20 / speed);
            if (idleProgress >= progressCap) {
                recalculateRecipes();
                idleProgress = 0;
                BlockHelper.updateAndNotifyState(level, worldPosition);
            }
        }

        if (!possibleRecipes.isEmpty()) {
            if (spiritYLevel < 30) {
                spiritYLevel++;
            }
            if (recipe != null) {
                if (!isCrafting) {
                    BlockHelper.updateAndNotifyState(level, worldPosition);
                    isCrafting = true;
                }
                progress++;
            }
            else {
                if (isCrafting) {
                    BlockHelper.updateAndNotifyState(level, worldPosition);
                    isCrafting = false;
                }
                progress = 0;
            }

            if (!level.isClientSide) {
                if (level.getGameTime() % 20L == 0) {
                    boolean canAccelerate = accelerators.stream().allMatch(IAltarAccelerator::canAccelerate);
                    if (!canAccelerate) {
                        recalibrateAccelerators();
                    }
                }
                int progressCap = (int) (300 / speed);
                if (progress >= progressCap) {
                    boolean success = consume();
                    if (success) {
                        craft();
                    }
                }
            }
        } else {
            isCrafting = false;
            progress = 0;
            if (spiritYLevel > 0) {
                spiritYLevel = Math.max(spiritYLevel - 0.8f, 0);
            }
        }
        if (level.isClientSide) {
            spiritSpin += 1 + spiritYLevel * 0.05f + speed * 0.5f;
            SpiritAltarParticleEffects.passiveSpiritAltarParticles(this);
        }
    }

    private void recalculateRecipes() {
        boolean hadRecipe = recipe != null;

        ItemStack stack = inventory.getStackInSlot(0);
        if (!stack.isEmpty()) {
            Collection<SpiritInfusionRecipe> recipes = DataHelper.getAll(LodestoneRecipeType.getRecipes(level, RecipeTypeRegistry.SPIRIT_INFUSION.get())).stream().filter(r -> r.matches(new SpiritBasedRecipeInput(stack, spiritInventory.nonEmptyItemStacks), level)).toList();
            possibleRecipes.clear();
            IItemHandlerModifiable pedestalItems = AltarCraftingHelper.createPedestalInventoryCapture(AltarCraftingHelper.capturePedestals(level, worldPosition));
            for (SpiritInfusionRecipe recipe : recipes) {
                possibleRecipes.put(recipe, AltarCraftingHelper.rankRecipe(recipe, stack, spiritInventory, pedestalItems, extrasInventory));
            }
            recipe = possibleRecipes.entrySet().stream().filter(it -> it.getValue() != null).max(Map.Entry.comparingByValue()).map(Map.Entry::getKey).orElse(null);
        } else {
            recipe = null;
            possibleRecipes.clear();
        }

        if (hadRecipe && recipe == null && level != null) {
            extrasInventory.dumpItems(level, worldPosition);
        }
    }

    public boolean consume() {
        if (recipe == null) {
            return false;
        } else if (recipe.extraIngredients.isEmpty())
            return true;

        List<IMalumSpecialItemAccessPoint> pedestalItems = AltarCraftingHelper.capturePedestals(level, worldPosition);
        ItemStack stack = inventory.getStackInSlot(0);
        AltarCraftingHelper.Ranking reranking = AltarCraftingHelper.rankRecipe(recipe, stack, spiritInventory, AltarCraftingHelper.createPedestalInventoryCapture(pedestalItems), extrasInventory);
        if (!Objects.equals(reranking, possibleRecipes.get(recipe))) {
            recalculateRecipes();
            return false;
        }

        SizedIngredient nextIngredient = AltarCraftingHelper.getNextIngredientToTake(recipe, extrasInventory);
        if (nextIngredient != null) {
            for (IMalumSpecialItemAccessPoint provider : pedestalItems) {

                LodestoneBlockEntityInventory inventoryForAltar = provider.getSuppliedInventory();
                ItemStack providedStack = inventoryForAltar.extractItem(0, nextIngredient.count(), true);

                if (nextIngredient.ingredient().test(providedStack)) {
                    level.playSound(null, provider.getAccessPointBlockPos(), SoundRegistry.ALTAR_CONSUME.get(), SoundSource.BLOCKS, 1, 0.9f + level.random.nextFloat() * 0.2f);
                    ParticleEffectTypeRegistry.SPIRIT_ALTAR_EATS_ITEM.createPositionedEffect((ServerLevel) level, new PositionEffectData(worldPosition), ColorEffectData.fromRecipe(recipe.spirits), SpiritAltarEatItemParticleEffect.createData(provider.getAccessPointBlockPos(), providedStack));
                    extrasInventory.insertItem(inventoryForAltar.extractItem(0, nextIngredient.count(), false));
                    inventoryForAltar.updateData();
                    BlockHelper.updateAndNotifyState(level, provider.getAccessPointBlockPos());
                    break;
                }
            }
            progress *= 0.8f;
            if (extrasInventory.isEmpty()) {
                return false;
            }
            return AltarCraftingHelper.extractIngredient(extrasInventory, nextIngredient.ingredient(), nextIngredient.count(), true).isEmpty();
        }
        return true;
    }

    public void craft() {
        ItemStack stack = inventory.getStackInSlot(0);
        ItemStack outputStack = recipe.output.copy();
        Vec3 itemPos = getItemPos();
        if (recipe.carryOverData) {
            outputStack.applyComponents(stack.getComponents());
        }
        stack.shrink(recipe.ingredient.count());
        inventory.updateData();
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
        progress *= 0.75f;
        extrasInventory.clear();
        ParticleEffectTypeRegistry.SPIRIT_ALTAR_CRAFTS.createPositionedEffect((ServerLevel) level, new PositionEffectData(worldPosition), ColorEffectData.fromRecipe(recipe.spirits));
        level.playSound(null, worldPosition, SoundRegistry.ALTAR_CRAFT.get(), SoundSource.BLOCKS, 1, 0.9f + level.random.nextFloat() * 0.2f);
        level.addFreshEntity(new ItemEntity(level, itemPos.x, itemPos.y, itemPos.z, outputStack));
        init();
        recalibrateAccelerators();
        BlockHelper.updateAndNotifyState(level, worldPosition);
    }

    public void recalibrateAccelerators() {
        speed = 1f;
        accelerators.clear();
        acceleratorPositions.clear();
        Collection<IAltarAccelerator> nearbyAccelerators = BlockHelper.getBlockEntities(IAltarAccelerator.class, level, worldPosition, HORIZONTAL_RANGE, VERTICAL_RANGE, HORIZONTAL_RANGE);
        Map<IAltarAccelerator.AltarAcceleratorType, Integer> entries = new HashMap<>();
        for (IAltarAccelerator accelerator : nearbyAccelerators) {
            if (accelerator.canAccelerate()) {
                int max = accelerator.getAcceleratorType().maximumEntries();
                int amount = entries.computeIfAbsent(accelerator.getAcceleratorType(), (a) -> 0);
                if (amount < max) {
                    accelerators.add(accelerator);
                    acceleratorPositions.add(((BlockEntity) accelerator).getBlockPos());
                    speed += accelerator.getAcceleration();
                    entries.replace(accelerator.getAcceleratorType(), amount + 1);
                }
            }
        }
    }

    public Vec3 getCentralItemOffset() {
        return ALTAR_ITEM_OFFSET;
    }

    public Vec3 getItemPos() {
        final BlockPos blockPos = getBlockPos();
        final Vec3 offset = getCentralItemOffset();
        return new Vec3(blockPos.getX() + offset.x, blockPos.getY() + offset.y, blockPos.getZ() + offset.z);
    }

    public Vec3 getSpiritItemOffset(int slot, float partialTicks) {
        float projectedSpiritSpin = spiritSpin + spiritYLevel * 0.05f + speed * 0.5f;
        float lerpSpiritSpin = spiritSpin + partialTicks * (projectedSpiritSpin - spiritSpin);
        float distance = 1 - getSpinUp(Easing.SINE_OUT) * 0.25f + (float) Math.sin((lerpSpiritSpin % 6.28f) / 20f) * 0.025f;
        float height = 0.75f + getSpinUp(Easing.QUARTIC_OUT) * getSpinUp(Easing.BACK_OUT) * 0.5f;

        int period = 360;
        double angle = slot / spiritAmount * (Math.PI * 2);
        angle += ((lerpSpiritSpin % period) / period) * (Math.PI * 2);
        double dx2 = (distance * Math.cos(angle));
        double dz2 = (distance * Math.sin(angle));

        Vec3 vector2f = new Vec3(dx2, 0, dz2);
        double x = vector2f.x * distance;
        double z = vector2f.z * distance;
        return new Vec3(x + 0.5f, height, z + 0.5f);
        // Datahelper clamps to long... why?
//        return DataHelper.rotatingRadialOffset(new Vec3(0.5f, height, 0.5f), distance, slot, spiritAmount, (long) lerpSpiritSpin, 360);
    }

    public float getSpinUp(Easing easing) {
        if (spiritYLevel > 30) {
            return 1;
        }
        return easing.ease(spiritYLevel / 30f, 0, 1, 1);
    }

    @Override
    public @Nullable IItemHandler getCapability(Level level, BlockPos blockPos, BlockState blockState, @Nullable BlockEntity blockEntity, Direction side) {
        if (side == null) {
            return internalInventory.get();
        }
        return exposedInventory.get();
    }
}
