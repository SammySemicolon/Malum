package com.sammy.malum.common.item.augment.core;

import com.sammy.malum.core.systems.artifice.ArtificeModifier;
import com.sammy.malum.common.item.augment.*;
import com.sammy.malum.core.systems.spirit.*;

public class CoreAugmentItem extends AugmentItem {

    public CoreAugmentItem(Properties pProperties, MalumSpiritType spiritType, ArtificeModifier... modifiers) {
        super(pProperties, spiritType, modifiers);
    }

    public CoreAugmentItem(Properties pProperties, MalumSpiritType spiritType, boolean isCoreAugment, ArtificeModifier... modifiers) {
        super(pProperties, spiritType, isCoreAugment, modifiers);
    }

    @Override
    public String getAugmentTypeTranslator() {
        return "core_augment";
    }
}
