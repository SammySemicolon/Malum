package com.sammy.malum.visual_effects.networked.pylon;

import com.sammy.malum.common.block.curiosities.repair_pylon.*;
import com.sammy.malum.common.block.storage.*;
import com.sammy.malum.visual_effects.*;
import com.sammy.malum.visual_effects.networked.*;
import com.sammy.malum.visual_effects.networked.data.*;
import net.minecraft.core.*;
import net.minecraft.nbt.*;
import net.neoforged.api.distmarker.*;

import java.util.function.*;

public class PylonRepairParticleEffect extends ParticleEffectType {

    public PylonRepairParticleEffect(String id) {
        super(id);
    }

    public static NBTEffectData createData(BlockPos holderPos) {
        NBTEffectData effectData = new NBTEffectData(new CompoundTag());
        effectData.compoundTag.putLong("pos", holderPos.asLong());
        return effectData;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public Supplier<ParticleEffectActor> get() {
        return () -> (level, random, positionData, colorData, nbtData) -> {
            if (!(level.getBlockEntity(positionData.getAsBlockPos()) instanceof RepairPylonCoreBlockEntity pylon)) {
                return;
            }
            if (!(level.getBlockEntity(BlockPos.of(nbtData.compoundTag.getLong("pos"))) instanceof IMalumSpecialItemAccessPoint holder)) {
                return;
            }
            RepairPylonParticleEffects.repairItemParticles(pylon, holder, colorData);
        };
    }
}