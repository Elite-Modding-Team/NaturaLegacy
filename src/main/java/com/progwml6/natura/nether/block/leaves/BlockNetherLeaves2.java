package com.progwml6.natura.nether.block.leaves;

import java.util.List;
import java.util.Locale;
import java.util.Random;

import javax.annotation.Nonnull;

import com.google.common.collect.Lists;
import com.progwml6.natura.Natura;
import com.progwml6.natura.common.block.base.BlockLeavesBase;
import com.progwml6.natura.nether.NaturaNether;
import com.progwml6.natura.shared.NaturaCommons;

import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import slimeknights.mantle.block.EnumBlock;

public class BlockNetherLeaves2 extends BlockLeavesBase
{
    public static final PropertyEnum<BlockNetherLeaves2.LeavesType> TYPE = PropertyEnum.create("type", BlockNetherLeaves2.LeavesType.class);

    public BlockNetherLeaves2()
    {
        this.setCreativeTab(Natura.TAB);

        Blocks.FIRE.setFireInfo(this, 0, 0);

        this.setDefaultState(this.blockState.getBaseState().withProperty(CHECK_DECAY, false).withProperty(DECAYABLE, true));
    }

    @Override
    public void updateTick(World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state, Random rand)
    {
        super.updateTick(worldIn, pos, state, rand);
    }

    @Override
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list)
    {
        for (LeavesType type : LeavesType.values())
        {
            list.add(new ItemStack(this, 1, this.getMetaFromState(this.getDefaultState().withProperty(TYPE, type))));
        }
    }

    // sapling item
    @Nonnull
    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Item.getItemFromBlock(NaturaNether.netherSapling);
    }

    // sapling meta
    @Override
    public int damageDropped(IBlockState state)
    {
        return 2;
    }

    @Override
    protected void dropApple(World world, BlockPos pos, IBlockState state, int chance)
    {
        if (state.getValue(TYPE) == LeavesType.DARKWOOD_FRUIT)
        {
            spawnAsEntity(world, pos, new ItemStack(NaturaCommons.edibles, 1, 10));
        }
    }

    @Nonnull
    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
    {
        return new ItemStack(Item.getItemFromBlock(this), 1, (state.getValue(TYPE)).ordinal() & 3);
    }

    // item dropped on silktouching
    @Nonnull
    @Override
    protected ItemStack getSilkTouchDrop(@Nonnull IBlockState state)
    {
        return new ItemStack(Item.getItemFromBlock(this), 1, (state.getValue(TYPE)).ordinal() & 3);
    }

    @Nonnull
    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, TYPE, CHECK_DECAY, DECAYABLE);
    }

    @Nonnull
    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        int type = meta % 4;
        if (type < 0 || type >= LeavesType.values().length)
        {
            type = 0;
        }
        LeavesType logtype = LeavesType.values()[type];
        return this.getDefaultState()
                .withProperty(TYPE, logtype)
                .withProperty(DECAYABLE, (meta & 4) == 0)
                .withProperty(CHECK_DECAY, (meta & 8) > 0);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        int meta = (state.getValue(TYPE)).ordinal() & 3; // only first 2 bits

        if (!state.getValue(DECAYABLE))
        {
            meta |= 4;
        }

        if (state.getValue(CHECK_DECAY))
        {
            meta |= 8;
        }

        return meta;
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if (state.getValue(TYPE) == LeavesType.DARKWOOD_FRUIT)
        {
            if (!world.isRemote)
            {
                world.setBlockState(pos, state.withProperty(TYPE, LeavesType.DARKWOOD_FLOWERING));
                world.playSound(null, pos, SoundEvents.BLOCK_GRASS_BREAK, SoundCategory.BLOCKS, 0.6F, 1.5F);
                spawnAsEntity(world, pos.down(), new ItemStack(NaturaCommons.edibles, 1, 10));
            }
            return true;
        }
        return false;
    }

    @Nonnull
    @Override
    public List<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune)
    {
        IBlockState state = world.getBlockState(pos);

        return Lists.newArrayList(this.getSilkTouchDrop(state));
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    @Override
    public int quantityDropped(IBlockState state, int fortune, Random random)
    {
        if (state.getValue(TYPE) == LeavesType.DARKWOOD_FRUIT)
        {
            return 1;
        }

        return this.quantityDroppedWithBonus(fortune, random);
    }

    public enum LeavesType implements IStringSerializable, EnumBlock.IEnumMeta
    {
        DARKWOOD, DARKWOOD_FLOWERING, DARKWOOD_FRUIT;

        public final int meta;

        LeavesType()
        {
            this.meta = this.ordinal();
        }

        @Nonnull
        @Override
        public String getName()
        {
            return this.toString().toLowerCase(Locale.US);
        }

        @Override
        public int getMeta()
        {
            return this.meta;
        }
    }

}
