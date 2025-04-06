package com.progwml6.natura.world.worldgen.clouds;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

import com.progwml6.natura.common.config.Config;
import java.util.Random;

public class CloudsGenerator implements IWorldGenerator
{
    public final int cloudSize;

    public final IBlockState cloud;

    public final boolean flatCloud;

    public CloudsGenerator(int cloudSize, IBlockState cloud, boolean flatCloud)
    {
        this.cloudSize = cloudSize;
        this.cloud = cloud;
        this.flatCloud = flatCloud;
    }

    public CloudsGenerator(int cloudSize, IBlockState cloud)
    {
        this(cloudSize, cloud, false);
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
    {
    }

    public void generateCloud(Random random, World world, BlockPos pos)
    {
        if (Config.generateLegacyClouds)
        {
            generateLegacyCloud(random, world, pos);
            return;
        }

        int xRand = random.nextInt(3) - 1;
        int zRand = random.nextInt(3) - 1;

        for (int block = 0; block < this.cloudSize; block++)
        {
            int xIter = pos.getX() + (random.nextInt(3) - 1) + xRand;
            int yIter = pos.getY();
            int zIter = pos.getZ() + (random.nextInt(3) - 1) + zRand;

            if (random.nextBoolean() && !this.flatCloud || this.flatCloud && random.nextInt(10) == 0)
            {
                yIter = pos.getY() + random.nextInt(3) - 1;
            }

            for (int x = xIter; x < xIter + random.nextInt(4) + 3 * (this.flatCloud ? 3 : 1); x++)
            {
                int mathX = xIter - x;

                for (int y = yIter; y < yIter + random.nextInt(1) + 2; y++)
                {
                    int mathY = yIter - y;

                    for (int z = zIter; z < zIter + random.nextInt(4) + 3 * (this.flatCloud ? 3 : 1); z++)
                    {
                        int mathZ = zIter - z;

                        if (Math.abs(mathX) + Math.abs(mathY) + Math.abs(mathZ) < 4 * (this.flatCloud ? 3 : 1) + random.nextInt(2))
                        {
                            BlockPos blockpos = new BlockPos(x, y, z);
                            IBlockState state = world.getBlockState(blockpos);

                            if (state.getBlock() == Blocks.AIR)
                            {
                                world.setBlockState(blockpos, this.cloud, 2);
                            }
                        }
                    }
                }
            }
        }
    }

    public void generateLegacyCloud(Random random, World world, BlockPos pos)
    {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        int l = random.nextInt(3) - 1;
        int i1 = random.nextInt(3) - 1;
        BlockPos.MutableBlockPos blockPos = new BlockPos.MutableBlockPos();

        for (int j1 = 0; j1 < cloudSize; j1++)
        {
            x += (random.nextInt(3) - 1) + l;
            z += (random.nextInt(3) - 1) + i1;
            if (random.nextBoolean() && !flatCloud || flatCloud && random.nextInt(10) == 0)
            {
                y += random.nextInt(3) - 1;
            }
            for (int xIter = x; xIter < x + random.nextInt(4) + 3 * (flatCloud ? 3 : 1); xIter++)
            {
                for (int yIter = y; yIter < y + random.nextInt(1) + 2; yIter++)
                {
                    for (int zIter = z; zIter < z + random.nextInt(4) + 3 * (flatCloud ? 3 : 1); zIter++)
                    {
                        blockPos.setPos(xIter, yIter, zIter);
                        if (world.isAirBlock(blockPos) && Math.abs(xIter - x) + Math.abs(yIter - y) + Math.abs(zIter - z) < 4 * (flatCloud ? 3 : 1) + random.nextInt(2))
                        {
                            world.setBlockState(blockPos, cloud, 2);
                        }
                    }
                }
            }
        }
    }
}
