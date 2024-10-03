package com.sammy.malum.core.handlers;

import com.mojang.datafixers.util.*;
import com.sammy.malum.*;
import com.sammy.malum.common.capability.*;
import com.sammy.malum.common.packets.malignant_conversion.*;
import com.sammy.malum.core.listeners.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.core.*;
import net.minecraft.core.registries.*;
import net.minecraft.network.chat.*;
import net.minecraft.resources.*;
import net.minecraft.server.level.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.neoforged.neoforge.event.tick.*;

import java.util.*;

public class MalignantConversionHandler {

    public static final ResourceLocation NEGATIVE_MODIFIER_ID = MalumMod.malumPath("malignant_conversion_tally");
    public static final HashMap<Holder<Attribute>, ResourceLocation> POSITIVE_MODIFIER_IDS = new HashMap<>();

    public final HashMap<Holder<Attribute>, Double> cachedAttributeValues = new HashMap<>();
    public boolean skipConversionLogic;

    public static void checkForAttributeChanges(EntityTickEvent event) {
        if (event.getEntity() instanceof LivingEntity livingEntity) {
            if (!livingEntity.level().isClientSide) {
                var handler = MalumLivingEntityDataCapability.getCapability(livingEntity).malignantConversionHandler;
                var conversionAttribute = AttributeRegistry.MALIGNANT_CONVERSION;
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
                        Holder<Attribute> attribute = data.sourceAttribute();
                        AttributeInstance instance = livingEntity.getAttribute(attribute);
                        if (instance != null) {
                            if (handler.cachedAttributeValues.containsKey(attribute)) {
                                convertAttribute(handler, livingEntity, data.sourceAttribute(), data.consumptionRatio(), data.ignoreBaseValue(), data.targetAttributes(), false);
                            }
                        }
                    }
                    if (handler.cachedAttributeValues.containsKey(conversionAttribute)) { //update attributes when malignant conversion changes
                        if (handler.cachedAttributeValues.get(conversionAttribute) != conversionInstance.getValue()) {
                            for (MalignantConversionReloadListener.MalignantConversionData data : conversionData.values()) {
                                convertAttribute(handler, livingEntity, data.sourceAttribute(), data.consumptionRatio(), data.ignoreBaseValue(), data.targetAttributes(), true);
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
    }

    private static void convertAttribute(MalignantConversionHandler handler, LivingEntity livingEntity, Holder<Attribute> sourceAttribute, double consumptionRatio, boolean ignoreBaseValue, List<Pair<Holder<Attribute>, Double>> targetAttributes, boolean skipCacheComparison) {
        var attributes = livingEntity.getAttributes();
        double malignantConversion = attributes.getValue(AttributeRegistry.MALIGNANT_CONVERSION);

        var sourceInstance = livingEntity.getAttribute(sourceAttribute);
        if (sourceInstance != null) {
            var originalModifier = sourceInstance.getModifier(NEGATIVE_MODIFIER_ID);
            if (originalModifier != null) {
                sourceInstance.removeModifier(originalModifier);
            }
            boolean hasMalignantConversion = malignantConversion > 0;
            if (skipCacheComparison || handler.cachedAttributeValues.get(sourceAttribute) != sourceInstance.getValue()) {
                double cachedValue = sourceInstance.getValue() - (ignoreBaseValue ? sourceInstance.getBaseValue() : 0);
                for (Pair<Holder<Attribute>, Double> target : targetAttributes) {
                    var targetAttribute = target.getFirst();
                    var targetInstance = livingEntity.getAttribute(targetAttribute);
                    if (targetInstance != null) {
                        var id = POSITIVE_MODIFIER_IDS.computeIfAbsent(sourceAttribute, MalignantConversionHandler::createPositiveModifierId);
                        targetInstance.removeModifier(id);
                        double bonus = cachedValue * malignantConversion * target.getSecond();
                        if (bonus > 0) {
                            var modifier = new AttributeModifier(id, bonus, AttributeModifier.Operation.ADD_VALUE);
                            targetInstance.addTransientModifier(modifier);
                        }
                    }
                }
                handler.cachedAttributeValues.put(sourceAttribute, sourceInstance.getValue());
                if (hasMalignantConversion) {
                    final double negativeOffset = -malignantConversion * consumptionRatio;
                    var modifier = new AttributeModifier(NEGATIVE_MODIFIER_ID, negativeOffset, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
                    sourceInstance.addTransientModifier(modifier);
                }
            }
            if (originalModifier != null && sourceInstance.getModifier(NEGATIVE_MODIFIER_ID) == null && hasMalignantConversion) {
                sourceInstance.addTransientModifier(originalModifier);
            }
        }
    }

    private static ResourceLocation createPositiveModifierId(Holder<Attribute> attribute) {
        return MalumMod.malumPath("malignant_conversion_buff_from_" + BuiltInRegistries.ATTRIBUTE.getKey(attribute.value())); //TODO: this feels a bit wrong
    }
}
