package com.sammy.malum.common.entity.nitrate;

import com.sammy.malum.registry.common.DamageTypeRegistry;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.level.ExplosionEvent;
import org.jetbrains.annotations.Nullable;
import team.lodestar.lodestone.helpers.*;

public class NitrateExplosion extends Explosion {

    public NitrateExplosion(Level pLevel, @Nullable Entity pSource, @Nullable DamageSource pDamageSource, @Nullable ExplosionDamageCalculator pDamageCalculator, double pToBlowX, double pToBlowY, double pToBlowZ, float pRadius, boolean pFire, BlockInteraction pBlockInteraction) {
        super(pLevel, pSource, pDamageSource, pDamageCalculator, pToBlowX, pToBlowY, pToBlowZ, pRadius, pFire, pBlockInteraction);
    }

    @Override
    public DamageSource getDamageSource() {
        if (getDirectSourceEntity() != null) {
            return DamageTypeHelper.create(getDirectSourceEntity().level(), DamageTypeRegistry.VOODOO, getDirectSourceEntity());
        }
        return super.getDamageSource();
    }

    public static void processExplosion(ExplosionEvent.Detonate event) {
        if (event.getExplosion() instanceof NitrateExplosion) {
            event.getAffectedEntities().removeIf(e -> e instanceof AbstractNitrateEntity || e instanceof Player player && player.isCreative());
        }
    }

    public static NitrateExplosion explode(Level level, @Nullable Entity pEntity, double pX, double pY, double pZ, float pExplosionRadius, Explosion.BlockInteraction pMode) {
        return explode(level, pEntity, null, null, pX, pY, pZ, pExplosionRadius, false, pMode);
    }

    public static NitrateExplosion explode(Level level, @Nullable Entity pExploder, @Nullable DamageSource pDamageSource, @Nullable ExplosionDamageCalculator pContext, double pX, double pY, double pZ, float pSize, boolean pCausesFire, Explosion.BlockInteraction pMode) {
        NitrateExplosion explosion = new NitrateExplosion(level, pExploder, pDamageSource, pContext, pX, pY, pZ, pSize, pCausesFire, pMode);
        if (net.minecraftforge.event.ForgeEventFactory.onExplosionStart(level, explosion)) return explosion;
        if (!level.isClientSide) {
            explosion.explode();
        }
        explosion.finalizeExplosion(true);
        return explosion;
    }
}