package com.sammy.malum.common.item.augment;

import com.sammy.malum.core.systems.artifice.*;
import com.sammy.malum.registry.common.*;

import java.util.List;

public class ImpurityStabilizer extends AugmentItem {
    public ImpurityStabilizer(Properties pProperties) {
        super(pProperties, SpiritTypeRegistry.WICKED_SPIRIT,
                new ArtificeModifier(ArtificeAttributeType.WEAKNESS_TUNING, 0.4f),
                new ArtificeModifier(ArtificeAttributeType.TUNING_STRAIN, 0.08f));
    }

    public static void applyWeaknessTuning(ArtificeAttributeData data, List<ArtificeAttributeType> attributesForTuning) {
        for (ArtificeAttributeValue attribute : data.attributes) {
            attribute.removeModifier(TuningModifier.WEAKEST_BOOST);
        }
        float bonus = data.weaknessTuning.getValue(data);
        if (bonus != 0) {
            var weakestAttribute = data.figureOutWeakestAttribute(attributesForTuning);
            if (weakestAttribute != null) {
                weakestAttribute.applyModifier(new TuningModifier(TuningModifier.WEAKEST_BOOST, bonus));
            }
        }
    }
}
