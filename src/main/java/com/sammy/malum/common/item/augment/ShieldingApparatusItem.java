package com.sammy.malum.common.item.augment;

import com.sammy.malum.core.systems.artifice.ArtificeAttributeData;
import com.sammy.malum.core.systems.artifice.ArtificeAttributeType;
import com.sammy.malum.core.systems.artifice.ArtificeModifier;
import com.sammy.malum.registry.common.*;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;

public class ShieldingApparatusItem extends AugmentItem {
    public ShieldingApparatusItem(Properties pProperties) {
        super(pProperties, SpiritTypeRegistry.ARCANE_SPIRIT,
                new ArtificeModifier(ArtificeAttributeType.DAMAGE_ABSORPTION_CHANCE, 0.1f),
                new ArtificeModifier(ArtificeAttributeType.INSTABILITY, -0.05f),
                new ArtificeModifier(ArtificeAttributeType.FOCUSING_SPEED, -0.2f));
    }

    public static boolean shieldImpetus(Level level, BlockPos pos, ArtificeAttributeData attributes) {
        float chance = attributes.damageAbsorptionChance.getValue(attributes);
        RandomSource random = level.getRandom();
        boolean success = chance > 0 && random.nextFloat() < chance;
        if (success) {
            level.playSound(null, pos, SoundRegistry.SHIELDING_APPARATUS_SHIELDS.get(), SoundSource.BLOCKS, 0.5f, 0.25f + random.nextFloat() * 0.25f);
        }
        return success;
    }
}
