package com.sammy.malum.common.data_components;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import com.sammy.malum.*;
import com.sammy.malum.registry.common.item.*;
import io.netty.buffer.*;
import net.minecraft.network.codec.*;
import net.minecraft.resources.*;
import net.minecraft.world.item.*;
import team.lodestar.lodestone.systems.item.*;

import java.util.*;

public record ItemSkinComponent(ResourceLocation name, int id) {

    public static Codec<ItemSkinComponent> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceLocation.CODEC.fieldOf("name").forGetter(ItemSkinComponent::name),
            Codec.INT.fieldOf("id").forGetter(ItemSkinComponent::id)
    ).apply(instance, ItemSkinComponent::new));

    public static StreamCodec<ByteBuf, ItemSkinComponent> STREAM_CODEC = ByteBufCodecs.fromCodec(ItemSkinComponent.CODEC);

    public static final List<ItemSkinComponent> REGISTERED_SKINS = new ArrayList<>();

    public static final ItemSkinComponent ACE = register("ace");
    public static final ItemSkinComponent AGENDER = register("agender");
    public static final ItemSkinComponent ARO = register("aro");
    public static final ItemSkinComponent AROACE = register("aroace");
    public static final ItemSkinComponent BI = register("bi");
    public static final ItemSkinComponent DEMIBOY = register("demiboy");
    public static final ItemSkinComponent DEMIGIRL = register("demigirl");
    public static final ItemSkinComponent ENBY = register("enby");
    public static final ItemSkinComponent GAY = register("gay");
    public static final ItemSkinComponent GENDERFLUID = register("genderfluid");
    public static final ItemSkinComponent GENDERQUEER = register("genderqueer");
    public static final ItemSkinComponent INTERSEX = register("intersex");
    public static final ItemSkinComponent LESBIAN = register("lesbian");
    public static final ItemSkinComponent PAN = register("pan");
    public static final ItemSkinComponent PLURAL = register("plural");
    public static final ItemSkinComponent POLY = register("poly");
    public static final ItemSkinComponent PRIDE = register("pride");
    public static final ItemSkinComponent TRANS = register("trans");

    public static final ItemSkinComponent BLUE_MACHINE = register("v1");
    public static final ItemSkinComponent RED_MACHINE = register("v2");

    public static final ItemSkinComponent COMMANDO = register("commando");

    public static final ItemSkinComponent ANCIENT_CLOTH = register("ancient_soul_hunter");
    public static final ItemSkinComponent ANCIENT_METAL = register("ancient_soul_stained_steel");

    public static ItemSkinComponent register(String name) {
        return register(MalumMod.malumPath(name));
    }

    public static ItemSkinComponent register(ResourceLocation name) {
        var pattern = new ItemSkinComponent(name, REGISTERED_SKINS.size());
        REGISTERED_SKINS.add(pattern);
        return pattern;
    }

    @SuppressWarnings("DataFlowIssue")
    public static int getSkinId(ItemStack stack) {
        if (!stack.has(DataComponentRegistry.ITEM_SKIN)) {
            return -1;
        }
        return stack.get(DataComponentRegistry.ITEM_SKIN.get()).id;
    }
    @SuppressWarnings("DataFlowIssue")
    public static int getAppliedSkinId(ItemStack stack) {
        if (!stack.has(DataComponentRegistry.APPLIED_ITEM_SKIN)) {
            return -1;
        }
        return stack.get(DataComponentRegistry.APPLIED_ITEM_SKIN.get()).id;
    }
}