package com.sammy.malum.common.item.curiosities.curios.sets.soulward;

import com.google.common.collect.Multimap;
import com.sammy.malum.MalumMod;
import com.sammy.malum.common.capability.MalumPlayerDataCapability;
import com.sammy.malum.common.item.IMalumEventResponderItem;
import com.sammy.malum.common.item.curiosities.curios.MalumCurioItem;
import com.sammy.malum.core.handlers.SoulWardHandler;
import com.sammy.malum.registry.common.AttributeRegistry;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import team.lodestar.lodestone.registry.common.tag.LodestoneDamageTypeTags;
import top.theillusivec4.curios.api.SlotContext;

import java.util.function.Consumer;

public class CurioMagebaneBelt extends MalumCurioItem implements IMalumEventResponderItem {

    public CurioMagebaneBelt(Properties builder) {
        super(builder, MalumTrinketType.ORNATE);
    }

    @Override
    public void addExtraTooltipLines(Consumer<Component> consumer) {
        consumer.accept(positiveEffect("soul_ward_magic_resilience"));
    }

    @Override
    public void onSoulwardAbsorbDamage(LivingDamageEvent.Post event, Player wardedPlayer, ItemStack stack, float soulwardLost, float damageAbsorbed) {
        DamageSource source = event.getSource();
        if (source.getEntity() != null) {
            if (source.is(LodestoneDamageTypeTags.IS_MAGIC)) {
                SoulWardHandler handler = MalumPlayerDataCapability.getCapability(wardedPlayer).soulWardHandler;
                handler.soulWardProgress = 0;
            }
        }
    }

    @Override
    public void addAttributeModifiers(Multimap<Holder<Attribute>, AttributeModifier> map, SlotContext slotContext, ItemStack stack) {
        addAttributeModifier(map, AttributeRegistry.SOUL_WARD_RECOVERY_RATE,
                new AttributeModifier(MalumMod.malumPath("curio_soul_ward_recovery_speed"), 3f, AttributeModifier.Operation.ADD_VALUE));
        addAttributeModifier(map, AttributeRegistry.SOUL_WARD_CAP,
                new AttributeModifier(MalumMod.malumPath("curio_soul_ward_capacity"), 6f, AttributeModifier.Operation.ADD_VALUE));
    }
}
