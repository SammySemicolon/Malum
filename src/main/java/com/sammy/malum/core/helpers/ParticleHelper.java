package com.sammy.malum.core.helpers;

import com.sammy.malum.common.item.spirit.*;
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
        return createEffect(effectType, (d, b) -> SlashAttackParticleEffect.createData(d, b.isMirrored, b.slashAngle));
    }
    public static WeaponParticleEffectBuilder createSlamEffect(ParticleEffectType effectType) {
        return createEffect(effectType, (d, b) -> SlamAttackParticleEffect.createData(d, b.slashAngle));
    }
    public static WeaponParticleEffectBuilder createEffect(ParticleEffectType effectType, WeaponParticleEffectBuilder.EffectDataSupplier supplier) {
        return new WeaponParticleEffectBuilder(effectType, supplier);
    }

    public static class WeaponParticleEffectBuilder {

        public final ParticleEffectType effectType;
        public final EffectDataSupplier supplier;
        public float horizontalOffset;
        public float slashAngle;
        public boolean isMirrored;
        public ColorEffectData color;


        public Vec3 positionOffset = Vec3.ZERO;
        public float horizontalDirectionOffset = 0, verticalDirectionOffset = 0, forwardOffset = 0, directionOffsetAngle = 0;

        protected WeaponParticleEffectBuilder(ParticleEffectType effectType, EffectDataSupplier supplier) {
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

        public WeaponParticleEffectBuilder setColor(ISpiritAffiliatedItem spiritAffiliatedItem) {
            return setColor(spiritAffiliatedItem.getDefiningSpiritType());
        }

        public WeaponParticleEffectBuilder setColor(MalumSpiritType spiritType) {
            return setColor(new ColorEffectData(spiritType));
        }

        public WeaponParticleEffectBuilder setColor(ColorEffectData color) {
            this.color = color;
            return this;
        }

        public WeaponParticleEffectBuilder setPositionOffset(float offset) {
            return setPositionOffset(offset, offset, offset);
        }

        public WeaponParticleEffectBuilder setPositionOffset(float x, float y, float z) {
            return setPositionOffset(new Vec3(x, y, z));
        }

        public WeaponParticleEffectBuilder setPositionOffset(Vec3 positionOffset) {
            this.positionOffset = positionOffset;
            return this;
        }

        public WeaponParticleEffectBuilder setDirectionOffset(float offset, float angle) {
            return setDirectionOffset(offset, offset, angle);
        }

        public WeaponParticleEffectBuilder setDirectionOffset(float horizontalDirectionOffset, float verticalDirectionOffset, float directionOffsetAngle) {
            this.horizontalDirectionOffset = horizontalDirectionOffset;
            this.verticalDirectionOffset = verticalDirectionOffset;
            this.directionOffsetAngle = directionOffsetAngle;
            return this;
        }

        public WeaponParticleEffectBuilder setForwardOffset(float forwardOffset) {
            this.forwardOffset = forwardOffset;
            return this;
        }

        public Vec3 getPosition(Vec3 position, Vec3 slashDirection) {
            return position.add(positionOffset).add(slashDirection.scale(forwardOffset));
        }

        public Vec3 getDirection(Vec3 direction) {
            if (horizontalDirectionOffset == 0 && verticalDirectionOffset == 0 && directionOffsetAngle == 0) {
                return direction;
            }
            float yRot = ((float) (Mth.atan2(direction.x, direction.z) * (double) (180F / (float) Math.PI)));
            float yaw = (float) Math.toRadians(yRot);
            var left = new Vec3(-Math.cos(yaw), 0, Math.sin(yaw));
            var up = left.cross(direction);
            return direction
                    .add(left.scale(Math.sin(directionOffsetAngle) * horizontalDirectionOffset))
                    .add(up.scale(Math.cos(directionOffsetAngle) * verticalDirectionOffset))
                    .normalize();
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

        public void spawnTargetBoundSlashingParticle(Entity attacker, Entity target, Vec3 slashDirection) {
            var direction = attacker.getLookAngle();
            var random = attacker.getRandom();
            float yRot = ((float) (Mth.atan2(direction.x, direction.z) * (double) (180F / (float) Math.PI)));
            float yaw = (float) Math.toRadians(yRot);
            var left = new Vec3(-Math.cos(yaw), 0, Math.sin(yaw));
            var up = left.cross(direction);

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
            effectType.createPositionedEffect(level,
                    new PositionEffectData(getPosition(slashPosition, slashDirection)),
                    color,
                    supplier.createData(getDirection(slashDirection), this));
        }

        public interface EffectDataSupplier {
            NBTEffectData createData(Vec3 direction, WeaponParticleEffectBuilder builder);
        }
    }
}
