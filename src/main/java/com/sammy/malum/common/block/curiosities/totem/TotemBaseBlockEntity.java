package com.sammy.malum.common.block.curiosities.totem;

import com.sammy.malum.common.spiritrite.*;
import com.sammy.malum.core.systems.spirit.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.block.*;
import net.minecraft.core.*;
import net.minecraft.nbt.*;
import net.minecraft.sounds.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.systems.blockentity.*;

import javax.annotation.*;
import java.util.*;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.*;

public class TotemBaseBlockEntity extends LodestoneBlockEntity {

    public static final StringRepresentable.EnumCodec<TotemRiteState> CODEC = StringRepresentable.fromEnum(TotemRiteState::values);

    public enum TotemRiteState implements StringRepresentable {
        IDLE("idle"),
        ASSEMBLING("assembling"),
        ACTIVE("active");
        final String name;

        TotemRiteState(String name) {
            this.name = name;
        }

        @Override
        public String getSerializedName() {
            return name;
        }
    }

    public final boolean isSoulwood;

    public TotemRiteState state = TotemRiteState.IDLE;
    public TotemicRiteType activeRite;
    public List<BlockPos> totemPolePositions = new ArrayList<>();
    private Direction direction;
    public int timer;

    public TotemicRiteType cachedRadiusRite;
    public int radiusVisibility;

    public TotemBaseBlockEntity(BlockEntityType<? extends TotemBaseBlockEntity> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        this.isSoulwood = ((TotemBaseBlock<?>) state.getBlock()).corrupted;
    }

    public TotemBaseBlockEntity(BlockPos pos, BlockState state) {
        this(BlockEntityRegistry.TOTEM_BASE.get(), pos, state);
    }

    @Override
    protected void saveAdditional(CompoundTag compound, HolderLookup.Provider registries) {
        compound.putString("state", state.name);
        if (activeRite != null) {
            compound.putString("rite", activeRite.identifier);
        }
        if (direction != null) {
            compound.putString("direction", direction.getName());
        }
        compound.putInt("height", totemPolePositions.size());
        compound.putInt("timer", timer);
        super.saveAdditional(compound, registries);
    }

    @Override
    protected void loadAdditional(CompoundTag compound, HolderLookup.Provider pRegistries) {
        state = compound.contains("state") ? CODEC.byName(compound.getString("state")) : TotemRiteState.IDLE;
        activeRite = SpiritRiteRegistry.getRite(compound.getString("rite"));
        direction = Direction.byName(compound.getString("direction"));
        totemPolePositions.clear();
        for (int i = 1; i <= compound.getInt("height"); i++) {
            totemPolePositions.add(worldPosition.above(i));
        }
        timer = compound.getInt("timer");
        super.loadAdditional(compound, pRegistries);
    }

    @Override
    public void tick() {
        super.tick();
        if (!level.isClientSide) {
            switch (state) {
                case ACTIVE -> {
                    timer++;
                    if (timer >= activeRite.getRiteEffect(isSoulwood).getRiteEffectTickRate()) {
                        activeRite.executeRite(this);
                        timer = 0;
                        BlockHelper.updateAndNotifyState(level, worldPosition);
                    }
                }
                case ASSEMBLING -> {
                    timer--;
                    if (timer <= 0) {
                        BlockPos polePos = worldPosition.above(totemPolePositions.size() + 1);
                        if (level.getBlockEntity(polePos) instanceof TotemPoleBlockEntity pole) {
                            timer = 20;
                            addTotemPole(pole);
                        } else {
                            TotemicRiteType rite = SpiritRiteRegistry.getRite(getSpirits());
                            if (rite == null) {
                                setState(TotemRiteState.IDLE);
                            } else {
                                activeRite = rite;
                                modifyTotemPoles(TotemPoleBlockEntity.TotemPoleState.ACTIVE);
                                rite.executeRite(this);
                                if (rite.getRiteEffect(isSoulwood).category.equals(TotemicRiteEffect.MalumRiteEffectCategory.ONE_TIME_EFFECT)) {
                                    setState(TotemRiteState.IDLE);
                                }
                                else {
                                    setState(TotemRiteState.ACTIVE);
                                    deactivateOtherRites();
                                }
                            }
                        }
                    }
                }
            }
        }
        else {

            if (state.equals(TotemRiteState.IDLE) && radiusVisibility > 0) {
                radiusVisibility--;
                if (radiusVisibility == 0) {
                    cachedRadiusRite = null;
                }
            } else if (state.equals(TotemRiteState.ACTIVE) && radiusVisibility < 40){
                if (activeRite != null && cachedRadiusRite == null) {
                    cachedRadiusRite = activeRite;
                }
                radiusVisibility++;
            }
        }
    }

    @Override
    public ItemInteractionResult onUseWithItem(Player player, ItemStack pStack, InteractionHand pHand) {
        if (state.equals(TotemRiteState.ASSEMBLING)) {
            return ItemInteractionResult.FAIL;
        }
        if (level.getBlockEntity(worldPosition.above()) instanceof TotemPoleBlockEntity) {
            if (!level.isClientSide) {
                if (state.equals(TotemRiteState.ACTIVE)) {
                    setState(TotemRiteState.IDLE);
                } else {
                    setState(TotemRiteState.ASSEMBLING);
                }
                BlockHelper.updateState(level, worldPosition);
            }
            player.swing(InteractionHand.MAIN_HAND, true);
            return ItemInteractionResult.SUCCESS;
        }
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    @Override
    public void onBreak(@Nullable Player player) {
        if (!level.isClientSide) {
            setState(TotemRiteState.IDLE);
        }
    }

    public void addTotemPole(TotemPoleBlockEntity pole) {
        Direction direction = pole.getBlockState().getValue(HORIZONTAL_FACING);
        if (totemPolePositions.isEmpty()) {
            this.direction = direction;
        }
        if (pole.isSoulwood == isSoulwood && direction.equals(this.direction)) {
            if (pole.spirit != null) {
                totemPolePositions.add(pole.getBlockPos());
                pole.riteStarting(this, totemPolePositions.size());
            }
        }
        BlockHelper.updateState(level, worldPosition);
    }

    public void deactivateOtherRites() {
        TotemicRiteEffect riteEffect = activeRite.getRiteEffect(isSoulwood);
        int horizontalRadius = riteEffect.getRiteEffectHorizontalRadius();
        int verticalRadius = riteEffect.getRiteEffectVerticalRadius();
        Collection<TotemBaseBlockEntity> deactivatedTotems = BlockHelper.getBlockEntities(TotemBaseBlockEntity.class, level, riteEffect.getRiteEffectCenter(this), horizontalRadius, verticalRadius, horizontalRadius);
        for (TotemBaseBlockEntity deactivatedTotem : deactivatedTotems) {
            if (deactivatedTotem.equals(this)) {
                continue;
            }
            if (!deactivatedTotem.isActiveOrAssembling()) {
                continue;
            }
            if (deactivatedTotem.activeRite == null || !deactivatedTotem.activeRite.equals(activeRite)) {
                continue;
            }
            deactivatedTotem.setState(TotemRiteState.IDLE);
        }
        Collection<TotemBaseBlockEntity> otherTotems = BlockHelper.getBlockEntities(TotemBaseBlockEntity.class, level, worldPosition, 24);
        for (TotemBaseBlockEntity otherTotem : otherTotems) {
            if (otherTotem.equals(this)) {
                continue;
            }
            if (otherTotem.activeRite == null || !otherTotem.activeRite.equals(activeRite)) {
                continue;
            }
            riteEffect = activeRite.getRiteEffect(isSoulwood);
            horizontalRadius = riteEffect.getRiteEffectHorizontalRadius();
            verticalRadius = riteEffect.getRiteEffectVerticalRadius();
            if (BlockHelper.getBlockEntities(TotemBaseBlockEntity.class, level, riteEffect.getRiteEffectCenter(otherTotem), horizontalRadius, verticalRadius, horizontalRadius).contains(this)) {
                otherTotem.setState(TotemRiteState.IDLE);
            }
        }
    }

    public void setState(TotemRiteState state) {
        if (state.equals(TotemRiteState.ACTIVE)) {
            level.playSound(null, worldPosition, SoundRegistry.TOTEM_ACTIVATED.get(), SoundSource.BLOCKS, 1, 1f);
        }
        if (state.equals(TotemRiteState.IDLE)) {
            if (isActiveOrAssembling()) {
                level.playSound(null, worldPosition, SoundRegistry.TOTEM_CANCELLED.get(), SoundSource.BLOCKS, 1, 1f);
            }
            modifyTotemPoles(TotemPoleBlockEntity.TotemPoleState.INACTIVE);
            totemPolePositions.clear();
            activeRite = null;
            direction = null;
        }
        this.state = state;
        this.timer = 0;
        BlockHelper.updateAndNotifyState(level, worldPosition);
    }

    public void modifyTotemPoles(TotemPoleBlockEntity.TotemPoleState state) {
        for (TotemPoleBlockEntity totemPole : getTotemPoles()) {
            totemPole.setState(state);
        }
    }

    public List<TotemPoleBlockEntity> getTotemPoles() {
        List<TotemPoleBlockEntity> totemPoles = new ArrayList<>();
        for (BlockPos totemPolePosition : totemPolePositions) {
            if (level.getBlockEntity(totemPolePosition) instanceof TotemPoleBlockEntity totemPole) {
                totemPoles.add(totemPole);
            }
        }
        return totemPoles;
    }

    public List<MalumSpiritType> getSpirits() {
        return getTotemPoles().stream().map(t -> t.spirit).toList();
    }

    public Direction getDirection() {
        if (direction == null) {
            final BlockState state = level.getBlockState(worldPosition.above());
            if (state.getBlock() instanceof TotemPoleBlock) {
                direction = state.getValue(HORIZONTAL_FACING);
            }
        }
        return direction;
    }

    public boolean isActiveOrAssembling() {
        return !state.equals(TotemRiteState.IDLE);
    }
}