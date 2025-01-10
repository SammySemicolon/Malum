package com.sammy.malum.common.item.augment.core;

import com.sammy.malum.core.systems.artifice.ArtificeAttributeType;
import com.sammy.malum.core.systems.artifice.ArtificeModifier;
import com.sammy.malum.registry.common.SpiritTypeRegistry;

import java.util.List;

public class SuspiciousDeviceItem extends CoreAugmentItem {
    public SuspiciousDeviceItem(Properties pProperties) {
        super(pProperties, List.of(SpiritTypeRegistry.ARCANE_SPIRIT, SpiritTypeRegistry.ELDRITCH_SPIRIT), true,
                new ArtificeModifier(ArtificeAttributeType.INSTABILITY, 1f));
    }
}
