package com.sammy.malum.common.packets.particle.rite.generic;

import com.sammy.malum.common.packets.particle.base.color.ColorBasedBlockParticleEffectPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import team.lodestar.lodestone.LodestoneLib;
import team.lodestar.lodestone.helpers.ColorHelper;
import team.lodestar.lodestone.registry.common.particle.LodestoneParticleTypes;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.particle.SimpleParticleOptions;
import team.lodestar.lodestone.systems.particle.builder.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.color.ColorParticleData;
import team.lodestar.lodestone.systems.particle.data.spin.SpinParticleData;
import team.lodestar.lodestone.systems.particle.render_types.LodestoneWorldParticleRenderType;

import java.awt.*;


public class BlockSparkleParticlePacket extends ColorBasedBlockParticleEffectPacket {

    boolean addMist;
    public static CustomPacketPayload.Type<BlockSparkleParticlePacket> ID = new CustomPacketPayload.Type(LodestoneLib.lodestonePath("block_sparkle"));
    public static final StreamCodec<? super RegistryFriendlyByteBuf, BlockSparkleParticlePacket> STREAM_CODEC = CustomPacketPayload.codec(BlockSparkleParticlePacket::write, BlockSparkleParticlePacket::new);


    public BlockSparkleParticlePacket(Color col, BlockPos pos, boolean blightedMist) {
        super(col, pos);
        this.addMist = blightedMist;
    }

    public BlockSparkleParticlePacket(Color col, BlockPos pos) {
        this(col, pos, false);
    }

    public BlockSparkleParticlePacket(FriendlyByteBuf buf) {
        super(buf);
        this.addMist = buf.readBoolean();
    }

    @Override
    public <T extends CustomPacketPayload> void handle(T t, ClientPlayNetworking.Context context) {
        Level level = Minecraft.getInstance().level;
        var rand = level.random;
        for (int i = 0; i < 5; i++) {
            int spinDirection = (rand.nextBoolean() ? 1 : -1);
            int spinOffset = rand.nextInt(360);
            WorldParticleBuilder.create(LodestoneParticleTypes.TWINKLE_PARTICLE)
                    .setTransparencyData(GenericParticleData.create(0, 0.8f, 0).build())
                    .setSpinData(SpinParticleData.create(0.7f * spinDirection, 0).setCoefficient(1.25f).setSpinOffset(spinOffset).setEasing(Easing.CUBIC_IN).build())
                    .setScaleData(GenericParticleData.create(0.05f, 0.1f, 0).setCoefficient(0.8f).setEasing(Easing.QUINTIC_OUT, Easing.EXPO_IN_OUT).build())
                    .setColorData(ColorParticleData.create(ColorHelper.brighter(color, 2), color).build())
                    .setLifetime(20)
                    .enableNoClip()
                    .setRandomOffset(0.6f)
                    .setGravityStrength(1.1f)
                    .addMotion(0, 0.28f + rand.nextFloat() * 0.15f, 0)
                    .disableNoClip()
                    .setRandomMotion(0.1f, 0.15f)
                    .setDiscardFunction(SimpleParticleOptions.ParticleDiscardFunctionType.ENDING_CURVE_INVISIBLE)
                    .repeat(level, pos.getX() + 0.5f, pos.getY() + 0.2f, pos.getZ() + 0.5f, 1);
        }
        for (int i = 0; i < 2; i++) {
            int spinDirection = (rand.nextBoolean() ? 1 : -1);
            int spinOffset = rand.nextInt(360);
            WorldParticleBuilder.create(LodestoneParticleTypes.SMOKE_PARTICLE)
                    .setTransparencyData(GenericParticleData.create(0.05f, 0.08f, 0).setEasing(Easing.SINE_IN, Easing.CIRC_IN).build())
                    .setSpinData(SpinParticleData.create((0.125f + rand.nextFloat() * 0.075f) * spinDirection).setSpinOffset(spinOffset).build())
                    .setScaleData(GenericParticleData.create(0.35f, 0.5f, 0).setEasing(Easing.QUINTIC_OUT, Easing.SINE_IN).build())
                    .setColorData(ColorParticleData.create(color, color).build())
                    .setLifetime(50 + rand.nextInt(10))
                    .setRandomOffset(0.4f)
                    .enableNoClip()
                    .setRandomMotion(0.01f, 0.01f)
                    .setDiscardFunction(SimpleParticleOptions.ParticleDiscardFunctionType.ENDING_CURVE_INVISIBLE)
                    .repeatSurroundBlock(level, pos, 1);
        }
        if (addMist) {
            for (int i = 0; i < 3; i++) {
                float multiplier = Mth.nextFloat(rand, 0.4f, 1f);
                float timeMultiplier = Mth.nextFloat(rand, 0.9f, 1.4f);
                Color color = new Color((int) (31 * multiplier), (int) (19 * multiplier), (int) (31 * multiplier));
                boolean spinDirection = rand.nextBoolean();
                WorldParticleBuilder.create(LodestoneParticleTypes.WISP_PARTICLE)
                        .setTransparencyData(GenericParticleData.create(0.15f, 1f, 0).build())
                        .setSpinData(SpinParticleData.create(0.2f * (spinDirection ? 1 : -1)).build())
                        .setScaleData(GenericParticleData.create(0.15f, 0.2f, 0).setEasing(Easing.QUINTIC_OUT, Easing.SINE_IN).build())
                        .setLifetime((int) (45 * timeMultiplier))
                        .setColorData(ColorParticleData.create(color, color).build())
                        .enableNoClip()
                        .setRandomOffset(0.1f, 0f)
                        .setRandomMotion(0.005f, 0.01f)
                        .setRenderType(LodestoneWorldParticleRenderType.LUMITRANSPARENT)
                        .repeatSurroundBlock(level, pos, 2, Direction.UP);

                WorldParticleBuilder.create(LodestoneParticleTypes.SMOKE_PARTICLE)
                        .setTransparencyData(GenericParticleData.create(0.25f, 0.55f, 0).build())
                        .setLifetime((int) (50 * timeMultiplier))
                        .setSpinData(SpinParticleData.create(0.1f * (spinDirection ? 1 : -1)).build())
                        .setScaleData(GenericParticleData.create(0.35f, 0.4f, 0).setEasing(Easing.QUINTIC_OUT, Easing.SINE_IN).build())
                        .setColorData(ColorParticleData.create(color, color).build())
                        .setRandomOffset(0.2f, 0)
                        .enableNoClip()
                        .setRandomMotion(0.005f, 0.005f)
                        .setRenderType(LodestoneWorldParticleRenderType.LUMITRANSPARENT)
                        .repeatSurroundBlock(level, pos, 2, Direction.UP);

                color = new Color((int) (80 * multiplier), (int) (40 * multiplier), (int) (80 * multiplier));
                WorldParticleBuilder.create(LodestoneParticleTypes.SMOKE_PARTICLE)
                        .setTransparencyData(GenericParticleData.create(0.02f, 0.15f, 0).build())
                        .setSpinData(SpinParticleData.create(0.1f * (spinDirection ? 1 : -1)).build())
                        .setScaleData(GenericParticleData.create(0.35f, 0.4f, 0).setEasing(Easing.QUINTIC_OUT, Easing.SINE_IN).build())
                        .setColorData(ColorParticleData.create(color, color).build())
                        .setLifetime((int) (50 * timeMultiplier))
                        .setRandomOffset(0.2f, 0)
                        .enableNoClip()
                        .setRandomMotion(0.01f, 0.005f)
                        .repeatSurroundBlock(level, pos, 2, Direction.UP);
            }
        }
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        super.write(buf);
        buf.writeBoolean(addMist);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }
}