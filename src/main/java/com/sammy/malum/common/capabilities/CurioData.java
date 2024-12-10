package com.sammy.malum.common.capabilities;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;

public class CurioData {

    public static Codec<CurioData> CODEC = RecordCodecBuilder.create(obj -> obj.group(
            Codec.INT.fieldOf("watcherNecklaceCooldown").forGetter(c -> c.watcherNecklaceCooldown),
            Codec.INT.fieldOf("hiddenBladeNecklaceCooldown").forGetter(c -> c.hiddenBladeNecklaceCooldown)
    ).apply(obj, CurioData::new));

    public static StreamCodec<FriendlyByteBuf, CurioData> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, p -> p.watcherNecklaceCooldown,
            ByteBufCodecs.INT, p -> p.hiddenBladeNecklaceCooldown,
            CurioData::new);

    public int watcherNecklaceCooldown;
    public int hiddenBladeNecklaceCooldown;

    public CurioData() {
    }

    public CurioData(int watcherNecklaceCooldown, int hiddenBladeNecklaceCooldown) {
        this.watcherNecklaceCooldown = hiddenBladeNecklaceCooldown;
        this.hiddenBladeNecklaceCooldown = hiddenBladeNecklaceCooldown;
    }
}