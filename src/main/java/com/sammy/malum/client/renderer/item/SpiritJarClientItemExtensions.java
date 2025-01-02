package com.sammy.malum.client.renderer.item;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

/**
 * @author WireSegal
 * Created at 10:09 PM on 1/1/25.
 */
public class SpiritJarClientItemExtensions implements IClientItemExtensions {
	private BlockEntityWithoutLevelRenderer renderer;

	@Override
	public BlockEntityWithoutLevelRenderer getCustomRenderer() {
		if (renderer == null) {
			renderer = new SpiritJarItemRenderer(Minecraft.getInstance().getBlockEntityRenderDispatcher(),
				Minecraft.getInstance().getEntityModels());
		}
		return renderer;
	}
}
