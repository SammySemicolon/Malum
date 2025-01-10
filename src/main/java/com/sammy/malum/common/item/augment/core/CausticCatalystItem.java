package com.sammy.malum.common.item.augment.core;

import com.sammy.malum.core.systems.artifice.*;
import com.sammy.malum.registry.common.SpiritTypeRegistry;

import java.util.List;

public class CausticCatalystItem extends CoreAugmentItem {
    public CausticCatalystItem(Properties pProperties) {
        super(pProperties, List.of(SpiritTypeRegistry.AQUEOUS_SPIRIT, SpiritTypeRegistry.INFERNAL_SPIRIT), true,
                new ArtificeModifier(ArtificeAttributeType.CAUSTIC_SYNERGY, 1f));
    }

    public static void scalePotency(ArtificeAttributeData data) {
        float causticSynergy = data.causticSynergy.getValue(data);
        if (causticSynergy > 0) {
            float scalar = data.instability.getValue(data) * causticSynergy * 2f;
            if (scalar > 0) {
                data.tuningPotency.applyModifier(new TuningModifier(TuningModifier.CAUSTIC_SYNERGY, scalar));
                return;
            }
        }
        data.tuningPotency.removeModifier(TuningModifier.CAUSTIC_SYNERGY);
    }
}
