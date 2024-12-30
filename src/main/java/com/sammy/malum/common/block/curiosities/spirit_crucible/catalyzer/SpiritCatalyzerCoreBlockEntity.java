package com.sammy.malum.common.block.curiosities.spirit_crucible.catalyzer;

import com.sammy.malum.common.block.*;
import com.sammy.malum.common.block.curiosities.spirit_crucible.artifice.ArtificeModifierSource;
import com.sammy.malum.common.block.curiosities.spirit_crucible.artifice.AugmentBlockEntityInventory;
import com.sammy.malum.common.block.curiosities.spirit_crucible.artifice.IArtificeAcceptor;
import com.sammy.malum.common.item.augment.*;
import com.sammy.malum.core.systems.spirit.*;
import com.sammy.malum.registry.common.block.*;
import com.sammy.malum.visual_effects.*;
import net.minecraft.core.*;
import net.minecraft.nbt.*;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.phys.*;
import net.neoforged.neoforge.capabilities.IBlockCapabilityProvider;
import net.neoforged.neoforge.items.IItemHandler;
import team.lodestar.lodestone.systems.multiblock.*;

import javax.annotation.*;
import java.util.*;
import java.util.function.*;

public class SpiritCatalyzerCoreBlockEntity extends MultiBlockCoreEntity implements ArtificeModifierSource.CrucibleInfluencer, IBlockCapabilityProvider<IItemHandler, Direction> {

    public static final Vec3 CATALYZER_ITEM_OFFSET = new Vec3(0.5f, 2f, 0.5f);
    public static final Vec3 CATALYZER_AUGMENT_OFFSET = new Vec3(0.5f, 2.75f, 0.5f);

    public static final Supplier<HorizontalDirectionStructure> STRUCTURE = () -> (HorizontalDirectionStructure.of(new MultiBlockStructure.StructurePiece(0, 1, 0, BlockRegistry.SPIRIT_CATALYZER_COMPONENT.get().defaultBlockState())));

    public MalumBlockEntityInventory inventory;
    public MalumBlockEntityInventory augmentInventory;

    public float burnTicks;
    public HashMap<MalumSpiritType, Integer> intensity;
    public CatalyzerArtificeModifierSource modifier;
    protected IArtificeAcceptor target;

    public SpiritCatalyzerCoreBlockEntity(BlockEntityType<? extends SpiritCatalyzerCoreBlockEntity> type, MultiBlockStructure structure, BlockPos pos, BlockState state) {
        super(type, structure, pos, state);
        inventory = MalumBlockEntityInventory.singleItemStack(this);
        augmentInventory = AugmentBlockEntityInventory.augmentInventory(this, 1);
    }

    public SpiritCatalyzerCoreBlockEntity(BlockPos pos, BlockState state) {
        this(BlockEntityRegistry.SPIRIT_CATALYZER.get(), STRUCTURE.get(), pos, state);
    }

    @Override
    public ArtificeModifierSource createFocusingModifierInstance() {
        return modifier = new CatalyzerArtificeModifierSource(this);
    }

    @Override
    public Optional<ArtificeModifierSource> getFocusingModifierInstance() {
        return Optional.ofNullable(modifier);
    }

    @Override
    public ItemInteractionResult onUse(Player pPlayer, InteractionHand pHand) {
        return super.onUse(pPlayer, pHand);
    }

    @Override
    public ItemInteractionResult onUseWithItem(Player player, ItemStack heldStack, InteractionHand hand) {
        if (!(level instanceof ServerLevel serverLevel)) {
            return ItemInteractionResult.CONSUME;
        }
        if (hand.equals(InteractionHand.MAIN_HAND)) {
            final boolean augmentOnly = heldStack.getItem() instanceof AugmentItem;
            if (augmentOnly || (heldStack.isEmpty() && inventory.isEmpty())) {
                ItemStack stack = augmentInventory.interact(serverLevel, player, hand);
                if (!stack.isEmpty()) {
                    return ItemInteractionResult.SUCCESS;
                }
            }
            if (!augmentOnly) {
                inventory.interact(serverLevel, player, hand);
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
        if (level.isClientSide) {
            if (intensity == null) {
                intensity = new HashMap<>();
            }
            if (modifier != null && modifier.isBound()) {
                MalumSpiritType activeSpiritType = target.getActiveSpiritType();
                if (activeSpiritType != null) {
                    intensity.putIfAbsent(activeSpiritType, 0);
                    intensity.put(activeSpiritType, Math.min(60, intensity.get(activeSpiritType) + 1));
                }
                for (MalumSpiritType spiritType : intensity.keySet()) {
                    if (spiritType.equals(activeSpiritType)) {
                        continue;
                    }
                    intensity.put(spiritType, Math.max(0, intensity.get(spiritType) - 1));
                }
            }
            SpiritCrucibleParticleEffects.passiveSpiritCatalyzerParticles(this);
        }
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
    public @Nullable IItemHandler getCapability(Level level, BlockPos blockPos, BlockState blockState, @Nullable BlockEntity blockEntity, Direction direction) {
        return inventory;
    }
}