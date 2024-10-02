package com.sammy.malum.core.handlers;

import com.mojang.datafixers.util.*;
import com.sammy.malum.common.capability.*;
import com.sammy.malum.core.listeners.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.network.chat.*;
import net.minecraft.util.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

import java.util.*;

public class MalignantConversionHandler {

    private static final UUID MALIGNANT_CONVERSION_UUID = UUID.fromString("ff803d68-a615-4279-a59a-d847db2481d7");
    public static final HashMap<Attribute, UUID> MODIFIER_UUIDS = new HashMap<>();

    public final HashMap<Attribute, Double> cachedAttributeValues = new HashMap<>();
    public boolean skipConversionLogic;

    public static void checkForAttributeChanges(EntityTickEvent event) {
        if (event.getEntity() instanceof LivingEntity livingEntity && !livingEntity.level().isClientSide) {
            var handler = MalumLivingEntityDataCapability.getCapability(livingEntity).malignantConversionHandler;
            final Attribute conversionAttribute = AttributeRegistry.MALIGNANT_CONVERSION.get();
            AttributeInstance conversionInstance = livingEntity.getAttribute(conversionAttribute);
            if (conversionInstance != null) {
                if (handler.skipConversionLogic) {
                    if (conversionInstance.getValue() == 0) {
                        return;
                    }
                    handler.skipConversionLogic = false;
                }
                var conversionData = MalignantConversionReloadListener.CONVERSION_DATA;
                for (MalignantConversionReloadListener.MalignantConversionData data : conversionData.values()) { //check for any changed attributes, and apply malignant conversion to them if they've been updated
                    Attribute attribute = data.sourceAttribute();
                    AttributeInstance instance = livingEntity.getAttribute(attribute);
                    if (instance != null) {
                        if (handler.cachedAttributeValues.containsKey(attribute)) {
                            convertAttribute(livingEntity, data.sourceAttribute(), data.consumptionRatio(), data.ignoreBaseValue(), data.targetAttributes(), false);
                        }
                    }
                }
                if (handler.cachedAttributeValues.containsKey(conversionAttribute)) { //update attributes when malignant conversion changes
                    if (handler.cachedAttributeValues.get(conversionAttribute) != conversionInstance.getValue()) {
                        for (MalignantConversionReloadListener.MalignantConversionData data : conversionData.values()) {
                            convertAttribute(livingEntity, data.sourceAttribute(), data.consumptionRatio(), data.ignoreBaseValue(), data.targetAttributes(), true);
                        }
                    }
                }
                handler.cachedAttributeValues.put(conversionAttribute, conversionInstance.getValue());
                if (conversionInstance.getValue() == 0) {
                    handler.skipConversionLogic = true;
                }
            }
        }
    }

    private static void convertAttribute(LivingEntity livingEntity, Attribute sourceAttribute, double consumptionRatio, boolean ignoreBaseValue, List<Pair<Attribute, Double>> targetAttributes, boolean skipCacheComparison) {
        var attributes = livingEntity.getAttributes();
        double malignantConversion = attributes.getValue(AttributeRegistry.MALIGNANT_CONVERSION);

        final AttributeInstance sourceInstance = livingEntity.getAttribute(sourceAttribute);
        if (sourceInstance != null) {
            var handler = MalumLivingEntityDataCapability.getCapability(livingEntity).malignantConversionHandler;
            final AttributeModifier originalModifier = sourceInstance.getModifier(MALIGNANT_CONVERSION_UUID);
            if (originalModifier != null) {
                sourceInstance.removeModifier(originalModifier);
            }
            if (skipCacheComparison || handler.cachedAttributeValues.get(sourceAttribute) != sourceInstance.getValue()) {
                double cachedValue = sourceInstance.getValue() - (ignoreBaseValue ? sourceInstance.getBaseValue() : 0);
                for (Pair<Attribute, Double> target : targetAttributes) {
                    final Attribute targetAttribute = target.getFirst();
                    final AttributeInstance targetInstance = livingEntity.getAttribute(targetAttribute);
                    if (targetInstance != null) {
                        final UUID uuid = MODIFIER_UUIDS.computeIfAbsent(sourceAttribute, a -> Mth.createInsecureUUID(RandomSource.createNewThreadLocalInstance()));
                        targetInstance.removeModifier(uuid);
                        final double bonus = cachedValue * malignantConversion * target.getSecond();
                        if (bonus > 0) {
                            targetInstance.addTransientModifier(
                                    new AttributeModifier(uuid, "Malignant Conversion: " + Component.translatable(targetAttribute.getDescriptionId()),
                                            bonus, AttributeModifier.Operation.ADD_VALUE));
                        }
                    }
                }
                handler.cachedAttributeValues.put(sourceAttribute, sourceInstance.getValue());
                if (malignantConversion > 0) {
                    sourceInstance.addTransientModifier(
                            new AttributeModifier(MALIGNANT_CONVERSION_UUID, "Malignant Conversion: " + Component.translatable(sourceAttribute.getDescriptionId()),
                                    -malignantConversion*consumptionRatio, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
                }
            }
            if (originalModifier != null && sourceInstance.getModifier(MALIGNANT_CONVERSION_UUID) == null && malignantConversion > 0) {
                sourceInstance.addTransientModifier(originalModifier);
            }
        }
    }
}
