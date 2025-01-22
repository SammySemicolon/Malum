package com.sammy.malum.common.data_components;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import com.sammy.malum.core.systems.geas.*;
import io.netty.buffer.*;
import net.minecraft.network.codec.*;

public record GeasDataComponent(GeasEffectType geasEffectType, GeasEffect effectInstance) {

    public GeasDataComponent(GeasEffectType geasEffectType) {
        this(geasEffectType, geasEffectType.createEffect());
    }
    public static Codec<GeasDataComponent> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            GeasEffectType.CODEC.fieldOf("geasEffectType").forGetter(GeasDataComponent::geasEffectType)
    ).apply(instance, GeasDataComponent::new));

    public static StreamCodec<ByteBuf, GeasDataComponent> STREAM_CODEC = ByteBufCodecs.fromCodec(GeasDataComponent.CODEC);
}