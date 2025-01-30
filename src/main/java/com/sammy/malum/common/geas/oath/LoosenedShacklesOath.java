package com.sammy.malum.common.geas.oath;

import com.sammy.malum.core.systems.geas.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.neoforged.neoforge.common.*;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.event.tick.*;
import team.lodestar.lodestone.helpers.*;

public class LoosenedShacklesOath extends GeasEffect {

    public int deaths;
    public long scheduledDeath;

    public LoosenedShacklesOath() {
        super(MalumGeasEffectTypeRegistry.OATH_OF_THE_LOOSENED_SHACKLES.get());
    }

    @Override
    public void update(EntityTickEvent.Pre event, LivingEntity entity) {
        final Level level = entity.level();
        if (deaths > 0 && level.getGameTime() >= scheduledDeath) {
            entity.hurt(DamageTypeHelper.create(level, DamageTypeRegistry.VOID), 1000f);
            deaths = 0;
            scheduledDeath = 0;
        }
    }

    @Override
    public void incomingDeathEvent(LivingDeathEvent event, LivingEntity attacker, LivingEntity target, ItemStack stack) {
        var source = event.getSource();
        if (source.is(Tags.DamageTypes.IS_TECHNICAL)) {
            return;
        }
        if (source.is(DamageTypeRegistry.VOID)) {
            return;
        }
        event.setCanceled(true);
        target.setHealth(1);
        deaths++;
        scheduledDeath = target.level().getGameTime() + 100;
    }

    @Override
    public void incomingDamageEvent(LivingDamageEvent.Pre event, LivingEntity attacker, LivingEntity target, ItemStack stack) {
        if (event.getSource().is(DamageTypeRegistry.VOID)) {
            final float damage = event.getNewDamage() * target.getMaxHealth() * 1000f;
            event.setNewDamage(damage);
        }
    }
}