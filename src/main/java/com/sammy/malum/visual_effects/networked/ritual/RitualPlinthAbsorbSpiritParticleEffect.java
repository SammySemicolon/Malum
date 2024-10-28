package com.sammy.malum.visual_effects.networked.ritual;

import com.sammy.malum.common.block.curiosities.ritual_plinth.RitualPlinthBlockEntity;
import com.sammy.malum.visual_effects.RitualPlinthParticleEffects;
import com.sammy.malum.visual_effects.networked.ParticleEffectType;
import com.sammy.malum.visual_effects.networked.data.NBTEffectData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

import net.neoforged.api.distmarker.*;

import java.util.function.Supplier;

public class RitualPlinthAbsorbSpiritParticleEffect extends ParticleEffectType {

    public RitualPlinthAbsorbSpiritParticleEffect(String id) {
        super(id);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public Supplier<ParticleEffectActor> get() {
        return () -> (level, random, positionData, colorData, nbtData) -> {
            if (!(level.getBlockEntity(positionData.getAsBlockPos()) instanceof RitualPlinthBlockEntity ritualPlinth)) {
                return;
            }
            final CompoundTag compoundTag = nbtData.compoundTag.getCompound("targetPosition");
            RitualPlinthParticleEffects.eatSpiritParticles(ritualPlinth, new Vec3(compoundTag.getDouble("x"), compoundTag.getDouble("y"), compoundTag.getDouble("z")), colorData, nbtData.getStack());
        };
    }
}