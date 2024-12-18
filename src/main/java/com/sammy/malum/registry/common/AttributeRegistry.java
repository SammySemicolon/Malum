package com.sammy.malum.registry.common;

import io.github.fabricators_of_create.porting_lib.util.DeferredHolder;
import io.github.fabricators_of_create.porting_lib.util.DeferredRegister;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import team.lodestar.lodestone.registry.common.LodestoneAttributes;

import static com.sammy.malum.MalumMod.MALUM;
import static team.lodestar.lodestone.registry.common.LodestoneAttributes.register;

public class AttributeRegistry {
    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(BuiltInRegistries.ATTRIBUTE, MALUM);
    public static final Holder<Attribute> SCYTHE_PROFICIENCY = register("scythe_proficiency", new RangedAttribute("scythe_proficiency",1.0D, 0.0D, 2048.0D).setSyncable(true));
    public static final Holder<Attribute> SPIRIT_SPOILS = register("spirit_spoils", new RangedAttribute("spirit_spoils", 0.0D, 0.0D, 2048.0D).setSyncable(true));
    public static final Holder<Attribute> ARCANE_RESONANCE = register("arcane_resonance", new RangedAttribute("arcane_resonance",1.0D, 0.0D, 2048.0D).setSyncable(true));

    public static final Holder<Attribute> SOUL_WARD_INTEGRITY = register("soul_ward_integrity", new RangedAttribute("soul_ward_integrity",1.0D, 0.0D, 2048.0D).setSyncable(true));
    public static final Holder<Attribute> SOUL_WARD_RECOVERY_RATE = register("soul_ward_recovery_rate", new RangedAttribute("soul_ward_recovery_rate",1.0D, 0.0D, 2048.0D).setSyncable(true));
    public static final Holder<Attribute> SOUL_WARD_RECOVERY_MULTIPLIER = register("soul_ward_multiplier", new RangedAttribute("soul_ward_multiplier", 0D, 0.0D, 2048.0D).setSyncable(true));
    public static final Holder<Attribute> SOUL_WARD_CAPACITY = register("soul_ward_capacity", new RangedAttribute("soul_ward_capacity",0D, 0.0D, 2048.0D).setSyncable(true));

    public static final Holder<Attribute> RESERVE_STAFF_CHARGES = register("reserve_staff_charges", new RangedAttribute("reserve_staff_charges",0D, 0.0D, 2048.0D).setSyncable(true));
    public static final Holder<Attribute> MALIGNANT_CONVERSION = register("malignant_conversion", new RangedAttribute("malignant_conversion", 0D, 0.0D, 1.0D).setSyncable(true));

    public static void modifyEntityAttributes() {
        //Moved to LivingEntityMixin
    }
}