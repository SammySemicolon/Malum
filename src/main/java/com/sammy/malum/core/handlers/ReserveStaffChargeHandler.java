package com.sammy.malum.core.handlers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.sammy.malum.common.capability.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.nbt.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.player.*;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

public class ReserveStaffChargeHandler {
    public int chargeCount;
    public float chargeProgress;

    public static Codec<ReserveStaffChargeHandler> CODEC = RecordCodecBuilder.create(obj -> obj.group(
            Codec.INT.fieldOf("chargeCount").forGetter(c -> c.chargeCount),
            Codec.FLOAT.fieldOf("chargeProgress").forGetter(c -> c.chargeProgress)
    ).apply(obj, ReserveStaffChargeHandler::new));

    public ReserveStaffChargeHandler() {}

    public ReserveStaffChargeHandler(int chargeCount, float chargeProgress) {
        this.chargeCount = chargeCount;
        this.chargeProgress = chargeProgress;
    }

    public static void recoverStaffCharges(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (!player.level().isClientSide) {
            AttributeInstance reserveStaffCharges = player.getAttribute(AttributeRegistry.RESERVE_STAFF_CHARGES);
            if (reserveStaffCharges != null) {
                ReserveStaffChargeHandler chargeHandler = MalumPlayerDataCapability.getCapability(player).reserveStaffChargeHandler;
                if (chargeHandler.chargeCount < reserveStaffCharges.getValue()) {
                    chargeHandler.chargeProgress++;
                    if (chargeHandler.chargeProgress >= 600) {
                        chargeHandler.chargeProgress = 0;
                        chargeHandler.chargeCount++;
                        MalumPlayerDataCapability.syncTrackingAndSelf(player);
                    }
                }
            }
        }
    }
}