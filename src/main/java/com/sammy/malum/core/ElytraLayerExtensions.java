package com.sammy.malum.core;

import com.sammy.malum.client.renderer.curio.TokenOfGratitudeRenderer;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public interface ElytraLayerExtensions<T> {
    default boolean shouldRender(ItemStack stack, T entity) {
        return stack.getItem() == Items.ELYTRA;
    }

    default ResourceLocation getElytraTexture(ItemStack stack, T entity) {
        if (entity instanceof Player player) {
            ResourceLocation tex = TokenOfGratitudeRenderer.getElytraTexture(player.getUUID(), ElytraLayer.WINGS_LOCATION);
            return tex;
        }
        return ElytraLayer.WINGS_LOCATION;
    }
}
