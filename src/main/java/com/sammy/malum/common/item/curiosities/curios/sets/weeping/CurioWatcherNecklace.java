package com.sammy.malum.common.item.curiosities.curios.sets.weeping;

import com.sammy.malum.common.capability.*;
import com.sammy.malum.common.entity.activator.*;
import com.sammy.malum.common.item.*;
import com.sammy.malum.common.item.curiosities.curios.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import team.lodestar.lodestone.helpers.*;

import java.util.function.*;

public class CurioWatcherNecklace extends MalumCurioItem implements IMalumEventResponderItem, IVoidItem {
    public CurioWatcherNecklace(Properties builder) {
        super(builder, MalumTrinketType.VOID);
    }

    @Override
    public void addExtraTooltipLines(Consumer<Component> consumer) {
        consumer.accept(positiveEffect("full_health_fake_collection"));
    }

    @Override
    public void hurtEvent(LivingDamageEvent.Post event, LivingEntity attacker, LivingEntity target, ItemStack stack) {
        if (target.getHealth() >= target.getMaxHealth() * 0.9875f) {
            MalumLivingEntityDataCapability.getCapabilityOptional(target).ifPresent(c -> {
                if (c.watcherNecklaceCooldown == 0) {
                    float speed = 0.4f;
                    final Level level = attacker.level();
                    var random = level.getRandom();
                    Vec3 position = target.position().add(0, target.getBbHeight() / 2f, 0);
                    int amount = target instanceof Player ? 5 : 2;
                    for (int i = 0; i < amount; i++) {
                        SpiritCollectionActivatorEntity entity = new SpiritCollectionActivatorEntity(level, attacker.getUUID(),
                                position.x,
                                position.y,
                                position.z,
                                RandomHelper.randomBetween(random, -speed, speed),
                                RandomHelper.randomBetween(random, 0.05f, 0.06f),
                                RandomHelper.randomBetween(random, -speed, speed));
                        level.addFreshEntity(entity);
                    }
                    c.watcherNecklaceCooldown = 100;
                }
            });
        }
    }

    public static void entityTick(EntityTickEvent event) {
        if (event.getEntity() instanceof LivingEntity entity) {
            MalumLivingEntityDataCapability.getCapabilityOptional(entity).ifPresent(c -> {
                if (c.watcherNecklaceCooldown > 0) {
                    c.watcherNecklaceCooldown--;
                }
            });
        }
    }
}