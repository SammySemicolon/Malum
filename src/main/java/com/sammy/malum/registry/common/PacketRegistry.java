package com.sammy.malum.registry.common;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.packets.*;
import com.sammy.malum.common.packets.particle.rite.*;
import com.sammy.malum.common.packets.particle.rite.generic.BlockSparkleParticlePacket;
import com.sammy.malum.common.packets.particle.rite.generic.MajorEntityEffectParticlePacket;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import team.lodestar.lodestone.registry.common.LodestoneNetworkPayloads;

@SuppressWarnings("unused")
@EventBusSubscriber(modid = MalumMod.MALUM, bus = EventBusSubscriber.Bus.MOD)
public class PacketRegistry {

    public static LodestoneNetworkPayloads.PayloadRegistryHelper MALUM_CHANNEL = new LodestoneNetworkPayloads.PayloadRegistryHelper(MalumMod.MALUM);

    @SubscribeEvent
    public static void registerNetworkStuff(RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("1");

        //functionality
        MALUM_CHANNEL.playToClient(registrar, "void_rejection", VoidRejectionPacket.class, VoidRejectionPacket::new);
        MALUM_CHANNEL.playToClient(registrar, "sync_staff_cooldown_changes", SyncStaffCooldownChangesPacket.class, SyncStaffCooldownChangesPacket::new);
        MALUM_CHANNEL.playToClient(registrar, "particle_effect", ParticleEffectPacket.class, ParticleEffectPacket::new);
        MALUM_CHANNEL.playToClient(registrar, "major_entity_effect", MajorEntityEffectParticlePacket.class, MajorEntityEffectParticlePacket::new);
        MALUM_CHANNEL.playToClient(registrar, "sacred_mist_rite_effect", SacredMistRiteEffectPacket.class, SacredMistRiteEffectPacket::new);
        MALUM_CHANNEL.playToClient(registrar, "block_sparkle_particle", BlockSparkleParticlePacket.class, BlockSparkleParticlePacket::new);
        MALUM_CHANNEL.playToClient(registrar, "infernal_acceleration_rite_effect", InfernalAccelerationRiteEffectPacket.class, InfernalAccelerationRiteEffectPacket::new);
        MALUM_CHANNEL.playToClient(registrar, "infernal_extinguish_rite_effect", InfernalExtinguishRiteEffectPacket.class, InfernalExtinguishRiteEffectPacket::new);
        MALUM_CHANNEL.playToClient(registrar, "aerial_block_fall_rite_effect", AerialBlockFallRiteEffectPacket.class, AerialBlockFallRiteEffectPacket::new);
        MALUM_CHANNEL.playToClient(registrar, "spirit_rite_activation", SpiritRiteActivationEffectPacket.class, SpiritRiteActivationEffectPacket::new);

        MALUM_CHANNEL.playToClient(registrar, "sync_malum_player_capability", SyncMalumPlayerCapabilityDataPacket.class, SyncMalumPlayerCapabilityDataPacket::new);
        MALUM_CHANNEL.playToClient(registrar, "sync_living_capability_data", SyncLivingCapabilityDataPacket.class, SyncLivingCapabilityDataPacket::new);

        MALUM_CHANNEL.playToClient(registrar, "blight_mist_particle", BlockSparkleParticlePacket.class, BlockSparkleParticlePacket::new);
        MALUM_CHANNEL.playToClient(registrar, "blight_transformation_item_particle", BlightTransformItemParticlePacket.class, BlightTransformItemParticlePacket::new);
    }
}