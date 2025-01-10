package com.sammy.malum.common.item.augment;

import com.sammy.malum.core.systems.artifice.ArtificeAttributeData;
import com.sammy.malum.core.systems.artifice.ArtificeAttributeType;
import com.sammy.malum.core.systems.artifice.ArtificeModifier;
import com.sammy.malum.registry.common.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class MendingDiffuserItem extends AugmentItem {
    public MendingDiffuserItem(Properties pProperties) {
        super(pProperties, SpiritTypeRegistry.SACRED_SPIRIT,
                new ArtificeModifier(ArtificeAttributeType.RESTORATION_CHANCE, 0.15f));
    }

    public static void repairImpetus(Level level, ArtificeAttributeData attributes, ItemStack impetus) {
        float restorationChance = attributes.restorationChance.getValue(attributes);
        var random = level.getRandom();
        while (restorationChance > 0) {
            if (restorationChance >= 1 || random.nextFloat() < restorationChance) {
                impetus.setDamageValue((int) Math.max(impetus.getDamageValue() - impetus.getMaxDamage()*0.01f, 0));
            }
            restorationChance -= 1;
        }
    }
}
