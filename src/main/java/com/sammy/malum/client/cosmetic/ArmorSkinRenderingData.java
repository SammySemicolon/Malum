package com.sammy.malum.client.cosmetic;

import com.sammy.malum.common.data_components.*;
import com.sammy.malum.registry.client.*;
import net.minecraft.Util;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.resources.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import team.lodestar.lodestone.systems.model.LodestoneArmorModel;

import java.util.*;
import java.util.function.Function;

public abstract class ArmorSkinRenderingData {

    public static HashMap<ItemSkinComponent, ArmorSkinRenderingData> ARMOR_RENDERING_DATA = new HashMap<>();

    private static void addPrideData(ItemSkinComponent component) {
        ARMOR_RENDERING_DATA.put(component, new PrideArmorSkinRenderingData(component));
    }
    private static void addData(ItemSkinComponent component, LodestoneArmorModel model) {
        ARMOR_RENDERING_DATA.put(component, new SimpleArmorSkinRenderingData(component, model));
    }

    static {
        addPrideData(ItemSkinComponent.ACE);
        addPrideData(ItemSkinComponent.AGENDER);
        addPrideData(ItemSkinComponent.ARO);
        addPrideData(ItemSkinComponent.AROACE);
        addPrideData(ItemSkinComponent.BI);
        addPrideData(ItemSkinComponent.DEMIBOY);
        addPrideData(ItemSkinComponent.DEMIGIRL);
        addPrideData(ItemSkinComponent.ENBY);
        addPrideData(ItemSkinComponent.GAY);
        addPrideData(ItemSkinComponent.GENDERFLUID);
        addPrideData(ItemSkinComponent.GENDERQUEER);
        addPrideData(ItemSkinComponent.INTERSEX);
        addPrideData(ItemSkinComponent.LESBIAN);
        addPrideData(ItemSkinComponent.PAN);
        addPrideData(ItemSkinComponent.PLURAL);
        addPrideData(ItemSkinComponent.POLY);
        addPrideData(ItemSkinComponent.PRIDE);
        addPrideData(ItemSkinComponent.TRANS);

        addData(ItemSkinComponent.BLUE_MACHINE, ModelRegistry.ULTRAKILL_MACHINE);
        addData(ItemSkinComponent.RED_MACHINE, ModelRegistry.ULTRAKILL_MACHINE);
        addData(ItemSkinComponent.COMMANDO, ModelRegistry.COMMANDO);

        addData(ItemSkinComponent.ANCIENT_CLOTH, ModelRegistry.ANCIENT_SOUL_HUNTER_ARMOR);
        addData(ItemSkinComponent.ANCIENT_METAL, ModelRegistry.ANCIENT_SOUL_STAINED_STEEL_ARMOR);
    }

    public abstract ResourceLocation getTexture(LivingEntity livingEntity, boolean slim);

    protected abstract LodestoneArmorModel getModel(LivingEntity livingEntity, boolean slim);

    public ResourceLocation getTexture(LivingEntity livingEntity) {
        return getTexture(livingEntity, isSlim(livingEntity));
    }

    public final LodestoneArmorModel getModel(LivingEntity livingEntity) {
        return getModel(livingEntity, isSlim(livingEntity));
    }

    public static boolean isSlim(LivingEntity livingEntity) {
        if (!(livingEntity instanceof AbstractClientPlayer player)) return false;
        final PlayerSkin.Model model = player.getSkin().model();
        return model.id().equals("slim");
    }
}
