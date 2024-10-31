package com.sammy.malum.common.item;

import com.sammy.malum.core.systems.events.*;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.living.*;
import team.lodestar.lodestone.handlers.*;

public interface IMalumEventResponderItem extends ItemEventHandler.IEventResponderItem {
    default void pickupSpirit(LivingEntity collector, double arcaneResonance) {

    }

    default void modifySoulWardProperties(ModifySoulWardPropertiesEvent event, Player wardedEntity, ItemStack stack) {
    }

    default void incomingSoulWardDamageEvent(LivingDamageEvent.Pre event, Player wardedEntity, ItemStack stack, double soulwardLost, float damageAbsorbed) {
    }
}