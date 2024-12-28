package com.sammy.malum.common.block.curiosities.spirit_crucible.artifice;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.sammy.malum.common.data_components.ArtificeAugmentData;

public record ArtificeModifier(ArtificeAttributeType attribute, float value) {
    public static Codec<ArtificeModifier> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ArtificeAttributeType.CODEC.fieldOf("attribute").forGetter(ArtificeModifier::attribute),
            Codec.FLOAT.fieldOf("value").forGetter(ArtificeModifier::value)
    ).apply(instance, ArtificeModifier::new));
}