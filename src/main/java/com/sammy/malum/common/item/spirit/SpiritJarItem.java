package com.sammy.malum.common.item.spirit;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.sammy.malum.client.renderer.item.SpiritJarItemRenderer;
import com.sammy.malum.core.handlers.*;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import com.sammy.malum.registry.common.item.DataComponentRegistry;
import io.netty.buffer.ByteBuf;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

import java.util.List;

public class SpiritJarItem extends BlockItem {
    public SpiritJarItem(Block pBlock, Properties pProperties) {
        super(pBlock, pProperties);
    }

    @Override
    public String getDescriptionId(ItemStack pStack) {
        if (pStack.has(DataComponentRegistry.SPIRIT_JAR_CONTENTS)) {
            return "item.malum.filled_spirit_jar";
        }
        return super.getDescriptionId(pStack);
    }

//    @Override
//    public void readComponent(int stackQuantity, DataComponentMap.Builder mutableMap, ComponentGetter originalSupplier) {
//        originalSupplier.get(DataComponentRegistry.SPIRIT_JAR_CONTENTS).ifPresent(contents -> {
//            if (originalSupplier.get(DataComponents.RARITY).isEmpty())
//                mutableMap.set(DataComponents.RARITY, SpiritHarvestHandler.getSpiritType(contents.spirit()).getItemRarity());
//        });
//    }

    @Override
    public void appendHoverText(ItemStack pStack, TooltipContext context, List<Component> pTooltip, TooltipFlag tooltipFlag) {
        if (pStack.has(DataComponentRegistry.SPIRIT_JAR_CONTENTS)) {
            Contents contents = pStack.get(DataComponentRegistry.SPIRIT_JAR_CONTENTS);
            MalumSpiritType spirit = SpiritHarvestHandler.getSpiritType(contents.spirit());
            pTooltip.add(Component.translatable("malum.spirit.description.stored_spirit").withStyle(ChatFormatting.GRAY));
            pTooltip.add(spirit.getSpiritJarCounterComponent(contents.count()));
        }
    }

    public static IClientItemExtensions CUSTOM_RENDERER = new IClientItemExtensions() {
        BlockEntityWithoutLevelRenderer renderer = new SpiritJarItemRenderer(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());

        @Override
        public BlockEntityWithoutLevelRenderer getCustomRenderer() {
            return this.renderer;
        }
    };

    public record Contents(String spirit, int count) {
        public static Codec<Contents> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Codec.STRING.fieldOf("spirit").forGetter(Contents::spirit),
                Codec.INT.fieldOf("count").forGetter(Contents::count)
        ).apply(instance, Contents::new));

        public static StreamCodec<ByteBuf, Contents> STREAM_CODEC = ByteBufCodecs.fromCodec(Contents.CODEC);
    }
}
