package com.sammy.malum.registry.common;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.sammy.malum.MalumMod.MALUM;
import static team.lodestar.lodestone.registry.common.LodestoneAttributes.registerAttribute;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class AttributeRegistry {
    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(BuiltInRegistries.ATTRIBUTE, MALUM);
    public static final DeferredHolder<Attribute, Attribute> SCYTHE_PROFICIENCY = registerAttribute(ATTRIBUTES, MALUM, "scythe_proficiency", (id) -> new RangedAttribute(id, 1.0D, 0.0D, 2048.0D).setSyncable(true));
    public static final DeferredHolder<Attribute, Attribute> SPIRIT_SPOILS = registerAttribute(ATTRIBUTES, MALUM, "spirit_spoils", (id) -> new RangedAttribute(id, 0.0D, 0.0D, 2048.0D).setSyncable(true));
    public static final DeferredHolder<Attribute, Attribute> ARCANE_RESONANCE = registerAttribute(ATTRIBUTES, MALUM, "arcane_resonance", (id) -> new RangedAttribute(id, 1.0D, 0.0D, 2048.0D).setSyncable(true));

    public static final DeferredHolder<Attribute, Attribute> SOUL_WARD_INTEGRITY = registerAttribute(ATTRIBUTES, MALUM, "soul_ward_integrity", (id) -> new RangedAttribute(id, 1.0D, 0.0D, 2048.0D).setSyncable(true));
    public static final DeferredHolder<Attribute, Attribute> SOUL_WARD_RECOVERY_RATE = registerAttribute(ATTRIBUTES, MALUM, "soul_ward_recovery_rate", (id) -> new RangedAttribute(id, 1.0D, 0.0D, 2048.0D).setSyncable(true));
    public static final DeferredHolder<Attribute, Attribute> SOUL_WARD_CAP = registerAttribute(ATTRIBUTES, MALUM, "soul_ward_capacity", (id) -> new RangedAttribute(id, 0D, 0.0D, 2048.0D).setSyncable(true));

    public static final DeferredHolder<Attribute, Attribute> RESERVE_STAFF_CHARGES = registerAttribute(ATTRIBUTES, MALUM, "reserve_staff_charges", (id) -> new RangedAttribute(id, 0D, 0.0D, 2048.0D).setSyncable(true));
    public static final DeferredHolder<Attribute, Attribute> MALIGNANT_CONVERSION = registerAttribute(ATTRIBUTES, MALUM, "malignant_conversion", (id) -> new RangedAttribute(id, 0D, 0.0D, 1.0D).setSyncable(true));


    @SubscribeEvent
    public static void modifyEntityAttributes(EntityAttributeModificationEvent event) {
        event.getTypes().forEach(e -> {
            event.add(e, SCYTHE_PROFICIENCY);
            event.add(e, SPIRIT_SPOILS);
            event.add(e, ARCANE_RESONANCE);

            event.add(e, SOUL_WARD_INTEGRITY);
            event.add(e, SOUL_WARD_RECOVERY_RATE);
            event.add(e, SOUL_WARD_CAP);
            event.add(e, RESERVE_STAFF_CHARGES);
            event.add(e, MALIGNANT_CONVERSION);
        });
    }
}