package com.sammy.malum;

import com.sammy.malum.compability.farmersdelight.*;
import com.sammy.malum.compability.tetra.*;
import com.sammy.malum.config.*;
import com.sammy.malum.registry.common.item.tabs.*;
import net.minecraft.resources.*;
import net.minecraft.util.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
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
@Mod(MalumMod.MALUM)
public class MalumMod {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MALUM = "malum";
    public static final RandomSource RANDOM = RandomSource.create();

    public MalumMod() {
        IEventBus modBus = ModLoadingContext.get().getActiveContainer().getEventBus();

        ModLoadingContext.get().getActiveContainer().registerConfig(ModConfig.Type.CLIENT, ClientConfig.SPEC);
        ModLoadingContext.get().getActiveContainer().registerConfig(ModConfig.Type.COMMON, CommonConfig.SPEC);

        BLOCKS.register(modBus);
        BLOCK_ENTITY_TYPES.register(modBus);
        COMPONENTS.register(modBus);
        ITEMS.register(modBus);
        ENTITY_TYPES.register(modBus);
        EFFECTS.register(modBus);
        PARTICLES.register(modBus);
        SOUNDS.register(modBus);
        CONTAINERS.register(modBus);
        ATTRIBUTES.register(modBus);
        RECIPE_TYPES.register(modBus);
        RECIPE_SERIALIZERS.register(modBus);
        FEATURE_TYPES.register(modBus);
        STRUCTURES.register(modBus);
        CREATIVE_MODE_TABS.register(modBus);

        TetraCompat.init();
        FarmersDelightCompat.init();

        modBus.addListener(CreativeTabRegistry::populateItemGroups);
    }

    public static ResourceLocation malumPath(String path) {
        return ResourceLocation.fromNamespaceAndPath(MALUM, path);
    }


}
