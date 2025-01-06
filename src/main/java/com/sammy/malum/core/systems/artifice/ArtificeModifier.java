package com.sammy.malum.core.systems.artifice;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record ArtificeModifier(ArtificeAttributeType attribute, float value) {
    public static Codec<ArtificeModifier> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ArtificeAttributeType.CODEC.fieldOf("attribute").forGetter(ArtificeModifier::attribute),
            Codec.FLOAT.fieldOf("value").forGetter(ArtificeModifier::value)
    ).apply(instance, ArtificeModifier::new));
}