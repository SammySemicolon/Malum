package com.sammy.malum.common.item.cosmetic.curios;

import com.sammy.malum.common.item.curiosities.curios.MalumCurioItem;
import com.sammy.malum.registry.common.item.ItemRegistry;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import org.jetbrains.annotations.NotNull;
import team.lodestar.lodestone.helpers.CurioHelper;
import team.lodestar.lodestone.handlers.*;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static top.theillusivec4.curios.api.type.capability.ICurio.DropRule.ALWAYS_KEEP;

public class CurioTokenOfGratitude extends MalumCurioItem implements ItemEventHandler.IEventResponder {
    public static final List<UUID> GRADITUDE_CERTIFIED = new ArrayList<>();
    public static final List<UUID> TRANS_SCARFS = new ArrayList<>();
    public static final UUID SAMMY = validateForGratitude("0ca54301-6170-4c44-b3e0-b8afa6b81ed2");
    public static final UUID OWL_PERSON = validateForGratitude("309b2cf6-caa1-4c9a-912d-7b4be827dc8c");
    public static final UUID SNAKE_SCARF_FELLA = validateForGratitude("07f1452b-7408-47b3-be2b-e6e08869e891");
    public static final UUID BOBBU = validateForGratitude("3ab4b4b3-dceb-4ff9-a901-fa6edd746070");
    public static final UUID DELLY = validateForGratitude("7c3a2f88-f6b8-47ff-971d-73544eb5ae62");
    public static final UUID LOFI = validateForGratitude("85715c8f-4f71-4ebd-9f3a-96dfd8e8e390");
    public static final UUID CREECHURE = validateForGratitude("9acb0ed8-a6a3-46bc-a6ff-23c176e5ec3d");
    public static final UUID SALT = validateForGratitude("3585541f-8289-45a9-b8d7-6729bb1d95da");

    static {
        addTransScarf(validateForGratitude("72155db3-d5e4-47fa-8200-c85bf7f87370")); //copilot says it's 'Snake'
        addTransScarf(validateForGratitude("26b08ef8-a0e8-4a38-b92c-285a14b27d32")); //bouncekey
        addTransScarf(validateForGratitude("d30d8e38-6f93-4d96-968d-dd6ec5596941")); //falky
        addTransScarf(validateForGratitude("b502a944-7417-4ac7-9ad8-9bc6ff67af0a")); //that one furry
        addTransScarf(validateForGratitude("89719de3-6445-4445-abe2-509659db930d")); //only god knows really
        addTransScarf(validateForGratitude("9380b46b-8de4-4a54-b454-88f33059c9ab")); //no idea
        addTransScarf(validateForGratitude("3a9d3905-3232-4ee2-8e3f-a57290e4a788")); //rttv
        addTransScarf(validateForGratitude("d3c951e6-824d-41fc-a73d-2a957c1575b1")); //who knows?
        addTransScarf(validateForGratitude("2b90423d-77d1-44e7-baab-015a8be27a71")); //the other furry
    }

    public CurioTokenOfGratitude(Properties builder) {
        super(builder, MalumTrinketType.GILDED);
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        if (slotContext.entity() instanceof Player player) {
            if (player.getUUID().equals(SAMMY) || player.getUUID().equals(LOFI) || player.getUUID().equals(CREECHURE) || player.getUUID().equals(SALT)) {
                int interval = player.isCrouching() ? 10 : 4000;
                if (player.level().getGameTime() % interval == 0) {
                    SoundEvent soundEvent = player.getRandom().nextInt(8) == 0 ? SoundEvents.CAT_PURREOW : SoundEvents.CAT_PURR;
                    player.level().playSound(player, player.blockPosition(), soundEvent, SoundSource.PLAYERS, 1, 1);
                }
            }
        }
    }

    @NotNull
    @Override
    public ICurio.DropRule getDropRule(SlotContext slotContext, DamageSource source, boolean recentlyHit, ItemStack stack) {
        return ALWAYS_KEEP;
    }

    public static void giveItem(EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof Player playerEntity) {
            if (!playerEntity.level().isClientSide) {
                if (GRADITUDE_CERTIFIED.stream().anyMatch(u -> u.equals(playerEntity.getUUID()))) {
                    if (CurioHelper.findCosmeticCurio(s -> s.getItem().equals(ItemRegistry.TOKEN_OF_GRATITUDE.get()), playerEntity).isEmpty()) {
                        ItemHandlerHelper.giveItemToPlayer(playerEntity, ItemRegistry.TOKEN_OF_GRATITUDE.get().getDefaultInstance());
                    }
                }
            }
        }
    }

    public static UUID validateForGratitude(String uuid) {
        UUID id = UUID.fromString(uuid);
        GRADITUDE_CERTIFIED.add(id);
        return id;
    }

    public static void addTransScarf(UUID uuid) {
        TRANS_SCARFS.add(uuid);
    }
}