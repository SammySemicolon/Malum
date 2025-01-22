package com.sammy.malum.common.data_components;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import com.sammy.malum.core.systems.ritual.*;
import io.netty.buffer.*;
import net.minecraft.network.codec.*;

public record RitualDataComponent(MalumRitualType ritualType, MalumRitualTier ritualTier) {
    public static Codec<RitualDataComponent> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            MalumRitualType.CODEC.fieldOf("type").forGetter(RitualDataComponent::ritualType),
            MalumRitualTier.CODEC.fieldOf("tier").forGetter(RitualDataComponent::ritualTier)
    ).apply(instance, RitualDataComponent::new));

    public static StreamCodec<ByteBuf, RitualDataComponent> STREAM_CODEC = ByteBufCodecs.fromCodec(RitualDataComponent.CODEC);
}