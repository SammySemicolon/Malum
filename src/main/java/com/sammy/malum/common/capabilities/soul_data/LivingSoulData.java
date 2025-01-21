package com.sammy.malum.common.capabilities.soul_data;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import com.sammy.malum.core.systems.etching.*;
import com.sammy.malum.registry.common.item.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.event.entity.living.*;

import java.util.*;

public class LivingSoulData {

    public static final Codec<LivingSoulData> CODEC = RecordCodecBuilder.create(obj -> obj.group(
            ItemStack.CODEC.listOf().fieldOf("etchings").forGetter(sd -> sd.etchings),
            Codec.FLOAT.fieldOf("exposedSoulDuration").forGetter(sd -> sd.exposedSoulDuration),
            Codec.BOOL.fieldOf("soulless").forGetter(sd -> sd.soulless),
            Codec.BOOL.fieldOf("spawnerSpawned").forGetter(sd -> sd.spawnerSpawned)
    ).apply(obj, LivingSoulData::new));

    private List<ItemStack> etchings = new ArrayList<>();
    private Map<ItemStack, EtchingEffect> cachedEtchingEffects = new WeakHashMap<>();
    private boolean dirtyEtchings;

    private float exposedSoulDuration;
    private boolean soulless;
    private boolean spawnerSpawned;

    public LivingSoulData() {
    }

    private LivingSoulData(List<ItemStack> etchings, float exposedSoulDuration, boolean soulless, boolean spawnerSpawned) {
        this.etchings = new ArrayList<>(etchings);
        this.exposedSoulDuration = exposedSoulDuration;
        this.soulless = soulless;
        this.spawnerSpawned = spawnerSpawned;
    }

    public void addEtching(ItemStack etching) {
        if (!etching.has(DataComponentRegistry.ETCHING_EFFECT)) {
            throw new IllegalArgumentException("Etching Itemstack does not have an etching effect");
        }
        if (cachedEtchingEffects.containsKey(etching)) {
            return;
        }
        etchings.add(etching);
        dirtyEtchings = true;
    }

    public void removeEtching(ItemStack etching) {
        etchings.remove(etching);
        dirtyEtchings = true;
    }

    public List<ItemStack> getEtchings() {
        return etchings;
    }

    @SuppressWarnings("DataFlowIssue")
    public Map<ItemStack, EtchingEffect> getEtchingEffects(LivingEntity entity) {
        if (dirtyEtchings) {
            cachedEtchingEffects.values().forEach(e -> e.removeAttributeModifiers(entity));
            cachedEtchingEffects.clear();
            for (ItemStack etching : etchings) {
                cachedEtchingEffects.put(etching, etching.get(DataComponentRegistry.ETCHING_EFFECT).etchingEffectType().createEffect());
            }
            dirtyEtchings = false;
        }
        return cachedEtchingEffects;
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