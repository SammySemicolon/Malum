package com.sammy.malum.common.capability;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.packets.SyncMalumPlayerCapabilityDataPacket;
import com.sammy.malum.core.handlers.*;
import com.sammy.malum.registry.common.PacketRegistry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.network.PacketDistributor;
import team.lodestar.lodestone.systems.capability.LodestoneCapability;
import team.lodestar.lodestone.systems.capability.LodestoneCapabilityProvider;

public class MalumPlayerDataCapability implements LodestoneCapability {

    public static Capability<MalumPlayerDataCapability> CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {
    });

    public SoulWardHandler soulWardHandler = new SoulWardHandler();
    public ReserveStaffChargeHandler reserveStaffChargeHandler = new ReserveStaffChargeHandler();

    public boolean obtainedEncyclopedia;
    public boolean hasBeenRejected;

    public MalumPlayerDataCapability() {
    }

    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.register(MalumPlayerDataCapability.class);
    }

    public static void attachPlayerCapability(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            final MalumPlayerDataCapability capability = new MalumPlayerDataCapability();
            event.addCapability(MalumMod.malumPath("player_data"), new LodestoneCapabilityProvider<>(MalumPlayerDataCapability.CAPABILITY, () -> capability));
        }
    }

    public static void playerJoin(EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (player instanceof ServerPlayer serverPlayer) {
                syncSelf(serverPlayer);
            }
        }
    }

    public static void syncPlayerCapability(PlayerEvent.StartTracking event) {
        if (event.getTarget() instanceof Player player) {
            if (player.level() instanceof ServerLevel) {
                syncTracking(player);
            }
        }
    }

    public static void playerClone(PlayerEvent.Clone event) {
        if (event.getOriginal() instanceof ServerPlayer oldPlayer && event.getEntity() instanceof ServerPlayer newPlayer) {
            oldPlayer.reviveCaps();
            MalumPlayerDataCapability.getCapabilityOptional(oldPlayer).ifPresent(oldCapability -> {
                MalumPlayerDataCapability.getCapabilityOptional(newPlayer).ifPresent(newCapability -> {
                    newCapability.deserializeNBT(oldCapability.serializeNBT());
                });
            });
            oldPlayer.invalidateCaps();
        }
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();

        tag.put("soulWardData", soulWardHandler.serializeNBT());
        tag.put("staffChargeData", reserveStaffChargeHandler.serializeNBT());

        tag.putBoolean("obtainedEncyclopedia", obtainedEncyclopedia);
        tag.putBoolean("hasBeenRejected", hasBeenRejected);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        if (tag.contains("soulWardData")) {
            soulWardHandler.deserializeNBT(tag.getCompound("soulWardData"));
        }
        if (tag.contains("staffChargeData")) {
            reserveStaffChargeHandler.deserializeNBT(tag.getCompound("staffChargeData"));
        }

        obtainedEncyclopedia = tag.getBoolean("obtainedEncyclopedia");
        hasBeenRejected = tag.getBoolean("hasBeenRejected");
    }

    public static void syncSelf(ServerPlayer player) {
        sync(player, PacketDistributor.PLAYER.with(() -> player));
    }

    public static void syncTrackingAndSelf(Player player) {
        sync(player, PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> player));
    }

    public static void syncTracking(Player player) {
        sync(player, PacketDistributor.TRACKING_ENTITY.with(() -> player));
    }

    public static void sync(Player player, PacketDistributor.PacketTarget target) {
        getCapabilityOptional(player).ifPresent(c -> PacketRegistry.MALUM_CHANNEL.send(target, new SyncMalumPlayerCapabilityDataPacket(player.getUUID(), c.serializeNBT())));
    }

    public static LazyOptional<MalumPlayerDataCapability> getCapabilityOptional(Player player) {
        return player.getCapability(CAPABILITY);
    }

    public static MalumPlayerDataCapability getCapability(Player player) {
        return player.getCapability(CAPABILITY).orElse(new MalumPlayerDataCapability());
    }
}
