package com.sammy.malum.common.etching;

import com.google.common.collect.*;
import com.sammy.malum.compability.irons_spellbooks.*;
import com.sammy.malum.core.systems.etching.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.core.*;
import net.minecraft.server.level.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.event.entity.living.*;

public class RunicInfusionEtching extends EtchingEffect {

    public RunicInfusionEtching() {
        super(MalumEtchingEffectTypeRegistry.RUNIC_INFUSION.get());
    }

    @Override
    public void outgoingDamageEvent(LivingDamageEvent.Pre event, LivingEntity attacker, LivingEntity target, ItemStack stack) {
        if (attacker instanceof ServerPlayer player) {
            var data = player.getData(AttachmentTypeRegistry.SOUL_WARD);
            double attackSpeed = Math.max(attacker.getAttributeValue(Attributes.ATTACK_SPEED), 0.01f);
            data.recoverSoulWard(player, 1f / attackSpeed);
        }
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> getAttributes(LivingEntity entity, Multimap<Holder<Attribute>, AttributeModifier> modifiers) {
        addAttributeModifier(modifiers, AttributeRegistry.SOUL_WARD_CAPACITY, 12, AttributeModifier.Operation.ADD_VALUE);
        addAttributeModifier(modifiers, AttributeRegistry.SOUL_WARD_INTEGRITY, 1f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        addAttributeModifier(modifiers, AttributeRegistry.SOUL_WARD_RECOVERY_RATE, -1f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        return modifiers;
    }
}