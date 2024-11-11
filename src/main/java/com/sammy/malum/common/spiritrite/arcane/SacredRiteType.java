package com.sammy.malum.common.spiritrite.arcane;

import com.sammy.malum.common.block.curiosities.totem.TotemBaseBlockEntity;
import com.sammy.malum.common.spiritrite.TotemicRiteEffect;
import com.sammy.malum.common.spiritrite.TotemicRiteType;
import com.sammy.malum.registry.common.ParticleEffectTypeRegistry;
import com.sammy.malum.visual_effects.networked.data.ColorEffectData;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.EatBlockGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.animal.allay.*;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Blocks;

import java.util.HashMap;
import java.util.Map;

import static com.sammy.malum.registry.common.SpiritTypeRegistry.ARCANE_SPIRIT;
import static com.sammy.malum.registry.common.SpiritTypeRegistry.SACRED_SPIRIT;
import static net.minecraft.world.entity.ai.goal.EatBlockGoal.IS_TALL_GRASS;

public class SacredRiteType extends TotemicRiteType {

    public SacredRiteType() {
        super("sacred_rite", ARCANE_SPIRIT, SACRED_SPIRIT, SACRED_SPIRIT);
    }

    @Override
    public TotemicRiteEffect getNaturalRiteEffect() {
        return new TotemicRiteEffect(TotemicRiteEffect.MalumRiteEffectCategory.LIVING_ENTITY_EFFECT) {
            @Override
            public void doRiteEffect(TotemBaseBlockEntity totemBase, ServerLevel level) {
                getNearbyEntities(totemBase, LivingEntity.class, e -> !(e instanceof Monster)).forEach(e -> {
                    if (e.getHealth() < e.getMaxHealth()) {
                        e.heal(2);
                        ParticleEffectTypeRegistry.HEXING_SMOKE.createEntityEffect(e, new ColorEffectData(SACRED_SPIRIT.getPrimaryColor()));
                    }
                });
            }
        };
    }

    @Override
    public TotemicRiteEffect getCorruptedEffect() {
        return new TotemicRiteEffect(TotemicRiteEffect.MalumRiteEffectCategory.LIVING_ENTITY_EFFECT) {
            @SuppressWarnings("DataFlowIssue")
            @Override
            public void doRiteEffect(TotemBaseBlockEntity totemBase, ServerLevel level) {
                getNearbyEntities(totemBase, Mob.class).forEach(e -> {
                    if (e instanceof Animal animal) {
                        if (animal.getAge() < 0) {
                            if (totemBase.getLevel().random.nextFloat() <= 0.04f) {
                                ParticleEffectTypeRegistry.HEXING_SMOKE.createEntityEffect(e, new ColorEffectData(SACRED_SPIRIT.getPrimaryColor()));
                                animal.ageUp(25);
                            }
                        }
                    }
                    if (NOURISHMENT_RITE_ACTORS.containsKey(e.getClass())) {
                        NourishmentRiteActor<? extends Mob> nourishmentRiteActor = NOURISHMENT_RITE_ACTORS.get(e.getClass());
                        nourishmentRiteActor.tryAct(totemBase, e);
                    }
                });
            }
        };
    }

    public static final Map<Class<? extends Mob>, NourishmentRiteActor<?>> NOURISHMENT_RITE_ACTORS = Util.make(new HashMap<>(), m -> {
        m.put(Sheep.class, new NourishmentRiteActor<>(Sheep.class) {
            @Override
            public void act(TotemBaseBlockEntity totemBaseBlockEntity, Sheep sheep) {
                if (sheep.getRandom().nextFloat() < 0.6f) {
                    BlockPos blockpos = sheep.blockPosition();
                    final Level level = sheep.level();
                    if (IS_TALL_GRASS.test(level.getBlockState(blockpos)) || level.getBlockState(blockpos.below()).is(Blocks.GRASS_BLOCK)) {
                        EatBlockGoal goal = sheep.eatBlockGoal;
                        goal.start();
                        ParticleEffectTypeRegistry.HEXING_SMOKE.createEntityEffect(sheep, new ColorEffectData(SACRED_SPIRIT.getPrimaryColor()));
                    }
                }
            }
        });
        m.put(Bee.class, new NourishmentRiteActor<>(Bee.class) {
            @Override
            public void act(TotemBaseBlockEntity totemBaseBlockEntity, Bee bee) {
                Bee.BeePollinateGoal goal = bee.beePollinateGoal;
                if (goal.canBeeUse()) {
                    goal.successfulPollinatingTicks += 40;
                    goal.tick();
                    ParticleEffectTypeRegistry.HEXING_SMOKE.createEntityEffect(bee, new ColorEffectData(SACRED_SPIRIT.getPrimaryColor()));
                }
            }
        });

        m.put(Chicken.class, new NourishmentRiteActor<>(Chicken.class) {
            @Override
            public void act(TotemBaseBlockEntity totemBaseBlockEntity, Chicken chicken) {
                chicken.eggTime -= 80;
                ParticleEffectTypeRegistry.HEXING_SMOKE.createEntityEffect(chicken, new ColorEffectData(SACRED_SPIRIT.getPrimaryColor()));
            }
        });

        m.put(Allay.class, new NourishmentRiteActor<>(Allay.class) {
            @Override
            public void act(TotemBaseBlockEntity totemBaseBlockEntity, Allay allay) {
                allay.duplicationCooldown -= 80;
                ParticleEffectTypeRegistry.HEXING_SMOKE.createEntityEffect(allay, new ColorEffectData(SACRED_SPIRIT.getPrimaryColor()));
            }
        });
    });

    public static abstract class NourishmentRiteActor<T extends Mob> {
        public final Class<T> targetClass;

        public NourishmentRiteActor(Class<T> targetClass) {
            this.targetClass = targetClass;
        }

        @SuppressWarnings("unchecked")
        public final void tryAct(TotemBaseBlockEntity totemBaseBlockEntity, Mob mob) {
            if (targetClass.isInstance(mob)) {
                act(totemBaseBlockEntity, (T) mob);
            }
        }

        public abstract void act(TotemBaseBlockEntity totemBaseBlockEntity, T entity);
    }
}
