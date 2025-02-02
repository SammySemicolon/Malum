package com.sammy.malum.data.item;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.data_components.*;
import com.sammy.malum.core.systems.ritual.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import team.lodestar.lodestone.helpers.DataHelper;
import team.lodestar.lodestone.systems.datagen.ItemModelSmithTypes;
import team.lodestar.lodestone.systems.datagen.itemsmith.ItemModelSmith;
import team.lodestar.lodestone.systems.datagen.itemsmith.ItemModelSmithResult;
import team.lodestar.lodestone.systems.item.LodestoneArmorItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class MalumItemModelSmithTypes extends ItemModelSmithTypes {

    public static final ResourceLocation LARGE_HANDHELD = MalumMod.malumPath("item/handheld_large");
    public static final Consumer<ItemModelSmithResult> HUGE_ITEM = result -> {
        var provider = result.getProvider();
        var existingFileHelper = provider.existingFileHelper;
        var separateTransforms = result.addSeparateTransformData();
        var firstPersonModel = PARENTED_ITEM.apply(LARGE_HANDHELD)
                .addModelNameAffix("_huge")
                .addTextureNameAffix("_huge")
                .act(provider, result::getItem);
        var guiModel = ItemModelSmithTypes.GENERATED_ITEM
                .addModelNameAffix("_gui")
                .act(provider, result::getItem);
        separateTransforms.perspective(ItemDisplayContext.GUI, guiModel.parentedToThis(existingFileHelper));
        separateTransforms.perspective(ItemDisplayContext.FIXED, guiModel.parentedToThis(existingFileHelper));
        separateTransforms.base(firstPersonModel.parentedToThis(existingFileHelper));
    };

    public static ItemModelSmith LARGE_HANDHELD_ITEM = PARENTED_ITEM.apply(LARGE_HANDHELD).modifyResult(HUGE_ITEM);

    public static ItemModelSmith IMPETUS_ITEM = new ItemModelSmith((item, provider) -> {
        String name = provider.getItemName(item);
        List<String> split = DataHelper.reverseOrder(new ArrayList<>(), Arrays.asList(name.split("_")));
        split.removeFirst();
        String alteredName = String.join("_", split);
        return provider.createGenericModel(item, GENERATED, provider.getItemTexture(alteredName));
    });

    public static ItemModelSmith RITUAL_SHARD_ITEM = new ItemModelSmith((item, provider) -> {
        String base = "ritual_shard";
        var model = provider.createGenericModel(item, GENERATED, provider.getItemTexture(base + "_faded"));
        for (MalumRitualTier ritualTier : MalumRitualTier.TIERS) {
            if (ritualTier.equals(MalumRitualTier.FADED)) {
                continue;
            }
            String path = ritualTier.identifier.getPath();
            ResourceLocation itemTexturePath = provider.getItemTexture(base + "_" + path);
            provider.getBuilder(BuiltInRegistries.ITEM.getKey(item).getPath()).override()
                    .predicate(MalumMod.malumPath("tier"), ritualTier.potency)
                    .model(provider.withExistingParent(provider.getItemName(item) + "_" + path, GENERATED).texture("layer0", itemTexturePath))
                    .end();
        }
        return model;
    });

    public static ItemModelSmith SOULWOVEN_POUCH = new ItemModelSmith((item, provider) -> {
        String base = provider.getItemName(item);
        final ResourceLocation texture = provider.getItemTexture(base);
        provider.createGenericModel(item, GENERATED, texture);
        return provider.getBuilder(BuiltInRegistries.ITEM.getKey(item).getPath()).override()
                .predicate(MalumMod.malumPath("filled"), 1)
                .model(provider.withExistingParent(base + "_filled", HANDHELD).texture("layer0", texture.withSuffix("_filled")))
                .end();
    });

    public static ItemModelSmith SOULWOVEN_BANNER = new ItemModelSmith((item, provider) -> {
        String base = provider.getItemName(item);
        var model = provider.createGenericModel(item, GENERATED, provider.getItemTexture(base + "_default"));
        for (SoulwovenBannerPatternDataComponent pattern : SoulwovenBannerPatternDataComponent.REGISTERED_PATTERNS) {
            final int i = SoulwovenBannerPatternDataComponent.REGISTERED_PATTERNS.indexOf(pattern);
            if (pattern.equals(SoulwovenBannerPatternDataComponent.DEFAULT)) {
                continue;
            }
            final String path = base + "_" + pattern.type().getPath();
            ResourceLocation itemTexturePath = provider.getItemTexture(path);
            provider.getBuilder(BuiltInRegistries.ITEM.getKey(item).getPath()).override()
                    .predicate(MalumMod.malumPath("pattern"), i)
                    .model(provider.withExistingParent(path, GENERATED).texture("layer0", itemTexturePath))
                    .end();
        }
        return model;
    });

    public static ItemModelSmith CATALYST_LOBBER = new ItemModelSmith((item, provider) -> {
        String base = provider.getItemName(item);
        var model = provider.createGenericModel(item, HANDHELD, provider.getItemTexture(base));
        for (int i = 1; i <= 2; i++) {
            String affix = i == 1 ? "open" : "loaded";
            ResourceLocation itemTexturePath = provider.getItemTexture(base + "_" + affix);
            provider.getBuilder(BuiltInRegistries.ITEM.getKey(item).getPath()).override()
                    .predicate(MalumMod.malumPath("state"), i)
                    .model(provider.withExistingParent(base + "_" + affix, HANDHELD).texture("layer0", itemTexturePath))
                    .end();
        }
        return model;
    });

    public static ItemModelSmith UMBRAL_SPIRIT_ITEM = new ItemModelSmith((item, provider) -> provider.createGenericModel(item, GENERATED, provider.getItemTexture("umbral_spirit_shard")));

    public static ItemModelSmith SPIRIT_ITEM = new ItemModelSmith((item, provider) -> provider.createGenericModel(item, GENERATED, provider.getItemTexture("spirit_shard")));

    public static ItemModelSmith GENERATED_OVERLAY_ITEM = new ItemModelSmith((item, provider) -> {
        String name = provider.getItemName(item);
        return provider.withExistingParent(name, GENERATED).texture("layer0", provider.getItemTexture(name)).texture("layer1", provider.getItemTexture(name + "_overlay"));
    });

    public static ItemModelSmith HANDHELD_OVERLAY_ITEM = new ItemModelSmith((item, provider) -> {
        String name = provider.getItemName(item);
        return provider.withExistingParent(name, HANDHELD).texture("layer0", provider.getItemTexture(name)).texture("layer1", provider.getItemTexture(name + "_overlay"));
    });

    public static ItemModelSmith ETHER_BRAZIER_ITEM = new ItemModelSmith((item, provider) -> {
        String name = provider.getItemName(item);
        String rockType = name.split("_")[0];
        String brazierName = rockType + "_ether_brazier";
        String overlayName = name.replace(rockType + "_", "");
        return provider.withExistingParent(name, GENERATED).texture("layer0", provider.getItemTexture(brazierName)).texture("layer1", provider.getItemTexture(overlayName + "_overlay"));
    });

    public static ItemModelSmith IRIDESCENT_ETHER_BRAZIER_ITEM = new ItemModelSmith((item, provider) -> {
        String name = provider.getItemName(item);
        String rockType = name.split("_")[0];
        String brazierName = rockType + "_ether_brazier";
        String overlayName = name.replace(rockType + "_", "");
        return provider.withExistingParent(name, GENERATED).texture("layer0", provider.getItemTexture(brazierName)).texture("layer1", provider.getItemTexture(overlayName)).texture("layer2", provider.getItemTexture(overlayName + "_overlay"));
    });

    public static ItemModelSmith IRIDESCENT_ETHER_TORCH_ITEM = new ItemModelSmith((item, provider) -> {
        String name = provider.getItemName(item);
        return provider.withExistingParent(name, HANDHELD).texture("layer0", provider.getItemTexture("ether_torch")).texture("layer1", provider.getItemTexture(name)).texture("layer2", provider.getItemTexture(name + "_overlay"));
    });

    public static ItemModelSmith SKIN_APPLICABLE_ARMOR_ITEM = new ItemModelSmith((item, provider) -> {
        String name = provider.getItemName(item);
        var model = provider.createGenericModel(item, GENERATED, provider.getItemTexture(name));
        for (ItemSkinComponent registeredSkin : ItemSkinComponent.REGISTERED_SKINS) {
            final int index = registeredSkin.id();
            var armorItem = (LodestoneArmorItem) item;
            var itemTexture = index < 18 ? getDefaultPrideTexturePath(registeredSkin, armorItem) : getDefaultTexturePath(registeredSkin, armorItem);
            var split = itemTexture.getPath().split("/");
            var modelName = split[split.length - 1];
            provider.getBuilder(BuiltInRegistries.ITEM.getKey(item).getPath()).override()
                    .predicate(MalumMod.malumPath("item_skin"), index)
                    .model(provider.withExistingParent(modelName, GENERATED).texture("layer0", itemTexture))
                    .end();
        }
        return model;
    });

    public static ResourceLocation getDefaultPrideTexturePath(ItemSkinComponent skin, LodestoneArmorItem item) {
        ResourceLocation path = MalumMod.malumPath("item/cosmetic/armor_icons/pride/" + skin.name().getPath());
        switch (item.getEquipmentSlot()) {
            case HEAD -> {
                return path.withSuffix("_beanie");
            }
            case CHEST -> {
                return path.withSuffix("_hoodie");
            }
            case LEGS -> {
                return path.withSuffix("_shorts");
            }
            case FEET -> {
                return path.withSuffix("_socks");
            }
        }
        return null;
    }

    public static ResourceLocation getDefaultTexturePath(ItemSkinComponent skin, LodestoneArmorItem item) {
        ResourceLocation path = MalumMod.malumPath("item/cosmetic/armor_icons/" + skin.name().getPath());
        switch (item.getEquipmentSlot()) {
            case HEAD -> {
                return path.withSuffix("_head");
            }
            case CHEST -> {
                return path.withSuffix("_body");
            }
            case LEGS -> {
                return path.withSuffix("_legs");
            }
            case FEET -> {
                return path.withSuffix("_feet");
            }
        }
        return null;
    }
}