package com.sammy.malum.registry.common.entity;

import com.sammy.malum.MalumMod;
import com.sammy.malum.client.renderer.entity.*;
import com.sammy.malum.client.renderer.entity.bolt.*;
import com.sammy.malum.client.renderer.entity.nitrate.*;
import com.sammy.malum.client.renderer.entity.scythe.*;
import com.sammy.malum.common.entity.activator.*;
import com.sammy.malum.common.entity.bolt.*;
import com.sammy.malum.common.entity.boomerang.ScytheBoomerangEntity;
import com.sammy.malum.common.entity.hidden_blade.*;
import com.sammy.malum.common.entity.nitrate.EthericNitrateEntity;
import com.sammy.malum.common.entity.nitrate.VividNitrateEntity;
import com.sammy.malum.common.entity.spirit.SpiritItemEntity;
import com.sammy.malum.common.entity.thrown.*;
import com.sammy.malum.registry.common.item.ItemRegistry;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import team.lodestar.lodestone.systems.entity.LodestoneBoatEntity;

public class EntityRegistry {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, MalumMod.MALUM);

    public static final DeferredHolder<EntityType<?>, EntityType<LodestoneBoatEntity>> RUNEWOOD_BOAT = ENTITY_TYPES.register("runewood_boat",
            () -> EntityType.Builder.<LodestoneBoatEntity>of((t, w) -> new LodestoneBoatEntity(t, w, ItemRegistry.RUNEWOOD_BOAT, ItemRegistry.RUNEWOOD_PLANKS), MobCategory.MISC).sized(1.375F, 0.5625F).clientTrackingRange(10)
                    .build(MalumMod.malumPath("runewood_boat").toString()));

    public static final DeferredHolder<EntityType<?>, EntityType<LodestoneBoatEntity>> SOULWOOD_BOAT = ENTITY_TYPES.register("soulwood_boat",
            () -> EntityType.Builder.<LodestoneBoatEntity>of((t, w) -> new LodestoneBoatEntity(t, w, ItemRegistry.SOULWOOD_BOAT, ItemRegistry.SOULWOOD_PLANKS), MobCategory.MISC).sized(1.375F, 0.5625F).clientTrackingRange(10)
                    .build(MalumMod.malumPath("soulwood_boat").toString()));

    public static final DeferredHolder<EntityType<?>, EntityType<SpiritItemEntity>> NATURAL_SPIRIT = ENTITY_TYPES.register("natural_spirit",
            () -> EntityType.Builder.<SpiritItemEntity>of((e, w) -> new SpiritItemEntity(w), MobCategory.MISC).sized(0.5F, 0.75F).clientTrackingRange(10)
                    .build(MalumMod.malumPath("natural_spirit").toString()));

    public static final DeferredHolder<EntityType<?>, EntityType<EthericNitrateEntity>> ETHERIC_NITRATE = ENTITY_TYPES.register("etheric_nitrate",
            () -> EntityType.Builder.<EthericNitrateEntity>of((e, w) -> new EthericNitrateEntity(w), MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(20)
                    .build(MalumMod.malumPath("etheric_nitrate").toString()));

    public static final DeferredHolder<EntityType<?>, EntityType<VividNitrateEntity>> VIVID_NITRATE = ENTITY_TYPES.register("vivid_nitrate",
            () -> EntityType.Builder.<VividNitrateEntity>of((e, w) -> new VividNitrateEntity(w), MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(20)
                    .build(MalumMod.malumPath("vivid_nitrate").toString()));

    public static final DeferredHolder<EntityType<?>, EntityType<ScytheBoomerangEntity>> SCYTHE_BOOMERANG = ENTITY_TYPES.register("scythe_boomerang",
            () -> EntityType.Builder.<ScytheBoomerangEntity>of((e, w) -> new ScytheBoomerangEntity(w), MobCategory.MISC).sized(2f, 2f).clientTrackingRange(20)
                    .build(MalumMod.malumPath("scythe_boomerang").toString()));

    public static final DeferredHolder<EntityType<?>, EntityType<SpiritCollectionActivatorEntity>> SPIRIT_COLLECTION_ACTIVATOR = ENTITY_TYPES.register("pneuma_void",
            () -> EntityType.Builder.<SpiritCollectionActivatorEntity>of((e, w) -> new SpiritCollectionActivatorEntity(w), MobCategory.MISC).sized(1f, 1f).clientTrackingRange(10)
                    .build(MalumMod.malumPath("pneuma_void").toString()));

    public static final DeferredHolder<EntityType<?>, EntityType<HiddenBladeDelayedImpactEntity>> HIDDEN_BLADE_DELAYED_IMPACT = ENTITY_TYPES.register("hidden_blade_delayed_impact",
            () -> EntityType.Builder.<HiddenBladeDelayedImpactEntity>of((e, w) -> new HiddenBladeDelayedImpactEntity(w), MobCategory.MISC).sized(8F, 8F).clientTrackingRange(10)
                    .build(MalumMod.malumPath("hidden_blade_delayed_impact").toString()));

    public static final DeferredHolder<EntityType<?>, EntityType<ThrownConcentratedGluttony>> THROWN_GLUTTONY = ENTITY_TYPES.register("thrown_gluttony",
            () -> EntityType.Builder.<ThrownConcentratedGluttony>of((e, w) -> new ThrownConcentratedGluttony(w), MobCategory.MISC).sized(0.25f, 0.25f).clientTrackingRange(10)
                    .build(MalumMod.malumPath("thrown_gluttony").toString()));

    public static final DeferredHolder<EntityType<?>, EntityType<HexBoltEntity>> HEX_BOLT = ENTITY_TYPES.register("hex_bolt",
            () -> EntityType.Builder.<HexBoltEntity>of((e, w) -> new HexBoltEntity(w), MobCategory.MISC).sized(1.25F, 1.25F).clientTrackingRange(10)
                    .build(MalumMod.malumPath("hex_bolt").toString()));

    public static final DeferredHolder<EntityType<?>, EntityType<DrainingBoltEntity>> DRAINING_BOLT = ENTITY_TYPES.register("draining_bolt",
            () -> EntityType.Builder.<DrainingBoltEntity>of((e, w) -> new DrainingBoltEntity(w), MobCategory.MISC).sized(1.5F, 1.5f).clientTrackingRange(10)
                    .build(MalumMod.malumPath("draining_bolt").toString()));

    public static final DeferredHolder<EntityType<?>, EntityType<AuricFlameBoltEntity>> AURIC_FLAME_BOLT = ENTITY_TYPES.register("auric_flame_bolt",
            () -> EntityType.Builder.<AuricFlameBoltEntity>of((e, w) -> new AuricFlameBoltEntity(w), MobCategory.MISC).sized(2F, 2F).clientTrackingRange(10)
                    .build(MalumMod.malumPath("auric_flame_bolt").toString()));

    @EventBusSubscriber(modid = MalumMod.MALUM, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
    public static class ClientOnly {
        @SubscribeEvent
        public static void bindEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
            EntityRenderers.register(EntityRegistry.RUNEWOOD_BOAT.get(), (manager) -> new MalumBoatRenderer(manager, "runewood", false));
            EntityRenderers.register(EntityRegistry.SOULWOOD_BOAT.get(), (manager) -> new MalumBoatRenderer(manager, "soulwood", false));
            EntityRenderers.register(EntityRegistry.NATURAL_SPIRIT.get(), FloatingItemEntityRenderer::new);
            EntityRenderers.register(EntityRegistry.SCYTHE_BOOMERANG.get(), ScytheBoomerangEntityRenderer::new);

            EntityRenderers.register(EntityRegistry.ETHERIC_NITRATE.get(), EthericNitrateEntityRenderer::new);
            EntityRenderers.register(EntityRegistry.VIVID_NITRATE.get(), VividNitrateEntityRenderer::new);

            EntityRenderers.register(EntityRegistry.SPIRIT_COLLECTION_ACTIVATOR.get(), SpiritCollectionActivatorEntityRenderer::new);
            EntityRenderers.register(EntityRegistry.HIDDEN_BLADE_DELAYED_IMPACT.get(), NoopRenderer::new);

            EntityRenderers.register(EntityRegistry.THROWN_GLUTTONY.get(), ThrownConcentratedGluttonyRenderer::new);

            EntityRenderers.register(EntityRegistry.HEX_BOLT.get(), HexBoltEntityRenderer::new);
            EntityRenderers.register(EntityRegistry.DRAINING_BOLT.get(), DrainingBoltEntityRenderer::new);
            EntityRenderers.register(EntityRegistry.AURIC_FLAME_BOLT.get(), AuricFlameBoltEntityRenderer::new);
        }
    }
}
