package com.progwml6.natura.world.worldgen;

import java.util.Random;
import com.progwml6.natura.common.config.Config;
import com.progwml6.natura.overworld.NaturaOverworld;
import com.progwml6.natura.overworld.block.leaves.BlockAppleLeaves;
import com.progwml6.natura.overworld.block.leaves.BlockRedwoodLeaves;
import com.progwml6.natura.overworld.block.logs.BlockAppleLog;
import com.progwml6.natura.overworld.block.logs.BlockOverworldLog;
import com.progwml6.natura.overworld.block.logs.BlockOverworldLog2;
import com.progwml6.natura.overworld.block.logs.BlockRedwoodLog;
import com.progwml6.natura.world.worldgen.saguaro.SaguaroGenerator;
import com.progwml6.natura.world.worldgen.trees.overworld.AppleTreeGenerator;
import com.progwml6.natura.world.worldgen.trees.overworld.EucalyptusTreeGenerator;
import com.progwml6.natura.world.worldgen.trees.overworld.HopseedTreeGenerator;
import com.progwml6.natura.world.worldgen.trees.overworld.OverworldTreeGenerator;
import com.progwml6.natura.world.worldgen.trees.overworld.RedwoodTreeGenerator;
import com.progwml6.natura.world.worldgen.trees.overworld.SakuraTreeGenerator;
import com.progwml6.natura.world.worldgen.trees.overworld.WillowTreeGenerator;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class OverworldTreesGenerator
{
    public static final OverworldTreesGenerator INSTANCE = new OverworldTreesGenerator();

    //@formatter:off
    OverworldTreeGenerator mapleTreeGen;
    OverworldTreeGenerator silverbellTreeGen;
    OverworldTreeGenerator amaranthTreeGen;
    OverworldTreeGenerator tigerTreeGen;
    WillowTreeGenerator willowTreeGen;
    EucalyptusTreeGenerator eucalyptusTreeGen;
    HopseedTreeGenerator hopseedTreeGen;
    SakuraTreeGenerator sakuraTreeGen;
    RedwoodTreeGenerator redwoodTreeGen;
    AppleTreeGenerator appleTreeGen;
    SaguaroGenerator saguaroGen;
    //@formatter:on

    public OverworldTreesGenerator()
    {
        IBlockState log = NaturaOverworld.overworldLog.getDefaultState();
        IBlockState leaves = NaturaOverworld.overworldLeaves.getDefaultState();

        this.mapleTreeGen = new OverworldTreeGenerator(4, 2, log.withProperty(BlockOverworldLog.TYPE, BlockOverworldLog.LogType.MAPLE), leaves.withProperty(BlockOverworldLog.TYPE, BlockOverworldLog.LogType.MAPLE));
        this.silverbellTreeGen = new OverworldTreeGenerator(4, 2, log.withProperty(BlockOverworldLog.TYPE, BlockOverworldLog.LogType.SILVERBELL), leaves.withProperty(BlockOverworldLog.TYPE, BlockOverworldLog.LogType.SILVERBELL));
        this.amaranthTreeGen = new OverworldTreeGenerator(9, 8, log.withProperty(BlockOverworldLog.TYPE, BlockOverworldLog.LogType.AMARANTH), leaves.withProperty(BlockOverworldLog.TYPE, BlockOverworldLog.LogType.AMARANTH));
        this.tigerTreeGen = new OverworldTreeGenerator(6, 4, log.withProperty(BlockOverworldLog.TYPE, BlockOverworldLog.LogType.TIGER), leaves.withProperty(BlockOverworldLog.TYPE, BlockOverworldLog.LogType.TIGER));

        IBlockState log2 = NaturaOverworld.overworldLog2.getDefaultState();
        IBlockState leaves2 = NaturaOverworld.overworldLeaves2.getDefaultState();

        this.willowTreeGen = new WillowTreeGenerator(4, 5, log2.withProperty(BlockOverworldLog2.TYPE, BlockOverworldLog2.LogType.WILLOW), leaves2.withProperty(BlockOverworldLog2.TYPE, BlockOverworldLog2.LogType.WILLOW));
        this.eucalyptusTreeGen = new EucalyptusTreeGenerator(6, 3, log2.withProperty(BlockOverworldLog2.TYPE, BlockOverworldLog2.LogType.EUCALYPTUS), leaves2.withProperty(BlockOverworldLog2.TYPE, BlockOverworldLog2.LogType.EUCALYPTUS));
        this.hopseedTreeGen = new HopseedTreeGenerator(2, 3, log2.withProperty(BlockOverworldLog2.TYPE, BlockOverworldLog2.LogType.HOPSEED), leaves2.withProperty(BlockOverworldLog2.TYPE, BlockOverworldLog2.LogType.HOPSEED));
        this.sakuraTreeGen = new SakuraTreeGenerator(log2.withProperty(BlockOverworldLog2.TYPE, BlockOverworldLog2.LogType.SAKURA), leaves2.withProperty(BlockOverworldLog2.TYPE, BlockOverworldLog2.LogType.SAKURA), true, false);

        IBlockState redwoodLog = NaturaOverworld.redwoodLog.getDefaultState();
        IBlockState redwoodLeaves = NaturaOverworld.redwoodLeaves.getDefaultState();

        this.redwoodTreeGen = new RedwoodTreeGenerator(redwoodLog.withProperty(BlockRedwoodLog.TYPE, BlockRedwoodLog.RedwoodType.BARK), redwoodLog.withProperty(BlockRedwoodLog.TYPE, BlockRedwoodLog.RedwoodType.HEART), redwoodLog.withProperty(BlockRedwoodLog.TYPE, BlockRedwoodLog.RedwoodType.ROOT), redwoodLeaves.withProperty(BlockRedwoodLeaves.TYPE, BlockRedwoodLeaves.RedwoodType.NORMAL), false, false);

        IBlockState appleLog = NaturaOverworld.appleLog.getDefaultState().withProperty(BlockAppleLog.TYPE, BlockAppleLog.LogType.APPLE);
        IBlockState appleLeavesNormal = NaturaOverworld.appleLeaves.getDefaultState().withProperty(BlockAppleLeaves.TYPE, BlockAppleLeaves.LeavesType.NORMAL);
        IBlockState appleLeavesFlowering = NaturaOverworld.appleLeaves.getDefaultState().withProperty(BlockAppleLeaves.TYPE, BlockAppleLeaves.LeavesType.FLOWERING);
        IBlockState appleLeavesFruiting = NaturaOverworld.appleLeaves.getDefaultState().withProperty(BlockAppleLeaves.TYPE, BlockAppleLeaves.LeavesType.FRUIT);
        IBlockState appleLeavesFruitingGolden = NaturaOverworld.appleLeaves.getDefaultState().withProperty(BlockAppleLeaves.TYPE, BlockAppleLeaves.LeavesType.GOLDEN_FRUIT);
        this.appleTreeGen = new AppleTreeGenerator(6, 4, appleLog, appleLeavesNormal, appleLeavesFlowering, appleLeavesFruiting, appleLeavesFruitingGolden);

        this.saguaroGen = new SaguaroGenerator(NaturaOverworld.saguaro.getDefaultState(), true, false);
    }

    @SubscribeEvent
    public void onDecorate(DecorateBiomeEvent.Decorate event)
    {
        if (event.getType() == DecorateBiomeEvent.Decorate.EventType.TREE)
        {
            this.generateOverworld(event.getRand(), event.getChunkPos().x, event.getChunkPos().z, event.getWorld(), false);
        }
    }

    public void retroGen(Random random, int chunkX, int chunkZ, World world)
    {
        this.generateOverworld(random, chunkX, chunkZ, world, true);

        world.getChunk(chunkX, chunkZ).markDirty();
    }

    public void generateOverworld(Random random, int chunkX, int chunkZ, World world, boolean retroGen)
    {
        int xSpawn;
        int ySpawn;
        int zSpawn;

        int xPos = chunkX * 16 + 8;
        int zPos = chunkZ * 16 + 8;

        BlockPos chunkPos = new BlockPos(xPos, 0, zPos);

        BlockPos position;

        Biome biome = world.getChunk(chunkPos).getBiome(chunkPos, world.getBiomeProvider());

        if (this.shouldGenerateInDimension(world.provider.getDimension()))
        {
            if (Config.generateSakura && matchesBiomeTypes(biome, Config.sakuraBiomeTypes) && random.nextInt(Config.sakuraSpawnRarity) == 0)
            {
                xSpawn = xPos + random.nextInt(16);
                ySpawn = Config.sakuraSpawnRange + Config.seaLevel;
                zSpawn = zPos + random.nextInt(16);
                position = new BlockPos(xSpawn, ySpawn, zSpawn);

                this.sakuraTreeGen.generateTree(random, world, position);
            }

            if (Config.generateEucalyptus && matchesBiomeTypes(biome, Config.eucalyptusBiomeTypes) && random.nextInt(Config.eucalyptusSpawnRarity) == 0)
            {
                xSpawn = xPos + random.nextInt(16);
                ySpawn = Config.eucalyptusSpawnRange + Config.seaLevel;
                zSpawn = zPos + random.nextInt(16);
                position = new BlockPos(xSpawn, ySpawn, zSpawn);

                this.eucalyptusTreeGen.generateTree(random, world, position);
            }

            if (Config.generateMaple && matchesBiomeTypes(biome, Config.mapleBiomeTypes) && random.nextInt(Config.mapleRarity) == 0)
            {
                xSpawn = xPos + random.nextInt(16);
                ySpawn = Config.mapleSpawnRange + Config.seaLevel;
                zSpawn = zPos + random.nextInt(16);
                position = new BlockPos(xSpawn, ySpawn, zSpawn);

                this.mapleTreeGen.generateTree(random, world, position);
            }

            if (Config.generateSilverbell && matchesBiomeTypes(biome, Config.silverbellBiomeTypes) && random.nextInt(Config.silverbellRarity) == 0)
            {
                xSpawn = xPos + random.nextInt(16);
                ySpawn = Config.silverbellSpawnRange + Config.seaLevel;
                zSpawn = zPos + random.nextInt(16);
                position = new BlockPos(xSpawn, ySpawn, zSpawn);

                this.silverbellTreeGen.generateTree(random, world, position);
            }

            if (Config.generateTiger && matchesBiomeTypes(biome, Config.tigerBiomeTypes) && random.nextInt(Config.tigerRarity) == 0)
            {
                xSpawn = xPos + random.nextInt(16);
                ySpawn = Config.tigerSpawnRange + Config.seaLevel;
                zSpawn = zPos + random.nextInt(16);
                position = new BlockPos(xSpawn, ySpawn, zSpawn);

                this.tigerTreeGen.generateTree(random, world, position);
            }

            if (Config.generateApple && matchesBiomeTypes(biome, Config.appleBiomeTypes) && random.nextInt(Config.appleSpawnRarity) == 0)
            {
                xSpawn = xPos + random.nextInt(16);
                ySpawn = Config.appleSpawnRange + Config.seaLevel;
                zSpawn = zPos + random.nextInt(16);
                position = new BlockPos(xSpawn, ySpawn, zSpawn);

                this.appleTreeGen.generateTree(random, world, position);
            }

            if (!retroGen && Config.generateRedwood && matchesBiomeTypes(biome, Config.redwoodBiomeTypes) && random.nextInt(Config.redwoodSpawnRarity) == 0)
            {
                xSpawn = xPos + random.nextInt(16);
                ySpawn = Config.redwoodSpawnRange + Config.seaLevel;
                zSpawn = zPos + random.nextInt(16);
                position = new BlockPos(xSpawn, ySpawn, zSpawn);

                this.redwoodTreeGen.generateTree(random, world, position);
            }

            if (Config.generateHopseed && matchesBiomeTypes(biome, Config.hopseedBiomeTypes) && random.nextInt(Config.hopseedSpawnRarity) == 0)
            {
                xSpawn = xPos + random.nextInt(16);
                ySpawn = Config.hopseedSpawnRange + Config.seaLevel;
                zSpawn = zPos + random.nextInt(16);
                position = new BlockPos(xSpawn, ySpawn, zSpawn);

                this.hopseedTreeGen.generateTree(random, world, position);
            }

            if (Config.generateAmaranth && matchesBiomeTypes(biome, Config.amaranthBiomeTypes))
            {
                boolean isJungle = BiomeDictionary.hasType(biome, BiomeDictionary.Type.getType("JUNGLE"));
                if (isJungle || random.nextInt(Config.amaranthRarity) == 0)
                {
                    xSpawn = xPos + random.nextInt(16);
                    ySpawn = Config.amaranthSpawnRange + Config.seaLevel;
                    zSpawn = zPos + random.nextInt(16);
                    position = new BlockPos(xSpawn, ySpawn, zSpawn);

                    this.amaranthTreeGen.generateTree(random, world, position);
                }
            }

            if (Config.generateWillow && matchesBiomeTypes(biome, Config.willowBiomeTypes) && random.nextInt(Config.willowRarity) == 0)
            {
                int willowCount = BiomeDictionary.hasType(biome, BiomeDictionary.Type.getType("SWAMP")) ? 3 : 1;
                for (int i = 0; i < willowCount; i++)
                {
                    xSpawn = xPos + random.nextInt(16);
                    ySpawn = Config.willowSpawnRange + Config.seaLevel;
                    zSpawn = zPos + random.nextInt(16);
                    position = new BlockPos(xSpawn, ySpawn, zSpawn);

                    this.willowTreeGen.generateTree(random, world, position);
                }
            }

            if (Config.generateSaguaro && matchesBiomeTypes(biome, Config.saguaroBiomeTypes) && random.nextInt(Config.saguaroSpawnRarity) == 0)
            {
                xSpawn = xPos + random.nextInt(16);
                ySpawn = Config.saguaroSpawnRange + Config.seaLevel;
                zSpawn = zPos + random.nextInt(16);
                position = new BlockPos(xSpawn, ySpawn, zSpawn);

                this.saguaroGen.generateSaguaro(random, world, position);
            }
        }
    }

    public boolean shouldGenerateInDimension(int dimension)
    {
        for (int dimensionId : Config.overworldWorldGenWhitelist)
        {
            if (dimension == dimensionId)
            {
                return true;
            }
        }

        return false;
    }

	public boolean matchesBiomeTypes(Biome biome, String[] typeNames)
	{
		// Blacklisted biome types
		for (String name : typeNames)
		{
			if (name.startsWith("!"))
			{
				BiomeDictionary.Type type = BiomeDictionary.Type.getType(name.substring(1));
				if (BiomeDictionary.hasType(biome, type))
				{
					return false;
				}
			}
		}
		// Whitelisted biome types
		for (String name : typeNames)
		{
			if (!name.startsWith("!"))
			{
				BiomeDictionary.Type type = BiomeDictionary.Type.getType(name);
				if (BiomeDictionary.hasType(biome, type))
				{
					return true;
				}
			}
		}

		return false;
	}
}
