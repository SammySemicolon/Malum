package com.sammy.malum.common.block.curiosities.spirit_crucible.artifice;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class ArtificeAttributeValue {

    public static Codec<ArtificeAttributeValue> CODEC = RecordCodecBuilder.create(obj -> obj.group(
            ArtificeAttributeType.CODEC.fieldOf("type").forGetter(v -> v.type),
            Codec.FLOAT.fieldOf("value").forGetter(v -> v.value),
            TuningModifier.CODEC.listOf().fieldOf("tuningModifiers").forGetter(v -> v.tuningModifiers)
    ).apply(obj, ArtificeAttributeValue::new));

    public final ArtificeAttributeType type;
    private float value;
    private List<TuningModifier> tuningModifiers;
    private float tuningMultiplierCache = 1;

    public ArtificeAttributeValue(ArtificeAttributeType type) {
        this(type, type.defaultValue);
    }

    public ArtificeAttributeValue(ArtificeAttributeType type, float value) {
        this(type, value, new ArrayList<>());
    }

    public ArtificeAttributeValue(ArtificeAttributeType type, float value, List<TuningModifier> tuningModifiers) {
        this.type = type;
        this.value = value;
        this.tuningModifiers = new ArrayList<>(tuningModifiers);
        updateMultiplierCache();
    }

    public float getValue(ArtificeAttributeData accelerationData) {
        float bonus = 1 + accelerationData.chainProcessingBonus;
        if (type.equals(ArtificeAttributeType.CHAIN_FOCUSING_CHANCE)) {
            bonus *= -1;
        }
        return value * bonus * tuningMultiplierCache;
    }

    public void applyModifier(ArtificeModifier modifier) {
        value += modifier.value();
    }

    public void applyModifier(TuningModifier modifier) {
        removeModifier(modifier.id());
        tuningModifiers.add(modifier);
        updateMultiplierCache();
    }

    public void updateMultiplierCache() {
        tuningMultiplierCache = (float) (1 + tuningModifiers.stream().mapToDouble(TuningModifier::value).sum());
    }

    public void copyFrom(ArtificeAttributeValue other) {
        if (!other.type.equals(type)) {
            throw new IllegalArgumentException();
        }
        this.value = other.value;
        this.tuningModifiers = other.tuningModifiers;
        updateMultiplierCache();
    }

    public void removeModifier(ResourceLocation id) {
        tuningModifiers.removeIf(m -> m.id().equals(id));
    }
}