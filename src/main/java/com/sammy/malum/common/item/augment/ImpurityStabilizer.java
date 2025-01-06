package com.sammy.malum.common.item.augment;

import com.sammy.malum.core.systems.artifice.ArtificeAttributeType;
import com.sammy.malum.core.systems.artifice.ArtificeModifier;
import com.sammy.malum.registry.common.*;

public class ImpurityStabilizer extends AugmentItem {
    public ImpurityStabilizer(Properties pProperties) {
        super(pProperties, SpiritTypeRegistry.WICKED_SPIRIT,
                new ArtificeModifier(ArtificeAttributeType.WEAKNESS_TUNING, 0.4f),
                new ArtificeModifier(ArtificeAttributeType.TUNING_STRAIN, 0.08f));
    }
}
