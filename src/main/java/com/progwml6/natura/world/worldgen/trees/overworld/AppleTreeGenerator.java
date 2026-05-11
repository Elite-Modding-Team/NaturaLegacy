package com.progwml6.natura.world.worldgen.trees.overworld;

import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class AppleTreeGenerator extends OverworldTreeGenerator
{
    public final int minTreeHeight;

    public final int treeHeightRange;

    public final IBlockState log;

    public final IBlockState leaves;

    public final IBlockState flowering;

    public final IBlockState fruiting;

    public final IBlockState fruitingGolden;

    public final boolean seekHeight;

    public final boolean isSapling;

    public AppleTreeGenerator(int treeHeight, int treeRange, IBlockState log, IBlockState leaves, IBlockState flowering, IBlockState fruiting, IBlockState fruitingGolden, boolean seekHeight, boolean isSapling)
    {
        super(treeHeight, treeRange, log, leaves, seekHeight, isSapling);
        this.minTreeHeight = treeHeight;
        this.treeHeightRange = treeRange;
        this.log = log;
        this.leaves = leaves;
        this.flowering = flowering;
        this.fruiting = fruiting;
        this.fruitingGolden = fruitingGolden;
        this.seekHeight = seekHeight;
        this.isSapling = isSapling;
    }

    public AppleTreeGenerator(int treeHeight, int treeRange, IBlockState log, IBlockState leaves, IBlockState flowering, IBlockState fruiting, IBlockState fruitingGolden)
    {
        this(treeHeight, treeRange, log, leaves, flowering, fruiting, fruitingGolden, true, false);
    }

    @Override
    protected void placeCanopy(World world, Random random, BlockPos pos, int height)
    {
        for (int y = pos.getY() - 3 + height; y <= pos.getY() + height; ++y)
        {
            int subtract = y - (pos.getY() + height);
            int subtract2 = 1 - subtract / 2;

            for (int x = pos.getX() - subtract2; x <= pos.getX() + subtract2; ++x)
            {
                int mathX = x - pos.getX();

                for (int z = pos.getZ() - subtract2; z <= pos.getZ() + subtract2; ++z)
                {
                    int mathZ = z - pos.getZ();

                    if (Math.abs(mathX) != subtract2 || Math.abs(mathZ) != subtract2 || random.nextInt(2) != 0 && subtract != 0)
                    {
                        BlockPos blockpos = new BlockPos(x, y, z);
                        setBlockAndMetadata(world, blockpos, this.getRandomizedLeaves(random));
                    }
                }
            }
        }
    }

    protected IBlockState getRandomizedLeaves(Random random)
    {
        int chance = random.nextInt(200);

        if (chance == 0)
        {
            return this.fruitingGolden;
        }
        else if (chance < 25)
        {
            return this.fruiting;
        }
        else if (chance < 40)
        {
            return this.flowering;
        }
        else
        {
            return this.leaves;
        }
    }
}
