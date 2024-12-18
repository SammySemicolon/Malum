package com.sammy.malum.core.handlers;

import com.sammy.malum.common.entity.scythe.*;
import com.sammy.malum.common.item.curiosities.weapons.scythe.*;
import com.sammy.malum.common.item.curiosities.weapons.staff.*;
import com.sammy.malum.compability.tetra.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.item.*;
import io.github.fabricators_of_create.porting_lib.entity.events.EntityJoinLevelEvent;
import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingChangeTargetEvent;
import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingDamageEvent;
import io.github.fabricators_of_create.porting_lib.entity.events.tick.EntityTickEvent;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;

import java.util.function.*;

public class SoulDataHandler {

    public static void markAsSpawnerSpawned(MobSpawnEvent event) {
        if (event.getSpawner() != null) {
            event.getEntity().getData(AttachmentTypeRegistry.LIVING_SOUL_INFO).setSpawnerSpawned(true);
        }
    }

    public static void entityJoin(EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof Mob mob) {
            mob.getAttachedOrCreate(AttachmentTypeRegistry.LIVING_SOUL_INFO).updateSoullessBehavior(mob);
        }
    }

    public static void preventTargeting(LivingChangeTargetEvent event) {
        if (event.getEntity() instanceof Mob mob) {
            mob.getAttachedOrCreate(AttachmentTypeRegistry.LIVING_SOUL_INFO).updateSoullessTargeting(event);
        }
    }

    public static void entityTick(EntityTickEvent.Pre event) {
        if (event.getEntity() instanceof LivingEntity living) {
            living.getAttachedOrCreate(AttachmentTypeRegistry.LIVING_SOUL_INFO).tickDuration();
        }
    }

    public static void exposeSoul(LivingDamageEvent event) {
        if (event.getAmount() <= 0) {
            return;
        }
        var target = event.getEntity();
        var source = event.getSource();
        var data = target.getAttachedOrCreate(AttachmentTypeRegistry.LIVING_SOUL_INFO);
        if (source.is(DamageTypeTagRegistry.SOUL_SHATTER_DAMAGE)) {
            data.setExposed();
            return;
        }

        var directEntity = source.getDirectEntity();
        if (directEntity != null) {
            var projectileData = directEntity.getAttachedOrCreate(AttachmentTypeRegistry.PROJECTILE_SOUL_INFO);
            if (projectileData.dealsSoulDamage()) {
                data.setExposed();
                return;
            }
        }
        if (source.getEntity() instanceof LivingEntity attacker) {
            ItemStack stack = getSoulHunterWeapon(source, attacker);
            if (stack.is(ItemTagRegistry.SOUL_SHATTER_CAPABLE_WEAPONS) || TetraCompat.hasSoulStrikeModifier(stack)) {
                data.setExposed();
            }
        }
    }

    //TODO: move these guys
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
