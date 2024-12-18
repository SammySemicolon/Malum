package com.sammy.malum.common.block.ether;

import com.sammy.malum.common.item.ether.*;
import com.sammy.malum.registry.client.*;
import com.sammy.malum.registry.common.block.*;
import com.sammy.malum.visual_effects.*;
import net.minecraft.core.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.phys.*;
import team.lodestar.lodestone.handlers.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.helpers.block.*;
import team.lodestar.lodestone.registry.common.particle.*;
import team.lodestar.lodestone.systems.blockentity.*;
import team.lodestar.lodestone.systems.easing.*;
import team.lodestar.lodestone.systems.particle.*;
import team.lodestar.lodestone.systems.particle.builder.*;
import team.lodestar.lodestone.systems.particle.data.*;
import team.lodestar.lodestone.systems.particle.data.color.*;
import team.lodestar.lodestone.systems.particle.data.spin.*;

import java.awt.*;

public class EtherBlockEntity extends LodestoneBlockEntity {
    public Color firstColor;
    public Color secondColor;

    public EtherBlockEntity(BlockEntityType<? extends EtherBlockEntity> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public EtherBlockEntity(BlockPos pos, BlockState state) {
        this(BlockEntityRegistry.ETHER.get(), pos, state);
    }

    public void setFirstColor(int rgb) {
        firstColor = new Color(rgb);
    }

    public void setSecondColor(int rgb) {
        secondColor = new Color(rgb);
    }

    @Override
    protected void loadAdditional(CompoundTag compound, HolderLookup.Provider registries) {
        setFirstColor(compound.contains("firstColor") ? compound.getInt("firstColor") : EtherItem.DEFAULT_FIRST_COLOR.rgb());
        if (getBlockState().getBlock().asItem() instanceof AbstractEtherItem etherItem && etherItem.iridescent) {
            setSecondColor(compound.contains("secondColor") ? compound.getInt("secondColor") : EtherItem.DEFAULT_SECOND_COLOR.rgb());
        }
        super.loadAdditional(compound, registries);
    }

    @Override
    protected void saveAdditional(CompoundTag compound, HolderLookup.Provider registries) {
        if (firstColor != null) {
            compound.putInt("firstColor", firstColor.getRGB());
        }
        if (getBlockState().getBlock().asItem() instanceof AbstractEtherItem etherItem && etherItem.iridescent) {
            if (secondColor != null && secondColor.getRGB() != EtherItem.DEFAULT_SECOND_COLOR.rgb()) {
                compound.putInt("secondColor", secondColor.getRGB());
            }
        }
        super.saveAdditional(compound, registries);
    }

    @Override
    public void onPlace(LivingEntity placer, ItemStack stack) {
        AbstractEtherItem item = (AbstractEtherItem) stack.getItem();
        setFirstColor(item.getFirstColor(stack));
        if (item.iridescent) {
            setSecondColor(item.getSecondColor(stack));
        }
    }

    @Override
    public ItemStack onClone(BlockState state, BlockGetter level, BlockPos pos) {
        ItemStack stack = state.getBlock().asItem().getDefaultInstance();
        AbstractEtherItem etherItem = (AbstractEtherItem) stack.getItem();
        if (firstColor != null) {
            etherItem.setFirstColor(stack, firstColor.getRGB());
        }
        if (secondColor != null) {
            etherItem.setSecondColor(stack, secondColor.getRGB());
        }
        return super.onClone(state, level, pos);
    }

    @Override
    public void init() {
        if (!level.isClientSide) {
            BlockStateHelper.updateState(level, worldPosition);
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (level.isClientSide) {
            if (firstColor == null) {
                return;
            }
            final RandomSource random = level.random;
            Block block = getBlockState().getBlock();
            Color firstColor = ColorHelper.darker(this.firstColor, 1);
            Color secondColor = this.secondColor == null ? firstColor : ColorHelper.brighter(this.secondColor, 1);
            double x = worldPosition.getX() + 0.5f;
            double y = worldPosition.getY() + 0.5f;
            double z = worldPosition.getZ() + 0.5f;

            if (block instanceof EtherWallTorchBlock) {
                final float offset = 0.15f;
                Direction direction = getBlockState().getValue(WallTorchBlock.FACING);
                x -= direction.getNormal().getX() * offset;
                y += 0.4f;
                z -= direction.getNormal().getZ() * offset;
            } else if (block instanceof EtherTorchBlock) {
                y += 0.3f;
            } else if (block instanceof EtherBrazierBlock) {
                y -= 0.05f;
            }

            final ColorParticleData colorData = ColorParticleData.create(firstColor, secondColor).setCoefficient(1.5f).setEasing(Easing.BOUNCE_IN_OUT).build();
            if (level.getGameTime() % 8L == 0) {
                int lifeTime = RandomHelper.randomBetween(random, 40, 60);
                float scale = RandomHelper.randomBetween(random, 0.6f, 0.7f);
                float velocity = RandomHelper.randomBetween(random, 0.02f, 0.03f);
                var lightSpecs = SpiritLightSpecs.spiritLightSpecs(level, new Vec3(x, y, z), colorData);
                lightSpecs.getBuilder()
                        .setRenderTarget(RenderHandler.LATE_DELAYED_RENDER)
                        .setLifetime(lifeTime)
                        .setScaleData(GenericParticleData.create(scale, 0).setEasing(Easing.SINE_IN_OUT).build())
                        .setTransparencyData(GenericParticleData.create(0.3f, 0.6f, 0).build())
                        .addMotion(0, velocity * 1.2f, 0);
                lightSpecs.spawnParticlesRaw();
            }

            if (level.getGameTime() % 2L == 0) {
                int lifeTime = RandomHelper.randomBetween(random, 12, 14);
                float scale = RandomHelper.randomBetween(random, 0.16f, 0.2f);
                float velocity = RandomHelper.randomBetween(random, 0.02f, 0.03f);
                WorldParticleBuilder.create(LodestoneParticleTypes.WISP_PARTICLE)
                        .setRenderTarget(RenderHandler.LATE_DELAYED_RENDER)
                        .setScaleData(GenericParticleData.create(scale, 0).setEasing(Easing.SINE_IN).build())
                        .setTransparencyData(GenericParticleData.create(0.4f, 0.8f, 0.2f).setEasing(Easing.QUAD_OUT).build())
                        .setColorData(colorData)
                        .setSpinData(SpinParticleData.create(0.2f, 0.4f).setSpinOffset((level.getGameTime() * 0.2f) % 6.28f).setEasing(Easing.QUARTIC_IN).build())
                        .setLifetime(lifeTime)
                        .addMotion(0, velocity * 1.5f, 0)
                        .enableNoClip()
                        .spawn(level, x, y, z);
                lifeTime = 20;
                scale = 0.4f;
                WorldParticleBuilder.create(LodestoneParticleTypes.TWINKLE_PARTICLE)
                        .setRenderTarget(RenderHandler.LATE_DELAYED_RENDER)
                        .setScaleData(GenericParticleData.create(scale, 0f).build())
                        .setTransparencyData(GenericParticleData.create(0.2f, 0.8f).build())
                        .setColorData(ColorParticleData.create(firstColor, secondColor).setEasing(Easing.SINE_IN).setCoefficient(0.5f).build())
                        .setSpinData(SpinParticleData.createRandomDirection(random, 0, 0.4f).setEasing(Easing.QUARTIC_IN).build())
                        .setLifetime(lifeTime)
                        .enableNoClip()
                        .spawn(level, x, y, z);
            }

            if (level.getGameTime() % 4L == 0) {
                final long gameTime = level.getGameTime();
                float scale = RandomHelper.randomBetween(random, 0.6f, 0.75f);
                float velocity = RandomHelper.randomBetween(random, 0f, 0.02f);
                float angle = ((gameTime % 24) / 24f) * (float) Math.PI * 2f;
                Vec3 offset = new Vec3(Math.sin(angle), 0, Math.cos(angle)).normalize();
                Vec3 offsetPosition = new Vec3(x + offset.x * 0.075f, y-0.05f, z + offset.z * 0.075f);
                WorldParticleBuilder.create(ParticleRegistry.SPIRIT_FLAME_PARTICLE)
                        .setRenderTarget(RenderHandler.LATE_DELAYED_RENDER)
                        .setScaleData(GenericParticleData.create(scale * 0.75f, scale, 0).build())
                        .setColorData(ColorParticleData.create(firstColor, secondColor).setEasing(Easing.CIRC_IN_OUT).setCoefficient(2.5f).build())
                        .setTransparencyData(GenericParticleData.create(0f, 1f, 0).setEasing(Easing.SINE_IN, Easing.QUAD_IN).setCoefficient(3.5f).build())
                        .addMotion(0, velocity, 0)
                        .addTickActor(p -> p.setParticleSpeed(p.getParticleSpeed().scale(1f - random.nextFloat() * 0f)))
                        .enableNoClip()
                        .setDiscardFunction(SimpleParticleOptions.ParticleDiscardFunctionType.ENDING_CURVE_INVISIBLE)
                        .spawn(level, offsetPosition.x, offsetPosition.y, offsetPosition.z);
            }
        }
    }
}