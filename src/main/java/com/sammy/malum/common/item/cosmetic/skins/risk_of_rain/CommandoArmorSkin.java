package com.sammy.malum.common.item.cosmetic.skins.risk_of_rain;

import com.sammy.malum.client.cosmetic.ArmorSkinRenderingData;
import com.sammy.malum.client.cosmetic.SimpleArmorSkinRenderingData;
import com.sammy.malum.common.item.cosmetic.skins.ArmorSkin;
import com.sammy.malum.registry.client.ModelRegistry;
import net.minecraft.world.item.Item;

import team.lodestar.lodestone.systems.item.LodestoneArmorItem;

import static com.sammy.malum.MalumMod.malumPath;

public class CommandoArmorSkin extends ArmorSkin {
    public CommandoArmorSkin(String id, Class<? extends LodestoneArmorItem> validArmorClass, Item weaveItem) {
        super(id, validArmorClass, weaveItem);
    }

    
    @Override
    public ArmorSkinRenderingData getRenderingData() {
        return new SimpleArmorSkinRenderingData(malumPath("textures/armor/cosmetic/risk_of_rain/commando.png"), ModelRegistry.COMMANDO);
    }
}
