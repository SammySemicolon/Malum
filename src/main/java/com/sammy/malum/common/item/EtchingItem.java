package com.sammy.malum.common.item;

import com.sammy.malum.core.handlers.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;

public class EtchingItem extends Item {
    public EtchingItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        EtchingHandler.addEtching(player, player.getItemInHand(usedHand));
        return super.use(level, player, usedHand);
    }
}
