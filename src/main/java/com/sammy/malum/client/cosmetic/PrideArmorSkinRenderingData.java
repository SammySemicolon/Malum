package com.sammy.malum.client.cosmetic;

import com.sammy.malum.common.data_components.*;
import com.sammy.malum.registry.client.ModelRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import team.lodestar.lodestone.systems.model.LodestoneArmorModel;

import static com.sammy.malum.MalumMod.malumPath;

public class PrideArmorSkinRenderingData extends ArmorSkinRenderingData {

    private final ResourceLocation slimTexture;
    private final ResourceLocation standardTexture;

    public PrideArmorSkinRenderingData(ItemSkinComponent skin) {
        String type = skin.name().getPath();
        this.slimTexture = malumPath("textures/armor/cosmetic/pride/" + type + "_drip_slim.png");
        this.standardTexture = malumPath("textures/armor/cosmetic/pride/" + type + "_drip.png");
    }

    @Override
    public ResourceLocation getTexture(LivingEntity livingEntity, boolean slim) {
        return slim ? this.slimTexture : standardTexture;
    }

    @Override
    public LodestoneArmorModel getModel(LivingEntity livingEntity, boolean slim) {
        return slim ? ModelRegistry.SLIM_PRIDEWEAR : ModelRegistry.PRIDEWEAR;
    }
}
