package com.sammy.malum.common.effect.aura;

import com.sammy.malum.*;
import com.sammy.malum.registry.common.MobEffectRegistry;
import com.sammy.malum.registry.common.SpiritTypeRegistry;
import io.github.fabricators_of_create.porting_lib.entity.events.player.PlayerEvent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import team.lodestar.lodestone.helpers.ColorHelper;

public class InfernalAura extends MobEffect {
    public InfernalAura() {
        super(MobEffectCategory.BENEFICIAL, ColorHelper.getColor(SpiritTypeRegistry.INFERNAL_SPIRIT.getPrimaryColor()));
        addAttributeModifier(Attributes.ATTACK_SPEED, MalumMod.malumPath("infernal_aura"), 0.2f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
    }

    public static void increaseDigSpeed(PlayerEvent.BreakSpeed event) {
        Player player = event.getEntity();
        if (player.hasEffect(MobEffectRegistry.MINERS_RAGE)) {
            final int amplifier = player.getEffect(MobEffectRegistry.MINERS_RAGE).getAmplifier()+1;
            event.setNewSpeed(event.getOriginalSpeed() * (1 + 0.2f * amplifier));
        }
    }
}