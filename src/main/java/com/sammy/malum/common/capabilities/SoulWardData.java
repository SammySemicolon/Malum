package com.sammy.malum.common.capabilities;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import com.sammy.malum.common.data_components.*;
import com.sammy.malum.config.*;
import com.sammy.malum.registry.common.*;
import io.netty.buffer.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.util.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.player.*;
import team.lodestar.lodestone.helpers.*;

import java.util.*;

public class SoulWardData {

    public static Codec<SoulWardData> CODEC = RecordCodecBuilder.create(obj -> obj.group(
            Codec.DOUBLE.fieldOf("soulWard").forGetter(sw -> sw.soulWard),
            Codec.DOUBLE.fieldOf("soulWardProgress").forGetter(sw -> sw.soulWardCooldown)
    ).apply(obj, SoulWardData::new));

    public static StreamCodec<ByteBuf, SoulWardData> STREAM_CODEC = ByteBufCodecs.fromCodec(SoulWardData.CODEC);

    private double soulWard;
    private double soulWardCooldown;

    private boolean isDirty;

    public SoulWardData() {
    }

    public SoulWardData(double soulWard, double soulWardCooldown) {
        this.soulWard = soulWard;
        this.soulWardCooldown = soulWardCooldown;
    }

    public void recoverSoulWard(LivingEntity entity, double amount) {
        var multiplier = Optional.ofNullable(entity.getAttribute(AttributeRegistry.SOUL_WARD_RECOVERY_MULTIPLIER)).map(AttributeInstance::getValue).orElse(1.0);
        addSoulWard(amount * multiplier);
        if (!(entity instanceof Player player) || !player.isCreative()) {
            var capacity = entity.getAttribute(AttributeRegistry.SOUL_WARD_CAPACITY);
            if (capacity != null) {
                var sound = soulWard >= capacity.getValue() ? SoundRegistry.SOUL_WARD_CHARGE : SoundRegistry.SOUL_WARD_GROW;
                double pitchOffset = (soulWard / capacity.getValue()) * 0.5f + (Mth.ceil(soulWard) % 3) * 0.25f;
                SoundHelper.playSound(entity, sound.get(), 0.25f, (float) (1f + pitchOffset));
            }
        }
        addCooldown(entity, 1);
    }

    public void addSoulWard(double added) {
        setSoulWard(soulWard + added);
    }

    public void reduceSoulWard(double removed) {
        setSoulWard(soulWard - removed);
    }

    public void setSoulWard(double soulWard) {
        this.soulWard = Math.max(soulWard, 0);
        isDirty = true;
    }

    public double getSoulWard() {
        return soulWard;
    }

    public void addCooldown(LivingEntity living, double multiplier) {
        final double newCooldown = getSoulWardCooldown(living) * multiplier;
        if (soulWardCooldown < newCooldown) {
            soulWardCooldown = newCooldown;
            setDirty(true);
        }
    }

    public void tickCooldown() {
        if (soulWardCooldown > 0) {
            soulWardCooldown--;
        }
    }

    public double getCooldown() {
        return soulWardCooldown;
    }

    public boolean isDirty() {
        return isDirty;
    }

    public void setDirty(boolean dirty) {
        isDirty = dirty;
    }

    public boolean isDepleted() {
        return soulWard <= 0;
    }

    public static float getSoulWardCooldown(LivingEntity living) {
        return getSoulWardCooldown(living.getAttributeValue(AttributeRegistry.SOUL_WARD_RECOVERY_RATE));
    }

    public static float getSoulWardCooldown(double recoverySpeed) {
        return Mth.floor(CommonConfig.SOUL_WARD_RATE.getConfigValue() / recoverySpeed);
    }
}