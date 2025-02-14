package com.sammy.malum.visual_effects.networked.geas;

import com.sammy.malum.core.systems.spirit.*;
import com.sammy.malum.visual_effects.networked.*;
import com.sammy.malum.visual_effects.networked.data.*;
import net.minecraft.nbt.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.phys.*;
import net.neoforged.api.distmarker.*;

import java.util.function.*;

public class WyrdReconstructionReviveParticleEffect extends ParticleEffectType {

    public WyrdReconstructionReviveParticleEffect(String id) {
        super(id);
    }

    public static NBTEffectData createData(Entity entity) {
        CompoundTag tag = new CompoundTag();
        tag.putInt("targetId", entity.getId());
        return new NBTEffectData(tag);
    }

    @Override
    public Supplier<ParticleEffectActor> get() {
        return () -> (level, random, positionData, colorData, nbtData) -> {
            if (level.isClientSide) {
                if (!nbtData.compoundTag.contains("targetId")) {
                    return;
                }
                final Entity entity = level.getEntity(nbtData.compoundTag.getInt("targetId"));
                GeasParticleEffects.wyrdReconstructionRevive(level, entity, random, positionData, colorData);
            }
        };
    }
}