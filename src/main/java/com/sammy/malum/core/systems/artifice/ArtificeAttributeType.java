package com.sammy.malum.core.systems.artifice;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.sammy.malum.MalumMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;

public class ArtificeAttributeType {

    public static final ArrayList<ArtificeAttributeType> CRUCIBLE_ATTRIBUTES = new ArrayList<>();

    public static final Codec<ArtificeAttributeType> CODEC = ResourceLocation.CODEC.xmap(ArtificeAttributeType::getAttribute, f -> f.id);

    public static final ArtificeAttributeType FOCUSING_SPEED = create(
            MalumMod.malumPath("focusing_speed"), d -> d.focusingSpeed).setDefaultValue(1).build();
    public static final ArtificeAttributeType FUEL_USAGE_RATE = create(
            MalumMod.malumPath("fuel_usage_rate"), d -> d.fuelUsageRate).withRequirement((d, v) -> d.demandsFuel && v.getValue(d) > 0).setDefaultValue(1).invertedTuning().build();
    public static final ArtificeAttributeType INSTABILITY = create(
            MalumMod.malumPath("instability"), d -> d.instability).setDefaultValue(0.2f).invertedTuning().build();
    public static final ArtificeAttributeType FORTUNE_CHANCE = create(
            MalumMod.malumPath("fortune_chance"), d -> d.fortuneChance).build();
    public static final ArtificeAttributeType CHAIN_FOCUSING_CHANCE = create(
            MalumMod.malumPath("chain_focusing_chance"), d -> d.chainFocusingChance).build();
    public static final ArtificeAttributeType DAMAGE_ABSORPTION_CHANCE = create(
            MalumMod.malumPath("damage_absorption_chance"), d -> d.damageAbsorptionChance).build();
    public static final ArtificeAttributeType RESTORATION_CHANCE = create(
            MalumMod.malumPath("restoration_chance"), d -> d.restorationChance).build();
    public static final ArtificeAttributeType WEAKNESS_TUNING = create(
            MalumMod.malumPath("weakness_tuning"), d -> d.weaknessTuning).noTuning().build();
    public static final ArtificeAttributeType TUNING_POTENCY = create(
            MalumMod.malumPath("tuning_potency"), d -> d.tuningPotency).withRequirement((d, v) -> v.getValue(d) != 0).noTuning().build();
    public static final ArtificeAttributeType TUNING_STRAIN = create(
            MalumMod.malumPath("tuning_strain"), d -> d.tuningStrain).withRequirement((d, v) -> v.getValue(d) != 0).noTuning().build();

    public final ResourceLocation id;
    public final Function<ArtificeAttributeData, ArtificeAttributeValue> valueGetter;
    public final BiPredicate<ArtificeAttributeData, ArtificeAttributeValue> valueValidator;
    public final TuningBehavior tuningBehavior;
    public final String translationKey;
    public final float defaultValue;
    public final boolean canBeTuned;

    public ArtificeAttributeType(ResourceLocation id, Function<ArtificeAttributeData, ArtificeAttributeValue> valueGetter, BiPredicate<ArtificeAttributeData, ArtificeAttributeValue> valueValidator, @Nullable TuningBehavior tuningBehavior, float defaultValue) {
        this.id = id;
        this.valueGetter = valueGetter;
        this.valueValidator = valueValidator;
        this.translationKey = id.getNamespace() + ".gui.crucible.attribute." + id.getPath();
        this.tuningBehavior = tuningBehavior;
        this.defaultValue = defaultValue;
        this.canBeTuned = tuningBehavior != null;
        CRUCIBLE_ATTRIBUTES.add(this);
    }

    public ArtificeAttributeValue getAttributeValue(ArtificeAttributeData data) {
        return valueGetter.apply(data);
    }

    public String getDataPrint(ArtificeAttributeData data) {
        return Math.round(getAttributeValue(data).getValue(data) * 100) + "%";
    }

    public boolean isValueValid(ArtificeAttributeData data) {
        return valueValidator.test(data, data.getAttributeValue(this));
    }

    public String getLangKey() {
        return translationKey;
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

    public static ArtificeAttributeTypeBuilder create(ResourceLocation id, Function<ArtificeAttributeData, ArtificeAttributeValue> valueGetter) {
        return new ArtificeAttributeTypeBuilder(id, valueGetter);
    }

    public static class ArtificeAttributeTypeBuilder {
        private final ResourceLocation id;
        private final Function<ArtificeAttributeData, ArtificeAttributeValue> valueGetter;

        private BiPredicate<ArtificeAttributeData, ArtificeAttributeValue> valueValidator = (d, v) -> v.getValue(d) > 0;

        private float defaultValue;
        private TuningBehavior tuningBehavior = TuningBehavior.STANDARD;

        public ArtificeAttributeTypeBuilder(ResourceLocation id,
                                            Function<ArtificeAttributeData, ArtificeAttributeValue> valueGetter) {

            this.id = id;
            this.valueGetter = valueGetter;
        }

        public ArtificeAttributeTypeBuilder withRequirement(BiPredicate<ArtificeAttributeData, ArtificeAttributeValue> valueValidator) {
            this.valueValidator = valueValidator;
            return this;
        }

        public ArtificeAttributeTypeBuilder setDefaultValue(float defaultValue) {
            this.defaultValue = defaultValue;
            return this;
        }

        public ArtificeAttributeTypeBuilder invertedTuning() {
            this.tuningBehavior = TuningBehavior.INVERSE;
            return this;
        }

        public ArtificeAttributeTypeBuilder noTuning() {
            this.tuningBehavior = null;
            return this;
        }

        public ArtificeAttributeType build() {
            return new ArtificeAttributeType(id, valueGetter, valueValidator, tuningBehavior, defaultValue);
        }
    }
}