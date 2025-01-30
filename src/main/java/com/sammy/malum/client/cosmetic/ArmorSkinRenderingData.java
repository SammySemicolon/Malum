package com.sammy.malum.client.cosmetic;

import com.sammy.malum.common.item.cosmetic.skins.ArmorSkin;
import net.minecraft.Util;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.resources.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import team.lodestar.lodestone.systems.model.LodestoneArmorModel;

import java.util.function.Function;

public abstract class ArmorSkinRenderingData {

    public static Function<ArmorSkin, ArmorSkinRenderingData> RENDERING_DATA = Util.memoize(ArmorSkin::getRenderingData);

    public abstract ResourceLocation getTexture(LivingEntity livingEntity, boolean slim);

    public abstract LodestoneArmorModel getModel(LivingEntity livingEntity, boolean slim);

    public ResourceLocation getTexture(LivingEntity livingEntity) {
        return getTexture(livingEntity, isSlim(livingEntity));
    }

    public LodestoneArmorModel getModel(LivingEntity livingEntity) {
        return getModel(livingEntity, isSlim(livingEntity));
    }

    public static boolean isSlim(LivingEntity livingEntity) {
        if (!(livingEntity instanceof AbstractClientPlayer player)) return false;
        final PlayerSkin.Model model = player.getSkin().model();
        return model.id().equals("slim");
    }
}
