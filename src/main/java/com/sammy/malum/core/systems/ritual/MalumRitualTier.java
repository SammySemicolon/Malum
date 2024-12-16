package com.sammy.malum.core.systems.ritual;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.sammy.malum.common.block.curiosities.ritual_plinth.*;
import net.minecraft.resources.*;

import java.util.*;

import static com.sammy.malum.MalumMod.malumPath;

public class MalumRitualTier {

    public static final List<MalumRitualTier> TIERS = new ArrayList<>();

    public static final MalumRitualTier FADED = create(new MalumRitualTier(malumPath("faded"), 64, 1));
    public static final MalumRitualTier DIM = create(new MalumRitualTier(malumPath("dim"), 256, 2));
    public static final MalumRitualTier VAGUE = create(new MalumRitualTier(malumPath("vague"), 1024, 3));
    public static final MalumRitualTier BRIGHT = create(new MalumRitualTier(malumPath("bright"), 4096, 4));
    public static final MalumRitualTier VIVID = create(new MalumRitualTier(malumPath("vivid"), 16384, 5));
    public static final MalumRitualTier RADIANT = create(new MalumRitualTier(malumPath("radiant"), 65536, 6));

    public static final Codec<MalumRitualTier> CODEC = ResourceLocation.CODEC.flatXmap(
            id -> TIERS.stream()
                    .filter(tier -> tier.identifier.equals(id))
                    .findFirst()
                    .map(DataResult::success)
                    .orElseThrow(),
            tier -> DataResult.success(tier.identifier)
    );

    public final ResourceLocation identifier;
    public final int spiritThreshold;
    public final int potency;

    public MalumRitualTier(ResourceLocation identifier, int spiritThreshold, int potency) {
        this.identifier = identifier;
        this.spiritThreshold = spiritThreshold;
        this.potency = potency;
    }

    public boolean isGreaterThan(MalumRitualTier ritualTier) {
        return potency > ritualTier.potency;
    }

    public String translationIdentifier() {
        return identifier.getNamespace() + ".gui.ritual.tier." + identifier.getPath();
    }

    public ResourceLocation getDecorTexture() {
        return identifier.withPrefix("textures/vfx/ritual/decor_").withSuffix(".png");
    }

    public static MalumRitualTier create(MalumRitualTier tier) {
        TIERS.add(tier);
        return tier;
    }

    public static MalumRitualTier figureOutTier(RitualPlinthBlockEntity plinthBlockEntity) {
        return figureOutTier(plinthBlockEntity.spiritAmount);
    }

    public static MalumRitualTier figureOutTier(int spiritAmount) {
        var reachedThresholds = TIERS.stream().filter(t -> (spiritAmount) >= t.spiritThreshold).toList();
        return reachedThresholds.isEmpty() ? null : reachedThresholds.getLast();
    }
}
