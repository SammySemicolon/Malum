package com.sammy.malum.common.capabilities;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;

public class ProgressionData {

    public static Codec<ProgressionData> CODEC = RecordCodecBuilder.create(obj -> obj.group(
            Codec.BOOL.fieldOf("obtainedEncyclopedia").forGetter(c -> c.obtainedEncyclopedia),
            Codec.BOOL.fieldOf("hasBeenRejected").forGetter(c -> c.hasBeenRejected)
    ).apply(obj, ProgressionData::new));

    public boolean obtainedEncyclopedia;
    public boolean hasBeenRejected;

    public ProgressionData() {
    }

    public ProgressionData(boolean obtainedEncyclopedia, boolean hasBeenRejected) {
        this.obtainedEncyclopedia = obtainedEncyclopedia;
        this.hasBeenRejected = hasBeenRejected;
    }
}