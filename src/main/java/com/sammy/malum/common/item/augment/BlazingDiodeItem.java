package com.sammy.malum.common.item.augment;

import com.sammy.malum.common.block.curiosities.spirit_crucible.artifice.ArtificeAttributeType;
import com.sammy.malum.common.block.curiosities.spirit_crucible.artifice.ArtificeModifier;
import com.sammy.malum.registry.common.*;

public class BlazingDiodeItem extends AugmentItem {
    public BlazingDiodeItem(Properties pProperties) {
        super(pProperties, SpiritTypeRegistry.INFERNAL_SPIRIT,
                new ArtificeModifier(ArtificeAttributeType.FUEL_USAGE_RATE, -0.25f),
                new ArtificeModifier(ArtificeAttributeType.FOCUSING_SPEED, 0.25f));
    }
}
