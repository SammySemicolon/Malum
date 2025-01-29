package com.sammy.malum.common.item.curiosities.weapons.staff;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.entity.bolt.*;
import com.sammy.malum.common.entity.nitrate.*;
import com.sammy.malum.common.item.ISpiritAffiliatedItem;
import com.sammy.malum.core.helpers.ComponentHelper;
import com.sammy.malum.core.helpers.ParticleHelper;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import com.sammy.malum.registry.client.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import net.neoforged.api.distmarker.*;
import net.neoforged.neoforge.client.event.AddAttributeTooltipsEvent;
import net.neoforged.neoforge.event.entity.living.*;
import team.lodestar.lodestone.handlers.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.registry.common.*;
import team.lodestar.lodestone.registry.common.tag.LodestoneDamageTypeTags;
import team.lodestar.lodestone.systems.easing.*;
import team.lodestar.lodestone.systems.item.*;
import team.lodestar.lodestone.systems.particle.builder.*;
import team.lodestar.lodestone.systems.particle.data.*;
import team.lodestar.lodestone.systems.particle.data.color.*;
import team.lodestar.lodestone.systems.particle.data.spin.*;
import team.lodestar.lodestone.systems.particle.render_types.*;
import team.lodestar.lodestone.systems.particle.world.behaviors.components.*;

public class UnwindingChaosStaffItem extends AbstractStaffItem implements ISpiritAffiliatedItem {

    public static final ColorParticleData AURIC_COLOR_DATA = EthericNitrateEntity.AURIC_COLOR_DATA;

    public UnwindingChaosStaffItem(Tier tier, float magicDamage, float chargeDuration, LodestoneItemProperties properties) {
        super(tier, -3.2f, magicDamage, chargeDuration, properties);
    }

    @Override
    public void modifyAttributeTooltipEvent(AddAttributeTooltipsEvent event) {
        event.addTooltipLines(ComponentHelper.positiveEffect("unwinding_chaos_volley"));
        event.addTooltipLines(ComponentHelper.positiveEffect("unwinding_chaos_burn"));
    }

    @Override
    public MalumSpiritType getDefiningSpiritType() {
        var spirits = new MalumSpiritType[]{SpiritTypeRegistry.INFERNAL_SPIRIT, SpiritTypeRegistry.SACRED_SPIRIT, SpiritTypeRegistry.AQUEOUS_SPIRIT, SpiritTypeRegistry.EARTHEN_SPIRIT};
        return spirits[MalumMod.RANDOM.nextInt(spirits.length)];
    }

    @Override
    public void outgoingDeathEvent(LivingDeathEvent event, LivingEntity attacker, LivingEntity target, ItemStack stack) {
        if (event.getSource().is(DamageTypeTags.IS_FIRE)) {
            var data = attacker.getData(AttachmentTypeRegistry.STAFF_ABILITIES);
            if (data.unwindingChaosBurnStacks < 6) {
                data.unwindingChaosBurnStacks++;
                float pitch = RandomHelper.randomBetween(attacker.level().getRandom(), 0.75f, 1.25f);
                SoundHelper.playSound(target, SoundRegistry.WORLDSOUL_MOTIF_LIGHT_IMPACT.get(), attacker.getSoundSource(), 1.5f, pitch);
            }
        }
    }

    @Override
    public void finalizedOutgoingDamageEvent(LivingDamageEvent.Post event, LivingEntity attacker, LivingEntity target, ItemStack stack) {
        DamageSource source = event.getSource();
        Level level = attacker.level();
        RandomSource random = level.random;
        if (source.is(DamageTypeTags.IS_FIRE)) {
            var data = attacker.getData(AttachmentTypeRegistry.STAFF_ABILITIES);
            if (data.unwindingChaosBurnStacks < 6) {
                data.unwindingChaosBurnStacks++;
                float pitch = RandomHelper.randomBetween(attacker.level().getRandom(), 0.75f, 1.25f);
                SoundHelper.playSound(target, SoundRegistry.WORLDSOUL_MOTIF_LIGHT_IMPACT.get(), attacker.getSoundSource(), 1.5f, pitch);
            }
        }
        boolean canTriggerMagic = source.is(LodestoneDamageTypeTags.CAN_TRIGGER_MAGIC);
        if (canTriggerMagic || source.is(DamageTypeRegistry.SOULWASHING_PROPAGATION)) {
            target.igniteForTicks(100);
        }
        if (canTriggerMagic) {
            for (int i = 0; i < 3; i++) {
                var particle = ParticleHelper.createSlamEffect(ParticleEffectTypeRegistry.STAFF_SLAM)
                        .setSpiritType(getDefiningSpiritType())
                        .setPositionOffset(RandomHelper.randomBetween(random, 0.3f, 0.8f) * (random.nextBoolean() ? 1 : -1))
                        .setDirectionOffset(RandomHelper.randomBetween(random, -0.4f, 0.4f), random.nextFloat() * 6.28f)
                        .setRandomSlashAngle(random);
                particle.spawnTargetBoundSlashingParticle(attacker, target);
            }
            float pitch = RandomHelper.randomBetween(level.getRandom(), 0.75f, 2f);
            SoundHelper.playSound(attacker, SoundRegistry.WORLDSOUL_MOTIF_HEAVY_IMPACT.get(), 2f, pitch);
        }
        super.finalizedOutgoingDamageEvent(event, attacker, target, stack);
    }

    @Override
    public int getCooldownDuration(Level level, LivingEntity livingEntity) {
        return 160;
    }

    @Override
    public int getProjectileCount(Level level, LivingEntity livingEntity, float pct) {
        if (pct == 1f) {
            var data = livingEntity.getData(AttachmentTypeRegistry.STAFF_ABILITIES);
            int projectileCount = Mth.clamp(3+data.unwindingChaosBurnStacks*2, 0, 15);
            data.unwindingChaosBurnStacks = 0;
            return projectileCount;
        }
        return 0;
    }

    @Override
    public void fireProjectile(LivingEntity player, ItemStack stack, Level level, InteractionHand hand, int count) {
        int ceil =  Mth.ceil(count / 2f);
        float spread = count > 0 ? ceil * 0.1f * (count % 2L == 0 ? 1 : -1) : 0f;
        float pitchOffset = count > 4 ? 2f + (2f - ceil * 1.5f) : 0.5f;
        int spawnDelay = 1 + ceil * 2;
        float velocity = 3f;
        float magicDamage = (float) player.getAttributes().getValue(LodestoneAttributes.MAGIC_DAMAGE);
        Vec3 pos = getProjectileSpawnPos(player, hand, 0.5f, 0.5f);
        EntropicFlameBoltEntity entity = new EntropicFlameBoltEntity(level, pos.x, pos.y, pos.z);
        entity.setData(player, magicDamage, spawnDelay);
        entity.setItem(stack);
        entity.shootFromRotation(player, player.getXRot(), player.getYRot(), -pitchOffset, velocity, 0f);
        Vec3 projectileDirection = entity.getDeltaMovement();
        float yRot = ((float) (Mth.atan2(projectileDirection.x, projectileDirection.z) * (double) (180F / (float) Math.PI)));
        float yaw = (float) Math.toRadians(yRot);
        Vec3 left = new Vec3(-Math.cos(yaw), 0, Math.sin(yaw));
        entity.setDeltaMovement(entity.getDeltaMovement().add(left.scale(spread)));
        level.addFreshEntity(entity);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void spawnChargeParticles(Level pLevel, LivingEntity pLivingEntity, Vec3 pos, ItemStack pStack, float pct) {
        RandomSource random = pLevel.random;
        WorldParticleBuilder.create(ParticleRegistry.AURIC_TARGET, new DirectionalBehaviorComponent(pLivingEntity.getLookAngle().normalize()))
                .setSpinData(SpinParticleData.createRandomDirection(random, 0.1f, 0.2f).setSpinOffset(RandomHelper.randomBetween(random, -0.314f, 0.314f)).build())
                .setTransparencyData(GenericParticleData.create(0.5f * pct, 0f).setEasing(Easing.SINE_IN_OUT, Easing.SINE_IN).build())
                .setScaleData(GenericParticleData.create(0.3f * pct, 0).setEasing(Easing.SINE_IN_OUT).build())
                .setMotion(pLivingEntity.getLookAngle().normalize().scale(0.2f * pct))
                .setRenderTarget(RenderHandler.LATE_DELAYED_RENDER)
                .setColorData(SpiritTypeRegistry.INFERNAL_SPIRIT.createColorData().build())
                .enableForcedSpawn()
                .setLifeDelay(2)
                .setLifetime(5)
                .enableNoClip()
                .spawn(pLevel, pos.x, pos.y, pos.z)
                .setRenderType(LodestoneWorldParticleRenderType.LUMITRANSPARENT)
                .spawn(pLevel, pos.x, pos.y, pos.z);
    }
}