package com.progwml6.natura.world.worldgen.trees.nether;

import java.util.Random;
import com.progwml6.natura.nether.NaturaNether;
import com.progwml6.natura.world.worldgen.trees.BaseTreeGenerator;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BloodwoodTreeGenerator extends BaseTreeGenerator
{
    public final IBlockState log;
    public final IBlockState leaves;

    public BloodwoodTreeGenerator(IBlockState log, IBlockState leaves)
    {
        this.log = log;
        this.leaves = leaves;
    }

    @Override
    public void generateTree(Random random, World world, BlockPos pos)
    {
        int treeHeight = random.nextInt(8) + 8;

        pos = this.findCeiling(world, pos);
        if (pos.getY() < 0)
        {
            return;
        }

        for (int heightIter = 0; heightIter < treeHeight; heightIter++)
        {
            Block localBlock = world.getBlockState(pos.add(0, -heightIter, 0)).getBlock();
            if (localBlock == Blocks.AIR || localBlock == NaturaNether.netherLeaves)
            {
                this.setBlockAndMetadata(world, pos.add(0, -heightIter, 0), this.log);
                this.setBlockAndMetadata(world, pos.add(1, -heightIter, 0), this.log);
                this.setBlockAndMetadata(world, pos.add(0, -heightIter, 1), this.log);
                this.setBlockAndMetadata(world, pos.add(1, -heightIter, 1), this.log);
            }
        }

        this.genBranch(world, random, pos, -treeHeight, 1);
        this.genBranch(world, random, pos.add(1, 0, 0), -treeHeight, 2);
        this.genBranch(world, random, pos.add(0, 0, 1), -treeHeight, 3);
        this.genBranch(world, random, pos.add(1, 0, 1), -treeHeight, 4);

        this.genStraightBranch(world, random, pos, -treeHeight, 1);
        this.genStraightBranch(world, random, pos.add(1, 0, 0), -treeHeight, 2);
        this.genStraightBranch(world, random, pos.add(0, 0, 1), -treeHeight, 3);
        this.genStraightBranch(world, random, pos.add(1, 0, 1), -treeHeight, 4);
    }

    public void generateNode(World world, BlockPos pos)
    {
        this.setBlockAndMetadata(world, pos, this.log);

        for (int xIter = pos.getX() - 1; xIter <= pos.getX() + 1; xIter++)
        {
            for (int zIter = pos.getZ() - 1; zIter <= pos.getZ() + 1; zIter++)
            {
                BlockPos newPos = new BlockPos(xIter, pos.getY(), zIter);
                IBlockState state = world.getBlockState(newPos);
                Block block = state.getBlock();

                if (block != NaturaNether.netherLeaves && !state.isFullBlock())
                {
                    this.setBlockAndMetadata(world, newPos, this.leaves);
                }
            }
        }

        for (int xIter = pos.getX() - 1; xIter <= pos.getX() + 1; xIter++)
        {
            for (int zIter = pos.getZ() - 2; zIter <= pos.getZ() + 2; zIter++)
            {
                BlockPos newPos = new BlockPos(xIter, pos.getY(), zIter);
                IBlockState state = world.getBlockState(newPos);
                Block block = state.getBlock();

                if (block != NaturaNether.netherLeaves && !state.isFullBlock())
                {
                    this.setBlockAndMetadata(world, newPos, this.leaves);
                }
            }
        }

        for (int xIter = pos.getX() - 2; xIter <= pos.getX() + 2; xIter++)
        {
            for (int zIter = pos.getZ() - 1; zIter <= pos.getZ() + 1; zIter++)
            {
                BlockPos newPos = new BlockPos(xIter, pos.getY() + 1, zIter);
                IBlockState state = world.getBlockState(newPos);
                Block block = state.getBlock();

                if (block != NaturaNether.netherLeaves && !state.isFullBlock())
                {
                    this.setBlockAndMetadata(world, newPos, this.leaves);
                }
            }
        }
    }

    protected void setBlockAndMetadata(World world, BlockPos pos, IBlockState stateNew)
    {
        world.setBlockState(pos, stateNew, 2);
    }

    BlockPos findCeiling(World world, BlockPos pos)
    {
        int returnHeight = 0;

        int height = pos.getY();

        do
        {
            BlockPos position = new BlockPos(pos.getX(), height, pos.getZ());

            Block block = world.getBlockState(position).getBlock();

            if ((block == Blocks.NETHERRACK || block == Blocks.SOUL_SAND || block == NaturaNether.netherTaintedSoil) && !world.getBlockState(position.down()).isFullBlock())
            {
                returnHeight = height - 1;
                break;
            }

            height++;
        } while (height <= world.provider.getActualHeight());

        return new BlockPos(pos.getX(), returnHeight, pos.getZ());
    }

    private void genBranch(World world, Random random, BlockPos pos, int height, int direction)
    {
        int posX = pos.getX();
        int posY = pos.getY() + height;
        int posZ = pos.getZ();
        byte offsetX = 0;
        byte offsetZ = 0;

        switch (direction)
        {
            case 1:
                offsetX = 1;
                offsetZ = 1;
                break;
            case 2:
                offsetX = -1;
                offsetZ = 1;
                break;
            case 3:
                offsetX = 1;
                offsetZ = -1;
                break;
            case 4:
                offsetX = -1;
                offsetZ = -1;
                break;
        }

        int heightShift = random.nextInt(6);

        for (int bIter = 4; bIter > 0; bIter--)
        {
            if (heightShift % 3 != 0)
            {
                posX += offsetX;
            }

            if (heightShift % 3 != 1)
            {
                posZ += offsetZ;
            }

            int branch = heightShift % 3 - 1;

            posY += branch;

            BlockPos blockpos = new BlockPos(posX, posY, posZ);

            this.generateNode(world, blockpos);

            heightShift = random.nextInt(6);
        }
    }

    private void genStraightBranch(World world, Random random, BlockPos pos, int height, int direction)
    {
        int posX = pos.getX();
        int posY = pos.getY() + height;
        int posZ = pos.getZ();

        byte xShift = 0;
        byte zShift = 0;

        switch (direction)
        {
            case 1:
                xShift = 1;
                break;
            case 2:
                zShift = 1;
                break;
            case 3:
                xShift = -1;
                break;
            case 4:
                zShift = -1;
                break;
        }

        int heightShift = random.nextInt(6);

        for (int j2 = 4; j2 > 0; j2--)
        {
            if (xShift == 0)
            {
                posX = (posX + random.nextInt(3)) - 1;
                posZ += zShift;
            }

            if (zShift == 0)
            {
                posX += xShift;
                posZ = (posZ + random.nextInt(3)) - 1;
            }

            int branch = heightShift % 3 - 1;

            posY += branch;

            BlockPos blockpos = new BlockPos(posX, posY, posZ);

            this.generateNode(world, blockpos);

            heightShift = random.nextInt(6);
        }
    }
}
