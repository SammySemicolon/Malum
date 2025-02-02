package com.sammy.malum.common.data_components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.sammy.malum.core.systems.artifice.ArtificeModifier;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.List;

public record ArtificeAugmentDataComponent(boolean isCoreAugment, List<ArtificeModifier> modifiers) {
    public static Codec<ArtificeAugmentDataComponent> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.BOOL.optionalFieldOf("isCoreAugment", true).forGetter(ArtificeAugmentDataComponent::isCoreAugment),
            ArtificeModifier.CODEC.listOf().fieldOf("attributes").forGetter(ArtificeAugmentDataComponent::modifiers)
    ).apply(instance, ArtificeAugmentDataComponent::new));

    public static StreamCodec<ByteBuf, ArtificeAugmentDataComponent> STREAM_CODEC = ByteBufCodecs.fromCodec(ArtificeAugmentDataComponent.CODEC);
}