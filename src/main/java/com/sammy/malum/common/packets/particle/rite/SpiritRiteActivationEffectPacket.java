package com.sammy.malum.common.packets.particle.rite;

import com.sammy.malum.common.packets.particle.base.spirit.SpiritBasedBlockParticleEffectPacket;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import team.lodestar.lodestone.registry.common.particle.*;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.particle.builder.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.color.ColorParticleData;
import team.lodestar.lodestone.systems.particle.data.spin.SpinParticleData;

import java.awt.*;
import java.util.List;

public class SpiritRiteActivationEffectPacket extends SpiritBasedBlockParticleEffectPacket {

    private int height = 0;

    public SpiritRiteActivationEffectPacket(List<String> spirits, BlockPos pos) {
        super(spirits, pos);
    }

    public SpiritRiteActivationEffectPacket(FriendlyByteBuf buf) {
        super(buf);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    protected void handle(IPayloadContext iPayloadContext, MalumSpiritType spiritType) {
        Level level = Minecraft.getInstance().level;
        Color color = spiritType.getPrimaryColor();
        WorldParticleBuilder.create(LodestoneParticleTypes.WISP_PARTICLE)
                .setTransparencyData(GenericParticleData.create(0.1f, 0.22f, 0).setCoefficient(0.8f).setEasing(Easing.QUINTIC_OUT, Easing.SINE_IN_OUT).build())
                .setSpinData(SpinParticleData.create(0.2f).build())
                .setScaleData(GenericParticleData.create(0.15f, 0.2f, 0.1f).setCoefficient(0.7f).setEasing(Easing.QUINTIC_OUT, Easing.SINE_IN_OUT).build())
                .setColorData(ColorParticleData.create(color, color).build())
                .setLifetime(25)
                .enableNoClip()
                .setRandomOffset(0.1f, 0.1f)
                .setRandomMotion(0.001f, 0.001f)
                .repeatSurroundBlock(level, pos.above(height), 3, Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST);

        WorldParticleBuilder.create(LodestoneParticleTypes.SMOKE_PARTICLE)
                .setTransparencyData(GenericParticleData.create(0.06f, 0.14f, 0).setCoefficient(0.8f).setEasing(Easing.QUINTIC_OUT, Easing.SINE_IN_OUT).build())
                .setSpinData(SpinParticleData.create(0.1f).build())
                .setScaleData(GenericParticleData.create(0.35f, 0.4f, 0.1f).setCoefficient(0.7f).setEasing(Easing.QUINTIC_OUT, Easing.SINE_IN_OUT).build())
                .setColorData(ColorParticleData.create(color, color).build())
                .setLifetime(30)
                .setRandomOffset(0.2f)
                .enableNoClip()
                .setRandomMotion(0.001f, 0.001f)
                .repeatSurroundBlock(level, pos.above(height), 5, Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST);
        height++;
    }
}
