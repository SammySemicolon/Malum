package com.sammy.malum.common.item.curiosities.curios.sets.scythe;

import com.google.common.collect.*;
import com.sammy.malum.*;
import com.sammy.malum.common.item.*;
import com.sammy.malum.common.item.curiosities.curios.*;
import com.sammy.malum.common.item.curiosities.weapons.scythe.*;
import com.sammy.malum.core.helpers.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.core.*;
import net.minecraft.core.particles.*;
import net.minecraft.network.chat.*;
import net.minecraft.server.level.*;
import net.minecraft.sounds.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.projectile.windcharge.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.neoforged.neoforge.event.entity.living.*;
import team.lodestar.lodestone.handlers.*;
import team.lodestar.lodestone.helpers.*;
import top.theillusivec4.curios.api.*;

import java.util.function.*;

public class CurioRisingEdgeRing extends MalumCurioItem implements IMalumEventResponderItem {
    public CurioRisingEdgeRing(Properties builder) {
        super(builder, MalumTrinketType.METALLIC);
    }

    @Override
    public void addExtraTooltipLines(Consumer<Component> consumer) {
        consumer.accept(ComponentHelper.positiveCurioEffect("ascension_launch"));
        consumer.accept(ComponentHelper.negativeCurioEffect("longer_ascension_cooldown"));
    }

    @Override
    public void addAttributeModifiers(Multimap<Holder<Attribute>, AttributeModifier> map, SlotContext slotContext, ItemStack stack) {
        addAttributeModifier(map, AttributeRegistry.SCYTHE_PROFICIENCY,
                new AttributeModifier(MalumMod.malumPath("rising_edge_ring"), 0.2f, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
    }

    @Override
    public void finalizedOutgoingDamageEvent(LivingDamageEvent.Post event, LivingEntity attacker, LivingEntity target, ItemStack stack) {
        if (event.getSource().is(DamageTypeRegistry.SCYTHE_ASCENSION)) {
            float velocity = 0.9f;
            if (MalumScytheItem.isEnhanced(attacker)) {
                velocity += 0.4f;
            }
            target.setDeltaMovement(target.getDeltaMovement().add(0, velocity, 0));
            if (target.level() instanceof ServerLevel serverLevel) {
                SoundHelper.playSound(target, SoundRegistry.SCYTHE_ASCENSION_LAUNCH.get(), SoundSource.PLAYERS, 2, 1.25f);
                serverLevel.sendParticles(ParticleTypes.GUST_EMITTER_SMALL,
                        target.getX(), target.getY(), target.getZ(), 1,
                        0, 0, 0, 0);
            }
        }
    }
}