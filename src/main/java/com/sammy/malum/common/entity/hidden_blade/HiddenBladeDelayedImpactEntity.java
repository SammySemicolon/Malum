package com.sammy.malum.common.entity.hidden_blade;

import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.entity.*;
import com.sammy.malum.registry.common.item.*;
import net.minecraft.nbt.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.projectile.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import team.lodestar.lodestone.helpers.*;

import static net.minecraft.world.entity.projectile.ProjectileUtil.getEntityHitResult;

public class HiddenBladeDelayedImpactEntity extends ThrowableItemProjectile {

    protected float damage;
    protected float magicDamage;
    public int age;
    public int duration = 25;

    public int enemiesHit;

    public HiddenBladeDelayedImpactEntity(Level level) {
        super(EntityRegistry.HIDDEN_BLADE_DELAYED_IMPACT.get(), level);
        noPhysics = false;
    }

    public HiddenBladeDelayedImpactEntity(Level level, double pX, double pY, double pZ) {
        super(EntityRegistry.HIDDEN_BLADE_DELAYED_IMPACT.get(), pX, pY, pZ, level);
        noPhysics = false;
    }

    public void setData(Entity owner, float damage, float magicDamage, int duration) {
        setOwner(owner);
        this.damage = damage;
        this.magicDamage = magicDamage;
        this.duration = duration;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        if (damage != 0) {
            compound.putFloat("damage", damage);
        }
        if (magicDamage != 0) {
            compound.putFloat("magicDamage", magicDamage);
        }
        if (age != 0) {
            compound.putInt("age", age);
        }
        if (duration != 0) {
            compound.putInt("duration", duration);
        }
        if (enemiesHit != 0) {
            compound.putInt("enemiesHit", enemiesHit);
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        damage = compound.getFloat("damage");
        magicDamage = compound.getFloat("magicDamage");
        age = compound.getInt("age");
        duration = compound.getInt("duration");
        enemiesHit = compound.getInt("enemiesHit");
    }

    @Override
    protected boolean canHitEntity(Entity pTarget) {
        return !pTarget.equals(getOwner()) && super.canHitEntity(pTarget);
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        if (getOwner() instanceof LivingEntity scytheOwner) {
            Entity target = result.getEntity();
            DamageSource source = DamageTypeHelper.create(level(), DamageTypeRegistry.HIDDEN_BLADE_COUNTER, this, scytheOwner);
            target.invulnerableTime = 0;
            final Vec3 deltaMovement = target.getDeltaMovement();
            boolean success = target.hurt(source, damage);
            if (success && target instanceof LivingEntity livingentity) {
                ItemStack scythe = getItem();
                ItemHelper.applyEnchantments(scytheOwner, livingentity, scythe);
                int i = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FIRE_ASPECT, scythe);
                if (i > 0) {
                    livingentity.setSecondsOnFire(i * 4);
                }
                if (magicDamage > 0) {
                    if (!livingentity.isDeadOrDying()) {
                        target.invulnerableTime = 0;
                        livingentity.hurt(DamageTypeHelper.create(level(), DamageTypeRegistry.VOODOO, this, scytheOwner), magicDamage);
                    }
                }
                enemiesHit+=1;
            }
            target.setDeltaMovement(deltaMovement);
            SoundHelper.playSound(this, SoundRegistry.SCYTHE_CUT.get(), 1.0F, 0.9f + level().getRandom().nextFloat() * 0.2f);
        }
        super.onHitEntity(result);
    }

    @Override
    public void tick() {
        age++;
        if (!level().isClientSide()) {
            for(Entity target : level().getEntities(this, getBoundingBox(), this::canHitEntity)) {
                onHitEntity(new EntityHitResult(target));
            }
            if (age >= duration) {
                remove(RemovalReason.DISCARDED);
            }
        }
    }

    @Override
    protected Item getDefaultItem() {
        return ItemRegistry.SOUL_STAINED_STEEL_SCYTHE.get();
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    @Override
    public float getPickRadius() {
        return 4f;
    }

    @Override
    public boolean fireImmune() {
        return true;
    }

    @Override
    public boolean ignoreExplosion() {
        return true;
    }
}