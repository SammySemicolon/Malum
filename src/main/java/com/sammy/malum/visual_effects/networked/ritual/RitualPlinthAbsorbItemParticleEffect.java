package com.sammy.malum.visual_effects.networked.ritual;

import com.sammy.malum.common.block.curiosities.ritual_plinth.RitualPlinthBlockEntity;
import com.sammy.malum.visual_effects.RitualPlinthParticleEffects;
import com.sammy.malum.visual_effects.networked.ParticleEffectType;
import com.sammy.malum.visual_effects.networked.data.ColorEffectData;
import com.sammy.malum.visual_effects.networked.data.NBTEffectData;
import com.sammy.malum.visual_effects.networked.data.PositionEffectData;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.function.Supplier;

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

    @Environment(EnvType.CLIENT)
    @Override
    public Supplier<ParticleEffectActor> get() {
        return new Supplier<>() {
            @Environment(EnvType.CLIENT)
            @Override
            public ParticleEffectActor get() {
                return new ParticleEffectActor() {
                    @Environment(EnvType.CLIENT)
                    @Override
                    public void act(Level level, RandomSource random, PositionEffectData positionData, ColorEffectData colorData, NBTEffectData nbtData) {
                        if (!(level.getBlockEntity(positionData.getAsBlockPos()) instanceof RitualPlinthBlockEntity ritualPlinth)) {
                            return;
                        }
                        final CompoundTag compoundTag = nbtData.compoundTag.getCompound("targetPosition");
                        RitualPlinthParticleEffects.eatItemParticles(ritualPlinth, new Vec3(compoundTag.getDouble("x"), compoundTag.getDouble("y"), compoundTag.getDouble("z")), colorData, nbtData.getStack());
                    }
                };
            }
        };
    }
}