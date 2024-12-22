package com.sammy.malum.common.block.curiosities.mana_mote;

import com.sammy.malum.core.systems.spirit.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.neoforged.neoforge.client.extensions.common.IClientBlockExtensions;
import team.lodestar.lodestone.systems.block.*;

import java.util.function.*;

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

    @Override
    public void initializeClient(Consumer<IClientBlockExtensions> consumer) {
        consumer.accept(new ManaMoteBlockClientExtension());
    }
}
