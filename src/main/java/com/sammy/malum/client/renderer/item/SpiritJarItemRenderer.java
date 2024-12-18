package com.sammy.malum.client.renderer.item;

import com.mojang.blaze3d.vertex.PoseStack;
import com.sammy.malum.common.block.storage.jar.SpiritJarBlockEntity;
import com.sammy.malum.common.item.spirit.SpiritJarItem;
import com.sammy.malum.core.systems.spirit.*;
import com.sammy.malum.registry.common.block.BlockRegistry;
import com.sammy.malum.registry.common.item.DataComponentRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class SpiritJarItemRenderer implements BuiltinItemRendererRegistry.DynamicItemRenderer {

    private final SpiritJarBlockEntity jar = new SpiritJarBlockEntity(BlockPos.ZERO, BlockRegistry.SPIRIT_JAR.get().defaultBlockState());


    public SpiritJarItemRenderer() {

    }

    @Override
    public void render(ItemStack itemStack, ItemDisplayContext itemDisplayContext, PoseStack poseStack, MultiBufferSource multiBufferSource, int pPackedLight, int pPackedOverlay) {
        if (itemStack.getItem() instanceof SpiritJarItem) {
            if (itemStack.has(DataComponentRegistry.SPIRIT_JAR_CONTENTS.get())) {
                SpiritJarItem.Contents contents = itemStack.get(DataComponentRegistry.SPIRIT_JAR_CONTENTS.get());
                jar.type = MalumSpiritType.getSpiritType(contents.spirit());
                jar.count = contents.count();

                Minecraft.getInstance().getBlockEntityRenderDispatcher().renderItem(jar, poseStack, multiBufferSource, pPackedLight, pPackedOverlay);
            }
        }
    }
}
