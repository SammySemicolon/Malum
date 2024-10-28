package com.sammy.malum.common.item.curiosities.weapons.scythe;

import com.google.common.collect.ImmutableMultimap;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Tier;
import team.lodestar.lodestone.registry.common.LodestoneAttributeRegistry;
import team.lodestar.lodestone.registry.common.LodestoneAttributes;

public class MagicScytheItem extends MalumScytheItem {

    public final float magicDamage;

    public MagicScytheItem(Tier tier, float attackDamageIn, float attackSpeedIn, float magicDamage, Properties builderIn) {
        super(tier, attackDamageIn, attackSpeedIn, builderIn);
        this.magicDamage = magicDamage;
    }

    @Override
    public ImmutableMultimap.Builder<Attribute, AttributeModifier> createExtraAttributes() {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = new ImmutableMultimap.Builder<>();
        builder.put(
                LodestoneAttributes.MAGIC_DAMAGE.get(),
                new AttributeModifier(
                        LodestoneAttributes.MAGIC_DAMAGE.getId(),
                        magicDamage,
                        AttributeModifier.Operation.ADD_VALUE)
        );
        return builder;
    }
}
