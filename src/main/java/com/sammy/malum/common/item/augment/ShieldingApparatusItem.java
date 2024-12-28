package com.sammy.malum.common.item.augment;

import com.sammy.malum.common.block.curiosities.spirit_crucible.artifice.ArtificeAttributeType;
import com.sammy.malum.common.block.curiosities.spirit_crucible.artifice.ArtificeModifier;
import com.sammy.malum.registry.common.*;

public class ShieldingApparatusItem extends AugmentItem {
    public ShieldingApparatusItem(Properties pProperties) {
        super(pProperties, SpiritTypeRegistry.ARCANE_SPIRIT,
                new ArtificeModifier(ArtificeAttributeType.DAMAGE_ABSORPTION_CHANCE, 0.08f),
                new ArtificeModifier(ArtificeAttributeType.INSTABILITY, -0.02f),
                new ArtificeModifier(ArtificeAttributeType.FOCUSING_SPEED, -0.2f));
    }
}
