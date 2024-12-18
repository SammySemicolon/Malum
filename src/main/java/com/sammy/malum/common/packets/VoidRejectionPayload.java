package com.sammy.malum.common.packets;

import com.sammy.malum.core.handlers.*;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.*;
import team.lodestar.lodestone.LodestoneLib;
import team.lodestar.lodestone.systems.network.LodestonePayload;

public class VoidRejectionPayload implements CustomPacketPayload, LodestonePayload {
    private final int entityId;

    public static CustomPacketPayload.Type<VoidRejectionPayload> ID = new CustomPacketPayload.Type(LodestoneLib.lodestonePath("void_rejection"));
    public static final StreamCodec<? super RegistryFriendlyByteBuf, VoidRejectionPayload> STREAM_CODEC = CustomPacketPayload.codec(VoidRejectionPayload::write, VoidRejectionPayload::new);


    public VoidRejectionPayload(int entityId) {
        this.entityId = entityId;
    }

    public VoidRejectionPayload(FriendlyByteBuf byteBuf) {
        this.entityId = byteBuf.readInt();
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeInt(entityId);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }

    @Override
    public <T extends CustomPacketPayload> void handle(T t, ClientPlayNetworking.Context context) {
        Entity entity = Minecraft.getInstance().level.getEntity(entityId);
        if (entity instanceof Player player) {
            TouchOfDarknessHandler.launchPlayer(player);
        }
    }
}