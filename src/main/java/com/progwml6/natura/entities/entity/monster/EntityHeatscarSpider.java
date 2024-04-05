package com.progwml6.natura.entities.entity.monster;

import javax.annotation.Nullable;

import com.progwml6.natura.common.config.Config;
import com.progwml6.natura.entities.NaturaEntities;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

public class EntityHeatscarSpider extends EntitySpider
{

    public EntityHeatscarSpider(World worldIn)
    {
        super(worldIn);
        this.setSize(2.85F, 1.9F);
        this.isImmuneToFire = true;
        this.experienceValue = 25;
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(50.0D); // Health
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(24D); // Detection range
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.45000001788139344D); // Movespeed
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4.0D); // Base damage
    }
    
    @Override
    @Nullable
    protected ResourceLocation getLootTable()
    {
        return NaturaEntities.HEATSCAR_SPIDER;
    }

    @Override
    public void jump()
    {
        this.motionY = 0.62D;

        if (this.isPotionActive(MobEffects.JUMP_BOOST))
        {
            this.motionY += (this.getActivePotionEffect(MobEffects.JUMP_BOOST).getAmplifier() + 1) * 0.1F;
        }

        if (this.isSprinting())
        {
            float f = this.rotationYaw * 0.017453292F;
            this.motionX -= MathHelper.sin(f) * 0.2F;
            this.motionZ += MathHelper.cos(f) * 0.2F;
        }

        this.isAirBorne = true;
        ForgeHooks.onLivingJump(this);
    }

    @Override
    public void fall(float distance, float damageMultiplier)
    {
        float[] ret = ForgeHooks.onLivingFall(this, distance, damageMultiplier);
        if (ret == null)
        {
            return;
        }

        super.fall(distance, damageMultiplier);
        int i = MathHelper.ceil(distance - 5.0F);

        if (i > 0)
        {
            if (i > 4)
            {
                this.playSound(SoundEvents.ENTITY_GENERIC_BIG_FALL, 1.0F, 1.0F);
            }
            else
            {
                this.playSound(SoundEvents.ENTITY_GENERIC_SMALL_FALL, 1.0F, 1.0F);
            }

            this.attackEntityFrom(DamageSource.FALL, i);

            BlockPos pos = new BlockPos(MathHelper.floor(this.posX), MathHelper.floor(this.posY - 0.20000000298023224D), MathHelper.floor(this.posZ));
            IBlockState state = this.world.getBlockState(pos);
            Block block = state.getBlock();

            if (block != null)
            {
                SoundType stepsound = block.getSoundType(state, this.world, pos, this);
                this.playSound(stepsound.getStepSound(), stepsound.getVolume() * 0.5F, stepsound.getPitch() * 0.75F);
            }
        }
    }

    @Override
    public boolean attackEntityAsMob(Entity par1Entity)
    {
        if (super.attackEntityAsMob(par1Entity))
        {
            if (par1Entity instanceof EntityLiving)
            {
                byte time = 0;

                if (this.world.getDifficulty() != EnumDifficulty.PEACEFUL)
                {
                    if (this.world.getDifficulty() == EnumDifficulty.NORMAL)
                    {
                        time = 5;
                    }
                    else if (this.world.getDifficulty() == EnumDifficulty.HARD)
                    {
                        time = 10;
                    }
                }

                if (time > 0)
                {
                    par1Entity.setFire(time);
                }
            }

            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public boolean getCanSpawnHere()
    {
        return this.world.getDifficulty() != EnumDifficulty.PEACEFUL && this.world.checkNoEntityCollision(this.getEntityBoundingBox())
                && this.world.getCollisionBoxes(this, this.getEntityBoundingBox()).isEmpty()
                && !this.world.containsAnyLiquid(this.getEntityBoundingBox());
    }

    @Override
    public void setDead()
    {
        if (!this.world.isRemote)
        {
            int num = Config.babyHeatscarSpiderDeathSpawnMaximum - Config.babyHeatscarSpiderDeathSpawnMinimum + 1;
            int amount = this.rand.nextInt(num) + Config.babyHeatscarSpiderDeathSpawnMinimum;
            for (int i = 0; i < amount; i++)
            {
                double f = this.rand.nextDouble() * 2;
                double f1 = this.rand.nextDouble() * 2;
                EntityBabyHeatscarSpider babyspider = new EntityBabyHeatscarSpider(this.world);
                babyspider.setLocationAndAngles(this.posX + f, this.posY + 0.5D, this.posZ + f1, this.rand.nextFloat() * 360.0F, 0.0F);
                this.world.spawnEntity(babyspider);
            }
        }

        super.setDead();
    }
    
    @Override
    public float getEyeHeight()
    {
        return 1.3F;
    }
}
