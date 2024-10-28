package com.sammy.malum.common.item.curiosities.curios.sets.misc;

import com.sammy.malum.common.item.curiosities.curios.*;
import com.sammy.malum.core.handlers.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.item.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import team.lodestar.lodestone.helpers.*;

import java.util.function.*;

public class CurioHarmonyNecklace extends MalumCurioItem {
    public CurioHarmonyNecklace(Properties builder) {
        super(builder, MalumTrinketType.METALLIC);
    }

    @Override
    public void addExtraTooltipLines(Consumer<Component> consumer) {
        consumer.accept(positiveEffect("friendly_enemies"));
    }

    public static void preventDetection(LivingEvent.LivingVisibilityEvent event) {
        if (event.getLookingEntity() instanceof LivingEntity watcher) {
            LivingEntity target = event.getEntity();
            if (CurioHelper.hasCurioEquipped(target, ItemRegistry.NECKLACE_OF_BLISSFUL_HARMONY.get())) {
                float visibilityModifier = SpiritHarvestHandler.getSpiritData(watcher).map(data -> 0.5f / (1 + data.dataEntries.stream().map(s -> s.equals(SpiritTypeRegistry.WICKED_SPIRIT) ? 1 : 0).count())).orElse(0.5f);
                if (target.hasEffect(MobEffects.INVISIBILITY)) {
                    visibilityModifier *= 0.5f;
                }
                event.modifyVisibility(visibilityModifier);
            }
        }
    }
}
