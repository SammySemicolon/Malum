package com.sammy.malum.common.item.augment;

import com.sammy.malum.core.systems.artifice.ArtificeAttributeData;
import com.sammy.malum.core.systems.artifice.ArtificeAttributeType;
import com.sammy.malum.core.systems.artifice.ArtificeAttributeValue;
import com.sammy.malum.core.systems.artifice.ArtificeModifier;
import com.sammy.malum.registry.common.*;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class WarpingEngineItem extends AugmentItem {
    public WarpingEngineItem(Properties pProperties) {
        super(pProperties, SpiritTypeRegistry.ELDRITCH_SPIRIT,
                new ArtificeModifier(ArtificeAttributeType.CHAIN_FOCUSING_CHANCE, 0.1f),
                new ArtificeModifier(ArtificeAttributeType.TUNING_STRAIN, 0.05f),
                new ArtificeModifier(ArtificeAttributeType.FUEL_USAGE_RATE, 0.15f));
    }

    public static boolean skipForward(Level level, BlockPos pos, ArtificeAttributeData attributes) {
        float chainFocusingChance = attributes.chainFocusingChance.getValue(attributes);
        var random = level.getRandom();
        boolean success = chainFocusingChance > 0 && random.nextFloat() < chainFocusingChance;
        if (success) {
            level.playSound(null, pos, SoundRegistry.WARPING_ENGINE_REVERBERATES.get(), SoundSource.BLOCKS, 1.5f, 1f + random.nextFloat() * 0.25f);
        }
        attributes.chainProcessingBonus = success ? attributes.chainProcessingBonus + 0.2f : 0;
        return success;
    }
}
