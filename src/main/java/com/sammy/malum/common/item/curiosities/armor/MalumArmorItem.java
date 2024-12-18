package com.sammy.malum.common.item.curiosities.armor;

import com.sammy.malum.client.cosmetic.*;
import com.sammy.malum.common.item.cosmetic.skins.*;
import net.minecraft.core.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import team.lodestar.lodestone.systems.item.*;

public abstract class MalumArmorItem extends LodestoneArmorItem {

    public MalumArmorItem(Holder<ArmorMaterial> materialIn, ArmorItem.Type slot, Properties builder) {
        super(materialIn, slot, builder);
    }

    public abstract ResourceLocation getArmorTexture();
}