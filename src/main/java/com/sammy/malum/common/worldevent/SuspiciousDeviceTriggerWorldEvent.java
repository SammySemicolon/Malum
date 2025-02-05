package com.sammy.malum.common.worldevent;

import com.sammy.malum.common.block.blight.*;
import com.sammy.malum.common.block.curiosities.totem.*;
import com.sammy.malum.core.systems.spirit.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.block.*;
import net.minecraft.core.*;
import net.minecraft.server.level.*;
import net.minecraft.sounds.*;
import net.minecraft.util.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.block.state.properties.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.helpers.block.*;

import static com.sammy.malum.registry.common.block.BlockRegistry.*;

public class SuspiciousDeviceTriggerWorldEvent extends ActiveBlightWorldEvent {
    public SuspiciousDeviceTriggerWorldEvent() {
        super(WorldEventTypeRegistry.UNCHAINED_TOTEM_CONVERSION.get());
    }
}