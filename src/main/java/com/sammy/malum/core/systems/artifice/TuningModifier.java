package com.sammy.malum.core.systems.artifice;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.sammy.malum.MalumMod;
import net.minecraft.resources.ResourceLocation;

public record TuningModifier(ResourceLocation id, float value) {

    public static final ResourceLocation TUNING_FORK = MalumMod.malumPath("tuning_fork");
    public static final ResourceLocation WEAKEST_BOOST = MalumMod.malumPath("weakest_boost");
    public static final ResourceLocation CAUSTIC_SYNERGY = MalumMod.malumPath("caustic_synergy");
    public static final ResourceLocation RESONANCE_TUNING = MalumMod.malumPath("resonance_tuning");
    public static Codec<TuningModifier> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceLocation.CODEC.fieldOf("id").forGetter(TuningModifier::id),
            Codec.FLOAT.fieldOf("value").forGetter(TuningModifier::value)
    ).apply(instance, TuningModifier::new));
}
