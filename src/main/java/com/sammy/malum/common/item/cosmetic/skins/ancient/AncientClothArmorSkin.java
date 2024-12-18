package com.sammy.malum.common.item.cosmetic.skins.ancient;

import com.sammy.malum.client.cosmetic.ArmorSkinRenderingData;
import com.sammy.malum.client.cosmetic.SimpleArmorSkinRenderingData;
import com.sammy.malum.common.item.cosmetic.skins.ArmorSkin;
import com.sammy.malum.common.item.curiosities.armor.SoulHunterArmorItem;
import com.sammy.malum.registry.client.ModelRegistry;
import net.minecraft.world.item.Item;


import static com.sammy.malum.MalumMod.malumPath;

public class AncientClothArmorSkin extends ArmorSkin {
    public AncientClothArmorSkin(String id, Item weaveItem) {
        super(id, SoulHunterArmorItem.class, weaveItem);
    }

    
    @Override
    public ArmorSkinRenderingData getRenderingData() {
        return new SimpleArmorSkinRenderingData(malumPath("textures/armor/cosmetic/ancient_soul_hunter.png"), ModelRegistry.ANCIENT_SOUL_HUNTER_ARMOR);
    }
}
