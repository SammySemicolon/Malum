package com.sammy.malum;


import com.sammy.malum.client.MalumModelLoaderPlugin;
import com.sammy.malum.client.renderer.armor.MalignantStrongholdArmorRenderer;
import com.sammy.malum.client.renderer.armor.SoulHunterArmorRenderer;
import com.sammy.malum.client.renderer.armor.SoulStainedSteelArmorRenderer;
import com.sammy.malum.client.renderer.block.SpiritCrucibleRenderer;
import com.sammy.malum.client.renderer.block.TotemBaseRenderer;
import com.sammy.malum.client.renderer.curio.TokenOfGratitudeRenderer;
import com.sammy.malum.client.renderer.curio.TopHatCurioRenderer;
import com.sammy.malum.client.renderer.item.SpiritJarItemRenderer;
import com.sammy.malum.core.events.ClientRuntimeEvents;
import com.sammy.malum.registry.client.*;
import com.sammy.malum.registry.common.ContainerRegistry;
import com.sammy.malum.registry.common.SpiritTypeRegistry;
import com.sammy.malum.registry.common.block.BlockEntityRegistry;
import com.sammy.malum.registry.common.block.BlockRegistry;
import com.sammy.malum.registry.common.entity.EntityRegistry;
import com.sammy.malum.registry.common.item.ItemRegistry;
import com.terraformersmc.terraform.sign.api.SpriteIdentifierRegistry;
import dev.emi.trinkets.api.client.TrinketRendererRegistry;
import io.github.fabricators_of_create.porting_lib.entity.events.EntityJoinLevelEvent;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.resources.model.Material;
import net.minecraft.network.chat.Component;

import static com.sammy.malum.registry.common.block.BlockRegistry.*;

public class MalumModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientTickEvents.START_CLIENT_TICK.register(this::startTick);

        ParticleRegistry.registerParticleFactory();
        BlockEntityRegistry.ClientOnly.registerRenderer();
        BlockRegistry.ClientOnly.setBlockColors();
        EntityRegistry.ClientOnly.bindEntityRenderers();
        ItemRegistry.ClientOnly.addItemProperties();
        ItemRegistry.ClientOnly.setItemColors();
        ContainerRegistry.ClientOnly.bindContainerRenderers();

        ShaderRegistry.shaderRegistry();
        ClientRuntimeEvents.clientTickEvent();
        ClientRuntimeEvents.itemTooltipEvent();

        EntityJoinLevelEvent.EVENT.register(ParticleEmitterRegistry::addParticleEmitters);

        TrinketRendererRegistry.registerRenderer(ItemRegistry.TOPHAT.get(), new TopHatCurioRenderer());
        TrinketRendererRegistry.registerRenderer(ItemRegistry.TOKEN_OF_GRATITUDE.get(), new TokenOfGratitudeRenderer());
        ModelRegistry.registerLayerDefinitions();

        BuiltinItemRendererRegistry.INSTANCE.register(SPIRIT_JAR.get(), new SpiritJarItemRenderer());
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderType.translucent(),
                SPIRIT_JAR.get(),
                BlockRegistry.ETHER_TORCH.get(),
                BlockRegistry.IRIDESCENT_ETHER_TORCH.get(),
                BlockRegistry.IRIDESCENT_WALL_ETHER_TORCH.get(),
                BlockRegistry.WALL_ETHER_TORCH.get(),
                BlockRegistry.TAINTED_ETHER_BRAZIER.get(),
                BlockRegistry.TAINTED_IRIDESCENT_ETHER_BRAZIER.get(),
                BlockRegistry.TWISTED_ETHER_BRAZIER.get(),
                BlockRegistry.TWISTED_IRIDESCENT_ETHER_BRAZIER.get(),

                AERIAL_SPIRITED_GLASS.get(),
                INFERNAL_SPIRITED_GLASS.get(),
                EARTHEN_SPIRITED_GLASS.get(),
                AQUEOUS_SPIRITED_GLASS.get(),
                ELDRITCH_SPIRITED_GLASS.get(),
                SACRED_SPIRITED_GLASS.get(),
                WICKED_SPIRITED_GLASS.get(),
                ARCANE_SPIRITED_GLASS.get()
        );

        SpriteIdentifierRegistry.INSTANCE.addIdentifier(new Material(Sheets.SIGN_SHEET, RUNEWOOD_SIGN.getId()));
        SpriteIdentifierRegistry.INSTANCE.addIdentifier(new Material(Sheets.SIGN_SHEET, SOULWOOD_SIGN.getId()));


        BlockRenderLayerMap.INSTANCE.putBlocks(RenderType.cutoutMipped(),
                BlockRegistry.HANGING_SOULWOOD_LEAVES.get(),
                BlockRegistry.HANGING_RUNEWOOD_LEAVES.get(),
                BlockRegistry.HANGING_AZURE_RUNEWOOD_LEAVES.get()
        );

        BlockRenderLayerMap.INSTANCE.putBlocks(RenderType.cutout(),
                BlockRegistry.CTHONIC_GOLD_CLUSTER.get(),
                BlockRegistry.BLAZING_QUARTZ_CLUSTER.get(),
                BlockRegistry.NATURAL_QUARTZ_CLUSTER.get(),
                BlockRegistry.BLAZING_QUARTZ_ORE.get(),
                BlockRegistry.BRILLIANT_STONE.get(),
                BlockRegistry.BRILLIANT_DEEPSLATE.get(),
                BlockRegistry.RUNEWOOD_SAPLING.get(),
                BlockRegistry.SOULWOOD_GROWTH.get(),
                BlockRegistry.AZURE_RUNEWOOD_SAPLING.get(),

                BLIGHTED_GROWTH.get(),
                CLINGING_BLIGHT.get(),
                CALCIFIED_BLIGHT.get(),
                TALL_CALCIFIED_BLIGHT.get(),
                SOULWOOD_DOOR.get(),
                SOULWOOD_TRAPDOOR.get(),
                RUNEWOOD_DOOR.get(),
                RUNEWOOD_TRAPDOOR.get(),
                SOULWOOD_LEAVES.get(),
                BUDDING_SOULWOOD_LEAVES.get()
        );

        ArmorRenderer.register(new SoulHunterArmorRenderer(),
                ItemRegistry.SOUL_HUNTER_CLOAK.get(),
                ItemRegistry.SOUL_HUNTER_ROBE.get(),
                ItemRegistry.SOUL_HUNTER_LEGGINGS.get(),
                ItemRegistry.SOUL_HUNTER_BOOTS.get()
        );

        ArmorRenderer.register(new SoulStainedSteelArmorRenderer(),
                ItemRegistry.SOUL_STAINED_STEEL_HELMET.get(),
                ItemRegistry.SOUL_STAINED_STEEL_CHESTPLATE.get(),
                ItemRegistry.SOUL_STAINED_STEEL_LEGGINGS.get(),
                ItemRegistry.SOUL_STAINED_STEEL_BOOTS.get()
        );

        ArmorRenderer.register(new MalignantStrongholdArmorRenderer(),
                ItemRegistry.MALIGNANT_STRONGHOLD_HELMET.get(),
                ItemRegistry.MALIGNANT_STRONGHOLD_CHESTPLATE.get(),
                ItemRegistry.MALIGNANT_STRONGHOLD_LEGGINGS.get(),
                ItemRegistry.MALIGNANT_STRONGHOLD_BOOTS.get()
        );

        ModelLoadingPlugin.register(new MalumModelLoaderPlugin());


        FabricLoader.getInstance().getModContainer(MalumMod.MALUM).ifPresent(container ->
                ResourceManagerHelper.registerBuiltinResourcePack(
                        MalumMod.malumPath("chibi_sprites"),
                        container,
                        Component.translatable("chibi_sprites"),
                        ResourcePackActivationType.NORMAL
                )
        );

        HiddenTagRegistry.registerHiddenTags();
    }

    private void startTick(Minecraft minecraft) {
        SpiritCrucibleRenderer.checkForTuningFork(minecraft);
        TotemBaseRenderer.checkForTotemicStaff(minecraft);
    }
}
