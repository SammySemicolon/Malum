package com.sammy.malum.common.geas;

import com.sammy.malum.core.handlers.*;
import com.sammy.malum.core.systems.geas.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.sounds.*;
import net.minecraft.util.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.neoforged.neoforge.common.*;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.event.tick.*;
import team.lodestar.lodestone.helpers.*;

public class WyrdReconstructionGeas extends GeasEffect {

    private static final int COOLDOWN_DURATION = 48000;
    public int spiritCollectionActivations;
    public int cooldown;

    public WyrdReconstructionGeas() {
        super(MalumGeasEffectTypeRegistry.PACT_OF_WYRD_RECONSTRUCTION.get());
    }

    @Override
    public void update(EntityTickEvent.Pre event, LivingEntity entity) {
        if (cooldown > 0) {
            if (spiritCollectionActivations > 0) {
                if (cooldown % 10 == 0) {
                    SoulHarvestHandler.triggerSpiritCollection(entity);
                    SoundHelper.playSound(entity, SoundRegistry.SPIRIT_PICKUP.get(), 0.3f, Mth.nextFloat(entity.getRandom(), 1.2f, 1.5f));
                    spiritCollectionActivations--;
                }
            }
            cooldown--;
        }
    }

    @Override
    public void incomingDeathEvent(LivingDeathEvent event, LivingEntity attacker, LivingEntity target, ItemStack stack) {
        var source = event.getSource();
        if (source.is(Tags.DamageTypes.IS_TECHNICAL)) {
            return;
        }
        if (source.is(DamageTypeRegistry.VOID)) {
            return;
        }
        if (cooldown > 0) {
            return;
        }
        event.setCanceled(true);
        target.setHealth(1);
        cooldown = COOLDOWN_DURATION;
        spiritCollectionActivations = 3;
    }
}