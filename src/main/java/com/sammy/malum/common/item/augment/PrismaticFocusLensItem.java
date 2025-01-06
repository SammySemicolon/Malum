package com.sammy.malum.common.item.augment;

import com.sammy.malum.core.systems.artifice.ArtificeAttributeType;
import com.sammy.malum.core.systems.artifice.ArtificeModifier;
import com.sammy.malum.registry.common.*;

public class PrismaticFocusLensItem extends AugmentItem {
    public PrismaticFocusLensItem(Properties pProperties) {
        super(pProperties, SpiritTypeRegistry.AQUEOUS_SPIRIT,
                new ArtificeModifier(ArtificeAttributeType.INSTABILITY, -0.15f));
    }
}
