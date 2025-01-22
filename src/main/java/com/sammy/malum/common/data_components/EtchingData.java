package com.sammy.malum.common.data_components;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import com.sammy.malum.core.systems.etching.*;
import io.netty.buffer.*;
import net.minecraft.network.codec.*;

public record EtchingData(GeasEffectType geasEffectType, GeasEffect effectInstance) {

    public EtchingData(GeasEffectType geasEffectType) {
        this(geasEffectType, geasEffectType.createEffect());
    }
    public static Codec<EtchingData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            GeasEffectType.CODEC.fieldOf("etchingEffectType").forGetter(EtchingData::geasEffectType)
    ).apply(instance, EtchingData::new));

    public static StreamCodec<ByteBuf, EtchingData> STREAM_CODEC = ByteBufCodecs.fromCodec(EtchingData.CODEC);
}