package com.sammy.malum.common.data_components;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import com.sammy.malum.core.systems.geas.*;
import io.netty.buffer.*;
import net.minecraft.network.codec.*;

import java.util.*;

public record GeasDataComponent(GeasEffectType geasEffectType) {

    public static Codec<GeasDataComponent> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            GeasEffectType.CODEC.lenientOptionalFieldOf("geasEffectType").forGetter(g -> Optional.ofNullable(g.geasEffectType))
    ).apply(instance, o -> new GeasDataComponent(o.orElse(null))));

    public static StreamCodec<ByteBuf, GeasDataComponent> STREAM_CODEC = ByteBufCodecs.fromCodec(GeasDataComponent.CODEC);

    public boolean isInvalid() {
        return geasEffectType == null;
    }

    public GeasEffect createEffectInstance() {
        return geasEffectType.createEffect();
    }
}