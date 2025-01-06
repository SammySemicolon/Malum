package com.sammy.malum.common.block.curiosities.spirit_crucible.artifice;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.sammy.malum.MalumMod;
import net.minecraft.resources.ResourceLocation;

public record TuningModifier(ResourceLocation id, float value) {

    public static final ResourceLocation TUNING_FORK = MalumMod.malumPath("tuning_fork");
    public static final ResourceLocation WEAKEST_BOOST = MalumMod.malumPath("tuning_fork");
    public static Codec<TuningModifier> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceLocation.CODEC.fieldOf("id").forGetter(TuningModifier::id),
            Codec.FLOAT.fieldOf("value").forGetter(TuningModifier::value)
    ).apply(instance, TuningModifier::new));

    public TuningModifier(ArtificeAttributeType type, AppliedTuningType tuningType) {
        this(TUNING_FORK, tuningType.getMultiplier(type));
    }
}
