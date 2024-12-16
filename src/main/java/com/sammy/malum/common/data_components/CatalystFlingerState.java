package com.sammy.malum.common.data_components;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import io.netty.buffer.*;
import net.minecraft.network.codec.*;
import net.minecraft.util.*;

public record CatalystFlingerState(int timer, int state, int stashedState) {

    public CatalystFlingerState() {
        this(0, 0, 0);
    }

    public static Codec<CatalystFlingerState> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ExtraCodecs.NON_NEGATIVE_INT.fieldOf("timer").forGetter(CatalystFlingerState::timer),
            ExtraCodecs.NON_NEGATIVE_INT.fieldOf("state").forGetter(CatalystFlingerState::state),
            ExtraCodecs.NON_NEGATIVE_INT.fieldOf("stashed_state").forGetter(CatalystFlingerState::stashedState)
    ).apply(instance, CatalystFlingerState::new));

    public static StreamCodec<ByteBuf, CatalystFlingerState> STREAM_CODEC = ByteBufCodecs.fromCodec(CatalystFlingerState.CODEC);

}
