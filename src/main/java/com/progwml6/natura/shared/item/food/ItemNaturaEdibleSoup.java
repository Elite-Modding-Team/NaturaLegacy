package com.progwml6.natura.shared.item.food;

import javax.annotation.Nullable;

import com.progwml6.natura.shared.NaturaCommons;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class ItemNaturaEdibleSoup extends ItemFood {
    public ItemNaturaEdibleSoup(int amount, float saturation, boolean isWolfFood) {
        super(amount, saturation, isWolfFood);
        this.setMaxStackSize(1);
    }

    /**
     * Called when the player finishes using this Item (E.g. finishes eating.). Not called when the player stops using
     * the Item before the action is complete.
     */
    @Nullable
    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
        super.onItemUseFinish(stack, worldIn, entityLiving);

        if (this.equals(NaturaCommons.glowshroom_stew)) {
            entityLiving.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 45 * 20, 0));
            entityLiving.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 8 * 20, 0));
        }

        if (this.equals(NaturaCommons.saguaros_delight)) {
            entityLiving.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 60 * 20, 0));
            entityLiving.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 8 * 20, 0));
            entityLiving.attackEntityFrom(DamageSource.CACTUS, 2.0F);
        }

        return new ItemStack(Items.BOWL);
    }
}
