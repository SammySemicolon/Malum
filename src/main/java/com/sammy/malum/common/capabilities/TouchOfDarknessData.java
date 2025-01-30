package com.sammy.malum.common.capabilities;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import net.minecraft.world.entity.*;

public class TouchOfDarknessData {

    public static final float MAX_TOUCH_OF_DARKNESS = 100f;

    public static final Codec<TouchOfDarknessData> CODEC = RecordCodecBuilder.create(obj -> obj.group(
            Codec.INT.fieldOf("expectedTouchOfDarkness").forGetter(h -> h.expectedTouchOfDarkness),
            Codec.INT.fieldOf("touchOfDarknessFalloffDelay").forGetter(h -> h.touchOfDarknessFalloffDelay),
            Codec.FLOAT.fieldOf("touchOfDarkness").forGetter(h -> h.touchOfDarkness)
    ).apply(obj, TouchOfDarknessData::new));

    public int expectedTouchOfDarkness;
    public int touchOfDarknessFalloffDelay;
    public float touchOfDarkness;

    public TouchOfDarknessData() {
    }

    public TouchOfDarknessData(int expectedTouchOfDarkness, int touchOfDarknessFalloffDelay, float touchOfDarkness) {
        this.expectedTouchOfDarkness = expectedTouchOfDarkness;
        this.touchOfDarknessFalloffDelay = touchOfDarknessFalloffDelay;
        this.touchOfDarkness = touchOfDarkness;
    }

    public void setAfflictionLevel(int expectedTouchOfDarkness) {
        if (this.expectedTouchOfDarkness > expectedTouchOfDarkness) {
            return;
        }
        this.expectedTouchOfDarkness = expectedTouchOfDarkness;
        touchOfDarknessFalloffDelay = 100;
    }

    public void update(LivingEntity living) {
        if (touchOfDarknessFalloffDelay > 0) {
            touchOfDarknessFalloffDelay--;
            if (touchOfDarknessFalloffDelay == 0) {
                expectedTouchOfDarkness = 0;
            }
        }
        if (touchOfDarkness < expectedTouchOfDarkness) {
            touchOfDarkness = Math.min(MAX_TOUCH_OF_DARKNESS, touchOfDarkness + 2f);
        }
        if (touchOfDarkness > expectedTouchOfDarkness) {
            touchOfDarkness = Math.max(touchOfDarkness - (expectedTouchOfDarkness == 0 ? 1.5f : 0.75f), expectedTouchOfDarkness);
        }
    }
}