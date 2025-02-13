package com.sammy.malum.common.item.curiosities.curios.sets.scythe;

import com.google.common.collect.*;
import com.sammy.malum.*;
import com.sammy.malum.common.item.curiosities.curios.*;
import com.sammy.malum.common.item.curiosities.weapons.scythe.*;
import com.sammy.malum.core.helpers.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.core.*;
import net.minecraft.core.particles.*;
import net.minecraft.core.registries.*;
import net.minecraft.network.chat.*;
import net.minecraft.server.level.*;
import net.minecraft.sounds.*;
import net.minecraft.tags.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import team.lodestar.lodestone.helpers.*;
import top.theillusivec4.curios.api.*;

import java.util.*;
import java.util.function.*;

public class CurioRisingEdgeRing extends MalumCurioItem {
    private static final ExplosionDamageCalculator EXPLOSION_DAMAGE_CALCULATOR = new SimpleExplosionDamageCalculator(
            true, false, Optional.of(0.5f), BuiltInRegistries.BLOCK.getTag(BlockTags.BLOCKS_WIND_CHARGE_EXPLOSIONS).map(Function.identity())
    );

    public CurioRisingEdgeRing(Properties builder) {
        super(builder, MalumTrinketType.METALLIC);
    }

    @Override
    public void addExtraTooltipLines(Consumer<Component> consumer) {
        consumer.accept(ComponentHelper.positiveCurioEffect("ascension_launch"));
        consumer.accept(ComponentHelper.negativeCurioEffect("lower_ascension_damage"));
    }

    public static void launchEntity(LivingEntity attacker, LivingEntity target) {
        float velocity = 0.5f;
        if (MalumScytheItem.isNarrow(attacker)) {
            velocity += 0.3f;
        }
        attacker.level().explode(
                attacker,
                null,
                EXPLOSION_DAMAGE_CALCULATOR,
                target.position().x(),
                target.position().y(),
                target.position().z(),
                1.2f,
                false,
                Level.ExplosionInteraction.TRIGGER,
                ParticleTypes.GUST,
                ParticleTypes.GUST,
                SoundRegistry.SCYTHE_ASCENSION_LAUNCH
        );
        target.setDeltaMovement(target.getDeltaMovement().add(0, velocity, 0));
    }
}