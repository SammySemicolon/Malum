package com.sammy.malum.common.geas.scythe;

import com.sammy.malum.common.entity.scythe.*;
import com.sammy.malum.common.item.curiosities.weapons.scythe.*;
import com.sammy.malum.common.item.spirit.*;
import com.sammy.malum.common.worldevent.*;
import com.sammy.malum.core.handlers.*;
import com.sammy.malum.core.helpers.*;
import com.sammy.malum.core.systems.geas.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.item.*;
import net.minecraft.core.*;
import net.minecraft.network.chat.*;
import net.minecraft.sounds.*;
import net.minecraft.util.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.*;
import net.minecraft.world.level.*;
import net.neoforged.neoforge.event.entity.living.*;
import team.lodestar.lodestone.handlers.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.registry.common.*;

import java.util.function.*;

import static net.minecraft.world.entity.EquipmentSlot.MAINHAND;

public class ReaperGeas extends GeasEffect {

    public ReaperGeas() {
        super(MalumGeasEffectTypeRegistry.PACT_OF_THE_REAPER.get());
    }

    @Override
    public void addTooltipComponents(LivingEntity entity, Consumer<Component> tooltipAcceptor, TooltipFlag tooltipFlag) {
        tooltipAcceptor.accept(ComponentHelper.positiveGeasEffect("scythe_combo"));
        tooltipAcceptor.accept(ComponentHelper.negativeGeasEffect("only_scythe"));
        super.addTooltipComponents(entity, tooltipAcceptor, tooltipFlag);
    }

    @Override
    public void outgoingDamageEvent(LivingDamageEvent.Pre event, LivingEntity attacker, LivingEntity target, ItemStack stack) {
        final DamageSource source = event.getSource();
        final Level level = attacker.level();

        final ItemStack mainHandItem = attacker.getMainHandItem();
        if (!mainHandItem.isEmpty()) {
            if (source.is(DamageTypes.PLAYER_ATTACK) || source.is(DamageTypeRegistry.TYRVING)) {
                event.setNewDamage(event.getNewDamage() * 0.1f);
                if (mainHandItem.isDamageableItem()) {
                    mainHandItem.hurtAndBreak(10, attacker, MAINHAND);
                }
                return;
            }
        }

        final boolean enhanced = MalumScytheItem.isNarrow(attacker);
        if (source.is(DamageTypeRegistry.SCYTHE_COMBO)) {
            var particle = ParticleHelper.createSlashingEffect(ParticleEffectTypeRegistry.SCYTHE_SLASH);
            var scytheStack = SoulDataHandler.getScytheWeapon(source, attacker);
            if (scytheStack.getItem() instanceof ISpiritAffiliatedItem spiritAffiliatedItem) {
                particle.setSpiritType(spiritAffiliatedItem);
            }
            if (enhanced) {
                particle.setVertical();
            }
            particle.mirrorRandomly(attacker.getRandom()).spawnTargetBoundSlashingParticle(attacker, target);
            if (enhanced) {
                return;
            }
            int sweeping = EnchantmentRegistry.getEnchantmentLevel(level, Enchantments.SWEEPING_EDGE, stack);
            float damage = event.getNewDamage() * (0.66f + sweeping * 0.33f);
            float radius = 1.5f + sweeping * 0.25f;
            level.getEntities(attacker, target.getBoundingBox().inflate(radius)).forEach(e -> {
                if (e instanceof LivingEntity sweepTarget) {
                    if (sweepTarget.isAlive() && sweepTarget != target) {
                        sweepTarget.hurt((DamageTypeHelper.create(level, DamageTypeRegistry.SCYTHE_SWEEP, attacker)), damage);
                        sweepTarget.knockback(0.4F,
                                Mth.sin(attacker.getYRot() * ((float) Math.PI / 180F)),
                                (-Mth.cos(attacker.getYRot() * ((float) Math.PI / 180F))));
                    }
                }
            });
            return;
        }
        if (source.is(DamageTypeTagRegistry.IS_SCYTHE)) {
            boolean canSweep = true;
            float chance = 0.3f;
            int extraHits = 2;
            float physicalDamage;
            float magicDamage;
            if (source.getDirectEntity() instanceof ScytheBoomerangEntity scytheBoomerang) {
                physicalDamage = scytheBoomerang.damage;
                magicDamage = scytheBoomerang.magicDamage;
                chance *= 2;
            }
            else {
                physicalDamage = (float) (attacker.getAttribute(Attributes.ATTACK_DAMAGE).getValue());
                magicDamage = (float) (attacker.getAttribute(LodestoneAttributes.MAGIC_DAMAGE).getValue());
            }
            float average = (physicalDamage + magicDamage)/2;
            physicalDamage *= physicalDamage/average * 0.1f;
            magicDamage *= magicDamage/average * 0.1f;
            if (enhanced) {
                extraHits++;
                canSweep = false;
            }
            if (level.getRandom().nextFloat() > chance) {
                return;
            }
            Holder<SoundEvent> scytheSound = SoundRegistry.SCYTHE_SWEEP;
            var scytheStack = SoulDataHandler.getScytheWeapon(source, attacker);
            if (scytheStack.getItem() instanceof MalumScytheItem scytheItem) {
                scytheSound = scytheItem.getScytheSound(canSweep);
            }

            for (int i = 0; i < extraHits; i++) {
                int delay = 4 + i * 3;
                WorldEventHandler.addWorldEvent(level,
                        new DelayedDamageWorldEvent(target)
                                .setAttacker(attacker, source.getDirectEntity())
                                .setDamageData(physicalDamage, magicDamage, delay)
                                .setPhysicalDamageType(DamageTypeRegistry.SCYTHE_COMBO)
                                .setSound(scytheSound, 0.5f, 1.5f, 0.3f));

            }
        }
    }
}