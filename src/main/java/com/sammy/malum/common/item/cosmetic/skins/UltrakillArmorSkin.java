package com.sammy.malum.common.item.cosmetic.skins;

import com.sammy.malum.client.cosmetic.ArmorSkinRenderingData;
import com.sammy.malum.client.cosmetic.SimpleArmorSkinRenderingData;
import com.sammy.malum.registry.client.ModelRegistry;
import net.minecraft.world.item.Item;

import team.lodestar.lodestone.systems.item.LodestoneArmorItem;

import static com.sammy.malum.MalumMod.malumPath;

public class UltrakillArmorSkin extends ArmorSkin {
    public UltrakillArmorSkin(String id, Class<? extends LodestoneArmorItem> validArmorClass, Item weaveItem) {
        super(id, validArmorClass, weaveItem);
    }

    
    @Override
    public ArmorSkinRenderingData getRenderingData() {
        return new SimpleArmorSkinRenderingData(malumPath("textures/armor/cosmetic/" + id + ".png"), ModelRegistry.ULTRAKILL_MACHINE);
    }
}
