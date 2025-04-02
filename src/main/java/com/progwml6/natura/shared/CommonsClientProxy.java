package com.progwml6.natura.shared;

import com.progwml6.natura.common.ClientProxy;
import com.progwml6.natura.common.config.Config;

import static com.progwml6.natura.common.ModelRegisterUtil.registerItemBlockMeta;
import static com.progwml6.natura.common.ModelRegisterUtil.registerItemModel;
import static com.progwml6.natura.shared.NaturaCommons.*;

public class CommonsClientProxy extends ClientProxy
{
    @Override
    public void preInit()
    {
        super.preInit();
    }

    @Override
    protected void registerModels()
    {
        registerItemBlockMeta(clouds);

        materials.registerItemModels();
        edibles.registerItemModels();
        seed_bags.registerItemModels();

        if (Config.enableStickVariants)
        {
            sticks.registerItemModels();
        }

        registerItemModel(berry_medley);
        registerItemModel(boneMealBag);
        registerItemModel(glowshroom_stew);
    }
}
