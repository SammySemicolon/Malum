package com.sammy.malum.common.item.curiosities.armor;

import com.sammy.malum.client.cosmetic.*;
import com.sammy.malum.common.item.cosmetic.skins.*;
import net.minecraft.core.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import team.lodestar.lodestone.systems.item.*;

public abstract class MalumArmorItem extends LodestoneArmorItem {

    public MalumArmorItem(Holder<ArmorMaterial> pMaterial, Type pType, int durabilityFactor, Properties pProperties) {
        super(pMaterial, pType, durabilityFactor, pProperties);
    }

    @Override
    public ResourceLocation getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, ArmorMaterial.Layer layer, boolean innerModel) {
        ArmorSkin skin = ArmorSkin.getAppliedItemSkin(stack);
        if (skin != null && entity instanceof LivingEntity livingEntity) {
            return ArmorSkinRenderingData.RENDERING_DATA.apply(skin).getTexture(livingEntity);
        }
        return getArmorTexture();
    }

    public abstract ResourceLocation getArmorTexture();
}