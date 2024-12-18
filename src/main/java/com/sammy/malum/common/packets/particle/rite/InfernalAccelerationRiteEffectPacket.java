package com.sammy.malum.common.packets.particle.rite;

import com.sammy.malum.common.packets.particle.base.spirit.SpiritBasedBlockParticleEffectPacket;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import team.lodestar.lodestone.LodestoneLib;
import team.lodestar.lodestone.registry.common.particle.*;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.particle.SimpleParticleOptions;
import team.lodestar.lodestone.systems.particle.builder.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.color.ColorParticleData;
import team.lodestar.lodestone.systems.particle.data.spin.SpinParticleData;

import java.awt.*;
import java.util.List;

public class InfernalAccelerationRiteEffectPacket extends SpiritBasedBlockParticleEffectPacket {

    public static CustomPacketPayload.Type<InfernalAccelerationRiteEffectPacket> ID = new CustomPacketPayload.Type(LodestoneLib.lodestonePath("infernal_acceleration"));
    public static final StreamCodec<? super RegistryFriendlyByteBuf, InfernalAccelerationRiteEffectPacket> STREAM_CODEC = CustomPacketPayload.codec(InfernalAccelerationRiteEffectPacket::write, InfernalAccelerationRiteEffectPacket::new);


    public InfernalAccelerationRiteEffectPacket(List<String> spirits, BlockPos pos) {
        super(spirits, pos);
    }

    public InfernalAccelerationRiteEffectPacket(FriendlyByteBuf buf) {
        super(buf);
    }

    @Override
    protected void handle(ClientPlayNetworking.Context context, MalumSpiritType spiritType) {
        Level level = Minecraft.getInstance().level;
        RandomSource rand = level.random;
        Color color = spiritType.getPrimaryColor();
        Color endColor = spiritType.getSecondaryColor();
        for (int i = 0; i < 3; i++) {
            int spinDirection = (rand.nextBoolean() ? 1 : -1);
            int spinOffset = rand.nextInt(360);
            WorldParticleBuilder.create(LodestoneParticleTypes.SMOKE_PARTICLE)
                    .setTransparencyData(GenericParticleData.create(0.02f, 0.095f, 0).setEasing(Easing.SINE_IN, Easing.CIRC_IN).build())
                    .setSpinData(SpinParticleData.create((0.125f + rand.nextFloat() * 0.075f) * spinDirection).setSpinOffset(spinOffset).build())
                    .setScaleData(GenericParticleData.create(0.25f, 0.45f, 0).setEasing(Easing.QUINTIC_OUT, Easing.SINE_IN).build())
                    .setColorData(ColorParticleData.create(color, endColor).build())
                    .setLifetime(50 + rand.nextInt(10))
                    .setRandomOffset(0.4f)
                    .enableNoClip()
                    .setRandomMotion(0.01f, 0.01f)
                    .setDiscardFunction(SimpleParticleOptions.ParticleDiscardFunctionType.ENDING_CURVE_INVISIBLE)
                    .repeatSurroundBlock(level, pos, 1);
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }
}