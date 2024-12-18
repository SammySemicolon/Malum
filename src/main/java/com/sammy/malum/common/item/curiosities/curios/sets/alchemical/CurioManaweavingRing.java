package com.sammy.malum.common.item.curiosities.curios.sets.alchemical;

import com.sammy.malum.common.item.*;
import com.sammy.malum.common.item.curiosities.curios.*;
import com.sammy.malum.core.helpers.*;
import com.sammy.malum.core.systems.events.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.network.chat.*;
import net.minecraft.server.level.*;
import net.minecraft.world.entity.*;

import java.util.function.*;

public class CurioManaweavingRing extends MalumCurioItem implements IMalumEventResponderItem {
    public CurioManaweavingRing(Properties builder) {
        super(builder, MalumTrinketType.ALCHEMICAL);
    }

    @Override
    public void addExtraTooltipLines(Consumer<Component> consumer) {
        consumer.accept(ComponentHelper.positiveCurioEffect("spirits_weave_mana"));
    }

    @Override
    public void spiritCollectionEvent(CollectSpiritEvent event, LivingEntity collector, double arcaneResonance) {
        if (collector instanceof ServerPlayer player) {
            var data = player.getAttachedOrCreate(AttachmentTypeRegistry.SOUL_WARD);
            data.recoverSoulWard(player, arcaneResonance);
        }
    }
}
