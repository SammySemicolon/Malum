package com.sammy.malum.common.block.curiosities.mana_mote;

import com.sammy.malum.core.systems.spirit.SpiritTypeProperty;
import com.sammy.malum.registry.common.SpiritTypeRegistry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import team.lodestar.lodestone.systems.block.LodestoneEntityBlock;

public class ManaMoteBlock extends LodestoneEntityBlock<ManaMoteBlockEntity> {

    public static final SpiritTypeProperty SPIRIT_TYPE = SpiritTypeRegistry.SPIRIT_TYPE_PROPERTY;

    public ManaMoteBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(SPIRIT_TYPE, "sacred"));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(SPIRIT_TYPE);
    }
}
