package com.sammy.malum.common.capabilities;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;

public class CurioData {

    public static Codec<CurioData> CODEC = RecordCodecBuilder.create(obj -> obj.group(
            Codec.INT.fieldOf("watcherNecklaceCooldown").forGetter(c -> c.watcherNecklaceCooldown),
            Codec.INT.fieldOf("hiddenBladeNecklaceCooldown").forGetter(c -> c.hiddenBladeNecklaceCooldown)
    ).apply(obj, CurioData::new));

    public int watcherNecklaceCooldown;
    public int hiddenBladeNecklaceCooldown;

    public CurioData() {
    }

    public CurioData(int watcherNecklaceCooldown, int hiddenBladeNecklaceCooldown) {
        this.watcherNecklaceCooldown = hiddenBladeNecklaceCooldown;
        this.hiddenBladeNecklaceCooldown = hiddenBladeNecklaceCooldown;
    }
}