package com.sammy.malum.core.helpers;

import com.sammy.malum.common.item.*;
import com.sammy.malum.core.systems.spirit.*;
import com.sammy.malum.visual_effects.networked.*;
import com.sammy.malum.visual_effects.networked.data.*;
import com.sammy.malum.visual_effects.networked.slash.*;
import net.minecraft.util.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import team.lodestar.lodestone.helpers.*;

public class ParticleHelper {

    public static SlashParticleEffectBuilder createSlashingEffect(ParticleEffectType effectType) {
        return new SlashParticleEffectBuilder(effectType);
    }

    public static class SlashParticleEffectBuilder {

        public final ParticleEffectType effectType;
        public float horizontalOffset;
        public float slashAngle;
        public boolean isMirrored;
        public MalumSpiritType spiritType;

        public SlashParticleEffectBuilder(ParticleEffectType effectType) {
            this.effectType = effectType;
        }

        public SlashParticleEffectBuilder setVertical() {
            return setVerticalSlashAngle().setHorizontalOffset(0.4f);
        }

        public SlashParticleEffectBuilder setHorizontalOffset(float horizontalOffset) {
            this.horizontalOffset = horizontalOffset;
            return this;
        }

        public SlashParticleEffectBuilder setVerticalSlashAngle() {
            return setSlashAngle(1.57f);
        }

        public SlashParticleEffectBuilder setRandomSlashAngle(RandomSource randomSource) {
            return setSlashAngle(randomSource.nextFloat() * 3.14f);
        }

        public SlashParticleEffectBuilder setSlashAngle(float slashAngle) {
            this.slashAngle = slashAngle;
            return this;
        }

        public SlashParticleEffectBuilder mirrorRandomly(RandomSource randomSource) {
            return setMirrored(randomSource.nextBoolean());
        }

        public SlashParticleEffectBuilder setMirrored(boolean isMirrored) {
            this.isMirrored = isMirrored;
            return this;
        }

        public SlashParticleEffectBuilder setSpiritType(ISpiritAffiliatedItem spiritAffiliatedItem) {
            this.spiritType = spiritAffiliatedItem.getDefiningSpiritType();
            return this;
        }

        public SlashParticleEffectBuilder setSpiritType(MalumSpiritType spiritType) {
            this.spiritType = spiritType;
            return this;
        }

        public void spawnForwardSlashingParticle(LivingEntity attacker) {
            var direction = attacker.getLookAngle();
            float yRot = ((float) (Mth.atan2(direction.x, direction.z) * (double) (180F / (float) Math.PI)));
            float yaw = (float) Math.toRadians(yRot);
            var left = new Vec3(-Math.cos(yaw), 0, Math.sin(yaw));
            var up = left.cross(direction);

            var offset = direction.scale(0.4f).add(up.scale(-0.3f));
            if (horizontalOffset != 0) {
                offset = offset.add(left.scale(horizontalOffset));
            }
            spawnForwardSlashingParticle(attacker, offset, direction);
        }

        public void spawnForwardSlashingParticle(LivingEntity attacker, Vec3 slashOffset, Vec3 slashDirection) {
            double xOffset = slashOffset.x;
            double yOffset = slashOffset.y + attacker.getBbHeight() * 0.5f;
            double zOffset = slashOffset.z;
            var position = attacker.position().add(xOffset, yOffset, zOffset);
            spawnSlashingParticle(attacker.level(), position, slashDirection);
        }

        public void spawnTargetBoundSlashingParticle(LivingEntity attacker, LivingEntity target) {
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

        public void spawnTargetBoundSlashingParticle(LivingEntity attacker, LivingEntity target, Vec3 slashOffset, Vec3 slashDirection) {
            double xOffset = slashOffset.x;
            double yOffset = slashOffset.y + attacker.getBbHeight() * 0.5f;
            double zOffset = slashOffset.z;
            var position = target.position().add(xOffset, yOffset, zOffset);
            spawnSlashingParticle(attacker.level(), position, slashDirection);
        }

        public void spawnSlashingParticle(Level level, Vec3 slashPosition, Vec3 slashDirection) {
            effectType.createPositionedEffect(level, new PositionEffectData(slashPosition), SlashAttackParticleEffect.createData(slashDirection, isMirrored, slashAngle, spiritType));
        }

        public interface SlashEffectDataSupplier {
            NBTEffectData createData(Vec3 slashPosition, boolean isMirrored, float slashAngle);
        }
    }
}
