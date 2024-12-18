package com.sammy.malum.common.item.curiosities.curios.runes.miracle;

import com.sammy.malum.common.item.curiosities.curios.runes.*;
import com.sammy.malum.core.helpers.*;
import com.sammy.malum.registry.common.SpiritTypeRegistry;
import com.sammy.malum.registry.common.item.ItemRegistry;
import io.github.fabricators_of_create.porting_lib.entity.events.living.MobEffectEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import team.lodestar.lodestone.helpers.EntityHelper;
import team.lodestar.lodestone.helpers.TrinketsHelper;

import java.util.function.Consumer;

public class RuneAlimentCleansingItem extends AbstractRuneCurioItem {

    public RuneAlimentCleansingItem(Properties builder) {
        super(builder, SpiritTypeRegistry.AQUEOUS_SPIRIT);
    }

    @Override
    public void addExtraTooltipLines(Consumer<Component> consumer) {
        consumer.accept(ComponentHelper.positiveCurioEffect("shorten_negative_effect"));
    }

    public static void onPotionApplied(MobEffectEvent.Added event) {
        LivingEntity entity = event.getEntity();
        if (event.getOldEffectInstance() == null && TrinketsHelper.hasTrinketEquipped(entity, ItemRegistry.RUNE_OF_ALIMENT_CLEANSING.get())) {
            MobEffectInstance effect = event.getEffectInstance();
            MobEffect type = effect.getEffect().value();
            if (type.getCategory().equals(MobEffectCategory.HARMFUL)) {
                EntityHelper.shortenEffect(effect, entity, effect.getDuration() / 3);
            }
        }
    }
}
