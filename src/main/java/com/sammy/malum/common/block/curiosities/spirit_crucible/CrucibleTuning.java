package com.sammy.malum.common.block.curiosities.spirit_crucible;

import com.sammy.malum.common.item.augment.*;

import java.util.*;
import java.util.function.*;

public class CrucibleTuning {

    public final ICatalyzerAccelerationTarget target;
    public final float tuningStrength;
    public final TuningModifier focusingSpeedMultiplier;
    public final InverseTuningModifier fuelUsageRate;
    public final InverseTuningModifier damageChanceMultiplier;
    public final TuningModifier bonusYieldChanceMultiplier;
    public final TuningModifier chainFocusingChanceMultiplier;
    public final TuningModifier damageAbsorptionChanceMultiplier;
    public final TuningModifier restorationChanceMultiplier;

    public CrucibleTuning(ICatalyzerAccelerationTarget target, float tuningStrength) {
        this.target = target;
        this.tuningStrength = tuningStrength;
        this.focusingSpeedMultiplier = new TuningModifier(this, CrucibleAttributeType.FOCUSING_SPEED, 1);
        this.fuelUsageRate = new InverseTuningModifier(this, CrucibleAttributeType.FUEL_USAGE_RATE, 1);
        this.damageChanceMultiplier = new InverseTuningModifier(this, CrucibleAttributeType.INSTABILITY);
        this.bonusYieldChanceMultiplier = new TuningModifier(this, CrucibleAttributeType.FORTUNE_CHANCE);
        this.chainFocusingChanceMultiplier = new TuningModifier(this, CrucibleAttributeType.CHAIN_FOCUSING_CHANCE);
        this.damageAbsorptionChanceMultiplier = new TuningModifier(this, CrucibleAttributeType.SHIELDING_CHANCE);
        this.restorationChanceMultiplier = new TuningModifier(this, CrucibleAttributeType.RESTORATION_CHANCE);
    }

    public CrucibleTuning.CrucibleAttributeType getTuningType() {
        return target.getTuningType();
    }

    public enum CrucibleAttributeType {
        NONE(),
        FOCUSING_SPEED(d -> d.focusingSpeed),
        FUEL_USAGE_RATE(d -> d.fuelUsageRate),
        INSTABILITY(d -> d.damageChance),
        FORTUNE_CHANCE(d -> d.bonusYieldChance),
        CHAIN_FOCUSING_CHANCE(d -> d.chainFocusingChance),
        SHIELDING_CHANCE(d -> d.damageAbsorptionChance),
        RESTORATION_CHANCE(d -> d.restorationChance);

        public final Function<CrucibleAccelerationData, CrucibleAccelerationData.TunedValue> valueGetter;
        public final Predicate<CrucibleAccelerationData> isValueValid;
        public final Function<CrucibleAccelerationData, String> statDisplayFunction;

        CrucibleAttributeType() {
            this(null, null, null);
        }

        CrucibleAttributeType(Function<CrucibleAccelerationData, CrucibleAccelerationData.TunedValue> valueGetter) {
            this(valueGetter, d -> valueGetter.apply(d).getValue(d) > 0, d -> String.format("%.2f", valueGetter.apply(d).getValue(d)));
        }

        CrucibleAttributeType(Function<CrucibleAccelerationData, CrucibleAccelerationData.TunedValue> valueGetter, Predicate<CrucibleAccelerationData> isValueValid, Function<CrucibleAccelerationData, String> statDisplayFunction) {
            this.valueGetter = valueGetter;
            this.isValueValid = isValueValid;
            this.statDisplayFunction = statDisplayFunction;
        }

        public static List<CrucibleAttributeType> getValidValues(CrucibleAccelerationData data) {
            List<CrucibleAttributeType> validValues = new ArrayList<>();
            for (int i = 1; i < values().length; i++) {
                CrucibleAttributeType type = values()[i];
                if (type.isValueValid.test(data)) {
                    validValues.add(type);
                }
            }
            return validValues;
        }

        public CrucibleAttributeType next(CrucibleAttributeType current, ICatalyzerAccelerationTarget target) {
            final List<CrucibleAttributeType> validValues = getValidValues(target.getAccelerationData());
            final int index = current.equals(NONE) ? 0 : validValues.indexOf(current) + 1;
            if (index == validValues.size()) {
                return NONE;
            }
            return validValues.get((index) % validValues.size());
        }

        public String translation() {
            return "malum.gui.crucible.attribute." + toString().toLowerCase(Locale.ROOT);
        }
    }

    public enum AppliedTuningType {
        BUFF(TuningModifier::getPositiveMultiplier),
        DEBUFF(TuningModifier::getNegativeMultiplier),
        NONE(0f);

        public final Function<TuningModifier, Float> multiplierGetter;

        AppliedTuningType(Function<TuningModifier, Float> multiplierGetter) {
            this.multiplierGetter = multiplierGetter;
        }

        AppliedTuningType(Float value) {
            this.multiplierGetter = t -> value;
        }
    }

    public static class TuningModifier {
        public static TuningModifier DEFAULT = new TuningModifier(CrucibleAttributeType.NONE, AppliedTuningType.NONE, 0, 1);
        public final CrucibleAttributeType attributeType;
        public final AppliedTuningType appliedTuningType;
        public final float baseValue;
        public final float tuningPotency;

        private TuningModifier(CrucibleAttributeType attributeType, AppliedTuningType appliedTuningType, float baseValue, float tuningPotency) {
            this.attributeType = attributeType;
            this.appliedTuningType = appliedTuningType;
            this.baseValue = baseValue;
            this.tuningPotency = tuningPotency;
        }

        public TuningModifier(CrucibleTuning tuning, CrucibleAttributeType attributeType, float baseValue) {
            this(attributeType, tuning.getTuningType().equals(CrucibleAttributeType.NONE) ?
                    AppliedTuningType.NONE : attributeType.equals(tuning.getTuningType()) ? AppliedTuningType.BUFF : AppliedTuningType.DEBUFF, baseValue, tuning.tuningStrength);
        }

        public TuningModifier(CrucibleTuning tuning, CrucibleAttributeType attributeType) {
            this(tuning, attributeType, 0);
        }

        public CrucibleAccelerationData.TunedValue createTunedValue(List<AbstractAugmentItem> augmentItems, Function<AbstractAugmentItem, Float> mappingFunction) {
            return createTunedValue(augmentItems.stream().map(mappingFunction).reduce(Float::sum).orElse(0f));
        }
        public CrucibleAccelerationData.TunedValue createTunedValue(List<AbstractAugmentItem> augmentItems, Function<AbstractAugmentItem, Float> mappingFunction, float bonus) {
            return createTunedValue(bonus + augmentItems.stream().map(mappingFunction).reduce(Float::sum).orElse(0f));
        }
        public CrucibleAccelerationData.TunedValue createTunedValue(float bonus) {
            return new CrucibleAccelerationData.TunedValue(this, bonus);
        }

        public float getRelativeValue(CrucibleAccelerationData data, CrucibleAccelerationData.TunedValue tunedValue) {
            return tunedValue.getValue(data);
        }

        public float getTuningMultiplier() {
            return appliedTuningType.multiplierGetter.apply(this) * tuningPotency;
        }

        protected float getMultiplierScalar(float attributeMultiplier) {
            return 1 + attributeMultiplier;
        }

        protected float getPositiveMultiplier() {
            return 0.2f;
        }

        protected float getNegativeMultiplier() {
            return -0.1f;
        }
    }

    public static class InverseTuningModifier extends TuningModifier {

        public InverseTuningModifier(CrucibleTuning tuning, CrucibleAttributeType attributeType, float baseValue) {
            super(tuning, attributeType, baseValue);
        }

        public InverseTuningModifier(CrucibleTuning tuning, CrucibleAttributeType attributeType) {
            super(tuning, attributeType);
        }

        @Override
        public float getRelativeValue(CrucibleAccelerationData data, CrucibleAccelerationData.TunedValue tunedValue) {
            return baseValue - tunedValue.getValue(data);
        }

        @Override
        protected float getMultiplierScalar(float attributeMultiplier) {
            return 1 - attributeMultiplier;
        }

        @Override
        protected float getPositiveMultiplier() {
            return -0.2f;
        }

        @Override
        protected float getNegativeMultiplier() {
            return 0.1f;
        }
    }
}