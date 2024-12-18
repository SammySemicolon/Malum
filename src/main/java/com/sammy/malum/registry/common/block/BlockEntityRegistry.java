package com.sammy.malum.registry.common.block;

import com.sammy.malum.*;
import com.sammy.malum.client.renderer.block.*;
import com.sammy.malum.common.block.curiosities.banner.*;
import com.sammy.malum.common.block.curiosities.mana_mote.*;
import com.sammy.malum.common.block.curiosities.obelisk.brilliant.*;
import com.sammy.malum.common.block.curiosities.obelisk.runewood.*;
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
import io.github.fabricators_of_create.porting_lib.util.DeferredHolder;
import io.github.fabricators_of_create.porting_lib.util.DeferredRegister;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.*;

import java.util.*;

import static com.sammy.malum.MalumMod.*;

public class BlockEntityRegistry {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, MALUM);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<VoidConduitBlockEntity>> VOID_CONDUIT = BLOCK_ENTITY_TYPES.register("void_conduit", () -> BlockEntityType.Builder.of(VoidConduitBlockEntity::new, BlockRegistry.VOID_CONDUIT.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<VoidDepotBlockEntity>> VOID_DEPOT = BLOCK_ENTITY_TYPES.register("void_depot", () -> BlockEntityType.Builder.of(VoidDepotBlockEntity::new, BlockRegistry.VOID_DEPOT.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SpiritAltarBlockEntity>> SPIRIT_ALTAR = BLOCK_ENTITY_TYPES.register("spirit_altar", () -> BlockEntityType.Builder.of(SpiritAltarBlockEntity::new, BlockRegistry.SPIRIT_ALTAR.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SpiritJarBlockEntity>> SPIRIT_JAR = BLOCK_ENTITY_TYPES.register("spirit_jar", () -> BlockEntityType.Builder.of(SpiritJarBlockEntity::new, BlockRegistry.SPIRIT_JAR.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<RitualPlinthBlockEntity>> RITUAL_PLINTH = BLOCK_ENTITY_TYPES.register("ritual_plinth", () -> BlockEntityType.Builder.of(RitualPlinthBlockEntity::new, BlockRegistry.RITUAL_PLINTH.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<WeaversWorkbenchBlockEntity>> WEAVERS_WORKBENCH = BLOCK_ENTITY_TYPES.register("weavers_workbench", () -> BlockEntityType.Builder.of(WeaversWorkbenchBlockEntity::new, BlockRegistry.WEAVERS_WORKBENCH.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<RunicWorkbenchBlockEntity>> RUNIC_WORKBENCH = BLOCK_ENTITY_TYPES.register("runic_workbench", () -> BlockEntityType.Builder.of(RunicWorkbenchBlockEntity::new, BlockRegistry.RUNIC_WORKBENCH.get()).build(null));

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

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SoulwovenBannerBlockEntity>> SOULWOVEN_BANNER = BLOCK_ENTITY_TYPES.register("soulwoven_banner", () -> BlockEntityType.Builder.of(SoulwovenBannerBlockEntity::new, getBlocks(SoulwovenBannerBlock.class)).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<MoteOfManaBlockEntity>> SPIRIT_MOTE = BLOCK_ENTITY_TYPES.register("mote_of_mana", () -> BlockEntityType.Builder.of(MoteOfManaBlockEntity::new, getBlocks(SpiritMoteBlock.class)).build(null));

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

    public static class ClientOnly {

        public static void registerRenderer() {
            BlockEntityRenderers.register(VOID_CONDUIT.get(), VoidConduitRenderer::new);
            BlockEntityRenderers.register(VOID_DEPOT.get(), VoidDepotRenderer::new);
            BlockEntityRenderers.register(SPIRIT_ALTAR.get(), SpiritAltarRenderer::new);
            BlockEntityRenderers.register(RUNIC_WORKBENCH.get(), MalumItemHolderRenderer::new);
            BlockEntityRenderers.register(SPIRIT_CRUCIBLE.get(), SpiritCrucibleRenderer::new);
            BlockEntityRenderers.register(SPIRIT_CATALYZER.get(), SpiritCatalyzerRenderer::new);
            BlockEntityRenderers.register(REPAIR_PYLON.get(), RepairPylonRenderer::new);
            BlockEntityRenderers.register(TOTEM_BASE.get(), TotemBaseRenderer::new);
            BlockEntityRenderers.register(TOTEM_POLE.get(), TotemPoleRenderer::new);
            BlockEntityRenderers.register(RITUAL_PLINTH.get(), RitualPlinthRenderer::new);
            BlockEntityRenderers.register(ITEM_STAND.get(), MalumItemHolderRenderer::new);
            BlockEntityRenderers.register(ITEM_PEDESTAL.get(), MalumItemHolderRenderer::new);
            BlockEntityRenderers.register(SPIRIT_JAR.get(), SpiritJarRenderer::new);
            BlockEntityRenderers.register(SPIRIT_MOTE.get(), MoteOfManaRenderer::new);
            BlockEntityRenderers.register(SOULWOVEN_BANNER.get(), SoulwovenBannerRenderer::new);
        }
    }
}
