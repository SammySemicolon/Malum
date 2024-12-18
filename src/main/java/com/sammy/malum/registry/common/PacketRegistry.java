package com.sammy.malum.registry.common;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.packets.*;
import com.sammy.malum.common.packets.particle.rite.*;
import com.sammy.malum.common.packets.particle.rite.generic.BlockSparkleParticlePacket;
import com.sammy.malum.common.packets.particle.rite.generic.MajorEntityEffectParticlePacket;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ChunkPos;
import team.lodestar.lodestone.registry.common.LodestoneNetworkPayloads;

import java.util.function.BiConsumer;

@SuppressWarnings("unused")
public class PacketRegistry {

    public static void registerNetworkStuff() {

        //functionality

        registerS2C(
                VoidRejectionPayload.ID,
                VoidRejectionPayload.STREAM_CODEC,
                (payload, context) -> payload.handle(payload, context)
        );

        registerS2C(
                ReplenishingCooldownUpdatePayload.ID,
                ReplenishingCooldownUpdatePayload.STREAM_CODEC,
                (payload, context) -> payload.handle(payload, context)
        );
        registerS2C(
                ParticleEffectPacket.ID,
                ParticleEffectPacket.STREAM_CODEC,
                (payload, context) -> payload.handle(payload, context)
        );
        registerS2C(
                MajorEntityEffectParticlePacket.ID,
                MajorEntityEffectParticlePacket.STREAM_CODEC,
                (payload, context) -> payload.handle(payload, context)
        );
        registerS2C(
                SacredMistRiteEffectPacket.ID,
                SacredMistRiteEffectPacket.STREAM_CODEC,
                (payload, context) -> payload.handle(payload, context)
        );
        registerS2C(
                BlockSparkleParticlePacket.ID,
                BlockSparkleParticlePacket.STREAM_CODEC,
                (payload, context) -> payload.handle(payload, context)
        );
        registerS2C(
                InfernalAccelerationRiteEffectPacket.ID,
                InfernalAccelerationRiteEffectPacket.STREAM_CODEC,
                (payload, context) -> payload.handle(payload, context)
        );
        registerS2C(
                InfernalExtinguishRiteEffectPacket.ID,
                InfernalExtinguishRiteEffectPacket.STREAM_CODEC,
                (payload, context) -> payload.handle(payload, context)
        );
        registerS2C(
                AerialBlockFallRiteEffectPacket.ID,
                AerialBlockFallRiteEffectPacket.STREAM_CODEC,
                (payload, context) -> payload.handle(payload, context)
        );
        registerS2C(
                SpiritRiteActivationEffectPacket.ID,
                SpiritRiteActivationEffectPacket.STREAM_CODEC,
                (payload, context) -> payload.handle(payload, context)
        );
        registerS2C(
                SyncCurioDataPayload.ID,
                SyncCurioDataPayload.STREAM_CODEC,
                (payload, context) -> payload.handle(payload, context)
        );
        registerS2C(
                SyncSoulWardDataPayload.ID,
                SyncSoulWardDataPayload.STREAM_CODEC,
                (payload, context) -> payload.handle(payload, context)
        );

        registerS2C(
                BlightTransformItemParticlePacket.ID,
                BlightTransformItemParticlePacket.STREAM_CODEC,
                (payload, context) -> payload.handle(payload, context)
        );
    }

    private static <T extends CustomPacketPayload> void registerS2C(
            CustomPacketPayload.Type<T> type,
            StreamCodec<? super RegistryFriendlyByteBuf, T> codec,
            BiConsumer<T, ClientPlayNetworking.Context> handler
    ) {
        PayloadTypeRegistry.playS2C().register(type, codec);
        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            ClientPlayNetworking.registerGlobalReceiver(type, (payload, context) -> {
                context.client().execute(() -> {
                    handler.accept(payload, context);
                });
            });
        } else {

        }
    }

    public static void sendToPlayersTrackingEntityAndSelf(LivingEntity e, CustomPacketPayload payload) {
        for (ServerPlayer player : PlayerLookup.tracking(e)) {
            ServerPlayNetworking.send(player, payload);
        }
    }

    public static void sendToPlayersTrackingEntityAndSelf(Entity e, CustomPacketPayload payload) {
        for (ServerPlayer player : PlayerLookup.tracking(e)) {
            ServerPlayNetworking.send(player, payload);
        }
    }

    public static void sendToPlayersTrackingChunk(ServerLevel level, ChunkPos chunkPos, CustomPacketPayload payload) {
        for (ServerPlayer player : PlayerLookup.tracking(level, chunkPos)) {
            ServerPlayNetworking.send(player, payload);
        }
    }

    public static void sendToPlayersTrackingEntity(LivingEntity entity, CustomPacketPayload payload) {
        for (ServerPlayer player : PlayerLookup.tracking(entity)) {
            ServerPlayNetworking.send(player, payload);
        }
    }

    public static void sendToPlayer(ServerPlayer player, CustomPacketPayload payload) {
        ServerPlayNetworking.send(player, payload);
    }
}