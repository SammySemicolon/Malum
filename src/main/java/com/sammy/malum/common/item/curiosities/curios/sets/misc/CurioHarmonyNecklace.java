package com.sammy.malum.common.item.curiosities.curios.sets.misc;

import com.sammy.malum.common.item.curiosities.curios.*;
import com.sammy.malum.core.helpers.*;
import com.sammy.malum.core.systems.spirit.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.item.*;
import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingEvent;
import net.minecraft.network.chat.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import team.lodestar.lodestone.helpers.TrinketsHelper;

import java.util.function.*;

public class CurioHarmonyNecklace extends MalumCurioItem {
    public CurioHarmonyNecklace(Properties builder) {
        super(builder, MalumTrinketType.METALLIC);
    }

    @Override
    public void addExtraTooltipLines(Consumer<Component> consumer) {
        consumer.accept(ComponentHelper.positiveCurioEffect("friendly_enemies"));
    }

    public static void preventDetection(LivingEvent.LivingVisibilityEvent event) {
        if (event.getLookingEntity() instanceof LivingEntity watcher) {
            LivingEntity target = event.getEntity();
            if (TrinketsHelper.hasTrinketEquipped(target, ItemRegistry.NECKLACE_OF_BLISSFUL_HARMONY.get())) {
                float visibilityModifier = EntitySpiritDropData.getSpiritData(watcher).map(data -> 0.5f / (1 + data.dataEntries.stream().map(s -> s.equals(SpiritTypeRegistry.WICKED_SPIRIT) ? 1 : 0).count())).orElse(0.5f);
                if (target.hasEffect(MobEffects.INVISIBILITY)) {
                    visibilityModifier *= 0.5f;
                }
                event.modifyVisibility(visibilityModifier);
            }
        }
    }
}
