package com.sammy.malum.common.data_components;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import com.sammy.malum.core.systems.artifice.*;
import com.sammy.malum.core.systems.etching.*;
import io.netty.buffer.*;
import net.minecraft.network.codec.*;

import java.util.*;

public record EtchingData(EtchingEffectType etchingEffectType) {
    public static Codec<EtchingData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            EtchingEffectType.CODEC.fieldOf("etchingEffectType").forGetter(EtchingData::etchingEffectType)
    ).apply(instance, EtchingData::new));

    public static StreamCodec<ByteBuf, EtchingData> STREAM_CODEC = ByteBufCodecs.fromCodec(EtchingData.CODEC);
}