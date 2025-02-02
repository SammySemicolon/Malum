package com.sammy.malum.client.extensions;

import com.sammy.malum.client.scarf.*;
import com.sammy.malum.common.data_components.*;
import com.sammy.malum.registry.client.*;
import com.sammy.malum.registry.common.item.*;
import net.minecraft.client.model.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import team.lodestar.lodestone.systems.model.*;
import team.lodestar.lodestone.systems.rendering.rendeertype.*;

import java.awt.*;
import java.util.function.*;

import static com.sammy.malum.MalumMod.malumPath;

public class MalignantArmorItemExtensions extends ArmorClientItemExtensions {
    public MalignantArmorItemExtensions(Supplier<LodestoneArmorModel> model) {
        super(model);
    }

    @Override
    public LodestoneArmorModel getHumanoidArmorModel(LivingEntity entity, ItemStack itemStack, EquipmentSlot armorSlot, HumanoidModel _default) {
        final LodestoneArmorModel model = super.getHumanoidArmorModel(entity, itemStack, armorSlot, _default);
        if (armorSlot.equals(EquipmentSlot.CHEST)) {
            final ItemSkinComponent skin = itemStack.get(DataComponentRegistry.APPLIED_ITEM_SKIN);
            RenderTypeToken scarfToken = skin != null ?
                    RenderTypeToken.createToken(skin.name().withPrefix("textures/vfx/scarf/").withSuffix(".png"))
                    : MalumRenderTypeTokens.SCARF;
            ScarfRenderHandler.addScarfRenderer(entity,
                    l -> {
                        var data = new ScarfRenderHandler.ScarfRenderData(scarfToken, 40).setScale(0.4f);
                        if (skin == null) {
                            data.setPrimaryColor(new Color(183, 45, 69)).setSecondaryColor(new Color(126, 25, 95));
                        }
                        return data;
                    }
            );

        }
        return model;
    }
}