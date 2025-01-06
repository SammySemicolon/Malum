package com.sammy.malum.registry.common.block;

import com.sammy.malum.*;
import com.sammy.malum.client.renderer.block.*;
import com.sammy.malum.client.renderer.block.artifice.SpiritCatalyzerRenderer;
import com.sammy.malum.client.renderer.block.artifice.SpiritCrucibleRenderer;
import com.sammy.malum.client.renderer.block.redstone.WaveMakerRenderer;
import com.sammy.malum.client.renderer.block.redstone.WaveChargerRenderer;
import com.sammy.malum.client.renderer.block.redstone.WavebankerRenderer;
import com.sammy.malum.client.renderer.block.redstone.WaveBreakerRenderer;
import com.sammy.malum.common.block.curiosities.banner.*;
import com.sammy.malum.common.block.curiosities.mana_mote.*;
import com.sammy.malum.common.block.curiosities.obelisk.brilliant.*;
import com.sammy.malum.common.block.curiosities.obelisk.runewood.*;
import com.sammy.malum.common.block.curiosities.redstone.wavemaker.WaveMakerBlock;
import com.sammy.malum.common.block.curiosities.redstone.wavemaker.WaveMakerBlockEntity;
import com.sammy.malum.common.block.curiosities.redstone.wavebanker.WaveBankerBlock;
import com.sammy.malum.common.block.curiosities.redstone.wavebanker.WaveBankerBlockEntity;
import com.sammy.malum.common.block.curiosities.redstone.wavecharger.WaveChargerBlock;
import com.sammy.malum.common.block.curiosities.redstone.wavecharger.WaveChargerBlockEntity;
import com.sammy.malum.common.block.curiosities.redstone.wavebreaker.WaveBreakerBlock;
import com.sammy.malum.common.block.curiosities.redstone.wavebreaker.WaveBreakerBlockEntity;
import com.sammy.malum.common.block.curiosities.repair_pylon.*;
import com.sammy.malum.common.block.curiosities.ritual_plinth.*;
import com.sammy.malum.common.block.curiosities.runic_workbench.*;
import com.sammy.malum.common.block.curiosities.spirit_altar.*;
import com.sammy.malum.common.block.curiosities.spirit_crucible.*;
import com.sammy.malum.common.block.curiosities.spirit_crucible.catalyzer.*;
import com.sammy.malum.common.block.curiosities.totem.*;
import com.sammy.malum.common.block.curiosities.void_depot.*;
import com.sammy.malum.common.block.curiosities.weavers_workbench.*;
import com.sammy.malum.common.block.curiosities.weeping_well.*;
import com.sammy.malum.common.block.ether.*;
import com.sammy.malum.common.block.storage.jar.*;
import com.sammy.malum.common.block.storage.pedestal.*;
import com.sammy.malum.common.block.storage.stand.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.*;

import static com.sammy.malum.MalumMod.*;

public class BlockEntityRegistry {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, MALUM);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<VoidConduitBlockEntity>> VOID_CONDUIT = BLOCK_ENTITY_TYPES.register("void_conduit", () -> BlockEntityType.Builder.of(VoidConduitBlockEntity::new, BlockRegistry.VOID_CONDUIT.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<VoidDepotBlockEntity>> VOID_DEPOT = BLOCK_ENTITY_TYPES.register("void_depot", () -> BlockEntityType.Builder.of(VoidDepotBlockEntity::new, BlockRegistry.VOID_DEPOT.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SpiritAltarBlockEntity>> SPIRIT_ALTAR = BLOCK_ENTITY_TYPES.register("spirit_altar", () -> BlockEntityType.Builder.of(SpiritAltarBlockEntity::new, BlockRegistry.SPIRIT_ALTAR.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SpiritJarBlockEntity>> SPIRIT_JAR = BLOCK_ENTITY_TYPES.register("spirit_jar", () -> BlockEntityType.Builder.of(SpiritJarBlockEntity::new, BlockRegistry.SPIRIT_JAR.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<RunicWorkbenchBlockEntity>> RUNIC_WORKBENCH = BLOCK_ENTITY_TYPES.register("runic_workbench", () -> BlockEntityType.Builder.of(RunicWorkbenchBlockEntity::new, BlockRegistry.RUNIC_WORKBENCH.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<WeaversWorkbenchBlockEntity>> WEAVERS_WORKBENCH = BLOCK_ENTITY_TYPES.register("weavers_workbench", () -> BlockEntityType.Builder.of(WeaversWorkbenchBlockEntity::new, BlockRegistry.WEAVERS_WORKBENCH.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SpiritCrucibleCoreBlockEntity>> SPIRIT_CRUCIBLE = BLOCK_ENTITY_TYPES.register("spirit_crucible", () -> BlockEntityType.Builder.of(SpiritCrucibleCoreBlockEntity::new, BlockRegistry.SPIRIT_CRUCIBLE.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SpiritCatalyzerCoreBlockEntity>> SPIRIT_CATALYZER = BLOCK_ENTITY_TYPES.register("spirit_catalyzer", () -> BlockEntityType.Builder.of(SpiritCatalyzerCoreBlockEntity::new, BlockRegistry.SPIRIT_CATALYZER.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<RepairPylonCoreBlockEntity>> REPAIR_PYLON = BLOCK_ENTITY_TYPES.register("repair_pylon", () -> BlockEntityType.Builder.of(RepairPylonCoreBlockEntity::new, BlockRegistry.REPAIR_PYLON.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<RunewoodObeliskBlockEntity>> RUNEWOOD_OBELISK = BLOCK_ENTITY_TYPES.register("runewood_obelisk", () -> BlockEntityType.Builder.of(RunewoodObeliskBlockEntity::new, BlockRegistry.RUNEWOOD_OBELISK.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<BrilliantObeliskBlockEntity>> BRILLIANT_OBELISK = BLOCK_ENTITY_TYPES.register("brilliant_obelisk", () -> BlockEntityType.Builder.of(BrilliantObeliskBlockEntity::new, BlockRegistry.BRILLIANT_OBELISK.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<EtherBlockEntity>> ETHER = BLOCK_ENTITY_TYPES.register("ether", () -> BlockEntityType.Builder.of(EtherBlockEntity::new, getBlocks(EtherBlock.class)).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ItemStandBlockEntity>> ITEM_STAND = BLOCK_ENTITY_TYPES.register("item_stand", () -> BlockEntityType.Builder.of(ItemStandBlockEntity::new, getBlocks(ItemStandBlock.class)).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ItemPedestalBlockEntity>> ITEM_PEDESTAL = BLOCK_ENTITY_TYPES.register("item_pedestal", () -> BlockEntityType.Builder.of(ItemPedestalBlockEntity::new, getBlocks(ItemPedestalBlock.class)).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TotemBaseBlockEntity>> TOTEM_BASE = BLOCK_ENTITY_TYPES.register("totem_base", () -> BlockEntityType.Builder.of(TotemBaseBlockEntity::new, getBlocks(TotemBaseBlock.class)).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TotemPoleBlockEntity>> TOTEM_POLE = BLOCK_ENTITY_TYPES.register("totem_pole", () -> BlockEntityType.Builder.of(TotemPoleBlockEntity::new, getBlocks(TotemPoleBlock.class)).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<WaveChargerBlockEntity>> WAVECHARGER = BLOCK_ENTITY_TYPES.register("wavecharger", () -> BlockEntityType.Builder.of(WaveChargerBlockEntity::new, getBlocks(WaveChargerBlock.class)).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<WaveBankerBlockEntity>> WAVEBANKER = BLOCK_ENTITY_TYPES.register("wavebanker", () -> BlockEntityType.Builder.of(WaveBankerBlockEntity::new, getBlocks(WaveBankerBlock.class)).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<WaveMakerBlockEntity>> WAVEMAKER = BLOCK_ENTITY_TYPES.register("wavemaker", () -> BlockEntityType.Builder.of(WaveMakerBlockEntity::new, getBlocks(WaveMakerBlock.class)).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<WaveBreakerBlockEntity>> WAVEBREAKER = BLOCK_ENTITY_TYPES.register("wavebreaker", () -> BlockEntityType.Builder.of(WaveBreakerBlockEntity::new, getBlocks(WaveBreakerBlock.class)).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<RitualPlinthBlockEntity>> RITUAL_PLINTH = BLOCK_ENTITY_TYPES.register("ritual_plinth", () -> BlockEntityType.Builder.of(RitualPlinthBlockEntity::new, BlockRegistry.RITUAL_PLINTH.get()).build(null));


    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SoulwovenBannerBlockEntity>> SOULWOVEN_BANNER = BLOCK_ENTITY_TYPES.register("soulwoven_banner", () -> BlockEntityType.Builder.of(SoulwovenBannerBlockEntity::new, getBlocks(SoulwovenBannerBlock.class)).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ManaMoteBlockEntity>> MANA_MOTE = BLOCK_ENTITY_TYPES.register("mote_of_mana", () -> BlockEntityType.Builder.of(ManaMoteBlockEntity::new, getBlocks(ManaMoteBlock.class)).build(null));

    public static Block[] getBlocks(Class<?>... blockClasses) {
        Collection<DeferredHolder<Block, ? extends Block>> blocks = BlockRegistry.BLOCKS.getEntries();
        List<Block> matchingBlocks = new ArrayList<>();
        for (DeferredHolder<Block, ? extends Block> registryObject : blocks) {
            if (registryObject.isBound() && Arrays.stream(blockClasses).anyMatch(b -> b.isInstance(registryObject.get()))) {
                matchingBlocks.add(registryObject.get());
            }
        }
        return matchingBlocks.toArray(new Block[0]);
    }

    public static Block[] getBlocksExact(Class<?> clazz) {
        Collection<DeferredHolder<Block, ? extends Block>> blocks = BlockRegistry.BLOCKS.getEntries();
        List<Block> matchingBlocks = new ArrayList<>();
        for (DeferredHolder<Block, ? extends Block> registryObject : blocks) {
            if (clazz.equals(registryObject.get().getClass())) {
                matchingBlocks.add(registryObject.get());
            }
        }
        return matchingBlocks.toArray(new Block[0]);
    }

    @EventBusSubscriber(modid = MalumMod.MALUM, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
    public static class ClientOnly {
        @SubscribeEvent
        public static void registerRenderer(EntityRenderersEvent.RegisterRenderers event) {
            event.registerBlockEntityRenderer(VOID_CONDUIT.get(), VoidConduitRenderer::new);
            event.registerBlockEntityRenderer(VOID_DEPOT.get(), VoidDepotRenderer::new);

            event.registerBlockEntityRenderer(SPIRIT_ALTAR.get(), SpiritAltarRenderer::new);
            event.registerBlockEntityRenderer(RUNIC_WORKBENCH.get(), MalumItemHolderRenderer::new);
            event.registerBlockEntityRenderer(SPIRIT_JAR.get(), SpiritJarRenderer::new);

            event.registerBlockEntityRenderer(SPIRIT_CRUCIBLE.get(), SpiritCrucibleRenderer::new);
            event.registerBlockEntityRenderer(SPIRIT_CATALYZER.get(), SpiritCatalyzerRenderer::new);
            event.registerBlockEntityRenderer(REPAIR_PYLON.get(), RepairPylonRenderer::new);

            event.registerBlockEntityRenderer(TOTEM_BASE.get(), TotemBaseRenderer::new);
            event.registerBlockEntityRenderer(TOTEM_POLE.get(), TotemPoleRenderer::new);

            event.registerBlockEntityRenderer(WAVECHARGER.get(), WaveChargerRenderer::new);
            event.registerBlockEntityRenderer(WAVEBANKER.get(), WavebankerRenderer::new);
            event.registerBlockEntityRenderer(WAVEMAKER.get(), WaveMakerRenderer::new);
            event.registerBlockEntityRenderer(WAVEBREAKER.get(), WaveBreakerRenderer::new);

            event.registerBlockEntityRenderer(RITUAL_PLINTH.get(), RitualPlinthRenderer::new);

            event.registerBlockEntityRenderer(ITEM_STAND.get(), MalumItemHolderRenderer::new);
            event.registerBlockEntityRenderer(ITEM_PEDESTAL.get(), MalumItemHolderRenderer::new);



            event.registerBlockEntityRenderer(MANA_MOTE.get(), MoteOfManaRenderer::new);
            event.registerBlockEntityRenderer(SOULWOVEN_BANNER.get(), SoulwovenBannerRenderer::new);
        }
    }
}
