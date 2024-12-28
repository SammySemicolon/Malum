package com.sammy.malum.common.item.augment;

import com.sammy.malum.common.block.curiosities.spirit_crucible.artifice.ArtificeAttributeType;
import com.sammy.malum.common.block.curiosities.spirit_crucible.artifice.ArtificeModifier;
import com.sammy.malum.registry.common.*;

public class WarpingEngineItem extends AugmentItem {
    public WarpingEngineItem(Properties pProperties) {
        super(pProperties, SpiritTypeRegistry.ELDRITCH_SPIRIT,
                new ArtificeModifier(ArtificeAttributeType.CHAIN_FOCUSING_CHANCE, 0.12f),
                new ArtificeModifier(ArtificeAttributeType.INSTABILITY, 0.03f),
                new ArtificeModifier(ArtificeAttributeType.FUEL_USAGE_RATE, 0.15f));
    }
}
