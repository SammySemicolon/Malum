package com.sammy.malum.common.item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.living.*;
import team.lodestar.lodestone.systems.item.IEventResponderItem;

public interface IMalumEventResponderItem extends IEventResponderItem {
    default void pickupSpirit(LivingEntity collector, double arcaneResonance) {

    }

    default float adjustSoulWardDamageAbsorption(LivingDamageEvent.Post event, Player wardedEntity, ItemStack stack, float original) {
        return adjustSoulWardDamageAbsorption(wardedEntity, stack, original);
    }

    default float adjustSoulWardDamageAbsorption(Player wardedEntity, ItemStack stack, float original) {
        return original;
    }

    default void onSoulwardAbsorbDamage(LivingDamageEvent.Post event, Player wardedEntity, ItemStack stack, double soulwardLost, float damageAbsorbed) {
        onSoulwardAbsorbDamage(wardedEntity, stack, soulwardLost, damageAbsorbed);
    }

    default void onSoulwardAbsorbDamage(Player wardedEntity, ItemStack stack, double soulwardLost, float damageAbsorbed) {

    }
}