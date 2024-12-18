package com.sammy.malum.common.item.curiosities.curios.sets.prospector;

import com.sammy.malum.common.item.curiosities.curios.MalumCurioItem;
import com.sammy.malum.core.helpers.*;
import com.sammy.malum.registry.common.item.ItemRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import team.lodestar.lodestone.helpers.TrinketsHelper;

import java.util.function.Consumer;

public class CurioHoarderRing extends MalumCurioItem {

    public CurioHoarderRing(Properties builder) {
        super(builder, MalumTrinketType.METALLIC);
    }

    @Override
    public void addExtraTooltipLines(Consumer<Component> consumer) {
        consumer.accept(ComponentHelper.positiveCurioEffect("explosion_drops_collected"));
    }

    public static boolean hasHoarderRing(LivingEntity entity) {
        return entity != null && TrinketsHelper.hasTrinketEquipped(entity, ItemRegistry.RING_OF_THE_HOARDER.get());
    }

    public static BlockPos getExplosionPos(boolean hasTheRing, BlockPos originalPos, LivingEntity entity, ItemStack droppedItem) {
        if (hasTheRing) {
            ItemStack itemInHand = entity.getItemInHand(InteractionHand.OFF_HAND);
            if (!itemInHand.isEmpty()) {
                if (!droppedItem.getItem().equals(itemInHand.getItem())) {
                    return originalPos;
                }
            }
        }
        return hasTheRing ? entity.blockPosition().above() : originalPos;
    }
}
