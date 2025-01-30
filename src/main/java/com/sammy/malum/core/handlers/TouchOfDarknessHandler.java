package com.sammy.malum.core.handlers;

import com.sammy.malum.*;
import com.sammy.malum.client.*;
import com.sammy.malum.common.block.curiosities.weeping_well.*;
import com.sammy.malum.common.capabilities.*;
import com.sammy.malum.common.packets.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.item.*;
import com.sammy.malum.visual_effects.networked.data.*;
import net.minecraft.core.*;
import net.minecraft.resources.*;
import net.minecraft.server.level.*;
import net.minecraft.sounds.*;
import net.minecraft.util.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import net.neoforged.neoforge.event.tick.*;
import net.neoforged.neoforge.network.*;
import team.lodestar.lodestone.helpers.*;

import java.util.*;

import static com.sammy.malum.client.VoidRevelationHandler.RevelationType.*;

public class TouchOfDarknessHandler {

    public static void handlePrimordialSoupContact(LivingEntity livingEntity) {
        livingEntity.getData(AttachmentTypeRegistry.TOUCH_OF_DARKNESS.get()).setAfflictionLevel(100);
    }

    public static void entityTick(EntityTickEvent.Pre event) {
        if (event.getEntity() instanceof LivingEntity livingEntity) {
            livingEntity.getData(AttachmentTypeRegistry.TOUCH_OF_DARKNESS).update(livingEntity);
        }
    }
}