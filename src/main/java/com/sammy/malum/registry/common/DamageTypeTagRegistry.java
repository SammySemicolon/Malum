package com.sammy.malum.registry.common;

import com.sammy.malum.*;
import net.minecraft.core.registries.*;
import net.minecraft.resources.*;
import net.minecraft.tags.*;
import net.minecraft.world.damagesource.*;
import team.lodestar.lodestone.registry.common.tag.*;

public class DamageTypeTagRegistry {

    public static final TagKey<DamageType> SOUL_SHATTER_DAMAGE = malumTag("can_soul_shatter");
    public static final TagKey<DamageType> IS_SCYTHE = malumTag("is_scythe");
    public static final TagKey<DamageType> IS_SCYTHE_MELEE = malumTag("is_scythe_melee");

    private static TagKey<DamageType> modTag(String path) {
        return TagKey.create(Registries.DAMAGE_TYPE, ResourceLocation.tryParse(path));
    }

    public static TagKey<DamageType> malumTag(String path) {
        return TagKey.create(Registries.DAMAGE_TYPE, MalumMod.malumPath(path));
    }

    public static TagKey<DamageType> forgeTag(String path) {
        return LodestoneDamageTypeTags.forgeTag(path);
    }
}
