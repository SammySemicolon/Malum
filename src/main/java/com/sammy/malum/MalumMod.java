package com.sammy.malum;

import com.sammy.malum.compability.attributelib.*;
import com.sammy.malum.compability.farmersdelight.*;
import com.sammy.malum.config.*;
import com.sammy.malum.core.events.RuntimeEvents;
import com.sammy.malum.core.events.SetupEvents;
import com.sammy.malum.core.listeners.MalignantConversionReloadListener;
import com.sammy.malum.core.listeners.ReapingDataReloadListener;
import com.sammy.malum.core.listeners.RitualRecipeReloadListener;
import com.sammy.malum.core.listeners.SpiritDataReloadListener;
import com.sammy.malum.data.worldgen.BiomeModifications;
import com.sammy.malum.registry.client.HiddenTagRegistry;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.item.ArmorSkinRegistry;
import com.sammy.malum.registry.common.item.ItemRegistry;
import com.sammy.malum.registry.common.item.tabs.CreativeTabRegistry;
import io.github.fabricators_of_create.porting_lib.config.ConfigRegistry;
import io.github.fabricators_of_create.porting_lib.config.ModConfig;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.resources.*;
import net.minecraft.server.packs.PackType;
import net.minecraft.util.*;
import org.apache.logging.log4j.*;


import static com.sammy.malum.registry.client.ParticleRegistry.*;
import static com.sammy.malum.registry.common.AttributeRegistry.*;
import static com.sammy.malum.registry.common.ContainerRegistry.*;
import static com.sammy.malum.registry.common.MobEffectRegistry.*;
import static com.sammy.malum.registry.common.SoundRegistry.*;
import static com.sammy.malum.registry.common.block.BlockEntityRegistry.*;
import static com.sammy.malum.registry.common.block.BlockRegistry.*;
import static com.sammy.malum.registry.common.entity.EntityRegistry.*;
import static com.sammy.malum.registry.common.item.DataComponentRegistry.*;
import static com.sammy.malum.registry.common.item.ItemRegistry.*;
import static com.sammy.malum.registry.common.item.tabs.CreativeTabRegistry.*;
import static com.sammy.malum.registry.common.recipe.RecipeSerializerRegistry.*;
import static com.sammy.malum.registry.common.recipe.RecipeTypeRegistry.*;
import static com.sammy.malum.registry.common.worldgen.FeatureRegistry.*;
import static com.sammy.malum.registry.common.worldgen.StructureRegistry.*;

@SuppressWarnings("unused")
public class MalumMod implements ModInitializer {


    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MALUM = "malum";
    public static final RandomSource RANDOM = RandomSource.create();

    @Override
    public void onInitialize() {

        ConfigRegistry.registerConfig(MalumMod.MALUM, ModConfig.Type.CLIENT, ClientConfig.SPEC);
        ConfigRegistry.registerConfig(MalumMod.MALUM, ModConfig.Type.COMMON, CommonConfig.SPEC);
        AttachmentTypeRegistry.register();
        COMPONENTS.register();
        SOUNDS.register();
        BLOCKS.register();
        BLOCK_ENTITY_TYPES.register();

        ITEMS.register();
        ENTITY_TYPES.register();
        EFFECTS.register();
        PARTICLES.register();
        CONTAINERS.register();
        ATTRIBUTES.register();
        RECIPE_TYPES.register();
        RECIPE_SERIALIZERS.register();
        FEATURE_TYPES.register();
        STRUCTURES.register();
        CREATIVE_MODE_TABS.register();
        PacketRegistry.registerNetworkStuff();
        WorldEventTypes.TYPES.register();
        ItemGroupEvents.MODIFY_ENTRIES_ALL.register(HiddenTagRegistry::hideItems);
        MobEffectRegistry.registerBrewingRecipes();

        RuntimeEvents.register();
        FarmersDelightCompat.init();
        AttributeLibCompat.init();

        BiomeModifications.init();
        ItemRegistry.Common.registerCompost();
        ArmorSkinRegistry.registerItemSkins();

        ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener(new SpiritDataReloadListenerFabricImpl());
        ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener(new ReapingDataReloadListenerFabricImpl());
        ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener(new MalignantConversionReloadListenerFabricImpl());
        ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener(new RitualRecipeReloadListenerFabricImpl());

        CreativeTabRegistry.populateItemGroups();
        SetupEvents.buildCreativeTabs();
    }

    public static ResourceLocation malumPath(String path) {
        return ResourceLocation.fromNamespaceAndPath(MALUM, path);
    }

    public static class SpiritDataReloadListenerFabricImpl extends SpiritDataReloadListener implements IdentifiableResourceReloadListener {

        @Override
        public ResourceLocation getFabricId() {
            return malumPath("spirit_data");
        }
    }

    public static class ReapingDataReloadListenerFabricImpl extends ReapingDataReloadListener implements IdentifiableResourceReloadListener {

        @Override
        public ResourceLocation getFabricId() {
            return malumPath("reaping_data");
        }
    }

    public static class MalignantConversionReloadListenerFabricImpl extends MalignantConversionReloadListener implements IdentifiableResourceReloadListener {

        @Override
        public ResourceLocation getFabricId() {
            return malumPath("malignant_conversion_data");
        }
    }

    public static class RitualRecipeReloadListenerFabricImpl extends RitualRecipeReloadListener implements IdentifiableResourceReloadListener {

        @Override
        public ResourceLocation getFabricId() {
            return malumPath("ritual_recipes");
        }
    }
}