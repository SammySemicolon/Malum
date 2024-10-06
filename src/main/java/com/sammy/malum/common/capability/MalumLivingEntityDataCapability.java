package com.sammy.malum.common.capability;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.sammy.malum.MalumMod;
import com.sammy.malum.common.packets.CodecUtil;
import com.sammy.malum.common.packets.SyncLivingCapabilityDataPacket;
import com.sammy.malum.core.handlers.*;
import net.minecraft.core.UUIDUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.EntityCapability;
import net.neoforged.neoforge.capabilities.ICapabilityProvider;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class MalumLivingEntityDataCapability {

    public static final EntityCapability<MalumLivingEntityDataCapability, Void> CAPABILITY = EntityCapability.createVoid(
            MalumMod.malumPath("living_data"),
            MalumLivingEntityDataCapability.class
    );

    public static final Codec<MalumLivingEntityDataCapability> CODEC = RecordCodecBuilder.create(obj -> obj.group(
            SoulDataHandler.CODEC.fieldOf("soulData").forGetter(c -> c.soulData),
            TouchOfDarknessHandler.CODEC.fieldOf("darknessAfflictionData").forGetter(c -> c.touchOfDarknessHandler),
            Codec.INT.fieldOf("watcherNecklaceCooldown").forGetter(c -> c.watcherNecklaceCooldown),
            Codec.INT.fieldOf("hiddenBladeNecklaceCooldown").forGetter(c -> c.hiddenBladeNecklaceCooldown),
            Codec.list(ItemStack.CODEC).fieldOf("soulsToApplyToDrops").forGetter(c -> c.soulsToApplyToDrops),
            UUIDUtil.CODEC.fieldOf("killerUUID").forGetter(c -> c.killerUUID)
    ).apply(obj, MalumLivingEntityDataCapability::new));

    public SoulDataHandler soulData = new SoulDataHandler();
    public MalignantConversionHandler malignantConversionHandler = new MalignantConversionHandler();
    public TouchOfDarknessHandler touchOfDarknessHandler = new TouchOfDarknessHandler();

    public int watcherNecklaceCooldown;
    public int hiddenBladeNecklaceCooldown;

    public List<ItemStack> soulsToApplyToDrops;
    public UUID killerUUID;

    public MalumLivingEntityDataCapability() {}

    public MalumLivingEntityDataCapability(SoulDataHandler soulData, TouchOfDarknessHandler touchOfDarknessHandler, int watcherNecklaceCooldown, int hiddenBladeNecklaceCooldown, List<ItemStack> soulsToApplyToDrops, UUID killerUUID) {
        this.soulData = soulData;
        this.touchOfDarknessHandler = touchOfDarknessHandler;
        this.hiddenBladeNecklaceCooldown = hiddenBladeNecklaceCooldown;
        this.soulsToApplyToDrops = soulsToApplyToDrops;
        this.killerUUID = killerUUID;
    }

    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerEntity(CAPABILITY, EntityType.PLAYER, GET_CAPABILITY);
    }

    public static ICapabilityProvider<LivingEntity, Void, MalumLivingEntityDataCapability> GET_CAPABILITY = (livingEntity, ctx) -> {
        CompoundTag tag = livingEntity.getPersistentData();
        return CodecUtil.decodeNBT(CODEC, tag);
    };

    public static void syncEntityCapability(PlayerEvent.StartTracking event) {
        if (event.getTarget() instanceof LivingEntity livingEntity) {
            if (livingEntity.level() instanceof ServerLevel) {
                MalumLivingEntityDataCapability.sync(livingEntity);
            }
        }
    }

    public static void sync(LivingEntity entity) {
        getCapabilityOptional(entity).ifPresent(c -> PacketDistributor.sendToPlayersTrackingEntity(entity, new SyncLivingCapabilityDataPacket(entity.getId(), (CompoundTag)CodecUtil.encodeNBT(CODEC, c))));
    }

    public static Optional<MalumLivingEntityDataCapability> getCapabilityOptional(LivingEntity entity) {
        return Optional.ofNullable(entity.getCapability(CAPABILITY));
    }

    public static MalumLivingEntityDataCapability getCapability(LivingEntity entity) {
        return getCapabilityOptional(entity).orElse(new MalumLivingEntityDataCapability());
    }

    public void pullFromNBT(CompoundTag tag) {
        MalumLivingEntityDataCapability decoded = CodecUtil.decodeNBT(CODEC, tag);
        this.soulData = decoded.soulData;
        this.touchOfDarknessHandler = decoded.touchOfDarknessHandler;
        this.watcherNecklaceCooldown = decoded.watcherNecklaceCooldown;
        this.soulsToApplyToDrops = decoded.soulsToApplyToDrops;
        this.killerUUID = decoded.killerUUID;
    }
}