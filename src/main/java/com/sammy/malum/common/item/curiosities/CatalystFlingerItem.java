package com.sammy.malum.common.item.curiosities;

import com.sammy.malum.common.entity.nitrate.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.item.*;
import net.minecraft.nbt.*;
import net.minecraft.sounds.*;
import net.minecraft.stats.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;

import java.util.function.*;

public class CatalystFlingerItem extends Item {

    public static final String STATE = "malum:state";
    public static final String STASHED_STATE = "malum:stashed_state";
    public static final String TIMER = "malum:timer";

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
        if (pStack.hasTag()) {
            CompoundTag tag = pStack.getTag();
            int state = tag.getInt(STATE);
            if (state != 0) {
                int timer = tag.getInt(TIMER);
                if (pIsSelected && timer > 0) {
                    tag.remove(TIMER);
                    return;
                }
                if (!pIsSelected) {
                    timer++;
                }
                if (timer >= 100) {
                    tag.putInt(STASHED_STATE, state);
                    tag.remove(STATE);
                    tag.remove(TIMER);

                    pEntity.playSound(SoundRegistry.CATALYST_LOBBER_LOCKED.get(), 1.2f, 0.8f);
                    return;
                }
                tag.putInt(TIMER, timer);
            }
        }
        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        CompoundTag tag = itemstack.getOrCreateTag();
        int state = tag.getInt(STATE);
        int cooldown = 0;
        SoundEvent sound;
        switch (state) {
            case 0 -> {
                cooldown = 100;
                state = Math.max(1, tag.getInt(STASHED_STATE));
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
                    return InteractionResultHolder.fail(itemstack);
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
                    itemstack.hurtAndBreak(1, playerIn, EquipmentSlot.MAINHAND);
                }
                state = 1;
                sound = SoundRegistry.CATALYST_LOBBER_FIRED.get();
            }
            default -> {
                tag.remove(STATE);
                throw new IllegalStateException("Nitrate lobber used with an invalid state.");
            }
        }
        tag.putInt(STATE, state);
        if (cooldown != 0) {
            playerIn.getCooldowns().addCooldown(this, cooldown);
        }
        playerIn.playSound(sound, 1f, 1f);
        return InteractionResultHolder.sidedSuccess(itemstack, worldIn.isClientSide());
    }
}
