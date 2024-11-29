package com.sammy.malum.common.item.disc;

import com.sammy.malum.registry.common.SoundRegistry;
import net.minecraft.world.item.*;

public class ArcaneElegyMusicDiscItem extends Item {

    public ArcaneElegyMusicDiscItem(Properties builder) {
        super(builder.jukeboxPlayable(SoundRegistry.ARCANE_ELEGY_KEY));
    }
}