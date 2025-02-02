package com.sammy.malum.core.helpers;

import net.minecraft.*;
import net.minecraft.network.chat.*;

public class ComponentHelper {


    public static Component positiveGeasEffect(String name, Object... args) {
        return Component.translatable("malum.effect.positive", Component.translatable("malum.effect.geas." + name, args)).withStyle(ChatFormatting.BLUE);
    }

    public static Component negativeGeasEffect(String name, Object... args) {
        return Component.translatable("malum.effect.negative", Component.translatable("malum.effect.geas." + name, args)).withStyle(ChatFormatting.RED);
    }

    public static Component positiveCurioEffect(String name, Object... args) {
        return Component.translatable("malum.effect.positive", Component.translatable("malum.effect.curio." + name, args)).withStyle(ChatFormatting.BLUE);
    }

    public static Component negativeCurioEffect(String name, Object... args) {
        return Component.translatable("malum.effect.negative", Component.translatable("malum.effect.curio." + name, args)).withStyle(ChatFormatting.RED);
    }

    public static Component positiveEffect(String name, Object... args) {
        return Component.literal(" ").append(Component.translatable("malum.effect." + name, args)).withStyle(ChatFormatting.DARK_GREEN);
    }

    public static Component negativeEffect(String name, Object... args) {
        return Component.literal(" ").append(Component.translatable("malum.effect." + name, args)).withStyle(ChatFormatting.RED);
    }
}
