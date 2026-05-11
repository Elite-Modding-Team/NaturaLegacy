package com.progwml6.natura.common.config;

import org.apache.logging.log4j.Logger;

import com.progwml6.natura.library.Util;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import slimeknights.mantle.pulsar.config.ForgeCFG;

public final class Config
{
    public static ForgeCFG pulseConfig = new ForgeCFG("NaturaModules", "Modules");

    public static Config instance = new Config();

    public static Logger log = Util.getLogger("Config");

    private static final String RETROGEN = "Retrogen";

    private static final String ENABLE_DISABLE = "ENABLE-DISABLE";

    private static final String WORLDGEN = "Worldgen";

    private static final String ENTITIES = "Entities";

    private Config()
    {
    }

    public static void load(FMLPreInitializationEvent event)
    {
        configFile = new Configuration(event.getSuggestedConfigurationFile(), "0.3", false);
        configFile.load();

        syncConfig();
    }

    public static boolean syncConfig()
    {
        // Retrogen Start
        doRetrogen = configFile.get(RETROGEN, "Retroactive Generation", doRetrogen).getBoolean(doRetrogen);
        // Retrogen End
        
        /* Entities */
        // Baby Heatscar Spider
        babyHeatscarSpiderDeathSpawnMaximum = configFile.get(ENTITIES, "Maximum Baby Heatscar Spiders on Heatscar Spider Death [default: 4]", babyHeatscarSpiderDeathSpawnMaximum).getInt(babyHeatscarSpiderDeathSpawnMaximum);
        if (babyHeatscarSpiderDeathSpawnMaximum < 0) babyHeatscarSpiderDeathSpawnMaximum = 0;
        
        babyHeatscarSpiderDeathSpawnMinimum = configFile.get(ENTITIES, "Minimum Baby Heatscar Spiders on Heatscar Spider Death [default: 2]", babyHeatscarSpiderDeathSpawnMinimum).getInt(babyHeatscarSpiderDeathSpawnMinimum);
        if (babyHeatscarSpiderDeathSpawnMinimum < 0) babyHeatscarSpiderDeathSpawnMinimum = 0;
        
        babyHeatscarSpiderSpawnMaximum = configFile.get(ENTITIES, "Baby Heatscar Spider Maximum Spawn Count [default: 4]", babyHeatscarSpiderSpawnMaximum).getInt(babyHeatscarSpiderSpawnMaximum);
        if (babyHeatscarSpiderSpawnMaximum < 0) babyHeatscarSpiderSpawnMaximum = 0;
        
        babyHeatscarSpiderSpawnMinimum = configFile.get(ENTITIES, "Baby Heatscar Spider Minimum Spawn Count [default: 4]", babyHeatscarSpiderSpawnMinimum).getInt(babyHeatscarSpiderSpawnMinimum);
        if (babyHeatscarSpiderSpawnMinimum < 0) babyHeatscarSpiderSpawnMinimum = 0;
        
        babyHeatscarSpiderWeight = configFile.get(ENTITIES, "Baby Heatscar Spider Spawn Weight [default: 10]", babyHeatscarSpiderWeight).getInt(babyHeatscarSpiderWeight);
        if (babyHeatscarSpiderWeight < 0) babyHeatscarSpiderWeight = 0;
        
        // Heatscar Spider
        heatscarSpiderSpawnMaximum = configFile.get(ENTITIES, "Heatscar Spider Maximum Spawn Count [default: 4]", heatscarSpiderSpawnMaximum).getInt(heatscarSpiderSpawnMaximum);
        if (heatscarSpiderSpawnMaximum < 0) heatscarSpiderSpawnMaximum = 0;
        
        heatscarSpiderSpawnMinimum = configFile.get(ENTITIES, "Heatscar Spider Minimum Spawn Count [default: 4]", heatscarSpiderSpawnMinimum).getInt(heatscarSpiderSpawnMinimum);
        if (heatscarSpiderSpawnMinimum < 0) heatscarSpiderSpawnMinimum = 0;
        
        heatscarSpiderWeight = configFile.get(ENTITIES, "Heatscar Spider Spawn Weight [default: 10]", babyHeatscarSpiderWeight).getInt(heatscarSpiderWeight);
        if (heatscarSpiderWeight < 0) heatscarSpiderWeight = 0;
        
        // Imp
        impSpawnMaximum = configFile.get(ENTITIES, "Imp Maximum Spawn Count [default: 12]", impSpawnMaximum).getInt(impSpawnMaximum);
        if (impSpawnMaximum < 0) impSpawnMaximum = 0;
        
        impSpawnMinimum = configFile.get(ENTITIES, "Imp Minimum Spawn Count [default: 8]", impSpawnMinimum).getInt(impSpawnMinimum);
        if (impSpawnMinimum < 0) impSpawnMinimum = 0;
        
        impWeight = configFile.get(ENTITIES, "Imp Spawn Weight [default: 10]", impWeight).getInt(impWeight);
        if (impWeight < 0) impWeight = 0;
        
        impVariants = configFile.get(ENTITIES, "Should Imps have more color variants?", impVariants).getBoolean(impVariants);
        
        // Nitro Creeper
        nitroCreeperFallExplosion = configFile.get(ENTITIES, "Should Nitro Creepers explode while moderate fall damage is taken? [default: true]", nitroCreeperFallExplosion).getBoolean(nitroCreeperFallExplosion);
        nitroCreeperInstantChargedExplosion = configFile.get(ENTITIES, "Should Nitro Creepers instantly explode while charged? [default: true]", nitroCreeperInstantChargedExplosion).getBoolean(nitroCreeperInstantChargedExplosion);
        nitroCreeperSpawnMaximum = configFile.get(ENTITIES, "Nitro Creeper Maximum Spawn Count [default: 6]", nitroCreeperSpawnMaximum).getInt(nitroCreeperSpawnMaximum);
        if (nitroCreeperSpawnMaximum < 0) nitroCreeperSpawnMaximum = 0;
        
        nitroCreeperSpawnMinimum = configFile.get(ENTITIES, "Nitro Creeper Minimum Spawn Count [default: 4]", nitroCreeperSpawnMinimum).getInt(nitroCreeperSpawnMinimum);
        if (nitroCreeperSpawnMinimum < 0) nitroCreeperSpawnMinimum = 0;
        
        nitroCreeperWeight = configFile.get(ENTITIES, "Nitro Creeper Spawn Weight [default: 8]", nitroCreeperWeight).getInt(nitroCreeperWeight);
        if (nitroCreeperWeight < 0) nitroCreeperWeight = 0;
        
        // Entities End

        canRespawnInNether = configFile.get(ENABLE_DISABLE, "Obelisks let players respawn in the Nether", canRespawnInNether).getBoolean(canRespawnInNether);

        // Trees Start
        generateRedwood = configFile.get(ENABLE_DISABLE, "Generate Redwood Trees", generateRedwood).getBoolean(generateRedwood);

        generateMaple = configFile.get(ENABLE_DISABLE, "Generate Maple Trees", generateMaple).getBoolean(generateMaple);
        generateSilverbell = configFile.get(ENABLE_DISABLE, "Generate Silverbell Trees", generateSilverbell).getBoolean(generateSilverbell);
        generateAmaranth = configFile.get(ENABLE_DISABLE, "Generate Amaranth Trees", generateAmaranth).getBoolean(generateAmaranth);
        generateTiger = configFile.get(ENABLE_DISABLE, "Generate Tigerwood Trees", generateTiger).getBoolean(generateTiger);

        generateWillow = configFile.get(ENABLE_DISABLE, "Generate Willow Trees", generateWillow).getBoolean(generateWillow);
        generateEucalyptus = configFile.get(ENABLE_DISABLE, "Generate Small Eucalyptus Trees", generateEucalyptus).getBoolean(generateEucalyptus);
        generateHopseed = configFile.get(ENABLE_DISABLE, "Generate Hopseed Trees", generateHopseed).getBoolean(generateHopseed);
        generateSakura = configFile.get(ENABLE_DISABLE, "Generate Sakura Trees", generateSakura).getBoolean(generateSakura);
        generateApple = configFile.get(ENABLE_DISABLE, "Generate Apple Trees", generateApple).getBoolean(generateApple);

        generateSaguaro = configFile.get(ENABLE_DISABLE, "Generate Saguaro Cactus", generateSaguaro).getBoolean(generateSaguaro);

        generateBloodwood = configFile.get(ENABLE_DISABLE, "Generate Bloodwood Trees", generateBloodwood).getBoolean(generateBloodwood);
        generateDarkwood = configFile.get(ENABLE_DISABLE, "Generate Darkwood Trees", generateDarkwood).getBoolean(generateDarkwood);
        generateFusewood = configFile.get(ENABLE_DISABLE, "Generate Fusewood Trees", generateFusewood).getBoolean(generateFusewood);
        generateGhostwood = configFile.get(ENABLE_DISABLE, "Generate Ghostwood Trees", generateGhostwood).getBoolean(generateGhostwood);
        // Trees End

        // Berries Start
        generateRaspberries = configFile.get(ENABLE_DISABLE, "Generate Raspberry Bushes", generateRaspberries).getBoolean(generateRaspberries);
        generateBlueberries = configFile.get(ENABLE_DISABLE, "Generate Blueberry Bushes", generateBlueberries).getBoolean(generateBlueberries);
        generateBlackberries = configFile.get(ENABLE_DISABLE, "Generate Blackberry Bushes", generateBlackberries).getBoolean(generateBlackberries);
        generateMaloberries = configFile.get(ENABLE_DISABLE, "Generate Maloberry Bushes", generateMaloberries).getBoolean(generateMaloberries);

        generateBlightberries = configFile.get(ENABLE_DISABLE, "Generate Blightberry Bushes", generateBlightberries).getBoolean(generateBlightberries);
        generateDuskberries = configFile.get(ENABLE_DISABLE, "Generate Duskberry Bushes", generateDuskberries).getBoolean(generateDuskberries);
        generateSkyberries = configFile.get(ENABLE_DISABLE, "Generate Skyberry Bushes", generateSkyberries).getBoolean(generateSkyberries);
        generateStingberries = configFile.get(ENABLE_DISABLE, "Generate Stingberry Bushes", generateStingberries).getBoolean(generateStingberries);
        // Berries End

        generateThornvines = configFile.get(ENABLE_DISABLE, "Generate Thornvines", generateThornvines).getBoolean(generateThornvines);

        //Cloud Start
        generateOverworldClouds = configFile.get(ENABLE_DISABLE, "Generate Overworld Clouds", generateOverworldClouds).getBoolean(generateOverworldClouds);
        generateSulfurClouds = configFile.get(ENABLE_DISABLE, "Generate Sulfur Clouds", generateSulfurClouds).getBoolean(generateSulfurClouds);
        generateAshClouds = configFile.get(ENABLE_DISABLE, "Generate Ash Clouds", generateAshClouds).getBoolean(generateAshClouds);
        generateDarkClouds = configFile.get(ENABLE_DISABLE, "Generate Dark Clouds", generateDarkClouds).getBoolean(generateDarkClouds);
        generateLegacyClouds = configFile.get(ENABLE_DISABLE, "Generate Legacy Clouds", generateLegacyClouds).getBoolean(generateLegacyClouds);

        enableCloudBlocks = configFile.get(ENABLE_DISABLE, "Enable Clouds", enableCloudBlocks).getBoolean(enableCloudBlocks);
        //Cloud End

        generateBarley = configFile.get(ENABLE_DISABLE, "Generate Barley Crops", generateBarley).getBoolean(generateBarley);
        generateCotton = configFile.get(ENABLE_DISABLE, "Generate Cotton Crops", generateCotton).getBoolean(generateCotton);
        generateBluebells = configFile.get(ENABLE_DISABLE, "Generate Bluebell Flowers", generateBluebells).getBoolean(generateBluebells);

        generateGreenglowshroom = configFile.get(ENABLE_DISABLE, "Generate Green Glowshroom", generateGreenglowshroom).getBoolean(generateGreenglowshroom);
        generatePurpleglowshroom = configFile.get(ENABLE_DISABLE, "Generate Purple Glowshroom", generatePurpleglowshroom).getBoolean(generatePurpleglowshroom);
        generateBlueglowshroom = configFile.get(ENABLE_DISABLE, "Generate Blue Glowshroom", generateBlueglowshroom).getBoolean(generateBlueglowshroom);
        generateGlowshroomtree = configFile.get(ENABLE_DISABLE, "Generate Glowshroom Trees", generateGlowshroomtree).getBoolean(generateGlowshroomtree);
        dropCotton = configFile.get(ENABLE_DISABLE, "Drop cotton seeds from grass", dropCotton).getBoolean(dropCotton);
        dropBarley = configFile.get(ENABLE_DISABLE, "Drop barley seeds from grass", dropBarley).getBoolean(dropBarley);
        enableStickVariants = configFile.get(ENABLE_DISABLE, "Enable stick variants", enableStickVariants).getBoolean(enableStickVariants);
        enableNoPoisonInFoods = configFile.get(ENABLE_DISABLE, "Enable Weakness instead of Poison in foods (Blightberry and Potash Apple)", true).getBoolean(true);
        try
        {
            Class.forName("chococraft.common.ModChocoCraft");
            enableWheatRecipe = configFile.get(ENABLE_DISABLE, "Enable wheat to flour recipe", false).getBoolean(false);
        }
        catch (Exception e)
        {
            enableWheatRecipe = configFile.get(ENABLE_DISABLE, "Enable wheat to flour recipe", true).getBoolean(true);
        }

        // Trees Start
        Property prop;
        prop = configFile.get(WORLDGEN, "Sea level", seaLevel);
        prop.setComment("Controls what the lowest Y level trees can grow at, make lower if the tree will not grow");
        seaLevel = prop.getInt(seaLevel);

        prop = configFile.get(WORLDGEN, "Flat Sea level", flatSeaLevel);
        prop.setComment("Controls what the lowest Y level trees can grow at in a flat world, make value lower if the tree will not grow");
        flatSeaLevel = prop.getInt(flatSeaLevel);

        redwoodBiomeTypes = configFile.get(WORLDGEN, "Redwood Tree Biome Types", redwoodBiomeTypes).getStringList();
        redwoodSpawnRarity = configFile.get(WORLDGEN, "Redwood Tree Spawn Rarity", redwoodSpawnRarity).getInt(redwoodSpawnRarity);
        redwoodSpawnRange = configFile.get(WORLDGEN, "Redwood Tree Spawn Range", redwoodSpawnRange).getInt(redwoodSpawnRange);

        mapleBiomeTypes = configFile.get(WORLDGEN, "Maple Tree Biome Types", mapleBiomeTypes).getStringList();
        mapleRarity = configFile.get(WORLDGEN, "Maple Tree Spawn Rarity", mapleRarity).getInt(mapleRarity);
        mapleSpawnRange = configFile.get(WORLDGEN, "Maple Tree Spawn Range", mapleSpawnRange).getInt(mapleSpawnRange);

        silverbellBiomeTypes = configFile.get(WORLDGEN, "Silverbell Tree Biome Types", silverbellBiomeTypes).getStringList();
        silverbellRarity = configFile.get(WORLDGEN, "Silverbell Tree Spawn Rarity", silverbellRarity).getInt(silverbellRarity);
        silverbellSpawnRange = configFile.get(WORLDGEN, "Silverbell Tree Spawn Range", silverbellSpawnRange).getInt(silverbellSpawnRange);

        amaranthBiomeTypes = configFile.get(WORLDGEN, "Amaranth Tree Biome Types", amaranthBiomeTypes).getStringList();
        amaranthRarity = configFile.get(WORLDGEN, "Amaranth Tree Spawn Rarity", amaranthRarity).getInt(amaranthRarity);
        amaranthSpawnRange = configFile.get(WORLDGEN, "Amaranth Tree Spawn Range", amaranthSpawnRange).getInt(amaranthSpawnRange);

        tigerBiomeTypes = configFile.get(WORLDGEN, "Tigerwood Tree Biome Types", tigerBiomeTypes).getStringList();
        tigerRarity = configFile.get(WORLDGEN, "Tigerwood Tree Spawn Rarity", tigerRarity).getInt(tigerRarity);
        tigerSpawnRange = configFile.get(WORLDGEN, "Tigerwood Tree Spawn Range", tigerSpawnRange).getInt(tigerSpawnRange);

        willowBiomeTypes = configFile.get(WORLDGEN, "Willow Tree Biome Types", willowBiomeTypes).getStringList();
        willowRarity = configFile.get(WORLDGEN, "Willow Tree Spawn Rarity", willowRarity).getInt(willowRarity);
        willowSpawnRange = configFile.get(WORLDGEN, "Willow Tree Spawn Range", willowSpawnRange).getInt(willowSpawnRange);

        eucalyptusBiomeTypes = configFile.get(WORLDGEN, "Eucalyptus Tree Biome Types", eucalyptusBiomeTypes).getStringList();
        eucalyptusSpawnRarity = configFile.get(WORLDGEN, "Eucalyptus Tree Spawn Rarity", eucalyptusSpawnRarity).getInt(eucalyptusSpawnRarity);
        eucalyptusSpawnRange = configFile.get(WORLDGEN, "Eucalyptus Tree Spawn Range", eucalyptusSpawnRange).getInt(eucalyptusSpawnRange);

        hopseedBiomeTypes = configFile.get(WORLDGEN, "Hopseed Tree Biome Types", hopseedBiomeTypes).getStringList();
        hopseedSpawnRarity = configFile.get(WORLDGEN, "Hopseed Tree Spawn Rarity", hopseedSpawnRarity).getInt(hopseedSpawnRarity);
        hopseedSpawnRange = configFile.get(WORLDGEN, "Hopseed Tree Spawn Range", hopseedSpawnRange).getInt(hopseedSpawnRange);

        sakuraBiomeTypes = configFile.get(WORLDGEN, "Sakura Tree Biome Types", sakuraBiomeTypes).getStringList();
        sakuraSpawnRarity = configFile.get(WORLDGEN, "Sakura Tree Spawn Rarity", sakuraSpawnRarity).getInt(sakuraSpawnRarity);
        sakuraSpawnRange = configFile.get(WORLDGEN, "Sakura Tree Spawn Range", sakuraSpawnRange).getInt(sakuraSpawnRange);

        appleBiomeTypes = configFile.get(WORLDGEN, "Apple Tree Biome Types", appleBiomeTypes).getStringList();
        appleSpawnRarity = configFile.get(WORLDGEN, "Apple Tree Spawn Rarity", appleSpawnRarity).getInt(appleSpawnRarity);
        appleSpawnRange = configFile.get(WORLDGEN, "Apple Tree Spawn Range", appleSpawnRange).getInt(appleSpawnRange);

        bloodwoodSpawnRarity = configFile.get(WORLDGEN, "Bloodwood Tree Spawn Rarity", bloodwoodSpawnRarity).getInt(bloodwoodSpawnRarity);
        darkwoodSpawnRarity = configFile.get(WORLDGEN, "Darkwood Tree Spawn Rarity", darkwoodSpawnRarity).getInt(darkwoodSpawnRarity);
        fusewoodSpawnRarity = configFile.get(WORLDGEN, "Fusewood Tree Spawn Rarity", fusewoodSpawnRarity).getInt(fusewoodSpawnRarity);
        ghostwoodSpawnRarity = configFile.get(WORLDGEN, "Ghostwood Tree Spawn Rarity", ghostwoodSpawnRarity).getInt(ghostwoodSpawnRarity);
        // Trees End

        saguaroBiomeTypes = configFile.get(WORLDGEN, "Saguaro Cactus Biome Types", saguaroBiomeTypes).getStringList();
        saguaroSpawnRarity = configFile.get(WORLDGEN, "Saguaro Cactus Spawn Rarity", saguaroSpawnRarity).getInt(saguaroSpawnRarity);
        saguaroSpawnRange = configFile.get(WORLDGEN, "Saguaro Cactus Spawn Range", saguaroSpawnRange).getInt(saguaroSpawnRange);

        // Berries Start
        raspberrySpawnRarity = configFile.get(WORLDGEN, "Raspberry Spawn Rarity", raspberrySpawnRarity).getInt(raspberrySpawnRarity);
        raspberrySpawnRange = configFile.get(WORLDGEN, "Raspberry Spawn Range", raspberrySpawnRange).getInt(raspberrySpawnRange);
        blueberrySpawnRarity = configFile.get(WORLDGEN, "Blueberry Spawn Rarity", blueberrySpawnRarity).getInt(blueberrySpawnRarity);
        blueberrySpawnRange = configFile.get(WORLDGEN, "Blueberry Spawn Range", blueberrySpawnRange).getInt(blueberrySpawnRange);
        blackberrySpawnRarity = configFile.get(WORLDGEN, "Blackberry Spawn Rarity", blackberrySpawnRarity).getInt(blackberrySpawnRarity);
        blackberrySpawnRange = configFile.get(WORLDGEN, "Blackberry Spawn Range", blackberrySpawnRange).getInt(blackberrySpawnRange);
        maloberrySpawnRarity = configFile.get(WORLDGEN, "Maloberry Spawn Rarity", maloberrySpawnRarity).getInt(maloberrySpawnRarity);
        maloberrySpawnRange = configFile.get(WORLDGEN, "Maloberry Spawn Range", maloberrySpawnRange).getInt(maloberrySpawnRange);

        blightberrySpawnRarity = configFile.get(WORLDGEN, "Blightberry Spawn Rarity", blightberrySpawnRarity).getInt(blightberrySpawnRarity);
        blightberrySpawnRange = configFile.get(WORLDGEN, "Blightberry Spawn Range", blightberrySpawnRange).getInt(blightberrySpawnRange);
        duskberrySpawnRarity = configFile.get(WORLDGEN, "Duskberry Spawn Rarity", duskberrySpawnRarity).getInt(duskberrySpawnRarity);
        duskberrySpawnRange = configFile.get(WORLDGEN, "Duskberry Spawn Range", duskberrySpawnRange).getInt(duskberrySpawnRange);
        skyberrySpawnRarity = configFile.get(WORLDGEN, "Skyberry Spawn Rarity", skyberrySpawnRarity).getInt(skyberrySpawnRarity);
        skyberrySpawnRange = configFile.get(WORLDGEN, "Skyberry Spawn Range", skyberrySpawnRange).getInt(skyberrySpawnRange);
        stingberrySpawnRarity = configFile.get(WORLDGEN, "Stingberry Spawn Rarity", stingberrySpawnRarity).getInt(stingberrySpawnRarity);
        stingberrySpawnRange = configFile.get(WORLDGEN, "Stingberry Spawn Range", stingberrySpawnRange).getInt(stingberrySpawnRange);
        // Berries End

        // Cloud Start
        cloudWhitelist = configFile.get(WORLDGEN, "Clouds Whitelist", cloudWhitelist).getIntList();
        darkCloudWhitelist = configFile.get(WORLDGEN, "Dark Clouds Whitelist", darkCloudWhitelist).getIntList();
        ashWhitelist = configFile.get(WORLDGEN, "Ash Clouds Whitelist", ashWhitelist).getIntList();
        sulfurCloudWhitelist = configFile.get(WORLDGEN, "Sulfur Clouds Whitelist", sulfurCloudWhitelist).getIntList();

        cloudSpawnRarity = configFile.get(WORLDGEN, "Cloud Spawn Rarity", cloudSpawnRarity).getInt(cloudSpawnRarity);
        cloudSpawnHeight = configFile.get(WORLDGEN, "Cloud Spawn Height", cloudSpawnHeight).getInt(cloudSpawnHeight);
        cloudSpawnRange = configFile.get(WORLDGEN, "Cloud Spawn Range", cloudSpawnRange).getInt(cloudSpawnRange);
        darkCloudSpawnRarity = configFile.get(WORLDGEN, "Dark Cloud Spawn Density", darkCloudSpawnRarity).getInt(darkCloudSpawnRarity);
        darkCloudSpawnHeight = configFile.get(WORLDGEN, "Dark Cloud Spawn Height", darkCloudSpawnHeight).getInt(darkCloudSpawnHeight);
        darkCloudSpawnRange = configFile.get(WORLDGEN, "Dark Cloud Spawn Range", darkCloudSpawnRange).getInt(darkCloudSpawnRange);
        sulfurSpawnRarity = configFile.get(WORLDGEN, "Sulfur Cloud Spawn Rarity", sulfurSpawnRarity).getInt(sulfurSpawnRarity);
        sulfurSpawnHeight = configFile.get(WORLDGEN, "Sulfur Cloud Spawn Height", sulfurSpawnHeight).getInt(sulfurSpawnHeight);
        sulfurSpawnRange = configFile.get(WORLDGEN, "Sulfur Cloud Spawn Range", sulfurSpawnRange).getInt(sulfurSpawnRange);
        ashSpawnRarity = configFile.get(WORLDGEN, "Ash Cloud Spawn Rarity", ashSpawnRarity).getInt(ashSpawnRarity);
        ashSpawnHeight = configFile.get(WORLDGEN, "Ash Cloud Spawn Height", ashSpawnHeight).getInt(ashSpawnHeight);
        ashSpawnRange = configFile.get(WORLDGEN, "Ash Cloud Spawn Range", ashSpawnRange).getInt(ashSpawnRange);
        // Cloud End

        // Mineable Start
        generateTaintedSoil = configFile.get(ENABLE_DISABLE, "Generate Tained Soil", generateTaintedSoil).getBoolean(generateTaintedSoil);
        generateHeatSand = configFile.get(ENABLE_DISABLE, "Generate Heat Sand", generateHeatSand).getBoolean(generateHeatSand);

        tainedSoilClusterCount = configFile.get(WORLDGEN, "Tainted Soil Cluster Count", tainedSoilClusterCount).getInt(tainedSoilClusterCount);
        heatSandClusterCount = configFile.get(WORLDGEN, "Heat Sand Cluster Count", heatSandClusterCount).getInt(heatSandClusterCount);

        tainedSoilClusterSize = configFile.get(WORLDGEN, "Tainted Soil Cluster Size", tainedSoilClusterSize).getInt(tainedSoilClusterSize);
        heatSandClusterSize = configFile.get(WORLDGEN, "Heat Sand Cluster Size", heatSandClusterSize).getInt(heatSandClusterSize);
        // Mineable End

        thornSpawnRarity = configFile.get(WORLDGEN, "Thornvines Spawn Rarity", thornSpawnRarity).getInt(thornSpawnRarity);

        overworldWorldGenWhitelist = configFile.get(WORLDGEN, "Overworld World Generation Dimension Whitelist", overworldWorldGenWhitelist).getIntList();
        netherWorldGenWhitelist = configFile.get(WORLDGEN, "Nether World Generation Dimension Whitelist", netherWorldGenWhitelist).getIntList();

        // save changes if any
        boolean changed = false;

        if (configFile.hasChanged())
        {
            configFile.save();
            changed = true;
        }

        return changed;
    }

    //@formatter:off
    // Clouds Start
    public static boolean generateOverworldClouds = false;
    public static boolean generateSulfurClouds = false;
    public static boolean generateAshClouds = false;
    public static boolean generateDarkClouds = true;
    public static boolean generateLegacyClouds = false;

    public static int[] cloudWhitelist = new int[] {0};
    public static int[] darkCloudWhitelist = new int[] {1};
    public static int[] ashWhitelist = new int[] {-1};
    public static int[] sulfurCloudWhitelist = new int[] {-1};

    public static boolean enableCloudBlocks = true;
    public static int cloudSpawnRarity = 10;
    public static int cloudSpawnHeight = 192;
    public static int cloudSpawnRange = 48;

    public static int darkCloudSpawnRarity = 2;
    public static int darkCloudSpawnHeight = 184;
    public static int darkCloudSpawnRange = 256;

    public static int sulfurSpawnRarity = 1;
    public static int sulfurSpawnHeight = 60;
    public static int sulfurSpawnRange = 1024;

    public static int ashSpawnRarity = 1;
    public static int ashSpawnHeight = 60;
    public static int ashSpawnRange = 1024;
    // Clouds End

    // Retrogen Start
    public static boolean doRetrogen = false;
    // Retrogen End

    // Entites Start
    public static int babyHeatscarSpiderDeathSpawnMaximum = 4;
    public static int babyHeatscarSpiderDeathSpawnMinimum = 2;
    public static int babyHeatscarSpiderSpawnMaximum = 4;
    public static int babyHeatscarSpiderSpawnMinimum = 4;
    public static int babyHeatscarSpiderWeight = 10;
    
    public static int heatscarSpiderSpawnMaximum = 4;
    public static int heatscarSpiderSpawnMinimum = 4;
    public static int heatscarSpiderWeight = 10;
    
    public static int impSpawnMaximum = 12;
    public static int impSpawnMinimum = 8;
    public static int impWeight = 10;
    public static boolean impVariants = true;
    
    public static boolean nitroCreeperFallExplosion = true;
    public static boolean nitroCreeperInstantChargedExplosion = true;
    public static int nitroCreeperSpawnMaximum = 6;
    public static int nitroCreeperSpawnMinimum = 4;
    public static int nitroCreeperWeight = 8;
    // Entites End

    public static int seaLevel = 64;
    public static int flatSeaLevel = 1;

    public static boolean canRespawnInNether = true;

    // Trees Start
    public static boolean generateRedwood = true;
    public static boolean generateMaple = true;
    public static boolean generateSilverbell = true;
    public static boolean generateAmaranth = true;
    public static boolean generateTiger = true;
    public static boolean generateWillow = true;
    public static boolean generateEucalyptus = true;
    public static boolean generateHopseed = true;
    public static boolean generateSakura = true;
    public static boolean generateApple = true;

    public static boolean generateBloodwood = true;
    public static boolean generateDarkwood = true;
    public static boolean generateFusewood = true;
    public static boolean generateGhostwood = true;

    public static String[] redwoodBiomeTypes = new String[] {"PLAINS"};
    public static int redwoodSpawnRarity = 200;
    public static int redwoodSpawnRange = 16;
    public static String[] mapleBiomeTypes = new String[] {"FOREST"};
    public static int mapleRarity = 10;
    public static int mapleSpawnRange = 48;
    public static String[] silverbellBiomeTypes = new String[] {"FOREST"};
    public static int silverbellRarity = 10;
    public static int silverbellSpawnRange = 48;
    public static String[] amaranthBiomeTypes = new String[] {"JUNGLE", "SAVANNA"};
    public static int amaranthRarity = 10;
    public static int amaranthSpawnRange = 48;
    public static String[] tigerBiomeTypes = new String[] {"FOREST"};
    public static int tigerRarity = 10;
    public static int tigerSpawnRange = 48;
    public static String[] willowBiomeTypes = new String[] {"RIVER", "SWAMP"};
    public static int willowRarity = 10;
    public static int willowSpawnRange = 16;
    public static String[] eucalyptusBiomeTypes = new String[] {"FOREST", "PLAINS", "MOUNTAIN", "HILLS"};
    public static int eucalyptusSpawnRarity = 30;
    public static int eucalyptusSpawnRange = 32;
    public static String[] hopseedBiomeTypes = new String[] {"MOUNTAIN", "HILLS"};
    public static int hopseedSpawnRarity = 10;
    public static int hopseedSpawnRange = 32;
    public static String[] sakuraBiomeTypes = new String[] {"FOREST", "RIVER"};
    public static int sakuraSpawnRarity = 30;
    public static int sakuraSpawnRange = 32;
    public static String[] appleBiomeTypes = new String[] {"FOREST", "PLAINS"};
    public static int appleSpawnRarity = 30;
    public static int appleSpawnRange = 48;

    public static int bloodwoodSpawnRarity = 14;
    public static int darkwoodSpawnRarity = 10;
    public static int fusewoodSpawnRarity = 50;
    public static int ghostwoodSpawnRarity = 10;
    // Trees End

    // Berries Start
    public static boolean generateRaspberries = true;
    public static boolean generateBlueberries = true;
    public static boolean generateBlackberries = true;
    public static boolean generateMaloberries = true;

    public static boolean generateBlightberries = true;
    public static boolean generateDuskberries = true;
    public static boolean generateSkyberries = true;
    public static boolean generateStingberries = true;

    public static int raspberrySpawnRarity = 30;
    public static int raspberrySpawnRange = 64;
    public static int blueberrySpawnRarity = 34;
    public static int blueberrySpawnRange = 64;
    public static int blackberrySpawnRarity = 48;
    public static int blackberrySpawnRange = 64;
    public static int maloberrySpawnRarity = 40;
    public static int maloberrySpawnRange = 64;

    public static int blightberrySpawnRarity = 18;
    public static int blightberrySpawnRange = 100;
    public static int duskberrySpawnRarity = 18;
    public static int duskberrySpawnRange = 100;
    public static int skyberrySpawnRarity = 18;
    public static int skyberrySpawnRange = 100;
    public static int stingberrySpawnRarity = 18;
    public static int stingberrySpawnRange = 100;
    // Berries End

    // Mineables Start
    public static boolean generateTaintedSoil = true;
    public static boolean generateHeatSand = true;

    public static int tainedSoilClusterCount = 4;
    public static int heatSandClusterCount = 4;

    public static int tainedSoilClusterSize = 33;
    public static int heatSandClusterSize = 33;
    // Mineables End

    // Overworld Start
    public static boolean generateBarley = true;
    public static boolean generateCotton = true;
    public static boolean generateBluebells = true;
    // Overworld End

    public static boolean generateGreenglowshroom = true;
    public static boolean generatePurpleglowshroom = true;
    public static boolean generateBlueglowshroom = true;
    public static boolean generateGlowshroomtree = true;

    public static String[] saguaroBiomeTypes = new String[] {"SANDY"};
    public static int saguaroSpawnRarity = 5;
    public static int saguaroSpawnRange = 16;

    public static int thornSpawnRarity = 40;

    public static boolean generateSaguaro = true;

    public static boolean generateThornvines = true;

    public static boolean enableNoPoisonInFoods = true;
    public static boolean enableWheatRecipe = true;
    public static boolean dropBarley = true;
    public static boolean dropCotton = true;

    public static boolean enableStickVariants = true;

    public static int[] overworldWorldGenWhitelist = new int[] {0};
    public static int[] netherWorldGenWhitelist = new int[] {-1};

    static Configuration configFile;
    //@formatter:on
}
