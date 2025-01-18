package com.sammy.malum.common.item.curiosities.weapons.staff;

import com.sammy.malum.common.entity.bolt.*;
import com.sammy.malum.common.item.ISpiritAffiliatedItem;
import com.sammy.malum.core.helpers.ComponentHelper;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import com.sammy.malum.registry.client.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.event.AddAttributeTooltipsEvent;
import team.lodestar.lodestone.handlers.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.registry.common.*;
import team.lodestar.lodestone.systems.easing.*;
import team.lodestar.lodestone.systems.particle.builder.*;
import team.lodestar.lodestone.systems.particle.data.*;
import team.lodestar.lodestone.systems.particle.data.spin.*;
import team.lodestar.lodestone.systems.particle.render_types.*;
import team.lodestar.lodestone.systems.particle.world.behaviors.components.*;

public class HexStaffItem extends AbstractStaffItem implements ISpiritAffiliatedItem {

    public HexStaffItem(Tier tier, float magicDamage, Properties builderIn) {
        super(tier, 15, magicDamage, builderIn);
    }

    @Override
    public void modifyAttributeTooltipEvent(AddAttributeTooltipsEvent event) {
        event.addTooltipLines(ComponentHelper.positiveEffect("hex_bolts"));
    }

    @Override
    public MalumSpiritType getDefiningSpiritType() {
        return SpiritTypeRegistry.WICKED_SPIRIT;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void spawnChargeParticles(Level pLevel, LivingEntity pLivingEntity, Vec3 pos, ItemStack pStack, float pct) {
        RandomSource random = pLevel.random;
        WorldParticleBuilder.create(ParticleRegistry.HEX_TARGET, new DirectionalBehaviorComponent(pLivingEntity.getLookAngle().normalize()))
                .setSpinData(SpinParticleData.createRandomDirection(random, 0.1f, 0.2f).setSpinOffset(RandomHelper.randomBetween(random, -0.314f, 0.314f)).build())
                .setTransparencyData(GenericParticleData.create(0.6f * pct, 0f).setEasing(Easing.SINE_IN_OUT, Easing.SINE_IN).build())
                .setScaleData(GenericParticleData.create(0.3f * pct, 0).setEasing(Easing.SINE_IN_OUT).build())
                .setColorData(SpiritTypeRegistry.WICKED_SPIRIT.createColorData().build())
                .setMotion(pLivingEntity.getLookAngle().normalize().scale(0.2f * pct))
                .setRenderTarget(RenderHandler.LATE_DELAYED_RENDER)
                .enableForcedSpawn()
                .setLifeDelay(2)
                .enableNoClip()
                .setLifetime(5)
                .spawn(pLevel, pos.x, pos.y, pos.z)
                .setRenderType(LodestoneWorldParticleRenderType.LUMITRANSPARENT)
                .spawn(pLevel, pos.x, pos.y, pos.z);
    }

    @Override
    public int getCooldownDuration(Level level, LivingEntity livingEntity) {
        return 80;
    }

    @Override
    public int getProjectileCount(Level level, LivingEntity livingEntity, float pct) {
        return pct == 1f ? 3 : 0;
    }

    @Override
    public void fireProjectile(LivingEntity player, ItemStack stack, Level level, InteractionHand hand, float chargePercentage, int count) {
        float pitchOffset = 3f + count;
        int spawnDelay = count * 3;
        float velocity = 3f + 0.5f * count;
        float magicDamage = (float) player.getAttributes().getValue(LodestoneAttributes.MAGIC_DAMAGE);
        Vec3 pos = getProjectileSpawnPos(player, hand, 0.5f, 0.5f);
        HexBoltEntity entity = new HexBoltEntity(level, pos.x, pos.y, pos.z);
        entity.setData(player, magicDamage, spawnDelay);
        entity.setItem(stack);

        entity.shootFromRotation(player, player.getXRot(), player.getYRot(), -pitchOffset, velocity, 0F);
        level.addFreshEntity(entity);
    }
}
