package com.sammy.malum.common.block.curiosities.banner;

import com.sammy.malum.common.item.*;
import com.sammy.malum.registry.common.block.*;
import net.minecraft.core.*;
import net.minecraft.nbt.*;
import net.minecraft.resources.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.state.*;
import team.lodestar.lodestone.systems.blockentity.*;

public class SoulwovenBannerBlockEntity extends LodestoneBlockEntity {

    public SoulwovenBannerBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.SOULWOVEN_BANNER.get(), pos, state);
    }
}
