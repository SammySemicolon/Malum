package com.sammy.malum.common.effect.aura;

import com.sammy.malum.*;
import com.sammy.malum.registry.common.MobEffectRegistry;
import com.sammy.malum.registry.common.SpiritTypeRegistry;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import team.lodestar.lodestone.helpers.ColorHelper;

public class AqueousAura extends MobEffect {
    public AqueousAura() {
        super(MobEffectCategory.BENEFICIAL, ColorHelper.getColor(SpiritTypeRegistry.AQUEOUS_SPIRIT.getPrimaryColor()));
        addAttributeModifier(Attributes.BLOCK_INTERACTION_RANGE, MalumMod.malumPath("aqueous_aura_block_range"), 1f, AttributeModifier.Operation.ADD_VALUE);
        addAttributeModifier(Attributes.ENTITY_INTERACTION_RANGE, MalumMod.malumPath("aqueous_aura_entity_range"), 1f, AttributeModifier.Operation.ADD_VALUE);
    }

    public static AABB growBoundingBox(Player player, AABB original) {
        MobEffectInstance effect = player.getEffect(MobEffectRegistry.POSEIDONS_GRASP);
        if (effect != null) {
            original = original.inflate((effect.getAmplifier() + 1) * 1.5f);
        }
        return original;
    }
}