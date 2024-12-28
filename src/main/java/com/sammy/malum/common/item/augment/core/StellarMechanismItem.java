package com.sammy.malum.common.item.augment.core;

import com.sammy.malum.common.block.curiosities.spirit_crucible.artifice.ArtificeAttributeType;
import com.sammy.malum.common.block.curiosities.spirit_crucible.artifice.ArtificeModifier;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import com.sammy.malum.registry.common.*;

import java.util.List;

public class StellarMechanismItem extends CoreAugmentItem {
    public StellarMechanismItem(Properties pProperties) {
        super(pProperties, SpiritTypeRegistry.ELDRITCH_SPIRIT, true, new ArtificeModifier(ArtificeAttributeType.TUNING_POTENCY, 1f));
    }
}
