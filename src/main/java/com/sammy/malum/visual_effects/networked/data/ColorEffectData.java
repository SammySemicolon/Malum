package com.sammy.malum.visual_effects.networked.data;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import com.sammy.malum.core.systems.recipe.SpiritIngredient;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import io.netty.buffer.*;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.*;
import team.lodestar.lodestone.systems.easing.*;
import team.lodestar.lodestone.systems.particle.data.color.*;

import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class ColorEffectData {

    public static final Codec<ColorEffectData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ColorParticleData.CODEC.listOf().fieldOf("colors").forGetter(data -> data.colors),
            MalumSpiritType.CODEC.listOf().fieldOf("spirits").forGetter(data -> data.spirits)
    ).apply(instance, ColorEffectData::new));

    public static final StreamCodec<ByteBuf, ColorEffectData> STREAM_CODEC = ByteBufCodecs.fromCodec(CODEC);

    private final List<ColorParticleData> colors;
    private final List<MalumSpiritType> spirits;
    public int colorCycleCounter;

    public static ColorEffectData fromSpiritIngredients(Collection<SpiritIngredient> malumSpiritTypes) {
        return new ColorEffectData(Collections.emptyList(), malumSpiritTypes.stream().map(SpiritIngredient::getSpiritType).collect(Collectors.toList()));
    }

    public static ColorEffectData fromColors(List<ColorParticleData> colors) {
        return new ColorEffectData(colors, Collections.emptyList());
    }

    public static ColorEffectData fromColor(ColorParticleData color) {
        return fromColors(List.of(color));
    }

    public ColorEffectData(List<ColorParticleData> colors, List<MalumSpiritType> spirits) {
        this.colors = colors.isEmpty() ? Collections.emptyList() : colors;
        this.spirits = spirits.isEmpty() ? Collections.emptyList() : spirits;
    }

    public ColorEffectData(ColorParticleData... colors) {
        this(List.of(colors), Collections.emptyList());
    }

    public ColorEffectData(MalumSpiritType... spirits) {
        this(Collections.emptyList(), List.of(spirits));
    }

    public boolean isSpiritBased() {
        return colors.isEmpty();
    }

    public ColorParticleData getColor() {
        if (!spirits.isEmpty()) {
            return getSpirit().createColorData().build();
        }
        if (colors.size() == 1) {
            return colors.getFirst();
        }
        return colors.get(colorCycleCounter++ % colors.size());
    }

    public MalumSpiritType getSpirit() {
        if (!colors.isEmpty()) {
            throw new IllegalArgumentException("Networked Particle Effect expected Spirit Color Data. Raw Color Data was passed instead, which is not supported.");
        }
        if (spirits.size() == 1) {
            return spirits.getFirst();
        }
        return spirits.get(colorCycleCounter++ % spirits.size());
    }
}