package com.sammy.malum.mixin;

import net.neoforged.bus.api.Event;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Event.class)
public interface AccessorEvent {
    @Accessor("isCanceled")
    boolean malum$isCancelled();

    @Mixin(LivingDamageEvent.Post.class)
    interface PostDamage extends AccessorEvent {
        @Accessor("newDamage")
        void malum$setNewDamage(float val);
    }
}
