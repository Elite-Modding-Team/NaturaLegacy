package com.progwml6.natura.shared.block.clouds;

import java.util.Locale;
import java.util.Random;

import com.progwml6.natura.Natura;
import com.progwml6.natura.library.NaturaRegistry;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.mantle.block.EnumBlock;

public class BlockCloud extends EnumBlock<BlockCloud.CloudType>
{
    public static PropertyEnum<CloudType> TYPE = PropertyEnum.create("type", CloudType.class);

    public BlockCloud()
    {
        super(NaturaRegistry.cloud, TYPE, CloudType.class);
        this.setCreativeTab(Natura.TAB);
        this.setHardness(0.2F);
        this.setSoundType(SoundType.CLOTH);
    }

    @Override
    public void onEntityCollision(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
    {
        if (state.getValue(BlockCloud.TYPE) == BlockCloud.CloudType.SULFUR && entityIn instanceof EntityArrow && !worldIn.isRemote)
        {
            EntityArrow entityarrow = (EntityArrow) entityIn;

            if (entityarrow.isBurning())
            {
                this.explode(worldIn, pos, 3, entityarrow.shootingEntity instanceof EntityLiving ? (EntityLiving) entityarrow.shootingEntity : null);

                worldIn.setBlockToAir(pos);

                return;
            }
        }

        if (state.getValue(BlockCloud.TYPE) == BlockCloud.CloudType.SULFUR && entityIn instanceof EntityLivingBase && !worldIn.isRemote)
        {
            EntityLivingBase livingentity = (EntityLivingBase) entityIn;

            if (livingentity.ticksExisted % 20 == 0) {
                livingentity.addPotionEffect(new PotionEffect(MobEffects.POISON, 5 * 20, 0));
                livingentity.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 5 * 20, 0));
            }

            if (livingentity.isBurning())
            {
                this.explode(worldIn, pos, 3, livingentity);

                worldIn.setBlockToAir(pos);

                return;
            }
        }

        if (state.getValue(BlockCloud.TYPE) == BlockCloud.CloudType.ASH && entityIn instanceof EntityLivingBase && !worldIn.isRemote)
        {
            if (entityIn instanceof EntityPlayer) {
                // Boots are protective
                ItemStack stack = ((EntityPlayer) entityIn).inventory.getStackInSlot(36);

                if (stack.isEmpty() && !entityIn.isImmuneToFire() && entityIn instanceof EntityLivingBase && !EnchantmentHelper.hasFrostWalkerEnchantment((EntityLivingBase) entityIn)) {
                    entityIn.attackEntityFrom(DamageSource.HOT_FLOOR, 2.0F);
                }
            } else if (!entityIn.isImmuneToFire() && !(entityIn instanceof EntityPlayer) && entityIn instanceof EntityLivingBase && !EnchantmentHelper.hasFrostWalkerEnchantment((EntityLivingBase) entityIn)) {
                entityIn.attackEntityFrom(DamageSource.HOT_FLOOR, 2.0F);
            }
        }

        if (entityIn.motionY < 0.0D)
        {
            entityIn.motionY *= 0.005D;
        }

        entityIn.fallDistance = 0.0F;
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        ItemStack itemstack = playerIn.getHeldItem(hand);

        if (!itemstack.isEmpty())
        {
            if (state.getValue(BlockCloud.TYPE) == BlockCloud.CloudType.SULFUR && itemstack.getItem() != Items.AIR && itemstack.getItem() == Items.FLINT_AND_STEEL)
            {
                worldIn.setBlockToAir(pos);

                this.explode(worldIn, pos, 1, playerIn);

                return true;
            }
        }

        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
    }

    @Override
    public void onExplosionDestroy(World worldIn, BlockPos pos, Explosion explosionIn)
    {
    }

    public void explode(World world, BlockPos pos, int size, EntityLivingBase living)
    {
        world.createExplosion(living, pos.getX(), pos.getY(), pos.getZ(), size, true);
    }

    @Override
    public boolean canDropFromExplosion(Explosion par1Explosion)
    {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer()
    {
        return BlockRenderLayer.TRANSLUCENT;
    }

    @Deprecated
    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState state, IBlockAccess worldIn, BlockPos pos, EnumFacing side)
    {
        IBlockState iblockstate = worldIn.getBlockState(pos);
        Block block = iblockstate.getBlock();

        if (worldIn.getBlockState(pos.offset(side)) != iblockstate)
        {
            return true;
        }

        if (block == this)
        {
            return false;
        }

        return !worldIn.getBlockState(pos.offset(side)).doesSideBlockRendering(worldIn, pos.offset(side), side.getOpposite());
    }

    @Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        Material material = world.getBlockState(pos).getMaterial();

        return material == this.material ? false : super.isNormalCube(state, world, pos);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
    {
        return new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.01D, 1.0D);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand)
    {
        if (state.getValue(BlockCloud.TYPE) == CloudType.DARK)
        {
            if (world.getBlockState(pos.down()).getBlock().isPassable(world, pos.down()))
            {
                world.spawnParticle(EnumParticleTypes.DRIP_WATER, pos.getX() + rand.nextFloat(), pos.getY(), pos.getZ() + rand.nextFloat(), 0, 0, 0);
            }
        }
    }

    public enum CloudType implements IStringSerializable, EnumBlock.IEnumMeta
    {
        WHITE, DARK, ASH, SULFUR;

        public final int meta;

        CloudType()
        {
            this.meta = this.ordinal();
        }

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
