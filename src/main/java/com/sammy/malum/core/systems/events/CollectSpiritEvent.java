package com.sammy.malum.core.systems.events;

import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingEvent;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.entity.*;

public class CollectSpiritEvent extends LivingEvent {

    public CollectSpiritEvent(LivingEntity entity) {
        super(entity);
    }

    public static final Event<CollectSpiritEvent.Callback> EVENT = EventFactory.createArrayBacked(CollectSpiritEvent.Callback.class, (callbacks) -> {
        return (event) -> {
            CollectSpiritEvent.Callback[] var2 = callbacks;
            int var3 = callbacks.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                CollectSpiritEvent.Callback callback = var2[var4];
                callback.onEvent(event);
            }

        };
    });

    public interface Callback {
        void onEvent(CollectSpiritEvent var1);
    }

    @Override
    public void sendEvent() {
        EVENT.invoker().onEvent(this);
    }
}
