package com.progwml6.natura.world;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteract;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.progwml6.natura.Natura;
import com.progwml6.natura.entities.entity.ai.EntityAITemptItemStack;
import com.progwml6.natura.overworld.NaturaOverworld;
import com.progwml6.natura.shared.NaturaCommons;

public class WorldEvents
{
    @SubscribeEvent
    public void interactEvent(EntityInteract event)
    {
        if (event.getTarget() instanceof EntityCow || event.getTarget() instanceof EntitySheep)
        {
            setTheMood(event.getEntityPlayer(), event.getHand(), (EntityAnimal) event.getTarget(), NaturaCommons.barley);
        }
        else if (Natura.pulseManager.isPulseLoaded(NaturaOverworld.PulseId) && event.getTarget() instanceof EntityChicken)
        {
            setTheMood(event.getEntityPlayer(), event.getHand(), (EntityAnimal) event.getTarget(), NaturaOverworld.barley_seeds);
            setTheMood(event.getEntityPlayer(), event.getHand(), (EntityAnimal) event.getTarget(), NaturaOverworld.cotton_seeds);
        }
    }

    @SubscribeEvent
    public void onLivingJoin(EntityJoinWorldEvent event)
    {
        if (event.getEntity() instanceof EntityCow)
        {
            ((EntityLiving) event.getEntity()).tasks.addTask(3, new EntityAITemptItemStack((EntityCreature) event.getEntity(), 1.25F, NaturaCommons.barley));
        }
        else if (event.getEntity() instanceof EntitySheep)
        {
            ((EntityLiving) event.getEntity()).tasks.addTask(3, new EntityAITemptItemStack((EntityCreature) event.getEntity(), 1.1F, NaturaCommons.barley));
        }
        else if (Natura.pulseManager.isPulseLoaded(NaturaOverworld.PulseId) && event.getEntity() instanceof EntityChicken)
        {
            ((EntityLiving) event.getEntity()).tasks.addTask(3, new EntityAITempt((EntityCreature) event.getEntity(), 1.0F, NaturaOverworld.overworldSeeds, false));
        }
    }

    private void setTheMood(EntityPlayer player, EnumHand hand, EntityAnimal animal, ItemStack stack)
    {
        ItemStack equipped = player.getHeldItem(hand);
        if (ItemStack.areItemsEqual(equipped, stack) && animal.getGrowingAge() == 0 && !animal.isInLove())
        {
            if (!player.capabilities.isCreativeMode)
            {
                equipped.shrink(1);
                if (equipped.getCount() <= 0)
                {
                    player.inventory.setInventorySlotContents(player.inventory.currentItem, ItemStack.EMPTY);
                }
            }
            animal.setInLove(player);
        }
    }
}
