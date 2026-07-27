package com.progwml6.natura.entities;

import java.util.Set;

import org.apache.logging.log4j.Logger;

import com.google.common.eventbus.Subscribe;
import com.progwml6.natura.Natura;
import com.progwml6.natura.common.CommonProxy;
import com.progwml6.natura.common.NaturaPulse;
import com.progwml6.natura.common.config.Config;
import com.progwml6.natura.entities.entity.monster.EntityBabyHeatscarSpider;
import com.progwml6.natura.entities.entity.monster.EntityHeatscarSpider;
import com.progwml6.natura.entities.entity.monster.EntityNitroCreeper;
import com.progwml6.natura.entities.entity.passive.EntityImp;
import com.progwml6.natura.library.Util;
import com.progwml6.natura.shared.NaturaCommons;

import net.minecraft.entity.EntityLiving.SpawnPlacementType;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import slimeknights.mantle.pulsar.pulse.Pulse;

@Pulse(id = NaturaEntities.PulseId, description = "The entities added by Natura")
public class NaturaEntities extends NaturaPulse
{
    public static final String PulseId = "NaturaEntities";

    static final Logger log = Util.getLogger(PulseId);

    @SidedProxy(clientSide = "com.progwml6.natura.entities.EntitiesClientProxy", serverSide = "com.progwml6.natura.common.CommonProxy", modId = Natura.modID)
    public static CommonProxy proxy;
    
    // Loot Tables
    public static final ResourceLocation BABY_HEATSCAR_SPIDER = Util.getResource("entities/baby_heatscar_spider");
    public static final ResourceLocation HEATSCAR_SPIDER = Util.getResource("entities/heatscar_spider");
    public static final ResourceLocation IMP = Util.getResource("entities/imp");
    public static final ResourceLocation NITRO_CREEPER = Util.getResource("entities/nitro_creeper");

    @SubscribeEvent
    public void registerEntities(Register<EntityEntry> event)
    {
    	int id = 0;
    	
    	EntityRegistry.registerModEntity(Util.getResource("babyheatscarspider"), EntityBabyHeatscarSpider.class, "babyheatscarspider", id++, Natura.instance, 64, 1, true, 0xE64D10, 0x57B1BD);
        EntityRegistry.registerModEntity(Util.getResource("heatscarspider"), EntityHeatscarSpider.class, "heatscarspider", id++, Natura.instance, 64, 1, true, 0xE64D10, 0x57B1BD);
        EntityRegistry.registerModEntity(Util.getResource("imp"), EntityImp.class, "imp", id++, Natura.instance, 64, 1, true, 0xF29735, 0x2E1F10);
        EntityRegistry.registerModEntity(Util.getResource("nitrocreeper"), EntityNitroCreeper.class, "nitrocreeper", id++, Natura.instance, 64, 1, true, 0xF73E6C, 0x9B5004);
    }

    @Subscribe
    public void preInit(FMLPreInitializationEvent event)
    {
        proxy.preInit();
    }

    @Subscribe
    public void init(FMLInitializationEvent event)
    {
        proxy.init();

        this.registerSmelting();
    }

    @Subscribe
    public void postInit(FMLPostInitializationEvent event)
    {
        //TODO add way to exclude some of these
        Set<Biome> biomeList = BiomeDictionary.getBiomes(BiomeDictionary.Type.NETHER);

        Biome[] biomes = biomeList.toArray(new Biome[biomeList.size()]);

        EntityRegistry.addSpawn(EntityImp.class, Config.impWeight, Config.impSpawnMinimum, Config.impSpawnMaximum, EnumCreatureType.CREATURE, biomes);

        EntitySpawnPlacementRegistry.setPlacementType(EntityImp.class, SpawnPlacementType.ON_GROUND);
        
        EntityRegistry.addSpawn(EntityHeatscarSpider.class, Config.heatscarSpiderWeight, Config.heatscarSpiderSpawnMinimum, Config.heatscarSpiderSpawnMaximum, EnumCreatureType.MONSTER, biomes);
        EntityRegistry.addSpawn(EntityBabyHeatscarSpider.class, Config.babyHeatscarSpiderWeight, Config.babyHeatscarSpiderSpawnMinimum, Config.babyHeatscarSpiderSpawnMaximum, EnumCreatureType.MONSTER, biomes);
        
        EntityRegistry.addSpawn(EntityNitroCreeper.class, Config.nitroCreeperWeight, Config.nitroCreeperSpawnMinimum, Config.nitroCreeperSpawnMaximum, EnumCreatureType.MONSTER, biomes);

        proxy.postInit();
    }

    private void registerSmelting()
    {
        FurnaceRecipes furnaceRecipes = FurnaceRecipes.instance();

        if (isEntitiesLoaded())
        {
            furnaceRecipes.addSmeltingRecipe(NaturaCommons.impmeatRaw.copy(), NaturaCommons.impmeatCooked.copy(), 0.2F);
        }
    }
}
