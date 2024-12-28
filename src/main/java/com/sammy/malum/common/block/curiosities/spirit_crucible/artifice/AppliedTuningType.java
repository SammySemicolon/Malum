package com.sammy.malum.common.block.curiosities.spirit_crucible.artifice;

public interface AppliedTuningType {

    AppliedTuningType POSITIVE = TuningBehavior::getPositiveMultiplier;
    AppliedTuningType NEGATIVE = TuningBehavior::getNegativeMultiplier;

    float getMultiplier(TuningBehavior tuningBehavior);

    default float getMultiplier(ArtificeAttributeType type) {
        return getMultiplier(type.tuningBehavior);
    }
}