package com.sammy.malum.core.handlers;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import com.sammy.malum.common.capability.*;
import com.sammy.malum.common.entity.scythe.*;
import com.sammy.malum.common.item.curiosities.weapons.scythe.*;
import com.sammy.malum.common.item.curiosities.weapons.staff.*;
import com.sammy.malum.compability.tetra.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.item.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.event.entity.*;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.event.tick.*;

import java.util.function.*;

public class SoulDataHandler {

    public static final String SOUL_SHATTER_ENTITY_TAG = "malum:can_shatter_souls";

    public float exposedSoulDuration;
    public boolean soulless;
    public boolean spawnerSpawned;

    public static final Codec<SoulDataHandler> CODEC = RecordCodecBuilder.create(obj -> obj.group(
            Codec.FLOAT.fieldOf("exposedSoulDuration").forGetter(sd -> sd.exposedSoulDuration),
            Codec.BOOL.fieldOf("soulless").forGetter(sd -> sd.soulless),
            Codec.BOOL.fieldOf("spawnerSpawned").forGetter(sd -> sd.spawnerSpawned)
    ).apply(obj, SoulDataHandler::new));

    public SoulDataHandler() {}

    public SoulDataHandler(boolean soulless, boolean spawnerSpawned) {
        this.soulless = soulless;
        this.spawnerSpawned = spawnerSpawned;
    }

    public SoulDataHandler(float esd, boolean soulless, boolean spawnerSpawned) {
        this(false, false);
        this.exposedSoulDuration = esd;
    }

    public static void markAsSpawnerSpawned(MobSpawnEvent.PositionCheck event) {
        if (event.getSpawner() != null) {
            MalumLivingEntityDataCapability.getCapabilityOptional(event.getEntity()).ifPresent(ec -> {
                SoulDataHandler soulData = ec.soulData;
                soulData.spawnerSpawned = true;
            });
        }
    }

    public static void updateAi(EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof LivingEntity livingEntity) {
            MalumLivingEntityDataCapability.getCapabilityOptional(livingEntity).ifPresent(ec -> {
                SoulDataHandler soulData = ec.soulData;
                if (livingEntity instanceof Mob mob) {
                    if (soulData.soulless) {
                        removeSentience(mob);
                    }
                }
            });
        }
    }

    public static void preventTargeting(LivingChangeTargetEvent event) {
        if (event.getEntity() instanceof Mob mob) {
            MalumLivingEntityDataCapability.getCapabilityOptional(mob).ifPresent(ec -> {
                SoulDataHandler soulData = ec.soulData;
                if (soulData.soulless) {
                    event.setNewAboutToBeSetTarget(null);
                }
            });
        }
    }

    public static void exposeSoul(LivingDamageEvent.Post event) {
        if (event.getOriginalDamage() <= 0) {
            return;
        }
        LivingEntity target = event.getEntity();
        DamageSource source = event.getSource();
        if (source.is(DamageTypeTagRegistry.SOUL_SHATTER_DAMAGE)) {
            exposeSoul(target);
        }
        if (source.getEntity() instanceof LivingEntity attacker) {
            ItemStack stack = getSoulHunterWeapon(source, attacker);
            if (stack.is(ItemTagRegistry.SOUL_HUNTER_WEAPON) || TetraCompat.hasSoulStrikeModifier(stack)) {
                exposeSoul(target);
            }
        }
        var directEntity = source.getDirectEntity();
        if (directEntity != null && directEntity.getTags().contains(SOUL_SHATTER_ENTITY_TAG)) {
            exposeSoul(target);
        }
    }
    public static void exposeSoul(LivingEntity entity) {
        SoulDataHandler soulData = MalumLivingEntityDataCapability.getCapability(entity).soulData;
        soulData.exposedSoulDuration = 200;
    }

    public static void manageSoul(EntityTickEvent.Pre event) {
        if (event.getEntity() instanceof LivingEntity living) {
            SoulDataHandler soulData = MalumLivingEntityDataCapability.getCapability(living).soulData;
            if (soulData.exposedSoulDuration > 0) {
                soulData.exposedSoulDuration--;
            }
        }
    }

    public static void removeSentience(Mob mob) {
        mob.goalSelector.getAvailableGoals().removeIf(g -> g.getGoal() instanceof LookAtPlayerGoal || g.getGoal() instanceof MeleeAttackGoal || g.getGoal() instanceof SwellGoal || g.getGoal() instanceof PanicGoal || g.getGoal() instanceof RandomLookAroundGoal || g.getGoal() instanceof AvoidEntityGoal);
    }

    public static ItemStack getStaffWeapon(DamageSource source, LivingEntity attacker) {
        return getSoulHunterWeapon(source, attacker, s -> s.getItem() instanceof AbstractStaffItem);
    }

    public static ItemStack getScytheWeapon(DamageSource source, LivingEntity attacker) {
        return getSoulHunterWeapon(source, attacker, s -> s.getItem() instanceof MalumScytheItem);
    }

    public static ItemStack getSoulHunterWeapon(DamageSource source, LivingEntity attacker, Predicate<ItemStack> predicate) {
        var soulHunterWeapon = getSoulHunterWeapon(source, attacker);
        return predicate.test(soulHunterWeapon) ? soulHunterWeapon : ItemStack.EMPTY;
    }

    public static ItemStack getSoulHunterWeapon(DamageSource source, LivingEntity attacker) {
        ItemStack stack = attacker.getMainHandItem();

        if (source.getDirectEntity() instanceof ScytheBoomerangEntity scytheBoomerang) {
            stack = scytheBoomerang.getItem();
        }
        return stack;
    }
}
