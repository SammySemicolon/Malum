package com.sammy.malum.core.systems.item;

import com.sammy.malum.registry.common.item.ItemTagRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.event.ClientTickEvent;

import java.util.ArrayList;
import java.util.function.Predicate;

public class HeldItemTracker {

    public final Predicate<ItemStack> predicate;

    public int heldTimer;
    public boolean isHeld;

    public static final ArrayList<HeldItemTracker> TRACKERS = new ArrayList<>();

    public HeldItemTracker(Predicate<ItemStack> predicate) {
        this.predicate = predicate;
        TRACKERS.add(this);
    }

    public static void tickTrackers() {
        for (HeldItemTracker tracker : TRACKERS) {
            tracker.update();
        }
    }

    public void update() {
        final LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) {
            return;
        }
        if ((predicate.test(player.getMainHandItem()) || predicate.test(player.getOffhandItem()))) {
            if (heldTimer < 20) {
                heldTimer++;
            }
            isHeld = true;
        } else if (heldTimer > 0) {
            heldTimer--;
            isHeld = false;
        }
    }

    public boolean isVisible() {
        return getDelta(0) > 0;
    }
    public float getDelta(float partialTicks) {
        return (heldTimer + (isHeld ? partialTicks : -partialTicks)) / 20f;
    }
}
