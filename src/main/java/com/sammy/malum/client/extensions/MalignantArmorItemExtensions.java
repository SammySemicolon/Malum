package com.sammy.malum.client.extensions;

import com.sammy.malum.client.scarf.*;
import net.minecraft.client.model.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import team.lodestar.lodestone.systems.model.*;

import java.awt.*;
import java.util.function.*;

public class MalignantArmorItemExtensions extends ArmorClientItemExtensions{
    public MalignantArmorItemExtensions(Supplier<LodestoneArmorModel> model) {
        super(model);
    }

    @Override
    public LodestoneArmorModel getHumanoidArmorModel(LivingEntity entity, ItemStack itemStack, EquipmentSlot armorSlot, HumanoidModel _default) {
        final LodestoneArmorModel model = super.getHumanoidArmorModel(entity, itemStack, armorSlot, _default);
        if (armorSlot.equals(EquipmentSlot.CHEST)) {
            ScarfRenderHandler.addScarfRenderer(entity, l ->
                    new ScarfRenderHandler.ScarfRenderData(40)
                            .setPrimaryColor(new Color(183, 45, 69))
                            .setSecondaryColor(new Color(126, 25, 95))
                            .setScale(0.4f)
            );
        }
        return model;
    }
}
