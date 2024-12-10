package com.sammy.malum.common.item.curiosities.curios.runes.miracle;

import com.sammy.malum.common.item.curiosities.curios.runes.*;
import com.sammy.malum.core.helpers.*;
import com.sammy.malum.registry.common.SpiritTypeRegistry;
import com.sammy.malum.registry.common.item.ItemRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import team.lodestar.lodestone.helpers.CurioHelper;

import java.util.function.Consumer;

public class RuneFervorItem extends AbstractRuneCurioItem {

    public RuneFervorItem(Properties builder) {
        super(builder, SpiritTypeRegistry.INFERNAL_SPIRIT);
    }

    @Override
    public void addExtraTooltipLines(Consumer<Component> consumer) {
        consumer.accept(ComponentHelper.positiveCurioEffect("fervor"));
    }

    public static void increaseDigSpeed(PlayerEvent.BreakSpeed event) {
        Player player = event.getEntity();
        if (CurioHelper.hasCurioEquipped(player, ItemRegistry.RUNE_OF_FERVOR.get())) {
            event.setNewSpeed(event.getOriginalSpeed() * 1.25f);
        }
    }
}
