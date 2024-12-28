package com.sammy.malum.common.block.curiosities.spirit_crucible.artifice;

import com.mojang.serialization.Codec;
import com.sammy.malum.MalumMod;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;

public class ArtificeAttributeType {

    public static final ArrayList<ArtificeAttributeType> CRUCIBLE_ATTRIBUTES = new ArrayList<>();

    public static final Codec<ArtificeAttributeType> CODEC = ResourceLocation.CODEC.xmap(ArtificeAttributeType::getAttribute, f -> f.id);

    public static final ArtificeAttributeType FOCUSING_SPEED = new ArtificeAttributeType(
            MalumMod.malumPath("focusing_speed"), d -> d.focusingSpeed, TuningBehavior.STANDARD, 1);
    public static final ArtificeAttributeType FUEL_USAGE_RATE = new ArtificeAttributeType(
            MalumMod.malumPath("fuel_usage_rate"), d -> d.fuelUsageRate, TuningBehavior.INVERSE, 1);
    public static final ArtificeAttributeType INSTABILITY = new ArtificeAttributeType(
            MalumMod.malumPath("instability"), d -> d.instability, TuningBehavior.INVERSE, 0);
    public static final ArtificeAttributeType FORTUNE_CHANCE = new ArtificeAttributeType(
            MalumMod.malumPath("fortune_chance"), d -> d.fortuneChance, TuningBehavior.STANDARD, 0);
    public static final ArtificeAttributeType CHAIN_FOCUSING_CHANCE = new ArtificeAttributeType(
            MalumMod.malumPath("chain_focusing_chance"), d -> d.chainFocusingChance, TuningBehavior.STANDARD, 0);
    public static final ArtificeAttributeType DAMAGE_ABSORPTION_CHANCE = new ArtificeAttributeType(
            MalumMod.malumPath("damage_absorption_chance"), d -> d.damageAbsorptionChance, TuningBehavior.STANDARD, 0);
    public static final ArtificeAttributeType RESTORATION_CHANCE = new ArtificeAttributeType(
            MalumMod.malumPath("restoration_chance"), d -> d.restorationChance, TuningBehavior.STANDARD, 0);
    public static final ArtificeAttributeType WEAKEST_BOOST = new ArtificeAttributeType(
            MalumMod.malumPath("weakest_boost"), d -> d.weakestBoost, TuningBehavior.STANDARD, 0, false);
    public static final ArtificeAttributeType TUNING_POTENCY = new ArtificeAttributeType(
            MalumMod.malumPath("tuning_potency"), d -> d.tuningPotency, TuningBehavior.STANDARD, 0, false);

    public final ResourceLocation id;
    public final Function<ArtificeAttributeData, ArtificeAttributeValue> valueGetter;
    public final TuningBehavior tuningBehavior;
    public final String translationKey;
    public final float defaultValue;
    public final boolean canBeTuned;

    public ArtificeAttributeType(ResourceLocation id, Function<ArtificeAttributeData, ArtificeAttributeValue> valueGetter, TuningBehavior tuningBehavior, float defaultValue) {
        this(id, valueGetter, tuningBehavior, defaultValue, true);
    }

    public ArtificeAttributeType(ResourceLocation id, Function<ArtificeAttributeData, ArtificeAttributeValue> valueGetter, TuningBehavior tuningBehavior, float defaultValue, boolean canBeTuned) {
        this.id = id;
        this.valueGetter = valueGetter;
        this.translationKey = id.getNamespace() + ".gui.crucible.attribute." + id.getPath();
        this.tuningBehavior = tuningBehavior;
        this.defaultValue = defaultValue;
        this.canBeTuned = canBeTuned;
        CRUCIBLE_ATTRIBUTES.add(this);
    }

    public ArtificeAttributeValue getAttributeValue(ArtificeAttributeData data) {
        return valueGetter.apply(data);
    }

    public String getDataPrint(ArtificeAttributeData data) {
        return String.format("%.2f", getAttributeValue(data).getValue(data));
    }

    public boolean isValueValid(ArtificeAttributeData data) {
        ArtificeAttributeValue attributeValue = getAttributeValue(data);
        return attributeValue.getValue(data) > 0;
    }

    public String getLangKey() {
        return id.getNamespace() + ".gui.crucible.attribute." + id.getPath();
    }


    public static List<ArtificeAttributeType> getExistingAttributes(ArtificeAttributeData data) {
        List<ArtificeAttributeType> attributes = new ArrayList<>();
        for (ArtificeAttributeType attributeType : CRUCIBLE_ATTRIBUTES) {
            if (attributeType.isValueValid(data)) {
                attributes.add(attributeType);
            }
        }
        return attributes;
    }

    public static ArtificeAttributeType getAttribute(ResourceLocation resourceLocation) {
        for (ArtificeAttributeType attributeType : CRUCIBLE_ATTRIBUTES) {
            if (attributeType.id.equals(resourceLocation)) {
                return attributeType;
            }
        }
        return null;
    }
}
