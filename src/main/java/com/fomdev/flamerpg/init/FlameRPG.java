package com.fomdev.flamerpg.init;

import com.fomdev.flamerpg.registry.FRPGRegistries;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;

@Mod(FlameRPG.MODID)
public class FlameRPG {
    public static final String MODID = "flamerpg";
    public static final Logger LOGGER = LogUtils.getLogger();

    public FlameRPG(IEventBus bus, ModContainer container)
    {
        FRPGRegistries.register(bus);
    }
}
