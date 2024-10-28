package com.sammy.malum.common.item.curiosities.curios.sets.misc;

import com.google.common.collect.Multimap;
import com.sammy.malum.MalumMod;
import com.sammy.malum.common.item.IMalumEventResponderItem;
import com.sammy.malum.common.item.curiosities.curios.MalumCurioItem;
import com.sammy.malum.mixin.AccessorEvent;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import top.theillusivec4.curios.api.SlotContext;

import java.util.function.Consumer;
import java.util.function.Function;

public class CurioWaterNecklace extends MalumCurioItem implements IMalumEventResponderItem {

    public static final Function<Boolean, AttributeModifier> SWIM_SPEED = (conduitPowered) -> {
        float value = 0.15f;
        if (conduitPowered) value *= 3;
        return new AttributeModifier(MalumMod.malumPath("curio_swim_speed"), value, AttributeModifier.Operation.ADD_VALUE);
    };

    public CurioWaterNecklace(Properties builder) {
        super(builder, MalumTrinketType.GILDED);
    }

    @Override
    public void addAttributeModifiers(Multimap<Holder<Attribute>, AttributeModifier> map, SlotContext slotContext, ItemStack stack) {
        super.addAttributeModifiers(map, slotContext, stack);
        addAttributeModifier(map, NeoForgeMod.SWIM_SPEED, SWIM_SPEED.apply(
                slotContext.entity().hasEffect(MobEffects.CONDUIT_POWER)
        ));
    }

    @Override
    public void addExtraTooltipLines(Consumer<Component> consumer) {
        consumer.accept(positiveEffect("better_conduit_power"));
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        LivingEntity livingEntity = slotContext.entity();
        if (livingEntity.level().getGameTime() % 40L == 0 && livingEntity.isSwimming()) {
            AttributeInstance attribute = livingEntity.getAttribute(NeoForgeMod.SWIM_SPEED);
            if (attribute != null) {
                attribute.setDirty();
            }
        }

        if (livingEntity.level().getGameTime() % 20L == 0) {
            if (livingEntity.hasEffect(MobEffects.CONDUIT_POWER)) {
                livingEntity.heal(2);
            }
        }
    }

    @Override
    public void takeDamageEvent(LivingDamageEvent.Post event, LivingEntity attacker, LivingEntity attacked, ItemStack stack) {
        if (attacked.hasEffect(MobEffects.CONDUIT_POWER)) {
            ((AccessorEvent.PostDamage)event).malum$setNewDamage(event.getNewDamage() * 0.5f);
        }
    }
}
