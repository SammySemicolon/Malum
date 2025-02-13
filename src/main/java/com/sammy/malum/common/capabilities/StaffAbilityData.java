package com.sammy.malum.common.capabilities;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.sammy.malum.registry.common.*;
import net.minecraft.util.*;
import net.minecraft.world.entity.*;

public class StaffAbilityData {

    public static Codec<StaffAbilityData> CODEC = RecordCodecBuilder.create(obj -> obj.group(
            Codec.INT.fieldOf("reserveChargeCount").forGetter(c -> c.reserveChargeCount),
            Codec.FLOAT.fieldOf("reserveChargeProgress").forGetter(c -> c.reserveChargeProgress),
            Codec.INT.fieldOf("unwindingChaosBurnStacks").forGetter(c -> c.unwindingChaosBurnStacks)
    ).apply(obj, StaffAbilityData::new));

    public int reserveChargeCount;
    public float reserveChargeProgress;
    public int unwindingChaosBurnStacks;

    public StaffAbilityData() {
    }

    public StaffAbilityData(int reserveChargeCount, float reserveChargeProgress, int unwindingChaosBurnStacks) {
        this.reserveChargeCount = reserveChargeCount;
        this.reserveChargeProgress = reserveChargeProgress;
        this.unwindingChaosBurnStacks = unwindingChaosBurnStacks;
    }

    public void tickData(LivingEntity livingEntity) {
        var reserveStaffCharges = livingEntity.getAttribute(AttributeRegistry.RESERVE_STAFF_CHARGES);
        if (reserveStaffCharges != null) {
            if (reserveChargeCount < reserveStaffCharges.getValue()) {
                reserveChargeProgress++;
                if (reserveChargeProgress >= 600) {
                    reserveChargeProgress = 0;
                    reserveChargeCount++;
                }
            }
        }
    }

    public boolean tryEmpowerChaosVolley() {
        boolean success = unwindingChaosBurnStacks > 8;
        if (success) {
            unwindingChaosBurnStacks -= 8;
        }
        return success;
    }

    public void chargeUpUnwindingChaos(int charge, Runnable onCharge) {
        if (unwindingChaosBurnStacks < 40) {
            unwindingChaosBurnStacks = Mth.clamp(unwindingChaosBurnStacks+charge, 0, 40);
            onCharge.run();
        }
    }
}