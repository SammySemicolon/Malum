package com.sammy.malum.core.helpers;

import com.sammy.malum.common.item.*;
import com.sammy.malum.core.systems.spirit.*;
import com.sammy.malum.visual_effects.networked.*;
import com.sammy.malum.visual_effects.networked.attack.slam.SlamAttackParticleEffect;
import com.sammy.malum.visual_effects.networked.data.*;
import com.sammy.malum.visual_effects.networked.attack.slash.*;
import net.minecraft.server.level.*;
import net.minecraft.util.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.phys.*;

public class ParticleHelper {

    public static WeaponParticleEffectBuilder createSlashingEffect(ParticleEffectType effectType) {
        return new WeaponParticleEffectBuilder(effectType, (d, b) -> SlashAttackParticleEffect.createData(d, b.isMirrored, b.slashAngle, b.spiritType));
    }
    public static WeaponParticleEffectBuilder createSlamEffect(ParticleEffectType effectType) {
        return new WeaponParticleEffectBuilder(effectType, (d, b) -> SlamAttackParticleEffect.createData(d, b.slashAngle, b.spiritType));
    }

    public static class WeaponParticleEffectBuilder {

        public final ParticleEffectType effectType;
        public final EffectDataSupplier supplier;
        public float horizontalOffset;
        public float slashAngle;
        public boolean isMirrored;
        public MalumSpiritType spiritType;

        public WeaponParticleEffectBuilder(ParticleEffectType effectType, EffectDataSupplier supplier) {
            this.effectType = effectType;
            this.supplier = supplier;
        }

        public WeaponParticleEffectBuilder setVertical() {
            return setVerticalSlashAngle().setHorizontalOffset(0.4f);
        }

        public WeaponParticleEffectBuilder setHorizontalOffset(float horizontalOffset) {
            this.horizontalOffset = horizontalOffset;
            return this;
        }

        public WeaponParticleEffectBuilder setVerticalSlashAngle() {
            return setSlashAngle(1.57f);
        }

        public WeaponParticleEffectBuilder setRandomSlashAngle(RandomSource randomSource) {
            return setSlashAngle(randomSource.nextFloat() * 3.14f);
        }

        public WeaponParticleEffectBuilder setSlashAngle(float slashAngle) {
            this.slashAngle = slashAngle;
            return this;
        }

        public WeaponParticleEffectBuilder mirrorRandomly(RandomSource randomSource) {
            return setMirrored(randomSource.nextBoolean());
        }

        public WeaponParticleEffectBuilder setMirrored(boolean isMirrored) {
            this.isMirrored = isMirrored;
            return this;
        }

        public WeaponParticleEffectBuilder setSpiritType(ISpiritAffiliatedItem spiritAffiliatedItem) {
            this.spiritType = spiritAffiliatedItem.getDefiningSpiritType();
            return this;
        }

        public WeaponParticleEffectBuilder setSpiritType(MalumSpiritType spiritType) {
            this.spiritType = spiritType;
            return this;
        }
        public void spawnForwardSlashingParticle(Entity attacker) {
            spawnForwardSlashingParticle(attacker, attacker.getLookAngle());
        }

        public void spawnForwardSlashingParticle(Entity attacker, Vec3 slashDirection) {
            float yRot = ((float) (Mth.atan2(slashDirection.x, slashDirection.z) * (double) (180F / (float) Math.PI)));
            float yaw = (float) Math.toRadians(yRot);
            var left = new Vec3(-Math.cos(yaw), 0, Math.sin(yaw));
            var up = left.cross(slashDirection);

            var offset = slashDirection.scale(0.4f).add(up.scale(-0.3f));
            if (horizontalOffset != 0) {
                offset = offset.add(left.scale(horizontalOffset));
            }
            spawnForwardSlashingParticle(attacker, offset, slashDirection);
        }

        public void spawnForwardSlashingParticle(Entity attacker, Vec3 slashOffset, Vec3 slashDirection) {
            if (attacker.level() instanceof ServerLevel serverLevel) {
                double xOffset = slashOffset.x;
                double yOffset = slashOffset.y + attacker.getBbHeight() * 0.5f;
                double zOffset = slashOffset.z;
                var position = attacker.position().add(xOffset, yOffset, zOffset);
                spawnSlashingParticle(serverLevel, position, slashDirection);
            }
        }

        public void spawnTargetBoundSlashingParticle(Entity attacker, Entity target) {
            var direction = attacker.getLookAngle();
            var random = attacker.getRandom();
            float yRot = ((float) (Mth.atan2(direction.x, direction.z) * (double) (180F / (float) Math.PI)));
            float yaw = (float) Math.toRadians(yRot);
            var left = new Vec3(-Math.cos(yaw), 0, Math.sin(yaw));
            var up = left.cross(direction);

            var slashDirection = target.position().subtract(attacker.position()).normalize();
            var offset = direction.scale(-1.4f).add(up.scale(-0.2f)).subtract(slashDirection.scale(0.5f + random.nextFloat() * 0.5f));
            if (horizontalOffset != 0) {
                offset = offset.add(left.scale(horizontalOffset));
            }
            spawnTargetBoundSlashingParticle(attacker, target, offset, slashDirection);
        }

        public void spawnTargetBoundSlashingParticle(Entity attacker, Entity target, Vec3 slashOffset, Vec3 slashDirection) {
            if (attacker.level() instanceof ServerLevel serverLevel) {
                double xOffset = slashOffset.x;
                double yOffset = slashOffset.y + attacker.getBbHeight() * 0.5f;
                double zOffset = slashOffset.z;
                var position = target.position().add(xOffset, yOffset, zOffset);
                spawnSlashingParticle(serverLevel, position, slashDirection);
            }
        }

        public void spawnSlashingParticle(ServerLevel level, Vec3 slashPosition, Vec3 slashDirection) {
            effectType.createPositionedEffect(level, new PositionEffectData(slashPosition), SlashAttackParticleEffect.createData(slashDirection, isMirrored, slashAngle, spiritType));
        }

        public interface EffectDataSupplier {
            NBTEffectData createData(Vec3 direction, WeaponParticleEffectBuilder builder);
        }
    }
}
