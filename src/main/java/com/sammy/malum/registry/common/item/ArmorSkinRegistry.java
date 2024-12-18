package com.sammy.malum.registry.common.item;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.item.cosmetic.skins.ArmorSkin;
import com.sammy.malum.common.item.cosmetic.skins.PrideArmorSkin;
import com.sammy.malum.common.item.cosmetic.skins.UltrakillArmorSkin;
import com.sammy.malum.common.item.cosmetic.skins.ancient.AncientClothArmorSkin;
import com.sammy.malum.common.item.cosmetic.skins.ancient.AncientMetalArmorSkin;
import com.sammy.malum.common.item.cosmetic.skins.risk_of_rain.CommandoArmorSkin;
import com.sammy.malum.common.item.cosmetic.skins.risk_of_rain.ExecutionerArmorSkin;
import com.sammy.malum.common.item.curiosities.armor.MalumArmorItem;
import net.minecraft.world.item.Item;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class ArmorSkinRegistry {

    public static final Map<String, ArmorSkin> SKINS = new LinkedHashMap<>();
    public static Map<ArmorSkin, ArmorSkin.ArmorSkinDatagenData> SKIN_DATAGEN_DATA = new HashMap<>();

    public static void wipeCache() {
        SKIN_DATAGEN_DATA = null;
    }

    public static void registerItemSkins() {
        registerPridewear("ace", ItemRegistry.ACE_PRIDEWEAVE.get());
        registerPridewear("agender", ItemRegistry.AGENDER_PRIDEWEAVE.get());
        registerPridewear("aro", ItemRegistry.ARO_PRIDEWEAVE.get());
        registerPridewear("aroace", ItemRegistry.AROACE_PRIDEWEAVE.get());
        registerPridewear("bi", ItemRegistry.BI_PRIDEWEAVE.get());
        registerPridewear("demiboy", ItemRegistry.DEMIBOY_PRIDEWEAVE.get());
        registerPridewear("demigirl", ItemRegistry.DEMIGIRL_PRIDEWEAVE.get());
        registerPridewear("enby", ItemRegistry.ENBY_PRIDEWEAVE.get());
        registerPridewear("gay", ItemRegistry.GAY_PRIDEWEAVE.get());
        registerPridewear("genderfluid", ItemRegistry.GENDERFLUID_PRIDEWEAVE.get());
        registerPridewear("genderqueer", ItemRegistry.GENDERQUEER_PRIDEWEAVE.get());
        registerPridewear("intersex", ItemRegistry.INTERSEX_PRIDEWEAVE.get());
        registerPridewear("lesbian", ItemRegistry.LESBIAN_PRIDEWEAVE.get());
        registerPridewear("pan", ItemRegistry.PAN_PRIDEWEAVE.get());
        registerPridewear("plural", ItemRegistry.PLURAL_PRIDEWEAVE.get());
        registerPridewear("poly", ItemRegistry.POLY_PRIDEWEAVE.get());
        registerPridewear("pride", ItemRegistry.PRIDE_PRIDEWEAVE.get());
        registerPridewear("trans", ItemRegistry.TRANS_PRIDEWEAVE.get());

        registerItemSkin(new ExecutionerArmorSkin("executioner_drip", MalumArmorItem.class, ItemRegistry.DREADED_WEAVE.get()),
                new ArmorSkin.ArmorSkinDatagenData(
                        "malum:item/cosmetic/armor_icons/executioner_",
                        "malum:models/item/executioner_",
                        "visor", "chestplate", "leggings", "boots"
                ));

        registerItemSkin(new CommandoArmorSkin("commando_drip", MalumArmorItem.class, ItemRegistry.CORNERED_WEAVE.get()),
                new ArmorSkin.ArmorSkinDatagenData(
                        "malum:item/cosmetic/armor_icons/commando_",
                        "malum:models/item/commando_",
                        "visor", "chestplate", "leggings", "boots"
                ));

        registerItemSkin(new UltrakillArmorSkin("ultrakill_v1", MalumArmorItem.class, ItemRegistry.MECHANICAL_WEAVE_V1.get()),
                new ArmorSkin.ArmorSkinDatagenData(
                        "malum:item/cosmetic/armor_icons/v1_",
                        "malum:models/item/v1_",
                        "visor", "chestplate", "leggings", "boots"
                ));

        registerItemSkin(new UltrakillArmorSkin("ultrakill_v2", MalumArmorItem.class, ItemRegistry.MECHANICAL_WEAVE_V2.get()),
                new ArmorSkin.ArmorSkinDatagenData(
                        "malum:item/cosmetic/armor_icons/v2_",
                        "malum:models/item/v2_",
                        "visor", "chestplate", "leggings", "boots"
                ));

        registerItemSkin(new AncientClothArmorSkin("ancient_cloth", ItemRegistry.ANCIENT_WEAVE.get()),
                new ArmorSkin.ArmorSkinDatagenData(
                        "malum:item/cosmetic/armor_icons/ancient_soul_hunter_",
                        "malum:models/item/ancient_soul_hunter_",
                        "cloak", "robe", "leggings", "boots"
                ));

        registerItemSkin(new AncientMetalArmorSkin("ancient_metal", ItemRegistry.ANCIENT_WEAVE.get()),
                new ArmorSkin.ArmorSkinDatagenData(
                        "malum:item/cosmetic/armor_icons/ancient_soul_stained_steel_",
                        "malum:models/item/ancient_soul_stained_steel_",
                        "helmet", "chestplate", "leggings", "boots"
                ));
    }

    public static ArmorSkin registerItemSkin(String tag, ArmorSkin skin) {
        SKINS.put(tag, skin);
        return skin;
    }

    public static void registerItemSkin(ArmorSkin skin, ArmorSkin.ArmorSkinDatagenData datagenData) {
        SKIN_DATAGEN_DATA.put(skin, datagenData);
        registerItemSkin(skin.id, skin);
    }

    public static void registerPridewear(String tag, Item prideweave) {
        registerItemSkin(new PrideArmorSkin(tag, MalumArmorItem.class, prideweave),
                new ArmorSkin.ArmorSkinDatagenData(
                        "malum:item/cosmetic/armor_icons/pride/" + tag + "_",
                        "malum:models/item/pridewear" + tag + "_",
                        "beanie", "hoodie", "shorts", "socks"
                ));
    }
}
