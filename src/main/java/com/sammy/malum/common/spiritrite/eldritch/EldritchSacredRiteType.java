package com.sammy.malum.common.spiritrite.eldritch;

import com.sammy.malum.common.block.curiosities.totem.*;
import com.sammy.malum.common.spiritrite.*;
import com.sammy.malum.registry.common.ParticleEffectTypeRegistry;
import com.sammy.malum.visual_effects.networked.data.ColorEffectData;
import net.minecraft.core.*;
import net.minecraft.server.level.*;
import net.minecraft.world.entity.animal.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;

import java.util.*;
import java.util.stream.*;

import static com.sammy.malum.registry.common.SpiritTypeRegistry.*;

public class EldritchSacredRiteType extends TotemicRiteType {
    public EldritchSacredRiteType() {
        super("greater_sacred_rite", ELDRITCH_SPIRIT, ARCANE_SPIRIT, SACRED_SPIRIT, SACRED_SPIRIT);
    }

    @Override
    public TotemicRiteEffect getNaturalRiteEffect() {
        return new TotemicRiteEffect(TotemicRiteEffect.MalumRiteEffectCategory.RADIAL_BLOCK_EFFECT) {

            @Override
            public int getRiteEffectHorizontalRadius() {
                return 4;
            }

            @Override
            public int getRiteEffectVerticalRadius() {
                return 2;
            }

            @Override
            public void doRiteEffect(TotemBaseBlockEntity totemBase, ServerLevel level) {
                BlockPos pos = totemBase.getBlockPos();
                getNearbyBlocks(totemBase, BonemealableBlock.class).forEach(p -> {
                    if (level.random.nextFloat() <= 0.06f) {
                        BlockState state = level.getBlockState(p);
                        final Block block = state.getBlock();
                        if (block instanceof CropBlock) {
                            for (int i = 0; i < 5 + level.random.nextInt(3); i++) {
                                state.randomTick(level, p, level.random);
                            }
                        }
                        else if (block instanceof BonemealableBlock bonemealableBlock && bonemealableBlock.isValidBonemealTarget(level, p, state)) {
                            if (bonemealableBlock.isBonemealSuccess(level, level.random, p, state)) {
                                bonemealableBlock.performBonemeal(level, level.random, p, state);
                            }
                        }
                        BlockPos particlePos = state.canOcclude() ? p : p.below();
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
                Map<Class<? extends Animal>, List<Animal>> animalMap = getNearbyEntities(totemBase, Animal.class).collect(Collectors.groupingBy(Animal::getClass));

                for (List<Animal> animals : animalMap.values()) {
                    if (animals.size() > 20) {
                        continue;
                    }
                    animals.forEach(e -> {
                        if (e.canFallInLove() && e.getAge() == 0) {
                            if (level.random.nextFloat() <= 0.2f) {
                                e.setInLoveTime(600);
                                ParticleEffectTypeRegistry.RITE_EFFECT_TRIGGERED.createEntityEffect(e, new ColorEffectData(SACRED_SPIRIT));
                            }
                        }
                    });
                }
            }
        };
    }
}