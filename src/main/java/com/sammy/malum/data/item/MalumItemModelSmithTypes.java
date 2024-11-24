package com.sammy.malum.data.item;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.item.cosmetic.skins.ArmorSkin;
import com.sammy.malum.common.item.spirit.*;
import com.sammy.malum.core.systems.ritual.*;
import com.sammy.malum.registry.common.item.ArmorSkinRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import team.lodestar.lodestone.helpers.DataHelper;
import team.lodestar.lodestone.systems.datagen.itemsmith.ItemModelSmith;
import team.lodestar.lodestone.systems.item.LodestoneArmorItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static team.lodestar.lodestone.systems.datagen.ItemModelSmithTypes.GENERATED;
import static team.lodestar.lodestone.systems.datagen.ItemModelSmithTypes.HANDHELD;

public class MalumItemModelSmithTypes {

    public static ItemModelSmith IMPETUS_ITEM = new ItemModelSmith((item, provider) -> {
        String name = provider.getItemName(item);
        List<String> split = DataHelper.reverseOrder(new ArrayList<>(), Arrays.asList(name.split("_")));
        split.remove(0);
        String alteredName = String.join("_", split);
        provider.createGenericModel(item, GENERATED, provider.getItemTexture(alteredName));
    });

    public static ItemModelSmith RITUAL_SHARD_ITEM = new ItemModelSmith((item, provider) -> {
        String base = "ritual_shard";
        provider.createGenericModel(item, GENERATED, provider.getItemTexture(base + "_faded"));
        for (MalumRitualTier ritualTier : MalumRitualTier.TIERS) {
            if (ritualTier.equals(MalumRitualTier.FADED)) {
                continue;
            }
            String path = ritualTier.identifier.getPath();
            ResourceLocation itemTexturePath = provider.getItemTexture(base + "_" + path);
            provider.getBuilder(BuiltInRegistries.ITEM.getKey(item).getPath()).override()
                    .predicate(ResourceLocation.withDefaultNamespace(RitualShardItem.RITUAL_TYPE), ritualTier.potency)
                    .model(provider.withExistingParent(provider.getItemName(item) + "_" + path, GENERATED).texture("layer0", itemTexturePath))
                    .end();
        }
    });

    public static ItemModelSmith CATALYST_LOBBER = new ItemModelSmith((item, provider) -> {
        String base = provider.getItemName(item);
        provider.createGenericModel(item, HANDHELD, provider.getItemTexture(base));
        for (int i = 1; i <= 2; i++) {
            String affix = i == 1 ? "open" : "loaded";
            ResourceLocation itemTexturePath = provider.getItemTexture(base + "_" + affix);
            provider.getBuilder(BuiltInRegistries.ITEM.getKey(item).getPath()).override()
                    .predicate(MalumMod.malumPath("state"), i)
                    .model(provider.withExistingParent(base + "_" + affix, HANDHELD).texture("layer0", itemTexturePath))
                    .end();
        }
    });

    public static ItemModelSmith SPIRIT_ITEM = new ItemModelSmith((item, provider) -> {
        provider.createGenericModel(item, GENERATED, provider.getItemTexture("spirit_shard"));
    });

    public static ItemModelSmith GENERATED_OVERLAY_ITEM = new ItemModelSmith((item, provider) -> {
        String name = provider.getItemName(item);
        provider.withExistingParent(name, GENERATED).texture("layer0", provider.getItemTexture(name)).texture("layer1", provider.getItemTexture(name + "_overlay"));
    });

    public static ItemModelSmith HANDHELD_OVERLAY_ITEM = new ItemModelSmith((item, provider) -> {
        String name = provider.getItemName(item);
        provider.withExistingParent(name, HANDHELD).texture("layer0", provider.getItemTexture(name)).texture("layer1", provider.getItemTexture(name + "_overlay"));
    });

    public static ItemModelSmith ETHER_BRAZIER_ITEM = new ItemModelSmith((item, provider) -> {
        String name = provider.getItemName(item);
        String rockType = name.split("_")[0];
        String brazierName = rockType + "_ether_brazier";
        String overlayName = name.replace(rockType + "_", "");
        provider.withExistingParent(name, GENERATED).texture("layer0", provider.getItemTexture(brazierName)).texture("layer1", provider.getItemTexture(overlayName + "_overlay"));
    });

    public static ItemModelSmith IRIDESCENT_ETHER_BRAZIER_ITEM = new ItemModelSmith((item, provider) -> {
        String name = provider.getItemName(item);
        String rockType = name.split("_")[0];
        String brazierName = rockType + "_ether_brazier";
        String overlayName = name.replace(rockType + "_", "");
        provider.withExistingParent(name, GENERATED).texture("layer0", provider.getItemTexture(brazierName)).texture("layer1", provider.getItemTexture(overlayName)).texture("layer2", provider.getItemTexture(overlayName + "_overlay"));
    });

    public static ItemModelSmith IRIDESCENT_ETHER_TORCH_ITEM = new ItemModelSmith((item, provider) -> {
        String name = provider.getItemName(item);
        provider.withExistingParent(name, HANDHELD).texture("layer0", provider.getItemTexture("ether_torch")).texture("layer1", provider.getItemTexture(name)).texture("layer2", provider.getItemTexture(name + "_overlay"));
    });


    public static ItemModelSmith ARMOR_ITEM = new ItemModelSmith((item, provider) -> {
        String name = provider.getItemName(item);
        provider.createGenericModel(item, GENERATED, provider.getItemTexture(name));
        for (Map.Entry<String, ArmorSkin> entry : ArmorSkinRegistry.SKINS.entrySet()) {
            ArmorSkin skin = entry.getValue();
            int value = skin.index;
            ArmorSkin.ArmorSkinDatagenData datagenData = ArmorSkinRegistry.SKIN_DATAGEN_DATA.get(skin);
            if (datagenData == null) {
                continue;
            }
            String itemSuffix = datagenData.getSuffix((LodestoneArmorItem) item);
            ResourceLocation itemTexturePath = ResourceLocation.tryParse(datagenData.itemTexturePrefix + itemSuffix);
            provider.getBuilder(BuiltInRegistries.ITEM.getKey(item).getPath()).override()
                    .predicate(ResourceLocation.parse(ArmorSkin.MALUM_SKIN_TAG), value)
                    .model(provider.withExistingParent(entry.getKey() + "_" + itemSuffix, GENERATED).texture("layer0", itemTexturePath))
                    .end();
        }
    });
}