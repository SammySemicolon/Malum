package com.sammy.malum.common.entity.nitrate;

import com.sammy.malum.registry.common.DamageTypeRegistry;
import net.minecraft.core.*;
import net.minecraft.core.particles.*;
import net.minecraft.sounds.*;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.level.*;
import net.neoforged.neoforge.event.level.ExplosionEvent;
import org.jetbrains.annotations.Nullable;
import team.lodestar.lodestone.helpers.*;

public class NitrateExplosion extends Explosion {

    public NitrateExplosion(Level level, @Nullable Entity source, @Nullable DamageSource damageSource, @Nullable ExplosionDamageCalculator damageCalculator, double x, double y, double z, float radius, boolean fire, BlockInteraction blockInteraction, ParticleOptions smallExplosionParticles, ParticleOptions largeExplosionParticles, Holder<SoundEvent> explosionSound) {
        super(level, source, damageSource, damageCalculator, x, y, z, radius, fire, blockInteraction, smallExplosionParticles, largeExplosionParticles, explosionSound);
    }

    public static void processExplosion(ExplosionEvent.Detonate event) {
        if (event.getExplosion() instanceof NitrateExplosion) {
            event.getAffectedEntities().removeIf(e -> e instanceof AbstractNitrateEntity || e instanceof Player player && player.isCreative());
        }
    }

    public static NitrateExplosion explode(Level level, @Nullable Entity source, double x, double y, double z, float radius) {
        return explode(level, source, null, x, y, z, radius);
    }

    public static NitrateExplosion explode(Level level, @Nullable Entity source, @Nullable ExplosionDamageCalculator damageCalculator, double x, double y, double z, float radius) {
        return explode(level, source, damageCalculator, x, y, z, radius, true);
    }

    public static NitrateExplosion explode(Level level, @Nullable Entity source, @Nullable ExplosionDamageCalculator damageCalculator, double x, double y, double z, float radius, boolean spawnParticles) {
        var damageSource = DamageTypeHelper.create(level, source != null ? DamageTypeRegistry.VOODOO : DamageTypeRegistry.VOODOO_PLAYERLESS, source);
        var explosion = new NitrateExplosion(
                level, source, damageSource, damageCalculator,
                x, y, z, radius, false,
                level.getGameRules().getBoolean(GameRules.RULE_TNT_EXPLOSION_DROP_DECAY) ? BlockInteraction.DESTROY_WITH_DECAY : BlockInteraction.DESTROY,
                ParticleTypes.EXPLOSION, ParticleTypes.EXPLOSION_EMITTER, SoundEvents.GENERIC_EXPLODE
        );
        if (net.neoforged.neoforge.event.EventHooks.onExplosionStart(level, explosion)) return explosion;
        explosion.explode();
        explosion.finalizeExplosion(spawnParticles);
        return explosion;
    }
}