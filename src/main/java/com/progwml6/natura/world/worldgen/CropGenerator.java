package com.progwml6.natura.world.worldgen;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

import com.progwml6.natura.common.config.Config;
import com.progwml6.natura.overworld.NaturaOverworld;
import com.progwml6.natura.overworld.block.crops.BlockNaturaBarley;
import com.progwml6.natura.overworld.block.crops.BlockNaturaCotton;

public class CropGenerator implements IWorldGenerator
{
    public static final CropGenerator INSTANCE = new CropGenerator();

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
    {
        this.generateOverworld(random, chunkX, chunkZ, world);
    }

    public void retroGen(Random random, int chunkX, int chunkZ, World world)
    {
        this.generateOverworld(random, chunkX, chunkZ, world);

        world.getChunk(chunkX, chunkZ).markDirty();
    }

    public void generateOverworld(Random random, int chunkX, int chunkZ, World world)
    {
        int xPos = chunkX * 16 + 8;
        int zPos = chunkZ * 16 + 8;

        BlockPos chunkPos = new BlockPos(xPos, 0, zPos);

        Biome biome = world.getChunk(chunkPos).getBiome(chunkPos, world.getBiomeProvider());

        if (this.shouldGenerateInDimension(world.provider.getDimension()))
        {
            // Barley
            if (Config.generateBarley && random.nextInt(3) == 0 && this.goodClimate(biome, 0.11f, 1.0f, 0.11f, 2f))
            {
                final int posX = xPos + random.nextInt(16);
                final int posY = random.nextInt(128) + Config.seaLevel;
                final int posZ = zPos + random.nextInt(16);
                final BlockPos newPos = new BlockPos(posX, posY, posZ);

                this.generateBarley(world, random, newPos);
            }

            // Cotton
            if (Config.generateCotton && random.nextInt(6) == 0 && this.goodClimate(biome, 0.11f, 1.0f, 0.11f, 2f))
            {
                final int posX = xPos + random.nextInt(16);
                final int posZ = zPos + random.nextInt(16);
                final int posY = random.nextInt(128) + Config.seaLevel;
                final BlockPos newPos = new BlockPos(posX, posY, posZ);

                this.generateCotton(world, random, newPos);
            }

            // Bluebells
            if (Config.generateBluebells && random.nextInt(6) == 0)
            {
                final int posX = xPos + random.nextInt(16);
                final int posZ = zPos + random.nextInt(16);
                final int posY = random.nextInt(128) + Config.seaLevel;
                final BlockPos newPos = new BlockPos(posX, posY, posZ);

                this.generateBluebells(world, random, newPos);
            }
        }
    }

    public void generateBarley(World world, Random random, BlockPos position)
    {
        IBlockState state = NaturaOverworld.barleyCrop.getDefaultState().withProperty(BlockNaturaBarley.AGE, 3);

        for (int tries = 0; tries < 64; tries++)
        {
            BlockPos blockpos = position.add(random.nextInt(8) - random.nextInt(8), random.nextInt(4) - random.nextInt(4), random.nextInt(8) - random.nextInt(8));

            if (world.isAirBlock(blockpos) && NaturaOverworld.barleyCrop.canBlockStay(world, blockpos, state))
            {
                world.setBlockState(blockpos, state, 2);
            }
        }
    }

    public void generateCotton(World world, Random random, BlockPos position)
    {
        IBlockState state = NaturaOverworld.cottonCrop.getDefaultState().withProperty(BlockNaturaCotton.AGE, 4);

        for (int tries = 0; tries < 64; tries++)
        {
            BlockPos blockpos = position.add(random.nextInt(8) - random.nextInt(8), random.nextInt(4) - random.nextInt(4), random.nextInt(8) - random.nextInt(8));

            if (world.isAirBlock(blockpos) && NaturaOverworld.cottonCrop.canBlockStay(world, blockpos, state))
            {
                world.setBlockState(blockpos, state, 2);
            }
        }
    }

    public void generateBluebells(World world, Random random, BlockPos position)
    {
        IBlockState state = NaturaOverworld.bluebellsFlower.getDefaultState();

        for (int tries = 0; tries < 40; tries++)
        {
            BlockPos blockpos = position.add(random.nextInt(8) - random.nextInt(8), random.nextInt(8) - random.nextInt(8), random.nextInt(8) - random.nextInt(8));

            if (world.isAirBlock(blockpos) && NaturaOverworld.bluebellsFlower.canBlockStay(world, blockpos, state))
            {
                world.setBlockState(blockpos, state, 2);
            }
        }
    }

    public boolean goodClimate(Biome biome, float minTemp, float maxTemp, float minRain, float maxRain)
    {
        float temp = biome.getDefaultTemperature();
        float rain = biome.getRainfall();

        return minTemp <= temp && temp <= maxTemp && minRain <= rain && rain <= maxRain;
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

}
