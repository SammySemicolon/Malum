package com.sammy.malum.common.effect;

import com.sammy.malum.*;
import com.sammy.malum.registry.common.MobEffectRegistry;
import io.github.fabricators_of_create.porting_lib.entity.events.living.MobEffectEvent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import team.lodestar.lodestone.helpers.ColorHelper;
import team.lodestar.lodestone.registry.common.LodestoneAttributes;

import static io.github.fabricators_of_create.porting_lib.entity.events.living.MobEffectEvent.Applicable.Result.DO_NOT_APPLY;


public class GluttonyEffect extends MobEffect {
    public GluttonyEffect() {
        super(MobEffectCategory.BENEFICIAL, ColorHelper.getColor(88, 86, 60));
        addAttributeModifier(LodestoneAttributes.MAGIC_PROFICIENCY, MalumMod.malumPath("gluttony"), 0.1f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
    }

    public static void canApplyPotion(MobEffectEvent.Applicable event) {
        MobEffectInstance potionEffect = event.getEffectInstance();
        LivingEntity entityLiving = event.getEntity();
        if (potionEffect.getEffect().equals(MobEffects.HUNGER) && entityLiving.hasEffect(MobEffectRegistry.GLUTTONY)) {
            event.setResult(DO_NOT_APPLY);
        }
    }

    @Override
    public boolean applyEffectTick(LivingEntity entityLivingBaseIn, int amplifier) {
        if (entityLivingBaseIn instanceof Player player) {
            player.causeFoodExhaustion(0.005f * (amplifier + 1));
        }
        return true;
    }
}