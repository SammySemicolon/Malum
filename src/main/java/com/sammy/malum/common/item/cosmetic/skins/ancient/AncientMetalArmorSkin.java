package com.sammy.malum.common.item.cosmetic.skins.ancient;

import com.sammy.malum.client.cosmetic.ArmorSkinRenderingData;
import com.sammy.malum.client.cosmetic.SimpleArmorSkinRenderingData;
import com.sammy.malum.common.item.cosmetic.skins.ArmorSkin;
import com.sammy.malum.common.item.curiosities.armor.SoulStainedSteelArmorItem;
import com.sammy.malum.registry.client.ModelRegistry;
import net.minecraft.world.item.Item;


import static com.sammy.malum.MalumMod.malumPath;

public class AncientMetalArmorSkin extends ArmorSkin {
    public AncientMetalArmorSkin(String id, Item weaveItem) {
        super(id, SoulStainedSteelArmorItem.class, weaveItem);
    }

    
    @Override
    public ArmorSkinRenderingData getRenderingData() {
        return new SimpleArmorSkinRenderingData(malumPath("textures/armor/cosmetic/ancient_soul_stained_steel.png"), ModelRegistry.ANCIENT_SOUL_STAINED_STEEL_ARMOR);
    }
}
