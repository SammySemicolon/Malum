package com.sammy.malum.common.spiritrite.eldritch;

import com.sammy.malum.common.block.curiosities.totem.TotemBaseBlockEntity;
import com.sammy.malum.common.packets.particle.curiosities.rite.generic.MajorEntityEffectParticlePacket;
import com.sammy.malum.common.spiritrite.TotemicRiteEffect;
import com.sammy.malum.common.spiritrite.TotemicRiteType;
import com.sammy.malum.registry.common.DamageTypeRegistry;
import net.minecraft.server.level.*;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.PacketDistributor;
import team.lodestar.lodestone.helpers.*;

import java.util.*;
import java.util.stream.Collectors;

import static com.sammy.malum.registry.common.PacketRegistry.MALUM_CHANNEL;
import static com.sammy.malum.registry.common.SpiritTypeRegistry.*;

public class EldritchWickedRiteType extends TotemicRiteType {
    public EldritchWickedRiteType() {
        super("greater_wicked_rite", ELDRITCH_SPIRIT, ARCANE_SPIRIT, WICKED_SPIRIT, WICKED_SPIRIT);
    }

    @Override
    public TotemicRiteEffect getNaturalRiteEffect() {
        return new TotemicRiteEffect(TotemicRiteEffect.MalumRiteEffectCategory.LIVING_ENTITY_EFFECT) {
            @Override
            public void doRiteEffect(TotemBaseBlockEntity totemBase, ServerLevel level) {
                getNearbyEntities(totemBase, LivingEntity.class, e -> !(e instanceof Player)).forEach(e -> {
                    if (e.getHealth() <= 2.5f && !e.isInvulnerableTo(DamageTypeHelper.create(e.level(), DamageTypeRegistry.VOODOO_PLAYERLESS))) {
                        MALUM_CHANNEL.send(PacketDistributor.TRACKING_ENTITY.with(() -> e), new MajorEntityEffectParticlePacket(getIdentifyingSpirit().getPrimaryColor(), e.getX(), e.getY() + e.getBbHeight() / 2f, e.getZ()));
                        e.hurt(DamageTypeHelper.create(e.level(), DamageTypeRegistry.VOODOO_PLAYERLESS), 10f);
                    }
                });
            }
        };
    }

    @Override
    public TotemicRiteEffect getCorruptedEffect() {
        return new TotemicRiteEffect(TotemicRiteEffect.MalumRiteEffectCategory.LIVING_ENTITY_EFFECT) {
            @Override
            public void doRiteEffect(TotemBaseBlockEntity totemBase, ServerLevel level) {
                Map<Class<? extends Animal>, List<Animal>> animalMap = getNearbyEntities(totemBase, Animal.class, e -> e.getAge() > 0 && !e.isInvulnerableTo(DamageTypeHelper.create(e.level(), DamageTypeRegistry.VOODOO_PLAYERLESS))).collect(Collectors.groupingBy(Animal::getClass));
                for (List<Animal> animals : animalMap.values()) {
                    if (animals.size() < 20) {
                        return;
                    }
                    int maxKills = animals.size() - 20;
                    animals.removeIf(Animal::isInLove);
                    for (Animal entity : animals) {
                        entity.hurt(DamageTypeHelper.create(entity.level(), DamageTypeRegistry.VOODOO_PLAYERLESS), entity.getMaxHealth());
                        MALUM_CHANNEL.send(PacketDistributor.TRACKING_ENTITY.with(() -> entity), new MajorEntityEffectParticlePacket(WICKED_SPIRIT.getPrimaryColor(), entity.getX(), entity.getY() + entity.getBbHeight() / 2f, entity.getZ()));
                        if (maxKills-- <= 0) {
                            return;
                        }
                    }
                }
            }
        };
    }
}