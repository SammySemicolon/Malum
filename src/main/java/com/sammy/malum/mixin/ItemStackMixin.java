package com.sammy.malum.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.sammy.malum.common.item.curiosities.weapons.scythe.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;

import static net.minecraft.world.item.Item.BASE_ATTACK_DAMAGE_ID;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

    @Shadow public abstract Item getItem();

    @ModifyVariable(method = "addAttributeTooltips", at = @At("STORE"))
    private ItemAttributeModifiers malum$getTooltip(ItemAttributeModifiers map, @Local(argsOnly = true) Player player) {
        if (player != null && getItem() instanceof MalumScytheItem) {
            ItemAttributeModifiers.Builder copied = ItemAttributeModifiers.builder();
            for (ItemAttributeModifiers.Entry entry : map.modifiers()) {
                Holder<Attribute> key = entry.attribute();
                AttributeModifier modifier = entry.modifier();
                double amount = modifier.amount();

                if (modifier.id().equals(BASE_ATTACK_DAMAGE_ID)) {
                    AttributeInstance instance = player.getAttribute(AttributeRegistry.SCYTHE_PROFICIENCY);
                    if (instance != null && instance.getValue() > 0) {
                        amount += instance.getValue() * 0.5f;
                    }

                    copied.add(key, new AttributeModifier(
                            modifier.id(), amount, modifier.operation()
                    ), entry.slot());
                } else {
                    copied.add(key, modifier, entry.slot());
                }


            }

            return copied.build();
        }
        return map;
    }
}
