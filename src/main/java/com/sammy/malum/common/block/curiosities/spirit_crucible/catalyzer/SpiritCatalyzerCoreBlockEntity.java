package com.sammy.malum.common.block.curiosities.spirit_crucible.catalyzer;

import com.sammy.malum.common.block.*;
import com.sammy.malum.common.block.curiosities.spirit_crucible.*;
import com.sammy.malum.common.item.BlazingQuartzItem;
import com.sammy.malum.common.item.augment.*;
import com.sammy.malum.common.item.spirit.*;
import com.sammy.malum.core.systems.spirit.*;
import com.sammy.malum.registry.common.block.*;
import com.sammy.malum.visual_effects.*;
import net.minecraft.core.*;
import net.minecraft.nbt.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.phys.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.capabilities.IBlockCapabilityProvider;
import net.neoforged.neoforge.common.CommonHooks;
import net.neoforged.neoforge.items.IItemHandler;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.systems.blockentity.*;
import team.lodestar.lodestone.systems.multiblock.*;

import javax.annotation.*;
import java.util.*;
import java.util.function.*;

public class SpiritCatalyzerCoreBlockEntity extends MultiBlockCoreEntity implements ICrucibleAccelerator, IBlockCapabilityProvider<IItemHandler, Direction> {

    private static final Vec3 CATALYZER_ITEM_OFFSET = new Vec3(0.5f, 2f, 0.5f);
    private static final Vec3 CATALYZER_AUGMENT_OFFSET = new Vec3(0.5f, 2.75f, 0.5f);

    public static final Supplier<HorizontalDirectionStructure> STRUCTURE = () -> (HorizontalDirectionStructure.of(new MultiBlockStructure.StructurePiece(0, 1, 0, BlockRegistry.SPIRIT_CATALYZER_COMPONENT.get().defaultBlockState())));

    public LodestoneBlockEntityInventory inventory;
    public LodestoneBlockEntityInventory augmentInventory;

    public float burnTicks;
    public HashMap<MalumSpiritType, Integer> intensity;
    protected ICatalyzerAccelerationTarget target;

    public SpiritCatalyzerCoreBlockEntity(BlockEntityType<? extends SpiritCatalyzerCoreBlockEntity> type, MultiBlockStructure structure, BlockPos pos, BlockState state) {
        super(type, structure, pos, state);
        inventory = new MalumBlockEntityInventory(1, 64, t -> !(t.getItem() instanceof SpiritShardItem)) {
            @Override
            public void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                BlockHelper.updateAndNotifyState(level, worldPosition);
            }
        };
        augmentInventory = new AugmentBlockEntityInventory(1, 1) {
            @Override
            public void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                needsSync = true;
                BlockHelper.updateAndNotifyState(level, worldPosition);
            }
        };
    }

    public SpiritCatalyzerCoreBlockEntity(BlockPos pos, BlockState state) {
        this(BlockEntityRegistry.SPIRIT_CATALYZER.get(), STRUCTURE.get(), pos, state);
    }

    @Override
    protected void saveAdditional(CompoundTag compound, HolderLookup.Provider registryLookup) {
        if (burnTicks != 0) {
            compound.putFloat("burnTicks", burnTicks);
        }

        inventory.save(registryLookup, compound);
        augmentInventory.save(registryLookup, compound, "augmentInventory");
        super.saveAdditional(compound, registryLookup);
    }

    @Override
    public void loadAdditional(CompoundTag compound, HolderLookup.Provider registries) {
        burnTicks = compound.getFloat("burnTicks");

        inventory.load(registries, compound);
        augmentInventory.load(registries, compound, "augmentInventory");
        super.loadAdditional(compound, registries);
    }


    @Override
    public ItemInteractionResult onUseWithItem(Player player, ItemStack heldStack, InteractionHand hand) {
        if (level.isClientSide) {
            return ItemInteractionResult.CONSUME;
        }
        if (hand.equals(InteractionHand.MAIN_HAND)) {
            final boolean augmentOnly = heldStack.getItem() instanceof AbstractAugmentItem;
            if (augmentOnly || (heldStack.isEmpty() && inventory.isEmpty())) {
                ItemStack stack = augmentInventory.interact(player.level(), player, hand);
                if (!stack.isEmpty()) {
                    return ItemInteractionResult.SUCCESS;
                }
            }
            if (!augmentOnly) {
                inventory.interact(player.level(), player, hand);
            }
            if (heldStack.isEmpty()) {
                return ItemInteractionResult.SUCCESS;
            }
        }
        return super.onUseWithItem(player, heldStack, hand);
    }

    @Override
    public void onBreak(@Nullable Player player) {
        if (!level.isClientSide) {
            inventory.dumpItems(level, worldPosition);
            augmentInventory.dumpItems(level, worldPosition);
        }
        super.onBreak(player);
    }

    @Override
    public void tick() {
        if (target != null && target.isValidAccelerationTarget()) {
            if (burnTicks > 0) {
                CrucibleAccelerationData data = target.getAccelerationData();
                float ratio = data.fuelUsageRate.getValue(data);
                if (ratio > 0) {
                    burnTicks -= ratio;
                }
            }
        }
        if (level.isClientSide) {
            if (intensity == null) {
                intensity = new HashMap<>();
            }
            if (target != null) {
                boolean canBeAccelerated = target.isValidAccelerationTarget();
                MalumSpiritType activeSpiritType = target.getActiveSpiritType();
                if (activeSpiritType != null) {
                    intensity.putIfAbsent(activeSpiritType, 0);
                    if (canBeAccelerated) {
                        intensity.put(activeSpiritType, Math.min(60, intensity.get(activeSpiritType) + 1));
                    }
                }
                for (MalumSpiritType spiritType : intensity.keySet()) {
                    if (canBeAccelerated && spiritType.equals(activeSpiritType)) {
                        continue;
                    }
                    intensity.put(spiritType, Math.max(0,intensity.get(spiritType) - 1));
                }
            }
            SpiritCrucibleParticleEffects.passiveSpiritCatalyzerParticles(this);
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void addParticles(ICatalyzerAccelerationTarget target, MalumSpiritType spiritType) {
        if (burnTicks > 0) {
            SpiritCrucibleParticleEffects.activeSpiritCatalyzerParticles(this, target, spiritType);
        }
    }

    @Override
    public boolean canStartAccelerating() {
        if (burnTicks > 0) {
            return true;
        }
        return inventory.getStackInSlot(0).getBurnTime(RecipeType.SMELTING) > 0;
    }

    @Override
    public boolean canContinueAccelerating() {
        if (isRemoved()) {
            return false;
        }
        if (burnTicks <= 0) {
            ItemStack stack = inventory.getStackInSlot(0);
            if (!stack.isEmpty()) {
                burnTicks = inventory.getStackInSlot(0).getBurnTime(RecipeType.SMELTING) / 2f;
                stack.shrink(1);
                inventory.updateData();
                BlockHelper.updateAndNotifyState(level, worldPosition);
            }
        }
        return burnTicks != 0;
    }

    @Override
    public CrucibleAcceleratorType getAcceleratorType() {
        return CatalyzerAcceleratorType.CATALYZER;
    }

    @Override
    public ItemStack getAugment() {
        return augmentInventory.getStackInSlot(0);
    }

    @Override
    public ICatalyzerAccelerationTarget getTarget() {
        return target;
    }

    @Override
    public void setTarget(ICatalyzerAccelerationTarget target) {
        this.target = target;
    }

    public Vec3 getItemOffset() {
        return CATALYZER_ITEM_OFFSET;
    }

    public Vec3 getAugmentOffset() {
        return CATALYZER_AUGMENT_OFFSET;
    }

    @Override
    public @Nullable IItemHandler getCapability(Level level, BlockPos blockPos, BlockState blockState, @Nullable BlockEntity blockEntity, Direction direction) {
        return inventory;
    }

    /*@Override
    public AABB getRenderBoundingBox() {
        var pos = worldPosition;
        return new AABB(pos.getX() - 1, pos.getY(), pos.getZ() - 1, pos.getX() + 1, pos.getY() + 4, pos.getZ() + 1);
    }*/
}
