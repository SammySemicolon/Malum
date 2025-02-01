package com.sammy.malum.client.cosmetic;

import com.sammy.malum.common.data_components.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import team.lodestar.lodestone.systems.model.LodestoneArmorModel;

import static com.sammy.malum.MalumMod.malumPath;

public class SimpleArmorSkinRenderingData extends ArmorSkinRenderingData {

    private final ResourceLocation texture;
    private final LodestoneArmorModel model;

    public SimpleArmorSkinRenderingData(ItemSkinComponent skin, LodestoneArmorModel model) {
        String type = skin.name().getPath();
        this.texture = malumPath("textures/armor/cosmetic/" + type + ".png");
        this.model = model;
    }

    @Override
    public ResourceLocation getTexture(LivingEntity livingEntity, boolean slim) {
        return texture;
    }

    @Override
    public LodestoneArmorModel getModel(LivingEntity livingEntity, boolean slim) {
        return model;
    }
}
