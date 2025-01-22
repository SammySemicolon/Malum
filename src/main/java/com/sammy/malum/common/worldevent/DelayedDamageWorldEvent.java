package com.sammy.malum.common.worldevent;

import com.sammy.malum.core.systems.spirit.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.visual_effects.networked.*;
import com.sammy.malum.visual_effects.networked.data.*;
import com.sammy.malum.visual_effects.networked.staff.*;
import net.minecraft.core.*;
import net.minecraft.core.registries.*;
import net.minecraft.nbt.*;
import net.minecraft.resources.*;
import net.minecraft.server.level.*;
import net.minecraft.sounds.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.systems.worldevent.*;

import java.awt.*;
import java.util.*;

public class DelayedDamageWorldEvent extends WorldEventInstance {

    protected ResourceKey<DamageType> magicDamageType = DamageTypeRegistry.VOODOO;

    protected UUID attackerUUID;
    protected UUID targetUUID;
    protected int delay;
    protected float physicalDamage;
    protected float magicDamage;

    protected Holder<SoundEvent> soundEvent;
    protected float minPitch;
    protected float maxPitch;
    protected float minVolume;
    protected float maxVolume;

    protected ParticleEffectType particleEffect;
    protected Color particleColor;
    protected MalumSpiritType particleSpirit;

    public DelayedDamageWorldEvent() {
        this(WorldEventTypeRegistry.DELAYED_DAMAGE.get());
    }

    public DelayedDamageWorldEvent(WorldEventType type) {
        super(type);
    }

    public DelayedDamageWorldEvent setData(UUID attackerUUID, UUID targetUUID, float physicalDamage, float magicDamage, int delay) {
        this.attackerUUID = attackerUUID;
        this.targetUUID = targetUUID;
        this.physicalDamage = physicalDamage;
        this.magicDamage = magicDamage;
        this.delay = delay;
        return this;
    }

    public DelayedDamageWorldEvent setMagicDamageType(ResourceKey<DamageType> magicDamageType) {
        this.magicDamageType = magicDamageType;
        return this;
    }

    public DelayedDamageWorldEvent setSound(Holder<SoundEvent> soundEvent, float minPitch, float maxPitch, float volume) {
        return setSound(soundEvent, minPitch, maxPitch, volume, volume);
    }

    public DelayedDamageWorldEvent setSound(Holder<SoundEvent> soundEvent, float minPitch, float maxPitch, float minVolume, float maxVolume) {
        this.soundEvent = soundEvent;
        this.minPitch = minPitch;
        this.maxPitch = maxPitch;
        this.minVolume = minVolume;
        this.maxVolume = maxVolume;
        return this;
    }

    public DelayedDamageWorldEvent setImpactParticleEffect(ParticleEffectType particleEffect, Color particleColor) {
        this.particleEffect = particleEffect;
        this.particleColor = particleColor;
        return this;
    }
    public DelayedDamageWorldEvent setImpactParticleEffect(ParticleEffectType particleEffect, MalumSpiritType particleSpirit) {
        this.particleEffect = particleEffect;
        this.particleSpirit = particleSpirit;
        return this;
    }

    @Override
    public void tick(Level level) {
        if (delay > 0) {
            delay--;
            return;
        }
        if (level instanceof ServerLevel serverLevel) {
            Entity entity = serverLevel.getEntity(targetUUID);
            Entity attacker = serverLevel.getEntity(attackerUUID);
            if (entity != null && attacker instanceof Player player) {
                if (entity.isAlive()) {
                    var deltaMovement = entity.getDeltaMovement();
                    if (physicalDamage > 0) {
                        entity.invulnerableTime = 0;
                        entity.hurt(attacker.damageSources().playerAttack(player), physicalDamage);
                    }
                    if (magicDamage > 0) {
                        entity.invulnerableTime = 0;
                        entity.hurt(DamageTypeHelper.create(serverLevel, magicDamageType, attacker), magicDamage);
                    }
                    entity.setDeltaMovement(deltaMovement);
                    if (soundEvent != null) {
                        float pitch = RandomHelper.randomBetween(player.getRandom(), minPitch, maxPitch);
                        float volume = RandomHelper.randomBetween(player.getRandom(), minVolume, maxVolume);
                        SoundHelper.playSound(attacker, soundEvent.value(), volume, pitch);
                    }
                    if (particleEffect != null) {
                        particleEffect.createPositionedEffect(serverLevel, new PositionEffectData(new Vec3(entity.getX(), entity.getY()+entity.getBbHeight()/2f, entity.getZ())),
                                new ColorEffectData(particleSpirit != null ? particleSpirit.getPrimaryColor() : particleColor));
                    }
                }
            }
        }
        end(level);
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compoundTag) {
        if (magicDamageType != DamageTypeRegistry.VOODOO) {
            compoundTag.putString("magicDamageType", magicDamageType.location().toString());
        }
        compoundTag.putUUID("attackerUUID", attackerUUID);
        compoundTag.putUUID("targetUUID", targetUUID);
        compoundTag.putFloat("physicalDamage", physicalDamage);
        compoundTag.putFloat("magicDamage", magicDamage);
        compoundTag.putInt("delay", delay);
        if (soundEvent != null) {
            compoundTag.put("soundEvent", SoundEvent.CODEC.encodeStart(NbtOps.INSTANCE, soundEvent).result().orElseThrow());
            compoundTag.putFloat("minPitch", minPitch);
            compoundTag.putFloat("maxPitch", maxPitch);
            compoundTag.putFloat("minVolume", minVolume);
            compoundTag.putFloat("maxVolume", maxVolume);
        }
        if (particleEffect != null) {
            compoundTag.put("particleEffect", ParticleEffectType.CODEC.encodeStart(NbtOps.INSTANCE, particleEffect).result().orElseThrow());
            if (particleSpirit != null) {
                compoundTag.put("particleSpirit", MalumSpiritType.CODEC.encodeStart(NbtOps.INSTANCE, particleSpirit).result().orElseThrow());
            } else {
                compoundTag.putInt("particleColor", particleColor.getRGB());
            }
        }
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compoundTag) {
        magicDamageType = compoundTag.contains("magicDamageType")
                ? ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.parse(compoundTag.getString("magicDamageType")))
                : DamageTypeRegistry.VOODOO;
        attackerUUID = compoundTag.getUUID("attackerUUID");
        targetUUID = compoundTag.getUUID("targetUUID");
        physicalDamage = compoundTag.getFloat("physicalDamage");
        magicDamage = compoundTag.getFloat("magicDamage");
        delay = compoundTag.getInt("delay");
        soundEvent = SoundEvent.CODEC.parse(NbtOps.INSTANCE, compoundTag.get("soundEvent")).result().orElse(null);
        minPitch = compoundTag.getFloat("minPitch");
        maxPitch = compoundTag.getFloat("maxPitch");
        minVolume = compoundTag.getFloat("minVolume");
        maxVolume = compoundTag.getFloat("maxVolume");
        particleEffect = ParticleEffectType.CODEC.parse(NbtOps.INSTANCE, compoundTag.get("particleEffect")).result().orElse(null);
        if (compoundTag.contains("particleSpirit")) {
            particleSpirit = MalumSpiritType.CODEC.parse(NbtOps.INSTANCE, compoundTag.get("particleSpirit")).result().orElse(null);
        } else {
            particleColor = new Color(compoundTag.getInt("particleColor"));
        }
    }
}