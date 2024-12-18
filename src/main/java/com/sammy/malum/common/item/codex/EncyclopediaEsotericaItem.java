package com.sammy.malum.common.item.codex;

import com.sammy.malum.client.screen.codex.screens.*;
import com.sammy.malum.visual_effects.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;

import team.lodestar.lodestone.handlers.screenparticle.*;
import team.lodestar.lodestone.systems.particle.screen.*;

public class EncyclopediaEsotericaItem extends EncyclopediaArcanaItem implements ParticleEmitterHandler.ItemParticleSupplier {

    public static boolean shouldOpenVoidCodex;

    public EncyclopediaEsotericaItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (level.isClientSide) {
            final ItemStack stack = player.getItemInHand(hand);
            if (shouldOpenVoidCodex) {
                VoidProgressionScreen.openCodexViaItem();
            } else {
                ArcanaProgressionScreen.openCodexViaItem(true);
            }
            player.swing(hand);
            return InteractionResultHolder.success(stack);
        }
        return super.use(level, player, hand);
    }

    
    @Override
    public void spawnLateParticles(ScreenParticleHolder target, Level level, float partialTick, ItemStack stack, float x, float y) {
        ScreenParticleEffects.spawnEncyclopediaEsotericaScreenParticles(target, level, partialTick);
    }
}