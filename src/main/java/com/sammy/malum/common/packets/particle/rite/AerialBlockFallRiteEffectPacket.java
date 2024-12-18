package com.sammy.malum.common.packets.particle.rite;

import com.sammy.malum.common.packets.particle.base.color.ColorBasedBlockParticleEffectPacket;
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
import team.lodestar.lodestone.helpers.ColorHelper;
import team.lodestar.lodestone.registry.common.particle.*;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.particle.builder.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.color.ColorParticleData;
import team.lodestar.lodestone.systems.particle.data.spin.SpinParticleData;

import java.awt.*;


public class AerialBlockFallRiteEffectPacket extends ColorBasedBlockParticleEffectPacket {

    public static CustomPacketPayload.Type<AerialBlockFallRiteEffectPacket> ID = new CustomPacketPayload.Type(LodestoneLib.lodestonePath("areal_block_fall"));
    public static final StreamCodec<? super RegistryFriendlyByteBuf, AerialBlockFallRiteEffectPacket> STREAM_CODEC = CustomPacketPayload.codec(AerialBlockFallRiteEffectPacket::write, AerialBlockFallRiteEffectPacket::new);


    public AerialBlockFallRiteEffectPacket(Color col, BlockPos pos) {
        super(col, pos);
    }

    public AerialBlockFallRiteEffectPacket(FriendlyByteBuf buf) {
        super(buf);
    }

    @Override
    public <T extends CustomPacketPayload> void handle(T t, ClientPlayNetworking.Context context) {
        Level level = Minecraft.getInstance().level;
        RandomSource rand = level.random;
        for (int i = 0; i <= 3; i++) {
            int spinDirection = (rand.nextBoolean() ? 1 : -1);
            int spinOffset = rand.nextInt(360);
            WorldParticleBuilder.create(LodestoneParticleTypes.TWINKLE_PARTICLE)
                    .setTransparencyData(GenericParticleData.create(0, 0.8f, 0).build())
                    .setSpinData(SpinParticleData.create(0, 0.8f * spinDirection, 0.1f * spinDirection).setCoefficient(2f).setSpinOffset(spinOffset).setEasing(Easing.CUBIC_IN, Easing.QUINTIC_OUT).build())
                    .setScaleData(GenericParticleData.create(0.05f, 0.1f, 0).setCoefficient(0.8f).setEasing(Easing.QUINTIC_OUT, Easing.SINE_IN).build())
                    .setColorData(ColorParticleData.create(ColorHelper.brighter(color, 2), color).build())
                    .setLifetime(25)
                    .enableNoClip()
                    .setRandomOffset(0.6f)
                    .setGravityStrength(0.3f)
                    .disableNoClip()
                    .setRandomMotion(0.1f, 0.15f)
                    .spawn(level, pos.getX() + 0.5f, pos.getY() + 0.2f, pos.getZ() + 0.5f);
        }


        for (int i = 0; i < 2; i++) {
            int spinDirection = (rand.nextBoolean() ? 1 : -1);
            int spinOffset = rand.nextInt(360);
            WorldParticleBuilder.create(LodestoneParticleTypes.SMOKE_PARTICLE)
                    .setTransparencyData(GenericParticleData.create(0.05f, 0.08f, 0).setCoefficient(0.8f + rand.nextFloat() * 0.4f).setEasing(Easing.SINE_IN, Easing.CIRC_IN).build())
                    .setSpinData(SpinParticleData.create((0.1f + rand.nextFloat() * 0.05f) * spinDirection).setSpinOffset(spinOffset).build()).setScaleData(GenericParticleData.create(0.35f, 0.5f, 0).setCoefficient(0.8f + rand.nextFloat() * 0.4f).setEasing(Easing.QUINTIC_OUT, Easing.SINE_IN).build())
                    .setColorData(ColorParticleData.create(color, color).build())
                    .setScaleData(GenericParticleData.create(0.35f, 0.5f, 0.25f).setCoefficient(0.8f + rand.nextFloat() * 0.4f).setEasing(Easing.QUINTIC_OUT, Easing.CIRC_IN).build())
                    .setLifetime(50 + rand.nextInt(10))
                    .setRandomOffset(0.4f)
                    .enableNoClip()
                    .addMotion(0, -0.02f, 0)
                    .setRandomMotion(0.01f, 0.01f)
                    .repeatSurroundBlock(level, pos, 2);
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }
}