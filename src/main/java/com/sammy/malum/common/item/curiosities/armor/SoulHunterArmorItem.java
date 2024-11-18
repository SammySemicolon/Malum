package com.sammy.malum.common.item.curiosities.armor;

import com.google.common.collect.*;
import com.sammy.malum.client.cosmetic.ArmorSkinRenderingData;
import com.sammy.malum.common.item.cosmetic.skins.ArmorSkin;
import com.sammy.malum.compability.irons_spellbooks.IronsSpellsCompat;
import com.sammy.malum.registry.client.ModelRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import team.lodestar.lodestone.registry.common.LodestoneAttributeRegistry;
import team.lodestar.lodestone.systems.model.LodestoneArmorModel;

import java.util.UUID;
import java.util.function.Consumer;

import static com.sammy.malum.registry.common.item.ArmorTiers.ArmorTierEnum.SPIRIT_HUNTER;

public class SoulHunterArmorItem extends MalumArmorItem {
    public SoulHunterArmorItem(ArmorItem.Type slot, Properties builder) {
        super(SPIRIT_HUNTER, slot, builder);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> createExtraAttributes(Type type) {
        Multimap<Attribute, AttributeModifier> attributes = ArrayListMultimap.create();
        UUID uuid = ARMOR_MODIFIER_UUID_PER_TYPE.get(type);
        attributes.put(LodestoneAttributeRegistry.MAGIC_PROFICIENCY.get(), new AttributeModifier(uuid, "Magic Proficiency", 0.15f, AttributeModifier.Operation.MULTIPLY_BASE));
        IronsSpellsCompat.addSoulHunterSpellPower(attributes, uuid);
        return attributes;
    }

    public String getTexture() {
        return "spirit_hunter_reforged";
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            @Override
            public LodestoneArmorModel getHumanoidArmorModel(LivingEntity entity, ItemStack itemStack, EquipmentSlot armorSlot, HumanoidModel _default) {
                float pticks = Minecraft.getInstance().getFrameTime();
                float f = Mth.rotLerp(pticks, entity.yBodyRotO, entity.yBodyRot);
                float f1 = Mth.rotLerp(pticks, entity.yHeadRotO, entity.yHeadRot);
                float netHeadYaw = f1 - f;
                float netHeadPitch = Mth.lerp(pticks, entity.xRotO, entity.getXRot());
                ArmorSkin skin = ArmorSkin.getAppliedItemSkin(itemStack);
                LodestoneArmorModel model = ModelRegistry.SOUL_HUNTER_ARMOR;
                if (skin != null) {
                    model = ArmorSkinRenderingData.RENDERING_DATA.apply(skin).getModel(entity);
                }
                model.slot = armorSlot;
                model.copyFromDefault(_default);

                //pLimbSwing, pLimbSwingAmount
                /*
                f8 = pEntity.walkAnimation.speed(pPartialTicks);
                f5 = pEntity.walkAnimation.position(pPartialTicks);
                 */
                model.setupAnim(entity, entity.walkAnimation.position(), entity.walkAnimation.speed(), entity.tickCount + pticks, netHeadYaw, netHeadPitch);
                return model;
            }
        });
    }
}