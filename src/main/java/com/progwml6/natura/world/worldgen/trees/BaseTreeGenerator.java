package com.progwml6.natura.world.worldgen.trees;

import com.progwml6.natura.nether.NaturaNether;
import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

public class BaseTreeGenerator implements IWorldGenerator
{
    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
    {
    }

    public void generateTree(Random random, World world, BlockPos pos)
    {
    }

    protected boolean isReplaceable(World world, BlockPos pos)
    {
        IBlockState state = world.getBlockState(pos);
        return state.getBlock().canBeReplacedByLeaves(state, world, pos) || state.getBlock().isReplaceable(world, pos) || state.getMaterial() == Material.PLANTS;
    }

    protected boolean isReplaceableNether(World world, BlockPos pos)
    {
        IBlockState state = world.getBlockState(pos);
        return isReplaceable(world, pos) && state.getBlock() != Blocks.NETHERRACK && state.getBlock() != Blocks.SOUL_SAND && state.getBlock() != NaturaNether.netherTaintedSoil;
    }

    protected void setBlockAndMetadata(World world, BlockPos pos, IBlockState stateNew)
    {
        if (isReplaceable(world, pos))
        {
            world.setBlockState(pos, stateNew, 2);
        }
    }

	protected void setBlockAndMetadataNether(World world, BlockPos pos, IBlockState stateNew)
	{
		if (isReplaceableNether(world, pos))
		{
			world.setBlockState(pos, stateNew, 2);
		}
	}
}
