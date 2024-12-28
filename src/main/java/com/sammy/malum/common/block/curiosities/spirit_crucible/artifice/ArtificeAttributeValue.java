package com.sammy.malum.common.block.curiosities.spirit_crucible.artifice;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class ArtificeAttributeValue {

    public static Codec<ArtificeAttributeValue> CODEC = RecordCodecBuilder.create(obj -> obj.group(
            ArtificeAttributeType.CODEC.fieldOf("type").forGetter(v -> v.type),
            Codec.FLOAT.fieldOf("value").forGetter(v -> v.value),
            Codec.FLOAT.fieldOf("tuningMultiplier").forGetter(v -> v.tuningMultiplier)
    ).apply(obj, ArtificeAttributeValue::new));

    public final ArtificeAttributeType type;
    private float value;
    private float tuningMultiplier;

    public ArtificeAttributeValue(ArtificeAttributeType type) {
        this(type, type.defaultValue, 1f);
    }
    public ArtificeAttributeValue(ArtificeAttributeType type, float value, float tuningMultiplier) {
        this.type = type;
        this.value = value;
        this.tuningMultiplier = tuningMultiplier;
    }

    public void copyFrom(ArtificeAttributeValue other) {
        if (!other.type.equals(type)) {
            throw new IllegalArgumentException();
        }
        this.value = other.value;
        this.tuningMultiplier = other.tuningMultiplier;
    }

    public void addValue(float bonus) {
        value += bonus;
    }

    protected void tune(AppliedTuningType tuningType) {
        addTuningMultiplier(tuningType.getMultiplier(type));
    }

    protected void addTuningMultiplier(float multiplier) {
        this.tuningMultiplier = multiplier;
    }

    public float getValue(ArtificeAttributeData accelerationData) {
        float bonus = 1 + accelerationData.chainProcessingBonus;
        if (type.equals(ArtificeAttributeType.CHAIN_FOCUSING_CHANCE)) {
            bonus *= -1;
        }
        return value * bonus * tuningMultiplier;
    }
}