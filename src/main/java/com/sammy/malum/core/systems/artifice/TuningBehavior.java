package com.sammy.malum.core.systems.artifice;

public abstract class TuningBehavior {

    public abstract float getRelativeValue(ArtificeAttributeData data, ArtificeAttributeValue artificeAttributeValue);

    public abstract float getMultiplier(boolean isPositive);

    public float getPositiveMultiplier() {
        return getMultiplier(true);
    }

    public float getNegativeMultiplier() {
        return getMultiplier(false);
    }

    public static final TuningBehavior STANDARD = new TuningBehavior() {
        @Override
        public float getMultiplier(boolean isPositive) {
            return isPositive ? 0.2f : -0.1f;
        }

        @Override
        public float getRelativeValue(ArtificeAttributeData data, ArtificeAttributeValue artificeAttributeValue) {
            return artificeAttributeValue.getValue(data);
        }
    };
    public static final TuningBehavior INVERSE = new TuningBehavior() {
        @Override
        public float getMultiplier(boolean isPositive) {
            return isPositive ? -0.2f : 0.1f;
        }

        @Override
        public float getRelativeValue(ArtificeAttributeData data, ArtificeAttributeValue artificeAttributeValue) {
            return artificeAttributeValue.getValue(data);
        }
    };
}