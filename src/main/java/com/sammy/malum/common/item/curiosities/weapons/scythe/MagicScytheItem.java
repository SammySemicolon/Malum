package com.sammy.malum.common.item.curiosities.weapons.scythe;

import com.sammy.malum.common.item.*;
import com.sammy.malum.core.systems.spirit.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.*;
import team.lodestar.lodestone.registry.common.*;

public class MagicScytheItem extends MalumScytheItem implements ISpiritAffiliatedItem {

    public final float magicDamage;

    public MagicScytheItem(Tier tier, float attackDamageIn, float attackSpeedIn, float magicDamage, Properties builderIn) {
        super(tier, attackDamageIn, attackSpeedIn, builderIn);
        this.magicDamage = magicDamage;
    }

    @Override
    public ItemAttributeModifiers.Builder createExtraAttributes() {
        var builder = ItemAttributeModifiers.builder();
        builder.add(LodestoneAttributes.MAGIC_DAMAGE, new AttributeModifier(LodestoneAttributes.MAGIC_DAMAGE.getId(), magicDamage, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
        return builder;
    }

    @Override
    public MalumSpiritType getDefiningSpiritType() {
        return SpiritTypeRegistry.WICKED_SPIRIT;
    }
}
