package com.sammy.malum.common.item.augment.core;

import com.sammy.malum.core.systems.artifice.ArtificeAttributeType;
import com.sammy.malum.core.systems.artifice.ArtificeModifier;
import com.sammy.malum.registry.common.SpiritTypeRegistry;

import java.util.List;

public class SympathyDrive extends CoreAugmentItem {
    public SympathyDrive(Properties pProperties) {
        super(pProperties, List.of(SpiritTypeRegistry.SACRED_SPIRIT, SpiritTypeRegistry.WICKED_SPIRIT), true,
                new ArtificeModifier(ArtificeAttributeType.MISFORTUNE_REVERSAL, 1f));
    }
}
