package com.sammy.malum.common.item.curiosities.curios.runes.miracle;

import com.google.common.collect.Multimap;
import com.sammy.malum.MalumMod;
import com.sammy.malum.common.item.curiosities.curios.runes.*;
import com.sammy.malum.registry.common.SpiritTypeRegistry;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public class RuneDexterityItem extends AbstractRuneCurioItem {

    public static final BiFunction<Float, Float, AttributeModifier> MOVEMENT_SPEED = (health, maxHealth) -> {
        float value = 0.2f * (2 - (health / maxHealth));
        return new AttributeModifier(MalumMod.malumPath("curio_movement_speed"), value, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
    };

    public RuneDexterityItem(Properties builder) {
        super(builder, SpiritTypeRegistry.AERIAL_SPIRIT);
    }

    @Override
    public void addExtraTooltipLines(Consumer<Component> consumer) {
        consumer.accept(positiveEffect("low_health_speed"));
    }

    @Override
    public void addAttributeModifiers(Multimap<Holder<Attribute>, AttributeModifier> map, SlotContext slotContext, ItemStack stack) {
        addAttributeModifier(map, Attributes.MOVEMENT_SPEED, MOVEMENT_SPEED.apply(
                slotContext.entity().getHealth(), slotContext.entity().getMaxHealth()
        ));
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        LivingEntity livingEntity = slotContext.entity();
        if (livingEntity.level().getGameTime() % 5L == 0) {
            AttributeInstance attribute = livingEntity.getAttribute(Attributes.MOVEMENT_SPEED);
            if (attribute != null) {
                attribute.setDirty();
            }
        }
    }
}
