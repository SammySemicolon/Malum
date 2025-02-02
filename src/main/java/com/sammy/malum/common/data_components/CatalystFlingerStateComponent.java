package com.sammy.malum.common.data_components;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import io.netty.buffer.*;
import net.minecraft.network.codec.*;
import net.minecraft.util.*;

public record CatalystFlingerStateComponent(int timer, int state, int stashedState) {

    public CatalystFlingerStateComponent() {
        this(0, 0, 0);
    }

    public static Codec<CatalystFlingerStateComponent> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ExtraCodecs.NON_NEGATIVE_INT.fieldOf("timer").forGetter(CatalystFlingerStateComponent::timer),
            ExtraCodecs.NON_NEGATIVE_INT.fieldOf("state").forGetter(CatalystFlingerStateComponent::state),
            ExtraCodecs.NON_NEGATIVE_INT.fieldOf("stashed_state").forGetter(CatalystFlingerStateComponent::stashedState)
    ).apply(instance, CatalystFlingerStateComponent::new));

    public static StreamCodec<ByteBuf, CatalystFlingerStateComponent> STREAM_CODEC = ByteBufCodecs.fromCodec(CatalystFlingerStateComponent.CODEC);

}
