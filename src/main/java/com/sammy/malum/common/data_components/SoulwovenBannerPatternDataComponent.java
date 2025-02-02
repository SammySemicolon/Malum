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

import javax.annotation.*;
import java.util.*;

public record SoulwovenBannerPatternDataComponent(ResourceLocation type, ResourceLocation texturePath, String translationKey) {

    public static Codec<SoulwovenBannerPatternDataComponent> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceLocation.CODEC.fieldOf("type").forGetter(SoulwovenBannerPatternDataComponent::type),
            ResourceLocation.CODEC.fieldOf("texture").forGetter(SoulwovenBannerPatternDataComponent::texturePath)
    ).apply(instance, SoulwovenBannerPatternDataComponent::new));

    public static StreamCodec<ByteBuf, SoulwovenBannerPatternDataComponent> STREAM_CODEC = ByteBufCodecs.fromCodec(SoulwovenBannerPatternDataComponent.CODEC);

    public static final List<SoulwovenBannerPatternDataComponent> REGISTERED_PATTERNS = new ArrayList<>();

    public static final SoulwovenBannerPatternDataComponent DEFAULT = register("default");

    public static final SoulwovenBannerPatternDataComponent SACRED = register("sequence");
//    public static final SoulwovenBannerPatternData WICKED = register("slash"));
    public static final SoulwovenBannerPatternDataComponent ARCANE = register("spawn");
    public static final SoulwovenBannerPatternDataComponent ELDRITCH = register("sanity");

    public static final SoulwovenBannerPatternDataComponent AERIAL = register("breeze");
    public static final SoulwovenBannerPatternDataComponent AQUEOUS = register("breath");
    public static final SoulwovenBannerPatternDataComponent EARTHEN = register("break");
//    public static final SoulwovenBannerPatternData INFERNAL = register("burn"));

    public static final SoulwovenBannerPatternDataComponent HUNGER = register("hunger");
    public static final SoulwovenBannerPatternDataComponent HORNS = register("horns");
    public static final SoulwovenBannerPatternDataComponent HEFT = register("heft");
    public static final SoulwovenBannerPatternDataComponent HALLUCINATION = register("hallucination");


    public static SoulwovenBannerPatternDataComponent register(String type) {
        return register(MalumMod.malumPath(type));
    }
    public static SoulwovenBannerPatternDataComponent register(ResourceLocation type) {
        var pattern = new SoulwovenBannerPatternDataComponent(type, ResourceLocation.fromNamespaceAndPath(type.getNamespace(), "textures/block/banners/soulwoven_banner_" + type.getPath() + ".png"));
        REGISTERED_PATTERNS.add(pattern);
        return pattern;
    }

    public SoulwovenBannerPatternDataComponent(ResourceLocation type, ResourceLocation texturePath) {
        this(type, texturePath, Util.makeDescriptionId("banner_pattern", type));
    }

    public ItemStack getDefaultStack() {
        final ItemStack stack = ItemRegistry.SOULWOVEN_BANNER.get().getDefaultInstance();
        stack.set(DataComponentRegistry.SOULWOVEN_BANNER_PATTERN, this);
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

    public static SoulwovenBannerPatternDataComponent load(@Nullable CompoundTag tag) {
        return tag != null && tag.contains("pattern") ? CODEC.parse(NbtOps.INSTANCE, tag.get("pattern")).result().orElse(DEFAULT) : DEFAULT;
    }
}