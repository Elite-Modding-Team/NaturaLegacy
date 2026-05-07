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

    public void generateCloud(Random random, World world, BlockPos pos) {
        if (Config.generateLegacyClouds)
        {
            generateLegacyCloud(random, world, pos);
            return;
        }

        int clouds = 10 + random.nextInt(10);

        for (int i = 0; i < clouds; i++)
        {
            int xOff = random.nextInt(16) - 8;
            int yOff = random.nextInt(4) - 2;
            int zOff = random.nextInt(16) - 8;

            int radiusX = random.nextInt(4) + 4;
            int radiusY = random.nextInt(2) + 2;
            int radiusZ = random.nextInt(4) + 4;

            if (this.flatCloud)
            {
                radiusY = 1;
            }

            generateCloud(world, pos.add(xOff, yOff, zOff), radiusX, radiusY, radiusZ);
        }
    }

    private void generateCloud(World world, BlockPos center, int rx, int ry, int rz) {
        for (int x = -rx; x <= rx; x++)
        {
            for (int y = -ry; y <= ry; y++)
            {
                for (int z = -rz; z <= rz; z++)
                {
                    double distance = (double)(x * x) / (rx * rx) +
                            (double)(y * y) / (ry * ry) +
                            (double)(z * z) / (rz * rz);

                    if (distance <= 0.9D)
                    {
                        BlockPos target = center.add(x, y, z);
                        if (world.getBlockState(target).getBlock() == Blocks.AIR)
                        {
                            world.setBlockState(target, this.cloud, 2);
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
