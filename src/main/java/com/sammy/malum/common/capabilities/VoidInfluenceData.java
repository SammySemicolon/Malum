package com.sammy.malum.common.capabilities;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import com.sammy.malum.common.block.curiosities.weeping_well.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;
import team.lodestar.lodestone.helpers.block.*;

import java.util.*;

public class VoidInfluenceData {

    public static final float MAX_AFFLICTION = 100f;
    public static final float MAX_REJECTION = 60;

    public static final Codec<VoidInfluenceData> CODEC = RecordCodecBuilder.create(obj -> obj.group(
            Codec.BOOL.fieldOf("isNearWeepingWell").forGetter(h -> h.isNearWeepingWell),
            Codec.INT.fieldOf("expectedAffliction").forGetter(h -> h.expectedAffliction),
            Codec.INT.fieldOf("afflictionFalloffCooldown").forGetter(h -> h.afflictionFalloffCooldown),
            Codec.FLOAT.fieldOf("voidAffliction").forGetter(h -> h.voidAffliction),
            Codec.BOOL.fieldOf("isInRejectedState").forGetter(h -> h.isInRejectedState),
            Codec.INT.fieldOf("voidRejection").forGetter(h -> h.voidRejection),
            Codec.INT.fieldOf("primordialGoopTimer").forGetter(h -> h.primordialGoopTimer)
    ).apply(obj, VoidInfluenceData::new));

    public boolean isNearWeepingWell;

    public int expectedAffliction;
    public int afflictionFalloffCooldown;
    public float voidAffliction;

    public boolean isInRejectedState;
    public int voidRejection;

    public int primordialGoopTimer;

    public VoidInfluenceData() {
    }

    public VoidInfluenceData(boolean isNearWeepingWell, int expectedAffliction, int afflictionFalloffCooldown, float voidAffliction, boolean isInRejectedState, int voidRejection, int primordialGoopTimer) {
        this.isNearWeepingWell = isNearWeepingWell;
        this.expectedAffliction = expectedAffliction;
        this.afflictionFalloffCooldown = afflictionFalloffCooldown;
        this.voidAffliction = voidAffliction;
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
        if (voidAffliction > 0) {
            voidAffliction--;
            if (voidAffliction == 0) {
                expectedAffliction = 0;
            }
        }
        if (voidAffliction < expectedAffliction) {
            voidAffliction = Math.min(MAX_AFFLICTION, voidAffliction + 2f);
        }
        if (voidAffliction > expectedAffliction) {
            voidAffliction = Math.max(voidAffliction - (expectedAffliction == 0 ? 1.5f : 0.75f), expectedAffliction);
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

    public void setAfflictionLevel(int expectedAffliction) {
        if (this.expectedAffliction > expectedAffliction) {
            return;
        }
        this.expectedAffliction = expectedAffliction;
        afflictionFalloffCooldown = 100;
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