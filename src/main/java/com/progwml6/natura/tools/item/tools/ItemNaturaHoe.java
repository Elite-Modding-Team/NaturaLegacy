package com.progwml6.natura.tools.item.tools;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.progwml6.natura.Natura;

// Unlike normal hoes, the Nether hoes will break certain blocks like leaves faster similar to what was done in 1.16
public class ItemNaturaHoe extends ItemHoe
{
    protected float efficiency;

    public ItemNaturaHoe(ToolMaterial material)
    {
        super(material);

        this.setCreativeTab(Natura.TAB);
        this.efficiency = material.getEfficiency();
    }

    @Override
    public boolean canHarvestBlock(IBlockState block)
    {
        Material material = block.getMaterial();
        return material == Material.LEAVES || material == Material.VINE || material == Material.PLANTS || material == Material.WEB
                || material == Material.SPONGE;
    }

    @Override
    public float getDestroySpeed(ItemStack stack, IBlockState state)
    {
        return canHarvestBlock(state) ? this.efficiency : super.getDestroySpeed(stack, state);
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving)
    {
        if (!worldIn.isRemote && (double) state.getBlockHardness(worldIn, pos) != 0.0D)
        {
            stack.damageItem(1, entityLiving);
        }

        return true;
    }
}
