 package com.sammy.malum.common.item.curiosities.curios.sets.soulward;

import com.google.common.collect.Multimap;
import com.sammy.malum.MalumMod;
import com.sammy.malum.common.item.IMalumEventResponderItem;
import com.sammy.malum.common.item.curiosities.curios.MalumCurioItem;
import com.sammy.malum.core.helpers.*;
import com.sammy.malum.core.systems.events.*;
import com.sammy.malum.registry.common.AttributeRegistry;
import dev.emi.trinkets.api.SlotReference;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import team.lodestar.lodestone.registry.common.tag.LodestoneDamageTypeTags;

import java.util.function.Consumer;

public class CurioMagebaneBelt extends MalumCurioItem implements IMalumEventResponderItem {

    public CurioMagebaneBelt(Properties builder) {
        super(builder, MalumTrinketType.ORNATE);
    }

    @Override
    public void addExtraTooltipLines(Consumer<Component> consumer) {
        consumer.accept(ComponentHelper.positiveCurioEffect("soul_ward_magic_resilience"));
        consumer.accept(ComponentHelper.negativeCurioEffect("soul_ward_long_shatter_cooldown"));
    }

    @Override
    public void modifySoulWardPropertiesEvent(ModifySoulWardPropertiesEvent event, LivingEntity wardedEntity, ItemStack stack) {
        if (event.getSource().is(LodestoneDamageTypeTags.IS_MAGIC)) {
            event.setNewIntegrity(event.getOriginalIntegrity()*1.5f);
        }
    }

    @Override
    public void soulWardDamageEvent(SoulWardDamageEvent event, LivingEntity wardedEntity, ItemStack stack) {
        var handler = event.getSoulWardHandler();

        if (handler.isDepleted()) {
            handler.addCooldown(wardedEntity, 8f);
            return;
        }
        if (event.getSource().is(LodestoneDamageTypeTags.IS_MAGIC)) {
            handler.recoverSoulWard(wardedEntity, 1.5f);
        }
    }

    @Override
    public void addAttributeModifiers(Multimap<Holder<Attribute>, AttributeModifier> map, SlotReference slot, ItemStack stack, LivingEntity living) {
        addAttributeModifier(map, AttributeRegistry.SOUL_WARD_RECOVERY_RATE,
                new AttributeModifier(MalumMod.malumPath("curio_soul_ward_recovery_speed"), 0.4f, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
        addAttributeModifier(map, AttributeRegistry.SOUL_WARD_CAPACITY,
                new AttributeModifier(MalumMod.malumPath("curio_soul_ward_capacity"), 6f, AttributeModifier.Operation.ADD_VALUE));
    }
}
