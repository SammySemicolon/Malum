package com.sammy.malum.common.capabilities;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import com.sammy.malum.common.block.curiosities.weeping_well.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;
import team.lodestar.lodestone.helpers.block.*;

import java.util.*;

public class WeepingWellData {

    public static final float MAX_REJECTION = 60;

    public static final Codec<WeepingWellData> CODEC = RecordCodecBuilder.create(obj -> obj.group(
            Codec.BOOL.fieldOf("isNearWeepingWell").forGetter(h -> h.isNearWeepingWell),
            Codec.BOOL.fieldOf("isInRejectedState").forGetter(h -> h.isInRejectedState),
            Codec.INT.fieldOf("voidRejection").forGetter(h -> h.voidRejection),
            Codec.INT.fieldOf("primordialGoopTimer").forGetter(h -> h.primordialGoopTimer)
    ).apply(obj, WeepingWellData::new));

    public boolean isNearWeepingWell;

    public boolean isInRejectedState;
    public int voidRejection;

    public int primordialGoopTimer;

    public WeepingWellData() {
    }

    public WeepingWellData(boolean isNearWeepingWell, boolean isInRejectedState, int voidRejection, int primordialGoopTimer) {
        this.isNearWeepingWell = isNearWeepingWell;
        this.isInRejectedState = isInRejectedState;
        this.voidRejection = voidRejection;
        this.primordialGoopTimer = primordialGoopTimer;
    }


    public void update(LivingEntity living) {
        var level = living.level();
        if (living instanceof Player) {
            if (level.getGameTime() % 20L == 0) {
                isNearWeepingWell = checkForWeepingWell(living).isPresent();
            }
        }
        if (isInGoop()) {
            primordialGoopTimer--;
            if (voidRejection < MAX_REJECTION) {
                voidRejection++;
                if (voidRejection == MAX_REJECTION) {
                    isInRejectedState = true;
                }
            }
        }
        if (isInRejectedState) {
            if (voidRejection > 0) {
                voidRejection--;
                if (voidRejection == 0) {
                    isInRejectedState = false;
                }
            }
        }
        if (!isInGoop() && !isInRejectedState) {
            voidRejection = 0;
        }
    }

    public void setGoopStatus() {
        primordialGoopTimer = 2;
    }

    public boolean isInGoop() {
        return primordialGoopTimer > 0;
    }

    public boolean wasJustRejected() {
        return isInRejectedState && voidRejection == MAX_REJECTION;
    }

    public static Optional<VoidConduitBlockEntity> checkForWeepingWell(LivingEntity livingEntity) {
        return BlockEntityHelper.getBlockEntitiesStream(VoidConduitBlockEntity.class, livingEntity.level(), livingEntity.blockPosition(), 8).findFirst();
    }
}