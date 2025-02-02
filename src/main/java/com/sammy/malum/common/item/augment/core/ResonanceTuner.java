package com.sammy.malum.common.item.augment.core;

import com.sammy.malum.core.systems.artifice.ArtificeAttributeData;
import com.sammy.malum.core.systems.artifice.ArtificeAttributeType;
import com.sammy.malum.core.systems.artifice.ArtificeModifier;
import com.sammy.malum.core.systems.artifice.TuningModifier;
import com.sammy.malum.registry.common.SpiritTypeRegistry;
import net.minecraft.util.Mth;

import java.util.List;

public class ResonanceTuner extends CoreAugmentItem {
    public ResonanceTuner(Properties pProperties) {
        super(pProperties, List.of(SpiritTypeRegistry.EARTHEN_SPIRIT, SpiritTypeRegistry.AERIAL_SPIRIT), true,
                new ArtificeModifier(ArtificeAttributeType.RESONANCE_TUNING, 1f));
    }

    public static void exchangeSpeed(ArtificeAttributeData data) {
        float resonanceTuning = data.resonanceTuning.getValue(data);
        if (resonanceTuning > 0) {
            float speed = data.focusingSpeed.getValue(data);
            float instability = data.instability.getValue(data);
            float goal = Math.min(instability * resonanceTuning, speed)*2f;
            float conversion = Mth.clampedLerp(0, 1, speed/goal);
            if (conversion > 0) {
                float toll = Mth.clamp(goal/speed, 0, 1) * 0.8f;
                data.focusingSpeed.applyModifier(new TuningModifier(TuningModifier.RESONANCE_TUNING, -toll));
                data.instability.applyModifier(new TuningModifier(TuningModifier.RESONANCE_TUNING, -conversion));
            }
        }
    }
}
