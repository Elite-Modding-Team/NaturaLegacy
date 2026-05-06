package com.progwml6.natura.common.block.base;

import com.progwml6.natura.Natura;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockPlanks.EnumType;
import net.minecraft.block.SoundType;
import net.minecraft.init.Blocks;

public class BlockFenceGateBase extends BlockFenceGate
{
    public BlockFenceGateBase()
    {
        super(EnumType.OAK);
        this.setHardness(2.0F);
        this.setResistance(5.0F);
        this.setSoundType(SoundType.WOOD);
        this.setCreativeTab(Natura.TAB);
        Blocks.FIRE.setFireInfo(this, 5, 20);
    }

}
