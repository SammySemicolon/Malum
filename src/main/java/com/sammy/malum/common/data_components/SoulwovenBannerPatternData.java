package com.sammy.malum.common.data_components;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import com.sammy.malum.*;
import com.sammy.malum.registry.common.item.*;
import io.netty.buffer.*;
import net.minecraft.*;
import net.minecraft.nbt.*;
import net.minecraft.network.codec.*;
import net.minecraft.resources.*;
import net.minecraft.world.item.*;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public record SoulwovenBannerPatternData(ResourceLocation type, ResourceLocation texturePath, String translationKey) {

    public static Codec<SoulwovenBannerPatternData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceLocation.CODEC.fieldOf("type").forGetter(SoulwovenBannerPatternData::type),
            ResourceLocation.CODEC.fieldOf("texture").forGetter(SoulwovenBannerPatternData::texturePath)
    ).apply(instance, SoulwovenBannerPatternData::new));

    public static StreamCodec<ByteBuf, SoulwovenBannerPatternData> STREAM_CODEC = ByteBufCodecs.fromCodec(SoulwovenBannerPatternData.CODEC);

    public static final List<SoulwovenBannerPatternData> REGISTERED_PATTERNS = new ArrayList<>();

    public static final SoulwovenBannerPatternData DEFAULT = register(MalumMod.malumPath("default"));

    public static final SoulwovenBannerPatternData SACRED = register(MalumMod.malumPath("sequence"));
//    public static final SoulwovenBannerPatternData WICKED = register(MalumMod.malumPath("slash"));
//    public static final SoulwovenBannerPatternData ARCANE = register(MalumMod.malumPath("spirit"));
    public static final SoulwovenBannerPatternData ELDRITCH = register(MalumMod.malumPath("sanity"));

    public static final SoulwovenBannerPatternData AERIAL = register(MalumMod.malumPath("breeze"));
    public static final SoulwovenBannerPatternData AQUEOUS = register(MalumMod.malumPath("breath"));
    public static final SoulwovenBannerPatternData EARTHEN = register(MalumMod.malumPath("break"));
//    public static final SoulwovenBannerPatternData INFERNAL = register(MalumMod.malumPath("burn"));

    public static final SoulwovenBannerPatternData HUNGER = register(MalumMod.malumPath("hunger"));
    public static final SoulwovenBannerPatternData HORNS = register(MalumMod.malumPath("horns"));
    public static final SoulwovenBannerPatternData HEFT = register(MalumMod.malumPath("heft"));
    public static final SoulwovenBannerPatternData HALLUCINATION = register(MalumMod.malumPath("hallucination"));


    public static SoulwovenBannerPatternData register(ResourceLocation type) {
        var pattern = new SoulwovenBannerPatternData(type, ResourceLocation.fromNamespaceAndPath(type.getNamespace(), "textures/block/banners/soulwoven_banner_" + type.getPath() + ".png"));
        REGISTERED_PATTERNS.add(pattern);
        return pattern;
    }

    public SoulwovenBannerPatternData(ResourceLocation type, ResourceLocation texturePath) {
        this(type, texturePath, Util.makeDescriptionId("banner_pattern", type));
    }

    public ItemStack getDefaultStack() {
        final ItemStack stack = ItemRegistry.SOULWOVEN_BANNER.get().getDefaultInstance();
        stack.set(DataComponentRegistry.SOULWOVEN_BANNER_PATTERN.get(), this);
        return stack;
    }

    public ResourceLocation getRecipeId() {
        return type().withSuffix("banner_crafting_");
    }

    public CompoundTag save(CompoundTag tag) {
        if (!this.equals(DEFAULT)) {
            tag.put("pattern", CODEC.encodeStart(NbtOps.INSTANCE, this).getOrThrow());
        }
        return tag;
    }

    public static SoulwovenBannerPatternData load(@Nullable CompoundTag tag) {
        return tag != null && tag.contains("pattern") ? CODEC.parse(NbtOps.INSTANCE, tag.get("pattern")).result().orElse(DEFAULT) : DEFAULT;
    }
}