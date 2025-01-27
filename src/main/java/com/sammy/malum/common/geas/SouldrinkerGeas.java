package com.sammy.malum.common.geas;

import com.google.common.collect.*;
import com.sammy.malum.core.helpers.*;
import com.sammy.malum.core.systems.geas.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.core.*;
import net.minecraft.network.chat.*;
import net.minecraft.server.commands.*;
import net.minecraft.server.level.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.event.tick.*;
import team.lodestar.lodestone.registry.common.*;

import java.util.function.*;

public class SouldrinkerGeas extends GeasEffect {

    private long mostRecentShatter;

    public SouldrinkerGeas() {
        super(MalumGeasEffectTypeRegistry.SOULDRINKERS_ECSTASY.get());
    }

    @Override
    public void addTooltipComponents(LivingEntity entity, Consumer<Component> tooltipAcceptor, TooltipFlag tooltipFlag) {
        tooltipAcceptor.accept(ComponentHelper.negativeGeasEffect("hunger_as_withdrawal"));
        super.addTooltipComponents(entity, tooltipAcceptor, tooltipFlag);
    }

    @Override
    public void outgoingDeathEvent(LivingDeathEvent event, LivingEntity attacker, LivingEntity target, ItemStack stack) {
        if (attacker.level() instanceof ServerLevel serverLevel) {
            if (!target.getData(AttachmentTypeRegistry.LIVING_SOUL_INFO).shouldDropSpirits()) {
                return;
            }
            boolean useDayTime = serverLevel.getGameRules().getBoolean(GameRules.RULE_DAYLIGHT);
            mostRecentShatter = useDayTime ? serverLevel.getDayTime() : serverLevel.getGameTime();
        }
    }

    @Override
    public void update(EntityTickEvent.Pre event, LivingEntity entity) {
        if (entity.level() instanceof ServerLevel serverLevel) {
            boolean useDayTime = serverLevel.getGameRules().getBoolean(GameRules.RULE_DAYLIGHT);
            long time = useDayTime ? serverLevel.getDayTime() : serverLevel.getGameTime();
            long timeSince = time - mostRecentShatter;
            if (timeSince > 32000) {
                float drain = 0.005f;
                long remainder = timeSince - 32000;
                while (remainder > 24000) {
                    remainder -= 24000;
                    drain += 0.005f;
                }
                if (entity instanceof Player player) {
                    player.causeFoodExhaustion(drain);
                }
            }
        }
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> createAttributeModifiers(LivingEntity entity, Multimap<Holder<Attribute>, AttributeModifier> modifiers) {
        addAttributeModifier(modifiers, AttributeRegistry.SPIRIT_SPOILS, 1, AttributeModifier.Operation.ADD_VALUE);
        addAttributeModifier(modifiers, AttributeRegistry.ARCANE_RESONANCE, 0.5f, AttributeModifier.Operation.ADD_VALUE);
        return modifiers;
    }
}