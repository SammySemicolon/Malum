package com.sammy.malum.common.item.curiosities.armor;

import com.sammy.malum.client.cosmetic.*;
import com.sammy.malum.common.data_components.*;
import com.sammy.malum.registry.common.item.*;
import net.minecraft.core.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import net.neoforged.api.distmarker.*;
import team.lodestar.lodestone.systems.item.*;

public abstract class MalumArmorItem extends LodestoneArmorItem {

    public MalumArmorItem(Holder<ArmorMaterial> pMaterial, Type pType, int durabilityFactor, Properties pProperties) {
        super(pMaterial, pType, durabilityFactor, pProperties);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public ResourceLocation getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, ArmorMaterial.Layer layer, boolean innerModel) {
        if (entity instanceof LivingEntity livingEntity) {
            ItemSkinComponent skin = stack.get(DataComponentRegistry.APPLIED_ITEM_SKIN);
            if (ArmorSkinRenderingData.ARMOR_RENDERING_DATA.containsKey(skin)) {
                return ArmorSkinRenderingData.ARMOR_RENDERING_DATA.get(skin).getTexture(livingEntity);
            }
        }
        return getArmorTexture();
    }

    public abstract ResourceLocation getArmorTexture();
}