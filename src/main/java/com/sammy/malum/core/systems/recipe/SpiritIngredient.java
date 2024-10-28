package com.sammy.malum.core.systems.recipe;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import com.sammy.malum.core.systems.spirit.*;
import com.sammy.malum.registry.common.recipe.*;
import net.minecraft.core.component.*;
import net.minecraft.core.registries.*;
import net.minecraft.resources.*;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.common.crafting.*;

import java.util.stream.*;

public class SpiritIngredient implements ICustomIngredient {

    public static final MapCodec<SpiritIngredient> CODEC = RecordCodecBuilder.mapCodec(
            builder -> builder
                    .group(
                            MalumSpiritType.CODEC.fieldOf("type").forGetter(SpiritIngredient::getSpiritType),
                            Codec.INT.fieldOf("count").forGetter(SpiritIngredient::getCount))
                    .apply(builder, SpiritIngredient::new));

    protected final MalumSpiritType spiritType;
    protected final int count;

    public SpiritIngredient(MalumSpiritType spiritType, int count) {
        this.spiritType = spiritType;
        this.count = count;
    }

    public MalumSpiritType getSpiritType() {
        return spiritType;
    }

    public int getCount() {
        return count;
    }

    @Override
    public boolean test(ItemStack itemStack) {
        return spiritType.test(itemStack) && itemStack.getCount() >= count;
    }

    @Override
    public Stream<ItemStack> getItems() {
        return Stream.of(getStack());
    }

    public ItemStack getStack() {
        return new ItemStack(spiritType.getSpiritShard(), count);
    }

    @Override
    public boolean isSimple() {
        return true;
    }

    @Override
    public IngredientType<?> getType() {
        return IngredientTypeRegistry.SPIRIT.get();
    }
}
