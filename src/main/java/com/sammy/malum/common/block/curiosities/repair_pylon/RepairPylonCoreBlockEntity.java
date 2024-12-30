package com.sammy.malum.common.block.curiosities.repair_pylon;

import com.sammy.malum.common.block.*;
import com.sammy.malum.common.block.storage.*;
import com.sammy.malum.common.item.spirit.*;
import com.sammy.malum.common.recipe.*;
import com.sammy.malum.core.systems.recipe.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.block.*;
import com.sammy.malum.registry.common.recipe.RecipeTypeRegistry;
import com.sammy.malum.visual_effects.*;
import com.sammy.malum.visual_effects.networked.data.*;
import com.sammy.malum.visual_effects.networked.pylon.*;
import net.minecraft.core.*;
import net.minecraft.nbt.*;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.phys.*;
import net.neoforged.neoforge.capabilities.IBlockCapabilityProvider;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.wrapper.CombinedInvWrapper;
import org.jetbrains.annotations.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.helpers.block.*;
import team.lodestar.lodestone.systems.blockentity.*;
import team.lodestar.lodestone.systems.easing.*;
import team.lodestar.lodestone.systems.multiblock.*;
import team.lodestar.lodestone.systems.recipe.*;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.*;

public class RepairPylonCoreBlockEntity extends MultiBlockCoreEntity implements IBlockCapabilityProvider<IItemHandler, Direction> {

    private static final Vec3 PYLON_ITEM_OFFSET = new Vec3(0.5f, 2.5f, 0.5f);
    private static final int HORIZONTAL_RANGE = 6;
    private static final int VERTICAL_RANGE = 4;

    public static final Supplier<MultiBlockStructure> STRUCTURE = () -> (MultiBlockStructure.of(
            new MultiBlockStructure.StructurePiece(0, 1, 0, BlockRegistry.REPAIR_PYLON_COMPONENT.get().defaultBlockState()),
            new MultiBlockStructure.StructurePiece(0, 2, 0, BlockRegistry.REPAIR_PYLON_COMPONENT.get().defaultBlockState().setValue(RepairPylonComponentBlock.TOP, true))));

    public static final StringRepresentable.EnumCodec<RepairPylonState> CODEC = StringRepresentable.fromEnum(RepairPylonState::values);

    public enum RepairPylonState implements StringRepresentable{
        IDLE("idle"),
        SEARCHING("searching"),
        CHARGING("active"),
        REPAIRING("repairing"),
        COOLDOWN("cooldown");
        final String name;

        RepairPylonState(String name) {
            this.name = name;
        }

        @Override
        public @NotNull String getSerializedName() {
            return name;
        }
    }

    public LodestoneBlockEntityInventory inventory;
    public LodestoneBlockEntityInventory spiritInventory;
    public SpiritRepairRecipe recipe;

    public RepairPylonState state = RepairPylonState.IDLE;
    public BlockPos repairablePosition;
    public int timer;

    public float spiritAmount;
    public float spiritSpin;

    private final Supplier<IItemHandler> combinedInventory = () -> new CombinedInvWrapper(inventory, spiritInventory);

    public RepairPylonCoreBlockEntity(BlockEntityType<? extends RepairPylonCoreBlockEntity> type, MultiBlockStructure structure, BlockPos pos, BlockState state) {
        super(type, structure, pos, state);
        inventory = MalumBlockEntityInventory.singleItemStack(this);
        spiritInventory = MalumSpiritBlockEntityInventory.spiritStacks(this, 4);
    }

    public RepairPylonCoreBlockEntity(BlockPos pos, BlockState state) {
        this(BlockEntityRegistry.REPAIR_PYLON.get(), STRUCTURE.get(), pos, state);
    }

    @Override
    protected void saveAdditional(CompoundTag compound, HolderLookup.Provider pRegistries) {
        compound.putString("state", state.name);
        if (spiritAmount != 0) {
            compound.putFloat("spiritAmount", spiritAmount);
        }
        if (repairablePosition != null) {
            compound.put("targetedBlock", NBTHelper.saveBlockPos(repairablePosition));
        }
        if (timer != 0) {
            compound.putInt("timer", timer);
        }
        inventory.save(pRegistries, compound);
        spiritInventory.save(pRegistries, compound, "spiritInventory");
    }

    @Override
    public void loadAdditional(CompoundTag compound, HolderLookup.Provider pRegistries) {
        state = compound.contains("state") ? CODEC.byName(compound.getString("state")) : RepairPylonState.IDLE;
        spiritAmount = compound.getFloat("spiritAmount");
        repairablePosition = NBTHelper.readBlockPos(compound.getCompound("targetedBlock"));
        timer = compound.getInt("timer");
        inventory.load(pRegistries, compound);
        spiritInventory.load(pRegistries, compound, "spiritInventory");

        super.loadAdditional(compound, pRegistries);
    }

    @Override
    public ItemInteractionResult onUse(Player pPlayer, InteractionHand pHand) {
        if (!(pPlayer.level() instanceof ServerLevel serverLevel)) {
            return ItemInteractionResult.CONSUME;
        }
        if (pHand.equals(InteractionHand.MAIN_HAND)) {
            ItemStack heldStack = pPlayer.getMainHandItem();
            ItemStack spiritStack = spiritInventory.interact(serverLevel, pPlayer, pHand);
            if (!spiritStack.isEmpty()) {
                return ItemInteractionResult.SUCCESS;
            }
            if (!(heldStack.getItem() instanceof SpiritShardItem)) {
                ItemStack finishedStack = inventory.interact(serverLevel, pPlayer, pHand);
                if (!finishedStack.isEmpty()) {
                    return ItemInteractionResult.SUCCESS;
                }
            }
            return ItemInteractionResult.FAIL;
        }
        return super.onUse(pPlayer, pHand);
    }

    @Override
    public void onBreak(@Nullable Player player) {
        inventory.dumpItems(level, worldPosition);
        spiritInventory.dumpItems(level, worldPosition);
        super.onBreak(player);
    }

    @Override
    public void update(@NotNull Level level) {
        spiritAmount = Math.max(1, Mth.lerp(0.15f, spiritAmount, spiritInventory.nonEmptyItemAmount + 1));
        if (state.equals(RepairPylonState.COOLDOWN)) {
            return;
        }
        findRecipe();
        if (recipe != null) {
            if (state.equals(RepairPylonState.IDLE)) {
                setState(RepairPylonState.SEARCHING);
            }
            if (level.isClientSide) {
                RepairPylonSoundInstance.playSound(this);
            }
        }
    }

    @Override
    public void tick() {
        super.tick();
        spiritAmount = Math.max(1, Mth.lerp(0.1f, spiritAmount, spiritInventory.nonEmptyItemAmount));
        if (level.isClientSide) {
            spiritSpin++;
            if (state.equals(RepairPylonState.COOLDOWN) && timer < 1200) {
                timer++;
            }
            var blockEntity = repairablePosition != null ? Optional.ofNullable(level.getBlockEntity(repairablePosition)).map(b -> b instanceof IMalumSpecialItemAccessPoint accessPoint ? accessPoint : null).orElse(null) : null;
            RepairPylonParticleEffects.passiveRepairPylonParticles(this, blockEntity);
        }
        else {
            if (!state.equals(RepairPylonState.IDLE) && !state.equals(RepairPylonState.COOLDOWN)) {
                if (recipe == null) {
                    setState(RepairPylonState.IDLE);
                    return;
                }
            }
            switch (state) {
                case IDLE -> {
                    if (recipe != null) {
                        setState(RepairPylonState.SEARCHING);
                    }
                }
                case SEARCHING -> {
                    timer++;
                    if (timer >= 40) {
                        boolean success = tryRepair();
                        if (success) {
                            setState(RepairPylonState.CHARGING);
                        }
                        else {
                            timer = 0;
                        }
                    }
                }
                case CHARGING -> {
                    timer++;
                    if (timer >= 600) {
                        if (repairablePosition == null) {
                            setState(RepairPylonState.IDLE);
                            return;
                        }
                        if (!(level.getBlockEntity(repairablePosition) instanceof IMalumSpecialItemAccessPoint provider) || !tryRepair(provider)) {
                            setState(RepairPylonState.IDLE);
                            return;
                        }
                        prepareRepair(provider);
                    }
                }
                case REPAIRING -> {
                    timer++;
                    if (timer >= 40) {
                        if (repairablePosition == null) {
                            setState(RepairPylonState.IDLE);
                            return;
                        }
                        if (!(level.getBlockEntity(repairablePosition) instanceof IMalumSpecialItemAccessPoint provider) || !tryRepair(provider)) {
                            setState(RepairPylonState.IDLE);
                            return;
                        }
                        repairItem(provider);
                    }
                }
                case COOLDOWN -> {
                    timer++;
                    if (timer >= 1200) {
                        setState(RepairPylonState.IDLE);
                    }
                }
            }
        }
    }

    public boolean tryRepair() {
        Collection<IMalumSpecialItemAccessPoint> altarProviders = BlockEntityHelper.getBlockEntities(IMalumSpecialItemAccessPoint.class, level, worldPosition, HORIZONTAL_RANGE, VERTICAL_RANGE, HORIZONTAL_RANGE);
        for (IMalumSpecialItemAccessPoint provider : altarProviders) {
            boolean success = tryRepair(provider);
            if (success) {
                repairablePosition = provider.getAccessPointBlockPos();
                return true;
            }
        }
        return false;
    }

    public boolean tryRepair(IMalumSpecialItemAccessPoint provider) {
        var inventoryForPylon = provider.getSuppliedInventory();
        var repairTarget = inventoryForPylon.getStackInSlot(0);
        if (repairTarget.isRepairable() && !repairTarget.isDamaged()) {
            return false;
        }
        findRecipe();
        return recipe != null && recipe.isValidItemForRepair(repairTarget);
    }


    public void prepareRepair(IMalumSpecialItemAccessPoint provider) {
        if (!(getLevel() instanceof ServerLevel serverLevel)) {
            return;
        }
        ParticleEffectTypeRegistry.REPAIR_PYLON_PREPARES.createPositionedEffect(serverLevel, new PositionEffectData(worldPosition), ColorEffectData.fromRecipe(recipe.spirits), PylonPrepareRepairParticleEffect.createData(provider.getAccessPointBlockPos()));
        level.playSound(null, worldPosition, SoundRegistry.REPAIR_PYLON_REPAIR_START.get(), SoundSource.BLOCKS, 1.0f, 0.8f);
        setState(RepairPylonState.REPAIRING);
    }

    public void repairItem(IMalumSpecialItemAccessPoint provider) {
        var suppliedInventory = provider.getSuppliedInventory();
        var repairTarget = suppliedInventory.getStackInSlot(0);
        var repairMaterial = inventory.getStackInSlot(0);
        repairMaterial.shrink(recipe.repairMaterial.count());
        for (SpiritIngredient spirit : recipe.spirits) {
            for (int i = 0; i < spiritInventory.slotCount; i++) {
                ItemStack spiritStack = spiritInventory.getStackInSlot(i);
                if (spirit.test(spiritStack)) {
                    spiritStack.shrink(spirit.getCount());
                    break;
                }
            }
        }
        var result = recipe.getResultItem(repairTarget);
        suppliedInventory.setStackInSlot(0, result);
        level.playSound(null, worldPosition, SoundRegistry.REPAIR_PYLON_REPAIR_FINISH.get(), SoundSource.BLOCKS, 1.0f, 0.8f);
        ParticleEffectTypeRegistry.REPAIR_PYLON_REPAIRS.createPositionedEffect((ServerLevel) level, new PositionEffectData(worldPosition), ColorEffectData.fromRecipe(recipe.spirits), PylonPrepareRepairParticleEffect.createData(provider.getAccessPointBlockPos()));
        setState(RepairPylonState.COOLDOWN);
    }

    public void setState(RepairPylonState state) {
        this.state = state;
        this.timer = state.equals(RepairPylonState.SEARCHING) ? 100 : 0;
        BlockStateHelper.updateAndNotifyState(level, worldPosition);
    }

    public void findRecipe() {
        recipe = LodestoneRecipeType.findRecipe(level,
                RecipeTypeRegistry.SPIRIT_REPAIR.get(),
                c -> c.matches(new SpiritBasedRecipeInput(inventory.getStackInSlot(0), spiritInventory.nonEmptyItemStacks), level));
    }

    public Vec3 getItemPos() {
        final BlockPos blockPos = getBlockPos();
        final Vec3 offset = getCentralItemOffset();
        return new Vec3(blockPos.getX()+offset.x, blockPos.getY()+offset.y, blockPos.getZ()+offset.z);
    }

    public Vec3 getCentralItemOffset() {
        return PYLON_ITEM_OFFSET;
    }

    public Vec3 getSpiritItemOffset(int slot, float partialTicks) {
        float distance = 0.75f + (float) Math.sin(((spiritSpin + partialTicks) % 6.28f) / 20f) * 0.025f;
        float height = 2.75f;
        if (state.equals(RepairPylonState.COOLDOWN)) {
            int relativeCooldown = timer < 1110 ? Math.min(timer, 90) : 1200-timer;
            distance += getCooldownOffset(relativeCooldown, Easing.SINE_OUT) * 0.25f;
            height -= getCooldownOffset(relativeCooldown, Easing.QUARTIC_OUT) * getCooldownOffset(relativeCooldown, Easing.BACK_OUT) * 0.5f;
        }
        return VecHelper.rotatingRadialOffset(new Vec3(0.5f, height, 0.5f), distance, slot, spiritAmount, spiritSpin + partialTicks, 360);
    }

    public float getCooldownOffset(int relativeCooldown, Easing easing) {
        return easing.ease(relativeCooldown / 90f, 0, 1, 1);
    }

    @Override
    public @Nullable IItemHandler getCapability(Level level, BlockPos blockPos, BlockState blockState, @Nullable BlockEntity blockEntity, Direction direction) {
        return combinedInventory.get();
    }
}