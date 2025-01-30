package com.sammy.malum.core.handlers;

import com.sammy.malum.registry.common.*;
import net.minecraft.util.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;
import net.neoforged.neoforge.event.tick.*;
import team.lodestar.lodestone.helpers.*;

public class StaffAbilityHandler {

    public static void recoverStaffCharges(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (!player.level().isClientSide) {
            player.getData(AttachmentTypeRegistry.STAFF_ABILITIES).tickData(player);
        }
    }
}
