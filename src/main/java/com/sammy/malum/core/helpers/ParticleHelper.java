package com.sammy.malum.core.helpers;

import com.sammy.malum.registry.common.*;
import com.sammy.malum.visual_effects.networked.*;
import com.sammy.malum.visual_effects.networked.data.*;
import com.sammy.malum.visual_effects.networked.slash.*;
import net.minecraft.util.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.phys.*;

public class ParticleHelper {
    public static void spawnHorizontalSlashParticle(ParticleEffectType effectType, LivingEntity attacker) {
        if (!attacker.level().isClientSide()) {
            spawnSlashingParticle(effectType, attacker, 0f);
        }
    }

    public static void spawnVerticalSlashParticle(ParticleEffectType effectType, LivingEntity attacker) {
        if (!attacker.level().isClientSide()) {
            spawnSlashingParticle(effectType, attacker, 0.4f, 1.57f, false);
        }
    }

    public static void spawnSlashingParticle(ParticleEffectType effectType, LivingEntity attacker, float slashAngle) {
        spawnSlashingParticle(effectType, attacker, 0, slashAngle, attacker.getRandom().nextBoolean());
    }

    public static void spawnSlashingParticle(ParticleEffectType effectType, LivingEntity attacker, float horizontalOffset, float slashAngle, boolean mirrored) {
        var direction = attacker.getLookAngle();
        float yRot = ((float) (Mth.atan2(direction.x, direction.z) * (double) (180F / (float) Math.PI)));
        float yaw = (float) Math.toRadians(yRot);
        var left = new Vec3(-Math.cos(yaw), 0, Math.sin(yaw));
        var up = left.cross(direction);

        var offset = direction.scale(0.4f).add(up.scale(-0.3f));
        if (horizontalOffset != 0) {
            offset = offset.add(left.scale(horizontalOffset));
        }
        spawnSlashingParticle(effectType, attacker, offset, direction, slashAngle, mirrored);
    }

    public static void spawnSlashingParticle(ParticleEffectType effectType, LivingEntity attacker, Vec3 slashOffset, Vec3 slashDirection, float slashAngle, boolean mirror) {
        double xOffset = slashOffset.x;
        double yOffset = slashOffset.y + attacker.getBbHeight() * 0.5f;
        double zOffset = slashOffset.z;
        var position = attacker.position().add(xOffset, yOffset, zOffset);
        effectType.createPositionedEffect(attacker.level(), new PositionEffectData(position), SlashAttackParticleEffect.createData(slashDirection, mirror, slashAngle));
    }
}
