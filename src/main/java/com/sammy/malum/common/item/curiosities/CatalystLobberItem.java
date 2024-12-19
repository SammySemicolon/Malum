package com.sammy.malum.common.item.curiosities;

import com.sammy.malum.common.data_components.*;
import com.sammy.malum.common.entity.nitrate.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.item.*;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.*;
import net.minecraft.stats.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;

import java.util.List;
import java.util.function.*;

public class CatalystLobberItem extends Item {

    public final Function<Player, AbstractNitrateEntity> entitySupplier;
    public CatalystLobberItem(Properties pProperties, Function<Player, AbstractNitrateEntity> entitySupplier) {
        super(pProperties);
        this.entitySupplier = entitySupplier;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        var data = stack.get(DataComponentRegistry.CATALYST_LOBBER_STATE.get());
        tooltipComponents.add(Component.literal(String.valueOf(data.state())));
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

    public static float getStateDisplay(ItemStack stack) {
        var data = stack.get(DataComponentRegistry.CATALYST_LOBBER_STATE.get());
        if (data == null) {
            return -1;
        }
        return data.state() / 10f;
    }
    @Override
    public boolean isValidRepairItem(ItemStack stack, ItemStack repairCandidate) {
        return repairCandidate.getItem().equals(ItemRegistry.MALIGNANT_LEAD.get()) || super.isValidRepairItem(stack, repairCandidate);
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        var data = pStack.get(DataComponentRegistry.CATALYST_LOBBER_STATE.get());
        if (data != null) {
            int state = data.state();
            if (state != 0) {
                int timer = data.timer();
                int stashedState = data.stashedState();
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
                pStack.set(DataComponentRegistry.CATALYST_LOBBER_STATE.get(), new CatalystFlingerState(timer, state, stashedState));
            }
        }
        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack stack = playerIn.getItemInHand(handIn);
        var component = stack.getOrDefault(DataComponentRegistry.CATALYST_LOBBER_STATE.get(), new CatalystFlingerState());
        int timer = component.timer();
        int state = component.state();
        int stashedState = component.stashedState();
        int cooldown = 0;
        SoundEvent sound;
        switch (state) {
            case 0 -> {
                cooldown = 100;
                state = Math.max(1, stashedState);
                sound = SoundRegistry.CATALYST_LOBBER_UNLOCKED.get();
            }
            case 1 -> {
                if (!playerIn.isCreative()) {
                    var ammo = ItemStack.EMPTY;
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
                    ammo.shrink(1);
                }
                cooldown = 20;
                state = 2;
                sound = SoundRegistry.CATALYST_LOBBER_PRIMED.get();
            }
            case 2 -> {
                if (!worldIn.isClientSide) {
                    var bombEntity = entitySupplier.apply(playerIn);
                    int angle = handIn == InteractionHand.MAIN_HAND ? 225 : 90;
                    var pos = playerIn.position().add(playerIn.getLookAngle().scale(0.5)).add(0.5 * Math.sin(Math.toRadians(angle - playerIn.yHeadRot)), playerIn.getBbHeight() * 2 / 3, 0.5 * Math.cos(Math.toRadians(angle - playerIn.yHeadRot)));
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
                stack.set(DataComponentRegistry.CATALYST_LOBBER_STATE.get(), new CatalystFlingerState());
                throw new IllegalStateException("Catalyst lobber used with an invalid state.");
            }
        }
        stack.set(DataComponentRegistry.CATALYST_LOBBER_STATE.get(), new CatalystFlingerState(timer, state, stashedState));
        if (cooldown != 0) {
            playerIn.getCooldowns().addCooldown(this, cooldown);
        }
        playerIn.playSound(sound, 1f, 1f);
        return InteractionResultHolder.sidedSuccess(stack, worldIn.isClientSide());
    }
}
