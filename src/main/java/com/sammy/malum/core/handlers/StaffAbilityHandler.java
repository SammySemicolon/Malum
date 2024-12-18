package com.sammy.malum.core.handlers;

import com.sammy.malum.registry.common.*;
import io.github.fabricators_of_create.porting_lib.entity.events.tick.PlayerTickEvent;
import net.minecraft.world.entity.player.*;

public class StaffAbilityHandler {

    public static void recoverStaffCharges(PlayerTickEvent event) {
        Player player = event.getEntity();
        if (!player.level().isClientSide) {
            var reserveStaffCharges = player.getAttribute(AttributeRegistry.RESERVE_STAFF_CHARGES);
            if (reserveStaffCharges != null) {
                var data = player.getAttachedOrCreate(AttachmentTypeRegistry.RESERVE_STAFF_CHARGES);
                if (data.reserveChargeCount < reserveStaffCharges.getValue()) {
                    data.reserveChargeProgress++;
                    if (data.reserveChargeProgress >= 600) {
                        data.reserveChargeProgress = 0;
                        data.reserveChargeCount++;
//                        MalumPlayerDataCapability.syncTrackingAndSelf(player);
                    }
                }
            }
        }
    }
}
