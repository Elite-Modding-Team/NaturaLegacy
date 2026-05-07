package com.progwml6.natura.overworld.block.flower;

import com.progwml6.natura.Natura;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.EnumPlantType;

import javax.annotation.Nonnull;

public class BlockBluebellsFlower extends BlockBush
{
    public BlockBluebellsFlower()
    {
        super();
        this.setSoundType(SoundType.PLANT);
        this.setCreativeTab(Natura.TAB);
        this.setHardness(0.0F);
    }

    @Override
    public int getFlammability(@Nonnull IBlockAccess world, @Nonnull BlockPos pos, @Nonnull EnumFacing face)
    {
        return Blocks.RED_FLOWER.getFlammability(world, pos, face);
    }

    @Override
    public int getFireSpreadSpeed(@Nonnull IBlockAccess world, @Nonnull BlockPos pos, @Nonnull EnumFacing face)
    {
        return Blocks.RED_FLOWER.getFireSpreadSpeed(world, pos, face);
    }

    @Override
    public @Nonnull Block.EnumOffsetType getOffsetType()
    {
        return Block.EnumOffsetType.XZ;
    }

    @SuppressWarnings("deprecation")
    @Nonnull
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos)
    {
        return BUSH_AABB.offset(state.getOffset(world, pos));
    }

    @Override
    public @Nonnull EnumPlantType getPlantType(@Nonnull IBlockAccess world, @Nonnull BlockPos pos)
    {
        return EnumPlantType.Plains;
    }
}
