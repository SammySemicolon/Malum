package com.sammy.malum.data;

import com.sammy.malum.*;
import com.sammy.malum.registry.common.*;
import io.github.fabricators_of_create.porting_lib.data.ExistingFileHelper;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.*;
import net.minecraft.data.tags.*;
import net.minecraft.tags.*;
import net.minecraft.world.damagesource.DamageType;
import team.lodestar.lodestone.registry.common.tag.*;

import java.util.concurrent.*;

public class MalumDamageTypeTags extends FabricTagProvider<DamageType> {

    public MalumDamageTypeTags(FabricDataOutput pOutput, CompletableFuture<Provider> pProvider, ExistingFileHelper existingFileHelper) {
        super(pOutput, Registries.DAMAGE_TYPE, pProvider);
    }

    @Override
    protected void addTags(Provider pProvider) {
        tag(LodestoneDamageTypeTags.CAN_TRIGGER_MAGIC).add(DamageTypeRegistry.SCYTHE_MELEE, DamageTypeRegistry.SCYTHE_SWEEP);

        tag(LodestoneDamageTypeTags.IS_MAGIC).add(DamageTypeRegistry.VOODOO_PLAYERLESS, DamageTypeRegistry.VOODOO);
        tag(DamageTypeTagRegistry.SOUL_SHATTER_DAMAGE).add(DamageTypeRegistry.SCYTHE_MELEE, DamageTypeRegistry.SCYTHE_SWEEP, DamageTypeRegistry.VOODOO_PLAYERLESS, DamageTypeRegistry.VOODOO, DamageTypeRegistry.HIDDEN_BLADE_COUNTER);

        tag(DamageTypeTagRegistry.IS_SCYTHE).add(DamageTypeRegistry.SCYTHE_MELEE, DamageTypeRegistry.SCYTHE_SWEEP, DamageTypeRegistry.HIDDEN_BLADE_COUNTER);
        tag(DamageTypeTagRegistry.IS_SCYTHE_MELEE).add(DamageTypeRegistry.SCYTHE_MELEE, DamageTypeRegistry.SCYTHE_SWEEP);


        tag(DamageTypeTags.BYPASSES_COOLDOWN).add(DamageTypeRegistry.VOODOO, DamageTypeRegistry.HIDDEN_BLADE_COUNTER);
    }
}
