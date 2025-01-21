package com.sammy.malum.common.item.curiosities.weapons;

import com.sammy.malum.*;
import com.sammy.malum.common.item.*;
import com.sammy.malum.common.item.curiosities.weapons.staff.*;
import com.sammy.malum.common.worldevent.*;
import com.sammy.malum.core.helpers.*;
import com.sammy.malum.core.systems.spirit.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.block.*;
import com.sammy.malum.visual_effects.networked.attack.slash.*;
import net.minecraft.core.*;
import net.minecraft.core.component.*;
import net.minecraft.tags.*;
import net.minecraft.util.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.*;
import net.minecraft.world.item.enchantment.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.neoforged.neoforge.event.entity.living.*;
import team.lodestar.lodestone.handlers.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.registry.common.*;
import team.lodestar.lodestone.registry.common.tag.*;
import team.lodestar.lodestone.systems.item.*;

import java.util.*;

public class SunderingAnchorItem extends ModCombatItem implements IMalumEventResponderItem, ISpiritAffiliatedItem {

    public final float magicDamage;

    public SunderingAnchorItem(Tier tier, float magicDamage, Properties builderIn) {
        super(tier, 1, -2f, builderIn.component(DataComponents.TOOL, createToolProperties(tier, BlockTagRegistry.MINEABLE_WITH_KNIFE)));
        this.magicDamage = magicDamage;
    }

    @Override
    public boolean supportsEnchantment(ItemStack stack, Holder<Enchantment> enchantment) {
        if (enchantment.equals(Enchantments.BREACH)) {
            return true;
        }
        return super.supportsEnchantment(stack, enchantment);
    }

    @Override
    public MalumSpiritType getDefiningSpiritType() {
        var spirits = new MalumSpiritType[]{SpiritTypeRegistry.INFERNAL_SPIRIT, SpiritTypeRegistry.SACRED_SPIRIT, SpiritTypeRegistry.AQUEOUS_SPIRIT, SpiritTypeRegistry.EARTHEN_SPIRIT};
        return spirits[MalumMod.RANDOM.nextInt(spirits.length)];
    }

    @Override
    public ItemAttributeModifiers.Builder createExtraAttributes() {
        var builder = ItemAttributeModifiers.builder();
        builder.add(LodestoneAttributes.MAGIC_DAMAGE, new AttributeModifier(LodestoneAttributes.MAGIC_DAMAGE.getId(), magicDamage, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
        return builder;
    }

    @Override
    public void outgoingDamageEvent(LivingDamageEvent.Pre event, LivingEntity attacker, LivingEntity target, ItemStack stack) {
        DamageSource source = event.getSource();
        Level level = attacker.level();
        RandomSource random = level.random;
        if (source.is(LodestoneDamageTypeTags.CAN_TRIGGER_MAGIC)) {
            var spiritType = getDefiningSpiritType();
            int slashCount = 1 + Mth.floor(random.nextFloat() * 5);
            float splitDamage = event.getOriginalDamage() / slashCount;
            if (target.isAlive()) {
                for (int i = 0; i < slashCount; i++) {
                    WorldEventHandler.addWorldEvent(level,
                            new DelayedDamageWorldEvent()
                                    .setData(attacker.getUUID(), target.getUUID(), 0, splitDamage, i * 2)
                                    .setSound(SoundRegistry.SUNDERING_ANCHOR_EXTRA_SWING, 0.5f, 2f, 1f));
                }
            }
            event.setNewDamage(splitDamage);

            float pitch = RandomHelper.randomBetween(level.getRandom(), 0.75f, 2f);
            SoundHelper.playSound(attacker, SoundRegistry.SUNDERING_ANCHOR_SWING.get(), 2f, pitch);
            var particle = ParticleHelper.createEffect(ParticleEffectTypeRegistry.SUNDERING_ANCHOR_SLASH,
                            (d, b) -> SunderingAnchorSlashParticleEffect.createData(d, b.isMirrored, b.slashAngle, slashCount, b.spiritType))
                    .setSpiritType(spiritType)
                    .setPositionOffset(RandomHelper.randomBetween(random, 0.2f, 0.6f) * (random.nextBoolean() ? 1 : -1))
                    .setRandomSlashAngle(random);
            particle.spawnTargetBoundSlashingParticle(attacker, target);
        }
    }

    public static Tool createToolProperties(Tier tier, TagKey<Block> blocks) {
        return new Tool(List.of(Tool.Rule.minesAndDrops(List.of(Blocks.COBWEB), 15.0F),
                Tool.Rule.overrideSpeed(BlockTags.SWORD_EFFICIENT, 1.5F),
                Tool.Rule.deniesDrops(tier.getIncorrectBlocksForDrops()), Tool.Rule.minesAndDrops(blocks, tier.getSpeed())), 1.0F, 2);
    }
}
