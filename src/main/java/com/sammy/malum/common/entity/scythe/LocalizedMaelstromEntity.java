package com.sammy.malum.common.entity.scythe;

import com.sammy.malum.common.item.curiosities.curios.sets.scythe.*;
import com.sammy.malum.core.handlers.enchantment.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.entity.*;
import com.sammy.malum.visual_effects.*;
import net.minecraft.nbt.*;
import net.minecraft.network.syncher.*;
import net.minecraft.server.level.*;
import net.minecraft.util.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.systems.rendering.trail.*;

public class LocalizedMaelstromEntity extends AbstractScytheProjectileEntity {

    public LocalizedMaelstromEntity(Level level) {
        super(EntityRegistry.SCYTHE_MAELSTROM.get(), level);
    }

    public LocalizedMaelstromEntity(Level level, double pX, double pY, double pZ) {
        super(EntityRegistry.SCYTHE_MAELSTROM.get(), pX, pY, pZ, level);
    }

    @Override
    public void tick() {
        super.tick();
        var level = level();
        if (level instanceof ServerLevel serverLevel) {
            returnTimer--;
            if (returnTimer <= 0) {
                remove(RemovalReason.DISCARDED);
                return;
            }
            if (getOwner() instanceof LivingEntity scytheOwner) {
                CurioHowlingMaelstromRing.handleMaelstrom(serverLevel, scytheOwner, this);
                playSound();
            }
        } else {
            WeaponParticleEffects.spawnMaelstromParticles(this);
        }
    }

    public void playSound() {
        if (age % 2 == 0) {
            float pitch = (float) (0.8f + Math.sin(level().getGameTime() * 0.5f) * 0.2f);
            float volumeScalar = Mth.clamp(age / 12f, 0, 1f);
            if (isInWater()) {
                volumeScalar *= 0.2f;
                pitch *= 0.5f;
            }
            SoundHelper.playSound(this, SoundRegistry.SCYTHE_SWEEP.get(), 0.4f * volumeScalar, pitch);
        }
    }
}