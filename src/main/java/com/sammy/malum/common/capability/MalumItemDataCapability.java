package com.sammy.malum.common.capability;

import com.sammy.malum.MalumMod;
import com.sammy.ortus.systems.capability.OrtusCapability;
import com.sammy.ortus.systems.capability.OrtusCapabilityProvider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MalumItemDataCapability implements OrtusCapability {

    public List<ItemStack> soulsToDrop;
    public UUID attackerForSouls;
    public float totalSoulCount;

    public static Capability<MalumItemDataCapability> CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {
    });


    public MalumItemDataCapability() {
    }

    public static void attachItemCapability(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof ItemEntity) {
            final MalumItemDataCapability capability = new MalumItemDataCapability();
            event.addCapability(MalumMod.prefix("item_data"), new OrtusCapabilityProvider<>(CAPABILITY, () -> capability));
        }
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        if (soulsToDrop != null) {
            ListTag souls = new ListTag();
            for (ItemStack soul : soulsToDrop) {
                souls.add(soul.serializeNBT());
            }
            tag.put("soulsToDrop", souls);
        }
        if (attackerForSouls != null) {
            tag.putUUID("attacker", attackerForSouls);
        }
        tag.putFloat("soulCount", totalSoulCount);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        if (tag.contains("soulsToDrop", Tag.TAG_LIST)) {
            soulsToDrop = new ArrayList<>();
            ListTag souls = tag.getList("soulsToDrop", Tag.TAG_COMPOUND);
            for (int i = 0; i < souls.size(); i++) {
                soulsToDrop.add(ItemStack.of(souls.getCompound(i)));
            }
        } else {
            soulsToDrop = null;
        }

        if (tag.hasUUID("attacker")) {
            attackerForSouls = tag.getUUID("attacker");
        } else {
            attackerForSouls = null;
        }

        totalSoulCount = tag.getFloat("soulCount");
    }

    public static LazyOptional<MalumItemDataCapability> getCapabilityOptional(ItemEntity entity) {
        return entity.getCapability(CAPABILITY);
    }

    public static MalumItemDataCapability getCapability(ItemEntity entity) {
        return entity.getCapability(CAPABILITY).orElse(new MalumItemDataCapability());
    }
}
