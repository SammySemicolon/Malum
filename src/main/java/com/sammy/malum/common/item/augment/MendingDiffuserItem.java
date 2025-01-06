package com.sammy.malum.common.item.augment;

import com.sammy.malum.core.systems.artifice.ArtificeAttributeType;
import com.sammy.malum.core.systems.artifice.ArtificeModifier;
import com.sammy.malum.registry.common.*;

public class MendingDiffuserItem extends AugmentItem {
    public MendingDiffuserItem(Properties pProperties) {
        super(pProperties, SpiritTypeRegistry.SACRED_SPIRIT,
                new ArtificeModifier(ArtificeAttributeType.RESTORATION_CHANCE, 0.15f));
    }
}
