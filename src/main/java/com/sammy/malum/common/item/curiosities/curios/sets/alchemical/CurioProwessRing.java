package com.sammy.malum.common.item.curiosities.curios.sets.alchemical;

import com.sammy.malum.common.item.IMalumEventResponderItem;
import com.sammy.malum.common.item.curiosities.curios.MalumCurioItem;
import net.minecraft.network.chat.Component;
import net.minecraft.util.*;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import team.lodestar.lodestone.helpers.*;

import java.util.function.Consumer;

public class CurioProwessRing extends MalumCurioItem implements IMalumEventResponderItem {
    public CurioProwessRing(Properties builder) {
        super(builder, MalumTrinketType.ALCHEMICAL);
    }

    @Override
    public void addExtraTooltipLines(Consumer<Component> consumer) {
        consumer.accept(positiveEffect("spirits_xp"));
    }

    @Override
    public void pickupSpirit(LivingEntity collector, double arcaneResonance) {
        var level = collector.level();
        int generatedExperience = (int) Math.round(RandomHelper.randomBetween(level.random, 1, 4) * arcaneResonance);

        while (generatedExperience > 0) {
            int value = ExperienceOrb.getExperienceValue(generatedExperience);
            generatedExperience -= value;
            level.addFreshEntity(new ExperienceOrb(level, collector.getX(), collector.getY(), collector.getZ(), value));
        }
    }
}
