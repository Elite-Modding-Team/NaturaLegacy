package com.progwml6.natura;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * A child mod container for mods to differentiate this Natura fork from the standard Natura mod.
 */
@Mod(modid = NaturaLegacy.modID, name = NaturaLegacy.modName, version = NaturaLegacy.modVersion)
public class NaturaLegacy
{
    public static final String modID = "natura_legacy";

    public static final String modVersion = Tags.VERSION;

    public static final String modName = "Natura";

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        event.getModMetadata().parent = Natura.modID;
    }
}
