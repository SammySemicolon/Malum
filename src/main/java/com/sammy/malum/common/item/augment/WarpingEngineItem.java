package com.sammy.malum.common.item.augment;

import com.sammy.malum.core.systems.artifice.ArtificeAttributeType;
import com.sammy.malum.core.systems.artifice.ArtificeModifier;
import com.sammy.malum.registry.common.*;

public class WarpingEngineItem extends AugmentItem {
    public WarpingEngineItem(Properties pProperties) {
        super(pProperties, SpiritTypeRegistry.ELDRITCH_SPIRIT,
                new ArtificeModifier(ArtificeAttributeType.CHAIN_FOCUSING_CHANCE, 0.1f),
                new ArtificeModifier(ArtificeAttributeType.TUNING_STRAIN, 0.05f),
                new ArtificeModifier(ArtificeAttributeType.FUEL_USAGE_RATE, 0.15f));
    }
}
