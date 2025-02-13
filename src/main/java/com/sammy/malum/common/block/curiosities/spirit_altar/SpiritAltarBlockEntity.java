package com.sammy.malum.common.block.curiosities.spirit_altar;

import com.sammy.malum.common.block.MalumBlockEntityInventory;
import com.sammy.malum.common.block.MalumSpiritBlockEntityInventory;
import com.sammy.malum.common.block.storage.IMalumSpecialItemAccessPoint;
import com.sammy.malum.common.recipe.spirit.infusion.SpiritInfusionRecipe;
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
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.*;
import net.neoforged.neoforge.common.crafting.SizedIngredient;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.IItemHandlerModifiable;
import net.neoforged.neoforge.items.wrapper.CombinedInvWrapper;
import org.jetbrains.annotations.NotNull;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.helpers.block.*;
import team.lodestar.lodestone.systems.blockentity.*;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.recipe.*;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Supplier;

public class SpiritAltarBlockEntity extends LodestoneBlockEntity implements IItemHandlerSupplier {

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

    public Supplier<IItemHandler> exposedInventory = () -> new CombinedInvWrapper(inventory, spiritInventory);

    public SpiritAltarBlockEntity(BlockEntityType<? extends SpiritAltarBlockEntity> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public SpiritAltarBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.SPIRIT_ALTAR.get(), pos, state);
        inventory = MalumBlockEntityInventory.singleStackNotSpirit(this).onContentsChanged(this::recalculateRecipes);
        extrasInventory = MalumBlockEntityInventory.stacksNotSpirits(this, 8);
        spiritInventory = MalumSpiritBlockEntityInventory.spiritStacks(this, SpiritTypeRegistry.SPIRITS.size()).onContentsChanged(this::recalculateRecipes);
    }

    @Override
    public IItemHandler getInventory(Direction direction) {
        return exposedInventory.get();
    }

    @Override
    protected void saveAdditional(CompoundTag compound, HolderLookup.Provider pRegistries) {
        compound.putInt("progress", progress);
        compound.putInt("idleProgress", idleProgress);
        compound.putFloat("spiritYLevel", spiritYLevel);
        compound.putFloat("speed", speed);
        compound.putFloat("spiritAmount", spiritAmount);

        var acceleratorData = new CompoundTag();
        acceleratorData.putInt("acceleratorAmount", acceleratorPositions.size());
        for (int i = 0; i < acceleratorPositions.size(); i++) {
            acceleratorData.put("acceleratorPosition_" + i, NBTHelper.saveBlockPos(acceleratorPositions.get(i)));
        }
        compound.put("acceleratorData", acceleratorData);

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

        var acceleratorData = compound.getCompound("acceleratorData");
        int amount = acceleratorData.getInt("acceleratorAmount");
        for (int i = 0; i < amount; i++) {
            BlockPos pos = NBTHelper.readBlockPos(acceleratorData.getCompound("acceleratorPosition_" + i));
            if (pos != null) {
                if (level != null && level.getBlockEntity(pos) instanceof IAltarAccelerator accelerator) {
                    acceleratorPositions.add(pos);
                    accelerators.add(accelerator);
                }
            }
        }
        inventory.load(pRegistries, compound);
        spiritInventory.load(pRegistries, compound, "spiritInventory");
        extrasInventory.load(pRegistries, compound, "extrasInventory");

        if (level != null) {
            recalculateRecipes();
            recalibrateAccelerators();
            if (level.isClientSide && isCrafting) {
                AltarSoundInstance.playSound(this);
            }
        }
        super.loadAdditional(compound, pRegistries);
    }

    @Override
    public void update(@NotNull Level level) {
    }

    @Override
    public void onBreak(@Nullable Player player) {
        inventory.dumpItems(level, worldPosition);
        spiritInventory.dumpItems(level, worldPosition);
        extrasInventory.dumpItems(level, worldPosition);
    }

    @Override
    public ItemInteractionResult onUse(Player pPlayer, InteractionHand pHand) {
        if (!(level instanceof ServerLevel serverLevel)) {
            return ItemInteractionResult.CONSUME;
        }
        var spiritResult = spiritInventory.interact(serverLevel, pPlayer, pHand);
        if (!spiritResult.isEmpty()) {
            return ItemInteractionResult.SUCCESS;
        }
        var impetusResult = inventory.interact(serverLevel, pPlayer, pHand);
        if (!impetusResult.isEmpty()) {
            return ItemInteractionResult.SUCCESS;
        }
        return super.onUse(pPlayer, pHand);
    }

    @Override
    public void tick() {
        super.tick();
        spiritAmount = Math.max(1, Mth.lerp(0.1f, spiritAmount, spiritInventory.getFilledSlotCount()));

        var primeItem = inventory.getStackInSlot(0);
        if (!primeItem.isEmpty()) {
            idleProgress++;
            int progressCap = (int) (20 / speed);
            if (idleProgress >= progressCap) {
                recalculateRecipes();
                idleProgress = 0;
                BlockStateHelper.updateAndNotifyState(level, worldPosition);
            }
        }

        if (!possibleRecipes.isEmpty()) {
            if (spiritYLevel < 30) {
                spiritYLevel++;
            }
            if (!isCrafting && recipe != null) {
                isCrafting = true;
                BlockStateHelper.updateAndNotifyState(level, worldPosition);
            } else if (isCrafting && recipe == null) {
                isCrafting = false;
                BlockStateHelper.updateAndNotifyState(level, worldPosition);
            }
            progress = isCrafting ? progress + 1 : progress;
            if (level instanceof ServerLevel serverLevel) {
                if (serverLevel.getGameTime() % 20L == 0) {
                    boolean canAccelerate = accelerators.stream().allMatch(IAltarAccelerator::canAccelerate);
                    if (!canAccelerate) {
                        recalibrateAccelerators();
                    }
                }
                int progressCap = (int) (300 / speed);
                if (progress >= progressCap) {
                    boolean success = consume();
                    if (success) {
                        craft(serverLevel);
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
        inventory.updateInventoryCaches();
        ItemStack stack = inventory.getStackInSlot(0);
        if (!stack.isEmpty()) {
            final Collection<SpiritInfusionRecipe> all = LodestoneRecipeType.getRecipes(level, RecipeTypeRegistry.SPIRIT_INFUSION.get());
            Collection<SpiritInfusionRecipe> recipes = all.stream().filter(r -> r.matches(new SpiritBasedRecipeInput(stack, spiritInventory.nonEmptyItemStacks), level)).toList();
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
        if (recipe != null) {
            isCrafting = true;
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
                    level.playSound(null, provider.getAccessPointBlockPos(), SoundRegistry.ALTAR_CONSUME.get(), SoundSource.BLOCKS, 1, 1.1f + level.random.nextFloat() * 0.5f);
                    ParticleEffectTypeRegistry.SPIRIT_ALTAR_EATS_ITEM.createPositionedEffect((ServerLevel) level, new PositionEffectData(worldPosition), ColorEffectData.fromSpiritIngredients(recipe.spirits), SpiritAltarEatItemParticleEffect.createData(provider.getAccessPointBlockPos(), providedStack));
                    extrasInventory.insertItem(inventoryForAltar.extractItem(0, nextIngredient.count(), false));
                    BlockStateHelper.updateAndNotifyState(level, provider.getAccessPointBlockPos());
                    break;
                }
            }
            progress = (int) (progress * 0.8f);
            if (extrasInventory.isEmpty()) {
                return false;
            }
            return AltarCraftingHelper.extractIngredient(extrasInventory, nextIngredient.ingredient(), nextIngredient.count(), true).isEmpty();
        }
        return true;
    }

    public void craft(ServerLevel level) {
        ItemStack stack = inventory.getStackInSlot(0);
        ItemStack outputStack = recipe.output.copy();
        Vec3 itemPos = getItemPos();
        ParticleEffectTypeRegistry.SPIRIT_ALTAR_CRAFTS
                .createPositionedEffect(level, new PositionEffectData(worldPosition), ColorEffectData.fromSpiritIngredients(recipe.spirits));
        level.playSound(null, worldPosition, SoundRegistry.ALTAR_CRAFT.get(), SoundSource.BLOCKS, 1, 0.9f + level.random.nextFloat() * 0.2f);
        if (recipe.carryOverData) {
            outputStack.applyComponents(stack.getComponents());
        }
        extrasInventory.clear();
        progress -= (int) (progress * 0.2f);
        stack.shrink(recipe.ingredient.count());
        level.addFreshEntity(new ItemEntity(level, itemPos.x, itemPos.y, itemPos.z, outputStack));
        for (SpiritIngredient spirit : recipe.spirits) {
            for (int i = 0; i < spiritInventory.slotCount; i++) {
                ItemStack spiritStack = spiritInventory.getStackInSlot(i);
                if (spirit.test(spiritStack)) {
                    spiritStack.shrink(spirit.getCount());
                    break;
                }
            }
        }
        recalibrateAccelerators();
        recalculateRecipes();
        BlockStateHelper.updateAndNotifyState(level, worldPosition);
    }

    public void recalibrateAccelerators() {
        speed = 1f;
        accelerators.clear();
        acceleratorPositions.clear();
        Collection<IAltarAccelerator> nearbyAccelerators = BlockEntityHelper.getBlockEntities(IAltarAccelerator.class, level, worldPosition, HORIZONTAL_RANGE, VERTICAL_RANGE, HORIZONTAL_RANGE);
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
        return VecHelper.rotatingRadialOffset(new Vec3(0.5f, height, 0.5f), distance, slot, spiritAmount, lerpSpiritSpin, 360);
    }

    public float getSpinUp(Easing easing) {
        if (spiritYLevel > 30) {
            return 1;
        }
        return easing.ease(spiritYLevel / 30f, 0, 1, 1);
    }
}