package com.sammy.malum.common.item.curiosities.curios;

import com.google.common.collect.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.*;
import team.lodestar.lodestone.helpers.*;
import top.theillusivec4.curios.api.*;
import top.theillusivec4.curios.api.type.capability.*;

import java.util.function.*;

public abstract class AbstractMalumCurioItem extends Item implements ICurioItem {

    public enum MalumTrinketType {
        CLOTH(SoundRegistry.CLOTH_TRINKET_EQUIP),
        ORNATE(SoundRegistry.ORNATE_TRINKET_EQUIP),
        GILDED(SoundRegistry.GILDED_TRINKET_EQUIP),
        ALCHEMICAL(SoundRegistry.ALCHEMICAL_TRINKET_EQUIP),
        ROTTEN(SoundRegistry.ROTTEN_TRINKET_EQUIP),
        METALLIC(SoundRegistry.METALLIC_TRINKET_EQUIP),
        RUNE(SoundRegistry.RUNE_TRINKET_EQUIP),
        VOID(SoundRegistry.VOID_TRINKET_EQUIP);
        final Supplier<SoundEvent> sound;

        MalumTrinketType(Supplier<SoundEvent> sound) {
            this.sound = sound;
        }
    }

    public final MalumTrinketType type;

    public AbstractMalumCurioItem(Properties properties, MalumTrinketType type) {
        super(properties);
        this.type = type;
    }

    public void addAttributeModifiers(Multimap<Holder<Attribute>, AttributeModifier> map, SlotContext slotContext, ItemStack stack) {
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(SlotContext slotContext, ResourceLocation id, ItemStack stack) {
        Multimap<Holder<Attribute>, AttributeModifier> map = LinkedHashMultimap.create();
        addAttributeModifiers(map, slotContext, stack);
        return map;
    }

    @Override
    public void onEquipFromUse(SlotContext slotContext, ItemStack stack) {
        final LivingEntity livingEntity = slotContext.entity();
        livingEntity.level().playSound(null, livingEntity.blockPosition(), type.sound.get(), SoundSource.PLAYERS, 1.0f, RandomHelper.randomBetween(livingEntity.getRandom(), 0.9f, 1.1f));
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        ItemEnchantments list = book.get(DataComponents.ENCHANTMENTS);
        if (list.size() == 1 && list.getLevel(Enchantments.BINDING_CURSE)) {
            return true;
        }
        return super.isBookEnchantable(stack, book);
    }


    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    public void addAttributeModifier(Multimap<Holder<Attribute>, AttributeModifier> map, Holder<Attribute> attribute, AttributeModifier modifier) {
        map.put(attribute, modifier);
    }
}