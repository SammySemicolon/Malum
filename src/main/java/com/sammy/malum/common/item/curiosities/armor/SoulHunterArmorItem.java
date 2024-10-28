package com.sammy.malum.common.item.curiosities.armor;

import com.sammy.malum.MalumMod;
import com.sammy.malum.client.cosmetic.ArmorSkinRenderingData;
import com.sammy.malum.common.item.cosmetic.skins.ArmorSkin;
import com.sammy.malum.registry.client.ModelRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import team.lodestar.lodestone.registry.common.LodestoneAttributes;
import team.lodestar.lodestone.systems.model.LodestoneArmorModel;

import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

import static com.sammy.malum.registry.common.item.ArmorTiers.SPIRIT_HUNTER;

public class SoulHunterArmorItem extends MalumArmorItem {
    public SoulHunterArmorItem(ArmorItem.Type slot, Properties builder) {
        super(SPIRIT_HUNTER, slot, builder);
    }

    @Override
    public List<ItemAttributeModifiers.Entry> createExtraAttributes() {
        ItemAttributeModifiers.Builder attributes = ItemAttributeModifiers.builder();
        UUID uuid = ARMOR_MODIFIER_UUID_PER_TYPE.get(type);
        attributes.add(LodestoneAttributes.MAGIC_PROFICIENCY, new AttributeModifier(MalumMod.malumPath("magic_proficiency"), 2f, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.ARMOR);
        return attributes.build().modifiers();
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            @Override
            public LodestoneArmorModel getHumanoidArmorModel(LivingEntity entity, ItemStack itemStack, EquipmentSlot armorSlot, HumanoidModel _default) {
                float pticks = (float)(Minecraft.getInstance().getFrameTimeNs() / 20000000000L);
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

    @Override
    public ResourceLocation getArmorTexture() {
        return MalumMod.malumPath("textures/armor/spirit_hunter_reforged");
    }
}