package com.sammy.malum.common.enchantment.scythe;

import com.sammy.malum.registry.common.item.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.item.enchantment.*;
import net.minecraftforge.event.*;

import java.util.*;

public class WindedEnchantment extends Enchantment {

    private static final UUID ATTACK_SPEED_MODIFIER = UUID.fromString("FA233E1C-4180-4865-B01B-BCCE9785ACA3");

    public WindedEnchantment() {
        super(Rarity.UNCOMMON, EnchantmentRegistry.SCYTHE_ONLY, new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
    }

    @Override
    protected boolean checkCompatibility(Enchantment pOther) {
        return !pOther.equals(EnchantmentRegistry.HAUNTED.get()) && super.checkCompatibility(pOther);
    }

    public static void addAttackSpeed(ItemAttributeModifierEvent event) {
        if (event.getSlotType().equals(EquipmentSlot.MAINHAND)) {
            var itemStack = event.getItemStack();
            int enchantmentLevel = itemStack.getEnchantmentLevel(EnchantmentRegistry.WINDED.get());
            if (enchantmentLevel > 0) {
                var attackSpeed = Attributes.ATTACK_SPEED;
                if (event.getOriginalModifiers().containsKey(attackSpeed)) {
                    AttributeModifier attributeModifier = null;
                    if (!event.getOriginalModifiers().get(attackSpeed).isEmpty()) {
                        attributeModifier = event.getOriginalModifiers().get(attackSpeed).iterator().next();
                    }
                    if (attributeModifier != null) {
                        double amount = attributeModifier.getAmount() + enchantmentLevel * 0.15f;
                        var newAttackSpeed = new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon attack speed", amount, AttributeModifier.Operation.ADDITION);
                        event.removeAttribute(attackSpeed);
                        event.addModifier(attackSpeed, newAttackSpeed);
                    }
                }
            }
        }
    }

    @Override
    public int getMaxLevel() {
        return 2;
    }

}