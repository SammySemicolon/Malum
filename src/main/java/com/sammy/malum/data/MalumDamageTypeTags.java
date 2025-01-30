package com.sammy.malum.data;

import com.sammy.malum.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.core.HolderLookup.*;
import net.minecraft.data.*;
import net.minecraft.data.tags.*;
import net.minecraft.tags.*;
import net.neoforged.neoforge.common.data.*;
import team.lodestar.lodestone.registry.common.tag.*;

import java.util.concurrent.*;

public class MalumDamageTypeTags extends DamageTypeTagsProvider {

    public MalumDamageTypeTags(PackOutput pOutput, CompletableFuture<Provider> pProvider, ExistingFileHelper existingFileHelper) {
        super(pOutput, pProvider, MalumMod.MALUM, existingFileHelper);
    }

    @Override
    protected void addTags(Provider pProvider) {
        tag(LodestoneDamageTypeTags.CAN_TRIGGER_MAGIC).add(DamageTypeRegistry.SCYTHE_MELEE, DamageTypeRegistry.SCYTHE_SWEEP);

        tag(LodestoneDamageTypeTags.IS_MAGIC).add(
                DamageTypeRegistry.VOODOO_PLAYERLESS, DamageTypeRegistry.VOODOO,
                DamageTypeRegistry.TYRVING,
                DamageTypeRegistry.SOULWASHING_PROPAGATION, DamageTypeRegistry.SOULWASHING_RETALIATION);

        tag(DamageTypeTagRegistry.SOUL_SHATTER_DAMAGE).add(
                DamageTypeRegistry.SCYTHE_MELEE, DamageTypeRegistry.SCYTHE_SWEEP,
                DamageTypeRegistry.SCYTHE_REBOUND, DamageTypeRegistry.SCYTHE_ASCENSION,
                DamageTypeRegistry.SCYTHE_COMBO, DamageTypeRegistry.HIDDEN_BLADE_COUNTER,
                DamageTypeRegistry.VOODOO_PLAYERLESS, DamageTypeRegistry.VOODOO,
                DamageTypeRegistry.SOULWASHING_PROPAGATION, DamageTypeRegistry.SOULWASHING_RETALIATION);

        tag(DamageTypeTagRegistry.IS_SCYTHE).addTag(DamageTypeTagRegistry.IS_SCYTHE_MELEE)
                .add(DamageTypeRegistry.SCYTHE_REBOUND, DamageTypeRegistry.SCYTHE_COMBO, DamageTypeRegistry.HIDDEN_BLADE_COUNTER);
        tag(DamageTypeTagRegistry.IS_SCYTHE_MELEE)
                .add(DamageTypeRegistry.SCYTHE_MELEE, DamageTypeRegistry.SCYTHE_SWEEP, DamageTypeRegistry.SCYTHE_ASCENSION);

        tag(DamageTypeTagRegistry.IS_SOULWASHING).add(DamageTypeRegistry.SOULWASHING_PROPAGATION, DamageTypeRegistry.SOULWASHING_RETALIATION);

        tag(DamageTypeTagRegistry.SOULWASHING_BLACKLIST).addTag(DamageTypeTagRegistry.IS_SOULWASHING).add(DamageTypeRegistry.SCYTHE_SWEEP);
        tag(DamageTypeTagRegistry.LIONS_HEART_BLACKLIST).addTag(DamageTypeTags.BYPASSES_ARMOR);

        tag(DamageTypeTags.BYPASSES_COOLDOWN).add(DamageTypeRegistry.VOODOO, DamageTypeRegistry.HIDDEN_BLADE_COUNTER);
        tag(DamageTypeTags.NO_KNOCKBACK)
                .add(DamageTypeRegistry.VOODOO, DamageTypeRegistry.VOODOO_PLAYERLESS, DamageTypeRegistry.VOID)
                .addTag(DamageTypeTagRegistry.IS_SOULWASHING);
    }
}