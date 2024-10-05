package com.sammy.malum.common.item.curiosities.curios.sets.weeping;

import com.sammy.malum.common.entity.boomerang.*;
import com.sammy.malum.common.entity.hidden_blade.*;
import com.sammy.malum.common.item.IMalumEventResponderItem;
import com.sammy.malum.common.item.IVoidItem;
import com.sammy.malum.common.item.curiosities.curios.MalumCurioItem;
import com.sammy.malum.core.handlers.*;
import com.sammy.malum.core.helpers.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.item.*;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.util.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.registry.common.*;
import team.lodestar.lodestone.registry.common.tag.*;

import java.util.function.Consumer;

public class CurioHiddenBladeNecklace extends MalumCurioItem implements IMalumEventResponderItem, IVoidItem {
    public CurioHiddenBladeNecklace(Properties builder) {
        super(builder, MalumTrinketType.VOID);
    }

    @Override
    public void addExtraTooltipLines(Consumer<Component> consumer) {
        consumer.accept(positiveEffect("scythe_counterattack"));
        consumer.accept(negativeEffect("no_sweep"));
    }

    @Override
    public void takeDamageEvent(LivingDamageEvent.Post event, LivingEntity attacker, LivingEntity attacked, ItemStack stack) {
        float amount = event.getNewDamage();
        int amplifier = (int) Math.ceil(amount / 4f);
        if (amplifier >= 6) {
            amplifier = Mth.ceil(amplifier * 1.5f);
        }
        Holder<MobEffect> effect = MobEffectRegistry.WICKED_INTENT;
        attacked.addEffect(new MobEffectInstance(effect, 40, amplifier + 1));
    }

    @Override
    public void hurtEvent(LivingDamageEvent.Post event, LivingEntity attacker, LivingEntity target, ItemStack stack) {
        var source = event.getSource();
        var level = attacker.level();
        if (level.isClientSide()) {
            return;
        }
        if (source.is(LodestoneDamageTypeTags.IS_MAGIC) || (source.is(DamageTypes.THORNS))) {
            return;
        }

        if (CurioHelper.hasCurioEquipped(attacker, ItemRegistry.NECKLACE_OF_THE_HIDDEN_BLADE.get())) {
            if (SoulDataHandler.getScytheWeapon(source, attacker).isEmpty()) {
                return;
            }
            var effect = attacker.getEffect(MobEffectRegistry.WICKED_INTENT);
            if (effect == null) {
                return;
            }
            int duration = 25;
            float baseDamage = (float) (attacker.getAttributes().getValue(Attributes.ATTACK_DAMAGE) / duration);
            float magicDamage = (float) (attacker.getAttributes().getValue(LodestoneAttributes.MAGIC_DAMAGE) / duration);
            var center = attacker.position().add(attacker.getLookAngle().scale(4));
            HiddenBladeDelayedImpactEntity entity = new HiddenBladeDelayedImpactEntity(level, center.x, center.y-3f+attacker.getBbHeight()/2f, center.z);
            entity.setData(attacker, baseDamage, magicDamage, duration);
            entity.setItem(stack);
            level.addFreshEntity(entity);
            ParticleHelper.spawnRandomOrientationSlashParticle(ParticleEffectTypeRegistry.HIDDEN_BLADE_COUNTER_SLASH, attacker);
            for (int i = 0; i < 3; i++) {
                SoundHelper.playSound(attacker, SoundRegistry.HIDDEN_BLADE_UNLEASHED, 3f, RandomHelper.randomBetween(level.getRandom(), 0.75f, 1.25f));
            }
            attacker.removeEffect(effect.getEffect());
            event.setCanceled(true);
        }

    }
}
