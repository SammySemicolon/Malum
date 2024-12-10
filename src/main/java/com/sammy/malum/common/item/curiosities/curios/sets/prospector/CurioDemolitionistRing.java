package com.sammy.malum.common.item.curiosities.curios.sets.prospector;

import com.sammy.malum.common.item.curiosities.curios.MalumCurioItem;
import com.sammy.malum.core.helpers.*;
import com.sammy.malum.registry.common.item.ItemRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import team.lodestar.lodestone.helpers.CurioHelper;

import java.util.function.Consumer;

public class CurioDemolitionistRing extends MalumCurioItem {

    public CurioDemolitionistRing(Properties builder) {
        super(builder, MalumTrinketType.METALLIC);
    }

    @Override
    public void addExtraTooltipLines(Consumer<Component> consumer) {
        consumer.accept(ComponentHelper.positiveCurioEffect("bigger_explosions"));
    }

    public static float increaseExplosionRadius(LivingEntity source, float original) {
        if (source != null && CurioHelper.hasCurioEquipped(source, ItemRegistry.RING_OF_THE_DEMOLITIONIST.get())) {
            return original + 1;
        }
        return original;
    }
}
