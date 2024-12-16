package com.sammy.malum.common.data_components;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import com.sammy.malum.core.systems.ritual.*;
import io.netty.buffer.*;
import net.minecraft.network.codec.*;

public record RitualData(MalumRitualType ritualType, MalumRitualTier ritualTier) {
    public static Codec<RitualData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            MalumRitualType.CODEC.fieldOf("type").forGetter(RitualData::ritualType),
            MalumRitualTier.CODEC.fieldOf("tier").forGetter(RitualData::ritualTier)
    ).apply(instance, RitualData::new));

    public static StreamCodec<ByteBuf, RitualData> STREAM_CODEC = ByteBufCodecs.fromCodec(RitualData.CODEC);
}