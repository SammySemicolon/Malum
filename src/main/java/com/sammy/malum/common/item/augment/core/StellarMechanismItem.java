package com.sammy.malum.common.item.augment.core;

import com.sammy.malum.core.systems.artifice.ArtificeAttributeType;
import com.sammy.malum.core.systems.artifice.ArtificeModifier;
import com.sammy.malum.registry.common.*;

public class StellarMechanismItem extends CoreAugmentItem {
    public StellarMechanismItem(Properties pProperties) {
        super(pProperties, SpiritTypeRegistry.ELDRITCH_SPIRIT, true,
                new ArtificeModifier(ArtificeAttributeType.TUNING_POTENCY, 1f),
                new ArtificeModifier(ArtificeAttributeType.TUNING_STRAIN, -0.5f));
    }
}
