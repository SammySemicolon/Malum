package com.sammy.malum.common.item.curiosities.curios.brooches;

import com.google.common.collect.Multimap;
import com.sammy.malum.MalumMod;
import com.sammy.malum.common.item.curiosities.curios.MalumCurioItem;
import com.sammy.malum.core.helpers.*;
import dev.emi.trinkets.api.SlotAttributes;
import dev.emi.trinkets.api.SlotReference;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.function.Consumer;

public class CurioGluttonousBrooch extends MalumCurioItem {

    public static final ResourceLocation GLUTTONOUS_BROOCH_BELT = MalumMod.malumPath("gluttonous_brooch_belt");

    public CurioGluttonousBrooch(Properties builder) {
        super(builder, MalumTrinketType.ROTTEN);
    }

    @Override
    public void addExtraTooltipLines(Consumer<Component> consumer) {
        consumer.accept(ComponentHelper.negativeCurioEffect("hunger_drain"));
    }

    @Override
    public void addAttributeModifier(Multimap<Holder<Attribute>, AttributeModifier> map, Holder<Attribute> attribute, AttributeModifier modifier) {
        SlotAttributes.addSlotModifier(map, "legs/belt", GLUTTONOUS_BROOCH_BELT, 1, AttributeModifier.Operation.ADD_VALUE);
    }

    @Override
    public void tick(ItemStack stack, SlotReference slot, LivingEntity entity) {
        if (slot.inventory().getComponent().getEntity() instanceof Player player) {
            player.causeFoodExhaustion(0.005f);
        }
        super.tick(stack, slot, entity);
    }
}
