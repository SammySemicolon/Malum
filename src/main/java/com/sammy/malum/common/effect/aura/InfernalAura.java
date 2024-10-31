package com.sammy.malum.common.effect.aura;

import com.sammy.malum.*;
import com.sammy.malum.registry.common.MobEffectRegistry;
import com.sammy.malum.registry.common.SpiritTypeRegistry;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import team.lodestar.lodestone.helpers.ColorHelper;

public class InfernalAura extends MobEffect {
    public InfernalAura() {
        super(MobEffectCategory.BENEFICIAL, ColorHelper.getColor(SpiritTypeRegistry.INFERNAL_SPIRIT.getPrimaryColor()));
        addAttributeModifier(Attributes.ATTACK_SPEED, MalumMod.malumPath("infernal_aura"), 0.2f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
    }

    public static void increaseDigSpeed(PlayerEvent.BreakSpeed event) {
        Player player = event.getEntity();
        if (player.hasEffect(MobEffectRegistry.MINERS_RAGE)) {
            event.setNewSpeed(event.getOriginalSpeed() * (1 + 0.2f * player.getEffect(MobEffectRegistry.MINERS_RAGE).getAmplifier()));
        }
    }
}