package com.progwml6.natura;

import com.progwml6.natura.common.CommonProxy;
import com.progwml6.natura.common.config.Config;
import com.progwml6.natura.common.gui.GuiHandler;
import com.progwml6.natura.decorative.NaturaDecorative;
import com.progwml6.natura.entities.NaturaEntities;
import com.progwml6.natura.library.NaturaCreativeTab;
import com.progwml6.natura.library.Util;
import com.progwml6.natura.library.datafixes.ItemIDFixer;
import com.progwml6.natura.nether.NaturaNether;
import com.progwml6.natura.oredict.NaturaOredict;
import com.progwml6.natura.overworld.NaturaOverworld;
import com.progwml6.natura.plugin.CraftingTweaks;
import com.progwml6.natura.shared.NaturaCommons;
import com.progwml6.natura.tools.NaturaTools;
import com.progwml6.natura.world.NaturaWorld;
import com.progwml6.natura.world.WorldEvents;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.datafix.FixTypes;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ModFixs;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import slimeknights.mantle.pulsar.control.PulseManager;

@Mod(modid = Natura.modID, name = Natura.modName, version = Natura.modVersion, dependencies = "required-after:mantle@[1.12-1.3.0,);", acceptedMinecraftVersions = "[1.12, 1.13)")
public class Natura
{
    public static final String modID = Tags.MOD_ID;

    public static final String modVersion = Tags.VERSION;

    public static final String modName = "Natura Legacy";

    public static final Logger log = LogManager.getLogger(modID);
    
    public static final CreativeTabs TAB = new NaturaCreativeTab(CreativeTabs.CREATIVE_TAB_ARRAY.length, modID + ".tab");

    /* Instance of this mod, used for grabbing prototype fields */
    @Instance(modID) public static Natura instance;

    @SidedProxy(clientSide = "com.progwml6.natura.common.CommonProxy", serverSide = "com.progwml6.natura.common.CommonProxy") public static CommonProxy proxy;

    public static PulseManager pulseManager = new PulseManager(Config.pulseConfig);

    public static boolean isServer()
    {
        return (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER);
    }

    static
    {
        pulseManager.registerPulse(new NaturaCommons());
        pulseManager.registerPulse(new NaturaOverworld());
        pulseManager.registerPulse(new NaturaNether());
        pulseManager.registerPulse(new NaturaDecorative());
        pulseManager.registerPulse(new NaturaTools());
        pulseManager.registerPulse(new NaturaEntities());
        pulseManager.registerPulse(new NaturaOredict());
        pulseManager.registerPulse(new NaturaWorld());
        pulseManager.registerPulse(new CraftingTweaks());
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        Config.load(event);

        MinecraftForge.EVENT_BUS.register(new WorldEvents());

        if (!isServer())
        {
            MinecraftForge.EVENT_BUS.register(new NaturaClientEvents());
        }

        NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
    }

    @EventHandler
    public void init(FMLPreInitializationEvent event)
    {
        ModFixs modFixer = FMLCommonHandler.instance().getDataFixer().init(Util.MODID, Util.DATAFIXER_VERSION);
        modFixer.registerFix(FixTypes.ITEM_INSTANCE, new ItemIDFixer());
    }
}