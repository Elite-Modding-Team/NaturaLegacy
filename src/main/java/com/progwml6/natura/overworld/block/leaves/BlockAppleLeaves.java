package com.progwml6.natura.overworld.block.leaves;

import java.util.List;
import java.util.Locale;
import java.util.Random;
import javax.annotation.Nonnull;

import com.google.common.collect.Lists;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.progwml6.natura.Natura;
import com.progwml6.natura.common.block.base.BlockLeavesBase;
import com.progwml6.natura.overworld.NaturaOverworld;
import slimeknights.mantle.block.EnumBlock;

public class BlockAppleLeaves extends BlockLeavesBase
{
    public static final PropertyEnum<LeavesType> TYPE = PropertyEnum.create("type", LeavesType.class);

    public BlockAppleLeaves()
    {
        this.setCreativeTab(Natura.TAB);

        Blocks.FIRE.setFireInfo(this, 5, 20);

        this.setDefaultState(this.blockState.getBaseState().withProperty(CHECK_DECAY, false).withProperty(DECAYABLE, true));
    }

    @Nonnull
    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Item.getItemFromBlock(NaturaOverworld.appleSapling);
    }

    @Override
    protected void dropApple(World world, BlockPos pos, IBlockState state, int chance)
    {
        if (state.getValue(TYPE) == LeavesType.FRUIT)
        {
            spawnAsEntity(world, pos, new ItemStack(Items.APPLE));
        }
        else if (state.getValue(TYPE) == LeavesType.GOLDEN_FRUIT)
        {
            spawnAsEntity(world, pos, new ItemStack(Items.GOLDEN_APPLE));
        }
    }

    @Override
    protected int getSaplingDropChance(IBlockState state)
    {
        if (state.getValue(TYPE) == LeavesType.FLOWERING)
        {
            return 20;
        }
        else
        {
            return 25;
        }
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
        LeavesType leavesType = LeavesType.values()[type];
        return this.getDefaultState().withProperty(TYPE, leavesType).withProperty(DECAYABLE, (meta & 4) == 0).withProperty(CHECK_DECAY, (meta & 8) > 0);
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
        if (state.getValue(TYPE) == LeavesType.FRUIT || state.getValue(TYPE) == LeavesType.GOLDEN_FRUIT)
        {
            if (!world.isRemote)
            {
                world.setBlockState(pos, state.withProperty(TYPE, LeavesType.FLOWERING));
                world.playSound(null, pos, SoundEvents.BLOCK_GRASS_BREAK, SoundCategory.BLOCKS, 0.6F, 1.5F);
                spawnAsEntity(world, pos.down(), new ItemStack(state.getValue(TYPE) == LeavesType.GOLDEN_FRUIT ? Items.GOLDEN_APPLE : Items.APPLE));
            }
            return true;
        }
        return false;
    }

    @Nonnull
    @Override
    protected ItemStack getSilkTouchDrop(@Nonnull IBlockState state)
    {
        return new ItemStack(Item.getItemFromBlock(this), 1, (state.getValue(TYPE)).ordinal() & 3);
    }

    @Override
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list)
    {
        for (LeavesType type : LeavesType.values())
        {
            list.add(new ItemStack(this, 1, this.getMetaFromState(this.getDefaultState().withProperty(TYPE, type))));
        }
    }

    @Nonnull
    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, TYPE, CHECK_DECAY, DECAYABLE);
    }

    @Override
    public int quantityDropped(IBlockState state, int fortune, Random random)
    {
        if (state.getValue(TYPE) == LeavesType.FRUIT || state.getValue(TYPE) == LeavesType.GOLDEN_FRUIT)
        {
            return 1;
        }

        return this.quantityDroppedWithBonus(fortune, random);
    }

    @Nonnull
    @Override
    public List<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune)
    {
        IBlockState state = world.getBlockState(pos);
        return Lists.newArrayList(this.getSilkTouchDrop(state));
    }

    public enum LeavesType implements IStringSerializable, EnumBlock.IEnumMeta
    {
        NORMAL, FLOWERING, FRUIT, GOLDEN_FRUIT;

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
