package com.sammy.malum.compability.attributelib;

import com.mojang.datafixers.util.*;
import com.sammy.malum.common.capability.*;
import com.sammy.malum.core.handlers.*;
import com.sammy.malum.core.listeners.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.item.*;
import dev.shadowsoffire.attributeslib.client.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.item.*;

import java.util.*;
import java.util.function.*;

public class MalignantConversionModifierSourceType extends ModifierSourceType<ItemStack> {

    @Override
    public void extract(LivingEntity livingEntity, BiConsumer<AttributeModifier, ModifierSource<?>> map) {
        for (MalignantConversionReloadListener.MalignantConversionData data : MalignantConversionReloadListener.CONVERSION_DATA.values()) {
            var sourceAttribute = data.sourceAttribute();
            var sourceInstance = livingEntity.getAttribute(sourceAttribute);
            if (sourceInstance != null) {
                var modifier = sourceInstance.getModifier(MalignantConversionHandler.NEGATIVE_MODIFIER_UUID);
                if (modifier != null) {
                    map.accept(modifier, new ModifierSource.ItemModifierSource(ItemRegistry.MALIGNANT_PEWTER_PLATING.get().getDefaultInstance()));
                }
            }
            for (Pair<Attribute, Double> target : data.targetAttributes()) {
                var targetAttribute = target.getFirst();
                var targetInstance = livingEntity.getAttribute(targetAttribute);
                if (targetInstance != null) {
                    var modifier = targetInstance.getModifier(MalignantConversionHandler.POSITIVE_MODIFIER_UUIDS.get(targetAttribute));
                    if (modifier != null) {
                        map.accept(modifier, new ModifierSource.ItemModifierSource(ItemRegistry.MALIGNANT_PEWTER_PLATING.get().getDefaultInstance()));
                    }
                }
            }
        }
    }

    @Override
    public int getPriority() {
        return 50;
    }
}
