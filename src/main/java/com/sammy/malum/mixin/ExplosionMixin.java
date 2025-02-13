package com.sammy.malum.mixin;

import com.sammy.malum.common.geas.explosion.*;
import com.sammy.malum.common.item.curiosities.curios.sets.prospector.CurioDemolitionistRing;
import com.sammy.malum.common.item.curiosities.curios.sets.prospector.CurioHoarderRing;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.*;
import net.minecraft.sounds.*;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Mixin(Explosion.class)
public abstract class ExplosionMixin {

    @Unique
    boolean malum$hasHoarderRing;

    @Unique
    ItemStack malum$droppedItem;

    @Mutable
    @Shadow
    @Final
    private float radius;

    @Shadow
    @Nullable
    public abstract LivingEntity getIndirectSourceEntity();

    @Mutable
    @Shadow @Final private Holder<SoundEvent> explosionSound;

    @Mutable
    @Shadow @Final private ParticleOptions smallExplosionParticles;

    @Mutable
    @Shadow @Final private ParticleOptions largeExplosionParticles;


    @Inject(method = "<init>(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/damagesource/DamageSource;Lnet/minecraft/world/level/ExplosionDamageCalculator;DDDFZLnet/minecraft/world/level/Explosion$BlockInteraction;Lnet/minecraft/core/particles/ParticleOptions;Lnet/minecraft/core/particles/ParticleOptions;Lnet/minecraft/core/Holder;)V", at = @At(value = "RETURN"))
    private void malum$modifyExplosionStats(Level level, Entity source, DamageSource damageSource, ExplosionDamageCalculator damageCalculator, double x, double y, double z, float radius, boolean fire, Explosion.BlockInteraction blockInteraction, ParticleOptions smallExplosionParticles, ParticleOptions largeExplosionParticles, Holder explosionSound, CallbackInfo ci) {
        LivingEntity sourceEntity = getIndirectSourceEntity();
        this.radius = CurioDemolitionistRing.increaseExplosionRadius(sourceEntity, radius);
        if (level.isClientSide) {
            if (CloudHopperGeas.canModifyExplosion(sourceEntity)) {
                this.explosionSound = SoundEvents.WIND_CHARGE_BURST;
                this.smallExplosionParticles = ParticleTypes.GUST_EMITTER_SMALL;
                this.largeExplosionParticles = ParticleTypes.GUST_EMITTER_LARGE;
            }
        }
    }

    @Inject(method = "finalizeExplosion", at = @At(value = "HEAD"))
    private void malum$finalizeExplosion(boolean pSpawnParticles, CallbackInfo ci) {
        malum$hasHoarderRing = CurioHoarderRing.hasHoarderRing(getIndirectSourceEntity());
    }

    @ModifyArg(method = "finalizeExplosion", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/Block;popResource(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/item/ItemStack;)V"), index = 2)
    private ItemStack malum$popResourceCache(ItemStack pStack) {
        return malum$droppedItem = pStack;
    }

    @ModifyArg(method = "finalizeExplosion", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/Block;popResource(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/item/ItemStack;)V"), index = 1)
    private BlockPos malum$popResource(BlockPos value) {
        return CurioHoarderRing.getExplosionPos(malum$hasHoarderRing, value, getIndirectSourceEntity(), malum$droppedItem);
    }
}