package com.sammy.malum.common.item.curiosities;

import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import team.lodestar.lodestone.registry.common.*;
import team.lodestar.lodestone.systems.item.*;

public class MagicKnifeItem extends MalumKnifeItem {
    public MagicKnifeItem(Tier tier, float attackDamage, float attackSpeed, float magicDamage, LodestoneItemProperties properties) {
        super(tier, attackDamage, attackSpeed, properties.mergeAttributes(
                ItemAttributeModifiers.builder()
                        .add(LodestoneAttributes.MAGIC_DAMAGE, new AttributeModifier(LodestoneAttributes.MAGIC_DAMAGE.getId(), magicDamage, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                        .build()));
    }
}