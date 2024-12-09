package com.sammy.malum.common.capabilities.soul_data;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.*;
import net.neoforged.neoforge.event.entity.living.*;

import java.util.*;

public class LivingSoulData {

    public static final Codec<LivingSoulData> CODEC = RecordCodecBuilder.create(obj -> obj.group(
            Codec.FLOAT.fieldOf("exposedSoulDuration").forGetter(sd -> sd.exposedSoulDuration),
            Codec.BOOL.fieldOf("soulless").forGetter(sd -> sd.soulless),
            Codec.BOOL.fieldOf("spawnerSpawned").forGetter(sd -> sd.spawnerSpawned)
    ).apply(obj, LivingSoulData::new));

    private float exposedSoulDuration;
    private boolean soulless;
    private boolean spawnerSpawned;

    public LivingSoulData() {
    }

    private LivingSoulData(float exposedSoulDuration, boolean soulless, boolean spawnerSpawned) {
        this.exposedSoulDuration = exposedSoulDuration;
        this.soulless = soulless;
        this.spawnerSpawned = spawnerSpawned;
    }

    public void setExposed() {
        exposedSoulDuration = 200;
    }

    public void updateSoullessBehavior(Mob mob) {
        if (isSoulless()) {
            ArrayList<Class<? extends Goal>> goalsToRemove = new ArrayList<>(List.of(
                    LookAtPlayerGoal.class, MeleeAttackGoal.class, SwellGoal.class, PanicGoal.class, RandomLookAroundGoal.class, AvoidEntityGoal.class
            ));
            mob.goalSelector.getAvailableGoals().removeIf(g -> goalsToRemove.stream().anyMatch(c -> c.isInstance(g)));
        }
    }

    public void updateSoullessTargeting(LivingChangeTargetEvent event) {
        if (isSoulless()) {
            event.setNewAboutToBeSetTarget(null);
        }
    }

    public void tickDuration() {
        if (exposedSoulDuration > 0) {
            exposedSoulDuration--;
        }
    }

    public boolean shouldDropSpirits() {
        return !soulless && exposedSoulDuration > 0;
    }

    public void setExposedSoulDuration(float exposedSoulDuration) {
        this.exposedSoulDuration = exposedSoulDuration;
    }

    public float getExposedSoulDuration() {
        return exposedSoulDuration;
    }

    public void setSoulless(boolean soulless) {
        this.soulless = soulless;
    }

    public boolean isSoulless() {
        return soulless;
    }

    public void setSpawnerSpawned(boolean spawnerSpawned) {
        this.spawnerSpawned = spawnerSpawned;
    }

    public boolean isSpawnerSpawned() {
        return spawnerSpawned;
    }
}