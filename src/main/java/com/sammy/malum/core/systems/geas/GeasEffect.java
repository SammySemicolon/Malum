package com.sammy.malum.core.systems.geas;

import com.google.common.collect.*;
import com.sammy.malum.common.item.*;
import net.minecraft.core.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.event.tick.*;

import java.util.*;
import java.util.function.*;

public class GeasEffect implements IMalumEventResponderItem {
    public final GeasEffectType type;
    private boolean isDirty = true;
    private Multimap<Holder<Attribute>, AttributeModifier> attributeCache;

    public GeasEffect(GeasEffectType type) {
        this.type = type;
    }

    public void markDirty() {
        isDirty = true;
    }

    public void addTooltipComponents(LivingEntity entity, Consumer<Component> tooltipAcceptor, TooltipFlag tooltipFlag) {
        createAttributeModifiers(entity).entries().forEach((entry) -> addTooltipComponent(entry, tooltipAcceptor, tooltipFlag));
    }

    public final void addTooltipComponent(Map.Entry<Holder<Attribute>, AttributeModifier> entry, Consumer<Component> tooltipAcceptor, TooltipFlag flag) {
        Holder<Attribute> holder = entry.getKey();
        Attribute attribute = holder.value();
        AttributeModifier attributeModifier = entry.getValue();
        tooltipAcceptor.accept(attribute.toComponent(attributeModifier, flag));
    }

    protected Multimap<Holder<Attribute>, AttributeModifier> createAttributeModifiers(LivingEntity entity) {
        return createAttributeModifiers(entity, LinkedHashMultimap.create());
    }

    public Multimap<Holder<Attribute>, AttributeModifier> createAttributeModifiers(LivingEntity entity, Multimap<Holder<Attribute>, AttributeModifier> modifiers) {
        return LinkedHashMultimap.create();
    }

    public void addAttributeModifier(Multimap<Holder<Attribute>, AttributeModifier> modifiers, Holder<Attribute> attribute, double value, AttributeModifier.Operation operation) {
        modifiers.put(attribute, new AttributeModifier(type.getId(), value, operation));
    }

    public final void updateDirty(LivingEntity entity) {
        if (isDirty) {
            applyAttributeModifiers(entity);
            isDirty = false;
        }
    }

    public void removeAttributeModifiers(LivingEntity entity) {
        if (attributeCache != null) {
            entity.getAttributes().removeAttributeModifiers(attributeCache);
        }
    }

    private void applyAttributeModifiers(LivingEntity entity) {
        removeAttributeModifiers(entity);
        final Multimap<Holder<Attribute>, AttributeModifier> attributes = createAttributeModifiers(entity);
        entity.getAttributes().addTransientAttributeModifiers(attributes);
        attributeCache = attributes;
    }

    public void update(EntityTickEvent.Pre event, LivingEntity entity) {

    }
}