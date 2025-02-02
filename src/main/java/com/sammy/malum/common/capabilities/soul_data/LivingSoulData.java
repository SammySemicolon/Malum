package com.sammy.malum.common.capabilities.soul_data;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import com.sammy.malum.core.handlers.*;
import com.sammy.malum.core.systems.geas.*;
import com.sammy.malum.registry.common.item.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.event.entity.living.*;

import java.util.*;

public class LivingSoulData {

    public static final Codec<LivingSoulData> CODEC = RecordCodecBuilder.create(obj -> obj.group(
            ItemStack.CODEC.listOf().optionalFieldOf("geasEffects").forGetter(sd -> Optional.of(sd.geasStacks)),
            Codec.FLOAT.fieldOf("exposedSoulDuration").forGetter(sd -> sd.exposedSoulDuration),
            Codec.BOOL.fieldOf("soulless").forGetter(sd -> sd.soulless),
            Codec.BOOL.fieldOf("spawnerSpawned").forGetter(sd -> sd.spawnerSpawned)
    ).apply(obj, LivingSoulData::new));

    private final List<ItemStack> geasStacks = new ArrayList<>();
    private final Map<ItemStack, GeasEffect> cachedGeasEffects = new WeakHashMap<>();
    private boolean dirtyEtchings;

    private float exposedSoulDuration;
    private boolean soulless;
    private boolean spawnerSpawned;

    public LivingSoulData() {
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    private LivingSoulData(Optional<List<ItemStack>> geasStacks, float exposedSoulDuration, boolean soulless, boolean spawnerSpawned) {
        geasStacks.ifPresent(s -> s.forEach(this::addGeasEffect));
        this.exposedSoulDuration = exposedSoulDuration;
        this.soulless = soulless;
        this.spawnerSpawned = spawnerSpawned;
    }

    public List<ItemStack> getGeasItems() {
        return geasStacks;
    }

    public void removeGeasEffect(ItemStack geas) {
        geasStacks.remove(geas);
        dirtyEtchings = true;
    }

    public void addGeasEffect(ItemStack geas) {
        if (!geas.has(DataComponentRegistry.GEAS_EFFECT)) {
            throw new IllegalArgumentException("Etching Itemstack does not have an geas effect");
        }
        var storedEtching = GeasEffectHandler.getStoredGeasEffect(geas);
        if (cachedGeasEffects.values().stream().anyMatch(e -> e.type.equals(storedEtching.type))) {
            return;
        }
        geasStacks.add(geas);
        dirtyEtchings = true;
    }

    public boolean hasGeasEffect(LivingEntity living, GeasEffectType type) {
        return getGeasEffect(living, type) != null;
    }

    public Map.Entry<ItemStack, GeasEffect> getGeasEffect(LivingEntity entity, GeasEffectType type) {
        return getGeasEffects(entity).entrySet().stream().filter(e -> e.getValue().type.equals(type)).findFirst().orElse(null);
    }

    @SuppressWarnings("DataFlowIssue")
    public Map<ItemStack, GeasEffect> getGeasEffects(LivingEntity entity) {
        if (dirtyEtchings) {
            cachedGeasEffects.values().forEach(e -> e.removeAttributeModifiers(entity));
            cachedGeasEffects.clear();
            for (ItemStack geas : geasStacks) {
                cachedGeasEffects.put(geas, geas.get(DataComponentRegistry.GEAS_EFFECT).geasEffectType().createEffect());
            }
            dirtyEtchings = false;
        }
        return cachedGeasEffects;
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