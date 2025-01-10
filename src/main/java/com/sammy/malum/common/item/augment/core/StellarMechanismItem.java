package com.sammy.malum.common.item.augment.core;

import com.sammy.malum.core.systems.artifice.ArtificeAttributeType;
import com.sammy.malum.core.systems.artifice.ArtificeModifier;
import com.sammy.malum.registry.common.*;

import java.util.List;

public class StellarMechanismItem extends CoreAugmentItem {
    public StellarMechanismItem(Properties pProperties) {
        super(pProperties, List.of(SpiritTypeRegistry.AERIAL_SPIRIT, SpiritTypeRegistry.AQUEOUS_SPIRIT, SpiritTypeRegistry.EARTHEN_SPIRIT, SpiritTypeRegistry.INFERNAL_SPIRIT), true,
                new ArtificeModifier(ArtificeAttributeType.TUNING_POTENCY, 1f),
                new ArtificeModifier(ArtificeAttributeType.TUNING_STRAIN, -1f));
    }
}
