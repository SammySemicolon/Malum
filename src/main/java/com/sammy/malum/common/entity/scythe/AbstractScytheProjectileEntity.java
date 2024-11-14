package com.sammy.malum.common.entity.scythe;

import com.sammy.malum.common.enchantment.scythe.*;
import com.sammy.malum.common.item.curiosities.weapons.scythe.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.entity.*;
import com.sammy.malum.registry.common.item.*;
import net.minecraft.core.particles.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.entity.projectile.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.systems.rendering.trail.*;

public abstract class AbstractScytheProjectileEntity extends ThrowableItemProjectile {

    public final TrailPointBuilder theFormer = TrailPointBuilder.create(8);
    public final TrailPointBuilder theLatter = TrailPointBuilder.create(8);
    public float spinOffset = (float) (random.nextFloat() * Math.PI * 2);

    public int slot;
    public int age;
    protected float damage;
    protected float magicDamage;
    public int enemiesHit;
    public int returnTimer;

    public AbstractScytheProjectileEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        noPhysics = true;
    }

    public AbstractScytheProjectileEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, double pX, double pY, double pZ, Level pLevel) {
        super(pEntityType, pX, pY, pZ, pLevel);
        noPhysics = true;
    }

    public void setData(Entity owner, float damage, float magicDamage, int slot, int returnTimer) {
        setOwner(owner);
        this.damage = damage;
        this.magicDamage = magicDamage;
        this.slot = slot;
        this.returnTimer = returnTimer;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        if (slot != 0) {
            compound.putInt("slot", slot);
        }
        if (age != 0) {
            compound.putInt("age", age);
        }
        if (damage != 0) {
            compound.putFloat("damage", damage);
        }
        if (magicDamage != 0) {
            compound.putFloat("magicDamage", magicDamage);
        }
        if (enemiesHit != 0) {
            compound.putInt("enemiesHit", enemiesHit);
        }
        if (returnTimer != 0) {
            compound.putInt("returnTimer", returnTimer);
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        slot = compound.getInt("slot");
        age = compound.getInt("age");
        damage = compound.getFloat("damage");
        magicDamage = compound.getFloat("magicDamage");
        enemiesHit = compound.getInt("enemiesHit");
        returnTimer = compound.getInt("returnTimer");
    }

    @Override
    protected boolean canHitEntity(Entity pTarget) {
        return !pTarget.equals(getOwner()) && super.canHitEntity(pTarget);
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        if (level().isClientSide()) {
            return;
        }
        if (getOwner() instanceof LivingEntity scytheOwner) {
            Entity target = result.getEntity();
            DamageSource source = DamageTypeHelper.create(level(), DamageTypeRegistry.SCYTHE_SWEEP, this, scytheOwner);
            var heldItem = scytheOwner.getMainHandItem();
            scytheOwner.setItemInHand(InteractionHand.MAIN_HAND, getItem());
            target.invulnerableTime = 0;
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
                        livingentity.invulnerableTime = 0;
                        livingentity.hurt(DamageTypeHelper.create(level(), DamageTypeRegistry.VOODOO, this, scytheOwner), magicDamage);
                    }
                }
                enemiesHit += 1;
                returnTimer += 2;
            }
            scytheOwner.setItemInHand(InteractionHand.MAIN_HAND, heldItem);
            SoundHelper.playSound(this, SoundRegistry.SCYTHE_SWEEP.get(),1.0f, RandomHelper.randomBetween(level().getRandom(), 0.75f, 1.25f));
        }
        super.onHitEntity(result);
    }

    @Override
    public void tick() {
        super.tick();
        age++;
        var level = level();
        if (level.isClientSide) {
            addTrailPoints();
            theFormer.tickTrailPoints();
            theLatter.tickTrailPoints();
        } else {
            var motion = getDeltaMovement();
            if (xRotO == 0.0F && yRotO == 0.0F) {
                setYRot((float) (Mth.atan2(motion.x, motion.z) * (double) (180F / (float) Math.PI)));
                yRotO = getYRot();
                xRotO = getXRot();
            }
        }
    }

    public void addTrailPoints() {
        for (int i = 0; i < 2; i++) {
            float progress = (i + 1) * 0.5f;
            Vec3 position = getPosition(progress);
            float scalar = (age + progress) / 2f;
            for (int j = 0; j < 2; j++) {
                var trail = j == 0 ? theFormer : theLatter;
                double xOffset = Math.cos(spinOffset + 3.14f * j + scalar) * 1.2f;
                double zOffset = Math.sin(spinOffset + 3.14f * j + scalar) * 1.2f;
                trail.addTrailPoint(position.add(xOffset, 0, zOffset));
            }
        }
    }

    public void shootFromRotation(Entity shooter, float rotationPitch, float rotationYaw, float pitchOffset, float velocity, float innacuracy) {
        float f = -Mth.sin(rotationYaw * ((float) Math.PI / 180F)) * Mth.cos(rotationPitch * ((float) Math.PI / 180F));
        float f1 = -Mth.sin((rotationPitch + pitchOffset) * ((float) Math.PI / 180F));
        float f2 = Mth.cos(rotationYaw * ((float) Math.PI / 180F)) * Mth.cos(rotationPitch * ((float) Math.PI / 180F));
        this.shoot(f, f1, f2, velocity, innacuracy);
        Vec3 vec3 = shooter.getDeltaMovement();
        this.setDeltaMovement(this.getDeltaMovement().add(vec3.x, 0, vec3.z));
    }

    @Override
    protected Item getDefaultItem() {
        return ItemRegistry.CRUDE_SCYTHE.get();
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