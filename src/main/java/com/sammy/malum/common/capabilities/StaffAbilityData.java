package com.sammy.malum.common.capabilities;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class StaffAbilityData {

    public static Codec<StaffAbilityData> CODEC = RecordCodecBuilder.create(obj -> obj.group(
            Codec.INT.fieldOf("reserveChargeCount").forGetter(c -> c.reserveChargeCount),
            Codec.FLOAT.fieldOf("reserveChargeProgress").forGetter(c -> c.reserveChargeProgress)
    ).apply(obj, StaffAbilityData::new));

    public int reserveChargeCount;
    public float reserveChargeProgress;

    public StaffAbilityData() {
    }

    public StaffAbilityData(int reserveChargeCount, float reserveChargeProgress) {
        this.reserveChargeCount = reserveChargeCount;
        this.reserveChargeProgress = reserveChargeProgress;
    }
}