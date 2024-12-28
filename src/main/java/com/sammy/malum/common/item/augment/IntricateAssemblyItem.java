package com.sammy.malum.common.item.augment;

import com.sammy.malum.common.block.curiosities.spirit_crucible.artifice.ArtificeAttributeType;
import com.sammy.malum.common.block.curiosities.spirit_crucible.artifice.ArtificeModifier;
import com.sammy.malum.registry.common.*;

public class IntricateAssemblyItem extends AugmentItem {
    public IntricateAssemblyItem(Properties pProperties) {
        super(pProperties, SpiritTypeRegistry.EARTHEN_SPIRIT,
                new ArtificeModifier(ArtificeAttributeType.FORTUNE_CHANCE, 0.12f),
                new ArtificeModifier(ArtificeAttributeType.FUEL_USAGE_RATE, 0.04f),
                new ArtificeModifier(ArtificeAttributeType.FOCUSING_SPEED, -0.20f));
    }
}
