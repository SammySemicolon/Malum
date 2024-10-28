package com.sammy.malum.common.block.curiosities.repair_pylon;

import com.sammy.malum.MalumMod;
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
import team.lodestar.lodestone.systems.blockentity.*;
import team.lodestar.lodestone.systems.easing.*;
import team.lodestar.lodestone.systems.multiblock.*;

import javax.annotation.Nullable;
import javax.annotation.*;
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
        public String getSerializedName() {
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
        inventory = new MalumBlockEntityInventory(1, 64, t -> !(t.getItem() instanceof SpiritShardItem)) {
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
            compound.put("targetedBlock", BlockHelper.saveBlockPos(new CompoundTag(), repairablePosition));
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
        repairablePosition = BlockHelper.loadBlockPos(compound.getCompound("targetedBlock"));
        timer = compound.getInt("timer");
        inventory.load(pRegistries, compound);
        spiritInventory.load(pRegistries, compound, "spiritInventory");

        super.loadAdditional(compound, pRegistries);
    }

    @Override
    public ItemInteractionResult onUseWithItem(Player player, ItemStack stack, InteractionHand hand) {
        if (level.isClientSide) {
            return ItemInteractionResult.CONSUME;
        }
        if (hand.equals(InteractionHand.MAIN_HAND)) {
            ItemStack heldStack = player.getMainHandItem();
            ItemStack spiritStack = spiritInventory.interact(level, player, hand);
            if (!spiritStack.isEmpty()) {
                return ItemInteractionResult.SUCCESS;
            }
            if (!(heldStack.getItem() instanceof SpiritShardItem)) {
                ItemStack finishedStack = inventory.interact(level, player, hand);
                if (!finishedStack.isEmpty()) {
                    return ItemInteractionResult.SUCCESS;
                }
            }
            return ItemInteractionResult.FAIL;
        }
        return super.onUse(player, hand);
    }

    @Override
    public void onBreak(@Nullable Player player) {
        inventory.dumpItems(level, worldPosition);
        spiritInventory.dumpItems(level, worldPosition);
        super.onBreak(player);
    }

    @Override
    public void init() {
        if (state.equals(RepairPylonState.COOLDOWN)) {
            return;
        }
        boolean wasNull = recipe == null;
        recipe = LodestoneRecipeType.findRecipe(level, RecipeTypeRegistry.SPIRIT_REPAIR.get(), c -> c.matches(new SingleRecipeInput(inventory.getStackInSlot(0)), level) && c.doSpiritsMatch(spiritInventory.nonEmptyItemStacks));
        if (wasNull) {
            setState(RepairPylonState.SEARCHING);
        }
    }

    @Override
    public void tick() {
        super.tick();
        spiritAmount = Math.max(1, Mth.lerp(0.1f, spiritAmount, spiritInventory.nonEmptyItemAmount));
        if (level.isClientSide) {
            spiritSpin++;
            if (state.equals(RepairPylonState.COOLDOWN) && timer < 300) {
                timer++;
            }
            RepairPylonParticleEffects.passiveRepairPylonParticles(this);
        }
        else {
            switch (state) {
                case IDLE -> {
                    if (recipe != null) {
                        setState(RepairPylonState.SEARCHING);
                    }
                }
                case SEARCHING -> {
                    timer++;
                    if (timer >= 100) {
                        if (recipe == null) {
                            timer = 0;
                            return;
                        }
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
                    if (recipe == null) {
                        setState(RepairPylonState.IDLE);
                        return;
                    }
                    if (timer >= 60) {
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
                    if (recipe == null) {
                        setState(RepairPylonState.IDLE);
                        return;
                    }
                    if (timer >= 30) {
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
                    if (timer >= 300) {
                        setState(RepairPylonState.IDLE);
                    }
                }
            }
        }
    }

    public boolean tryRepair() {
        Collection<IMalumSpecialItemAccessPoint> altarProviders = BlockHelper.getBlockEntities(IMalumSpecialItemAccessPoint.class, level, worldPosition, HORIZONTAL_RANGE, VERTICAL_RANGE, HORIZONTAL_RANGE);
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
        LodestoneBlockEntityInventory inventoryForAltar = provider.getSuppliedInventory();
        if (inventoryForAltar.getStackInSlot(0).isRepairable() && !inventoryForAltar.getStackInSlot(0).isDamaged()) return false;
        if (getLevel() instanceof ServerLevel serverSide) return LodestoneRecipeType.findRecipe(serverSide, RecipeTypeRegistry.SPIRIT_REPAIR.get(), c -> c.matches(new SingleRecipeInput(inventoryForAltar.getStackInSlot(0)), level) && c.doesRepairMatch(inventory.getStackInSlot(0)) && c.doSpiritsMatch(spiritInventory.nonEmptyItemStacks)) != null;
        else MalumMod.LOGGER.warn("RepairPylonCBE.tryRepair called from wrong side"); return false;
    }

    public void prepareRepair(IMalumSpecialItemAccessPoint provider) {
        if (getLevel() instanceof ServerLevel serverSide) ParticleEffectTypeRegistry.REPAIR_PYLON_PREPARES.createPositionedEffect(serverSide, new PositionEffectData(worldPosition), ColorEffectData.fromRecipe(recipe.spirits), PylonPrepareRepairParticleEffect.createData(provider.getAccessPointBlockPos()));
        else MalumMod.LOGGER.warn("RepairPylonCBE.prepareRepair called from wrong side");
        setState(RepairPylonState.REPAIRING);
    }

    public void repairItem(IMalumSpecialItemAccessPoint provider) {
        final LodestoneBlockEntityInventory suppliedInventory = provider.getSuppliedInventory();
        ItemStack damagedItem = suppliedInventory.getStackInSlot(0);
        ItemStack repairMaterial = inventory.getStackInSlot(0);
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
        spiritInventory.updateData();
        ItemStack result = LodestoneRecipeType.getRecipe(
                level, RecipeTypeRegistry.SPIRIT_REPAIR.get(),
                new SingleRecipeInput(damagedItem)
        ).getResultItem(level.registryAccess());
        result.setDamageValue(Math.max(0, result.getDamageValue() - (int) (result.getMaxDamage() * recipe.durabilityPercentage)));
        suppliedInventory.setStackInSlot(0, result);
        ParticleEffectTypeRegistry.REPAIR_PYLON_REPAIRS.createPositionedEffect((ServerLevel) level, new PositionEffectData(worldPosition), ColorEffectData.fromRecipe(recipe.spirits), PylonPrepareRepairParticleEffect.createData(provider.getAccessPointBlockPos()));
        setState(RepairPylonState.COOLDOWN);
    }

    public void setState(RepairPylonState state) {
        this.state = state;
        this.timer = 0;
        BlockHelper.updateAndNotifyState(level, worldPosition);
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
            int relativeCooldown = timer < 270 ? Math.min(timer, 30) : 300-timer;
            distance += getCooldownOffset(relativeCooldown, Easing.SINE_OUT) * 0.25f;
            height -= getCooldownOffset(relativeCooldown, Easing.QUARTIC_OUT) * getCooldownOffset(relativeCooldown, Easing.BACK_OUT) * 0.5f;
        }
        return DataHelper.rotatingRadialOffset(new Vec3(0.5f, height, 0.5f), distance, slot, spiritAmount, (long) (spiritSpin + partialTicks), 360);
    }

    public float getCooldownOffset(int relativeCooldown, Easing easing) {
        return easing.ease(relativeCooldown / 30f, 0, 1, 1);
    }

    @Override
    public @Nullable IItemHandler getCapability(Level level, BlockPos blockPos, BlockState blockState, @Nullable BlockEntity blockEntity, Direction direction) {
        return combinedInventory.get();
    }

    /*@Override
    public AABB getRenderBoundingBox() {
        var pos = worldPosition;
        return new AABB(pos.getX() - 1, pos.getY(), pos.getZ() - 1, pos.getX() + 1, pos.getY() + 4, pos.getZ() + 1);
    }*/
}