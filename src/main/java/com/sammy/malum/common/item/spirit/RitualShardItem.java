package com.sammy.malum.common.item.spirit;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.sammy.malum.core.systems.ritual.*;
import com.sammy.malum.core.systems.spirit.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.item.DataComponentRegistry;
import com.sammy.malum.visual_effects.*;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.*;
import net.minecraft.network.chat.*;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import team.lodestar.lodestone.handlers.screenparticle.ParticleEmitterHandler.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.registry.common.particle.*;
import team.lodestar.lodestone.systems.easing.*;
import team.lodestar.lodestone.systems.particle.builder.*;
import team.lodestar.lodestone.systems.particle.data.*;
import team.lodestar.lodestone.systems.particle.data.spin.*;
import team.lodestar.lodestone.systems.particle.screen.*;

import java.util.*;

public class RitualShardItem extends Item implements ItemParticleSupplier {

    public static final String RITUAL_TYPE = "stored_ritual";
    public static final String STORED_SPIRITS = "stored_spirits";

    public RitualShardItem(Properties properties) {
        super(properties);
    }

//    @Override
//    public void readComponent(int stackQuantity, DataComponentMap.Builder mutableMap, ComponentGetter originalSupplier) {
//        originalSupplier.get(DataComponentRegistry.RITUAL_SHARD_PROPS).ifPresent(props -> {
//            if (originalSupplier.get(DataComponents.RARITY).isEmpty())
//                mutableMap.set(DataComponents.RARITY, getRitualType(props).spirit.getItemRarity());
//        });
//    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        final MalumRitualType ritualType = getRitualType(stack);
        final MalumRitualTier ritualTier = getRitualTier(stack);
        if (ritualType != null && ritualTier != null) {
            tooltipComponents.addAll(ritualType.makeRitualShardDescriptor(ritualTier));
        }
    }

    @Override
    public void spawnLateParticles(ScreenParticleHolder target, Level level, float partialTick, ItemStack stack, float x, float y) {
        final MalumRitualType ritualType = getRitualType(stack);
        final MalumRitualTier ritualTier = getRitualTier(stack);
        if (ritualType != null && ritualTier != null) {
            MalumSpiritType type = ritualType.spirit;
            ScreenParticleEffects.spawnSpiritShardScreenParticles(target, type);
            if (ritualTier.isGreaterThan(MalumRitualTier.DIM)) {
                float distance = 2f + ritualTier.potency;
                var rand = Minecraft.getInstance().level.getRandom();
                for (int i = 0; i < 2; i++) {
                    float time = (((i == 1 ? 3.14f : 0) + ((level.getGameTime() + partialTick) * 0.05f)) % 6.28f);
                    float scalar = 0.4f + 0.15f * ritualTier.potency;
                    if (time > 1.57f && time < 4.71f) {
                        scalar *= Easing.QUAD_IN.ease(Math.abs(3.14f - time) / 1.57f, 0, 1, 1);
                    }
                    double xOffset = Math.sin(time) * distance;
                    double yOffset = Math.cos(time) * distance * 0.5f;
                    ScreenParticleBuilder.create(LodestoneScreenParticleTypes.WISP, target)
                            .setTransparencyData(GenericParticleData.create(0.2f, 0f).setEasing(Easing.SINE_IN_OUT).build())
                            .setSpinData(SpinParticleData.create(RandomHelper.randomBetween(rand, 0.2f, 0.4f)).setEasing(Easing.EXPO_OUT).build())
                            .setScaleData(GenericParticleData.create(RandomHelper.randomBetween(rand, 0.2f, 0.3f)*scalar, 0).setEasing(Easing.EXPO_OUT).build())
                            .setColorData(type.createColorData().build())
                            .setLifetime(RandomHelper.randomBetween(rand, 80, 120))
                            .setRandomOffset(0.1f)
                            .spawnOnStack(xOffset, yOffset);
                    if (!ritualTier.isGreaterThan(MalumRitualTier.BRIGHT)) {
                        break;
                    }
                }
            }
        }
    }

    public static MalumRitualType getRitualType(Props props) {
        return RitualRegistry.get(ResourceLocation.parse(props.ritualType()));
    }

    public static MalumRitualType getRitualType(ItemStack stack) {
        try { return RitualRegistry.get(ResourceLocation.parse(stack.get(DataComponentRegistry.RITUAL_SHARD_PROPS).ritualType())); }
        catch (NullPointerException noComponent) { return null; }
    }

    public static MalumRitualTier getRitualTier(ItemStack stack) {
        try { return MalumRitualTier.figureOutTier(stack.get(DataComponentRegistry.RITUAL_SHARD_PROPS).storedSpirits()); }
        catch (NullPointerException noComponent) { return null; }
    }

    public record Props(String ritualType, int storedSpirits) {
        public static Codec<Props> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Codec.STRING.fieldOf(RITUAL_TYPE).forGetter(Props::ritualType),
                Codec.INT.fieldOf(STORED_SPIRITS).forGetter(Props::storedSpirits)
        ).apply(instance, Props::new));

        public static StreamCodec<ByteBuf, Props> STREAM_CODEC = ByteBufCodecs.fromCodec(RitualShardItem.Props.CODEC);
    }
}
