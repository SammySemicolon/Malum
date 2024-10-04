package com.sammy.malum.common.item.curiosities.curios.sets.alchemical;

import com.sammy.malum.common.capability.*;
import com.sammy.malum.common.item.*;
import com.sammy.malum.common.item.curiosities.curios.*;
import com.sammy.malum.core.handlers.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;

import java.util.function.*;

public class CurioManaweavingRing extends MalumCurioItem implements IMalumEventResponderItem {
    public CurioManaweavingRing(Properties builder) {
        super(builder, MalumTrinketType.ALCHEMICAL);
    }

    @Override
    public void addExtraTooltipLines(Consumer<Component> consumer) {
        consumer.accept(positiveEffect("spirits_weave_mana"));
    }

    @Override
    public void pickupSpirit(LivingEntity collector, double arcaneResonance) {
        if (collector instanceof Player player) {
            var handler = MalumPlayerDataCapability.getCapability(player).soulWardHandler;
            handler.soulWardProgress -= SoulWardHandler.getSoulWardCooldown(player) * arcaneResonance;
        }
    }
}
