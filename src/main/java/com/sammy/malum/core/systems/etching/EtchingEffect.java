package com.sammy.malum.core.systems.etching;

import com.google.common.collect.*;
import com.sammy.malum.common.item.*;
import net.minecraft.core.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.neoforged.neoforge.event.tick.*;

public class EtchingEffect implements IMalumEventResponderItem {
    public final EtchingEffectType type;
    private boolean isDirty = true;
    private Multimap<Holder<Attribute>, AttributeModifier> attributeCache;

    public EtchingEffect(EtchingEffectType type) {
        this.type = type;
    }

    public void markDirty() {
        isDirty = true;
    }

    private Multimap<Holder<Attribute>, AttributeModifier> getAttributes(LivingEntity entity) {
        return getAttributes(entity, LinkedHashMultimap.create());
    }

    public Multimap<Holder<Attribute>, AttributeModifier> getAttributes(LivingEntity entity, Multimap<Holder<Attribute>, AttributeModifier> modifiers) {
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
        final Multimap<Holder<Attribute>, AttributeModifier> attributes = getAttributes(entity);
        entity.getAttributes().addTransientAttributeModifiers(attributes);
        attributeCache = attributes;
    }

    public void update(EntityTickEvent event) {

    }
}