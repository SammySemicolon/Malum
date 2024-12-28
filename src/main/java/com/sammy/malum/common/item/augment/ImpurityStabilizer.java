package com.sammy.malum.common.item.augment;

import com.sammy.malum.common.block.curiosities.spirit_crucible.artifice.ArtificeAttributeType;
import com.sammy.malum.common.block.curiosities.spirit_crucible.artifice.ArtificeModifier;
import com.sammy.malum.registry.common.*;

public class ImpurityStabilizer extends AugmentItem {
    public ImpurityStabilizer(Properties pProperties) {
        super(pProperties, SpiritTypeRegistry.WICKED_SPIRIT,
                new ArtificeModifier(ArtificeAttributeType.WEAKEST_BOOST, 0.25f),
                new ArtificeModifier(ArtificeAttributeType.INSTABILITY, 0.05f));
    }
}
