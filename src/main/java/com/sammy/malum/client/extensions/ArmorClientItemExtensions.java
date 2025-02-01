package com.sammy.malum.client.extensions;

import com.sammy.malum.client.cosmetic.ArmorSkinRenderingData;
import com.sammy.malum.common.data_components.*;
import com.sammy.malum.registry.common.item.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import team.lodestar.lodestone.systems.model.LodestoneArmorModel;

import java.util.function.Supplier;

/**
 * @author WireSegal
 * Created at 10:05 PM on 1/1/25.
 */
public class ArmorClientItemExtensions implements IClientItemExtensions {
	private final Supplier<LodestoneArmorModel> model;

	public ArmorClientItemExtensions(Supplier<LodestoneArmorModel> model) {
		this.model = model;
	}

	@Override
	public LodestoneArmorModel getHumanoidArmorModel(LivingEntity entity, ItemStack itemStack, EquipmentSlot armorSlot, HumanoidModel _default) {
		float pticks = (float) (Minecraft.getInstance().getFrameTimeNs() / 20000000000L);
		float f = Mth.rotLerp(pticks, entity.yBodyRotO, entity.yBodyRot);
		float f1 = Mth.rotLerp(pticks, entity.yHeadRotO, entity.yHeadRot);
		float netHeadYaw = f1 - f;
		float netHeadPitch = Mth.lerp(pticks, entity.xRotO, entity.getXRot());
		LodestoneArmorModel model = this.model.get();
		ItemSkinComponent skin = itemStack.get(DataComponentRegistry.APPLIED_ITEM_SKIN);
		if (skin != null) {
			if (ArmorSkinRenderingData.ARMOR_RENDERING_DATA.containsKey(skin)) {
				model = ArmorSkinRenderingData.ARMOR_RENDERING_DATA.get(skin).getModel(entity);
			}
		}
		model.slot = armorSlot;
		model.copyFromDefault(_default);

//                if (model instanceof MalignantStrongholdArmorModel malignantStrongholdArmorModel) {
//                    final LazyOptional<ICuriosItemHandler> curiosInventory = CuriosApi.getCuriosInventory(entity);
//                    if (curiosInventory.isPresent()) {
//                        final List<AbstractRuneCurioItem> equippedRunes = curiosInventory
//                                .map(i -> i.findCurios(s -> s.getItem() instanceof AbstractRuneCurioItem))
//                                .map(l -> l.stream()
//                                        .filter(c -> c.slotContext().visible())
//                                        .map(c -> (AbstractRuneCurioItem) c.stack().getItem()).collect(Collectors.toList()))
//                                .orElse(Collections.emptyList());
//                        malignantStrongholdArmorModel.updateGlow(equippedRunes);
//                    }
//                }

		model.setupAnim(entity, entity.walkAnimation.position(), entity.walkAnimation.speed(), entity.tickCount + pticks, netHeadYaw, netHeadPitch);
		return model;
	}
}
