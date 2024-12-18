package com.sammy.malum.common.packets.particle.rite;

import com.sammy.malum.common.packets.particle.rite.generic.BlockSparkleParticlePacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.level.Level;
import team.lodestar.lodestone.LodestoneLib;

import java.awt.*;

public class InfernalExtinguishRiteEffectPacket extends BlockSparkleParticlePacket {

    public static CustomPacketPayload.Type<InfernalExtinguishRiteEffectPacket> ID = new CustomPacketPayload.Type(LodestoneLib.lodestonePath("infernal_extinguish"));
    public static final StreamCodec<? super RegistryFriendlyByteBuf, InfernalExtinguishRiteEffectPacket> STREAM_CODEC = CustomPacketPayload.codec(InfernalExtinguishRiteEffectPacket::write, InfernalExtinguishRiteEffectPacket::new);

    public InfernalExtinguishRiteEffectPacket(Color col, BlockPos pos) {
        super(col, pos, false);
    }

    public InfernalExtinguishRiteEffectPacket(FriendlyByteBuf buf) {
        super(buf);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }

    @Override
    public <T extends CustomPacketPayload> void handle(T t, ClientPlayNetworking.Context context) {
        Level level = Minecraft.getInstance().level;
        var rand = level.random;

        for (int i = 0; i < 8; ++i) {
            double d0 = pos.getX() + rand.nextFloat();
            double d1 = pos.getY() + rand.nextFloat();
            double d2 = pos.getZ() + rand.nextFloat();
            level.addParticle(ParticleTypes.LARGE_SMOKE, d0, d1, d2, 0.0D, 0.0D, 0.0D);
        }
        super.handle(t, context);
    }
}