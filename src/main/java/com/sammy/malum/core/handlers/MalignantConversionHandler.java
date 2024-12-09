package com.sammy.malum.core.handlers;

import com.mojang.datafixers.util.*;
import com.sammy.malum.*;
import com.sammy.malum.common.capabilities.*;
import com.sammy.malum.core.listeners.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.core.*;
import net.minecraft.core.registries.*;
import net.minecraft.resources.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.neoforged.neoforge.event.tick.*;

import java.util.*;

public class MalignantConversionHandler {

    public static final ResourceLocation NEGATIVE_MODIFIER_ID = MalumMod.malumPath("malignant_conversion_tally");
    public static final HashMap<Holder<Attribute>, ResourceLocation> POSITIVE_MODIFIER_IDS = new HashMap<>();

    public static void entityTick(EntityTickEvent.Pre event) {
        if (event.getEntity() instanceof LivingEntity livingEntity) {
            if (!livingEntity.level().isClientSide) {
                var data = livingEntity.getData(AttachmentTypeRegistry.MALIGNANT_INFLUENCE);
                var conversionAttribute = AttributeRegistry.MALIGNANT_CONVERSION;
                AttributeInstance conversionInstance = livingEntity.getAttribute(conversionAttribute);
                if (conversionInstance != null) {
                    if (data.skipConversionLogic) {
                        if (conversionInstance.getValue() == 0) {
                            return;
                        }
                        data.skipConversionLogic = false;
                    }
                    var values = MalignantConversionReloadListener.CONVERSION_DATA.values();
                    for (MalignantConversionReloadListener.MalignantConversionData conversionData : values) { //check for any changed attributes, and apply malignant conversion to them if they've been updated
                        Holder<Attribute> attribute = conversionData.sourceAttribute();
                        AttributeInstance instance = livingEntity.getAttribute(attribute);
                        if (instance != null) {
                            if (data.cachedAttributeValues.containsKey(attribute)) {
                                convertAttribute(data, livingEntity, conversionData.sourceAttribute(), conversionData.consumptionRatio(), conversionData.ignoreBaseValue(), conversionData.targetAttributes(), false);
                            }
                        }
                    }
                    if (data.cachedAttributeValues.containsKey(conversionAttribute)) { //update attributes when the malignant conversion attribute itself changes
                        if (data.cachedAttributeValues.get(conversionAttribute) != conversionInstance.getValue()) {
                            for (MalignantConversionReloadListener.MalignantConversionData conversionData : values) {
                                convertAttribute(data, livingEntity, conversionData.sourceAttribute(), conversionData.consumptionRatio(), conversionData.ignoreBaseValue(), conversionData.targetAttributes(), true);
                            }
                        }
                    }
                    data.cachedAttributeValues.put(conversionAttribute, conversionInstance.getValue());
                    if (conversionInstance.getValue() == 0) {
                        data.skipConversionLogic = true;
                    }
                }
            }
        }
    }

    private static void convertAttribute(MalignantInfluenceData data, LivingEntity livingEntity, Holder<Attribute> sourceAttribute, double consumptionRatio, boolean ignoreBaseValue, List<Pair<Holder<Attribute>, Double>> targetAttributes, boolean skipCacheComparison) {
        var attributes = livingEntity.getAttributes();
        double malignantConversion = attributes.getValue(AttributeRegistry.MALIGNANT_CONVERSION);

        var sourceInstance = livingEntity.getAttribute(sourceAttribute);
        if (sourceInstance != null) {
            var originalModifier = sourceInstance.getModifier(NEGATIVE_MODIFIER_ID);
            if (originalModifier != null) {
                sourceInstance.removeModifier(originalModifier);
            }
            boolean hasMalignantConversion = malignantConversion > 0;
            if (skipCacheComparison || data.cachedAttributeValues.get(sourceAttribute) != sourceInstance.getValue()) {
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
                data.cachedAttributeValues.put(sourceAttribute, sourceInstance.getValue());
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
        return MalumMod.malumPath("malignant_conversion_buff_from_" + BuiltInRegistries.ATTRIBUTE.getKey(attribute.value()));
    }
}