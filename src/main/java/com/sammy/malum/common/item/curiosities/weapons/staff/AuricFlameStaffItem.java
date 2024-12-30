package com.sammy.malum.common.item.curiosities.weapons.staff;

import com.sammy.malum.common.entity.bolt.*;
import com.sammy.malum.common.entity.nitrate.*;
import com.sammy.malum.common.item.ISpiritAffiliatedItem;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import com.sammy.malum.registry.client.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import net.neoforged.api.distmarker.*;
import net.neoforged.neoforge.event.entity.living.*;
import team.lodestar.lodestone.handlers.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.registry.common.*;
import team.lodestar.lodestone.systems.easing.*;
import team.lodestar.lodestone.systems.particle.builder.*;
import team.lodestar.lodestone.systems.particle.data.*;
import team.lodestar.lodestone.systems.particle.data.color.*;
import team.lodestar.lodestone.systems.particle.data.spin.*;
import team.lodestar.lodestone.systems.particle.render_types.*;
import team.lodestar.lodestone.systems.particle.world.behaviors.components.*;

public class AuricFlameStaffItem extends AbstractStaffItem implements ISpiritAffiliatedItem {

    public static final ColorParticleData AURIC_COLOR_DATA = EthericNitrateEntity.AURIC_COLOR_DATA;

    public AuricFlameStaffItem(Tier tier, float magicDamage, Properties builderIn) {
        super(tier, -0.2f, 30, magicDamage, builderIn);
    }
    @Override
    public MalumSpiritType getDefiningSpiritType() {
        return SpiritTypeRegistry.INFERNAL_SPIRIT;
    }
    @Override
    public void outgoingDamageEvent(LivingDamageEvent.Pre event, LivingEntity attacker, LivingEntity target, ItemStack stack) {
        if (!(event.getSource().getDirectEntity() instanceof AbstractBoltProjectileEntity)) {
            target.setRemainingFireTicks(4 * 20);
            attacker.level().playSound(null, target.getX(), target.getY(), target.getZ(), SoundRegistry.AURIC_FLAME_MOTIF.get(), attacker.getSoundSource(), 1, 1.25f);
        }
        super.outgoingDamageEvent(event, attacker, target, stack);
    }

    @Override
    public int getCooldownDuration(Level level, LivingEntity livingEntity) {
        return 160;
    }

    @Override
    public int getProjectileCount(Level level, LivingEntity livingEntity, float pct) {
        return pct == 1f ? 5 : 0;
    }

    @Override
    public void fireProjectile(LivingEntity player, ItemStack stack, Level level, InteractionHand hand, float chargePercentage, int count) {
        final float ceil = (float) Math.ceil(count / 2f);
        float spread = count > 0 ? ceil * 0.05f * (count % 2L == 0 ? 1 : -1) : 0f;
        float pitchOffset = count > 0 ? 4f + (2f - ceil * 4f) : -2.5f;
        int spawnDelay = 1 + count * 2;
        float velocity = 2f;
        float magicDamage = (float) player.getAttributes().getValue(LodestoneAttributes.MAGIC_DAMAGE) - 2;
        Vec3 pos = getProjectileSpawnPos(player, hand, 0.5f, 0.5f);
        AuricFlameBoltEntity entity = new AuricFlameBoltEntity(level, pos.x, pos.y, pos.z);
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
        final SpinParticleData spinData = SpinParticleData.createRandomDirection(random, 0.25f, 0.5f).setSpinOffset(RandomHelper.randomBetween(random, 0f, 6.28f)).build();
        WorldParticleBuilder.create(ParticleRegistry.HEXAGON, new DirectionalBehaviorComponent(pLivingEntity.getLookAngle().normalize()))
                .setRenderTarget(RenderHandler.LATE_DELAYED_RENDER)
                .setTransparencyData(GenericParticleData.create(0.5f * pct, 0f).setEasing(Easing.SINE_IN_OUT, Easing.SINE_IN).build())
                .setScaleData(GenericParticleData.create(0.35f * pct, 0).setEasing(Easing.SINE_IN_OUT).build())
                .setSpinData(spinData)
                .setColorData(AURIC_COLOR_DATA)
                .setLifetime(5)
                .setMotion(pLivingEntity.getLookAngle().normalize().scale(0.05f))
                .enableNoClip()
                .enableForcedSpawn()
                .setLifeDelay(2)
                .spawn(pLevel, pos.x, pos.y, pos.z)
                .setRenderType(LodestoneWorldParticleRenderType.LUMITRANSPARENT)
                .spawn(pLevel, pos.x, pos.y, pos.z);;
    }
}