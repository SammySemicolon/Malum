package com.sammy.malum.common.item.curiosities.curios.sets.weeping;

import com.google.common.collect.Multimap;
import com.sammy.malum.MalumMod;
import com.sammy.malum.common.item.IMalumEventResponderItem;
import com.sammy.malum.common.item.IVoidItem;
import com.sammy.malum.common.item.curiosities.curios.MalumCurioItem;
import com.sammy.malum.core.systems.events.*;
import com.sammy.malum.registry.common.AttributeRegistry;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.util.*;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;

import java.util.function.Consumer;

public class CurioLimitlessBelt extends MalumCurioItem implements IMalumEventResponderItem, IVoidItem {

    public CurioLimitlessBelt(Properties builder) {
        super(builder, MalumTrinketType.VOID);
    }

    @Override
    public void addExtraTooltipLines(Consumer<Component> consumer) {
        consumer.accept(positiveEffect("soul_ward_complete_absorption"));
        consumer.accept(positiveEffect("soul_ward_escalating_integrity"));
    }

    @Override
    public void addAttributeModifiers(Multimap<Holder<Attribute>, AttributeModifier> map, SlotContext slotContext, ItemStack stack) {
        var id = MalumMod.malumPath("belt_of_the_limitless");
        addAttributeModifier(map, AttributeRegistry.SOUL_WARD_CAPACITY, new AttributeModifier(id, 1f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        addAttributeModifier(map, AttributeRegistry.SOUL_WARD_RECOVERY_RATE, new AttributeModifier(id, -0.25f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
    }

    @Override
    public void modifySoulWardPropertiesEvent(ModifySoulWardPropertiesEvent event, Player wardedEntity, ItemStack stack) {
        var handler = event.getSoulWardHandler();

        double capacity = wardedEntity.getAttributeValue(AttributeRegistry.SOUL_WARD_CAPACITY);
        double delta = (1 - handler.getSoulWard() / capacity);

        double integrityMultiplier = Mth.lerp(delta, 0.75f, 2f);
        event.setNewIntegrity(event.getNewIntegrity() * integrityMultiplier);
        event.setNewMagicDamageAbsorption(1f);
        event.setNewPhysicalDamageAbsorption(1f);
    }
}
