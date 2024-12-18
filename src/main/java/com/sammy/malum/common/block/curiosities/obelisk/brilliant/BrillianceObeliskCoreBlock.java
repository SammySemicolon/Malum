package com.sammy.malum.common.block.curiosities.obelisk.brilliant;

import com.sammy.malum.common.block.curiosities.obelisk.*;
import com.sammy.malum.registry.common.block.BlockEntityRegistry;
import io.github.fabricators_of_create.porting_lib.enchant.CustomEnchantingBehaviorItem;
import io.github.fabricators_of_create.porting_lib.enchant.EnchantmentBonusBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;

public class BrillianceObeliskCoreBlock extends ObeliskCoreBlock<BrilliantObeliskBlockEntity> implements EnchantmentBonusBlock {
    public BrillianceObeliskCoreBlock(Properties properties) {
        super(properties, BlockEntityRegistry.BRILLIANT_OBELISK);
    }

    @Override
    public float getEnchantPowerBonus(BlockState state, LevelReader world, BlockPos pos) {
        return 5;
    }
}
