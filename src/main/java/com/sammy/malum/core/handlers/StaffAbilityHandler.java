package com.sammy.malum.core.handlers;

import com.sammy.malum.registry.common.*;
import net.minecraft.world.entity.player.*;
import net.neoforged.neoforge.event.tick.*;

public class StaffAbilityHandler {

    public static void recoverStaffCharges(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (!player.level().isClientSide) {
            var reserveStaffCharges = player.getAttribute(AttributeRegistry.RESERVE_STAFF_CHARGES);
            if (reserveStaffCharges != null) {
                var data = player.getData(AttachmentTypeRegistry.STAFF_ABILITIES);
                if (data.reserveChargeCount < reserveStaffCharges.getValue()) {
                    data.reserveChargeProgress++;
                    if (data.reserveChargeProgress >= 600) {
                        data.reserveChargeProgress = 0;
                        data.reserveChargeCount++;
                    }
                }
            }
        }
    }
}
