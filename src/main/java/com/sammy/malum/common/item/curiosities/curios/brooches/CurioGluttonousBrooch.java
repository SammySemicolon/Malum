package com.sammy.malum.common.item.curiosities.curios.brooches;

import com.google.common.collect.Multimap;
import com.sammy.malum.MalumMod;
import com.sammy.malum.common.item.curiosities.curios.MalumCurioItem;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;

import java.util.UUID;
import java.util.function.Consumer;

public class CurioGluttonousBrooch extends MalumCurioItem {

    public static final ResourceLocation GLUTTONOUS_BROOCH_BELT = MalumMod.malumPath("gluttonous_brooch_belt");

    public CurioGluttonousBrooch(Properties builder) {
        super(builder, MalumTrinketType.ROTTEN);
    }

    @Override
    public void addExtraTooltipLines(Consumer<Component> consumer) {
        consumer.accept(negativeEffect("hunger_drain"));
    }

    @Override
    public void addAttributeModifiers(Multimap<Holder<Attribute>, AttributeModifier> map, SlotContext slotContext, ItemStack stack) {
        CuriosApi.addSlotModifier(map, "belt", GLUTTONOUS_BROOCH_BELT, 1, AttributeModifier.Operation.ADD_VALUE);
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        if (slotContext.entity() instanceof Player player) {
            player.causeFoodExhaustion(0.005f);
        }
        super.curioTick(slotContext, stack);
    }
}
