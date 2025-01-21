package com.sammy.malum.registry.common;

import com.sammy.malum.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import team.lodestar.lodestone.*;
import team.lodestar.lodestone.systems.attribute.*;

import static com.sammy.malum.MalumMod.MALUM;
import static team.lodestar.lodestone.registry.common.LodestoneAttributes.registerAttribute;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class AttributeRegistry {
    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(BuiltInRegistries.ATTRIBUTE, MALUM);

    public static final DeferredHolder<Attribute, Attribute> SCYTHE_PROFICIENCY = registerAttribute(ATTRIBUTES,
            LodestoneRangedAttribute.create(MalumMod.malumPath("scythe_proficiency"), 1.0D, 0.0D, 2048.0D).setAsBaseAttribute().setSyncable(true));
    public static final DeferredHolder<Attribute, Attribute> SPIRIT_SPOILS = registerAttribute(ATTRIBUTES,
            LodestoneRangedAttribute.create(MalumMod.malumPath("spirit_spoils"), 0.0D, 0.0D, 2048.0D).setSyncable(true));
    public static final DeferredHolder<Attribute, Attribute> ARCANE_RESONANCE = registerAttribute(ATTRIBUTES,
            LodestoneRangedAttribute.create(MalumMod.malumPath("arcane_resonance"), 1.0D, 0.0D, 2048.0D).setSyncable(true));

    public static final DeferredHolder<Attribute, Attribute> HEALING_MULTIPLIER = registerAttribute(ATTRIBUTES,
            LodestoneRangedAttribute.create(MalumMod.malumPath("healing_received"), 1.0D, 0.0D, 2048.0D).setSyncable(true));

    public static final DeferredHolder<Attribute, Attribute> SOUL_WARD_INTEGRITY = registerAttribute(ATTRIBUTES,
            LodestoneRangedAttribute.create(MalumMod.malumPath("soul_ward_integrity"), 1.0D, 0.0D, 2048.0D).setSyncable(true));
    public static final DeferredHolder<Attribute, Attribute> SOUL_WARD_RECOVERY_RATE = registerAttribute(ATTRIBUTES,
            LodestoneRangedAttribute.create(MalumMod.malumPath("soul_ward_recovery_rate"), 1.0D, 0.0D, 2048.0D).setSyncable(true));
    public static final DeferredHolder<Attribute, Attribute> SOUL_WARD_RECOVERY_MULTIPLIER = registerAttribute(ATTRIBUTES,
            LodestoneRangedAttribute.create(MalumMod.malumPath("soul_ward_multiplier"), 1.0D, 0.0D, 2048.0D).setSyncable(true));
    public static final DeferredHolder<Attribute, Attribute> SOUL_WARD_CAPACITY = registerAttribute(ATTRIBUTES,
            LodestoneRangedAttribute.create(MalumMod.malumPath("soul_ward_capacity"), 0D, 0.0D, 2048.0D).setSyncable(true));

    public static final DeferredHolder<Attribute, Attribute> CHARGE_DURATION = registerAttribute(ATTRIBUTES,
            LodestoneRangedAttribute.create(MalumMod.malumPath("charge_duration"), 0D, 0.0D, 2048.0D).setAsBaseAttribute().setSentiment(Attribute.Sentiment.NEGATIVE).setSyncable(true));
    public static final DeferredHolder<Attribute, Attribute> RESERVE_STAFF_CHARGES = registerAttribute(ATTRIBUTES,
            LodestoneRangedAttribute.create(MalumMod.malumPath("reserve_staff_charges"), 0D, 0.0D, 2048.0D).setSyncable(true));

    public static final DeferredHolder<Attribute, Attribute> MALIGNANT_CONVERSION = registerAttribute(ATTRIBUTES,
            LodestoneRangedAttribute.create(MalumMod.malumPath("malignant_conversion"), 0D, 0.0D, 1.0D).forcePercentageDisplay().setSyncable(true));

    @SubscribeEvent
    public static void modifyEntityAttributes(EntityAttributeModificationEvent event) {
        event.getTypes().forEach(e -> {
            for (DeferredHolder<Attribute, ? extends Attribute> entry : ATTRIBUTES.getEntries()) {
                event.add(e, entry);
            }
        });
    }
}