package com.sammy.malum.common.item.curiosities;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import vectorwing.farmersdelight.common.item.KnifeItem;

public class MalumKnifeItem extends KnifeItem {
    private ItemAttributeModifiers attributes;

    public MalumKnifeItem(Tier tier, float attackDamageIn, float attackSpeedIn, Properties properties) {
        super(makeTier(tier, attackDamageIn, attackSpeedIn - 2f), properties);
    }

    @Override
    public ItemAttributeModifiers getDefaultAttributeModifiers() {
        ItemAttributeModifiers defaultModifiers = super.getDefaultAttributeModifiers();
        if (attributes == null) {
            ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();
            createExtraAttributes().build().modifiers().forEach(e -> builder.add(e.attribute(), e.modifier(), e.slot()));
            defaultModifiers.modifiers().forEach(e -> builder.add(e.attribute(), e.modifier(), e.slot()));
            attributes = builder.build();
        }
        return super.getDefaultAttributeModifiers();
    }

    public ItemAttributeModifiers.Builder createExtraAttributes() {
        return ItemAttributeModifiers.builder();
    }

    protected static Tier makeTier(Tier tier, float attackDamageIn, float attackSpeedIn) {
        return new Tier() {
            @Override
            public int getUses() { return tier.getUses(); }

            @Override
            public float getSpeed() { return attackSpeedIn; }

            @Override
            public float getAttackDamageBonus() { return attackDamageIn; }

            @Override
            public TagKey<Block> getIncorrectBlocksForDrops() {
                return tier.getIncorrectBlocksForDrops();
            }

            @Override
            public int getEnchantmentValue() {
                return tier.getEnchantmentValue();
            }

            @Override
            public Ingredient getRepairIngredient() {
                return tier.getRepairIngredient();
            }
        };
    }
}