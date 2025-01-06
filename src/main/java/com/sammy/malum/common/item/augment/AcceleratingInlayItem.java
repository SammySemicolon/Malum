package com.sammy.malum.common.item.augment;

import com.sammy.malum.core.systems.artifice.ArtificeAttributeType;
import com.sammy.malum.core.systems.artifice.ArtificeModifier;
import com.sammy.malum.registry.common.*;

public class AcceleratingInlayItem extends AugmentItem {
    public AcceleratingInlayItem(Properties pProperties) {
        super(pProperties, SpiritTypeRegistry.AERIAL_SPIRIT, new ArtificeModifier(ArtificeAttributeType.FOCUSING_SPEED, 1f));
    }
}
