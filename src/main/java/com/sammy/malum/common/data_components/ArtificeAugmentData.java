package com.sammy.malum.common.data_components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.sammy.malum.core.systems.artifice.ArtificeModifier;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.List;

public record ArtificeAugmentData(boolean isCoreAugment, List<ArtificeModifier> modifiers) {
    public static Codec<ArtificeAugmentData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.BOOL.optionalFieldOf("isCoreAugment", true).forGetter(ArtificeAugmentData::isCoreAugment),
            ArtificeModifier.CODEC.listOf().fieldOf("attributes").forGetter(ArtificeAugmentData::modifiers)
    ).apply(instance, ArtificeAugmentData::new));

    public static StreamCodec<ByteBuf, ArtificeAugmentData> STREAM_CODEC = ByteBufCodecs.fromCodec(ArtificeAugmentData.CODEC);
}