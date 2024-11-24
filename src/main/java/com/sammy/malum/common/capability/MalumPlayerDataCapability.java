package com.sammy.malum.common.capability;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.sammy.malum.MalumMod;
import com.sammy.malum.common.packets.CodecUtil;
import com.sammy.malum.common.packets.SyncMalumPlayerCapabilityDataPacket;
import com.sammy.malum.core.handlers.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.capabilities.EntityCapability;
import net.neoforged.neoforge.capabilities.ICapabilityProvider;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.Optional;
import java.util.function.Consumer;

public class MalumPlayerDataCapability {

    public static final EntityCapability<MalumPlayerDataCapability, Void> CAPABILITY = EntityCapability.createVoid(
            MalumMod.malumPath("player_data"),
            MalumPlayerDataCapability.class
    );

    public static final Codec<MalumPlayerDataCapability> CODEC = RecordCodecBuilder.create(obj -> obj.group(
            SoulWardHandler.CODEC.fieldOf("soulWardData").forGetter(c -> c.soulWardHandler),
            ReserveStaffChargeHandler.CODEC.fieldOf("staffChargeData").forGetter(c -> c.reserveStaffChargeHandler),
            Codec.BOOL.fieldOf("obtainedEncyclopedia").forGetter(c -> c.obtainedEncyclopedia),
            Codec.BOOL.fieldOf("hasBeenRejected").forGetter(c -> c.hasBeenRejected)
    ).apply(obj, MalumPlayerDataCapability::new));

    public SoulWardHandler soulWardHandler = new SoulWardHandler();
    public ReserveStaffChargeHandler reserveStaffChargeHandler = new ReserveStaffChargeHandler();

    public boolean obtainedEncyclopedia;
    public boolean hasBeenRejected;

    public MalumPlayerDataCapability() {}

    public MalumPlayerDataCapability(SoulWardHandler soulWardHandler, ReserveStaffChargeHandler reserveStaffChargeHandler, boolean obtainedEncyclopedia, boolean hasBeenRejected) {
        this.soulWardHandler = soulWardHandler;
        this.reserveStaffChargeHandler = reserveStaffChargeHandler;
        this.obtainedEncyclopedia = obtainedEncyclopedia;
        this.hasBeenRejected = hasBeenRejected;
    }

    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerEntity(CAPABILITY, EntityType.PLAYER, GET_CAPABILITY);
    }

    public static ICapabilityProvider<Player, Void, MalumPlayerDataCapability> GET_CAPABILITY = (player, ctx) -> {
        CompoundTag tag = player.getPersistentData();
        return CodecUtil.decodeNBT(CODEC, tag);
    };

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
        MalumPlayerDataCapability originalCapability = MalumPlayerDataCapability.getCapability(event.getOriginal());
        MalumPlayerDataCapability capability = MalumPlayerDataCapability.getCapability(event.getEntity());
        capability.hasBeenRejected = originalCapability.hasBeenRejected;
        capability.obtainedEncyclopedia = originalCapability.obtainedEncyclopedia;
        capability.reserveStaffChargeHandler = originalCapability.reserveStaffChargeHandler;
        capability.soulWardHandler = originalCapability.soulWardHandler;
    }

    public static void syncSelf(ServerPlayer player) {
        sync(player, c -> PacketDistributor.sendToPlayer(player,
                new SyncMalumPlayerCapabilityDataPacket(player.getUUID(), (CompoundTag)CodecUtil.encodeNBT(CODEC, c)))
        );
    }

    public static void syncTrackingAndSelf(Player player) {
        sync(player, c -> PacketDistributor.sendToPlayersTrackingEntityAndSelf(player,
                new SyncMalumPlayerCapabilityDataPacket(player.getUUID(), (CompoundTag)CodecUtil.encodeNBT(CODEC, c)))
        );
    }

    public static void syncTracking(Player player) {
        sync(player, c -> PacketDistributor.sendToPlayersTrackingEntity(player,
                new SyncMalumPlayerCapabilityDataPacket(player.getUUID(), (CompoundTag)CodecUtil.encodeNBT(CODEC, c)))
        );
    }

    public static void sync(Player player, Consumer<MalumPlayerDataCapability> syncFunc) {
        getCapabilityOptional(player).ifPresent(syncFunc);
    }

    public static Optional<MalumPlayerDataCapability> getCapabilityOptional(Player player) {
        return Optional.ofNullable(player.getCapability(CAPABILITY));
    }

    public static MalumPlayerDataCapability getCapability(Player player) {
        return getCapabilityOptional(player).orElse(new MalumPlayerDataCapability());
    }

    public void pullFromNBT(CompoundTag tag) {
        MalumPlayerDataCapability decoded = CodecUtil.decodeNBT(CODEC, tag);
        this.soulWardHandler = decoded.soulWardHandler;
        this.reserveStaffChargeHandler = decoded.reserveStaffChargeHandler;
        this.obtainedEncyclopedia = decoded.obtainedEncyclopedia;
        this.hasBeenRejected = decoded.hasBeenRejected;
    }
}
