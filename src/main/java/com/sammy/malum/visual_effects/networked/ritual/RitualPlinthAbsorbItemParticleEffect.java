package com.sammy.malum.visual_effects.networked.ritual;

import com.sammy.malum.common.block.curiosities.ritual_plinth.*;
import com.sammy.malum.visual_effects.*;
import com.sammy.malum.visual_effects.networked.*;
import com.sammy.malum.visual_effects.networked.data.*;
import net.minecraft.nbt.*;
import net.minecraft.world.item.*;
import net.minecraft.world.phys.*;
import net.neoforged.api.distmarker.*;

import java.util.function.*;

public class RitualPlinthAbsorbItemParticleEffect extends ParticleEffectType {

    public RitualPlinthAbsorbItemParticleEffect(String id) {
        super(id);
    }

    public static NBTEffectData createData(Vec3 targetPos, ItemStack stack) {
        NBTEffectData effectData = new NBTEffectData(stack);
        final CompoundTag compoundTag = effectData.compoundTag;
        CompoundTag position = new CompoundTag();
        position.putDouble("x", targetPos.x);
        position.putDouble("y", targetPos.y);
        position.putDouble("z", targetPos.z);
        compoundTag.put("targetPosition", position);
        return effectData;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public Supplier<ParticleEffectActor> get() {
        return () -> (level, random, positionData, colorData, nbtData) -> {
            if (!(level.getBlockEntity(positionData.getAsBlockPos()) instanceof RitualPlinthBlockEntity ritualPlinth)) {
                return;
            }
            final CompoundTag compoundTag = nbtData.compoundTag.getCompound("targetPosition");
            RitualPlinthParticleEffects.eatItemParticles(ritualPlinth, new Vec3(compoundTag.getDouble("x"), compoundTag.getDouble("y"), compoundTag.getDouble("z")), colorData, nbtData.getStack());
        };
    }
}