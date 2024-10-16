package com.sammy.malum.common.entity.boomerang;

import com.sammy.malum.common.item.curiosities.weapons.scythe.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.entity.*;
import com.sammy.malum.registry.common.item.*;
import net.minecraft.core.*;
import net.minecraft.core.particles.*;
import net.minecraft.core.registries.*;
import net.minecraft.nbt.*;
import net.minecraft.server.level.*;
import net.minecraft.sounds.*;
import net.minecraft.util.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.item.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.entity.projectile.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import net.neoforged.neoforge.items.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.systems.rendering.trail.*;

import java.util.*;

public class ScytheBoomerangEntity extends ThrowableItemProjectile {

    public final TrailPointBuilder theFormer = TrailPointBuilder.create(8);
    public final TrailPointBuilder theLatter = TrailPointBuilder.create(8);
    public float spinOffset = (float) (random.nextFloat() * Math.PI * 2);

    protected int slot;
    protected float damage;
    protected float magicDamage;
    public int age;
    protected int returnTimer = 8;

    public int enemiesHit;

    public ScytheBoomerangEntity(Level level) {
        super(EntityRegistry.SCYTHE_BOOMERANG.get(), level);
        noPhysics = false;
    }

    public ScytheBoomerangEntity(Level level, double pX, double pY, double pZ) {
        super(EntityRegistry.SCYTHE_BOOMERANG.get(), pX, pY, pZ, level);
        noPhysics = false;
    }

    public void setData(Entity owner, float damage, float magicDamage, int slot) {
        setOwner(owner);
        this.damage = damage;
        this.magicDamage = magicDamage;
        this.slot = slot;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        if (slot != 0) {
            compound.putInt("slot", slot);
        }
        if (damage != 0) {
            compound.putFloat("damage", damage);
        }
        if (magicDamage != 0) {
            compound.putFloat("magicDamage", magicDamage);
        }
        if (age != 0) {
            compound.putInt("age", age);
        }
        if (returnTimer != 0) {
            compound.putInt("returnTimer", returnTimer);
        }
        if (enemiesHit != 0) {
            compound.putInt("enemiesHit", enemiesHit);
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        slot = compound.getInt("slot");
        damage = compound.getFloat("damage");
        magicDamage = compound.getFloat("magicDamage");
        age = compound.getInt("age");
        returnTimer = compound.getInt("returnTimer");
        enemiesHit = compound.getInt("enemiesHit");
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        returnTimer = 0;
        if (getOwner() instanceof LivingEntity scytheOwner) {
            var ownerPos = scytheOwner.position().add(0, 1, 0);
            var returnMotion = ownerPos.subtract(position()).normalize().scale(0.75f);
            setDeltaMovement(returnMotion);
        }
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
        if (getOwner() instanceof LivingEntity scytheOwner && level() instanceof ServerLevel level) {
            Entity target = result.getEntity();
            DamageSource source = DamageTypeHelper.create(level(), DamageTypeRegistry.SCYTHE_SWEEP, this, scytheOwner);
            target.invulnerableTime = 0;
            boolean success = target.hurt(source, damage);
            if (success && target instanceof LivingEntity livingentity) {
                ItemStack scythe = getItem();
                scythe.hurtAndBreak(1, level, scytheOwner, (e) -> remove(RemovalReason.KILLED));
                ItemHelper.applyEnchantments(scytheOwner, livingentity, source, scythe);
                if (magicDamage > 0) {
                    if (!livingentity.isDeadOrDying()) {
                        livingentity.invulnerableTime = 0;
                        livingentity.hurt(DamageTypeHelper.create(level(), DamageTypeRegistry.VOODOO, this, scytheOwner), magicDamage);
                    }
                }
                enemiesHit+=1;
            }
            returnTimer += 4;
            SoundHelper.playSound(this, SoundRegistry.SCYTHE_SWEEP,1.0f, RandomHelper.randomBetween(level().getRandom(), 0.75f, 1.25f));
        }
        super.onHitEntity(result);
    }

    @Override
    public void tick() {
        super.tick();
        age++;
        var scythe = getItem();
        var level = level();
        if (level.isClientSide) {
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
            theFormer.tickTrailPoints();
            theLatter.tickTrailPoints();

            if (!isInWaterRainOrBubble()) {
                if (getItem().getEnchantmentLevel(level.holderLookup(Registries.ENCHANTMENT).getOrThrow(Enchantments.FIRE_ASPECT)) > 0) {
                    Vec3 vector = new Vec3(getRandomX(0.7), getRandomY(), getRandomZ(0.7));
                    if (scythe.getItem() instanceof MalumScytheItem) {
                        float rotation = random.nextFloat();
                        vector = new Vec3(getX() + Math.cos(age) * 1.2f, getY(0.1), getZ() + Math.sin(age) * 1.2f);
                        double x = Math.cos(age + rotation * 2 - 1) * 0.8f + getX();
                        double z = Math.sin(age + rotation * 2 - 1) * 0.8f + getZ();
                        level.addParticle(ParticleTypes.FLAME, x, vector.y, z, 0, 0, 0);
                        level.addParticle(ParticleTypes.FLAME, x, vector.y, z, 0, 0, 0);
                    }
                    level.addParticle(ParticleTypes.FLAME, vector.x, vector.y, vector.z, 0, 0, 0);
                }
            }
        } else {
            var owner = getOwner();
            if (owner == null || !owner.isAlive() || !owner.level().equals(level()) || distanceTo(owner) > 1000f) {
                if (age > 3600) {
                    ItemEntity itemEntity = new ItemEntity(level, getX(), getY() + 0.5, getZ(), scythe);
                    itemEntity.setPickUpDelay(40);
                    itemEntity.setNoGravity(true);
                    level.addFreshEntity(itemEntity);
                    remove(RemovalReason.DISCARDED);
                }
                if (level().getGameTime() % 40L == 0) {
                    Player playerEntity = level().getNearestPlayer(this, 50);
                    if (playerEntity != null) {
                        setOwner(playerEntity);
                    }
                }
                setDeltaMovement(Vec3.ZERO);
                return;
            }
            if (owner instanceof LivingEntity scytheOwner) {
                if (age % 3 == 0) {
                    final float pitch = (float) (1f + Math.sin(level.getGameTime() * 0.5f) * 0.2f);
                    float volumeScalar = Mth.clamp(age / 12f, 0, 1f);
                    SoundHelper.playSound(this, SoundRegistry.SCYTHE_SPINS.get(),0.8f * volumeScalar, pitch);
                    SoundHelper.playSound(this, SoundRegistry.SCYTHE_SWEEP.get(),0.3f * volumeScalar, pitch);
                }
                var motion = getDeltaMovement();
                if (xRotO == 0.0F && yRotO == 0.0F) {
                    setYRot((float) (Mth.atan2(motion.x, motion.z) * (double) (180F / (float) Math.PI)));
                    yRotO = getYRot();
                    xRotO = getXRot();
                }
                if (returnTimer <= 0) {
                    noPhysics = true;
                    var ownerPos = scytheOwner.position().add(0, scytheOwner.getBbHeight()*0.6f, 0);
                    var returnMotion = ownerPos.subtract(position()).normalize().scale(Mth.clamp(motion.length()*3, 0.5f, 2f));
                    float distance = distanceTo(scytheOwner);

                    if (isAlive() && distance < 3f) {
                        if (scytheOwner instanceof Player player) {
                            ItemHandlerHelper.giveItemToPlayer(player, scythe, slot);
                            if (!player.isCreative()) {

                                HolderGetter<Enchantment> enchantmentLookup = attacker.level().registryAccess()
                                        .asGetterLookup().lookupOrThrow(Registries.ENCHANTMENT);
                                int enchantmentLevel = scythe.getEnchantmentLevel(enchantmentLookup.getOrThrow(EnchantmentRegistry.REBOUND));
                                if (enchantmentLevel < 4) {
                                    player.getCooldowns().addCooldown(scythe.getItem(), 100 - 25 * (enchantmentLevel - 1));
                                }
                            }
                            SoundHelper.playSound(this, SoundRegistry.SCYTHE_CATCH.get(),1.5f, RandomHelper.randomBetween(level().getRandom(), 0.75f, 1.25f));
                            remove(RemovalReason.DISCARDED);
                        }
                    }
                    double x = Mth.lerp(0.1f, motion.x, returnMotion.x);
                    double y = Mth.lerp(0.1f, motion.y, returnMotion.y);
                    double z = Mth.lerp(0.1f, motion.z, returnMotion.z);
                    setDeltaMovement(new Vec3(x, y, z));
                }
                returnTimer--;
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
    public boolean ignoreExplosion(Explosion explosion) {
        return true;
    }
}