package com.sammy.malum.core.handlers;

import com.sammy.malum.common.capability.*;
import com.sammy.malum.common.entity.boomerang.*;
import com.sammy.malum.compability.tetra.*;
import com.sammy.malum.registry.common.item.*;
import net.minecraft.nbt.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.item.*;
import net.minecraftforge.event.entity.*;
import net.minecraftforge.event.entity.living.*;

public class SoulDataHandler {

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
        SoulDataHandler soulData = MalumLivingEntityDataCapability.getCapability(target).soulData;
        if (source.getEntity() instanceof LivingEntity attacker) {
            ItemStack stack = getSoulHunterWeapon(source, attacker);
            if (stack.is(ItemTagRegistry.SOUL_HUNTER_WEAPON) || (TetraCompat.LOADED && TetraCompat.LoadedOnly.hasSoulStrike(stack))) {
                soulData.exposedSoulDuration = 200;
            }
        }
        if (source.getDirectEntity() != null && source.getDirectEntity().getTags().contains("malum:soul_arrow")) {
            soulData.exposedSoulDuration = 200;
        }
    }

    public static void manageSoul(EntityTickEvent event) {
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

    public static ItemStack getScytheWeapon(DamageSource source, LivingEntity attacker) {
        var soulHunterWeapon = getSoulHunterWeapon(source, attacker);
        return soulHunterWeapon.getItem() instanceof MalumScytheItem ? soulHunterWeapon : ItemStack.EMPTY;
    }

    public static ItemStack getSoulHunterWeapon(DamageSource source, LivingEntity attacker) {
        ItemStack stack = attacker.getMainHandItem();

        if (source.getDirectEntity() instanceof ScytheBoomerangEntity scytheBoomerang) {
            stack = scytheBoomerang.getItem();
        }
        return stack;
    }
}
