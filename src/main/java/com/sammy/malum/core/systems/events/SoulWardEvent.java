package com.sammy.malum.core.systems.events;

import com.sammy.malum.common.capabilities.*;
import com.sammy.malum.core.handlers.SoulWardHandler;
import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingEvent;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.entity.*;

public abstract class SoulWardEvent extends LivingEvent {

    private final SoulWardHandler soulWardHandler;
    private final DamageSource source;

    public SoulWardEvent(LivingEntity entity, SoulWardHandler soulWardHandler, DamageSource source) {
        super(entity);
        this.soulWardHandler = soulWardHandler;
        this.source = source;
    }

    public SoulWardHandler getSoulWardHandler() {
        return soulWardHandler;
    }

    public DamageSource getSource() {
        return source;
    }

    public static final Event<Callback> EVENT = EventFactory.createArrayBacked(SoulWardEvent.Callback.class, (callbacks) -> {
        return (event) -> {
            SoulWardEvent.Callback[] var2 = callbacks;
            int var3 = callbacks.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                SoulWardEvent.Callback callback = var2[var4];
                callback.onEvent(event);
            }

        };
    });

    public interface Callback {
        void onEvent(SoulWardEvent var1);
    }

    @Override
    public void sendEvent() {
        EVENT.invoker().onEvent(this);
    }
}