package com.sammy.malum.common.packets.malignant_conversion;

import com.sammy.malum.common.capability.*;
import com.sammy.malum.core.handlers.*;
import net.minecraft.client.*;
import net.minecraft.client.player.*;
import net.minecraft.core.registries.*;
import net.minecraft.network.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.network.*;
import net.minecraftforge.network.simple.*;
import team.lodestar.lodestone.systems.network.*;

import java.util.*;
import java.util.function.*;

public class SyncMalignantConversionPacket extends LodestoneClientPacket {

    private final Attribute attribute;
    private final UUID uuid;

    public SyncMalignantConversionPacket(Attribute attribute, UUID uuid) {
        this.attribute = attribute;
        this.uuid = uuid;
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeId(BuiltInRegistries.ATTRIBUTE, attribute);
        buf.writeUUID(uuid);
    }

    @OnlyIn(Dist.CLIENT)
    public void execute(Supplier<NetworkEvent.Context> context) {
        final LocalPlayer player = Minecraft.getInstance().player;
        MalignantConversionHandler.syncPositiveUUIDS(player, attribute, uuid);
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, SyncMalignantConversionPacket.class, SyncMalignantConversionPacket::encode, SyncMalignantConversionPacket::decode, SyncMalignantConversionPacket::handle);
    }

    public static SyncMalignantConversionPacket decode(FriendlyByteBuf buf) {
        return new SyncMalignantConversionPacket(buf.readById(BuiltInRegistries.ATTRIBUTE), buf.readUUID());
    }
}