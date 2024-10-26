package com.sammy.malum.common.enchantment;

import com.sammy.malum.registry.common.item.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.*;
import net.neoforged.neoforge.event.ItemAttributeModifierEvent;

import java.util.*;

public class HauntedEnchantment extends Enchantment {
    public HauntedEnchantment() {
        super(Rarity.UNCOMMON, EnchantmentRegistry.MAGIC_CAPABLE_WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
    }

    @Override
    protected boolean checkCompatibility(Enchantment pOther) {
        return !pOther.equals(EnchantmentRegistry.WINDED.get()) && super.checkCompatibility(pOther);
    }

    public static void addMagicDamage(ItemAttributeModifierEvent event) {
        if (event.getSlotType().equals(EquipmentSlot.MAINHAND)) {
            var itemStack = event.getItemStack();
            int enchantmentLevel = itemStack.getEnchantmentLevel(EnchantmentRegistry.HAUNTED.get());
            if (enchantmentLevel > 0) {
                var uuid = LodestoneAttributeRegistry.UUIDS.get(LodestoneAttributeRegistry.MAGIC_DAMAGE);
                var magicDamage = LodestoneAttributeRegistry.MAGIC_DAMAGE.get();
                if (event.getOriginalModifiers().containsKey(magicDamage)) {
                    AttributeModifier attributeModifier = null;
                    if (!event.getOriginalModifiers().get(magicDamage).isEmpty()) {
                        attributeModifier = event.getOriginalModifiers().get(magicDamage).iterator().next();
                    }
                    if (attributeModifier != null) {
                        double amount = attributeModifier.getAmount() + enchantmentLevel;
                        var newMagicDamage = new AttributeModifier(uuid, "Weapon magic damage", amount, AttributeModifier.Operation.ADDITION);
                        event.removeAttribute(magicDamage);
                        event.addModifier(magicDamage, newMagicDamage);
                    }
                } else {
                    event.addModifier(magicDamage, new AttributeModifier(uuid, "Weapon magic damage", enchantmentLevel, AttributeModifier.Operation.ADDITION));
                }
            }
        }
    }

    @Override
    public int getMaxLevel() {
        return 2;
    }

}