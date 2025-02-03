package com.sammy.malum.registry.client;

import com.sammy.malum.MalumMod;
import com.sammy.malum.client.model.*;
import com.sammy.malum.client.model.cosmetic.GenericArmorModel;
import com.sammy.malum.client.model.cosmetic.GenericSlimArmorModel;
import com.sammy.malum.client.model.cosmetic.ScarfModel;
import com.sammy.malum.client.model.cosmetic.ancient.AncientSoulHunterArmorModel;
import com.sammy.malum.client.model.cosmetic.ancient.AncientSoulStainedSteelArmorModel;
import com.sammy.malum.client.model.cosmetic.pride.PridewearArmorModel;
import com.sammy.malum.client.model.cosmetic.pride.SlimPridewearArmorModel;
import com.sammy.malum.client.model.cosmetic.risky.CommandoArmorModel;
import com.sammy.malum.client.model.cosmetic.risky.ExecutionerArmorModel;
import com.sammy.malum.client.model.cosmetic.ultrakill.UltrakillMachineArmorModel;

import com.sammy.malum.client.scarf.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.resources.*;
import net.minecraft.world.entity.player.*;
import net.neoforged.api.distmarker.*;
import net.neoforged.bus.api.*;
import net.neoforged.fml.common.*;
import net.neoforged.neoforge.client.event.*;
import top.theillusivec4.curios.client.render.*;

@EventBusSubscriber(modid = MalumMod.MALUM, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class ModelRegistry {

    public static SoulHunterArmorModel SOUL_HUNTER_ARMOR;
    public static AncientSoulHunterArmorModel ANCIENT_SOUL_HUNTER_ARMOR;

    public static SoulStainedSteelArmorModel SOUL_STAINED_ARMOR;
    public static AncientSoulStainedSteelArmorModel ANCIENT_SOUL_STAINED_STEEL_ARMOR;

    public static MalignantStrongholdArmorModel MALIGNANT_LEAD_ARMOR;

    public static GenericSlimArmorModel GENERIC_SLIM_ARMOR;
    public static GenericArmorModel GENERIC_ARMOR;

    public static CommandoArmorModel COMMANDO;
    public static ExecutionerArmorModel EXECUTIONER;

    public static UltrakillMachineArmorModel ULTRAKILL_MACHINE;

    public static PridewearArmorModel PRIDEWEAR;
    public static SlimPridewearArmorModel SLIM_PRIDEWEAR;

    public static TopHatModel TOP_HAT;
    public static TailModel TAIL_MODEL;

    public static HeadOverlayModel HEAD_OVERLAY_MODEL;
    public static ScarfModel SCARF;

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(SoulHunterArmorModel.LAYER, SoulHunterArmorModel::createBodyLayer);
        event.registerLayerDefinition(SoulStainedSteelArmorModel.LAYER, SoulStainedSteelArmorModel::createBodyLayer);
        event.registerLayerDefinition(MalignantStrongholdArmorModel.LAYER, MalignantStrongholdArmorModel::createBodyLayer);
        event.registerLayerDefinition(GenericSlimArmorModel.LAYER, GenericSlimArmorModel::createBodyLayer);
        event.registerLayerDefinition(GenericArmorModel.LAYER, GenericArmorModel::createBodyLayer);

        event.registerLayerDefinition(CommandoArmorModel.LAYER, CommandoArmorModel::createBodyLayer);
        event.registerLayerDefinition(ExecutionerArmorModel.LAYER, ExecutionerArmorModel::createBodyLayer);

        event.registerLayerDefinition(UltrakillMachineArmorModel.LAYER, UltrakillMachineArmorModel::createBodyLayer);

        event.registerLayerDefinition(AncientSoulStainedSteelArmorModel.LAYER, AncientSoulStainedSteelArmorModel::createBodyLayer);
        event.registerLayerDefinition(AncientSoulHunterArmorModel.LAYER, AncientSoulHunterArmorModel::createBodyLayer);

        event.registerLayerDefinition(PridewearArmorModel.LAYER, PridewearArmorModel::createBodyLayer);
        event.registerLayerDefinition(SlimPridewearArmorModel.LAYER, SlimPridewearArmorModel::createBodyLayer);

        event.registerLayerDefinition(TopHatModel.LAYER, TopHatModel::createBodyLayer);
        event.registerLayerDefinition(TailModel.LAYER, TailModel::createBodyLayer);

        event.registerLayerDefinition(HeadOverlayModel.LAYER, HeadOverlayModel::createBodyLayer);
        event.registerLayerDefinition(ScarfModel.LAYER, ScarfModel::createBodyLayer);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.AddLayers event) {
        SOUL_HUNTER_ARMOR = new SoulHunterArmorModel(event.getEntityModels().bakeLayer(SoulHunterArmorModel.LAYER));
        SOUL_STAINED_ARMOR = new SoulStainedSteelArmorModel(event.getEntityModels().bakeLayer(SoulStainedSteelArmorModel.LAYER));
        MALIGNANT_LEAD_ARMOR = new MalignantStrongholdArmorModel(event.getEntityModels().bakeLayer(MalignantStrongholdArmorModel.LAYER));
        GENERIC_SLIM_ARMOR = new GenericSlimArmorModel(event.getEntityModels().bakeLayer(GenericSlimArmorModel.LAYER));
        GENERIC_ARMOR = new GenericArmorModel(event.getEntityModels().bakeLayer(GenericArmorModel.LAYER));

        COMMANDO = new CommandoArmorModel(event.getEntityModels().bakeLayer(CommandoArmorModel.LAYER));
        EXECUTIONER = new ExecutionerArmorModel(event.getEntityModels().bakeLayer(ExecutionerArmorModel.LAYER));

        ULTRAKILL_MACHINE = new UltrakillMachineArmorModel(event.getEntityModels().bakeLayer(UltrakillMachineArmorModel.LAYER));

        ANCIENT_SOUL_HUNTER_ARMOR = new AncientSoulHunterArmorModel(event.getEntityModels().bakeLayer(AncientSoulHunterArmorModel.LAYER));
        ANCIENT_SOUL_STAINED_STEEL_ARMOR = new AncientSoulStainedSteelArmorModel(event.getEntityModels().bakeLayer(AncientSoulStainedSteelArmorModel.LAYER));

        PRIDEWEAR = new PridewearArmorModel(event.getEntityModels().bakeLayer(PridewearArmorModel.LAYER));
        SLIM_PRIDEWEAR = new SlimPridewearArmorModel(event.getEntityModels().bakeLayer(SlimPridewearArmorModel.LAYER));

        TOP_HAT = new TopHatModel(event.getEntityModels().bakeLayer(TopHatModel.LAYER));
        TAIL_MODEL = new TailModel(event.getEntityModels().bakeLayer(TailModel.LAYER));

        HEAD_OVERLAY_MODEL = new HeadOverlayModel(event.getEntityModels().bakeLayer(HeadOverlayModel.LAYER));
        SCARF = new ScarfModel(event.getEntityModels().bakeLayer(ScarfModel.LAYER));
    }
}
