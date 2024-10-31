package com.sammy.malum.common.entity;

import com.sammy.malum.core.systems.spirit.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.nbt.*;
import net.minecraft.network.syncher.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;

public abstract class FloatingItemEntity extends FloatingEntity {

    private static final EntityDataAccessor<ItemStack> DATA_ITEM_STACK = SynchedEntityData.defineId(FloatingItemEntity.class, EntityDataSerializers.ITEM_STACK);
    protected static final EntityDataAccessor<String> DATA_SPIRIT = SynchedEntityData.defineId(FloatingItemEntity.class, EntityDataSerializers.STRING);

    public FloatingItemEntity(EntityType<? extends FloatingEntity> type, Level level) {
        super(type, level);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {

        builder.define(DATA_ITEM_STACK, ItemStack.EMPTY);
        builder.define(DATA_SPIRIT, SpiritTypeRegistry.ARCANE_SPIRIT.getIdentifier());
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        ItemStack itemstack = this.getItem();
        if (!itemstack.isEmpty()) {
            pCompound.put("item", getItem().save(this.registryAccess()));
        }
        pCompound.putString("spiritType", getSpiritType().getIdentifier());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        setItem(ItemStack.parse(registryAccess(), pCompound.getCompound("item")).orElse(ItemStack.EMPTY));
        setSpirit(pCompound.getString("spiritType"));
    }

    public ItemStack getItem() {
        return getEntityData().get(DATA_ITEM_STACK);
    }

    public void setItem(ItemStack pStack) {
        this.getEntityData().set(DATA_ITEM_STACK, pStack);
    }

    public MalumSpiritType getSpiritType() {
        return SpiritTypeRegistry.SPIRITS.get(getEntityData().get(DATA_SPIRIT));
    }

    public void setSpirit(MalumSpiritType spiritType) {
        setSpirit(spiritType.getIdentifier());
    }

    public void setSpirit(String spiritIdentifier) {
        this.getEntityData().set(DATA_SPIRIT, spiritIdentifier);
    }
}