package com.sammy.malum.visual_effects.networked.data;

import com.sammy.malum.MalumMod;
import com.sammy.malum.core.handlers.*;
import com.sammy.malum.core.systems.recipe.SpiritIngredient;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import net.minecraft.network.FriendlyByteBuf;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ColorEffectData {

    public final ArrayList<ColorRecord> colorRecordList = new ArrayList<>();
    public int recordCycleCounter;

    public static ColorEffectData fromRecipe(Collection<SpiritIngredient> malumSpiritTypes) {
        return fromSpirits(malumSpiritTypes.stream().map(SpiritIngredient::getSpiritType).collect(Collectors.toList()), ColorRecord::new);
    }

    public static ColorEffectData fromSpirits(Collection<MalumSpiritType> malumSpiritTypes) {
        return fromSpirits(malumSpiritTypes, ColorRecord::new);
    }

    public static ColorEffectData fromSpirits(Collection<MalumSpiritType> malumSpiritTypes, Function<MalumSpiritType, ColorRecord> spiritColorMapper) {
        return new ColorEffectData(malumSpiritTypes.stream().map(spiritColorMapper).collect(Collectors.toList()));
    }

    public ColorEffectData(ColorRecord colorRecord) {
        colorRecordList.add(colorRecord);
    }

    public ColorEffectData(Collection<ColorRecord> colorRecords) {
        colorRecordList.addAll(colorRecords);
    }

    public ColorEffectData(Color primaryColor) {
        this(new ColorRecord(primaryColor));
    }

    public ColorEffectData(MalumSpiritType spiritType) {
        this(new ColorRecord(spiritType));
    }

    public ColorEffectData(Color primaryColor, Color secondaryColor) {
        this(new ColorRecord(primaryColor, secondaryColor));
    }

    public ColorEffectData(Color primaryColor, Color secondaryColor, @Nullable MalumSpiritType spiritType) {
        this(new ColorRecord(primaryColor, secondaryColor, spiritType));
    }

    public ColorEffectData(FriendlyByteBuf buf) {
        boolean isEmpty = buf.readBoolean();
        if (!isEmpty) {
            int colorDataCount = buf.readInt();
            for (int i = 0; i < colorDataCount; i++) {
                colorRecordList.add(new ColorRecord(
                        new Color(buf.readInt()),
                        new Color(buf.readInt()),
                        buf.readBoolean() ? SpiritHarvestHandler.getSpiritType(buf.readUtf()) : null
                ));
            }
        }
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeBoolean(colorRecordList.isEmpty());
        if (!colorRecordList.isEmpty()) {
            buf.writeInt(colorRecordList.size());
            for (ColorRecord colorRecord : this.colorRecordList) {
                buf.writeInt(colorRecord.primaryColor.getRGB());
                buf.writeInt(colorRecord.secondaryColor.getRGB());
                boolean nonNullSpirit = colorRecord.spiritType != null;
                buf.writeBoolean(nonNullSpirit);
                if (nonNullSpirit) {
                    buf.writeUtf(colorRecord.spiritType.getIdentifier());
                }
            }
        }
    }

    public ColorRecord getDefaultColorRecord() {
        if (colorRecordList.isEmpty()) {
            return null;
        }
        return colorRecordList.get(0);
    }

    public ColorRecord getRandomColorRecord() {
        if (colorRecordList.isEmpty()) {
            return null;
        }
        return colorRecordList.get(MalumMod.RANDOM.nextInt(colorRecordList.size()));
    }

    public ColorRecord getCyclingColorRecord() {
        if (colorRecordList.isEmpty()) {
            return null;
        }
        return colorRecordList.get(recordCycleCounter++ % colorRecordList.size());
    }

    public Color getPrimaryColor(ColorRecord colorRecord) {
        return colorRecord.primaryColor;
    }

    public Color getSecondaryColor(ColorRecord colorRecord) {
        return colorRecord.secondaryColor;
    }

    @Nullable
    public MalumSpiritType getSpiritType() {
        return getSpiritType(getDefaultColorRecord());
    }

    @Nullable
    public MalumSpiritType getSpiritType(ColorRecord colorRecord) {
        return colorRecord.spiritType;
    }

    public record ColorRecord(Color primaryColor, Color secondaryColor, @Nullable MalumSpiritType spiritType) {
        public ColorRecord(Color color) {
            this(color, color);
        }

        public ColorRecord(MalumSpiritType type) {
            this(type.getPrimaryColor(), type.getSecondaryColor(), type);
        }

        public ColorRecord(Color primaryColor, Color secondaryColor) {
            this(primaryColor, secondaryColor, null);
        }
    }
}