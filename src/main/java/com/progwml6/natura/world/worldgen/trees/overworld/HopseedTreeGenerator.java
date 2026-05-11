package com.progwml6.natura.world.worldgen.trees.overworld;

import java.util.Random;
import com.progwml6.natura.overworld.NaturaOverworld;
import com.progwml6.natura.world.worldgen.trees.BaseTreeGenerator;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.World;

public class HopseedTreeGenerator extends BaseTreeGenerator
{
    public final int minTreeHeight;

    public final int treeHeightRange;

    public final IBlockState log;

    public final IBlockState leaves;

    public final boolean seekHeight;

    public final boolean isSapling;

    public HopseedTreeGenerator(int treeHeight, int treeRange, IBlockState log, IBlockState leaves, boolean seekHeight, boolean isSapling)
    {
        this.minTreeHeight = treeHeight;
        this.treeHeightRange = treeRange;
        this.log = log;
        this.leaves = leaves;
        this.seekHeight = seekHeight;
        this.isSapling = isSapling;
    }

    public HopseedTreeGenerator(int treeHeight, int treeRange, IBlockState log, IBlockState leaves)
    {
        this(treeHeight, treeRange, log, leaves, true, false);
    }

    @Override
    public void generateTree(Random random, World worldIn, BlockPos position)
    {
        int height = random.nextInt(this.treeHeightRange) + this.minTreeHeight;

        if (this.seekHeight)
        {
            while (position.getY() > 1 && worldIn.isAirBlock(position))
            {
                position = position.down();
            }

            if (position.getY() < 0)
            {
                return;
            }
        }

        if (!(this.blocksMatch(worldIn, position)))
        {
            return;
        }

        position = position.up();

        boolean hasSpace = true;

        if (position.getY() >= 1 && position.getY() + height + 1 <= 256)
        {
            int radius;

            for (int y = position.getY(); y <= position.getY() + 1 + height; ++y)
            {
                radius = 1;

                if (y == position.getY())
                {
                    radius = 0;
                }

                if (y >= position.getY() + 1 + height - 2)
                {
                    radius = 2;
                }

                MutableBlockPos mutableblockpos = new MutableBlockPos();

                for (int x = position.getX() - radius; x <= position.getX() + radius && hasSpace; ++x)
                {
                    for (int z = position.getZ() - radius; z <= position.getZ() + radius && hasSpace; ++z)
                    {
                        if (y >= 0 && y < 256)
                        {
                            if (!isReplaceable(worldIn, mutableblockpos.setPos(x, y, z)))
                            {
                                hasSpace = false;
                            }
                        }
                        else
                        {
                            hasSpace = false;
                        }
                    }
                }
            }

            if (hasSpace)
            {
                BlockPos downPosition = position.down();

                IBlockState state = worldIn.getBlockState(downPosition);

                state.getBlock();
                boolean isSoil = state.getBlock().canSustainPlant(state, worldIn, downPosition, EnumFacing.UP, NaturaOverworld.overworldSapling2);

                if (isSoil && position.getY() < 256 - height - 1)
                {
                    this.onPlantGrow(worldIn, downPosition, position);
                    this.onPlantGrow(worldIn, downPosition.east(), position);
                    this.onPlantGrow(worldIn, downPosition.south(), position);
                    this.onPlantGrow(worldIn, downPosition.south().east(), position);

                    this.growLogs(worldIn, position);

                    this.growLeaves(worldIn, random, position, height);

                    for (int l = 0; l < height; ++l)
                    {
                        BlockPos blockpos = position.up(l);

                        IBlockState newState = worldIn.getBlockState(blockpos);

                        if (newState.getBlock() == Blocks.AIR)
                        {
                            worldIn.setBlockState(blockpos, this.log, 0);
                        }

                        if (l < height - 1)
                        {
                            blockpos = position.add(1, l, 0);

                            newState = worldIn.getBlockState(blockpos);

                            if (newState.getBlock() == Blocks.AIR)
                            {
                                worldIn.setBlockState(blockpos, this.log, 0);
                            }

                            blockpos = position.add(1, l, 1);

                            newState = worldIn.getBlockState(blockpos);

                            if (newState.getBlock() == Blocks.AIR)
                            {
                                worldIn.setBlockState(blockpos, this.log, 0);
                            }

                            blockpos = position.add(0, l, 1);

                            newState = worldIn.getBlockState(blockpos);

                            if (newState.getBlock() == Blocks.AIR)
                            {
                                worldIn.setBlockState(blockpos, this.log, 0);
                            }
                        }
                    }
                }
            }
        }
    }

    protected void growLeaves(World worldIn, Random random, BlockPos positionIn, int height)
    {
        for (int y = positionIn.getY() - 2 + height; y <= positionIn.getY() + height; ++y)
        {
            int subtract = y - (positionIn.getY() + height);
            int subtract2 = 2 + 1 - subtract;

            for (int x = positionIn.getX() - subtract2; x <= positionIn.getX() + subtract2; ++x)
            {
                int mathX = x - positionIn.getX();

                for (int z = positionIn.getZ() - subtract2; z <= positionIn.getZ() + subtract2; ++z)
                {
                    int mathZ = z - positionIn.getZ();

                    BlockPos blockpos = new BlockPos(x, y, z);

                    if ((mathX >= 0 || mathZ >= 0 || mathX * mathX + mathZ * mathZ <= subtract2 * subtract2) && (mathX <= 0 && mathZ <= 0 || mathX * mathX + mathZ * mathZ <= (subtract2 + 1) * (subtract2 + 1)) && (random.nextInt(4) != 0 || mathX * mathX + mathZ * mathZ <= (subtract2 - 1) * (subtract2 - 1)) && (isReplaceable(worldIn, blockpos)))
                    {
                        this.setBlockAndMetadata(worldIn, blockpos, this.leaves);
                    }
                }
            }
        }
    }

    private boolean blocksMatch(World world, BlockPos pos)
    {
        IBlockState underState = world.getBlockState(pos);
        Block underBlock = underState.getBlock();
        return underBlock.canSustainPlant(underState, world, pos, EnumFacing.UP, NaturaOverworld.overworldSapling);
    }

    private void growLogs(World worldIn, BlockPos positionIn)
    {
        this.setBlockAndMetadata(worldIn, positionIn, this.log);
        this.setBlockAndMetadata(worldIn, positionIn.add(+1, 0, 0), this.log);
        this.setBlockAndMetadata(worldIn, positionIn.add(0, 0, +1), this.log);
        this.setBlockAndMetadata(worldIn, positionIn.add(+1, 0, +1), this.log);

        this.setBlockAndMetadata(worldIn, positionIn.add(0, +1, 0), this.log);
        this.setBlockAndMetadata(worldIn, positionIn.add(+1, +1, 0), this.log);
        this.setBlockAndMetadata(worldIn, positionIn.add(0, +1, +1), this.log);
        this.setBlockAndMetadata(worldIn, positionIn.add(+1, +1, +1), this.log);
    }

    private void onPlantGrow(World worldIn, BlockPos positionIn, BlockPos source)
    {
        IBlockState state = worldIn.getBlockState(positionIn);

        state.getBlock().onPlantGrow(state, worldIn, positionIn, source);
    }
}
