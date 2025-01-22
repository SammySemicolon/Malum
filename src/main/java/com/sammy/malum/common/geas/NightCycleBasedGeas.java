package com.sammy.malum.common.geas;

import com.google.common.collect.*;
import com.sammy.malum.core.systems.etching.*;
import net.minecraft.*;
import net.minecraft.core.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.event.tick.*;

import java.util.function.*;

public abstract class NightCycleBasedGeas extends GeasEffect {

    public boolean isNight;

    public NightCycleBasedGeas(GeasEffectType type) {
        super(type);
    }

    @Override
    public void addTooltipComponents(LivingEntity entity, Consumer<Component> tooltipAcceptor, TooltipFlag tooltipFlag) {
        tooltipAcceptor.accept(Component.translatable("malum.gui.geas.day_effect").withStyle(ChatFormatting.GOLD));
        createAttributeModifiers(entity, false).entries().forEach((entry) -> addTooltipComponent(entry, tooltipAcceptor, tooltipFlag));
        tooltipAcceptor.accept(Component.empty());
        tooltipAcceptor.accept(Component.translatable("malum.gui.geas.night_effect").withStyle(ChatFormatting.GOLD));
        createAttributeModifiers(entity, true).entries().forEach((entry) -> addTooltipComponent(entry, tooltipAcceptor, tooltipFlag));
    }

    @Override
    public void update(EntityTickEvent event) {
        boolean wasNight = isNight;
        isNight = event.getEntity().level().isNight();
        if (wasNight != isNight) {
            markDirty();
        }
    }


    public final Multimap<Holder<Attribute>, AttributeModifier> createAttributeModifiers(LivingEntity entity, Multimap<Holder<Attribute>, AttributeModifier> modifiers) {
        return createAttributeModifiers(entity, modifiers, isNight);
    }

    public Multimap<Holder<Attribute>, AttributeModifier> createAttributeModifiers(LivingEntity entity, boolean isNight) {
        return createAttributeModifiers(entity, LinkedHashMultimap.create(), isNight);
    }
    public Multimap<Holder<Attribute>, AttributeModifier> createAttributeModifiers(LivingEntity entity, Multimap<Holder<Attribute>, AttributeModifier> modifiers, boolean isNight) {
        return LinkedHashMultimap.create();
    }
}