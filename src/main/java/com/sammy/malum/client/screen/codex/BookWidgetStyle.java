package com.sammy.malum.client.screen.codex;

import net.minecraft.resources.*;

import java.util.*;

import static com.sammy.malum.MalumMod.malumPath;

public record BookWidgetStyle(ResourceLocation frameTexture, ResourceLocation fillingTexture, int textureWidth, int textureHeight) {

    public enum WidgetDesignType {
        DEFAULT("default"),
        SPIRIT("spirit", 26, 40),
        TOTEMIC("totemic"),
        RESEARCH("research"),
        GILDED("gilded"),
        SMALL("small"),
        GRAND("grand", 40);
        public final String id;
        public final int textureWidth;
        public final int textureHeight;

        WidgetDesignType(String id) {
            this(id, 32);
        }
        WidgetDesignType(String id, int dimensions) {
            this(id, dimensions, dimensions);
        }
        WidgetDesignType(String id, int textureWidth, int textureHeight) {
            this.id = id;
            this.textureWidth = textureWidth;
            this.textureHeight = textureHeight;
        }
    }

    public BookWidgetStyle(WidgetStylePreset framePreset, WidgetStylePreset fillingPreset, WidgetDesignType type) {
        this(framePreset.getTexture(type), fillingPreset.getTexture(type), type.textureWidth, type.textureHeight);
    }

    private static final WidgetStylePreset RUNEWOOD_FRAMES = new WidgetStylePreset("runewood_frame");
    private static final WidgetStylePreset SOULWOOD_FRAMES = new WidgetStylePreset("soulwood_frame");
    private static final WidgetStylePreset PAPER_FILLINGS = new WidgetStylePreset("paper_filling");
    private static final WidgetStylePreset DARK_FILLINGS = new WidgetStylePreset("dark_filling");

    // These ONLY have default designs!
    private static final WidgetStylePreset WITHERED_FRAME = new WidgetStylePreset("withered_frame");
    private static final WidgetStylePreset EMPTY_FRAME = new WidgetStylePreset("empty_frame");

    //TODO: clean this up :sob:

    public static final BookWidgetStyle WITHERED = new BookWidgetStyle(WITHERED_FRAME, DARK_FILLINGS, WidgetDesignType.DEFAULT);
    public static final BookWidgetStyle SMALL_WITHERED = new BookWidgetStyle(WITHERED_FRAME, DARK_FILLINGS, WidgetDesignType.SMALL);
    public static final BookWidgetStyle FRAMELESS = new BookWidgetStyle(EMPTY_FRAME, DARK_FILLINGS, WidgetDesignType.DEFAULT);

    public static final BookWidgetStyle RUNEWOOD = new BookWidgetStyle(RUNEWOOD_FRAMES, PAPER_FILLINGS, WidgetDesignType.DEFAULT);
    public static final BookWidgetStyle SPIRIT_RUNEWOOD = new BookWidgetStyle(RUNEWOOD_FRAMES, PAPER_FILLINGS, WidgetDesignType.DEFAULT);
    public static final BookWidgetStyle TOTEMIC_RUNEWOOD = new BookWidgetStyle(RUNEWOOD_FRAMES, PAPER_FILLINGS, WidgetDesignType.TOTEMIC);
    public static final BookWidgetStyle GILDED_RUNEWOOD = new BookWidgetStyle(RUNEWOOD_FRAMES, PAPER_FILLINGS, WidgetDesignType.GILDED);
    public static final BookWidgetStyle SMALL_RUNEWOOD = new BookWidgetStyle(RUNEWOOD_FRAMES, PAPER_FILLINGS, WidgetDesignType.SMALL);
    public static final BookWidgetStyle GRAND_RUNEWOOD = new BookWidgetStyle(RUNEWOOD_FRAMES, PAPER_FILLINGS, WidgetDesignType.GRAND);

    public static final BookWidgetStyle DARK_RUNEWOOD = new BookWidgetStyle(RUNEWOOD_FRAMES, DARK_FILLINGS, WidgetDesignType.DEFAULT);
    public static final BookWidgetStyle DARK_TOTEMIC_RUNEWOOD = new BookWidgetStyle(RUNEWOOD_FRAMES, DARK_FILLINGS, WidgetDesignType.TOTEMIC);
    public static final BookWidgetStyle DARK_GILDED_RUNEWOOD = new BookWidgetStyle(RUNEWOOD_FRAMES, DARK_FILLINGS, WidgetDesignType.GILDED);
    public static final BookWidgetStyle DARK_SMALL_RUNEWOOD = new BookWidgetStyle(RUNEWOOD_FRAMES, DARK_FILLINGS, WidgetDesignType.SMALL);
    public static final BookWidgetStyle DARK_GRAND_RUNEWOOD = new BookWidgetStyle(RUNEWOOD_FRAMES, DARK_FILLINGS, WidgetDesignType.GRAND);

    public static final BookWidgetStyle SOULWOOD = new BookWidgetStyle(SOULWOOD_FRAMES, PAPER_FILLINGS, WidgetDesignType.DEFAULT);
    public static final BookWidgetStyle TOTEMIC_SOULWOOD = new BookWidgetStyle(SOULWOOD_FRAMES, PAPER_FILLINGS, WidgetDesignType.TOTEMIC);
    public static final BookWidgetStyle GILDED_SOULWOOD = new BookWidgetStyle(SOULWOOD_FRAMES, PAPER_FILLINGS, WidgetDesignType.GILDED);
    public static final BookWidgetStyle SMALL_SOULWOOD = new BookWidgetStyle(SOULWOOD_FRAMES, PAPER_FILLINGS, WidgetDesignType.SMALL);
    public static final BookWidgetStyle GRAND_SOULWOOD = new BookWidgetStyle(SOULWOOD_FRAMES, PAPER_FILLINGS, WidgetDesignType.GRAND);

    public static final BookWidgetStyle DARK_SOULWOOD = new BookWidgetStyle(SOULWOOD_FRAMES, DARK_FILLINGS, WidgetDesignType.DEFAULT);
    public static final BookWidgetStyle DARK_TOTEMIC_SOULWOOD = new BookWidgetStyle(SOULWOOD_FRAMES, DARK_FILLINGS, WidgetDesignType.TOTEMIC);
    public static final BookWidgetStyle DARK_GILDED_SOULWOOD = new BookWidgetStyle(SOULWOOD_FRAMES, DARK_FILLINGS, WidgetDesignType.GILDED);
    public static final BookWidgetStyle DARK_SMALL_SOULWOOD = new BookWidgetStyle(SOULWOOD_FRAMES, DARK_FILLINGS, WidgetDesignType.SMALL);
    public static final BookWidgetStyle DARK_GRAND_SOULWOOD = new BookWidgetStyle(SOULWOOD_FRAMES, DARK_FILLINGS, WidgetDesignType.GRAND);

    public record WidgetStylePreset(Map<WidgetDesignType, ResourceLocation> map) {

        public WidgetStylePreset(String name) {
            this(new HashMap<>());
            for (WidgetDesignType value : WidgetDesignType.values()) {
                map.put(value, texturePath(name + "_" + value.id));
            }
        }

        public ResourceLocation getTexture(WidgetDesignType type) {
            return map.get(type);
        }
    }

    public static ResourceLocation texturePath(String name) {
        return malumPath("textures/gui/book/widgets/" + name + ".png");
    }
}
