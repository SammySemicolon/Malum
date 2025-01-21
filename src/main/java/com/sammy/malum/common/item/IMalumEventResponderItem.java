package com.sammy.malum.common.item;

import com.sammy.malum.core.systems.events.*;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import team.lodestar.lodestone.handlers.*;

public interface IMalumEventResponderItem extends ItemEventHandler.IEventResponder{


    default void spiritCollectionEvent(CollectSpiritEvent event, LivingEntity collector, double arcaneResonance) {

    }

    default void modifySoulWardPropertiesEvent(ModifySoulWardPropertiesEvent event, LivingEntity wardedEntity, ItemStack stack) {
    }

    default void soulWardDamageEvent(SoulWardDamageEvent event, LivingEntity wardedEntity, ItemStack stack) {
    }
}