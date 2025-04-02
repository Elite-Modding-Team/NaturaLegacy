package com.progwml6.natura.tools;

import com.google.common.eventbus.Subscribe;
import org.apache.logging.log4j.Logger;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

import com.progwml6.natura.common.CommonProxy;
import com.progwml6.natura.common.NaturaPulse;
import com.progwml6.natura.library.Util;
import com.progwml6.natura.nether.NaturaNether;
import com.progwml6.natura.tools.item.armor.ItemNaturaImpArmor;
import com.progwml6.natura.tools.item.bows.ItemNaturaBow;
import com.progwml6.natura.tools.item.tools.*;
import slimeknights.mantle.pulsar.pulse.Pulse;

@Pulse(id = NaturaTools.PulseId, description = "All of the tools + armor added by natura")
public class NaturaTools extends NaturaPulse
{
    public static final String PulseId = "NaturaTools";

    static final Logger log = Util.getLogger(PulseId);

    @SidedProxy(clientSide = "com.progwml6.natura.tools.ToolsClientProxy", serverSide = "com.progwml6.natura.common.CommonProxy")
    public static CommonProxy proxy;

    //@formatter:off
    public static Item impHelmet;
    public static Item impChestplate;
    public static Item impLeggings;
    public static Item impBoots;

    public static ItemStack impHelmetStack;
    public static ItemStack impChestplateStack;
    public static ItemStack impLeggingsStack;
    public static ItemStack impBootsStack;

    public static Item bloodwoodSword;
    public static Item bloodwoodPickaxe;
    public static Item bloodwoodShovel;
    public static Item bloodwoodAxe;
    public static Item bloodwoodHoe;
    public static Item bloodwoodBow;

    public static Item darkwoodSword;
    public static Item darkwoodPickaxe;
    public static Item darkwoodShovel;
    public static Item darkwoodAxe;
    public static Item darkwoodHoe;
    public static Item darkwoodBow;

    public static Item fusewoodSword;
    public static Item fusewoodPickaxe;
    public static Item fusewoodShovel;
    public static Item fusewoodAxe;
    public static Item fusewoodHoe;
    public static Item fusewoodBow;
    
    public static Item ghostwoodSword;
    public static Item ghostwoodPickaxe;
    public static Item ghostwoodShovel;
    public static Item ghostwoodAxe;
    public static Item ghostwoodHoe;
    public static Item ghostwoodBow;

    public static Item netherquartzSword;
    public static Item netherquartzPickaxe;
    public static Item netherquartzShovel;
    public static Item netherquartzAxe;
    public static Item netherquartzHoe;
    public static Item netherquartzShears;

    public static Item flintAndBlaze;

    //Dummy Entries (for compatibility purposes)
    public static Item ghostwoodKama;
    public static Item bloodwoodKama;
    public static Item darkwoodKama;
    public static Item fusewoodKama;
    public static Item netherquartzKama;
    //@formatter:on

    @SubscribeEvent
    public void registerItems(Register<Item> event)
    {
        IForgeRegistry<Item> registry = event.getRegistry();

        if (isEntitiesLoaded())
        {
            ArmorMaterial impMaterial = EnumHelper.addArmorMaterial("IMP_HIDE", "imp_hide", 12, new int[] {2, 4, 5, 2}, 18, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0);

            impHelmet = registerItem(registry, new ItemNaturaImpArmor(impMaterial, EntityEquipmentSlot.HEAD), "imp_armor_helmet");
            impChestplate = registerItem(registry, new ItemNaturaImpArmor(impMaterial, EntityEquipmentSlot.CHEST), "imp_armor_chestplate");
            impLeggings = registerItem(registry, new ItemNaturaImpArmor(impMaterial, EntityEquipmentSlot.LEGS), "imp_armor_leggings");
            impBoots = registerItem(registry, new ItemNaturaImpArmor(impMaterial, EntityEquipmentSlot.FEET), "imp_armor_boots");

            impHelmetStack = new ItemStack(impHelmet);
            impChestplateStack = new ItemStack(impChestplate);
            impLeggingsStack = new ItemStack(impLeggings);
            impBootsStack = new ItemStack(impBoots);
        }

        if (isNetherLoaded())
        {
            ToolMaterial netherwoodToolMaterial = EnumHelper.addToolMaterial("NETHERWOOD", 2, 225, 5.0F, 1.5F, 16);
            ToolMaterial quartzToolMaterial = EnumHelper.addToolMaterial("QUARTZ", 2, 151, 8.5F, 2.5F, 12);

            ghostwoodSword = registerItem(registry, new ItemNaturaSword(netherwoodToolMaterial), "ghostwood_sword");
            ghostwoodPickaxe = registerItem(registry, new ItemNaturaPickaxe(netherwoodToolMaterial), "ghostwood_pickaxe");
            ghostwoodShovel = registerItem(registry, new ItemNaturaShovel(netherwoodToolMaterial), "ghostwood_shovel");
            ghostwoodAxe = registerItem(registry, new ItemNaturaAxe(netherwoodToolMaterial, 9.0F, 0.85F), "ghostwood_axe");
            ghostwoodHoe = registerItem(registry, new ItemNaturaHoe(netherwoodToolMaterial), "ghostwood_hoe");
            ghostwoodBow = registerItem(registry, new ItemNaturaBow(584, 1.2F, 1.0F, 0.7F, 1.4F, Ingredient.fromStacks(new ItemStack(NaturaNether.netherPlanks, 1, 0))), "ghostwood_bow");

            bloodwoodSword = registerItem(registry, new ItemNaturaSword(netherwoodToolMaterial), "bloodwood_sword");
            bloodwoodPickaxe = registerItem(registry, new ItemNaturaPickaxe(netherwoodToolMaterial), "bloodwood_pickaxe");
            bloodwoodShovel = registerItem(registry, new ItemNaturaShovel(netherwoodToolMaterial), "bloodwood_shovel");
            bloodwoodAxe = registerItem(registry, new ItemNaturaAxe(netherwoodToolMaterial, 9.0F, 0.85F), "bloodwood_axe");
            bloodwoodHoe = registerItem(registry, new ItemNaturaHoe(netherwoodToolMaterial), "bloodwood_hoe");
            bloodwoodBow = registerItem(registry, new ItemNaturaBow(584, 1.2F, 1.0F, 0.7F, 1.4F, Ingredient.fromStacks(new ItemStack(NaturaNether.netherPlanks, 1, 1))), "bloodwood_bow");

            darkwoodSword = registerItem(registry, new ItemNaturaSword(netherwoodToolMaterial), "darkwood_sword");
            darkwoodPickaxe = registerItem(registry, new ItemNaturaPickaxe(netherwoodToolMaterial), "darkwood_pickaxe");
            darkwoodShovel = registerItem(registry, new ItemNaturaShovel(netherwoodToolMaterial), "darkwood_shovel");
            darkwoodAxe = registerItem(registry, new ItemNaturaAxe(netherwoodToolMaterial, 9.0F, 0.85F), "darkwood_axe");
            darkwoodHoe = registerItem(registry, new ItemNaturaHoe(netherwoodToolMaterial), "darkwood_hoe");
            darkwoodBow = registerItem(registry, new ItemNaturaBow(584, 1.2F, 1.0F, 0.7F, 1.4F, Ingredient.fromStacks(new ItemStack(NaturaNether.netherPlanks, 1, 2))), "darkwood_bow");

            fusewoodSword = registerItem(registry, new ItemNaturaSword(netherwoodToolMaterial), "fusewood_sword");
            fusewoodPickaxe = registerItem(registry, new ItemNaturaPickaxe(netherwoodToolMaterial), "fusewood_pickaxe");
            fusewoodShovel = registerItem(registry, new ItemNaturaShovel(netherwoodToolMaterial), "fusewood_shovel");
            fusewoodAxe = registerItem(registry, new ItemNaturaAxe(netherwoodToolMaterial, 9.0F, 0.85F), "fusewood_axe");
            fusewoodHoe = registerItem(registry, new ItemNaturaHoe(netherwoodToolMaterial), "fusewood_hoe");
            fusewoodBow = registerItem(registry, new ItemNaturaBow(584, 1.2F, 1.0F, 0.7F, 1.4F, Ingredient.fromStacks(new ItemStack(NaturaNether.netherPlanks, 1, 3))), "fusewood_bow");

            netherquartzSword = registerItem(registry, new ItemNaturaSword(quartzToolMaterial), "netherquartz_sword");
            netherquartzPickaxe = registerItem(registry, new ItemNaturaPickaxe(quartzToolMaterial), "netherquartz_pickaxe");
            netherquartzShovel = registerItem(registry, new ItemNaturaShovel(quartzToolMaterial), "netherquartz_shovel");
            netherquartzAxe = registerItem(registry, new ItemNaturaAxe(quartzToolMaterial, 9.0F, 0.95F), "netherquartz_axe");
            netherquartzHoe = registerItem(registry, new ItemNaturaHoe(quartzToolMaterial), "netherquartz_hoe");
            netherquartzShears = registerItem(registry, new ItemNaturaShears(151, Ingredient.fromStacks(new ItemStack(Items.QUARTZ))), "netherquartz_shears");

            flintAndBlaze = registerItem(registry, new ItemNaturaFlintAndBlaze(), "flint_and_blaze");
        }
    }

    @SubscribeEvent
    public void registerModels(ModelRegistryEvent event)
    {
        proxy.preInit();
    }

    @Subscribe
    public void init(FMLInitializationEvent event)
    {
        proxy.init();
    }
}
