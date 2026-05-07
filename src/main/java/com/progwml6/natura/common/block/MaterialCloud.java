package com.progwml6.natura.common.block;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class MaterialCloud extends Material
{
    public MaterialCloud()
    {
        super(MapColor.SNOW);
        this.setNoPushMobility();
    }

    /**
     * Will prevent grass from growing on dirt underneath and kill any grass below it if it returns true
     */
    @Override
    public boolean blocksLight()
    {
        return false;
    }
}
