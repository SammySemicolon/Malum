package com.sammy.malum.common.packets.particle.curiosities.rite;

import com.sammy.malum.common.packets.particle.base.color.ColorBasedBlockParticleEffectPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;
import team.lodestar.lodestone.helpers.ColorHelper;
import team.lodestar.lodestone.registry.common.particle.*;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.particle.builder.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.color.ColorParticleData;
import team.lodestar.lodestone.systems.particle.data.spin.SpinParticleData;

import java.awt.*;
import java.util.function.Supplier;

public class AerialBlockFallRiteEffectPacket extends ColorBasedBlockParticleEffectPacket {
    public AerialBlockFallRiteEffectPacket(Color color, BlockPos pos) {
        super(color, pos);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void execute(Supplier<NetworkEvent.Context> context) {
        Level level = Minecraft.getInstance().level;
        var rand = level.random;
        for (int i = 0; i <= 3; i++) {
            int spinDirection = (rand.nextBoolean() ? 1 : -1);
            int spinOffset = rand.nextInt(360);
            WorldParticleBuilder.create(LodestoneParticleRegistry.TWINKLE_PARTICLE)
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
            WorldParticleBuilder.create(LodestoneParticleRegistry.SMOKE_PARTICLE)
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

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, AerialBlockFallRiteEffectPacket.class, AerialBlockFallRiteEffectPacket::encode, AerialBlockFallRiteEffectPacket::decode, AerialBlockFallRiteEffectPacket::handle);
    }

    public static AerialBlockFallRiteEffectPacket decode(FriendlyByteBuf buf) {
        return decode(AerialBlockFallRiteEffectPacket::new, buf);
    }
}