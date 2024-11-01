package com.sammy.malum.common.item.curiosities;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import com.sammy.malum.common.entity.nitrate.*;
import com.sammy.malum.common.item.spirit.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.item.*;
import io.netty.buffer.*;
import net.minecraft.nbt.*;
import net.minecraft.network.codec.*;
import net.minecraft.sounds.*;
import net.minecraft.stats.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;

import java.util.function.*;

public class CatalystFlingerItem extends Item {

    public record FlingerData(int timer, int state, int stashedState) {

        public FlingerData() {
            this(0, 0, 0);
        }

        public static Codec<FlingerData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                ExtraCodecs.NON_NEGATIVE_INT.fieldOf("malum:timer").forGetter(FlingerData::timer),
                ExtraCodecs.NON_NEGATIVE_INT.fieldOf("malum:state").forGetter(FlingerData::state),
                ExtraCodecs.NON_NEGATIVE_INT.fieldOf("malum:stashed_state").forGetter(FlingerData::stashedState)
        ).apply(instance, FlingerData::new));

    }

    public final Function<Player, AbstractNitrateEntity> entitySupplier;
    public CatalystFlingerItem(Properties pProperties, Function<Player, AbstractNitrateEntity> entitySupplier) {
        super(pProperties);
        this.entitySupplier = entitySupplier;
    }

    @Override
    public boolean isValidRepairItem(ItemStack stack, ItemStack repairCandidate) {
        return repairCandidate.getItem().equals(ItemRegistry.MALIGNANT_LEAD.get()) || super.isValidRepairItem(stack, repairCandidate);
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        var stateComponent = DataComponentRegistry.CATALYST_FLINGER_DATA;
        if (pStack.has(stateComponent)) {
            var data = pStack.get(stateComponent);
            int state = data.state;
            if (state != 0) {
                int timer = data.timer;
                int stashedState = data.stashedState;
                if (!pIsSelected) {
                    timer++;
                } else if (timer > 0) {
                    timer = 0;
                }
                if (timer >= 100) {
                    timer = 0;
                    stashedState = state;
                    state = 0;
                    pEntity.playSound(SoundRegistry.CATALYST_LOBBER_LOCKED.get(), 1.2f, 0.8f);
                }
                pStack.set(stateComponent, new FlingerData(timer, state, stashedState));
            }
        }
        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack stack = playerIn.getItemInHand(handIn);
        var stateComponent = stack.getOrDefault(DataComponentRegistry.CATALYST_FLINGER_DATA, new FlingerData());

        int timer = stateComponent.timer();
        int state = stateComponent.state();
        int stashedState = stateComponent.stashedState();
        int cooldown = 0;
        SoundEvent sound;
        switch (state) {
            case 0 -> {
                cooldown = 100;
                state = Math.max(1, stashedState);
                sound = SoundRegistry.CATALYST_LOBBER_UNLOCKED.get();
            }
            case 1 -> {
                ItemStack ammo = ItemStack.EMPTY;
                for (int i = 0; i < playerIn.getInventory().getContainerSize(); i++) {
                    ItemStack maybeAmmo = playerIn.getInventory().getItem(i);
                    if (maybeAmmo.getItem().equals(ItemRegistry.AURIC_EMBERS.get())) {
                        ammo = maybeAmmo;
                        break;
                    }
                }
                if (ammo.isEmpty()) {
                    return InteractionResultHolder.fail(stack);
                }
                cooldown = 20;
                state = 2;
                ammo.shrink(1);
                sound = SoundRegistry.CATALYST_LOBBER_PRIMED.get();
            }
            case 2 -> {
                if (!worldIn.isClientSide) {
                    AbstractNitrateEntity bombEntity = entitySupplier.apply(playerIn);
                    int angle = handIn == InteractionHand.MAIN_HAND ? 225 : 90;
                    Vec3 pos = playerIn.position().add(playerIn.getLookAngle().scale(0.5)).add(0.5 * Math.sin(Math.toRadians(angle - playerIn.yHeadRot)), playerIn.getBbHeight() * 2 / 3, 0.5 * Math.cos(Math.toRadians(angle - playerIn.yHeadRot)));
                    float pitch = -10.0F;
                    bombEntity.shootFromRotation(playerIn, playerIn.getXRot(), playerIn.getYRot(), pitch, 1.25F, 0.9F);
                    bombEntity.setPos(pos);
                    worldIn.addFreshEntity(bombEntity);
                }
                playerIn.awardStat(Stats.ITEM_USED.get(this));
                if (!playerIn.getAbilities().instabuild) {
                    stack.hurtAndBreak(1, playerIn, EquipmentSlot.MAINHAND);
                }
                state = 1;
                sound = SoundRegistry.CATALYST_LOBBER_FIRED.get();
            }
            default -> {
                stack.set(DataComponentRegistry.CATALYST_FLINGER_DATA, new FlingerData());
                throw new IllegalStateException("Catalyst lobber used with an invalid state.");
            }
        }
        stack.set(DataComponentRegistry.CATALYST_FLINGER_DATA, new FlingerData(timer, state, stashedState));
        if (cooldown != 0) {
            playerIn.getCooldowns().addCooldown(this, cooldown);
        }
        playerIn.playSound(sound, 1f, 1f);
        return InteractionResultHolder.sidedSuccess(stack, worldIn.isClientSide());
    }
}
