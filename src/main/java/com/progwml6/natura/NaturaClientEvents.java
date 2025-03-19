package com.progwml6.natura;

import com.progwml6.natura.tools.item.bows.ItemNaturaBow;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.client.event.RenderSpecificHandEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class NaturaClientEvents
{
    // Courtesy of NeRdTheNed
    @SubscribeEvent
    public void bowFOV(FOVUpdateEvent event)
    {
        final EntityPlayer eventPlayer = event.getEntity();
        final Item eventItem = eventPlayer.getActiveItemStack().getItem();

        if (eventItem instanceof ItemNaturaBow)
        {
            float finalFov = event.getFov();
            final float itemUseCount = ((ItemNaturaBow) eventItem).getMaxItemUseDuration(eventPlayer.getActiveItemStack()) - eventPlayer.getItemInUseCount();

            /*
             * First, we have to reverse the standard bow zoom.
             * Minecraft helpfully applies the standard bow zoom
             * to any item that is an instance of a ItemBow.
             * However, our custom bows draw back at different speeds,
             * so the standard zoom is not at the right speed.
             * To compensate for this, we just calculate the standard bow zoom,
             * and apply it in reverse.
             */

            float realBow = itemUseCount / 20.0F;

            if (realBow > 1.0F)
            {
                realBow = 1.0F;
            } else
            {
                realBow *= realBow;
            }

            /*
             * Minecraft uses finalFov *= 1.0F - (realBow * 0.15F)
             * to calculate the standard bow zoom.
             * To reverse this, we just divide it instead.
             */

            finalFov /= 1.0F - (realBow * 0.15F);

            /*
             * We now calculate and apply our custom bow zoom.
             * The only difference between standard bow zoom and custom bow zoom
             * is that we change the hardcoded value of 20.0F to
             * whatever drawTime is.
             */

            float drawTime = 20.0F * ((ItemNaturaBow) eventItem).drawTimeMult;
            float customBow = itemUseCount / drawTime;

            if (customBow > 1.0F)
            {
                customBow = 1.0F;
            } else
            {
                customBow *= customBow;
            }

            finalFov *= 1.0F - (customBow * 0.15F);
            event.setNewfov(finalFov);
        }
    }

    // Courtesy of NeRdTheNed
    @SubscribeEvent
    public void renderBow(RenderSpecificHandEvent event)
    {
        final Minecraft mc = Minecraft.getMinecraft();
        final Item eventItem = event.getItemStack().getItem();

        // Only handle rendering if we're in first person and drawing back a CustomBow.
        if ((mc.gameSettings.thirdPersonView == 0) && mc.player.isHandActive() && (mc.player.getActiveHand() == event.getHand()) && (mc.player.getItemInUseCount() > 0) && (event.getItemStack().getItem() instanceof ItemNaturaBow))
        {
            // Cancel rendering so we can render instead
            event.setCanceled(true);
            GlStateManager.pushMatrix();

            final boolean rightHanded = (event.getHand() == EnumHand.MAIN_HAND ? mc.player.getPrimaryHand() : mc.player.getPrimaryHand().opposite()) == EnumHandSide.RIGHT;
            final int handedSide = rightHanded ? 1 : -1;

            GlStateManager.translate(handedSide * 0.2814318F, -0.3365561F + (event.getEquipProgress() * -0.6F), -0.5626847F);

            // Rotate angles
            GlStateManager.rotate(-13.935F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(handedSide * 35.3F, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(handedSide * -9.785F, 0.0F, 0.0F, 1.0F);

            final float ticks = ((ItemNaturaBow) eventItem).getMaxItemUseDuration(event.getItemStack()) - ((mc.player.getItemInUseCount() - event.getPartialTicks()) + 1.0F);
            float drawTime = 20.0F * ((ItemNaturaBow) eventItem).drawTimeMult;
            float divTicks = ticks / drawTime;
            divTicks = ((divTicks * divTicks) + (divTicks * 2.0F)) / 3.0F;

            if (divTicks > 1.0F)
            {
                divTicks = 1.0F;
            }

            // Bow animations and transformations
            if (divTicks > 0.1F)
            {
                // Bow shake
                GlStateManager.translate(0.0F, MathHelper.sin((ticks - 0.1F) * 1.3F) * (divTicks - 0.1F) * 0.004F, 0.0F);
            }

            // Backwards motion ("draw back" animation)
            GlStateManager.translate(0.0F, 0.0F, divTicks * 0.04F);

            // Relative scaling for FOV reasons
            GlStateManager.scale(1.0F, 1.0F, 1.0F + (divTicks * 0.2F));

            // Rotate bow based on handedness
            GlStateManager.rotate(handedSide * 45.0F, 0.0F, -1.0F, 0.0F);

            // Let Minecraft do the rest of the item rendering
            mc.getItemRenderer().renderItemSide(mc.player, event.getItemStack(), rightHanded ? ItemCameraTransforms.TransformType.FIRST_PERSON_RIGHT_HAND : ItemCameraTransforms.TransformType.FIRST_PERSON_LEFT_HAND, !rightHanded);
            GlStateManager.popMatrix();
        }
    }
}
